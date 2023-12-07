package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Payments;
import cn.shenmuyan.mapper.PaymentsMapper;
import cn.shenmuyan.service.PaymentService;

import javax.annotation.Resource;

/**
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentsMapper paymentsMapper;

    @Override
    public int addPayment(Payments payment) {
        int i = paymentsMapper.insertSelective(payment);
        return i;
    }

    @Override
    public Payments findPaymentByOrderId(Integer orderId) {
        Payments payments=paymentsMapper.findPaymentByOrderId(orderId);
        if (payments!=null){
            return payments;
        }
        return null;
    }

    @Override
    public int updatePayment(Payments payment) {
        int i = paymentsMapper.updateByPrimaryKeySelective(payment);
        return i;
    }
}
