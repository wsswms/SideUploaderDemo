package dev.wsswms.sideuploader.service;

import com.alibaba.fastjson.JSON;
import dev.wsswms.sideuploader.Util.JsonReadUtil;
import dev.wsswms.sideuploader.Util.JsonWriteUtil;
import dev.wsswms.sideuploader.entity.AllSelectors;
import dev.wsswms.sideuploader.entity.Project;
import dev.wsswms.sideuploader.entity.ResultJson;
import dev.wsswms.sideuploader.entity.Score;
import dev.wsswms.sideuploader.property.RootProperties;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: yin
 * @className: ScoreService
 * @packageName: dev.wsswms.sideuploader.service
 * @description:
 * @data: 2020/5/9 22:47
 **/
@Service
public class ScoreService {

    private final Path fileStorageLocation;

    @Autowired
    public ScoreService(RootProperties rootProperties) {
        this.fileStorageLocation = Paths.get(rootProperties.getRootDir()).toAbsolutePath().normalize();
    }

    String url = null;

    /**
     * 整合学生css选择器库的总方法
     * 必须先走一次本方法，这样url才有值
     *
     * @param projectName 项目名称
     */
    public void collector(String projectName){
        String listFilePath = String.valueOf(this.fileStorageLocation.resolve("Projects.List"));
        List<Project> tempList = JSON.parseArray(JsonReadUtil.ToString(listFilePath), Project.class);

        for (int i = 0; i < tempList.size(); i++){
            if (projectName.equals(tempList.get(i).getProjectName())){
                url = tempList.get(i).getProjectUrl();
                break;
            }
        }

        //将所有json文件路径存入list
        String path = String.valueOf(this.fileStorageLocation.resolve(projectName).resolve("thumbnails"));
        File file = new File(path);
        List<File> fileList = Arrays.stream(file.listFiles())
                .filter(x -> "json".equals(FilenameUtils.getExtension(String.valueOf(x))))
                .distinct()
                .collect(Collectors.toList());
        //总存储对象as
        AllSelectors as = new AllSelectors();
        as.setUrl(url);
        for(int i = 0; i < fileList.size(); i ++){
            //将对应路径的_thumb.json转换为Java对象
            String jsonStr = JsonReadUtil.ToString(String.valueOf(fileList.get(i)));
            ResultJson resultJson = JSON.parseObject(jsonStr, ResultJson.class);
            //判断url是否符合，符合则合并到as的selectors List中
            if(resultJson.getUrl().equals(url)
                    || resultJson.getUrl().equals(url + "/")
                    || url.equals(resultJson.getUrl() + "/")){
                as.setSelectors(Stream.of(as.getSelectors(), resultJson.getSelectors())
                        .flatMap(Collection::stream)
                        .distinct()
                        .collect(Collectors.toList())
                );
            }
        }
        //在thumbnail文件夹下保存为 AllSelectors.thumb
        String asPath = String.valueOf(this.fileStorageLocation
                .resolve(projectName)
                .resolve("thumbnails")
                .resolve("AllSelectors.thumb"));
        JsonWriteUtil.ToFile(asPath, JSON.toJSONString(as));
    }


    /**
     * 计算分数总方法，写入成绩文件中
     *
     * @param projectName 项目名称
     * @return 成绩文件的字符串
     */
    public String Calculator(String projectName){
        String folderPath = String.valueOf(this.fileStorageLocation.resolve(projectName));
        //把所有json文件路径存入list
        //位置在 项目文件夹/thumbnails
        //json格式参照ResultJson
        File file = new File(folderPath + "/thumbnails");
        List<File> fileList = Arrays.stream(file.listFiles())
                .filter(x -> "json".equals(FilenameUtils.getExtension(String.valueOf(x))))
                .distinct()
                .collect(Collectors.toList());

        //读取用于比较的总选择器库AllSelectors.thumb，转换为Java对象thumbObj
        String thumbStr = JsonReadUtil.ToString(folderPath + "/thumbnails/AllSelectors.thumb");
        AllSelectors thumbObj = JSON.parseObject(thumbStr, AllSelectors.class);

        //成绩数据存储对象sc
        Score sc = new Score();
        sc.setUrl(url);
        for (int i = 0; i < fileList.size(); i ++){
            //将对应路径的_thumb.json转换为Java对象
            String jsonStr = JsonReadUtil.ToString(String.valueOf(fileList.get(i)));
            ResultJson resultObj = JSON.parseObject(jsonStr, ResultJson.class);
            //判断url是否符合，符合则进行分数计算
            if (thumbObj.getUrl().equals(resultObj.getUrl())
                    || thumbObj.getUrl().equals(resultObj.getUrl() + "/")
                    || resultObj.getUrl().equals(thumbObj.getUrl() + "/")){
                temp t = new temp();
                //记录姓名
                t.setName(resultObj.getProjname());
                //计算分数并记录
                t.setScore(PointMatch(thumbObj, resultObj));
                sc.getStudents().add(t);
            }
        }

        //保存分数文件
        //位置是 项目文件夹/thumbnails/final.score
        String scoreStr = JSON.toJSONString(sc);
        JsonWriteUtil.ToFile(folderPath + "/thumbnails/final.score", scoreStr);
        return scoreStr;
    }

    @Data
    public static class temp extends Score.Stu {
    }

    /**
     * 计算分数
     *
     * @param thumbObj  总选择器库AllSelectors.thumb
     * @param resultObj 循环中单个学生作业ResultJson
     * @return 成绩百分比 xx.xx% 保留2位小数
     */
    public String PointMatch(AllSelectors thumbObj, ResultJson resultObj){
        //数值格式化对象，保留2位小数
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        //设定分母
        int fm = thumbObj.getSelectors().size();
        //设定分子
        //取两个Selectors List的交集，之后取size
        List<String> temp = new ArrayList<String>();
        temp.addAll(resultObj.getSelectors());
        temp.retainAll(thumbObj.getSelectors());
        int fz = temp.size();

        String point = numberFormat.format((double)fz / (double)fm * 100) + "%";
        return point;
    }




}
