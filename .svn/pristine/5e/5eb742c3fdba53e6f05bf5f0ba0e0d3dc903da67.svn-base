package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel;
import com.wanmi.sbc.marketing.bean.enums.ParticipateType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 正在进行的优惠券活动查询
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheQueryRequest extends BaseRequest {

    /**
     * 优惠券活动Id集合
     */
    private List<String> couponActivityIds;

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
//    Map<Long, CustomerLevel> levelMap;
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


    /**
     * 构建平台优惠券+店铺优惠券的查询条件
     *
     * @return
     */
    public Criteria getCriteria() {
        //店铺相关
        List<Criteria> storeCriteriaList = getStoreCriteria();
        //店铺券（符合等级的/正在进行的/Scope满足的）
        Criteria storeCriteria = new Criteria().andOperator(storeCriteriaList.toArray(new Criteria[storeCriteriaList.size()]));

        //平台相关
        List<Criteria> platformCriteriaList = getPlatformCriteria();
        //平台券（符合等级的/正在进行的/Scope满足的）
        Criteria platformCriteria = new Criteria().andOperator(platformCriteriaList.toArray(new Criteria[platformCriteriaList.size()]));

        //优惠券相关过滤，过滤出 平台 + 店铺
        Criteria couponCriteria = new Criteria();
        couponCriteria.orOperator(platformCriteria, storeCriteria);

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(couponCriteria);

        populateBaseCondition(criteria);

        //sbc查询优惠券只查询平台券和店铺券，PluginType是NORMAL的
        criteria.add(Criteria.where("couponActivity.pluginType").is(PluginType.NORMAL));

        return new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]));
    }


    public List<Criteria> getStoreCriteria() {
        //店铺相关
        List<Criteria> storeCriteriaList = this.getStoreCriteriaList();
        //店铺scope相关
        List<Criteria> storeScopeList = this.getStoreScope();
        if (CollectionUtils.isNotEmpty(storeScopeList)) {
            storeCriteriaList.add(new Criteria().orOperator(storeScopeList.toArray(new Criteria[storeScopeList.size()])));
        }
        return storeCriteriaList;
    }

    public List<Criteria> getPlatformCriteria() {
        //平台相关
        List<Criteria> platformCriteriaList = this.getPlatformCriteriaList();
        //平台scope相关
        if (CollectionUtils.isNotEmpty(brandIds) || CollectionUtils.isNotEmpty(cateIds)) {
            platformCriteriaList.add(this.getPlatformScope());
        }
        return platformCriteriaList;
    }

