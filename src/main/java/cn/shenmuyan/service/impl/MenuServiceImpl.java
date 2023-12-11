package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Menu;
import cn.shenmuyan.bean.MenuAndRoute;
import cn.shenmuyan.bean.Route;
import cn.shenmuyan.mapper.MenuMapper;
import cn.shenmuyan.service.MenuService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单接口实现类
 *
 * @Title: MenuServiceImpl
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service.impl
 * @Date 2023/12/10 23:03
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    public MenuAndRoute findAll() {
        log.info("1");
        List<Menu> list = menuMapper.findAll();
        log.info("2");
        if(list!=null){
            log.info("3");
            return menu2MenuAndRoute(list);
        }
        return  null;
    }

    public MenuAndRoute findByUser(String username) {
        List<Menu> list = menuMapper.findByUser(username);
        return menu2MenuAndRoute(list);
    }

    private MenuAndRoute menu2MenuAndRoute(List<Menu> list) {
        Map<Integer, Menu> all = new HashMap<>();
        List<Menu> tree = new ArrayList<>();
        List<Route> routes = new ArrayList<>();
        for (Menu menu : list) {
            if (menu.getPid() == 0) {
                tree.add(menu);
            }
            all.put(menu.getId(), menu);
            if (menu.getRoutePath() != null) {
                routes.add(new Route(menu.getRoutePath(), menu.getRouteComponent(), menu.getRouteName(), menu.getRouteParentName(),menu.getTitle()));
            }
        }

        for (Menu menu : list) {
            Menu parent = all.get(menu.getPid());
            if (parent != null) {
                List<Menu> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(menu);
            }
        }
        return new MenuAndRoute(routes, tree);
    }

}
