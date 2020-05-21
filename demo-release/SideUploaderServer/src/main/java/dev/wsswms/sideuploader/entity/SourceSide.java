package dev.wsswms.sideuploader.entity;

import lombok.Data;
import java.util.*;

/**
 * @author: yin
 * @className: SideTests
 * @packageName: dev.wsswms
 * @description: side文件实体类
 * @data: 2020/4/24 18:41
 **/
@Data
public class SourceSide {
    private String name;  //需要学生填写自己的学号，用于评分
    private String url;
    private List<TestsList> tests = new ArrayList<TestsList>();

    @Data
    public static class TestsList {
        private String name;  //test case name，暂作保留
        private List<CommandsList> commands = new ArrayList<CommandsList>();
    }

    @Data
    public static class CommandsList {
        private String command;  //测试指令
        private String target;  //指令对象
    }
}
