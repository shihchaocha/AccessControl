package org.example.pip.services;

import org.example.models.UserProfileDTO;
import org.example.pip.models.UserProfile;
import org.example.pip.models.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<UserProfileDTO> getAllUserProfiles() {
        return userProfileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserProfileDTO> getUserProfileById(Long id) {
        return userProfileRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = convertToEntity(userProfileDTO);
        UserProfile savedUser = userProfileRepository.save(userProfile);
        return convertToDTO(savedUser);
    }

    @Override
    public UserProfileDTO updateUserProfile(Long id, UserProfileDTO userProfileDTO) {
        return userProfileRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userProfileDTO.getUsername());
                    existingUser.setEmail(userProfileDTO.getEmail());
                    existingUser.setDateOfBirth(userProfileDTO.getDateOfBirth());
                    existingUser.setGender(userProfileDTO.getGender());
                    UserProfile updatedUser = userProfileRepository.save(existingUser);
                    return convertToDTO(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + id));
    }

    @Override
    public void deleteUserProfile(Long id) {
        userProfileRepository.deleteById(id);
    }

    // DTO <-> Entity converters
    private UserProfileDTO convertToDTO(UserProfile userProfile) {
        return new UserProfileDTO(userProfile.getId(), userProfile.getUsername(),
                userProfile.getEmail(), userProfile.getDateOfBirth(),
                userProfile.getGender(), userProfile.getRole(), userProfile.getSecurityLevel());
    }

    private UserProfile convertToEntity(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(userProfileDTO.getUsername());
        userProfile.setEmail(userProfileDTO.getEmail());
        userProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        userProfile.setGender(userProfileDTO.getGender());
        userProfile.setRole(userProfileDTO.getRole());
        userProfile.setSecurityLevel(userProfileDTO.getSecurityLevel());
        return userProfile;
    }
}