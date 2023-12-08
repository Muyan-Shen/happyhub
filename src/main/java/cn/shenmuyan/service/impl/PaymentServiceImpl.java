package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Payments;
import cn.shenmuyan.mapper.PaymentsMapper;
import cn.shenmuyan.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付信息接口实现类
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
@Service
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

    @Override
    public Payments selectPaymentById(Integer paymentId) {
        Payments payment = paymentsMapper.selectByPrimaryKey(paymentId);
        return payment;
    }
}
