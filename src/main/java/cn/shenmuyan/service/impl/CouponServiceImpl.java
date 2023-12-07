package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Coupons;
import cn.shenmuyan.mapper.CouponsMapper;
import cn.shenmuyan.mapper.UserCouponsMapper;
import cn.shenmuyan.service.CouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author 李诚林
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/12/7
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponsMapper couponsMapper;
    @Resource
    private UserCouponsMapper  userCouponsMapper;

    /**
     * 通过用户id查询出所有使用日期为null的优惠券
     * @param userId
     * @return
     */
    @Override
    public List<Integer> selectAllByUserId(int userId) {
        List<Integer> couponIds=userCouponsMapper.selectAllByUserId(userId);
        if(couponIds==null||couponIds.size()<0){
            return null;
        }
        return  couponIds;
    }

    @Override
    public List<Coupons> selectCoupons(List<Integer> couponsIds) {
        List<Coupons> coupons=couponsMapper.selectByCouponIds(couponsIds);
        if(coupons==null||coupons.size()<0){
            return null;
        }
        return coupons;
    }
}
