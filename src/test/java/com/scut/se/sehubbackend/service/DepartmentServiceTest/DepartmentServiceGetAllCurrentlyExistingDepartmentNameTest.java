package com.scut.se.sehubbackend.service.DepartmentServiceTest;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.service.DepartmentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.scut.se.sehubbackend.enumeration.DepartmentNameEnum.Quality;
import static com.scut.se.sehubbackend.enumeration.DepartmentNameEnum.Research;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class DepartmentServiceGetAllCurrentlyExistingDepartmentNameTest {

    @Autowired DepartmentRepository departmentRepository;
    @Autowired DepartmentService departmentService;

    /**
     * 简单测试，验证只返回数据库中有的部门名
     */
    @Test
    public void testGetAllCurrentlyExistingDepartmentName() { verifyReturnedValue(departmentService.getAllCurrentlyExistingDepartmentName()); }

    private void verifyReturnedValue(List<String> names){
        assertTrue(names.contains(name1.toString()));
        assertTrue(names.contains(name2.toString()));
        assertEquals(2,names.size());
    }

    @Before
    public void setUp() {
        Department department1= Department.builder().departmentName(name1).memberList(new ArrayList<>()).build();
        Department department2= Department.builder().departmentName(name2).memberList(new ArrayList<>()).build();

        departmentRepository.saveAndFlush(department1);
        departmentRepository.saveAndFlush(department2);
    }

    private DepartmentNameEnum name1=Research;
    private DepartmentNameEnum name2=Quality;

    @After
    public void tearDown() {
        departmentRepository.deleteAll();
    }
}