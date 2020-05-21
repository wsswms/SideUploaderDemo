package dev.wsswms.sideuploader.dto;

import lombok.Data;

/**
 * @author: yin
 * @className: CreateProjectResponse
 * @packageName: dev.wsswms.sideuploader.dto
 * @description:
 * @data: 2020/5/8 15:18
 **/
@Data
public class CreateProjectResponse {
    private String projectName;
    private String projectUrl;

    public CreateProjectResponse(String projectName, String projectUrl) {
        this.projectName = projectName;
        this.projectUrl = projectUrl;
    }
}
