package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Seats;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
/**
* @author 86136
* @description 针对表【seats(座位表)】的数据库操作Mapper
* @createDate 2023-12-06 09:25:25
* @Entity cn.shenmuyan.bean.Seats
*/
public interface SeatsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Seats record);

    int insertSelective(Seats record);

    void insertSeats(List<Seats> seats);

    Seats selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seats record);

    int updateByPrimaryKey(Seats record);

    List<Seats> selectAll();

    int getLastSeatNum(@Param("eventId") int eventId,@Param("gear") int gear,@Param("direction")String direction,@Param("status") Integer status);

    List<Seats> selectByEventIdAndGearAndDirection(@Param("eventId") int eventId,@Param("gear") int gear,@Param("direction")String direction,@Param("num")int num);

    void updateStatusByPrimaryKey(@Param("ids") int[] seatIds,@Param("status") int status);

    BigDecimal[] getGearPrices(@Param("eventId") int eventId);
}
