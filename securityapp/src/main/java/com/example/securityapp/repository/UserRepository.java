package com.example.securityapp.repository;

import com.example.securityapp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Account, Long> {// JPA 인터페이스 구현, 만들 Entity 와 ID 타입이 들어감

    Account findByUsername(String username);
}
