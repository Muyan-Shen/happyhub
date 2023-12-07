package cn.shenmuyan.vo;

import lombok.Data;

/**
 * 座位种类试图(查询用
 * @className: SeatTypeVO
 * @author: 叶宝谦
 * @date: 2023/12/07 10:25
 **/
@Data
public class SeatTypeVO {
    private Integer eventId;
    private String direction;
    private Integer gear;
    private Integer num;
}
