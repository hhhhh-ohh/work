package com.wanmi.sbc.elastic.sku.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsInfoSelectStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * <p>EsGoods表动态查询条件构建器</p>
 * @author dyt
 * @date 2020-12-04 10:39:15
 */
public class EsSkuSearchCriteriaBuilder {

    /**
     * 封装公共条件
     *
     * @return
     */
    protected static BoolQuery getWhereCriteria(EsSkuPageRequest request) {
//        BoolQueryBuilder boolQb = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQb = QueryBuilders.bool();
        //批量SKU编号
        if(CollectionUtils.isNotEmpty(request.getGoodsInfoIds())){
//            boolQb.must(idsQuery().addIds(request.getGoodsInfoIds().toArray(new String[]{})));
            boolQb.must(ids(g -> g.values(request.getGoodsInfoIds())));
        }
        //SPU编号
        if(StringUtils.isNotBlank(request.getGoodsId())){
//            boolQb.must(termQuery("goodsInfo.goodsId", request.getGoodsId()));
            boolQb.must(term(g -> g.field("goodsInfo.goodsId").value(request.getGoodsId())));
        }
        //批量店铺分类ID
        if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
//            boolQb.must(termsQuery("storeCateIds", request.getStoreCateIds()));
            boolQb.must(terms(g -> g.field("storeCateIds").terms(x -> x.value(request.getStoreCateIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        } else if (Objects.nonNull(request.getStoreCateId())){
//            boolQb.must(termQuery("storeCateIds", request.getStoreCateId()));
            boolQb.must(term(g -> g.field("storeCateIds").value(request.getStoreCateId())));
        }
        //批量SPU编号
        if(CollectionUtils.isNotEmpty(request.getGoodsIds())){
            /*boolQb.filter(
                    termsQuery(
                            "goodsInfo.goodsId", request.getGoodsIds()));*/
            boolQb.filter(terms(g -> g.field("goodsInfo.goodsId").terms(x -> x.value(request.getGoodsIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //批量SKU编号
        if(CollectionUtils.isNotEmpty(request.getGoodsInfoNos())){
//            boolQb.must(termsQuery("goodsInfo.goodsInfoNo", request.getGoodsInfoNos()));
            boolQb.must(terms(g -> g.field("goodsInfo.goodsInfoNo").terms(x -> x.value(request.getGoodsInfoNos().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //SPU编码
        if(StringUtils.isNotEmpty(request.getLikeGoodsNo())){
//            boolQb.must(wildcardQuery("goodsNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim())));
            boolQb.must(wildcard(g -> g.field("goodsNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim()))));
        }

        //SKU编码
        if(StringUtils.isNotEmpty(request.getLikeGoodsInfoNo())){
//            boolQb.must(wildcardQuery("goodsInfo.goodsInfoNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim())));
            boolQb.must(wildcard(g -> g.field("goodsInfo.goodsInfoNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim()))));
        }
        //店铺ID
        if(request.getStoreId() != null){
//            boolQb.must(termQuery("goodsInfo.storeId", request.getStoreId()));
            boolQb.must(term(g -> g.field("goodsInfo.storeId").value(request.getStoreId())));
        }

        //店铺名称
        if (StringUtils.isNotBlank(request.getStoreName())) {
//            boolQb.must(matchPhraseQuery("goodsInfo.storeName", request.getStoreName().trim()));
            boolQb.must(matchPhrase(g -> g.field("goodsInfo.storeName").query(request.getStoreName().trim())));
        }

        //店铺名称
        if (StringUtils.isNotBlank(request.getLikeSupplierName())) {
//            boolQb.must(wildcardQuery("goodsInfo.storeName.keyword", "*"+request.getLikeSupplierName().trim()+"*"));
            boolQb.must(wildcard(g -> g.field("goodsInfo.storeName.keyword").value("*" + request.getLikeSupplierName().trim() + "*")));
        }

        //分类ID
        if(request.getCateId() != null && request.getCateId() > 0){
//            boolQb.must(termQuery("goodsInfo.cateId", request.getCateId()));
            boolQb.must(term(g -> g.field("goodsInfo.cateId").value(request.getCateId())));
        }

        //分类ID
        if(CollectionUtils.isNotEmpty(request.getCateIds())){
//            boolQb.must(termsQuery("goodsInfo.cateId", request.getCateIds()));
            boolQb.must(terms(g -> g.field("goodsInfo.cateId").terms(x -> x.value(request.getCateIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //公司信息ID
        if(request.getCompanyInfoId() != null){
//            boolQb.must(termQuery("goodsInfo.companyInfoId", request.getCompanyInfoId()));
            boolQb.must(term(g -> g.field("goodsInfo.companyInfoId").value(request.getCompanyInfoId())));
        }
        if (request.getNotThirdPlatformType() != null && request.getNotThirdPlatformType().size() > 0) {
            for(int notThirdPlatformType : request.getNotThirdPlatformType()){
//                boolQb.mustNot(termQuery("goodsInfo.thirdPlatformType", notThirdPlatformType));
                boolQb.mustNot(term(g -> g.field("goodsInfo.thirdPlatformType").value(notThirdPlatformType)));
            }
        }

        //批量店铺ID
        if(CollectionUtils.isNotEmpty(request.getStoreIds())){
//            boolQb.must(termsQuery("goodsInfo.storeId", request.getStoreIds()));
            boolQb.must(terms(g -> g.field("goodsInfo.storeId").terms(x -> x.value(request.getStoreIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //模糊查询名称
        if(StringUtils.isNotEmpty(request.getLikeGoodsName())){
//            boolQb.must(wildcardQuery("goodsName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsName().trim())));
            boolQb.must(wildcard(g -> g.field("goodsName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsName().trim()))));
        }

        //关键字搜索
        if (StringUtils.isNotBlank(request.getKeyword())) {
            String str = ElasticCommonUtil.replaceEsLikeWildcard(request.getKeyword().trim());
            /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
            bq.should(wildcardQuery("goodsName", str));
            bq.should(wildcardQuery("goodsNo", str));
            boolQb.must(bq);*/
            BoolQuery.Builder bq = QueryBuilders.bool();
            bq.should(wildcard(g -> g.field("goodsName").value(str)));
            bq.should(wildcard(g -> g.field("goodsNo").value(str)));
            boolQb.must(a -> a.bool(bq.build()));
        }
        //上下架状态
        if (request.getAddedFlag() != null) {
//            boolQb.must(termQuery("goodsInfo.addedFlag", request.getAddedFlag()));
            boolQb.must(term(g -> g.field("goodsInfo.addedFlag").value(request.getAddedFlag())));
        }
        //多个上下架状态
        if (CollectionUtils.isNotEmpty(request.getAddedFlags())) {
//            boolQb.must(termsQuery("goodsInfo.addedFlag", request.getAddedFlags()));
            boolQb.must(terms(g -> g.field("goodsInfo.addedFlag").terms(x -> x.value(request.getAddedFlags().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //单个品牌
        if (request.getBrandId() != null && request.getBrandId() > 0) {
//            boolQb.must(termQuery("goodsInfo.brandId", request.getBrandId()));
            boolQb.must(term(g -> g.field("goodsInfo.brandId").value(request.getBrandId())));
        }
        //多个品牌
        if(CollectionUtils.isNotEmpty(request.getBrandIds())){
//            boolQb.must(termsQuery("goodsInfo.brandId",request.getBrandIds()));
            boolQb.must(terms(g -> g.field("goodsInfo.brandId").terms(x -> x.value(request.getBrandIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //审核状态
        if(request.getAuditStatus() != null){
//            boolQb.must(termQuery("auditStatus", request.getAuditStatus().toValue()));
            boolQb.must(term(g -> g.field("auditStatus").value(request.getAuditStatus().toValue())));
        }

        //多个审核状态
        if(CollectionUtils.isNotEmpty(request.getAuditStatuses())){
//            boolQb.must(termsQuery("auditStatus", request.getAuditStatuses().stream().map(CheckStatus::toValue).collect(Collectors.toList())));
            boolQb.must(terms(g -> g.field("auditStatus").terms(x -> x.value(request.getAuditStatuses().stream().map(CheckStatus::toValue).map(FieldValue::of).collect(Collectors.toList())))));
        }
        //过滤积分价商品
        if (Objects.nonNull(request.getIntegralPriceFlag()) && Objects.equals(Boolean.TRUE, request.getIntegralPriceFlag())){
//            boolQb.must(termQuery("goodsInfo.buyPoint", 0));
            boolQb.must(term(g -> g.field("goodsInfo.buyPoint").value(0)));
        }
        //市场价范围 - 大于等于
        if(Objects.nonNull(request.getSalePriceFirst())){
//            boolQb.must(rangeQuery("goodsInfo.marketPrice").gte(request.getSalePriceFirst().doubleValue()));
            boolQb.must(range(g -> g.field("goodsInfo.marketPrice").gte(JsonData.of(request.getSalePriceFirst().doubleValue()))));
        }
        //市场价范围 - 小于等于
        if (Objects.nonNull(request.getSalePriceLast())){
//            boolQb.must(rangeQuery("goodsInfo.marketPrice").lte(request.getSalePriceLast().doubleValue()));
            boolQb.must(range(g -> g.field("goodsInfo.marketPrice").lte(JsonData.of(request.getSalePriceLast().doubleValue()))));
        }
        //预估佣金范围- 大于等于
        if(Objects.nonNull(request.getDistributionCommissionFirst())){
//            boolQb.must(rangeQuery("goodsInfo.distributionCommission").gte(request.getDistributionCommissionFirst().doubleValue()));
            boolQb.must(range(g -> g.field("goodsInfo.distributionCommission").gte(JsonData.of(request.getDistributionCommissionFirst().doubleValue()))));
        }

        //预估佣金范围- 小于等于
        if(Objects.nonNull(request.getDistributionCommissionLast())){
//            boolQb.must(rangeQuery("goodsInfo.distributionCommission").lte(request.getDistributionCommissionLast().doubleValue()));
            boolQb.must(range(g -> g.field("goodsInfo.distributionCommission").lte(JsonData.of(request.getDistributionCommissionLast().doubleValue()))));
        }

        //分销佣金比例开始 - 大于等于
        if(Objects.nonNull(request.getCommissionRateFirst())){
//            boolQb.must(rangeQuery("goodsInfo.commissionRate").gte(request.getCommissionRateFirst().doubleValue()));
            boolQb.must(range(g -> g.field("goodsInfo.commissionRate").gte(JsonData.of(request.getCommissionRateFirst().doubleValue()))));
        }

        //分销佣金比例结束- 小于等于
        if(Objects.nonNull(request.getCommissionRateLast())){
//            boolQb.must(rangeQuery("goodsInfo.commissionRate").lte(request.getCommissionRateLast().doubleValue()));
            boolQb.must(range(g -> g.field("goodsInfo.commissionRate").lte(JsonData.of(request.getCommissionRateLast().doubleValue()))));
        }

        //库存范围 - 大于等于
        if(Objects.nonNull(request.getStockFirst())){
//            boolQb.must(rangeQuery("goodsInfo.stock").gte(request.getStockFirst()));
            boolQb.must(range(g -> g.field("goodsInfo.stock").gte(JsonData.of(request.getStockFirst()))));
        }
        //库存范围 - 小于等于
        if (Objects.nonNull(request.getStockLast())){
//            boolQb.must(rangeQuery("goodsInfo.stock").lte(request.getStockLast()));
            boolQb.must(range(g -> g.field("goodsInfo.stock").lte(JsonData.of(request.getStockLast()))));
        }

        //分销商品审核状态
        if(Objects.nonNull(request.getDistributionGoodsAudit())){
//            boolQb.must(termQuery("goodsInfo.distributionGoodsAudit", request.getDistributionGoodsAudit()));
            boolQb.must(term(g -> g.field("goodsInfo.distributionGoodsAudit").value(request.getDistributionGoodsAudit())));
        }

        //企业购商品的审核状态
        if(Objects.nonNull(request.getEnterPriseAuditState())){
//            boolQb.must(termQuery("goodsInfo.enterPriseAuditStatus", request.getEnterPriseAuditState().toValue()));
            boolQb.must(term(g -> g.field("goodsInfo.enterPriseAuditStatus").value(request.getEnterPriseAuditState().toValue())));
        }
        //删除标记
        if (request.getDelFlag() != null) {
//            boolQb.must(termQuery("goodsInfo.delFlag", request.getDelFlag()));
            boolQb.must(term(g -> g.field("goodsInfo.delFlag").value(request.getDelFlag())));
        }
        //非商品编号
        if(StringUtils.isNotBlank(request.getNotGoodsId())){
//            boolQb.mustNot(termQuery("goodsInfo.goodsId", request.getNotGoodsId()));
            boolQb.mustNot(term(g -> g.field("goodsInfo.goodsId").value(request.getNotGoodsId())));
        }
        //非商品SKU编号
        if(StringUtils.isNotBlank(request.getNotGoodsInfoId())){
//            boolQb.mustNot(idsQuery().addIds(request.getNotGoodsInfoId()));
            boolQb.mustNot(ids(g -> g.values(request.getNotGoodsInfoId())));
        }
        //非商品SKU编号
        if(CollectionUtils.isNotEmpty(request.getNotGoodsInfoIds())){
            /*boolQb.mustNot(idsQuery().addIds(request.getNotGoodsInfoIds()
                    .toArray(new String[request.getNotGoodsInfoIds().size()])));*/
            boolQb.mustNot(ids(g -> g.values(request.getNotGoodsInfoIds())));
        }

        // 商家类型
        if(Objects.nonNull(request.getCompanyType())){
//            boolQb.must(termQuery("goodsInfo.companyType", request.getCompanyType().toValue()));
            boolQb.must(term(g -> g.field("goodsInfo.companyType").value(request.getCompanyType().toValue())));
        }

        //查询标签关联
        if (Objects.nonNull(request.getLabelId())) {
            /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
            bq.must(termQuery("goodsLabelList.goodsLabelId", request.getLabelId()));
            boolQb.must(nestedQuery("goodsLabelList", bq, ScoreMode.None));*/
            boolQb.must(nested(n -> n.path("goodsLabelList")
                    .query(term(g -> g.field("goodsLabelList.goodsLabelId").value(request.getLabelId())))
                    .scoreMode(ChildScoreMode.None)));
        }

        //可售性
        if(request.getVendibility() != null) {
            /*boolQb.must(termQuery("goodsInfo.vendibilityStatus", DefaultFlag.YES.toValue()));
            boolQb.must(termQuery("goodsInfo.providerStatus", Constants.yes));*/
            boolQb.must(term(g -> g.field("goodsInfo.vendibilityStatus").value(DefaultFlag.YES.toValue())));
            boolQb.must(term(g -> g.field("goodsInfo.providerStatus").value(Constants.yes)));
        }

        //业务员app商品状态筛选
        if(CollectionUtils.isNotEmpty(request.getGoodsSelectStatuses())){
//            BoolQueryBuilder orBq = QueryBuilders.boolQuery();
            BoolQuery.Builder orBq = QueryBuilders.bool();
            request.getGoodsSelectStatuses().forEach(goodsInfoSelectStatus -> {
                if(goodsInfoSelectStatus != null){
                    if(goodsInfoSelectStatus == GoodsInfoSelectStatus.ADDED){
                        /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
                        bq.should(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));
                        bq.should(termQuery("goodsInfo.addedFlag", AddedFlag.YES.toValue()));
                        orBq.should(bq);*/
                        BoolQuery.Builder bq = QueryBuilders.bool();
                        bq.should(term(g -> g.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
                        bq.should(term(g -> g.field("goodsInfo.addedFlag").value(AddedFlag.YES.toValue())));
                        orBq.should(a -> a.bool(bq.build()));
                    } else if(goodsInfoSelectStatus == GoodsInfoSelectStatus.NOT_ADDED){
//                        BoolQueryBuilder bq = QueryBuilders.boolQuery();
//                        bq.should(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));
//                        bq.should(termQuery("goodsInfo.addedFlag", AddedFlag.NO.toValue()));
//                        orBq.should(bq);
                        BoolQuery.Builder bq = QueryBuilders.bool();
                        bq.should(term(g -> g.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
                        bq.should(term(g -> g.field("goodsInfo.addedFlag").value(AddedFlag.NO.toValue())));
                        orBq.should(a -> a.bool(bq.build()));
                    } else if(goodsInfoSelectStatus == GoodsInfoSelectStatus.OTHER){
                        List<Integer> status = Arrays.asList(CheckStatus.FORBADE.toValue(), CheckStatus.NOT_PASS.toValue(), CheckStatus.WAIT_CHECK.toValue());
//                        orBq.should(termsQuery("auditStatus", status));
                        orBq.should(terms(g -> g.field("auditStatus").terms(t -> t.value(status.stream().map(FieldValue::of).collect(Collectors.toList())))));
                    }
                }
            });
//            boolQb.must(orBq);
            boolQb.must(a -> a.bool(orBq.build()));
        }

        if (request.getSaleType() != null){
//            boolQb.must(termQuery("goodsInfo.saleType", request.getSaleType()));
            boolQb.must(term(g -> g.field("goodsInfo.saleType").value(request.getSaleType())));
        }

        // 商品来源
        if(Objects.nonNull(request.getGoodsSource())){
//            boolQb.must(termQuery("goodsSource", request.getGoodsSource()));
            boolQb.must(term(g -> g.field("goodsSource").value(request.getGoodsSource())));
        }

        //商品来源，供应商商品/商家商品
        if (!ObjectUtils.isEmpty(request.getSearchGoodsSource())) {
            if (request.getSearchGoodsSource() == GoodsSource.PROVIDER){
//                boolQb.must(existsQuery("goodsInfo.providerId"));
                boolQb.must(exists(g -> g.field("goodsInfo.providerId")));
            }else if(request.getSearchGoodsSource() == GoodsSource.SELLER){
//                boolQb.mustNot(existsQuery("goodsInfo.providerId"));
                boolQb.mustNot(exists(g -> g.field("goodsInfo.providerId")));
            }
        }

        //查询销量
        if(Objects.nonNull(request.getMinGoodsSalesNum()) && Objects.isNull(request.getMaxGoodsSalesNum())){
//            boolQb.must(rangeQuery("goodsInfo.goodsSalesNum").gte(request.getMinGoodsSalesNum()));
            boolQb.must(range(g -> g.field("goodsInfo.goodsSalesNum").gte(JsonData.of(request.getMinGoodsSalesNum()))));
        }
        if(Objects.nonNull(request.getMaxGoodsSalesNum()) && Objects.isNull(request.getMinGoodsSalesNum())){
//            boolQb.must(rangeQuery("goodsInfo.goodsSalesNum").lte(request.getMaxGoodsSalesNum()));
            boolQb.must(range(g -> g.field("goodsInfo.goodsSalesNum").lte(JsonData.of(request.getMaxGoodsSalesNum()))));
        }
        if(Objects.nonNull(request.getMaxGoodsSalesNum()) && Objects.nonNull(request.getMinGoodsSalesNum())){
//            boolQb.must(rangeQuery("goodsInfo.goodsSalesNum").gte(request.getMinGoodsSalesNum()).lte(request.getMaxGoodsSalesNum()));
            boolQb.must(range(g -> g.field("goodsInfo.goodsSalesNum").gte(JsonData.of(request.getMinGoodsSalesNum())).lte(JsonData.of(request.getMaxGoodsSalesNum()))));
        }
        //查询市场价
        if(Objects.nonNull(request.getMinMarketPrice()) && Objects.isNull(request.getMaxMarketPrice())){
//            RangeQueryBuilder marketPrice = QueryBuilders.rangeQuery("goodsInfo.marketPrice")
//                    .gte(request.getMinMarketPrice().doubleValue());
//            boolQb.must(marketPrice);
            boolQb.must(range(g -> g.field("goodsInfo.marketPrice").gte(JsonData.of(request.getMinMarketPrice().doubleValue()))));
        }
        if(Objects.nonNull(request.getMaxMarketPrice()) && Objects.isNull(request.getMinMarketPrice())){
            /*RangeQueryBuilder marketPrice = QueryBuilders.rangeQuery("goodsInfo.marketPrice")
                    .lte(request.getMaxMarketPrice().doubleValue());
            boolQb.must(marketPrice);*/
            boolQb.must(range(g -> g.field("goodsInfo.marketPrice").lte(JsonData.of(request.getMaxMarketPrice().doubleValue()))));
        }
        if(Objects.nonNull(request.getMaxMarketPrice()) && Objects.nonNull(request.getMinMarketPrice())){
            /*RangeQueryBuilder marketPrice = QueryBuilders.rangeQuery("goodsInfo.marketPrice")
                    .gte(request.getMinMarketPrice().doubleValue())
                    .lte(request.getMaxMarketPrice().doubleValue());
            boolQb.must(marketPrice);*/
            boolQb.must(range(g -> g.field("goodsInfo.marketPrice")
                    .gte(JsonData.of(request.getMinMarketPrice().doubleValue()))
                    .lte(JsonData.of(request.getMaxMarketPrice().doubleValue()))));
        }

        //查询库存
        if(Objects.nonNull(request.getMinStock()) ){
//            boolQb.must(rangeQuery("goodsInfo.stock").gte(request.getMinStock()));
            boolQb.must(range(g -> g.field("goodsInfo.stock").gte(JsonData.of(request.getMinStock()))));
        }
        if(Objects.nonNull(request.getMaxStock())){
//            boolQb.must(rangeQuery("goodsInfo.stock").lte(request.getMaxStock()));
            boolQb.must(range(g -> g.field("goodsInfo.stock").lte(JsonData.of(request.getMaxStock()))));
        }

        // 是否排除供应商商品(包括由供应商品库导入的商品)
        if(Boolean.TRUE.equals(request.getExcludeProviderFlag())){
//            boolQb.mustNot(QueryBuilders.existsQuery("goodsInfo.providerId"));
            boolQb.mustNot(exists(g -> g.field("goodsInfo.providerId")));
        }

        //封装跨境信息
        if (Objects.nonNull(request.getPluginType())) {
//            boolQb.must(termQuery("goodsInfo.pluginType", request.getPluginType()));
            boolQb.must(term(g -> g.field("goodsInfo.pluginType").value(request.getPluginType())));
        }
        if (StringUtils.isNotEmpty(request.getTradeType())) {
//            boolQb.must(termQuery("goodsInfo.extendedAttributes.tradeType", request.getTradeType()));
            boolQb.must(term(g -> g.field("goodsInfo.extendedAttributes.tradeType").value(request.getTradeType())));
        }
        if (CollectionUtils.isNotEmpty(request.getTradeTypes())){
//            boolQb.must(termsQuery("goodsInfo.extendedAttributes.tradeType", request.getTradeTypes()));
            boolQb.must(terms(g -> g.field("goodsInfo.extendedAttributes.tradeType")
                    .terms(v -> v.value(request.getTradeTypes().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        if (StringUtils.isNotEmpty(request.getElectronPort())) {
//            boolQb.must(termQuery("goodsInfo.extendedAttributes.electronPort", request.getElectronPort()));
            boolQb.must(term(g -> g.field("goodsInfo.extendedAttributes.electronPort").value(request.getElectronPort())));
        }
        if (CollectionUtils.isNotEmpty(request.getRecordStatus())){
//            boolQb.must(termsQuery("goodsInfo.extendedAttributes.recordStatus", request.getRecordStatus()));
            boolQb.must(terms(g -> g.field("goodsInfo.extendedAttributes.recordStatus")
                    .terms(v -> v.value(request.getRecordStatus().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        if (Objects.nonNull(request.getStoreType())) {
            if(request.getStoreType() == StoreType.SUPPLIER){
//                boolQb.mustNot(termQuery("goodsInfo.storeType", StoreType.CROSS_BORDER.toValue()));
                boolQb.mustNot(term(g -> g.field("goodsInfo.storeType").value(StoreType.CROSS_BORDER.toValue())));
            } else {
//                boolQb.must(termQuery("goodsInfo.storeType", request.getStoreType().toValue()));
                boolQb.must(term(g -> g.field("goodsInfo.storeType").value(request.getStoreType().toValue())));
            }
        }
        //批量卡券id
        if (CollectionUtils.isNotEmpty(request.getElectronicCouponsIds())) {
//            boolQb.must(termsQuery("goodsInfo.electronicCouponsId", request.getElectronicCouponsIds()));
            boolQb.must(terms(g -> g.field("goodsInfo.electronicCouponsId")
                    .terms(v -> v.value(request.getElectronicCouponsIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //卡券名称
        if (StringUtils.isNotBlank(request.getElectronicCouponsName())) {
//            boolQb.must(wildcardQuery("goodsInfo.electronicCouponsName", ElasticCommonUtil.replaceEsLikeWildcard(request.getElectronicCouponsName().trim())));
            boolQb.must(wildcard(g -> g.field("goodsInfo.electronicCouponsName").wildcard(ElasticCommonUtil.replaceEsLikeWildcard(request.getElectronicCouponsName().trim()))));
        }
        //商品类型
        if (Objects.nonNull(request.getGoodsType())) {
//            boolQb.must(termQuery("goodsInfo.goodsType", request.getGoodsType()));
            boolQb.must(term(g -> g.field("goodsInfo.goodsType").value(request.getGoodsType())));
        }
        //排除的商品类型
        if (CollectionUtils.isNotEmpty(request.getExcludeGoodsTypes())) {
//            boolQb.mustNot(termsQuery("goodsInfo.goodsType", request.getExcludeGoodsTypes()));
            boolQb.mustNot(terms(g -> g.field("goodsInfo.goodsType")
                    .terms(v -> v.value(request.getExcludeGoodsTypes().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //批量查询goodsNo
        if(CollectionUtils.isNotEmpty(request.getGoodsNos())){
//            boolQb.must(termsQuery("goodsNo", request.getGoodsNos()));
            boolQb.must(terms(g -> g.field("goodsNo")
                    .terms(v -> v.value(request.getGoodsNos().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        return boolQb.build();
    }

    protected static List<SortOptions> getSorts(EsSkuPageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        if(MapUtils.isNotEmpty(request.getSortMap())) {
            request.getSortMap().forEach((k, v) -> {
                SortOrder order;
                if (SortOrder.Asc.name().equalsIgnoreCase(v)) {
                    order = SortOrder.Asc;
                }else {
                    order = SortOrder.Desc;
                }
                sortBuilders.add(SortOptions.of(g -> g.field(a -> a.field(k).order(order))));
            });
        }else if(StringUtils.isNotBlank(request.getSortColumn())){
            SortOrder order;
            if(SortOrder.Asc.name().equalsIgnoreCase(request.getSortRole())){
                order = SortOrder.Asc;
            } else {
                order = SortOrder.Desc;
            }
            sortBuilders.add(SortOptions.of(g -> g.field(a -> a.field(request.getSortColumn()).order(order))));
        }
        return sortBuilders;
    }

    public static Query getSearchCriteria(EsSkuPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        //设置可以查询1w+数据
//        builder.withTrackTotalHits(Boolean.TRUE);
//        builder.withQuery(getWhereCriteria(request));
//
//        if(CollectionUtils.isNotEmpty(request.getFilterCols())){
//            builder.withSourceFilter(new FetchSourceFilter(request.getFilterCols().toArray(new String[]{}),null));
//        }
//
//        System.out.println("where===>" + getWhereCriteria(request).toString());
//        builder.withPageable(request.getPageable());
//        List<SortBuilder> sortBuilders = getSorts(request);
//        if (CollectionUtils.isNotEmpty(sortBuilders)) {
//            sortBuilders.forEach(builder::withSort);
//        }
//        return builder.build();
        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(g -> g.bool(getWhereCriteria(request)))
                .withPageable(request.getPageable());
        if (CollectionUtils.isNotEmpty(request.getFilterCols())){
            nativeQueryBuilder = nativeQueryBuilder.withSourceFilter(new FetchSourceFilter(null,
                    request.getFilterCols().toArray(new String[0])));
        }
        List<SortOptions> sortBuilders = getSorts(request);
        if (CollectionUtils.isNotEmpty(sortBuilders)){
            nativeQueryBuilder.withSort(sortBuilders);
        }
        return nativeQueryBuilder.build();
    }
}
