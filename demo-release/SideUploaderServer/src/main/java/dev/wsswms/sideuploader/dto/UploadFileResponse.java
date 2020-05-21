package dev.wsswms.sideuploader.dto;

import lombok.Data;

/**
 * Response响应实体类
 */
@Data
public class UploadFileResponse {
    private String fileName;
    private String pName;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String pName, String fileType, long size) {
        this.fileName = fileName;
        this.pName = pName;
        this.fileType = fileType;
        this.size = size;
    }
}
