package cn.shenmuyan.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 发起购票VO
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/12
 */
@Data
public class PaymentBeforeVO {
    private Integer eventId;
    private BigDecimal price;
}
