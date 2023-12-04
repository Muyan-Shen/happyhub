package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.CouponsCopy1;

/**
* @author 50414
* @description 针对表【coupons_copy1(优惠卷表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.CouponsCopy1
*/
public interface CouponsCopy1Mapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CouponsCopy1 record);

    int insertSelective(CouponsCopy1 record);

    CouponsCopy1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponsCopy1 record);

    int updateByPrimaryKey(CouponsCopy1 record);

}
