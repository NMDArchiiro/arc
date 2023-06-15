package com.arc.app.request.report;

import com.arc.app.entity.report.RPREPReport;
import com.arc.app.entity.report.RPREPReportContent;
import com.arc.app.entity.report.SPReport;
import com.arc.app.entity.report.SPReportContent;
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
public class SPReportRequest {
    private Long id;
    private List<SPReportContentRequest> contents;

    public SPReportRequest(SPReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (SPReportContent item : entity.getContents()) {
                    this.contents.add(new SPReportContentRequest(item));
                }
            }
        }
    }

    public List<SPReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(SPReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
