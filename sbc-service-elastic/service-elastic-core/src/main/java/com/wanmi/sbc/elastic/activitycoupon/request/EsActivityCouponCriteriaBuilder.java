package com.wanmi.sbc.elastic.activitycoupon.request;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Slf4j
@Schema
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EsActivityCouponCriteriaBuilder {
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

    /**
     * 券类型
     */
    private ScopeType scopeType;

    /**
     * 券名称模糊查询
     */
    private String likeCouponName;

    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    private CouponMarketingType couponMarketingType;


    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    private List<CouponMarketingType> couponMarketingTypes;


    public BoolQuery getWhereCriteria() {

//		//店铺相关
//		//店铺券（符合等级的/正在进行的/Scope满足的）
//        BoolQueryBuilder storeQ = new BoolQueryBuilder();
        BoolQuery.Builder storeQ = QueryBuilders.bool();
        getStoreBuildList(storeQ);


        //平台相关
        //平台券（符合等级的/正在进行的/Scope满足的）
//        BoolQueryBuilder platformQ = new BoolQueryBuilder();
        BoolQuery.Builder platformQ = QueryBuilders.bool();
        getPlatformBuilderList(platformQ);
//
//
//		//优惠券相关过滤，过滤出 平台 + 店铺
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
		boolQueryBuilder.minimumShouldMatch("1");
        boolQueryBuilder.should(a -> a.bool(platformQ.build()));
        boolQueryBuilder.should(a -> a.bool(storeQ.build()));
        //其他共有条件
        populateBaseCondition(boolQueryBuilder);
        populatePluginType(boolQueryBuilder);


        return boolQueryBuilder.build();

    }


    /***
     * 填充基础查询条件
     * @param boolQueryBuilder
     */
    protected void populateBaseCondition(BoolQuery.Builder boolQueryBuilder) {
        //限定优惠券活动Id
        if (CollectionUtils.isNotEmpty(couponActivityIds)) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("activityId", couponActivityIds));
            boolQueryBuilder.must(terms(a -> a.field("activityId").terms(v -> v.value(couponActivityIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //限定优惠券Id
        if (CollectionUtils.isNotEmpty(couponIdList)) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("couponId", couponIdList));
            boolQueryBuilder.must(terms(a -> a.field("couponId").terms(v -> v.value(couponIdList.stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //优惠券分类查询
        if (StringUtils.isNotBlank(couponCateId)) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("couponCateIds", couponCateId));
            boolQueryBuilder.must(term(a -> a.field("couponCateIds").value(couponCateId)));
        }

        //优惠券类型查询
        if (Objects.nonNull(couponType)) {
            if (CollectionUtils.isNotEmpty(couponTypes)) {
//                boolQueryBuilder.must(QueryBuilders.termsQuery("couponType", couponTypes.stream().map(CouponType::toValue).collect(Collectors.toList())));
                boolQueryBuilder.must(terms(a -> a.field("couponType").terms(v -> v.value(couponTypes.stream().map(CouponType::toValue).map(FieldValue::of).collect(Collectors.toList())))));
            } else {
//                boolQueryBuilder.must(QueryBuilders.termsQuery("couponType", Lists.newArrayList(couponType.toValue())));
                boolQueryBuilder.must(term(a -> a.field("couponType").value(couponType.toValue())));
            }
        }

        if (Objects.nonNull(couponMarketingType)) {
//            boolQueryBuilder.must(termQuery("couponMarketingType", couponMarketingType.toValue()));
            boolQueryBuilder.must(term(a -> a.field("couponMarketingType").value(couponMarketingType.toValue())));
        }

        //优惠券营销类型
        if (Objects.nonNull(couponMarketingType)) {
            if (CollectionUtils.isNotEmpty(couponMarketingTypes)) {
//                boolQueryBuilder.must(QueryBuilders.termsQuery("couponMarketingType", couponMarketingTypes.stream().map(CouponMarketingType::toValue).collect(Collectors.toList())));
                boolQueryBuilder.must(terms(a -> a.field("couponMarketingType").terms(v -> v.value(couponMarketingTypes.stream().map(CouponMarketingType::toValue).map(FieldValue::of).collect(Collectors.toList())))));
            } else {
//                boolQueryBuilder.must(QueryBuilders.termsQuery("couponMarketingType", Lists.newArrayList(couponMarketingType.toValue())));
                boolQueryBuilder.must(term(a -> a.field("couponMarketingType").value(couponMarketingType.toValue())));
            }
        }

        if (Objects.nonNull(scopeType)){
//            boolQueryBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(scopeType.toValue())));
            boolQueryBuilder.must(term(a -> a.field("scopeType").value(scopeType.toValue())));
        }

        //模糊查询名称
        if (StringUtils.isNotEmpty(likeCouponName)) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("couponName", StringUtil.ES_LIKE_CHAR.concat(
//                    XssUtils.replaceEsLikeWildcard(likeCouponName.trim())).concat(StringUtil.ES_LIKE_CHAR)));
            boolQueryBuilder.must(wildcard(a -> a.field("couponName").value(ElasticCommonUtil.replaceEsLikeWildcard(likeCouponName.trim()))));
        }

//        boolQueryBuilder.must(QueryBuilders.rangeQuery("activityStartTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//        boolQueryBuilder.must(QueryBuilders.rangeQuery("activityEndTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
        boolQueryBuilder.must(range(a -> a.field("activityStartTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        boolQueryBuilder.must(range(a -> a.field("activityEndTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        //活动状态
//        boolQueryBuilder.must(QueryBuilders.termsQuery("pauseFlag", Lists.newArrayList(DefaultFlag.NO.toValue())));
        boolQueryBuilder.must(term(a -> a.field("pauseFlag").value(DefaultFlag.NO.toValue())));

    }

    protected void populatePluginType(BoolQuery.Builder boolQueryBuilder) {
        // 因为C端领券中心等场景需要展示全量SBC券和门店券
        // 因此标记不需要过滤插件类型
        if (BoolFlag.NO == filterPlugin) {
            return;
        }
        // 优惠券类型限制
        if (Objects.isNull(pluginType)) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("pluginType", Lists.newArrayList(PluginType.NORMAL.toValue())));
            boolQueryBuilder.must(term(a -> a.field("pluginType").value(PluginType.NORMAL.toValue())));
        } else {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("pluginType", Lists.newArrayList(pluginType.toValue())));
            boolQueryBuilder.must(term(a -> a.field("pluginType").value(pluginType.toValue())));
        }
    }


    public void getPlatformBuilderList(BoolQuery.Builder pt) {
        //平台相关
//        pt.must(QueryBuilders.termsQuery("platformFlag", Lists.newArrayList(DefaultFlag.YES.toValue())));
        pt.must(term(a -> a.field("platformFlag").value(DefaultFlag.YES.toValue())));
        //平台scope相关
        if (CollectionUtils.isNotEmpty(brandIds) || CollectionUtils.isNotEmpty(cateIds)) {
            pt.must(a -> a.bool(this.getPlatformScope()));
        }
    }


    public void getStoreBuildList(BoolQuery.Builder st) {
        //店铺相关
//        st.must(QueryBuilders.termsQuery("platformFlag", Lists.newArrayList(DefaultFlag.NO.toValue())));
        st.must(term(a -> a.field("platformFlag").value(DefaultFlag.NO.toValue())));
        if (PluginType.O2O.equals(pluginType)) {
//            st.must(QueryBuilders.termsQuery("auditState", Lists.newArrayList(AuditState.CHECKED.getStatusId())));
            st.must(term(a -> a.field("auditState").value(AuditState.CHECKED.getStatusId())));
        }
        this.getStoreScope(st);


    }

    /**
     * 获取平台的Scope相关查询条件
     *
     * @return
     */
    protected BoolQuery getPlatformScope() {
        //按照scope进行过滤
//        BoolQueryBuilder scopeBuilder = new BoolQueryBuilder();
//		scopeBuilder.minimumShouldMatch(1);
        BoolQuery.Builder scopeBuilder = QueryBuilders.bool();
        scopeBuilder.minimumShouldMatch("1");

        List<Query> scopeList = new ArrayList<>();

        //查询品牌
        if (CollectionUtils.isNotEmpty(brandIds)) {
//            BoolQueryBuilder brandBuilder = new BoolQueryBuilder();
//            brandBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.BRAND.toValue())));
//            brandBuilder.must(QueryBuilders.termsQuery("scopeId", brandIds));
//            scopeList.add(brandBuilder);
            BoolQuery.Builder brandBuilder = QueryBuilders.bool();
            brandBuilder.must(term(a -> a.field("scopeType").value(ScopeType.BRAND.toValue())));
            brandBuilder.must(terms(a -> a.field("scopeId").terms(v -> v.value(brandIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
            scopeList.add(brandBuilder.build()._toQuery());
        }

        //查询平台分类
        if (CollectionUtils.isNotEmpty(cateIds)) {
            /*BoolQueryBuilder cateBuilder = boolQuery();
            cateBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.BOSS_CATE.toValue())));
            cateBuilder.must(QueryBuilders.termsQuery("scopeId", cateIds.stream().map(Object::toString).collect(Collectors.toList())));
            scopeList.add(cateBuilder);*/
            BoolQuery.Builder cateBuilder = QueryBuilders.bool();
            cateBuilder.must(term(a -> a.field("scopeType").value(ScopeType.BOSS_CATE.toValue())));
            cateBuilder.must(terms(a -> a.field("scopeId").terms(v -> v.value(cateIds.stream().map(Object::toString).map(FieldValue::of).collect(Collectors.toList())))));
            scopeList.add(cateBuilder.build()._toQuery());
        }

        //查商品
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            /*BoolQueryBuilder goodsBuilder = boolQuery();
            goodsBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.SKU.toValue())));
            goodsBuilder.must(QueryBuilders.termsQuery("scopeId", goodsInfoIds));
            scopeList.add(goodsBuilder);*/
            BoolQuery.Builder goodsBuilder = QueryBuilders.bool();
            goodsBuilder.must(term(a -> a.field("scopeType").value(ScopeType.SKU.toValue())));
            goodsBuilder.must(terms(a -> a.field("scopeId").terms(v -> v.value(goodsInfoIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
            scopeList.add(goodsBuilder.build()._toQuery());
        }

        //查店铺
        if (CollectionUtils.isNotEmpty(storeIds)) {
            /*BoolQueryBuilder storeBuilder = boolQuery();
            storeBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.STORE.toValue())));
            storeBuilder.must(QueryBuilders.termsQuery("scopeId", storeIds));
            scopeList.add(storeBuilder);*/
            BoolQuery.Builder storeBuilder = QueryBuilders.bool();
            storeBuilder.must(term(a -> a.field("scopeType").value(ScopeType.STORE.toValue())));
            storeBuilder.must(terms(a -> a.field("scopeId").terms(v -> v.value(storeIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
            scopeList.add(storeBuilder.build()._toQuery());
        }

        //全部商品
        //全部商品
//        BoolQueryBuilder allBuilder = boolQuery();
        BoolQuery.Builder allBuilder = QueryBuilders.bool();
        if (Objects.nonNull(scopeType)){
//            allBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(scopeType.toValue())));
            allBuilder.must(term(a -> a.field("scopeType").value(scopeType.toValue())));
        } else {
//            allBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.ALL.toValue())));
            allBuilder.must(term(a -> a.field("scopeType").value(ScopeType.ALL.toValue())));
        }
//        scopeList.add(allBuilder);
        scopeList.add(allBuilder.build()._toQuery());

        //所有商品
        if (CollectionUtils.isNotEmpty(scopeList)) {
            scopeList.forEach(scopeBuilder::should);
        }

        return scopeBuilder.build();
    }

    /**
     * /**
     * 获取商铺的Scope相关查询条件
     *
     * @return
     */
    private void getStoreScope(BoolQuery.Builder st) {

        if (Objects.nonNull(storeIds) && CollectionUtils.isNotEmpty(storeIds.parallelStream().filter(Objects::nonNull).collect(Collectors.toList()))) {
//            st.minimumShouldMatch(1);
            st.minimumShouldMatch("1");
            storeIds.stream().distinct().forEach(storeId -> {
                        //按照scope进行过滤
//                        BoolQueryBuilder scopeBuilder = new BoolQueryBuilder();
                        BoolQuery.Builder scopeBuilder = QueryBuilders.bool();

//                        BoolQueryBuilder scopeIdBuild = new BoolQueryBuilder();
                        BoolQuery.Builder scopeIdBuild = QueryBuilders.bool();
//                        scopeIdBuild.minimumShouldMatch(1);
                        scopeIdBuild.minimumShouldMatch("1");
                        //查询品牌
                        if (CollectionUtils.isNotEmpty(brandIds)) {
                            /*BoolQueryBuilder brandBuilder = boolQuery();
                            brandBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.BRAND.toValue())));
                            brandBuilder.must(QueryBuilders.termsQuery("scopeId", brandIds));
                            scopeIdBuild.should(brandBuilder);*/
                            BoolQuery.Builder brandBuilder = QueryBuilders.bool();
                            brandBuilder.must(term(a -> a.field("scopeType").value(ScopeType.BRAND.toValue())));
                            brandBuilder.must(terms(a -> a.field("scopeId").terms(b -> b.value(brandIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
                            scopeIdBuild.should(a -> a.bool(brandBuilder.build()));
                        }

                        //查询店铺分类
                        if (CollectionUtils.isNotEmpty(storeCateIds)) {
                            /*BoolQueryBuilder storeCateBuilder = boolQuery();
                            storeCateBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.STORE_CATE.toValue())));
                            storeCateBuilder.must(QueryBuilders.termsQuery("scopeId", storeCateIds.stream().map(Object::toString).collect(Collectors.toList())));
                            scopeIdBuild.should(storeCateBuilder);*/
                            BoolQuery.Builder storeCateBuilder = QueryBuilders.bool();
                            storeCateBuilder.must(term(a -> a.field("scopeType").value(ScopeType.STORE_CATE.toValue())));
                            storeCateBuilder.must(terms(a -> a.field("scopeId").terms(b -> b.value(storeCateIds.stream().map(Object::toString).map(FieldValue::of).collect(Collectors.toList())))));
                            scopeIdBuild.should(a -> a.bool(storeCateBuilder.build()));
                        }

                        //查询平台分类
                        if (CollectionUtils.isNotEmpty(cateIds)) {
                            /*BoolQueryBuilder cateBuilder = boolQuery();
                            cateBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.BOSS_CATE.toValue())));
                            cateBuilder.must(QueryBuilders.termsQuery("scopeId", cateIds.stream().map(Object::toString).collect(Collectors.toList())));
                            scopeIdBuild.should(cateBuilder);*/
                            BoolQuery.Builder cateBuilder = QueryBuilders.bool();
                            cateBuilder.must(term(a -> a.field("scopeType").value(ScopeType.BOSS_CATE.toValue())));
                            cateBuilder.must(terms(a -> a.field("scopeId").terms(b -> b.value(cateIds.stream().map(Object::toString).map(FieldValue::of).collect(Collectors.toList())))));
                            scopeIdBuild.should(a -> a.bool(cateBuilder.build()));
                        }
                        //查商品
                        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
                            /*BoolQueryBuilder goodsBuilder = boolQuery();
                            goodsBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.SKU.toValue())));
                            goodsBuilder.must(QueryBuilders.termsQuery("scopeId", goodsInfoIds));
                            scopeIdBuild.should(goodsBuilder);*/
                            BoolQuery.Builder goodsBuilder = QueryBuilders.bool();
                            goodsBuilder.must(term(a -> a.field("scopeType").value(ScopeType.SKU.toValue())));
                            goodsBuilder.must(terms(a -> a.field("scopeId").terms(b -> b.value(goodsInfoIds.stream().map(Object::toString).map(FieldValue::of).collect(Collectors.toList())))));
                            scopeIdBuild.should(a -> a.bool(goodsBuilder.build()));
                        }

                        //查询门店分类
                        if (PluginType.O2O.equals(pluginType) && CollectionUtils.isNotEmpty(cateIds)) {
                            /*BoolQueryBuilder cateBuilder = boolQuery();
                            cateBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.O2O_CATE.toValue())));
                            cateBuilder.must(QueryBuilders.termsQuery("scopeId", cateIds.stream().map(Object::toString).collect(Collectors.toList())));
                            scopeIdBuild.should(cateBuilder);*/
                            BoolQuery.Builder cateBuilder = QueryBuilders.bool();
                            cateBuilder.must(term(a -> a.field("scopeType").value(ScopeType.O2O_CATE.toValue())));
                            cateBuilder.must(terms(a -> a.field("scopeId").terms(b -> b.value(cateIds.stream().map(Object::toString).map(FieldValue::of).collect(Collectors.toList())))));
                            scopeIdBuild.should(a -> a.bool(cateBuilder.build()));
                        }
                        //全部商品
//                        BoolQueryBuilder allBuilder = boolQuery();
                        BoolQuery.Builder allBuilder = QueryBuilders.bool();
                        if (Objects.nonNull(scopeType)){
//                            allBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(scopeType.toValue())));
                            allBuilder.must(term(a -> a.field("scopeType").value(scopeType.toValue())));
                        } else {
//                            allBuilder.must(QueryBuilders.termsQuery("scopeType", Lists.newArrayList(ScopeType.ALL.toValue())));
                            allBuilder.must(term(a -> a.field("scopeType").value(ScopeType.ALL.toValue())));
                        }
//                        scopeIdBuild.should(allBuilder);
                        scopeIdBuild.should(a -> a.bool(allBuilder.build()));


//                        BoolQueryBuilder levelBuilder = boolQuery();
                        BoolQuery.Builder levelBuilder = QueryBuilders.bool();
                        if (Objects.nonNull(levelMap) && levelMap.get(storeId) != null) {
                            /*levelBuilder.should(QueryBuilders.termsQuery("joinLevels", Lists.newArrayList(MarketingJoinLevel.ALL_LEVEL.toValue())));
                            levelBuilder.should(QueryBuilders.termsQuery("joinLevels", Lists.newArrayList(levelMap.get(storeId).getLevelId())));
                            levelBuilder.should(QueryBuilders.termsQuery("joinLevels", Lists.newArrayList(MarketingJoinLevel.ALL_CUSTOMER.toValue())));*/
                            levelBuilder.should(term(a -> a.field("joinLevels").value(MarketingJoinLevel.ALL_LEVEL.toValue())));
                            levelBuilder.should(term(a -> a.field("joinLevels").value(levelMap.get(storeId).getLevelId())));
                            levelBuilder.should(term(a -> a.field("joinLevels").value(MarketingJoinLevel.ALL_CUSTOMER.toValue())));
                        } else {
//                            levelBuilder.should(QueryBuilders.termsQuery("joinLevels", Lists.newArrayList(MarketingJoinLevel.ALL_CUSTOMER.toValue())));
                            levelBuilder.should(term(a -> a.field("joinLevels").value(MarketingJoinLevel.ALL_CUSTOMER.toValue())));
                        }
//                        scopeBuilder.must(scopeIdBuild);
//                        scopeBuilder.must(levelBuilder);
//                        scopeBuilder.must(QueryBuilders.termsQuery("storeId", Lists.newArrayList(storeId)));
//                        st.should(scopeBuilder);
                        scopeBuilder.must(a -> a.bool(scopeIdBuild.build()));
                        scopeBuilder.must(a -> a.bool(levelBuilder.build()));
                        scopeBuilder.must(term(b -> b.field("storeId").value(storeId)));
                        st.should(a -> a.bool(scopeBuilder.build()));
                    }
            );

        }
    }
}
