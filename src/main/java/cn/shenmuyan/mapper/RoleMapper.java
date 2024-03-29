package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表Mapper
* @author 50414
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Role
*/
public interface RoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    boolean insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);


    List<Role> selectAll(@Param("name")String name,@Param("code")String code);

    void deleteByIds(@Param("ids") Integer[] ids);
    Role selectByCode(String code);

    List<String> selectAllRole();

    Integer selectByName(@Param("name")String name);
}
