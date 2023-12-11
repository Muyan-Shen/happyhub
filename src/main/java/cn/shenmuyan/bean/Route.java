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

    public Route(String routePath, String routeComponent, String routeName, String routeParentName) {
        this.path = routePath;
        this.component = routeComponent;
        this.name = routeName;
        this.parentName = routeParentName;
    }
}
