package com.scut.se.sehubbackend.security;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.security.authorization.interfaces.AuthorizationDecisionManager;
import org.springframework.stereotype.Component;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.*;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.*;

/**
 * {@link AuthorizationDecisionManager}的一个简单实现
 * @see AuthorizationDecisionManager
 */
@Component
public class AuthorizationDecisionManagerImpl implements AuthorizationDecisionManager {


    /**
     * {@inheritDoc}
     * <p>从职位、部门、权限三个角度进行decide，必须同时满足</p>
     * @param operator 操作者
     * @param target 变更的对象
     * @param authority 要变更的权限
     * @return
     */
    @Override
    public Boolean decide(Member operator,Member target, AuthorityEnum authority) {
        return positionDecide(operator.getPosition(),target.getPosition())
                &&departmentDecide(operator.getDepartment().getDepartmentName(),target.getDepartment().getDepartmentName())
                &&authorityDecide(operator.getDepartment().getDepartmentName(),authority);
    }


    /**
     * <p>从职位的角度decide</p>
     * <p>常委变更部长或部长变更部员</p>
     * @param operatorPosition 操作者职位
     * @param targetPosition 目标职位
     * @return decide结果
     */
    private boolean positionDecide(PositionEnum operatorPosition,PositionEnum targetPosition){
        return  (operatorPosition==StandingCommittee&&targetPosition==Minister)
                ||(operatorPosition==Minister&&targetPosition==Staff);
    }

    /**
     * <p>从部门的角度decide</p>
     * <p>同部门且不能都是常委</p>
     * @param operatorDepartment 操作者部门
     * @param targetDepartment 目标部门
     * @return decide结果
     */
    private boolean departmentDecide(DepartmentNameEnum operatorDepartment, DepartmentNameEnum targetDepartment){
        return (operatorDepartment==targetDepartment)
                &&(operatorDepartment!=DepartmentNameEnum.StandingCommittee);
    }

    /**
     * <p>从要变更的权限角度decide</p>
     * <p>每个部门只能变更本部门的权限</p>
     * @param operatorDepartment 操作者部门
     * @param authority 目标权限
     * @return decide结果
     */
    private boolean authorityDecide(DepartmentNameEnum operatorDepartment,AuthorityEnum authority){
        switch (operatorDepartment){
            case Relation: return authority==Etiquette;
            case Secretary: return authority==Host;
            case Research: return authority==LectureTicket;
            case Propaganda: return authority==Poster;
            default: return false;
        }
    }
}
