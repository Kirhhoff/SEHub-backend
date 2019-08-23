package com.scut.se.sehubbackend.exception;


import com.scut.se.sehubbackend.other.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = InvalidIdException.class)
    public Response invaildIdExceptionHandler(Exception e) {
        return new Response(302, "参数不合法");
    }

    @ResponseBody
    @ExceptionHandler(value = CheckHasBeenOperatedException.class)
    public Response checkHasBeenOperatedExceptionHandler(Exception e) {
        return new Response(302, "参数不合法");
    }
}
