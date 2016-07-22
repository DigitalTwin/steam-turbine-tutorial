package com.ge.digitaltwin.tutorial.data;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.Long.parseLong;

@Component
@SuppressWarnings("unused")
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        return new Date(parseLong(source));
    }
}
