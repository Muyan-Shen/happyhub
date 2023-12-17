package cn.shenmuyan.service;

import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.bean.UserInformation;
import cn.shenmuyan.vo.UserInformationVO;
import cn.shenmuyan.vo.UserInsertVO;
import cn.shenmuyan.vo.UserWhereVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户接口
 * @Title: UserService
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service
 * @Date 2023/12/4 15:34
 * @description:
 */



public interface UserService {

    List<User> findAll(UserWhereVO UserWhereVO);

    User findById(Integer id);

    User findByUsernameAndPassword(String Username, String passwordHash);

    void add(UserInsertVO userInsertVO);

    Integer add2(UserInsertVO userInsertVO);

    void associationRole(int loginIdAsInt, Integer[] roleIds);

    UserInformation findUserInformationByLoginId(Integer loginId);


    boolean updateInformationByUserId(Integer userId, UserInformationVO userInformation);

    boolean updateUser(User user);

    List<Integer> findAllId();
}
