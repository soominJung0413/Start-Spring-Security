package com.example.securityapp.controller.user;


import com.example.securityapp.domain.dto.AccountDto;
import com.example.securityapp.repository.RoleRepository;
import com.example.securityapp.service.UserService;
import com.example.securityapp.domain.dto.AccountDto;
import com.example.securityapp.domain.entity.Account;
import com.example.securityapp.domain.entity.Role;
import com.example.securityapp.repository.RoleRepository;
import com.example.securityapp.security.service.AccountContext;
import com.example.securityapp.security.token.AjaxAuthenticationToken;
import com.example.securityapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping(value="/users")
	public String createUser() throws Exception {

		return "user/login/register";
	}

	@PostMapping(value="/users")
	public String createUser(AccountDto accountDto) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		Account account = modelMapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

		userService.createUser(account);

		return "redirect:/";
	}

	@GetMapping(value="/mypage")
	public String myPage(@AuthenticationPrincipal Account account, Authentication authentication, Principal principal) throws Exception {
		return "user/mypage";
	}

	@GetMapping("/order")
	public String order(){
		userService.order();
		return "user/mypage";
	}

}
