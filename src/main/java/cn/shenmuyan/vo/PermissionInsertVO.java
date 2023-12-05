package cn.shenmuyan.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 新增权限视图
 * @Title: RoleInsertVO
 * @Author ShenMuyan
 * @Package cn.shenmuyan.vo
 * @Date 2023/11/28 22:29
 * @description:
 */
@Data
public class PermissionInsertVO {
    @NotBlank(message = "权限名不能为空")
    private String name;
    @NotBlank(message = "权限编码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_:]{4,20}$",message = "权限编码只能是4-20位的字母数字下划线")
    private String code;

    private String description;

}
