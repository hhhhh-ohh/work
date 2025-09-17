package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoRequest extends BaseRequest {

    private static final long serialVersionUID = 946149820871022491L;

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
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 客户等级
     */
    @Schema(description = "客户等级")
    private Long customerLevelId;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    private BigDecimal customerLevelDiscount;

    /**
     * 是否需要显示规格明细
     * 0:否,1:是
     */
    @Schema(description = "是否需要显示规格明细", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isHavSpecText;

    /**
     * 是否需要设置客户商品全局数量
     * 0:否,1:是
     */
    @Schema(description = "是否需要设置客户商品全局数量", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isHavCusGoodsNum;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除，0: 否, 1: 是")
    private DeleteFlag deleteFlag;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    private Boolean showLabelFlag;

    @Schema(description = "当showLabelFlag=true时，true:返回开启状态的标签，false或null:所有标签")
    private Boolean showSiteLabelFlag;

}
