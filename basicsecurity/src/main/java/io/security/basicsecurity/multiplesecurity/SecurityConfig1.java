package io.security.basicsecurity.multiplesecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
//@Order(1)
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/**")//특정 Url의
                .authorizeRequests()//인가 요청에 대한
                .anyRequest() //어떠한 요청이라도
                .authenticated()//인증완료시 접근 가능하다
        .and()//다른기능을 추가
                .httpBasic();//httpBasic 방식으로 안중 적업을 한다.
    }
}

//@Configuration
//@Order(0)
class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//인가 요청에 대한
                .anyRequest()//어떠한 요청이라도
                .permitAll()//접근을 허가한다.
        .and()//다른기능을 추가
                .formLogin();// 폼 로그인 방식을 쓰겠다.
    }
}
