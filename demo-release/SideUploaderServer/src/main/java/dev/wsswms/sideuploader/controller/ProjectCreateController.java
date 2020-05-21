package dev.wsswms.sideuploader.controller;

import dev.wsswms.sideuploader.dto.CreateProjectResponse;
import dev.wsswms.sideuploader.service.ProjectCreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: yin
 * @className: ProjectCreateController
 * @packageName: dev.wsswms.sideuploader.controller
 * @description: 教师用创建项目API的controller
 * @data: 2020/5/8 15:11
 **/
@RestController
public class ProjectCreateController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectCreateController.class);

    @Autowired
    private ProjectCreateService projectCreateService;

    @PostMapping("/createProject")
    public CreateProjectResponse createProject(@RequestParam("pName") String projectName,
                                               @RequestParam("pUrl") String projectUrl){
        projectCreateService.createProjectDir(projectName, projectUrl);
        return new CreateProjectResponse(projectName, projectUrl);
    }
}










