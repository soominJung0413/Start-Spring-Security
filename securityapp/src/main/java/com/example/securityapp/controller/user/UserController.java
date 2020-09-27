package com.example.securityapp.controller.user;

import com.example.securityapp.domain.Account;
import com.example.securityapp.domain.AccountDTO;
import com.example.securityapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;// 비밀번호 암호화

    @GetMapping(value = "/mypage")
    public String myPage() throws Exception {
        return "user/mypage";
    }

    @GetMapping(value = "/users")
    public String createUser(){
        return "user/login/register";
    }

    @PostMapping(value = "/users")
    public String createUser(AccountDTO accountDTO){

        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class); // 해당 객체를 Account 클래스로 맵핑 시킴
        account.setPassword(passwordEncoder.encode(account.getPassword()));// 가입시 비밀번호 암호화

        userService.createUser(account);//등록

        return "redirect:/";
    }
}
