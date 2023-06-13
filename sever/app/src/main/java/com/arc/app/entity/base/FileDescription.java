package com.arc.app.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity(name = "tbl_file_description")
@Getter
@Setter
public class FileDescription extends BaseEntity {
    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_size")
    private Long contentSize;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "file_path")
    private String filePath;
}
