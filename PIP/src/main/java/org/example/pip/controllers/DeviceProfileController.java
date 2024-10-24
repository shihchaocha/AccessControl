package org.example.pip.controllers;

import org.example.models.DeviceProfileDTO;
import org.example.pip.services.DeviceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pip/device-profiles")
public class DeviceProfileController {

    private final DeviceProfileService deviceProfileService;

    @Autowired
    public DeviceProfileController(DeviceProfileService deviceProfileService) {
        this.deviceProfileService = deviceProfileService;
    }

    @GetMapping
    public List<DeviceProfileDTO> getAllDeviceProfiles() {
        return deviceProfileService.getAllDeviceProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceProfileDTO> getDeviceProfileById(@PathVariable Long id) {
        return deviceProfileService.getDeviceProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DeviceProfileDTO createDeviceProfile(@RequestBody DeviceProfileDTO deviceProfileDTO) {
        return deviceProfileService.createDeviceProfile(deviceProfileDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceProfileDTO> updateDeviceProfile(@PathVariable Long id, @RequestBody DeviceProfileDTO deviceProfileDTO) {
        try {
            DeviceProfileDTO updatedProfile = deviceProfileService.updateDeviceProfile(id, deviceProfileDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceProfile(@PathVariable Long id) {
        deviceProfileService.deleteDeviceProfile(id);
        return ResponseEntity.noContent().build();
    }
}
