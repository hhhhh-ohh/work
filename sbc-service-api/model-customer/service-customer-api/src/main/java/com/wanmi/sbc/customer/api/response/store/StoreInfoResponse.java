package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询店铺基本信息返回
 * Created by CHENLI on 2017/11/2.
 */
@Schema
@Data
public class StoreInfoResponse extends BasicResponse {
    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 使用的运费模板类别(0:店铺运费,1:单品运费)
     */
    @Schema(description = "使用的运费模板类别")
    private DefaultFlag freightTemplateType;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String supplierCode;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 店铺店招
     */
    @Schema(description = "店铺店招")
    private String storeSign;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String contactPerson;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String contactMobile;

    /**
     * 联系邮箱
     */
    @Schema(description = "联系邮箱")
    @SensitiveWordsField(signType = SignWordType.EMAIL)
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
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String addressDetail;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String accountName;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState auditState;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    private String auditReason;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 店铺关闭原因
     */
    @Schema(description = "店铺关闭原因")
    private String storeClosedReason;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    @Schema(description = "账号禁用原因")
    private String accountDisableReason;

    /**
     * 签约开始日期
     */
    @Schema(description = "签约开始日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private BoolFlag companyType;

    /**
     * 申请入驻时间
     */
    @Schema(description = "申请入驻时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家")
    private StoreType storeType;

    /**
     * 商家来源类型 0:商城入驻 1:linkMall初始化 2:vop初始化
     */
    @Schema(description = "商家来源类型 0:商城入驻 1:linkMall初始化 2:vop初始化")
    private CompanySourceType companySourceType;

    /**
     * 自提配置
     */
    @Schema(description = "自提配置")
    private Integer pickupState;

    /**
     * 二次签约审核状态
     */
    @Schema(description = "二次签约审核状态")
    private CheckState contractAuditState;

    /**
     * 二次签约拒绝原因
     */
    @Schema(description = "二次签约拒绝原因")
    private String contractAuditReason;

    /**
     * 是都展示我知道了
     */
    @Schema(description = "是都展示我知道了")
    private Boolean isKnow;

}
