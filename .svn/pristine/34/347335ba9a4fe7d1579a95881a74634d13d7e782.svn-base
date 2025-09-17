package com.wanmi.sbc.marketing.api.request.bookingsale;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.marketing.bean.enums.BookingSaleTimeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>预售活动商品信息表分页查询请求参数</p>
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingGoodsInfoSimplePageRequest extends BaseQueryRequest {


    private static final long serialVersionUID = -9027973540251141452L;
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
     * 预售类型 0：全款预售  1：定金预售
     */
    @Schema(description = "预售类型 0：全款预售  1：定金预售")
    private Integer bookingType;


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
     * 4:按定金支付数倒序
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 是否需要显示规格明细
     */
    @Schema(description = "是否显示规格")
    private Boolean havSpecTextFlag;

    /**
     * 预售时间筛选枚举，每个枚举之间是以or关系
     */
    @Schema(description = "预售时间筛选枚举")
    private List<BookingSaleTimeStatus> bookingSaleTimeStatuses;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;
}