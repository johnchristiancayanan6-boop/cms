package com.eastwest.controller;

import com.eastwest.dto.FloorPlanPatchDTO;
import com.eastwest.dto.FloorPlanShowDTO;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.FloorPlanMapper;
import com.eastwest.model.FloorPlan;
import com.eastwest.model.Location;
import com.eastwest.model.OwnUser;
import com.eastwest.service.FloorPlanService;
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
@RequestMapping("/floor-plans")
@Tag(name = "floorPlan")
@RequiredArgsConstructor
public class FloorPlanController {

    private final FloorPlanService floorPlanService;
    private final UserService userService;
    private final LocationService locationService;
    private final FloorPlanMapper floorPlanMapper;

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public FloorPlanShowDTO getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<FloorPlan> optionalFloorPlan = floorPlanService.findById(id);
        if (optionalFloorPlan.isPresent()) {
            FloorPlan savedFloorPlan = optionalFloorPlan.get();
            return floorPlanMapper.toShowDto(savedFloorPlan);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/location/{id}")
    @PreAuthorize("permitAll()")

    public Collection<FloorPlanShowDTO> getByLocation(@PathVariable("id") Long id,
                                                      HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Location> optionalLocation = locationService.findById(id);
        if (optionalLocation.isPresent()) {
            return floorPlanService.findByLocation(id).stream().map(floorPlanMapper::toShowDto).collect(Collectors.toList());
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    FloorPlanShowDTO create(@Valid @RequestBody FloorPlan floorPlanReq,
                            HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        return floorPlanMapper.toShowDto(floorPlanService.create(floorPlanReq));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public FloorPlanShowDTO patch(@Valid @RequestBody FloorPlanPatchDTO floorPlan, @PathVariable("id") Long id,
                                  HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<FloorPlan> optionalFloorPlan = floorPlanService.findById(id);

        if (optionalFloorPlan.isPresent()) {
            FloorPlan savedFloorPlan = optionalFloorPlan.get();
            return floorPlanMapper.toShowDto(floorPlanService.update(id, floorPlan));
        } else throw new CustomException("FloorPlan not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<FloorPlan> optionalFloorPlan = floorPlanService.findById(id);
        if (optionalFloorPlan.isPresent()) {
            floorPlanService.delete(id);
            return new ResponseEntity(new SuccessResponse(true, "Deleted successfully"),
                    HttpStatus.OK);
        } else throw new CustomException("FloorPlan not found", HttpStatus.NOT_FOUND);
    }

}


