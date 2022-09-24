package com.base.convert;

import com.base.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxk
 * @date 2022/9/24 10:36
 */
public class String2EnumConvertFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> CONVERTER_MAP = new HashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> aClass) {
        return CONVERTER_MAP.computeIfAbsent(aClass, key-> new String2EnumConvert<T>(aClass));
    }
}
