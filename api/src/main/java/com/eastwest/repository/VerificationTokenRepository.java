package com.eastwest.repository;

import com.eastwest.model.OwnUser;
import com.eastwest.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findVerificationTokenEntityByToken(String token);
    ArrayList<VerificationToken> findAllVerificationTokenEntityByUser(OwnUser user);
}
