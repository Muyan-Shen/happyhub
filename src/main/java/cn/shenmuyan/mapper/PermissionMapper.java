package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
* @author 50414
* @description 针对表【permission(权限表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Permission
*/
public interface PermissionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    Set<Permission> selectByRoleId(@Param("roleId") Integer roleId);

}
