package cn.shenmuyan.mapper;

import cn.hutool.system.UserInfo;
import cn.shenmuyan.bean.UserInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户详细信息表
* @author 50414
* @description 针对表【user_information】的数据库操作Mapper
* @createDate 2023-12-13 09:53:04
* @Entity cn.shenmuyan.bean.UserInformation
*/
public interface UserInformationMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserInformation record);

    int insertSelective(UserInformation record);

    UserInformation selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserInformation record);

    @Transactional
    boolean updateByUserId(UserInformation record);
}
