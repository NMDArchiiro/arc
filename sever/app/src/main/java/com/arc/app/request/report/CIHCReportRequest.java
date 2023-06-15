package com.arc.app.request.report;

import com.arc.app.entity.report.ARVReport;
import com.arc.app.entity.report.ARVReportContent;
import com.arc.app.entity.report.CIHCReport;
import com.arc.app.entity.report.CIHCReportContent;
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
public class CIHCReportRequest {
    private Long id;
    private List<CIHCReportContentRequest> contents;

    public CIHCReportRequest(CIHCReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (CIHCReportContent item : entity.getContents()) {
                    this.contents.add(new CIHCReportContentRequest(item));
                }
            }
        }
    }

    public List<CIHCReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(CIHCReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
