package com.odg.typeconverter;

import com.odg.typeconverter.converter.IntegerToStringConverter;
import com.odg.typeconverter.converter.IpPortToStringConverter;
import com.odg.typeconverter.converter.StringToIntegerConverter;
import com.odg.typeconverter.converter.StringToIpPortConverter;
import com.odg.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 주석처리: 우선순위 이슈
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());

        // 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
