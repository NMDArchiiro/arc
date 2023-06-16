package com.arc.app.rest;

import com.arc.app.request.report.HIVReportRequest;
import com.arc.app.request.search.HIVReportSearchRequest;
import com.arc.app.service.report.HIVReportService;
import com.arc.app.utils.enums.InputStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * author: NMDuc
 **/
@RestController
@RequestMapping("/hiv-report")
public class RestHIVReportController {
    @Resource
    private HIVReportService hivReportService;

    @GetMapping("/{id}")
    public ResponseEntity<HIVReportRequest> getById(@PathVariable("id") Long id) {
        HIVReportRequest result = hivReportService.getHIVReport(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/exist-quarter")
    public ResponseEntity<HIVReportRequest> hivReportExistByQuarter(@RequestBody HIVReportRequest request) {
        HIVReportRequest result = hivReportService.exitsQuarter(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/exist-year")
    public ResponseEntity<HIVReportRequest> hivReportExistByYear(@RequestBody HIVReportRequest request) {
        HIVReportRequest result = hivReportService.exitsYear(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-quarter-commune")
    public ResponseEntity<HIVReportRequest> createQuarterCommune() {
        HIVReportRequest result = hivReportService.createQuarterCommune();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-quarter-district")
    public ResponseEntity<HIVReportRequest> createQuarterDistrict() {
        HIVReportRequest result = hivReportService.createQuarterDistrict();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-quarter-under-district")
    public ResponseEntity<HIVReportRequest> createQuarterUnderDistrict() {
        HIVReportRequest result = hivReportService.createQuarterDistrict();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-year-district")
    public ResponseEntity<HIVReportRequest> createYearDistrict() {
        HIVReportRequest result = hivReportService.createYearDistrict();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-year-under-district")
    public ResponseEntity<HIVReportRequest> createYearUnderDistrict() {
        HIVReportRequest result = hivReportService.createYearDistrict();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-quarter-province")
    public ResponseEntity<HIVReportRequest> createQuarterProvince() {
        HIVReportRequest result = hivReportService.createQuarterProvince();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-quarter-under-province")
    public ResponseEntity<HIVReportRequest> createQuarterUnderProvince() {
        HIVReportRequest result = hivReportService.createQuarterProvince();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-year-province")
    public ResponseEntity<HIVReportRequest> createYearProvince() {
        HIVReportRequest result = hivReportService.createYearProvince();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-year-under-province")
    public ResponseEntity<HIVReportRequest> createYearUnderProvince() {
        HIVReportRequest result = hivReportService.createYearProvince();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public ResponseEntity<HIVReportRequest> saveHIVReport(@RequestBody HIVReportRequest request) {
        HIVReportRequest result = hivReportService.saveHIVReport(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/change-status-complete/{id}")
    public HIVReportRequest changeStatusComplete(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.COMPLETE.getKey(), request);
    }

    @PostMapping("/change-status-confirm/{id}")
    public HIVReportRequest changeStatusConfirm(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.SAME_CONFIRM.getKey(), request);
    }

    @PostMapping("/change-status-no-confirm/{id}")
    public HIVReportRequest changeStatusNoConfirm(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.SAME_NO_CONFIRM.getKey(), request);
    }

    @PostMapping("/change-status-send/{id}")
    public HIVReportRequest changeStatusSend(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.SEND_SUPERIORS.getKey(), request);
    }

    @PostMapping("/change-status-reject/{id}")
    public HIVReportRequest changeStatusReject(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.REQUEST_EDIT.getKey(), request);
    }

    @PostMapping("/change-status-lock/{id}")
    public HIVReportRequest changeStatusLock(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.LOCK.getKey(), request);
    }

    @PostMapping("/change-status-unlock/{id}")
    public HIVReportRequest changeStatusUnlock(@PathVariable("id") Long id, @RequestBody HIVReportRequest request) {
        return hivReportService.changeStatus(id, InputStatusEnum.UN_LOCK.getKey(), request);
    }

    @PostMapping("/page-quarter")
    public Page<HIVReportRequest> pageQuarter(@RequestBody HIVReportSearchRequest request) {
        return hivReportService.pagingQuarter(request);
    }

    @PostMapping("/page-year")
    public Page<HIVReportRequest> pageYear(@RequestBody HIVReportSearchRequest request) {
        return hivReportService.pagingYear(request);
    }
}
