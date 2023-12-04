package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.UserRoleMapping;

/**
* @author 50414
* @description 针对表【users_role_mapping(账号角色映射表)】的数据库操作Mapper
* @createDate 2023-12-04 14:30:14
* @Entity cn.shenmuyan.bean.UsersRoleMapping
*/
public interface UsersRoleMappingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleMapping record);

    int insertSelective(UserRoleMapping record);

    UserRoleMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleMapping record);

    int updateByPrimaryKey(UserRoleMapping record);

}
