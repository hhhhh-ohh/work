package com.wanmi.sbc.pay.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Created by sunkun on 2017/8/10.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayGatewayReponse extends BasicResponse {

    /**
     * 网关id
     */
    @Schema(description = "网关id")
    private Long id;

    /**
     * 网关名称
     */
    @Schema(description = "网关名称")
    private String name;

    /**
     * 网关别名
     */
    @Schema(description = "网关别名")
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
    private Boolean type;

    /**
     * 网关配置
     */
    @Schema(description = "网关配置")
    private PayGatewayConfigVO payGatewayConfig;

    /**
     * 支付渠道项
     */
    @Schema(description = "支付渠道项")
    private List<PayChannelItemVO> channelItemList;
}
