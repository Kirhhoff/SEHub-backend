package com.scut.se.sehubbackend.Security.Authorization;

import com.scut.se.sehubbackend.Enumeration.DepartmentEnum;
import com.scut.se.sehubbackend.Enumeration.DynamicDetail;
import com.scut.se.sehubbackend.Enumeration.Position;
import com.scut.se.sehubbackend.Security.Authorization.interfaces.AuthorityMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 一个简单的权限映射策略，用于规范输入字符串<br/>
 * 内部维护一个哈希表，由字符串映射到权限。所有的权限都是在类的初始化阶段静态加载的，后续的查找都是在这个哈希表中查找
 */
// 内部维护一个哈希表，由字符串映射到权限。所有的权限都是在类的初始化阶段静态加载的，后续的查找都是在这个哈希表中查找
// 查找有多个接口，区别接受的参数不同，但是共同点是用接受的参数构造字符串再在哈希表中查找权限
@Service
public class HashAuthorityMapper implements AuthorityMapper {

    private static Map<String,GrantedAuthority> authorityMapper =new HashMap<>();//内部维护的映射表
    private static final String SEPARATOR ="_";//分割符
    private static final String STANDING_COMMITTEE_PREFIX=Position.StandingCommittee.toString();//常委前缀
    private static final String MINISTER_PREFIX = Position.Minister.toString();//部长前缀
    private static final String STUFF_PREFIX=Position.Staff.toString();//部员前缀
    private static final String MUTABLE_PREFIX="Dynamic";//动态权限前缀

    /**
     * 将预定义的权限放入字典中
     */
    // 静态方法，权限字符串：职位 + 部门 or 部门
    static {
         authorityMapper.put(Position.StandingCommittee.toString(),new SimpleGrantedAuthority(STANDING_COMMITTEE_PREFIX));//常委权限
        for (DepartmentEnum departmentEnum : DepartmentEnum.values()){
            //部长权限
            authorityMapper.put(MINISTER_PREFIX + SEPARATOR + departmentEnum.toString(),new SimpleGrantedAuthority(MINISTER_PREFIX + SEPARATOR + departmentEnum.toString()));
            //部员权限
            authorityMapper.put(STUFF_PREFIX + SEPARATOR + departmentEnum.toString(),new SimpleGrantedAuthority(STUFF_PREFIX + SEPARATOR + departmentEnum.toString()));
            //部门动态权限
            authorityMapper.put(MUTABLE_PREFIX+ SEPARATOR + departmentEnum.toString(),new SimpleGrantedAuthority(MUTABLE_PREFIX+ SEPARATOR + departmentEnum.toString()));
        }
        authorityMapper.put(MUTABLE_PREFIX+ SEPARATOR + DepartmentEnum.Media+SEPARATOR+DynamicDetail.NewMediaApplication,new SimpleGrantedAuthority(MUTABLE_PREFIX+ SEPARATOR + DepartmentEnum.Media+SEPARATOR+DynamicDetail.NewMediaApplication));
        authorityMapper.put(MUTABLE_PREFIX+ SEPARATOR + DepartmentEnum.Media+SEPARATOR+DynamicDetail.ReporterApplication,new SimpleGrantedAuthority(MUTABLE_PREFIX+ SEPARATOR + DepartmentEnum.Media+SEPARATOR+DynamicDetail.ReporterApplication));
    }

    /**
     * 直接由{@link HashAuthorityMapper#authorityMapper}查找
     * @param authority 输入的字符串
     * @return 查找到的权限
     */
    @Override
    public GrantedAuthority map(String authority) {
        return authorityMapper.get(authority);
    }

    /**
     * 描述见{@link AuthorityMapper#map(Position, DepartmentEnum)}<br/>
     * 这里直接由{@code position}和{@code departmentEnum}构造字符串后调用{@link #map(String)}
     * @param position 权限对应的职位
     * @param departmentEnum 权限对应的部门
     * @return 查找到的权限
     */
    @Override
    public GrantedAuthority map(Position position, DepartmentEnum departmentEnum) {
        if(position==null)//职位为空时非法，返回空
            return null;
        return departmentEnum ==null?map(position.toString()):map(position.toString()+ SEPARATOR + departmentEnum.toString());
    }

    /**
     * 描述见{@link AuthorityMapper#mapDynamic(DepartmentEnum, DynamicDetail)}<br/>
     * 这里直接由{@code departmentEnum}和{@code detail}构造字符串后调用{@link #map(String)}
     * @param departmentEnum 动态权限所属的部门
     * @param detail 用于标志同一部门的不同权限，目前因每个部门只有至多一种动态权限，传入{@code null}即可
     * @return 查找到的权限
     */
    @Override
    public GrantedAuthority mapDynamic(DepartmentEnum departmentEnum, DynamicDetail detail) {
        if (departmentEnum ==null)//部门为空时（如常委）直接返回空
            return null;
        return detail==null?map(MUTABLE_PREFIX+ SEPARATOR + departmentEnum.toString()):map(MUTABLE_PREFIX+ SEPARATOR + departmentEnum.toString()+ SEPARATOR +detail.toString());
    }

    @Override
    public Set<GrantedAuthority> mapAllDynamic(DepartmentEnum departmentEnum) {
        Set<GrantedAuthority> grantedAuthoritySet=new HashSet<>();
        if(departmentEnum !=null){
            if(departmentEnum == DepartmentEnum.Media){
                grantedAuthoritySet.add(mapDynamic(DepartmentEnum.Media,DynamicDetail.NewMediaApplication));
                grantedAuthoritySet.add(mapDynamic(DepartmentEnum.Media,DynamicDetail.ReporterApplication));
            }else
                grantedAuthoritySet.add(mapDynamic(departmentEnum,null));
            return grantedAuthoritySet;
        }
        return grantedAuthoritySet;
    }

}
