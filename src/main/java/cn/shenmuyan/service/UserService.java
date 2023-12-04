package cn.shenmuyan.service;

import cn.shenmuyan.bean.User;
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


@Service
public interface UserService {

    List<User> findAll(UserWhereVO UserWhereVO);

    User findById(Integer id);

    User findByUsernameAndPassword(String Username, String password);

    void add(UserInsertVO account);

    void associationRole(int loginIdAsInt, Integer[] roleIds);
}
