package com.eastwest.controller;

import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.PlanFeatures;
import com.eastwest.security.JwtTokenProvider;
import com.eastwest.service.LicenseService;
import com.eastwest.service.UserService;
import com.eastwest.utils.Consts;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Hidden
@RequestMapping("/swagger")
public class SwaggerAccessController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private LicenseService licenseService;
    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/swagger-session")
    public ResponseEntity<?> createSwaggerSession(
            @RequestHeader("Authorization") String authHeader,
            HttpServletResponse response) {

        // Verify the JWT from Authorization header
        String token = authHeader.replace(Consts.TOKEN_PREFIX, "");

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtTokenProvider.getUsername(token);
        OwnUser user = userService.findByEmail(username).orElseThrow();

        // License check removed - all features are now free
        // Check API access permission based on subscription plan only
        if (user.getCompany().getSubscription().getSubscriptionPlan().getFeatures().stream().noneMatch(planFeatures -> planFeatures.equals(PlanFeatures.API_ACCESS))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("API Access not available");
        }

        Cookie swaggerCookie = new Cookie("swagger_jwt", token);
        swaggerCookie.setHttpOnly(true);
        swaggerCookie.setSecure(true);
        swaggerCookie.setPath("/");
        swaggerCookie.setMaxAge(15 * 60); // 15 minutes

        response.addCookie(swaggerCookie);
        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok().body(Map.of("message", "Swagger session created"));
    }
}