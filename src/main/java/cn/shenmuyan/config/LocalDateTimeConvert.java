package cn.shenmuyan.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @className: LocalDateTimeConvert
 * @author: 叶宝谦
 * @date: 2023/12/05 11:49
 **/
@Component
public class LocalDateTimeConvert implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        LocalDateTime time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(source, LocalDateTime::from);
        return time;
    }
}
