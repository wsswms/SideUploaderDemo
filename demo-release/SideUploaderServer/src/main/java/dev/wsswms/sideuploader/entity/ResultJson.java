package dev.wsswms.sideuploader.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.util.*;

/**
 * @author: yin
 * @className: SideTestsF
 * @packageName: dev.wsswms
 * @description: 单个学生的选择器整合实体类
 * @data: 2020/4/26 18:06
 **/
@Data
public class ResultJson {
    @JSONField(ordinal=1)
    private String projname;  // = SourceJson.getName()
    @JSONField(ordinal=2)
    private String url; // = SourceJson.getUrl()
    @JSONField(ordinal=3)
    private List<String> selectors = new ArrayList<String>();
}
