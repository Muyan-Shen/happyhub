package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Coupons;

/**
* @author 50414
* @description 针对表【coupons(优惠卷表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Coupons
*/
public interface CouponsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Coupons record);

    int insertSelective(Coupons record);

    Coupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupons record);

    int updateByPrimaryKey(Coupons record);

}
