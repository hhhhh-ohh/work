package com.wanmi.sbc.customer.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 公司信息参数
 * Created by daiyitian on 2017/5/12.
 */
@Schema
@Data
public class CompanyInfoAllModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long companyInfoId;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;

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
    private String detailAddress;

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
     * 版权信息
     */
    @Schema(description = "版权信息")
    private String copyright;

    /**
     * 公司简介
     */
    @Schema(description = "公司简介")
    private String companyDescript;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private String operator;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型-0：平台自营 1：第三方商家")
    private BoolFlag companyType;

    /**
     * 一对多关系，多个SPU编号
     */
    @Schema(description = "一对多关系，多个SPU编号")
    private List<String> goodsIds = new ArrayList<>();

    /**
     * 社会信用代码
     */
    @Schema(description = "社会信用代码")
    private String socialCreditCode;

    /**
     * 住所
     */
    @Schema(description = "住所")
    private String address;

    /**
     * 法定代表人
     */
    @Schema(description = "法定代表人")
    private String legalRepresentative;

    /**
     * 注册资本
     */
    @Schema(description = "注册资本")
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    @Schema(description = "成立日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime foundDate;

    /**
     * 营业期限自
     */
    @Schema(description = "营业期限自")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime businessTermStart;

    /**
     * 营业期限至
     */
    @Schema(description = "营业期限至")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime businessTermEnd;

    /**
     * 经营范围
     */
    @Schema(description = "经营范围")
    private String businessScope;

    /**
     * 营业执照副本电子版
     */
    @Schema(description = "营业执照副本电子版")
    private String businessLicence;

    /**
     * 法人身份证正面
     */
    @Schema(description = "法人身份证正面")
    private String frontIDCard;

    /**
     * 法人身份证反面
     */
    @Schema(description = "法人身份证反面")
    private String backIDCard;


    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 是否确认打款
     */
    @Schema(description = "是否确认打款")
    private BoolFlag remitAffirm;

    /**
     * 入驻时间(第一次审核通过时间)
     */
    @Schema(description = "入驻时间(第一次审核通过时间)")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;
}
