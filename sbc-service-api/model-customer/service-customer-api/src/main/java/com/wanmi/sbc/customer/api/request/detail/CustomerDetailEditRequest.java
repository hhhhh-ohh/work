package com.wanmi.sbc.customer.api.request.detail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by CHENLI on 2017/4/20.
 */
@Schema
@Data
public class CustomerDetailEditRequest extends BaseRequest {
    /**
     * 会员详细信息标识UUID
     */
    @Schema(description = "会员详细信息标识UUID")
    private String customerDetailId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 省
     */
    @Schema(description = "省")
    private Long provinceId;

    /**
     * 市
     */
    @Schema(description = "市")
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String customerAddress;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    private String contactName;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String contactPhone;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员")
    private String employeeId;
}
