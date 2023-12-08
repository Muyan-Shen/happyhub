package cn.shenmuyan.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 支付信息确认视图
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
@Data
public class PaymentConfirmedVO {
    @NotNull(message = "支付id不能为空")
    private Integer paymentId;
    private  Integer couponsId;
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    @NotNull(message = "档位不能为空")
    private Integer gear;

}
