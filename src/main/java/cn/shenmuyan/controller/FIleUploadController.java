package cn.shenmuyan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.bean.UserInformation;
import cn.shenmuyan.config.FileSaveComponent;
import cn.shenmuyan.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RestController
public class FIleUploadController {
    @Resource
    private FileSaveComponent fileSaveComponent;
    @Resource
    private UserService userService;

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
    @PostMapping("/upload-avatar")
    //@SaCheckPermission(value = "upload::file",orRole = "admin")
    public SaResult uploadAvatar(@RequestParam("avatar") MultipartFile file,@RequestParam("userId") String userId) throws IOException {

        //保存上传的文件
        Path path = Paths.get("./happyhub_master_web01/public/user");
        file.transferTo(path.resolve(file.getOriginalFilename()));
        Path foregroundPath = Paths.get("./happyhub_master_web02/public/user");
        file.transferTo(foregroundPath.resolve(file.getOriginalFilename()));
        User user = new User();
        user.setPhotoUrl("/user/"+file.getOriginalFilename());
        user.setId(Integer.valueOf(userId));
        boolean b = userService.updateUser(user);
        if(b){
            return SaResult.ok().set("url","/user/"+file.getOriginalFilename()).setMsg("头像上传成功");
        }
        return SaResult.error("头像上传失败");
    }
    @PostMapping("/upload-event")
    //@SaCheckPermission(value = "upload::file",orRole = "admin")
    public SaResult uploadEvent(@RequestParam("photo") MultipartFile file) throws IOException {
        Path path = Paths.get("./happyhub_master_web01/public/event");
        file.transferTo(path.resolve(file.getOriginalFilename()));
        Path foregroundPath = Paths.get("./happyhub_master_web02/public/event");
        file.transferTo(foregroundPath.resolve(file.getOriginalFilename()));
        return SaResult.ok().set("url","/event/"+file.getOriginalFilename()).setMsg("图片上传成功");
    }
}
