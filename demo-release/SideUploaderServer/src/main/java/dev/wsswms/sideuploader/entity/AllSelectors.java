package dev.wsswms.sideuploader.entity;

import lombok.Data;
import java.util.*;

/**
 * @author: yin
 * @className: TargetList
 * @packageName: dev.wsswms.model
 * @description: 全部学生的选择器整合实体类
 * @data: 2020/4/29 17:54
 **/
@Data
public class AllSelectors {
    private String url;
    private List<String> selectors = new ArrayList<String>();
}