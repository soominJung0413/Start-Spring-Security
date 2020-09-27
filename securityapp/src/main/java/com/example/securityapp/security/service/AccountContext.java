package com.example.securityapp.security.service;

import com.example.securityapp.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountContext extends User {// UserDetail 를 구현한 User 클래스를 상속

    private final Account account;

    //생성자 재정의
    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getUsername(),
                account.getPassword(), authorities);
        this.account = account; // 나중에 account 를 참조하기 위해 멤버변수로 선언
    }

    public Account getAccount() {
        return account;
    }
}
