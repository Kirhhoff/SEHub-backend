package com.scut.se.sehubbackend.security.authorization;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;

/**
 * 提供权限变更是否有权进行的决策服务<br/>
 */
public interface AuthorizationDecisionManager {

    /**
     * 决定operator对target的权限是否有权变更
     * @param operator 操作者
     * @param target 变更的对象
     * @param authority 要变更的权限
     * @return 是否有权变更
     */
    Boolean decide(Member operator,Member target, AuthorityEnum authority);

}
