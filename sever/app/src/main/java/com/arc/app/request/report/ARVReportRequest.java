package com.arc.app.request.report;

import com.arc.app.entity.report.ARVReport;
import com.arc.app.entity.report.ARVReportContent;
import com.arc.app.entity.report.ATHReport;
import com.arc.app.entity.report.ATHReportContent;
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
public class ARVReportRequest {
    private Long id;
    private List<ARVReportContentRequest> contents;

    public ARVReportRequest(ARVReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (ARVReportContent item : entity.getContents()) {
                    this.contents.add(new ARVReportContentRequest(item));
                }
            }
        }
    }

    public List<ARVReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(ARVReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }
}
