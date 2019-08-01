package com.scut.se.sehubbackend.Security.Authorization;

import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Domain.user.UserAuthorityRecord;
import com.scut.se.sehubbackend.Repository.user.UserAuthenticationRepository;
import com.scut.se.sehubbackend.Security.Authorization.interfaces.AuthorityManager;
import com.scut.se.sehubbackend.Security.Authorization.interfaces.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * 一个简单的权限管理者的实现<br/>
 * 根据学号查询数据库中的用户，对权限变更后更新数据库<br/>
 * 字符串构造权限的服务委派给{@code mapper}提供
 * @see AuthorityManager
 */
@Service
public class SimpleAuthorityManager implements AuthorityManager {

    @Autowired AuthorityMapper mapper;
    @Autowired UserAuthenticationRepository userRepository;

    @Override
    public Boolean addAuthority(UserDetails user, GrantedAuthority authority) {
        Optional<UserAuthentication> userToBeGrantedOpt=userRepository.findById(user.getUsername());//根据学号筛选

        if (userToBeGrantedOpt.isPresent()){//检测学号对应用户是否存在
            UserAuthentication userToBeGranted=userToBeGrantedOpt.get();//获取用户
            Set<GrantedAuthority> grantedAuthoritySet= UserAuthorityRecord.toGrantedAuthorities(userToBeGranted.getAuthorityRecords());//获取权限列表
            if(!grantedAuthoritySet.contains(authority)) {//如果没有此权限进行追加并存储
                // ？
                Set<GrantedAuthority> changeable=new HashSet<>(grantedAuthoritySet);//防止查询本身是由数组转化而成导致的不可修改
                changeable.add(authority);//添加权限
                userToBeGranted.setAuthorityRecords(changeable);//重设
                userRepository.save(userToBeGranted);
            }
            return true;
        }else return false;//用户不存在
    }

    @Override
    public Boolean removeAuthority(UserDetails user, GrantedAuthority authority) {
        Optional<UserAuthentication> userToBeGrantedOpt=userRepository.findById(user.getUsername());//根据学号筛选

        if (userToBeGrantedOpt.isPresent()){//检测学号对应用户是否存在
            UserAuthentication userToBeGranted=userToBeGrantedOpt.get();//获取用户
            Set<GrantedAuthority> grantedAuthoritySet=UserAuthorityRecord.toGrantedAuthorities(userToBeGranted.getAuthorityRecords());//获取权限列表
            if(grantedAuthoritySet.contains(authority)) {//如果没有此权限进行追加并存储
                Set<GrantedAuthority> changeable=new HashSet<>(grantedAuthoritySet);//防止查询本身是由数组转化而成导致的不可修改
                changeable.remove(authority);//移除权限
                userToBeGranted.setAuthorityRecords(changeable);//重设
                userRepository.save(userToBeGranted);
            }
            return true;
        }else return false;//用户不存在
    }

    /**
     * 委派给{@code mapper}提供
     * @param authority 输入的字符串
     * @return 构造出的权限
     */
    @Override
    public GrantedAuthority generateAuthority(String authority) {
        return mapper.map(authority);
    }
}
