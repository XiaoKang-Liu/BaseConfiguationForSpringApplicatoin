package com.base.convert;

import com.base.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxk
 * @date 2022/9/24 10:29
 */
public class String2EnumConvert<T extends BaseEnum> implements Converter<String, T> {

    private final Map<String, T> enumMap = new HashMap<>();

    public String2EnumConvert(Class<T> enumType) {
        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConstant : enumConstants) {
            enumMap.put(enumConstant.toString(), enumConstant);
        }
    }

    @Override
    public T convert(String s) {
        T t = enumMap.get(s);
        if (t == null) {
            throw new RuntimeException("枚举不存在！");
        }
        return t;
    }
}
