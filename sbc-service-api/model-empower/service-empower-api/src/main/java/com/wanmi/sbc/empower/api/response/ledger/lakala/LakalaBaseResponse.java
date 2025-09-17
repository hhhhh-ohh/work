package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 拉卡拉响应基类
 */
@Schema
@Data
public class LakalaBaseResponse {

    /**
     * 接口版本号
     * 当前默认 1.0
     */
    @Schema(description = "接口版本号")
    private String version;

    /**
     * 四方机构自定义订单编号
     * 建议：平台编号+14位年月日时（24小时制）分秒+8位的随机数（同一接入机构不重复）
     */
    @Schema(description = "四方机构自定义订单编号")
    private String orderNo;

    /**
     * 机构号
     * 签约方所属拉卡拉机构
     */
    @Schema(description = "机构号")
    private String orgCode;
}
