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

    public GetUserDto signUp(SignUpDto signUpDto) throws RuntimeException{
        String login = signUpDto.login().toLowerCase();
        Optional<User> userOp = this.userRepository.findByLogin(login);

        if(userOp.isPresent())
            throw new RuntimeException("O login com " + login + " já está associado a um usuário authenticado.");

        if(!emailFormateValidator(login))
            throw new RuntimeException("Este não é um e-mail com formato válido.");


        User newUser = new User(
                login,
                new BCryptPasswordEncoder().encode(signUpDto.password()),
                getRolesList(signUpDto.rolesId())
        );

        newUser = this.userRepository.save(newUser);
        return new GetUserDto(newUser);
    }

    public String signIn(SignInDto signInDto) throws RuntimeException{
        String login = signInDto.login().toLowerCase();
        Optional<User> userOp = this.userRepository.findByLogin(login);

        if(!userOp.isPresent())
            throw new RuntimeException("login ou senha incorretos.");

        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(login, signInDto.password());
        Authentication auth = this.authenticationManager.authenticate(loginToken);
        String token = this.tokenService.generateToken((User) auth.getPrincipal());
        return token;
    }

    public Page<GetUserDto> getUsersPaginated(int page, int size) throws RuntimeException{
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = this.userRepository.findAll(pageable);
        return userPage.map(GetUserDto::new);
    }

    public GetUserDto getUserById(long id)  throws RuntimeException{
        Optional<User> userOp = this.userRepository.findById(id);
        return userOp.map(GetUserDto::new).orElse(null);
    }

    public GetUserDto updateUserRoles(PutUserRolesDto putUserRolesDto) throws RuntimeException{
        String login = putUserRolesDto.login().toLowerCase();
        Optional<User> userOp = this.userRepository.findByLogin(login);

        if( putUserRolesDto.rolesId() == null || putUserRolesDto.rolesId().isEmpty() )
            throw new RuntimeException("A lista de permissões não pode estar vazia.");

        if(!userOp.isPresent())
            throw new RuntimeException("Login " + login + " não encontrado");

        User updatedUser = userOp.get();
        updatedUser.setRoles(getRolesList(putUserRolesDto.rolesId().stream().distinct().toList()));
        updatedUser = this.userRepository.save(updatedUser);
        return new GetUserDto(updatedUser);
    }

    public GetUserDto deleteUser(long id) throws RuntimeException{
        Optional<User> userOp = this.userRepository.findById(id);

        if(!userOp.isPresent())
            throw new RuntimeException("Não há usuário com id " + id + " cadastrado no sistema");

        this.userRepository.deleteById(id);
        return userOp.map(GetUserDto::new).get();
    }

    private List<Role> getRolesList(List<Long> roleIdList) throws RuntimeException{
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
