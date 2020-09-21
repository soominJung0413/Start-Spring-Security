package io.security.basicsecurity.sessioncontroll;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().authenticated();

        http
                .formLogin();

        http
                .sessionManagement()//세션 관리
                .sessionFixation()//세션 고정 보호 전략을 세우겠다.
//                .none();//세선 고정 보호 전략을 세우지 않겠다.
//                .migrateSession()//Servlet 3.1 미만의 세션 고정 보호
                .changeSessionId();//Servlet 3.1 이상의 세션 고정 보호

    }
}
