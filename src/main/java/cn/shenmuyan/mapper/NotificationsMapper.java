package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Notifications;

/**
 * 通知信息表Mapper
* @author 50414
* @description 针对表【notifications(通知信息表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Notifications
*/
public interface NotificationsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Notifications record);

    int insertSelective(Notifications record);

    Notifications selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notifications record);

    int updateByPrimaryKey(Notifications record);

}
