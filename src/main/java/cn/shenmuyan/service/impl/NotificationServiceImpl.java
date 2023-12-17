package cn.shenmuyan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.shenmuyan.bean.Notifications;
import cn.shenmuyan.mapper.NotificationsMapper;
import cn.shenmuyan.mapper.RoleMapper;
import cn.shenmuyan.mapper.UserRoleMappingMapper;
import cn.shenmuyan.service.NotificationService;
import cn.shenmuyan.vo.NotificationInsertVO;
import cn.shenmuyan.vo.NotificationUpdateVO;
import cn.shenmuyan.vo.NotificationWhereVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: NotificationServiceImpl
 * @author: 熊浩
 * @date: 2023/12/13 15:51
 * @version: 1.0
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationsMapper notificationsMapper;
    @Resource
    private UserRoleMappingMapper userRoleMappingMapper;
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Notifications> selectAll(NotificationWhereVO notificationWhereVO) {
        return notificationsMapper.selectAll(notificationWhereVO);
    }

    @Override
    public Notifications selectById(Integer id) {
        return notificationsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateById(NotificationUpdateVO vo) {
        Notifications notifications = BeanUtil.copyProperties(vo, Notifications.class);
        notificationsMapper.updateByPrimaryKeySelective(notifications);
    }

    @Override
    public void deleteById(Integer id) {
        notificationsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(NotificationInsertVO vo) {
        Notifications notifications = BeanUtil.copyProperties(vo, Notifications.class);
        notificationsMapper.insertSelective(notifications);
    }

    @Override
    public void addByRole(String role, String title, String message) {
        Notifications notifications=new Notifications();
        notifications.setTitle(title);
        notifications.setMessage(message);
        Integer roleId = roleMapper.selectByName(role);
        List<Integer> userIds = userRoleMappingMapper.searchByRoleId(roleId);
        for (Integer userId : userIds) {
            notifications.setUserId(userId);
            notifications.setNotificationId(null);
            notificationsMapper.insertSelective(notifications);
        }
    }
}
