package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>预约活动商品信息表分页查询请求参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGoodsInfoSimplePageDTO extends BaseQueryRequest {


    private static final long serialVersionUID = 1154213435297457823L;


    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 预约类型 0：不预约不可购买  1：不预约可购买
     */
    @Schema(description = "预约类型 0：不预约不可购买  1：不预约可购买")
    private Integer appointmentType;


    /**
     * 状态  0:全部 1:进行中，2 已暂停 3 未开始 4. 已结束
     */
    @Schema(description = "状态  0:全部 1:进行中，2 已暂停 3 未开始 4. 已结束")
    private AppointmentStatus queryTab;


    /**
     * 商品skuId
     */
    @Schema(description = "批量商品skuId")
    private List<String> goodsInfoIds;

    /**
     * 店铺storeId
     */
    @Schema(description = "店铺storeId")
    private List<Long> storeIds;

    /**
     * 店铺storeId
     */
    @Schema(description = "店铺storeId")
    private Long storeId;

    /**
     * 排序标识
     * 0:销量倒序
     * 1:好评数倒序
     * 2:评论率倒序
     * 3:排序号倒序
     * 4:排序号倒序
     * 5:成才数倒序
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;

    /**
     * 删除标识,0: 未删除 1: 已删除
     */
    @Schema(description = "删除标识,0: 未删除 1: 已删除")
    private DeleteFlag delFlag;
}