package com.scut.se.sehubbackend.domain;

import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import com.scut.se.sehubbackend.domain.activity.CheckInfo;

/**
 * 申请表的公有信息，也是基本功能
 */
public interface Application {

    /**
     * @return 表id
     */
    Long getId();

    /**
     * @return 表基本信息
     */
    ActivityBasicInfo getActivityBasicInfo();

    /**
     * @return 审核、发起人方面的信息
     */
    CheckInfo getCheckInfo();

}
