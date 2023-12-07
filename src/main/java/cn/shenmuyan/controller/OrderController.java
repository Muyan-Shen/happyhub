package cn.shenmuyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.Payments;
import cn.shenmuyan.bean.Seats;
import cn.shenmuyan.service.OrderService;
import cn.shenmuyan.service.SeatService;
import cn.shenmuyan.service.UserService;
import cn.shenmuyan.vo.SeatTypeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单控制器
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
    @Resource
    SeatService seatService;

    @PostMapping("/getPrice")
    public SaResult getPrice(SeatTypeVO vo){
        List<Seats> seats = seatService.getSeat(vo.getEventId(), vo.getGear(), vo.getDirection(), vo.getNum());
        BigDecimal price = new BigDecimal("0");
        for (Seats seat : seats) {
            price.add(seat.getPrice());
        }
        // TODO:可以在这里计算折扣，优惠券之类，也可以返回用了什么优惠卷
        return SaResult.ok().set("price",price);
    }
    /**
     * 点击购买后生成一个状态挂起的订单
     *
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
        int i = orderService.addOrder(orders);
        if (i > 0) {
            Orders orders1 = orderService.findOrdersById(orders.getId());
            return SaResult.ok("订单发起成功").setData(orders1);
        }
        return SaResult.error("订单发起失败");
    }

    /**
     * 根据订单id修改订单状态为cancelled状态
     *
     * @param ordersId
     * @return
     */
    @GetMapping("/orderCancel")
    public SaResult cancelOrders(Integer ordersId) {
        StpUtil.checkLogin();
        Orders order = new Orders();
        order.setId(ordersId);
        order.setStatus("cancelled");
        int i = orderService.updateOrdersStatus(order);
        if (i > 0) {
            return SaResult.ok().setMsg("订单取消成功");
        }
        return SaResult.error("订单取消失败");
    }

    @GetMapping("/orderConfirmed")
    public SaResult confirmedOrders(Integer ordersId) {
        StpUtil.checkLogin();
        Orders order = new Orders();
        order.setId(ordersId);
        order.setStatus("confirmed");
        int i = orderService.updateOrdersStatus(order);
        if (i > 0) {
            System.out.println("订单已确认");
            //订单确认之后生成支付信息
            //1.通过id查询该订单
            Orders order1 = orderService.findOrdersById(ordersId);
            if (order1 != null) {
                //2.通过order1封装支付信息
                Payments payment = new Payments();
                payment.setOrderId(order1.getId());
                payment.setAmount(order1.getTotalPrice());
                payment.setStatus("process");
                int i1 = orderService.addPayment(payment);
                if (i1 > 0) {
                    Payments payment1 = orderService.findPaymentByOrderId(order1.getId());
                    if (payment1 != null) {
                        return SaResult.ok("支付信息").setData(payment1);
                    }
                }
            } else {
                return SaResult.error("订单查询失败");
            }
        }
        return SaResult.error("添加支付失败");
    }
}
