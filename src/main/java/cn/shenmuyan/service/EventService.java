package cn.shenmuyan.service;

import cn.shenmuyan.bean.Events;
import cn.shenmuyan.vo.EventInsertVO;
import cn.shenmuyan.vo.EventUpdateVO;
import cn.shenmuyan.vo.EventWhereVO;
import org.springframework.stereotype.Service;
import cn.shenmuyan.bean.Orders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动接口
 * @className: EventService
 * @author: 叶宝谦
 * @date: 2023/12/05 9:23
 **/

public interface EventService {
    public List<Events> findAll(EventWhereVO eventWhereVO);
    public Events findById(Integer eventId);
    int updateById(EventUpdateVO vo);
    Integer addEvent(EventInsertVO eventInsertVO);
    String findOrganizerUsername(Integer organizerId);

    String[] getCity();
}
