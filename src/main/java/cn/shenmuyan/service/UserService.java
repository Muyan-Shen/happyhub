package cn.shenmuyan.service;

import cn.shenmuyan.bean.Users;

/**
 *
 * @className: UserService
 * @author: 叶宝谦
 * @date: 2023/12/04 14:58
 **/
public interface UserService {
    Users findById(Integer id);
}
