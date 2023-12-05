package cn.shenmuyan.service;

import cn.shenmuyan.bean.Permission;

import java.util.List;

/**
 * 权限接口
 *
 * @Title: PermissionService
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service
 * @Date 2023/12/5 13:46
 */

public interface PermissionService {
    List<Permission> findAll(String name, String password);
    Permission findById(Integer id);
    void add(Permission permission);
    void deleteById(Integer ... ids);

    void updateById(Permission permission);
}
