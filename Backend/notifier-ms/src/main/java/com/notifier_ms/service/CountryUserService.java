package com.notifier_ms.service;

import com.notifier_ms.controller.clients.CountryController;
import com.notifier_ms.controller.clients.UserController;
import com.notifier_ms.dto.*;
import com.notifier_ms.entity.CountryUser;
import com.notifier_ms.entity.Notification;
import com.notifier_ms.repository.CountryUserRepository;
import com.notifier_ms.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CountryUserService {
    @Autowired
    private CountryUserRepository countryUserRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CountryController countryController;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    public UserFollowCountryDto userFollowCountry(UserFollowCountryDto dto){
        Optional<CountryUser> countryUserOp = this.countryUserRepository
                                                    .findByUserIdAndCountryId(dto.userId(), dto.countryId());
        CountryUser newCountryUser;

        if(countryUserOp.isPresent() && countryUserOp.get().getActive())
            throw new RuntimeException("O usuário " + dto.userId() + " já segue o país " + dto.countryId());

        CountryDto countryDto = checkCountry(dto.countryId());

        if(countryUserOp.isPresent()){
            newCountryUser = countryUserOp.get();
            newCountryUser.setActive(true);
        }else{
            GetUserDto user = checkUser(dto.userId());

            newCountryUser = new CountryUser(
                    null,
                    user.id(),
                    user.email(),
                    dto.countryId(),
                    countryDto.name(),
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        }
        this.countryUserRepository.save(newCountryUser);

        return dto;
    }
    public UserUnfollowCountryDto userUnfollowCountry(UserUnfollowCountryDto dto){
        Optional<CountryUser> countryUserOp = this.countryUserRepository
                .findByUserIdAndCountryId(dto.userId(), dto.countryId());

        if(!countryUserOp.isPresent() || !countryUserOp.get().getActive() )
            throw new RuntimeException("O usuário " + dto.userId() + " não segue o país " + dto.countryId());

        CountryUser countryUser = countryUserOp.get();
        countryUser.setActive(false);

        this.countryUserRepository.save(countryUser);

        return dto;
    }

    public SentMessageDto sendMessage(MessageDataDto notificationDto){
        if( notificationDto.medalsWon() > 1 )
            throw new RuntimeException("Quantidade de medalhas indevida.");

        String medalType = checkMedal(notificationDto.medalId()).type().name();
        String sportModality = "{some modality name}";

        List<CountryUser> countryUser = this.countryUserRepository.findByCountryId(notificationDto.countryId());

        countryUser.forEach((registry)->{
            String message = makeMessage( registry.getCountryName(),
                                            notificationDto.medalsWon(),
                                            medalType,
                                            sportModality
                                            );

            sendEmail(registry.getUserEmail(), message);

            this.notificationRepository.save(new Notification(
                    null,
                    registry,
                    notificationDto.sportModalityId(),
                    notificationDto.medalsWon(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            ));
        });
        return null;
    }

    private GetUserDto checkUser(long userId){
        ResponseEntity<GetUserDto> getUserDto = this.userController.getUserById(userId);

        if(getUserDto.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException("O usuário " + userId + " não existe.");

        return getUserDto.getBody();
    }

    private CountryDto checkCountry(long countryId){
        ResponseEntity<CountryDto> response =  this.countryController.getCountryById(countryId);

        if(response.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException("O país " + countryId + " não existe.");

        return response.getBody();
    }

    private MedalDto checkMedal(long medalId){
        ResponseEntity<MedalDto> response = this.countryController.getMedal(medalId);

        if(response.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException("O país " + medalId + " não existe.");

        return response.getBody();
    }

    private String makeMessage(String countryName, long medalsWon, String medalType, String sportModality){
        StringBuilder message = new StringBuilder();

        message.append("Hey! E aí, tudo certo?\n\nTHE GOAT " + countryName +
                " gain more " + medalsWon + " " + medalType + " medal" + (medalsWon > 1 ? "s " : " ") +
                " on " + sportModality + " modality!");

        return message.toString();
    }

    private void sendEmail(String mailTo, String message){
        PostEmailDto email = new PostEmailDto(
                mailTo,
                "O país que você segue ganhou mais uma medalha. Vem cá checar!",
                message
        );
        this.rabbitTemplate.convertAndSend(queue, email);
    }
}
