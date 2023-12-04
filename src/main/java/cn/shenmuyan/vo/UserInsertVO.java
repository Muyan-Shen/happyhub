package cn.shenmuyan.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 用户注册
 * @Title: UserInsertVO
 * @Author ShenMuyan
 * @Package cn.shenmuyan.vo
 * @Date 2023/12/4 15:32
 *
 */
@Data
public class UserInsertVO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "用户名不能为空")
    @Length(min = 1,max = 16,message = "密码长度必须在1-16位之间")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$",message = "密码必须由1-16位数字字母下划线")
    private String passwordHash;
}
