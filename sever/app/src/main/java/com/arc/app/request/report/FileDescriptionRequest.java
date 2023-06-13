package com.arc.app.request.report;

import com.arc.app.entity.base.FileDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: NMDuc
 **/
@Getter
@Setter
@NoArgsConstructor
public class FileDescriptionRequest {
    private Long id;
    private String contentType;
    private Long contentSize;
    private String name;
    private String extension;
    private String filePath;

    public FileDescriptionRequest(FileDescription entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.contentType = entity.getContentType();
            this.contentSize = entity.getContentSize();
            this.name = entity.getName();
            this.extension = entity.getExtension();
            this.filePath = entity.getFilePath();
        }
    }
}
