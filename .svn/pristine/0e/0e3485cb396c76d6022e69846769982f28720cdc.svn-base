package com.wanmi.sbc.common.util;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.ExcelLoopFunction;
import com.wanmi.sbc.common.util.excel.ExcelLoopRowResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 * Excel常用工具
 * @className ExcelUtils
 * @author zhengyang
 * @date 2021/8/7 14:45
 **/
public final class ExcelUtils {
    private ExcelUtils(){

    }

    /***
     * Excel后缀名
     */
    private final static String[] EXCEL_EXT = new String[] {"xls","xlsx"};

    /***
     * 判断一个后缀名是否Excel
     * @param ext   后缀名
     * @return      是否Excel
     */
    public static Boolean isExcelExt(String ext){
        if (StringUtils.isNotEmpty(ext)) {
            for (String s : EXCEL_EXT) {
                if (s.equals(ext.trim())) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /***
     * 校验模板文件是否正确
     * @param workbook              Excel文档对象
     * @param checkFunction         校验方法
     */
    public static void checkExcel(Workbook workbook, Function<Cell[], Boolean> checkFunction) {
        if (Objects.isNull(workbook) || Objects.isNull(checkFunction)) {
            return;
        }
        try {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            short cellNum = row.getLastCellNum();
            Cell[] cells = new Cell[cellNum];
            for (int i = 0; i < cellNum; i++) {
                Cell cell = null;
                if ((cell = row.getCell(i)) == null) {
                    cell = row.createCell(i);
                }
                cells[i] = cell;
            }
            if(!checkFunction.apply(cells)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        } catch (Exception var5) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    /***
     * Excel导入共通
     * @param workbook	导入Excel
     * @param maxCell	最大行数
     * @param function	循环执行的方法
     * @param <T>	    泛型
     * @return	        Excel转成的对象
     */
    public static <T> ExcelImportResult<T> importCommon(Workbook workbook, int maxCell, ExcelLoopFunction<T> function){
        List<T> dataList = new ArrayList<>();
        boolean isError = false;
        Sheet sheet = workbook.getSheetAt(0);
        // 获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        // 获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        CellStyle style = workbook.createCellStyle();
        String ext = "xls";
        // 根据workBook实现类对象判断Excel后缀名
        if(workbook instanceof XSSFWorkbook){
            ext = "xlsx";
        }
        ImportCount importCount = new ImportCount(ext);
        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (Objects.isNull(row)) {
                continue;
            }
            Cell[] cells = new Cell[maxCell];
            boolean isNotEmpty = false;
            for (int i = 0; i < maxCell; i++) {
                Cell cell = null;
                if ((cell = row.getCell(i)) == null) {
                    cell = row.createCell(i);
                }
                cells[i] = cell;
                if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                    isNotEmpty = true;
                }
            }
            // 列数据都为空，则跳过去
            if (!isNotEmpty) {
                continue;
            }
            ExcelLoopRowResult rowResult = function.apply(cells, style);
            if (!rowResult.isError()) {
                dataList.add((T) rowResult.getResult());
                importCount.successIncrement();
            } else {
                importCount.errorIncrement();
            }
            isError = isError || rowResult.isError();
        }
        String importCountJson = JSON.toJSONString(importCount);
        if (importCount.getErrorCount() == 0 && importCount.getSuccessCount() == 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"导入数据有误!"}, importCountJson);
        }
        if (isError) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"导入数据有误!"}, importCountJson);
        }
        return new ExcelImportResult<>(dataList, importCountJson);
    }

    /***
     * 从Excel拉取指定单元格的数据
     * @param workbook	导入Excel
     * @param cellIndex	单元格下标
     * @param distinct  是否去重
     * @return	        单元格指定下标数据汇聚成的List
     */
    public static List<String> getCellValues(Workbook workbook, int cellIndex, boolean distinct){
        List<String> dataList = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        // 获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        // 获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (Objects.isNull(row)) {
                continue;
            }

            Cell cell = null;
            if ((cell = row.getCell(cellIndex)) == null) {
                cell = row.createCell(cellIndex);
            }
            String cellVal = ExcelHelper.getValue(cell);
            if (StringUtils.isNotBlank(cellVal)) {
                dataList.add(cellVal);
            }
        }
        if (WmCollectionUtils.isNotEmpty(dataList) && distinct) {
            return dataList.parallelStream().distinct().collect(Collectors.toList());
        }
        return dataList;
    }

    /**
     * 导出正确和失败的统计
     */
    public static class ImportCount implements Serializable {

        public ImportCount(String ext){
            this.ext = ext;
            successCount = new AtomicInteger(0);
            errorCount = new AtomicInteger(0);
        }

        /***
         * 成功数量
         */
        private AtomicInteger successCount;

        /***
         * 失败数量
         */
        private AtomicInteger errorCount;

        /***
         * 文件后缀名
         */
        private String ext;

        public void successIncrement(){
            successCount.incrementAndGet();
        }

        public void errorIncrement(){
            errorCount.incrementAndGet();
        }

        public Integer getSuccessCount() {
            return successCount.get();
        }

        public Integer getErrorCount() {
            return errorCount.get();
        }

        public String getExt() {
            return ext;
        }
    }
}
