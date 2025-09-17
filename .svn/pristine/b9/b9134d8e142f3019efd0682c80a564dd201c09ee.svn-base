package com.wanmi.sbc.goods.provider.impl.info;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsDeleteByIdsRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.api.response.info.*;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.distributor.goods.service.DistributorGoodsInfoService;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceConfigService;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.request.GoodsInfoSaveRequest;
import com.wanmi.sbc.goods.info.service.GoodsInfoExtraService;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsInfoStockTccService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.service.StandardImportService;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuService;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>对商品info操作接口</p>
 * Created by daiyitian on 2018-11-5-下午6:23.
 */
@RestController
@Validated
public class GoodsInfoController implements GoodsInfoProvider {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private DistributorGoodsInfoService distributorGoodsInfoService;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Autowired
    private StandardImportService standardImportService;

    @Autowired
    private WechatSkuService wechatSkuService;

    @Autowired
    private GoodsCommissionPriceConfigService goodsCommissionPriceConfigService;

    @Autowired
    private GoodsAuditRepository goodsAuditRepository;

    @Autowired
    private GoodsInfoExtraService goodsInfoExtraService;

    @Autowired
    private GoodsInfoStockTccService goodsInfoStockTccService;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    /**
     * 根据商品sku编号批量删除商品sku信息
     *
     * @param request 包含商品sku编号商品sku信息删除结构 {@link GoodsInfoDeleteByIdsRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override

    public BaseResponse deleteByIds(@RequestBody @Valid GoodsInfoDeleteByIdsRequest request) {
        goodsInfoService.delete(request.getGoodsInfoIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    @Transactional
    public BaseResponse<Boolean> updateByIds(@RequestBody @Valid GoodsInfoConsignIdRequest request) {
        if(Objects.nonNull(request.getDelFlag())&&request.getDelFlag().toValue() == 0
                &&Objects.nonNull(request.getGoodsInfos().getProviderGoodsInfoId())){
            GoodsInfoSaveVO providerGoodsInfo = goodsInfoService.findById(request.getGoodsInfos().getProviderGoodsInfoId()).getGoodsInfo();
            goodsInfoService.updateInfoBarImg(providerGoodsInfo.getGoodsInfoBarcode(), providerGoodsInfo.getGoodsInfoImg(), request.getGoodsInfoIds());
        }
        goodsInfoService.updateGood(request.getDelFlag(),request.getGoodsInfoIds());
        //处理 SPU
        //SPU 代销状态   验证是否全部都已取消
        List<GoodsInfo> goodsInfoList = goodsInfoService.queryBygoodsId(request.getGoodsInfos().getGoodsId(),Boolean.FALSE);
        if (CollectionUtils.isEmpty(goodsInfoList)
                || CollectionUtils.isEmpty(goodsInfoList.stream().filter(g -> !g.getGoodsInfoId().equals(request.getGoodsInfos().getGoodsInfoId())).collect(Collectors.toList()))) {
            GoodsDeleteByIdsRequest deleteRequest = new GoodsDeleteByIdsRequest();
            deleteRequest.setGoodsIds(Arrays.asList(request.getGoodsInfos().getGoodsId()));
            deleteRequest.setStoreId(request.getGoodsInfos().getStoreId());
            try{
                goodsService.delete(deleteRequest);
            } catch (SbcRuntimeException e) {
                if (GoodsErrorCodeEnum.K030035.getCode().equals(e.getErrorCode())) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030046);
                }
                throw e;
            } catch (Exception e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
            }
            return BaseResponse.success(Boolean.TRUE);
        }

        List<GoodsInfo> goodsInfos = goodsInfoList.stream().filter(g -> !g.getGoodsInfoId().equals(request.getGoodsInfos().getGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfos)) {
            Goods goods = goodsService.getGoodsById(request.getGoodsInfos().getGoodsId());
            //库存
            Long spuStock = goodsInfos.stream().map(GoodsInfo::getStock).reduce(0L, Long::sum);
            goods.setStock(spuStock);
            //市场价
            goodsInfos.sort(Comparator.comparing(GoodsInfo::getMarketPrice));
            goods.setMarketPrice(goodsInfos.get(0).getMarketPrice());
            goods.setSkuMinMarketPrice(goodsInfos.get(0).getMarketPrice());
            goodsService.save(goods);
        }
        return BaseResponse.success(Boolean.FALSE);
    }

    /**
     * 修改商品sku信息
     *
     * @param request 商品sku信息修改结构 {@link GoodsInfoModifyRequest}
     * @return 商品sku信息 {@link GoodsInfoModifyResponse}
     */
    @Override
    @Transactional
    public BaseResponse<GoodsInfoModifyResponse> modify(@RequestBody @Valid GoodsInfoModifyRequest request) {
        GoodsInfoSaveDTO info = new GoodsInfoSaveDTO();
        KsBeanUtil.copyPropertiesThird(request.getGoodsInfo(), info);
        GoodsInfoSaveRequest saveRequest = new GoodsInfoSaveRequest();
        saveRequest.setGoodsInfo(info);
        if(StringUtils.isNotBlank(request.getGoodsInfo().getProviderGoodsInfoId())){
            //更新sku的加价比例，若是存在
            this.updateCommissionPriceConfig(request.getGoodsInfo(), request.getGoodsInfo().getStoreId(), request.getUserId(), info.getGoodsId());
        }
        GoodsInfo newInfo = goodsInfoService.edit(saveRequest);
        Goods goods = goodsService.getGoodsById(newInfo.getGoodsId());
        //同步商品库上下架状态
        if (goods != null && GoodsSource.PROVIDER.toValue() == goods.getGoodsSource()) {
            StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByGoodsIdAndDelFlag(newInfo.getGoodsId(), DeleteFlag.NO);
            if (standardGoodsRel != null) {
                    standardGoodsRepository.updateAddedFlag(standardGoodsRel.getStandardId(), goods.getAddedFlag());
                    List<StandardSku> standardSkuList = standardSkuRepository.findByGoodsId(standardGoodsRel.getStandardId());
                    if (CollectionUtils.isNotEmpty(standardSkuList)) {
                        for (StandardSku standardSku : standardSkuList) {
                            if (newInfo.getGoodsInfoId().equals(standardSku.getProviderGoodsInfoId())) {
                                standardSkuRepository.updateAddedFlagAndGoodsInfoNo(standardSku.getGoodsInfoId(), newInfo.getAddedFlag(), newInfo.getGoodsInfoNo());
                            }
                        }
                    }
            }
        }
        GoodsInfoModifyResponse response = new GoodsInfoModifyResponse();
        KsBeanUtil.copyPropertiesThird(newInfo, response);
        return BaseResponse.success(response);
    }

