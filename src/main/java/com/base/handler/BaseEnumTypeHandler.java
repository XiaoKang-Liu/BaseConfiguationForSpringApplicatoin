package com.base.handler;

import com.base.enums.BaseEnum;
import com.base.enums.Status;
import com.base.enums.Type;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * @author lxk
 * @date 2022/9/21 16:36
 */
@MappedTypes(value = {Status.class, Type.class})
public class BaseEnumTypeHandler<E extends Enum<E> & BaseEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    public BaseEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    /**
     * 插入时设置参数类型
     * @param preparedStatement SQL预编译
     * @param i 需要赋值的索引位置(相当于在JDBC中对占位符的位置进行赋值)
     * @param e 索引位置i需要赋的值
     * @param jdbcType jdbc 的类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            preparedStatement.setString(i, e.toString());
        } else {
            preparedStatement.setObject(i, e.getValue(), jdbcType.TYPE_CODE);
        }
    }

    /**
     * 根据列名获取时转换回的自定义类型
     * @param resultSet 结果集
     * @param s 列名
     */
    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return get(resultSet.getString(s));
    }

    /**
     * 根据索引位置获取时转换回的自定义类型
     * @param resultSet 结果集
     * @param i 列索引
     */
    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return get(resultSet.getString(i));
    }

    /**
     * 根据存储过程获取时转换回的自定义类型
     * @param callableStatement 结果集
     * @param i 列索引
     */
    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return get(callableStatement.getString(i));
    }

    private E get(String v) {
        if (v == null) {
            return null;
        }
        if (PATTERN.matcher(v).matches()) {
            return get(type, Integer.parseInt(v));
        }
        return get(type, v);
    }

    private E get(Class<E> type, int value) {
        E result = null;
        try {
            Method method = type.getMethod("get", Integer.class);
            // 通过 value 值获取枚举
            result = (E) method.invoke(type, value);
        } catch (NoSuchMethodException e) {
            result = Enum.valueOf(type, String.valueOf(value));
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    private E get(Class<E> type, String value) {
        E result = null;
        try {
            Method method = type.getMethod("get", String.class);
            result = (E) method.invoke(type, value);
        } catch (NoSuchMethodException e) {
            result = Enum.valueOf(type, value);
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
