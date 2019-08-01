package com.scut.se.sehubbackend.Enumeration;

public enum CheckStatusEnum{

    WAIT(0, "待审核"),
    PASS(1, "审核通过"),
    NOPASS(-1, "审核未通过");

    private Integer code;

    private String message;

    CheckStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
