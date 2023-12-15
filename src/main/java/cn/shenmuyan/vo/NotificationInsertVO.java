package cn.shenmuyan.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @className: NotificationInsertVo
 * @author: 熊浩
 * @date: 2023/12/14 8:55
 * @version: 1.0
 */
@Data
public class NotificationInsertVO {
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message ="内容不能为空")
    private String message;
}
