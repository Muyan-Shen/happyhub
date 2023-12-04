package cn.shenmuyan.service;

import cn.shenmuyan.bean.User;

/**
 *
 * @className: UserService
 * @author: 叶宝谦
 * @date: 2023/12/04 14:58
 **/
public interface UserService {
    User findById(Integer id);
}
