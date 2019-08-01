package com.scut.se.sehubbackend.Security.Authorization;

import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Enumeration.DepartmentEnum;
import com.scut.se.sehubbackend.Enumeration.Position;
import com.scut.se.sehubbackend.Security.Authorization.interfaces.AuthorizationDecisionManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


/**
 * 一个基于部门和职位进行决策的实现，可参考{@link AuthorizationDecisionManager}<br/>
 * 决策的依据是:<br/>
 * 1.{@code operator}与{@code user}应位于相同部门，常委不受限<br/>
 * 2.{@code operator}的职位应高于{@code user}<br/>
 * 3 部员无权进行权限变更操作<br/>
 * @see AuthorizationDecisionManager
 */
@Service
public class PositionAndDepartmentBasedAuthorizationDecisionManger implements AuthorizationDecisionManager {

    /**
     * 具体描述见{@link AuthorizationDecisionManager#decide(UserDetails, UserDetails, GrantedAuthority)}<br/>
     * 此处采取的依据见{@link PositionAndDepartmentBasedAuthorizationDecisionManger}
     * @param operator 变更行为的发起者
     * @param user 变更行为的承受者
     * @param dynamicAuthority 要变更的权限
     * @return 决策结果
     */
    @Override
    public Boolean decide(UserDetails operator, UserDetails user, GrantedAuthority dynamicAuthority) {
        if(!(operator instanceof UserAuthentication)||!(user instanceof UserAuthentication)||dynamicAuthority==null){//检测类型，以防万一
            return false;
        }

        //获取操作者和被操作者的职位和部门
        Position operatorPosition=((UserAuthentication) operator).getUserHistories().first().getPosition();
        Position userPosition=((UserAuthentication)user).getUserHistories().first().getPosition();
        DepartmentEnum operatorDepartmentEnum =((UserAuthentication) operator).getUserHistories().first().getDepartmentEnum();
        DepartmentEnum userDepartmentEnum =((UserAuthentication)user).getUserHistories().first().getDepartmentEnum();

        if(isNotImmutable(dynamicAuthority)){//若权限可变
            if(operatorPosition==Position.StandingCommittee){//操作者为常委时
                return userPosition!=Position.StandingCommittee;//被操作对象不是常委即可
            }else if (operatorPosition==Position.Minister){//操作者为部长时
                return userPosition==Position.Staff&& userDepartmentEnum == operatorDepartmentEnum;//被操作者为部员且时相同部门
            }else return false;//部员不得操作
        }
        return false;
    }

    /**
     * 检测目标权限是否可变<br/>
     * 此处直接检测权限是否以{@code Dynamic}开头
     * @param dynamicAuthority 目标权限是否可变
     * @return 结果
     */
    Boolean isNotImmutable(GrantedAuthority dynamicAuthority){
        return dynamicAuthority.getAuthority().startsWith("Dynamic");
    }
}
