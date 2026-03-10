package com.eastwest.controller;

import com.eastwest.advancedsearch.FilterField;
import com.eastwest.advancedsearch.SearchCriteria;
import com.eastwest.dto.MeterMiniDTO;
import com.eastwest.dto.MeterPatchDTO;
import com.eastwest.dto.MeterShowDTO;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.MeterMapper;
import com.eastwest.model.Asset;
import com.eastwest.model.Meter;
import com.eastwest.model.OwnUser;
import com.eastwest.model.Team;
import com.eastwest.model.enums.PermissionEntity;
import com.eastwest.model.enums.PlanFeatures;
import com.eastwest.model.enums.RoleType;
import com.eastwest.service.AssetService;
import com.eastwest.service.MeterService;
import com.eastwest.service.ReadingService;
import com.eastwest.service.UserService;
import com.eastwest.utils.Helper;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meters")
@Tag(name = "meter")
@RequiredArgsConstructor
public class MeterController {

    private final MeterService meterService;
    private final MeterMapper meterMapper;
    private final UserService userService;
    private final AssetService assetService;
    private final ReadingService readingService;
    private final EntityManager em;

    @PostMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<MeterShowDTO>> search(@RequestBody SearchCriteria searchCriteria,
                                                     HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            if (user.getRole().getViewPermissions().contains(PermissionEntity.METERS)) {
                searchCriteria.filterCompany(user);
                boolean canViewOthers = user.getRole().getViewOtherPermissions().contains(PermissionEntity.METERS);
                if (!canViewOthers) {
                    searchCriteria.getFilterFields().add(FilterField.builder()
                            .field("createdBy")
                            .value(user.getId())
                            .operation("eq")
                            .values(new ArrayList<>())
                            .alternatives(Arrays.asList(
                                    FilterField.builder()
                                            .field("users")
                                            .operation("inm")
                                            .joinType(JoinType.LEFT)
                                            .value("")
                                            .values(Collections.singletonList(user.getId())).build())).build());
                }
            } else throw new CustomException("Access Denied", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(meterService.findBySearchCriteria(searchCriteria));
    }

    @GetMapping("/mini")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public Collection<MeterMiniDTO> getMini(HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        return meterService.findByCompany(user.getCompany().getId()).stream().map(meterMapper::toMiniDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public MeterShowDTO getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Meter> optionalMeter = meterService.findById(id);
        if (optionalMeter.isPresent()) {
            Meter savedMeter = optionalMeter.get();
            if (user.getRole().getViewPermissions().contains(PermissionEntity.METERS) &&
                    (user.getRole().getViewOtherPermissions().contains(PermissionEntity.METERS) ||
                            (savedMeter.getCreatedBy().equals(user.getId())) || savedMeter.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId())))) {
                return meterMapper.toShowDto(savedMeter, readingService);
            } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    MeterShowDTO create(@Valid @RequestBody Meter meterReq, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getCreatePermissions().contains(PermissionEntity.METERS)
                && user.getCompany().getSubscription().getSubscriptionPlan().getFeatures().contains(PlanFeatures.METER)) {
            Meter savedMeter = meterService.create(meterReq, user);
            meterService.notify(savedMeter, Helper.getLocale(user));
            return meterMapper.toShowDto(savedMeter, readingService);
        } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public MeterShowDTO patch(@Valid @RequestBody MeterPatchDTO meter,
                              @PathVariable("id") Long id,
                              HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Meter> optionalMeter = meterService.findById(id);

        if (optionalMeter.isPresent()) {
            Meter savedMeter = optionalMeter.get();
            em.detach(savedMeter);
            if (user.getRole().getEditOtherPermissions().contains(PermissionEntity.METERS) || savedMeter.getCreatedBy().equals(user.getId())) {
                Meter patchedMeter = meterService.update(id, meter);
                meterService.patchNotify(savedMeter, patchedMeter, Helper.getLocale(user));
                return meterMapper.toShowDto(patchedMeter, readingService);
            } else throw new CustomException("Forbidden", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Meter not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/asset/{id}")
    @PreAuthorize("permitAll()")

    public Collection<MeterShowDTO> getByAsset(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Asset> optionalAsset = assetService.findById(id);
        if (optionalAsset.isPresent()) {
            return meterService.findByAsset(id).stream().map(meter -> meterMapper.toShowDto(meter, readingService)).collect(Collectors.toList());
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<Meter> optionalMeter = meterService.findById(id);
        if (optionalMeter.isPresent()) {
            Meter savedMeter = optionalMeter.get();
            if (savedMeter.getCreatedBy().equals(user.getId()) ||
                    user.getRole().getDeleteOtherPermissions().contains(PermissionEntity.METERS)) {
                meterService.delete(id);
                return new ResponseEntity<>(new SuccessResponse(true, "Deleted successfully"),
                        HttpStatus.OK);
            } else throw new CustomException("Forbidden", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Meter not found", HttpStatus.NOT_FOUND);
    }
}



