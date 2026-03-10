package com.eastwest.service;

import com.eastwest.model.PushNotificationToken;
import com.eastwest.repository.PushNotificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PushNotificationTokenService {

    private final PushNotificationTokenRepository pushNotificationTokenRepository;


    public PushNotificationToken create(PushNotificationToken pushNotificationToken) {
        return pushNotificationTokenRepository.save(pushNotificationToken);
    }

    public Optional<PushNotificationToken> findByUser(Long userId) {
        return pushNotificationTokenRepository.findByUser_Id(userId);
    }

    public PushNotificationToken save(PushNotificationToken pushNotificationToken) {
        return pushNotificationTokenRepository.save(pushNotificationToken);
    }

    public void delete(Long id) {
        pushNotificationTokenRepository.deleteById(id);
    }
}
