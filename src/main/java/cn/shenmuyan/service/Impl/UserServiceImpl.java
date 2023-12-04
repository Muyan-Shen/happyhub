package cn.shenmuyan.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.mapper.UserMapper;
import cn.shenmuyan.mapper.UserRoleMappingMapper;
import cn.shenmuyan.service.UserService;
import cn.shenmuyan.vo.UserInsertVO;
import cn.shenmuyan.vo.UserWhereVO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: UserServiceImpl
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service.Impl
 * @Date 2023/12/4 15:35
 * @description:
 */

public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMappingMapper userRoleMappingMapper;
    @Override
    public List<User> findAll(UserWhereVO where) {
        return userMapper.selectAll(where);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = userMapper.selectByUsernameAndPasswordHash(username, password);
        if(user == null){
            return null;
        }
        user.setPasswordHash("");
        return user;
    }

    @Override
    public void add(UserInsertVO user) {
        //将UserInsertVO对象中的属性值拷贝到User对象中
        User user1 = BeanUtil.copyProperties(user, User.class);
        userMapper.insertSelective(user1);
    }

    @Override
    @Transactional  //开启事务
    public void associationRole(int userId, Integer[] roleIds) {
        //先删除之前的关联
        userRoleMappingMapper
                .deleteByUserId(userId);
        //再添加新的关联
        userRoleMappingMapper.insertBatch(userId,roleIds);
    }
}
