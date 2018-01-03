package com.spring_shiro.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dllo on 18/1/3.
 */
@Controller
public class MainController {

    @RequestMapping(value = "home")
    public String homepage() {
        return "home";
    }

    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "loginsubmit")
    public String loginsub(HttpServletRequest request) throws Exception {

        String exmsg = (String) request.getAttribute("shiroLoginFailure");

        if (UnknownAccountException.class.getName().equals(exmsg)){
            throw new Exception("账号不存在");
        } else if (IncorrectCredentialsException.class.getName().equals(exmsg)){
            throw new Exception("密码错误");
        }else {
            throw new Exception("服务器异常");
        }
    }
}
