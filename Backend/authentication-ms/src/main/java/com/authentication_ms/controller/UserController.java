package com.authentication_ms.controller;

import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.PutUserRolesDto;
import com.authentication_ms.service.UserService;
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
    public ResponseEntity<GetUserDto> getUserById(@RequestParam long id){
        GetUserDto getUserDto = userService.getUserById(id);

        if( getUserDto == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(getUserDto);
    }

    @GetMapping()
    public ResponseEntity<Page<GetUserDto>> getUsersPaginated(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "100") int size){
        Page<GetUserDto> getUserDtoList = userService.getUsersPaginated(page, size);

        return ResponseEntity.ok(getUserDtoList);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetUserDto> putUser(@PathVariable long id,
                                              @RequestBody PutUserRolesDto putUserRolesDto){
        GetUserDto getUserDto = this.userService.updateUserRoles(id, putUserRolesDto);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<GetUserDto> deleteUser(@PathVariable long id){
        GetUserDto getUserDto = this.userService.deleteUser(id);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }
}
