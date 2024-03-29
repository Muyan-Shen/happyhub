package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.RolePermissionMapping;
import org.apache.ibatis.annotations.Param;

/**
 * 角色权限映射表Mapper
* @author 50414
* @description 针对表【role_permission_mapping(角色权限映射表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.RolePermissionMapping
*/
public interface RolePermissionMappingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionMapping record);

    int insertSelective(RolePermissionMapping record);

    RolePermissionMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermissionMapping record);

    int updateByPrimaryKey(RolePermissionMapping record);

    void deleteByRoleId(Integer roleId);

    void insertBatch(@Param("roleId") Integer roleId, @Param("permissionIds") Integer[] permissionIds);
}
