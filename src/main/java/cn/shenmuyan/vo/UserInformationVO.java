package cn.shenmuyan.vo;

import lombok.Data;

/**
 * 用户详细信息视图
 *
 * @Title: UserInformationVO
 * @Author ShenMuyan
 * @Package cn.shenmuyan.vo
 * @Date 2023/12/13 10:50
 */
@Data
public class UserInformationVO {
    /**
     *
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户联系方式
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像地址
     */
    private String picPath;
}
