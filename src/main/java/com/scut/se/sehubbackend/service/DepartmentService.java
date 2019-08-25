package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    final ContextHelper<Member> contextHelper;
    final DTOUtil dtoUtil;
    final DepartmentRepository departmentRepository;

    public DepartmentService(ContextHelper<Member> contextHelper, DTOUtil dtoUtil, DepartmentRepository departmentRepository) {
        this.contextHelper = contextHelper;
        this.dtoUtil = dtoUtil;
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDTO getCurrentDepartment(){
        return dtoUtil.toDTO(
                contextHelper.getCurrentPrincipal().getDepartment());
    }

    public Department findById(DepartmentNameEnum departmentName) throws InvalidIdException {return departmentRepository.findById(departmentName).orElseThrow(InvalidIdException::new);}
    public void save(Department department){departmentRepository.saveAndFlush(department);}
}

