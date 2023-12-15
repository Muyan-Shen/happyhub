package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Events;
import cn.shenmuyan.vo.EventUpdateVO;
import cn.shenmuyan.vo.EventWhereVO;

import java.util.List;

/**
 * 活动表Mapper
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

    List<Events> selectAll(EventWhereVO eventWhereVO);

    int updateByPrimaryKeySelective(EventUpdateVO vo);

    int updateByPrimaryKey(Events record);


    String[] selectAllLocation();

    List<Events> selectThree();
}
