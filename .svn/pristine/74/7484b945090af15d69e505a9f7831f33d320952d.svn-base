package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CompareUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsCustomerPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsLevelPriceQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsCheckRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyAllRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.request.price.GoodsCustomerPriceBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceListBySkuIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsLevelPriceBySkuIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsViewByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByGoodsIdresponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsCustomerPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsIntervalPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsLevelPriceDTO;
import com.wanmi.sbc.goods.bean.enums.AuditType;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.request.GoodsInfoByGoodsIdsRequest;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 17/4/12.
 */
@Tag(name = "GoodsInfoController", description = "商品服务 Api")
@RestController
@Validated
@Slf4j
public class GoodsInfoController {

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private GoodsLevelPriceQueryProvider goodsLevelPriceQueryProvider;

    @Autowired
    private GoodsCustomerPriceQueryProvider goodsCustomerPriceQueryProvider;

    @Autowired
    private GoodsIntervalPriceQueryProvider goodsIntervalPriceQueryProvider;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    @Autowired
    private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;


    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    /**
     * 获取商品SKU详情信息
     *
     * @param goodsInfoId 商品编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品SKU详情信息")
    @Parameter(name = "goodsInfoId", description = "sku Id", required = true)
    @RequestMapping(value = "/goods/sku/{goodsInfoId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoViewByIdResponse> info(@PathVariable String goodsInfoId) {
        GoodsInfoViewByIdRequest goodsInfoViewByIdRequest = new GoodsInfoViewByIdRequest();
        goodsInfoViewByIdRequest.setGoodsInfoId(goodsInfoId);
        goodsInfoViewByIdRequest.setBaseStoreId(commonUtil.getStoreId());
        BaseResponse<GoodsInfoViewByIdResponse> viewById = goodsInfoQueryProvider.getViewById(goodsInfoViewByIdRequest);
        GoodsInfoVO goodsInfo = viewById.getContext().getGoodsInfo();
        Long storeId = commonUtil.getStoreId();
        if(Objects.nonNull(storeId)){
            //填充当前商家的库存预警值
            StoreMessageNodeSettingVO storeMessageNodeSettingVO = storeMessageNodeSettingQueryProvider.getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
            if (Objects.nonNull(storeMessageNodeSettingVO)&& storeMessageNodeSettingVO.getStatus().equals(BoolFlag.YES)) {
                Long warningStock = storeMessageNodeSettingVO.getWarningStock();
                goodsBaseService.fillShowOutStockFlag(goodsInfo, warningStock);
            }
        }
        return viewById;
    }

    /**
     * 根据spuId获取商品SKU详情信息
     *
     * @param goodId 商品编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品SPU详情信息")
    @Parameter(name = "goodId", description = "spu Id", required = true)
    @RequestMapping(value = "/goods/skus/{goodId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoByGoodsIdresponse> skusByGoodsId(@PathVariable String goodId) {
        BaseResponse<GoodsInfoByGoodsIdresponse> byGoodsId = goodsInfoQueryProvider.getByGoodsId(DistributionGoodsChangeRequest.builder()
                .goodsId(goodId).showProviderInfoFlag(Boolean.TRUE).showVendibilityFlag(Boolean.TRUE).showSpecFlag(Boolean.TRUE).build());
        List<GoodsInfoVO> goodsInfoVOList = byGoodsId.getContext().getGoodsInfoVOList();
        //填充供应商名称
        storeBaseService.populateProviderName(goodsInfoVOList);
        Long storeId = commonUtil.getStoreId();
        if(Objects.nonNull(storeId)){
            //填充当前商家的库存预警值
            StoreMessageNodeSettingVO storeMessageNodeSettingVO =storeMessageNodeSettingQueryProvider.getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
            if (Objects.nonNull(storeMessageNodeSettingVO) && storeMessageNodeSettingVO.getStatus().equals(BoolFlag.YES)) {
                Long warningStock = storeMessageNodeSettingVO.getWarningStock();
                goodsBaseService.fillShowOutStockFlag(goodsInfoVOList, warningStock);
            }
        }
        return byGoodsId;
    }

    /**
     * 根据spuId获取商品SKU详情信息
     *
     * @param request 商品编号
     * @return 商品SKU列表
     */
    @Operation(summary = "获取商品SPU详情信息")
    @RequestMapping(value = "/goods/skus/spuId", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListByConditionResponse> skusByGoodsIds(@RequestBody @Valid GoodsInfoByGoodsIdsRequest request) {
        return goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                .goodsIds(request.getGoodsIds())
                .delFlag(DeleteFlag.NO.toValue())
                .showProviderInfoFlag(Boolean.TRUE)
                .showVendibilityFlag(Boolean.TRUE)
                .showSpecFlag(Boolean.TRUE)
                .notShowCrossGoodsFlag(request.getNotShowCrossGoodsFlag())
                .recordStatus(request.getRecordStatus())
                .addedFlag(request.getAddedFlag()).build());
    }

