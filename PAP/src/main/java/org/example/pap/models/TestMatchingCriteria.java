package org.example.pap.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMatchingCriteria {
    public static void main(String[] args) {
        // 創建 MatchingCriteria 對象
        MatchingCriteria criteria =
                MatchingCriteriaParser.fromComplexString("(user.id == '123' and (user.age >= 30 or E < F))");

        // 將對象轉換為 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(criteria);
            System.out.println("JSON Output: " + jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
