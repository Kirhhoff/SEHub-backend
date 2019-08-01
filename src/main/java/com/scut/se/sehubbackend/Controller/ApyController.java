package com.scut.se.sehubbackend.Controller;

import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Enumeration.SeStatus;
import com.scut.se.sehubbackend.Others.ReqApplicationForm;
import com.scut.se.sehubbackend.Others.Response;
import com.scut.se.sehubbackend.Service.ApyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/apply")
public class ApyController {

    @Autowired private ApyService apyService;

    // 提交申请表
    @RequestMapping("/submit")
    // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
    // ReqApplicationForm 申请表
    public Response SubmitApplication(@RequestBody ReqApplicationForm form){
        try {
            // Spring Security使用一个Authentication对象来描述当前用户的相关信息。
            // SecurityContextHolder中持有的是当前用户的SecurityContext，
            // 而SecurityContext持有的是代表当前用户相关信息的Authentication的引用。
            // 这个Authentication对象不需要我们自己去创建，在与系统交互的过程中，Spring Security会自动为我们创建相应的Authentication对象，然后赋值给当前的SecurityContext。
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 通过Authentication.getPrincipal()可以获取到代表当前用户的信息，这个对象通常是UserDetails的实例。
            UserAuthentication user = (UserAuthentication) authentication.getPrincipal();

            return new Response(apyService.SubmitApplication(user, form));
        }catch (NullPointerException e){
            return new Response(SeStatus.NotLogin);
        }
    }
}
