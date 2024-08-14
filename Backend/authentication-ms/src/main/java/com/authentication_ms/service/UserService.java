package com.authentication_ms.service;

import com.authentication_ms.dto.PutUserRolesDto;
import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.SignInDto;
import com.authentication_ms.dto.SignUpDto;
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
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTokenService tokenService;


    public GetUserDto signUp(SignUpDto signUpDto){
        Optional<User> userOp = this.userRepository.findByLogin(signUpDto.login());

        if(userOp.isPresent())
            throw new RuntimeException("O login com " + signUpDto.login() + " já está associado a um usuário authenticado."); //Personalizar exceção

        if(!emailFormateValidator(signUpDto.login()))
            throw new RuntimeException("Este não é um e-mail com formato válido."); //Personalizar exceção


        User newUser = new User(
                signUpDto.login(),
                new BCryptPasswordEncoder().encode(signUpDto.password()),
                getRolesList(signUpDto.rolesId())
        );

        newUser = this.userRepository.save(newUser);
        return new GetUserDto(newUser);
    }

    public String signIn(SignInDto signInDto){
        Optional<User> userOp = this.userRepository.findByLogin(signInDto.login());

        if(!userOp.isPresent())
            throw new RuntimeException("login ou senha incorretos."); //Personalizar exceção

        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(signInDto.login(), signInDto.password());
        Authentication auth = this.authenticationManager.authenticate(loginToken);
        return this.tokenService.generateToken((User) auth.getPrincipal());
    }

    public Page<GetUserDto> getUsersPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = this.userRepository.findAll(pageable);

        return userPage.map(GetUserDto::new);
    }

    public GetUserDto getUserById(long id){
        Optional<User> userOp = this.userRepository.findById(id);

        return userOp.map(GetUserDto::new).orElse(null);
    }

    public GetUserDto updateUserRoles(long id, PutUserRolesDto putUserRolesDto){
        if( putUserRolesDto.rolesId() == null || putUserRolesDto.rolesId().isEmpty() )
            throw new RuntimeException("A lista de permissões não pode estar vazia.");

        Optional<User> userOp = this.userRepository.findById(id);

        if(!userOp.isPresent())
            throw new RuntimeException("Não há usuário com id " + id + " autenticado no sistema");

        User updatedUser = userOp.get();

        updatedUser.setRoles(getRolesList(putUserRolesDto.rolesId()));

        return new GetUserDto(updatedUser);
    }

    public GetUserDto deleteUser(long id){
        Optional<User> userOp = this.userRepository.findById(id);

        if(!userOp.isPresent())
            throw new RuntimeException("Não há usuário com id " + id + " cadastrado no sistema");

        this.userRepository.deleteById(id);

        return userOp.map(GetUserDto::new).get();
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

    private boolean emailFormateValidator(String email){
        String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

}
