//package com.hqep.pmsn.mybatis.handlers;
//
//import oracle.sql.DATE;
//import oracle.sql.TIMESTAMP;
//import oracle.sql.TIMESTAMPLTZ;
//import oracle.sql.TIMESTAMPTZ;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.sql.*;
//
///**
// * @类名: MyObjectTypeHandle
// * @功能描述 数据库查询时 json返回对象
// * @作者信息 Wang_XD
// * @创建时间 2019/8/21
// */
//@MappedTypes({Object.class})
//@MappedJdbcTypes(value = {JdbcType.TIMESTAMP})
//public class MyObjectTypeHandle extends BaseTypeHandler<Object> {
//    public MyObjectTypeHandle() {
//    }
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
//        ps.setObject(i, parameter);
//    }
//
//    @Override
//    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        Object result = rs.getObject(columnName);
//        return rs.wasNull() ? null : dealResult(result);
//    }
//
//    @Override
//    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        Object result = rs.getObject(columnIndex);
//        return rs.wasNull() ? null : dealResult(result);
//    }
//
//    @Override
//    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        Object result = cs.getObject(columnIndex);
//        return cs.wasNull() ? null : dealResult(result);
//    }
//
//    /**
//     * 为了解决错误：
//     * Request processing failed; nested exception is org.springframework.http.converter.HttpMessageConversionException:
//     * Type definition error: [simple type, class java.io.ByteArrayInputStream];
//     * nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
//     * No serializer found for class java.io.ByteArrayInputStream and no properties discovered to create BeanSerializer (
//     * to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
//     * (through reference chain: com.hqep.wzglpt.util.PageData["data"]->java.util.ArrayList[0]->com.hqep.wzglpt.util.PageData["create_time"]->oracle.sql.TIMESTAMP["stream"])
//     */
//    private Object dealResult(Object result) throws SQLException {
//        if (result instanceof TIMESTAMP) {
//            return new Date(((TIMESTAMP) result).dateValue().getTime());
//        } else if (result instanceof DATE) {
//            return new Date(((DATE) result).dateValue().getTime());
//        } else if (result instanceof TIMESTAMPLTZ) {
//            return new Date(((TIMESTAMPLTZ) result).dateValue().getTime());
//        } else if (result instanceof TIMESTAMPTZ) {
//            return new Date(((TIMESTAMPTZ) result).dateValue().getTime());
//        } else{
//            return result;
//        }
//    }
//
//}
