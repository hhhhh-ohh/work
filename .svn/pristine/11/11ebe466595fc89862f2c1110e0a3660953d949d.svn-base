package com.wanmi.sbc.goods.goodsaudit.service;

import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>商品审核动态查询条件构建器</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
public class GoodsAuditWhereCriteriaBuilder {
    public static Specification<GoodsAudit> build(GoodsAuditQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-goodsIdList
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsIdList())) {
                predicates.add(root.get("goodsId").in(queryRequest.getGoodsIdList()));
            }

            // goodsId
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.equal(root.get("goodsId"), queryRequest.getGoodsId()));
            }

            // 模糊查询 - 旧商品Id
            if (StringUtils.isNotEmpty(queryRequest.getOldGoodsId())) {
                predicates.add(cbuild.like(root.get("oldGoodsId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getOldGoodsId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 销售类别(0:批发,1:零售)
            if (queryRequest.getSaleType() != null) {
                predicates.add(cbuild.equal(root.get("saleType"), queryRequest.getSaleType()));
            }

            // 商品分类Id
            if (queryRequest.getCateId() != null) {
                predicates.add(cbuild.equal(root.get("cateId"), queryRequest.getCateId()));
            }

            // 品牌Id
            if (queryRequest.getBrandId() != null) {
                predicates.add(cbuild.equal(root.get("brandId"), queryRequest.getBrandId()));
            }

            // 模糊查询 - goodsName
            if (StringUtils.isNotEmpty(queryRequest.getGoodsName())) {
                predicates.add(cbuild.like(root.get("goodsName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - goodsSubtitle
            if (StringUtils.isNotEmpty(queryRequest.getGoodsSubtitle())) {
                predicates.add(cbuild.like(root.get("goodsSubtitle"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsSubtitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - goodsNo
            if (StringUtils.isNotEmpty(queryRequest.getGoodsNo())) {
                predicates.add(cbuild.equal(root.get("goodsNo"), queryRequest.getGoodsNo()));
            }

            // 模糊查询 - goodsUnit
            if (StringUtils.isNotEmpty(queryRequest.getGoodsUnit())) {
                predicates.add(cbuild.like(root.get("goodsUnit"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsUnit()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - goodsImg
            if (StringUtils.isNotEmpty(queryRequest.getGoodsImg())) {
                predicates.add(cbuild.like(root.get("goodsImg"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsImg()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - goodsVideo
            if (StringUtils.isNotEmpty(queryRequest.getGoodsVideo())) {
                predicates.add(cbuild.like(root.get("goodsVideo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsVideo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品重量
            if (queryRequest.getGoodsWeight() != null) {
                predicates.add(cbuild.equal(root.get("goodsWeight"), queryRequest.getGoodsWeight()));
            }

            // 商品体积
            if (queryRequest.getGoodsCubage() != null) {
                predicates.add(cbuild.equal(root.get("goodsCubage"), queryRequest.getGoodsCubage()));
            }

            // 单品运费模板id
            if (queryRequest.getFreightTempId() != null) {
                predicates.add(cbuild.equal(root.get("freightTempId"), queryRequest.getFreightTempId()));
            }

            // 市场价
            if (queryRequest.getMarketPrice() != null) {
                predicates.add(cbuild.equal(root.get("marketPrice"), queryRequest.getMarketPrice()));
            }

            // 供货价
            if (queryRequest.getSupplyPrice() != null) {
                predicates.add(cbuild.equal(root.get("supplyPrice"), queryRequest.getSupplyPrice()));
            }

            // 建议零售价
            if (queryRequest.getRetailPrice() != null) {
                predicates.add(cbuild.equal(root.get("retailPrice"), queryRequest.getRetailPrice()));
            }

            // 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
            if (queryRequest.getGoodsType() != null) {
                predicates.add(cbuild.equal(root.get("goodsType"), queryRequest.getGoodsType()));
            }

            // 划线价
            if (queryRequest.getLinePrice() != null) {
                predicates.add(cbuild.equal(root.get("linePrice"), queryRequest.getLinePrice()));
            }

            // 成本价
            if (queryRequest.getCostPrice() != null) {
                predicates.add(cbuild.equal(root.get("costPrice"), queryRequest.getCostPrice()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            // 大于或等于 搜索条件:更新时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:更新时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 大于或等于 搜索条件:上下架时间开始
            if (queryRequest.getAddedTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("addedTime"),
                        queryRequest.getAddedTimeBegin()));
            }
            // 小于或等于 搜索条件:上下架时间截止
            if (queryRequest.getAddedTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("addedTime"),
                        queryRequest.getAddedTimeEnd()));
            }

            // 商品来源，0供应商，1商家,2linkedmall
            if (queryRequest.getGoodsSource() != null) {
                predicates.add(cbuild.equal(root.get("goodsSource"), queryRequest.getGoodsSource()));
            }

            // 删除标识,0:未删除1:已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.fromValue(queryRequest.getDelFlag())));
            }

            // 上下架状态,0:下架1:上架2:部分上架
            if (queryRequest.getAddedFlag() != null) {
                predicates.add(cbuild.equal(root.get("addedFlag"), queryRequest.getAddedFlag()));
            }

            // 规格类型,0:单规格1:多规格
            if (queryRequest.getMoreSpecFlag() != null) {
                predicates.add(cbuild.equal(root.get("moreSpecFlag"), queryRequest.getMoreSpecFlag()));
            }

            // 设价类型,0:按客户1:按订货量2:按市场价
            if (queryRequest.getPriceType() != null) {
                predicates.add(cbuild.equal(root.get("priceType"), queryRequest.getPriceType()));
            }

            // 订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)
            if (queryRequest.getAllowPriceSet() != null) {
                predicates.add(cbuild.equal(root.get("allowPriceSet"), queryRequest.getAllowPriceSet()));
            }

            // 按客户单独定价,0:否1:是
            if (queryRequest.getCustomFlag() != null) {
                predicates.add(cbuild.equal(root.get("customFlag"), queryRequest.getCustomFlag()));
            }

            // 叠加客户等级折扣，0:否1:是
            if (queryRequest.getLevelDiscountFlag() != null) {
                predicates.add(cbuild.equal(root.get("levelDiscountFlag"), queryRequest.getLevelDiscountFlag()));
            }

            // 店铺标识
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 公司信息ID
            if (queryRequest.getCompanyInfoId() != null) {
                predicates.add(cbuild.equal(root.get("companyInfoId"), queryRequest.getCompanyInfoId()));
            }

            //店铺类型  0、平台自营 1、第三方商家
            if (queryRequest.getCompanyType() != null){
                predicates.add(cbuild.equal(root.get("companyType"), queryRequest.getCompanyType()));
            }

            //商品来源，供应商商品/商家商品
            if (queryRequest.getSearchGoodsSource() != null) {
                if (queryRequest.getSearchGoodsSource() == GoodsSource.PROVIDER){
                    predicates.add(cbuild.isNotNull(root.get("providerGoodsId")));
                }else if(queryRequest.getSearchGoodsSource() == GoodsSource.SELLER){
                    predicates.add(cbuild.isNull(root.get("providerGoodsId")));
                }
            }

            // 模糊查询 - supplierName
            if (StringUtils.isNotEmpty(queryRequest.getSupplierName())) {
                predicates.add(cbuild.like(root.get("supplierName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getSupplierName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:提交审核时间开始
            if (queryRequest.getSubmitTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("submitTime"),
                        queryRequest.getSubmitTimeBegin()));
            }
            // 小于或等于 搜索条件:提交审核时间截止
            if (queryRequest.getSubmitTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("submitTime"),
                        queryRequest.getSubmitTimeEnd()));
            }

            // 审核状态,0:未审核1 审核通过2审核失败3禁用中
            if (queryRequest.getAuditStatus() != null) {
                predicates.add(cbuild.equal(root.get("auditStatus"), CheckStatus.fromValue(queryRequest.getAuditStatus())));
            }

            // 模糊查询 - auditReason
            if (StringUtils.isNotEmpty(queryRequest.getAuditReason())) {
                predicates.add(cbuild.like(root.get("auditReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAuditReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商品详情
            if (StringUtils.isNotEmpty(queryRequest.getGoodsDetail())) {
                predicates.add(cbuild.like(root.get("goodsDetail"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsDetail()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 移动端图文详情
            if (StringUtils.isNotEmpty(queryRequest.getGoodsMobileDetail())) {
                predicates.add(cbuild.like(root.get("goodsMobileDetail"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsMobileDetail()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品评论数
            if (queryRequest.getGoodsEvaluateNum() != null) {
                predicates.add(cbuild.equal(root.get("goodsEvaluateNum"), queryRequest.getGoodsEvaluateNum()));
            }

            // 商品收藏量
            if (queryRequest.getGoodsCollectNum() != null) {
                predicates.add(cbuild.equal(root.get("goodsCollectNum"), queryRequest.getGoodsCollectNum()));
            }

            // 商品销量
            if (queryRequest.getGoodsSalesNum() != null) {
                predicates.add(cbuild.equal(root.get("goodsSalesNum"), queryRequest.getGoodsSalesNum()));
            }

            // 商品好评数量
            if (queryRequest.getGoodsFavorableCommentNum() != null) {
                predicates.add(cbuild.equal(root.get("goodsFavorableCommentNum"), queryRequest.getGoodsFavorableCommentNum()));
            }

            // 注水销量
            if (queryRequest.getShamSalesNum() != null) {
                predicates.add(cbuild.equal(root.get("shamSalesNum"), queryRequest.getShamSalesNum()));
            }

            // 排序号
            if (queryRequest.getSortNo() != null) {
                predicates.add(cbuild.equal(root.get("sortNo"), queryRequest.getSortNo()));
            }

            // 0:多规格1:单规格
            if (queryRequest.getSingleSpecFlag() != null) {
                predicates.add(cbuild.equal(root.get("singleSpecFlag"), queryRequest.getSingleSpecFlag().equals(Constants.yes)));
            }

            // 是否定时上架 0:否1:是
            if (queryRequest.getAddedTimingFlag() != null) {
                predicates.add(cbuild.equal(root.get("addedTimingFlag"), queryRequest.getAddedTimingFlag().equals(Constants.yes)));
            }

            // 大于或等于 搜索条件:定时上架时间开始
            if (queryRequest.getAddedTimingTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("addedTimingTime"),
                        queryRequest.getAddedTimingTimeBegin()));
            }
            // 小于或等于 搜索条件:定时上架时间截止
            if (queryRequest.getAddedTimingTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("addedTimingTime"),
                        queryRequest.getAddedTimingTimeEnd()));
            }

            // 库存（准实时）
            if (queryRequest.getStock() != null) {
                predicates.add(cbuild.equal(root.get("stock"), queryRequest.getStock()));
            }

            // 最小市场价
            if (queryRequest.getSkuMinMarketPrice() != null) {
                predicates.add(cbuild.equal(root.get("skuMinMarketPrice"), queryRequest.getSkuMinMarketPrice()));
            }

            // 模糊查询 - goodsBuyTypes
            if (StringUtils.isNotEmpty(queryRequest.getGoodsBuyTypes())) {
                predicates.add(cbuild.like(root.get("goodsBuyTypes"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsBuyTypes()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - providerGoodsId
            if (StringUtils.isNotEmpty(queryRequest.getProviderGoodsId())) {
                predicates.add(cbuild.like(root.get("providerGoodsId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getProviderGoodsId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 供应商Id
            if (queryRequest.getProviderId() != null) {
                predicates.add(cbuild.equal(root.get("providerId"), queryRequest.getProviderId()));
            }

            // 模糊查询 - providerName
            if (StringUtils.isNotEmpty(queryRequest.getProviderName())) {
                predicates.add(cbuild.like(root.get("providerName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getProviderName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 建议零售价
            if (queryRequest.getRecommendedRetailPrice() != null) {
                predicates.add(cbuild.equal(root.get("recommendedRetailPrice"), queryRequest.getRecommendedRetailPrice()));
            }

            // 是否需要同步 0：不需要同步 1：需要同步
            if (queryRequest.getNeedSynchronize() != null) {
                predicates.add(cbuild.equal(root.get("needSynchronize"), queryRequest.getNeedSynchronize().equals(Constants.yes)));
            }

            // 模糊查询 - deleteReason
            if (StringUtils.isNotEmpty(queryRequest.getDeleteReason())) {
                predicates.add(cbuild.like(root.get("deleteReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDeleteReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - addFalseReason
            if (StringUtils.isNotEmpty(queryRequest.getAddFalseReason())) {
                predicates.add(cbuild.like(root.get("addFalseReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAddFalseReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否可售，0不可售，1可售
            if (queryRequest.getVendibility() != null) {
                predicates.add(cbuild.equal(root.get("vendibility"), queryRequest.getVendibility()));
            }

            // 模糊查询 - thirdPlatformSpuId
            if (StringUtils.isNotEmpty(queryRequest.getThirdPlatformSpuId())) {
                predicates.add(cbuild.like(root.get("thirdPlatformSpuId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getThirdPlatformSpuId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 第三方卖家id
            if (queryRequest.getSellerId() != null) {
                predicates.add(cbuild.equal(root.get("sellerId"), queryRequest.getSellerId()));
            }

            // 三方渠道类目id
            if (queryRequest.getThirdCateId() != null) {
                predicates.add(cbuild.equal(root.get("thirdCateId"), queryRequest.getThirdCateId()));
            }

            // 三方平台类型，0，linkedmall
            if (queryRequest.getThirdPlatformType() != null) {
                predicates.add(cbuild.equal(root.get("thirdPlatformType"), ThirdPlatformType.fromValue(queryRequest.getThirdPlatformType())));
            }

            // 供应商状态 0: 关店 1:开店
            if (queryRequest.getProviderStatus() != null) {
                predicates.add(cbuild.equal(root.get("providerStatus"), queryRequest.getProviderStatus()));
            }

            // 模糊查询 - 标签id，以逗号拼凑
            if (StringUtils.isNotEmpty(queryRequest.getLabelId())) {
                predicates.add(cbuild.like(root.get("labelIdStr"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLabelId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 商品类型；0:一般商品 1:跨境商品
            if (queryRequest.getPluginType() != null) {
                predicates.add(cbuild.equal(root.get("pluginType"), PluginType.fromValue(queryRequest.getPluginType())));
            }

            // 商家类型 0 普通商家,1 跨境商家
            if (queryRequest.getSupplierType() != null) {
                predicates.add(cbuild.equal(root.get("supplierType"), SupplierType.fromValue(queryRequest.getSupplierType())));
            }

            // 商家类型：0供应商，1商家，2：O2O商家，3：跨境商家
            if (queryRequest.getStoreType() != null) {
                predicates.add(cbuild.equal(root.get("storeType"), StoreType.fromValue(queryRequest.getStoreType())));
            }

            // 商家端编辑供应商商品判断页面是否是独立设置价格 0 ：否  1：是
            if (queryRequest.getIsIndependent() != null) {
                predicates.add(cbuild.equal(root.get("isIndependent"), EnableStatus.fromValue(queryRequest.getIsIndependent())));
            }

            // 审核类型 1:初次审核 2:二次审核
            if (queryRequest.getAuditType() != null) {
                predicates.add(cbuild.equal(root.get("auditType"), queryRequest.getAuditType()));
            }

            // 批量店铺分类关联商品编号
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreCateGoodsIds())) {
                predicates.add(root.get("goodsId").in(queryRequest.getStoreCateGoodsIds()));
            }

            // 批量查询分类编号
            if (CollectionUtils.isNotEmpty(queryRequest.getCateIds())) {
                predicates.add(root.get("cateId").in(queryRequest.getCateIds()));
            }

            // 模糊查询 - 商品编号
            if (StringUtils.isNotEmpty(queryRequest.getLikeGoodsNo())) {
                predicates.add(cbuild.like(root.get("goodsNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getLikeGoodsNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 批量查询分类编号
            if (StringUtils.isNotBlank(queryRequest.getNotGoodsId())) {
                predicates.add(cbuild.notEqual(root.get("goodsId"), queryRequest.getNotGoodsId()));
            }

            // 批量查询分类编号
            if (StringUtils.isNotBlank(queryRequest.getNotOldGoodsId())) {
                predicates.add(cbuild.notEqual(root.get("oldGoodsId"), queryRequest.getNotOldGoodsId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
