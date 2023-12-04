package cn.shenmuyan.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户优惠券关联表
 * @TableName user_coupons
 */
@Data
public class UserCoupons implements Serializable {
    /**
     * 用户id_外键
     */
    private Integer userId;

    /**
     * 优惠卷id_外键
     */
    private Integer couponId;

    /**
     * 领取时间
     */
    private Date claimedDate;

    /**
     * 使用时间
     */
    private Date usedDate;

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
        UserCoupons other = (UserCoupons) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCouponId() == null ? other.getCouponId() == null : this.getCouponId().equals(other.getCouponId()))
            && (this.getClaimedDate() == null ? other.getClaimedDate() == null : this.getClaimedDate().equals(other.getClaimedDate()))
            && (this.getUsedDate() == null ? other.getUsedDate() == null : this.getUsedDate().equals(other.getUsedDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCouponId() == null) ? 0 : getCouponId().hashCode());
        result = prime * result + ((getClaimedDate() == null) ? 0 : getClaimedDate().hashCode());
        result = prime * result + ((getUsedDate() == null) ? 0 : getUsedDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", couponId=").append(couponId);
        sb.append(", claimedDate=").append(claimedDate);
        sb.append(", usedDate=").append(usedDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}