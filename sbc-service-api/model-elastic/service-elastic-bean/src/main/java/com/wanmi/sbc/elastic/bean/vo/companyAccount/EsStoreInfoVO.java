package com.wanmi.sbc.elastic.bean.vo.companyAccount;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ES商品实体类
 * 以SKU维度
 * Created by dyt on 2017/4/21.
 */
@Data
@Schema
public class EsStoreInfoVO extends BasicResponse {

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    private String accountName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private Integer companyType;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态")
    private Integer auditState;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    private String auditReason;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private Integer accountState;

    /**
     * 账号禁用原因
     */
    @Schema(description = "账号禁用原因")
    private String accountDisableReason;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private Integer storeState;

    /**
     * 账号关闭原因
     */
    @Schema(description = "账号关闭原因")
    private String storeClosedReason;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家")
    private Integer storeType;

    /**
     * 是否是主账号
     */
    @Schema(description = "店铺名称")
    private String isMasterAccount;

    /**
     * 店铺删除状态
     */
    @Schema(description = "店铺删除状态")
    private DeleteFlag storeDelFlag;

    /**
     * 公司删除状态
     */
    @Schema(description = "公司删除状态")
    private DeleteFlag companyInfoDelFlag;

    /**
     * 员工删除状态
     */
    @Schema(description = "员工删除状态")
    private DeleteFlag employeeDelFlag;

    /**
     * 是否确认打款 (-1:全部,0:否,1:是)
     */
    @Schema(description = "是否确认打款(-1:全部,0:否,1:是)")
    private Integer remitAffirm;


    /**
     * 创建结束时间
     */
    @Schema(description = "入驻时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;


    /**
     * 创建结束时间
     */
    @Schema(description = "合同有效期开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;


    /**
     * 创建结束时间
     */
    @Schema(description = "合同有效期截至时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

}
