package org.example.pip.services;

import org.example.models.DeviceProfileDTO;
import org.example.pip.models.DeviceProfile;
import org.example.pip.models.DeviceProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceProfileServiceImpl implements DeviceProfileService {

    private final DeviceProfileRepository deviceProfileRepository;

    @Autowired
    public DeviceProfileServiceImpl(DeviceProfileRepository deviceProfileRepository) {
        this.deviceProfileRepository = deviceProfileRepository;
    }

    @Override
    public List<DeviceProfileDTO> getAllDeviceProfiles() {
        return deviceProfileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DeviceProfileDTO> getDeviceProfileById(Long id) {
        return deviceProfileRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public DeviceProfileDTO createDeviceProfile(DeviceProfileDTO deviceProfileDTO) {
        DeviceProfile deviceProfile = convertToEntity(deviceProfileDTO);
        DeviceProfile savedProfile = deviceProfileRepository.save(deviceProfile);
        return convertToDTO(savedProfile);
    }

    @Override
    public DeviceProfileDTO updateDeviceProfile(Long id, DeviceProfileDTO deviceProfileDTO) {
        return deviceProfileRepository.findById(id)
                .map(existingProfile -> {
                    existingProfile.setName(deviceProfileDTO.getName());
                    existingProfile.setType(deviceProfileDTO.getType());
                    DeviceProfile updatedProfile = deviceProfileRepository.save(existingProfile);
                    return convertToDTO(updatedProfile);
                })
                .orElseThrow(() -> new RuntimeException("DeviceProfile not found with id " + id));
    }

    @Override
    public void deleteDeviceProfile(Long id) {
        deviceProfileRepository.deleteById(id);
    }

    // DTO <-> Entity converters
    private DeviceProfileDTO convertToDTO(DeviceProfile deviceProfile) {
        return new DeviceProfileDTO(deviceProfile.getId(), deviceProfile.getName(), deviceProfile.getType());
    }

    private DeviceProfile convertToEntity(DeviceProfileDTO deviceProfileDTO) {
        return new DeviceProfile(deviceProfileDTO.getId(), deviceProfileDTO.getName(), deviceProfileDTO.getType());
    }
}