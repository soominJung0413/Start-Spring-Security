package com.example.securityapp.controller.login;

import com.example.securityapp.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error",required = false) String error,
                        @RequestParam(value = "exception",required = false) String exception, Model model){

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
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

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception",required = false) String exception, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account)authentication.getPrincipal();
        model.addAttribute("username",account.getUsername());
        model.addAttribute("exception", exception);

        return "user/login/denied";
    }
}
