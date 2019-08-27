package com.scut.se.sehubbackend.service.DepartmentServiceTest;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.service.DepartmentService;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.scut.se.sehubbackend.enumeration.DepartmentNameEnum.Research;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class DepartmentServiceTest {

    @MockBean MemberContextHelper memberContextHelper;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired DepartmentService departmentService;


    /**
     * 测试获取当前部门信息
     */
    @Test
    public void getCurrentDepartment() {
        assertEquals(
            Research,
            departmentService.getCurrentDepartment().getDepartmentName());
    }

    @Before
    public void setUp() {
        department.addMember(currentMember);
        department.addMember(anotherMember);
        departmentRepository.saveAndFlush(department);

        doReturn(currentMember).when(memberContextHelper).getCurrentPrincipal();
    }

    private Member currentMember= Member.builder()
            .studentNumber(201730683314L)
            .password("123")
            .name("光度")
            .position(PositionEnum.Minister)
            .build();
    private Member anotherMember= Member.builder()
            .studentNumber(201830662011L)
            .password("123")
            .name("光度")
            .position(PositionEnum.Minister)
            .build();
    private Department department= Department.builder()
            .departmentName(Research)
            .memberList(new ArrayList<>())
            .departmentDescription("")
            .build();

    @After
    public void tearDown() {
        departmentRepository.deleteAll();
    }
}