//    public List<Criteria> getStoreFrontCriteriaList() {
//        // 门店券相关
//        List<Criteria> criteriaList = new ArrayList<>();
//        if (PluginType.O2O == pluginType) {
//            criteriaList.add(Criteria.where("couponActivity.couponActivityType").is(CouponActivityType.ALL_COUPONS.toString()));
//            criteriaList.add(Criteria.where("couponActivity.startTime").lt(LocalDateTime.now()));
//            criteriaList.add(Criteria.where("couponActivity.endTime").gt(LocalDateTime.now()));
//            criteriaList.add(Criteria.where("couponActivity.pluginType").is(pluginType));
//            criteriaList.add(Criteria.where("couponActivity.auditState").is(AuditState.CHECKED));
//            criteriaList.add(Criteria.where("couponStoreIds").is(storeFrontId));
//        }
//        return criteriaList;
//    }
//
//    public List<Criteria> getPlatformStoreFrontCriteriaList() {
//        // 平台选择全部门店
//        List<Criteria> criteriaList = new ArrayList<>();
//        if (PluginType.O2O == pluginType) {
//            criteriaList.add(Criteria.where("couponActivity.couponActivityType").is(CouponActivityType.ALL_COUPONS.toString()));
////            criteriaList.add(Criteria.where("couponActivity.platformFlag").is(DefaultFlag.YES.toString()));
//            criteriaList.add(Criteria.where("couponActivity.startTime").lt(LocalDateTime.now()));
//            criteriaList.add(Criteria.where("couponActivity.endTime").gt(LocalDateTime.now()));
//            criteriaList.add(Criteria.where("couponActivity.pluginType").is(pluginType));
//            criteriaList.add(Criteria.where("couponInfo.participateType").is(ParticipateType.ALL.toString()));
//        }
//        return criteriaList;
//    }


    /**
     * 获取店铺的查询条件
     *
     * @return
     */
    protected List<Criteria> getStoreCriteriaList() {
        // 店铺相关券
        List<Criteria> storeCriteriaList = new ArrayList<>();
        storeCriteriaList.add(Criteria.where("couponActivity.couponActivityType").is(CouponActivityType.ALL_COUPONS.toString()));
        storeCriteriaList.add(Criteria.where("couponActivity.platformFlag").is(DefaultFlag.NO.toString()));
        storeCriteriaList.add(Criteria.where("couponActivity.startTime").lt(LocalDateTime.now()));
        storeCriteriaList.add(Criteria.where("couponActivity.endTime").gt(LocalDateTime.now()));
        if(PluginType.O2O.equals(pluginType)){
            storeCriteriaList.add(Criteria.where("couponActivity.auditState").is(AuditState.CHECKED));
        }
        return storeCriteriaList;
    }

    /**
     * 获取平台的查询条件
     *
     * @return
     */
    protected List<Criteria> getPlatformCriteriaList() {
        // 平台相关券
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("couponActivity.couponActivityType").is(CouponActivityType.ALL_COUPONS.toString()));
        criteriaList.add(Criteria.where("couponActivity.platformFlag").is(DefaultFlag.YES.toString()));
        criteriaList.add(Criteria.where("couponActivity.startTime").lt(LocalDateTime.now()));
        criteriaList.add(Criteria.where("couponActivity.endTime").gt(LocalDateTime.now()));
        return criteriaList;
    }

    /**
     * 获取平台的Scope相关查询条件
     *
     * @return
     */
    protected Criteria getPlatformScope() {
        //按照scope进行过滤
        Criteria scopeCriteria = new Criteria();

        List<Criteria> scopeCriteriaList = new ArrayList<>();

        //查询品牌
        if (CollectionUtils.isNotEmpty(brandIds)) {
            Criteria brandCriteria = new Criteria();
            brandCriteria.andOperator(
                    Criteria.where("scopes.scopeType").is(ScopeType.BRAND.toString()),
                    Criteria.where("scopes.scopeId").in(brandIds.stream().map(Object::toString).collect(Collectors.toList()))
            );
            scopeCriteriaList.add(brandCriteria);
        }

        //查询平台分类
        if (CollectionUtils.isNotEmpty(cateIds)) {
            Criteria cateCriteria = new Criteria();
            cateCriteria.andOperator(
                    Criteria.where("scopes.scopeType").is(ScopeType.BOSS_CATE.toString()),
                    Criteria.where("scopes.scopeId").in(cateIds.stream().map(Object::toString).collect(Collectors.toList()))
            );
            scopeCriteriaList.add(cateCriteria);
        }

        //全部商品
        scopeCriteriaList.add(new Criteria().andOperator(
                Criteria.where("couponInfo.scopeType").is(ScopeType.ALL.toString())
        ));

        //查商品
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            Criteria goodsCriteria = new Criteria();
            goodsCriteria.andOperator(
                    Criteria.where("scopes.scopeType").is(ScopeType.SKU.toString()),
                    Criteria.where("scopes.scopeId").in(goodsInfoIds),
                    Criteria.where("couponActivity.storeId").is(Constants.BOSS_DEFAULT_STORE_ID)
            );
            scopeCriteriaList.add(goodsCriteria);
        }

        //所有商品
        scopeCriteria.orOperator(scopeCriteriaList.toArray(new Criteria[scopeCriteriaList.size()]));

        return scopeCriteria;
    }

    /**
     * 获取商铺的Scope相关查询条件
     *
     * @return
     */
    private List<Criteria> getStoreScope() {
        List<Criteria> levelMapCriteria = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(storeIds)) {
            storeIds.stream().distinct().forEach(storeId -> {
                        //按照scope进行过滤
                        Criteria scopeCriteria = new Criteria();

                        List<Criteria> scopeCriteriaList = new ArrayList<>();

                        //查询品牌
                        if (CollectionUtils.isNotEmpty(brandIds)) {
                            Criteria brandCriteria = new Criteria();
                            brandCriteria.andOperator(
                                    Criteria.where("scopes.scopeType").is(ScopeType.BRAND.toString()),
                                    Criteria.where("scopes.scopeId").in(brandIds.stream().map(Object::toString).collect(Collectors.toList())),
                                    Criteria.where("couponActivity.storeId").is(storeId)
                            );
                            scopeCriteriaList.add(brandCriteria);
                        }

                        //查询店铺分类
                        if (CollectionUtils.isNotEmpty(storeCateIds)) {
                            Criteria storeCateCriteria = new Criteria();
                            storeCateCriteria.andOperator(
                                    Criteria.where("scopes.scopeType").is(ScopeType.STORE_CATE.toString()),
                                    Criteria.where("scopes.scopeId").in(storeCateIds.stream().map(Object::toString).collect(Collectors.toList())),
                                    Criteria.where("couponActivity.storeId").is(storeId)
                            );
                            scopeCriteriaList.add(storeCateCriteria);
                        }

                        //查询平台分类
                        if (CollectionUtils.isNotEmpty(cateIds)) {
                            Criteria cateCriteria = new Criteria();
                            cateCriteria.andOperator(
                                    Criteria.where("scopes.scopeType").is(ScopeType.BOSS_CATE.toString()),
                                    Criteria.where("scopes.scopeId").in(cateIds.stream().map(Object::toString).collect(Collectors.toList())),
                                    Criteria.where("couponActivity.storeId").is(storeId)
                            );
                            scopeCriteriaList.add(cateCriteria);
                        }
                        //查商品
                        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
                            Criteria goodsCriteria = new Criteria();
                            goodsCriteria.andOperator(
                                    Criteria.where("scopes.scopeType").is(ScopeType.SKU.toString()),
                                    Criteria.where("scopes.scopeId").in(goodsInfoIds),
                                    Criteria.where("couponActivity.storeId").is(storeId)
                            );
                            scopeCriteriaList.add(goodsCriteria);
                        }

                        //查询门店分类
                        if (PluginType.O2O.equals(pluginType) && CollectionUtils.isNotEmpty(cateIds)) {
                            Criteria o2oCateCriteria = new Criteria();
                            o2oCateCriteria.andOperator(
                                    Criteria.where("scopes.scopeType").is(ScopeType.O2O_CATE.toString()),
                                    Criteria.where("scopes.scopeId").in(cateIds.stream().map(Object::toString).collect(Collectors.toList())),
                                    Criteria.where("couponActivity.storeId").is(storeId)
                            );
                            scopeCriteriaList.add(o2oCateCriteria);
                        }

                        //全部商品
                        scopeCriteriaList.add(new Criteria().andOperator(
                                Criteria.where("couponInfo.scopeType").is(ScopeType.ALL.toString()),
                                Criteria.where("couponActivity.storeId").is(storeId)
                        ));

                        //所有商品
                        scopeCriteria.orOperator(scopeCriteriaList.toArray(new Criteria[scopeCriteriaList.size()]));
                        levelMapCriteria.add(new Criteria().andOperator(
                                Criteria.where("couponActivity.storeId").is(storeId),
                                (
                                        Objects.nonNull(levelMap) && levelMap.get(storeId) != null ?
                                                new Criteria().orOperator(
                                                        Criteria.where("couponActivity.joinLevel").is(MarketingJoinLevel.ALL_LEVEL.toValue() + ""),
                                                        Criteria.where("couponActivity.joinLevel").is(levelMap.get(storeId).getLevelId().toString()),
                                                        Criteria.where("couponActivity.joinLevel").is(MarketingJoinLevel.ALL_CUSTOMER.toValue() + "")
                                                ) :
                                                Criteria.where("couponActivity.joinLevel").is(MarketingJoinLevel.ALL_CUSTOMER.toValue() + "")
                                ),
                                scopeCriteria
                        ));
                    }
            );
        }
        return levelMapCriteria;
    }

    /***
     * 填充基础查询条件
     * @param criteria
     */
    protected void populateBaseCondition(List<Criteria> criteria) {
        //限定优惠券活动Id
        if (CollectionUtils.isNotEmpty(couponActivityIds)) {
            criteria.add(Criteria.where("couponActivityId").in(couponActivityIds));
        }

        //优惠券分类查询
        if (StringUtils.isNotBlank(couponCateId)) {
            criteria.add(Criteria.where("couponCateIds").is(couponCateId));
        }

        //优惠券类型查询
        if (Objects.nonNull(couponType)) {
            if (CollectionUtils.isNotEmpty(couponTypes)){
                criteria.add(Criteria.where("couponInfo.couponType").in(couponTypes));
            }else {
                criteria.add(Criteria.where("couponInfo.couponType").is(couponType));
            }
        }

        //活动状态
        criteria.add(Criteria.where("couponActivity.pauseFlag").is(DefaultFlag.NO.toString()));

    }

    protected void populatePluginType(List<Criteria> criteria) {
        // 因为C端领券中心等场景需要展示全量SBC券和门店券
        // 因此标记不需要过滤插件类型
        if (BoolFlag.NO == filterPlugin) {
            return;
        }
        // 优惠券类型限制
        if (Objects.isNull(pluginType)) {
            criteria.add(Criteria.where("couponActivity.pluginType").is(PluginType.NORMAL));
        } else {
            criteria.add(Criteria.where("couponActivity.pluginType").is(pluginType));
        }
    }

}
