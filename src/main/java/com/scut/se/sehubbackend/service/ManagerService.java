package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.user.UserAuthentication;
import com.scut.se.sehubbackend.domain.user.UserHistory;
import com.scut.se.sehubbackend.domain.user.UserInformation;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.enumeration.SeStatus;
import com.scut.se.sehubbackend.other.Response;
import com.scut.se.sehubbackend.dao.user.UserAuthenticationRepository;
import com.scut.se.sehubbackend.security.authorization.interfaces.AuthorityMapper;
import com.scut.se.sehubbackend.UserDAORequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service
public class ManagerService {

    @Autowired AuthorityMapper authorityMapper;
    @Autowired UserAuthenticationRepository userAuthenticationRepository;

    public Response createUser(UserDAORequest userDAORequest){
        UserHistory userHistory=UserHistory.builder()
                .year(userDAORequest.getYear())
                .departmentNameEnum(userDAORequest.getDepartmentNameEnum())
                .positionEnum(userDAORequest.getPositionEnum())
                .build();

        UserInformation userInformation= UserInformation.builder()
                .name(userDAORequest.getName())
                .build();

        UserAuthentication userAuthentication=UserAuthentication.builder()
                .studentNO(userDAORequest.getStudentNO())
                .password(userDAORequest.getPassword())
                .userHistory(userHistory)
                .userInformation(userInformation)
                .build();

        GrantedAuthority staticAuthority=authorityMapper.map(userDAORequest.getPositionEnum(),userDAORequest.getDepartmentNameEnum());
        if(staticAuthority!=null){
            Set<GrantedAuthority> authorityRecords=new HashSet<>();
            authorityRecords.add(staticAuthority);
            if(userDAORequest.getPositionEnum()== PositionEnum.Minister)
                authorityRecords.addAll(authorityMapper.mapAllDynamic(userDAORequest.getDepartmentNameEnum()));
            userAuthentication.setAuthorityRecords(authorityRecords);
        }
        userHistory.setUserAuthentication(userAuthentication);
        userInformation.setUserAuthentication(userAuthentication);

        userAuthenticationRepository.save(userAuthentication);
        return new Response(SeStatus.Success,userAuthentication);
    }

    public Response deleteUser(@RequestBody UserDAORequest userDAORequest){
        userAuthenticationRepository.deleteById(userDAORequest.getStudentNO());
        return new Response(SeStatus.Success);
    }
}
