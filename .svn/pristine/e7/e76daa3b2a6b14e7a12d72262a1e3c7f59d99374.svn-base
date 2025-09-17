package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>用户分销排行榜VO</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@Schema
@Data
public class DistributionCustomerRankingVO extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 邀新人数
     */
    @Schema(description = "邀新人数")
    private Integer inviteCount;

    /**
     * 有效邀新人数
     */
    @Schema(description = "有效邀新人数")
    private Integer inviteAvailableCount;

    /**
     * 销售额(元)
     */
    @Schema(description = "销售额(元) ")
    private BigDecimal saleAmount;

    /**
     * 预估收益
     */
    @Schema(description = "预估收益")
    private BigDecimal commission;

    /**
     * 排行
     */
    @Schema(description = "排行")
    private String ranking;
    /**
     * 头像
     */
    @Schema(description = "头像")
    private String img;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String name;


}