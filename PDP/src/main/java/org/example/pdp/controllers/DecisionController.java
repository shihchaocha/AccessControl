package org.example.pdp.controllers;

import org.example.api.AccessRuleClientAPI;
import org.example.api.DeviceProfileClientAPI;
import org.example.api.ResourceProfileClientAPI;
import org.example.api.UserProfileClientAPI;
import org.example.models.*;
import org.example.pdp.filters.AccessControlFilter;
import org.example.pdp.filters.DefaultAccessControlFilter;
import org.example.pdp.services.FilterLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/pdp")
public class DecisionController {

    @Autowired
    private FilterLoader filterLoader;

    @Autowired
    WebClient webClientPIP;

    @Autowired
    WebClient webClientPAP;


    @PostMapping(
            value = "/decision", consumes = "application/json", produces = "application/json")
    public AccessResponse accssDecision(@RequestBody AccessRequest accessRequest) {
        //列出相關的 Filter
        List<AccessControlFilter> filters = filterLoader.getFilterList();

        //查詢 Filter 要的資料
        Subject subject = Subject.generateSubject(accessRequest.getSubjectID(), accessRequest.getContextInfo());
        Resource resource = Resource.generateResource(accessRequest.getResouceID(), accessRequest.getContextInfo());
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, String> contextInfo = accessRequest.getContextInfo();
        objectMap.putAll(contextInfo);
        objectMap.put("Operation", accessRequest.getOperation());
        try {
            UserProfileDTO userProfileDTO = UserProfileClientAPI.getUserProfileById(webClientPIP, subject.getId());
            if(userProfileDTO!=null){
                objectMap.put("UserProfile", userProfileDTO);
            }
            if(contextInfo.containsValue("DeviceId")){
                String deviceID = contextInfo.get("DeviceId");
                Long did = Long.parseLong(deviceID);
                if(deviceID!=null && deviceID.trim().length()!=0) {
                    DeviceProfileDTO deviceProfileDTO = DeviceProfileClientAPI.getDeviceProfileById(webClientPIP, did);
                    if (deviceProfileDTO != null) {
                        objectMap.put("Device", deviceProfileDTO);
                    } else {
                        objectMap.put("Device", new DeviceProfileDTO());
                    }
                }
            }
            ResourceProfileDTO resourceProfileDTO = ResourceProfileClientAPI.getResourceProfileById(webClientPIP, resource.getId());
            if(resourceProfileDTO!=null){
                objectMap.put("Resource", resourceProfileDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean allowed = false;

        //查詢資料
        for(AccessControlFilter filter : filters){
            String filtername = filter.getClass().getName();
            if(filtername==null){
                continue;
            }

            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setResource(resource);
            searchCriteria.setSubject(subject);
            searchCriteria.setFilterName(filtername);

            List<AccessRuleDTO> accessRules = null;
            try {
                accessRules = AccessRuleClientAPI.findBySubjectAndResource(webClientPAP, searchCriteria);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //把要求丟下去處理
            boolean result = filter.doFilter(objectMap, accessRules);
            if(result){
                allowed = true;
            } else {
                allowed = false;
                break;
            }
        }
        //
        if(allowed)
            return new AccessResponse(accessRequest.getRequestID(), 1, "allowed");
        else
            return new AccessResponse(accessRequest.getRequestID(), 2, "denied");
    }}
