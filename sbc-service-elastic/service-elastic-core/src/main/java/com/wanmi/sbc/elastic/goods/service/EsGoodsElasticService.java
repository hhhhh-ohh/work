package com.wanmi.sbc.elastic.goods.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsInitRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.goods.model.root.*;
import com.wanmi.sbc.elastic.pointsgoods.serivce.EsPointsGoodsService;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodspropcaterel.GoodsPropCateRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodspropertydetail.GoodsPropertyDetailQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodspropertydetailrel.GoodsPropertyDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsCustomerPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsLevelPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateGoodsRelaQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsPageByConditionRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelQueryRequest;
import com.wanmi.sbc.goods.api.request.goodsproperty.GoodsPropertyListRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailListRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsCountByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsCustomerPriceBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceListBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsLevelPriceBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateGoodsRelaListByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsListByIdsResponse;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelByIdResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyListResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailListResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelListResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingListByStoreIdsRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponListRequest;
import com.wanmi.sbc.marketing.bean.vo.DistributionStoreSettingVO;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.setting.api.provider.country.PlatformCountryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressListResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformCountryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ES商品信息数据源操作
 * Created by daiyitian on 2017/4/21.
 */
@Slf4j
@Primary
@Service
public class EsGoodsElasticService implements EsGoodsElasticServiceInterface{

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsIntervalPriceQueryProvider goodsIntervalPriceQueryProvider;

    @Autowired
    private GoodsLevelPriceQueryProvider goodsLevelPriceQueryProvider;

    @Autowired
    private GoodsCustomerPriceQueryProvider goodsCustomerPriceQueryProvider;

    @Autowired
    private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

    @Autowired
    private DistributionSettingQueryProvider distributionSettingQueryProvider;

    @Autowired
    private EsGoodsLabelService esGoodsLabelService;

    @Autowired
    private EsPointsGoodsService esPointsGoodsService;

    @Autowired
    private GoodsPropertyDetailRelQueryProvider goodsPropertyDetailRelQueryProvider;

    @Autowired
    private GoodsPropertyQueryProvider goodsPropertyQueryProvider;

    @Autowired
    private GoodsPropertyDetailQueryProvider goodsPropertyDetailQueryProvider;

    @Autowired
    private GoodsPropCateRelQueryProvider goodsPropCateRelQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private PlatformCountryProvider platformCountryProvider;

    @Autowired
    protected EsBaseService esBaseService;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @WmResource("mapping/esGoods.json")
    private Resource esGoodsMapping;

    @WmResource("mapping/esGoodsInfo.json")
    private Resource esSkuMapping;

    private static final int SIZE = 1000;

    /**
     * 初始化SPU持化于ES
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initEsGoods(EsGoodsInfoRequest request) {
        // 清理-重建索引
        clearAndRebuildIndex(request);

        if (request.getGoodsIds() == null) {
            request.setGoodsIds(new ArrayList<>());
        }

        if (StringUtils.isNotBlank(request.getGoodsId())) {
            request.getGoodsIds().add(request.getGoodsId());
        }

        if (CollectionUtils.isNotEmpty(request.getSkuIds())) {
            int totalSize = request.getSkuIds().size();
            int i = 1;
            List<String> firstRequestSkuIds = request.getSkuIds();
            List<String> secondRequestSkuIds = Lists.newArrayList();
            List<String> totalGoodsIds = Lists.newArrayList();
            // 商品导入数量太大，分两批操作
            if (totalSize > SIZE){
                Iterator<String> it = firstRequestSkuIds.iterator();
                while(it.hasNext()){
                    String x = it.next();
                    secondRequestSkuIds.add(x);
                    it.remove();
                    // 每次处理1000条
                    if(i++ % 1000 == 0){
                        queryGoodsIds(request, secondRequestSkuIds, totalGoodsIds);
                        secondRequestSkuIds = Lists.newArrayList();
                    }
                }
                if (CollectionUtils.isNotEmpty(secondRequestSkuIds)) {
                    queryGoodsIds(request, secondRequestSkuIds, totalGoodsIds);
                }
            } else {
                queryGoodsIds(request, firstRequestSkuIds, totalGoodsIds);
            }

            request.getGoodsIds().addAll(totalGoodsIds);
            if (CollectionUtils.isEmpty(request.getGoodsIds())) {
                return;
            }
        }
        // 批量查询所有SPU信息列表
        GoodsCountByConditionRequest goodsCountQueryRequest = getGoodsQueryRequest(request);
        Long totalCount = goodsQueryProvider.countByCondition(goodsCountQueryRequest).getContext().getCount();

        if (totalCount <= 0) {
            return;
        }

        log.info("商品所有索引开始");
        long startTime = System.currentTimeMillis();

        /*
         * 一个ID因采用uuid有32位字符串，目前mysql的SQL语句最大默认限制1M，通过mysql的配置文件（my.ini）中的max_allowed_packet来调整
         * 每批查询2000个GoodsID，根据jvm内存、服务请求超时时间来综合考虑调整。
         */
        Integer pageSize = 1000;
        //控制查询数量，防止内存溢出
        if(request.getPageSize() != null && request.getPageSize() < pageSize){
            pageSize = request.getPageSize();
        }

