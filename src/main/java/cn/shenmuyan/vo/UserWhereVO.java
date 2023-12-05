package cn.shenmuyan.vo;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户登录视图
 * @Title: AccountWhereVO
 * @Author ShenMuyan
 * @Package cn.shenmuyan.vo
 * @Date 2023/12/4 15:25
 *
 */
@Data
@Validated
public class UserWhereVO{
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "用户名格式不正确,必须是3-15位字母数字下划线")
    private String passwordHash;
}
