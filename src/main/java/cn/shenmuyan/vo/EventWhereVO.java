package cn.shenmuyan.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Event查询vo
 * @className: EventWhereVO
 * @author: 叶宝谦
 * @date: 2023/12/05 10:44
 **/
@Data
public class EventWhereVO {
    private Integer id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime[] startTime;
    private LocalDateTime[] endTime;
    /**
     * 活动组织人
     */
    private String organizer;
    private Integer[] organizerId;
    private Integer status;
}
