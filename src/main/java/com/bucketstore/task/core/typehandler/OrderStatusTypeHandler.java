package com.bucketstore.task.core.typehandler;

import com.bucketstore.task.core.util.constants.OrderStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusTypeHandler extends BaseTypeHandler<OrderStatus> {

   @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getOrderStatusCode());
    }

    @Override
    public OrderStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return OrderStatus.fromCode(code);
    }

    @Override
    public OrderStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return OrderStatus.fromCode(code);
    }

    @Override
    public OrderStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return OrderStatus.fromCode(code);
    }



}
