package com.base.convert;

import com.base.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxk
 * @date 2022/9/22 19:18
 * 将枚举的 value(Integer类型) 值转换成对应的枚举类
 */
public class Integer2EnumConvert<T extends BaseEnum> implements Converter<Integer, T> {

    private final Map<Integer, T> enumMap = new HashMap<>();

    public Integer2EnumConvert(Class<T> enumType) {
        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConstant : enumConstants) {
            enumMap.put(Integer.valueOf(enumConstant.getValue().toString()), enumConstant);
        }
    }

    @Override
    public T convert(Integer integer) {
        T t = enumMap.get(integer);
        if (t == null) {
            throw new RuntimeException("枚举不存在！");
        }
        return t;
    }
}
