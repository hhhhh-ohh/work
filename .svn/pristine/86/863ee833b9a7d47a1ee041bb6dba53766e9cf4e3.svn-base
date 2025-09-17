package com.wanmi.sbc.xsite;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ImageUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreHomeInfoRequest;
import com.wanmi.sbc.customer.api.request.store.StorePageRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.xsitegoodscate.XsiteGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateAddRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateDeleteRequest;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandByIdsResponse;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateByIdsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoPageResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.setting.api.provider.storeresource.StoreResourceQueryProvider;
import com.wanmi.sbc.setting.api.provider.storeresourcecate.StoreResourceCateQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemresource.SystemResourceQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemresourcecate.SystemResourceCateQueryProvider;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourcePageRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateListRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourcePageResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateListResponse;
import com.wanmi.sbc.setting.api.response.yunservice.YunUploadResourceResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceCateVO;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 魔方建站接口
 */
@Tag(name = "XsiteController", description = "魔方建站接口")
@RestController
@Validated
@RequestMapping("")
@Slf4j
public class XsiteController {

    private static final Logger logger = LoggerFactory.getLogger(XsiteController.class);
    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;
    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;
    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;
    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private SystemResourceCateQueryProvider systemResourceCateQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private StoreResourceCateQueryProvider storeResourceCateQueryProvider;

    @Autowired
    private SystemResourceQueryProvider systemResourceQueryProvider;

