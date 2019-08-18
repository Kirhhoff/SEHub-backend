package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.ActivityApplicationRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ActivityApplicationDTO;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * <p>对{@link ActivityApplicationService}的集成测试</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class ActivityApplicationServiceTest {

    @Autowired ActivityApplicationService activityApplicationService;
    @MockBean MemberRepository memberRepository;
    @MockBean ActivityApplicationRepository activityApplicationRepository;
    @MockBean DTOUtil dtoUtil;


    /**
     * 构建一个用户环境，测试可以获取到当前用户所在部门的所有申请表
     */
    @Test
    public void testGetAllApplication(){
        assertEquals(
                expectedActivityApplicationDTOs,
                activityApplicationService.getAllActivityApplicationOfCurrentDepartment()
        );
    }

    @Before
    public void before(){
        setSecurityContext();
        prepareData();

        when(memberRepository.findById(currentUserId)).thenReturn(ofNullable(mockMember));
        when(activityApplicationRepository.findAllByDepartment(mockDepartment)).thenReturn(mockActivityApplications);
        when(dtoUtil.toDTO(mockActivityApplications)).thenReturn(expectedActivityApplicationDTOs);
    }

    private void setSecurityContext(){
        SecurityContextHolder.getContext().setAuthentication(
                new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return String.valueOf(currentUserId);
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                    }

                    @Override
                    public String getName() {
                        return null;
                    }
                }
        );
    }
    private void prepareData(){
        mockDepartment= Department.builder().build();
        mockMember= Member.builder()
                .studentNumber(currentUserId)
                .department(mockDepartment)
                .build();

        ActivityApplication activityApplication1=ActivityApplication.builder()
                .checkStatus(CheckStatusEnum.WAIT)
                .build();
        ActivityApplication activityApplication2=ActivityApplication.builder()
                .checkStatus(CheckStatusEnum.PASS)
                .build();
        mockActivityApplications.add(activityApplication1);
        mockActivityApplications.add(activityApplication2);

        ActivityApplicationDTO activityApplicationDTO1=ActivityApplicationDTO.builder()
                .checkFeedback("稍等")
                .build();
        ActivityApplicationDTO activityApplicationDTO2=ActivityApplicationDTO.builder()
                .checkFeedback("带我一个")
                .build();

        expectedActivityApplicationDTOs.add(activityApplicationDTO1);
        expectedActivityApplicationDTOs.add(activityApplicationDTO2);
    }

    private Long currentUserId=201730683314L;
    private Department mockDepartment;
    private Member mockMember;
    private List<ActivityApplication> mockActivityApplications=new ArrayList<>();
    private List<ActivityApplicationDTO> expectedActivityApplicationDTOs=new ArrayList<>();

}