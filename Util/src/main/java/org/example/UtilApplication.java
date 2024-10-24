package org.example;

import org.example.api.PDPRequestClientAPI;
import org.example.api.ResourceProfileClientAPI;
import org.example.api.UserProfileClientAPI;
import org.example.models.*;
import org.example.util.FilterEntityControllerTest;
import org.example.util.PAPAPITest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class UtilApplication {
    public static void main(String[] args) {
        SpringApplication.run(UtilApplication.class, args);
    }

    @Bean
    public WebClient webClientPDP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8090/api/pdp")  // 設置基礎 URL
                .build();
    }

    @Bean
    public WebClient webClientPIP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8092")  // 設置基礎 URL
                .build();
    }

    @Bean
    public WebClient webClientPAP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8091/api/pap/access_rules")  // 設置基礎 URL
                .build();
    }

    private void testResourceAdd() {
        try {
            // 創建並保存幾筆 ResourceProfile 測試資料
            ResourceProfileDTO resourceProfile1 = new ResourceProfileDTO();
            resourceProfile1.setZone("zone1");
            resourceProfile1.setType("server");
            resourceProfile1.setName("resource1");

            ResourceProfileDTO resourceProfilecreated = ResourceProfileClientAPI.createResourceProfile(webClientPIP(), resourceProfile1);
            System.out.println("Created ResourceProfile: " + resourceProfilecreated.getId());

            ResourceProfileDTO resourceProfile2 = new ResourceProfileDTO();
            resourceProfile2.setZone("zone2");
            resourceProfile2.setType("workstation");
            resourceProfile2.setName("resource2");

            resourceProfilecreated = ResourceProfileClientAPI.createResourceProfile(webClientPIP(), resourceProfile2);
            System.out.println("Created ResourceProfile: " + resourceProfilecreated.getId());

            List<ResourceProfileDTO> resourceProfileDTOList = ResourceProfileClientAPI.getAllResourceProfiles(webClientPIP());
            for (ResourceProfileDTO resourceProfileDTO : resourceProfileDTOList) {
                System.out.println("Found ResourceProfile: " + resourceProfileDTO.getId() + ":" + resourceProfileDTO.getZone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testPDP_request()  {
        try {
            AccessRequest accessRequest = new AccessRequest();
            accessRequest.setOperation("Read");
            accessRequest.setSubjectID("1");
            accessRequest.setRequestID("REQ12345");
            accessRequest.setResouceID("1");
            accessRequest.setContextInfo(new java.util.HashMap<String, String>() {{
                put("DeviceId", "1");
            }});
            AccessResponse accessResponse = PDPRequestClientAPI.sendAccessRequest(webClientPDP(), accessRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test_userprofile() throws Exception {
        // 創建並保存幾筆 UserProfile 測試資料
        UserProfileDTO userProfile1 = new UserProfileDTO();
        userProfile1.setUsername("Alice");
        userProfile1.setRole("admin");
        userProfile1.setEmail("alice@example.org");
        userProfile1.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userProfile1.setSecurityLevel(5);

        UserProfileDTO userProfilecreated = UserProfileClientAPI.createUserProfile(webClientPIP(), userProfile1);
        System.out.println("Created UserProfile: " + userProfilecreated.getId());

        UserProfileDTO userProfile2 = new UserProfileDTO();
        userProfile2.setUsername("Bob");
        userProfile2.setRole("user");
        userProfile2.setEmail("bob@example.org");
        userProfile2.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userProfile2.setSecurityLevel(4);

        userProfilecreated = UserProfileClientAPI.createUserProfile(webClientPIP(), userProfile2);
        System.out.println("Created UserProfile: " + userProfilecreated.getId());

        List<UserProfileDTO> userProfileDTOList = UserProfileClientAPI.getAllUserProfiles(webClientPIP());
        for(UserProfileDTO userProfileDTO : userProfileDTOList) {
            System.out.println("Found UserProfile: " + userProfileDTO.getId()+":"+userProfileDTO.getUsername());
        }
    }

    private void test_addfilters() {
        // 創建並保存幾筆 FilterEntity 測試資料
        FilterEntityDTO filter1 = new FilterEntityDTO();
        filter1.setClassName("org.example.pdp.filters.DefaultAccessControlFilter");
        filter1.setOrder(0);
        filter1.setName("default");

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
        resource1.setZone("zone2");
        List<AccessRuleDTO> accessRuleDTOList = PAPAPITest.testFindBySubjectAndResource(webClientPAP(), subject1, resource1);
        for(AccessRuleDTO accessRuleDTO : accessRuleDTOList) {
            System.out.println("Found AccessRuleDTO: " + accessRuleDTO.getId()+":"+accessRuleDTO.getTarget());
        }
    }

    //add access rules
    private void test_addData() {
        // 創建並保存幾筆 AccessRule 測試資料
        MatchingCriteriaDTO criteria1 = new MatchingCriteriaDTO();
        criteria1.setAttributeDesignator("UserProfile.risk_5");
        criteria1.setMatchOperator("==");
        criteria1.setAttributeValue("'L'");
        criteria1.setMatchingType(MatchingType.LEAF_NODE);

        MatchingCriteriaDTO criteria2 = new MatchingCriteriaDTO();
        criteria2.setAttributeDesignator("Operation");
        criteria2.setMatchOperator("==");
        criteria2.setAttributeValue("'Read'");
        criteria2.setMatchingType(MatchingType.LEAF_NODE);

        MatchingCriteriaDTO criteria3 = new MatchingCriteriaDTO();
        criteria3.setSubCriteria(new java.util.ArrayList<MatchingCriteriaDTO>());
        criteria3.addSubCriteria(criteria1);
        criteria3.addSubCriteria(criteria2);
        criteria3.setMatchingType(MatchingType.ALL_OF);

        MatchingCriteriaDTO criteria4 = new MatchingCriteriaDTO();
        criteria4.setAttributeDesignator("UserProfile.role");
        criteria4.setMatchOperator("==");
        criteria4.setAttributeValue("'admin'");
        criteria4.setMatchingType(MatchingType.LEAF_NODE);

        AccessRuleDTO rule2 = new AccessRuleDTO();
        rule2.setTarget("Resource.zone=='zone1'");
        rule2.setCriteria(criteria4);  // 關聯到 criteria2
        rule2.setDecision(AccessRuleDTO.ALLOW);
        PAPAPITest.testSaveAccessRule(webClientPAP(), rule2);


        // 創建並保存幾筆 AccessRule 測試資料
        AccessRuleDTO rule1 = new AccessRuleDTO();
        rule1.setTarget("Resource.zone=='zone2'");
        rule1.setCriteria(criteria3);  // 關聯到 criteria1
        rule2.setDecision(AccessRuleDTO.ALLOW);
        PAPAPITest.testSaveAccessRule(webClientPAP(), rule1);
    }

    @Bean
    public CommandLineRunner runTest() {
        return args -> {
            try {
                testPDP_request();
                //test_userprofile();
                //test_addData();
                //testResourceAdd();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //test_findBySubjectAndResource();
            //test_addfilters();
        };
    }
}

