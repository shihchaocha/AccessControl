package org.example.pip.services;

import org.example.models.UserProfileDTO;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    List<UserProfileDTO> getAllUserProfiles();
    Optional<UserProfileDTO> getUserProfileById(Long id);
    UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO);
    UserProfileDTO updateUserProfile(Long id, UserProfileDTO userProfileDTO);
    void deleteUserProfile(Long id);
}
