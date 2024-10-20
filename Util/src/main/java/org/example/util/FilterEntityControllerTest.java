package org.example.util;

import org.example.models.FilterEntityDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class FilterEntityControllerTest {

    public static List<FilterEntityDTO> testGetAllFilters(WebClient webClient) {
        List<FilterEntityDTO> response = webClient.get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(FilterEntityDTO.class)
                .collectList()  // 將多個元素收集為 List
                .block();

        return response;
    }

    // 測試 GET 請求，根據 ID 獲取單個 FilterEntityDTO
    public static FilterEntityDTO testGetFilterById(WebClient webClient,Long id) {
        FilterEntityDTO response = webClient.get()
                .uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FilterEntityDTO.class)
                .block();

        return response;
    }

    // 測試 POST 請求，創建新的 FilterEntityDTO
    public static FilterEntityDTO testCreateFilter(WebClient webClient, FilterEntityDTO filterDTO) {
        FilterEntityDTO response = webClient.post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(filterDTO), FilterEntityDTO.class)
                .retrieve()
                .bodyToMono(FilterEntityDTO.class)
                .block();

        return response;
    }

    // 測試 PUT 請求，更新 FilterEntityDTO
    public static FilterEntityDTO testUpdateFilter(WebClient webClient, FilterEntityDTO filterDTO) {
        FilterEntityDTO response = webClient.put()
                .uri("/{id}", filterDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(filterDTO), FilterEntityDTO.class)
                .retrieve()
                .bodyToMono(FilterEntityDTO.class)
                .block();

        return response;
    }

    // 測試 DELETE 請求，刪除 FilterEntity
    public static void testDeleteFilter(WebClient webClient, Long id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
