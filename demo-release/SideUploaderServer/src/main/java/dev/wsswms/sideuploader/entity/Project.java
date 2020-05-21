package dev.wsswms.sideuploader.entity;

import lombok.Data;

/**
 * @author: yin
 * @className: Project
 * @packageName: dev.wsswms.sideuploader.entity
 * @description:
 * @data: 2020/5/8 13:46
 **/
@Data
public class Project {
    private String projectName;
    private String projectUrl;
    public Project(String projectName, String projectUrl){
        this.projectName = projectName;
        this.projectUrl = projectUrl;
    }
}
