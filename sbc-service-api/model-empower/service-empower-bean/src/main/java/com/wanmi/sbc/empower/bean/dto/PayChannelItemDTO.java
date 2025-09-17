package com.wanmi.sbc.empower.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.IsOpen;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sunkun on 2017/8/9.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class PayChannelItemDTO extends BaseRequest {

    @Schema(description = "支付渠道id")
    private Long id;

    @Schema(description = "是否开启")
    private IsOpen IsOpen;
}
