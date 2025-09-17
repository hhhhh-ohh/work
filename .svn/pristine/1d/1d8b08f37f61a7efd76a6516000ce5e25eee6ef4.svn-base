package com.wanmi.sbc.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CouponGoodsPageRequest extends BaseRequest {

    /**
     * 分页条数
     */
    @Schema(description = "分页条数")
    private int pageSize;

    /**
     * 当前页数
     */
    @Schema(description = "当前页数")
    private int pageNum;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券id")
    private String couponId;

    /**
     * 优惠券活动id
     */
    @Schema(description = "优惠券活动id")
    private String activity;

    /**
     * 商品排序类型 - 同EsGoodsInfoQueryRequest
     * 0:最新倒序->按上下架时间倒序
     * 1:最新倒序->按上下架时间升序
     * 2:价格升序->按市场价倒序
     * 3:价格升序->按市场价升序
     */
    @Schema(description = "商品排序类型,0最新->按上下架时间倒序 1最新->按上下架时间升序 2价格->按市场价倒序 3价格->按市场价升序")
    private Integer sortType;

    /**
     * 排序标识
     * 0:最新倒序->按上下架时间倒序
     * 1:最新倒序->按上下架时间升序
     * 2:价格升序->按市场价倒序
     * 3:价格升序->按市场价升序
     * 4:销量倒序
     * 5:评论数倒序
     * 6:好评倒序
     * 7:收藏倒序
     *
     * sortType不知道哪里用到了。反正凑单页没用到，也不敢删
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 平台类目查询条件
     */
    @Schema(description = "平台类目id集合")
    private List<Long> cateIds;

    /**
     * 店铺分类查询条件
     */
    @Schema(description = "店铺分类查询条件")
    private List<Long> storeCateIds;

    /**
     * 品牌查询条件
     */
    @Schema(description = "品牌id集合")
    private List<Long> brandIds;

    /**
     * 是否自营
     */
    @Schema(description = "是否自营", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer companyType;

    /**
     * 搜索关键字
     */
    @Schema(description = "搜索关键字")
    private String keywords;

}
