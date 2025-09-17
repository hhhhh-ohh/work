package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.order.bean.enums.FollowFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseQueryDTO extends BaseQueryRequest {

    private static final long serialVersionUID = 3635486109211612000L;

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
     * 收藏标识
     */
    @Schema(description = "收藏标识")
    private FollowFlag followFlag;

    /**
     * 校验库存
     */
    @Schema(description = "是否校验库存", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    @Default
    private Boolean verifyStock = Boolean.TRUE;

    /**
     * 当前客户等级
     */
    @Schema(description = "当前客户等级")
    private CustomerLevelVO customerLevel;

    /**
     * 是否赠品 true 是 false 否
     */
    @Schema(description = "是否赠品", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    @Default
    private Boolean isGift = Boolean.FALSE;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    @Schema(description = "区的区域码")
    private Long areaId;

    /**
     * 本地地址区域
     */
    @Schema(description = "本地地址区域")
    private PlatformAddress address;

    @Schema(description = "类型： 0-正常商品 1-跨境商品 2-o2o商品")
    private PluginType pluginType;

    @Schema(description = "是否根据pluginType查询")
    private BoolFlag pluginTypeFlag;

    @Schema(description = "门店ID")
    private Long storeId;
}
