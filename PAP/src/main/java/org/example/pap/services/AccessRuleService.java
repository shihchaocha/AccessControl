package org.example.pap.services;

import jakarta.annotation.PostConstruct;
import org.example.models.*;
import org.example.pap.models.*;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.locks.ReentrantLock;

import java.util.*;

@Service
public class AccessRuleService {
    // 將 AccessRule 實體轉換為 AccessRuleDTO
    private AccessRuleDTO convertToDTO(AccessRule accessRule) {
        AccessRuleDTO dto = new AccessRuleDTO();
        dto.setId(accessRule.getId());
        dto.setTarget(accessRule.getTarget());
        dto.setCriteria(convertToDTO(accessRule.getCriteria(), null));
        dto.setDecision(accessRule.getDecision());
        dto.setFilterName(accessRule.getFilterName());
        return dto;
    }

    // 將 MatchingCriteria 實體轉換為 MatchingCriteriaDTO
    private MatchingCriteriaDTO convertToDTO(MatchingCriteria criteria, MatchingCriteriaDTO parentCriteria) {
        if (criteria == null) {
            return null;
        }
        MatchingCriteriaDTO dto = new MatchingCriteriaDTO();
        dto.setAttributeDesignator(criteria.getAttributeDesignator());
        dto.setMatchOperator(criteria.getMatchOperator());
        dto.setAttributeValue(criteria.getAttributeValue());
        dto.setNotLogic(criteria.isNotLogic());
        dto.setMatchingType(criteria.getMatchingType());

        // 處理 parentCriteria
        if (criteria.getParentCriteria() != null) {
            dto.setParentCriteria(parentCriteria);
        }

        // 處理子 Criteria
        if (criteria.getSubCriteria() == null || criteria.getSubCriteria().isEmpty()) {
            dto.setSubCriteria(null);
        } else {
            List<MatchingCriteriaDTO> subCriteriaDTOs = new ArrayList<>();
            for (MatchingCriteria subCriteria : criteria.getSubCriteria()) {
                subCriteriaDTOs.add(convertToDTO(subCriteria,dto));
            }
            dto.setSubCriteria(subCriteriaDTOs);
        }



        return dto;
    }

    // 將 AccessRuleDTO 轉換為 AccessRule 實體
    private AccessRule convertToEntity(AccessRuleDTO dto) {
        AccessRule entity = new AccessRule();
        entity.setId(dto.getId());
        entity.setTarget(dto.getTarget());
        entity.setFilterName(dto.getFilterName());
        entity.setCriteria(convertToEntity(dto.getCriteria(), null));
        entity.setDecision(dto.getDecision());
        return entity;
    }

    // 將 MatchingCriteriaDTO 轉換為 MatchingCriteria 實體
    private MatchingCriteria convertToEntity(MatchingCriteriaDTO dto, MatchingCriteria parentCriteria) {
        if (dto == null) {
            return null;
        }
        MatchingCriteria entity = new MatchingCriteria();
        entity.setAttributeDesignator(dto.getAttributeDesignator());
        entity.setMatchOperator(dto.getMatchOperator());
        entity.setAttributeValue(dto.getAttributeValue());
        entity.setNotLogic(dto.isNotLogic());
        entity.setMatchingType(dto.getMatchingType());
        entity.setParentCriteria(parentCriteria);

        // 處理子 Criteria
        if (dto.getSubCriteria() != null) {
            List<MatchingCriteria> subCriteriaEntities = new ArrayList<>();
            for (MatchingCriteriaDTO subCriteriaDTO : dto.getSubCriteria()) {
                subCriteriaEntities.add(convertToEntity(subCriteriaDTO, entity));
            }
            entity.setSubCriteria(subCriteriaEntities);
        }

        return entity;
    }


    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    private AccessRuleRepository accessRuleRepository;

    @Autowired
    private MatchingCriteriaRepository matchingCriteriaRepository;

    private HashMap<Long, AccessRule> ruleHashMap = new HashMap<Long, AccessRule>();
    private DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
    private Rules rules = new Rules();

    private void loadTestData() {
        //accessRuleRepository.deleteAll();
        //matchingCriteriaRepository.deleteAll();
        MatchingCriteria criteria1 = new MatchingCriteria();
        criteria1.setAttributeDesignator("UserProfile.age");
        criteria1.setMatchOperator(">");
        criteria1.setAttributeValue("18");
        //criteria1 = matchingCriteriaRepository.save(criteria1);

        MatchingCriteria criteria2 = new MatchingCriteria();
        criteria2.setAttributeDesignator("Operation");
        criteria2.setMatchOperator("==");
        criteria2.setAttributeValue("Read");
        //criteria2 = matchingCriteriaRepository.save(criteria2);

        MatchingCriteria criteria3 = new MatchingCriteria();
        criteria3.setSubCriteria(new java.util.ArrayList<MatchingCriteria>());
        criteria3.addSubCriteria(criteria1);
        criteria3.addSubCriteria(criteria2);
        criteria3.setMatchingType(MatchingType.ALL_OF);
        //criteria3 = matchingCriteriaRepository.save(criteria3);

        MatchingCriteria criteria4 = new MatchingCriteria();
        criteria4.setAttributeDesignator("UserProfile.role");
        criteria4.setMatchOperator("==");
        criteria4.setAttributeValue("admin");
        //criteria4 = matchingCriteriaRepository.save(criteria4);

        AccessRule rule2 = new AccessRule();
        rule2.setTarget("Resource.zone=='zone1'");
        rule2.setCriteria(criteria4);  // 關聯到 criteria2
        accessRuleRepository.save(rule2);

        // 創建並保存幾筆 AccessRule 測試資料
        AccessRule rule1 = new AccessRule();
        rule1.setTarget("Resource.id==12345");
        rule1.setCriteria(criteria3);  // 關聯到 criteria1
        accessRuleRepository.save(rule1);

        System.out.println("Inserted test data into AccessRule table.");

    }


