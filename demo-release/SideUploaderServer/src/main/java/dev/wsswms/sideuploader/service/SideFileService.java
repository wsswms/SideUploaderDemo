package dev.wsswms.sideuploader.service;

import com.alibaba.fastjson.JSON;
import dev.wsswms.sideuploader.entity.*;
import dev.wsswms.sideuploader.exception.FileException;
import dev.wsswms.sideuploader.property.RootProperties;
import dev.wsswms.sideuploader.Util.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: yin
 * @className: SideFileService
 * @packageName: dev.wsswms.sideuploader.service
 * @description: side脚本上传service
 * @data: 2020/5/9 16:43
 **/
@Service
public class SideFileService {
    private final Path fileStorageLocation;

    @Autowired
    public SideFileService(RootProperties rootProperties) {
        this.fileStorageLocation = Paths.get(rootProperties.getRootDir()).toAbsolutePath().normalize();
    }

    /**
     * 存储学生上传的side文件到项目名文件夹内
     *
     * @param file        side文件
     * @param projectName 项目名
     * @return the string
     */
    public String saveSideFile(MultipartFile file, String projectName){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileException("抱歉！文件名存在非法字母 " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(projectName).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            //调用文件预处理模块
            StuJsonTrans(String.valueOf(this.fileStorageLocation.resolve(projectName)));

            return projectName + "/" + fileName;
        } catch (IOException ex) {
            throw new FileException("无法存储文件 " + fileName + "。 请重试一次。", ex);
        }
    }

    /**
     * 学生脚本预处理的总方法
     *
     * @param folderPath 本次项目的文件夹路径
     */
    public static void StuJsonTrans(String folderPath){
        //将所有side文件存入list
        File sides = new File(folderPath);
        List<File> sidesList = Arrays.stream(sides.listFiles())
                .filter(x -> "side".equals(
                        FilenameUtils.getExtension(String.valueOf(x))))
                .distinct()
                .collect(Collectors.toList());
        File mkdir = new File(folderPath + "/thumbnails");
        mkdir.mkdir();
        System.out.println(sidesList);

        //遍历文件进行转换
        for (int i = 0; i < sidesList.size(); i ++){
            //.side -> side String
            String jsonStr = JsonReadUtil.ToString(
                    String.valueOf(sidesList.get(i)));
            //side String -> ResultJson Object
            ResultJson outJson = SideTrans.toResultObject(jsonStr);
            //ResultJson Object -> json String
            String outJsonStr = JSON.toJSONString(outJson);
            String savePath = folderPath
                    + "/thumbnails/"
                    + outJson.getProjname()
                    + "_thumb.json";
            //json String -> .json
            JsonWriteUtil.ToFile(savePath,outJsonStr);
        }
    }

    /**
     * The type Json trans.
     *
     * @author: yin
     * @className: JsonTrans
     * @packageName: dev.wsswms.service
     * @description: 整理学生的side脚本，精简为新的ResultJson实体
     * @data: 2020 /4/28 20:15
     */
    public static class SideTrans {

        /**
         * 将输入的.side脚本去重后转换为Java对象
         *
         * @param fileString 文件String，由ReadUtils得到
         * @return ResultJson对象
         */
        public static ResultJson toResultObject(String fileString){
            // 将Json字符串反序列化为Java对象
            SourceSide sourceSide = JSON.parseObject(fileString, SourceSide.class);

            //装配新的实体result用于存放url和css选择器
            ResultJson result = new ResultJson();
            result.setProjname(sourceSide.getName());
            result.setUrl(sourceSide.getUrl());
            result.setSelectors(sourceSide.getTests().get(0).getCommands().stream()
                    .filter(x -> "click".equals(x.getCommand()))
                    .map(x -> x.getTarget())
                    .distinct()
                    .collect(Collectors.toList())
            );
            //转换为String并返回
            //System.out.println(result);
            return result;

        }
    }



}
