package com.arc.app.rest;

import com.arc.app.request.AdminUnitRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.AdminUnitService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/admin-unit")
public class RestAdminUnitController {
    @Resource
    private AdminUnitService adminUnitService;

    @GetMapping("/{id}")
    public ResponseObject getAminUnit(@RequestParam("id") Long id) {
        return adminUnitService.find(id);
    }

    @GetMapping("/duplicate/{code}")
    public Boolean duplicateCode(@RequestParam("code") String code) {
        return adminUnitService.duplicate(code);
    }

    @PostMapping("/save")
    public ResponseObject save(@RequestBody AdminUnitRequest request) {
        return adminUnitService.save(request);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@RequestParam("id") Long id) {
        return adminUnitService.delete(id);
    }

    @GetMapping("/locked/{id}")
    public ResponseObject setLocked(@RequestParam("id") Long id) {
        return adminUnitService.setLocked(id);
    }

    @PostMapping("/get-page")
    public Page<AdminUnitRequest> getPage(@RequestBody SearchRequest request) {
        return adminUnitService.getPage(request);
    }

    @PostMapping("/import-data")
    public ResponseObject importData(@RequestParam("upload") MultipartFile file) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
            return adminUnitService.saveListData(bis);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
