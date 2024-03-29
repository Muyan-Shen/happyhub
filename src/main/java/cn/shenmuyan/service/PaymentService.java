package cn.shenmuyan.service;

import cn.shenmuyan.bean.Payments;

/**
 * 支付信息接口
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
public interface PaymentService {
    int addPayment(Payments payment);

    Payments findPaymentByOrderId(Integer id);


    int updatePayment(Payments payment);

    Payments selectPaymentById(Integer paymentId);
}
