package cn.shenmuyan.service.Impl;

import cn.shenmuyan.bean.Users;
import cn.shenmuyan.service.UsersService;
import cn.shenmuyan.vo.UsersInsertVO;
import cn.shenmuyan.vo.UsersWhereVO;

import java.util.List;

/**
 * @Title: UsersServiceImpl
 * @Author ShenMuyan
 * @Package cn.shenmuyan.service.Impl
 * @Date 2023/12/4 15:35
 * @description:
 */

public class UsersServiceImpl implements UsersService {


    /**
     * @param usersWhereVO
     * @return
     */
    @Override
    public List<Users> findAll(UsersWhereVO usersWhereVO) {
        return null;
    }

    /**
     * @param account
     */
    @Override
    public void add(UsersInsertVO account) {

    }

    /**
     * @param loginIdAsInt
     * @param roleIds
     */
    @Override
    public void associationRole(int loginIdAsInt, Integer[] roleIds) {

    }
}
