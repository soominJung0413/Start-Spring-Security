package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//인가
                .authorizeRequests() //요청들에 대한
                .anyRequest().authenticated();//어느 요청이든. 인증을한다.
        http//인증
                .formLogin()//폼로그인 방식으로 인증작업을 하겠다.
                .loginPage("/loginPage")//로그인할 url
                .defaultSuccessUrl("/")//로그인 성공시 url
                .failureUrl("/loginPage")//로그인 실패시 url
                .usernameParameter("userId")// input[name='']
                .passwordParameter("passwd")// password[name='']
                .loginProcessingUrl("/login_proc")// form[action='']
                .successHandler(new AuthenticationSuccessHandler() {//로그인 성공시 작동할 핸들러 AuthenticationSuccessHandler 구현객체가 온다.
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("authentication " + authentication.getName());//인증자의 이름 리턴
                        response.sendRedirect("/");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {// 로그인 실패시 작동할 핸들러 AuthenticationFailureHandler 구현객체가 온다.
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception "+exception.getMessage());// AuthenticationException 의 메세지 내용 전달
                        response.sendRedirect("/loginPage");
                    }
                })
                .permitAll();
        ;
    }
}
