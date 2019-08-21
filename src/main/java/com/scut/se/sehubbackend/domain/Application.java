package com.scut.se.sehubbackend.domain;

import com.scut.se.sehubbackend.domain.activity.CheckInfo;

/**
 * 申请表的基本功能
 */
public interface Application {

    /**
     * 获取审核、发起人方面的信息
     * @return checkInfo
     */
    CheckInfo getCheckInfo();

}
