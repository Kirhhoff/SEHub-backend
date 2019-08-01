package com.scut.se.sehubbackend.Enumeration;

import lombok.Getter;

@Getter
public enum ResultEnum {

    ACTIVITY_APPLICATION_STATUS_ERROR(0, "活动申请状态错误"),
    ACTIVITY_APPLICATION_UPDATE_FAILED(1, "活动申请更新错误"),
    ACTIVITY_APPLICATION_NOT_EXIST(2, "活动申请不存在");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
