package com.user_ms.service;

import com.user_ms.dto.GetUserDto;
import com.user_ms.dto.PostUserDto;
import com.user_ms.entity.User;
import com.user_ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public GetUserDto getUserById(long id){
        Optional<User> userOp = this.userRepository.findById(id);

        return userOp.map(GetUserDto::new).orElse(null);
    }

    public Page<GetUserDto> getUsersPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = this.userRepository.findAll(pageable);

        return userPage.map(GetUserDto::new);
    }

    public GetUserDto postUser(PostUserDto postUserDto){
        Optional<User> userOp = this.userRepository.findByEmail(postUserDto.email());

        if(userOp.isPresent())
            throw new RuntimeException("O email " + postUserDto.email() + " já está associado a um usuário do sistema."); //Personalizar exceção

        if(!emailFormateValidator(postUserDto.email()))
            throw new RuntimeException("Este não é um e-mail com formato válido."); //Personalizar exceção

        User newUser = this.userRepository.save(new User(postUserDto));
        return new GetUserDto(newUser);
    }

    public GetUserDto putUser(long id, PostUserDto postUserDto){
        Optional<User> userOp = this.userRepository.findById(id);

        if(!userOp.isPresent())
            throw new RuntimeException("Não há usuário com id " + id + " cadastrado no sistema");

        Optional<User> userOp2 = this.userRepository.findByEmail(postUserDto.email());

        if(userOp2.isPresent() && userOp2.get().getId() != id )
            throw new RuntimeException("O email " + postUserDto.email() + " já está associado a um usuário do sistema."); //Personalizar exceção

        if(!emailFormateValidator(postUserDto.email()))
            throw new RuntimeException("Este não é um e-mail com formato válido."); //Personalizar exceção

        User updatedUser = userOp.get();

        updatedUser.setName(postUserDto.name());
        updatedUser.setEmail(postUserDto.email());
        updatedUser.setDth_upd(LocalDateTime.now());

        return new GetUserDto(updatedUser);
    }

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
}
