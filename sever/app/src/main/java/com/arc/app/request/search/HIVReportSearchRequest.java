package com.arc.app.request.search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * author: NMDuc
 **/
@Getter
@Setter
public class HIVReportSearchRequest {
    private Integer pageIndex;
    private Integer pageSize;
    private Long provinceId;
    private Long districtId;
    private Long communeId;
    private Long orgId;
    private Integer year; // nam bao cao
    private Integer quarter; // quy bao cao
    private String performer; // nguoi lap bao cao
    private Integer status; // trang thai
    private Integer type; //1 - quy xa, 2 - quy huyen, 3 - quy tinh, 4 - nam huyen, 5 - nam tinh
    private List<Long> provinceIds = new ArrayList<>();
}
