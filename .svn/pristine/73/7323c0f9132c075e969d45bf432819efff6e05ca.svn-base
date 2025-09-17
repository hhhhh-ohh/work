package com.wanmi.sbc.goods.info.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.util.XssUtils;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.criteria.Predicate;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoQueryRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = 2067929276444140704L;

    /**
     * 批量SKU编号
     */
    private List<String> goodsInfoIds;

    /**
     * SPU编号
     */
    private String goodsId;

    /**
     * 批量SPU编号
     */
    private List<String> goodsIds;

    /**
     * 批量店铺分类相关SPU编号
     */
    private List<String> storeCateGoodsIds;

    /**
     * 品牌编号
     */
    private Long brandId;

    /**
     * 批量品牌编号
     */
    private List<Long> brandIds;

    /**
     * 分类编号
     */
    private Long cateId;

    /**
     * 分类编号列表
     */
    private List<Long> cateIds;

    /**
     * 店铺分类id
     */
    private Long storeCateId;

    /**
     * 批量查询-店铺分类id
     */
    private List<Long> storeCateIds;

    /**
     * 模糊条件-商品名称
     */
    private String likeGoodsName;

    /**
     * 精确条件-批量SKU编码
     */
    private List<String> goodsInfoNos;

    /**
     * 精确条件-SKU编码
     */
    private String goodsInfoNo;

    /**
     * 模糊条件-SKU编码
     */
    private String likeGoodsInfoNo;

    /**
     * 模糊条件-SPU编码
     */
    private String likeGoodsNo;

    /**
     * 上下架状态
     */
    private Integer addedFlag;

    /**
     * 上下架状态-批量
     */
    private List<Integer> addedFlags;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 客户编号
     */
    private String customerId;

    /**
     * 客户等级
     */
    private Long customerLevelId;

    /**
     * 客户等级折扣
     */
    private BigDecimal customerLevelDiscount;

    /**
     * 非GoodsId
     */
    private String notGoodsId;

    /**
     * 非GoodsIdList
     */
    private List<String> notGoodsIds;

    /**
     * 非GoodsInfoId
     */
    private String notGoodsInfoId;

    /**
     * 公司信息ID
     */
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 批量店铺ID
     */
    private List<Long> storeIds;

    /**
     * 审核状态
     */
    private CheckStatus auditStatus;

    /**
     * 审核状态
     */
    private List<CheckStatus> auditStatuses;

    /**
     * 关键词，目前范围：商品名称、SKU编码
     */
    private String keyword;

    /**
     * 业务员app,商品状态筛选
     */
    private List<GoodsInfoSelectStatus> goodsSelectStatuses;

    /**
     * 商家类型
     */
    private BoolFlag companyType;

    /**
     * 销售类别
     */
    private Integer saleType;

    /**
     * 企业购商品审核状态
     */
    private EnterpriseAuditState enterPriseAuditState;

    /**
     * 批量供应商商品SKU编号
     */
    private List<String> providerGoodsInfoIds;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall
     */
    private Integer goodsSource;

    /**
     * 批量商品来源，0供应商，1商家
     */
    private List<GoodsSource> goodsSourceList;

    /**
     * 第三方平台的skuId
     */
    private List<String> thirdPlatformSkuId;

    /**
     * 第三方平台的spuId
     */
    private String thirdPlatformSpuId;

    /**
     * 三方渠道类目id
     */
    private Long thirdCateId;

    /**
     * 是否定时上架
     */
    private Boolean addedTimingFlag;

    /**
     * 是否定时下架
     */
    private Boolean takedownTimeFlag;

    /**
     * 需要排除的三方渠道
     */
    private List<Integer> notThirdPlatformType;
    /**
     * 三方渠道
     */
    private Integer thirdPlatformType;

    /**
     * 是否可售
     */
    private Integer vendibility;

    /**
     * 是否过滤积分价商品 true是 false不过滤
     */
    private Boolean integralPriceFlag;

    /**
     * 标签ID
     */
    private Long labelId;

    /**
     * 库存状态
     * null:所有,1:有货,0:无货
     */
    private Integer stockFlag;

    /**
     * 是否屏蔽跨境商品
     */
    private Boolean notShowCrossGoodsFlag;

    /**
     * 商品类型
     */
    private Integer pluginType;

    /**
     * 备案状态
     */
    private List<Integer> recordStatus;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;

    /**
     * 是否营销查询商品
     */
    private Boolean isMarketing = false;

    /**
     * 是否过滤虚拟商品
     */
    private Integer goodsType;


    /**
     * 是否周期购商品
     */
    private Integer isBuyCycle;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    private String quickOrderNo;

    /**
     * 精确条件-批量SKU订货号
     */
    private List<String> quickOrderNos;

    @Schema(description = "分销商品审核状态")
    private List<DistributionGoodsAudit> distributionGoodsAuditList;
    /**
     * 0-春秋装 1-夏装 2-冬装
     */
    @Schema(description = "款式季节")
    private Integer attributeSeason;

    /**
     * 0-校服服饰 1-非校服服饰 2-自营产品
     */
    @Schema(description = "是否校服")
    private Integer attributeGoodsType;

    /**
     * 0-老款 1-新款
     */
    @Schema(description = "新老款")
    private Integer attributeSaleType;

    /**
     * 售卖地区
     */
    @Schema(description = "售卖地区")
    private String attributeSaleRegion;

    /**
     * 学段
     */
    @Schema(description = "学段")
    private String attributeSchoolSection;
    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<GoodsInfo> getWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>(10);
            //批量SKU编号
            if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
                predicates.add(root.get("goodsInfoId").in(goodsInfoIds));
            }
            //SPU编号
            if (StringUtils.isNotBlank(goodsId)) {
                predicates.add(cbuild.equal(root.get("goodsId"), goodsId));
            }
            //SKU编号
            if(StringUtils.isNotBlank(goodsInfoNo)){
                Predicate cbuildNull = cbuild.isNull(root.get("oldGoodsInfoId"));
                Predicate cbuildEmpty = cbuild.equal(root.get("oldGoodsInfoId"), StringUtils.EMPTY);
                predicates.add(cbuild.equal(root.get("goodsInfoNo"), goodsInfoNo));
                predicates.add(cbuild.or(cbuildNull, cbuildEmpty));
            }
            //批量SKU编号-goodsInfoNo存在两条数据,需要根据oldGoodsInfoId为null或者空来判断唯一性
            if (CollectionUtils.isNotEmpty(goodsInfoNos)) {
                Predicate cbuildNull = cbuild.isNull(root.get("oldGoodsInfoId"));
                Predicate cbuildEmpty = cbuild.equal(root.get("oldGoodsInfoId"), StringUtils.EMPTY);
                predicates.add(root.get("goodsInfoNo").in(goodsInfoNos));
                predicates.add(cbuild.or(cbuildNull, cbuildEmpty));

            }

            //SKU订货号
            if(StringUtils.isNotBlank(quickOrderNo)){
                Predicate cbuildNull = cbuild.isNull(root.get("oldGoodsInfoId"));
                Predicate cbuildEmpty = cbuild.equal(root.get("oldGoodsInfoId"), StringUtils.EMPTY);
                predicates.add(cbuild.equal(root.get("quickOrderNo"), quickOrderNo));
                predicates.add(cbuild.or(cbuildNull, cbuildEmpty));
            }
            //批量SKU订货号-quickOrderNo存在两条数据,需要根据oldGoodsInfoId为null或者空来判断唯一性
            if (CollectionUtils.isNotEmpty(quickOrderNos)) {
                Predicate cbuildNull = cbuild.isNull(root.get("oldGoodsInfoId"));
                Predicate cbuildEmpty = cbuild.equal(root.get("oldGoodsInfoId"), StringUtils.EMPTY);
                predicates.add(root.get("quickOrderNo").in(quickOrderNos));
                predicates.add(cbuild.or(cbuildNull, cbuildEmpty));

            }
            //批量SPU编号
            if (CollectionUtils.isNotEmpty(goodsIds)) {
                predicates.add(root.get("goodsId").in(goodsIds));
            }
            //批量店铺分类相关SPU编号
            if (CollectionUtils.isNotEmpty(storeCateGoodsIds)) {
                predicates.add(root.get("goodsId").in(storeCateGoodsIds));
            }
            if (thirdCateId != null) {
                predicates.add(cbuild.equal(root.get("thirdCateId"), thirdCateId));
            }
            //SKU编码
            if (StringUtils.isNotEmpty(likeGoodsInfoNo)) {
                predicates.add(cbuild.like(root.get("goodsInfoNo"), StringUtil.SQL_LIKE_CHAR.concat(XssUtils.replaceLikeWildcard(likeGoodsInfoNo.trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }
            //店铺ID
            if (storeId != null) {
                predicates.add(cbuild.equal(root.get("storeId"), storeId));
            }

            //分类ID
            if (cateId != null && cateId > 0) {
                predicates.add(cbuild.equal(root.get("cateId"), cateId));
            }

            //批量查询-分类ids
            if (CollectionUtils.isNotEmpty(cateIds)) {
                predicates.add(root.get("cateId").in(cateIds));
            }

            //批量查询-品牌ids
            if(CollectionUtils.isNotEmpty(brandIds)){
                predicates.add(root.get("brandId").in(brandIds));
            }

            //公司信息ID
            if (companyInfoId != null) {
                predicates.add(cbuild.equal(root.get("companyInfoId"), companyInfoId));
            }
            if (notThirdPlatformType != null && !notThirdPlatformType.isEmpty()) {
                List<ThirdPlatformType> thirdPlatformTypes = new ArrayList<>();
                notThirdPlatformType.forEach(a -> thirdPlatformTypes.add(ThirdPlatformType.fromValue(a)));
                predicates.add(cbuild.or(cbuild.not(root.get("thirdPlatformType").in(thirdPlatformTypes)), root.get("thirdPlatformType").isNull()));
            }
            if (thirdPlatformType != null) {
                predicates.add(cbuild.equal(root.get("thirdPlatformType"), ThirdPlatformType.fromValue(thirdPlatformType)));
            }
            //批量店铺ID
            if (CollectionUtils.isNotEmpty(storeIds)) {
                predicates.add(root.get("storeId").in(storeIds));
            }
            //模糊查询名称
            if (StringUtils.isNotEmpty(likeGoodsName)) {
                predicates.add(cbuild.like(root.get("goodsInfoName"), StringUtil.SQL_LIKE_CHAR.concat(XssUtils.replaceLikeWildcard(likeGoodsName.trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }

            //关键字搜索
            if (StringUtils.isNotBlank(keyword)) {
                String str = StringUtil.SQL_LIKE_CHAR.concat(XssUtils.replaceLikeWildcard(keyword.trim())).concat(StringUtil.SQL_LIKE_CHAR);
                predicates.add(cbuild.or(cbuild.like(root.get("goodsInfoName"), str), cbuild.like(root.get("goodsInfoNo"), str)));
            }
            //上下架状态
            if (addedFlag != null) {
                predicates.add(cbuild.equal(root.get("addedFlag"), addedFlag));
            }
            //上下架状态
            if (attributeSeason != null) {
                predicates.add(cbuild.equal(root.get("attributeSeason"), attributeSeason));
            }
            //上下架状态
            if (attributeGoodsType != null) {
                predicates.add(cbuild.equal(root.get("attributeGoodsType"), attributeGoodsType));
            }
            //上下架状态
            if (attributeSaleType != null) {
                predicates.add(cbuild.equal(root.get("attributeSaleType"), attributeSaleType));
            }
            if (StringUtils.isNotBlank(attributeSaleRegion)) {
                predicates.add(cbuild.equal(root.get("attributeSaleRegion"), attributeSaleRegion));
            }
            if (StringUtils.isNotBlank(attributeSchoolSection)) {
                predicates.add(cbuild.equal(root.get("attributeSchoolSection"), attributeSchoolSection));
            }
            //多个上下架状态
            if (CollectionUtils.isNotEmpty(addedFlags)) {
                predicates.add(root.get("addedFlag").in(addedFlags));
            }
            //审核状态
            if (auditStatus != null) {
                predicates.add(cbuild.equal(root.get("auditStatus"), auditStatus));
            }
            if (vendibility != null) {
                Predicate p1 = cbuild.equal(root.get("vendibility"), DefaultFlag.YES.toValue());
                Predicate p2 = cbuild.isNull(root.get("vendibility"));
                predicates.add(cbuild.or(p1, p2));
                Predicate p3 = cbuild.equal(root.get("providerStatus"), Constants.yes);
                Predicate p4 = cbuild.isNull(root.get("providerStatus"));
                predicates.add(cbuild.or(p3, p4));
            }
            //多个审核状态
            if (CollectionUtils.isNotEmpty(auditStatuses)) {
                predicates.add(root.get("auditStatus").in(auditStatuses));
            }
            //是否处于定时上架
            if (addedTimingFlag != null) {
                predicates.add(cbuild.equal(root.get("addedTimingFlag"), addedTimingFlag));
                if (Boolean.TRUE.equals(addedTimingFlag)) {
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("addedTimingTime"), LocalDateTime.now()));
                }
            }
            //是否定时下架
            if (takedownTimeFlag != null) {
                predicates.add(cbuild.equal(root.get("takedownTimeFlag"), takedownTimeFlag));
                if (Boolean.TRUE.equals(takedownTimeFlag)) {
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("takedownTime"), LocalDateTime.now()));
                }
            }

            // 过滤积分价商品
            if (Objects.nonNull(integralPriceFlag) && Objects.equals(Boolean.TRUE, integralPriceFlag)) {
                Predicate p1 = cbuild.equal(root.get("buyPoint"), 0);
                Predicate p2 = cbuild.isNull(root.get("buyPoint"));
                predicates.add(cbuild.or(p1, p2));
            }
            //删除标记
            if (delFlag != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.fromValue(delFlag)));
            }
            //非商品编号
            if (StringUtils.isNotBlank(notGoodsId)) {
                predicates.add(cbuild.notEqual(root.get("goodsId"), notGoodsId));
            }
            //非商品编号
            if (CollectionUtils.isNotEmpty(notGoodsIds)) {
                Predicate predicate =  cbuild.not(root.get("goodsId").in(notGoodsIds));
                predicates.add(predicate);
            }
            //非商品SKU编号
            if (StringUtils.isNotBlank(notGoodsInfoId)) {
                predicates.add(cbuild.notEqual(root.get("goodsInfoId"), notGoodsInfoId));
            }

            //屏蔽已绑定的跨境商品
            if (Objects.nonNull(notShowCrossGoodsFlag) && notShowCrossGoodsFlag) {
                predicates.add(cbuild.notEqual(root.get("pluginType"), PluginType.CROSS_BORDER));
            }

            //商品类型
            if (Objects.nonNull(pluginType)) {
                predicates.add(cbuild.notEqual(root.get("pluginType"), PluginType.fromValue(pluginType)));
            }

            // 商家类型
            if (Objects.nonNull(companyType)) {
                predicates.add(cbuild.equal(root.get("companyType"), companyType));
            }

            //批量供应商商品SKU编号
            if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
                predicates.add(root.get("providerGoodsInfoId").in(providerGoodsInfoIds));
            }
            if (thirdPlatformSkuId != null && thirdPlatformSkuId.size() > 0) {
                predicates.add(root.get("thirdPlatformSkuId").in(thirdPlatformSkuId));
            }
            if (StringUtils.isNotBlank(thirdPlatformSpuId)) {
                predicates.add(cbuild.equal(root.get("thirdPlatformSpuId"), thirdPlatformSpuId));
            }
            //业务员app商品状态筛选
            if (CollectionUtils.isNotEmpty(goodsSelectStatuses)) {
                List<Predicate> orPredicate = new ArrayList<>();
                goodsSelectStatuses.forEach(goodsInfoSelectStatus -> {
                    if (goodsInfoSelectStatus != null) {
                        if (goodsInfoSelectStatus == GoodsInfoSelectStatus.ADDED) {
                            orPredicate.add(cbuild.and(cbuild.equal(root.get("auditStatus"), CheckStatus.CHECKED), cbuild.equal(root.get("addedFlag"), AddedFlag.YES.toValue())));
                        } else if (goodsInfoSelectStatus == GoodsInfoSelectStatus.NOT_ADDED) {
                            orPredicate.add(cbuild.and(cbuild.equal(root.get("auditStatus"), CheckStatus.CHECKED), cbuild.equal(root.get("addedFlag"), AddedFlag.NO.toValue())));
                        } else if (goodsInfoSelectStatus == GoodsInfoSelectStatus.OTHER) {
                            orPredicate.add(root.get("auditStatus").in(CheckStatus.FORBADE, CheckStatus.NOT_PASS, CheckStatus.WAIT_CHECK));
                        }
                    }
                });
                predicates.add(cbuild.or(orPredicate.toArray(new Predicate[orPredicate.size()])));
            }

            if (saleType != null) {
//                Join<Goods, GoodsInfo> join = root.join("goods", JoinType.LEFT);
//                predicates.add(cbuild.equal(join.get("saleType"), saleType));
                predicates.add(cbuild.equal(root.get("saleType"), saleType));
            }

            /**
             * 企业购商品的审核状态
             */
            if (enterPriseAuditState != null && EnterpriseAuditState.INIT != enterPriseAuditState) {
                predicates.add(cbuild.equal(root.get("enterPriseAuditState"), enterPriseAuditState));
            }

            // 商品来源
            if (Objects.nonNull(goodsSource)) {
                predicates.add(cbuild.equal(root.get("goodsSource"), goodsSource));
            }

            // 批量商品来源
            if(CollectionUtils.isNotEmpty(goodsSourceList)){
                predicates.add(root.get("goodsSource")
                        .in(goodsSourceList.stream()
                                .map(GoodsSource::toValue)
                                .collect(Collectors.toList())));
            }

            //分销状态
            if (CollectionUtils.isNotEmpty(distributionGoodsAuditList)) {
                predicates.add(root.get("distributionGoodsAudit").in(distributionGoodsAuditList));
            }

            // 判断是否需要校验有货状态
            if (Constants.yes.equals(stockFlag)) {
                predicates.add(cbuild.gt(root.get("stock"), BigDecimal.ZERO));
            }

            // 判断商家类型
            if (Objects.nonNull(storeType)) {
                if(storeType == StoreType.SUPPLIER){
                    Predicate p1 = cbuild.equal(root.get("storeType"), StoreType.SUPPLIER);
                    Predicate p2 = cbuild.isNull(root.get("storeType"));
                    predicates.add(cbuild.or(p1, p2));
                } else {
                    predicates.add(cbuild.equal(root.get("storeType"), storeType));
                }
            }

            //过滤虚拟商品
            if (Objects.nonNull(goodsType)) {
                predicates.add(cbuild.equal(root.get("goodsType"), goodsType));
            }

            //周期购商品
            if (Objects.nonNull(isBuyCycle)) {
                predicates.add(cbuild.equal(root.get("isBuyCycle"), isBuyCycle));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
