package com.example.securityapp.service;

import com.example.securityapp.domain.entity.Role;
import com.example.securityapp.domain.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);

    void deleteRole(long id);
}
