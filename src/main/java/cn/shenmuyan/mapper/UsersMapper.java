package cn.shenmuyan.mapper;

/**
* @author 50414
* @description 针对表【users(用户表)】的数据库操作Mapper
* @createDate 2023-12-04 14:30:14
* @Entity cn.shenmuyan.bean.Users
*/
public interface UsersMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

}
