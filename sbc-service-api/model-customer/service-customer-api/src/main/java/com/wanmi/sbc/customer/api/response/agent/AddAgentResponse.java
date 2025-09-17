package com.wanmi.sbc.customer.api.response.agent;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @program: sbc-micro-service
 * @description: 添加代理商返回结果
 * @create: 2020-04-01 15:05
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAgentResponse extends BasicResponse {



    /**
     * 代理商名称
     */
    @Schema(name = "代理商名称")
    @NotBlank(message = "代理商名称不能为空")
    private String agentName;


    /**
     * 省ID
     */
    @Schema(name = "省ID")
    @NotNull(message = "省ID不能为空")
    private Long provinceId;

    /**
     * 市ID
     */
    @Schema(name = "市ID")
    @NotNull(message = "市ID不能为空")
    private Long cityId;

    /**
     * 区ID
     */
    @Schema(description = "区ID")
    @NotNull(message = "区ID不能为空")
    private Long areaId;

    /**
     * 街道ID
     */
    @Schema(description = "街道ID")
    @NotNull(message = "街道ID不能为空")
    private Long streetId;


    /**
     * 学校名称
     */
    @Schema(name = "学校名称")
    @NotBlank(message = "学校名称不能为空")
    private String schoolName;

    /**
     * 店铺名称
     */
    @Schema(name = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String shopName;

    /**
     * 店铺负责人
     */
    @Schema(name = "店铺负责人")
    @NotBlank(message = "店铺负责人不能为空")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Schema(name = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    /**
     * 营业执照URL
     */
    @Schema(name = "营业执照URL")
    @NotBlank(message = "营业执照URL不能为空")
    private String businessLicense;

    /**
     * 银行卡号
     */
    @Schema(name = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String bankAccount;





}
