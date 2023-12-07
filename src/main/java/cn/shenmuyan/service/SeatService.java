package cn.shenmuyan.service;

import cn.shenmuyan.bean.Seats;
import cn.shenmuyan.vo.SeatInsertVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 座位接口
 * @className: SeatService
 * @author: 叶宝谦
 * @date: 2023/12/06 9:04
 **/
public interface SeatService {
    /**
     * 创建所有档次的座位
     *
     * @param eventId   活动id
     * @param topGear   最高档次
     * @param direction 座位朝向
     * @param maxCol    一行最多有多少座位
     * @param gearSum   每个档次的座位数
     * @param gearPrice 每个档次的价格
     */
    void setSeat(int eventId, int topGear, String direction, int maxCol, int[] gearSum, BigDecimal[] gearPrice);

    /**
     * 获得活动各个档次座位的价格
     * @param eventId
     * @return
     */
    BigDecimal[] getGearPrices(int eventId);
    /**
     * 得到某个活动还有多少座位
     *
     * @param eventId 活动id
     * @param status  座位状态，-1：所有，1：空座，2：预约
     * @return  还有多少座位
     */
    Integer getLastSeatNum(int eventId, Integer status);

    /**
     * 得到某个档次还有多少座位
     *
     * @param eventId 活动id
     * @param gear    档次，-1：所有
     * @param status  座位状态，-1：所有，1：空座，2：预约
     * @return  还有多少座位
     */
    Integer getLastSeatNum(int eventId, int gear, Integer status);

    /**
     * 得到某个方位还有多少座位
     *
     * @param eventId   活动id
     * @param direction 方位，null为所有
     * @param status    座位状态，-1：所有，1：空座，2：预约
     * @return  还有多少座位
     */
    Integer getLastSeatNum(int eventId, String direction, Integer status);

    /**
     * 得到特定方位的某个档次还有多少座位
     *
     * @param eventId   活动id
     * @param gear      档次，-1：所有
     * @param direction 方位，null为所有
     * @param status    座位状态，-1：所有，1：空座，2：预约
     * @return  还有多少座位
     */
    Integer getLastSeatNum(int eventId, int gear, String direction, Integer status);

    /**
     * 根据活动id获得num个空座位
     *
     * @param eventId 活动id
     * @param num 要多少个座位
     * @return num个空座位
     */
    List<Seats> getSeat(int eventId, int num);

    /**
     * 根据活动id和座位等级获得num个空座位
     *
     * @param eventId   活动id
     * @param gear  档次，-1为所有
     * @param num   要多少个座位
     * @return num个空座位
     */
    List<Seats> getSeat(int eventId, int gear, int num);

    /**
     * 根据活动id和方位等级获得num个空座位
     *
     * @param eventId   活动id
     * @param direction 方位，null为所有
     * @param num 要多少个座位
     * @return num个空座位
     */
    List<Seats> getSeat(int eventId, String direction, int num);

    /**
     * 根据活动id，档次和方位座位等级获得num个空座位
     *
     * @param eventId   活动id
     * @param gear      档次，-1为所有
     * @param direction 方位，null为所有
     * @param num 要多少个座位
     * @return num个空座位
     */
    List<Seats> getSeat(int eventId, int gear, String direction, int num);

    /**
     * 设置座位状态
     * @param seatIds 座位id
     * @param status    座位状态：1、空座，2、已预约
     */
    void updateSeat(int[] seatIds,int status);
}
