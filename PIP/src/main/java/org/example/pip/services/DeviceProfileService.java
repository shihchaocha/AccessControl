package org.example.pip.services;

import org.example.models.DeviceProfileDTO;
import java.util.List;
import java.util.Optional;

public interface DeviceProfileService {
    List<DeviceProfileDTO> getAllDeviceProfiles();
    Optional<DeviceProfileDTO> getDeviceProfileById(Long id);
    DeviceProfileDTO createDeviceProfile(DeviceProfileDTO deviceProfileDTO);
    DeviceProfileDTO updateDeviceProfile(Long id, DeviceProfileDTO deviceProfileDTO);
    void deleteDeviceProfile(Long id);
}

