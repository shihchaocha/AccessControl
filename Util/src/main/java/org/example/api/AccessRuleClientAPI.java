package org.example.api;

import org.example.models.AccessRuleDTO;
import org.example.models.SearchCriteria;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AccessRuleClientAPI {

    // Get all Access Rules - returns a List instead of Flux
    public static List<AccessRuleDTO> getAllAccessRules(WebClient webClient) throws Exception{
        try {
            return webClient.get()
                    .uri("/api/pap/access_rules")
                    .retrieve()
                    .bodyToFlux(AccessRuleDTO.class)
                    .collectList()
                    .block();  // Blocking to get the result synchronously as List
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving access rules: " + e.getResponseBodyAsString(), e);
        }
    }

    // Search for Access Rules by Subject and Resource - returns a List instead of Flux
    public static List<AccessRuleDTO> findBySubjectAndResource(WebClient webClient, SearchCriteria searchCriteria) throws Exception{
        try {
            return webClient.post()
                    .uri("/api/pap/access_rules/search")
                    .bodyValue(searchCriteria)
                    .retrieve()
                    .bodyToFlux(AccessRuleDTO.class)
                    .collectList()
                    .block();  // Blocking to get the result synchronously as List
        } catch (WebClientResponseException e) {
            throw new Exception("Error searching for access rules: " + e.getResponseBodyAsString(), e);
        }
    }

    // Get a single Access Rule by ID - returns a single object instead of Mono
    public static AccessRuleDTO getAccessRuleById(WebClient webClient, Long id) throws Exception {
        try {
            return webClient.get()
                    .uri("/api/pap/access_rules/{id}", id)
                    .retrieve()
                    .bodyToMono(AccessRuleDTO.class)
                    .block();  // Blocking to get the result synchronously as single object
        } catch (WebClientResponseException e) {
            throw new Exception("Error retrieving access rule with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Save a new Access Rule - returns a single object instead of Mono
    public static AccessRuleDTO saveAccessRule(WebClient webClient, AccessRuleDTO accessRuleDTO) throws Exception {
        try {
            return webClient.post()
                    .uri("/api/pap/access_rules")
                    .bodyValue(accessRuleDTO)
                    .retrieve()
                    .bodyToMono(AccessRuleDTO.class)
                    .block();  // Blocking to get the result synchronously as single object
        } catch (WebClientResponseException e) {
            throw new Exception("Error saving access rule: " + e.getResponseBodyAsString(), e);
        }
    }

    // Update an existing Access Rule - returns a single object instead of Mono
    public static AccessRuleDTO updateAccessRule(WebClient webClient, Long id, AccessRuleDTO accessRuleDTO) throws Exception {
        try {
            return webClient.put()
                    .uri("/api/pap/access_rules/{id}", id)
                    .bodyValue(accessRuleDTO)
                    .retrieve()
                    .bodyToMono(AccessRuleDTO.class)
                    .block();  // Blocking to get the result synchronously as single object
        } catch (WebClientResponseException e) {
            throw new Exception("Error updating access rule with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Delete an Access Rule by ID - returns void instead of Mono<Void>
    public static void deleteAccessRule(WebClient webClient, Long id) throws Exception {
        try {
            webClient.delete()
                    .uri("/api/pap/access_rules/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();  // Blocking to ensure the deletion completes
        } catch (WebClientResponseException e) {
            throw new Exception("Error deleting access rule with ID: " + id + " - " + e.getResponseBodyAsString(), e);
        }
    }

    // Get all Access Rules - returns Flux<AccessRuleDTO>
    public static Flux<AccessRuleDTO> getAllAccessRulesNB(WebClient webClient) {
        return webClient.get()
                .uri("/api/pap/access_rules")
                .retrieve()
                .bodyToFlux(AccessRuleDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Flux.error(new Exception("Error retrieving access rules: " + e.getResponseBodyAsString(), e));
                });
    }

    // Search for Access Rules by Subject and Resource - returns Flux<AccessRuleDTO>
    public static Flux<AccessRuleDTO> findBySubjectAndResourceNB(WebClient webClient, SearchCriteria searchCriteria) {
        return webClient.post()
                .uri("/api/pap/access_rules/search")
                .bodyValue(searchCriteria)
                .retrieve()
                .bodyToFlux(AccessRuleDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Flux.error(new Exception("Error searching for access rules: " + e.getResponseBodyAsString(), e));
                });
    }

    // Get a single Access Rule by ID - returns Mono<AccessRuleDTO>
    public static Mono<AccessRuleDTO> getAccessRuleByIdNB(WebClient webClient, Long id) {
        return webClient.get()
                .uri("/api/pap/access_rules/{id}", id)
                .retrieve()
                .bodyToMono(AccessRuleDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error retrieving access rule with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Save a new Access Rule - returns Mono<AccessRuleDTO>
    public static Mono<AccessRuleDTO> saveAccessRuleNB(WebClient webClient, AccessRuleDTO accessRuleDTO) {
        return webClient.post()
                .uri("/api/pap/access_rules")
                .bodyValue(accessRuleDTO)
                .retrieve()
                .bodyToMono(AccessRuleDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error saving access rule: " + e.getResponseBodyAsString(), e));
                });
    }

    // Update an existing Access Rule - returns Mono<AccessRuleDTO>
    public static Mono<AccessRuleDTO> updateAccessRuleNB(WebClient webClient, Long id, AccessRuleDTO accessRuleDTO) {
        return webClient.put()
                .uri("/api/pap/access_rules/{id}", id)
                .bodyValue(accessRuleDTO)
                .retrieve()
                .bodyToMono(AccessRuleDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error updating access rule with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }

    // Delete an Access Rule by ID - returns Mono<Void>
    public static Mono<Void> deleteAccessRuleNB(WebClient webClient, Long id) {
        return webClient.delete()
                .uri("/api/pap/access_rules/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new Exception("Error deleting access rule with ID: " + id + " - " + e.getResponseBodyAsString(), e));
                });
    }
}
