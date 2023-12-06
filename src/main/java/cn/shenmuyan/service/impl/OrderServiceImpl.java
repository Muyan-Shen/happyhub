package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.Payments;
import cn.shenmuyan.mapper.OrdersMapper;
import cn.shenmuyan.mapper.PaymentsMapper;
import cn.shenmuyan.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className: OrderServiceImpl
 * @author: 叶宝谦
 * @date: 2023/12/05 20:27
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private PaymentsMapper paymentsMapper;
    @Override
    public int addOrder(Orders orders) {
        int i = ordersMapper.insert(orders);
        return i;
    }

    @Override
    public int updateOrdersStatus(Orders order) {
        int i = ordersMapper.updateByPrimaryKeySelective(order);
        return i;
    }

    @Override
    public Orders findOrdersById(Integer id) {
        Orders orders = ordersMapper.selectByPrimaryKey(id);
        if(orders!=null){
            return orders;
        }
        return null;
    }

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


}
