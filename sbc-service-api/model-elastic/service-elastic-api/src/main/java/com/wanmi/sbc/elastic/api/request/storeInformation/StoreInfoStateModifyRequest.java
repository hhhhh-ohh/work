package com.wanmi.sbc.elastic.api.request.storeInformation;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yangzhen
 * @Description // 商家店铺信息
 * @Date 18:30 2020/12/7
 * @Param
 * @return
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class StoreInfoStateModifyRequest extends BaseRequest {

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

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
     * 是否确认打款 (-1:全部,0:否,1:是)
     */
    @Schema(description = "是否确认打款(-1:全部,0:否,1:是)")
    private Integer remitAffirm;

    /**
     * 二次签约审核状态
     */
    @Schema(description = "二次签约审核状态")
    private Integer contractAuditState;

    /**
     * 二次签约拒绝原因
     */
    @Schema(description = "二次签约拒绝原因")
    private String contractAuditReason;

    /**
     * 公司id
     */
    @Schema(description = "公司id")
    private Long companyInfoId;

}
