package com.eastwest.controller;

import com.eastwest.dto.FloorPlanPatchDTO;
import com.eastwest.dto.FloorPlanShowDTO;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.dto.license.LicensingState;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.FloorPlanMapper;
import com.eastwest.model.FloorPlan;
import com.eastwest.model.Location;
import com.eastwest.model.OwnUser;
import com.eastwest.service.FloorPlanService;
import com.eastwest.service.LicenseService;
import com.eastwest.service.LocationService;
import com.eastwest.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @GetMapping("/state")
    public LicensingState getValidity(HttpServletRequest req) {
        return licenseService.getLicensingState();
    }
}


