package org.example.api;

import org.example.models.DeviceProfileDTO;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class DeviceProfileClientAPI {

    // Get all Device Profiles
    public static List<DeviceProfileDTO> getAllDeviceProfiles(WebClient webClient) throws Exception{
        try {
            return webClient.get()
                    .uri("/api/pip/device-profiles")
                    .retrieve()
                    .bodyToFlux(DeviceProfileDTO.class)
                    .collectList()
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving device profiles: " + e.getResponseBodyAsString(), e);
        }
    }

    // Get a single Device Profile by ID
    public static DeviceProfileDTO getDeviceProfileById(WebClient webClient, Long id) throws Exception {
        try {
            return webClient.get()
                    .uri("/api/pip/device-profiles/{id}", id)
                    .retrieve()
                    .bodyToMono(DeviceProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving device profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Create a new Device Profile
    public static DeviceProfileDTO createDeviceProfile(WebClient webClient, DeviceProfileDTO deviceProfileDTO) throws Exception {
        try {
            return webClient.post()
                    .uri("/api/pip/device-profiles")
                    .body(Mono.just(deviceProfileDTO), DeviceProfileDTO.class)
                    .retrieve()
                    .bodyToMono(DeviceProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error creating device profile: " + e.getResponseBodyAsString(), e);
        }
    }

    // Update an existing Device Profile
    public static DeviceProfileDTO updateDeviceProfile(WebClient webClient, Long id, DeviceProfileDTO deviceProfileDTO) throws Exception{
        try {
            return webClient.put()
                    .uri("/api/pip/device-profiles/{id}", id)
                    .body(Mono.just(deviceProfileDTO), DeviceProfileDTO.class)
                    .retrieve()
                    .bodyToMono(DeviceProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error updating device profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Delete a Device Profile by ID
    public static void deleteDeviceProfile(WebClient webClient, Long id) throws Exception{
        try {
            webClient.delete()
                    .uri("/api/pip/device-profiles/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();  // Blocking to ensure the deletion completes
        } catch (WebClientResponseException e) {
            throw new Exception("Error deleting device profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    public static Flux<DeviceProfileDTO> getAllDeviceProfilesNB(WebClient webClient) {
        return webClient.get()
                .uri("/api/pip/device-profiles")
                .retrieve()
                .bodyToFlux(DeviceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Flux.error(new Exception("Error retrieving device profiles: " + e.getResponseBodyAsString(), e));
                });
    }

    // Get a single Device Profile by ID - returns Mono<DeviceProfileDTO>
    public static Mono<DeviceProfileDTO> getDeviceProfileByIdNB(WebClient webClient, Long id) {
        return webClient.get()
                .uri("/api/pip/device-profiles/{id}", id)
                .retrieve()
                .bodyToMono(DeviceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error retrieving device profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Create a new Device Profile - returns Mono<DeviceProfileDTO>
    public static Mono<DeviceProfileDTO> createDeviceProfileNB(WebClient webClient, DeviceProfileDTO deviceProfileDTO) {
        return webClient.post()
                .uri("/api/pip/device-profiles")
                .body(Mono.just(deviceProfileDTO), DeviceProfileDTO.class)
                .retrieve()
                .bodyToMono(DeviceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error creating device profile: " + e.getResponseBodyAsString(), e));
                });
    }

    // Update an existing Device Profile - returns Mono<DeviceProfileDTO>
    public static Mono<DeviceProfileDTO> updateDeviceProfileNB(WebClient webClient, Long id, DeviceProfileDTO deviceProfileDTO) {
        return webClient.put()
                .uri("/api/pip/device-profiles/{id}", id)
                .body(Mono.just(deviceProfileDTO), DeviceProfileDTO.class)
                .retrieve()
                .bodyToMono(DeviceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error updating device profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Delete a Device Profile by ID - returns Mono<Void>
    public static Mono<Void> deleteDeviceProfileNB(WebClient webClient, Long id) {
        return webClient.delete()
                .uri("/api/pip/device-profiles/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error deleting device profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }
}
