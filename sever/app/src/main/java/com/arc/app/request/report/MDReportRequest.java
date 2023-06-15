package com.arc.app.request.report;

import com.arc.app.entity.report.MDReport;
import com.arc.app.entity.report.MDReportContent;
import com.arc.app.entity.report.POCReport;
import com.arc.app.entity.report.POCReportContent;
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
public class MDReportRequest {
    private Long id;
    private List<MDReportContentRequest> contents;
    public MDReportRequest(MDReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (MDReportContent item : entity.getContents()) {
                    this.contents.add(new MDReportContentRequest(item));
                }
            }
        }
    }

    public List<MDReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(MDReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
