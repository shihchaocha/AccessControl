package org.example.api;

import org.example.models.AccessRequest;
import org.example.models.AccessResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class PDPRequestClientAPI {

    // 靜態方法：向 /decision API 發送請求，並返回 AccessResponse
    public static AccessResponse sendAccessRequest(WebClient webClient, AccessRequest accessRequest) throws Exception {
        try {
            // 使用 WebClient 發送 POST 請求
            return webClient.post()
                    .uri("/decision")
                    .body(Mono.just(accessRequest), AccessRequest.class)
                    .retrieve()
                    .bodyToMono(AccessResponse.class)
                    .block();  // 阻塞操作，同步獲取結果
        } catch (WebClientResponseException e) {
            // 捕獲錯誤，並打印異常信息
            throw new Exception("Error during sending access request: " + e.getResponseBodyAsString(), e);
        }
    }
}