    private void updateCommissionPriceConfig(GoodsInfoDTO goodsInfoVO, Long storeId, String userId, String goodsId) {
        //处理加价比例
        List<CommissionPriceTargetVO> commissionPriceTargetVOList = new ArrayList<>();
            if(Objects.nonNull(goodsInfoVO.getAddRate())){
                CommissionPriceTargetVO commissionPriceTargetVO = new CommissionPriceTargetVO();
                commissionPriceTargetVO.setEnableStatus(EnableStatus.ENABLE);
                commissionPriceTargetVO.setAddRate(goodsInfoVO.getAddRate());
                commissionPriceTargetVO.setTargetId(goodsInfoVO.getGoodsInfoId());
                commissionPriceTargetVO.setTargetType(CommissionPriceTargetType.SKU);
                commissionPriceTargetVOList.add(commissionPriceTargetVO);
            }
        if(CollectionUtils.isNotEmpty(commissionPriceTargetVOList)){
            GoodsCommissionPriceConfigUpdateRequest updateRequest = new GoodsCommissionPriceConfigUpdateRequest();
            updateRequest.setCommissionPriceTargetVOList(commissionPriceTargetVOList);
            updateRequest.setBaseStoreId(storeId);
            updateRequest.setUserId(userId);
            goodsCommissionPriceConfigService.update(updateRequest);
            goodsService.updateIsIndependent(EnableStatus.ENABLE, goodsId);
        }
    }


