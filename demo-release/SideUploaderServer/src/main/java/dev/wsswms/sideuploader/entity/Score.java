package dev.wsswms.sideuploader.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yin
 * @className: Score
 * @packageName: dev.wsswms.model
 * @description:
 * @data: 2020/4/30 19:44
 **/
@Data
public class Score {
    @JSONField(ordinal = 1)
    private String url;
    @JSONField(ordinal = 2)
    private List <Stu> students = new ArrayList<Stu>();

    @Data
    public static class Stu {
        private String name;
        private String score;
    }
}
