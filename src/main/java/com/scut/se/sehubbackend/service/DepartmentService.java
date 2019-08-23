package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    final ContextHelper<Member> contextHelper;
    final DTOUtil dtoUtil;

    public DepartmentService(ContextHelper<Member> contextHelper, DTOUtil dtoUtil) {
        this.contextHelper = contextHelper;
        this.dtoUtil = dtoUtil;
    }

    DepartmentDTO getCurrentDepartment(){
        return dtoUtil.toDTO(
                contextHelper.getCurrentPrincipal().getDepartment());
    }
}

