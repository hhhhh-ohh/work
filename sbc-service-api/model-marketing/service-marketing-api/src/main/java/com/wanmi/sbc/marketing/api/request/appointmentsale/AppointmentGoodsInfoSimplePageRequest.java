package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>预约活动商品信息表分页查询请求参数</p>
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGoodsInfoSimplePageRequest extends BaseQueryRequest {


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
    private Long storeId;

    /**
     * 店铺storeIdList
     */
    @Schema(description = "店铺storeIdList")
    private List<Long> storeIds;

    /**
     * 排序标识
     * 0:销量倒序
     * 1:好评数倒序
     * 2:评论率倒序
     * 3:排序号倒序
     * 4:预约数倒序
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 是否需要显示规格明细
     */
    @Schema(description = "是否显示规格")
    private Boolean havSpecTextFlag;


    /**
     * 状态 0：预约进行中
     */
    @Schema(description = "预约状态 0：预约进行中")
    private Integer appointmentStatus;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;
}