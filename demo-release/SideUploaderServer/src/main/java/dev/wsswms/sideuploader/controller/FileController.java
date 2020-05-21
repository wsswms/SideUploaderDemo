package dev.wsswms.sideuploader.controller;

import dev.wsswms.sideuploader.dto.UploadFileResponse;
import dev.wsswms.sideuploader.service.SideFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * side文件上传Controller
 */
@RestController
@CrossOrigin(origins = "*")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private SideFileService sideFileService;

    /**
     * side文件上传API，根据选中的项目名，将文件传到响应的文件夹下
     *
     * @param request 前台传来的POST，类型为formData，包含一个参数和一个文件
     * @return UploadFileResponse响应
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public UploadFileResponse sideFileUpload(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);

        String pName = params.getParameter("pName");
        System.out.println("projectName: " + pName);
        MultipartFile file = params.getFile("file");
        String info = sideFileService.saveSideFile(file, pName);
        return new UploadFileResponse(info, pName, file.getContentType(), file.getSize());
    }
}
