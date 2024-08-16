package com.user_ms.entity;

import com.user_ms.dto.GetUserDto;
import com.user_ms.dto.PostUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private LocalDateTime dth_inc;

    @NonNull
    private LocalDateTime dth_upd;

    public User(PostUserDto postUserDto){
        this.name = postUserDto.name();
        this.email = postUserDto.email().toLowerCase();
        this.dth_inc = LocalDateTime.now();
        this.dth_upd = LocalDateTime.now();
    }

}
