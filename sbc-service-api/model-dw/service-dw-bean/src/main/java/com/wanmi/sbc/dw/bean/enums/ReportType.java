package com.wanmi.sbc.dw.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @ClassName: com.wanmi.sbc.dw.bean.enums
 * @Description: 报表类型
 * @Author: 何军红
 * @Time: 11:30 11:30
 */

@Schema
public enum ReportType {

    @ApiEnumProperty("0:天报表")
    DAY_REPORT(0, "天报表"),


    @ApiEnumProperty("1:商品报表")
    GOODS_REPORT(1, "商品报表"),


    @ApiEnumProperty("2:类目报表")
    CATE_REPORT(2, "类目报表"),


    @ApiEnumProperty("3:品牌报表")
    BRAND_REPORT(3, "品牌报表");

    private Integer typeId;
    private String typeName;

    ReportType(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public static ReportType getEnum(String typeName) {
        switch (typeName) {
            case "天报表":
                return DAY_REPORT;
            case "商品报表":
                return GOODS_REPORT;
            case "类目报表":
                return CATE_REPORT;
            case "品牌报表":
                return BRAND_REPORT;
            default:
                return null;
        }

    }

    public Integer getTypeId() {
        return typeId;
    }
    
}