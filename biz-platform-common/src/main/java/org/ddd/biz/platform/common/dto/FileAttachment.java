package org.ddd.biz.platform.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class FileAttachment implements Serializable {
    private String fileName;
    private String fileUrl;

    public FileAttachment() {

    }

    public FileAttachment(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
