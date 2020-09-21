package io.security.basicsecurity.authorities;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");//메모리 방식으로 사용자를 생성하겠다.
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS","USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN","SYS","USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .antMatcher("")
                .authorizeRequests()
                .antMatchers("/user").hasRole("USER")// 해당경로에 대해 USER 권한심사를 하겠다.
                .antMatchers("/admin/pay").hasRole("ADMIN")// 해당경로에 대해 ADMIN 권한심사를 하겠다.
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")//해당 경로들에 대해 SpEL 이 true 인지 권한심사를 하겠다.
                .anyRequest()//모든 요청에 대해
                .authenticated();//인증된 사용자 만의 접근을 허용한다.

        http
                .formLogin();//폼 로그인 방식을 사용하겠다.
    }
}
