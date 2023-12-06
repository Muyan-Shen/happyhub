package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.mapper.OrdersMapper;
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
    OrdersMapper ordersMapper;
    @Override
    public int addOrder(Orders orders) {
        int i = ordersMapper.insert(orders);
        return i;
    }
}
