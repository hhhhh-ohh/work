package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>对账单返回数据结构</p>
 * Created by of628-wenzhi on 2017-12-08-上午11:16.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecordVO extends BasicResponse {

    private static final long serialVersionUID = 6489586085599213516L;
    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long supplierId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 总计金额，单位元，格式："￥#,###.00"
     */
    @Schema(description = "总计金额")
    private String totalAmount;

    /**
     * 存在各支付项金额的Map，key: 枚举PayWay的name, value: 金额，单位元，格式："￥#,###.00"
     */
    @Schema(description = "存在各支付项金额的Map(key: 枚举PayWay的name, value: 金额，单位元)")
    private Map<String, String> payItemAmountMap;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private StoreType storeType;
}
