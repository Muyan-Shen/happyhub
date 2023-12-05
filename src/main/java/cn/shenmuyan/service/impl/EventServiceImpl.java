package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Events;
import cn.shenmuyan.mapper.EventsMapper;
import cn.shenmuyan.service.EventService;
import cn.shenmuyan.vo.EventWhereVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @className: EventServiceImpl
 * @author: 叶宝谦
 * @date: 2023/12/05 9:23
 **/
@Service
public class EventServiceImpl implements EventService {
    @Resource
    EventsMapper eventsMapper;
    @Override
    public List<Events> findAll(EventWhereVO eventWhereVO) {
        List<Events> events = eventsMapper.selectAll(eventWhereVO);
        return events;
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
