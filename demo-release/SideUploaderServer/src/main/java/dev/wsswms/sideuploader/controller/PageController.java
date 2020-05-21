package dev.wsswms.sideuploader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: yin
 * @className: PageController
 * @packageName: dev.wsswms.sideuploader.controller
 * @description: 用于页面跳转的Controller
 * @data: 2020/5/9 12:05
 **/
@Controller
public class PageController {

    @RequestMapping("/")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/sideUpload")
    public String sideUpload() {
        return "sideUpload";
    }


}
