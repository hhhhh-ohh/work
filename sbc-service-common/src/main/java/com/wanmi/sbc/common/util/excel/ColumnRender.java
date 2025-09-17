package com.wanmi.sbc.common.util.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by mac on 2017/5/6.
 */
public interface ColumnRender<T> {
    void render(Cell row, T object);
}
