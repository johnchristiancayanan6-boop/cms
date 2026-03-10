package com.eastwest.controller;

import com.eastwest.exception.CustomException;
import com.eastwest.model.OwnUser;
import com.eastwest.model.UserSettings;
import com.eastwest.service.UserService;
import com.eastwest.service.UserSettingsService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/user-settings")
@Tag(name = "userSettings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserSettingsService userSettingsService;
    private final UserService userService;


    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public UserSettings getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<UserSettings> optionalUserSettings = userSettingsService.findById(id);
        if (optionalUserSettings.isPresent()) {
            UserSettings userSettings = optionalUserSettings.get();
            return userSettings;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public UserSettings patch(@Valid @RequestBody UserSettings userSettings,
                              @PathVariable("id") Long id,
                              HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<UserSettings> optionalUserSettings = userSettingsService.findById(id);

        if (optionalUserSettings.isPresent()) {
            UserSettings savedUserSettings = optionalUserSettings.get();
            if (savedUserSettings.getId().equals(user.getUserSettings().getId())) {
                return userSettingsService.update(userSettings);
            } else {
                throw new CustomException("You don't have permission", HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            throw new CustomException("Can't get someone else's userSettings", HttpStatus.NOT_ACCEPTABLE);
        }

    }

}


