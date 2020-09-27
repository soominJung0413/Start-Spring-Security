package com.example.securityapp.service.impl;

import com.example.securityapp.domain.Account;
import com.example.securityapp.repository.UserRepository;
import com.example.securityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {
        userRepository.save(account); // Account 객체를 저장
    }
}
