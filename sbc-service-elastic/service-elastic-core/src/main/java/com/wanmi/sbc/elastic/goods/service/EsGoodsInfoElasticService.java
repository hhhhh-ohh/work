package com.wanmi.sbc.elastic.goods.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.plugin.annotation.Routing;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.elastic.api.request.goods.*;
import com.wanmi.sbc.elastic.api.response.goods.*;
import com.wanmi.sbc.elastic.base.response.EsSearchResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsLabelNestVO;
import com.wanmi.sbc.elastic.goods.model.root.EsGoods;
import com.wanmi.sbc.elastic.goods.model.root.EsGoodsInfo;
import com.wanmi.sbc.elastic.goods.model.root.GoodsLevelPriceNest;
import com.wanmi.sbc.elastic.goods.request.EsGoodsInfoCriteriaBuilder;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsLevelPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsSpecQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsListRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceListBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsLevelPriceBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreCateIdAndIsHaveSelfRequest;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateByIdResponse;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateListByConditionResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsListByIdsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreCateIdAndIsHaveSelfResponse;
import com.wanmi.sbc.goods.bean.dto.BatchEnterPrisePriceDTO;
import com.wanmi.sbc.goods.bean.dto.DistributionGoodsInfoModifyDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.term;
import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.terms;

/**
 * ES商品信息数据源操作
 * Created by daiyitian on 2017/4/21.
 */
@Slf4j
@Service
@Primary
public class EsGoodsInfoElasticService implements EsGoodsInfoElasticServiceInterface {

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsIntervalPriceQueryProvider goodsIntervalPriceQueryProvider;

    @Autowired
    private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Autowired
    private GoodsSpecQueryProvider goodsSpecQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EsGoodsLabelService esGoodsLabelService;

    @Autowired
    private GoodsLevelPriceQueryProvider goodsLevelPriceQueryProvider;

    @Autowired
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired
    private RedisUtil redisService;
    @Autowired
    protected AuditQueryProvider auditQueryProvider;

    @Value("${wm.elasticsearch.analyzer:''}")
    private String analyzer;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    protected EsBaseService esBaseService;

    @Autowired
    private EsGoodsElasticServiceInterface esGoodsElasticService;

    /**
     * ES价格排序脚本
     */
    private final String PRICE_ORDER_SCRIPT =
            "def priceType=doc['goodsInfo.priceType'].value;" +
                    "def levelDiscountFlag=doc['goodsInfo.levelDiscountFlag'].value;" +
                    "def marketPrice=doc['goodsInfo.marketPrice'].value;" +
                    "if(priceType&&priceType==1){" +
                    "if(levelDiscountFlag && levelDiscountFlag == 1 && levelDiscount){" +
                    "return doc['goodsInfo.intervalMinPrice'].value * levelDiscount;" +
                    "};" +
                    "return doc['goodsInfo.intervalMinPrice'].value;" +
                    "};" +
                    "def customerPrices=_source.customerPrices;" +
                    "if(customerPrices && customerPrices.size()>0){" +
                    "for (cp in customerPrices){" +
                    "if(cp.customerId==customerId){return cp.price;};" +
                    "};" +
                    "};" +
                    "def goodsLevelPrices=_source.goodsLevelPrices;" +
                    "if(goodsLevelPrices && goodsLevelPrices.size()>0){" +
                    "for (lp in goodsLevelPrices){" +
                    "if(lp.levelId==levelId){return lp.price;};" +
                    "};" +
                    "};" +
                    "if(levelDiscount && levelDiscount > 0){" +
                    "return marketPrice * levelDiscount;" +
                    "};" +
                    "return marketPrice";

