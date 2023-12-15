package cn.shenmuyan.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: NotificationWhereVO
 * @author: 熊浩
 * @date: 2023/12/13 16:28
 * @version: 1.0
 */
@Data
public class NotificationWhereVO {
    private Integer notificationId;
    private Integer userId;
    private String title;
    private String message;
    private Object status;
    private LocalDateTime createdAt;
}
