package cn.shenmuyan.service.impl;

import cn.shenmuyan.bean.Events;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.mapper.EventsMapper;
import cn.shenmuyan.mapper.OrdersMapper;
import cn.shenmuyan.mapper.UserMapper;
import cn.shenmuyan.service.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: EventServiceImpl
 * @author: 叶宝谦
 * @date: 2023/12/05 9:23
 **/
@Service
public class EventServiceImpl implements EventService {
    @Resource
    private EventsMapper eventsMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public List<Events> findAll(Events events) {
        return null;
    }

    @Override
    public Events findById(Integer eventId) {
        Events event = eventsMapper.selectByPrimaryKey(eventId);
        return event;
    }

    @Override
    public void updateById(Events events) {

    }

    @Override
    public void addEvent(Events events) {

    }

    @Override
    public String findOrganizerUsername(Integer organizerId) {
        User user = userMapper.selectByPrimaryKey(organizerId);
        if(user!=null){
           return user.getUsername();
        }
       return null;
    }




}
