package com.arc.app.request.report;

import com.arc.app.entity.report.HGReport;
import com.arc.app.entity.report.HGReportContent;
import com.arc.app.entity.report.HIReport;
import com.arc.app.entity.report.HIReportContent;
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
public class HIReportRequest {
    private Long id;
    private List<HIReportContentRequest> contents;

    public HIReportRequest(HIReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (HIReportContent item : entity.getContents()) {
                    this.contents.add(new HIReportContentRequest(item));
                }
            }
        }
    }

    public List<HIReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(HIReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
