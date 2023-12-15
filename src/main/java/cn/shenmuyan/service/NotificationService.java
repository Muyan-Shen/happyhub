package cn.shenmuyan.service;

import cn.shenmuyan.bean.Notifications;
import cn.shenmuyan.vo.NotificationInsertVO;
import cn.shenmuyan.vo.NotificationUpdateVO;
import cn.shenmuyan.vo.NotificationWhereVO;

import java.util.List;

/**
 * 通知接口
 * @className: NotificationService
 * @date: 2023/12/13 15:37
 */

public interface NotificationService {
    //查所有通知
    List<Notifications> selectAll(NotificationWhereVO notificationWhereVO);
    //通过id查通知
    Notifications selectById(Integer id);
    //修改通知信息
    void updateById(NotificationUpdateVO vo);
    //删除通知
    void deleteById(Integer id);
    //添加通知
    void add(NotificationInsertVO vo);
}
