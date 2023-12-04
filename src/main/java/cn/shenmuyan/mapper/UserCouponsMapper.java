package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.UserCoupons;

/**
* @author 50414
* @description 针对表【user_coupons(用户优惠券关联表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.UserCoupons
*/
public interface UserCouponsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupons record);

    int insertSelective(UserCoupons record);

    UserCoupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupons record);

    int updateByPrimaryKey(UserCoupons record);

}
