package com.wanmi.sbc.goods.goodsaudit.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelListResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.EditLevelPriceRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditListResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditModifyResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByGoodsResponse;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsRepository;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodsaudit.request.GoodsAuditSaveRequest;
import com.wanmi.sbc.goods.goodsaudit.response.GoodsAuditEditResponse;
import com.wanmi.sbc.goods.goodsaudit.response.GoodsAuditQueryResponse;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import com.wanmi.sbc.goods.info.service.GoodsBaseInterface;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.price.model.root.GoodsCustomerPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsLevelPrice;
import com.wanmi.sbc.goods.price.repository.GoodsCustomerPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsIntervalPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsLevelPriceRepository;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.goods.storecate.service.StoreCateGoodsRelaService;
import com.wanmi.sbc.goods.storegoodstab.model.root.GoodsTabRela;
import com.wanmi.sbc.goods.storegoodstab.model.root.StoreGoodsTab;
import com.wanmi.sbc.goods.storegoodstab.repository.GoodsTabRelaRepository;
import com.wanmi.sbc.goods.storegoodstab.repository.StoreGoodsTabRepository;
import com.wanmi.sbc.goods.util.mapper.GoodsAuditMapper;
import com.wanmi.sbc.goods.util.mapper.GoodsSaveRequestMapper;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>商品审核业务逻辑</p>
 *
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Service("GoodsAuditService")
@Slf4j
public class GoodsAuditService {

    @Resource
    private GoodsAuditRepository goodsAuditRepository;

    @Resource
    private GoodsInfoRepository goodsInfoRepository;

    @Resource
    private GoodsCateService goodsCateService;

    @Resource
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Resource
    private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Resource
    private StoreCateGoodsRelaService storeCateGoodsRelaService;

    @Resource
    private FreightTemplateGoodsRepository freightTemplateGoodsRepository;

    @Resource
    private StoreGoodsTabRepository storeGoodsTabRepository;

    @Resource
    private GoodsTabRelaRepository goodsTabRelaRepository;

    @Resource
    private GoodsImageRepository goodsImageRepository;

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private GoodsSpecRepository goodsSpecRepository;

    @Resource
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Resource
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Resource
    private GoodsIntervalPriceRepository goodsIntervalPriceRepository;

    @Resource
    private GoodsLevelPriceRepository goodsLevelPriceRepository;

    @Resource
    private GoodsCustomerPriceRepository goodsCustomerPriceRepository;

    @Resource
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Resource
    private GoodsBaseInterface goodsBaseInterface;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private OsUtil osUtil;

    @Resource
    private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Resource
    private AuditQueryProvider auditQueryProvider;

    @Resource
    private GoodsAuditMapper goodsAuditMapper;

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsCateRepository goodsCateRepository;

    @Resource
    private GoodsCommissionConfigService goodsCommissionConfigService;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Resource
    private GoodsSaveRequestMapper goodsSaveRequestMapper;

    /**
     * 分页查询商品审核
     *
     * @author 黄昭
     */
    public GoodsAuditQueryResponse page(GoodsAuditQueryRequest request) {
        GoodsAuditQueryResponse response = new GoodsAuditQueryResponse();

        //根据SKU模糊查询SKU，获取SKU编号
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        if (StringUtils.isNotBlank(request.getLikeGoodsInfoNo())) {
            infoQueryRequest.setLikeGoodsInfoNo(request.getLikeGoodsInfoNo());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIdList(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }

        //根据店铺分类获取GoodsIds
        if (Objects.nonNull(request.getStoreCateId())) {
            List<String> goodsIds =
                    storeCateGoodsRelaRepository.selectRelByStoreCateIds(Collections.singletonList(request.getStoreCateId()));
            if (CollectionUtils.isNotEmpty(goodsIds)) {
                request.setStoreCateGoodsIds(goodsIds);
            } else {
                return response;
            }
        }

        //获取该分类的所有子分类
        if (request.getCateId() != null) {
            request.setCateIds(goodsCateService.getChlidCateId(request.getCateId()));
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
                request.setCateId(null);
            }
        }

        Page<GoodsAudit> goodsAuditPage = goodsAuditRepository.findAll(GoodsAuditWhereCriteriaBuilder.build(request), request.getPageRequest());
        //获取商品店铺分类
        List<String> goodsIds = goodsAuditPage.getContent().stream().map(page -> page.getGoodsId()).collect(Collectors.toList());
        List<StoreCateGoodsRela> storeCateGoodsRelas = storeCateGoodsRelaService.selectByGoodsId(goodsIds);

        response.setGoodsAuditPage(KsBeanUtil.convertPage(goodsAuditPage, GoodsAuditSaveVO.class));
        response.getGoodsAuditPage().getContent().forEach(page -> {
            page.setStoreCateIds(storeCateGoodsRelas.stream()
                    .filter(rela -> Objects.equals(rela.getGoodsId(), page.getGoodsId()))
                    .map(StoreCateGoodsRela::getStoreCateId)
                    .collect(Collectors.toList()));
        });

        //如果是linkedmall商品，实时查库存
        List<String> spuIds = response.getGoodsAuditPage().getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(GoodsAuditSaveVO::getGoodsId)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(spuIds)) {
            return response;
        }

