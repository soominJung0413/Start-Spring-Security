package io.security.basicsecurity.exceptiontranslationfilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("USER","ADMIN");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("USER","SYS","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//인가 요청들
                .antMatchers("/login").permitAll()//로그인 페이지는 모든 접근 허가
                .antMatchers("/user").hasRole("USER")//해당 경로에 대해 USER 권한 심사를 하겠다.
                .antMatchers("/admin/pay").hasRole("ADMIN")// 해당 경로에 대해 ADMIN 권한 심사를 하겠다.
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")// 해당 경로에 대해 SpEL 이 true 인지 권한 심사를 하겠다.
                .anyRequest()//어떤 요청이든
                .authenticated();//인증 성공시 접근가능하다.

        http
                .formLogin()
                .successHandler(new AuthenticationSuccessHandler() {//사용자의 인증 시 사용자가 요청했던 경로로 보내주는 구현
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        RequestCache requestCache = new HttpSessionRequestCache();//SavedRequest 저장한 RequestCache 객체 생성.
                        SavedRequest savedRequest = requestCache.getRequest(request, response);//HttpSessionRequestCache 에서 유저 요청정보를 저장한 SavedRequest 객체 생성.
                        String redirectUrl = savedRequest.getRedirectUrl(); // SavedRequest 에서 사용자가 가고자하는 경로 추출
                        response.sendRedirect(redirectUrl);// 가고자 했던 경로로 Redirect
                    }
                });//폼 로그인 방식을 사용하겠다.

        http
                .exceptionHandling()// 인증, 인가 예외처리 기능 활성화
                .authenticationEntryPoint(new AuthenticationEntryPoint() {//AuthenticationException 발생시 처리
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        System.out.println("인증 예외 발생 "+authException.getMessage());
                        response.setStatus(401);
                        response.sendRedirect("/login");
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {//AccessDeniedException 발생시 처리
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        System.out.println("인가 예외 발생 "+accessDeniedException.getMessage());
                        response.sendRedirect("/denied");
                    }
                });

    }
}
