package com.arc.app.request.report;

import com.arc.app.entity.report.MTCReport;
import com.arc.app.entity.report.MTCReportContent;
import com.arc.app.entity.report.PREPReport;
import com.arc.app.entity.report.PREPReportContent;
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
public class PREPReportRequest {
    private Long id;
    private List<PREPReportContentRequest> contents;

    public PREPReportRequest(PREPReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (PREPReportContent item : entity.getContents()) {
                    this.contents.add(new PREPReportContentRequest(item));
                }
            }
        }
    }

    public List<PREPReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(PREPReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
