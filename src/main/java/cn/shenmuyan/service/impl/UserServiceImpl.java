package cn.shenmuyan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.bean.UserInformation;
import cn.shenmuyan.bean.UserRoleMapping;
import cn.shenmuyan.mapper.OrdersMapper;
import cn.shenmuyan.mapper.UserInformationMapper;
import cn.shenmuyan.mapper.UserMapper;
import cn.shenmuyan.mapper.UserRoleMappingMapper;
import cn.shenmuyan.service.UserService;
import cn.shenmuyan.vo.UserInformationVO;
import cn.shenmuyan.vo.UserInsertVO;
import cn.shenmuyan.vo.UserWhereVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户接口实现类
 * @Title: UserServiceImpl
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service.Impl
 * @Date 2023/12/4 15:35
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMappingMapper userRoleMappingMapper;

    @Resource
    private UserInformationMapper userInformationMapper;
    @Override
    public List<User> findAll(UserWhereVO where) {
        return userMapper.selectAll(where);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByUsernameAndPassword(String username, String passwordHash) {
        User user = userMapper.selectByUsernameAndPasswordHash(username,MD5.create().digestHex(passwordHash));
        if(user == null){
            return null;
        }
        user.setPasswordHash(null);
        return user;
    }

    @Override
    public void add(UserInsertVO user) {
        //将UserInsertVO对象中的属性值拷贝到User对象中
        User user1 = BeanUtil.copyProperties(user, User.class);
        user1.setPasswordHash(MD5.create().digestHex(user1.getPasswordHash()));
        int i = userMapper.insertSelective(user1);
        if (i>0){
            User user2 = userMapper.selectByUsernameAndPasswordHash(user1.getUsername(), user1.getPasswordHash());
            System.out.println(user2);
            userRoleMappingMapper.insertBatch(user2.getId(),new Integer[]{1002});
        }
    }
    @Override
    public Integer add2(UserInsertVO user) {
        //将UserInsertVO对象中的属性值拷贝到User对象中
        User user1 = BeanUtil.copyProperties(user, User.class);
        user1.setPasswordHash(MD5.create().digestHex(user1.getPasswordHash()));
        int i = userMapper.insertSelective(user1);
        if (i>0){
            User user2 = userMapper.selectByUsernameAndPasswordHash(user1.getUsername(), user1.getPasswordHash());
            return user2.getId();
        }
        return -1;
    }

    @Override
    @Transactional  //开启事务
    public void associationRole(int userId, Integer[] roleIds) {
        //先删除之前的关联
        userRoleMappingMapper.deleteByUserId(userId);
        //再添加新的关联
        userRoleMappingMapper.insertBatch(userId,roleIds);
    }

    /**
     * @param loginId
     * @return
     */
    @Override
    public UserInformation findUserInformationByLoginId(Integer loginId) {
        UserInformation userInformation = userInformationMapper.selectByUserId(loginId);
        return userInformation;
    }

    /**
     * @param userId
     * @param userInformationVO
     * @return
     */
    @Override

    public boolean updateInformationByUserId(Integer userId, UserInformationVO userInformationVO) {
        UserInformation userInformation = BeanUtil.copyProperties(userInformationVO, UserInformation.class);
        userInformation.setUserId(userId);
        return userInformationMapper.updateByUserId(userInformation);
    }

    /**
     * @param userInformation
     */
    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
