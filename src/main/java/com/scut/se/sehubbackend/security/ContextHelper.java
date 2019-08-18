package com.scut.se.sehubbackend.security;

import org.springframework.security.core.Authentication;

public interface ContextHelper<T> {

    /**
     * 获取当前的自定义用户
     * @return 当前的自定义用户
     */
    T getCurrentPrincipal();

    /**
     * 获取当前的Authentication
     * @return 当前的Authentication
     */
    Authentication getCurrentAuthentication();
}
