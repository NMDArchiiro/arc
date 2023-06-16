package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_cron_task_Expression",uniqueConstraints = { @UniqueConstraint(columnNames = { "type", "is_quarter","quarter" })})
@Getter
@Setter
public class CronTaskExpression extends BaseEntity {
    @Column(name = "is_quarter")
    private Boolean hasQuarter;
    @Column(name = "minus_year")
    private Integer minusYear;
    @Column(name = "quarter")
    private Integer quarter;
    @Column(name = "type")
    private Integer type;
    @Column(name = "second_string")
    private String second;
    @Column(name="minute_string")
    private String minute;
    @Column(name = "hour_string")
    private String hour;
    @Column(name="day_of_month")
    private String dayOfMonth;
    @Column(name = "month_string")
    private String month;
    @Column(name = "day_of_week")
    private String dayOfWeek;
    @Column(name = "cron_string")
    private String cronString;
    @Column(name="description")
    private String description;
}
