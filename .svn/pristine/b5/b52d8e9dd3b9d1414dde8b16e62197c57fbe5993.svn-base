package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>支付网关VO</p>
 * Created by of628-wenzhi on 2018-08-10-下午3:42.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayGatewayVO implements Serializable{

    private static final long serialVersionUID = 1725819478142926858L;
    @Schema(description = "支付网关id")
    private Long id;

    /**
     * 网关名称
     */
    @Schema(description = "网关名称")
    private PayGatewayEnum name;

    /**
     * 网关别名
     */
    @Schema(description = "网关别名")
    private String alias;

    /**
     * 是否开启: 0关闭 1开启
     */
    @Schema(description = "是否开启")
    private IsOpen isOpen;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 是否聚合支付
     */
    @Schema(description = "是否聚合支付")
    private Boolean type;

    /**
     * 支付网关配置
     */
    @Schema(description = "支付网关配置")
    private PayGatewayConfigVO config;

    /**
     * 支付项
     */
    @Schema(description = "支付项")
    private List<PayChannelItemVO> payChannelItemList;
}
