package cn.shenmuyan.mapper;

/**
* @author 50414
* @description 针对表【users_role_mapping(账号角色映射表)】的数据库操作Mapper
* @createDate 2023-12-04 14:30:14
* @Entity cn.shenmuyan.bean.UsersRoleMapping
*/
public interface UsersRoleMappingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UsersRoleMapping record);

    int insertSelective(UsersRoleMapping record);

    UsersRoleMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UsersRoleMapping record);

    int updateByPrimaryKey(UsersRoleMapping record);

}
