package com.user_ms.controller;

import com.user_ms.controller.client.TokenValidator;
import com.user_ms.dto.*;
import com.user_ms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenValidator tokenValidator;

    @GetMapping("/id")
    @Operation(summary="Retorna usuário com base no id inserido, se existir", description="Retorna usuário com base no id inserido, se existir.")
    public ResponseEntity<GetUserDto> getUserById(@RequestHeader("Authorization") String requestHeader,
                                                  @RequestParam long id){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));

        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()) {
            GetUserDto getUserDto = userService.getUserById(id);

            if (getUserDto == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(getUserDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/email")
    @Operation(summary="Retorna usuário com base no email inserido, se existir", description="Retorna usuário com base no email inserido, se existir.")
    public ResponseEntity<GetUserDto> getUserByEmail(@RequestHeader("Authorization") String requestHeader,
                                                     @RequestParam String email){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));

        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()) {
            GetUserDto getUserDto = userService.getUserByEmail(email);

            if( getUserDto == null )
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(getUserDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping()
    @Operation(summary="Retorna página de usuários", description="Retorna página de usuários.")
    public ResponseEntity<Page<GetUserDto>> getUsersPaginated(@RequestHeader("Authorization") String requestHeader,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "100") int size){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));

        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()) {
            Page<GetUserDto> getUserDtoList = userService.getUsersPaginated(page, size);
            return ResponseEntity.ok(getUserDtoList);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping()
    @Transactional
    @Operation(summary="Cria usuário", description="Cria usuário.")
    public ResponseEntity<GetUserDto> postUser(@RequestBody PostUserDto postUserDto,
                                               UriComponentsBuilder uriBuilder){

        GetUserDto getUserDto = this.userService.postUser(postUserDto);
        URI uri = uriBuilder.path("/user/id")
                            .buildAndExpand(getUserDto.id())
                            .toUri();
        return ResponseEntity.created(uri).body(getUserDto);

    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary="Atualiza usuário", description="Atualiza usuário.")
    public ResponseEntity<GetUserDto> putUser(@RequestHeader("Authorization") String requestHeader,
                                              @PathVariable long id,
                                              @RequestBody PutUserDto putUserDto){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));

        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()) {
            GetUserDto getUserDto = this.userService.putUser(id, putUserDto);
            return new ResponseEntity<>(getUserDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary="Deleta usuário", description="Deleta usuário.")
    public ResponseEntity<GetUserDto> deleteUser(@RequestHeader("Authorization") String requestHeader,
                                                 @PathVariable long id){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));

        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()) {
            GetUserDto getUserDto = this.userService.deleteUser(id);
            return new ResponseEntity<>(getUserDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
