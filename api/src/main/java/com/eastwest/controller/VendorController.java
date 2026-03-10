package com.eastwest.controller;

import com.eastwest.advancedsearch.SearchCriteria;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.dto.VendorMiniDTO;
import com.eastwest.dto.VendorPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.VendorMapper;
import com.eastwest.model.OwnUser;
import com.eastwest.model.Vendor;
import com.eastwest.model.enums.PermissionEntity;
import com.eastwest.model.enums.RoleType;
import com.eastwest.service.UserService;
import com.eastwest.service.VendorService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/vendors")
@Tag(name = "vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;
    private final UserService userService;
    private final VendorMapper vendorMapper;

    @PostMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<Vendor>> search(@RequestBody SearchCriteria searchCriteria, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            if (user.getRole().getViewPermissions().contains(PermissionEntity.VENDORS_AND_CUSTOMERS)) {
                searchCriteria.filterCompany(user);
            } else throw new CustomException("Access Denied", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(vendorService.findBySearchCriteria(searchCriteria));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public Vendor getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Vendor> optionalVendor = vendorService.findById(id);
        if (optionalVendor.isPresent()) {
            Vendor savedVendor = optionalVendor.get();
            if (user.getRole().getViewPermissions().contains(PermissionEntity.VENDORS_AND_CUSTOMERS)) {
                return savedVendor;
            } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/mini")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public Collection<VendorMiniDTO> getMini(HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        return vendorService.findByCompany(user.getCompany().getId()).stream().map(vendorMapper::toMiniDto).collect(Collectors.toList());
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    Vendor create(@Valid @RequestBody Vendor vendorReq, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getCreatePermissions().contains(PermissionEntity.VENDORS_AND_CUSTOMERS)) {
            return vendorService.create(vendorReq);
        } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public Vendor patch(@Valid @RequestBody VendorPatchDTO vendor, @PathVariable(
                                "id") Long id,
                        HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Vendor> optionalVendor = vendorService.findById(id);

        if (optionalVendor.isPresent()) {
            Vendor savedVendor = optionalVendor.get();
            if (user.getRole().getEditOtherPermissions().contains(PermissionEntity.VENDORS_AND_CUSTOMERS) || savedVendor.getCreatedBy().equals(user.getId())) {
                return vendorService.update(id, vendor);
            } else throw new CustomException("Forbidden", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Vendor not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<Vendor> optionalVendor = vendorService.findById(id);
        if (optionalVendor.isPresent()) {
            Vendor savedVendor = optionalVendor.get();
            if (user.getId().equals(savedVendor.getCreatedBy()) ||
                    user.getRole().getDeleteOtherPermissions().contains(PermissionEntity.VENDORS_AND_CUSTOMERS)) {
                vendorService.delete(id);
                return new ResponseEntity<>(new SuccessResponse(true, "Deleted successfully"),
                        HttpStatus.OK);
            } else throw new CustomException("Forbidden", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Vendor not found", HttpStatus.NOT_FOUND);
    }

}


