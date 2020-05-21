package dev.wsswms.sideuploader.controller;

import dev.wsswms.sideuploader.service.ProjectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yin
 * @className: ProjectListController
 * @packageName: dev.wsswms.sideuploader.controller
 * @description:
 * @data: 2020/5/9 12:41
 **/
@RestController
@CrossOrigin(origins = "*")
public class ProjectListController {

    @Autowired
    private ProjectListService projectListService;

    @PostMapping("/getList")
    @CrossOrigin(origins = "*")
    public String getList() {
        return projectListService.getProjectNameList();
    }
}
