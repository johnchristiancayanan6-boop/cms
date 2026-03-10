package com.eastwest.controller;

import com.eastwest.advancedsearch.SearchCriteria;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.dto.TeamMiniDTO;
import com.eastwest.dto.TeamPatchDTO;
import com.eastwest.dto.TeamShowDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.TeamMapper;
import com.eastwest.model.OwnUser;
import com.eastwest.model.Team;
import com.eastwest.model.enums.PermissionEntity;
import com.eastwest.model.enums.RoleType;
import com.eastwest.service.TeamService;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
@Tag(name = "team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final UserService userService;
    private final EntityManager em;

    @PostMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<TeamShowDTO>> search(@RequestBody SearchCriteria searchCriteria,
                                                    HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            if (user.getRole().getViewPermissions().contains(PermissionEntity.PEOPLE_AND_TEAMS)) {
                searchCriteria.filterCompany(user);
            } else throw new CustomException("Access Denied", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(teamService.findBySearchCriteria(searchCriteria));
    }

    @GetMapping("/mini")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public Collection<TeamMiniDTO> getMini(HttpServletRequest req) {
        OwnUser team = userService.whoami(req);
        return teamService.findByCompany(team.getCompany().getId()).stream().map(teamMapper::toMiniDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public TeamShowDTO getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Team> optionalTeam = teamService.findById(id);
        if (optionalTeam.isPresent()) {
            Team savedTeam = optionalTeam.get();
            return teamMapper.toShowDto(savedTeam);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    TeamShowDTO create(@Valid @RequestBody Team teamReq, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getCreatePermissions().contains(PermissionEntity.PEOPLE_AND_TEAMS)) {
            Team savedTeam = teamService.create(teamReq);
            teamService.notify(savedTeam, Helper.getLocale(user));
            return teamMapper.toShowDto(savedTeam);
        } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public TeamShowDTO patch(@Valid @RequestBody TeamPatchDTO team, @PathVariable(
                                     "id") Long id,
                             HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Team> optionalTeam = teamService.findById(id);
        if (optionalTeam.isPresent()) {
            Team savedTeam = optionalTeam.get();
            em.detach(savedTeam);
            Team patchTeam = teamService.update(id, team);
            teamService.patchNotify(savedTeam, patchTeam, Helper.getLocale(user));
            return teamMapper.toShowDto(patchTeam);
        } else throw new CustomException("Team not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<Team> optionalTeam = teamService.findById(id);
        if (optionalTeam.isPresent()) {
            Team savedTeam = optionalTeam.get();
            if (savedTeam.getCreatedBy().equals(user.getId()) || user.getRole().getDeleteOtherPermissions().contains(PermissionEntity.PEOPLE_AND_TEAMS)) {
                teamService.delete(id);
                return new ResponseEntity(new SuccessResponse(true, "Deleted successfully"),
                        HttpStatus.OK);
            } else throw new CustomException("Forbidden", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Team not found", HttpStatus.NOT_FOUND);
    }

}



