package cn.shenmuyan.controller;

import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.MenuAndRoute;
import cn.shenmuyan.service.MenuService;
import cn.shenmuyan.service.impl.MenuServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 菜单Controller
 * @Title: MenuController
 * @Author ShenMuyan
 * @Package cn.shenmuyan.controller
 * @Date 2023/12/10 22:08
 */


@RestController
@Slf4j
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menu")
    public SaResult findAll(){
        MenuAndRoute menuAndRoute = menuService.findAll();
        return SaResult.ok().setData(menuAndRoute);
    }

    @GetMapping("/menu/{username}")
    public SaResult findBy(@PathVariable String username){
        return SaResult.ok().setData(menuService.findByUser(username));
    }

}
