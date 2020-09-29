package com.example.securityapp.security.config;

import com.example.securityapp.security.filter.AjaxLoginProcessingFilter;
import com.example.securityapp.security.handler.CustomAccessDeniedHandler;
import com.example.securityapp.security.handler.CustomAuthenticationFailureHandler;
import com.example.securityapp.security.handler.CustomAuthenticationHandler;
import com.example.securityapp.security.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationHandler customAuthenticationHandler;

    @Autowired
    private AuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());//빈 객체를 만들어 전달
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {//
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() { // 직접 구현한 AuthenticationProvider
        return new CustomAuthenticationProvider();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();//암호화 인코더
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler(){
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/users","user/login/**","/login*").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()

            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .successHandler(customAuthenticationHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .loginProcessingUrl("/login_proc")
                .permitAll()
        .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler());
    }
}
