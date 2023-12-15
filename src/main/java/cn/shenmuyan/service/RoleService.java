package cn.shenmuyan.service;

import cn.shenmuyan.bean.Role;
import cn.shenmuyan.vo.RoleInsertVO;

import java.util.List;

/**
 * 角色接口
 * @Title: RoleService
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service
 * @Date 2023/12/5 13:44
 */

public interface RoleService {
    List<Role> findAll(String name, String password);
    Role findById(Integer id);
    boolean add(RoleInsertVO role);
    void deleteById(Integer ... ids);

    void updateById(Role role);

    void associationPermission(Integer roleId, Integer[] permissionIds);
}

