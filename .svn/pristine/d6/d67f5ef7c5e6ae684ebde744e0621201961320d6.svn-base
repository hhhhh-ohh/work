package com.wanmi.sbc.login;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录返回
 * Created by chenli on 2017/11/29
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginStoreResponse extends BasicResponse {

    /**
     * 店铺状态 0、开启 1、关店 2、过期
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 店铺关店原因
     */
    @Schema(description = "店铺关店原因")
    private String storeClosedReason;

    /**
     * 店铺有效期还剩10天时，开始提醒，每天倒计时提醒
     */
    @Schema(description = "店铺有效期还剩10天时，开始提醒，每天倒计时提醒")
    private int overDueDay;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 店铺类型
     */
    @Schema(description = "商家类型：1商家2直营门店")
    private StoreType storeType;

    /**
     * 商家编号
     */
    @Schema(description = "门店编号")
    private String companyCode;

    /**
     * 门店名称
     */
    @Schema(description = "门店名称")
    private String storeName;

}
