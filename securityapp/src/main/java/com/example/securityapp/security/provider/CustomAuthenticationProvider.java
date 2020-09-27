package com.example.securityapp.security.provider;

import com.example.securityapp.security.service.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CustomAuthenticationProvider implements AuthenticationProvider {//실질적으로 인증처리를 하는 AuntheticationProvider 구현

    @Autowired
    private UserDetailsService userDetailsService;//유저 정보를 끌어오는 클래스

    @Autowired
    PasswordEncoder passwordEncoder; // 패스워드 검증위한 인코더

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {//검증의 로직
        //authentication 객체는 입력한 username, password 를 담고 있다.
        String username = authentication.getName(); // username 추출
        String password = (String)authentication.getCredentials(); // password 는 credentical 에 저장되어 있음.

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);// 원하는 UserDetails 로 캐스팅

        if(!passwordEncoder.matches(password, accountContext.getPassword())){// 사용자 암호와 저장된 암호화된 정보를 비교
            throw new BadCredentialsException("BadCredentialsException");
        }

        //principal : 아이디 credentials : 암호 athorities : 권한정보 . 즉 최종 인증 토큰 생성 과정
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null,accountContext.getAuthorities());

        return usernamePasswordAuthenticationToken;//AuthenticationManager 에게 리턴
    }

    @Override
    public boolean supports(Class<?> authentication) {//authentication 객체가 사용하려고하는 Token과 일치하는지 확인
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
