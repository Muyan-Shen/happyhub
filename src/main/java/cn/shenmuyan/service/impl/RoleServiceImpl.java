package cn.shenmuyan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.shenmuyan.bean.Role;
import cn.shenmuyan.exceptions.ParamException;
import cn.shenmuyan.mapper.RoleMapper;
import cn.shenmuyan.mapper.RolePermissionMappingMapper;
import cn.shenmuyan.service.RoleService;
import cn.shenmuyan.vo.RoleInsertVO;
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
    public boolean add(RoleInsertVO role) {
        Role role1 = BeanUtil.copyProperties(role, Role.class);
        Role role2 = roleMapper.selectByCode(role1.getCode());
        if (role2 != null){
            throw new ParamException(400,"角色编码已存在");
        }
        return roleMapper.insertSelective(role1);
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

    @Override
    public List<String> findAllRole() {
        return roleMapper.selectAllRole();
    }
}