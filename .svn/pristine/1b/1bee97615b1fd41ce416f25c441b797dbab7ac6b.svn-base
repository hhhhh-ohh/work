package com.wanmi.ares.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Objects;

/**
 * 平台搜索类型：0全部，1商家，2门店，3跨境商家
 */
@ApiEnum
public enum StoreSelectType {
    @ApiEnumProperty(value = "0全部")
    ALL("-1"),

    @ApiEnumProperty(value = "1商家")
    SUPPLIER("-12"),

    @ApiEnumProperty(value = "2门店")
    O2O("-13"),

    @ApiEnumProperty(value = "跨境商家")
    CROSS("-12");

    /***
     * 对应的模拟公司ID，用于统计
     */
    private String mockCompanyInfoId;

    StoreSelectType(String mockCompanyInfoId){
        this.mockCompanyInfoId = mockCompanyInfoId;
    }

    @JsonCreator
    public StoreSelectType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    public String getMockCompanyInfoId(){
        return mockCompanyInfoId;
    }

    /***
     * 判断一个CompanyId是否在枚举的MockId中
     * @param companyInfoId 公司ID
     * @return
     */
    public static boolean isMockCompanyInfoId(Long companyInfoId){
        if(Objects.isNull(companyInfoId)){
            return false;
        }
        return companyInfoId.equals(Long.parseLong(SUPPLIER.mockCompanyInfoId))
                || companyInfoId.equals(Long.parseLong(O2O.mockCompanyInfoId));
    }
}
