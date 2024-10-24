package org.example.pip.controllers;

import org.example.models.ResourceProfileDTO;
import org.example.pip.services.ResourceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pip/resource-profiles")
public class ResourceProfileController {

    private final ResourceProfileService resourceProfileService;

    @Autowired
    public ResourceProfileController(ResourceProfileService resourceProfileService) {
        this.resourceProfileService = resourceProfileService;
    }

    @GetMapping
    public List<ResourceProfileDTO> getAllResourceProfiles() {
        return resourceProfileService.getAllResourceProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceProfileDTO> getResourceProfileById(@PathVariable Long id) {
        return resourceProfileService.getResourceProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResourceProfileDTO createResourceProfile(@RequestBody ResourceProfileDTO resourceProfileDTO) {
        return resourceProfileService.createResourceProfile(resourceProfileDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceProfileDTO> updateResourceProfile(@PathVariable Long id, @RequestBody ResourceProfileDTO resourceProfileDTO) {
        try {
            ResourceProfileDTO updatedProfile = resourceProfileService.updateResourceProfile(id, resourceProfileDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResourceProfile(@PathVariable Long id) {
        resourceProfileService.deleteResourceProfile(id);
        return ResponseEntity.noContent().build();
    }
}
