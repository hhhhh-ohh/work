package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateSumVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 店铺基本信息
 * (安全考虑只保留必要信息,隐藏前端会员无需知道的信息)
 * Created by bail on 2017/11/29.
 */
@Schema
@Data
public class StoreDocumentResponse extends BasicResponse {
    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺小程序码
     */
    @Schema(description = "店铺小程序码")
    private String smallProgramCode;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型",contentSchema = com.wanmi.sbc.customer.bean.enums.CompanyType.class)
    private Integer companyType;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    private String contactPerson;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String contactMobile;

    /**
     * 联系邮箱
     */
    @Schema(description = "联系邮箱")
    private String contactEmail;

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
    private String addressDetail;

    /**
     * 社会信用代码
     */
    @Schema(description = "社会信用代码")
    private String socialCreditCode;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;

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
     * 类目经营资质图片
     */
    @Schema(description = "类目经营资质图片")
    private List<String> catePicList;

    /**
     * 品牌经营资质图片
     */
    @Schema(description = "品牌经营资质图片")
    private List<String> brandPicList;

    /**
     * 店铺服务
     */
    @Schema(description = "店铺服务")
    private StoreEvaluateSumVO storeEvaluateSumVO;

    /**
     * 店铺spu总数
     */
    @Schema(description = "spu总数")
    private long goodsCount;

    /**
     * 店铺sku总数
     */
    @Schema(description = "sku总数")
    private long goodsInfoCount;

    /**
     * 店铺关注总数
     */
    @Schema(description = "收藏总数")
    private long followCount;

}
