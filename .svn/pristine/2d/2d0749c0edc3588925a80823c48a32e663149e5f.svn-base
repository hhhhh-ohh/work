package com.wanmi.sbc.elastic.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.elastic.api.request.base.EsBaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponStatus;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 商品库查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsCouponScopePageRequest extends EsBaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -4485444157498437822L;

    /**
     * 优惠券活动Id集合
     */
    private List<String> couponActivityIds;

    /**
     * 优惠券id集合
     */
    private List<String> couponIdList;

    /**
     * 品牌List
     */
    private List<Long> brandIds;

    /**
     * 平台分类List
     */
    private List<Long> cateIds;

    /**
     * 店铺分类List
     */
    private List<Long> storeCateIds;

    /**
     * 商品集合
     */
    private List<String> goodsInfoIds;

    /**
     * 用户店铺 + 等级 Map
     */
    Map<Long, CommonLevelVO> levelMap;

    /**
     * 店铺Id集合
     */
    List<Long> storeIds;

    /**
     * 优惠券分类Id
     */
    private String couponCateId;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    private CouponType couponType;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    private List<CouponType> couponTypes;

    /**
     * 优惠券类型 0 普通优惠券  2 O2O运费券
     */
    @Schema(description = "优惠券类型")
    private PluginType pluginType;

    /**
     * 当前缓存门店id
     */
    private Long storeFrontId;

    /***
     * 是否过滤门店类型
     */
    private BoolFlag filterPlugin;

}
