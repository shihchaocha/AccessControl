package org.example.pdp.controllers;

import org.example.pdp.filters.DefaultAccessControlFilter;
import org.example.models.AccessRequest;
import org.example.models.AccessResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DecisionController {

    private DefaultAccessControlFilter accessControlFilter;

    @PostMapping(
            value = "/decision", consumes = "application/json", produces = "application/json")
    public AccessResponse createPerson(@RequestBody AccessRequest accessRequest) {
        //列出相關的 Filter

        //查詢 Filter 要的資料


        //查詢資料

        //

        return new AccessResponse(accessRequest.getRequestID(), 0, "under construction");
    }
}
