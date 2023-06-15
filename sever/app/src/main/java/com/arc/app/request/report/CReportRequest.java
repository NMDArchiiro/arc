package com.arc.app.request.report;

import com.arc.app.entity.report.CReport;
import com.arc.app.entity.report.CReportContent;
import com.arc.app.entity.report.HRIReport;
import com.arc.app.entity.report.HRIReportContent;
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
public class CReportRequest {
    private Long id;
    private List<CReportContentRequest> contents;

    public CReportRequest(CReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (CReportContent item : entity.getContents()) {
                    this.contents.add(new CReportContentRequest(item));
                }
            }
        }
    }

    public List<CReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(CReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
