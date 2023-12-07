package cn.shenmuyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.shenmuyan.bean.Coupons;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.Payments;
import cn.shenmuyan.bean.Seats;
import cn.shenmuyan.service.CouponService;
import cn.shenmuyan.service.OrderService;
import cn.shenmuyan.service.SeatService;
import cn.shenmuyan.service.PaymentService;
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

    @Resource
    CouponService couponService;
    @Resource
    PaymentService paymentService;

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
     * @param ordersId 订单id
     * @return
     */
    @GetMapping("/orderCancel")
    public SaResult cancelOrders(@NotNull(message = "订单id不能为空") Integer ordersId) {
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

    /**
     * 确认订单-使用优惠卷之前
     *
     * @param ordersId 订单id
     * @return
     */
    @GetMapping("/orderConfirmed")
    public SaResult confirmedOrders(@NotNull(message = "订单id不能为空") Integer ordersId) {
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
                int i1 = paymentService.addPayment(payment);
                if (i1 > 0) {
                    Payments payment1 =paymentService.findPaymentByOrderId(order1.getId());
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

    /**
     * 支付使用查询优惠券
     * @return
     */
    @GetMapping("/selectCoupons")
    public SaResult confirmedPayment() {
        if (StpUtil.isLogin()) {
            //1.得到登录id
            int userId = StpUtil.getLoginIdAsInt();
            //2.通过userID查询用户优惠券关联表中使用日期为空的优惠券id
            List<Integer> couponsIds = couponService.selectAllByUserId(userId);
            if (couponsIds.size() > 0) {
                //3.通过优惠券id查出未到截止日期的优惠券
                List<Coupons> coupons = couponService.selectCoupons(couponsIds);
                return SaResult.ok().setData(coupons);
            }
        }
        return SaResult.ok("未查询到优惠券");
    }

    /**
     * 支付使用优惠券计算价格
     * @param paymentId
     * @param couponsId
     * @return
     */
    @GetMapping("/useCoupons")
    public SaResult useCoupons(@NotNull(message = "支付id不能为空") Integer paymentId,
                               @NotNull(message = "优惠券id不能为空") Integer couponsId) {
        //1.通过couponsId查到使用的优惠券
        //2.通过paymentId查到支付信息
        //3.获取订单价格和优惠券折扣 ，计算最后的价格 setprice但不修改数据库
        //4.返回支付信息
        return SaResult.ok();
    }

    @GetMapping("/paymentConfirmed")
    public SaResult PaymentConfirmed(@NotNull(message = "支付id不能为空") Integer paymentId,
                                     @NotNull(message = "优惠券id不能为空") Integer couponsId) {
        //通过couponsId查到使用的优惠券
        //通过paymentId查到支付信息

        //通过payment中的orderId查到该订单
        //通过couponsId修改用户优惠券关联表中used_date
        //修改订单和支付信息价格


        //理论上在插入数据库之前要判断钱是否到账但是不知道怎么弄 支付方式那一块
        //多线程的处理
        int i = paymentService.updatePayment(payment);
        if (i > 0) {
            //分配座位     在座位表中根据档位查出该档位所有空座的位置,随机分配后，拿到座位，给座位添加用户id,修改座位状态为已预约
            //生成票务返回  封装成一个VO类返回
            return SaResult.ok("支付成功");
        }
        return SaResult.error("支付失败");
        return SaResult.ok();
    }
}
