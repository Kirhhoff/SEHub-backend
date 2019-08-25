package com.scut.se.sehubbackend.security;

import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import org.springframework.security.core.GrantedAuthority;

/**
 * Role也是一种权限，但是一般不会随意变更，更多地是描述一种职位，如部长等等
 */
public class Role implements GrantedAuthority {

    public final static String ROLE_PREFIX="ROLE_";
    private String role;

    public Role(PositionEnum position){
        this.role=ROLE_PREFIX+position;
    }

    public Role(DepartmentNameEnum departmentName){
        this.role=ROLE_PREFIX+departmentName;
    }
    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof GrantedAuthority && ((GrantedAuthority) o).getAuthority().equals(role);
    }
}
