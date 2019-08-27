package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.ContextHelper;
import com.scut.se.sehubbackend.security.Role;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.*;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Admin;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Minister;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ContextHelper<Member> contextHelper;
    private final DepartmentService departmentService;
    private final String defaultPassword;
    private final DTOUtil dtoUtil;

    public MemberService(MemberRepository memberRepository, ContextHelper<Member> contextHelper, DepartmentService departmentService, @Qualifier("defaultPassword") String defaultPassword, DTOUtil dtoUtil) {
        this.memberRepository = memberRepository;
        this.contextHelper = contextHelper;
        this.departmentService = departmentService;
        this.defaultPassword = defaultPassword;
        this.dtoUtil = dtoUtil;
    }

    /**
     * <p>从用户的角度更新一个人的信息，可更新的包括姓名、手机、邮箱</p>
     * @param memberDTO 请求中的用户信息
     */
    public void update(MemberDTO memberDTO){
        Member currentMember=contextHelper.getCurrentPrincipal();
        //确保要修改的用户就是当前用户
        if (!currentMember.getStudentNumber().equals(memberDTO.getStudentNumber()))
            throw new AccessDeniedException("");
        else {
            currentMember.setName(memberDTO.getName());
            currentMember.setPhoneNumber(memberDTO.getPhoneNumber());
            currentMember.setEmail(memberDTO.getEmail());
            save(currentMember);
        }
    }

    /**
     * <p>从管理员的角度修改一个人的信息，只能在姓名、职位、部门范围内修改</p>
     * @param memberDTO 请求中的用户信息
     */
    @PreAuthorize("hasRole('Admin')")
    @Transactional
    public void modify(MemberDTO memberDTO) throws InvalidIdException {
        //管理员既不能修改自己的信息，也不能把其他人设为管理员
        if(memberDTO.getPosition()==Admin)
            throw new AccessDeniedException("");

        //检查部门是否被修改，如果是的话，则相应地更新部门关联
        Member memberInDatabase=findById(memberDTO.getStudentNumber());
        Department initialDepartment=memberInDatabase.getDepartment();
        DepartmentNameEnum newDepartmentName=memberDTO.getDepartmentName();
        Department newDepartment=null;
        if (newDepartmentName!=initialDepartment.getDepartmentName()){
            newDepartment=departmentService.findById(newDepartmentName);
            initialDepartment.removeMember(memberInDatabase);
            newDepartment.addMember(memberInDatabase);
        }

        //更行姓名和职位
        memberInDatabase.setName(memberDTO.getName());
        memberInDatabase.setPosition(memberDTO.getPosition());

        //重新计算权限
        memberInDatabase.removeAllAuthorities();
        memberInDatabase.addAllAuthorities(
                buildAuthority(memberDTO.getDepartmentName(),memberDTO.getPosition()));

        //如果更新了部门，则通过部门更新级联更新用户信息，否则只更新用户信息
        if (newDepartment!=null){
            departmentService.save(initialDepartment);
            departmentService.save(newDepartment);
        }else
            save(memberInDatabase);
    }

    /**
     * <p>创建一个新用户，管理员才能执行</p>
     * @param memberDTO 新用户的信息，需包括学号、姓名、职位、部门
     */
    @PreAuthorize("hasRole('Admin')")
    public void create(MemberDTO memberDTO) throws InvalidIdException {
        Long studentNumber=memberDTO.getStudentNumber();

        //要确保新学号不存在，也就是要确保这里一定扔出异常，如果不扔出异常，说明学号已经存在，则报错
        try {
            findById(studentNumber);
        } catch (InvalidIdException e) {
            //确保新学号不存在后，要确保所属的部门是存在的，否则也需要报异常
            Department department=departmentService.findById(memberDTO.getDepartmentName());

            //不允许创建管理员
            PositionEnum position=memberDTO.getPosition();
            if(position==Admin)
                throw new InvalidIdException();

            //构建新用户信息，权限根据计算得到
            Member member= Member.builder()
                    .studentNumber(studentNumber)
                    .password(defaultPassword)
                    .name(memberDTO.getName())
                    .position(position)
                    .authorityList(new ArrayList<>())
                    .build();
            member.addAllAuthorities(
                    buildAuthority(department.getDepartmentName(),position));

            //通过更新部门级联更新用户信息
            department.addMember(member);
            departmentService.save(department);

            return;
        }

        //学号已存在或部门不存在
        throw new InvalidIdException();
    }

    /**
     * 获取所有部门名字和所有成员信息（前端的奇怪要求，没办法只能照做，返回的类型都是临时的Map）
     * @return 字符串形式的部门名字和MemberDTO形式的所有成员列表
     */
    @PreAuthorize("hasRole('Admin')")
    public Map<String,Object> getAllDepartmentNameAndAllMember(){
        Map<String,Object> data=new HashMap<>();
        data.put("allDepartment", departmentService.getAllCurrentlyExistingDepartmentName());

        List<Member> allMemberExceptAdmin=memberRepository.findAllByPositionNot(Admin);
        List<MemberDTO> allMemberDTO=new ArrayList<>();
        for (Member member:allMemberExceptAdmin)
            allMemberDTO.add(dtoUtil.toDTO(member));
        data.put("allMember",allMemberDTO);
        return data;
    }

    public Member findById(Long id) throws InvalidIdException { return memberRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(Member member){memberRepository.saveAndFlush(member);}
    @PreAuthorize("hasRole('Admin')") public void delete(Long studentNumber){memberRepository.deleteById(studentNumber);}

    /**
     * 通过一个成员的部门和职位计算这个成员应当具有的权限，部员默认不设置动态权限
     * @param department 部门名
     * @param position 职位名
     * @return 构建好待add到具体member的权限list
     */
    private List<Authority> buildAuthority(DepartmentNameEnum department, PositionEnum position){
        List<Authority> authorities=new ArrayList<>();

        //静态的部门、职位权限
        authorities.add(Authority.builder().authorityName(new Role(department).getAuthority()).build());
        authorities.add(Authority.builder().authorityName(new Role(position).getAuthority()).build());

        //对于部长，根据部门设置动态权限
        if(position==Minister){
            switch (department){
                case Relation:authorities.add(Authority.builder().authorityName(Etiquette.toString()).build());
                    break;
                case Secretary:authorities.add(Authority.builder().authorityName(Host.toString()).build());
                    break;
                case Research:authorities.add(Authority.builder().authorityName(LectureTicket.toString()).build());
                    break;
                case Propaganda:authorities.add(Authority.builder().authorityName(Poster.toString()).build());
                    break;
            }
        }
        return authorities;
    }
}
