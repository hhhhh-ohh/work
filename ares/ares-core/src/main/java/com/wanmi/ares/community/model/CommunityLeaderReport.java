package com.wanmi.ares.community.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author edz
 * @className CommunityLeaderReport
 * @description TODO
 * @date 2023/8/11 10:05
 **/
@Data
public class CommunityLeaderReport {
    /**
     * 创建时间
     */
    private LocalDate createDate;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 团长ID
     */
    private String leaderId;

    /**
     * 团长会员ID
     */
    private String leaderCustomerId;

    /**
     * 团长账号
     */
    private String leaderAccount;

    /**
     * 团长名称
     */
    private String leaderName;

    /**
     * 服务订单数
     */
    private Long serviceOrderNum;

    /**
     * 服务订单总额
     */
    private BigDecimal serviceOrderTotalPrice;

    /**
     * 帮卖订单数
     */
    private Long assistOrderNum;

    /**
     * 帮卖订单总额
     */
    private BigDecimal assistOrderTotalPrice;
}
