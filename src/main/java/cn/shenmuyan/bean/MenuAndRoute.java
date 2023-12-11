package cn.shenmuyan.bean;

import lombok.Data;
import org.springframework.util.RouteMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单和路由
 *
 * @Title: MenuAndRoute
 * @Author ShenMuyan
 * @Package cn.shenmuyan.bean
 * @Date 2023/12/10 23:20
 */


@Data
public class MenuAndRoute {
    private  List<Route> routeList = new ArrayList<Route>();

    private  List<Menu> menuTree = new ArrayList<Menu>();

    public MenuAndRoute(List<Route> routes, List<Menu> tree) {
        this.routeList = routes;
        this.menuTree = tree;
    }
}