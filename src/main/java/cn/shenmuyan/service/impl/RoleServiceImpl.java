package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Role;
import cn.shenmuyan.exceptions.ParamException;
import cn.shenmuyan.mapper.RoleMapper;
import cn.shenmuyan.mapper.RolePermissionMappingMapper;
import cn.shenmuyan.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色接口实现类
 *
 * @Title: RoleServiceImpl
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service.impl
 * @Date 2023/12/5 13:48
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMappingMapper rolePermissionMappingMapper;
    @Override
    public List<Role> findAll(String name, String password) {
        return roleMapper.selectAll(name, password);
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Role role) {
        Role role1 = roleMapper.selectByCode(role.getCode());
        if (role1 != null){
            throw new ParamException(400,"角色编码已存在");
        }
        roleMapper.insertSelective(role);
    }

    @Override
    public void deleteById(Integer ... ids) {
        roleMapper.deleteByIds(ids);
    }

    @Override
    public void updateById(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Transactional
    public void associationPermission(Integer roleId, Integer[] permissionIds) {
        //删除之前的关联
        rolePermissionMappingMapper.deleteByRoleId(roleId);
        //添加新的关联
        rolePermissionMappingMapper.insertBatch(roleId, permissionIds);
    }
}