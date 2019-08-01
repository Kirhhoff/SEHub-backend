package com.scut.se.sehubbackend.Controller;

import com.scut.se.sehubbackend.Others.Response;
import com.scut.se.sehubbackend.Service.ManagerService;
import com.scut.se.sehubbackend.UserDAORequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired ManagerService managerService;

    @PostMapping("/user")
    // @PostMapping，处理post请求
    // UserDAORequest：保存用户基本信息和权限
    Response createUser(@RequestBody UserDAORequest userDAORequest){
        return managerService.createUser(userDAORequest);
    }

    @DeleteMapping("/user")
    // @DeleteMapping，处理delete请求
    Response deleteUser(@RequestBody UserDAORequest userDAORequest){
        return managerService.deleteUser(userDAORequest);
    }
}
