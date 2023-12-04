package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.User;
import cn.shenmuyan.vo.UserWhereVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 50414
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.User
*/
public interface UserMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll(UserWhereVO where);

    User selectByUsernameAndPasswordHash(@Param("username") String username, @Param("passwordHash")String passwordHash);

}
