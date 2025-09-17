package com.wanmi.sbc.common.util.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * LocalDate 转换器
 * Created by aqlu on 15/12/6.
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
}