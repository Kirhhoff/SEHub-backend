package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final ContextHelper<Member> contextHelper;
    private final DTOUtil dtoUtil;
    private final DepartmentRepository departmentRepository;

    public DepartmentService(ContextHelper<Member> contextHelper, DTOUtil dtoUtil, DepartmentRepository departmentRepository) {
        this.contextHelper = contextHelper;
        this.dtoUtil = dtoUtil;
        this.departmentRepository = departmentRepository;
    }

    /**
     * 获取当前用户的部门信息，不会报id不存在的错误，因为除admin外均有部门，而admin也不需要部门信息
     * @return 当前用户的部门信息
     */
    @Transactional
    public DepartmentDTO getCurrentDepartment(){
        DepartmentNameEnum departmentName=contextHelper.getCurrentPrincipal().getDepartment().getDepartmentName();

        //忽略空异常
        Department department= null;
        try {
            department = findById(departmentName);
        } catch (InvalidIdException ignored) {
        }

        return dtoUtil.toDTO(department);
    }

    /**
     * 获取当前数据库存在的所有部门的名字列表
     * @return 当前数据库存在的所有部门的名字列表
     */
    public List<String> getAllCurrentlyExistingDepartmentName(){
        List<Department> departments=findAll();
        List<String> names=new ArrayList<>();
        for (Department department:departments)
            names.add(department.getDepartmentName().toString());
        return names;
    }

    @PreAuthorize("hasRole('Admin')") public Department findById(DepartmentNameEnum departmentName) throws InvalidIdException {return departmentRepository.findById(departmentName).orElseThrow(InvalidIdException::new);}
    public void save(Department department){departmentRepository.saveAndFlush(department);}
    public List<Department> findAll(){return departmentRepository.findAll();}
}

