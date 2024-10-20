package org.example;

import org.example.models.*;
import org.example.util.FilterEntityControllerTest;
import org.example.util.PAPAPITest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class UtilApplication {
    public static void main(String[] args) {
        SpringApplication.run(UtilApplication.class, args);
    }

    @Bean
    public WebClient webClientPDP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8090/api/pdp/filters")  // 設置基礎 URL
                .build();
    }


    @Bean
    public WebClient webClientPAP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8091/api/pap/access_rules")  // 設置基礎 URL
                .build();
    }

    private void test_addfilters() {
        // 創建並保存幾筆 FilterEntity 測試資料
        FilterEntityDTO filter1 = new FilterEntityDTO();
        filter1.setClassName("org.example.pdp.filters.DefaultAccessControlFilter");
        filter1.setOrder(0);
        filter1.setName("DefaultAccessControlFilter");

        FilterEntityDTO filtercreated = FilterEntityControllerTest.testCreateFilter(webClientPDP(), filter1);
        System.out.println("Created FilterEntity: " + filtercreated.getId());

        List<FilterEntityDTO> filterEntityDTOList = FilterEntityControllerTest.testGetAllFilters(webClientPDP());
        for(FilterEntityDTO filterEntityDTO : filterEntityDTOList) {
            System.out.println("Found FilterEntity: " + filterEntityDTO.getId()+":"+filterEntityDTO.getName());
        }
    }

    private void test_findAll() {
        // 發送 GET 請求，返回 AccessRuleDTO 的列表
        List<AccessRuleDTO> accessRuleDTOList =  PAPAPITest.testFindAll(webClientPAP());
        for(AccessRuleDTO accessRuleDTO : accessRuleDTOList) {
            System.out.println("Found AccessRuleDTO: " + accessRuleDTO.getId()+":"+accessRuleDTO.getTarget());
        }
    }

    private void test_findBySubjectAndResource() {
        // 發送 POST 請求，返回 AccessRuleDTO 的列表
        Subject subject1 = new Subject();
        Resource resource1 = new Resource();
        resource1.setZone("zone1");
        List<AccessRuleDTO> accessRuleDTOList = PAPAPITest.testFindBySubjectAndResource(webClientPAP(), subject1, resource1);
        for(AccessRuleDTO accessRuleDTO : accessRuleDTOList) {
            System.out.println("Found AccessRuleDTO: " + accessRuleDTO.getId()+":"+accessRuleDTO.getTarget());
        }
    }

    private void test_addData() {
        // 創建並保存幾筆 AccessRule 測試資料
        MatchingCriteriaDTO criteria1 = new MatchingCriteriaDTO();
        criteria1.setAttributeDesignator("UserProfile.age");
        criteria1.setMatchOperator(">");
        criteria1.setAttributeValue("18");
        criteria1.setMatchingType(MatchingType.LEAF_NODE);

        MatchingCriteriaDTO criteria2 = new MatchingCriteriaDTO();
        criteria2.setAttributeDesignator("Operation");
        criteria2.setMatchOperator("==");
        criteria2.setAttributeValue("Read");
        criteria2.setMatchingType(MatchingType.LEAF_NODE);

        MatchingCriteriaDTO criteria3 = new MatchingCriteriaDTO();
        criteria3.setSubCriteria(new java.util.ArrayList<MatchingCriteriaDTO>());
        criteria3.addSubCriteria(criteria1);
        criteria3.addSubCriteria(criteria2);
        criteria3.setMatchingType(MatchingType.ALL_OF);

        MatchingCriteriaDTO criteria4 = new MatchingCriteriaDTO();
        criteria4.setAttributeDesignator("UserProfile.role");
        criteria4.setMatchOperator("==");
        criteria4.setAttributeValue("admin");
        criteria4.setMatchingType(MatchingType.LEAF_NODE);

        AccessRuleDTO rule2 = new AccessRuleDTO();
        rule2.setTarget("Resource.zone=='zone1'");
        rule2.setCriteria(criteria4);  // 關聯到 criteria2
        PAPAPITest.testSaveAccessRule(webClientPAP(), rule2);

        // 創建並保存幾筆 AccessRule 測試資料
        AccessRuleDTO rule1 = new AccessRuleDTO();
        rule1.setTarget("Resource.id==12345");
        rule1.setCriteria(criteria3);  // 關聯到 criteria1
        PAPAPITest.testSaveAccessRule(webClientPAP(), rule1);
    }

    @Bean
    public CommandLineRunner runTest() {
        return args -> {
            test_findBySubjectAndResource();
            //test_addfilters();
        };
    }
}

