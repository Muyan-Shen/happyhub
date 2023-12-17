package cn.shenmuyan.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 使用优惠券VO
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/15
 */
@Data
public class UseCouponsVO {
    @NotNull(message = "支付id不能为空")
    private Integer paymentId;
    @NotNull(message = "优惠券id不能为空")
    private Integer couponsId;
}
