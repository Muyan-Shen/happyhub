package cn.shenmuyan.service;

import cn.shenmuyan.bean.Menu;
import cn.shenmuyan.bean.MenuAndRoute;
import cn.shenmuyan.bean.Route;
import cn.shenmuyan.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单接口
 *
 * @Title: MenuService
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service
 * @Date 2023/12/10 22:28
 */


public interface MenuService {

    MenuAndRoute findAll();

    MenuAndRoute findByUser(String username);
}