        long pageCount = totalCount / pageSize;
        long m = totalCount % pageSize;
        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        }
        Map<Long, StoreVO> storeMap = new HashMap<>();
        Map<Long, StoreVO> providerStoreMap = new HashMap<>();
        Map<String, GoodsInfoVO> providerGoodsInfoVOMap = new HashMap<>();
        Map<String, GoodsVO> providerGoodsVOMap = new HashMap<>();
        Map<String, DefaultFlag> distributionStoreSettingMap = new HashMap<>();
        Map<String, List<GoodsLevelPriceNest>> levelPriceMap = new HashMap<>();
        Map<String, List<GoodsCustomerPriceNest>> customerPriceMap = new HashMap<>();
        Map<String, List<GoodsIntervalPriceVO>> intervalPriceMap = new HashMap<>();
        Map<String, String> goodsInfoSpecDetailMap = new HashMap<>();
        Map<String, List<GoodsPropRelNest>> goodsPropDetailMap = new HashMap<>();
        Map<String, List<Long>> storeCateGoodsMap = new HashMap<>();
        Map<Long, String> electronicCouponMap = new HashMap<>();

        Map<Long, GoodsCateVO> goodsCateMap = goodsCateQueryProvider.listByCondition(new GoodsCateListByConditionRequest())
                .getContext().getGoodsCateVOList().stream().collect(Collectors.toMap(GoodsCateVO::getCateId, Function.identity()));

        Map<Long, GoodsLabelNest> labelMap = esGoodsLabelService.getLabelMapNoCache();

        GoodsPageByConditionRequest goodsPageRequest = new GoodsPageByConditionRequest();
        KsBeanUtil.copyPropertiesThird(goodsCountQueryRequest, goodsPageRequest);
        goodsPageRequest.setDelFlag(DeleteFlag.NO.toValue());
        goodsPageRequest.setGoodsIds(goodsCountQueryRequest.getGoodsIds());

        int errorThrow = 0;//满10次，退出循环往上抛异常
        int pageIndex = 0; //开始位置
        if (request.getPageNum() != null) {
            pageIndex = request.getPageNum();
        }
        for (int i = pageIndex; i < pageCount; i++) {
            try {
                goodsPageRequest.setPageNum(i);
                goodsPageRequest.setPageSize(pageSize);
                List<GoodsVO> goodsList = goodsQueryProvider.pageByCondition(goodsPageRequest).getContext().getGoodsPage().getContent();
                if (CollectionUtils.isNotEmpty(goodsList)) {
                    List<String> goodsIds = goodsList.stream().map(GoodsVO::getGoodsId).collect(Collectors.toList());
                    GoodsInfoListByConditionRequest infoQueryRequest = new GoodsInfoListByConditionRequest();
                    infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
                    infoQueryRequest.setGoodsIds(goodsIds);
                    infoQueryRequest.setShowPointFlag(Boolean.TRUE);
                    List<GoodsInfoVO> goodsinfos = goodsInfoQueryProvider.listByCondition(infoQueryRequest).getContext().getGoodsInfos();
                    if (CollectionUtils.isEmpty(goodsinfos)) {
                        continue;
                    }
                    List<String> skuIds = goodsinfos.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                    List<Long> electronicCouponIds = goodsinfos.stream()
                            .map(GoodsInfoVO::getElectronicCouponsId)
                            .filter(Objects::nonNull).collect(Collectors.toList());

                    List<String> providerGoodsInfoIds = goodsinfos.stream().map(GoodsInfoVO::getProviderGoodsInfoId).filter(Objects::nonNull).collect(Collectors.toList());
                    providerGoodsInfoIds = providerGoodsInfoIds.stream()
                            .filter(v -> !providerGoodsInfoVOMap.containsKey(v)) //仅提取不存在map的
                            .distinct()
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
                        GoodsInfoListByConditionRequest providerInfoQueryRequest = new GoodsInfoListByConditionRequest();
                        providerInfoQueryRequest.setGoodsInfoIds(providerGoodsInfoIds);
                        GoodsInfoListByIdsResponse goodsInfoListByIdsResponse = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(providerGoodsInfoIds).build()).getContext();
                        if (goodsInfoListByIdsResponse != null && CollectionUtils.isNotEmpty(goodsInfoListByIdsResponse.getGoodsInfos())) {
                            List<GoodsInfoVO> providerGoodsinfos = goodsInfoListByIdsResponse.getGoodsInfos();
                            //供应商map
                            providerGoodsInfoVOMap.putAll(providerGoodsinfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity())));
                        }
                    }

                    List<Long> brandIds = goodsList.stream().filter(s -> Objects.nonNull(s.getBrandId())).map(GoodsVO::getBrandId).collect(Collectors.toList());
                    Map<Long, GoodsBrandVO> goodsBrandMap = new HashMap<>();
                    if (CollectionUtils.isNotEmpty(brandIds)) {
                        goodsBrandMap.putAll(goodsBrandQueryProvider.list(GoodsBrandListRequest.builder().brandIds(brandIds).build())
                                .getContext().getGoodsBrandVOList().stream().collect(Collectors.toMap(GoodsBrandVO::getBrandId,
                                        Function.identity())));
                    }

                    List<String> providerGoodsIds = goodsList.stream().map(GoodsVO::getProviderGoodsId).filter(Objects::nonNull).collect(Collectors.toList());
                    providerGoodsIds = providerGoodsIds.stream()
                            .filter(v -> !providerGoodsVOMap.containsKey(v)) //仅提取不存在map的
                            .distinct()
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(providerGoodsIds)) {
                        GoodsListByIdsResponse providerGoodsListByIdsResponse = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder().goodsIds(providerGoodsIds).build()).getContext();
                        if (providerGoodsListByIdsResponse != null && CollectionUtils.isNotEmpty(providerGoodsListByIdsResponse.getGoodsVOList())) {
                            List<GoodsVO> providerGoods = providerGoodsListByIdsResponse.getGoodsVOList();
                            //供应商map
                            providerGoodsVOMap.putAll(providerGoods.stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity())));
                        }
                    }

                    //区间价Map
                    intervalPriceMap.putAll(getIntervalPriceMapBySkuId(skuIds));

                    //等级价Map
                    levelPriceMap.putAll(goodsLevelPriceQueryProvider.listBySkuIds(new GoodsLevelPriceBySkuIdsRequest(skuIds))
                            .getContext().getGoodsLevelPriceList()
                            .stream().map(price -> KsBeanUtil.convert(price, GoodsLevelPriceNest.class))
                            .collect(Collectors.groupingBy(GoodsLevelPriceNest::getGoodsInfoId)));

                    //客户价Map
                    customerPriceMap.putAll(goodsCustomerPriceQueryProvider.listBySkuIds(new GoodsCustomerPriceBySkuIdsRequest(skuIds))
                            .getContext().getGoodsCustomerPriceVOList().stream()
                            .map(price -> KsBeanUtil.convert(price, GoodsCustomerPriceNest.class))
                            .collect(Collectors.groupingBy(GoodsCustomerPriceNest::getGoodsInfoId)));


                    //规格值Map
                    goodsInfoSpecDetailMap.putAll(goodsInfoSpecDetailRelQueryProvider.listBySkuIds(new GoodsInfoSpecDetailRelBySkuIdsRequest(skuIds))
                            .getContext().getGoodsInfoSpecDetailRelVOList().stream()
                            .filter(v -> StringUtils.isNotBlank(v.getDetailName()))
                            .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (a, b) -> a.concat(" ").concat(b))));

                    //属性值Map
                    goodsPropDetailMap.putAll(getPropDetailRelList(goodsIds));

                    //商品店铺分类Map
                    storeCateGoodsMap.putAll(storeCateGoodsRelaQueryProvider.listByGoodsIds(new StoreCateGoodsRelaListByGoodsIdsRequest(goodsIds)).getContext().getStoreCateGoodsRelaVOList().stream()
                            .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId, Collectors.mapping(StoreCateGoodsRelaVO::getStoreCateId, Collectors.toList()))));

                    //电子卡券
                    if (CollectionUtils.isNotEmpty(electronicCouponIds)) {
                        electronicCouponMap.putAll(electronicCouponQueryProvider.list(ElectronicCouponListRequest.builder().idList(electronicCouponIds).build())
                                .getContext().getElectronicCouponVOList().stream()
                                .collect(Collectors.toMap(ElectronicCouponVO::getId, ElectronicCouponVO::getCouponName)));
                    }

                    List<Long> storeIds = goodsList.stream().map(GoodsVO::getStoreId).filter(Objects::nonNull)
                            .filter(v -> !storeMap.containsKey(v)) //仅提取不存在map的store
                            .distinct()
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(storeIds)) {
                        storeMap.putAll(storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(storeIds))
                                .getContext().getStoreVOList().stream()
                                .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity())));
                        List<String> stringList = storeIds.stream().distinct().map(String::valueOf).collect(Collectors.toList());
                        distributionStoreSettingMap.putAll(distributionSettingQueryProvider.listByStoreIds(new DistributionStoreSettingListByStoreIdsRequest(stringList)).getContext().getList().stream().collect(Collectors.toMap(DistributionStoreSettingVO::getStoreId, DistributionStoreSettingVO::getOpenFlag)));
                    }
                    List<Long> providerStoreIds = goodsList.stream().map(GoodsVO::getProviderId).filter(Objects::nonNull)
                            .filter(v -> !providerStoreMap.containsKey(v)) //仅提取不存在map的store
                            .distinct()
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(providerStoreIds)) {
                        providerStoreMap.putAll(Objects.requireNonNull(storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(providerStoreIds))
                                .getContext().getStoreVOList()).stream()
                                .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity())));
                    }


                    //遍历SKU，填充SPU、图片
                    List<IndexQuery> esGoodsList = new ArrayList<>();
                    List<IndexQuery> esSkuList = new ArrayList<>();
                    goodsList.forEach(goods -> {
                        EsGoods esGoods = new EsGoods();
                        esGoods.setId(goods.getGoodsId());
                        esGoods.setVendibilityStatus(buildGoodsVendibility(goods, providerStoreMap, providerGoodsVOMap));
                        esGoods.setAddedTime(goods.getAddedTime());
                        esGoods.setGoodsSubtitle(goods.getGoodsSubtitle());
                        esGoods.setLowGoodsName(StringUtils.lowerCase(goods.getGoodsName()));
                        esGoods.setPinyinGoodsName(esGoods.getLowGoodsName());
                        esGoods.setGoodsUnit(goods.getGoodsUnit());
                        esGoods.setLinePrice(goods.getLinePrice());
                        esGoods.setAuditStatus(goods.getAuditStatus().toValue());
                        esGoods.setSortNo(goods.getSortNo());
                        esGoods.setGoodsInfos(new ArrayList<>());
                        esGoods.setThirdPlatformType(goods.getThirdPlatformType());
                        esGoods.setStoreId(goods.getStoreId());
                        esGoods.setCompanyInfoId(goods.getCompanyInfoId());
                        esGoods.setProviderId(goods.getProviderId());
                        esGoods.setRealGoodsSalesNum(goods.getGoodsSalesNum());
                        esGoods.setGoodsNo(goods.getGoodsNo());
                        esGoods.setAddedFlag(goods.getAddedFlag());
                        esGoods.setGoodsSource(goods.getGoodsSource());
                        esGoods.setGoodsType(goods.getGoodsType());
                        esGoods.setStock(goods.getStock());
                        esGoods.setCreateTime(goods.getCreateTime());
                        esGoods.setGoodsName(goods.getGoodsName());
                        esGoods.setProviderGoodsId(goods.getProviderGoodsId());
                        esGoods.setFreightTempId(goods.getFreightTempId());
                        esGoods.setIsBuyCycle(goods.getIsBuyCycle());
                        //填充商品销量
                        Long shamSalesNum = goods.getShamSalesNum() == null ? Long.valueOf(0L) : goods.getShamSalesNum();
                        esGoods.setGoodsSalesNum(goods.getGoodsSalesNum() == null ? shamSalesNum : goods.getGoodsSalesNum() + shamSalesNum);
                        //填充商品收藏量
                        esGoods.setGoodsCollectNum(goods.getGoodsCollectNum() == null ? Long.valueOf(0L) : goods.getGoodsCollectNum());
                        //填充商品评论数
                        esGoods.setGoodsEvaluateNum(goods.getGoodsEvaluateNum() == null ? Long.valueOf(0L) : goods.getGoodsEvaluateNum());
                        //填充商品好评数
                        esGoods.setGoodsFavorableCommentNum(goods.getGoodsFavorableCommentNum() == null ? Long.valueOf(0L) :
                                goods.getGoodsFavorableCommentNum());
                        //填充扩展信息
                        esGoods.setPluginType(goods.getPluginType());
                        esGoods.setExtendedAttributes(goods.getExtendedAttributes());
                        esGoods.setStoreType(goods.getStoreType());
                        //填充好评率数据
                        Long goodsFeedbackRate = 0L;
                        if (Objects.nonNull(goods.getGoodsEvaluateNum()) && Objects.nonNull(goods.getGoodsFavorableCommentNum())
                                && goods.getGoodsEvaluateNum() > 0L) {
                            goodsFeedbackRate =
                                    (long) ((double) goods.getGoodsFavorableCommentNum() / (double) goods.getGoodsEvaluateNum() * 100);
                        }
                        esGoods.setGoodsFeedbackRate(goodsFeedbackRate);

                        //分配属性值
                        if (goodsPropDetailMap.containsKey(goods.getGoodsId())) {
                            esGoods.setGoodsPropRelNests(goodsPropDetailMap.get(goods.getGoodsId()).stream().distinct().collect(Collectors.toList()));
                        }
                        //设置spu的分类和品牌
                        GoodsCateVO goodsCate = goodsCateMap.get(goods.getCateId());
                        GoodsBrandVO goodsBrand = new GoodsBrandVO();
                        goodsBrand.setBrandId(0L);
                        if (goods.getBrandId() != null) {
                            goodsBrand = goodsBrandMap.get(goods.getBrandId());
                        }
                        EsCateBrand esCateBrand = this.putEsCateBrand(goodsCate, goodsBrand);
                        if (Objects.nonNull(esCateBrand.getGoodsBrand())) {
                            esGoods.setGoodsBrand(esCateBrand.getGoodsBrand());
                        }

                        if (Objects.nonNull(esCateBrand.getGoodsCate())) {
                            esGoods.setGoodsCate(esCateBrand.getGoodsCate());
                        }
                        //填充签约有效期时间
                        if (MapUtils.isNotEmpty(storeMap) && storeMap.containsKey(goods.getStoreId())) {
                            StoreVO store = storeMap.get(goods.getStoreId());
                            esGoods.setContractStartDate(store.getContractStartDate());
                            esGoods.setContractEndDate(store.getContractEndDate());
                            esGoods.setStoreState(store.getStoreState().toValue());
                            //店铺名称和商家名称
                            esGoods.setStoreName(store.getStoreName());
                            esGoods.setSupplierName(store.getSupplierName());
                        }

                        //填充供应商名称
                        if (Objects.nonNull(esGoods.getProviderId()) && providerStoreMap.containsKey(esGoods.getProviderId())) {
                            esGoods.setProviderName(providerStoreMap.get(goods.getProviderId()).getSupplierName());
                        }

                        //获取店铺等级
                        if (storeCateGoodsMap.containsKey(goods.getGoodsId())) {
                            esGoods.setStoreCateIds(storeCateGoodsMap.get(goods.getGoodsId())
                                    .stream().distinct().collect(Collectors.toList()));
                        }

                        if (MapUtils.isNotEmpty(distributionStoreSettingMap) && distributionStoreSettingMap.containsKey(goods.getStoreId().toString())) {
                            esGoods.setDistributionGoodsStatus(distributionStoreSettingMap.get(goods.getStoreId().toString()) == DefaultFlag.NO ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO);
                        }

                        //获取商品标签
                        esGoods.setGoodsLabelList(Collections.emptyList());
                        if (StringUtils.isNotBlank(goods.getLabelIdStr()) && MapUtils.isNotEmpty(labelMap)) {
                            String[] labelIds = goods.getLabelIdStr().split(",");
                            esGoods.setGoodsLabelList(Arrays.stream(labelIds).map(NumberUtils::toLong)
                                    .filter(labelMap::containsKey).map(labelMap::get).collect(Collectors.toList()));
                        }

                        Map<String, GoodsInfoNest> firstSkuBySpuId = new HashMap<>();
                        goodsinfos.stream().filter(goodsInfoVO -> goods.getGoodsId().equals(goodsInfoVO.getGoodsId()))
                                .forEach(goodsInfoVO -> {
                                    GoodsInfoNest goodsInfoNest = KsBeanUtil.convert(goodsInfoVO, GoodsInfoNest.class);
                                    goodsInfoNest.setCateId(goods.getCateId());
                                    goodsInfoNest.setBrandId(goods.getBrandId());
                                    goodsInfoNest.setPriceType(goods.getPriceType());
                                    goodsInfoNest.setCompanyType(goods.getCompanyType());
                                    goodsInfoNest.setGoodsCubage(goodsInfoVO.getGoodsCubage());
                                    goodsInfoNest.setGoodsWeight(goodsInfoVO.getGoodsWeight());
                                    goodsInfoNest.setEnterPriseAuditStatus(goodsInfoVO.getEnterPriseAuditState() != null ?
                                            goodsInfoVO.getEnterPriseAuditState().toValue() : EnterpriseAuditState.INIT.toValue());
                                    goodsInfoNest.setGoodsStatus(GoodsStatus.OK);
                                    // sku维度的使用真实销量
                                    goodsInfoNest.setGoodsSalesNum(esGoods.getRealGoodsSalesNum());
                                    goodsInfoNest.setGoodsCollectNum(esGoods.getGoodsCollectNum());
                                    goodsInfoNest.setGoodsEvaluateNum(esGoods.getGoodsEvaluateNum());
                                    goodsInfoNest.setGoodsFavorableCommentNum(esGoods.getGoodsFavorableCommentNum());
                                    goodsInfoNest.setGoodsFeedbackRate(esGoods.getGoodsFeedbackRate());
                                    goodsInfoNest.setVendibilityStatus(buildGoodsInfoVendibility(goodsInfoVO, providerStoreMap, providerGoodsInfoVOMap));
                                    goodsInfoNest.setProviderStatus(goodsInfoVO.getProviderStatus() == null ? Constants.yes : goodsInfoVO.getProviderStatus());
                                    goodsInfoNest.setSupplyPrice(goodsInfoVO.getSupplyPrice());
                                    goodsInfoNest.setStoreId(esGoods.getStoreId());
                                    goodsInfoNest.setStoreName(esGoods.getStoreName());
                                    goodsInfoNest.setExtendedAttributes(goodsInfoVO.getExtendedAttributes());
                                    goodsInfoNest.setStoreType(esGoods.getStoreType());
                                    goodsInfoNest.setGoodsSubtitle(goods.getGoodsSubtitle());
                                    goodsInfoNest.setGoodsUnit(esGoods.getGoodsUnit());
                                    goodsInfoNest.setFreightTempId(goods.getFreightTempId());
                                    if (Objects.isNull(goodsInfoNest.getDistributionCreateTime())) {
                                        goodsInfoNest.setDistributionCreateTime(goodsInfoVO.getCreateTime());
                                    }
                                    if (StringUtils.isNotBlank(goodsInfoVO.getProviderGoodsInfoId())) {
                                        GoodsInfoVO providerGoodsInfoVO = providerGoodsInfoVOMap.get(goodsInfoVO.getProviderGoodsInfoId());
                                        if (providerGoodsInfoVO != null) {
                                            goodsInfoNest.setStock(providerGoodsInfoVO.getStock());
                                            goodsInfoNest.setProviderQuickOrderNo(providerGoodsInfoVO.getQuickOrderNo());
                                        }
                                    }
                                    //填充规格值
                                    if (Constants.yes.equals(goods.getMoreSpecFlag())) {
                                        goodsInfoNest.setSpecText(goodsInfoSpecDetailMap.get(goodsInfoVO.getGoodsInfoId()));
                                    }

                                    //为空，则以商品主图
                                    if (StringUtils.isEmpty(goodsInfoNest.getGoodsInfoImg())) {
                                        goodsInfoNest.setGoodsInfoImg(goods.getGoodsImg());
                                    }

                                    //区间价
                                    if (CollectionUtils.isNotEmpty(intervalPriceMap.get(goodsInfoVO.getGoodsInfoId()))) {
                                        List<BigDecimal> prices = intervalPriceMap.get(goodsInfoVO.getGoodsInfoId()).stream().map(GoodsIntervalPriceVO::getPrice).filter(Objects::nonNull).collect(Collectors.toList());
                                        goodsInfoNest.setIntervalMinPrice(prices.stream().filter(Objects::nonNull).min(BigDecimal::compareTo).orElseGet(goodsInfoVO::getMarketPrice));
                                        goodsInfoNest.setIntervalMaxPrice(prices.stream().filter(Objects::nonNull).max(BigDecimal::compareTo).orElseGet(goodsInfoVO::getMarketPrice));
                                    }

                                    if (goodsInfoNest.getBuyPoint() == null) {
                                        goodsInfoNest.setBuyPoint(NumberUtils.LONG_ZERO);
                                    }

                                    goodsInfoNest.setAttributeSize(goodsInfoVO.getAttributeSize());
                                    goodsInfoNest.setAttributeSeason(goodsInfoVO.getAttributeSeason());
                                    goodsInfoNest.setAttributeGoodsType(goodsInfoVO.getAttributeGoodsType());
                                    goodsInfoNest.setAttributeSaleType(goodsInfoVO.getAttributeSaleType());
                                    goodsInfoNest.setAttributeSaleRegion(goodsInfoVO.getAttributeSaleRegion());
                                    goodsInfoNest.setAttributeSchoolSection(goodsInfoVO.getAttributeSchoolSection());
                                    goodsInfoNest.setAttributePriceSilver(goodsInfoVO.getAttributePriceSilver());
                                    goodsInfoNest.setAttributePriceGold(goodsInfoVO.getAttributePriceGold());
                                    goodsInfoNest.setAttributePriceDiamond(goodsInfoVO.getAttributePriceDiamond());
                                    goodsInfoNest.setAttributePriceDiscount(goodsInfoVO.getAttributePriceDiscount());


                                    goodsInfoNest.setEsSortPrice();
                                    goodsInfoNest.setGoodsType(esGoods.getGoodsType());
                                    goodsInfoNest.setElectronicCouponsId(goodsInfoVO.getElectronicCouponsId());
                                    goodsInfoNest.setElectronicCouponsName(electronicCouponMap.get(goodsInfoVO.getElectronicCouponsId()));
                                    if (!firstSkuBySpuId.containsKey(goodsInfoNest.getGoodsId())) {
                                        firstSkuBySpuId.put(goodsInfoNest.getGoodsId(), goodsInfoNest);
                                    }

                                    //SKU索引
                                    EsGoodsInfo esGoodsInfo = new EsGoodsInfo();
                                    esGoodsInfo.setId(goodsInfoNest.getGoodsInfoId());
                                    esGoodsInfo.setGoodsId(goodsInfoNest.getGoodsId());
                                    if (Objects.nonNull(esCateBrand.getGoodsCate())) {
                                        esGoodsInfo.setGoodsCate(esCateBrand.getGoodsCate());
                                    }
                                    if (Objects.nonNull(esCateBrand.getGoodsBrand())) {
                                        esGoodsInfo.setGoodsBrand(esCateBrand.getGoodsBrand());
                                    }
                                    esGoodsInfo.setGoodsInfo(goodsInfoNest);
                                    esGoodsInfo.setGoodsSubtitle(esGoods.getGoodsSubtitle());
                                    esGoodsInfo.setAddedTime(goodsInfoNest.getAddedTime());
                                    //上面有setGoodsInfo，这里不用重新set
//                                    esGoodsInfo.setGoodsInfo(goodsInfoNest);
                                    esGoodsInfo.setGoodsLevelPrices(levelPriceMap.get(goodsInfoNest.getGoodsInfoId()));
                                    esGoodsInfo.setCustomerPrices(customerPriceMap.get(goodsInfoNest.getGoodsInfoId()));
                                    esGoodsInfo.setLowGoodsName(esGoods.getLowGoodsName());
                                    esGoodsInfo.setPinyinGoodsName(esGoods.getLowGoodsName());
                                    esGoodsInfo.setGoodsUnit(esGoods.getGoodsUnit());
                                    esGoodsInfo.setLinePrice(esGoods.getLinePrice());
                                    esGoodsInfo.setPropDetailIds(esGoods.getPropDetailIds());
                                    esGoodsInfo.setContractStartDate(esGoods.getContractStartDate());
                                    esGoodsInfo.setContractEndDate(esGoods.getContractEndDate());
                                    esGoodsInfo.setStoreState(esGoods.getStoreState());
                                    esGoodsInfo.setStoreCateIds(esGoods.getStoreCateIds());
                                    esGoodsInfo.setAuditStatus(esGoods.getAuditStatus());
                                    esGoodsInfo.setDistributionGoodsStatus(esGoods.getDistributionGoodsStatus());
                                    esGoodsInfo.setVendibilityStatus(buildGoodsInfoVendibility(goodsInfoVO, providerStoreMap, providerGoodsInfoVOMap));
                                    esGoodsInfo.setGoodsLabelList(esGoods.getGoodsLabelList());
                                    esGoodsInfo.setGoodsSource(esGoods.getGoodsSource());
                                    esGoodsInfo.setGoodsNo(esGoods.getGoodsNo());
                                    esGoodsInfo.setGoodsName(esGoods.getGoodsName());
                                    esGoodsInfo.setGoodsPropRelNests(esGoods.getGoodsPropRelNests());
                                    esGoodsInfo.setStoreType(esGoods.getStoreType());
                                    esGoodsInfo.setGoodsType(esGoods.getGoodsType());
                                    esGoodsInfo.setElectronicCouponsId(goodsInfoNest.getElectronicCouponsId());
                                    esGoodsInfo.setElectronicCouponsName(goodsInfoNest.getElectronicCouponsName());
                                    esGoodsInfo.setIsBuyCycle(goodsInfoNest.getIsBuyCycle());
                                    esGoodsInfo.setQuickOrderNo(goodsInfoNest.getQuickOrderNo());
                                    esGoodsInfo.setAttributeSize(goodsInfoNest.getAttributeSize());
                                    esGoodsInfo.setAttributeSeason(goodsInfoNest.getAttributeSeason());
                                    esGoodsInfo.setAttributeGoodsType(goodsInfoNest.getAttributeGoodsType());
                                    esGoodsInfo.setAttributeSaleType(goodsInfoNest.getAttributeSaleType());
                                    esGoodsInfo.setAttributeSaleRegion(goodsInfoNest.getAttributeSaleRegion());
                                    esGoodsInfo.setAttributeSchoolSection(goodsInfoNest.getAttributeSchoolSection());
                                    esGoodsInfo.setAttributePriceSilver(goodsInfoNest.getAttributePriceSilver());
                                    esGoodsInfo.setAttributePriceGold(goodsInfoNest.getAttributePriceGold());
                                    esGoodsInfo.setAttributePriceDiamond(goodsInfoNest.getAttributePriceDiamond());
                                    esGoodsInfo.setAttributePriceDiscount(goodsInfoNest.getAttributePriceDiscount());


                                    esGoods.getGoodsInfos().add(goodsInfoNest);
                                    IndexQuery iq = new IndexQuery();
                                    iq.setObject(esGoodsInfo);
                                    esSkuList.add(iq);
                                });
                        //取价格最低KU的信息
                        if (CollectionUtils.isNotEmpty(esGoods.getGoodsInfos())) {
                            List<GoodsInfoNest> esGoodsInfos
                                    = esGoods.getGoodsInfos().stream()
                                        .sorted(Comparator.comparing(GoodsInfoNest::getEsSortPrice))
                                        .collect(Collectors.toList());
                            GoodsInfoNest goodsInfoNest = esGoodsInfos.get(0);
                            esGoods.setEsSortPrice(goodsInfoNest.getEsSortPrice());
                            esGoods.setBuyPoint(goodsInfoNest.getBuyPoint());
                        }
                        IndexQuery iq = new IndexQuery();
                        iq.setObject(esGoods);
                        esGoodsList.add(iq);
                    });

                    esGoodsList.forEach(
                            g -> {
                                EsGoods esGoods = (EsGoods) g.getObject();
                                if (esGoods != null && esGoods.getGoodsPropRelNests() != null){
                                    esGoods.getGoodsPropRelNests()
                                            .forEach(
                                                    x -> {
                                                        if (x.getGoodsPropDetailNest() != null){
                                                            x.getGoodsPropDetailNest()
                                                                    .forEach(q -> System.out.println(q.getDetailName()));
                                                        }

                                                    });
                                }
                            });

                    // 将商品对象保存到ES
                    saveGoods2Es(esGoodsList, esSkuList);

                    // 清空缓存
                    intervalPriceMap.clear();
                    goodsInfoSpecDetailMap.clear();
                    goodsPropDetailMap.clear();
                    levelPriceMap.clear();
                    customerPriceMap.clear();
                    storeCateGoodsMap.clear();
                }
            } catch (Exception e) {
                log.error("初始化ES商品页码位置".concat(String.valueOf(i)).concat("，异常："), e);
                errorThrow++;
                if (errorThrow >= 10) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{i});
                }
                i--;
            } catch (Throwable t) {
                log.error("初始化ES商品页码位置".concat(String.valueOf(i)).concat("，异常："), t);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{i});
            }
        }

        log.info("商品所有索引结束->花费{}毫秒", (System.currentTimeMillis() - startTime));

        // 异步同步积分商品ES
        initPointGoodsWithAsync(request);
    }

    private void queryGoodsIds(EsGoodsInfoRequest request, List<String> requestSkuIds, List<String> totalGoodsIds) {
        //批量查询所有SKU信息列表
        GoodsInfoListByConditionRequest infoQueryRequest = new GoodsInfoListByConditionRequest();
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        infoQueryRequest.setCompanyInfoId(request.getCompanyInfoId());
        infoQueryRequest.setGoodsInfoIds(requestSkuIds);
        infoQueryRequest.setStoreId(request.getStoreId());
        infoQueryRequest.setGoodsIds(request.getGoodsIds());
        infoQueryRequest.setBrandIds(request.getBrandIds());
        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.listByCondition(infoQueryRequest).getContext().getGoodsInfos();
        List<String> goodsIds = goodsInfos.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            totalGoodsIds.addAll(goodsIds);
        }
    }

    /**
     * 更新店铺信息
     *
     * @param request 更新入参
     */
    @Async
    @Override
    public void updateStoreStateByStoreId(EsGoodsStoreInfoModifyRequest request) {
        if (Objects.nonNull(request.getContractStartDate()) || Objects.nonNull(request.getContractEndDate())
                || Objects.nonNull(request.getStoreState())) {
            StringBuilder sbr = new StringBuilder();
            if (Objects.nonNull(request.getContractStartDate())) {
                sbr.append("ctx._source.contractStartDate='")
                        .append(DateUtil.format(request.getContractStartDate(), DateUtil.FMT_TIME_4)).append("';");
            }
            if (Objects.nonNull(request.getContractEndDate())) {
                sbr.append("ctx._source.contractEndDate='")
                        .append(DateUtil.format(request.getContractEndDate(), DateUtil.FMT_TIME_4)).append("';");
            }
            if (Objects.nonNull(request.getStoreState())) {
                sbr.append("ctx._source.storeState=").append(request.getStoreState().toValue()).append(';');
            }
//            Script script = new Script(sbr.toString());
//
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
//            updateByQueryRequest.setQuery(QueryBuilders.termQuery("goodsInfo.storeId", request.getStoreId()));
//            updateByQueryRequest.setScript(script);
//            updateByQueryRequest.setAbortOnVersionConflict(false);
            try {
//                BulkByScrollResponse response = restHighLevelClient.updateByQuery(updateByQueryRequest,
//                        RequestOptions.DEFAULT);
//                if (CollectionUtils.isNotEmpty(response.getBulkFailures())) {
//                    response.getBulkFailures()
//                            .forEach(f -> log.error("店铺信息更新es_goods_info，storeId:{} ===> 更新异常:{}",
//                                    request.getStoreId(), f.getMessage()));
//                }
//                log.info("店铺信息更新es_goods_info，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), response.getUpdated());
                UpdateQuery updateQuery =
                        UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("goodsInfo.storeId").value(request.getStoreId()))).build())
                                .withIndex(EsConstants.DOC_GOODS_INFO_TYPE)
                                .withScript(sbr.toString())
                                .withScriptType(ScriptType.INLINE)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .withAbortOnVersionConflict(false).build();
                ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                        IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                    byQueryResponse.getFailures().forEach(f -> log.error("店铺信息更新es_goods_info，storeId:{} ===> 更新异常",
                            request.getStoreId(), f.getCause()));
                }
                log.info("店铺信息更新es_goods_info，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), byQueryResponse.getUpdated());

//                updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
//                updateByQueryRequest.setQuery(QueryBuilders.termQuery("storeId", request.getStoreId()));
//                updateByQueryRequest.setScript(script);
//                BulkByScrollResponse response1 = restHighLevelClient.updateByQuery(updateByQueryRequest,
//                                RequestOptions.DEFAULT);
//                if (CollectionUtils.isNotEmpty(response1.getBulkFailures())) {
//                    response1.getBulkFailures()
//                            .forEach(f -> log.error("店铺信息更新es_goods，storeId:{} ===> 更新异常:{}",
//                                    request.getStoreId(), f.getMessage()));
//                }
//                log.info("店铺信息更新es_goods，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), response1.getUpdated());
                updateQuery =
                        UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("storeId").value(request.getStoreId()))).build())
                                .withIndex(EsConstants.DOC_GOODS_TYPE)
                                .withScript(sbr.toString())
                                .withScriptType(ScriptType.INLINE)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .withAbortOnVersionConflict(false).build();
                byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                        IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                    byQueryResponse.getFailures().forEach(f -> log.error("店铺信息更新es_goods，storeId:{} ===> 更新异常",
                            request.getStoreId(), f.getCause()));
                }
                log.info("店铺信息更新es_goods，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), byQueryResponse.getUpdated());
            } catch (Exception e) {
                log.error("EsGoodsElasticService updateStoreStateByStoreId IOException", e);
            }
        }
    }

    @Override
    public void updateStoreNameByStoreId(EsGoodsModifyStoreNameByStoreIdRequest request) {
        if (Objects.nonNull(request.getStoreId()) && StringUtils.isNotBlank(request.getStoreName())) {
            StringBuilder sbr = new StringBuilder();
            if (StringUtils.isNotBlank(request.getStoreName())) {
                sbr.append("ctx._source.supplierName=").append(request.getSupplierName()).append(';');
                sbr.append("ctx._source.goodsInfos.storeName=").append(request.getStoreName()).append(';');
            }
//            Script script = new Script(sbr.toString());

//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
//            updateByQueryRequest.setQuery(QueryBuilders.termQuery("storeId", request.getStoreId()));
//            updateByQueryRequest.setScript(script);
            try {
//                BulkByScrollResponse response = restHighLevelClient.updateByQuery(updateByQueryRequest,
//                        RequestOptions.DEFAULT);
//                if (CollectionUtils.isNotEmpty(response.getBulkFailures())) {
//                    response.getBulkFailures()
//                            .forEach(f -> log.error("店铺名称更新es_goods，storeId:{} ===> 更新异常:{}",
//                                    request.getStoreId(), f.getMessage()));
//                }
                UpdateQuery updateQuery =
                        UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("storeId").value(request.getStoreId()))).build())
                                .withIndex(EsConstants.DOC_GOODS_TYPE)
                                .withScript(sbr.toString())
                                .withScriptType(ScriptType.INLINE)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .withAbortOnVersionConflict(false).build();
                ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                        IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                    byQueryResponse.getFailures().forEach(f -> log.error("店铺名称更新es_goods，storeId:{} ===> 更新异常",
                            request.getStoreId(), f.getCause()));
                }
                log.info("店铺信息更新es_goods_info，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), byQueryResponse.getUpdated());
//                script = new Script("ctx._source.goodsInfo.storeName=" + request.getStoreName() + ";");
//                updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
//                updateByQueryRequest.setQuery(QueryBuilders.termQuery("goodsInfo.storeId", request.getStoreId()));
//                updateByQueryRequest.setScript(script);
//                BulkByScrollResponse response1 = restHighLevelClient.updateByQuery(updateByQueryRequest,
//                        RequestOptions.DEFAULT);
//                if (CollectionUtils.isNotEmpty(response1.getBulkFailures())) {
//                    response1.getBulkFailures()
//                            .forEach(f -> log.error("店铺信息更新es_goods_info，storeId:{} ===> 更新异常:{}",
//                                    request.getStoreId(), f.getMessage()));
//                }
//                log.info("店铺名称更新es_goods，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), response.getUpdated());
                updateQuery =
                        UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("goodsInfo.storeId").value(request.getStoreId()))).build())
                                .withIndex(EsConstants.DOC_GOODS_INFO_TYPE)
                                .withScript(sbr.toString())
                                .withScriptType(ScriptType.INLINE)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .withAbortOnVersionConflict(false).build();
                byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                        IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
                if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                    byQueryResponse.getFailures().forEach(f -> log.error("店铺信息更新es_goods_info，storeId:{} ===> 更新异常",
                            request.getStoreId(), f.getCause()));
                }
                log.info("店铺名称更新es_goods，storeId:{} ===> 完成更新数量:{}", request.getStoreId(), byQueryResponse.getUpdated());
            } catch (Exception e) {
                log.error("EsGoodsElasticService updateStoreNameByStoreId IOException", e);
            }
        }
    }

    /**
     * @param goods
     * @param providerStoreMap
     * @return
     */
    private Integer buildGoodsVendibility(GoodsVO goods, Map<Long, StoreVO> providerStoreMap, Map<String, GoodsVO> providerGoodsVOMap) {
        Integer vendibility = Constants.yes;

        LocalDateTime now = LocalDateTime.now();

        String providerGoodsId = goods.getProviderGoodsId();

        if (StringUtils.isNotBlank(providerGoodsId)) {

            GoodsVO providerGoods = providerGoodsVOMap.get(providerGoodsId);

            if (providerGoods != null) {
                if (Constants.no.equals(goods.getVendibility())) {
                    vendibility = Constants.no;
                }
            }

        }
        return vendibility;
    }

    /**
     * @param goodsInfo
     * @param providerStoreMap
     * @return
     */
    private Integer buildGoodsInfoVendibility(GoodsInfoVO goodsInfo, Map<Long, StoreVO> providerStoreMap, Map<String, GoodsInfoVO> providerGoodsInfoVOMap) {
        Integer vendibility = Constants.yes;

        String providerGoodsInfoId = goodsInfo.getProviderGoodsInfoId();

        if (StringUtils.isNotBlank(providerGoodsInfoId)) {

            GoodsInfoVO providerGoodsInfo = providerGoodsInfoVOMap.get(providerGoodsInfoId);

            if (providerGoodsInfo != null) {
                if (Constants.no.equals(goodsInfo.getVendibility())) {
                    vendibility = Constants.no;
                }
            }

        }
        return vendibility;
    }

    /**
     * 根据商品spu批量获取商品属性关键Map
     *
     * @param goodsIds 商品id
     * @return 商品属性关键Map内容<商品id商品属性关联list>
     */
    private Map<String, List<GoodsPropRelNest>> getPropDetailRelList(List<String> goodsIds) {
        List<GoodsPropRelNest> goodsPropRelNests = new ArrayList<>();

        // 所有国家地区
        List<PlatformCountryVO> platformCountryVOList =
                platformCountryProvider.findAll().getContext().getPlatformCountryVOList();

        // 查询商品和属性的关联关系
        GoodsPropertyDetailRelListResponse relListResponses =
                goodsPropertyDetailRelQueryProvider
                        .list(
                                GoodsPropertyDetailRelListRequest.builder()
                                        .goodsIds(goodsIds)
                                        .goodsType(GoodsPropertyType.GOODS)
                                        .delFlag(DeleteFlag.NO)
                                        .build())
                        .getContext();
        // 查询属性
        if (CollectionUtils.isNotEmpty(relListResponses.getGoodsPropertyDetailRelVOList())) {

            // 属性id集合
            Set<Long> propIds =
                    relListResponses.getGoodsPropertyDetailRelVOList().stream()
                            .map(GoodsPropertyDetailRelVO::getPropId)
                            .collect(Collectors.toSet());

            if (CollectionUtils.isEmpty(propIds)) {
                return null;
            }

            GoodsPropertyListResponse goodsPropertyListResponse =
                    goodsPropertyQueryProvider
                            .list(
                                    GoodsPropertyListRequest.builder()
                                            .propIdList(new ArrayList<>(propIds))
                                            .delFlag(DeleteFlag.NO)
                                            .build())
                            .getContext();

            // 属性值id集合
            List<String> propDetails =
                    relListResponses.getGoodsPropertyDetailRelVOList().stream()
                            .filter(g -> g.getPropType().equals(GoodsPropertyEnterType.CHOOSE))
                            .filter(g -> StringUtils.isNotBlank(g.getDetailId()))
                            .map(GoodsPropertyDetailRelVO::getDetailId)
                            .collect(Collectors.toList());

            Set<Long> propDetailIds = new HashSet<>();
            propDetails.forEach(
                    prop -> {
                        propDetailIds.addAll(
                                Arrays.asList(prop.split(",")).stream()
                                        .map(p -> Long.valueOf(p))
                                        .collect(Collectors.toList()));
                    });

            // 查询属性值集合
            GoodsPropertyDetailListResponse goodsPropertyDetailListResponse =
                    goodsPropertyDetailQueryProvider
                            .list(
                                    GoodsPropertyDetailListRequest.builder()
                                            .detailIdList(new ArrayList<>(propDetailIds))
                                            .delFlag(DeleteFlag.NO)
                                            .build())
                            .getContext();

            relListResponses
                    .getGoodsPropertyDetailRelVOList()
                    .forEach(
                            prop -> {

                                // 过滤出来属性
                                List<GoodsPropertyVO> goodsPropertyVOs =
                                        goodsPropertyListResponse.getGoodsPropertyVOList().stream()
                                                .filter(
                                                        gp ->
                                                                gp.getPropId()
                                                                        .equals(prop.getPropId()))
                                                .collect(Collectors.toList());

                                GoodsPropRelNest goodsPropRelNest = new GoodsPropRelNest();
                                goodsPropRelNest.setPropId(prop.getPropId());
                                goodsPropRelNest.setGoodsId(prop.getGoodsId());

                                if (CollectionUtils.isNotEmpty(goodsPropertyVOs)) {

                                    // 商品选中的选项属性值
                                    List<GoodsPropertyDetailVO> goodsPropertyDetailVOs =
                                            new ArrayList<>();

                                    if (StringUtils.isNotBlank(prop.getDetailId())) {
                                        List<Long> detailIds =
                                                Arrays.asList(prop.getDetailId().split(","))
                                                        .stream()
                                                        .map(p -> Long.valueOf(p))
                                                        .collect(Collectors.toList());

                                        goodsPropertyDetailVOs =
                                                goodsPropertyDetailListResponse
                                                        .getGoodsPropertyDetailVOList()
                                                        .stream()
                                                        .filter(
                                                                gd ->
                                                                        detailIds.contains(
                                                                                gd.getDetailId()))
                                                        .collect(Collectors.toList());

                                        if (CollectionUtils.isNotEmpty(goodsPropertyDetailVOs)) {
                                            goodsPropertyDetailVOs.forEach(
                                                    propDetail -> {
                                                        if (StringUtils.isNotBlank(
                                                                propDetail.getDetailName())) {
                                                            propDetail.setDetailPinYin(
                                                                    Pinyin4jUtil.converterToSpell(
                                                                            propDetail
                                                                                    .getDetailName(),
                                                                            ","));
                                                        }
                                                    });
                                        }
                                    }

                                    // 商品关联的属性
                                    GoodsPropertyVO goodsProperty = goodsPropertyVOs.get(0);
                                    goodsPropRelNest.setPropName(goodsProperty.getPropName());
                                    goodsPropRelNest.setPropType(goodsProperty.getPropType());

                                    // 日期转化为detail id为时间戳
                                    if (null != prop.getPropValueDate()) {
                                        goodsPropertyDetailVOs = new ArrayList<>();
                                        GoodsPropertyDetailVO dateDetail =
                                                new GoodsPropertyDetailVO();
                                        dateDetail.setDetailName(
                                                prop.getPropValueDate()
                                                        .format(
                                                                DateTimeFormatter.ofPattern(
                                                                        "yyyy-MM-dd")));
                                        dateDetail.setDetailId(
                                                Long.valueOf(prop.getPropValueDate()
                                                        .format(
                                                                DateTimeFormatter.ofPattern(
                                                                        "yyyyMMdd")))
                                        );
                                        goodsPropertyDetailVOs.add(dateDetail);
                                    }

                                    // 地址属性值
                                    if (StringUtils.isNotBlank(prop.getPropValueProvince())) {

                                        PlatformAddressListResponse platformAddressListResponse =
                                                platformAddressQueryProvider
                                                        .list(
                                                                PlatformAddressListRequest.builder()
                                                                        .addrIdList(
                                                                                Arrays.asList(
                                                                                        prop.getPropValueProvince()
                                                                                                .split(
                                                                                                        ",")))
                                                                        .delFlag(DeleteFlag.NO)
                                                                        .build())
                                                        .getContext();

                                        if (CollectionUtils.isNotEmpty(
                                                platformAddressListResponse
                                                        .getPlatformAddressVOList())) {
                                            goodsPropertyDetailVOs = new ArrayList<>();
                                            List<GoodsPropertyDetailVO> addDetails =
                                                    new ArrayList<>();
                                            platformAddressListResponse
                                                    .getPlatformAddressVOList()
                                                    .stream()
                                                    .forEach(
                                                            add -> {
                                                                GoodsPropertyDetailVO addDetail =
                                                                        new GoodsPropertyDetailVO();
                                                                addDetail.setDetailId(
                                                                        Long.valueOf(
                                                                                add.getAddrId()));
                                                                addDetail.setDetailName(
                                                                        add.getAddrName());
                                                                if (StringUtils.isNotBlank(
                                                                        addDetail
                                                                                .getDetailName())) {
                                                                    addDetail.setDetailPinYin(
                                                                            Pinyin4jUtil
                                                                                    .converterToSpell(
                                                                                            addDetail
                                                                                                    .getDetailName(),
                                                                                            ","));
                                                                }
                                                                addDetails.add(addDetail);
                                                            });
                                            goodsPropertyDetailVOs.addAll(addDetails);
                                        }
                                    }

                                    // 国家地区
                                    if (CollectionUtils.isNotEmpty(platformCountryVOList)
                                            && StringUtils.isNotBlank(prop.getPropValueCountry())) {
                                        List<PlatformCountryVO> platformCountryVOs =
                                                platformCountryVOList.stream()
                                                        .filter(
                                                                pc ->
                                                                        Arrays.asList(
                                                                                prop.getPropValueCountry()
                                                                                        .split(
                                                                                                ","))
                                                                                .contains(
                                                                                        pc.getId()
                                                                                                .toString()))
                                                        .collect(Collectors.toList());

                                        if (CollectionUtils.isNotEmpty(platformCountryVOs)) {
                                            goodsPropertyDetailVOs = new ArrayList<>();
                                            List<GoodsPropertyDetailVO> countryDetails =
                                                    new ArrayList<>();
                                            platformCountryVOs.stream()
                                                    .forEach(
                                                            countryVO -> {
                                                                GoodsPropertyDetailVO
                                                                        countryDetail =
                                                                        new GoodsPropertyDetailVO();
                                                                countryDetail.setDetailId(
                                                                        countryVO.getId());
                                                                countryDetail.setDetailName(
                                                                        countryVO.getName());
                                                                if (StringUtils.isNotBlank(
                                                                        countryDetail
                                                                                .getDetailName())) {
                                                                    countryDetail.setDetailPinYin(
                                                                            Pinyin4jUtil
                                                                                    .converterToSpell(
                                                                                            countryDetail
                                                                                                    .getDetailName(),
                                                                                            ","));
                                                                }
                                                                countryDetails.add(countryDetail);
                                                            });
                                            goodsPropertyDetailVOs.addAll(countryDetails);
                                        }
                                    }

                                    // 属性值
                                    if (CollectionUtils.isNotEmpty(goodsPropertyDetailVOs)) {
                                        goodsPropRelNest.setGoodsPropDetailNest(
                                                KsBeanUtil.copyListProperties(
                                                        goodsPropertyDetailVOs,
                                                        GoodsPropDetailNest.class));

                                        goodsPropRelNest
                                                .getGoodsPropDetailNest()
                                                .forEach(
                                                        detail -> {
                                                            detail.setDetailNameValue(
                                                                    detail.getDetailName());
                                                        });
                                    }

                                    goodsPropRelNest.setIndexFlag(goodsProperty.getIndexFlag());
                                    goodsPropRelNest.setPropCharacter(
                                            goodsProperty.getPropCharacter());
                                    goodsPropRelNest.setPropSort(goodsProperty.getPropSort());

                                    GoodsPropCateRelByIdResponse goodsPropCateRelByIdResponse =
                                            goodsPropCateRelQueryProvider
                                                    .getByCateIdAndPropId(
                                                            GoodsPropCateRelQueryRequest.builder()
                                                                    .cateId(prop.getCateId())
                                                                    .propId(prop.getPropId())
                                                                    .build())
                                                    .getContext();

                                    // 分类属性关联排序
                                    if (Objects.nonNull(
                                            goodsPropCateRelByIdResponse.getGoodsPropCateRelVO())) {
                                        goodsPropRelNest.setCatePropSort(
                                                goodsPropCateRelByIdResponse
                                                        .getGoodsPropCateRelVO()
                                                        .getRelSort());
                                    }
                                }

                                if (!Objects.equals(goodsPropRelNest
                                        .getPropType(),GoodsPropertyEnterType.TEXT)) {
                                    goodsPropRelNests.add(goodsPropRelNest);
                                }
                            });
        }

        // 查询类目与属性关联关系

        return goodsPropRelNests.stream()
                .collect(Collectors.groupingBy(GoodsPropRelNest::getGoodsId));
    }

    /**
     * 根据商品sku批量获取区间价键值Map
     *
     * @param skuIds 商品skuId
     * @return 区间价键值Map内容<商品skuId区间价列表>
     */
    private Map<String, List<GoodsIntervalPriceVO>> getIntervalPriceMapBySkuId(List<String> skuIds) {
        List<GoodsIntervalPriceVO> voList =
                goodsIntervalPriceQueryProvider
                        .listByGoodsIds(GoodsIntervalPriceListBySkuIdsRequest.builder().skuIds(skuIds).build())
                        .getContext()
                        .getGoodsIntervalPriceVOList();
        return voList.stream().collect(Collectors.groupingBy(GoodsIntervalPriceVO::getGoodsInfoId));
    }

    /**
     * ES_cate_brand定义结构
     *
     * @param goodsCate  商品分类
     * @param goodsBrand 商品品牌
     * @return
     */
    private EsCateBrand putEsCateBrand(GoodsCateVO goodsCate, GoodsBrandVO goodsBrand) {
        GoodsCateNest cate = new GoodsCateNest();
        cate.setCateId(0L);
        if (goodsCate != null) {
            KsBeanUtil.copyPropertiesThird(goodsCate, cate);
            cate.setPinYin(ObjectUtils.toString(goodsCate.getCateName()));
        }

        GoodsBrandNest brand = new GoodsBrandNest();
        brand.setBrandId(0L);
        if (goodsBrand != null) {
            KsBeanUtil.copyPropertiesThird(goodsBrand, brand);
            brand.setPinYin(ObjectUtils.toString(goodsBrand.getBrandName()));
        }
        EsCateBrand esCateBrand = new EsCateBrand();
        esCateBrand.setId(String.valueOf(cate.getCateId()).concat("_").concat(String.valueOf(brand.getBrandId())));
        esCateBrand.setGoodsCate(cate);
        esCateBrand.setGoodsBrand(brand);
        return esCateBrand;
    }

    /***
     * 清理-重建索引
     * @param request 初始化ES请求
     */
    protected void clearAndRebuildIndex(EsGoodsInfoRequest request) {
        boolean isClear = request.isClearEsIndex();
        if(Objects.isNull(request.getClearEsIndexFlag()) || (Objects.nonNull(request.getClearEsIndexFlag()) && request.getClearEsIndexFlag() == DefaultFlag.NO)){
            if (!(CollectionUtils.isNotEmpty(request.getSkuIds())
                    || request.getCompanyInfoId() != null || request.getStoreId() != null
                    || StringUtils.isNotBlank(request.getGoodsId()) || CollectionUtils.isNotEmpty(request.getGoodsIds()) || CollectionUtils.isNotEmpty(request.getBrandIds())
                    || request.getCreateTimeBegin() != null || request.getCreateTimeEnd() != null
                    || CollectionUtils.isNotEmpty(request.getIdList()))
            ) {
                return;
            }
        }
        boolean isMapping = false;

        if (esBaseService.exists(EsConstants.DOC_GOODS_TYPE) || esBaseService.exists(EsConstants.DOC_GOODS_INFO_TYPE)) {
            if (isClear) {
                log.info("商品spu->删除索引");
                esBaseService.deleteIndex(EsConstants.DOC_GOODS_TYPE);
                esBaseService.deleteIndex(EsConstants.DOC_GOODS_INFO_TYPE);
                isMapping = true;
            }
        } else { //主要考虑第一次新增商品，此时还没有索引的时候
            isMapping = true;
        }

        if (isMapping) {
            //重建商品索引
            esBaseService.existsOrCreate(EsConstants.DOC_GOODS_TYPE, esGoodsMapping, false);
            esBaseService.existsOrCreate(EsConstants.DOC_GOODS_INFO_TYPE, esSkuMapping, false);
        }
    }

    /***
     * 将商品对象保存到ES
     * @param esGoodsList       商品列表
     * @param esSkuList         SKU列表
     */
    protected void saveGoods2Es(List<IndexQuery> esGoodsList, List<IndexQuery> esSkuList) {
        List<IndexQuery> saveEsGoodsList = new ArrayList<>(esGoodsList.size());
        // 持久化商品
        for (IndexQuery indexQuery : esGoodsList) {
            if (indexQuery.getObject() instanceof EsGoods){
                EsGoods esGoods = (EsGoods) indexQuery.getObject();
                if(PluginType.O2O.equals(esGoods.getPluginType())){
                    continue;
                }
            }
            saveEsGoodsList.add(indexQuery);
        }
        BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
        elasticsearchTemplate.bulkIndex(saveEsGoodsList, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
        esBaseService.refresh(EsConstants.DOC_GOODS_TYPE);

        // 持久化商品
        elasticsearchTemplate.bulkIndex(esSkuList, bulkOptions, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
        esBaseService.refresh(EsConstants.DOC_GOODS_INFO_TYPE);
    }

    /***
     * 请求对象
     * @param request   初始化请求
     */
    protected void initPointGoodsWithAsync(EsGoodsInfoRequest request) {
        if (CollectionUtils.isNotEmpty(request.getGoodsIds()) || CollectionUtils.isNotEmpty(request.getSkuIds())) {
            EsPointsGoodsInitRequest pointsInitRequest = new EsPointsGoodsInitRequest();
            pointsInitRequest.setGoodsIds(request.getGoodsIds());
            pointsInitRequest.setGoodsInfoIds(request.getSkuIds());
            esPointsGoodsService.initWithAsync(pointsInitRequest);
        }
    }

    /***
     * 获得商品查询请求
     * @param request 初始化请求
     * @return        商品查询请求
     */
    protected GoodsCountByConditionRequest getGoodsQueryRequest(EsGoodsInfoRequest request) {
        GoodsCountByConditionRequest goodsCountQueryRequest = new GoodsCountByConditionRequest();
        goodsCountQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        goodsCountQueryRequest.setCompanyInfoId(request.getCompanyInfoId());
        goodsCountQueryRequest.setGoodsIds(request.getGoodsIds());
        goodsCountQueryRequest.setStoreId(request.getStoreId());
        goodsCountQueryRequest.setBrandIds(request.getBrandIds());
        goodsCountQueryRequest.setCreateTimeBegin(request.getCreateTimeBegin());
        goodsCountQueryRequest.setCreateTimeEnd(request.getCreateTimeEnd());
        goodsCountQueryRequest.setIsShowO2oFlag(Boolean.FALSE);
        return goodsCountQueryRequest;
    }
}
