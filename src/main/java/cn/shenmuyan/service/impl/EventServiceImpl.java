package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Events;
import cn.shenmuyan.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动接口实现类
 * @className: EventServiceImpl
 * @author: 叶宝谦
 * @date: 2023/12/05 9:23
 **/
@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<Events> findAll(Events events) {
        return null;
    }

    @Override
    public Events findById(Integer eventId) {
        return null;
    }

    @Override
    public void updateById(Events events) {

    }

    @Override
    public void addEvent(Events events) {

    }

}
