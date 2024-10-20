package org.example.pap.controllers;

import org.example.models.AccessRuleDTO;
import org.example.models.Resource;
import org.example.models.SearchCriteria;
import org.example.models.Subject;
import org.example.pap.models.*;
import org.example.pap.services.AccessRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pap/access_rules")
public class AccessRuleController {

    @Autowired
    private AccessRuleService accessRuleService;

    @GetMapping
    public Flux<AccessRuleDTO> getAllAccessRules() {
        // 直接返回 Flux<AccessRuleDTO>
        return accessRuleService.findAll();
    }

    @PostMapping("/search")
    public Flux<AccessRuleDTO> findBySubjectAndResource(@RequestBody SearchCriteria requestData) {
        Subject subject = requestData.getSubject();
        Resource resource = requestData.getResource();
        String filterName = requestData.getFilterName();
        if(filterName==null || filterName.trim().length()==0)
            filterName = "default";

        // 調用新的非阻塞方法
        return accessRuleService.findbySubjectAndResourceAsync(subject, resource, filterName)
                .flatMapMany(Flux::fromIterable);  // 將 List<AccessRuleDTO> 轉換為 Flux
    }

    // 其他 WebFlux 方法
    @GetMapping("/{id}")
    public Mono<AccessRuleDTO> getAccessRuleById(@PathVariable Long id) {
        // 返回 Mono<AccessRuleDTO>，WebFlux 將自動處理非阻塞
        return accessRuleService.findById(id);
    }

    @PostMapping
    public Mono<AccessRuleDTO> saveAccessRule(@RequestBody AccessRuleDTO accessRuleDTO) {
        // 非阻塞地保存 AccessRuleDTO，並在完成後執行 loadAccessRules
        return accessRuleService.save(accessRuleDTO);
    }

    @PutMapping("/{id}")
    public Mono<AccessRuleDTO> updateAccessRule(@PathVariable Long id, @RequestBody AccessRuleDTO accessRuleDTO) {
        // 確保更新的 AccessRuleDTO 有正確的 ID，保持與 PathVariable 一致
        accessRuleDTO.setId(id);
        // 調用 Service 層的 save 方法，返回 Mono<AccessRuleDTO>
        return accessRuleService.save(accessRuleDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAccessRule(@PathVariable Long id) {
        return accessRuleService.deleteById(id);
    }
}