    @Autowired
    private StoreResourceQueryProvider storeResourceQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private MarketingPluginProvider marketingPluginProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private XsiteGoodsCateProvider xsiteGoodsCateProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 魔方分页显示商品
     *
     * @param
     * @return 商品详情
     */
    @Operation(summary = "魔方分页显示商品")
    @RequestMapping(value = "/xsite/skusForXsite", method = RequestMethod.POST)
    public Map<String, Object> skuList(@RequestBody GoodsInfoRequest request) {
        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setPageNum(request.getPageNum());
        esGoodsInfoQueryRequest.setPageSize(request.getPageSize());
        esGoodsInfoQueryRequest.setLikeGoodsName(request.getQ());
        esGoodsInfoQueryRequest.setCateId(request.getCatName());
        esGoodsInfoQueryRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        esGoodsInfoQueryRequest.setStoreState(StoreState.OPENING.toValue());
        esGoodsInfoQueryRequest.setAddedFlag(AddedFlag.YES.toValue());
        esGoodsInfoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        String now = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4);
        esGoodsInfoQueryRequest.setContractStartDate(now);
        esGoodsInfoQueryRequest.setContractEndDate(now);
        esGoodsInfoQueryRequest.setCustomerLevelId(0L);
        esGoodsInfoQueryRequest.setCustomerLevelDiscount(BigDecimal.ONE);
        esGoodsInfoQueryRequest.setSortFlag(request.getSortFlag());
        if (Objects.nonNull(request.getLabelId())) {
            esGoodsInfoQueryRequest.setLabelIds(Collections.singletonList(request.getLabelId()));
        }
        Long storeId = request.getStoreId();
        //如果店铺id存在，带入查询
        if (!ObjectUtils.isEmpty(storeId)) {
            esGoodsInfoQueryRequest.setStoreId(storeId);
            if (!ObjectUtils.isEmpty(request.getCatName())) {
                esGoodsInfoQueryRequest.setCateId(null);
                List<Long> storeCateIds = new ArrayList<>();
                storeCateIds.add(request.getCatName());
                esGoodsInfoQueryRequest.setStoreCateIds(storeCateIds);
            }
        }
        Long searchByStore = request.getSearchByStore();
        if (!ObjectUtils.isEmpty(searchByStore)) {
            esGoodsInfoQueryRequest.setStoreId(searchByStore);
        }
        EsGoodsInfoResponse response = esGoodsInfoElasticQueryProvider.page(esGoodsInfoQueryRequest).getContext();

        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        List<EsGoodsInfoVO> goodsInfos = response.getEsGoodsInfoPage().getContent();
        systemPointsConfigService.clearBuyPoinsForEsSku(goodsInfos);
        //计算营销价格
        List<GoodsInfoDTO> goodsInfoList = goodsInfos.stream().filter(e -> Objects.nonNull(e.getGoodsInfo()))
                .map(e -> KsBeanUtil.convert(e.getGoodsInfo(), GoodsInfoDTO.class))
                .collect(Collectors.toList());
        MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
        filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class));
        GoodsInfoListByGoodsInfoResponse filterResponse =
                marketingPluginProvider.goodsListFilter(filterRequest).getContext();
        Map<String, GoodsInfoVO> goodsInfoVOMap = new HashMap<>();
        if (Objects.nonNull(filterResponse.getGoodsInfoVOList())) {
            goodsInfoVOMap.putAll(filterResponse.getGoodsInfoVOList().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity())));
        }
        List<Map<String, Object>> skus = new ArrayList<>();
        goodsInfos.forEach(esGoodsInfo -> {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            Map<String, Object> sku = new HashMap<>();
            GoodsInfoViewByIdResponse goodsInfoEditResponse =
                    goodsInfoQueryProvider.getViewById(GoodsInfoViewByIdRequest.builder().goodsInfoId(goodsInfo
                    .getGoodsInfoId()).build()).getContext();
            sku.put("skuId", goodsInfo.getGoodsInfoId());
            sku.put("spuId", goodsInfo.getGoodsId());
            sku.put("skuName", goodsInfo.getGoodsInfoName());
            sku.put("spuName", goodsInfoEditResponse.getGoods().getGoodsName());
            List<String> imgs = new ArrayList<>();
            String img = goodsInfoEditResponse.getGoodsInfo().getGoodsInfoImg();
            if (StringUtils.isEmpty(img)) {
                img = goodsInfoEditResponse.getGoods().getGoodsImg();
            }
            imgs.add(img);
            sku.put("images", imgs);
            List<Map<String, Object>> specs = new ArrayList<>();
            Map<String, Object> spec = new HashMap<>();
            spec.put("goodsId", goodsInfo.getGoodsId());
            spec.put("valKey", goodsInfo.getSpecText());
            specs.add(spec);
            sku.put("specs", specs);
            sku.put("sellPoint", "");
            sku.put("salePrice", goodsInfo.getSalePrice());
            Map<String, Object> stock = new HashMap<>();
            stock.put("stock", goodsInfo.getStock());
            sku.put("stock", stock);
            sku.put("buyPoint", goodsInfo.getBuyPoint());

            GoodsInfoVO vo = goodsInfoVOMap.get(goodsInfo.getGoodsInfoId());
            if (Objects.nonNull(vo)) {
                sku.put("grouponLabel", vo.getGrouponLabel());
                sku.put("grouponPrice", vo.getGrouponPrice());
                sku.put("marketingLabels", vo.getMarketingLabels());
                sku.put("couponLabels", vo.getCouponLabels());
            }
            skus.add(sku);
        });
        data.put("dataList", skus);
        data.put("pageNum", request.getPageNum());
        data.put("pageSize", request.getPageSize());
        data.put("totalCount", response.getEsGoodsInfoPage().getTotalElements());
        resMap.put("data", data);
        resMap.put("status", 1);
        resMap.put("message", "操作成功");
        resMap.put("code", null);
        return resMap;
    }

    @Operation(summary = "魔方分页显示商品")
    @RequestMapping(value = "/xsite/skuPageForXsite", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoPageResponse> skusPage(@RequestBody GoodsInfoRequest request) {
        EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        esGoodsInfoQueryRequest.setPageNum(request.getPageNum());
        esGoodsInfoQueryRequest.setPageSize(request.getPageSize());
        esGoodsInfoQueryRequest.setLikeGoodsName(request.getQ());
        esGoodsInfoQueryRequest.setCateId(request.getCatName());
        esGoodsInfoQueryRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        esGoodsInfoQueryRequest.setStoreState(StoreState.OPENING.toValue());
        esGoodsInfoQueryRequest.setAddedFlag(AddedFlag.YES.toValue());
        esGoodsInfoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        esGoodsInfoQueryRequest.setSortFlag(0);
        String now = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4);
        esGoodsInfoQueryRequest.setContractStartDate(now);
        esGoodsInfoQueryRequest.setContractEndDate(now);
        esGoodsInfoQueryRequest.setCustomerLevelId(0L);
        esGoodsInfoQueryRequest.setCustomerLevelDiscount(BigDecimal.ONE);
        if (Objects.nonNull(request.getLabelId())) {
            esGoodsInfoQueryRequest.setLabelIds(Collections.singletonList(request.getLabelId()));
        }

        Long storeId = request.getStoreId();
        //如果店铺id存在，带入查询
        if (!ObjectUtils.isEmpty(storeId)) {
            esGoodsInfoQueryRequest.setStoreId(storeId);
            if (!ObjectUtils.isEmpty(request.getCatName())) {
                esGoodsInfoQueryRequest.setCateId(null);
                List<Long> storeCateIds = new ArrayList<>();
                storeCateIds.add(request.getCatName());
                esGoodsInfoQueryRequest.setStoreCateIds(storeCateIds);
            }
        }
        Long searchByStore = request.getSearchByStore();
        if (!ObjectUtils.isEmpty(searchByStore)) {
            esGoodsInfoQueryRequest.setStoreId(searchByStore);
        }
        EsGoodsInfoResponse response = esGoodsInfoElasticQueryProvider.page(esGoodsInfoQueryRequest).getContext();

        List<EsGoodsInfoVO> goodsInfos = response.getEsGoodsInfoPage().getContent();
        systemPointsConfigService.clearBuyPoinsForEsSku(goodsInfos);
        // 店铺信息
        List<Long> storeIds =
                goodsInfos.stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getStoreId).collect(Collectors.toList());
        Map<Long, StoreVO> storeVOMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(storeIds)) {
            ListStoreByIdsResponse storeByIdsResponse =
                    storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext();
            storeVOMap = storeByIdsResponse.getStoreVOList().stream().collect(Collectors.toMap(StoreVO::getStoreId,
                    Function.identity()));
        }

        // 分类信息
        Map<Long, GoodsCateVO> goodsCateVOMap = new HashMap<>();
        List<Long> cateIds =
                goodsInfos.stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getCateId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(cateIds)) {
            GoodsCateByIdsResponse cateByIdsResponse =
                    goodsCateQueryProvider.getByIds(new GoodsCateByIdsRequest(cateIds)).getContext();
            goodsCateVOMap =
                    cateByIdsResponse.getGoodsCateVOList().stream().collect(Collectors.toMap(GoodsCateVO::getCateId,
                            Function.identity()));
        }

        // 品牌信息
        Map<Long, GoodsBrandVO> goodsBrandVOMap = new HashMap<>();
        List<Long> branIds =
                goodsInfos.stream().map(EsGoodsInfoVO::getGoodsInfo).map(GoodsInfoNestVO::getBrandId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(branIds)) {
            GoodsBrandByIdsResponse brandByIdsResponse =
                    goodsBrandQueryProvider.listByIds(new GoodsBrandByIdsRequest(branIds)).getContext();
            goodsBrandVOMap =
                    brandByIdsResponse.getGoodsBrandVOList().stream().collect(Collectors.toMap(GoodsBrandVO::getBrandId,
                            Function.identity()));
        }

        List<GoodsInfoVO> goodsInfoVOS = new ArrayList<>();
        final Map<Long, GoodsCateVO> resultGoodsCateVOMap = goodsCateVOMap;
        final Map<Long, GoodsBrandVO> resultGoodsBrandVOMap = goodsBrandVOMap;
        final Map<Long, StoreVO> resultstoreVOMap = storeVOMap;
        goodsInfos.forEach(esGoodsInfo -> {
            GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
            GoodsInfoVO vo = new GoodsInfoVO();
            vo.setGoodsInfoId(goodsInfo.getGoodsInfoId());
            vo.setGoodsInfoName(goodsInfo.getGoodsInfoName());
            vo.setGoodsInfoImg(goodsInfo.getGoodsInfoImg());
            vo.setStock(goodsInfo.getStock());
            vo.setMarketPrice(goodsInfo.getSalePrice());
            vo.setStoreId(goodsInfo.getStoreId());
            vo.setSpecText(goodsInfo.getSpecText());
            vo.setBrandId(goodsInfo.getBrandId());
            vo.setCateId(goodsInfo.getCateId());
            vo.setBuyPoint(goodsInfo.getBuyPoint());

            StoreVO storeVO = resultstoreVOMap.get(goodsInfo.getStoreId());
            if (Objects.nonNull(storeVO)) {
                vo.setStoreName(storeVO.getStoreName());
            }

            GoodsCateVO goodsCateVO = resultGoodsCateVOMap.get(goodsInfo.getCateId());
            if (Objects.nonNull(goodsCateVO)) {
                vo.setCateName(goodsCateVO.getCateName());
            }

            GoodsBrandVO goodsBrandVO = resultGoodsBrandVOMap.get(goodsInfo.getBrandId());
            if (Objects.nonNull(goodsBrandVO)) {
                vo.setBrandName(goodsBrandVO.getBrandName());
            }

            goodsInfoVOS.add(vo);
        });

        GoodsInfoPageResponse pageResponse = new GoodsInfoPageResponse();
        pageResponse.setGoodsInfoPage(new MicroServicePage<GoodsInfoVO>(goodsInfoVOS,
                response.getEsGoodsInfoPage().getPageable(),
                response.getEsGoodsInfoPage().getTotalElements()));

        return BaseResponse.success(pageResponse);
    }

    @Operation(summary = "魔方分页显示商品组")
    @RequestMapping(value = "/xsite/spusForXsite", method = RequestMethod.POST)
    public BaseResponse spuList(@RequestBody GoodsInfoRequest request) {
        EsGoodsInfoQueryRequest pageRequest = KsBeanUtil.convert(request, EsGoodsInfoQueryRequest.class);
        pageRequest.setIsMoFang(Boolean.TRUE);
        pageRequest.setVendibility(Constants.yes);
        pageRequest.setStoreState(StoreState.OPENING.toValue());
        String now = DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4);
        pageRequest.setContractStartDate(now);
        pageRequest.setContractEndDate(now);
        pageRequest.setCustomerLevelId(0L);
        pageRequest.setCustomerLevelDiscount(BigDecimal.ONE);
        pageRequest.setLikeGoodsName(request.getQ());
        pageRequest.setCateId(request.getCatName());
        pageRequest.setAuditStatus(CheckStatus.CHECKED.toValue());
        pageRequest.setAddedFlag(AddedFlag.YES.toValue());
        pageRequest.setDelFlag(DeleteFlag.NO.toValue());
        pageRequest.setQueryGoods(true);
        pageRequest.setLikeStoreName(request.getLikeSupplierName());
        // 只检索可售的商品
        pageRequest.setVendibility(Constants.yes);
        if(Objects.nonNull(request.getLabelId())){
            List<Long> labelList = Collections.singletonList(request.getLabelId());
            pageRequest.setLabelIds(labelList);
        }
        if(Objects.nonNull(request.getBrandId())){
            List<Long> brandIdList = Collections.singletonList(request.getBrandId());
            pageRequest.setBrandIds(brandIdList);
        }
        if(Objects.nonNull(request.getStoreCateId())){
            List<Long> storeCateIdList = Collections.singletonList(request.getStoreCateId());
            pageRequest.setStoreCateIds(storeCateIdList);
        }
        EsGoodsResponse goodsResponse = esGoodsInfoElasticQueryProvider.pageByGoods(pageRequest).getContext();
        List<EsGoodsVO> content = goodsResponse.getEsGoods().getContent();
        Map<Long, String> storeCateMap = this.getStoreCateMap(content);
        content.forEach(goods->{
            List<Long> storeCateIds = goods.getStoreCateIds();
            if(CollectionUtils.isNotEmpty(storeCateIds)) {
                String storeCateName = storeCateIds.stream()
                        .map(storeCateMap::get)
                        .collect(Collectors.joining(","));
                goods.setStoreCateName(storeCateName);
            }
        });
        return BaseResponse.success(goodsResponse);
    }

    /**
     * PC端魔方显示商品类别
     *
     * @param
     * @return
     */
    @Operation(summary = "PC端魔方显示商品类别")
    @RequestMapping(value = "/xsite/goodsCatesForXsite", method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateForXsite>> goodsCatesForXsite(@RequestParam(required = false) Long storeId) {
        List<GoodsCateForXsite> goodsCateForXsites = getCateList(storeId);
        return BaseResponse.success(goodsCateForXsites);
    }

    /**
     * 微信端魔方显示商品类别
     *
     * @param
     * @return
     */
    @Operation(summary = "微信端魔方显示商品类别")
    @RequestMapping(value = "/xsite/goodsCatesForXsite", method = RequestMethod.POST)
    public Map<String, Object> xSiteCatesForWx(@RequestParam(required = false) Long storeId) {
        List<GoodsCateForXsite> goodsCateForXsites = getCateList(storeId);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("data", goodsCateForXsites);
        resMap.put("msg", "操作成功");
        resMap.put("rescode", 200);
        resMap.put("result", "ok");
        return resMap;
    }


    private List<GoodsCateForXsite> getCateList(Long storeId) {

        if (ObjectUtils.isEmpty(storeId) || storeId == -1) {
            List<GoodsCateVO> goodsCates = goodsCateQueryProvider.list().getContext().getGoodsCateVOList();
            List<GoodsCateForXsite> goodsCateForXsites = new ArrayList<>();
            goodsCates.stream().forEach(goodsCate -> {
                List<GoodsCateVO> cates = goodsCate.getGoodsCateList();
                cates.forEach(cate -> {
                    GoodsCateForXsite gcfx = getGcfx(cate);
                    goodsCateForXsites.add(gcfx);
                    List<GoodsCateVO> cateList = cate.getGoodsCateList();
                    cateList.forEach(gCate -> {
                        GoodsCateForXsite xsiteCate = getGcfx(gCate);
                        goodsCateForXsites.add(xsiteCate);
                    });
                });

                GoodsCateForXsite goodsCateForXsite = getGcfx(goodsCate);
                goodsCateForXsites.add(goodsCateForXsite);
            });
            return goodsCateForXsites;
        } else {
            List<StoreCateResponseVO> goodsCates =
                    storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeId)).getContext().getStoreCateResponseVOList();
            List<GoodsCateForXsite> goodsCateForXsites = new ArrayList<>();
            goodsCates.stream().forEach(goodsCate -> {
                GoodsCateForXsite goodsCateForXsite = getGcfx(goodsCate);
                goodsCateForXsites.add(goodsCateForXsite);
            });
            return goodsCateForXsites;
        }

    }

    private GoodsCateForXsite getGcfx(Object goodsCate) {
        GoodsCateVO goodsCateVO = null;
        StoreCateResponseVO storeCateResponseVO = null;
        if (goodsCate instanceof GoodsCateVO) {
            goodsCateVO = (GoodsCateVO) goodsCate;
        } else if (goodsCate instanceof StoreCateResponseVO) {
            storeCateResponseVO = (StoreCateResponseVO) goodsCate;
        }
        GoodsCateForXsite goodsCateForXsite = new GoodsCateForXsite();
        goodsCateForXsite.setId(ObjectUtils.isEmpty(storeCateResponseVO) ?
                Objects.requireNonNull(goodsCateVO).getCateId() + "" : storeCateResponseVO.getStoreCateId() + "");
        goodsCateForXsite.setName(ObjectUtils.isEmpty(storeCateResponseVO) ?
                Objects.requireNonNull(goodsCateVO).getCateName() : storeCateResponseVO.getCateName());
        goodsCateForXsite.setParentId(ObjectUtils.isEmpty(storeCateResponseVO) ?
                Objects.requireNonNull(goodsCateVO).getCateParentId() + "" : storeCateResponseVO.getCateParentId() + "");
        Integer rate;
        if (ObjectUtils.isEmpty(storeCateResponseVO)) {
            assert goodsCateVO != null;
            rate = goodsCateVO.getCateGrade();
        } else {
            rate = storeCateResponseVO.getCateGrade();
        }
        goodsCateForXsite.setDepth(rate);
        String path;
        if (ObjectUtils.isEmpty(storeCateResponseVO)) {
            assert goodsCateVO != null;
            path = goodsCateVO.getCatePath();
        } else {
            path = storeCateResponseVO.getCatePath();
        }
        path = path.substring(0, path.length() - 1);
        path = path.replace('|', ',');
        goodsCateForXsite.setPath(path);
        goodsCateForXsite.setPinYin(ObjectUtils.isEmpty(storeCateResponseVO) ?
                Objects.requireNonNull(goodsCateVO).getPinYin() : "");
        goodsCateForXsite.setSimplePinYin(ObjectUtils.isEmpty(storeCateResponseVO) ?
                Objects.requireNonNull(goodsCateVO).getSPinYin() : "");
        return goodsCateForXsite;
    }

    /**
     * 魔方查询图片分类
     *
     * @return
     */
    @Operation(summary = "魔方查询图片分类")
    @RequestMapping(value = {"/api/gallery/cate/list"}, method = RequestMethod.GET)
    public Map<String, Object> list(@RequestParam(required = false) Long storeId) {
        List<Map<String, Object>> maps;
        if (ObjectUtils.isEmpty(storeId)) {

            BaseResponse<SystemResourceCateListResponse> response =
                    systemResourceCateQueryProvider.list(SystemResourceCateListRequest.builder().delFlag(DeleteFlag.NO).build());
            maps = KsBeanUtil.objectsToMaps(response.getContext().getSystemResourceCateVOList());

        } else {
            SystemResourceCateListRequest queryRequest = SystemResourceCateListRequest.builder()
                    .storeId(storeId).build();
            BaseResponse<SystemResourceCateListResponse> response = storeResourceCateQueryProvider.list(queryRequest);
            maps = KsBeanUtil.objectsToMaps(response.getContext().getSystemResourceCateVOList());
        }

        List<Map<String, Object>> resMaps = new ArrayList<>();
        maps.stream().forEach(stringObjectMap -> {
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("id", stringObjectMap.get("cateId"));
            resMap.put("name", stringObjectMap.get("cateName"));
            resMap.put("pid", stringObjectMap.get("cateParentId"));
            resMap.put("isDefault", stringObjectMap.get("isDefault"));
            String path = String.valueOf(stringObjectMap.get("catePath"));
            path = path.substring(0, path.length() - 1);
            path = path.replace('|', ',');
            resMap.put("path", path);
            resMaps.add(resMap);
        });
        Map<String, Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("data", resMaps);
        result.put("success", Boolean.TRUE);
        return result;
    }


    /**
     * 魔方分页图片
     *
     * @param imgPageReq 图片参数
     * @return
     */
    @Operation(summary = "魔方分页图片")
    @RequestMapping(value = "/api/gallery/item/list", method = RequestMethod.POST)
    public Map<String, Object> page(@RequestBody ImgPageReq imgPageReq, @RequestParam(required = false) Long storeId) {
        List<Map<String, Object>> pageList;
        long totalElements = 0;
        if (imgPageReq.getPageNo() == null || imgPageReq.getPageNo() < 0) {
            imgPageReq.setPageNo(Constants.ZERO);
        }
        if (imgPageReq.getPageSize() == null || imgPageReq.getPageSize() < 0) {
            imgPageReq.setPageSize(Constants.NUM_10);
        }
        if (ObjectUtils.isEmpty(storeId)) {
            Long cateId = imgPageReq.getCateId();
            if (cateId == null) {
                cateId = getDefaultCateId(storeId);
            }
            List<Long> cateIds = getCateIds(cateId, storeId);
            SystemResourcePageRequest pageReq = new SystemResourcePageRequest();
            pageReq.setCateIds(cateIds);
            pageReq.setPageNum(imgPageReq.getPageNo());
            pageReq.setPageSize(imgPageReq.getPageSize());
            pageReq.setResourceName(imgPageReq.getName());
            pageReq.setResourceType(ResourceType.IMAGE);
            BaseResponse<SystemResourcePageResponse> response = systemResourceQueryProvider.page(pageReq);
            MicroServicePage<SystemResourceVO> systemResourceVOPage = response.getContext().getSystemResourceVOPage();
            totalElements = systemResourceVOPage.getTotalElements();
            pageList = KsBeanUtil.objectsToMaps(systemResourceVOPage.getContent());
        } else {
            Long cateId = imgPageReq.getCateId();
            if (cateId == null) {
                cateId = getDefaultCateId(storeId);
            }
            List<Long> cateIds = getCateIds(cateId, storeId);
            SystemResourcePageRequest pageReq = new SystemResourcePageRequest();
            pageReq.setCateIds(cateIds);
            pageReq.setPageNum(imgPageReq.getPageNo());
            pageReq.setPageSize(imgPageReq.getPageSize());
            pageReq.setStoreId(storeId);
            pageReq.setResourceName(imgPageReq.getName());
            pageReq.setResourceType(ResourceType.IMAGE);
            BaseResponse<SystemResourcePageResponse> response = storeResourceQueryProvider.page(pageReq);
            MicroServicePage<SystemResourceVO> systemResourceVOPage = response.getContext().getSystemResourceVOPage();
            totalElements = systemResourceVOPage.getTotalElements();
            pageList = KsBeanUtil.objectsToMaps(systemResourceVOPage.getContent());
        }

        if (totalElements == 0) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 1);
            res.put("success", Boolean.TRUE);
            Map<String, Object> content = new HashMap<>();
            content.put("data", Collections.EMPTY_LIST);
            content.put("content", Collections.EMPTY_LIST);
            content.put("totalElements", totalElements);
            res.put("data", content);
            return res;
        }
        List<Map<String, Object>> data = new ArrayList<>();
        pageList.stream().forEach(stringObjectMap -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", stringObjectMap.get("resourceId"));
            item.put("pid", stringObjectMap.get("cateId"));
            item.put("name", stringObjectMap.get("resourceName"));
            item.put("isCate", "0");
            item.put("path", stringObjectMap.get("artworkUrl"));
            data.add(item);
        });
        Map<String, Object> res = new HashMap<>();
        res.put("status", 1);
        res.put("success", Boolean.TRUE);
        Map<String, Object> content = new HashMap<>();
        content.put("data", data);
        content.put("content", data);
        content.put("totalElements", totalElements);
        content.put("pageIndex", imgPageReq.getPageNo());
        content.put("pageSize", imgPageReq.getPageSize());
        content.put("total", totalElements);
        content.put("size", 10);
        content.put("number", imgPageReq.getPageNo());
        content.put("totalPages", (totalElements % 10) + 1);
        res.put("data", content);
        return res;
    }

    /**
     * 魔方分页视频
     *
     * @param videoPageReq 视频参数
     * @return
     */
    @Operation(summary = "魔方分页视频")
    @RequestMapping(value = "/api/gallery/video/list", method = RequestMethod.POST)
    public Map<String, Object> page(@RequestBody VideoPageReq videoPageReq, @RequestParam(required = false) Long storeId) {
        List<Map<String, Object>> pageList;
        if (videoPageReq.getPageNo() == null || videoPageReq.getPageNo() < 0) {
            videoPageReq.setPageNo(Constants.ZERO);
        }
        if (videoPageReq.getPageSize() == null || videoPageReq.getPageSize() < 0) {
            videoPageReq.setPageSize(Constants.NUM_10);
        }
        long totalElements = 0;
        if (ObjectUtils.isEmpty(storeId)) {
            Long cateId = videoPageReq.getCateId();
            if (cateId == null) {
                cateId = getDefaultCateId(storeId);
            }
            List<Long> cateIds = getCateIds(cateId, storeId);
            SystemResourcePageRequest pageReq = new SystemResourcePageRequest();
            pageReq.setCateIds(cateIds);
            pageReq.setPageNum(videoPageReq.getPageNo());
            pageReq.setPageSize(videoPageReq.getPageSize());
            pageReq.setResourceName(videoPageReq.getName());
            pageReq.setResourceType(ResourceType.VIDEO);
            BaseResponse<SystemResourcePageResponse> response = systemResourceQueryProvider.page(pageReq);
            MicroServicePage<SystemResourceVO> systemResourceVOPage = response.getContext().getSystemResourceVOPage();
            totalElements = systemResourceVOPage.getTotalElements();
            pageList = KsBeanUtil.objectsToMaps(systemResourceVOPage.getContent());
        } else {
            Long cateId = videoPageReq.getCateId();
            if (cateId == null) {
                cateId = getDefaultCateId(storeId);
            }
            List<Long> cateIds = getCateIds(cateId, storeId);
            SystemResourcePageRequest pageReq = new SystemResourcePageRequest();
            pageReq.setCateIds(cateIds);
            pageReq.setPageNum(videoPageReq.getPageNo());
            pageReq.setPageSize(videoPageReq.getPageSize());
            pageReq.setStoreId(storeId);
            pageReq.setResourceName(videoPageReq.getName());
            pageReq.setResourceType(ResourceType.VIDEO);
            BaseResponse<SystemResourcePageResponse> response = storeResourceQueryProvider.page(pageReq);
            MicroServicePage<SystemResourceVO> systemResourceVOPage = response.getContext().getSystemResourceVOPage();
            totalElements = systemResourceVOPage.getTotalElements();
            pageList = KsBeanUtil.objectsToMaps(systemResourceVOPage.getContent());
        }

        if (totalElements == 0) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 1);
            res.put("success", Boolean.TRUE);
            Map<String, Object> content = new HashMap<>();
            content.put("data", Collections.EMPTY_LIST);
            content.put("content", Collections.EMPTY_LIST);
            content.put("totalElements", totalElements);
            res.put("data", content);
            return res;
        }
        List<Map<String, Object>> data = new ArrayList<>();
        pageList.stream().forEach(stringObjectMap -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", stringObjectMap.get("resourceId"));
            item.put("pid", stringObjectMap.get("cateId"));
            item.put("name", stringObjectMap.get("resourceName"));
            item.put("isCate", "0");
            item.put("path", stringObjectMap.get("artworkUrl"));
            data.add(item);
        });
        Map<String, Object> res = new HashMap<>();
        res.put("status", 1);
        res.put("success", Boolean.TRUE);
        Map<String, Object> content = new HashMap<>();
        content.put("data", data);
        content.put("content", data);
        content.put("totalElements", totalElements);
        content.put("pageIndex", videoPageReq.getPageNo());
        content.put("pageSize", videoPageReq.getPageSize());
        content.put("total", totalElements);
        content.put("size", 10);
        content.put("number", videoPageReq.getPageNo());
        content.put("totalPages", (totalElements % 10) + 1);
        res.put("data", content);
        return res;
    }

    private List<Long> getCateIds(Long cateId, Long storeId) {
        List<Map<String, Object>> maps;
        List<SystemResourceCateVO> cateList;
        if (ObjectUtils.isEmpty(storeId)) {
            BaseResponse<SystemResourceCateListResponse> response =
                    systemResourceCateQueryProvider.list(SystemResourceCateListRequest
                    .builder()
                    .delFlag(DeleteFlag.NO)
                    .build());
            cateList = response.getContext().getSystemResourceCateVOList();
            //maps = KsBeanUtil.objectsToMaps(response.getContext().getSystemResourceCateVOList());
        } else {
            SystemResourceCateListRequest queryRequest = SystemResourceCateListRequest.builder()
                    .storeId(storeId).delFlag(DeleteFlag.NO).build();
            BaseResponse<SystemResourceCateListResponse> response = storeResourceCateQueryProvider.list(queryRequest);
//            maps = KsBeanUtil.objectsToMaps(response.getContext().getSystemResourceCateVOList());
            cateList = response.getContext().getSystemResourceCateVOList();

        }
        List<Long> cateIds = new ArrayList<>();
        cateIds.add(cateId);
        if(CollectionUtils.isEmpty(cateList)) {
            return cateIds;
        }
        List<Long> second = new ArrayList<>();
        for(SystemResourceCateVO cateVO : cateList) {
            Long pid = cateVO.getCateParentId();
            if (pid.equals(cateId)) {
                second.add(cateVO.getCateId());
            }
        }
//        for (Map<String, Object> map : maps) {
//            Long pid = (Long) map.get("cateParentId");
//            if (pid == cateId.intValue()) {
//                Long fid = (Long) (map.get("cateId"));
//                second.add(fid);
//            }
//        }
//        cateIds.addAll(second);
//        if (second.size() > 0) {
//            for (Map<String, Object> map : maps) {
//                Long pid = (Long) (map.get("cateParentId"));
//                for (Long cId : second) {
//                    if (pid == cId.intValue()) {
//                        Long fid = (Long) (map.get("cateId"));
//                        cateIds.add(fid);
//                    }
//                }
//            }
//        }
        if (CollectionUtils.isNotEmpty(second)) {
            cateIds.addAll(second);
            for (SystemResourceCateVO cateVO : cateList) {
                Long pid = cateVO.getCateParentId();
                for (Long cid : second) {
                    if (pid.equals(cid)) {
                        cateIds.add(cateVO.getCateId());
                    }
                }
            }
        }
        return cateIds;
    }

    /**
     * 返回状态
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/open_site_api/refresh", method = RequestMethod.POST)
    public Map<String, Object> refresh(RefreshRequest request) {
        String msg = "操作成功！";
        Integer rescode = 200;
        String result = "ok";
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        res.put("msg", msg);
        res.put("data", data);
        res.put("rescode", rescode);
        res.put("result", result);
        return res;
    }

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     */
    @Operation(summary = "上传图片")
    @RequestMapping(value = "/api/upload/image", method = RequestMethod.POST)
    public Map<String, Object> uploadFileImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam(required =
            false) Long storeId) {
        return uploadFile(multipartFile,storeId,ResourceType.IMAGE);
    }


    /**
     * 上传视频
     *
     * @param multipartFile
     * @return
     */
    @Operation(summary = "上传视频")
    @RequestMapping(value = "/api/upload/video", method = RequestMethod.POST)
    public Map<String, Object> uploadFileVideo(@RequestParam("file") MultipartFile multipartFile, @RequestParam(required =
            false) Long storeId) {
        return uploadFile(multipartFile,storeId,ResourceType.VIDEO);
    }

    /**
     * 上传图片视频公共方法
     * @param multipartFile
     * @param storeId
     * @param resourceType
     * @return
     */
    private Map<String, Object> uploadFile(MultipartFile multipartFile,Long storeId,ResourceType resourceType){
        //验证上传参数
        if (null == multipartFile || multipartFile.getSize() == 0 || multipartFile.getOriginalFilename() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        log.info("uploadFile Filename = " + multipartFile.getOriginalFilename());

        if (resourceType.equals(ResourceType.IMAGE)) {
            if(!ImageUtils.checkImageSuffix(multipartFile.getOriginalFilename())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        if (resourceType.equals(ResourceType.VIDEO)) {
            if(!ImageUtils.checkVideoSuffix(multipartFile.getOriginalFilename())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        String resourceUrl;
        try {
            YunUploadResourceResponse response;

//            Long cateId = getDefaultCateId(storeId);

            if (ObjectUtils.isEmpty(storeId)) {
                // 上传
                response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                        .cateId(null)
                        .resourceType(resourceType)
                        .content(multipartFile.getBytes())
                        .resourceName(multipartFile.getOriginalFilename())
                        .build()).getContext();

                resourceUrl = response.getResourceUrl();
            } else {
                Long companyInfoId =
                        storeQueryProvider.getStoreHomeInfo(StoreHomeInfoRequest.builder().storeId(storeId).build()).getContext().getCompanyInfoId();
                // 上传
                response = yunServiceProvider.uploadFile(YunUploadResourceRequest.builder()
                        .cateId(null)
                        .storeId(storeId)
                        .companyInfoId(companyInfoId)
                        .resourceType(resourceType)
                        .content(multipartFile.getBytes())
                        .resourceName(multipartFile.getOriginalFilename())
                        .build()).getContext();

                resourceUrl = response.getResourceUrl();
            }

//            if (Objects.nonNull(response.getSystemResourceVO())) {
//                EsSystemResourceVO esSystemResourceVO = EsSystemResourceVO.builder().build();
//                KsBeanUtil.copyPropertiesThird(response.getSystemResourceVO(), esSystemResourceVO);
//                EsSystemResourceSaveRequest saveRequest = EsSystemResourceSaveRequest.builder()
//                        .systemResourceVOList(Collections.singletonList(esSystemResourceVO))
//                        .build();
//                //同步es
//                esSystemResourceProvider.add(saveRequest);
//            }

        } catch (Exception e) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("msg", e.getMessage());
            resMap.put("err", err);
            return resMap;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("url", resourceUrl);
        data.put("name", multipartFile.getOriginalFilename());
        data.put("fileType",
                multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.') + 1));
        try {
            data.put("duration", multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Object> datas = new ArrayList<>();
        datas.add(data);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("status", 1);
        resMap.put("message", null);
        resMap.put("code", null);
        resMap.put("detail", null);
        resMap.put("errorCodes", null);
        resMap.put("success", Boolean.TRUE);
        resMap.put("data", datas);
        return resMap;
    }

    /**
     * 保存图片信息
     *
     * @param imgRequest
     * @return
     */
    @Operation(summary = "保存图片信息")
    @RequestMapping(value = "/api/gallery/image/save", method = RequestMethod.POST)
    public Map<String, Object> saveImg(@RequestBody ImgRequest imgRequest) {
        return saveFile(imgRequest,ResourceType.IMAGE);
    }

    /**
     * 保存视频信息
     *
     * @param imgRequest
     * @return
     */
    @Operation(summary = "保存视频信息")
    @RequestMapping(value = "/api/gallery/video/save", method = RequestMethod.POST)
    public Map<String, Object> saveVideo(@RequestBody ImgRequest imgRequest) {
        return saveFile(imgRequest,ResourceType.VIDEO);
    }

    private Map<String,Object> saveFile(ImgRequest imgRequest,ResourceType resourceType){
        List<YunUploadResourceRequest> yunUploadResourceRequestList = new ArrayList<>();
        boolean isFileNameTooLong = false;
        for (ImageForms imageForms : imgRequest.getImageForms()) {
            String fileName = imageForms.getName();
            int length = fileName.length();
            if (length > 45) {
                isFileNameTooLong = true;
                break;
            }
            YunUploadResourceRequest resourceRequest = YunUploadResourceRequest.builder()
                    .resourceType(resourceType)
                    .resourceName(imageForms.getName())
                    .content(imageForms.getDuration())
                    .artworkUrl(imageForms.getUrl())
                    .build();
            yunUploadResourceRequestList.add(resourceRequest);
        }

        if (isFileNameTooLong) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("msg", "文件名过长，请检查后重试！");
            resMap.put("err", err);
            return resMap;
        }

        boolean isPic = true;
        for (ImageForms imageForms : imgRequest.getImageForms()) {
            String fileName = imageForms.getName();

            log.info("saveFile Filename = " + fileName);

            if (resourceType.equals(ResourceType.IMAGE)) {
                if(!ImageUtils.checkImageSuffix(fileName)){
                    isPic = false;
                    break;
                }
            }

            if (resourceType.equals(ResourceType.VIDEO)) {
                if(!ImageUtils.checkVideoSuffix(fileName)){
                    isPic = false;
                    break;
                }
            }
        }

        if (!isPic) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("msg", "文件格式错误！");
            resMap.put("err", err);
            return resMap;
        }

        Long storeId = imgRequest.getStoreId();
        List<String> resourceUrls = new ArrayList<>();
        SystemResourceVO systemResourceVO = null;
        try {
            if (ObjectUtils.isEmpty(storeId)) {
                Long cateId = Long.valueOf(imgRequest.getCateId());
                if (cateId == 0) {
                    cateId = getDefaultCateId(storeId);
                }
                for (YunUploadResourceRequest uploadResourceRequest : yunUploadResourceRequestList) {
                    uploadResourceRequest.setCateId(cateId);
                    // 上传
                    YunUploadResourceResponse response =
                            yunServiceProvider.saveUploadedFile(uploadResourceRequest).getContext();
                    systemResourceVO = response.getSystemResourceVO();

                    resourceUrls.add(uploadResourceRequest.getArtworkUrl());
                }
            } else {
                Long cateId = Long.valueOf(imgRequest.getCateId());
                if (cateId == 0) {
                    cateId = getDefaultCateId(storeId);
                }
                Long companyInfoId =
                        storeQueryProvider.getStoreHomeInfo(StoreHomeInfoRequest.builder().storeId(storeId).build()).getContext().getCompanyInfoId();
                for (YunUploadResourceRequest uploadResourceRequest : yunUploadResourceRequestList) {
                    uploadResourceRequest.setCateId(cateId);
                    uploadResourceRequest.setStoreId(storeId);
                    uploadResourceRequest.setCompanyInfoId(companyInfoId);
                    // 上传
                    YunUploadResourceResponse response =
                            yunServiceProvider.saveUploadedFile(uploadResourceRequest).getContext();
                    resourceUrls.add(uploadResourceRequest.getArtworkUrl());
                    systemResourceVO = response.getSystemResourceVO();
                }

            }
            if (Objects.nonNull(systemResourceVO)) {
                EsSystemResourceVO esSystemResourceVO = EsSystemResourceVO.builder().build();
                KsBeanUtil.copyPropertiesThird(systemResourceVO, esSystemResourceVO);
                EsSystemResourceSaveRequest saveRequest = EsSystemResourceSaveRequest.builder()
                        .systemResourceVOList(Collections.singletonList(esSystemResourceVO))
                        .build();
                //同步es
                esSystemResourceProvider.add(saveRequest);
            }
        } catch (Exception e) {
            logger.error("uploadStoreImage rop error: {}", e.getMessage());
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("msg", e.getMessage());
            resMap.put("err", err);
            return resMap;
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("status", 1);
        resMap.put("message", null);
        resMap.put("code", null);
        resMap.put("detail", null);
        resMap.put("errorCodes", null);
        resMap.put("success", Boolean.TRUE);
        resMap.put("data", resourceUrls);
        return resMap;
    }
    /**
     * 根据网络地址拉网络图片
     *
     * @param url
     * @return
     * @throws Exception
     */
    @Operation(summary = "根据网络地址拉网络图片")
    @RequestMapping(value = "/api/upload/image/net", method = RequestMethod.GET)
    public Map<String, Object> netImg(@RequestParam String url, @RequestParam(required = false) Long storeId) {
        boolean fileTypeSupport = false;
        String[] fileUrl = url.split("\\.");
        int index = fileUrl.length - 1;
        String fileType = "";
        if (index < 0) {
            fileTypeSupport = true;
        } else {
            fileType = fileUrl[fileUrl.length - 1].toLowerCase();
        }
        switch (fileType) {
            case "jpeg":
                break;
            case "jpg":
                break;
            case "gif":
                break;
            case "png":
                break;
            default:
                fileTypeSupport = true;
                break;

        }
        if (fileTypeSupport) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("message", "这不是图片的网络地址，请检查后重试");
            resMap.put("err", err);
            return resMap;
        }
        URL urls = null;
        try {
            urls = new URL(url);
        } catch (MalformedURLException e) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("message", "请输入正确的网址！");
            resMap.put("err", err);
            return resMap;
        }
        //打开链接
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) urls.openConnection();
        } catch (IOException e) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("message", "请输入正确的网址");
            resMap.put("err", err);
            return resMap;
        }
        //设置请求方式为"GET"
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = null;
        try {
            inStream = conn.getInputStream();
        } catch (IOException e) {
            Map<String, Object> resMap = getErrorMap();
            Map<String, String> err = new HashMap<>();
            err.put("message", "这不是图片的网络地址，请检查后重试！");
            resMap.put("err", err);
            return resMap;
        }
        String name = UUID.randomUUID().toString() + "\\." + fileType;
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(name, name, null,
                    inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> res = uploadFileImage(multipartFile, storeId);
        List<Object> datas = (List<Object>) res.get("data");
        res.put("data", datas.get(0));
        return res;
    }

    /**
     * 返回访问错误的数据格式
     *
     * @return
     */
    private Map<String, Object> getErrorMap() {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("status", 1);
        resMap.put("message", null);
        resMap.put("code", null);
        resMap.put("detail", null);
        resMap.put("errorCodes", null);
        resMap.put("success", Boolean.FALSE);
        resMap.put("data", null);
        return resMap;
    }

    /**
     * 向开放平台查询默认分类ID
     *
     * @return
     */
    private Long getDefaultCateId(Long storeId) {
        List<Map<String, Object>> maps;
        if (ObjectUtils.isEmpty(storeId)) {

            BaseResponse<SystemResourceCateListResponse> response =
                    systemResourceCateQueryProvider.list(SystemResourceCateListRequest
                    .builder()
                    .delFlag(DeleteFlag.NO)
                    .isDefault(DefaultFlag.YES)
                    .build());
            maps = KsBeanUtil.objectsToMaps(response.getContext().getSystemResourceCateVOList());
        } else {
            SystemResourceCateListRequest queryRequest = SystemResourceCateListRequest.builder()
                    .storeId(storeId).isDefault(DefaultFlag.YES).build();
            BaseResponse<SystemResourceCateListResponse> response = storeResourceCateQueryProvider.list(queryRequest);
            maps = KsBeanUtil.objectsToMaps(response.getContext().getSystemResourceCateVOList());

        }
        Long defaultCateId = null;
        if (maps != null && maps.size() > 0) {
            Optional<Map<String, Object>> optionalMap = maps.stream().filter(stringObjectMap -> {
                DefaultFlag isDefault = DefaultFlag.valueOf(stringObjectMap.get("isDefault").toString());
                return Objects.equals(DefaultFlag.YES, isDefault);
            }).findFirst();
            Map<String, Object> map = optionalMap.orElse(null);
            assert map != null;
            defaultCateId = Long.valueOf(String.valueOf(map.get("cateId")));
        }
        return defaultCateId;
    }


    /**
     * 店铺列表(未登录)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "店铺列表(未登录)")
    @RequestMapping(value = "/xsite/storeList", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<StoreVO>> nonLoginList(@RequestBody StorePageRequest queryRequest) {
        queryRequest.setAuditState(CheckState.CHECKED);
        queryRequest.setStoreState(StoreState.OPENING);
        queryRequest.setGteContractStartDate(LocalDateTime.now());
        queryRequest.setLteContractEndDate(LocalDateTime.now());
        MicroServicePage<StoreVO> page = storeQueryProvider.page(queryRequest).getContext().getStoreVOPage();
        return BaseResponse.success(page);
    }

    /**
     * 查询店铺商品分类List
     */
    @Operation(summary = "查询店铺商品分类List")
    @RequestMapping(value = "/xsite/storeCateList", method = RequestMethod.GET)
    public BaseResponse<List<StoreCateResponseVO>> getStoreCateList(@RequestParam(required = false) Long storeId) {
        BaseResponse<StoreCateListByStoreIdResponse> baseResponse =
                storeCateQueryProvider.listByStoreId(new StoreCateListByStoreIdRequest(storeId));
        StoreCateListByStoreIdResponse response = baseResponse.getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(Collections.emptyList());
        }
        return BaseResponse.success(response.getStoreCateResponseVOList());
    }

    /**
     *
     * @param goodsVOList
     * @return
     */
    private Map<Long,String> getStoreCateMap(List<EsGoodsVO> goodsVOList){
        List<Long> cateIds = goodsVOList.stream()
                .filter(goods -> CollectionUtils.isNotEmpty(goods.getStoreCateIds()))
                .map(EsGoodsVO::getStoreCateIds)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        StoreCateListByIdsRequest idsRequest = new StoreCateListByIdsRequest(cateIds);
        List<StoreCateVO> storeCateVOList = storeCateQueryProvider.listByIds(idsRequest)
                .getContext().getStoreCateVOList();
        if(CollectionUtils.isEmpty(storeCateVOList)){
            return Collections.emptyMap();
        }
        return storeCateVOList.stream()
                .collect(Collectors.toMap(StoreCateVO::getStoreCateId,StoreCateVO::getCateName));
    }

    /**
     * 新增商品三级分类
     */
    @Operation(summary = "新增商品三级分类")
    @PostMapping(value = "/xsite/goodsCateAdd")
    public BaseResponse xsiteGoodsCateAdd(@RequestBody @Valid XsiteGoodsCateAddRequest xsiteGoodsCateAddRequest) {
        String userId = commonUtil.getOperator().getUserId();
        xsiteGoodsCateAddRequest.setUserId(userId);
        return xsiteGoodsCateProvider.add(xsiteGoodsCateAddRequest);
    }

    /**
     * 删除商品三级分类
     */
    @Operation(summary = "删除商品三级分类")
    @PostMapping(value = "/xsite/goodsCateDelete")
    public BaseResponse goodsCateDelete(@RequestBody @Valid XsiteGoodsCateDeleteRequest xsiteGoodsCateDeleteRequest) {
        String userId = commonUtil.getOperator().getUserId();
        xsiteGoodsCateDeleteRequest.setUserId(userId);
        return xsiteGoodsCateProvider.delete(xsiteGoodsCateDeleteRequest);
    }

    @PostMapping("/mofang/mq/message")
    public BaseResponse mqMessage(MqRequest mqRequest){
        if (mqRequest.getDelay() == null){
            MqSendDTO delayDTO = new MqSendDTO();
            delayDTO.setTopic(ProducerTopic.MOFANF_MESSAGE);
            delayDTO.setData(mqRequest.getData());
            mqSendProvider.send(delayDTO);
        } else {
            MqSendDelayDTO delayDTO = new MqSendDelayDTO();
            delayDTO.setDelayTime(mqRequest.getDelay());
            delayDTO.setTopic(ProducerTopic.MOFANF_MESSAGE);
            delayDTO.setData(mqRequest.getData());
            mqSendProvider.sendDelay(delayDTO);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
