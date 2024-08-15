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
@Entity(name="notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private CountryUser countryUser;

    @NonNull
    private Long sportModalityId;

    @NonNull
    private Long medalsWon;

    @NonNull
    private String status;

    @NonNull
    private LocalDateTime dth_inc;

    @NonNull
    private LocalDateTime dth_upd;
}
