package cn.shenmuyan.service;

import cn.shenmuyan.bean.Coupons;

import java.util.List;

/**
 * 优惠卷接口
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
public interface CouponService {
    List<Integer> selectAllByUserId(int userId);

    List<Coupons> selectCoupons(List<Integer> couponsIds);

    Coupons selectCouponById(Integer couponId);

    int updateCouponUsedDate(Integer couponId);
}
