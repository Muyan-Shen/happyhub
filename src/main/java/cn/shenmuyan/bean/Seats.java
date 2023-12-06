package cn.shenmuyan.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 座位表
 * @TableName seats
 */
@Data
public class Seats implements Serializable {
    /**
     * 座位id
     */
    private Integer id;

    /**
     * 活动id
     */
    private Integer eventId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 座位档位
     */
    private Integer gears;

    /**
     * 座位编号
     */
    private String seatNumber;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 状态：1：空座，2：已预约
     */
    private Object status;

    /**
     * 方位
     */
    private String direction;

    /**
     * 行
     */
    private Integer row;

    /**
     * 列
     */
    private Integer col;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Seats other = (Seats) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEventId() == null ? other.getEventId() == null : this.getEventId().equals(other.getEventId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getGears() == null ? other.getGears() == null : this.getGears().equals(other.getGears()))
            && (this.getSeatNumber() == null ? other.getSeatNumber() == null : this.getSeatNumber().equals(other.getSeatNumber()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDirection() == null ? other.getDirection() == null : this.getDirection().equals(other.getDirection()))
            && (this.getRow() == null ? other.getRow() == null : this.getRow().equals(other.getRow()))
            && (this.getCol() == null ? other.getCol() == null : this.getCol().equals(other.getCol()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEventId() == null) ? 0 : getEventId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getGears() == null) ? 0 : getGears().hashCode());
        result = prime * result + ((getSeatNumber() == null) ? 0 : getSeatNumber().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDirection() == null) ? 0 : getDirection().hashCode());
        result = prime * result + ((getRow() == null) ? 0 : getRow().hashCode());
        result = prime * result + ((getCol() == null) ? 0 : getCol().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", eventId=").append(eventId);
        sb.append(", userId=").append(userId);
        sb.append(", gears=").append(gears);
        sb.append(", seatNumber=").append(seatNumber);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append(", direction=").append(direction);
        sb.append(", row=").append(row);
        sb.append(", col=").append(col);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}