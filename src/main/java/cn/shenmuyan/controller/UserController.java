package cn.shenmuyan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Permission;
import cn.shenmuyan.bean.Role;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.service.UserService;
import cn.shenmuyan.vo.UserInsertVO;
import cn.shenmuyan.vo.UserWhereVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 账号controller
 * @Title: Account
 * @Author ShenMuyan
 * @Package cn.shenmuyan.controller
 * @Date 2023/12/4 15:17
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping
    @SaCheckPermission(value = "account::list", orRole = "admin")
    public Map<String, Object> list(UserWhereVO userWhereVO,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);//在查询之前使用,会自动的对紧接着的第一个查询进行分页
        List<User> list = userService.findAll(userWhereVO);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "success");
        map.put("data", pageInfo.getList());
        map.put("count", pageInfo.getTotal());
        return SaResult.ok()
                .setData(pageInfo.getList())
                .set("count", pageInfo.getTotal());
    }


    @PutMapping
    @SaCheckPermission(value = "account::add", orRole = "admin")
    public SaResult add(@RequestBody @Validated UserInsertVO account) {
        userService.add(account);
        return SaResult.ok();
    }

    //关联角色
    @PostMapping("/role")
    @SaCheckPermission(value = "account::role", orRole = "admin")
    public SaResult role(@RequestParam Integer[] roleIds) {
        userService.associationRole(StpUtil.getLoginIdAsInt(), roleIds);
        return SaResult.ok();
    }

    /**
     * 用户登录
     *
     * @param userWhereVO
     * @return
     */
    @PostMapping("/login")
    public SaResult login(@RequestBody @Validated UserWhereVO userWhereVO) {
        User user = userService.findByUsernameAndPassword(userWhereVO.getUsername(), userWhereVO.getPasswordHash());
        if (user == null) {
            return SaResult.error("用户名或者密码错误");
        }

        StpUtil.login(user.getId());
        user.setRoles(null);//减少数据传输量 提高性能
        return SaResult.ok().set("token",StpUtil.getTokenValue())
                .set("users",user);

    }



}
