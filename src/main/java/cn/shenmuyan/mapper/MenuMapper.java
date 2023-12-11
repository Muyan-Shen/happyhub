package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单Mapper
 *
 * @Title: MenuMapper
 * @Author ShenMuyan
 * @Package cn.shenmuyan.mapper
 * @Date 2023/12/11 1:12
 */


public interface MenuMapper {


    List<Menu> findAll();


    List<Menu> findByUser(@Param("username") String username);
}