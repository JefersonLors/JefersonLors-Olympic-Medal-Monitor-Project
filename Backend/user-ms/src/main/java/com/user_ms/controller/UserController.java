package com.user_ms.controller;

import com.user_ms.dto.GetUserDto;
import com.user_ms.dto.PostUserDto;
import com.user_ms.dto.PutUserDto;
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

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/id")
    @Operation(summary="Retorna usuário com base no id inserido, se existir", description="Retorna usuário com base no id inserido, se existir.")
    public ResponseEntity<GetUserDto> getUserById(@RequestParam long id){
        GetUserDto getUserDto = userService.getUserById(id);

        if( getUserDto == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(getUserDto);
    }

    @GetMapping("/email")
    @Operation(summary="Retorna usuário com base no email inserido, se existir", description="Retorna usuário com base no email inserido, se existir.")
    public ResponseEntity<GetUserDto> getUserByEmail(@RequestParam String email){
        GetUserDto getUserDto = userService.getUserByEmail(email);

        if( getUserDto == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(getUserDto);
    }

    @GetMapping()
    @Operation(summary="Retorna página de usuários", description="Retorna página de usuários.")
    public ResponseEntity<Page<GetUserDto>> getUsersPaginated(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "100") int size){
        Page<GetUserDto> getUserDtoList = userService.getUsersPaginated(page, size);

        return ResponseEntity.ok(getUserDtoList);
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
    public ResponseEntity<GetUserDto> putUser(@PathVariable long id,
                                              @RequestBody PutUserDto putUserDto){
        GetUserDto getUserDto = this.userService.putUser(id, putUserDto);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary="Deleta usuário", description="Deleta usuário.")
    public ResponseEntity<GetUserDto> deleteUser(@PathVariable long id){
        GetUserDto getUserDto = this.userService.deleteUser(id);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }
}
