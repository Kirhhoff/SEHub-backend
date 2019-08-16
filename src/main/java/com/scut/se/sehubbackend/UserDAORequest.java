package com.scut.se.sehubbackend;

import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDAORequest implements Serializable {

    private static final Long serialVersionUID=8L;

    private String studentNO;
    private String password;
    private String name;
    private Long year;
    private DepartmentNameEnum departmentNameEnum;
    private PositionEnum positionEnum;
    private List<GrantedAuthority> grantedAuthorities;
}
