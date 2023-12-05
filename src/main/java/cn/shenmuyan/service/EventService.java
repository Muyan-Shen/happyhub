package cn.shenmuyan.service;

import cn.shenmuyan.bean.Events;
import cn.shenmuyan.bean.Orders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: EventService
 * @author: 叶宝谦
 * @date: 2023/12/05 9:23
 **/

public interface EventService {
    public List<Events> findAll(Events events);
    public  Events findById(Integer id);
    void updateById(Events events);
    void addEvent(Events events);

    String findOrganizerUsername(Integer organizerId);






}
