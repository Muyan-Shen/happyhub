package cn.shenmuyan.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动修改vo
 * @className: EventUpdateVO
 * @author: 叶宝谦
 * @date: 2023/12/15 15:15
 **/
@Data
public class EventUpdateVO {
    private Integer id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String photoUrl;
    private String status;
    private String statusDescription;
}
