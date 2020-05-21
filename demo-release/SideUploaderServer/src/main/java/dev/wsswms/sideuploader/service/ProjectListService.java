package dev.wsswms.sideuploader.service;

import com.alibaba.fastjson.JSON;
import dev.wsswms.sideuploader.Util.JsonReadUtil;
import dev.wsswms.sideuploader.entity.Project;
import dev.wsswms.sideuploader.property.RootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author: yin
 * @className: ProjectListService
 * @packageName: dev.wsswms.sideuploader.service
 * @description:
 * @data: 2020/5/9 12:42
 **/
@Service
public class ProjectListService {
    private final Path projectDirLocation;
    @Autowired
    public ProjectListService(RootProperties rootProperties){
        this.projectDirLocation = Paths.get(rootProperties
                .getRootDir())
                .toAbsolutePath()
                .normalize();
    }
    public String getProjectNameList(){
        String projListPath = String.valueOf(
                this.projectDirLocation
                .resolve("Projects.List"));
        return JsonReadUtil.ToString(projListPath);
    }
}
