package com.user_ms.controller;

import com.user_ms.dto.GetUserDto;
import com.user_ms.dto.PostUserDto;
import com.user_ms.dto.PutUserDto;
import com.user_ms.service.UserService;
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

    @PostMapping()
    public ResponseEntity<GetUserDto> postUser(@RequestBody PostUserDto postUserDto,
                                               UriComponentsBuilder uriBuilder){
        GetUserDto getUserDto = this.userService.postUser(postUserDto);

        URI uri = uriBuilder.path("/user/id")
                            .buildAndExpand(getUserDto.id())
                            .toUri();

        return ResponseEntity.created(uri).body(getUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> putUser(@PathVariable long id,
                                              @RequestBody PutUserDto putUserDto){
        GetUserDto getUserDto = this.userService.putUser(id, putUserDto);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetUserDto> deleteUser(@PathVariable long id){
        GetUserDto getUserDto = this.userService.deleteUser(id);

        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }
}
