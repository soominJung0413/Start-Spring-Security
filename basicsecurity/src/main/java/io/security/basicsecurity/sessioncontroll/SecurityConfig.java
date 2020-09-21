package io.security.basicsecurity.sessioncontroll;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()// 권한 요청들에
                .anyRequest()//어떤 요청이든
                .authenticated();// 인증을하겠다.

        http
                .formLogin();//폼로그인 방식으로 하겠다.


        http
                .sessionManagement()//세션 관리를 하겠다.
                .invalidSessionUrl("/login")//세션만료시 이동할 경로
                .maximumSessions(1)//최대 세션을 정하겠다.
                .maxSessionsPreventsLogin(true);//최대세션 관리전략을 정하겠다. true = 현제사용자 로그인 차단 false : 이전사용자 만료전략

    }
}
