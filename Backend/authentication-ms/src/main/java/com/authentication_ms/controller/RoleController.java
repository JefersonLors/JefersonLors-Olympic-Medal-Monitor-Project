package com.authentication_ms.controller;

import com.authentication_ms.dto.GetRoleDto;
import com.authentication_ms.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping()
    @Operation(summary="Retorna as roles existentes no banco", description="Retorna as roles existentes no banco.")
    public ResponseEntity<Page<GetRoleDto>> getRolesPaginated(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "100") int size){
        Page<GetRoleDto> getRoleDtos = roleService.getRolesPaginated(page, size);
        return ResponseEntity.ok(getRoleDtos);
    }
    @GetMapping("/id")
    @Operation(summary="Retorna a role com o id inserido, se existir", description="Retorna a role com o id inserido, se existir.")
    public ResponseEntity<GetRoleDto> getRoleById(@RequestParam long id){
        GetRoleDto getRoleDto = roleService.getRoleById(id);

        if( getRoleDto == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(getRoleDto);
    }
}
