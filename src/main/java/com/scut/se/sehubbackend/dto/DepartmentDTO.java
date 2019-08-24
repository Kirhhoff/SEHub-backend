package com.scut.se.sehubbackend.dto;


import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DepartmentDTO {

    DepartmentNameEnum departmentName;//部门名称

    List<MemberDTO> memberList;//部门成员

    String departmentDescription;//部门描述
}
