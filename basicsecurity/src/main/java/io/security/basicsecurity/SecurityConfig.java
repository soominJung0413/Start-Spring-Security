package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//인가
                .authorizeRequests() //요청들에 대한
                .anyRequest().authenticated();//어느 요청이든. 인증을한다.
        http//인증
                .formLogin();//폼로그인 방식으로 인증작업을 하겠다.
    }
}
