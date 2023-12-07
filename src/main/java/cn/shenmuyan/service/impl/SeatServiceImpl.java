package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Seats;
import cn.shenmuyan.exceptions.SeatAlreadyExistedException;
import cn.shenmuyan.exceptions.SeatsNumOutOfBoundsException;
import cn.shenmuyan.mapper.SeatsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 座位信息实现类
 * @className: SeatService
 * @author: 叶宝谦
 * @date: 2023/12/06 9:04
 **/
@Service
public class SeatServiceImpl implements cn.shenmuyan.service.SeatService {
    @Resource
    SeatsMapper seatsMapper;

    @Override
    public void setSeat(int eventId, int topGear, String direction, int maxCol, int[] gearSum, BigDecimal[] gearPrice) {
        if (getLastSeatNum(eventId,-1)>0){
            throw new SeatAlreadyExistedException();
        }
        List<Seats> seats = new ArrayList<>();
        int sum = Arrays.stream(gearSum).sum();
        int maxRow = ((sum % maxCol) == 0) ? sum / maxCol : sum / maxCol + 1;
        //已安排的座位数
        int seatSum = 0;
        for (int col = 1; col <= maxCol; col++) {
            for (int row = 1; row <= maxRow; row++) {
                Seats seat = new Seats();
                seat.setEventId(eventId);
                seat.setDirection(direction);
                seat.setSeatNumber(eventId + "" + row + "" + col);
                seat.setRow(row);
                seat.setCol(col);
                for (int i = 1; i <= topGear; i++) {
                    if (seatSum < Arrays.stream(gearSum).limit(i).sum()) {
                        seatSum++;
                        seat.setGears(i);
                        seat.setPrice(gearPrice[i - 1]);
                        break;
                    }
                }
                seats.add(seat);
            }
        }
        seatsMapper.insertSeats(seats);
    }

    @Override
    public BigDecimal[] getGearPrices(int eventId) {
        return seatsMapper.getGearPrices(eventId);
    }

    @Override
    public Integer getLastSeatNum(int eventId, Integer status) {
        return seatsMapper.getLastSeatNum(eventId, -1, null, status);
    }

    @Override
    public Integer getLastSeatNum(int eventId, int gear, Integer status) {
        return seatsMapper.getLastSeatNum(eventId, gear, null, status);
    }

    @Override
    public Integer getLastSeatNum(int eventId, String direction, Integer status) {
        return getLastSeatNum(eventId, -1, direction, status);
    }

    @Override
    public Integer getLastSeatNum(int eventId, int gear, String direction, Integer status) {
        return seatsMapper.getLastSeatNum(eventId, gear, direction, status);
    }

    @Override
    public List<Seats> getSeat(int eventId, int num) {
        return getSeat(eventId, -1, null, num);
    }

    @Override
    public List<Seats> getSeat(int eventId, int gear, int num) {
        return getSeat(eventId, gear, null, num);
    }

    @Override
    public List<Seats> getSeat(int eventId, String direction, int num) {
        return getSeat(eventId, -1, direction, num);
    }

    @Override
    public List<Seats> getSeat(int eventId, int gear, String direction, int num) {
        if (getLastSeatNum(eventId,gear,direction,1)<num){
            throw new SeatsNumOutOfBoundsException("订座大于当前剩余座位数",400);
        }
        List<Seats> seats = seatsMapper.selectByEventIdAndGearAndDirection(eventId, gear, direction, num);
        return seats;
    }

    @Override
    public void updateSeat(int[] seatIds, int status) {
        seatsMapper.updateStatusByPrimaryKey(seatIds, status);
    }
}
