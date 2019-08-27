package com.scut.se.sehubbackend.utils;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>自定义的用户类型与Spring内置的UserDetails接口间的适配器</p>
 * @param <T> 自定义的用户类型
 */
public interface UserDetailsAdapter<T> {

    /**
     * 将UserDetails转化为自定义的用户类型
     * @param userDetails 要转化的UserDetails
     * @return 转化得的自定义用户类型
     */
    T from(UserDetails userDetails);

    /**
     * 与{@link UserDetailsAdapter#from(UserDetails)}相反
     */
    UserDetails to(T t);

}
