package com.arc.app.request.report;

import com.arc.app.entity.report.ATHReport;
import com.arc.app.entity.report.ATHReportContent;
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
public class ATHReportRequest {
    private Long id;
    private List<ATHReportContentRequest> contents;

    public ATHReportRequest(ATHReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (ATHReportContent item : entity.getContents()) {
                    this.contents.add(new ATHReportContentRequest(item));
                }
            }
        }
    }

    public List<ATHReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(ATHReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