    /**
     * 分页查询ES商品(实现WEB的商品列表)
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoResponse page(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoResponse goodsInfoResponse = EsGoodsInfoResponse.builder().build();

        //判断是否是三级分类页面
        Long cateId = queryRequest.getCateId();

        EsSearchResponse response = getEsBaseInfoByParams(queryRequest);
        if (response.getData().size() < 1) {
            goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsInfoResponse;
        }

        // 过滤不展示的商品标签
        response.getData().forEach(goods -> {
            List<GoodsLabelNestVO> labels = goods.getGoodsLabelList();
            if (CollectionUtils.isNotEmpty(labels)) {
                labels = labels.stream()
                        .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                        .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                        .collect(Collectors.toList());
                goods.setGoodsLabelList(labels);
            }
        });

        List<String> skuIds =
                response.getData().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsInfoId).distinct().collect(Collectors.toList());

        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoQueryProvider.listByIds(
                GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIds).build()
        ).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));


        List<EsGoodsInfoVO> esGoodsInfoList = new LinkedList<>();
        Set<String> existsGoods = new HashSet<>();
        for (EsGoodsInfoVO esGoodsInfo : response.getData()) {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            //排除分销商品，错误数据
            if (Objects.nonNull(goodsInfo.getDistributionGoodsAudit()) &&
                    goodsInfo.getDistributionGoodsAudit() == DistributionGoodsAudit.CHECKED &&
                    Objects.isNull(goodsInfo.getDistributionCommission())) {
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                continue;
            }
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());

            goodsInfo.setStock(goodsInfoMap.getOrDefault(goodsInfo.getGoodsInfoId(), new GoodsInfoVO()).getStock());


            if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
            }

            esGoodsInfoList.add(esGoodsInfo);

            if (!existsGoods.contains(esGoodsInfo.getGoodsId())) {
                existsGoods.add(esGoodsInfo.getGoodsId());
            }
        }

        // 填充sku起售数量
        List<String> goodsInfoIds = esGoodsInfoList.stream().map(EsGoodsInfoVO::getId).collect(Collectors.toList());
        Map<String, Long> skuStartSaleNumMap = this.getSkuStartSaleNumMap(goodsInfoIds, queryRequest.getTerminalSource());
        for (EsGoodsInfoVO esGoodsInfoVO : esGoodsInfoList) {
            if (Objects.nonNull(esGoodsInfoVO.getGoodsInfo())) {
                esGoodsInfoVO.getGoodsInfo().setStartSaleNum(skuStartSaleNumMap.get(esGoodsInfoVO.getId()));
            }
        }

        response.setData(esGoodsInfoList);

        //提取聚合数据
        EsGoodsBaseResponse baseResponse = extractBrands(response);
        goodsInfoResponse.setBrands(baseResponse.getBrands());
        goodsInfoResponse.setBrandMap(baseResponse.getBrandMap());

        if (queryRequest.isCateAggFlag()) {
            //提取聚合数据
            baseResponse = extractGoodsCate(response);
            goodsInfoResponse.setCateList(baseResponse.getCateList());
        }

        //提取规格与规格值聚合数据
        EsGoodsBaseResponse specResponse = extractGoodsSpecsAndSpecDetails(response);

        //提取属性
        EsGoodsBaseResponse propResponse = extractGoodsProp(response, cateId);

        //提取商品标签数据
        goodsInfoResponse.setGoodsLabelVOList(esGoodsLabelService.extractGoodsLabel(response));

        goodsInfoResponse.setGoodsSpecs(baseResponse.getGoodsSpecs());
        goodsInfoResponse.setGoodsSpecDetails(specResponse.getGoodsSpecDetails());
        goodsInfoResponse.setGoodsPropertyVOS(propResponse.getGoodsPropertyVOS());
        goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));

        return goodsInfoResponse;
    }

    @Override
    public EsGoodsInfoResponse optimizationPage(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoResponse goodsInfoResponse = EsGoodsInfoResponse.builder().build();


        EsSearchResponse response = getEsBaseInfoByParams(queryRequest);
        if (response.getData().size() < 1) {
            goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsInfoResponse;
        }

        // 过滤不展示的商品标签
        response.getData().forEach(goods -> {
            List<GoodsLabelNestVO> labels = goods.getGoodsLabelList();
            if (CollectionUtils.isNotEmpty(labels)) {
                labels = labels.stream()
                        .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                        .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                        .collect(Collectors.toList());
                goods.setGoodsLabelList(labels);
            }
        });

        List<String> skuIds =
                response.getData().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsInfoId).distinct().collect(Collectors.toList());

        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoQueryProvider.listByIds(
                GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIds).build()
        ).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));


        List<EsGoodsInfoVO> esGoodsInfoList = new LinkedList<>();
        Set<String> existsGoods = new HashSet<>();
        for (EsGoodsInfoVO esGoodsInfo : response.getData()) {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            //排除分销商品，错误数据
            if (Objects.nonNull(goodsInfo.getDistributionGoodsAudit()) &&
                    goodsInfo.getDistributionGoodsAudit() == DistributionGoodsAudit.CHECKED &&
                    Objects.isNull(goodsInfo.getDistributionCommission())) {
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                continue;
            }
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());

            goodsInfo.setStock(goodsInfoMap.getOrDefault(goodsInfo.getGoodsInfoId(), new GoodsInfoVO()).getStock());


            if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
            }
            esGoodsInfoList.add(esGoodsInfo);

            if (!existsGoods.contains(esGoodsInfo.getGoodsId())) {
                existsGoods.add(esGoodsInfo.getGoodsId());
            }
        }
        response.setData(esGoodsInfoList);

        goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));

        return goodsInfoResponse;
    }

    /**
     * 我的店铺（店铺精选页）
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoResponse distributorGoodsListByCustomerId(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoResponse goodsInfoResponse = EsGoodsInfoResponse.builder().build();

        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        Long cateId = queryRequest.getCateId();
        // 聚合品牌
//        builder.putAgg(AggregationBuilders.terms("brand_group").field("goodsBrand.brandId"));
        builder.putAgg("brand_group", AggregationBuilders.terms(g -> g.field("goodsBrand.brandId")));
        // 判断BOSS是否配置了无货商品不展示
        if (this.checkIsOutOfStockShow()) {
            queryRequest.setStockFlag(Constants.yes);
            queryRequest.setIsOutOfStockShow(Constants.yes);
        }
        builder.setSort(queryRequest);
        //聚合属性
        //属性id+属性名称+属性排序+类目排序+输入方式+是否索引
        //属性值id+属性值名称
        String propStr = "doc['goodsPropRelNests.propId'] " +
                "+'#&&#'+ doc['goodsPropRelNests.propName'] " +
                "+'#&&#'+ doc['goodsPropRelNests.propSort'] " +
                "+'#&&#'+ doc['goodsPropRelNests.catePropSort'] " +
                "+'#&&#'+ doc['goodsPropRelNests.propType'] " +
                "+'#&&#'+ doc['goodsPropRelNests.indexFlag'] ";
        Script script = Script.of(g -> g.inline(a -> a.source(propStr)));

        String childStr = "doc['goodsPropRelNests.goodsPropDetailNest.detailId'] "
                                + "+'#&&#'+ doc['goodsPropRelNests.goodsPropDetailNest.detailNameValue'] ";
        Script childScript = Script.of(g -> g.inline(a -> a.source(childStr)));


        builder.putAgg("goodsPropRelNests", new Aggregation.Builder().nested(g -> g.path("goodsPropRelNests"))
                        .aggregations("prop_group", a -> a.terms(b -> b.script(script).size(100000))
                                .aggregations("prop_detail_group", c -> c.nested(d -> d.path("goodsPropRelNests.goodsPropDetailNest"))
                                        .aggregations("prop_detail_group", e -> e.terms(f -> f.script(childScript).size(100000)))))
                        .build());
        SearchHits<EsGoodsInfoVO> searchHits = esBaseService.commonSearchHits(builder.getSearchCriteria(queryRequest),
                EsGoodsInfoVO.class, EsConstants.DOC_GOODS_INFO_TYPE);
        Page<EsGoodsInfoVO> pages = esBaseService.commonSearchPage(searchHits, builder.getSearchCriteria(queryRequest).getPageable());

        EsSearchResponse response = EsSearchResponse.build(searchHits, pages);

        if (pages.getContent().size() < 1) {
            goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(pages.getContent(),
                    pages.getPageable(), pages.getTotalElements()));
            return goodsInfoResponse;
        }

        // 过滤不展示的商品标签
        AtomicInteger index = new AtomicInteger(queryRequest.getPageNum() * queryRequest.getPageSize());
        pages.getContent().forEach(goods -> {
            List<GoodsLabelNestVO> labels = goods.getGoodsLabelList();
            if (CollectionUtils.isNotEmpty(labels)) {
                labels = labels.stream()
                        .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                        .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                        .collect(Collectors.toList());
                goods.setGoodsLabelList(labels);
            }
            goods.setSortNo(index.longValue());
            index.getAndIncrement();
        });

        List<String> goodsInfoIds = queryRequest.getGoodsInfoIds();

        List<String> goodsIds =
                pages.getContent().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsId).distinct().collect(Collectors.toList());
        List<String> skuIds =
                pages.getContent().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsInfoId).distinct().collect(Collectors.toList());
        //批量查询SPU数据
        GoodsByConditionRequest goodsQueryRequest = new GoodsByConditionRequest();
        goodsQueryRequest.setGoodsIds(goodsIds);
        List<GoodsVO> goodses = goodsQueryProvider.listByCondition(goodsQueryRequest).getContext().getGoodsVOList();

        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoQueryProvider.listByIds(
                GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIds).build()
        ).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));

        //批量查询规格值表
        Map<String, List<GoodsInfoSpecDetailRelVO>> detailRels =
                goodsInfoSpecDetailRelQueryProvider.listBySkuIds(new GoodsInfoSpecDetailRelBySkuIdsRequest(skuIds))
                        .getContext().getGoodsInfoSpecDetailRelVOList().stream()
                        .collect(Collectors.groupingBy(GoodsInfoSpecDetailRelVO::getGoodsInfoId));
        List<EsGoodsInfoVO> data = pages.getContent();
        List<EsGoodsInfoVO> resultList = new LinkedList<>();
        for (String goodsInfoId : goodsInfoIds) {
            for (EsGoodsInfoVO esGoodsInfo : data) {
                GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
                if (Objects.isNull(goodsInfo) || !goodsInfoId.equals(goodsInfo.getGoodsInfoId())) {
                    continue;
                }
                goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                        goodsInfo.getMarketPrice());
                Optional<GoodsVO> goodsOptional =
                        goodses.stream().filter(goods -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).findFirst();
                if (goodsOptional.isPresent()) {
                    GoodsVO goods = goodsOptional.get();
                    //为空，则以商品主图
                    if (StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())) {
                        goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
                    }
                    //填充规格值
                    if (Constants.yes.equals(goods.getMoreSpecFlag()) && MapUtils.isNotEmpty(detailRels) && detailRels.containsKey(goodsInfo.getGoodsInfoId())) {
                        goodsInfo.setSpecText(detailRels.get(goodsInfo.getGoodsInfoId()).stream().map(GoodsInfoSpecDetailRelVO::getDetailName).collect(Collectors.joining(" ")));
                    }
                    goodsInfo.setStock(goodsInfoMap.getOrDefault(goodsInfo.getGoodsInfoId(), new GoodsInfoVO()).getStock());

                    if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                        goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                } else {//不存在，则做为删除标记
                    goodsInfo.setDelFlag(DeleteFlag.YES);
                    goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                }
                resultList.add(esGoodsInfo);
            }
        }

        response.setData(resultList);

        //提取聚合数据
        EsGoodsBaseResponse baseResponse = extractBrands(response);
        goodsInfoResponse.setBrands(baseResponse.getBrands());
        goodsInfoResponse.setBrandMap(baseResponse.getBrandMap());

        //提取属性
        EsGoodsBaseResponse propResponse = extractGoodsProp(response, cateId);
        goodsInfoResponse.setGoodsPropertyVOS(propResponse.getGoodsPropertyVOS());

        goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
        goodsInfoResponse.setGoodsList(goodses);
        return goodsInfoResponse;
    }

    /**
     * 分销员-我的店铺-选品功能
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoResponse distributorGoodsList(EsGoodsInfoQueryRequest queryRequest, List<String> goodsIdList) {
        EsGoodsInfoResponse goodsInfoResponse = EsGoodsInfoResponse.builder().build();

        //判断是否是三级分类页面
        Long cateId = queryRequest.getCateId();

        EsSearchResponse response = getEsBaseInfoByParams(queryRequest);

        if (response.getData().size() < 1) {
            goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsInfoResponse;
        }

        // 过滤不展示的商品标签
        response.getData().forEach(goods -> {
            List<GoodsLabelNestVO> labels = goods.getGoodsLabelList();
            if (CollectionUtils.isNotEmpty(labels)) {
                goods.setGoodsLabelList(labels.stream().filter(label -> Boolean.TRUE.equals(label.getLabelVisible())
                                && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                        .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                        .collect(Collectors.toList()));
            }
        });

        List<String> goodsIds =
                response.getData().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsId).distinct().collect(Collectors.toList());
        List<String> skuIds =
                response.getData().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsInfoId).distinct().collect(Collectors.toList());
        //批量查询SPU数据
        GoodsByConditionRequest goodsQueryRequest = new GoodsByConditionRequest();
        goodsQueryRequest.setGoodsIds(goodsIds);
        List<GoodsVO> goodses = goodsQueryProvider.listByCondition(goodsQueryRequest).getContext().getGoodsVOList();


        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoQueryProvider.listByIds(
                GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIds).build()
        ).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));


        //批量查询规格值表
        Map<String, List<GoodsInfoSpecDetailRelVO>> detailRels =
                goodsInfoSpecDetailRelQueryProvider.listBySkuIds(new GoodsInfoSpecDetailRelBySkuIdsRequest(skuIds))
                        .getContext().getGoodsInfoSpecDetailRelVOList().stream()
                        .collect(Collectors.groupingBy(GoodsInfoSpecDetailRelVO::getGoodsInfoId));
        List<EsGoodsInfoVO> esGoodsInfoList = new LinkedList<>();
        Boolean hideSelectedDistributionGoods = queryRequest.isHideSelectedDistributionGoods();
        for (EsGoodsInfoVO esGoodsInfo : response.getData()) {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            Boolean anyMatch = goodsIdList.stream().anyMatch(id -> id.equals(goodsInfo.getGoodsInfoId()));

            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());
            Optional<GoodsVO> goodsOptional =
                    goodses.stream().filter(goods -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).findFirst();
            if (goodsOptional.isPresent()) {
                GoodsVO goods = goodsOptional.get();
                goodsInfo.setPriceType(goods.getPriceType());
                //为空，则以商品主图
                if (StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())) {
                    goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
                }
                if (anyMatch) {
                    goodsInfo.setJoinDistributior(1);
                } else {
                    goodsInfo.setJoinDistributior(0);
                }
                //填充规格值
                if (Constants.yes.equals(goods.getMoreSpecFlag()) && MapUtils.isNotEmpty(detailRels) && detailRels.containsKey(goodsInfo.getGoodsInfoId())) {
                    goodsInfo.setSpecText(detailRels.get(goodsInfo.getGoodsInfoId()).stream().map(GoodsInfoSpecDetailRelVO::getDetailName).collect(Collectors.joining(" ")));
                }
                goodsInfo.setStock(goodsInfoMap.getOrDefault(goodsInfo.getGoodsInfoId(), new GoodsInfoVO()).getStock());

                if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                    goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                }
            } else {//不存在，则做为删除标记
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            }
            esGoodsInfoList.add(esGoodsInfo);
        }
        // 填充sku起售数量
        List<String> goodsInfoIds = esGoodsInfoList.stream().map(EsGoodsInfoVO::getId).collect(Collectors.toList());
        Map<String, Long> skuStartSaleNumMap = this.getSkuStartSaleNumMap(goodsInfoIds, queryRequest.getTerminalSource());
        for (EsGoodsInfoVO esGoodsInfoVO : esGoodsInfoList) {
            if (Objects.nonNull(esGoodsInfoVO.getGoodsInfo())) {
                esGoodsInfoVO.getGoodsInfo().setStartSaleNum(skuStartSaleNumMap.get(esGoodsInfoVO.getId()));
            }
        }
        response.setData(esGoodsInfoList);
        //提取聚合数据
        EsGoodsBaseResponse baseResponse = extractBrands(response);
        goodsInfoResponse.setBrands(KsBeanUtil.convert(baseResponse.getBrands(), GoodsListBrandVO.class));
        goodsInfoResponse.setBrandMap(baseResponse.getBrandMap());
        if (queryRequest.isCateAggFlag()) {
            //提取聚合数据
            baseResponse = extractGoodsCate(response);
            goodsInfoResponse.setCateList(baseResponse.getCateList());
        }

        //提取规格与规格值聚合数据
        baseResponse = extractGoodsSpecsAndSpecDetails(response);
        goodsInfoResponse.setGoodsSpecs(baseResponse.getGoodsSpecs());
        goodsInfoResponse.setGoodsSpecDetails(baseResponse.getGoodsSpecDetails());

        //提取属性
        EsGoodsBaseResponse propResponse = extractGoodsProp(response, cateId);
        goodsInfoResponse.setGoodsPropertyVOS(propResponse.getGoodsPropertyVOS());

        goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));

        return goodsInfoResponse;
    }

    /**
     * 根据不同条件查询ES商品信息
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsSearchResponse getEsBaseInfoByParams(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        Boolean checkIsOutOfStockShow = Objects.isNull(queryRequest.getIsOutOfStockShow()) ? this.checkIsOutOfStockShow() : Boolean.valueOf(queryRequest.getIsOutOfStockShow().toString());
        queryRequest = setPageRequest(builder, queryRequest, checkIsOutOfStockShow);
        SearchHits<EsGoodsInfoVO> searchHits = esBaseService.commonSearchHits(builder.getSearchCriteria(queryRequest),
                EsGoodsInfoVO.class, EsConstants.DOC_GOODS_INFO_TYPE);
        Page<EsGoodsInfoVO> pages = esBaseService.commonSearchPage(searchHits, builder.getSearchCriteria(queryRequest).getPageable());

        EsSearchResponse response = EsSearchResponse.build(searchHits, pages);

        // 判断BOSS是否配置了无货商品不展示
        if (checkIsOutOfStockShow) {
            // 过滤无货商品
            if (CollectionUtils.isNotEmpty(response.getData())) {
                response.setData(response.getData().stream().filter(d -> d.getGoodsInfo().getStock() > 0L).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(response.getGoodsData())) {
                response.getGoodsData().forEach(gd -> {
                    if (CollectionUtils.isNotEmpty(gd.getGoodsInfos())) {
                        gd.setGoodsInfos(gd.getGoodsInfos().stream().filter(d -> d.getStock() > 0L).collect(Collectors.toList()));
                    }
                });
            }
        }
        return response;
    }

    /**
     * 提取商品品牌聚合数据
     *
     * @param response
     * @return
     */
    @Override
    public EsGoodsBaseResponse extractBrands(EsSearchResponse response) {
        List<? extends EsSearchResponse.AggregationResultItem> brandBucket = MapUtils.isEmpty(response.getAggResultMap())? new ArrayList<>():response.getAggResultMap().get("brand_group");
        EsGoodsBaseResponse baseResponse = new EsGoodsBaseResponse();
        if (CollectionUtils.isNotEmpty(brandBucket)) {
            Map<String, List<GoodsBrandVO>> brandMap = new HashMap<>();
            List<Long> brandIds =
                    brandBucket.stream().map(EsSearchResponse.AggregationResultItem<String>::getKey).map(NumberUtils::toLong).collect(Collectors.toList());
            List<GoodsBrandVO> goodsBrandVOS = goodsBrandQueryProvider.list(
                    GoodsBrandListRequest.builder()
                            .delFlag(DeleteFlag.NO.toValue())
                            .brandIds(brandIds).build()
            ).getContext().getGoodsBrandVOList();

            goodsBrandVOS.forEach(brand -> {
                String py = Pinyin4jUtil.converterToFirstSpell(brand.getBrandName(), ",");
                if (StringUtils.isNotBlank(py)) {
                    brand.setFirst(py.substring(0, 1).toUpperCase());
                }
                if (brand.getRecommendFlag() == null) {
                    brand.setRecommendFlag(DefaultFlag.NO);
                }
                if (brand.getBrandSort() == null) {
                    brand.setBrandSort(NumberUtils.LONG_ZERO);
                }
            });

            List<GoodsBrandVO> sortList = goodsBrandVOS.stream()
                    .sorted(Comparator.comparing(GoodsBrandVO::getCreateTime).reversed())
                    .sorted(Comparator.comparing(GoodsBrandVO::getBrandSort).reversed())
                    .sorted(Comparator.comparing(GoodsBrandVO::getRecommendFlag).reversed())
                    .collect(Collectors.toList());

            //推荐品牌
            List<GoodsBrandVO> recommend = sortList.stream()
                    .filter(brand -> null != brand.getRecommendFlag()
                            && brand.getRecommendFlag().equals(DefaultFlag.YES))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(recommend)) {
                brandMap.put("推荐", recommend);
            }

            Map<String, List<GoodsBrandVO>> charMap = sortList.stream()
                    .filter(brand -> StringUtils.isNotBlank(brand.getFirst()) &&
                            brand.getFirst().matches("[a-zA-Z]"))
                    .collect(Collectors.groupingBy(GoodsBrandVO::getFirst));

            if (MapUtils.isNotEmpty(charMap)) {
                brandMap.putAll(charMap);
            }

            List<GoodsBrandVO> other = sortList.stream()
                    .filter(brand -> StringUtils.isBlank(brand.getFirst()) ||
                            !brand.getFirst().matches("[a-zA-Z]"))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(other)) {
                brandMap.put("其他", other);
            }

