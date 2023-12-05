package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.UserRoleMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账号角色映射表Mapper
* @author 50414
* @description 针对表【user_role_mapping(账号角色映射表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.UserRoleMapping
*/
public interface UserRoleMappingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleMapping record);

    int insertSelective(UserRoleMapping record);

    UserRoleMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleMapping record);

    int updateByPrimaryKey(UserRoleMapping record);

    void deleteByUserId(@Param(value = "userId")int userId);

    void insertBatch(@Param("accountId") int accountId, @Param("roleIds") Integer[] roleIds);
    List<UserRoleMapping> selectAll();

}
