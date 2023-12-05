package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Payments;

/**
 * 支付表Mapper
* @author 50414
* @description 针对表【payments(支付表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Payments
*/
public interface PaymentsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Payments record);

    int insertSelective(Payments record);

    Payments selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Payments record);

    int updateByPrimaryKey(Payments record);

}
