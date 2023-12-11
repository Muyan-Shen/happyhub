package cn.shenmuyan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.shenmuyan.bean.Events;
import cn.shenmuyan.bean.User;
import cn.shenmuyan.bean.Orders;
import cn.shenmuyan.mapper.EventsMapper;
import cn.shenmuyan.mapper.UserMapper;
import cn.shenmuyan.mapper.OrdersMapper;
import cn.shenmuyan.service.EventService;
import cn.shenmuyan.vo.EventInsertVO;
import org.springframework.stereotype.Service;
import cn.shenmuyan.vo.EventWhereVO;
import cn.shenmuyan.vo.UserWhereVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动接口实现类
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
    public List<Events> findAll(EventWhereVO eventWhereVO) {
        if (eventWhereVO.getOrganizer()!=null){
            UserWhereVO userWhereVO = new UserWhereVO();
            userWhereVO.setUsername(eventWhereVO.getOrganizer());
            List<User> users = userMapper.selectAll(userWhereVO);
            if (users != null){
                //将users的所有id转换成一个Integer数组
                Integer[] ids = users.stream().map(User::getId).toArray(Integer[]::new);
                eventWhereVO.setOrganizerId(ids);
            }
        }
        System.out.println(eventWhereVO);
        List<Events> events = eventsMapper.selectAll(eventWhereVO);
        return events;
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
    public void addEvent(EventInsertVO vo) {
        Events events = BeanUtil.copyProperties(vo, Events.class);
        eventsMapper.insertSelective(events);
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
