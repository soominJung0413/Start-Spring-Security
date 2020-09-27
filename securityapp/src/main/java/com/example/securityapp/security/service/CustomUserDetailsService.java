package com.example.securityapp.security.service;

import com.example.securityapp.domain.Account;
import com.example.securityapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userRepository.findByUsername(username);

        if(account == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }


        List<GrantedAuthority> roles = new ArrayList<>();//컬렉션 타입으로 권한정보를 넘겨주어야함
        roles.add(new SimpleGrantedAuthority(account.getRole())); // GrantedAuthority 는 인터페이스, GrantedAuthority 의 구현객체 중 하나인 SimpleGrantedAuthority 사용.

        //UserDetailsService 작동 과정에서 UserDetails 타입으로 리턴해주어야한다.
        AccountContext accountContext = new AccountContext(account, roles);

        return accountContext;
    }
}
