package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Notifications;
import cn.shenmuyan.bean.Role;
import cn.shenmuyan.service.NotificationService;
import cn.shenmuyan.service.RoleService;
import cn.shenmuyan.service.UserService;
import cn.shenmuyan.vo.NotificationInsertVO;
import cn.shenmuyan.vo.NotificationUpdateVO;
import cn.shenmuyan.vo.NotificationWhereVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: NotificationController
 * @author: 熊浩
 * @date: 2023/12/13 16:14
 * @version: 1.0
 */
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Resource
    private NotificationService notificationService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @GetMapping("/getAll")
    public SaResult getAll(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        NotificationWhereVO notificationWhereVO=new NotificationWhereVO();
        if (!keyword.isEmpty()){
            notificationWhereVO.setTitle(keyword);
            notificationWhereVO.setMessage(keyword);
        }
        List<Notifications> notifications = notificationService.selectAll(notificationWhereVO);
        return SaResult.ok().setData(notifications);
    }

    @PostMapping("/addNotification")
    public SaResult addNotification(@RequestBody @Validated NotificationInsertVO vo){
        notificationService.add(vo);
        return SaResult.ok("创建成功");
    }

    @PostMapping("/addNotificationByRole")
    public SaResult addNotificationByRole(@RequestParam(value = "role") String role,@RequestBody @Validated NotificationInsertVO vo){
        notificationService.addByRole(role, vo.getTitle(), vo.getMessage());
        return SaResult.ok("创建成功");
    }

    @GetMapping("/deleteGroup")
    public SaResult deleteGroup(@RequestParam List<Integer> ids){
        for (Integer id : ids) {
            notificationService.deleteById(id);
        }
        return SaResult.ok("删除成功");
    }

    @GetMapping("/deleteOne")
    public SaResult deleteOne(@RequestParam Integer id){
        notificationService.deleteById(id);
        return SaResult.ok("删除成功");
    }
    @PostMapping("/updateNotification")
    public SaResult updateNotification(@RequestBody @Validated NotificationUpdateVO vo){
        System.out.println(vo.toString());
        notificationService.updateById(vo);
        return SaResult.ok("修改成功");
    }

    @GetMapping("/getAllUserId")
    public SaResult getAllUserId(){
        List<Integer> userIds = userService.findAllId();
        return SaResult.ok().set("userIds",userIds);
    }

    @GetMapping("/getAllRole")
    public SaResult getAllRole(){
        List<String> roles=roleService.findAllRole();
        return SaResult.ok().set("roles",roles);
    }
}
