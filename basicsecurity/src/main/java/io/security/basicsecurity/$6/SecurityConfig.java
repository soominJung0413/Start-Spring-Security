package io.security.basicsecurity.$6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // rememberMe 기능의 시스템사용자정보 조회를 위해 UserDetailsService 주입

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//인가
                .authorizeRequests() //요청들에 대한
                .anyRequest().authenticated();//어느 요청이든. 인증을한다.
        http//인증
                .formLogin()//폼로그인 방식으로 인증작업을 하겠다.
                 .loginPage("/loginPage")
                 .usernameParameter("userId")
                 .passwordParameter("pwd")
                 .loginProcessingUrl("/loginProc")
                 .defaultSuccessUrl("/login")
                 .failureUrl("/login")
                 .successHandler(new AuthenticationSuccessHandler() {
                     @Override
                     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                         System.out.println("로그인 완료");
                         //부가처리
                     }
                 })
                 .failureHandler(new AuthenticationFailureHandler() {
                     @Override
                     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                         System.out.println("로그인 실패");
                         //부가처리
                     }
                 });



        http//로그아웃
                .logout() // 로그아웃 기능을 활성화
                .logoutUrl("/logout") // form[action=/logout] 원칙적으로 POST방식
                .logoutSuccessUrl("/login") // 로그아웃 성공시 이동할 Url
                .addLogoutHandler(new LogoutHandler() { // 로그아웃처리에 작업이 필요할 경우
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession httpSession = request.getSession();
                        httpSession.invalidate();
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() { // 로그아웃 성공 후 작업이 필요할 경우
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                })

        .and()//다른 기능 추가
                .rememberMe()// 리멤버미 기능 추가
                .rememberMeParameter("remember") // checkbox[name=]
                .tokenValiditySeconds(3600) // Remember-Me 쿠키의 생명기한, seconds 단위
                .userDetailsService(userDetailsService); // rememberMe 기능 수행 시 시스템에 있는 사용자정보를 조회하는 과정에서 필수적으로 필요한 클래스.

        ;
    }
}
