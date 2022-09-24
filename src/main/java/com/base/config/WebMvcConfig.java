package com.base.config;

import com.base.convert.Integer2EnumConvertFactory;
import com.base.convert.String2EnumConvertFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lxk
 * @date 2022/9/22 20:01
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 只对 GET 请求有效
        registry.addConverterFactory(new Integer2EnumConvertFactory());
        registry.addConverterFactory(new String2EnumConvertFactory());
    }
}
