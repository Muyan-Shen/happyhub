package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Role;
/**
* @author 50414
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-12-04 14:30:14
* @Entity cn.shenmuyan.bean.Role
*/
public interface RoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}
