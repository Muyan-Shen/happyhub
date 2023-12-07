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



}
