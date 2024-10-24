package org.example.pip.services;

import org.example.models.ResourceProfileDTO;
import java.util.List;
import java.util.Optional;

public interface ResourceProfileService {
    List<ResourceProfileDTO> getAllResourceProfiles();
    Optional<ResourceProfileDTO> getResourceProfileById(Long id);
    ResourceProfileDTO createResourceProfile(ResourceProfileDTO resourceProfileDTO);
    ResourceProfileDTO updateResourceProfile(Long id, ResourceProfileDTO resourceProfileDTO);
    void deleteResourceProfile(Long id);
}
