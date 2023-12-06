package cn.shenmuyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单控制器，
 * @className: PayController
 * @author: 叶宝谦
 * @date: 2023/12/05 20:43
 **/
@RestController
@Validated
@RequestMapping("/pay")
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
    public SaResult beforePaid(@NotNull(message = "活动id不能为空") Integer eventId,
                               @NotNull(message = "价格档位不能为空") BigDecimal price) {

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

    /**
     * 根据订单id修改订单状态为cancelled状态
     * @param ordersId
     * @return
     */
    @GetMapping("/cancel")
    public SaResult cancelOrders(Integer ordersId) {
        StpUtil.checkLogin();
        int i=orderService.updateOrdersStatus(ordersId);
        if(i>0){
            return SaResult.ok().setMsg("订单取消成功");
        }
       return SaResult.error("订单取消失败");
    }

//    @GetMapping("/confirmed")
//    public SaResult confirmedOrders(Integer ordersId){
//
//    }
}
