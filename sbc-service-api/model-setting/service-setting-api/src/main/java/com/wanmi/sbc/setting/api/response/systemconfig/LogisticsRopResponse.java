package com.wanmi.sbc.setting.api.response.systemconfig;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by feitingting on 2019/11/7.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsRopResponse extends BasicResponse {
    /**
     * 配置ID
     */
    @Schema(description = "配置ID")
    private Long configId;

    /**
     * 快递100 api key
     */
    @Schema(description = "快递100 api key")
    private String deliveryKey;

    /**
     * 客户key
     */
    @Schema(description = "客户key")
    private String customerKey;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private String callBackUrl;

    /**
     * 状态 0:未启用1:已启用
     */
    @Schema(description = "状态 0:未启用1:已启用")
    private Integer status;

    /**
     * 订阅推送状态 0:关闭1:开启
     */
    @Schema(description ="订阅推送状态 0:关闭1:开启")
    private Integer subscribeStatus;
}
