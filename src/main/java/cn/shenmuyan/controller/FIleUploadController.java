package cn.shenmuyan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.config.FileSaveComponent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上传控制器
 * @className: FIleUploadController
 * @author: 叶宝谦
 * @date: 2023/12/13 11:49
 **/
@RestController("/upload")
public class FIleUploadController {
    @Resource
    private FileSaveComponent fileSaveComponent;

    @PostMapping("/upload")
    //@SaCheckPermission(value = "upload::file",orRole = "admin")
    public SaResult upload(MultipartFile file) throws IOException {
        //保存上传的文件
        Path path = fileSaveComponent.getUploadPath().getFile().toPath();
        file.transferTo(path.resolve(file.getOriginalFilename()));
        Path foregroundPath = Paths.get("./happyhub_master_web02/public/event");
        file.transferTo(foregroundPath.resolve(file.getOriginalFilename()));
        return SaResult.ok().set("url","/public/event/"+file.getOriginalFilename());
    }
}
