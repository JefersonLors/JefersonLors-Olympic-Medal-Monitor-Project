package com.authentication_ms.service;

import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.GetRoleDto;
import com.authentication_ms.entity.Role;
import com.authentication_ms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public GetRoleDto getRoleById(long id){
        Optional<Role> roleOp = this.roleRepository.findById(id);
        return roleOp.map(GetRoleDto::new).orElse(null);
    }

    public Page<GetRoleDto> getRolesPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> userPage = this.roleRepository.findAll(pageable);
        return userPage.map(GetRoleDto::new);
    }
}
