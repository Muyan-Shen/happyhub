package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Seats;
import cn.shenmuyan.mapper.SeatsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className: SeatService
 * @author: 叶宝谦
 * @date: 2023/12/06 9:04
 **/
@Service
public class SeatServiceImpl implements cn.shenmuyan.service.SeatService {
    @Resource
    SeatsMapper seatsMapper;

    @Override
    public void setSeat(int eventId, int topGear, String direction, int maxRow, int[] gearSum, BigDecimal[] gearPrice) {
        List<Seats> seats = new ArrayList<>();
        int sum = Arrays.stream(gearSum).sum();
        int maxCol = ((sum % maxRow) == 0) ? sum / maxRow : sum / maxRow + 1;
        //已安排的座位数
        int seatSum = 0;
        for (int row = 1; row <= maxRow; row++) {
            for (int col = 1; col <= maxCol; col++) {
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
    public Integer getLastSeatNum(int eventId, Integer status) {
        return seatsMapper.getLastSeatNum(eventId, -1, null,status);
    }

    @Override
    public Integer getLastSeatNum(int eventId, int gear, Integer status) {
        return seatsMapper.getLastSeatNum(eventId, gear, null,status);
    }

    @Override
    public Integer getLastSeatNum(int eventId, String direction, Integer status) {
        return getLastSeatNum(eventId, -1, direction,status);
    }

    @Override
    public Integer getLastSeatNum(int eventId, int gear, String direction, Integer status) {
        return seatsMapper.getLastSeatNum(eventId, gear, direction,status);
    }

    @Override
    public Seats getSeat(int eventId) {
        return getSeat(eventId, -1, null);
    }

    @Override
    public Seats getSeat(int eventId, int gear) {
        return getSeat(eventId, gear, null);
    }

    @Override
    public Seats getSeat(int eventId, String direction) {
        return getSeat(eventId, -1, direction);
    }

    @Override
    public Seats getSeat(int eventId, int gear, String direction) {
        Seats seat = seatsMapper.selectByEventIdAndGearAndDirection(eventId, gear, direction);
        return seat;
    }
}
