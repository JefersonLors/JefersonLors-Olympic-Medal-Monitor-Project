package com.authentication_ms.service;

import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.RoleDto;
import com.authentication_ms.entity.Role;
import com.authentication_ms.entity.User;
import com.authentication_ms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleDto getRoleById(long id){
        Optional<Role> roleOp = this.roleRepository.findById(id);
        return roleOp.map(RoleDto::new).orElse(null);
    }
}