    /**
     * 修改商品sku设价信息
     *
     * @param request 商品sku设价信息修改结构 {@link GoodsInfoPriceModifyRequest}
     * @return 商品sku设价信息 {@link GoodsInfoPriceModifyResponse}
     */
    @Override
    @Transactional
    public BaseResponse<GoodsInfoPriceModifyResponse> modifyPrice(@RequestBody @Valid GoodsInfoPriceModifyRequest
                                                                          request) {
        GoodsInfoSaveRequest saveRequest = new GoodsInfoSaveRequest();
        GoodsInfoSaveDTO info = new GoodsInfoSaveDTO();
        KsBeanUtil.copyPropertiesThird(request.getGoodsInfo(), info);
        saveRequest.setGoodsInfo(info);
        //等级设价
        if (CollectionUtils.isNotEmpty(request.getGoodsLevelPrices())) {
            saveRequest.setGoodsLevelPrices(request.getGoodsLevelPrices());
        }
        //客户设价
        if (CollectionUtils.isNotEmpty(request.getGoodsCustomerPrices())) {
            saveRequest.setGoodsCustomerPrices(request.getGoodsCustomerPrices());
        }
        //区间设价
        if (CollectionUtils.isNotEmpty(request.getGoodsIntervalPrices())) {
            saveRequest.setGoodsIntervalPrices(request.getGoodsIntervalPrices());
        }

        if(StringUtils.isNotBlank(request.getGoodsInfo().getProviderGoodsInfoId())){
            //更新sku的加价比例，若是存在
            this.updateCommissionPriceConfig(request.getGoodsInfo(), request.getGoodsInfo().getStoreId(), request.getUserId(), info.getGoodsId());
        }

        saveRequest.setSkuEditPrice(request.getSkuEditPrice());
        GoodsInfo newInfo = goodsInfoService.editPrice(saveRequest);
        GoodsInfoPriceModifyResponse response = new GoodsInfoPriceModifyResponse();
        KsBeanUtil.copyPropertiesThird(newInfo, response);
        return BaseResponse.success(response);
    }

