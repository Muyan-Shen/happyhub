package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 权限表Mapper
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

    boolean updateByPrimaryKeySelective(Permission permission);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectAll(@Param("id") String id,@Param("name") String name, @Param("code") String code);

    boolean deleteByIds(Integer[] ids);
    boolean deleteById(@Param("id") Integer id);
    Permission selectByCode(String code);
}
