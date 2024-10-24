package org.example.pip.services;

import org.example.models.ResourceProfileDTO;
import org.example.pip.models.ResourceProfile;
import org.example.pip.models.ResourceProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceProfileServiceImpl implements ResourceProfileService {

    private final ResourceProfileRepository resourceProfileRepository;

    @Autowired
    public ResourceProfileServiceImpl(ResourceProfileRepository resourceProfileRepository) {
        this.resourceProfileRepository = resourceProfileRepository;
    }

    @Override
    public List<ResourceProfileDTO> getAllResourceProfiles() {
        return resourceProfileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResourceProfileDTO> getResourceProfileById(Long id) {
        return resourceProfileRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public ResourceProfileDTO createResourceProfile(ResourceProfileDTO resourceProfileDTO) {
        ResourceProfile resourceProfile = convertToEntity(resourceProfileDTO);
        ResourceProfile savedProfile = resourceProfileRepository.save(resourceProfile);
        return convertToDTO(savedProfile);
    }

    @Override
    public ResourceProfileDTO updateResourceProfile(Long id, ResourceProfileDTO resourceProfileDTO) {
        return resourceProfileRepository.findById(id)
                .map(existingProfile -> {
                    existingProfile.setName(resourceProfileDTO.getName());
                    existingProfile.setType(resourceProfileDTO.getType());
                    existingProfile.setIp(resourceProfileDTO.getIp());
                    existingProfile.setZone(resourceProfileDTO.getZone());
                    ResourceProfile updatedProfile = resourceProfileRepository.save(existingProfile);
                    return convertToDTO(updatedProfile);
                })
                .orElseThrow(() -> new RuntimeException("ResourceProfile not found with id " + id));
    }

    @Override
    public void deleteResourceProfile(Long id) {
        resourceProfileRepository.deleteById(id);
    }

    // DTO <-> Entity converters
    private ResourceProfileDTO convertToDTO(ResourceProfile resourceProfile) {
        return new ResourceProfileDTO(resourceProfile.getId(), resourceProfile.getName(),
                resourceProfile.getType(), resourceProfile.getIp(), resourceProfile.getZone());
    }

    private ResourceProfile convertToEntity(ResourceProfileDTO resourceProfileDTO) {
        ResourceProfile resourceProfile = new ResourceProfile();
        resourceProfile.setName(resourceProfileDTO.getName());
        resourceProfile.setType(resourceProfileDTO.getType());
        resourceProfile.setIp(resourceProfileDTO.getIp());
        resourceProfile.setZone(resourceProfileDTO.getZone());
        return resourceProfile;
    }
}