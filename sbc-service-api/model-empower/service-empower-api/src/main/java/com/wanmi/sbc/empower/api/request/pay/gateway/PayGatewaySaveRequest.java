package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.dto.PayChannelItemDTO;
import com.wanmi.sbc.empower.bean.dto.PayGatewayConfigDTO;
import com.wanmi.sbc.empower.bean.enums.IsOpen;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Created by sunkun on 2017/8/9.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class PayGatewaySaveRequest extends BaseRequest {

    private static final long serialVersionUID = 4304124969782172682L;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 网关id
     */
    @Schema(description = "网关id")
    private Long id;

    /**
     * 网关名称（英文名称，全大写）
     */
    @Schema(description = "网关名称（英文名称，全大写）")
    @NotNull
    private String name;

    /**
     * 网关别名
     */
    @Schema(description = "网关别名")
    @Length(max = 4)
    private String alias;

    /**
     * 是否开启
     */
    @Schema(description = "是否开启")
    private IsOpen isOpen;

    /**
     * 是否聚合支付
     */
    @Schema(description = "是否聚合支付", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean type = Boolean.TRUE;

    /**
     * 网关配置
     */
    @Schema(description = "网关配置")
    @Valid
    private PayGatewayConfigDTO payGatewayConfig;

    /**
     * 支付渠道项
     */
    @Schema(description = "支付渠道项")
    private List<PayChannelItemDTO> channelItemList;
}
