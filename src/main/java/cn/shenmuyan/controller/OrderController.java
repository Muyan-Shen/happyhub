package cn.shenmuyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 订单控制器，
 * @className: PayController
 * @author: 叶宝谦
 * @date: 2023/12/05 20:43
 **/
@RestController("/pay")
public class OrderController {
    @Resource
    OrderService orderService;

    /**
     * 点击购买后(价格类型未解决 BigDecimal)生成一个状态挂起的订单
     * @param eventId 活动id
     * @param price   价格
     * @return
     */
    @PutMapping("/beforePaid")
    public SaResult beforePaid(Integer eventId, BigDecimal price) {

        int userId = Integer.parseInt((String) StpUtil.getLoginId());
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setEventId(eventId);
        orders.setStatus("pending");
        orders.setTotalPrice(price);
        int i=orderService.addOrder(orders);
        if(i>0) {
            return SaResult.ok("订单发起成功").setData(orders);
        }
        return SaResult.error("订单发起失败");
    }

    @GetMapping("/cancel")
    public SaResult cancelOrders( Integer eventId) {
        Integer userId = (Integer) StpUtil.getLoginId();
        return SaResult.ok().setMsg("订单取消成功");
    }
}
