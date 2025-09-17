package com.wanmi.ares.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/13 13:56
 * @Description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GrouponOverview extends MarketingAnalysisBase implements Serializable {

    /**
     * 发起分享人数
     */
    private Long shareCount;

    /**
     * 分享访客人数
     */
    private Long shareVisitorsCount;

    /**
     * 分享参团数
     */
    private Long shareGrouponCount;

    /**
     * 拼团订单数
     */
    private Long grouponTradeCount;

    /**
     * 拼团人数
     */
    private Long grouponPersonCount;

    /**
     * 成团订单数
     */
    private Long alreadyGrouponTradeCount;

    /**
     * 成团人数
     */
    private Long alreadyGrouponPersonCount;

    /**
     * 拼团-成团转化率（成团人数 / 拼团人数）
     */
    private BigDecimal grouponRoi;

    /**
     * 商品名称
     */
    private String goodsInfoName;

    /**
     * 商品编码
     */
    private String goodsInfoNo;

    /**
     * 规格信息
     */
    private String specDetails;

    private String groupId;

    private String goodsInfoId;

    /**
     * 访问-拼团团转化率
     */
    private BigDecimal uvGrouponRoi;

    /**
     * 成团率
     */
    private BigDecimal alreadyGrouponRoi;

    public Long getShareCount() {
        return shareCount == null ? Long.valueOf(0) : shareCount;
    }

    public Long getShareVisitorsCount() {
        return shareVisitorsCount == null ? Long.valueOf(0) : shareVisitorsCount;
    }

    public Long getShareGrouponCount() {
        return shareGrouponCount == null ? Long.valueOf(0) : shareGrouponCount;
    }

    public Long getGrouponTradeCount() {
        return grouponTradeCount == null ? Long.valueOf(0) : grouponTradeCount;
    }

    public Long getGrouponPersonCount() {
        return grouponPersonCount == null ? Long.valueOf(0) : grouponPersonCount;
    }

    public Long getAlreadyGrouponTradeCount() {
        return alreadyGrouponTradeCount == null ? Long.valueOf(0) : alreadyGrouponTradeCount;
    }

    public Long getAlreadyGrouponPersonCount() {
        return alreadyGrouponPersonCount == null ? Long.valueOf(0) : alreadyGrouponPersonCount;
    }

    public BigDecimal getGrouponRoi() {
        return grouponRoi == null ? BigDecimal.ZERO : grouponRoi;
    }
}
