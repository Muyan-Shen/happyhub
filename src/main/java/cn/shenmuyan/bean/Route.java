package cn.shenmuyan.bean;

import lombok.Data;

/**
 * 路由类
 *
 * @Title: Route
 * @Author ShenMuyan
 * @Package cn.shenmuyan.bean
 * @Date 2023/12/11 1:09
 */
@Data
public class Route {
    private  String path;
    private  String component;
    private  String name;
    private  String parentName;
    private  String title;

    public Route(String routePath, String routeComponent, String routeName, String routeParentName,String title) {
        this.path = routePath;
        this.component = routeComponent;
        this.name = routeName;
        this.parentName = routeParentName;
        this.title = title;
    }
}
