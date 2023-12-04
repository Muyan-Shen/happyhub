package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.User;
import java.util.List;

/**
* @author Lenovo
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-12-04 15:48:02
* @Entity cn.shenmuyan.bean.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();

}
