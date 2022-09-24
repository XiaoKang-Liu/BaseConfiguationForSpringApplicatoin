package com.base.convert;

import com.base.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxk
 * @date 2022/9/22 19:27
 */
public class Integer2EnumConvertFactory implements ConverterFactory<Integer, BaseEnum> {

    private static final Map<Class, Converter> CONVERTER_MAP = new HashMap<>();


    @Override
    public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> aClass) {
        return CONVERTER_MAP.computeIfAbsent(aClass, aClass1 -> new Integer2EnumConvert<>(aClass));
    }
}
