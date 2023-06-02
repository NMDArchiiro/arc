package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import com.arc.app.utils.IntegerConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tbl_report_content")
@Getter
@Setter
public class ReportContent extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "index_number")
    private Integer indexNumber;

    @Column(name = "report_number")
    private Integer reportNumber; // 15 bao cao

    @Column(name = "sub_content")
    private String subContent;

    @Column(name = "can_write")
    private Boolean canWrite;

    @Column(name = "bold")
    private Boolean bold;

    @Column(name = "italics")
    private Boolean italics;

    @Convert(converter = IntegerConverter.class)
    @Column(name="sum_of_index_number")
    private List<Integer> sumOfIndexNumber;
}
