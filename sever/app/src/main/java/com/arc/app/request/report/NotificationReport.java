package com.arc.app.request.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: NMDuc
 **/
@Getter
@Setter
@NoArgsConstructor
public class NotificationReport {
    private Long id;
    private String name;
    private Integer year;
    private Integer quarter;
    private String note;
    private String type;
    private String reporter;

    public NotificationReport(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public NotificationReport(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
