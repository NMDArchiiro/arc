package com.arc.app.rest;

import com.arc.app.request.report.HIVReportRequest;
import com.arc.app.service.report.HIVReportService;
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
        HIVReportRequest result = hivReportService.getHIVReportForm(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/exist-quarter")
    public ResponseEntity<HIVReportRequest> hivReportExistByQuarter(@RequestBody HIVReportRequest dto) {
        HIVReportRequest result = hivReportService.exitsQuarter(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/exist-year")
    public ResponseEntity<HIVReportRequest> hivReportExistByYear(@RequestBody HIVReportRequest dto) {
        HIVReportRequest result = hivReportService.exitsYear(dto);
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
}
