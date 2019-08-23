package com.scut.se.sehubbackend.exception;


import com.scut.se.sehubbackend.other.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = InvalidIdException.class)
    public void invaildIdExceptionHandler(Exception e, HttpServletResponse res) throws IOException {
        res.sendError(302,"参数不合法");
    }

    @ResponseBody
    @ExceptionHandler(value = CheckHasBeenOperatedException.class)
    public void checkHasBeenOperatedExceptionHandler(Exception e, HttpServletResponse res) throws IOException {
        res.sendError(302,"参数不合法");
    }
}
