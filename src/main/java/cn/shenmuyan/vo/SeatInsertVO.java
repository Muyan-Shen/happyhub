package cn.shenmuyan.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 座位创建视图
 * @className: SeatInsertService
 * @author: 叶宝谦
 * @date: 2023/12/07 9:03
 **/
@Data
public class SeatInsertVO {
    private Integer eventId;
    private Integer topGear;
    private String[] direction;
    private Integer maxCol;
    private int[] gearSum;
    private BigDecimal[] gearPrice;
}
