package com.car_rental.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.sql.Date;

@Configuration
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        try {
            return Date.valueOf(source);
        }
        catch (Exception e) {
            return null;
        }
    }
}
