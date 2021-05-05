package com.geekbrains.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class FormatConfiguration {

    //Настройка форматирования даты и времени
    @Bean
    public Formatter<Date> dateFormatter(MessageSource messageSource) {
        return new Formatter<>() {
            private final MessageSource source = messageSource;

            public Date parse(final String text, final Locale locale) throws ParseException {
                return createDateFormat(locale).parse(text);
            }

            public String print(final Date object, final Locale locale) {
                return createDateFormat(locale).format(object);
            }

            //Генерирует форматтер
            private SimpleDateFormat createDateFormat(final Locale locale) {
                //Считываем формат из message_**.properties по указанному полю
                String format = source.getMessage("date.format", null, locale);
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.setLenient(false);
                return dateFormat;
            }
        };
    }
}