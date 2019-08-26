package com.scut.se.sehubbackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    Long studentNumber;//学号

    String name;//名字

    PositionEnum position;//职位

    @JsonProperty("phone")
    String phoneNumber;//手机号

    String email;//邮箱

    @JsonProperty("department")
    DepartmentNameEnum departmentName;//部门名称

    @JsonProperty("authorities")
    List<String> authorityList;//字符串形式的权限列表
}
