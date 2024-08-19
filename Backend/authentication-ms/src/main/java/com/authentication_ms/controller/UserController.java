package com.authentication_ms.controller;

import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.PutUserRolesDto;
import com.authentication_ms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/id")
    @Operation(summary="Retorna a role com o id inserido, se existir", description="Retorna a role com o id inserido, se existir.")
    public ResponseEntity<GetUserDto> getUserById(@RequestParam long id){
        GetUserDto getUserDto = userService.getUserById(id);

        if( getUserDto == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(getUserDto);
    }

    @GetMapping()
    @Operation(summary="Retorna uma página de usuários cadastrados", description="Retorna uma página de usuários cadastrados.")
    public ResponseEntity<Page<GetUserDto>> getUsersPaginated(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "100") int size){
        Page<GetUserDto> getUserDtoList = userService.getUsersPaginated(page, size);

        return ResponseEntity.ok(getUserDtoList);
    }
    @PutMapping()
    @Transactional
    @Operation(summary="Atualiza as roles do usuário, se ele existir", description="Atualiza as roles do usuário, se ele existir.")
    public ResponseEntity<GetUserDto> putUserRoles(@RequestBody PutUserRolesDto putUserRolesDto){
        GetUserDto getUserDto = this.userService.updateUserRoles(putUserRolesDto);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary="Retorna o usuário com o id inserido, se existir", description="Retorna o usuário com o id inserido, se existir.")
    public ResponseEntity<GetUserDto> deleteUser(@PathVariable long id){
        GetUserDto getUserDto = this.userService.deleteUser(id);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }
}
