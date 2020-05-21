package dev.wsswms.sideuploader.service;

import dev.wsswms.sideuploader.Util.*;
import dev.wsswms.sideuploader.entity.Project;
import dev.wsswms.sideuploader.exception.FileException;
import dev.wsswms.sideuploader.property.RootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yin
 * @className: FolderCreate
 * @packageName: dev.wsswms.sideuploader.service
 * @description: 前台输入项目名和网址，生成对应List和文件夹
 * @data: 2020/5/8 12:54
 **/
@Service
public class ProjectCreateService {

    private final Path projectDirLocation;

    /**
     * 实例化构造函数
     *
     * @param rootProperties the root properties
     */
    @Autowired
    public ProjectCreateService(RootProperties rootProperties){
        this.projectDirLocation = Paths.get(rootProperties.getRootDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.projectDirLocation);
        } catch (Exception ex) {
            throw new FileException("无法创建根目录", ex);
        }
    }

    /**
     * 创建项目文件夹，添加至List文件中
     *
     * @param projectName 获取的项目名
     * @param projectUrl  获取的项目url
     * @return 异常info
     */
    public void createProjectDir(String projectName, String projectUrl){
        try {
            // 创建项目文件夹
            Files.createDirectory(this.projectDirLocation.resolve(projectName));
            /**
             * 在根目录下创建或写入Projects.List文件
             * projListPath List文件位置
             * 文件不存在时，新建一个json字符串写入
             * 文件存在时，读取后添加再写入
             */
            String projListPath = String.valueOf(this.projectDirLocation.resolve("Projects.List"));
            if (!new File(projListPath).exists()) {
                List<Project> projList = new ArrayList<>();
                projList.add(new Project(projectName, projectUrl));
                String projListStr = JSON.toJSONString(projList);
                JsonWriteUtil.ToFile(projListPath, projListStr);
            } else {
                String projListStr = JsonReadUtil.ToString(projListPath);
                List<Project> projList = JSON.parseArray(projListStr, Project.class);
                projList.add(new Project(projectName, projectUrl));
                String projListStrF = JSON.toJSONString(projList);
                JsonWriteUtil.ToFile(projListPath, projListStrF);
            }
        } catch (FileAlreadyExistsException ex) {
            throw new FileException("已经存在该项目，请更换项目名。", ex);
        } catch (IOException ex) {
            throw new FileException("无法创建项目目录", ex);
        }
    }
}
