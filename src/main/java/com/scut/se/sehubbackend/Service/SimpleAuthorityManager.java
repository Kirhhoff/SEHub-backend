package com.scut.se.sehubbackend.Service;

import com.scut.se.sehubbackend.Domain.User;
import com.scut.se.sehubbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleAuthorityManager implements AuthorityManager {

    @Autowired AuthorityMapper mapper;
    @Autowired UserRepository userRepository;

    @Override
    public Boolean addAuthority(UserDetails user, GrantedAuthority authority) {
        Optional<User> userToBeGrantedOpt=userRepository.findById(user.getUsername());//根据学号筛选

        if (userToBeGrantedOpt.isPresent()){//检测学号对应用户是否存在
            User userToBeGranted=userToBeGrantedOpt.get();//获取用户
            List<GrantedAuthority> grantedAuthorityList=userToBeGranted.getGrantedAuthorities();//获取权限列表
            if(!grantedAuthorityList.contains(authority)) {//如果没有此权限进行追加并存储
                grantedAuthorityList.add(authority);
                userRepository.save(userToBeGranted);
            }
            return true;
        }else return false;//用户不存在
    }

    @Override
    public Boolean removeAuthority(UserDetails user, GrantedAuthority authority) {
        Optional<User> userToBeGrantedOpt=userRepository.findById(user.getUsername());//根据学号筛选

        if (userToBeGrantedOpt.isPresent()){//检测学号对应用户是否存在
            User userToBeGranted=userToBeGrantedOpt.get();//获取用户
            List<GrantedAuthority> grantedAuthorityList=userToBeGranted.getGrantedAuthorities();//获取权限列表
            if(grantedAuthorityList.contains(authority)) {//如果没有此权限进行追加并存储
                grantedAuthorityList.remove(authority);
                userRepository.save(userToBeGranted);
            }
            return true;
        }else return false;//用户不存在
    }

    @Override
    public GrantedAuthority generateAuthority(String authority) {
        return mapper.map(authority);
    }
}
