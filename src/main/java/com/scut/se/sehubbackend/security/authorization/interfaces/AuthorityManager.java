package com.scut.se.sehubbackend.security.authorization.interfaces;

import com.scut.se.sehubbackend.security.authorization.SimpleAuthorityManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 权限管理服务<br/>
 * 应当实现的功能应当包括增加、移除权限等等，同时还提供通过字符串生成权限的功能<br/>
 * <b>注意这个服务只负责执行，该不该执行由上层调用者决定</b><br/>
 * 具体的实现类可以参考{@link SimpleAuthorityManager}
 * @see SimpleAuthorityManager
* */
public interface AuthorityManager {
    /**
     * 对某一个{@code user}增加一个指定的权限{@code authority}
     * @param user 要变更的用户
     * @param authority 要增加的权限
     * @return 若用户执行了添加或用户已有此权限，则返回true；若因未找到用户等原因导致操作未执行则返回false
     */
    Boolean addAuthority(UserDetails user, GrantedAuthority authority);

    /**
     * 对某一个{@code user}移除一个指定的权限{@code authority}
     * @param user 要变更的用户
     * @param authority 要移除的权限
     * @return 若用户执行了移除或用户没有此权限，则返回true；若因未找到用户等原因导致操作未执行则返回false
     */
    Boolean removeAuthority(UserDetails user, GrantedAuthority authority);

    /**
     * 通过输入一个字符串获得一个对应的权限对象<br/>
     * 实际实现时可能委派给其他接口如{@link AuthorityMapper}等来提供
     * @param authority 输入的字符串
     * @return 构造出的权限，字符串错误时返回null
     */
    GrantedAuthority generateAuthority(String authority);
}
