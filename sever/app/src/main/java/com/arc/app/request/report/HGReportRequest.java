package com.arc.app.request.report;

import com.arc.app.entity.report.CIReport;
import com.arc.app.entity.report.CIReportContent;
import com.arc.app.entity.report.HGReport;
import com.arc.app.entity.report.HGReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class HGReportRequest {
    private Long id;
    private List<HGReportContentRequest> contents;

    public HGReportRequest(HGReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (HGReportContent item : entity.getContents()) {
                    this.contents.add(new HGReportContentRequest(item));
                }
            }
        }
    }

    public List<HGReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(HGReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
