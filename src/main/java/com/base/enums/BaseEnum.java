package com.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author lxk
 * @date 2022/9/21 16:28
 */
public interface BaseEnum<T> {

    /**
     * 枚举返回json时，返回的是它的value值
     * @return value 值
     */
    @JsonValue
    T getValue();

    /**
     * 描述
     * @return 描述
     */
    String getDescription();
}