    /**
     * 修改商品sku基本上下架信息-二次审核
     *
     * @param request 修改商品sku基本上下架信息修改结构 {@link GoodsInfoPriceModifyRequest}
     * @return 修改商品sku基本上下架信息 {@link GoodsInfoPriceModifyResponse}
     */
    @Override
    @Transactional
    public BaseResponse modifyBaseInfo(@RequestBody @Valid GoodsInfoPriceModifyRequest
                                                   request) {
        GoodsInfoSaveRequest saveRequest = new GoodsInfoSaveRequest();
        GoodsInfoSaveDTO info = new GoodsInfoSaveDTO();
        KsBeanUtil.copyPropertiesThird(request.getGoodsInfo(), info);
        saveRequest.setGoodsInfo(info);
        if(StringUtils.isNotBlank(request.getGoodsInfo().getProviderGoodsInfoId())
                && Objects.nonNull(request.getGoodsInfo().getAddRate())){
            //更新sku的加价比例，若是存在
            this.updateCommissionPriceConfig(request.getGoodsInfo(), request.getGoodsInfo().getStoreId(),
                    request.getUserId(), request.getGoodsInfo().getGoodsId());
        } else {
            saveRequest.getGoodsInfo().setMarketPrice(null);
        }
        goodsInfoService.modifyBaseInfo(saveRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 修改商品sku上下架
     *
     * @param request 商品上下架修改结构 {@link GoodsInfoModifyAddedStatusRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override

    public BaseResponse modifyAddedStatus(@RequestBody @Valid GoodsInfoModifyAddedStatusRequest request) {
        goodsInfoService.updateAddedStatus(request.getAddedFlag(), request.getGoodsInfoIds());
        //判断是否购买视频号增值服务
        if (VASStatus.ENABLE.toValue().equals(redisService.hget(
                ConfigKey.VALUE_ADDED_SERVICES.toValue(), VASConstants.getBySellPlatformType(SellPlatformType.WECHAT_VIDEO).toValue()))) {
            //处理微信商品
            WechatShelveStatus wechatShelveStatus = request.getAddedFlag().equals(0) ? WechatShelveStatus.UN_SHELVE : WechatShelveStatus.SHELVE;
            WechatSkuQueryRequest queryRequest = WechatSkuQueryRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .editStatus(EditStatus.checked)
                    .goodsInfoIds(request.getGoodsInfoIds())
                    .build();
            if (request.getAddedFlag().equals(0)) {
                queryRequest.setWechatShelveStatus(WechatShelveStatus.SHELVE);
            }else {
                queryRequest.setWechatShelveStatusList(Arrays.asList(WechatShelveStatus.UN_SHELVE,WechatShelveStatus.VIOLATION_UN_SHELVE));
            }
            List<WechatSku> wechatSkuList = wechatSkuService.list(queryRequest);
            if (CollectionUtils.isNotEmpty(wechatSkuList)) {
                wechatSkuService.updateWecahtShelveStatus(wechatSkuList.stream().map(v->v.getGoodsId()).collect(Collectors.toList()), wechatShelveStatus);
            }
        }
        //异步通知处理
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        sendRequest.setGoodsInfoIds(request.getGoodsInfoIds());
        sendRequest.setFlag(AddedFlag.YES.toValue() == request.getAddedFlag() ? GoodsEditFlag.UP : GoodsEditFlag.DOWN);
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsInfoModifyAddedStatusByProviderResponse> modifyAddedStatusByProvider(
            @RequestBody @Valid GoodsInfoModifyAddedStatusByProviderRequest request) {
        return BaseResponse.success(
                goodsInfoService.updateAddedStatusByProvider(
                        request.getAddedFlag(), request.getProviderGoodsInfoIds()));
    }

    /**
     * 根据商品skuId增加商品sku库存
     *
     * @param request 包含skuId的商品sku库存增量结构 {@link GoodsInfoPlusStockByIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override

    public BaseResponse plusStockById(@RequestBody @Valid GoodsInfoPlusStockByIdRequest request) {
        goodsInfoService.addStockById(request.getStock(), request.getGoodsInfoId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量增量商品sku库存
     *
     * @param request 包含多个库存的sku库存增量结构 {@link GoodsInfoBatchPlusStockRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse batchPlusStock(@RequestBody @Valid GoodsInfoBatchPlusStockRequest request) {
        goodsInfoService.batchAddStock(request.getStockList());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据商品skuId扣除商品sku库存
     *
     * @param request 包含skuId的商品sku库存减量结构 {@link GoodsInfoMinusStockByIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override

    public BaseResponse minusStockById(@RequestBody @Valid GoodsInfoMinusStockByIdRequest request) {
        goodsInfoService.subStockById(request.getStock(), request.getGoodsInfoId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量扣除商品sku库存
     *
     * @param request 包含多个库存的sku库存减量结构 {@link GoodsInfoBatchMinusStockRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse batchMinusStock(@RequestBody @Valid GoodsInfoBatchMinusStockRequest request) {
        goodsInfoService.batchSubStock(request.getStockList());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量扣除赠品sku库存
     *
     * @param request 包含多个库存的sku库存减量结构 {@link GoodsInfoBatchMinusStockRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse<List<String>> batchMinusGiftStock(@RequestBody @Valid GoodsInfoBatchMinusStockRequest request) {
        return BaseResponse.success(goodsInfoService.batchSubGiftStock(request.getStockList()));
    }

    /**
     * 根据库存状态/上下架状态/相关店铺状态来填充商品数据的有效性
     *
     * @param request 商品列表数据结构 {@link GoodsInfoFillGoodsStatusRequest}
     * @return 包含商品有效状态的商品列表数据 {@link GoodsInfoFillGoodsStatusResponse}
     */
    @Override

    public BaseResponse<GoodsInfoFillGoodsStatusResponse> fillGoodsStatus(@RequestBody @Valid
                                                                                  GoodsInfoFillGoodsStatusRequest request) {
        List<GoodsInfoVO> goodsInfoList = KsBeanUtil.convert(request.getGoodsInfos(), GoodsInfoVO.class);
        goodsInfoList = goodsInfoService.fillGoodsStatus(goodsInfoList);
        return BaseResponse.success(GoodsInfoFillGoodsStatusResponse.builder()
                .goodsInfos(goodsInfoList).build());
    }

    @Override
    public BaseResponse updateSkuSmallProgram(@RequestBody @Valid
                                                      GoodsInfoSmallProgramCodeRequest request) {
        goodsInfoService.updateSkuSmallProgram(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse clearSkuSmallProgramCode() {
        goodsInfoService.clearSkuSmallProgramCode();
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 分销商品审核通过(单个)
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse checkDistributionGoods(@RequestBody @Valid DistributionGoodsCheckRequest request) {
        goodsInfoService.checkDistributionGoods(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量审核分销商品
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse batchCheckDistributionGoods(@RequestBody @Valid DistributionGoodsBatchCheckRequest request) {
        goodsInfoService.batchCheckDistributionGoods(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 驳回分销商品
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse refuseCheckDistributionGoods(@RequestBody @Valid DistributionGoodsRefuseRequest request) {
        goodsInfoService.refuseCheckDistributionGoods(request.getGoodsInfoId(),
                DistributionGoodsAudit.NOT_PASS,
                request.getDistributionGoodsAuditReason());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 禁止分销商品
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse forbidCheckDistributionGoods(@RequestBody @Valid DistributionGoodsForbidRequest request) {
        goodsInfoService.refuseCheckDistributionGoods(request.getGoodsInfoId(), DistributionGoodsAudit.FORBID,
                request.getDistributionGoodsAuditReason());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除分销商品
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse delDistributionGoods(@RequestBody @Valid DistributionGoodsDeleteRequest request) {
        goodsInfoService.delDistributionGoods(request);
        // 同步删除分销员与商品关联表
        distributorGoodsInfoService.deleteByGoodsInfoId(request.getGoodsInfoId());

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 添加分销商品
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<DistributionGoodsAddResponse> addDistributionGoods(@RequestBody @Valid DistributionGoodsAddRequest request) {
        DistributionGoodsAddResponse goodsAddResponse = new DistributionGoodsAddResponse();
        if (CollectionUtils.isEmpty(request.getDistributionGoodsInfoModifyDTOS())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> goodsInfoIds = request.getDistributionGoodsInfoModifyDTOS().stream().map(DistributionGoodsInfoModifyDTO::getGoodsInfoId).collect(Collectors.toList());
        // 添加分销商品前，验证所添加的sku是否符合条件
        List<String> invalidList = goodsInfoService.getInvalidGoodsInfoByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(invalidList)) {
            goodsAddResponse.setGoodsInfoIds(invalidList);
        } else {
            for (DistributionGoodsInfoModifyDTO modifyDTO : request.getDistributionGoodsInfoModifyDTOS()) {
                BigDecimal commissionRate = modifyDTO.getCommissionRate();
                if (Objects.isNull(commissionRate)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                double rate = commissionRate.doubleValue();
                if ( rate< 0.01 || rate> 0.99 ) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                goodsInfoService.addCommissionDistributionGoods(modifyDTO.getGoodsInfoId(), commissionRate, modifyDTO.getDistributionCommission()
                        , request.getDistributionGoodsAudit());
            }
        }
        return BaseResponse.success(goodsAddResponse);
    }

    /**
     * 已审核通过 编辑分销商品佣金
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse modifyDistributionGoodsCommission(@RequestBody @Valid DistributionGoodsModifyRequest request) {
        BigDecimal commissionRate = request.getCommissionRate();
        if (Objects.isNull(commissionRate)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        double rate = commissionRate.doubleValue();
        if ( rate < 0.01 || rate > 0.99 ) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsInfoService.modifyCommissionDistributionGoods(request.getGoodsInfoId(), commissionRate,
                request.getDistributionCommission(), DistributionGoodsAudit.CHECKED);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 审核未通过或禁止分销的商品重新编辑后，状态为待审核
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse modifyDistributionGoods(@RequestBody @Valid DistributionGoodsModifyRequest request) {
        goodsInfoService.modifyCommissionDistributionGoods(request.getGoodsInfoId(), request.getCommissionRate()
                , request.getDistributionCommission(), DistributionGoodsAudit.WAIT_CHECK);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 商品ID<spu> 修改商品审核状态
     *
     * @param request
     */
    @Override
    public BaseResponse distributeTogeneralGoods(@RequestBody @Valid DistributionGoodsChangeRequest request) {
        goodsInfoService.modifyDistributeState(request.getGoodsId(), DistributionGoodsAudit.COMMON_GOODS);
        distributorGoodsInfoService.deleteByGoodsId(request.getGoodsId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 供应商商品库存同步
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ProviderGoodsStockSyncResponse> providerGoodsStockSync(@RequestBody @Valid ProviderGoodsStockSyncRequest request) {
        List<GoodsInfoVO> goodsInfoList = KsBeanUtil.convert(request.getGoodsInfoList(), GoodsInfoVO.class);
        goodsInfoService.fillSupplyPriceAndStock(goodsInfoList);
        return BaseResponse.success(ProviderGoodsStockSyncResponse.builder()
                .goodsInfoList(goodsInfoList).build());
    }

    @Override
    public BaseResponse<GoodsInfoModifySimpleBySkuNoResponse> modifySimpleBySkuNo(
            @RequestBody @Valid GoodsInfoModifySimpleBySkuNoRequest request) {
        GoodsSaveDTO goods = goodsInfoService.modifySimpleBySkuNo(request);
        standardImportService.synProviderGoods(goods);
        return BaseResponse.success(
                GoodsInfoModifySimpleBySkuNoResponse.builder().goodsId(goods.getGoodsId()).build());
    }

    @Override
    public BaseResponse checkStock(@RequestBody @Valid GoodsInfoBatchMinusStockRequest request) {
        goodsInfoService.checkStock(request.getStockList());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<QueryGoodsInfoRedisStockResponse> queryGoodsInfoRedisStock(@RequestBody @Valid QueryGoodsInfoRedisStockRequest request) {
        List<GoodsInfoRedisStockVO> redisStockDTOList = goodsInfoService.getRedisStock(request.getGoodsInfoIdList());
        return BaseResponse.success(QueryGoodsInfoRedisStockResponse.builder().goodsInfoRedisStockVOList(redisStockDTOList).build());
    }

    @Override
    public BaseResponse updateSupplyPrice(@Valid GoodsInfoUpdateSupplyPriceRequest request) {
        goodsInfoService.updateSupplyPrice(request.getGoodsInfos());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchModifyStock(@Valid GoodsInfoBatchStockRequest request) {
        goodsInfoService.batchModifyStockById(request.getStockList());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsAuditModifyResponse> modifyAudit(GoodsInfoModifyRequest request) {

        GoodsAudit oldGoodsAudit = goodsAuditRepository.getOne(request.getGoodsInfo().getGoodsId());

        if (Objects.isNull(oldGoodsAudit)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsAuditVO goodsAuditVO = KsBeanUtil.convert(oldGoodsAudit, GoodsAuditVO.class);

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

        GoodsInfoSaveDTO info = new GoodsInfoSaveDTO();
        KsBeanUtil.copyPropertiesThird(request.getGoodsInfo(), info);
        GoodsInfoSaveRequest saveRequest = new GoodsInfoSaveRequest();
        saveRequest.setGoodsInfo(info);
        if(StringUtils.isNotBlank(request.getGoodsInfo().getProviderGoodsInfoId())){
            //更新sku的加价比例，若是存在
            this.updateCommissionPriceConfig(request.getGoodsInfo(), request.getGoodsInfo().getStoreId(), request.getUserId(), info.getGoodsId());
        }

        goodsInfoService.editAudit(saveRequest);

        return BaseResponse.success(GoodsAuditModifyResponse.builder().goodsAuditVO(goodsAuditVO).build());
    }

    @Override
    public BaseResponse<GoodsInfoSyncSpuResponse> syncSpu(@RequestBody @Valid GoodsInfoSyncSpuRequest request) {
        return BaseResponse.success(goodsInfoExtraService.syncSpu(request));
    }


    /**
     * 批量增量商品sku库存
     *
     * @param request 包含多个库存的sku库存增量结构 {@link GoodsInfoBatchPlusStockRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse batchPlusStockTcc(@RequestBody @Valid GoodsInfoBatchPlusStockRequest request) {
        goodsInfoStockTccService.addStock(request.getStockList());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量扣除商品sku库存
     *
     * @param request 包含多个库存的sku库存减量结构 {@link GoodsInfoBatchMinusStockRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse batchMinusStockTcc(@RequestBody @Valid GoodsInfoBatchMinusStockRequest request) {
        goodsInfoStockTccService.subStock(request.getStockList());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量扣除赠品sku库存
     *
     * @param request 包含多个库存的sku库存减量结构 {@link GoodsInfoBatchMinusStockRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse<List<String>> batchMinusGiftStockTcc(@RequestBody @Valid GoodsInfoBatchMinusStockRequest request) {

        return BaseResponse.success(goodsInfoStockTccService.subGifStock(request.getStockList()));
    }

    @Override
    public BaseResponse vopGoodsSyncCheck(@RequestBody @Valid VopGoodsSyncCheckRequest checkRequest) {
        // 查询商品是否存在
        List<GoodsInfo> goodsInfoList = goodsInfoService.queryByThirdPlatformSkuIds(checkRequest.getThirdPlatformSkuIdList(), GoodsSource.VOP.toValue());
        if(CollectionUtils.isEmpty(goodsInfoList)) {
            return BaseResponse.SUCCESSFUL();
        }
        // 下架的goodsInfoId
        List<String> takeDownList = new ArrayList<>();
        // 上架商品
        List<String> addedList = new ArrayList<>();

        if (Objects.isNull(checkRequest.getGoodsId())) {
            takeDownList = goodsInfoList.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        } else {
            takeDownList = goodsInfoList.stream()
                    .filter(goodsInfo -> !Objects.equals(checkRequest.getGoodsId(), goodsInfo.getGoodsId()))
                    .map(GoodsInfo::getGoodsInfoId)
                    .collect(Collectors.toList());

//            addedList = goodsInfoList.stream()
//                    .filter(goodsInfo -> Objects.equals(checkRequest.getGoodsId(), goodsInfo.getGoodsId()) && goodsInfo.getAddedFlag() != 1)
//                    .map(GoodsInfo::getGoodsInfoId)
//                    .collect(Collectors.toList());
        }
        // 批量上架
        if (CollectionUtils.isNotEmpty(addedList)) {
            goodsInfoService.updateAddedStatusByProvider(
                    AddedFlag.YES, addedList);
        }
        // 批量下架
        if (CollectionUtils.isNotEmpty(takeDownList)) {
            goodsInfoService.updateAddedStatusByProvider(
                    AddedFlag.NO, takeDownList);
        }
        // 跟新商品信息
        if (CollectionUtils.isNotEmpty(addedList) || CollectionUtils.isNotEmpty(takeDownList)) {
            List<String> goodsIds = goodsInfoList.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList());
            //更新ES
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(goodsIds).build());
            esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                    storeId(null).providerGoodsIds(goodsIds).build());
            goodsIds.forEach(goodsId -> {
                //更新redis商品基本数据
                String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
                if (StringUtils.isNotBlank(goodsDetailInfo)) {
                    redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
                }
            });
        }
        return BaseResponse.SUCCESSFUL();
    }


    @Override
    public BaseResponse vopGoodsDownAdded(@RequestBody @Valid VopGoodsDownAddedRequest checkRequest) {

        // 查询商品是否存在
        List<GoodsInfo> goodsInfoList = goodsInfoService.queryByThirdPlatformSkuIds(Arrays.asList(checkRequest.getThirdPlatformSkuId()), GoodsSource.VOP.toValue());
        if(CollectionUtils.isEmpty(goodsInfoList)) {
            return BaseResponse.SUCCESSFUL();
        }
        List<String> providerGoodsInfoIds = goodsInfoList.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        goodsInfoService.updateAddedStatusByProvider(AddedFlag.NO, providerGoodsInfoIds);

        //刷新ES
        List<String> changeList = new ArrayList<>(providerGoodsInfoIds);
        if (CollectionUtils.isNotEmpty(changeList)) {
            //更新ES
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(changeList).build());
            esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                    storeId(null).providerGoodsIds(changeList).build());
            providerGoodsInfoIds.forEach(goodsId -> {
                //更新redis商品基本数据
                String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
                if (StringUtils.isNotBlank(goodsDetailInfo)) {
                    redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
                }
            });
        }

        return BaseResponse.SUCCESSFUL();
    }

}
