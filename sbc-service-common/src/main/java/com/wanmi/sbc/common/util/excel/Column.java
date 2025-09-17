package com.wanmi.sbc.common.util.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mac on 2017/5/6.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Column {

    private String header;
    private ColumnRender render;

    // 合并单元格, 先不用了
    //private int rowSpan;
    //private int cellSpan;

    /**
     * 是否必填标识
     */
    private Boolean required = false;

    /**
     * 下拉选择
     */
    private String[] chooseArray;

    public Column (String header, ColumnRender render) {
        this.header = header;
        this.render = render;
    }

    public Column (String header, ColumnRender render, Boolean required) {
        this.header = header;
        this.render = render;
        this.required = required;
    }
}