            baseResponse.setBrands(KsBeanUtil.convert(sortList, GoodsListBrandVO.class));
            baseResponse.setBrandMap(brandMap);
        }
        return baseResponse;
    }

    /**
     * 提取商品分类聚合数据
     *
     * @param response
     * @return
     */
    @Override
    public EsGoodsBaseResponse extractGoodsCate(EsSearchResponse response) {
        List<? extends EsSearchResponse.AggregationResultItem> cateBucket = MapUtils.isEmpty(response.getAggResultMap())? new ArrayList<>():response.getAggResultMap().get("cate_group");
        EsGoodsBaseResponse baseResponse = new EsGoodsBaseResponse();
        if (CollectionUtils.isNotEmpty(cateBucket)) {
            List<Long> cateIds =
                    cateBucket.stream().map(EsSearchResponse.AggregationResultItem<String>::getKey).map(NumberUtils::toLong).collect(Collectors.toList());
            GoodsCateListByConditionRequest goodsCateQueryRequest = new GoodsCateListByConditionRequest();
            goodsCateQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            goodsCateQueryRequest.setCateIds(cateIds);
            GoodsCateListByConditionResponse goodsCateListByConditionResponse =
                    goodsCateQueryProvider.listByCondition(goodsCateQueryRequest).getContext();
            baseResponse.setCateList(Objects.nonNull(goodsCateListByConditionResponse) ?
                    goodsCateListByConditionResponse.getGoodsCateVOList() : Collections.emptyList());
        }
        return baseResponse;
    }

    /**
     * 提取商品属性聚合数据
     *
     * @param response
     * @return
     */
    @Override
    public EsGoodsBaseResponse extractGoodsProp(EsSearchResponse response, Long cateId) {
        List<? extends EsSearchResponse.AggregationResultItem> propBucket = MapUtils.isEmpty(response.getAggResultMap())? new ArrayList<>() : response.getAggResultMap().get(
                "goodsPropRelNests");

        //属性id+属性名称+属性排序+类目排序+输入方式+是否索引
        EsGoodsBaseResponse baseResponse = new EsGoodsBaseResponse();
        if (CollectionUtils.isNotEmpty(propBucket)) {
            List<GoodsPropertyVO> goodsPropertyVOS = Lists.newArrayList();
            List<GoodsPropertyVO> goodsPropertyS = Lists.newArrayList();
            for (EsSearchResponse.AggregationResultItem prop : propBucket) {
                //商品属性
                String goodsPropKey = prop.getKey().toString();
                //拆分
                String[] goodsProp = goodsPropKey.split("#&&#");
                //属性对象
                GoodsPropertyVO goodsPropertyVO = new GoodsPropertyVO();
                if (StringUtils.isNotBlank(goodsProp[0]) && goodsProp[0].length() > 2) {
                    goodsPropertyVO.setPropId(
                            Long.valueOf(goodsProp[0].substring(1, goodsProp[0].length() - 1)));
                }
                if (StringUtils.isNotBlank(goodsProp[1]) && goodsProp[1].length() > 2) {
                    goodsPropertyVO.setPropName(
                            goodsProp[1].substring(1, goodsProp[1].length() - 1));
                } else {
                    goodsPropertyVO.setPropName("");
                }
                if (StringUtils.isNotBlank(goodsProp[2]) && goodsProp[2].length() > 2) {
                    goodsPropertyVO.setPropSort(
                            Long.valueOf(goodsProp[2].substring(1, goodsProp[2].length() - 1)));
                } else {
                    goodsPropertyVO.setPropSort(NumberUtils.LONG_ZERO);
                }
                if (StringUtils.isNotBlank(goodsProp[3]) && goodsProp[3].length() > 2) {
                    goodsPropertyVO.setSort(
                            Integer.valueOf(goodsProp[3].substring(1, goodsProp[3].length() - 1)));
                } else {
                    goodsPropertyVO.setSort(NumberUtils.INTEGER_ZERO);
                }
                if (StringUtils.isNotBlank(goodsProp[4]) && goodsProp[4].length() > 2) {
                    goodsPropertyVO.setPropType(
                            GoodsPropertyEnterType.fromValue(Integer.valueOf(goodsProp[4].substring(1, goodsProp[4].length() - 1))));
                } else {
                    goodsPropertyVO.setPropType(GoodsPropertyEnterType.TEXT);
                }
                if (StringUtils.isNotBlank(goodsProp[5]) && goodsProp[5].length() > 2) {
                    goodsPropertyVO.setIndexFlag(
                            DefaultFlag.fromValue(Integer.valueOf(goodsProp[5].substring(1, goodsProp[5].length() - 1))));
                } else {
                    goodsPropertyVO.setIndexFlag(DefaultFlag.NO);
                }

                List<EsSearchResponse.AggregationResultItem> childs = prop.getChilds();
                //属性值
                Map<Long, String> detailMap = new HashMap<>();
                for (int i = 0; i < childs.size(); i++) {
                    EsSearchResponse.AggregationResultItem detail = childs.get(i);
                    String detailKeys = detail.getKey().toString();

                    if (StringUtils.isNotBlank(detailKeys)) {
                        String[] goodsPropDetail = detailKeys.split("#&&#");

                        if (StringUtils.isNotBlank(goodsPropDetail[0]) && goodsPropDetail[0].length() > 2
                                && StringUtils.isNotBlank(goodsPropDetail[1]) && goodsPropDetail[1].length() > 2) {
                            Long detailId =
                                    Long.valueOf(goodsPropDetail[0].substring(1, goodsPropDetail[0].length() - 1));

                            String detailName = goodsPropDetail[1].substring(1, goodsPropDetail[1].length() - 1);

                            detailMap.put(detailId, detailName);
                        }
                    }
                }
                goodsPropertyVO.setDetailMap(detailMap);

                if (goodsPropertyVO.getIndexFlag().equals(DefaultFlag.YES)) {
                    goodsPropertyVOS.add(goodsPropertyVO);
                }
            }

            if (CollectionUtils.isNotEmpty(goodsPropertyVOS)) {
                goodsPropertyVOS.parallelStream().collect(Collectors.groupingBy(GoodsPropertyVO::getPropId, Collectors.toList()))
                        .forEach((id, transfer) -> {
                            transfer.stream().reduce((a, b) -> {
                                GoodsPropertyVO gpv = new GoodsPropertyVO();
                                gpv.setPropId(a.getPropId());
                                gpv.setPropName(a.getPropName());
                                gpv.setPropSort(a.getPropSort());
                                gpv.setSort(a.getSort());
                                gpv.setIndexFlag(a.getIndexFlag());

                                Map<Long, String> detailMap = new HashMap<>();

                                if (MapUtils.isNotEmpty(a.getDetailMap())) {
                                    detailMap.putAll(a.getDetailMap());
                                }

                                if (MapUtils.isNotEmpty(b.getDetailMap())) {
                                    detailMap.putAll(b.getDetailMap());
                                }
                                gpv.setDetailMap(detailMap);
                                return gpv;
                            }).ifPresent(goodsPropertyS::add);
                        });

            }

            List<GoodsPropertyVO> subLists = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(goodsPropertyS)) {
                List<GoodsPropertyVO> propertyVOS;
                if (null != cateId) {
                    propertyVOS = goodsPropertyS.stream().sorted(
                                    Comparator.comparing(GoodsPropertyVO::getSort))
                            .collect(Collectors.toList());
                } else {
                    propertyVOS = goodsPropertyS.stream().sorted(
                                    Comparator.comparing(GoodsPropertyVO::getPropSort).reversed())
                            .collect(Collectors.toList());
                }

                if (propertyVOS.size() > Constants.TEN) {
                    subLists = propertyVOS.subList(Constants.ZERO, Constants.TEN);
                } else {
                    subLists = propertyVOS;
                }
            }


            baseResponse.setGoodsPropertyVOS(subLists);
        }
        return baseResponse;
    }

    /**
     * 提取规格与规格值聚合数据
     *
     * @param response
     * @return
     */
    @Override
    public EsGoodsBaseResponse extractGoodsSpecsAndSpecDetails(EsSearchResponse response) {
        List<? extends EsSearchResponse.AggregationResultItem> specGroup = MapUtils.isEmpty(response.getAggResultMap()) ? new ArrayList<>() : response.getAggResultMap().get(
                "specDetails");
        EsGoodsBaseResponse baseResponse = new EsGoodsBaseResponse();
        if (CollectionUtils.isNotEmpty(specGroup)) {
            List<GoodsSpecVO> goodsSpecs = new ArrayList<>(specGroup.size());
            List<GoodsSpecDetailVO> goodsSpecDetails = new ArrayList<>();
            long i = 0;
            long j = 0;
            for (EsSearchResponse.AggregationResultItem spec : specGroup) {
                GoodsSpecVO goodsSpec = new GoodsSpecVO();
                goodsSpec.setSpecId(i);
                goodsSpec.setSpecName(spec.getKey().toString());
                goodsSpecs.add(goodsSpec);
                List<EsSearchResponse.AggregationResultItem> childs = spec.getChilds();
                for (EsSearchResponse.AggregationResultItem specDetail : childs) {
                    GoodsSpecDetailVO goodsSpecDetail = new GoodsSpecDetailVO();
                    goodsSpecDetail.setSpecDetailId(j);
                    goodsSpecDetail.setSpecId(i);
                    goodsSpecDetail.setDetailName(specDetail.getKey().toString());
                    goodsSpecDetails.add(goodsSpecDetail);
                    j++;
                }
                i++;
            }
            baseResponse.setGoodsSpecs(goodsSpecs);
            baseResponse.setGoodsSpecDetails(goodsSpecDetails);
        }
        return baseResponse;
    }

    /**
     * 包装排序字段到EsGoodsInfoQueryRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoQueryRequest wrapperSortToEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest) {
        return queryRequest;
    }

    /**
     * 包装店铺分类信息到EsGoodsInfoQueryRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoQueryRequest wrapperStoreCateToEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest) {
        BaseResponse<StoreCateListByStoreCateIdAndIsHaveSelfResponse> baseResponse =
                storeCateQueryProvider.listByStoreCateIdAndIsHaveSelf(new StoreCateListByStoreCateIdAndIsHaveSelfRequest(queryRequest.getStoreCateIds().get(0), false));
        StoreCateListByStoreCateIdAndIsHaveSelfResponse storeCateListByStoreCateIdAndIsHaveSelfResponse =
                baseResponse.getContext();
        if (Objects.nonNull(storeCateListByStoreCateIdAndIsHaveSelfResponse)) {
            List<StoreCateVO> storeCateVOList = storeCateListByStoreCateIdAndIsHaveSelfResponse.getStoreCateVOList();
            queryRequest.getStoreCateIds().addAll(storeCateVOList.stream().map(StoreCateVO::getStoreCateId).collect(Collectors.toList()));
        }
        return queryRequest;
    }

    /**
     * 包装分类商品信息到EsGoodsInfoQueryRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoQueryRequest wrapperGoodsCateToEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest) {
        if (CollectionUtils.isNotEmpty(queryRequest.getCateIds())) {
            queryRequest.setCateId(null);
            return queryRequest;
        }
        GoodsCateByIdResponse cate =
                goodsCateQueryProvider.getById(new GoodsCateByIdRequest(queryRequest.getCateId())).getContext();
        if (cate != null) {
            GoodsCateListByConditionRequest goodsCateListByConditionRequest = new GoodsCateListByConditionRequest();
            goodsCateListByConditionRequest.setLikeCatePath(ObjectUtils.toString(cate.getCatePath()).concat(String.valueOf(cate.getCateId())).concat("|"));
            List<GoodsCateVO> t_cateList = new ArrayList<>();
            BaseResponse<GoodsCateListByConditionResponse> baseResponse =
                    goodsCateQueryProvider.listByCondition(goodsCateListByConditionRequest);
            GoodsCateListByConditionResponse goodsCateListByConditionResponse = baseResponse.getContext();
            if (Objects.nonNull(goodsCateListByConditionResponse)) {
                t_cateList = goodsCateListByConditionResponse.getGoodsCateVOList();
            }
            if (CollectionUtils.isNotEmpty(t_cateList)) {
                queryRequest.setCateIds(t_cateList.stream().map(GoodsCateVO::getCateId).collect(Collectors.toList()));
                queryRequest.getCateIds().add(queryRequest.getCateId());
                queryRequest.setCateId(null);
            }
        }
        return queryRequest;
    }

    /**
     * 分页查询ES商品(实现WEB的商品列表)
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsResponse pageByGoods(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsResponse goodsResponse = EsGoodsResponse.builder().build();
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        //判断是否是三级分类页面
        Long cateId = queryRequest.getCateId();
        Boolean checkIsOutOfStockShow = this.checkIsOutOfStockShow();
        queryRequest = setPageRequest(builder, queryRequest, checkIsOutOfStockShow);

        SearchHits<EsGoodsVO> searchHits = esBaseService.commonSearchHits(builder.getSearchCriteria(queryRequest),
                EsGoodsVO.class, EsConstants.DOC_GOODS_TYPE);
        Page<EsGoodsVO> pages = esBaseService.commonSearchPage(searchHits, builder.getSearchCriteria(queryRequest).getPageable());

        EsSearchResponse response = EsSearchResponse.buildGoods(searchHits, pages);

        if (response.getGoodsData().size() < 1) {
            goodsResponse.setEsGoods(new MicroServicePage<>(response.getGoodsData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsResponse;
        }

        // 判断BOSS是否配置了无货商品不展示
        if (!Objects.equals(Boolean.TRUE,queryRequest.getIsMoFang()) && checkIsOutOfStockShow) {
            response.getGoodsData().forEach(goodsVo -> {
                // ES过滤ES_Goods.goodsInfos.stock不生效，因此需要手动过滤
                if (CollectionUtils.isNotEmpty(goodsVo.getGoodsInfos())) {
                    goodsVo.setGoodsInfos(goodsVo.getGoodsInfos().stream().filter(info -> info.getStock() > 0)
                            .collect(Collectors.toList()));
                }
            });
        }

        // 过滤不展示的商品标签
        response.getGoodsData().forEach(goods -> {
            if (CollectionUtils.isNotEmpty(goods.getGoodsLabelList())) {
                List<GoodsLabelNestVO> goodsLabelList =
                        goods.getGoodsLabelList().stream()
                                .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                                .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(goodsLabelList)) {
                    goods.setGoodsLabelList(goodsLabelList);
                }
            }
        });

        List<GoodsInfoNestVO> goodsInfoVOList =
                response.getGoodsData().stream().map(EsGoodsVO::getGoodsInfos).flatMap(Collection::stream).collect(Collectors.toList());
        for (GoodsInfoNestVO goodsInfo : goodsInfoVOList) {
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());
            if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
            }
        }

        // 填充sku起售数量
        List<String> goodsInfoIds = goodsInfoVOList.stream().map(GoodsInfoNestVO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, Long> skuStartSaleNumMap = this.getSkuStartSaleNumMap(goodsInfoIds, queryRequest.getTerminalSource());
        for (GoodsInfoNestVO goodsInfoNestVO : goodsInfoVOList) {
            goodsInfoNestVO.setStartSaleNum(skuStartSaleNumMap.get(goodsInfoNestVO.getGoodsInfoId()));
        }

        //提取聚合数据
        EsGoodsBaseResponse baseResponse = extractBrands(response);
        goodsResponse.setBrands(KsBeanUtil.convert(baseResponse.getBrands(), GoodsListBrandVO.class));
        goodsResponse.setBrandMap(baseResponse.getBrandMap());
        if (queryRequest.isCateAggFlag()) {
            //提取聚合数据
            baseResponse = extractGoodsCate(response);
            goodsResponse.setCateList(baseResponse.getCateList());
        }

        //提取规格与规格值聚合数据
        EsGoodsBaseResponse specResponse = extractGoodsSpecsAndSpecDetails(response);

        //提取属性
        EsGoodsBaseResponse propResponse = extractGoodsProp(response, cateId);

        //提取商品标签数据
        goodsResponse.setGoodsLabelVOList(esGoodsLabelService.extractGoodsLabel(response));
        goodsResponse.setGoodsSpecs(baseResponse.getGoodsSpecs());
        goodsResponse.setGoodsSpecDetails(specResponse.getGoodsSpecDetails());
        goodsResponse.setGoodsPropertyVOS(propResponse.getGoodsPropertyVOS());

        goodsResponse.setEsGoods(new MicroServicePage<>(response.getGoodsData(), PageRequest.of(queryRequest.getPageNum(),
                queryRequest.getPageSize()), response.getTotal()));
        return goodsResponse;
    }

    //更新与供应商关联的商家商品es
    @Override
    public void initProviderEsGoodsInfo(Long storeId, List<String> providerGoodsIds) {
        List<String> goodsIds = new ArrayList<>();
        if (storeId != null && CollectionUtils.isEmpty(providerGoodsIds)) {
            GoodsByConditionResponse goodsByConditionResponse = goodsQueryProvider.listByCondition(GoodsByConditionRequest.builder().storeId(storeId).build()).getContext();
            if (goodsByConditionResponse != null && org.apache.commons.collections.CollectionUtils.isNotEmpty(goodsByConditionResponse.getGoodsVOList())) {
                providerGoodsIds = goodsByConditionResponse.getGoodsVOList().stream().map(GoodsVO::getGoodsId).distinct().collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isNotEmpty(providerGoodsIds)) {
            GoodsListByIdsResponse goodsListByIdsResponse = goodsQueryProvider.listByProviderGoodsId(GoodsListByIdsRequest.builder().goodsIds(providerGoodsIds).build()).getContext();
            if (goodsListByIdsResponse != null && org.apache.commons.collections.CollectionUtils.isNotEmpty(goodsListByIdsResponse.getGoodsVOList())) {
                goodsIds = goodsListByIdsResponse.getGoodsVOList().stream().map(GoodsVO::getGoodsId).distinct().collect(Collectors.toList());
            }
            goodsIds.addAll(providerGoodsIds);
        }
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            esGoodsElasticService.initEsGoods(EsGoodsInfoRequest.builder().goodsIds(goodsIds).build());
        }
    }

    /**
     * 初始化SKU持化于ES
     */
    @Override
    public void initEsGoodsInfo(EsGoodsInfoRequest request) {
        esGoodsElasticService.initEsGoods(request);
    }

    /**
     * 上下架
     *
     * @param addedFlag    上下架状态
     * @param goodsIds     商品id列表
     * @param goodsInfoIds 商品skuId列表
     */
    @Override
    @Routing(routingRule = MethodRoutingRule.PLUGIN_TYPE, pluginType = PluginType.NORMAL)
    public void updateAddedStatus(Integer addedFlag, List<String> goodsIds, List<String> goodsInfoIds, PluginType pluginType) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        GoodsInfoListByConditionRequest infoQueryRequest = new GoodsInfoListByConditionRequest();
        infoQueryRequest.setShowPointFlag(Boolean.TRUE);
        if (goodsIds != null) { // 如果传了goodsIds，则按spu查
            queryRequest.setGoodsIds(goodsIds);
            infoQueryRequest.setGoodsIds(goodsIds);
            if (goodsIds.size() > queryRequest.getPageSize()) {
                queryRequest.setPageSize(goodsIds.size());
            }
        }
        if (goodsInfoIds != null) { // 如果传了goodsInfoIds，则按sku查
            queryRequest.setGoodsInfoIds(goodsInfoIds);
            infoQueryRequest.setGoodsInfoIds(goodsInfoIds);
            if (goodsInfoIds.size() > queryRequest.getPageSize()) {
                queryRequest.setPageSize(goodsInfoIds.size());
            }
        }
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        Query searchQuery = NativeQuery.builder().withQuery(builder.getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();

        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);

        LocalDateTime now = LocalDateTime.now();

        Map<String, GoodsInfoVO> goodsInfoMap =
                goodsInfoQueryProvider.listByCondition(infoQueryRequest).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        if (esGoodsInfoList != null) {
            esGoodsInfoList.forEach(esGoodsInfo -> {
                esGoodsInfo.getGoodsInfo().setAddedFlag(addedFlag);
                if (goodsInfoMap.containsKey(esGoodsInfo.getGoodsInfo().getGoodsInfoId())) {
                    GoodsInfoVO info = goodsInfoMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                    esGoodsInfo.getGoodsInfo().setAddedTime(info.getAddedTime());
                    esGoodsInfo.setAddedTime(info.getAddedTime());
                } else {
                    esGoodsInfo.getGoodsInfo().setAddedTime(now);
                    esGoodsInfo.setAddedTime(now);
                }
                esGoodsInfo.getGoodsInfo().setEsSortPrice();

                // EsGoodsInfo里的addedTime
                Document document = Document.create();
                document.put("addedTime", esGoodsInfo.getAddedTime().format(
                        DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));

                // EsGoodsInfo里的GoodsInfo
                Map<String, String> map = new HashMap<>();
                map.put("addedFlag", addedFlag == null ? "" : addedFlag.toString());
                map.put("addedTime", esGoodsInfo.getGoodsInfo().getAddedTime().format(
                        DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));

                document.put("goodsInfo", map);
                UpdateQuery updateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();

                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            });
        }
        queryRequest.setQueryGoods(true);
        Query infoSearchQuery = NativeQuery.builder().withQuery(builder.getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
        List<IndexQuery> esGoodsQuery = new ArrayList<>();
        if (esGoodsList != null) {
            esGoodsList.forEach(esGoods -> {
                esGoods.setAddedFlag(addedFlag);
                esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                    esGoodsInfo.setAddedFlag(addedFlag);
                    if (goodsInfoMap.containsKey(esGoodsInfo.getGoodsInfoId())) {
                        GoodsInfoVO info = goodsInfoMap.get(esGoodsInfo.getGoodsInfoId());
                        esGoodsInfo.setAddedTime(info.getAddedTime());
                        esGoods.setAddedTime(info.getAddedTime());
                    } else {
                        esGoods.setAddedTime(now);
                        esGoodsInfo.setAddedTime(now);
                    }
                    esGoodsInfo.setEsSortPrice();
                });

                IndexQuery iq = new IndexQuery();
                iq.setId(esGoods.getId());
                iq.setObject(esGoods);
                esGoodsQuery.add(iq);

            });
            if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            }
        }
    }

    /**
     * 根据商品批量删除
     *
     * @param goodsIds
     */
    @Override
    public void deleteByGoods(List<String> goodsIds) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setGoodsIds(goodsIds);
        queryRequest.setQueryGoods(true);
        if (CollectionUtils.isNotEmpty(goodsIds) && goodsIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(goodsIds.size());
        }
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        Query infoSearchQuery = NativeQuery.builder().withQuery(builder.getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
        if (esGoodsList != null) {
            esGoodsList.forEach(esGoods -> {
                elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
                elasticsearchTemplate.delete(esGoods.getId(), IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            });
        }
        esBaseService.refresh(EsConstants.DOC_GOODS_TYPE);

        queryRequest.setQueryGoods(false);
        //es不进行分页查询的情况下，默认也只会查询10条数据，
        //要修改size长度elasticsearchTemplate客户端控制size大小只能通过分页api控制,因为window窗口的最大值是2000000000，
        //所以在此处指定了2000000000,还可以再调整，实际一个商品对应的sku不会超过200个
        PageRequest pageRequest =PageRequest.of(NumberUtils.INTEGER_ZERO, 1000);
        Query searchQuery =
                NativeQuery.builder().withQuery(builder.getWhereCriteria(queryRequest)).withPageable(pageRequest).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        if (esGoodsInfoList != null) {
            esGoodsInfoList.forEach(esGoodsInfo -> {
                elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
                elasticsearchTemplate.delete(esGoodsInfo.getId(), IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            });
        }
        esBaseService.refresh(EsConstants.DOC_GOODS_INFO_TYPE);
    }

    /**
     * 批量删除
     *
     * @param skuIds SKU编号
     */
    @Override
    public void delete(List<String> skuIds) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(skuIds);
        if (CollectionUtils.isNotEmpty(skuIds) && skuIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(skuIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);

//        DeleteQuery deleteQuery = new DeleteQuery();
//        deleteQuery.setQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest));
//        deleteQuery.setPageSize(skuIds.size());
//        elasticsearchRestTemplate.delete(deleteQuery, EsGoods.class);

        if (esGoodsInfoList != null) {
            esGoodsInfoList.forEach(esGoodsInfo -> {
                elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
                elasticsearchTemplate.delete(esGoodsInfo.getId(), IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            });
        }
        esBaseService.refresh(EsConstants.DOC_GOODS_INFO_TYPE);
    }

    /**
     * 使用标准分词对字符串分词
     *
     * @param text 待分词文本
     * @return 分此后的词条
     */
    @Override
    public String analyze(String text) {
        return analyze(text, analyzer);
    }

    /**
     * 根据给定的分词器对字符串进行分词
     *
     * @param text     要分词的文本
     * @param analyzer 指定分词器
     * @return 分词后的词条列表
     */
    private String analyze(String text, String analyzer) {
        final String fAnalyzer = StringUtils.isBlank(analyzer) ? "simple" : analyzer;
        List<String> res = new ArrayList<>();
        try {
            AnalyzeResponse response = elasticsearchClient.indices().analyze(g -> g.analyzer(fAnalyzer).text(text));
            if (CollectionUtils.isNotEmpty(response.tokens())) {
                res.addAll(response.tokens().stream().map(AnalyzeToken::token).collect(Collectors.toList()));
            }
        } catch (IOException e) {
            log.error("EsGoodsInfoElasticService analyze IOException", e);
        }
        res.addAll(Arrays.asList(text.split("[^0-9]+")));
        res.add(text);
        return StringUtils.join(res.stream().distinct().collect(Collectors.toList()), " ");
    }

    /**
     * 根据商品sku批量获取区间价键值Map
     *
     * @param skuIds 商品skuId
     * @return 区间价键值Map内容<商品skuId, 区间价列表>
     */
    private Map<String, List<GoodsIntervalPriceVO>> getIntervalPriceMapBySkuId(List<String> skuIds) {
        List<GoodsIntervalPriceVO> voList = goodsIntervalPriceQueryProvider.listByGoodsIds(
                GoodsIntervalPriceListBySkuIdsRequest.builder().skuIds(skuIds).build()).getContext().getGoodsIntervalPriceVOList();
        return voList.stream().collect(Collectors.groupingBy(GoodsIntervalPriceVO::getGoodsInfoId));
    }

    /**
     * 删除品牌时，更新es数据
     */
    @Override
    public void delBrandIds(List<Long> brandIds, Long storeId) {
        deleteBrandNameCommon(brandIds, EsConstants.DOC_GOODS_TYPE, storeId);
        deleteBrandNameCommon(brandIds, EsConstants.DOC_GOODS_INFO_TYPE, storeId);

    }

    /**
     * 删除店铺分类时更新es数据
     *
     * @param storeCateIds
     * @param storeId
     */
    @Override
    public void delStoreCateIds(List<Long> storeCateIds, Long storeId) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setStoreCateIds(storeCateIds);
        queryRequest.setStoreId(storeId);
        if (CollectionUtils.isNotEmpty(storeCateIds) && storeCateIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(storeCateIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        if (!esGoodsInfoList.iterator().hasNext()) {
            return;
        }
        List<IndexQuery> esGoodsInfos = new ArrayList<>();
        esGoodsInfoList.forEach(item -> {
                    item.setStoreCateIds(null);
                    IndexQuery iq = new IndexQuery();
                    iq.setObject(item);
                    esGoodsInfos.add(iq);
                }
        );

        //生成新数据
        BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
        elasticsearchTemplate.bulkIndex(esGoodsInfos, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
    }

    /**
     * 更新分销佣金、分销商品审核状态（添加分销商品时）
     *
     * @param esGoodsInfoDistributionRequest
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    @Override
    public Boolean modifyDistributionCommission(EsGoodsInfoModifyDistributionCommissionRequest esGoodsInfoDistributionRequest) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        List<DistributionGoodsInfoModifyDTO> distributionGoodsInfoDTOList =
                esGoodsInfoDistributionRequest.getDistributionGoodsInfoDTOList();
        List<String> goodsInfoIds =
                distributionGoodsInfoDTOList.stream().map(DistributionGoodsInfoModifyDTO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, BigDecimal> stringBigDecimalMap = distributionGoodsInfoDTOList.stream().collect(Collectors.toMap
                (DistributionGoodsInfoModifyDTO::getGoodsInfoId, DistributionGoodsInfoModifyDTO::getCommissionRate));
        Map<String, BigDecimal> commssionlMap = distributionGoodsInfoDTOList.stream().collect(Collectors.toMap
                (DistributionGoodsInfoModifyDTO::getGoodsInfoId, DistributionGoodsInfoModifyDTO::getDistributionCommission));
        queryRequest.setGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoIds) && goodsInfoIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(goodsInfoIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        Integer result = 0;
        if (esGoodsInfoList != null) {
            Map<String, Object> map;
            for (EsGoodsInfo esGoodsInfo : esGoodsInfoList) {
                Document document = Document.create();
                map = new HashMap<>(4);
                BigDecimal commission = commssionlMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                BigDecimal commissionRate = stringBigDecimalMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                if (Objects.nonNull(commissionRate)) {
                    map.put("commissionRate", commissionRate.doubleValue());
                }
                if (Objects.nonNull(commission)) {
                    map.put("distributionCommission", commission.doubleValue());
                }
                if (Objects.nonNull(esGoodsInfoDistributionRequest.getDistributionCreateTime())) {
                    map.put("distributionCreateTime", esGoodsInfoDistributionRequest.getDistributionCreateTime().format(
                            DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));
                }
                map.put("distributionGoodsAudit", esGoodsInfoDistributionRequest.getDistributionGoodsAudit().toValue());
                document.put("goodsInfo", map);
                if (Objects.nonNull(esGoodsInfoDistributionRequest.getDistributionGoodsStatus())) {
                    document.put("distributionGoodsStatus",
                            esGoodsInfoDistributionRequest.getDistributionGoodsStatus());
                }

                UpdateQuery updateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                result++;
            }
            queryRequest.setQueryGoods(true);
            Query infoSearchQuery =
                    NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
            Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
            List<IndexQuery> esGoodsQuery = new ArrayList<>();
            if (esGoodsList != null) {
                esGoodsList.forEach(esGoods -> {
                    esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                        BigDecimal commissionRate = stringBigDecimalMap.get(esGoodsInfo.getGoodsInfoId());
                        BigDecimal commission = commssionlMap.get(esGoodsInfo.getGoodsInfoId());
                        if (Objects.nonNull(commissionRate)) {
                            esGoodsInfo.setCommissionRate(commissionRate);
                            esGoodsInfo.setDistributionCommission(commission);
                        }
                        boolean isDistributionGoods = esGoodsInfoDistributionRequest.getDistributionGoodsInfoDTOList().parallelStream().anyMatch(goodsInfo -> StringUtils.equals(goodsInfo.getGoodsInfoId(), esGoodsInfo.getGoodsInfoId()));
                        if (isDistributionGoods) {
                            esGoodsInfo.setDistributionGoodsAudit(esGoodsInfoDistributionRequest.getDistributionGoodsAudit());
                        }
                    });
                    if (Objects.nonNull(esGoodsInfoDistributionRequest.getDistributionGoodsStatus())) {
                        esGoods.setDistributionGoodsStatus(esGoodsInfoDistributionRequest.getDistributionGoodsStatus());
                    }
                    IndexQuery iq = new IndexQuery();
                    iq.setId(esGoods.getId());
                    iq.setObject(esGoods);
                    esGoodsQuery.add(iq);

                });
                if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }
            }
        }
        return result.equals(goodsInfoIds.size()) ? Boolean.TRUE : Boolean.FALSE;
    }


    /**
     * 更新分销商品审核状态（平台端审核时）
     *
     * @param request
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    @Override
    public Boolean modifyDistributionGoodsAudit(EsGoodsInfoModifyDistributionGoodsAuditRequest request) {
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        List<String> goodsInfoIds = request.getGoodsInfoIds();
        queryRequest.setGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoIds) && goodsInfoIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(goodsInfoIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        Integer result = 0;
        if (esGoodsInfoList != null) {
            Integer distributionGoodsAudit = request.getDistributionGoodsAudit();
            DistributionGoodsAudit goodsAudit = DistributionGoodsAudit.values()[distributionGoodsAudit];

            Map<String, Object> map;
            for (EsGoodsInfo esGoodsInfo : esGoodsInfoList) {
                Document document = Document.create();
                map = new HashMap<>(4);
                map.put("distributionGoodsAuditReason",
                        distributionGoodsAudit.equals(DistributionGoodsAudit.CHECKED.toValue()) ? "" :
                                request.getDistributionGoodsAuditReason());
                map.put("distributionGoodsAudit", distributionGoodsAudit);
                document.put("goodsInfo", map);

                UpdateQuery updateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                result++;
            }
            queryRequest.setQueryGoods(true);
            Query infoSearchQuery =
                    NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
            Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
            List<IndexQuery> esGoodsQuery = new ArrayList<>();
            if (esGoodsList != null) {
                esGoodsList.forEach(esGoods -> {
                    esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                        Boolean aBoolean =
                                goodsInfoIds.stream().anyMatch(goodsInfoId -> goodsInfoId.equals(esGoodsInfo.getGoodsInfoId()));
                        if (aBoolean) {
                            esGoodsInfo.setDistributionGoodsAudit(goodsAudit);
                            esGoodsInfo.setDistributionGoodsAuditReason(distributionGoodsAudit.equals(DistributionGoodsAudit.CHECKED.toValue()) ? "" : request.getDistributionGoodsAuditReason());

                        }
                    });
                    IndexQuery iq = new IndexQuery();
                    iq.setId(esGoods.getId());
                    iq.setObject(esGoods);
                    esGoodsQuery.add(iq);

                });
                if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }
            }
        }
        return result.equals(goodsInfoIds.size()) ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 商家-社交分销开关设置，更新分销商品状态
     *
     * @param request
     * @return
     */
    @Override
    public Boolean modifyDistributionGoodsStatus(EsGoodsInfoModifyDistributionGoodsStatusRequest request) {
        Long storeId = request.getStoreId();
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setDistributionGoodsAudit(DistributionGoodsAudit.CHECKED.toValue());
        queryRequest.setStoreId(storeId);
        //不能用默认分页来处理，给出最大值
        queryRequest.setPageSize(1000000);
        Query searchQuery = NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        if (esGoodsInfoList != null) {
            Integer distributionGoodsStatus = request.getDistributionGoodsStatus();
            for (EsGoodsInfo esGoodsInfo : esGoodsInfoList) {
                Document document = Document.create();
                document.put("distributionGoodsStatus", distributionGoodsStatus);
                UpdateQuery skuUpdateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(skuUpdateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));

                UpdateQuery spuUpdateQuery = UpdateQuery.builder(esGoodsInfo.getGoodsInfo().getGoodsId()).withDocument(document).build();
                elasticsearchTemplate.update(spuUpdateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 更新分销商品审核状态（修改商品销售模式：零售->批发）
     *
     * @param spuId
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    @Override
    public Boolean modifyDistributionGoodsAudit(String spuId) {
        EsGoodsInfoQueryRequest request = new EsGoodsInfoQueryRequest();
        List<String> goodsIds = Collections.singletonList(spuId);
        request.setGoodsIds(goodsIds);
        if (CollectionUtils.isNotEmpty(goodsIds) && goodsIds.size() > request.getPageSize()) {
            request.setPageSize(goodsIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(request)).withPageable(request.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        if (esGoodsInfoList != null) {

            Map<String, Object> map;
            for (EsGoodsInfo esGoodsInfo : esGoodsInfoList) {
                Document document = Document.create();
                map = new HashMap<>(4);
                map.put("distributionGoodsAuditReason", "");
                map.put("distributionGoodsAudit", DistributionGoodsAudit.COMMON_GOODS.toValue());
                document.put("goodsInfo", map);
                UpdateQuery updateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            }
            request.setQueryGoods(true);
            Query infoSearchQuery =
                    NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(request)).withPageable(request.getPageable()).build();
            Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
            List<IndexQuery> esGoodsQuery = new ArrayList<>();
            if (esGoodsList != null) {
                esGoodsList.forEach(esGoods -> {
                    esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                        esGoodsInfo.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                        esGoodsInfo.setDistributionGoodsAuditReason("");
                    });
                    IndexQuery iq = new IndexQuery();
                    iq.setId(esGoods.getId());
                    iq.setObject(esGoods);
                    esGoodsQuery.add(iq);
                });
                if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 修改spu和sku的商品分类索引信息
     *
     * @param goodsCateVO
     */
    @Override
    public void updateCateName(GoodsCateVO goodsCateVO) {
        updateCateNameCommon(goodsCateVO);
    }

    /**
     * 修改spu和sku的商品品牌索引信息
     *
     * @param goodsBrandVO
     */
    @Override
    public void updateBrandName(GoodsBrandVO goodsBrandVO) {
        updateBrandNameCommon(goodsBrandVO);
    }

    /**
     * 修改商品分类
     *
     * @param goodsCateVO 分类bean
     * @return
     */
    private Long updateCateNameCommon(GoodsCateVO goodsCateVO) {
        String queryName = "goodsCate.cateId";
        String updateName = "goodsCate.cateName";
        Long resCount = 0L;
        if (Objects.nonNull(goodsCateVO)) {
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE);
            updateByQueryRequest.setQuery(QueryBuilders.termQuery(queryName, goodsCateVO.getCateId()));
            updateByQueryRequest.setScript(new Script("ctx._source." + updateName + "='" + goodsCateVO.getCateName() + "'"));
            try {
                BulkByScrollResponse response = elasticsearchClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
                resCount = response.getUpdated();
            } catch (IOException e) {
                log.error("EsGoodsInfoElasticService updateCateNameCommon IOException", e);
            }*/
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(term(g -> g.field(queryName).value(goodsCateVO.getCateId()))).build())
                            .withScript("ctx._source." + updateName + "='" + goodsCateVO.getCateName() + "'")
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE));
            resCount = byQueryResponse.getUpdated();
        }
        return resCount;
    }

    /**
     * 修改商品品牌
     *
     * @param goodsBrandVO 品牌bean
     * @return
     */
    private Long updateBrandNameCommon(GoodsBrandVO goodsBrandVO) {
        String queryName = "goodsBrand.brandId";
        String updateName = "goodsBrand.brandName";
        String updateSort = "goodsBrand.brandSort";
        String updateRecommend = "goodsBrand.recommendFlag";
        String updateFirst = "goodsBrand.first";
        String updateLogo = "goodsBrand.logo";
        Long resCount = 0L;
        if (Objects.nonNull(goodsBrandVO)) {
            //设置默认的推荐和排序
            if (goodsBrandVO.getRecommendFlag() == null) {
                goodsBrandVO.setRecommendFlag(DefaultFlag.NO);
            }

            if (goodsBrandVO.getBrandSort() == null) {
                goodsBrandVO.setBrandSort(0L);
            }

            StringBuffer script = new StringBuffer("ctx._source." + updateName + "='" + goodsBrandVO.getBrandName() + "';");
            script.append("ctx._source.").append(updateSort).append("='").append(goodsBrandVO.getBrandSort()).append(
                    "';");
            script.append("ctx._source.").append(updateRecommend).append("='").append(goodsBrandVO.getRecommendFlag().toValue()).append("';");
            script.append("ctx._source.").append(updateFirst).append("='").append(goodsBrandVO.getFirst()).append("';");
            script.append("ctx._source.").append(updateLogo).append("='").append(goodsBrandVO.getLogo()).append("'");

//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE);
//            updateByQueryRequest.setQuery(QueryBuilders.termQuery(queryName, goodsBrandVO.getBrandId()));
//            updateByQueryRequest.setScript(new Script(script.toString()));
//            try {
//                BulkByScrollResponse response = elasticsearchClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//                resCount = response.getUpdated();
//            } catch (IOException e) {
//                log.error("EsGoodsInfoElasticService updateBrandNameCommon IOException", e);
//            }
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(term(g -> g.field(queryName).value(goodsBrandVO.getBrandId()))).build())
                            .withScript(script.toString())
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE));
            resCount = byQueryResponse.getUpdated();
        }
        return resCount;
    }

    /**
     * 删除商品品牌之后同步es
     *
     * @param ids       删除的品牌Id
     * @param indexName 索引名称
     * @param storeId   店铺Id
     * @return
     */
    private Long deleteBrandNameCommon(List<Long> ids, String indexName, Long storeId) {
        String queryName = "goodsBrand.brandId";
        String queryStoreName = StringUtils.equals(EsConstants.DOC_GOODS_TYPE, indexName) ? "goodsInfos.storeId" :
                "goodsInfo.storeId";
        Long resCount = 0L;
        if (CollectionUtils.isNotEmpty(ids)) {
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(indexName);
//            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//            boolQueryBuilder.must(QueryBuilders.termsQuery(queryName, ids));
//            if (Objects.nonNull(storeId)) {
//                boolQueryBuilder.must(termQuery(queryStoreName, storeId));
//            }
//            updateByQueryRequest.setQuery(boolQueryBuilder);
//            updateByQueryRequest.setScript(new Script("ctx._source.goodsBrand.brandName='';ctx._source.goodsBrand.brandId='';ctx" +
//                    "._source.goodsBrand.pinyin=''"));
//            try {
//                BulkByScrollResponse response = elasticsearchClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//                resCount = response.getUpdated();
//            } catch (IOException e) {
//                log.error("EsGoodsInfoElasticService deleteBrandNameCommon IOException", e);
//            }
            BoolQuery.Builder builder = QueryBuilders.bool();
            builder.must(terms(g -> g.field(queryName).terms(x -> x.value(ids.stream().map(FieldValue::of).collect(Collectors.toList())))));
            if (Objects.nonNull(storeId)) {
                builder.must(term(g -> g.field(queryStoreName).value(storeId)));
            }
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(g -> g.bool(builder.build())).build())
                            .withScript("ctx._source.goodsBrand.brandName='';ctx._source.goodsBrand.brandId='';ctx" +
                            "._source.goodsBrand.pinyin=''")
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(indexName));
            resCount = byQueryResponse.getUpdated();
        }
        return resCount;
    }

    /**
     * 新增不需要审核的企业购商品时 刷新es
     *
     * @param batchEnterPrisePriceDTOS
     */
    @Override
    public Boolean updateEnterpriseGoodsInfo(List<BatchEnterPrisePriceDTO> batchEnterPrisePriceDTOS, EnterpriseAuditState enterpriseAuditState) {
        List<String> goodsInfoIds = batchEnterPrisePriceDTOS.stream().map(BatchEnterPrisePriceDTO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, BigDecimal> skuIdEnterprisePriceMap = new HashMap<>();
        batchEnterPrisePriceDTOS.forEach(b -> skuIdEnterprisePriceMap.put(b.getGoodsInfoId(), b.getEnterPrisePrice()));
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoIds) && goodsInfoIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(goodsInfoIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);

        Integer result = 0;
        if (esGoodsInfoList != null) {
            Map<String, Object> map;
            for (EsGoodsInfo esGoodsInfo : esGoodsInfoList) {
                Document document = Document.create();
                map = new HashMap<>(4);
                map.put("enterPriseAuditStatus", enterpriseAuditState.toValue());
                map.put("enterPrisePrice", String.valueOf(skuIdEnterprisePriceMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId())));
                if (EnterpriseAuditState.CHECKED.equals(enterpriseAuditState)) {
                    map.put("esSortPrice", String.valueOf(skuIdEnterprisePriceMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId())));
                }
                document.put("goodsInfo", map);
                UpdateQuery updateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                result++;
            }
            queryRequest.setQueryGoods(true);
            Query infoSearchQuery = NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
            Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
            List<IndexQuery> esGoodsQuery = new ArrayList<>();
            if (esGoodsList != null) {
                esGoodsList.forEach(esGoods -> {
                    esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                        Boolean aBoolean =
                                goodsInfoIds.stream().anyMatch(goodsInfoId -> goodsInfoId.equals(esGoodsInfo.getGoodsInfoId()));
                        if (aBoolean) {
                            esGoodsInfo.setEnterPriseAuditStatus(enterpriseAuditState.toValue());
                            esGoodsInfo.setEnterPrisePrice(skuIdEnterprisePriceMap.get(esGoodsInfo.getGoodsInfoId()));
                            esGoodsInfo.setEsSortPrice();
                        }
                    });

                    IndexQuery iq = new IndexQuery();
                    iq.setId(esGoods.getId());
                    iq.setObject(esGoods);
                    esGoodsQuery.add(iq);

                });
                if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }
            }
        }
        return result.equals(goodsInfoIds.size()) ? Boolean.TRUE : Boolean.FALSE;
    }


    /**
     * 更新企业购商品（平台端审核时）
     *
     * @param request
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    @Override
    public Boolean modifyEnterpriseAuditStatus(EsGoodsInfoEnterpriseAuditRequest request) {
//        Client client = elasticsearchRestTemplate.getClient();
        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        List<String> goodsInfoIds = request.getGoodsInfoIds();
        queryRequest.setGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoIds) && goodsInfoIds.size() > queryRequest.getPageSize()) {
            queryRequest.setPageSize(goodsInfoIds.size());
        }
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        Integer result = 0;
        if (esGoodsInfoList != null) {
            Map<String, Object> map;
            for (EsGoodsInfo esGoodsInfo : esGoodsInfoList) {
                Document document = Document.create();
                map = new HashMap<>(4);
                map.put("enterPriseGoodsAuditReason",
                        request.getEnterPriseAuditStatus().equals(EnterpriseAuditState.NOT_PASS) ? request.getEnterPriseGoodsAuditReason() : "");
                map.put("enterPriseAuditStatus", request.getEnterPriseAuditStatus().toValue());
                if (EnterpriseAuditState.CHECKED.equals(request.getEnterPriseAuditStatus()) && Objects.nonNull(esGoodsInfo.getGoodsInfo().getEnterPrisePrice())) {
                    map.put("esSortPrice", String.valueOf(esGoodsInfo.getGoodsInfo().getEnterPrisePrice()));
                }
                document.put("goodsInfo", map);
                UpdateQuery updateQuery = UpdateQuery.builder(esGoodsInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                result++;
            }
            queryRequest.setQueryGoods(true);
            Query infoSearchQuery =
                    NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
            Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
            List<IndexQuery> esGoodsQuery = new ArrayList<>();
            if (esGoodsList != null) {
                esGoodsList.forEach(esGoods -> {
                    esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                        Boolean aBoolean =
                                goodsInfoIds.stream().anyMatch(goodsInfoId -> goodsInfoId.equals(esGoodsInfo.getGoodsInfoId()));
                        if (aBoolean) {
                            esGoodsInfo.setEnterPriseAuditStatus(request.getEnterPriseAuditStatus().toValue());
                            esGoodsInfo.setEnterPriseGoodsAuditReason(request.getEnterPriseAuditStatus().equals(EnterpriseAuditState.NOT_PASS)
                                    ? request.getEnterPriseGoodsAuditReason() : "");
                            esGoodsInfo.setEsSortPrice();
                        }
                    });

                    IndexQuery iq = new IndexQuery();
                    iq.setId(esGoods.getId());
                    iq.setObject(esGoods);
                    esGoodsQuery.add(iq);

                });
                if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }
            }
        }
        return result.equals(goodsInfoIds.size()) ? Boolean.TRUE : Boolean.FALSE;
    }


    /**
     * 修改商品销量
     *
     * @param spuId    spuId
     * @param salesNum 销量
     * @return
     */
    @Override
    public Long updateSalesNumBySpuId(String spuId, Long salesNum) {
        Long resCount = 0L;
        if (StringUtils.isNotEmpty(spuId) && Objects.nonNull(salesNum)) {
            /*try {
                UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
                updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
                updateByQueryRequest.setQuery(QueryBuilders.termQuery("goodsInfo.goodsId", spuId));
                updateByQueryRequest.setScript(new Script("ctx._source.goodsInfo.goodsSalesNum=" + salesNum));
                BulkByScrollResponse response =
                        elasticsearchRestTemplate.getClient().updateByQuery(updateByQueryRequest,
                                RequestOptions.DEFAULT);
                resCount = response.getUpdated();

                updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
                updateByQueryRequest.setQuery(QueryBuilders.termQuery("_id", spuId));
                updateByQueryRequest.setScript(new Script("ctx._source.goodsSalesNum=" + salesNum));
                BulkByScrollResponse response1 = elasticsearchClient.updateByQuery(updateByQueryRequest,
                        RequestOptions.DEFAULT);
                resCount += response1.getUpdated();

            } catch (IOException e) {
                log.error("EsGoodsInfoElasticService updateSalesNumBySpuId IOException", e);
            }*/
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("goodsInfo.goodsId").value(spuId))).build())
                            .withIndex(EsConstants.DOC_GOODS_INFO_TYPE)
                            .withScript("ctx._source.goodsInfo.goodsSalesNum=" + salesNum)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            resCount = byQueryResponse.getUpdated();

            updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(spuId))).build())
                            .withIndex(EsConstants.DOC_GOODS_TYPE)
                            .withScript("ctx._source.goodsSalesNum=" + salesNum)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            resCount += byQueryResponse.getUpdated();
        }
        return resCount;
    }

    /**
     * 修改商品排序号
     *
     * @param spuId  spuId
     * @param sortNo 排序号
     * @return
     */
    @Override
    public Long updateSortNoBySpuId(String spuId, Long sortNo) {
        Long resCount = 0L;
        if (StringUtils.isNotEmpty(spuId) && Objects.nonNull(sortNo)) {
            /*try {
                UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
                updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
                updateByQueryRequest.setQuery(QueryBuilders.termQuery("goodsInfo.goodsId", spuId));
                updateByQueryRequest.setScript(new Script("ctx._source.sortNo=" + sortNo));
                BulkByScrollResponse response = elasticsearchClient.updateByQuery(updateByQueryRequest,
                        RequestOptions.DEFAULT);
                resCount = response.getUpdated();

                updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
                updateByQueryRequest.setQuery(QueryBuilders.termQuery("_id", spuId));
                updateByQueryRequest.setScript(new Script("ctx._source.sortNo=" + sortNo));
                BulkByScrollResponse response1 = elasticsearchClient.updateByQuery(updateByQueryRequest,
                        RequestOptions.DEFAULT);
                resCount += response1.getUpdated();
            } catch (IOException e) {
                log.error("EsGoodsInfoElasticService updateSortNoBySpuId IOException", e);
            }*/
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("goodsInfo.goodsId").value(spuId))).build())
                            .withIndex(EsConstants.DOC_GOODS_INFO_TYPE)
                            .withScript("ctx._source.sortNo=" + sortNo)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            resCount = byQueryResponse.getUpdated();

            updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(spuId))).build())
                            .withIndex(EsConstants.DOC_GOODS_TYPE)
                            .withScript("ctx._source.sortNo=" + sortNo)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            resCount += byQueryResponse.getUpdated();
        }
        return resCount;
    }

    /**
     * 渠道设置未配置或停用，不显示linkedMall商品
     *
     * @param queryRequest 请求
     */
    private void filterLinkedMallShow(EsGoodsInfoQueryRequest queryRequest) {
        String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
        if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
            queryRequest.setNotShowLinkedMallFlag(Boolean.TRUE);
        }
    }

    /**
     * 渠道设置未配置或停用，不显示VOP商品
     *
     * @param queryRequest 请求
     */
    private void filterVOPShow(EsGoodsInfoQueryRequest queryRequest) {
        String vopStatus = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
        if (StringUtils.isBlank(vopStatus) || VASStatus.DISABLE.toValue().equalsIgnoreCase(vopStatus)) {
            queryRequest.setNotShowVOPFlag(Boolean.TRUE);
        }
    }

    /**
     * 修改商品价格
     *
     * @param request 调价参数
     * @return
     */
    @Override
    public void adjustPrice(EsGoodsInfoAdjustPriceRequest request) {

        //根据goodsInfoIds获取goodsIds
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder()
                .goodsInfoIds(request.getGoodsInfoIds()).build()).getContext().getGoodsInfos();
        List<String> goodsIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());

        //查询SPU
        List<GoodsVO> goodsVOList = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(goodsIds).build()).getContext().getGoodsVOList();
        Map<String, GoodsVO> goodsVOMap = goodsVOList.stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        //查询指定SPU下的所有SKU
        goodsInfos = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().goodsIds(goodsIds).build()).getContext().getGoodsInfos();
        Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfos.stream()
                .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        List<String> goodsInfoIds = new ArrayList<>(goodsInfoVOMap.keySet());

        Map<String, List<GoodsLevelPriceNest>> levelPriceMap = new HashMap<>();
        Map<String, List<GoodsIntervalPriceVO>> intervalPriceMap = new HashMap<>();

        //区间价Map
        intervalPriceMap.putAll(getIntervalPriceMapBySkuId(goodsInfoIds));
        //等级价Map
        levelPriceMap.putAll(goodsLevelPriceQueryProvider.listBySkuIds(new GoodsLevelPriceBySkuIdsRequest(goodsInfoIds))
                .getContext().getGoodsLevelPriceList()
                .stream().map(price -> KsBeanUtil.convert(price, GoodsLevelPriceNest.class))
                .collect(Collectors.groupingBy(GoodsLevelPriceNest::getGoodsInfoId)));

        EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
        //sku
        queryRequest.setGoodsIds(goodsIds);
        queryRequest.setPageSize(goodsInfoIds.size());
        Query searchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoodsInfo> esGoodsInfoList = esBaseService.commonPage(searchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE);
        List<IndexQuery> esGoodsInfoQuery = new ArrayList<>();
        if (esGoodsInfoList != null) {
            esGoodsInfoList.forEach(esGoodsInfo -> {
                GoodsInfoVO goodsInfoVO = goodsInfoVOMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId());
                GoodsVO goodsVO = goodsVOMap.get(esGoodsInfo.getGoodsId());
                if (Objects.nonNull(goodsInfoVO)) {
                    esGoodsInfo.getGoodsInfo().setAloneFlag(goodsInfoVO.getAloneFlag());
                    esGoodsInfo.getGoodsInfo().setPriceType(goodsVO.getPriceType());
                    esGoodsInfo.getGoodsInfo().setSaleType(goodsInfoVO.getSaleType());
                    esGoodsInfo.getGoodsInfo().setMarketPrice(goodsInfoVO.getMarketPrice());
                    esGoodsInfo.getGoodsInfo().setSupplyPrice(goodsInfoVO.getSupplyPrice());

                    //区间价
                    if (CollectionUtils.isNotEmpty(intervalPriceMap.get(goodsInfoVO.getGoodsInfoId()))) {
                        List<BigDecimal> prices = intervalPriceMap.get(goodsInfoVO.getGoodsInfoId()).stream().map(GoodsIntervalPriceVO::getPrice).filter(Objects::nonNull).collect(Collectors.toList());
                        esGoodsInfo.getGoodsInfo().setIntervalMinPrice(prices.stream().filter(Objects::nonNull).min(BigDecimal::compareTo).orElseGet(goodsInfoVO::getMarketPrice));
                        esGoodsInfo.getGoodsInfo().setIntervalMaxPrice(prices.stream().filter(Objects::nonNull).max(BigDecimal::compareTo).orElseGet(goodsInfoVO::getMarketPrice));
                    }
                    esGoodsInfo.setGoodsLevelPrices(levelPriceMap.get(goodsInfoVO.getGoodsInfoId()));
                }

                IndexQuery iq = new IndexQuery();
                iq.setId(esGoodsInfo.getId());
                iq.setObject(esGoodsInfo);
                esGoodsInfoQuery.add(iq);
            });
            if (CollectionUtils.isNotEmpty(esGoodsInfoQuery)) {
                BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                elasticsearchTemplate.bulkIndex(esGoodsInfoQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            }
        }

        //spu
        queryRequest.setQueryGoods(true);
        Query infoSearchQuery =
                NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
        //spu
        List<IndexQuery> esGoodsQuery = new ArrayList<>();
        if (esGoodsList != null) {
            esGoodsList.forEach(esGoods -> {
                GoodsVO goodsVO = goodsVOMap.get(esGoods.getId());
                esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                    GoodsInfoVO goodsInfoVO = goodsInfoVOMap.get(esGoodsInfo.getGoodsInfoId());
                    if (Objects.nonNull(goodsInfoVO)) {
                        esGoodsInfo.setAloneFlag(goodsInfoVO.getAloneFlag());
                        esGoodsInfo.setPriceType(goodsVO.getPriceType());
                        esGoodsInfo.setSaleType(goodsInfoVO.getSaleType());
                        esGoodsInfo.setMarketPrice(goodsInfoVO.getMarketPrice());
                        esGoodsInfo.setSupplyPrice(goodsInfoVO.getSupplyPrice());
                        //区间价
                        if (CollectionUtils.isNotEmpty(intervalPriceMap.get(goodsInfoVO.getGoodsInfoId()))) {
                            List<BigDecimal> prices = intervalPriceMap.get(goodsInfoVO.getGoodsInfoId()).stream().map(GoodsIntervalPriceVO::getPrice).filter(Objects::nonNull).collect(Collectors.toList());
                            esGoodsInfo.setIntervalMinPrice(prices.stream().filter(Objects::nonNull).min(BigDecimal::compareTo).orElseGet(goodsInfoVO::getMarketPrice));
                            esGoodsInfo.setIntervalMaxPrice(prices.stream().filter(Objects::nonNull).max(BigDecimal::compareTo).orElseGet(goodsInfoVO::getMarketPrice));
                        }
                    }
                });

                IndexQuery iq = new IndexQuery();
                iq.setId(esGoods.getId());
                iq.setObject(esGoods);
                esGoodsQuery.add(iq);
            });
            if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                elasticsearchTemplate.bulkIndex(esGoodsQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            }
        }

        //代销商品
        if (PriceAdjustmentType.SUPPLY.equals(request.getType())) {
            Map<String, BigDecimal> supplyPriceMap = goodsInfoVOMap.values().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getSupplyPrice));
            adjustConsignedGoods(supplyPriceMap);
        }
    }

    /**
     * 代销商品调价
     *
     * @param supplyPriceMap
     */
    @Override
    public void adjustConsignedGoods(Map<String, BigDecimal> supplyPriceMap) {

        //遍历每一个供应商商品
        supplyPriceMap.forEach((key, value) -> {
            //查询该商品的代销
            GoodsInfoListByConditionResponse response = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().providerGoodsInfoIds(Lists.newArrayList(key)).build()).getContext();
            if (response != null && CollectionUtils.isNotEmpty(response.getGoodsInfos())) {
                List<String> goodsInfoIds = response.getGoodsInfos().stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
                queryRequest.setGoodsInfoIds(goodsInfoIds);
                queryRequest.setPageSize(goodsInfoIds.size());
                Query infoSearchQuery =
                        NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
                List<EsGoodsInfo> esGoodsInfos = esBaseService.commonPage(infoSearchQuery, EsGoodsInfo.class, EsConstants.DOC_GOODS_INFO_TYPE).getContent();
                List<IndexQuery> esGoodsInfoQuery = new ArrayList<>();
                if (esGoodsInfos != null) {
                    esGoodsInfos.forEach(esGoodsInfo -> {
                        esGoodsInfo.getGoodsInfo().setSupplyPrice(value);

                        IndexQuery iq = new IndexQuery();
                        iq.setId(esGoodsInfo.getId());
                        iq.setObject(esGoodsInfo);
                        esGoodsInfoQuery.add(iq);
                    });

                    if (CollectionUtils.isNotEmpty(esGoodsInfoQuery)) {
                        BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                        elasticsearchTemplate.bulkIndex(esGoodsInfoQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                    }
                }

                queryRequest.setQueryGoods(true);
                Query goodsSearchQuery =
                        NativeQuery.builder().withQuery(EsGoodsInfoCriteriaBuilder.newInstance().getWhereCriteria(queryRequest)).withPageable(queryRequest.getPageable()).build();
                Iterable<EsGoods> esGoodsList = esBaseService.commonPage(goodsSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
                List<IndexQuery> esGoodsQuery = new ArrayList<>();
                if (esGoodsList != null) {
                    esGoodsList.forEach(esGoods -> {
                        esGoods.getGoodsInfos().forEach(esGoodsInfo -> {
                            esGoodsInfo.setSupplyPrice(value);
                        });

                        IndexQuery iq = new IndexQuery();
                        iq.setId(esGoods.getId());
                        iq.setObject(esGoods);
                        esGoodsQuery.add(iq);
                    });
                    if (CollectionUtils.isNotEmpty(esGoodsQuery)) {
                        BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                        elasticsearchTemplate.bulkIndex(esGoodsQuery, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                    }
                }
            }
        });

    }

    /**
     * 根据skuId列表获取与之对应的起售数量Map
     * @param goodsInfoIds skuId列表
     * @param terminalSource 终端
     * @return 起售数量Map，goodsInfoId => startSaleNum
     */
    public Map<String, Long> getSkuStartSaleNumMap(List<String> goodsInfoIds, String terminalSource) {
        Map<String, Long> skuStartSaleNumMap = new HashMap<>(goodsInfoIds.size());
        // 0. 如存在营销活动，以销活动的起售数量为准，秒杀 > 限售配置
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            // PC端没有秒杀和拼团，不查询秒杀和拼团，仅查询限售配置
            if (StringUtils.isNotBlank(terminalSource) && !TerminalSource.PC.toString().equals(terminalSource)) {
                // 1. 查询正在进行的秒杀活动商品列表
                List<FlashSaleGoodsVO> flashSaleGoods = flashSaleGoodsQueryProvider.list(FlashSaleGoodsListRequest.builder()
                        .goodsinfoIds(goodsInfoIds)
                        .delFlag(DeleteFlag.NO)
                        .queryDataType(1)
                        .build()).getContext().getFlashSaleGoodsVOList();
                if (CollectionUtils.isNotEmpty(flashSaleGoods)) {
                    // 1.1 填充命中的秒杀商品起售数量
                    flashSaleGoods.forEach(flashSaleGood -> {
                        Integer minNum = flashSaleGood.getMinNum();
                        skuStartSaleNumMap.put(flashSaleGood.getGoodsInfoId(), Objects.isNull(minNum) ? 1L : (long) minNum);
                    });
                    // 1.2 排除已填充的goodsInfoIds
                    goodsInfoIds = goodsInfoIds.stream()
                            .filter(goodsInfoId -> !skuStartSaleNumMap.containsKey(goodsInfoId))
                            .collect(Collectors.toList());
                    // 1.3 全部填充完，直接返回
                    if (goodsInfoIds.size() == 0) {
                        return skuStartSaleNumMap;
                    }
                }
            }

            // 2. 查询具有限售配置的商品列表
            List<GoodsRestrictedSaleVO> restrictedSaleGoods = goodsRestrictedSaleQueryProvider.list(GoodsRestrictedSaleListRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .goodsInfoIds(goodsInfoIds)
                    .build()).getContext().getGoodsRestrictedSaleVOList();
            if(CollectionUtils.isNotEmpty(restrictedSaleGoods)) {
                // 2.1 填充命中的限售配置商品起售数量
                restrictedSaleGoods.forEach(restrictedSaleGood -> {
                    Long startSaleNum = restrictedSaleGood.getStartSaleNum();
                    skuStartSaleNumMap.put(restrictedSaleGood.getGoodsInfoId(), Objects.isNull(startSaleNum) ? 1L : startSaleNum);
                });
                // 2.2 排除已填充的goodsInfoIds
                goodsInfoIds = goodsInfoIds.stream()
                        .filter(goodsInfoId -> !skuStartSaleNumMap.containsKey(goodsInfoId))
                        .collect(Collectors.toList());
                // 2.3 全部填充完，直接返回
                if (goodsInfoIds.size() == 0) {
                    return skuStartSaleNumMap;
                }
            }
        }

        // 3. 未命中的，起售数量统一设为1
        goodsInfoIds.forEach(goodsInfoId -> skuStartSaleNumMap.put(goodsInfoId, 1L));
        return skuStartSaleNumMap;
    }

    @Override
    public EsGoodsSelectOptionsResponse selectOptions(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        EsGoodsSelectOptionsResponse selectResponse = EsGoodsSelectOptionsResponse.builder().build();
        Long cateId = queryRequest.getCateId();
        Boolean checkIsOutOfStockShow = this.checkIsOutOfStockShow();
        queryRequest = setPageRequest(builder, queryRequest, checkIsOutOfStockShow);
        SearchHits<EsGoodsInfoVO> searchHits = getSearchHits(queryRequest, builder);
        Page<EsGoodsInfoVO> pages = esBaseService.commonSearchPage(searchHits, builder.getSearchCriteria(queryRequest).getPageable());
        EsSearchResponse response = EsSearchResponse.build(searchHits, pages);

        //提取聚合数据
        EsGoodsBaseResponse baseResponse = extractBrands(response);
        selectResponse.setBrands(KsBeanUtil.convert(baseResponse.getBrands(), GoodsListBrandVO.class));
        selectResponse.setBrandMap(baseResponse.getBrandMap());
        if (queryRequest.isCateAggFlag()) {
            //提取聚合数据
            baseResponse = extractGoodsCate(response);
            selectResponse.setCateList(baseResponse.getCateList());
        }

        //提取规格与规格值聚合数据
        EsGoodsBaseResponse specResponse = extractGoodsSpecsAndSpecDetails(response);

        //提取属性
        EsGoodsBaseResponse propResponse = extractGoodsProp(response, cateId);

        //提取商品标签数据
        selectResponse.setGoodsLabelVOList(esGoodsLabelService.extractGoodsLabel(response));
        selectResponse.setGoodsSpecs(baseResponse.getGoodsSpecs());
        selectResponse.setGoodsSpecDetails(specResponse.getGoodsSpecDetails());
        selectResponse.setGoodsPropertyVOS(propResponse.getGoodsPropertyVOS());


        return selectResponse;


    }

    /**
     * 分页查询ES商品(实现WEB的商品列表)
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsSimpleResponse spuPage(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsSimpleResponse goodsResponse = EsGoodsSimpleResponse.builder().build();
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        //判断是否是三级分类页面
        Long cateId = queryRequest.getCateId();
        Boolean checkIsOutOfStockShow = this.checkIsOutOfStockShow();
        queryRequest = setPageRequest(builder, queryRequest, checkIsOutOfStockShow);

        SearchHits<EsGoodsVO> searchHits = esBaseService.commonSearchHits(builder.getSearchCriteria(queryRequest),
                EsGoodsVO.class, EsConstants.DOC_GOODS_TYPE);
        Page<EsGoodsVO> pages = esBaseService.commonSearchPage(searchHits, builder.getSearchCriteria(queryRequest).getPageable());

        EsSearchResponse response = EsSearchResponse.buildGoods(searchHits, pages);

        if (response.getGoodsData().size() < 1) {
            goodsResponse.setEsGoods(new MicroServicePage<>(response.getGoodsData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsResponse;
        }

        // 判断BOSS是否配置了无货商品不展示
        if (checkIsOutOfStockShow) {
            response.getGoodsData().forEach(goodsVo -> {
                // ES过滤ES_Goods.goodsInfos.stock不生效，因此需要手动过滤
                if (CollectionUtils.isNotEmpty(goodsVo.getGoodsInfos())) {
                    goodsVo.setGoodsInfos(goodsVo.getGoodsInfos().stream().filter(info -> info.getStock() > 0)
                            .collect(Collectors.toList()));
                }
            });
        }

        //是否过滤周期购商品
        if (Constants.no.equals(queryRequest.getIsBuyCycle())) {
            response.getGoodsData().forEach(goodsVo -> {
                if (CollectionUtils.isNotEmpty(goodsVo.getGoodsInfos())) {
                    goodsVo.setGoodsInfos(goodsVo.getGoodsInfos().stream().filter(info -> Constants.no.equals(info.getIsBuyCycle()))
                            .collect(Collectors.toList()));
                }
            });
        }

        // 过滤不展示的商品标签
        response.getGoodsData().forEach(goods -> {
            if (CollectionUtils.isNotEmpty(goods.getGoodsLabelList())) {
                List<GoodsLabelNestVO> goodsLabelList =
                        goods.getGoodsLabelList().stream()
                                .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                                .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(goodsLabelList)) {
                    goods.setGoodsLabelList(goodsLabelList);
                }
            }
        });

        List<GoodsInfoNestVO> goodsInfoVOList =
                response.getGoodsData().stream().map(EsGoodsVO::getGoodsInfos).flatMap(Collection::stream).collect(Collectors.toList());
        for (GoodsInfoNestVO goodsInfo : goodsInfoVOList) {
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());
            if (goodsInfo.getAddedFlag() ==  AddedFlag.NO.toValue()
                    || DeleteFlag.YES.equals(goodsInfo.getDelFlag())
                    || !CheckStatus.CHECKED.equals(goodsInfo.getAuditStatus())) {
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            } else if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
            }
        }

        goodsResponse.setEsGoods(new MicroServicePage<>(response.getGoodsData(), PageRequest.of(queryRequest.getPageNum(),
                queryRequest.getPageSize()), response.getTotal()));
        return goodsResponse;
    }

    /**
     * 分页查询ES商品(实现WEB的商品列表)
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoSimpleResponse skuPage(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoSimpleResponse goodsInfoResponse = EsGoodsInfoSimpleResponse.builder().build();

        EsSearchResponse response = getEsBaseInfoByParams(queryRequest);
        if (response.getData().size() < 1) {
            goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsInfoResponse;
        }

        // 过滤不展示的商品标签
        response.getData().forEach(goods -> {
            List<GoodsLabelNestVO> labels = goods.getGoodsLabelList();
            if (CollectionUtils.isNotEmpty(labels)) {
                labels = labels.stream()
                        .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                        .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                        .collect(Collectors.toList());
                goods.setGoodsLabelList(labels);
            }
        });

        List<String> skuIds =
                response.getData().stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getGoodsInfoId).distinct().collect(Collectors.toList());

//        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoQueryProvider.listByIds(
//                GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIds).build()
//        ).getContext().getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
//

        List<EsGoodsInfoVO> esGoodsInfoList = new LinkedList<>();
        Set<String> existsGoods = new HashSet<>();
        // 封装Goods
        EsGoodsInfoCriteriaBuilder builder = EsGoodsInfoCriteriaBuilder.newInstance();
        EsGoodsInfoQueryRequest request = new EsGoodsInfoQueryRequest();
        request.setGoodsInfoIds(skuIds);
        request.setQueryGoods(true);
        Query infoSearchQuery = NativeQuery.builder().withQuery(builder.getWhereCriteria(request)).withPageable(queryRequest.getPageable()).build();
        Iterable<EsGoods> esGoodsList = esBaseService.commonPage(infoSearchQuery, EsGoods.class, EsConstants.DOC_GOODS_TYPE);
        Map<String, EsGoods> esGoodsVOMap = new HashMap<>();
        if (Objects.nonNull(esGoodsList)) {
            esGoodsList.forEach(esGoods -> {
                if (!esGoodsVOMap.containsKey(esGoods.getId())) {
                    esGoodsVOMap.put(esGoods.getId(), esGoods);
                }
            });
        }
        for (EsGoodsInfoVO esGoodsInfo : response.getData()) {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            if (esGoodsVOMap.containsKey(esGoodsInfo.getGoodsId())) {
                EsGoods esGoods = esGoodsVOMap.get(esGoodsInfo.getGoodsId());
                if (Objects.nonNull(esGoods.getGoodsSalesNum()) && esGoods.getGoodsSalesNum() > 0) {
                    goodsInfo.setGoodsSalesNum(esGoods.getGoodsSalesNum());
                }
            }
            //排除分销商品，错误数据
            if (Objects.nonNull(goodsInfo.getDistributionGoodsAudit()) &&
                    goodsInfo.getDistributionGoodsAudit() == DistributionGoodsAudit.CHECKED &&
                    Objects.isNull(goodsInfo.getDistributionCommission())) {
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                continue;
            }
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());

//            goodsInfo.setStock(goodsInfoMap.getOrDefault(goodsInfo.getGoodsInfoId(), new GoodsInfoVO()).getStock());

            if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
            }
            esGoodsInfoList.add(esGoodsInfo);

            existsGoods.add(esGoodsInfo.getGoodsId());
        }
        response.setData(esGoodsInfoList);
        goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));

        return goodsInfoResponse;
    }


    /**
     * 分页查询ES商品(实现WEB的商品列表)
     * 不做任何参数处理，按照除过来的request参数进行查询，所以需要调用者自己处理参数
     * @param queryRequest
     * @return
     */
    @Override
    public EsGoodsInfoSimpleResponse skuPageByAllParams(EsGoodsInfoQueryRequest queryRequest) {
        EsGoodsInfoSimpleResponse goodsInfoResponse = EsGoodsInfoSimpleResponse.builder().build();
        queryRequest.setQueryGoods(false);

        SearchHits<EsGoodsInfoVO> searchHits = esBaseService.commonSearchHits(EsGoodsInfoCriteriaBuilder.newInstance().getSearchCriteria(queryRequest),
                EsGoodsInfoVO.class, EsConstants.DOC_GOODS_INFO_TYPE);
        Page<EsGoodsInfoVO> pages = esBaseService.commonSearchPage(searchHits, EsGoodsInfoCriteriaBuilder.newInstance().getSearchCriteria(queryRequest).getPageable());

        EsSearchResponse response = EsSearchResponse.build(searchHits, pages);
        if (response.getData().size() < 1) {
            goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));
            return goodsInfoResponse;
        }

        // 过滤不展示的商品标签
        response.getData().forEach(goods -> {
            List<GoodsLabelNestVO> labels = goods.getGoodsLabelList();
            if (CollectionUtils.isNotEmpty(labels)) {
                labels = labels.stream()
                        .filter(label -> Boolean.TRUE.equals(label.getLabelVisible()) && Objects.equals(label.getDelFlag(), DeleteFlag.NO))
                        .sorted(Comparator.comparing(GoodsLabelNestVO::getLabelSort).thenComparing(GoodsLabelNestVO::getGoodsLabelId).reversed())
                        .collect(Collectors.toList());
                goods.setGoodsLabelList(labels);
            }
        });


        List<EsGoodsInfoVO> esGoodsInfoList = new LinkedList<>();
        Set<String> existsGoods = new HashSet<>();
        for (EsGoodsInfoVO esGoodsInfo : response.getData()) {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            //排除分销商品，错误数据
            if (Objects.nonNull(goodsInfo.getDistributionGoodsAudit()) &&
                    goodsInfo.getDistributionGoodsAudit() == DistributionGoodsAudit.CHECKED &&
                    Objects.isNull(goodsInfo.getDistributionCommission())) {
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            }
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO :
                    goodsInfo.getMarketPrice());

            if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
            }
            // 验证商品上下架状态
            if (Objects.isNull(goodsInfo.getAddedFlag())
                    || goodsInfo.getAddedFlag() == AddedFlag.NO.toValue()) {
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            }

            esGoodsInfoList.add(esGoodsInfo);

            if (!existsGoods.contains(esGoodsInfo.getGoodsId())) {
                existsGoods.add(esGoodsInfo.getGoodsId());
            }
        }
        response.setData(esGoodsInfoList);
        goodsInfoResponse.setEsGoodsInfoPage(new MicroServicePage<>(response.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), response.getTotal()));

        return goodsInfoResponse;
    }

    protected EsGoodsInfoQueryRequest setPageRequest(EsGoodsInfoCriteriaBuilder builder,
                                                     EsGoodsInfoQueryRequest queryRequest, Boolean checkIsOutOfStockShow){
        //仅显示商家的
        queryRequest.setGoodsSource(GoodsSource.SELLER);
        if (StringUtils.isNotBlank(queryRequest.getKeywords())) {
            queryRequest.setKeywords(this.analyze(queryRequest.getKeywords()));
        }
        //判断是否是三级分类页面

        if (queryRequest.getCateId() != null) {
            //分类商品
            queryRequest = wrapperGoodsCateToEsGoodsInfoQueryRequest(queryRequest);
        }

        //店铺分类，加入所有子分类
        if (CollectionUtils.isNotEmpty(queryRequest.getStoreCateIds())) {
            queryRequest = wrapperStoreCateToEsGoodsInfoQueryRequest(queryRequest);
        }

        //设定排序
        if (queryRequest.getSortFlag() != null) {
            builder.setSort(queryRequest);
            //queryRequest = wrapperSortToEsGoodsInfoQueryRequest(queryRequest);
        }

        //聚合品牌
        if(Objects.isNull(queryRequest.getBrandAggFlag()) || Boolean.TRUE.equals(queryRequest.getBrandAggFlag())) {
            builder.putAgg("brand_group", AggregationBuilders.terms(g -> g.field("goodsBrand.brandId").size(100000)));
        }

        if (queryRequest.isCateAggFlag()) {
            //聚合分类
            builder.putAgg("cate_group", AggregationBuilders.terms(g -> g.field("goodsCate.cateId").size(100000)));
        }

        //聚合商品标签
        if(Objects.isNull(queryRequest.getLabelAggFlag()) || Boolean.TRUE.equals(queryRequest.getLabelAggFlag())) {
            builder.putAgg("goodsLabelList", new Aggregation.Builder().nested(g -> g.path("goodsLabelList"))
                    .aggregations("goods_label_group", e -> e.terms(d -> d.field("goodsLabelList.goodsLabelId").size(100000))).build());
        }

        //聚合属性
        if (Objects.isNull(queryRequest.getPropAggFlag()) || Boolean.TRUE.equals(queryRequest.getLabelAggFlag())) {
            //属性id+属性名称+属性排序+类目排序+输入方式+是否索引
            //属性值id+属性值名称
            String scriptStr = "doc['goodsPropRelNests.propId'] " +
                    "+'#&&#'+ doc['goodsPropRelNests.propName'] " +
                    "+'#&&#'+ doc['goodsPropRelNests.propSort'] " +
                    "+'#&&#'+ doc['goodsPropRelNests.catePropSort'] " +
                    "+'#&&#'+ doc['goodsPropRelNests.propType'] " +
                    "+'#&&#'+ doc['goodsPropRelNests.indexFlag'] ";
            Script script = Script.of(g -> g.inline(a -> a.source(scriptStr)));

            String childScriptStr = "doc['goodsPropRelNests.goodsPropDetailNest.detailId'] "
                    + "+'#&&#'+ doc['goodsPropRelNests.goodsPropDetailNest.detailNameValue'] ";
            Script childScript = Script.of(g -> g.inline(a -> a.source(childScriptStr)));

            builder.putAgg("goodsPropRelNests", new Aggregation.Builder().nested(g -> g.path("goodsPropRelNests"))
                    .aggregations("prop_group", e -> e.terms(d -> d.script(script).size(100000))
                            .aggregations("prop_detail_group", f -> f.nested(g -> g.path("goodsPropRelNests.goodsPropDetailNest"))
                                    .aggregations("prop_detail_group", h -> h.terms(i -> i.script(childScript).size(100000))))).build());
        }

        // 判断BOSS是否配置了无货商品不展示
        if (!Objects.equals(Boolean.TRUE,queryRequest.getIsMoFang()) && checkIsOutOfStockShow) {
            queryRequest.setStockFlag(Constants.yes);
            queryRequest.setIsOutOfStockShow(Constants.yes);
        }

        //渠道设置未配置或停用，不显示linkedMall商品
        this.filterLinkedMallShow(queryRequest);
        //渠道设置未配置或停用，不显示VOP商品
        this.filterVOPShow(queryRequest);
        return queryRequest;
    }

    /**
     * 判断是否开启无货不展示
     **/
    private Boolean checkIsOutOfStockShow() {
        return auditQueryProvider.isGoodsOutOfStockShow().getContext().isOutOfStockShow();
    }

    /***
     * 获得查询搜索命中
     * @param queryRequest
     * @param builder
     * @return
     */
    protected SearchHits<EsGoodsInfoVO> getSearchHits(EsGoodsInfoQueryRequest queryRequest, EsGoodsInfoCriteriaBuilder builder) {
        return esBaseService.commonSearchHits(builder.getSearchCriteria(queryRequest),
                EsGoodsInfoVO.class, EsConstants.DOC_GOODS_INFO_TYPE);
    }
}
