package cn.shenmuyan.service;

import cn.shenmuyan.bean.Seats;

import java.math.BigDecimal;

/**
 * @className: SeatService
 * @author: 叶宝谦
 * @date: 2023/12/06 9:04
 **/
public interface SeatService {
    /**
     * 创建所有档次的座位
     * @param eventId   活动id
     * @param topGear   最高档次
     * @param direction 座位朝向
     * @param maxRow    一行最多有多少座位
     * @param gearSum   每个档次的座位数
     * @param gearPrice   每个档次的价格
     */
    void setSeat(int eventId, int topGear, String direction, int maxRow, int[] gearSum, BigDecimal[] gearPrice);

    /**
     * 得到某个活动还有多少座位
     * @param eventId
     * @param status    座位状态，-1：所有，1：空座，2：预约
     * @return
     */
    Integer getLastSeatNum(int eventId, Integer status);

    /**
     * 得到某个档次还有多少座位
     * @param eventId
     * @param gear
     * @param status    座位状态，-1：所有，1：空座，2：预约
     * @return
     */
    Integer getLastSeatNum(int eventId,int gear, Integer status);

    /**
     * 得到某个方位还有多少座位
     * @param eventId
     * @param direction
     * @param status    座位状态，-1：所有，1：空座，2：预约
     * @return
     */
    Integer getLastSeatNum(int eventId,String direction, Integer status);
    /**
     * 得到特定方位的某个档次还有多少座位
     * @param eventId
     * @param gear
     * @param direction
     * @param status    座位状态，-1：所有，1：空座，2：预约
     * @return
     */
    Integer getLastSeatNum(int eventId,int gear,String direction,Integer status);

    /**
     * 根据活动id获得1个空座位
     * @param eventId
     * @return
     */
    Seats getSeat(int eventId);
    /**
     * 根据活动id和座位等级获得1个空座位
     * @param eventId
     * @param gear
     * @return
     */
    Seats getSeat(int eventId,int gear);
    /**
     * 根据活动id和方位等级获得1个空座位
     * @param eventId
     * @param direction
     * @return
     */
    Seats getSeat(int eventId,String direction);
    /**
     * 根据活动id，档次和方位座位等级获得1个空座位
     * @param eventId
     * @param gear
     * @param direction
     * @return
     */
    Seats getSeat(int eventId,int gear,String direction);
}
