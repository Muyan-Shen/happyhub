package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Seats;

/**
* @author 50414
* @description 针对表【seats(座位表)】的数据库操作Mapper
* @createDate 2023-12-04 14:30:14
* @Entity cn.shenmuyan.bean.Seats
*/
public interface SeatsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Seats record);

    int insertSelective(Seats record);

    Seats selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seats record);

    int updateByPrimaryKey(Seats record);

}
