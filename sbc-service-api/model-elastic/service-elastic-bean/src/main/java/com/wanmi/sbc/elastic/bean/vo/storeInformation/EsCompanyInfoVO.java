package com.wanmi.sbc.elastic.bean.vo.storeInformation;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.SignWordType;
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
 * @Author: songhanlin
 * @Date: Created In 16:02 2020/12/11
 * @Description: Es查询商家列表Response
 */
@Data
@Schema
public class EsCompanyInfoVO extends BasicResponse {


    /**
     * 商家主键
     */
    @Schema(description = "商家主键")
    private Long companyInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String accountName;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private BoolFlag companyType;

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
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 账号关闭原因
     */
    @Schema(description = "账号关闭原因")
    private String storeClosedReason;

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
     * 关联公司信息ID
     */
    @Schema(description = "关联公司信息ID")
    private Long relCompanyInfoId;

    /**
     * 关联店铺ID
     */
    @Schema(description = "关联店铺ID")
    private Long relStoreId;

}
