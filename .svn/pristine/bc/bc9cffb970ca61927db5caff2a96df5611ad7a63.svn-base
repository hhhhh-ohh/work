package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * <p>用于接受达达配送回调参数</p>
 *
 * @author zhangwenchang
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaCallBackRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 达达运单号
     */
    @Schema(description = "达达运单号")
    private String client_id;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String order_id;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    @NotNull
    private Integer order_status;

    /**
     * 取消原因
     */
    @Schema(description = "取消原因")
    private String cancel_reason;

    /**
     * 取消理由id
     */
    @Schema(description = "订单取消原因来源")
    private Integer cancel_from;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Long update_time;

    /**
     * 签名
     */
    @Schema(description = "签名")
    private String signature;

    /**
     * 配送员id
     */
    @Schema(description = "配送员id")
    private Integer dm_id;

    /**
     * 配送员姓名
     */
    @Schema(description = "配送员姓名")
    private String dm_name;

    /**
     * 配送员手机号
     */
    @Schema(description = "配送员手机号")
    private String dm_mobile;

    /**
     * 更新人
     */
    @Schema(description = "更新人", hidden = true)
    private String updatePerson;

    @Schema(description = "插件类型", hidden = true)
    private PluginType pluginType;
}