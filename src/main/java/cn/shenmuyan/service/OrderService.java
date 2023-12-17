package cn.shenmuyan.service;

import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.Payments;

import java.util.List;

/**
 * 订单接口
 * @className: OrderService
 * @author: 叶宝谦
 * @date: 2023/12/05 20:27
 **/
public interface OrderService {
    int addOrder(Orders orders);


    int updateOrdersStatus(Orders order);

    Orders findOrdersById(Integer id);


    int updateOrder(Orders order);

    List<Orders> getAllOrder(Integer userId);
}
