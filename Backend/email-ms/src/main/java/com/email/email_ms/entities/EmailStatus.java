package com.email.email_ms.entities;

import com.email.email_ms.dtos.EmailStatusDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name="EmailStatuses")
public class EmailStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String status;

    public EmailStatus(EmailStatusDto emailStatusDto){
        if( emailStatusDto != null ){
            this.id = emailStatusDto.id();
            this.status = emailStatusDto.status();
        }
    }
}
