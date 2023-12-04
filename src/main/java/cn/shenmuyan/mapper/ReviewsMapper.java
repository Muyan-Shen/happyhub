package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Reviews;

/**
* @author 50414
* @description 针对表【reviews(评论表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Reviews
*/
public interface ReviewsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Reviews record);

    int insertSelective(Reviews record);

    Reviews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reviews record);

    int updateByPrimaryKey(Reviews record);

}
