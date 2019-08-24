package com.scut.se.sehubbackend.dto;


import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MemberDTO {

    Long studentNumber;//学号

    String name;//名字

    PositionEnum position;//职位

    String phoneNumber;//手机号

    String email;//邮箱

    DepartmentNameEnum departmentName;//部门名称

    List<String> authorityList;//字符串形式的权限列表
}
