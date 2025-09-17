package com.wanmi.sbc.marketing.common.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.model.root.MarketingScope;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * marketing + marketingScope 关联
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingRequest extends BaseQueryRequest {

    /**
     * 商品Id集合，查询出对应的营销信息
     */
    List<String> goodsInfoIdList;

    /**
     * 查询某个状态下的营销活动
     */
    MarketingStatus marketingStatus;

    /**
     * 排除某个状态下的营销活动
     */
    MarketingStatus excludeStatus;

    /**
     * 删除状态
     */
    DeleteFlag deleteFlag;

    /**
     * 是否关联活动级别
     */
    private Boolean cascadeLevel;

    /**
     * 店铺分类
     */
    private List<Long> storeCateIds;

    /**
     * 品牌
     */
    private List<Long> brandIds;

    /**
     * 店铺
     */
    private List<Long> storeIds;

    /**
     * 商品数据
     */
    private List<MarketingGoods> marketingGoods;

    /**
     * 门店分类
     */
    private List<Long> o2oStoreCateIds;

    /**
     * 平台分类
     */
    private List<Long> cateIds;

    /**
     * 插件类型
     */
    private PluginType pluginType;
    /**
     * 促销类型 促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐 7满返
     */
    private MarketingType marketingType;

    public void setMarketingGoods(List<MarketingGoods> marketingGoods){
        this.marketingGoods = marketingGoods;
        this.goodsInfoIdList = marketingGoods.stream().map(MarketingGoods::getGoodsInfoId).distinct().collect(Collectors.toList());
        this.brandIds = marketingGoods.stream().map(MarketingGoods::getBrandId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        this.storeCateIds = marketingGoods.stream()
                .filter(item -> WmCollectionUtils.isNotEmpty(item.getStoreCateIds()))
                .flatMap(item -> item.getStoreCateIds().stream()).distinct().collect(Collectors.toList());
        this.storeIds = marketingGoods.stream().map(MarketingGoods::getStoreId).distinct().collect(Collectors.toList());
        this.o2oStoreCateIds = marketingGoods.stream()
                                            .filter(m-> PluginType.O2O.equals(m.getPluginType()))
                                            .filter(item-> WmCollectionUtils.isNotEmpty(item.getStoreCateIds()))
                                            .flatMap(item -> item.getStoreCateIds().stream())
                                            .distinct().collect(Collectors.toList());
    }


    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<Marketing> getWhereCriteria() {
        return (root, cQuery, cBuild) -> {
            cQuery.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            Join<Marketing, MarketingScope> marketingJoin = root.join("marketingScopeList", JoinType.LEFT);

            if(deleteFlag != null){
                predicates.add(cBuild.equal(root.get("delFlag"), deleteFlag));
            }

            if (Objects.nonNull(marketingType)){
                predicates.add(cBuild.equal(root.get("marketingType"), marketingType));
            }

            //没写全，需要的可以添加
            if (marketingStatus != null) {
                switch (marketingStatus) {
                    case STARTED:
                        predicates.add(cBuild.lessThanOrEqualTo(root.get("beginTime"), LocalDateTime.now()));
                        predicates.add(cBuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                        break;
                    default:
                        break;
                }
            }

            if (excludeStatus != null) {
                predicates.add(cBuild.equal(root.get("isPause"), BoolFlag.NO));
                switch (excludeStatus) {
                    case ENDED:
                        predicates.add(cBuild.greaterThan(root.get("endTime"), LocalDateTime.now()));
                        break;
                    default:
                        break;
                }
            }

            // 是否SBC
            if(Objects.nonNull(pluginType)){
                predicates.add(cBuild.equal(root.get("pluginType"), pluginType));
            } else {
                predicates.add(cBuild.equal(root.get("pluginType"), PluginType.NORMAL));
            }

            //指定商品
            List<Predicate> scopePredicates = new ArrayList<>(8);
            if (CollectionUtils.isNotEmpty(goodsInfoIdList)) {
                CriteriaBuilder.In in = cBuild.in(marketingJoin.get("scopeId"));
                for (String goodsId : goodsInfoIdList) {
                    in.value(goodsId);
                }
                Predicate skuPredicate = cBuild.and(cBuild.equal(root.get("scopeType"), MarketingScopeType.SCOPE_TYPE_CUSTOM), in);
                scopePredicates.add(skuPredicate);
            }

            //品牌
            if (CollectionUtils.isNotEmpty(brandIds)) {
                CriteriaBuilder.In in = cBuild.in(marketingJoin.get("scopeId"));
                for (Long brand : brandIds) {
                    in.value(brand.toString());
                }
                Predicate brandPredicate = cBuild.and(cBuild.equal(root.get("scopeType"), MarketingScopeType.SCOPE_TYPE_BRAND), in);
                scopePredicates.add(brandPredicate);
            }

            //店铺分类
            if (CollectionUtils.isNotEmpty(storeCateIds)) {
                CriteriaBuilder.In in = cBuild.in(marketingJoin.get("scopeId"));
                for (Long cateId : storeCateIds) {
                    in.value(cateId.toString());
                }
                Predicate storeCatePredicate = cBuild.and(cBuild.equal(root.get("scopeType"), MarketingScopeType.SCOPE_TYPE_STORE_CATE), in);
                scopePredicates.add(storeCatePredicate);
            }

            //平台分类
            if (CollectionUtils.isNotEmpty(cateIds)) {
                CriteriaBuilder.In in = cBuild.in(marketingJoin.get("scopeId"));
                for (Long cateId : cateIds) {
                    in.value(cateId.toString());
                }
                Predicate storeCatePredicate = cBuild.and(cBuild.equal(root.get("scopeType"),
                        MarketingScopeType.SCOPE_TYPE_GOODS_CATE), in);
                scopePredicates.add(storeCatePredicate);
            }

            //全部商品
            if (CollectionUtils.isNotEmpty(storeIds)) {
                CriteriaBuilder.In in = cBuild.in(root.get("storeId"));
                for (Long storeId : storeIds) {
                    in.value(storeId.toString());
                }
                Predicate allSkuPredicate = cBuild.and(cBuild.equal(root.get("scopeType"), MarketingScopeType.SCOPE_TYPE_ALL), in);
                scopePredicates.add(allSkuPredicate);
            }

            Predicate scopePredicate = cBuild.or(scopePredicates.toArray(new Predicate[scopePredicates.size()]));
            predicates.add(scopePredicate);

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);

            return p.length == 0 ? null : p.length == 1 ? p[0] : cBuild.and(p);
        };
    }

}
