package io.security.basicsecurity.delegatingfilterproxy;


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
                .authorizeRequests()//인가 요청에 대해
                .anyRequest()//어느 요청이던
                .authenticated();//인증을 해야 접근할 수 있다.

        http
                .formLogin();
    }
}
