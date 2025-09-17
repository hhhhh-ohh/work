package com.wanmi.sbc.common.util.excel.impl;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.ColumnRender;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by mac on 2017/5/6.
 */
public class SpelColumnRender<T> implements ColumnRender<T> {

    private String expString;

    /**
     * 时间格式化
     */
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式化
     */
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SpelColumnRender(String expString) {
        this.expString = expString;
    }

    @Override
    public void render(Cell cell, T object) {
        ExpressionParser parser = new SpelExpressionParser();
        Object o;
        try {
            Expression exp = parser.parseExpression(expString);
            o = exp.getValue(object);
        } catch (SpelEvaluationException e) {
            cell.setCellValue("");
            return;
        }
        if (o == null) {
            cell.setCellValue("");
            return;
        }

        Class<?> aClass = o.getClass();
        //
        if (aClass.isAssignableFrom(String.class)) {
            try {
                cell.setCellValue((String) o);
            }catch (Exception e){
                cell.setCellValue("");
            }
        } else if (aClass.isAssignableFrom(Integer.class)) {
            cell.setCellValue(String.valueOf(o));
        }
        //
        else if (aClass.isAssignableFrom(LocalDateTime.class)) {
            LocalDateTime dd = (LocalDateTime) o;
            cell.setCellValue(dd.format(timeFormatter));
        } else if (aClass.isAssignableFrom(LocalDate.class)) {
            LocalDate dd = (LocalDate) o;
            cell.setCellValue(dd.format(dateFormatter));
        } else if (aClass.isAssignableFrom(BigDecimal.class)) {
            BigDecimal n = (BigDecimal) o;
            StringBuilder pattern = new StringBuilder("#,##0.00");
            int i = n.scale() - Constants.TWO;
            int j = Constants.ZERO;
            // 物流体积允许小数点后6位，最多循环4次
            while (i-->Constants.ZERO && j++ < Constants.FOUR){
                pattern.append("0");
            }
            cell.setCellValue(new java.text.DecimalFormat(pattern.toString()).format(o));
        } else if (aClass.isAssignableFrom(Long.class)) {
            cell.setCellValue(String.valueOf(o));
        } else if (aClass.isAssignableFrom(Double.class)) {
            cell.setCellValue(String.valueOf(o));
        }

        //
        else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"未检出的类型");
        }
    }

}
