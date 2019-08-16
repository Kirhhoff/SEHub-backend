package com.scut.se.sehubbackend.security.authorization.interfaces;

import com.scut.se.sehubbackend.security.authorization.PositionAndDepartmentBasedAuthorizationDecisionManger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 提供权限变更是否有权进行的决策服务<br/>
 * 决定{@code operator}对{@code user}的权限是否有权变更<br/>
 * 提供了一个基于职位和部门的默认实现{@link PositionAndDepartmentBasedAuthorizationDecisionManger}
 * @see PositionAndDepartmentBasedAuthorizationDecisionManger
 */
public interface AuthorizationDecisionManager {

    /**
     * 决定{@code operator}对{@code user}的{@code dynamicAuthority}权限是否有权变更
     * @param operator 变更行为的发起者
     * @param user 变更行为的承受者
     * @param dynamicAuthority 要变更的权限
     * @return 是否有权变更
     */
    /*public*/ Boolean decide(UserDetails operator,UserDetails user, GrantedAuthority dynamicAuthority);

}
