package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.activity.CheckInfo;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 初始化CheckInfo的工具
 */
@Component
public class CheckInfoUtil {

    private final ContextHelper<Member> contextHelper;

    public CheckInfoUtil(ContextHelper<Member> contextHelper) {
        this.contextHelper = contextHelper;
    }

    /**
     * 返回一张申请表刚被创建时的审核、发起人信息
     * @return 初始化的CheckInfo
     */
    public CheckInfo initialCheckInfo(){
        return CheckInfo.builder()
                .checkStatus(CheckStatusEnum.WAIT)
                .checkFeedback(null)
                .submissionDate(new Date())
                .checkDate(null)
                .initializer(contextHelper.getCurrentPrincipal())
                .lastModifier(contextHelper.getCurrentPrincipal())
                .build();
    }
}
