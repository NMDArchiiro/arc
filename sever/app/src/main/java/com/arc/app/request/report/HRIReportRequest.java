package com.arc.app.request.report;

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
public class HRIReportRequest {
    private List<HRIReportContentRequest> contents;

    public HRIReportRequest(HRIReport entity) {
        if(entity != null) {
            if (entity.getContents() != null && entity.getContents().size() > 0) {
                this.contents = new ArrayList<>();
                for (HRIReportContent hriReportContent : entity.getContents()) {
                    this.contents.add(new HRIReportContentRequest(hriReportContent));
                }
            }
        }
    }

    public List<HRIReportContentRequest> getContents() {
        if(!CollectionUtils.isEmpty(contents)) {
            this.contents = this.contents.stream().sorted(Comparator.comparing(HRIReportContentRequest::getIndexNumber,
                    Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
            return contents;
        }
        return contents;
    }

}