    /**
     * 编辑商品SKU
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑商品SKU")
    @RequestMapping(value = "/goods/sku", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> edit(@RequestBody GoodsInfoModifyRequest saveRequest) {
        if (saveRequest.getGoodsInfo() == null || saveRequest.getGoodsInfo().getGoodsInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //判断是否开启商品二次审核
        boolean supplierAudit = Objects.equals(commonUtil.getOperator().getPlatform(), Platform.SUPPLIER) &&
                auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                        .builder().configType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit();
        if (supplierAudit) {
            //获取商品详情
            GoodsViewByIdResponse response = goodsBaseService.info(saveRequest.getGoodsInfo().getGoodsId()).getContext();
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, saveRequest);
            goodsBaseService.edit(goodsModifyRequest);
        }
        GoodsInfoVO goodsInfoVO = goodsInfoQueryProvider
                .getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(saveRequest.getGoodsInfo().getGoodsInfoId()).build())
                .getContext()
                .getGoodsInfoVO();
        saveRequest.getGoodsInfo().setGoodsInfoNo(goodsInfoVO.getGoodsInfoNo());
        saveRequest.getGoodsInfo().setGoodsInfoImg(goodsInfoVO.getGoodsInfoImg());
        saveRequest.getGoodsInfo().setGoodsInfoBarcode(goodsInfoVO.getGoodsInfoBarcode());
        saveRequest.getGoodsInfo().setStock(goodsInfoVO.getStock());
        saveRequest.getGoodsInfo().setMarketPrice(goodsInfoVO.getMarketPrice());
        saveRequest.getGoodsInfo().setUpdateTime(LocalDateTime.now());

        goodsInfoProvider.modify(saveRequest);

        //刷新商品库es
        esStandardProvider.init(EsStandardInitRequest.builder()
                .relGoodsIds(Collections.singletonList(saveRequest.getGoodsInfo().getGoodsId())).build());

        //持化至ES
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                .skuIds(Collections.singletonList(saveRequest.getGoodsInfo().getGoodsInfoId())).build());


        operateLogMQUtil.convertAndSend("商品", "编辑商品", "编辑商品：SKU编码" + saveRequest.getGoodsInfo().getGoodsInfoNo());

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 编辑商品SKU
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑商品SKU")
    @RequestMapping(value = "/goods/sku/price", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> price(@RequestBody GoodsInfoPriceModifyRequest saveRequest) {
        if (saveRequest.getGoodsInfo() == null || saveRequest.getGoodsInfo().getGoodsInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
            BigDecimal marketPrice = saveRequest.getGoodsInfo().getMarketPrice();
            BigDecimal linePrice = saveRequest.getGoodsInfo().getLinePrice();
            if (Objects.nonNull(marketPrice) && Objects.nonNull(linePrice)) {
                if (linePrice.compareTo(marketPrice) < 0) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        //判断是否开启商品二次审核
        boolean supplierAudit = Objects.equals(commonUtil.getOperator().getPlatform(), Platform.SUPPLIER) &&
                auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                        .builder().configType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit();
        if (supplierAudit) {
            GoodsInfoVO goodsInfoVO =
                    goodsInfoQueryProvider
                            .getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(saveRequest.getGoodsInfo().getGoodsInfoId()).build())
                            .getContext()
                            .getGoodsInfoVO();
            GoodsInfoDTO oldDTO = KsBeanUtil.convert(goodsInfoVO, GoodsInfoDTO.class);
            GoodsInfoDTO newDTO = KsBeanUtil.convert(saveRequest.getGoodsInfo(),GoodsInfoDTO.class);
            if (CompareUtil.compareObject(
                    newDTO, oldDTO, new String[] {"stock","goodsStatus","updateTime"})) {
                supplierAudit = false;
            }
        }
        if (supplierAudit) {
            String goodsInfoId = saveRequest.getGoodsInfo().getGoodsInfoId();
            // 修改基本上下架信息
            goodsInfoProvider.modifyBaseInfo(saveRequest);

            //获取商品详情
            GoodsViewByIdResponse response = goodsBaseService.info(saveRequest.getGoodsInfo().getGoodsId()).getContext();
            // 判断设价是否有改动
            if (checkGoodsInfoAudit(saveRequest, response.getGoods().getPriceType())){
                //持化至ES
                esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                        .skuIds(Collections.singletonList(saveRequest.getGoodsInfo().getGoodsInfoId())).build());
                return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
            }
            GoodsInfoModifyRequest goodsInfoModifyRequest = new GoodsInfoModifyRequest();
            goodsInfoModifyRequest.setGoodsInfo(saveRequest.getGoodsInfo());
            GoodsModifyRequest goodsModifyRequest = goodsBaseService.viewToGoodsModify(response, goodsInfoModifyRequest);
            GoodsModifyAllRequest request  = KsBeanUtil.convert(goodsModifyRequest, GoodsModifyAllRequest.class);
            if(Objects.nonNull(request)) {
                request.setOldGoodsInfoId(goodsInfoId);
                request.setSkuEditPrice(BoolFlag.YES);
                saveRequest.setSkuEditPrice(BoolFlag.YES);
                request.setGoodsLevelPrices(KsBeanUtil.convert(saveRequest.getGoodsLevelPrices(),
                        GoodsLevelPriceVO.class));
                request.setGoodsIntervalPrices(KsBeanUtil.convert(saveRequest.getGoodsIntervalPrices(),
                        GoodsIntervalPriceVO.class));
                // 将goodsInfoId赋值
                if (CollectionUtils.isNotEmpty(request.getGoodsLevelPrices())){
                    request.getGoodsLevelPrices().forEach(g ->
                            g.setGoodsInfoId(goodsInfoId));
                }
                // 将goodsInfoId赋值
                if (CollectionUtils.isNotEmpty(request.getGoodsIntervalPrices())){
                    request.getGoodsIntervalPrices().forEach(g ->
                            g.setGoodsInfoId(goodsInfoId));
                }
                if (Objects.equals(Constants.ONE,saveRequest.getGoodsInfo().getCustomFlag())){
                    request.setGoodsCustomerPrices(KsBeanUtil.convert(saveRequest.getGoodsCustomerPrices(),
                            GoodsCustomerPriceVO.class));
                    // 将goodsInfoId赋值
                    if (CollectionUtils.isNotEmpty(request.getGoodsCustomerPrices())){
                        request.getGoodsCustomerPrices().forEach(goodsCustomerPriceVO ->
                                goodsCustomerPriceVO.setGoodsInfoId(goodsInfoId));
                    }
                }
                //如果sku不为独立设价,修改市场价无效
                if (Objects.equals(request.getGoods().getPriceType(),Constants.ZERO)
                && !Objects.equals(Boolean.TRUE,saveRequest.getGoodsInfo().getAloneFlag())){
                    Optional<GoodsInfoVO> opt = request.getGoodsInfos()
                            .stream()
                            .filter(goodsInfoVO -> Objects
                                    .equals(saveRequest.getGoodsInfo().getGoodsInfoId(), goodsInfoVO.getGoodsInfoId()))
                            .findFirst();
                    opt.ifPresent(goodsInfoVO -> goodsInfoVO.setMarketPrice(request.getGoods().getMarketPrice()));
                }

                //生成一份复制对象
                goodsBaseService.dealPrice(request);
            }
            //持化至ES
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                    .skuIds(Collections.singletonList(goodsInfoId)).build());
            return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
        }

        //校验定时上下架
        goodsBaseService.checkAddAndTakedownTime(saveRequest.getGoodsInfo().getTakedownTime(), saveRequest.getGoodsInfo().getTakedownTimeFlag(),
                saveRequest.getGoodsInfo().getAddedTimingTime(), saveRequest.getGoodsInfo().getAddedTimingFlag());

        goodsInfoProvider.modifyPrice(saveRequest);

        //持化至ES
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                .skuIds(Collections.singletonList(saveRequest.getGoodsInfo().getGoodsInfoId())).build());

        operateLogMQUtil.convertAndSend("商品", "设价",
                "设价：SKU编码" + saveRequest.getGoodsInfo().getGoodsInfoNo());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 比较sku是否修改
     * @param saveRequest
     * @return
     */
    private boolean checkGoodsInfoAudit(GoodsInfoPriceModifyRequest saveRequest, Integer priceType) {
        //比较sku
        GoodsInfoVO oldGoodsInfo = goodsInfoQueryProvider
                .getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(saveRequest.getGoodsInfo().getGoodsInfoId()).build())
                .getContext().getGoodsInfoVO();
        GoodsInfoDTO newGoodsInfo = saveRequest.getGoodsInfo();
        if (Objects.isNull(oldGoodsInfo)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030047);
        }
        if (!Objects.equals(newGoodsInfo.getGoodsInfoNo(), oldGoodsInfo.getGoodsInfoNo())) {
            return false;
        }

        if (Objects.nonNull(newGoodsInfo.getAloneFlag())){
            if (!newGoodsInfo.getAloneFlag().equals(oldGoodsInfo.getAloneFlag())){
                return false;
            }
        }

        if (Objects.nonNull(newGoodsInfo.getCustomFlag())){
            if (!newGoodsInfo.getCustomFlag().equals(oldGoodsInfo.getCustomFlag())){
                return false;
            }
        }

        if (Objects.nonNull(newGoodsInfo.getBuyPoint())){
            if (!(Objects.isNull(newGoodsInfo.getBuyPoint())?Constants.NUM_0L:newGoodsInfo.getBuyPoint())
                    .equals(Objects.isNull(oldGoodsInfo.getBuyPoint())?Constants.NUM_0L:oldGoodsInfo.getBuyPoint())){
                return false;
            }
        }

        if (Objects.nonNull(newGoodsInfo.getLevelDiscountFlag())){
            if (!newGoodsInfo.getLevelDiscountFlag().equals(oldGoodsInfo.getLevelDiscountFlag())){
                return false;
            }
        }

        if (!Objects.equals(newGoodsInfo.getGoodsInfoBarcode(), oldGoodsInfo.getGoodsInfoBarcode())) {
            return false;
        }
        if (!Objects.equals(newGoodsInfo.getGoodsInfoImg(), oldGoodsInfo.getGoodsInfoImg())) {
            return false;
        }
        if (Objects.nonNull(newGoodsInfo.getMarketPrice()) && Objects.isNull(saveRequest.getGoodsInfo().getAddRate())) {
            //如果商品是按客户设价 并且SKU没有打开独立设价 不比较市场价
            if (!Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(priceType)
                    || Objects.equals(Boolean.TRUE,saveRequest.getGoodsInfo().getAloneFlag())) {
                if (!(newGoodsInfo.getMarketPrice().compareTo(oldGoodsInfo.getMarketPrice()) == 0)) {
                    return false;
                }
            }
        }

        if (Objects.nonNull(newGoodsInfo.getBuyPoint())) {
            //获取积分设置
            boolean isGoodsPoint = systemPointsConfigService.isGoodsPoint();
            if (isGoodsPoint && !(newGoodsInfo.getBuyPoint().compareTo(oldGoodsInfo.getBuyPoint()) == 0)) {
                return false;
            }
        }

        // 比较库存
        if (!Objects.equals(newGoodsInfo.getStock(), oldGoodsInfo.getStock())) {
            return false;
        }
        //商品等级价格列表
        if (Objects.nonNull(saveRequest.getGoodsLevelPrices())) {

            List<GoodsLevelPriceVO> levelPriceList = goodsLevelPriceQueryProvider
                    .listBySkuIds(new GoodsLevelPriceBySkuIdsRequest(Collections.singletonList(newGoodsInfo.getGoodsInfoId())))
                    .getContext()
                    .getGoodsLevelPriceList();
            if (saveRequest.getGoodsLevelPrices().size() != levelPriceList.size()) {
                return false;
            }
            List<GoodsLevelPriceDTO> goodsLevelPriceList = saveRequest.getGoodsLevelPrices()
                    .stream()
                    .filter(v -> Objects.isNull(v.getLevelPriceId()))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(goodsLevelPriceList)) {
                return false;
            }
            List<GoodsLevelPriceDTO> goodsLevelPriceList1 = saveRequest.getGoodsLevelPrices()
                    .stream()
                    .filter(v -> Objects.nonNull(v.getLevelPriceId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsLevelPriceList1)) {
                for (GoodsLevelPriceVO levelPrice : levelPriceList) {
                    GoodsLevelPriceDTO goodsLevelPrice = goodsLevelPriceList1
                            .stream()
                            .filter(v -> Objects.equals(v.getLevelPriceId(), levelPrice.getLevelPriceId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(goodsLevelPrice)){
                        return false;
                    }
                    if (Objects.isNull(goodsLevelPrice.getPrice())) {
                        if (!Objects.equals(goodsLevelPrice.getPrice(), levelPrice.getPrice())) {
                            return false;
                        }
                    } else if (goodsLevelPrice.getPrice().compareTo(levelPrice.getPrice()) != 0){
                        return false;
                    }
                }
            }
        }
        //比较商品客户价格列表
        if (Objects.nonNull(saveRequest.getGoodsCustomerPrices())) {
            List<GoodsCustomerPriceVO> customerPriceList = goodsCustomerPriceQueryProvider
                    .listBySkuIds(new GoodsCustomerPriceBySkuIdsRequest(Collections.singletonList(newGoodsInfo.getGoodsInfoId())))
                    .getContext()
                    .getGoodsCustomerPriceVOList();
            if (saveRequest.getGoodsCustomerPrices().size() != customerPriceList.size()) {
                return false;
            }
            List<GoodsCustomerPriceDTO> goodsCustomerPriceList = saveRequest.getGoodsCustomerPrices()
                    .stream()
                    .filter(v -> Objects.isNull(v.getCustomerPriceId()))
                    .collect(Collectors.toList());
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(goodsCustomerPriceList)) {
                return false;
            }
            List<GoodsCustomerPriceDTO> goodsCustomerPriceList1 = saveRequest.getGoodsCustomerPrices()
                    .stream()
                    .filter(v -> Objects.nonNull(v.getCustomerPriceId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsCustomerPriceList1)) {
                for (GoodsCustomerPriceVO customerPrice : customerPriceList) {
                    GoodsCustomerPriceDTO goodsCustomerPrice = goodsCustomerPriceList1
                            .stream()
                            .filter(v -> Objects.equals(v.getCustomerPriceId(), customerPrice.getCustomerPriceId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(goodsCustomerPrice)){
                        return false;
                    }
                    if (Objects.isNull(goodsCustomerPrice.getPrice())) {
                        if (!Objects.equals(goodsCustomerPrice.getPrice(), customerPrice.getPrice())) {
                            return false;
                        }
                    } else if (goodsCustomerPrice.getPrice().compareTo(customerPrice.getPrice()) != 0){
                        return false;
                    }
                    if (!Objects.equals(goodsCustomerPrice.getCustomerId(),customerPrice.getCustomerId())){
                        return false;
                    }
                }
            }
        }
        //比较商品订货区间价格列表
        if (Objects.nonNull(saveRequest.getGoodsIntervalPrices())) {
            List<GoodsIntervalPriceVO> intervalPriceList = goodsIntervalPriceQueryProvider
                    .listByGoodsIds(new GoodsIntervalPriceListBySkuIdsRequest(Collections.singletonList(newGoodsInfo.getGoodsInfoId())))
                    .getContext()
                    .getGoodsIntervalPriceVOList();
            List<GoodsIntervalPriceDTO> savePriceList = saveRequest.getGoodsIntervalPrices()
                    .stream()
                    .filter(p -> Objects.nonNull(p.getPrice()))
                    .collect(Collectors.toList());
            if (savePriceList.size() != intervalPriceList.size()) {
                return false;
            }
            List<GoodsIntervalPriceDTO> savePriceList1 = saveRequest.getGoodsIntervalPrices().stream().filter(p ->
                    Objects.nonNull(p.getPrice())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(savePriceList1)) {
                for (GoodsIntervalPriceVO intervalPrice : intervalPriceList) {
                    GoodsIntervalPriceDTO goodsIntervalPrice = savePriceList1
                            .stream()
                            .filter(v -> Objects.equals(v.getIntervalPriceId(), intervalPrice.getIntervalPriceId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(goodsIntervalPrice)){
                        return false;
                    }
                    if (Objects.isNull(intervalPrice.getPrice())){
                        if (!Objects.equals(intervalPrice.getPrice(), goodsIntervalPrice.getPrice())) {
                            return false;
                        }
                    } else if (intervalPrice.getPrice().compareTo(goodsIntervalPrice.getPrice()) != 0){
                        return false;
                    }
                    if (!Objects.equals(intervalPrice.getCount(),goodsIntervalPrice.getCount())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取审核商品SKU详情信息
     *
     * @param goodsInfoId 商品编号
     * @return 商品详情
     */
    @Operation(summary = "获取审核商品SKU详情信息")
    @Parameter(name = "goodsInfoId", description = "sku Id", required = true)
    @RequestMapping(value = "/goods/audit/sku/{goodsInfoId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoViewByIdResponse> auditInfo(@PathVariable String goodsInfoId) {
        GoodsInfoViewByIdRequest goodsInfoViewByIdRequest = new GoodsInfoViewByIdRequest();
        goodsInfoViewByIdRequest.setGoodsInfoId(goodsInfoId);
        goodsInfoViewByIdRequest.setBaseStoreId(commonUtil.getStoreId());
        return goodsInfoQueryProvider.getAuditViewById(goodsInfoViewByIdRequest);
    }

    /**
     * 编辑审核商品SKU
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑审核商品SKU")
    @RequestMapping(value = "/goods/audit/sku", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> auditEdit(@RequestBody @Valid GoodsInfoModifyRequest saveRequest) {
        if (saveRequest.getGoodsInfo() == null || saveRequest.getGoodsInfo().getGoodsInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        GoodsAuditVO goodsAuditVO = goodsInfoProvider.modifyAudit(saveRequest).getContext().getGoodsAuditVO();
        if (Objects.isNull(goodsAuditVO)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        operateLogMQUtil.convertAndSend("商品", "编辑商品", "编辑商品：SKU编码" + saveRequest.getGoodsInfo().getGoodsInfoNo());

        //判断是否开启审核
        boolean isAudit = true;
        if (Objects.equals(commonUtil.getOperator().getPlatform(), Platform.SUPPLIER)){
            isAudit = auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                    .builder().configType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit();
        }else if (Objects.equals(commonUtil.getOperator().getPlatform(), Platform.PROVIDER)){
            isAudit = auditQueryProvider.isBossGoodsSecondaryAudit(GoodsSecondaryAuditRequest
                    .builder().configType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT).build()).getContext().isAudit();
        }
        // 处理商品是否需审核
        if (AuditType.INITIAL_AUDIT.toValue() == goodsAuditVO.getAuditType()
                || Objects.equals(CheckStatus.FORBADE, goodsAuditVO.getAuditStatus())) {
            isAudit = auditQueryProvider.isBossGoodsAudit().getContext().isAudit();
        }
        if (!isAudit) {
            GoodsCheckRequest request = new GoodsCheckRequest();
            request.setGoodsIds(Collections.singletonList(saveRequest.getGoodsInfo().getGoodsId()));
            request.setAuditStatus(CheckStatus.CHECKED);
            goodsBaseService.check(request);
        }

        // ============= 处理平台的消息发送：待审核列表，商家/供应商编辑商品触发待审核 START =============
        storeMessageBizService.handleForEditAuditGoods(saveRequest, isAudit);
        // ============= 处理平台的消息发送：待审核列表，商家/供应商编辑商品触发待审核 END =============

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    void fillShowOutStockFlag(GoodsInfoVO goodsInfoVO, Long warningStock) {
        if (Objects.isNull(goodsInfoVO) || Objects.isNull(warningStock)) {
            return;
        }
        if (Objects.equals(goodsInfoVO.getAuditStatus(), CheckStatus.CHECKED)
                && Objects.equals(goodsInfoVO.getAddedFlag(), NumberUtils.INTEGER_ONE)) {
            if (goodsInfoVO.getStock() < warningStock) {
                goodsInfoVO.setShowStockOutFlag(true);
            }
        }
    }
}
