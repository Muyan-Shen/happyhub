package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.UserCoupons;

import java.util.List;

/**
 * 用户优惠卷关联表Mapper
* @author 50414
* @description 针对表【user_coupons(用户优惠券关联表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.UserCoupons
*/
public interface UserCouponsMapper {



    int insert(UserCoupons record);

    int insertSelective(UserCoupons record);



    int updateByPrimaryKeySelective(UserCoupons record);

    int updateByPrimaryKey(UserCoupons record);

    List<Integer> selectAllByUserId(int userId);
}
