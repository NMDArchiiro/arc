package com.arc.app.rest;

import com.arc.app.request.AdminUnitRequest;
import com.arc.app.request.HealthOrganizationRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.HealthOrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/health-organization")
public class RestHealthOrganizationController {
    @Resource
    private HealthOrganizationService healthOrganizationService;

    @GetMapping("/{id}")
    public ResponseObject getHealthOrganization(@RequestParam("id") Long id) {
        return healthOrganizationService.find(id);
    }

    @GetMapping("/duplicate/{code}")
    public Boolean duplicateCode(@RequestParam("code") String code) {
        return healthOrganizationService.duplicate(code);
    }

    @PostMapping("/save")
    public ResponseObject save(@RequestBody HealthOrganizationRequest request) {
        return healthOrganizationService.save(request);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@RequestParam("id") Long id) {
        return healthOrganizationService.delete(id);
    }

    @GetMapping("/locked/{id}")
    public ResponseObject setLocked(@RequestParam("id") Long id) {
        return healthOrganizationService.setLocked(id);
    }

    @PostMapping("/get-page")
    public Page<HealthOrganizationRequest> getPage(@RequestBody SearchRequest request) {
        return healthOrganizationService.getPage(request);
    }
}