    public Mono<List<AccessRuleDTO>> findbySubjectAndResourceAsync(Subject subject, Resource resource, String filterName) {
        return Mono.fromCallable(() -> {
            lock.lock();  // 獲取鎖，保護臨界區

            try {
                List<AccessRule> allRules = new ArrayList<>();
                List<AccessRuleDTO> allRuleDTOs = new ArrayList<>();

                Facts facts = new Facts();
                if (subject != null) {
                    facts.put("Subject", subject);
                }
                if (resource != null) {
                    facts.put("Resource", resource);
                }

                // 在 Callable 中執行同步的 rulesEngine.check 操作
                Map<Rule, Boolean> check = rulesEngine.check(rules, facts);
                Iterator<Map.Entry<Rule, Boolean>> iterator = check.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<org.jeasy.rules.api.Rule, Boolean> entry = iterator.next();
                    org.jeasy.rules.api.Rule rule = entry.getKey();
                    Boolean isTriggered = entry.getValue();
                    if (isTriggered) {
                        Long key = Long.parseLong(rule.getName());
                        AccessRule accessRule = ruleHashMap.get(key);
                        if(accessRule.getFilterName().equals(filterName)) {
                            allRules.add(accessRule);
                            System.out.println("Rule '" + rule.getName() + "' is matched.");
                        }
                    }
                }

                for (AccessRule accessRule : allRules) {
                    allRuleDTOs.add(convertToDTO(accessRule));
                }

                return allRuleDTOs;
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<AccessRuleDTO>();
            }
            finally {
                lock.unlock();  // 保證釋放鎖，防止死鎖
            }
        }).subscribeOn(Schedulers.boundedElastic()); // 使用 Scheduler 將阻塞操作移動到工作線程
    }



    @PostConstruct
    public void loadAccessRules() {
        // 在應用啟動時從資料庫中加載所有 AccessRule 資料
        //loadTestData();
        //accessRuleRepository.deleteAll();
        //matchingCriteriaRepository.deleteAll();
        List<AccessRule> allRules = accessRuleRepository.findAll();
        ruleHashMap.clear();
        rules.clear();
        for(AccessRule accessRule : allRules) {
            ruleHashMap.put(accessRule.getId(), accessRule);
            MVELRule mvelRule = new MVELRule()
                    .name(accessRule.getId().toString())
                    .when(accessRule.getTarget());
            rules.register(mvelRule);
        }
        System.out.println("Loaded " + rules.size() + " AccessRules into memory.");
    }

    public Flux<AccessRuleDTO> findAll() {
        // 從 repository 中獲取所有 AccessRule 實體
        List<AccessRule> accessRules = accessRuleRepository.findAll();

        // 將 AccessRule 實體轉換為 DTO
        List<AccessRuleDTO> accessRuleDTOs = new ArrayList<>();
        for (AccessRule accessRule : accessRules) {
            accessRuleDTOs.add(convertToDTO(accessRule));
        }

        // 將 List<AccessRuleDTO> 轉換為 Flux<AccessRuleDTO> 返回
        return Flux.fromIterable(accessRuleDTOs);
    }

    public Mono<AccessRuleDTO> findById(Long id) {
        return Mono.justOrEmpty(accessRuleRepository.findById(id).map(this::convertToDTO));
    }

    public Mono<AccessRuleDTO> save(AccessRuleDTO accessRuleDTO) {
        return Mono.fromCallable(() -> {
            lock.lock(); // 獲取鎖，防止其他線程進入
            try {
                AccessRule entity = convertToEntity(accessRuleDTO);
                AccessRule savedRule = accessRuleRepository.save(entity);
                loadAccessRules(); // 更新內存中的快取
                return convertToDTO(savedRule);
            } finally {
                lock.unlock(); // 保證釋放鎖
            }
        });
    }

    // To do: 更新 Cache 可以讓她效率更好一些
    public Mono<Void> deleteById(Long id) {
        return Mono.fromRunnable(() -> {
            lock.lock(); // 使用 ReentrantLock 保護臨界區
            try {
                accessRuleRepository.deleteById(id); // 刪除數據
                loadAccessRules(); // 刪除後更新內存
            } finally {
                lock.unlock(); // 保證釋放鎖
            }
        });
    }
}
