package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Orders;

import java.util.List;

/**
 * 订单表Mapper
* @author 50414
* @description 针对表【orders(订单)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Orders
*/
public interface OrdersMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<Orders> findAllOrder(Integer userId);
}
