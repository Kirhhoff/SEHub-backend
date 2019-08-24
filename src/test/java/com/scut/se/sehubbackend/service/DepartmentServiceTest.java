package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.utils.DTOUtil;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class DepartmentServiceTest {

    @MockBean MemberContextHelper memberContextHelper;
    @MockBean DTOUtil dtoUtil;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired DepartmentService departmentService;


    /**
     * 测试获取当前部门信息
     */
    @Test
    public void getCurrentDepartment() {
        assertEquals(
            expectedDepartmentDTO,
            departmentService.getCurrentDepartment());
    }

    @Before
    public void setUp() {
        department.addMember(currentMember);
        department.addMember(anotherMember);
        departmentRepository.saveAndFlush(department);

        doReturn(currentMember).when(memberContextHelper).getCurrentPrincipal();
        when(dtoUtil.toDTO(department)).thenReturn(expectedDepartmentDTO);
    }

    private Member currentMember= Member.builder()
            .studentNumber(201730683314L)
            .build();
    private Member anotherMember= Member.builder()
            .studentNumber(201830662011L)
            .build();
    private Department department= Department.builder()
            .departmentName(DepartmentNameEnum.Research)
            .memberList(new ArrayList<>())
            .departmentDescription("")
            .build();
    private DepartmentDTO expectedDepartmentDTO= DepartmentDTO.builder()
            .departmentName(DepartmentNameEnum.Research)
            .build();
}