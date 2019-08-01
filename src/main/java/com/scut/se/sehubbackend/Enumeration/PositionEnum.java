package com.scut.se.sehubbackend.Enumeration;

public enum PositionEnum {

    MINISTER(0, "部长"),
    STAFF(1, "部员"),
    STANDINGCOMMITEE(3, "常委");

    private Integer code;

    private String message;

    PositionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
