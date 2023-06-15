package com.arc.app.request.report;

import com.arc.app.entity.report.PREPReport;
import com.arc.app.entity.report.PREPReportContent;
import com.arc.app.entity.report.RPREPReport;
import com.arc.app.entity.report.RPREPReportContent;
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
public class RPREPReportRequest {
    private Long id;
    private List<RPREPReportContentRequest> contents;

    public RPREPReportRequest(RPREPReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (RPREPReportContent item : entity.getContents()) {
                    this.contents.add(new RPREPReportContentRequest(item));
                }
            }
        }
    }

    public List<RPREPReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(RPREPReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
