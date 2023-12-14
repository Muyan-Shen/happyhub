package cn.shenmuyan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Event新增vo
 * @className: EventInsertVO
 * @author: 叶宝谦
 * @date: 2023/12/06 9:21
 **/
@Data
public class EventInsertVO {
    /**
     * 活动标题
     */
    @NotBlank(message = "活动标题不能为空")
    private String title;
    /**
     * 活动描述
     */
    @NotBlank(message = "描述不能为空")
    private String description;
    /**
     * 活动位置
     */
    @NotBlank(message = "活动位置不能为空")
    private String location;
    /**
     * 开始时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endTime;
    /**
     * 组织者id
     */
    @NotNull(message = "组织者id不能为空")
    private Integer organizerId;
    /**
     * 图片url
     */
    @NotNull(message = "图片url不能为空")
    private String photoUrl;
    /**
     * 最高档位
     */
    @NotNull(message = "最高档位不能为空")
    private Integer topGear;
}
