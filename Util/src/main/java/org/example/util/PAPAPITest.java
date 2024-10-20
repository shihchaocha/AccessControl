package org.example.util;


import org.example.models.AccessRuleDTO;
import org.example.models.Resource;
import org.example.models.SearchCriteria;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.example.models.Subject;

import java.util.List;

public class PAPAPITest {

    public static List<AccessRuleDTO> testFindAll(WebClient webClient) {
        // 創建 SearchCriteria 的測試數據

        // 發送 POST 請求，返回 AccessRuleDTO 的列表
        List<AccessRuleDTO> accessRuleDTOs = webClient.get()
                .retrieve()
                .bodyToFlux(AccessRuleDTO.class)
                .collectList() // 收集 Flux 中的所有 AccessRuleDTO 並轉換為 Mono<List<AccessRuleDTO>>
                .block(); // 阻塞，直到 Mono 完成並返回 List<AccessRuleDTO>

        // 將結果打印出來
        accessRuleDTOs.forEach(accessRule -> System.out.println("Found AccessRuleDTO: " + accessRule));
        return accessRuleDTOs;
    }



    // 測試 POST /search 端點，查詢符合條件的 AccessRuleDTOs
    public static List<AccessRuleDTO> testFindBySubjectAndResource(WebClient webClient, Subject subject, Resource resource) {
        // 創建 SearchCriteria 的測試數據
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSubject(subject);
        searchCriteria.setResource(resource);

        // 發送 POST 請求，返回 AccessRuleDTO 的列表
        List<AccessRuleDTO> accessRuleDTOs = webClient.post()
                .uri("/search")
                .body(Mono.just(searchCriteria), SearchCriteria.class)
                .retrieve()
                .bodyToFlux(AccessRuleDTO.class)
                .collectList() // 收集 Flux 中的所有 AccessRuleDTO 並轉換為 Mono<List<AccessRuleDTO>>
                .block(); // 阻塞，直到 Mono 完成並返回 List<AccessRuleDTO>

        // 將結果打印出來
        accessRuleDTOs.forEach(accessRule -> System.out.println("Found AccessRuleDTO: " + accessRule));
        return accessRuleDTOs;
    }

    // 測試 GET /{id} 端點，根據 ID 獲取單個 AccessRuleDTO
    public static AccessRuleDTO testGetAccessRuleById(WebClient webClient, Long id) {
        // 發送 GET 請求，返回單個 AccessRuleDTO
        AccessRuleDTO accessRuleDTO = webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(AccessRuleDTO.class)
                .block(); // 阻塞，直到 Mono 完成並返回 AccessRuleDTO 物件

        // 打印取回的 AccessRuleDTO 物件
        System.out.println("AccessRuleDTO: " + accessRuleDTO);
        // 返回 AccessRuleDTO 物件
        return accessRuleDTO;
    }

    // 測試 POST 端點，創建新的 AccessRuleDTO
    public static AccessRuleDTO testSaveAccessRule(WebClient webClient, AccessRuleDTO accessRuleDTO) {

        // 發送 POST 請求，創建並返回 AccessRuleDTO
        AccessRuleDTO createdAccessRuleDTO = webClient.post()
                .body(Mono.just(accessRuleDTO), AccessRuleDTO.class)
                .retrieve()
                .bodyToMono(AccessRuleDTO.class)
                .doOnNext(newAccessRuleDTO -> System.out.println("Created AccessRuleDTO: " + newAccessRuleDTO))
                .block();  // 阻塞直到請求完成，並取得返回的 AccessRuleDTO 物件

        // 返回創建的 AccessRuleDTO 物件
        return createdAccessRuleDTO;
    }

    // 測試 PUT /{id} 端點，更新 AccessRuleDTO
    public static AccessRuleDTO testUpdateAccessRule(Long id, AccessRuleDTO updatedAccessRuleDTO, WebClient webClient) {
        // 發送 PUT 請求，更新並返回 AccessRuleDTO
        AccessRuleDTO updatedRuleDTO = webClient.put()
                .uri("/{id}", id)
                .body(Mono.just(updatedAccessRuleDTO), AccessRuleDTO.class)
                .retrieve()
                .bodyToMono(AccessRuleDTO.class)
                .doOnNext(accessRuleDTO -> System.out.println("Updated AccessRuleDTO: " + accessRuleDTO))
                .block();  // 阻塞直到請求完成，並取得返回的 AccessRuleDTO 物件

        // 返回更新後的 AccessRuleDTO 物件
        return updatedRuleDTO;
    }

    // 測試 DELETE /{id} 端點，刪除 AccessRuleDTO
    public static void testDeleteAccessRule(Long id, WebClient webClient) {
        // 發送 DELETE 請求，刪除 AccessRuleDTO
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(unused -> System.out.println("Deleted AccessRuleDTO with ID: " + id))
                .block();
    }
}
