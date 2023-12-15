package cn.shenmuyan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: NotificationUpdateVO
 * @author: 熊浩
 * @date: 2023/12/14 14:05
 * @version: 1.0
 */
@Data
public class NotificationUpdateVO {
    private Integer notificationId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String message;

    /**
     * 阅读状态:1.已读 2.未读
     */
    private Object status;
}
