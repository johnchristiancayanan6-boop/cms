package com.eastwest.controller;

import com.eastwest.advancedsearch.FilterField;
import com.eastwest.advancedsearch.SearchCriteria;
import com.eastwest.dto.NotificationPatchDTO;
import com.eastwest.dto.PushTokenPayload;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.exception.CustomException;
import com.eastwest.model.Notification;
import com.eastwest.model.OwnUser;
import com.eastwest.model.PushNotificationToken;
import com.eastwest.model.enums.RoleType;
import com.eastwest.service.NotificationService;
import com.eastwest.service.PushNotificationTokenService;
import com.eastwest.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
@Tag(name = "notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;
    private final PushNotificationTokenService pushNotificationTokenService;

    @GetMapping("")
    @PreAuthorize("permitAll()")

    public Collection<Notification> getAll(HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            return notificationService.findByUser(user.getId());
        } else return notificationService.getAll();
    }

    @PostMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<Notification>> search(@RequestBody SearchCriteria searchCriteria,
                                                     HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            searchCriteria.getFilterFields().add(FilterField.builder()
                    .field("user")
                    .value(user.getId())
                    .operation("eq")
                    .values(new ArrayList<>())
                    .build());
        }
        return ResponseEntity.ok(notificationService.findBySearchCriteria(searchCriteria));
    }

    @GetMapping("/read-all")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public SuccessResponse readAll(HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        notificationService.readAll(user.getId());
        return new SuccessResponse(true, "Notifications read");
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public Notification getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Notification> optionalNotification = notificationService.findById(id);
        if (optionalNotification.isPresent()) {
            Notification savedNotification = optionalNotification.get();
            return savedNotification;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public Notification patch(@Valid @RequestBody NotificationPatchDTO notification,
                              @PathVariable("id") Long id,
                              HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Notification> optionalNotification = notificationService.findById(id);

        if (optionalNotification.isPresent()) {
            Notification savedNotification = optionalNotification.get();
            return notificationService.update(id, notification);
        } else throw new CustomException("Notification not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/push-token")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public SuccessResponse savePushToken(@RequestBody @Valid PushTokenPayload tokenPayload, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        String token = tokenPayload.getToken();
        PushNotificationToken pushNotificationToken;
        Optional<PushNotificationToken> optionalPushNotificationToken =
                pushNotificationTokenService.findByUser(user.getId());
        if (optionalPushNotificationToken.isPresent()) {
            pushNotificationToken = optionalPushNotificationToken.get();
            pushNotificationToken.setToken(token);
        } else {
            pushNotificationToken = PushNotificationToken.builder()
                    .user(user)
                    .token(token).build();
        }
        pushNotificationTokenService.save(pushNotificationToken);
        return new SuccessResponse(true, "Ok");
    }
}


