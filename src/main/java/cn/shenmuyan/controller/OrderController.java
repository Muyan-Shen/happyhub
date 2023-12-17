package cn.shenmuyan.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.shenmuyan.bean.*;
import cn.shenmuyan.service.*;
import cn.shenmuyan.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @Resource
    EventService eventService;
    @Resource
    UserService userService;


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
//     * @param eventId 活动id
//     * @param price   价格
     * @return
     */
    @PostMapping ("/beforePaid")
    public SaResult beforePaid(
            @RequestBody PaymentBeforeVO paymentBeforeVO
//            @RequestBody@NotNull(message = "活动id不能为空") Integer eventId,
//                               @RequestBody@NotNull(message = "价格档位不能为空") BigDecimal price
    ) {
        System.out.println(paymentBeforeVO.getPrice());
        System.out.println(paymentBeforeVO.getEventId());
        int userId = Integer.parseInt((String) StpUtil.getLoginId());
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setEventId(paymentBeforeVO.getEventId());
        orders.setStatus("pending");
        orders.setTotalPrice(paymentBeforeVO.getPrice());
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
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/orderCancel/{orderId}")
    public SaResult cancelOrders(@PathVariable("orderId") @NotNull(message = "订单id不能为空") Integer orderId) {
        StpUtil.checkLogin();
        Orders order = new Orders();
        order.setId(orderId);
        order.setStatus("cancelled");
        int i = orderService.updateOrdersStatus(order);
        if (i > 0) {
            return SaResult.ok().setMsg("订单取消成功");
        }
        return SaResult.error("订单取消失败");
    }

    /**
     * 取消支付
     * @param paymentId
     * @return
     */
    @GetMapping("/paymentCanceled/{paymentId}")
    public SaResult paymentCanceled(@PathVariable("paymentId") @NotNull(message = "支付id不能为空") Integer paymentId){
        StpUtil.checkLogin();
        Payments payment=new Payments();
        payment.setId(paymentId);
        payment.setStatus("failed");
        int i = paymentService.updatePayment(payment);
        if (i > 0) {
            return SaResult.ok().setMsg("支付取消成功");
        }
        return SaResult.error("支付取消失败");
    }


    /**
     * 确认订单-使用优惠卷之前
     *
     * @param ordersId 订单id
     * @return
     */
    @GetMapping("/orderConfirmed/{orderId}")
    public SaResult confirmedOrders(@PathVariable("orderId") @NotNull(message = "订单id不能为空") Integer ordersId) {
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
     * @param useCouponsVO
     * @return
     */
    @PostMapping("/useCoupons")
    public SaResult useCoupons(@RequestBody UseCouponsVO useCouponsVO) {
        //1.通过couponsId查到使用的优惠券
         Coupons coupon=couponService.selectCouponById(useCouponsVO.getCouponsId());
        //2.通过paymentId查到支付信息
        Payments payment = paymentService.selectPaymentById(useCouponsVO.getPaymentId());
        System.out.println(payment);
        //3.获取支付信息价格和优惠券折扣 ，计算最后的价格 setprice但不修改数据库
        BigDecimal price=null;
        if(payment!=null) {
            price= payment.getAmount();
        }
        if(coupon!=null) {
            if (ObjectUtil.isNotNull(coupon.getDiscountAmount())) {
                price = price.subtract(coupon.getDiscountAmount());
            }
            if (ObjectUtil.isNotNull(coupon.getDiscount())) {
                 switch (String.valueOf(coupon.getDiscount())){
                     case "5折":price=price.multiply(new BigDecimal(0.5));break;
                     case "6折":price=price.multiply(new BigDecimal(0.6));break;
                     case "7折":price=price.multiply(new BigDecimal(0.7));break;
                     case "8折":price=price.multiply(new BigDecimal(0.8));break;
                     case "9折":price=price.multiply(new BigDecimal(0.9));break;
                 }
            }
            payment.setAmount(price);
        }
        //4.返回支付信息
        return SaResult.ok().setData(payment);
    }

    @PostMapping("/paymentConfirmed")
    public SaResult PaymentConfirmed(@RequestBody @Validated PaymentConfirmedVO paymentConfirmedVO) {
        if(paymentConfirmedVO.getCouponsId()!=null) {
            //通过couponsId查到使用的优惠券
            Coupons coupon = couponService.selectCouponById(paymentConfirmedVO.getCouponsId());
            //通过couponsId修改用户优惠券关联表中used_date
            int i=couponService.updateCouponUsedDate(paymentConfirmedVO.getCouponsId());
            if(i<0){
                return SaResult.error("使用日期更新失败");
            }
        }
        //通过paymentId查到支付信息
        Payments payment = paymentService.selectPaymentById(paymentConfirmedVO.getPaymentId());
        //通过payment中的orderId查到该订单
        Orders order = orderService.findOrdersById(payment.getOrderId());
        //修改订单
         order.setTotalPrice(paymentConfirmedVO.getPrice());
         int i2=orderService.updateOrder(order);
        //理论上在插入数据库之前要判断钱是否到账但是不知道怎么弄 支付方式那一块
        //修改支付信息价格
        //多线程的处理
        payment.setAmount(paymentConfirmedVO.getPrice());
        payment.setStatus("success");
        int i1 = paymentService.updatePayment(payment);
        if (i1 > 0) {
            //分配座位     在座位表中根据档位查出该档位所有空座的位置,随机分配后，拿到座位，给座位添加用户id,修改座位状态为已预约
            List<Seats> seat = seatService.getSeat(order.getEventId(), paymentConfirmedVO.getGear(), 1);
            if (seat.size()>0) {
                Integer[] seatIdsInteger = seat.stream().map(Seats::getId).toArray(Integer[]::new);
                int[] seatIds = Arrays.stream(seatIdsInteger).mapToInt(Integer::intValue).toArray();
                seatService.updateSeat(seatIds,order.getUserId(),2);
                //生成票务返回  封装成一个VO类返回
                Events event= eventService.findById(order.getEventId());
                User user = userService.findById(order.getUserId());
                TicketVO ticketVO=new TicketVO();
                ticketVO.setTitle(event.getTitle());
                ticketVO.setUsername(user.getUsername());

                for (Seats s : seat) {
                    ticketVO.setGear(s.getGears());
                    ticketVO.setDirection(s.getDirection());
                    ticketVO.setRow(s.getRow());
                    ticketVO.setCol(s.getCol());
                    ticketVO.setSeatNumber(Integer.parseInt(s.getSeatNumber()));
                }
                System.out.println(ticketVO);
                return SaResult.ok("支付成功").setData(ticketVO);
            }
        }
        return SaResult.error("支付失败");
    }
}
