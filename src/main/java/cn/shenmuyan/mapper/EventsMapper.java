package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Events;

/**
* @author 50414
* @description 针对表【events(活动表
)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Events
*/
public interface EventsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Events record);

    int insertSelective(Events record);

    Events selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Events record);

    int updateByPrimaryKey(Events record);


}