        infoQueryRequest.setGoodsIds(spuIds);
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> skuList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());

        List<Long> itemIds = response.getGoodsAuditPage().getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());

        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (GoodsInfo goodsInfo : skuList) {
                for (LinkedMallStockVO spuStock : stocks) {
                    if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                                .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            goodsInfo.setStock(skuStock);
                        }
                    }
                }
            }
            for (GoodsAuditSaveVO goods : response.getGoodsAuditPage().getContent()) {
                if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType())) {
                    Long spuStock = skuList.stream()
                            .filter(v -> v.getGoodsId().equals(goods.getGoodsId()) && v.getStock() != null)
                            .map(GoodsInfo::getStock).reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                    goods.setStock(spuStock);
                }
            }

        }
        return response;
    }

    /**
     * 根据商品Id获取商品详情
     *
     * @param goodsId
     * @param isEditProviderGoods
     * @return
     */
    public GoodsAuditEditResponse findInfoById(String goodsId, Boolean isEditProviderGoods) {
        GoodsAuditEditResponse response = new GoodsAuditEditResponse();
        GoodsAudit audit = goodsAuditRepository.findById(goodsId).orElse(null);
        //放开商家端查询供应商商品delFlag=1的条件
        if (isEditProviderGoods) {
            if (Objects.isNull(audit)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
        } else {
            if (Objects.isNull(audit) || DeleteFlag.YES.equals(audit.getDelFlag())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
        }



        // 获取商家所绑定的模板列表
        List<StoreGoodsTab> storeGoodsTabs = storeGoodsTabRepository.queryTabByStoreId(audit.getStoreId());
        response.setStoreGoodsTabs(KsBeanUtil.convertList(storeGoodsTabs, StoreGoodsTabSaveVO.class));
        if (CollectionUtils.isNotEmpty(response.getStoreGoodsTabs())) {
            response.getStoreGoodsTabs().sort(Comparator.comparingInt(StoreGoodsTabSaveVO::getSort));
            List<GoodsTabRela> goodsTabRelas = goodsTabRelaRepository.queryListByTabIds(goodsId,
                    response.getStoreGoodsTabs().stream().map(StoreGoodsTabSaveVO::getTabId).collect(Collectors.toList()));
            response.setGoodsTabRelas(KsBeanUtil.convertList(goodsTabRelas, GoodsTabRelaVO.class));
        }

        //查询商品图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(audit.getGoodsId()), GoodsImageVO.class));

        //查询SKU列表
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setGoodsId(audit.getGoodsId());
        //根据请求状态决定放开deleteFlag
        if (!isEditProviderGoods) {
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        }
        List<GoodsInfo> skuList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());

        skuList.forEach(goodsInfo -> {
            goodsInfo.setBrandId(audit.getBrandId());
            goodsInfo.setCateId(audit.getCateId());
            updateGoodsInfoSupplyPriceAndStock(goodsInfo);
        });

        List<GoodsInfoSaveVO> goodsInfos = KsBeanUtil.convertList(skuList, GoodsInfoSaveVO.class);
        goodsInfos.forEach(goodsInfo -> {
            goodsInfo.setPriceType(audit.getPriceType());
            skuList.stream()
                    .filter(s -> s.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).findFirst()
                    .ifPresent(s -> {
                        s.setVendibility(goodsInfoService.buildGoodsInfoVendibility(goodsInfo));
                    	goodsInfo.setVendibility(s.getVendibility());
                    });
        });

        //封装SPU市场价
        /*GoodsInfo tempGoodsInfo = goodsInfos.stream()
                .filter(goodsInfo -> Objects.nonNull(goodsInfo.getMarketPrice()))
                .min(Comparator.comparing(GoodsInfo::getMarketPrice)).orElse(null);
        goodsAudit.setMarketPrice(tempGoodsInfo != null ? tempGoodsInfo.getMarketPrice() : goodsAudit.getMarketPrice());*/

        //如果是linkedmall商品，实时查库存
        if (ThirdPlatformType.LINKED_MALL.equals(audit.getThirdPlatformType()) || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(audit.getGoodsSource())) {
            List<LinkedMallStockVO> stockList = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(audit.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stockList.size() > 0) {
                LinkedMallStockVO stock = stockList.get(0);
                for (GoodsInfoSaveVO goodsInfo : goodsInfos) {
                    for (LinkedMallStockVO.SkuStock sku : stock.getSkuList()) {
                        if (stock.getItemId().equals(Long.valueOf(goodsInfo.getThirdPlatformSpuId())) && sku.getSkuId().equals(Long.valueOf(goodsInfo.getThirdPlatformSkuId()))) {
                            Long skuStock = sku.getStock();
                            goodsInfo.setStock(skuStock);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(skuStock > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
                audit.setStock(stock.getSkuList().stream()
                        .map(v -> v.getStock())
                        .reduce(0L, (aLong, aLong2) -> aLong + aLong2));
            }
        }

        GoodsAuditSaveVO goodsAudit = KsBeanUtil.convert(audit, GoodsAuditSaveVO.class);
        if (Objects.nonNull(goodsAudit.getFreightTempId())) {
            FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(goodsAudit.getFreightTempId());
            if (Objects.nonNull(goodsTemplate)) {
                goodsAudit.setFreightTempName(goodsTemplate.getFreightTempName());
            }
        }
        if (StringUtils.isNotBlank(goodsAudit.getProviderGoodsId())) {
            Goods providerGoods = goodsRepository.findById(goodsAudit.getProviderGoodsId()).orElse(null);
            FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(providerGoods.getFreightTempId());
            if (Objects.nonNull(goodsTemplate)) {
                goodsAudit.setFreightTempName(goodsTemplate.getFreightTempName());
            }
        }
        response.setGoodsAudit(goodsAudit);

        //商品属性
        List<GoodsPropertyDetailRel> relList = goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(
                goodsAudit.getGoodsId(), DeleteFlag.NO, GoodsPropertyType.GOODS);
        response.setGoodsPropDetailRels(KsBeanUtil.convertList(relList, GoodsPropertyDetailRelVO.class));

        //如果是多规格
        if (Constants.yes.equals(goodsAudit.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goodsAudit.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goodsAudit.getGoodsId()), GoodsSpecDetailSaveVO.class));

            if (isEditProviderGoods && Objects.nonNull(goodsAudit.getProviderGoodsId())) {
                List<GoodsSpec> byGoodsIdSpec = goodsSpecRepository.findByGoodsId(goodsAudit.getProviderGoodsId());
                response.getGoodsSpecs().addAll(KsBeanUtil.convertList(byGoodsIdSpec, GoodsSpecSaveVO.class));
                List<GoodsSpecDetail> byGoodsIdDetail = goodsSpecDetailRepository.findByGoodsId(goodsAudit.getProviderGoodsId());
                response.getGoodsSpecDetails().addAll(KsBeanUtil.convertList(byGoodsIdDetail, GoodsSpecDetailSaveVO.class));
            }

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream()
                        .filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId()))
                        .map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<GoodsInfoSpecDetailRel>> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goodsAudit.getGoodsId()).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
            goodsInfos.forEach(goodsInfo -> {
                goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }
        response.setGoodsInfos(goodsInfos);

        //商品按订货区间，查询订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goodsAudit.getPriceType())) {
            response.setGoodsIntervalPrices(KsBeanUtil.convert(goodsIntervalPriceRepository.findByGoodsId(goodsAudit.getGoodsId()), GoodsIntervalPriceVO.class));

            if (Objects.equals(AuditType.SECOND_AUDIT.toValue(),goodsAudit.getAuditType())){
                List<GoodsIntervalPrice> skuIntervalPriceList = goodsIntervalPriceRepository
                        .findSkuByGoodsId(goodsAudit.getGoodsId());
                List<GoodsIntervalPriceVO> goodsInfoIntervalPrices =
                        KsBeanUtil.convertList(skuIntervalPriceList, GoodsIntervalPriceVO.class);
                response.setGoodsInfoIntervalPrices(goodsInfoIntervalPrices);

                //校验sku改动
                Map<String, String> goodsInfoMap = checkSkuIntervalPriceList(goodsAudit, goodsInfos, skuIntervalPriceList, goodsInfoIntervalPrices);

                response.setGoodsInfoMap(goodsInfoMap);
            }

        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goodsAudit.getPriceType())) {
            response.setGoodsLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findByGoodsId(goodsAudit.getGoodsId()), GoodsLevelPriceVO.class));

            Map<Long, String> levelIdAndNameMap = new HashMap<>();
            CustomerLevelListResponse customerLevelListResponse = customerLevelQueryProvider.listAllCustomerLevel().getContext();
            if (customerLevelListResponse != null) {
                List<CustomerLevelVO> customerLevelVOList = customerLevelListResponse.getCustomerLevelVOList();
                if (CollectionUtils.isNotEmpty(customerLevelVOList)) {
                    for (CustomerLevelVO customerLevelVO : customerLevelVOList) {
                        levelIdAndNameMap.put(customerLevelVO.getCustomerLevelId(), customerLevelVO.getCustomerLevelName());
                    }
                }
            }

            //如果是按单独客户定价
            if (Constants.yes.equals(goodsAudit.getCustomFlag())) {
                List<GoodsCustomerPrice> byGoodsId = goodsCustomerPriceRepository.findByGoodsId(goodsAudit.getGoodsId());

                //获取商品按客户设价信息
                response.setGoodsCustomerPrices(goodsService.getGoodsCustomerPriceVo(byGoodsId, levelIdAndNameMap));
            }

            if (Objects.equals(AuditType.SECOND_AUDIT.toValue(),goodsAudit.getAuditType())){

                List<GoodsLevelPrice> goodsInfoLevelPriceList = goodsLevelPriceRepository.findSkuLevelPriceByGoodsId(goodsAudit.getGoodsId());
                List<GoodsLevelPriceVO> goodsLevelPriceVOS =
                        KsBeanUtil.convertList(goodsInfoLevelPriceList, GoodsLevelPriceVO.class);

                response.setGoodsInfoLevelPrices(goodsLevelPriceVOS);

                List<GoodsCustomerPrice> skuByGoodsId = goodsCustomerPriceRepository.findSkuByGoodsId(goodsAudit.getGoodsId());

                List<GoodsCustomerPriceVO> goodsCustomerPriceVOS = goodsService.getGoodsCustomerPriceVo(skuByGoodsId, levelIdAndNameMap);

                //获取商品sku按客户设价信息
                response.setGoodsInfoCustomerPrices(goodsCustomerPriceVOS);

                //校验sku改动
                Map<String, String> goodsInfoMap = checkCustomerPrice(goodsAudit, goodsInfos, skuByGoodsId, goodsInfoLevelPriceList);

                response.setGoodsInfoMap(goodsInfoMap);
            }
        }

        //比较新老商品信息
        if (Objects.nonNull(goodsAudit.getOldGoodsId())){
            GoodsSaveDTO newGoods = KsBeanUtil.convert(goodsAudit, GoodsSaveDTO.class);
            newGoods.setGoodsId(goodsAudit.getOldGoodsId());
            Goods oldGoods = goodsRepository.findById(goodsAudit.getOldGoodsId()).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
            GoodsSaveRequest goodsSaveRequest = new GoodsSaveRequest();
            goodsSaveRequest.setImages(KsBeanUtil.convert(response.getImages(),GoodsImageDTO.class));
            goodsSaveRequest.setGoodsPropertyDetailRel(KsBeanUtil.convert(response.getGoodsPropDetailRels(), GoodsPropertyDetailRelSaveDTO.class));
            goodsSaveRequest.setGoodsSpecs(KsBeanUtil.convert(response.getGoodsSpecs(), GoodsSpecSaveDTO.class));
            goodsSaveRequest.setGoodsSpecDetails(KsBeanUtil.convert(response.getGoodsSpecDetails(), GoodsSpecDetailSaveDTO.class));
            List<GoodsInfoSaveDTO> infos = KsBeanUtil.convert(response.getGoodsInfos(), GoodsInfoSaveDTO.class);
            infos.forEach(v->v.setGoodsInfoId(v.getOldGoodsInfoId()));
            goodsSaveRequest.setGoodsInfos(infos);
            goodsSaveRequest.setGoodsLevelPrices(KsBeanUtil.convertList(response.getGoodsLevelPrices(), GoodsLevelPriceDTO.class));
            if(CollectionUtils.isNotEmpty(response.getGoodsCustomerPrices())){
                goodsSaveRequest.setGoodsCustomerPrices(KsBeanUtil.convert(response.getGoodsCustomerPrices(),GoodsCustomerPriceDTO.class));
            }
            List<StoreCateGoodsRela> storeCateGoodsRelaList = storeCateGoodsRelaRepository.selectByGoodsId(Collections.singletonList(goodsAudit.getGoodsId()));
            newGoods.setStoreCateIds(storeCateGoodsRelaList.stream().map(StoreCateGoodsRela::getStoreCateId).collect(Collectors.toList()));
            goodsSaveRequest.setGoodsIntervalPrices(KsBeanUtil.convert(response.getGoodsIntervalPrices(), GoodsIntervalPriceDTO.class));
            GoodsCommissionConfig goodsCommissionConfig = null;
            if (Objects.nonNull(oldGoods.getStoreId())){
                goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(oldGoods.getStoreId());
            }
            response.setChangeFlag(goodsBaseInterface.checkGoodsIsAudit(KsBeanUtil.copyPropertiesThird(oldGoods, GoodsSaveDTO.class),newGoods, goodsSaveRequest,goodsCommissionConfig,false));
        }


        //获取商品店铺分类
        if (osUtil.isS2b()) {
            StoreCateListByGoodsRequest storeCateListByGoodsRequest =
                    new StoreCateListByGoodsRequest(Collections.singletonList(goodsId));
            BaseResponse<StoreCateListByGoodsResponse> baseResponse =
                    storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
            StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
            if (Objects.nonNull(storeCateListByGoodsResponse)) {
                List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList =
                        storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                response.getGoodsAudit().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                        .filter(rela -> rela.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                        .collect(Collectors.toList()));
                if (org.apache.commons.collections.CollectionUtils.isNotEmpty(response.getGoodsAudit().getStoreCateIds())){
                    StoreCateListByIdsRequest storeCateListByIdsRequest = new StoreCateListByIdsRequest();
                    List<Long> storeCateIds = response.getGoodsAudit().getStoreCateIds();
                    storeCateListByIdsRequest.setCateIds(storeCateIds);
                    List<Long> parentIds = storeCateQueryProvider
                            .listByIds(storeCateListByIdsRequest)
                            .getContext()
                            .getStoreCateVOList()
                            .stream()
                            .map(StoreCateVO::getCateParentId)
                            .collect(Collectors.toList());
                    response.getGoodsAudit().setStoreCateParentIds(parentIds);
                }
            }

        }

        return response;
    }

    /**
     * 校验sku指定客户价
     * @param goodsAudit
     * @param goodsInfos
     * @param skuByGoodsId
     * @param goodsInfoLevelPriceList
     */
    private Map<String,String>  checkCustomerPrice(GoodsAuditSaveVO goodsAudit, List<GoodsInfoSaveVO> goodsInfos, List<GoodsCustomerPrice> skuByGoodsId, List<GoodsLevelPrice> goodsInfoLevelPriceList) {
        if (Objects.equals(CheckStatus.FORBADE,goodsAudit.getAuditStatus())){
            return null;
        }

        List<GoodsInfo> oldGoodsInfos = goodsInfoRepository
                .findByGoodsIds(Collections.singletonList(goodsAudit.getOldGoodsId()));

        Map<String, List<GoodsCustomerPrice>> oldSkuCustomerPriceMap = goodsCustomerPriceRepository
                .findSkuByGoodsId(goodsAudit.getOldGoodsId())
                .stream()
                .collect(Collectors.groupingBy(GoodsCustomerPrice::getGoodsInfoId));
        Map<String, List<GoodsCustomerPrice>> skuCustomerPriceMap = skuByGoodsId.stream()
                .collect(Collectors.groupingBy(GoodsCustomerPrice::getGoodsInfoId));

        Map<String, List<GoodsLevelPrice>> oldSkuLevelPriceMap = goodsLevelPriceRepository
                .findSkuLevelPriceByGoodsId(goodsAudit.getOldGoodsId())
                .stream()
                .collect(Collectors.groupingBy(GoodsLevelPrice::getGoodsInfoId));
        Map<String, List<GoodsLevelPrice>> skuLevelPriceMap = goodsInfoLevelPriceList.stream()
                .collect(Collectors.groupingBy(GoodsLevelPrice::getGoodsInfoId));

        Map<String,String> goodsInfoMap = new HashMap<>();

        a:for (GoodsInfoSaveVO goodsInfo : goodsInfos) {

            GoodsInfo oldGoodsInfo = checkGoodsInfo(oldGoodsInfos, goodsInfoMap, goodsInfo);

            if(Objects.isNull(oldGoodsInfo)){
                continue ;
            }

            //将价格排序
            List<GoodsCustomerPrice> oldGoodsCustomerPriceList = oldSkuCustomerPriceMap.getOrDefault(oldGoodsInfo.getGoodsInfoId(),new ArrayList<>())
                    .stream()
                    .sorted(Comparator.comparing(GoodsCustomerPrice::getCustomerId))
                    .collect(Collectors.toList());
            List<GoodsCustomerPrice> goodsCustomerPriceList = skuCustomerPriceMap.getOrDefault(goodsInfo.getGoodsInfoId(),new ArrayList<>())
                    .stream()
                    .sorted(Comparator.comparing(GoodsCustomerPrice::getCustomerId))
                    .collect(Collectors.toList());

            if (goodsCustomerPriceList.size() != oldGoodsCustomerPriceList.size()){
                goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                continue;
            }

            for (int i = 0; i < goodsCustomerPriceList.size(); i++) {
                if (!Objects.equals(goodsCustomerPriceList.get(i).getCustomerId(),oldGoodsCustomerPriceList.get(i).getCustomerId())){
                    goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                    continue a;
                }
                if (goodsCustomerPriceList.get(i).getPrice().compareTo(oldGoodsCustomerPriceList.get(i).getPrice()) != 0){
                    goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                    continue a;
                }
            }

            //将价格排序
            List<GoodsLevelPrice> oldGoodsLevelPriceList = oldSkuLevelPriceMap.getOrDefault(oldGoodsInfo.getGoodsInfoId(),new ArrayList<>())
                    .stream()
                    .sorted(Comparator.comparing(GoodsLevelPrice::getLevelId))
                    .collect(Collectors.toList());
            List<GoodsLevelPrice> goodsLevelPriceList = skuLevelPriceMap.getOrDefault(goodsInfo.getGoodsInfoId(),new ArrayList<>())
                    .stream()
                    .sorted(Comparator.comparing(GoodsLevelPrice::getLevelId))
                    .collect(Collectors.toList());

            if (goodsLevelPriceList.size() != oldGoodsLevelPriceList.size()){
                goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                continue;
            }

            for (int i = 0; i < goodsLevelPriceList.size(); i++) {
                if (!Objects.equals(goodsLevelPriceList.get(i).getLevelId(),oldGoodsLevelPriceList.get(i).getLevelId())){
                    goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                    continue a;
                }
                if (Objects.nonNull(goodsLevelPriceList.get(i).getPrice()) && goodsLevelPriceList.get(i).getPrice().compareTo(oldGoodsLevelPriceList.get(i).getPrice()) != 0){
                    goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                    continue a;
                }
            }
        }
        return goodsInfoMap;
    }

    /**
     * 校验sku信息
     * @param oldGoodsInfos
     * @param goodsInfoMap
     * @param goodsInfo
     * @return
     */
    private GoodsInfo checkGoodsInfo(List<GoodsInfo> oldGoodsInfos, Map<String, String> goodsInfoMap, GoodsInfoSaveVO goodsInfo) {
        GoodsInfo oldGoodsInfo = oldGoodsInfos.stream()
                .filter(v -> Objects.equals(goodsInfo.getOldGoodsInfoId(), v.getGoodsInfoId()))
                .findFirst().orElse(null);

        if (Objects.isNull(oldGoodsInfo)) {
            return null;
        }

        if (!Objects.equals(oldGoodsInfo.getAloneFlag(), goodsInfo.getAloneFlag())) {
            goodsInfoMap.put(goodsInfo.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId());
            return null;
        }
        if (!Objects.equals(oldGoodsInfo.getLevelDiscountFlag(), goodsInfo.getLevelDiscountFlag())) {
            goodsInfoMap.put(goodsInfo.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId());
            return null;
        }
        return oldGoodsInfo;
    }

    /**
     * 校验sku批发价
     * @param goodsAudit
     * @param goodsInfos
     * @param skuIntervalPriceList
     * @param goodsInfoIntervalPrices
     */
    private Map<String,String> checkSkuIntervalPriceList(GoodsAuditSaveVO goodsAudit, List<GoodsInfoSaveVO> goodsInfos, List<GoodsIntervalPrice> skuIntervalPriceList, List<GoodsIntervalPriceVO> goodsInfoIntervalPrices) {
        if (Objects.equals(CheckStatus.FORBADE,goodsAudit.getAuditStatus())){
            return null;
        }

        List<GoodsInfo> oldGoodsInfos = goodsInfoRepository
                .findByGoodsIds(Collections.singletonList(goodsAudit.getOldGoodsId()));

        Map<String, List<GoodsIntervalPrice>> oldSkuIntervalPriceMap = goodsIntervalPriceRepository
                .findSkuByGoodsId(goodsAudit.getOldGoodsId())
                .stream()
                .collect(Collectors.groupingBy(GoodsIntervalPrice::getGoodsInfoId));
        Map<String, List<GoodsIntervalPrice>> skuIntervalPriceMap = skuIntervalPriceList.stream()
                .collect(Collectors.groupingBy(GoodsIntervalPrice::getGoodsInfoId));

        Map<String,String> goodsInfoMap = new HashMap<>();

        a:for (GoodsInfoSaveVO goodsInfo : goodsInfos) {

            GoodsInfo oldGoodsInfo = checkGoodsInfo(oldGoodsInfos, goodsInfoMap, goodsInfo);

            if(Objects.isNull(oldGoodsInfo)){
                continue;
            }

            //将价格排序
            List<GoodsIntervalPrice> goodsIntervalPriceList = oldSkuIntervalPriceMap.getOrDefault(oldGoodsInfo.getGoodsInfoId(),new ArrayList<>())
                    .stream()
                    .sorted(Comparator.comparing(GoodsIntervalPrice::getCount))
                    .collect(Collectors.toList());
            List<GoodsIntervalPrice> oldGoodsIntervalPriceList = skuIntervalPriceMap.getOrDefault(goodsInfo.getGoodsInfoId(),new ArrayList<>())
                    .stream()
                    .sorted(Comparator.comparing(GoodsIntervalPrice::getCount))
                    .collect(Collectors.toList());

            if (goodsIntervalPriceList.size() != oldGoodsIntervalPriceList.size()){
                goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                continue;
            }

            for (int i = 0; i < goodsIntervalPriceList.size(); i++) {
                if (!Objects.equals(goodsIntervalPriceList.get(i).getCount(),oldGoodsIntervalPriceList.get(i).getCount())){
                    goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                    continue a;
                }
                if (goodsIntervalPriceList.get(i).getPrice().compareTo(oldGoodsIntervalPriceList.get(i).getPrice()) != 0){
                    goodsInfoMap.put(goodsInfo.getGoodsInfoId(),oldGoodsInfo.getGoodsInfoId());
                    continue a;
                }
            }

        }
        return goodsInfoMap;
    }

    /**
     * 修改商品审核
     *
     * @author 黄昭
     */
    @Transactional(rollbackFor = {Exception.class})
    public GoodsAuditModifyResponse modify(GoodsAuditSaveRequest request) {
        GoodsAuditSaveDTO newGoodsAudit = request.getGoodsAudit();
        GoodsAudit finalGoods = goodsAuditRepository.findById(newGoodsAudit.getGoodsId()).orElse(null);
        GoodsAudit oldGoodsAudit = KsBeanUtil.convert(finalGoods, GoodsAudit.class);

        if (Objects.isNull(oldGoodsAudit)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        if (Objects.equals(CheckStatus.CHECKED, oldGoodsAudit.getAuditStatus())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030148);
        }
        //根据老商品Id和查询所有商品
        List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIds(Collections.singletonList(oldGoodsAudit.getOldGoodsId()));
        if (CollectionUtils.isNotEmpty(goodsAuditList)) {
            // 商品禁售中,不可在编辑未审核商品
            if (Objects.equals(CheckStatus.NOT_PASS, oldGoodsAudit.getAuditStatus()) && goodsAuditList.stream().anyMatch(goodsAudit -> Objects.equals(CheckStatus.FORBADE,
                    goodsAudit.getAuditStatus()))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030149);
            }
            // 待审核商品不可编辑
            if (goodsAuditList.stream().anyMatch(goodsAudit -> Objects.equals(CheckStatus.WAIT_CHECK,
                    goodsAudit.getAuditStatus()))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030148);
            }
        }

        //为供应商spu添加价格独立设置属性
        if (Objects.nonNull(newGoodsAudit.getProviderGoodsId())) {
            newGoodsAudit.setIsIndependent(request.getIsIndependent());
        }
        if (Objects.nonNull(oldGoodsAudit.getProviderGoodsId())) {
            oldGoodsAudit.setIsIndependent(request.getIsIndependent());
        }

        /****** 1.商品修改校验 ******/
        // 如果S2B模式下，商品已审核无法编辑分类
        // TODO 京东商品处理迁移到子类 如果是京东商品，同步时支持修改商品CateId
        if (GoodsSource.VOP.toValue() != oldGoodsAudit.getGoodsSource()) {
            if (osUtil.isS2b()
                    && CheckStatus.CHECKED.toValue() == oldGoodsAudit.getAuditStatus().toValue()
                    && (!oldGoodsAudit.getCateId().equals(newGoodsAudit.getCateId()))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030039);
            }
        }

        // 1.2 校验商品分类、商品品牌、是否存在
        // 1.3 如果是S2B，校验签约分类、签约品牌、店铺分类是否正确

        newGoodsAudit.setStoreId(oldGoodsAudit.getStoreId());
        goodsBaseInterface.checkBasic(KsBeanUtil.convert(newGoodsAudit, GoodsSaveDTO.class),1);

        //校验goodsNo和goodsInfoNo
        List<GoodsInfo> goodsInfos = KsBeanUtil.convert(request.getGoodsInfos(), GoodsInfo.class);
        checkNo(newGoodsAudit, oldGoodsAudit, goodsInfos);

        //填充商品默认数据
        populateGoodsAuditDefaultVal(request, newGoodsAudit, oldGoodsAudit);

        //更新goodsAudit
        newGoodsAudit.setAuditStatus(CheckStatus.WAIT_CHECK);
        goodsAuditRepository.save(KsBeanUtil.copyPropertiesThird(newGoodsAudit, GoodsAudit.class));

        //更新goodsImage
        updateGoodsImage(request, newGoodsAudit);

        //更新店铺分类
        updateGoodsStoreCateRel(newGoodsAudit);

        //更新商品属性
        updateGoodsPropertyDetailRel(request, newGoodsAudit);

        //更新商品详情模板关联实体
        updateGoodsTabRel(request, newGoodsAudit);

        //更新商品规格与规格详情
        updateGoodsSpecAndDetails(request, newGoodsAudit);

        //更新商品SKU
        updateGoodsInfos(request, newGoodsAudit);

        goodsAuditRepository.save(KsBeanUtil.copyPropertiesThird(newGoodsAudit, GoodsAudit.class));

        //更新商品
        GoodsSaveRequest saveRequest = goodsSaveRequestMapper.auditRequestToGoodsRequest(request);
        saveRequest.setGoods(KsBeanUtil.convert(newGoodsAudit, GoodsSaveDTO.class));

        goodsService.savePrice(saveRequest);

        //校验审核开关
        GoodsCheckRequest checkRequest = checkAudit(oldGoodsAudit, request);
        if (CollectionUtils.isNotEmpty(checkRequest.getGoodsIds())) {
            GoodsAuditModifyResponse response = new GoodsAuditModifyResponse();
            response.setCheckRequest(checkRequest);
            return response;
        }

        return null;
    }

    /**
     * 校验商品审核状态
     * @param oldGoodsAudit
     * @param request
     * @return
     */
    private GoodsCheckRequest checkAudit(GoodsAudit oldGoodsAudit, GoodsAuditSaveRequest request) {
        GoodsCheckRequest checkRequest = new GoodsCheckRequest();

        if (Objects.equals(GoodsSource.SELLER.toValue(), oldGoodsAudit.getGoodsSource())) {
            //商家商品
            if ((Objects.equals(AuditType.INITIAL_AUDIT.toValue(), oldGoodsAudit.getAuditType())
                    || Objects.equals(CheckStatus.FORBADE,oldGoodsAudit.getAuditStatus()))
                    && !auditQueryProvider.isBossGoodsAudit().getContext().isAudit()) {
                checkRequest.setGoodsIds(Collections.singletonList(request.getGoodsAudit().getGoodsId()));
                checkRequest.setAuditStatus(CheckStatus.CHECKED);
                checkRequest.setChecker(request.getUserId());
            } else if (Objects.equals(AuditType.SECOND_AUDIT.toValue(), oldGoodsAudit.getAuditType())
                    && !Objects.equals(CheckStatus.FORBADE,oldGoodsAudit.getAuditStatus())
                    && !auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest.builder().configType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit()) {
                //二次审核
                checkRequest.setGoodsIds(Collections.singletonList(request.getGoodsAudit().getGoodsId()));
                checkRequest.setAuditStatus(CheckStatus.CHECKED);
                checkRequest.setChecker(request.getUserId());
            }

        } else if (Objects.equals(GoodsSource.PROVIDER.toValue(), oldGoodsAudit.getGoodsSource())) {
            //供应商商品
            if ((Objects.equals(AuditType.INITIAL_AUDIT.toValue(), oldGoodsAudit.getAuditType())
                    || Objects.equals(CheckStatus.FORBADE,oldGoodsAudit.getAuditStatus()))
                    && !auditQueryProvider.isSupplierGoodsAudit().getContext().isAudit()) {
                checkRequest.setGoodsIds(Collections.singletonList(request.getGoodsAudit().getGoodsId()));
                checkRequest.setAuditStatus(CheckStatus.CHECKED);
                checkRequest.setChecker(request.getUserId());
                checkRequest.setDealStandardGoodsFlag(Boolean.TRUE);
            } else if (Objects.equals(AuditType.SECOND_AUDIT.toValue(), oldGoodsAudit.getAuditType())
                    && !Objects.equals(CheckStatus.FORBADE,oldGoodsAudit.getAuditStatus())
                    && !auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest.builder().configType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit()) {
                //二次审核
                checkRequest.setGoodsIds(Collections.singletonList(request.getGoodsAudit().getGoodsId()));
                checkRequest.setAuditStatus(CheckStatus.CHECKED);
                checkRequest.setChecker(request.getUserId());
                checkRequest.setDealStandardGoodsFlag(Boolean.TRUE);
            }
        }

        return checkRequest;
    }

    /**
     * 更新审核商品sku信息
     * @param request
     * @param newGoodsAudit
     */
    private void updateGoodsInfos(GoodsAuditSaveRequest request, GoodsAuditSaveDTO newGoodsAudit) {
        List<GoodsInfo> finalOldGoodsInfos;
        if (Objects.nonNull(newGoodsAudit.getProviderGoodsId())) {
            finalOldGoodsInfos = goodsInfoRepository.findByGoodsIdsAndDelFlag(Collections.singletonList(newGoodsAudit.getGoodsId()));
        } else {
            finalOldGoodsInfos = goodsInfoRepository.findByGoodsIds(Collections.singletonList(newGoodsAudit.getGoodsId()));
        }
        List<GoodsInfo> oldGoodsInfos = KsBeanUtil.convert(finalOldGoodsInfos, GoodsInfo.class);
        goodsInfoRepository.deleteByGoodsIds(Collections.singletonList(newGoodsAudit.getGoodsId()));

        List<GoodsInfoSaveDTO> goodsInfos = request.getGoodsInfos();
        // 最小sku市场价
        BigDecimal minPrice = newGoodsAudit.getMarketPrice();
        List<GoodsInfoSpecDetailRelDTO> detailRelList = new ArrayList<>();

        for (GoodsInfoSaveDTO sku : goodsInfos) {
            // 属性拷贝
            KsBeanUtil.copyPropertiesIgnoreNullVal(newGoodsAudit, sku, new String[]{"marketPrice", "stock", "addedFlag", "supplyPrice"});

            //供应商导入商品补充信息
            GoodsCate cate = this.goodsCateRepository.findById(newGoodsAudit.getCateId()).orElse(null);
            if (!Objects.isNull(cate) && !Objects.isNull(cate.getCatePath())) {
                sku.setCateTopId(Long.valueOf(cate.getCatePath().split("\\|")[1]));
            }
            Optional<GoodsInfo> opt = oldGoodsInfos.stream().filter(v -> Objects.equals(sku.getGoodsInfoId(), v.getGoodsInfoId())).findFirst();
            opt.ifPresent(goodsInfo -> {
                sku.setProviderGoodsInfoId(goodsInfo.getProviderGoodsInfoId());
                sku.setPluginType(goodsInfo.getPluginType());
                if(Objects.isNull(sku.getAddedFlag())) {
                    sku.setAddedFlag(goodsInfo.getAddedFlag());
                }
                if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
                    sku.setDelFlag(goodsInfo.getDelFlag());
                }
                sku.setIsBuyCycle(goodsInfo.getIsBuyCycle());
            });

            sku.setGoodsInfoName(newGoodsAudit.getGoodsName());
            if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(newGoodsAudit.getPriceType())
                    && newGoodsAudit.getMarketPrice() != null) {
                // XXX 后面商家boss新增商品页优化后，可以删除这部分逻辑
                sku.setMarketPrice(newGoodsAudit.getMarketPrice());
            }
            // SKU库存为空默认给0
            Nutils.isNullAction(sku.getStock(), 0L, sku::setStock);
            // 是否跟随SPU上下架状态
            if (!newGoodsAudit.getAddedFlag().equals(AddedFlag.PART.toValue())) {
                sku.setAddedFlag(newGoodsAudit.getAddedFlag());
            }
            sku.setAloneFlag(Boolean.FALSE);
            sku.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
            // 批发类型不支付购买积分
            if (Integer.valueOf(SaleType.WHOLESALE.toValue()).equals(newGoodsAudit.getSaleType())) {
                sku.setBuyPoint(0L);
            }
            String goodsInfoId = goodsInfoRepository.save(KsBeanUtil.copyPropertiesThird(sku, GoodsInfo.class)).getGoodsInfoId();
            sku.setGoodsInfoId(goodsInfoId);

            //删除该商品下规格关联信息
            goodsInfoSpecDetailRelRepository.deleteByGoodsId(newGoodsAudit.getGoodsId());
            // 如果是多规格,新增SKU与规格明细值的关联表
            if (Constants.yes.equals(newGoodsAudit.getMoreSpecFlag())) {
                WmCollectionUtils.notEmpty2Loop(request.getGoodsSpecs(), spec -> {
                    if (!sku.getMockSpecIds().contains(spec.getMockSpecId())) {
                        return;
                    }
                    request.getGoodsSpecDetails().stream()
                            .filter(detail -> spec.getMockSpecId().equals(detail.getMockSpecId())
                                    && sku.getMockSpecDetailIds().contains(detail.getMockSpecDetailId())).forEach(detail -> {
                                GoodsInfoSpecDetailRelDTO detailRel = new GoodsInfoSpecDetailRelDTO();
                                detailRel.setGoodsId(newGoodsAudit.getGoodsId());
                                detailRel.setGoodsInfoId(goodsInfoId);
                                detailRel.setSpecId(spec.getSpecId());
                                detailRel.setSpecDetailId(detail.getSpecDetailId());
                                detailRel.setDetailName(detail.getDetailName());
                                detailRel.setCreateTime(detail.getCreateTime());
                                detailRel.setUpdateTime(detail.getUpdateTime());
                                detailRel.setDelFlag(detail.getDelFlag());
                                detailRel.setSpecName(spec.getSpecName());
                                detailRelList.add(detailRel);
                            });
                });
            }

            if (minPrice == null || minPrice.compareTo(sku.getMarketPrice()) > 0) {
                minPrice = sku.getMarketPrice();
            }
        }
        // 判断是否需要保存详情-商品映射信息
        if (CollectionUtils.isNotEmpty(detailRelList)) {
            try {
                goodsInfoSpecDetailRelRepository.saveAll(KsBeanUtil.convertList(detailRelList, GoodsInfoSpecDetailRel.class));
            } catch (Exception e) {
                log.error("goodsInfoSpecDetailRelRepository -> save error,params is {}",
                        JSON.toJSONString(detailRelList));
                throw e;
            }
        }
        newGoodsAudit.setSkuMinMarketPrice(minPrice);
    }

    /**
     * 更新审核商品规格以及规格值
     * @param request
     * @param newGoodsAudit
     */
    private void updateGoodsSpecAndDetails(GoodsAuditSaveRequest request, GoodsAuditSaveDTO newGoodsAudit) {
        List<GoodsInfoSaveDTO> goodsInfos = request.getGoodsInfos();
        goodsSpecDetailRepository.deleteByGoodsId(newGoodsAudit.getGoodsId());
        goodsSpecRepository.deleteByGoodsId(newGoodsAudit.getGoodsId());
        // 如果不是多规格，不处理直接返回
        if (!Constants.yes.equals(newGoodsAudit.getMoreSpecFlag())) {
            return;
        }

        //  填放可用SKU相应的规格\规格值
        // （主要解决SKU可以前端删除，但相应规格值或规格仍然出现的情况）
        Map<Long, Integer> isSpecEnable = new HashMap<>(goodsInfos.size() * 2);
        Map<Long, Integer> isSpecDetailEnable = new HashMap<>(goodsInfos.size() * 2);

        for (GoodsInfoSaveDTO goodsInfo : goodsInfos) {
            goodsInfo.getMockSpecIds().forEach(specId -> isSpecEnable.put(specId, Constants.yes));
            goodsInfo.getMockSpecDetailIds().forEach(detailId -> isSpecDetailEnable.put(detailId, Constants.yes));
        }

        // 规格详情数量
        Map<Long, Boolean> specCount = new HashMap<>(8);

        // 新增规格
        // 过滤SKU有这个规格
        request.getGoodsSpecs().stream().filter(goodsSpec -> Constants.yes.equals(isSpecEnable.get(goodsSpec.getMockSpecId())))
                .forEach(goodsSpec -> {
                    goodsSpec.setCreateTime(newGoodsAudit.getCreateTime());
                    goodsSpec.setUpdateTime(newGoodsAudit.getUpdateTime());
                    goodsSpec.setGoodsId(newGoodsAudit.getGoodsId());
                    goodsSpec.setDelFlag(DeleteFlag.NO);
                    goodsSpec.setSpecId(null);
                    GoodsSpec spec = KsBeanUtil.convert(goodsSpec, GoodsSpec.class);
                    spec.setSpecId(goodsSpecRepository.save(spec).getSpecId());
                    spec.setOldSpecId(spec.getSpecId());
                    goodsSpecRepository.save(spec);
                    goodsSpec.setSpecId(spec.getSpecId());
                    goodsSpec.setOldSpecId(spec.getOldSpecId());
                });

        // 新增规格值
        // 过滤SKU有这个规格详情
        request.getGoodsSpecDetails().stream().filter(goodsSpecDetail -> Constants.yes.equals(isSpecDetailEnable.get(goodsSpecDetail.getMockSpecDetailId())))
                .forEach(goodsSpecDetail -> {
                    Optional<GoodsSpecSaveDTO> specOpt = request.getGoodsSpecs().stream().filter(goodsSpec -> goodsSpec.getMockSpecId().equals(goodsSpecDetail.getMockSpecId())).findFirst();
                    if (specOpt.isPresent()) {
                        goodsSpecDetail.setCreateTime(newGoodsAudit.getCreateTime());
                        goodsSpecDetail.setUpdateTime(newGoodsAudit.getUpdateTime());
                        goodsSpecDetail.setGoodsId(newGoodsAudit.getGoodsId());
                        goodsSpecDetail.setDelFlag(DeleteFlag.NO);
                        goodsSpecDetail.setSpecId(specOpt.get().getSpecId());
                        goodsSpecDetail.setSpecDetailId(null);
                        GoodsSpecDetail detail = KsBeanUtil.convert(goodsSpecDetail, GoodsSpecDetail.class);
                        detail.setSpecDetailId(goodsSpecDetailRepository.save(detail).getSpecDetailId());
                        detail.setOldSpecDetailId(detail.getSpecDetailId());
                        goodsSpecDetailRepository.save(detail);
                        goodsSpecDetail.setSpecDetailId(detail.getSpecDetailId());
                        goodsSpecDetail.setOldSpecDetailId(detail.getOldSpecDetailId());
                        specCount.put(goodsSpecDetail.getSpecDetailId(), Boolean.TRUE);
                    }
                });

        // 判断是否单规格标识
        if (request.getGoodsSpecDetails().stream().filter(s -> StringUtils.isNotBlank(s.getGoodsId())).count() > 1) {
            newGoodsAudit.setSingleSpecFlag(Boolean.FALSE);
        }
    }

    /**
     * 更新审核商品详情模板
     * @param request
     * @param newGoodsAudit
     */
    private void updateGoodsTabRel(GoodsAuditSaveRequest request, GoodsAuditSaveDTO newGoodsAudit) {
        List<GoodsTabRela> goodsTabRelas = KsBeanUtil.convert(request.getGoodsTabRelas(), GoodsTabRela.class);
        if (CollectionUtils.isNotEmpty(goodsTabRelas)) {
            goodsTabRelas.forEach(info -> {
                Nutils.isNullAction(info.getGoodsId(), newGoodsAudit.getGoodsId(), info::setGoodsId);
                goodsTabRelaRepository.save(info);
            });
        }
    }

    /**
     * 更新审核商品属性信息
     * @param request
     * @param newGoodsAudit
     */
    private void updateGoodsPropertyDetailRel(GoodsAuditSaveRequest request, GoodsAuditSaveDTO newGoodsAudit) {
        goodsPropertyDetailRelRepository.deleteByGoodsIdAndGoodsType(newGoodsAudit.getGoodsId(), GoodsPropertyType.GOODS);
        if (CollectionUtils.isNotEmpty(request.getGoodsPropertyDetailRel())) {
            // 把属性值为空的数据去除掉
            request.getGoodsPropertyDetailRel().forEach(detailRel ->
                    Nutils.isNullAction(detailRel.getGoodsId(), newGoodsAudit.getGoodsId(), detailRel::setGoodsId));
            // 保存该商品的属性
            goodsPropertyDetailRelRepository.saveAll(request.getGoodsPropertyDetailRel().stream().map(s -> {
                GoodsPropertyDetailRel rel = new GoodsPropertyDetailRel();
                KsBeanUtil.copyPropertiesThird(s, rel);
                return rel;
            }).collect(Collectors.toList()));
        }
    }

    /**
     * 更新审核商品店铺分类
     * @param newGoodsAudit
     */
    private void updateGoodsStoreCateRel(GoodsAuditSaveDTO newGoodsAudit) {
        storeCateGoodsRelaRepository.deleteByGoodsId(newGoodsAudit.getGoodsId());
        newGoodsAudit.getStoreCateIds().stream().distinct().forEach(cateId -> {
            StoreCateGoodsRela storeCateGoodsRel = new StoreCateGoodsRela();
            storeCateGoodsRel.setGoodsId(newGoodsAudit.getGoodsId());
            storeCateGoodsRel.setStoreCateId(cateId);
            storeCateGoodsRelaRepository.save(storeCateGoodsRel);
        });
    }

    /**
     * 更新审核商品图片
     * @param request
     * @param newGoodsAudit
     */
    private void updateGoodsImage(GoodsAuditSaveRequest request, GoodsAuditSaveDTO newGoodsAudit) {
        List<GoodsImage> oldImages = goodsImageRepository.findByGoodsId(newGoodsAudit.getGoodsId());
        if (CollectionUtils.isNotEmpty(oldImages)) {
            goodsImageRepository.deleteInBatch(oldImages);
        }
        if (CollectionUtils.isNotEmpty(request.getImages())) {
            request.getImages().forEach(goodsImage -> {
                goodsImage.setCreateTime(newGoodsAudit.getCreateTime());
                goodsImage.setUpdateTime(newGoodsAudit.getUpdateTime());
                goodsImage.setGoodsId(newGoodsAudit.getGoodsId());
                goodsImage.setDelFlag(DeleteFlag.NO);
                goodsImage.setImageId(null);
                GoodsImage image = KsBeanUtil.convert(goodsImage, GoodsImage.class);
                image.setImageId(goodsImageRepository.save(image).getImageId());
                goodsImage.setImageId(image.getImageId());
            });
        }
    }

    /**
     * 校验goodsNO
     * @param newGoodsAudit
     * @param oldGoodsAudit
     * @param goodsInfoList
     */
    private void checkNo(GoodsAuditSaveDTO newGoodsAudit, GoodsAudit oldGoodsAudit, List<GoodsInfo> goodsInfoList) {
        List<GoodsInfo> checkGoodsInfoList = KsBeanUtil.convert(goodsInfoList, GoodsInfo.class);
        //判断goodsNo是否重复
        if (!Objects.equals(newGoodsAudit.getGoodsNo(), oldGoodsAudit.getGoodsNo())) {
            GoodsQueryRequest queryRequest = new GoodsQueryRequest();
            queryRequest.setDelFlag(DeleteFlag.NO.toValue());
            queryRequest.setGoodsNo(newGoodsAudit.getGoodsNo());
            queryRequest.setNotGoodsId(newGoodsAudit.getOldGoodsId());
            GoodsAuditQueryRequest auditQueryRequest = new GoodsAuditQueryRequest();
            auditQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            auditQueryRequest.setGoodsNo(newGoodsAudit.getGoodsNo());
            auditQueryRequest.setNotGoodsId(newGoodsAudit.getGoodsId());
            Assert.assertIsZero(goodsRepository.count(queryRequest.getWhereCriteria()), GoodsErrorCodeEnum.K030036);
            Assert.assertIsZero(goodsAuditRepository.count(GoodsAuditWhereCriteriaBuilder.build(auditQueryRequest)), GoodsErrorCodeEnum.K030036);
        }

        //判断goodsInfoNo是否重复
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        List<GoodsInfo> newGoodsInfo = checkGoodsInfoList.stream().filter(goodsInfo -> StringUtils.isBlank(goodsInfo.getGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(newGoodsInfo)) {
            //新goodsInfo
            infoQueryRequest.setGoodsInfoNos(newGoodsInfo.stream().map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList()));
            infoQueryRequest.setDelFlag(Constants.ZERO);
            infoQueryRequest.setNotGoodsId(newGoodsAudit.getGoodsId());
            List<GoodsInfo> goodsInfosList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(goodsInfosList)) {

                List<String> goodsIds = goodsInfosList.stream().map(GoodsInfo::getGoodsId).distinct().collect(Collectors.toList());
                List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIds(goodsIds);
                List<String> goodsAuditIds = goodsAuditList.stream()
                        .filter(v -> !Objects.equals(v.getGoodsId(), newGoodsAudit.getGoodsId()))
                        .map(GoodsAudit::getGoodsId).collect(Collectors.toList());
                List<String> wrongGoodsInfoNos = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(goodsAuditIds)) {
                    for (String goodsAuditId : goodsAuditIds) {
                        wrongGoodsInfoNos.addAll(goodsInfosList.stream()
                                .filter(v -> Objects.equals(v.getGoodsId(), goodsAuditId))
                                .map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList()));
                    }
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030038,
                            new Object[]{StringUtils
                                    .join(wrongGoodsInfoNos, ",")});
                }
            }
        }
        checkGoodsInfoList.removeAll(newGoodsInfo);
        if (CollectionUtils.isNotEmpty(checkGoodsInfoList)) {
            //老goodsInfo
            List<GoodsInfo> oldGoodsInfosList = goodsInfoRepository.findByGoodsIds(Collections.singletonList(oldGoodsAudit.getGoodsId()));
            List<String> oldGoodsInfoNo = oldGoodsInfosList.stream().map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList());
            List<GoodsInfo> changedGoodsInfoList = checkGoodsInfoList.stream().filter(goodsInfo -> !oldGoodsInfoNo.contains(goodsInfo.getGoodsInfoNo())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(changedGoodsInfoList)) {
                infoQueryRequest.setGoodsInfoNos(changedGoodsInfoList.stream().map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList()));
                infoQueryRequest.setDelFlag(Constants.ZERO);
                List<GoodsInfo> goodsInfosList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
                if (CollectionUtils.isNotEmpty(goodsInfosList)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030038,
                            new Object[]{StringUtils
                                    .join(goodsInfosList.stream().map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList()), ",")});
                }
            }
        }
    }

    /**
     * 添加审核商品默认值
     * @param request
     * @param newGoodsAudit
     * @param oldGoodsAudit
     */
    private void populateGoodsAuditDefaultVal(GoodsAuditSaveRequest request, GoodsAuditSaveDTO newGoodsAudit, GoodsAudit oldGoodsAudit) {
        LocalDateTime currDate = LocalDateTime.now();
        newGoodsAudit.setUpdateTime(currDate);

        // 商家代销商品的可售性，修改时使用
        Boolean isDealGoodsVendibility = Boolean.FALSE;

        // 默认按市场定价
        Nutils.isNullAction(newGoodsAudit.getPriceType(), GoodsPriceType.MARKET.toValue(), newGoodsAudit::setPriceType);
        // 默认状态-上架
        Nutils.isNullAction(newGoodsAudit.getAddedFlag(), AddedFlag.YES.toValue(), newGoodsAudit::setAddedFlag);
        // 默认取第一张图片作为商品图片
        Nutils.getFirst(request.getImages(), image -> newGoodsAudit.setGoodsImg(image.getArtworkUrl()));
        // 默认不是多规格商品
        Nutils.isNullAction(newGoodsAudit.getMoreSpecFlag(), Constants.no, newGoodsAudit::setMoreSpecFlag);
        // 默认不按客户单独定价
        Nutils.isNullAction(newGoodsAudit.getCustomFlag(), Constants.no, newGoodsAudit::setCustomFlag);
        // 默认不叠加客户等级折扣
        Nutils.isNullAction(newGoodsAudit.getLevelDiscountFlag(), Constants.no, newGoodsAudit::setLevelDiscountFlag);

        // 如果勾选了定时上架时间
        if (Boolean.TRUE.equals(newGoodsAudit.getAddedTimingFlag())
                && Objects.nonNull(newGoodsAudit.getAddedTimingTime())) {
            if (newGoodsAudit.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0) {
                newGoodsAudit.setAddedFlag(AddedFlag.NO.toValue());
            } else {
                newGoodsAudit.setAddedFlag(AddedFlag.YES.toValue());
            }
        }
        // 上下架状态是否发生变化
        boolean isChgAddedTime = false;
        // 上下架更改商家代销商品的可售性
        if (!oldGoodsAudit.getAddedFlag().equals(newGoodsAudit.getAddedFlag())) {
            isChgAddedTime = true;
            isDealGoodsVendibility = Boolean.TRUE;
        }

        // 根据上下架状态是否发生变化设置上下架时间是当前时间还是等于之前时间
        if (isChgAddedTime) {
            newGoodsAudit.setAddedTime(currDate);
        } else {
            newGoodsAudit.setAddedTime(oldGoodsAudit.getAddedTime());
        }

        // 设价类型是否发生变化 -> 影响sku的独立设价状态为false
        boolean isChgPriceType = !newGoodsAudit.getPriceType().equals(oldGoodsAudit.getPriceType());

        // 如果设价方式变化为非按客户设价，则将spu市场价清空
        if (isChgPriceType
                && !GoodsPriceType.CUSTOMER.toIntegerValue().equals(newGoodsAudit.getPriceType())) {
            newGoodsAudit.setMarketPrice(null);
            newGoodsAudit.setCustomFlag(Constants.no);
        }
        if (SaleType.RETAIL.toValue() == newGoodsAudit.getSaleType()
                && GoodsPriceType.STOCK.toValue() == newGoodsAudit.getPriceType()) {
            newGoodsAudit.setPriceType(GoodsPriceType.MARKET.toValue());
        }

        if (GoodsPriceType.MARKET.toValue() == newGoodsAudit.getPriceType()) {
            newGoodsAudit.setMarketPrice(null);
        }

        KsBeanUtil.copyPropertiesThird(newGoodsAudit, oldGoodsAudit, new String[]{"auditStatus", "pluginType"});

        newGoodsAudit.setStock(request.getGoodsInfos().stream().filter(s -> Objects.nonNull(s.getStock())).mapToLong(GoodsInfoSaveDTO::getStock).sum());
        // 兼容云掌柜, 如果没有定时上下架功能, 则默认设置为false
        if (Objects.isNull(newGoodsAudit.getAddedTimingFlag())) {
            newGoodsAudit.setAddedTimingFlag(Boolean.FALSE);
        }
        // 如果勾选的定时上架时间
        if (Boolean.TRUE.equals(newGoodsAudit.getAddedTimingFlag()) && newGoodsAudit.getAddedTimingTime() != null) {
            if (newGoodsAudit.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0) {
                newGoodsAudit.setAddedFlag(AddedFlag.NO.toValue());
            } else {
                newGoodsAudit.setAddedFlag(AddedFlag.YES.toValue());
            }
        }
        newGoodsAudit.setPluginType(oldGoodsAudit.getPluginType());
    }

    /**
     * 更新审核商品供货价和库存
     * @param goodsInfo
     */
    private void updateGoodsInfoSupplyPriceAndStock(GoodsInfo goodsInfo) {
        //供应商库存
        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && goodsInfo.getThirdPlatformType() == null) {
            GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(goodsInfo.getProviderGoodsInfoId()).orElse(null);
            if (providerGoodsInfo != null) {
                goodsInfo.setStock(providerGoodsInfo.getStock());
                goodsInfo.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
            }
        }
    }


    /**
     * 批量删除商品审核
     *
     * @author 黄昭
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteByIdList(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return;
        }

        goodsAuditRepository.deleteByIdList(ids);
        //删除商品图片
        goodsImageRepository.deleteByGoodsIds(ids);
        //删除商品图片
        storeCateGoodsRelaRepository.deleteByGoodsIds(ids);
        //删除商品店铺分类
        goodsPropertyDetailRelRepository.deleteByGoodsId(ids);
        //删除商品规格
        goodsSpecRepository.deleteByGoodsIds(ids);
        //删除商品规格值
        goodsSpecDetailRepository.deleteByGoodsIds(ids);
        //删除商品规格关联规格值
        goodsInfoSpecDetailRelRepository.deleteByGoodsIds(ids);
        //删除商品sku
        goodsInfoRepository.deleteByGoodsIds(ids);
        //删除商品阶梯价
        goodsLevelPriceRepository.deleteByGoodsIds(ids);
        //删除商品会员设价
        goodsCustomerPriceRepository.deleteByGoodsIds(ids);
        //删除商品订货区间价
        goodsIntervalPriceRepository.deleteByGoodsIds(ids);

    }

    /**
     * 将实体包装成VO
     *
     * @author 黄昭
     */
    public GoodsAuditVO wrapperVo(GoodsAudit goodsAudit) {
        if (goodsAudit != null) {
            GoodsAuditVO goodsAuditVO = KsBeanUtil.convert(goodsAudit, GoodsAuditVO.class);
            return goodsAuditVO;
        }
        return null;
    }

    /**
     * 通过oldGoodsIds获取商品
     *
     * @param oldGoodsIds
     * @return
     */
    public List<String> getByOldGoodsIds(List<String> oldGoodsIds) {
        List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIds(oldGoodsIds);
        if (CollectionUtils.isNotEmpty(goodsAuditList)) {
            return goodsAuditList.stream().map(GoodsAudit::getGoodsId).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 通过oldGoodsIds获取商品
     *
     * @param oldGoodsIds
     * @return
     */
    public GoodsAuditListResponse getListByOldGoodsIds(List<String> oldGoodsIds) {
        GoodsAuditListResponse response = new GoodsAuditListResponse();
        List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIds(oldGoodsIds);
        if (CollectionUtils.isEmpty(goodsAuditList)) {
            response.setGoodsAuditVOList(new ArrayList<>());
            return response;
        }
        response.setGoodsAuditVOList(goodsAuditMapper.goodsAuditToVoList(goodsAuditList));
        return response;
    }

    /**
     * @description 查询总数量
     * @author 黄昭
     */
    public Long count(GoodsAuditQueryRequest queryReq) {
        return goodsAuditRepository.count(GoodsAuditWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 获取审核商品信息
     * @param providerGoodsId
     * @return
     */
    public GoodsAudit getGoodsAuditById(String providerGoodsId) {
        return goodsAuditRepository.findById(providerGoodsId).orElse(null);
    }

    /**
     * 校验审核商品是否可修改
     * @param goodsAuditVOList
     */
    public void checkEdit(List<GoodsAuditVO> goodsAuditVOList) {

        List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIdsAndAuditState(goodsAuditVOList.stream()
                .map(GoodsAuditVO::getOldGoodsId)
                .collect(Collectors.toList()), CheckStatus.FORBADE);

        for (GoodsAuditVO vo : goodsAuditVOList) {
            if (goodsAuditList.stream().map(GoodsAudit::getOldGoodsId).collect(Collectors.toList()).contains(vo.getOldGoodsId())) {
                vo.setEditFlag(BoolFlag.NO);
            }
        }
    }

    /**
     * 获取待审核审核商品
     * @param request
     * @return
     */
    public GoodsAuditListResponse getWaitCheckGoodsAudit(GoodsAuditQueryRequest request) {
        List<GoodsAudit> goodsAudits = goodsAuditRepository.findByOldGoodsIdsAndAuditState(request.getGoodsIdList(), CheckStatus.WAIT_CHECK);
        GoodsAuditListResponse response = new GoodsAuditListResponse();
        response.setGoodsAuditVOList(goodsAuditMapper.goodsAuditToVoList(goodsAudits));
        return response;
    }

    /**
     * 根据审核商品id批量获取审核商品信息
     * @param request
     * @return
     */
    public GoodsAuditListResponse getByIds(GoodsAuditQueryRequest request) {
        List<GoodsAudit> goodsAudits = goodsAuditRepository.findByGoodsIdList(request.getGoodsIdList());
        GoodsAuditListResponse response = new GoodsAuditListResponse();
        response.setGoodsAuditVOList(goodsAuditMapper.goodsAuditToVoList(goodsAudits));
        return response;
    }

    /**
     * 批量修改等级价
     * @param request
     */
    @Transactional(rollbackFor = {Exception.class})
    public void editLevelPrice(EditLevelPriceRequest request) {
        List<GoodsAudit> goodsAuditList = goodsAuditRepository
                .findByOldGoodsIdsAndAuditState(Collections.singletonList(request.getOldGoodsId()), CheckStatus.WAIT_CHECK);
        if (CollectionUtils.isNotEmpty(goodsAuditList)){
            GoodsAudit goodsAudit = goodsAuditList.get(0);
            List<GoodsInfo> goodsInfoList = goodsInfoRepository.findByGoodsIds(Collections.singletonList(goodsAudit.getGoodsId()));
            List<GoodsLevelPriceVO> goodsLevelPriceVOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(goodsInfoList)){
                for (GoodsInfo goodsInfo : goodsInfoList) {
                    PriceAdjustmentRecordDetailVO detailVO = request.getDetailVOList()
                            .stream()
                            .filter(v -> Objects.equals(v.getGoodsInfoId(), goodsInfo.getOldGoodsInfoId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.nonNull(detailVO)){
                        goodsInfo.setAloneFlag(detailVO.getAloneFlag());
                        if (Objects.equals(Boolean.TRUE,detailVO.getAloneFlag())){
                            goodsInfo.setMarketPrice(detailVO.getAdjustedMarketPrice());
                        }
                        Map<String, List<GoodsLevelPriceVO>> goodsLevelMap = JSON
                                .parseArray(detailVO.getLeverPrice(), GoodsLevelPriceVO.class)
                                .stream()
                                .filter(v -> Objects.nonNull(v.getPrice()))
                                .collect(Collectors.groupingBy(GoodsLevelPriceVO::getGoodsInfoId));
                        goodsLevelMap.getOrDefault(goodsInfo.getOldGoodsInfoId(),new ArrayList<>()).forEach(v->v.setGoodsInfoId(goodsInfo.getGoodsInfoId()));
                        if (Objects.nonNull(goodsLevelMap.get(goodsInfo.getOldGoodsInfoId()))){
                            goodsLevelPriceVOList.addAll(goodsLevelMap.get(goodsInfo.getOldGoodsInfoId()));
                        }

                    }
                }
            }
            goodsInfoRepository.saveAll(goodsInfoList);
            goodsLevelPriceRepository.deleteByGoodsInfoIds(goodsLevelPriceVOList
                    .stream()
                    .map(GoodsLevelPriceVO::getGoodsInfoId)
                    .collect(Collectors.toList()));
            List<GoodsLevelPrice> goodsLevelPriceList = KsBeanUtil.convert(goodsLevelPriceVOList, GoodsLevelPrice.class);
            goodsLevelPriceList.forEach(v->v.setGoodsId(goodsAudit.getGoodsId()));
            goodsLevelPriceRepository.saveAll(goodsLevelPriceList);
        }
    }

    /**
     * 修改商品批发价
     * @param request
     */
    @Transactional(rollbackFor = {Exception.class})
    public void editStockPrice(EditLevelPriceRequest request) {
        List<GoodsAudit> goodsAuditList = goodsAuditRepository
                .findByOldGoodsIdsAndAuditState(Collections.singletonList(request.getOldGoodsId()), CheckStatus.WAIT_CHECK);
        if (CollectionUtils.isNotEmpty(goodsAuditList)){
            GoodsAudit goodsAudit = goodsAuditList.get(0);
            List<GoodsInfo> goodsInfoList = goodsInfoRepository.findByGoodsIds(Collections.singletonList(goodsAudit.getGoodsId()));
            List<GoodsIntervalPriceVO> goodsIntervalPriceVOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(goodsInfoList)){
                for (GoodsInfo goodsInfo : goodsInfoList) {
                    PriceAdjustmentRecordDetailVO detailVO = request.getDetailVOList()
                            .stream()
                            .filter(v -> Objects.equals(v.getGoodsInfoId(), goodsInfo.getOldGoodsInfoId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.nonNull(detailVO)){
                        goodsInfo.setAloneFlag(detailVO.getAloneFlag());
                        if (Objects.nonNull(detailVO.getAdjustedMarketPrice())){
                            goodsInfo.setMarketPrice(detailVO.getAdjustedMarketPrice());
                        }
                        Map<String, List<GoodsIntervalPriceVO>> goodsStockMap = JSON
                                .parseArray(detailVO.getIntervalPrice(), GoodsIntervalPriceVO.class)
                                .stream()
                                .filter(v -> Objects.nonNull(v.getPrice()))
                                .collect(Collectors.groupingBy(GoodsIntervalPriceVO::getGoodsInfoId));
                        goodsStockMap.get(goodsInfo.getOldGoodsInfoId()).forEach(v->v.setGoodsInfoId(goodsInfo.getGoodsInfoId()));
                        goodsIntervalPriceVOList.addAll(goodsStockMap.get(goodsInfo.getOldGoodsInfoId()));
                    }
                }
            }
            goodsInfoRepository.saveAll(goodsInfoList);
            goodsIntervalPriceRepository.deleteByGoodsInfoIds(goodsIntervalPriceVOList
                    .stream()
                    .map(GoodsIntervalPriceVO::getGoodsInfoId)
                    .collect(Collectors.toList()));
            List<GoodsIntervalPrice> goodsIntervalPriceList = KsBeanUtil.convert(goodsIntervalPriceVOList, GoodsIntervalPrice.class);
            goodsIntervalPriceList.forEach(v->v.setGoodsId(goodsAudit.getGoodsId()));
            goodsIntervalPriceRepository.saveAll(goodsIntervalPriceList);
        }
    }

    /**
     * 按条件查询审核商品信息
     * @param queryRequest
     * @return
     */
    public List<GoodsAudit> findAll(GoodsAuditQueryRequest queryRequest) {
        return goodsAuditRepository.findAll(GoodsAuditWhereCriteriaBuilder.build(queryRequest));
    }
}

