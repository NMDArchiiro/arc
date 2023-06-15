package com.arc.app.request.report;

import com.arc.app.entity.report.ICReport;
import com.arc.app.entity.report.ICReportContent;
import com.arc.app.entity.report.MDReport;
import com.arc.app.entity.report.MDReportContent;
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
public class ICReportRequest {
    private Long id;
    private List<ICReportContentRequest> contents;

    public ICReportRequest(ICReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (ICReportContent item : entity.getContents()) {
                    this.contents.add(new ICReportContentRequest(item));
                }
            }
        }
    }

    public List<ICReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(ICReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
