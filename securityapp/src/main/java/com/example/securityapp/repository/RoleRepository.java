package com.example.securityapp.repository;


import com.example.securityapp.domain.entity.Role;
import com.example.securityapp.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String name);

    @Override
    void delete(Role role);

}
