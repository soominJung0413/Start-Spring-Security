package com.example.securityapp.controller.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login(){
        return "user/login/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// SecurityContextHolder 에서 Authentication 객체 추출

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);//Authentication 객체 존재시 SecurityContextLogoutHandler 가 session 과  authentication 객체 삭제
        }

        return "redirect:/";
    }
}
