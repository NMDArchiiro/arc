package com.arc.app.request.report;

import com.arc.app.entity.report.CronTaskExpression;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class CronTaskExpressionRequest {
    private Long id;
    private Boolean hasQuarter;
    private Integer minusYear;
    private Integer quarter;
    private Integer type;
    private String second;
    private String minute;
    private String hour;
    private String dayOfMonth;
    private String month;
    private String dayOfWeek;
    private String cronString;
    private String description;
    private Boolean voided;

    public CronTaskExpressionRequest(CronTaskExpression entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.hasQuarter = entity.getHasQuarter();
            this.minusYear = entity.getMinusYear();
            this.quarter = entity.getQuarter();
            this.type = entity.getType();
            this.second = entity.getSecond();
            this.minute = entity.getMinute();
            this.hour = entity.getHour();
            this.dayOfMonth = entity.getDayOfMonth();
            this.month = entity.getMonth();
            this.dayOfWeek = entity.getDayOfWeek();
            this.cronString = entity.getCronString();
            this.description = entity.getDescription();
            this.voided = entity.getVoided();
        }
    }

    public CronTaskExpressionRequest(Boolean hasQuarter, Integer minusYear, Integer quarter, Integer type,
                                     String second, String minute, String hour, String dayOfMonth, String month, String dayOfWeek,
                                     String cronString, String description) {
        this.hasQuarter = hasQuarter;
        this.minusYear = minusYear;
        this.quarter = quarter;
        this.type = type;
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.cronString = cronString;
        this.description = description;
    }
}
