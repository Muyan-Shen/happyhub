package cn.shenmuyan.service;

import cn.shenmuyan.bean.Users;
import cn.shenmuyan.vo.UsersInsertVO;
import cn.shenmuyan.vo.UsersWhereVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户接口
 * @Title: UsersService
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service
 * @Date 2023/12/4 15:34
 * @description:
 */


@Service
public interface UsersService {

    List<Users> findAll(UsersWhereVO usersWhereVO);

    void add(UsersInsertVO account);

    void associationRole(int loginIdAsInt, Integer[] roleIds);
}
