package cn.shenmuyan.vo;

import lombok.Data;

/**
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
@Data
public class TicketVO {
    private String title;
    private String username;
    private int gear;
    private int seatNumber;
    private String direction;
    private int row;
    private int col;
}
