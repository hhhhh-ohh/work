package com.wanmi.sbc.customer.api.request.agent;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @program: sbc-micro-service
 * @description: 添加代理商请求参数
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AddAgentRequest extends BaseRequest {

    @NotBlank(message = "代理商名称不能为空")
    @Schema(description = "代理商名称")
    private String agentName;

    @NotNull(message = "省ID不能为空")
    @Schema(description = "省ID")
    private Long provinceId;

    @NotNull(message = "市ID不能为空")
    @Schema(description = "市ID")
    private Long cityId;

    @NotNull(message = "区ID不能为空")
    @Schema(description = "区ID")
    private Long areaId;

    @NotNull(message = "街道ID不能为空")
    @Schema(description = "街道ID")
    private Long streetId;

    @NotBlank(message = "省名称不能为空")
    @Schema(description = "省名称")
    private String provinceName;

    @NotBlank(message = "市名称不能为空")
    @Schema(description = "市名称")
    private String cityName;

    @NotBlank(message = "区名称不能为空")
    @Schema(description = "区名称")
    private String areaName;

    @NotBlank(message = "街道名称不能为空")
    @Schema(description = "街道名称")
    private String streetName;

    @NotBlank(message = "学校名称不能为空")
    @Schema(description = "学校名称")
    private String schoolName;

    @NotBlank(message = "店铺名称不能为空")
    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺属性")
    private String shopAttribute;

    @Schema(description = "店铺地址")
    private String shopAddress;

    @NotBlank(message = "店铺负责人不能为空")
    @Schema(description = "店铺负责人")
    private String contactPerson;

    @NotBlank(message = "联系电话不能为空")
    @Schema(description = "联系电话")
    private String contactPhone;

    @NotBlank(message = "营业执照URL不能为空")
    @Schema(description = "营业执照URL")
    private String businessLicense;

    @Schema(description = "开户行")
    private String bankOpen;

    @Schema(description = "银行卡号")
    private String bankAccount;

    /**
     * 验证码
     */
    // @NotBlank(message = "验证码不能为空")
    // @Schema(description = "验证码")
    // private String verifyCode;

}
