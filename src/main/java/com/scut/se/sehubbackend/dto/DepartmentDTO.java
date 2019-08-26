package com.scut.se.sehubbackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    @JsonProperty("name")
    DepartmentNameEnum departmentName;//部门名称

    List<MemberDTO> memberList;//部门成员

    @JsonProperty("description")
    String departmentDescription;//部门描述
}
