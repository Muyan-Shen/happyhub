package cn.shenmuyan.mapper;

import cn.shenmuyan.bean.Faqs;

/**
* @author 50414
* @description 针对表【faqs(常见问题表)】的数据库操作Mapper
* @createDate 2023-12-04 15:45:24
* @Entity cn.shenmuyan.bean.Faqs
*/
public interface FaqsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Faqs record);

    int insertSelective(Faqs record);

    Faqs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Faqs record);

    int updateByPrimaryKey(Faqs record);

}
