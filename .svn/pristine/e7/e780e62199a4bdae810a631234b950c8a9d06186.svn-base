package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.bean.dto.CustomerLevelDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.order.bean.enums.FollowFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-30
 */
@Data
@Builder
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private List<Long> followIds;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;

    /**
     * 批量sku
     */
    @Schema(description = "批量sku")
    private List<GoodsInfoDTO> goodsInfos;

    /**
     * 会员编号
     */
    @Schema(description = "会员编号")
    private String customerId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @Range(min = 1)
    private Long goodsNum;

    /**
     * 收藏标识
     */
    @Schema(description = "收藏标识")
    private FollowFlag followFlag;

    /**
     * 校验库存
     */
    @Schema(description = "是否校验库存",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    @Builder.Default
    private Boolean verifyStock = Boolean.TRUE;

    /**
     * 当前客户等级
     */
    @Schema(description = "当前客户等级")
    private CustomerLevelDTO customerLevel;

    /**
     * 是否赠品 true 是 false 否
     */
    @Schema(description = "是否赠品",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    @Builder.Default
    private Boolean isGift = Boolean.FALSE;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    /**
     * 终端来源
     */
    @Schema(description = "终端来源", hidden = true)
    private TerminalSource terminalSource;

    /**
     * 第一次从商品列表和详情加入购物车时的价格
     **/
    @Schema(description = "加入购物车时的价格")
    private BigDecimal firstPurchasePrice;
}
