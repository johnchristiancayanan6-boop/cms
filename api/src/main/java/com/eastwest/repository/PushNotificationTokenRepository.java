package com.eastwest.repository;

import com.eastwest.model.PushNotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, Long> {
    Optional<PushNotificationToken> findByUser_Id(Long id);
}
