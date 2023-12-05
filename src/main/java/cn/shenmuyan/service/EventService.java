package cn.shenmuyan.service;

import cn.shenmuyan.bean.Events;

import java.util.List;

/**
 * 活动接口
 * @className: EventService
 * @author: 叶宝谦
 * @date: 2023/12/05 9:23
 **/
public interface EventService {
    public List<Events> findAll(Events events);
    public Events findById(Integer eventId);
    void updateById(Events events);
    void addEvent(Events events);
}
