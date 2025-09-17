package com.wanmi.sbc.common.util;

import lombok.Data;

import java.util.List;

/***
 * Excel导入返回值
 * @className ExcelImportResult
 * @author zhengyang
 * @date 2021/8/16 17:08
 **/
@Data
public class ExcelImportResult<T> {

    /***
     * 导入的值
     */
    private List<T> data;

    /***
     * 返回值
     */
    private String msg;

    /***
     * 构建一个返回值对象
     * @param data  Excel导入集合
     * @param msg   Excel导入返回数据
     * @return      返回值对象
     */
    public ExcelImportResult(List<T> data, String msg) {
        setData(data);
        setMsg(msg);
    }
}
