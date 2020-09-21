package io.security.basicsecurity.csrf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//인가 요청에 대한
                .anyRequest()//어떤 요청이던
                .permitAll(); // 접근 허가하겠다.

        http
                .formLogin();//폼 로그인 기능을 활성화 한다.

        http
                .csrf();//Cross Site Request Forgery 공격 방지 기능을 활성화 한다. < 기본 값.
    }
}
