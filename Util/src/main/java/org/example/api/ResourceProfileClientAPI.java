package org.example.api;

import org.example.models.ResourceProfileDTO;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ResourceProfileClientAPI {

    // Get all Resource Profiles
    public static List<ResourceProfileDTO> getAllResourceProfiles(WebClient webClient) throws Exception {
        try {
            return webClient.get()
                    .uri("/api/pip/resource-profiles")
                    .retrieve()
                    .bodyToFlux(ResourceProfileDTO.class)
                    .collectList()
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving resource profiles: " + e.getResponseBodyAsString(), e);
        }
    }

    // Get a single Resource Profile by ID
    public static ResourceProfileDTO getResourceProfileById(WebClient webClient, Long id) throws Exception {
        try {
            return webClient.get()
                    .uri("/api/pip/resource-profiles/{id}", id)
                    .retrieve()
                    .bodyToMono(ResourceProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving resource profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Create a new Resource Profile
    public static ResourceProfileDTO createResourceProfile(WebClient webClient, ResourceProfileDTO resourceProfileDTO) throws Exception {
        try {
            return webClient.post()
                    .uri("/api/pip/resource-profiles")
                    .body(Mono.just(resourceProfileDTO), ResourceProfileDTO.class)
                    .retrieve()
                    .bodyToMono(ResourceProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error creating resource profile: " + e.getResponseBodyAsString(), e);
        }
    }

    // Update an existing Resource Profile
    public static ResourceProfileDTO updateResourceProfile(WebClient webClient, Long id, ResourceProfileDTO resourceProfileDTO) throws Exception {
        try {
            return webClient.put()
                    .uri("/api/pip/resource-profiles/{id}", id)
                    .body(Mono.just(resourceProfileDTO), ResourceProfileDTO.class)
                    .retrieve()
                    .bodyToMono(ResourceProfileDTO.class)
                    .block();  // Blocking to get the result synchronously
        } catch (WebClientResponseException e) {
            throw new Exception("Error updating resource profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Delete a Resource Profile by ID
    public static void deleteResourceProfile(WebClient webClient, Long id) throws Exception{
        try {
            webClient.delete()
                    .uri("/api/pip/resource-profiles/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();  // Blocking to ensure the deletion completes
        } catch (WebClientResponseException e) {
            throw new Exception("Error deleting resource profile with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Get all Resource Profiles - returns Flux<ResourceProfileDTO>
    public static Flux<ResourceProfileDTO> getAllResourceProfilesNB(WebClient webClient) {
        return webClient.get()
                .uri("/api/pip/resource-profiles")
                .retrieve()
                .bodyToFlux(ResourceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Flux.error(new Exception("Error retrieving resource profiles: " + e.getResponseBodyAsString(), e));
                });
    }

    // Get a single Resource Profile by ID - returns Mono<ResourceProfileDTO>
    public static Mono<ResourceProfileDTO> getResourceProfileByIdNB(WebClient webClient, Long id) {
        return webClient.get()
                .uri("/api/pip/resource-profiles/{id}", id)
                .retrieve()
                .bodyToMono(ResourceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error retrieving resource profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Create a new Resource Profile - returns Mono<ResourceProfileDTO>
    public static Mono<ResourceProfileDTO> createResourceProfileNB(WebClient webClient, ResourceProfileDTO resourceProfileDTO) {
        return webClient.post()
                .uri("/api/pip/resource-profiles")
                .body(Mono.just(resourceProfileDTO), ResourceProfileDTO.class)
                .retrieve()
                .bodyToMono(ResourceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error creating resource profile: " + e.getResponseBodyAsString(), e));
                });
    }

    // Update an existing Resource Profile - returns Mono<ResourceProfileDTO>
    public static Mono<ResourceProfileDTO> updateResourceProfileNB(WebClient webClient, Long id, ResourceProfileDTO resourceProfileDTO) {
        return webClient.put()
                .uri("/api/pip/resource-profiles/{id}", id)
                .body(Mono.just(resourceProfileDTO), ResourceProfileDTO.class)
                .retrieve()
                .bodyToMono(ResourceProfileDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error updating resource profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Delete a Resource Profile by ID - returns Mono<Void>
    public static Mono<Void> deleteResourceProfileNB(WebClient webClient, Long id) {
        return webClient.delete()
                .uri("/api/pip/resource-profiles/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error deleting resource profile with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }
}
