package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.UserRoleMapping;
import java.util.List;

/**
* @author Lenovo
* @description 针对表【user_role_mapping(账号角色映射表)】的数据库操作Mapper
* @createDate 2023-12-04 16:03:14
* @Entity cn.shenmuyan.bean.UserRoleMapping
*/
public interface UserRoleMappingMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRoleMapping record);

    int insertSelective(UserRoleMapping record);

    UserRoleMapping selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleMapping record);

    int updateByPrimaryKey(UserRoleMapping record);

    List<UserRoleMapping> selectAll();

}
