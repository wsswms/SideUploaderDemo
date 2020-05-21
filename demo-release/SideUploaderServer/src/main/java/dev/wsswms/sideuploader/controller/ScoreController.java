package dev.wsswms.sideuploader.controller;

import dev.wsswms.sideuploader.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yin
 * @className: ScoreController
 * @packageName: dev.wsswms.sideuploader.controller
 * @description:
 * @data: 2020/5/9 23:10
 **/
@RestController
@CrossOrigin(origins = "*")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @PostMapping("/getScore")
    @CrossOrigin(origins = "*")
    public String getScore(@RequestParam("pName") String projectName){
        //整合选择器
        scoreService.collector(projectName);
        //统计分数
        return scoreService.Calculator(projectName);
    }
}
