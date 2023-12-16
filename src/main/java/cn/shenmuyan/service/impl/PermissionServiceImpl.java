package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Permission;
import cn.shenmuyan.exceptions.ParamException;
import cn.shenmuyan.mapper.PermissionMapper;
import cn.shenmuyan.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限接口实现类
 *
 * @Title: PermissionServiceImpl
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service.impl
 * @Date 2023/12/5 13:46
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> findAll(String id,String name, String password) {
        return permissionMapper.selectAll(id,name, password);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Permission permission) {
        Permission permission1 = permissionMapper.selectByCode(permission.getCode());
        if (permission1 != null){
            throw new ParamException(400,"权限编码已存在");
        }
        permissionMapper.insertSelective(permission);
    }

    @Override
    public boolean deleteById(Integer id) {
        return permissionMapper.deleteById(id);
    }

    @Override
    public boolean updateById(Permission permission) {
        return permissionMapper.updateByPrimaryKeySelective(permission);
    }

}
