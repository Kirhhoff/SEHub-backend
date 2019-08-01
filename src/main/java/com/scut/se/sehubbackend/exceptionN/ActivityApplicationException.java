package com.scut.se.sehubbackend.exceptionN;

import com.scut.se.sehubbackend.Enumeration.ResultEnum;

public class ActivityApplicationException extends RuntimeException{

    private Integer code;

    public ActivityApplicationException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public ActivityApplicationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}