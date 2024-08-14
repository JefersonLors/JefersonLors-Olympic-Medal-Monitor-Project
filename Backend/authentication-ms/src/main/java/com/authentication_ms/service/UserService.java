package com.authentication_ms.service;

import com.authentication_ms.dto.AuthenticationDto;
import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.PostUserDto;
import com.authentication_ms.entity.Role;
import com.authentication_ms.entity.User;
import com.authentication_ms.repository.RoleRepository;
import com.authentication_ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    public Page<GetUserDto> getUsersPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = this.userRepository.findAll(pageable);

        return userPage.map(GetUserDto::new);
    }

    public GetUserDto getUserById(long id){
        Optional<User> userOp = this.userRepository.findById(id);

        return userOp.map(GetUserDto::new).orElse(null);
    }

    public GetUserDto signUp(PostUserDto postUserDto){
        Optional<User> userOp = this.userRepository.findByLogin(postUserDto.login());

        if(userOp.isPresent())
            throw new RuntimeException("O login com " + postUserDto.login() + " já está associado a um usuário authenticado."); //Personalizar exceção

        if(!emailFormateValidator(postUserDto.login()))
            throw new RuntimeException("Este não é um e-mail com formato válido."); //Personalizar exceção


        User newUser = new User(
           postUserDto.login(),
                new BCryptPasswordEncoder().encode(postUserDto.password()),
           getRolesList(postUserDto.rolesId())
        );

        newUser = this.userRepository.save(newUser);
        return new GetUserDto(newUser);
    }

    public String signIn(AuthenticationDto authenticationDto){
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());
        Authentication auth = this.authenticationManager.authenticate(loginToken);
        return this.tokenService.generateToken((User) auth.getPrincipal());
    }

//    public GetUserDto putUser(long id, PostUserDto postUserDto){
//        Optional<User> userOp = this.userRepository.findById(id);
//
//        if(!userOp.isPresent())
//            throw new RuntimeException("Não há usuário com id " + id + " cadastrado no sistema");
//
//        Optional<User> userOp2 = this.userRepository.findByLogin(postUserDto.login());
//
//        if(userOp2.isPresent() && userOp2.get().getId() != id )
//            throw new RuntimeException("O email " + postUserDto.login() + " já está associado a um usuário do sistema."); //Personalizar exceção
//
//        if(!emailFormateValidator(postUserDto.login()))
//            throw new RuntimeException("Este não é um e-mail com formato válido."); //Personalizar exceção
//
//        User updatedUser = userOp.get();
//
//        updatedUser.setName(postUserDto.name());
//        updatedUser.setEmail(postUserDto.email());
//        updatedUser.setDth_upd(LocalDateTime.now());
//
//        return new GetUserDto(updatedUser);
//    }

    public GetUserDto deleteUser(long id){
        Optional<User> userOp = this.userRepository.findById(id);

        if(!userOp.isPresent())
            throw new RuntimeException("Não há usuário com id " + id + " cadastrado no sistema");

        this.userRepository.deleteById(id);

        return userOp.map(GetUserDto::new).get();
    }

    private boolean emailFormateValidator(String email){
        String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    private List<Role> getRolesList(List<Long> roleIdList){
        List<Role> roles =  new ArrayList<>();
        roleIdList.stream().forEach((id)->{
            Optional<Role> roleOp = this.roleRepository.findById(id);
            if( !roleOp.isPresent() )
                throw new RuntimeException("O id " + id + " não pertence a nenhuma permissão.");
            roles.add(roleOp.get());
        });
        return roles;
    }
}
