package cn.shenmuyan.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: UserUpdateVO
 * @author: 熊浩
 * @date: 2023/12/17 22:51
 * @version: 1.0
 */
@Data
public class UserUpdateVO {
    private Integer id;

    /**
     * 用户名 非空
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String passwordHash;

    /**
     * 头像
     */
    private String photoUrl;

    /**
     * 用户创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 用户更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 是否删除 1：'deleted'，2： 'normal'
     */
    private String isDelete;
}
