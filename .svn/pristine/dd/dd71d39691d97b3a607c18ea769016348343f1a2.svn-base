package com.wanmi.sbc.elastic.bean.vo.storeInformation;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: yangzhen
 * @Date: Created In 10:02 2020/12/17
 * @Description: 模糊匹配返回店铺信息
 */
@Data
@Schema
public class EsStoreInfoVO extends BasicResponse {


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
    private Integer companyType;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型(0、供应商 1、商家)")
    private StoreType storeType;

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
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 账号关闭原因
     */
    @Schema(description = "账号关闭原因")
    private String storeClosedReason;

}
