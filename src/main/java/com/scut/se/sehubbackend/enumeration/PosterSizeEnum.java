package com.scut.se.sehubbackend.enumeration;

public enum PosterSizeEnum {

    K8(0,"八开"),
    K4(1, "四开"),
    K2(2, "二开");

    private Integer code;

    private String message;

    PosterSizeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
