package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.GenderType;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 会员基本信息返回数据
 * Created by CHENLI on 2017/7/17.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerBaseInfoResponse extends BasicResponse {

    @Schema(description = "账号")
    private String customerDetailId;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerAccount;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

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
     * 业务员名称
     */
    @Schema(description = "业务员名称")
    private String employeeName;

    @Schema(description = "头像")
    private String headImg;

    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthDay;

    /**
     * 性别，0女，1男
     */
    @Schema(description = "性别，0女，1男")
    private GenderType gender;

    /**
     * 是否是企业会员
     */
    @Schema(description = "是否是企业会员")
    private Boolean isEnterpriseCustomer;

    /**
     * 企业信息
     */
    @Schema(description = "企业信息")
    private EnterpriseInfoVO enterpriseInfo;
}
