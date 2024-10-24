package org.example.api;

import org.example.models.UserProfileDTO;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserProfileClientAPI {


    // Get all User Profiles - returns Flux<UserProfileDTO>
    public static Flux<UserProfileDTO> getAllUserProfilesNB(WebClient webClient) {
        return webClient.get()
                .uri("/api/pip/user-profiles")
                .retrieve()
                .bodyToFlux(UserProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Flux.error(new Exception("Error retrieving user profiles: " + e.getResponseBodyAsString(), e));
                });
    }

    // Get a single User Profile by ID - returns Mono<UserProfileDTO>
    public static Mono<UserProfileDTO> getUserProfileByIdNB(WebClient webClient, Long id) {
        return webClient.get()
                .uri("/api/pip/user-profiles/{id}", id)
                .retrieve()
                .bodyToMono(UserProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error retrieving user profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Create a new User Profile - returns Mono<UserProfileDTO>
    public static Mono<UserProfileDTO> createUserProfileNB(WebClient webClient, UserProfileDTO userProfileDTO) {
        return webClient.post()
                .uri("/api/pip/user-profiles")
                .body(Mono.just(userProfileDTO), UserProfileDTO.class)
                .retrieve()
                .bodyToMono(UserProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error creating user profile: " + e.getResponseBodyAsString(), e));
                });
    }

    // Update an existing User Profile - returns Mono<UserProfileDTO>
    public static Mono<UserProfileDTO> updateUserProfileNB(WebClient webClient, Long id, UserProfileDTO userProfileDTO) {
        return webClient.put()
                .uri("/api/pip/user-profiles/{id}", id)
                .body(Mono.just(userProfileDTO), UserProfileDTO.class)
                .retrieve()
                .bodyToMono(UserProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new RuntimeException("Error updating user profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Delete a User Profile by ID - returns Mono<Void>
    public static Mono<Void> deleteUserProfileNB(WebClient webClient, Long id) {
        return webClient.delete()
                .uri("/api/pip/user-profiles/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error deleting user profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Get all User Profiles
    public static List<UserProfileDTO> getAllUserProfiles(WebClient webClient) throws Exception {
        try {
            return webClient.get()
                    .uri("/api/pip/user-profiles")
                    .retrieve()
                    .bodyToFlux(UserProfileDTO.class)
                    .collectList()
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving user profiles: " + e.getResponseBodyAsString(), e);
        }
    }

    // Get a single User Profile by ID
    public static UserProfileDTO getUserProfileById(WebClient webClient, Long id) throws Exception {
        try {
            return webClient.get()
                    .uri("/api/pip/user-profiles/{id}", id)
                    .retrieve()
                    .bodyToMono(UserProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving user profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Create a new User Profile
    public static UserProfileDTO createUserProfile(WebClient webClient, UserProfileDTO userProfileDTO) throws Exception {
        try {
            return webClient.post()
                    .uri("/api/pip/user-profiles")
                    .body(Mono.just(userProfileDTO), UserProfileDTO.class)
                    .retrieve()
                    .bodyToMono(UserProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error creating user profile: " + e.getResponseBodyAsString(), e);
        }
    }

    // Update an existing User Profile
    public static UserProfileDTO updateUserProfile(WebClient webClient, Long id, UserProfileDTO userProfileDTO) throws Exception{
        try {
            return webClient.put()
                    .uri("/api/pip/user-profiles/{id}", id)
                    .body(Mono.just(userProfileDTO), UserProfileDTO.class)
                    .retrieve()
                    .bodyToMono(UserProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error updating user profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Delete a User Profile by ID
    public static void deleteUserProfile(WebClient webClient, Long id) throws Exception {
        try {
            webClient.delete()
                    .uri("/api/pip/user-profiles/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();  // Blocking to ensure the deletion completes
        } catch (WebClientResponseException e) {
            throw new Exception("Error deleting user profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }
}