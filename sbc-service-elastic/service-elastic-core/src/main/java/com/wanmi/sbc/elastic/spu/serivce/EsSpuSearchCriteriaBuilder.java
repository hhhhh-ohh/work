package com.wanmi.sbc.elastic.spu.serivce;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortMode;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSelectStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
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
public class EsSpuSearchCriteriaBuilder {

    /**
     * 封装公共条件
     *
     * @return
     */
    private static BoolQuery getWhereCriteria(EsSpuPageRequest request) {
//        BoolQueryBuilder boolQb = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQb = QueryBuilders.bool();
        //批量商品编号
        if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
//            boolQb.must(idsQuery().addIds(request.getGoodsIds().toArray(new String[]{})));
            boolQb.must(ids(g -> g.values(request.getGoodsIds())));
        }
        //批量店铺分类关联商品编号
        if (CollectionUtils.isNotEmpty(request.getStoreCateGoodsIds())) {
//            boolQb.must(idsQuery().addIds(request.getStoreCateGoodsIds().toArray(new String[]{})));
            boolQb.must(ids(g -> g.values(request.getStoreCateGoodsIds())));
        }
        //查询SPU编码
        if (StringUtils.isNotBlank(request.getGoodsNo())) {
//            boolQb.must(termQuery("goodsNo", request.getGoodsNo()));
            boolQb.must(term(a -> a.field("goodsNo").value(request.getGoodsNo())));
        }
        //批量查询SPU编码
        if (CollectionUtils.isNotEmpty(request.getGoodsNos())) {
//            boolQb.must(termsQuery("goodsNo", request.getGoodsNos()));
            boolQb.must(terms(a -> a.field("goodsNo").terms(v -> v.value(request.getGoodsNos().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //查询品牌编号
        if (request.getBrandId() != null && request.getBrandId() > 0) {
//            boolQb.must(termQuery("goodsBrand.brandId", request.getBrandId()));
            boolQb.must(term(a -> a.field("goodsBrand.brandId").value(request.getBrandId())));
        }
        //查询分类编号
        if (request.getCateId() != null && request.getCateId() > 0) {
//            boolQb.must(termQuery("goodsCate.cateId", request.getCateId()));
            boolQb.must(term(a -> a.field("goodsCate.cateId").value(request.getCateId())));
        }
        //批量查询分类编号
        if (CollectionUtils.isNotEmpty(request.getCateIds())) {
//            boolQb.must(termsQuery("goodsCate.cateId", request.getCateIds()));
            boolQb.must(terms(a -> a.field("goodsCate.cateId").terms(v -> v.value(request.getCateIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //批量查询分类编号
        if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
//            boolQb.must(termsQuery("goodsBrand.brandId", request.getBrandIds()));
            boolQb.must(terms(a -> a.field("goodsBrand.brandId").terms(v -> v.value(request.getBrandIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //公司信息ID
        if (request.getCompanyInfoId() != null) {
//            boolQb.must(termQuery("companyInfoId", request.getCompanyInfoId()));
            boolQb.must(term(a -> a.field("companyInfoId").value(request.getCompanyInfoId())));
        }
        //店铺ID
        if (Objects.nonNull(request.getStoreId())) {
//            boolQb.must(termQuery("storeId", request.getStoreId()));
            boolQb.must(term(a -> a.field("storeId").value(request.getStoreId())));
        }
        //模糊查询SPU编码
        if (StringUtils.isNotEmpty(request.getLikeGoodsNo())) {
//            boolQb.must(wildcardQuery("goodsNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim())));
            boolQb.must(wildcard(a -> a.field("goodsNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim()))));
        }
        //精准查询SKUId
        if (StringUtils.isNotEmpty(request.getGoodsInfoId())) {
//            boolQb.must(wildcardQuery("goodsInfos.goodsInfoId", request.getGoodsInfoId().trim()));
            boolQb.must(wildcard(a -> a.field("goodsInfos.goodsInfoId").value(request.getGoodsInfoId().trim())));
        }
        //模糊查询SKU编码
        if (StringUtils.isNotEmpty(request.getLikeGoodsInfoNo())) {
//            boolQb.must(wildcardQuery("goodsInfos.goodsInfoNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim())));
            boolQb.must(wildcard(a -> a.field("goodsInfos.goodsInfoNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim()))));
        }
        //模糊查询名称
        if (StringUtils.isNotEmpty(request.getLikeGoodsName())) {
//            boolQb.must(wildcardQuery("goodsName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsName().trim())));
            boolQb.must(wildcard(a -> a.field("goodsName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsName().trim()))));
        }
        //模糊查询商家名称
        if (StringUtils.isNotBlank(request.getLikeSupplierName())) {
//            boolQb.must(wildcardQuery("supplierName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeSupplierName().trim())));
            boolQb.must(wildcard(a -> a.field("supplierName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeSupplierName().trim()))));
        }
        //模糊查询供应商名称
        if (StringUtils.isNotBlank(request.getLikeProviderName())) {
//            boolQb.must(wildcardQuery("providerName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeProviderName().trim())));
            boolQb.must(wildcard(a -> a.field("providerName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeProviderName().trim()))));
        }

        //关键词搜索
        if (StringUtils.isNotBlank(request.getKeyword())) {
            String str = ElasticCommonUtil.replaceEsLikeWildcard(request.getKeyword().trim());
            /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
            bq.should(wildcardQuery("goodsName", str));
            bq.should(wildcardQuery("goodsNo", str));
            boolQb.must(bq);*/
            BoolQuery.Builder bq = QueryBuilders.bool();
            bq.should(wildcard(a -> a.field("goodsName").value(str)));
            bq.should(wildcard(a -> a.field("goodsNo").value(str)));
            boolQb.must(a -> a.bool(bq.build()));
        }

        //审核状态
        if (request.getAuditStatus() != null) {
//            boolQb.must(termQuery("auditStatus", request.getAuditStatus().toValue()));
            boolQb.must(term(a -> a.field("auditStatus").value(request.getAuditStatus().toValue())));
        }

        //上下架状态
        if (request.getAddedFlag() != null) {
//            boolQb.must(termQuery("addedFlag", request.getAddedFlag()));
            boolQb.must(term(a -> a.field("addedFlag").value(request.getAddedFlag())));
        }

        //可售状态
        if (request.getVendibility() != null && request.getVendibility().equals(NumberUtils.INTEGER_ONE)) {
            /*boolQb.must(termQuery("vendibilityStatus", request.getVendibility()));
            boolQb.must(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));*/
            boolQb.must(term(a -> a.field("vendibilityStatus").value(request.getVendibility())));
            boolQb.must(term(a -> a.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
        }


        // 代销商品是否可售，B端筛选条件专用
        // B端和C端对于SPU维度可售性定义互斥，因为新增独立的字段
        if (Objects.nonNull(request.getVendibilityFilter4bff())) {
            // 针对代销商品
//            boolQb.must(existsQuery("providerId"));
            boolQb.must(exists(a -> a.field("providerId")));
            if (Constants.no.equals(request.getVendibilityFilter4bff())) {
                // 1. (存在任意sku不可售 || 供应商不正常)，spu维度即为不可售
                /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
                bq.should(termQuery("goodsInfos.vendibilityStatus", Constants.no));
                bq.should(QueryBuilders.boolQuery().mustNot(termsQuery("providerId", request.getProviderIds())));
                boolQb.must(bq);*/
                BoolQuery.Builder bq = QueryBuilders.bool();
                bq.should(term(a -> a.field("goodsInfos.vendibilityStatus").value(Constants.no)));
                bq.should(g -> g.bool(QueryBuilders.bool().mustNot(terms(a -> a.field("providerId").terms(v -> v.value(request.getProviderIds().stream().map(FieldValue::of).collect(Collectors.toList()))))).build()));
                boolQb.must(a -> a.bool(bq.build()));
            } else if (Constants.yes.equals(request.getVendibilityFilter4bff())) {
                // 2. (仅当全部sku可售时 && 供应商正常)，spu维度才可售
                // 这里用脚本遍历所有sku可售状态，任意sku不可售，返回false，否则true（即能被查出来）
                String allSkuCanVendibilityScript =
                        "if (doc['goodsInfos.vendibilityStatus'] == null || doc['goodsInfos.vendibilityStatus'].size() == 0) "
                                + "{ return false; } "
                                + "for (status in doc['goodsInfos.vendibilityStatus']) "
                                + "{ if (status != 1) { return false; } } return true; ";
                /*boolQb.must(QueryBuilders.scriptQuery(new Script(allSkuCanVendibilityScript)));
                boolQb.must(termsQuery("providerId", request.getProviderIds()));*/
                boolQb.must(script(a -> a.script(s -> s.inline(b -> b.source(allSkuCanVendibilityScript)))));
                boolQb.must(terms(a -> a.field("providerId").terms(v -> v.value(request.getProviderIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
            }
        }

        //多个上下架状态
        if (CollectionUtils.isNotEmpty(request.getAddedFlags())) {
//            boolQb.must(termsQuery("addedFlag", request.getAddedFlags()));
            boolQb.must(terms(a -> a.field("addedFlag").terms(v -> v.value(request.getAddedFlags().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //批量审核状态
        if (CollectionUtils.isNotEmpty(request.getAuditStatusList())) {
//            boolQb.must(termsQuery("auditStatus", request.getAuditStatusList().stream().map(CheckStatus::toValue).collect(Collectors.toList())));
            boolQb.must(terms(a -> a.field("auditStatus").terms(v -> v.value(request.getAuditStatusList().stream().map(CheckStatus::toValue).map(FieldValue::of).collect(Collectors.toList())))));
        }

        //查询标签关联
        if (Objects.nonNull(request.getLabelId())) {
            /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
            bq.must(termQuery("goodsLabelList.goodsLabelId", request.getLabelId()));
            boolQb.must(nestedQuery("goodsLabelList", bq, ScoreMode.None));*/
            boolQb.must(nested(a -> a.path("goodsLabelList")
                    .query(term(b -> b.field("goodsLabelList.goodsLabelId").value(request.getLabelId())))
                    .scoreMode(ChildScoreMode.None)));
        }

        //非商品编号
        if (StringUtils.isNotBlank(request.getNotGoodsId())) {
//            boolQb.mustNot(idsQuery().addIds(request.getNotGoodsId()));
            boolQb.mustNot(ids(a -> a.values(request.getNotGoodsId())));
        }

        //销售类型
        if (Objects.nonNull(request.getSaleType())) {
//            boolQb.must(termQuery("goodsInfos.saleType", request.getSaleType()));
            boolQb.must(term(a -> a.field("goodsInfos.saleType").value(request.getSaleType())));
        }

        //商品类型
        if (Objects.nonNull(request.getGoodsType())) {
//            boolQb.must(termQuery("goodsType", request.getGoodsType()));
            boolQb.must(term(a -> a.field("goodsType").value(request.getGoodsType())));
        }

        //商品来源
        if (Objects.nonNull(request.getGoodsSource())) {
//            boolQb.must(termQuery("goodsSource", request.getGoodsSource()));
            boolQb.must(term(a -> a.field("goodsSource").value(request.getGoodsSource())));
        }

        //商品来源，供应商商品/商家商品
        if (Objects.nonNull(request.getSearchGoodsSource())) {
            if (request.getSearchGoodsSource() == GoodsSource.PROVIDER){
//                boolQb.must(existsQuery("providerGoodsId"));
                boolQb.must(exists(a -> a.field("providerGoodsId")));
            }else if(request.getSearchGoodsSource() == GoodsSource.SELLER){
//                boolQb.mustNot(existsQuery("providerGoodsId"));
                boolQb.mustNot(exists(a -> a.field("providerGoodsId")));
            }
        }

        //店铺类型
        if (Objects.nonNull(request.getCompanyType())) {
//            boolQb.must(termQuery("goodsInfos.companyType", request.getCompanyType().toValue()));
            boolQb.must(term(a -> a.field("goodsInfos.companyType").value(request.getCompanyType().toValue())));
        }

        //查询库存
        if(Objects.nonNull(request.getMinStock()) && Objects.isNull(request.getMaxStock())){
//            boolQb.must(rangeQuery("stock").gte(request.getMinStock()));
            boolQb.must(range(g -> g.field("stock").gte(JsonData.of(request.getMinStock()))));
        }
        if(Objects.nonNull(request.getMaxStock()) && Objects.isNull(request.getMinStock())){
//            boolQb.must(rangeQuery("stock").lte(request.getMaxStock()));
            boolQb.must(range(g -> g.field("stock").lte(JsonData.of(request.getMaxStock()))));
        }
        if(Objects.nonNull(request.getMaxStock()) && Objects.nonNull(request.getMinStock())){
//            boolQb.must(rangeQuery("stock").gte(request.getMinStock()).lte(request.getMaxStock()));
            boolQb.must(range(g -> g.field("stock")
                    .gte(JsonData.of(request.getMinStock())).lte(JsonData.of(request.getMaxStock()))));
        }
        //查询销量
        if(Objects.nonNull(request.getMinGoodsSalesNum()) && Objects.isNull(request.getMaxGoodsSalesNum())){
//            boolQb.must(rangeQuery("realGoodsSalesNum").gte(request.getMinGoodsSalesNum()));
            boolQb.must(range(g -> g.field("realGoodsSalesNum").gte(JsonData.of(request.getMinGoodsSalesNum()))));
        }
        if(Objects.nonNull(request.getMaxGoodsSalesNum()) && Objects.isNull(request.getMinGoodsSalesNum())){
//            boolQb.must(rangeQuery("realGoodsSalesNum").lte(request.getMaxGoodsSalesNum()));
            boolQb.must(range(g -> g.field("realGoodsSalesNum").lte(JsonData.of(request.getMaxGoodsSalesNum()))));
        }
        if(Objects.nonNull(request.getMaxGoodsSalesNum()) && Objects.nonNull(request.getMinGoodsSalesNum())){
//            boolQb.must(rangeQuery("realGoodsSalesNum").gte(request.getMinGoodsSalesNum()).lte(request.getMaxGoodsSalesNum()));
            boolQb.must(range(g -> g.field("realGoodsSalesNum")
                    .gte(JsonData.of(request.getMinGoodsSalesNum())).lte(JsonData.of(request.getMaxGoodsSalesNum()))));
        }
        //查询市场价
        if(Objects.nonNull(request.getMinMarketPrice()) && Objects.isNull(request.getMaxMarketPrice())){
            /*RangeQueryBuilder marketPrice = QueryBuilders.rangeQuery("goodsInfos.marketPrice")
                    .gte(request.getMinMarketPrice().doubleValue());
            boolQb.must(marketPrice);*/
            boolQb.must(range(g -> g.field("goodsInfos.marketPrice").gte(JsonData.of(request.getMinMarketPrice().doubleValue()))));
        }
        if(Objects.nonNull(request.getMaxMarketPrice()) && Objects.isNull(request.getMinMarketPrice())){
            /*RangeQueryBuilder marketPrice = QueryBuilders.rangeQuery("goodsInfos.marketPrice")
                    .lte(request.getMaxMarketPrice().doubleValue());
            boolQb.must(marketPrice);*/
            boolQb.must(range(g -> g.field("goodsInfos.marketPrice").lte(JsonData.of(request.getMaxMarketPrice().doubleValue()))));
        }
        if(Objects.nonNull(request.getMaxMarketPrice()) && Objects.nonNull(request.getMinMarketPrice())){
            /*RangeQueryBuilder marketPrice = QueryBuilders.rangeQuery("goodsInfos.marketPrice")
                    .gte(request.getMinMarketPrice().doubleValue())
                    .lte(request.getMaxMarketPrice().doubleValue());
            boolQb.must(marketPrice);*/
            boolQb.must(range(g -> g.field("goodsInfos.marketPrice")
                    .gte(JsonData.of(request.getMinMarketPrice().doubleValue()))
                    .lte(JsonData.of(request.getMaxMarketPrice().doubleValue()))));
        }

        if (Objects.nonNull(request.getFreightTempId())) {
//            boolQb.must(termQuery("freightTempId", request.getFreightTempId()));
            boolQb.must(term(a -> a.field("freightTempId").value(request.getFreightTempId())));
        }

      //商品状态筛选
        if (CollectionUtils.isNotEmpty(request.getGoodsSelectStatuses())) {
//            BoolQueryBuilder orBq = QueryBuilders.boolQuery();
            BoolQuery.Builder orBq = QueryBuilders.bool();
            request.getGoodsSelectStatuses().forEach(goodsInfoSelectStatus -> {
                if (goodsInfoSelectStatus != null) {
                    if (goodsInfoSelectStatus == GoodsSelectStatus.ADDED) {
                        /*BoolQueryBuilder bq = QueryBuilders.boolQuery();
                        bq.should(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));
                        bq.should(termQuery("addedFlag", AddedFlag.YES.toValue()));
                        orBq.should(bq);*/
                        BoolQuery.Builder bq = QueryBuilders.bool();
                        bq.should(term(a -> a.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
                        bq.should(term(a -> a.field("addedFlag").value(AddedFlag.YES.toValue())));
                        orBq.should(a -> a.bool(bq.build()));
                    } else if (goodsInfoSelectStatus == GoodsSelectStatus.NOT_ADDED) {
//                        BoolQueryBuilder bq = QueryBuilders.boolQuery();
//                        bq.should(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));
//                        bq.should(termQuery("addedFlag", AddedFlag.NO.toValue()));
//                        orBq.should(bq);
                        BoolQuery.Builder bq = QueryBuilders.bool();
                        bq.should(term(a -> a.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
                        bq.should(term(a -> a.field("addedFlag").value(AddedFlag.NO.toValue())));
                        orBq.should(a -> a.bool(bq.build()));
                    } else if (goodsInfoSelectStatus == GoodsSelectStatus.PART_ADDED) {
//                        BoolQueryBuilder bq = QueryBuilders.boolQuery();
//                        bq.should(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));
//                        bq.should(termQuery("addedFlag", AddedFlag.PART.toValue()));
//                        orBq.should(bq);
                        BoolQuery.Builder bq = QueryBuilders.bool();
                        bq.should(term(a -> a.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
                        bq.should(term(a -> a.field("addedFlag").value(AddedFlag.PART.toValue())));
                        orBq.should(a -> a.bool(bq.build()));
                    } else if (goodsInfoSelectStatus == GoodsSelectStatus.OTHER) {
                        List<Integer> status = Arrays.asList(CheckStatus.FORBADE.toValue(), CheckStatus.NOT_PASS.toValue(), CheckStatus.WAIT_CHECK.toValue());
//                        BoolQueryBuilder bq = QueryBuilders.boolQuery();
//                        bq.should(termsQuery("auditStatus", status));
//                        orBq.should(bq);
                        BoolQuery.Builder bq = QueryBuilders.bool();
                        bq.should(terms(a -> a.field("auditStatus").terms(v -> v.value(status.stream().map(FieldValue::of).collect(Collectors.toList())))));
                        orBq.should(a -> a.bool(bq.build()));
                    }
                }
            });
//            boolQb.must(orBq);
            boolQb.must(a -> a.bool(orBq.build()));
        }

        //跨境商品
        if(Objects.nonNull(request.getPluginType()) && PluginType.CROSS_BORDER.toValue() == request.getPluginType().intValue()) {
//            boolQb.must(termQuery("pluginType", request.getPluginType()));
            boolQb.must(term(a -> a.field("pluginType").value(request.getPluginType())));
        }
        if (StringUtils.isNotEmpty(request.getTradeType())) {
//            boolQb.must(termQuery("extendedAttributes.tradeType", request.getTradeType()));
            boolQb.must(term(a -> a.field("extendedAttributes.tradeType").value(request.getTradeType())));
        }
        if (StringUtils.isNotEmpty(request.getElectronPort())) {
//            boolQb.must(termQuery("extendedAttributes.electronPort", request.getElectronPort()));
            boolQb.must(term(a -> a.field("extendedAttributes.electronPort").value(request.getElectronPort())));
        }
        if (StringUtils.isNotEmpty(request.getRecordNo())) {
//            boolQb.must(termQuery("goodsInfos.extendedAttributes.recordNo", request.getRecordNo()));
            boolQb.must(term(a -> a.field("goodsInfos.extendedAttributes.recordNo").value(request.getRecordNo())));
        }
        if (Objects.nonNull(request.getNotShowCrossGoodsFlag()) && request.getNotShowCrossGoodsFlag()) {
//            boolQb.must(termQuery("goodsInfos.pluginType", PluginType.NORMAL.toValue()));
            boolQb.must(term(a -> a.field("goodsInfos.pluginType").value(PluginType.NORMAL.toValue())));
            // 从商品资料库发布跨境商品，默认排除供应商商品和由供应商导入的商品
//            boolQb.mustNot(QueryBuilders.existsQuery("providerId"));
            boolQb.mustNot(exists(a -> a.field("providerId")));
        }
        if (CollectionUtils.isNotEmpty(request.getRecordStatus())){
//            boolQb.must(termsQuery("goodsInfos.extendedAttributes.recordStatus", request.getRecordStatus()));
            boolQb.must(terms(a -> a.field("goodsInfos.extendedAttributes.recordStatus")
                    .terms(v -> v.value(request.getRecordStatus().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        return boolQb.build();
    }

    private static List<SortOptions> getSorts(EsSpuPageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        SortOrder order;
        if (SortOrder.Asc.name().equalsIgnoreCase(request.getSortRole())) {
            order = SortOrder.Asc;
        } else {
            order = SortOrder.Desc;
        }

        if (Objects.nonNull(request.getGoodsSortType())) {
            switch (request.getGoodsSortType()) {
                case MARKET_PRICE:
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("goodsInfos.marketPrice").order(order).mode(SortMode.Min))));
                    break;
                case STOCK:
//                    sortBuilders.add(new FieldSortBuilder("stock").order(order));
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("stock").order(order))));
                    break;
                case SALES_NUM:
//                    sortBuilders.add(new FieldSortBuilder("realGoodsSalesNum").order(order));
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("realGoodsSalesNum").order(order))));
                    break;
                case WATER_FLOODING:
//                    sortBuilders.add(new FieldSortBuilder("goodsSalesNum").order(order));
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("goodsSalesNum").order(order))));
                    break;
                case SORT_NO:
//                    sortBuilders.add(new FieldSortBuilder("sortNo").order(order));
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("sortNo").order(order))));
                    break;
                case CROSS_TIME:
//                    sortBuilders.add(new FieldSortBuilder("extendedAttributes.createTime").order(order));
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("extendedAttributes.createTime").order(order))));
                    break;
                default:
//                    sortBuilders.add(new FieldSortBuilder("createTime").order(order));
                    sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("createTime").order(order))));
                    break;//按创建时间倒序、ID升序
            }
        } else {
            /*sortBuilders.add(new FieldSortBuilder("createTime").order(order));
            sortBuilders.add(new FieldSortBuilder("goodsNo").order(order));*/
            sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("createTime").order(order))));
            sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field("goodsNo").order(order))));
        }
        return sortBuilders;
    }



    public static Query getSearchCriteria(EsSpuPageRequest request) {
        NativeQueryBuilder builder = NativeQuery.builder();
        builder.withQuery(a -> a.bool(getWhereCriteria(request)));
        //设置可以查询1w+数据
        builder.withTrackTotalHits(Boolean.TRUE);
//        System.out.println("where===>" + getWhereCriteria(request).toString());
        builder.withPageable(request.getPageable());
        List<SortOptions> sortBuilders = getSorts(request);
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            builder.withSort(sortBuilders);
        }
        return builder.build();
    }
}
