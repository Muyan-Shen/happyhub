package cn.shenmuyan.typeHandler;

/**
 * 枚举类型处理器
 * @Title: EnumTypeHandler
 * @Author ShenMuyan
 * @Package cn.shenmuyan.typeHandler
 * @Date 2023/12/12 22:59
 */

import cn.shenmuyan.bean.DeleteStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(DeleteStatus.class)
public class EnumTypeHandler extends BaseTypeHandler<Integer> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,DeleteStatus.fromInt(parameter));
    }

    /**
     * @param resultSet
     * @param s
     * @return
     * @throws SQLException
     */
    @Override
    public Integer getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return DeleteStatus.fromString(resultSet.getString(s));
    }

    /**
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Integer getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return DeleteStatus.fromString(resultSet.getString(i));
    }

    /**
     * @param callableStatement
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Integer getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return DeleteStatus.fromString(callableStatement.getString(i));
    }


}