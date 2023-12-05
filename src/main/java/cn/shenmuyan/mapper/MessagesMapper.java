package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Messages;

/**
 * 信息表Mapper
* @author 50414
* @description 针对表【messages(消息表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Messages
*/
public interface MessagesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Messages record);

    int insertSelective(Messages record);

    Messages selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Messages record);

    int updateByPrimaryKey(Messages record);

}
