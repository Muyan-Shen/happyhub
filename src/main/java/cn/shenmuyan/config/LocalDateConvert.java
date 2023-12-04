package cn.shenmuyan.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Title: LocalDateConvert
 * @Author ShenMuyan
 * @Package cn.shenmuyan.config
 * @Date 2023/11/28 8:42
 * @description:
 */
@Component
public class LocalDateConvert implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(source, LocalDate::from);
    }
}
