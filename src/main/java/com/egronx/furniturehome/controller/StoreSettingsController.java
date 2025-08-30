package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.StoreSettingsDTO;
import com.egronx.furniturehome.service.StoreSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/settings")
public class StoreSettingsController {

    @Autowired
    private StoreSettingsService storeSettingsService;


    @GetMapping
    public StoreSettingsDTO getStoreSettings() {
        return storeSettingsService.getStoreSettings();
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StoreSettingsDTO saveStoreSettings(@RequestBody StoreSettingsDTO dto) {
        return storeSettingsService.createStoreSettings(dto);
    }


    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StoreSettingsDTO updateStoreSettings(@RequestBody StoreSettingsDTO dto) {
        return storeSettingsService.updateStoreSettings(dto);
    }
}
