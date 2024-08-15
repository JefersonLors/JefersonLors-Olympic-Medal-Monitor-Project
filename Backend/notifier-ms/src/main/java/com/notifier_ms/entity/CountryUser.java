package com.notifier_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name="country_user")
public class CountryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long userId;

    @NonNull
    private String userEmail;

    @NonNull
    private Long countryId;

    @NonNull
    private String countryName;

    @NonNull
    private Boolean active;

    @NonNull
    private LocalDateTime dth_inc;

    @NonNull
    private LocalDateTime dth_upd;
}
