package com.wanmi.sbc.common.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/***
 * 循环Excel单行执行方法
 * @className ExcelLoopFunction
 * @author zhengyang
 * @date 2021/8/2 17:14
 **/
@FunctionalInterface
public interface ExcelLoopFunction<T> {

    /***
     * 循环Excel执行方法
     * @param cells 单行单元格集合
     * @param style 单元格样式
     * @return      返回值
     */
    ExcelLoopRowResult<T> apply(Cell[] cells, CellStyle style);
}
