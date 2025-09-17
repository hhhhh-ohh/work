package com.wanmi.sbc.goods.common;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.OsUtil;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.request.common.GoodsCommonBatchAddRequest;
import com.wanmi.sbc.goods.api.request.common.InfoForPurchaseRequest;
import com.wanmi.sbc.goods.api.request.common.OperationLogAddRequest;
import com.wanmi.sbc.goods.api.request.goods.BatchGoodsStockRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.common.GoodsInfoForPurchaseResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByIdsResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardImportStandardRequest;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodsrestrictedsale.service.GoodsRestrictedSaleService;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsRequest;
import com.wanmi.sbc.goods.info.service.GoodsCacheService;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsInfoStockService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.marketing.model.data.GoodsMarketing;
import com.wanmi.sbc.goods.marketing.service.GoodsMarketingService;
import com.wanmi.sbc.goods.message.StoreMessageBizService;
import com.wanmi.sbc.goods.mq.ProducerService;
import com.wanmi.sbc.goods.price.model.root.GoodsCustomerPrice;
import com.wanmi.sbc.goods.price.repository.GoodsCustomerPriceRepository;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.standard.service.StandardImportService;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponUpdateBindRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.GoodsSecondaryAuditRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressListRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressVO;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品公共服务
 * Created by daiyitian on 2017/4/11.
 */
@Primary
@Service
@Slf4j
public class GoodsCommonService implements GoodsCommonServiceInterface {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsImageRepository goodsImageRepository;

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private GoodsCacheService goodsCacheService;

    @Autowired
    private GoodsMarketingService goodsMarketingService;

    @Autowired
    private GoodsCustomerPriceRepository goodsCustomerPriceRepository;

    @Autowired
    private GoodsRestrictedSaleService goodsRestrictedSaleService;

    @Autowired
    private ThirdAddressQueryProvider thirdAddressQueryProvider;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    protected ProducerService producerService;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StandardImportService standardImportService;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private GoodsAuditRepository goodsAuditRepository;

    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    @Autowired
    private GoodsInfoStockService goodsInfoStockService;

    /**
     * 批量导入商品数据
     * @param request 商品批量信息
     * @return 批量新增的skuId
     */
    @Override
    public List<String> batchAdd(GoodsCommonBatchAddRequest request){
        Map<String, List<BatchGoodsInfoDTO>> skus = request.getGoodsInfoList().stream()
                .collect(Collectors.groupingBy(BatchGoodsInfoDTO::getMockGoodsId));

        Map<String, List<BatchGoodsSpecDTO>> allSpecs = new HashMap<>();
        if (CollectionUtils.isNotEmpty(request.getSpecList())) {
            allSpecs.putAll(request.getSpecList().stream()
                    .collect(Collectors.groupingBy(BatchGoodsSpecDTO::getMockGoodsId)));
        }

        Map<String, List<BatchGoodsSpecDetailDTO>> allSpecDetails = new HashMap<>();
        if (CollectionUtils.isNotEmpty(request.getSpecList())) {
            allSpecDetails.putAll(request.getSpecDetailList().stream()
                    .collect(Collectors.groupingBy(BatchGoodsSpecDetailDTO::getMockGoodsId)));
        }

        Map<String, List<BatchGoodsImageDTO>> images = new HashMap<>();
        if (CollectionUtils.isNotEmpty(request.getImageList())) {
            images.putAll(request.getImageList().stream()
                    .collect(Collectors.groupingBy(BatchGoodsImageDTO::getMockGoodsId)));
        }

        List<String> newSkuIds = new ArrayList<>();
        List<Long> electronicIds = new ArrayList<>();
        request.getGoodsList().forEach(goods -> {
            goods.setCreateTime(LocalDateTime.now());
            goods.setAddedTime(goods.getCreateTime());
            goods.setUpdateTime(goods.getCreateTime());
            goods.setCustomFlag(Constants.no);
            goods.setLevelDiscountFlag(Constants.no);
            goods.setDelFlag(DeleteFlag.NO);
            goods.setPriceType(GoodsPriceType.MARKET.toValue());

            List<BatchGoodsInfoDTO> goodsInfoList = skus.get(goods.getGoodsNo());
            goods.setMoreSpecFlag(Constants.no);
            if (goodsInfoList.stream().anyMatch(goodsInfo -> CollectionUtils.isNotEmpty(goodsInfo.getMockSpecDetailIds()))) {
                goods.setMoreSpecFlag(Constants.yes);
            }

            //判定上下架
            long yes_addedFlag = goodsInfoList.stream().filter(goodsInfo -> AddedFlag.YES.toValue() == goodsInfo.getAddedFlag()).count();
            goods.setAddedFlag(AddedFlag.PART.toValue());
            if (goodsInfoList.size() == yes_addedFlag) {
                goods.setAddedFlag(AddedFlag.YES.toValue());
            } else if (yes_addedFlag == 0) {
                goods.setAddedFlag(AddedFlag.NO.toValue());
            }

            GoodsSaveDTO tempGoods = KsBeanUtil.convert(goods, GoodsSaveDTO.class);
            this.setCheckState(tempGoods);
            goods.setAuditStatus(tempGoods.getAuditStatus());
            tempGoods.setGoodsSalesNum(0L);
            tempGoods.setGoodsCollectNum(0L);
            tempGoods.setGoodsEvaluateNum(0L);
            tempGoods.setGoodsFavorableCommentNum(0L);
            tempGoods.setStock(goodsInfoList.stream().filter(g -> g.getStock() != null).mapToLong(BatchGoodsInfoDTO::getStock).sum());
            // 修改扩展点
            tempGoods = beforeGoodsSave(tempGoods);
            String goodsId;
            if (Objects.equals(CheckStatus.CHECKED,tempGoods.getAuditStatus())){
                goodsId = goodsRepository.save(KsBeanUtil.copyPropertiesThird(tempGoods, Goods.class)).getGoodsId();
            }else {
                GoodsAudit goodsAudit = KsBeanUtil.convert(tempGoods, GoodsAudit.class);
                goodsAudit.setAuditType(AuditType.INITIAL_AUDIT.toValue());
                if (Objects.equals(Constants.ZERO,goodsAudit.getGoodsSource())){
                    goodsAudit.setSupplyPrice(goodsInfoList.stream().map(GoodsInfoDTO::getSupplyPrice).min(BigDecimal::compareTo).orElse(null));
                }
                goodsId = goodsAuditRepository.save(goodsAudit).getGoodsId();
                // ============= 处理平台的消息发送：商家/供应商导入商品待审核 START =============
                storeMessageBizService.handleForBatchImportGoodsAudit(tempGoods, request);
                // ============= 处理平台的消息发送：商家/供应商导入商品待审核 END =============
            }

            List<BatchGoodsSpecDTO> specs = allSpecs.getOrDefault(goods.getMockGoodsId(), Collections.emptyList());
            List<BatchGoodsSpecDetailDTO> specDetails = allSpecDetails.getOrDefault(goods.getMockGoodsId(), Collections.emptyList());

            //如果是多规格
            if (Constants.yes.equals(goods.getMoreSpecFlag())) {
                //新增含有规格值的规格
                specs.stream()
                        .filter(goodsSpec -> specDetails.stream().anyMatch(goodsSpecDetail ->
                                goodsSpecDetail.getMockSpecId().equals(goodsSpec.getMockSpecId())))
                        .forEach(goodsSpec -> {
                            goodsSpec.setCreateTime(goods.getCreateTime());
                            goodsSpec.setUpdateTime(goods.getCreateTime());
                            goodsSpec.setGoodsId(goodsId);
                            goodsSpec.setDelFlag(DeleteFlag.NO);
                            goodsSpec.setSpecId(goodsSpecRepository.save(
                                    KsBeanUtil.convert(goodsSpec, GoodsSpec.class)).getSpecId());
                        });
                //新增规格值
                specDetails.forEach(goodsSpecDetail -> {
                    Optional<BatchGoodsSpecDTO> specOpt = specs.stream().filter(goodsSpec -> goodsSpec.getMockSpecId
                            ().equals(goodsSpecDetail.getMockSpecId())).findFirst();
                    if (specOpt.isPresent()) {
                        goodsSpecDetail.setCreateTime(goods.getCreateTime());
                        goodsSpecDetail.setUpdateTime(goods.getCreateTime());
                        goodsSpecDetail.setGoodsId(goodsId);
                        goodsSpecDetail.setDelFlag(DeleteFlag.NO);
                        goodsSpecDetail.setSpecId(specOpt.get().getSpecId());
                        goodsSpecDetail.setSpecDetailId(goodsSpecDetailRepository.save(
                                KsBeanUtil.convert(goodsSpecDetail, GoodsSpecDetail.class)).getSpecDetailId());
                    }
                });
            }

            GoodsSaveDTO finalTempGoods = tempGoods;
            goodsInfoList.forEach(goodsInfo -> {
                goodsInfo.setGoodsId(goodsId);
                goodsInfo.setGoodsInfoName(goods.getGoodsName());
                goodsInfo.setCreateTime(goods.getCreateTime());
                goodsInfo.setUpdateTime(goods.getCreateTime());
                goodsInfo.setAddedTime(goods.getCreateTime());
                goodsInfo.setDelFlag(goods.getDelFlag());
                goodsInfo.setCompanyInfoId(goods.getCompanyInfoId());
                goodsInfo.setPriceType(goods.getPriceType());
                goodsInfo.setCustomFlag(goods.getCustomFlag());
                goodsInfo.setLevelDiscountFlag(goods.getLevelDiscountFlag());
                goodsInfo.setCateId(goods.getCateId());
                goodsInfo.setBrandId(goods.getBrandId());
                goodsInfo.setStoreId(goods.getStoreId());
                goodsInfo.setAuditStatus(goods.getAuditStatus());
                goodsInfo.setCompanyType(goods.getCompanyType());
                goodsInfo.setStoreType(goods.getStoreType());
                goodsInfo.setAloneFlag(Boolean.FALSE);
                goodsInfo.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                goodsInfo.setSaleType(goods.getSaleType());
                goodsInfo.setGoodsType(goods.getGoodsType());
                goodsInfo.setAddedTimingFlag(goods.getAddedTimingFlag());
                goodsInfo.setTakedownTimeFlag(goods.getTakedownTimeFlag());
                // 保存前扩展点
                goodsInfo = beforeGoodsInfoSave(goodsInfo);
                String skuId = goodsInfoRepository.save(KsBeanUtil.convert(goodsInfo, GoodsInfo.class))
                        .getGoodsInfoId();

                if(Objects.equals(CheckStatus.CHECKED, finalTempGoods.getAuditStatus())){
                    newSkuIds.add(skuId);
                }

                if (Objects.nonNull(goodsInfo.getGoodsType())
                        && GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == goodsInfo.getGoodsType()) {
                    electronicIds.add(goodsInfo.getElectronicCouponsId());
                }

                //存储规格
                //如果是多规格,新增SKU与规格明细值的关联表
                if (Constants.yes.equals(goods.getMoreSpecFlag())) {
                    for (BatchGoodsSpecDTO spec : specs) {
                        if (goodsInfo.getMockSpecIds().contains(spec.getMockSpecId())) {
                            for (BatchGoodsSpecDetailDTO detail : specDetails) {
                                if (spec.getMockSpecId().equals(detail.getMockSpecId()) && goodsInfo
                                        .getMockSpecDetailIds().contains(detail.getMockSpecDetailId())) {
                                    GoodsInfoSpecDetailRel detailRel = new GoodsInfoSpecDetailRel();
                                    detailRel.setGoodsId(goodsId);
                                    detailRel.setGoodsInfoId(skuId);
                                    detailRel.setSpecId(spec.getSpecId());
                                    detailRel.setSpecDetailId(detail.getSpecDetailId());
                                    detailRel.setDetailName(detail.getDetailName());
                                    detailRel.setCreateTime(detail.getCreateTime());
                                    detailRel.setUpdateTime(detail.getUpdateTime());
                                    detailRel.setDelFlag(detail.getDelFlag());
                                    goodsInfoSpecDetailRelRepository.save(detailRel);
                                }
                            }
                        }
                    }
                }
            });

            if (CollectionUtils.isNotEmpty(goods.getStoreCateIds())) {
                goods.getStoreCateIds().forEach(cateId -> {
                    StoreCateGoodsRela rela = new StoreCateGoodsRela();
                    rela.setGoodsId(goodsId);
                    rela.setStoreCateId(cateId);
                    storeCateGoodsRelaRepository.save(rela);
                });
            }

            //批量保存
            List<BatchGoodsImageDTO> imageUrls = images.get(goods.getGoodsNo());
            if (CollectionUtils.isNotEmpty(imageUrls)) {
                imageUrls.forEach(img -> {
                    img.setGoodsId(goodsId);
                    img.setCreateTime(goods.getCreateTime());
                    img.setUpdateTime(goods.getCreateTime());
                    img.setDelFlag(goods.getDelFlag());
                    goodsImageRepository.save(KsBeanUtil.convert(img, GoodsImage.class));
                });
            }
        });

        //更新卡券绑定关系
        if (CollectionUtils.isNotEmpty(electronicIds)) {
            electronicCouponProvider.updateBindingFlag(ElectronicCouponUpdateBindRequest.builder().bindingIds(electronicIds).build());
        }

        // 未审核商品不初始化ES
        // 发送初始化ES消息
        if(CollectionUtils.isNotEmpty(newSkuIds)){
            sendInitEsMsg(newSkuIds);
        }

        OperationLogAddRequest operationLogAddRequest = request.getOperationLogAddRequest();
        if (StoreType.SUPPLIER.equals(request.getType()) || StoreType.O2O.equals(request.getType())){
            //处理商家后续操作
            dealSupplierData(newSkuIds, operationLogAddRequest);
        } else if (StoreType.PROVIDER.equals(request.getType())){
            //处理供应商后续操作
            dealProviderData(newSkuIds, operationLogAddRequest);
        }
        return newSkuIds;
    }


    public void batchGoodsStockUpdate(BatchGoodsStockRequest request){
        Map<String,BatchGoodsStockDTO> batchGoodsStockDTOMap = request.getBatchGoodsStockDTOS()
                .stream()
                .collect(Collectors.toMap(BatchGoodsStockDTO::getGoodsInfoNo,m->m));
        List<String> goodsInfoNoList = new ArrayList<>(batchGoodsStockDTOMap.keySet());
        List<GoodsInfo> goodsInfos = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder()
                .goodsInfoNos(goodsInfoNoList)
                .delFlag(DeleteFlag.NO.toValue())
                .auditStatuses(Lists.newArrayList(CheckStatus.CHECKED,CheckStatus.FORBADE,CheckStatus.NOT_PASS))
                .build());
        //正常的商品
        Map<String,GoodsInfo> normalMap = new HashMap<>();
        //二审的商品
        Map<String,GoodsInfo> secondMap = new HashMap<>();
        Map<String,List<GoodsInfo>> skuNoMap = goodsInfos.stream().collect(Collectors.groupingBy(GoodsInfo::getGoodsInfoNo));
        for(GoodsInfo goodsInfo : goodsInfos){
            List<GoodsInfo> getList = skuNoMap.get(goodsInfo.getGoodsInfoNo());
            if(CollectionUtils.isNotEmpty(getList) && getList.size()>1){
                if(goodsInfo.getAuditStatus()==CheckStatus.CHECKED){
                    normalMap.put(goodsInfo.getGoodsInfoNo(),goodsInfo);
                }else{
                    secondMap.put(goodsInfo.getGoodsInfoNo(),goodsInfo);
                }
            }else{
                normalMap.put(goodsInfo.getGoodsInfoNo(),goodsInfo);
            }
        }

        normalMap.forEach(
                (k,v)->{
                    BatchGoodsStockDTO batchGoodsStockDTO = batchGoodsStockDTOMap.get(k);
                    OperateType operateType = batchGoodsStockDTO.getOperateType();
                    if(operateType.equals(OperateType.GROWTH)){
                        goodsInfoStockService.addStockByIdNoErr(batchGoodsStockDTO.getStock().longValue(),v.getGoodsInfoId());
                    }else if(operateType.equals(OperateType.DEDUCT)){
                        goodsInfoStockService.subStockByIdNoErr(batchGoodsStockDTO.getStock().longValue(),v.getGoodsInfoId());
                    }else if(operateType.equals(OperateType.REPLACE)){
                        goodsInfoStockService.replaceStockByIdNoErr(batchGoodsStockDTO.getStock().longValue(),v.getGoodsInfoId());
                    }
                }
        );

        // 处理预警消息发送
        storeMessageBizService.handleWarningStockByGoodsInfos(new ArrayList<>(normalMap.values()));

    }

    private void dealProviderData(List<String> newSkuIds, OperationLogAddRequest operationLog) {
        operationLog.setOpModule("商品");
        operationLog.setOpCode("商品模板导入");
        operationLog.setOpContext("商品模板导入");
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(operationLog));
        mqSendProvider.send(mqSendDTO);
        GoodsInfoListByIdsRequest goodsInfoListByIdsRequest =
                GoodsInfoListByIdsRequest.builder().goodsInfoIds(newSkuIds).build();

        //供应商商品审核成功直接加入到商品库
        List<GoodsInfo> skuList = goodsInfoService.findByIds(goodsInfoListByIdsRequest.getGoodsInfoIds());
        List<GoodsInfoVO> goodsInfoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(skuList)) {
            goodsInfoService.updateGoodsInfoSupplyPriceAndStock(skuList);
            goodsInfoList = goodsInfoService.fillGoodsStatus(KsBeanUtil.convertList(skuList, GoodsInfoVO.class));
        }
        GoodsInfoListByIdsResponse response = GoodsInfoListByIdsResponse.builder()
                .goodsInfos(goodsInfoList)
                .build();
        //填充供应商商品编码
        List<String> providerGoodsInfoIds = response.getGoodsInfos().stream()
                .map(GoodsInfoVO::getProviderGoodsInfoId)
                .filter(id -> Objects.nonNull(id)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
            //供应商商品
            List<GoodsInfo> goodsInfos = goodsInfoService.findByIds(providerGoodsInfoIds);

            if (CollectionUtils.isNotEmpty(goodsInfos) && CollectionUtils.isNotEmpty(response.getGoodsInfos())) {
                response.getGoodsInfos().stream().forEach(goodsInfoVO -> {
                    goodsInfos.stream().filter(goodsInfo ->
                            goodsInfo.getGoodsInfoId().equals(goodsInfoVO.getProviderGoodsInfoId()))
                            .findFirst().ifPresent(g -> goodsInfoVO.setProviderGoodsInfoNo(g.getGoodsInfoNo()));
                });
            }
        }
        //在设置未开启商品抵扣下清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfos());

        List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfos();
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            List<String> goodsIds =
                    goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(goodsIds.get(0));


            GoodsByIdResponse goods = KsBeanUtil.convert(goodsService.getGoodsById(goodsByIdRequest.getGoodsId()), GoodsByIdResponse.class);

            if (goods.getAuditStatus() == CheckStatus.CHECKED && GoodsSource.PROVIDER.toValue() == goods.getGoodsSource()) {

                StandardImportStandardRequest standardImportStandardRequest = new StandardImportStandardRequest();
                standardImportStandardRequest.setGoodsIds(goodsIds);

                GoodsRequest goodsRequest = KsBeanUtil.convert(standardImportStandardRequest, GoodsRequest.class);

                List<String> standardIds = standardImportService.importStandard(goodsRequest);
                //初始化商品库ES
                //刷新ES
                if (CollectionUtils.isNotEmpty(standardIds)) {
                    producerService.initStandardByStandardIds(standardIds);
                }
                //操作日志记录
                operationLog.setOpModule("商品");
                operationLog.setOpCode("加入商品库");
                operationLog.setOpContext("加入商品库：SPU编码" + goods.getGoodsNo());
                mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
                mqSendDTO.setData(JSONObject.toJSONString(operationLog));
                mqSendProvider.send(mqSendDTO);
            }
        }
    }

    private void dealSupplierData(List<String> newSkuIds, OperationLogAddRequest operationLog) {
        operationLog.setOpModule("商品");
        operationLog.setOpCode("商品模板导入");
        operationLog.setOpContext("商品模板导入");
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.OPERATE_LOG_ADD);
        mqSendDTO.setData(JSONObject.toJSONString(operationLog));
        mqSendProvider.send(mqSendDTO);
    }


    /**
     * 新增/编辑操作中，商品审核状态
     *
     * @param goods 商品
     */
    @Override
    public void setCheckState(GoodsSaveDTO goods) {
        //B2B模式直接审核通过
        if (osUtil.isB2b()
                || (Objects.nonNull(goods.getGoodsSource())
                && GoodsSource.VOP.toValue() == goods.getGoodsSource())) {
            goods.setAuditStatus(CheckStatus.CHECKED);
            goods.setSubmitTime(LocalDateTime.now());
            return;
        }
        //新增商品
        if (Objects.isNull(goods.getAuditStatus())) {
            if (Objects.equals(BoolFlag.NO, goods.getCompanyType())) {
                if (auditQueryProvider.isBossGoodsAudit().getContext().isAudit()) {
                    goods.setAuditStatus(CheckStatus.WAIT_CHECK);
                    return;
                }
                goods.setAuditStatus(CheckStatus.CHECKED);
                goods.setSubmitTime(LocalDateTime.now());
            } else {
                if (auditQueryProvider.isSupplierGoodsAudit().getContext().isAudit()) {
                    goods.setAuditStatus(CheckStatus.WAIT_CHECK);
                    return;
                }
                goods.setAuditStatus(CheckStatus.CHECKED);
                goods.setSubmitTime(LocalDateTime.now());
            }
        } else {
            //自营
            GoodsSecondaryAuditRequest request = new GoodsSecondaryAuditRequest();
            if (Objects.equals(Constants.ONE, goods.getGoodsSource())) {
                request.setConfigType(ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT);
                if (auditQueryProvider.isBossGoodsSecondaryAudit(request).getContext().isAudit()) {
                    //商家开启商品二次审核
                    goods.setAuditStatus(CheckStatus.WAIT_CHECK);
                    goods.setSubmitTime(LocalDateTime.now());
                } else {
                    goods.setAuditStatus(CheckStatus.CHECKED);
                }
            } else {//第三方
                request.setConfigType(ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT);
                if (auditQueryProvider.isBossGoodsSecondaryAudit(request).getContext().isAudit()) {
                    goods.setAuditStatus(CheckStatus.WAIT_CHECK);
                    goods.setSubmitTime(LocalDateTime.now());
                } else {
                    goods.setAuditStatus(CheckStatus.CHECKED);
                }
            }

        }
    }

    @Override
    public GoodsInfoForPurchaseResponse queryInfoForPurchase(InfoForPurchaseRequest request) {

        GoodsInfoForPurchaseResponse response = new GoodsInfoForPurchaseResponse();
        List<String> goodsInfoIds = request.getGoodsInfoIds();
        CustomerVO customer = request.getCustomer();

        // 查询商品信息、单品信息
        List<GoodsInfoVO> goodsInfoList = goodsCacheService.listGoodsInfosByIds(goodsInfoIds);
        if (CollectionUtils.isEmpty(goodsInfoList)){
            return response;
        }

        List<GoodsVO> goodsList = goodsCacheService.listGoodsByIds(
                goodsInfoList.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList())
        );
        //如果是linkedmall商品，实时查库存,根据区域码查库存
        List<Long> itemIds = goodsList.stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        if (itemIds.size() > 0) {
            String thirdAddrId = null;
            if (request.getAreaId() != null) {
                List<ThirdAddressVO> thirdAddressList = thirdAddressQueryProvider.list(ThirdAddressListRequest.builder()
                        .platformAddrIdList(Collections.singletonList(Objects.toString(request.getAreaId())))
                        .thirdFlag(ThirdPlatformType.LINKED_MALL)
                        .build()).getContext().getThirdAddressList();
                if (CollectionUtils.isNotEmpty(thirdAddressList)) {
                    thirdAddrId = thirdAddressList.get(0).getThirdAddrId();
                }
            }
            List<LinkedMallStockVO> stocks = null;
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(itemIds, thirdAddrId == null ? "0" : thirdAddrId, null)).getContext();
            if (stocks != null) {
                for (GoodsInfoVO goodsInfo : goodsInfoList) {
                    if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                        for (LinkedMallStockVO spuStock : stocks) {
                            Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                    .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                                    .findFirst();
                            if (stock.isPresent()) {
                                Long skuStock = stock.get().getStock();
                                goodsInfo.setStock(skuStock);
                                if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                    goodsInfo.setGoodsStatus(skuStock > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                                }
                            }
                        }
                    }
                }
                for (GoodsVO goodsVO : goodsList) {
                    if (ThirdPlatformType.LINKED_MALL.equals(goodsVO.getThirdPlatformType())) {
                        Optional<LinkedMallStockVO> optional = stocks.stream()
                                .filter(v -> String.valueOf(v.getItemId()).equals(goodsVO.getThirdPlatformSpuId())).findFirst();
                        if (optional.isPresent()) {
                            Long spuStock = optional.get().getSkuList().stream()
                                    .map(v -> v.getStock())
                                    .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                            goodsVO.setStock(spuStock);
                        }
                    }
                }
            }
        }

        goodsList.forEach(goods ->
                goodsInfoList.stream().filter(i -> i.getGoodsId().equals(goods.getGoodsId())).forEach(goodsInfo -> {
                    goodsInfo.setCateId(goods.getCateId());
                    goodsInfo.setBrandId(goods.getBrandId());
                    goodsInfo.setPriceType(goods.getPriceType());
                    goodsInfo.setCompanyType(goods.getCompanyType());
                    goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
                    goodsInfo.setGoodsInfoImg(Objects.nonNull(goodsInfo.getGoodsInfoImg()) ?
                            goodsInfo.getGoodsInfoImg() : goods.getGoodsImg());
                })
        );

        List<Long> storeIds = goodsInfoList.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList());

        // 商品起限定价
        if (Objects.nonNull(customer)) {
            List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS = goodsInfoList.stream()
                    .map(g -> GoodsRestrictedValidateVO.builder().num(g.getBuyCount()).skuId(g.getGoodsInfoId()).build()).collect(Collectors.toList());
            List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS = goodsRestrictedSaleService.getGoodsRestrictedInfo(
                    GoodsRestrictedBatchValidateRequest.builder().goodsRestrictedValidateVOS(goodsRestrictedValidateVOS).customerVO(customer).build());
            if (CollectionUtils.isNotEmpty(goodsRestrictedPurchaseVOS)) {
                Map<String, GoodsRestrictedPurchaseVO> purchaseMap = goodsRestrictedPurchaseVOS.stream().collect((Collectors.toMap(GoodsRestrictedPurchaseVO::getGoodsInfoId, Function.identity())));
                goodsInfoList.stream().forEach(g -> {
                    GoodsRestrictedPurchaseVO goodsRestrictedPurchaseVO = purchaseMap.get(g.getGoodsInfoId());
                    if (Objects.nonNull(goodsRestrictedPurchaseVO)) {
                        if (DefaultFlag.YES.equals(goodsRestrictedPurchaseVO.getDefaultFlag())) {
                            g.setMaxCount(goodsRestrictedPurchaseVO.getRestrictedNum());
                            g.setCount(goodsRestrictedPurchaseVO.getStartSaleNum());
                        } else {
                            //限售没有资格购买时，h5需要的商品状态是正常
//                            g.setMaxCount(0L);
                            g.setGoodsStatus(GoodsStatus.NO_AUTH);
                        }
                    }
                });
            }
        }


        // 会员等级
        HashMap<Long, CommonLevelVO> levelsMap = new HashMap<>();
        if (Objects.nonNull(customer)) {
            levelsMap = goodsCacheService.listCustomerLevelMapByCustomerIdAndIds(customer.getCustomerId(), storeIds);
        }

        // 商品选择的营销
        if (Objects.nonNull(customer)) {
            List<GoodsMarketing> goodsMarketingList = goodsMarketingService.queryGoodsMarketingList(customer.getCustomerId());
            goodsInfoList.forEach(goodsInfo -> {
                GoodsMarketing marketing = goodsMarketingList.stream()
                        .filter(i -> i.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).findFirst().orElse(null);
                goodsInfo.setGoodsMarketing(KsBeanUtil.convert(marketing, GoodsMarketingVO.class));
            });
        }

        // 按客户单独设价信息
        List<String> cusSkuIds = goodsInfoList.stream()
                .filter(i -> Constants.yes.equals(i.getCustomFlag())).map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsCustomerPrice> goodsCustomerPrices = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(cusSkuIds) && Objects.nonNull(customer)) {
            goodsCustomerPrices = goodsCustomerPriceRepository.findSkuByGoodsInfoIdAndCustomerId(cusSkuIds, customer.getCustomerId());
        }
        Map<String, GoodsCustomerPrice> customerPriceMap = goodsCustomerPrices.stream().collect(Collectors.toMap(GoodsCustomerPrice::getGoodsInfoId, Function.identity()));

        // 商品设价方式信息设置
        HashMap<Long, CommonLevelVO> finalLevelsMap = levelsMap;
        goodsInfoList.forEach(goodsInfo -> {
            Integer priceType = Objects.nonNull(goodsInfo.getPriceType()) ? goodsInfo.getPriceType() : GoodsPriceType.MARKET.toValue();
            if (priceType.equals(GoodsPriceType.MARKET.toValue())) {
                // 按市场价销售
                goodsInfo.setSalePrice(goodsInfo.getMarketPrice());
            }
            if (priceType.equals(GoodsPriceType.CUSTOMER.toValue())) {
                // 按客户设价--级别价
                CommonLevelVO customerLevel = finalLevelsMap.get(goodsInfo.getStoreId());
                if (Objects.isNull(customerLevel)){
                    customerLevel = new CommonLevelVO();
                    customerLevel.setLevelId(0L);
                }
                if (Objects.nonNull(customerLevel.getLevelDiscount())) {
                    goodsInfo.setSalePrice(goodsInfo.getSalePrice().multiply(customerLevel.getLevelDiscount()).setScale(2, RoundingMode.HALF_UP));
                }
                CommonLevelVO finalCustomerLevel = customerLevel;
                goodsInfo.getGoodsLevelPriceList().stream()
                        .filter(i -> i.getLevelId().equals(finalCustomerLevel.getLevelId())).findFirst()
                        .ifPresent((levelPrice -> {
                            if(Objects.nonNull(levelPrice.getPrice())) {
                                goodsInfo.setSalePrice(levelPrice.getPrice());
                            }
                        }));

                // 按客户设价--客户单独定价
                GoodsCustomerPrice customerPrice = customerPriceMap.get(goodsInfo.getGoodsInfoId());
                if (Objects.nonNull(customerPrice)) {
                    if(customerPrice.getPrice() != null) {
                        goodsInfo.setSalePrice(customerPrice.getPrice());
                    }
                }
            }
            if (priceType.equals(GoodsPriceType.STOCK.toValue())) {
                // 按订货量设价
                CommonLevelVO customerLevel = finalLevelsMap.get(goodsInfo.getStoreId());
                List<GoodsIntervalPriceVO> intervalPrices = goodsInfo.getIntervalPriceList();
                if (Constants.yes.equals(goodsInfo.getLevelDiscountFlag())
                        && Objects.nonNull(customerLevel) && Objects.nonNull(customerLevel.getLevelDiscount())) {
                    intervalPrices.forEach(intervalPrice -> {
                        intervalPrice.setPrice(intervalPrice.getPrice().multiply(customerLevel.getLevelDiscount()).setScale(2, RoundingMode.HALF_UP));
                    });
                }
                List<BigDecimal> intervalPriceList = intervalPrices.stream().map(GoodsIntervalPriceVO::getPrice).collect(Collectors.toList());
                goodsInfo.setIntervalPriceIds(intervalPrices.stream().map(GoodsIntervalPriceVO::getIntervalPriceId).collect(Collectors.toList()));
                goodsInfo.setIntervalMinPrice(intervalPriceList.stream().filter(Objects::nonNull).min(BigDecimal::compareTo).orElse(null));
                goodsInfo.setIntervalMaxPrice(intervalPriceList.stream().filter(Objects::nonNull).max(BigDecimal::compareTo).orElse(null));
                goodsInfo.setIntervalPriceList(intervalPrices);
            }

        });

        response.setGoodsInfoList(goodsInfoList);
        response.setGoodsList(goodsList);
        response.setLevelsMap(levelsMap);
        return response;
    }


    /**
     * 递归方式，获取全局唯一SPU编码
     * @return Spu编码
     */
    @Override
    public String getSpuNoByUnique(){
        String spuNo = getSpuNo();
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsNo(spuNo);
        if (goodsRepository.count(queryRequest.getWhereCriteria()) > 0) {
            return getSpuNoByUnique();
        }
        return spuNo;
    }

    /**
     * 获取Spu编码
     * @return Spu编码
     */
    @Override
    public String getSpuNo() {
        return "P".concat(String.valueOf(System.currentTimeMillis()).substring(4, 10)).concat(RandomStringUtils.randomNumeric(3));
    }

    /**
     * 递归方式，获取全局唯一SPU编码
     * @return Sku编码
     */
    @Override
    public String getSkuNoByUnique(){
        String skuNo = getSkuNo();
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsInfoNos(Collections.singletonList(skuNo));
        if (goodsInfoRepository.count(queryRequest.getWhereCriteria()) > 0) {
            return getSkuNoByUnique();
        }
        return skuNo;
    }

    /**
     * 获取Sku编码
     * @return Sku编码
     */
    @Override
    public String getSkuNo() {
        return "8".concat(String.valueOf(System.currentTimeMillis()).substring(4, 10)).concat(RandomStringUtils.randomNumeric(3));
    }

    /***
     * 商品保存前修改保存对象扩展点
     * @param goods 商品对象
     * @return      修改后的商品对象
     */
    protected GoodsSaveDTO beforeGoodsSave(GoodsSaveDTO goods){
        return goods;
    }

    /***
     * 商品SKU保存前修改保存对象扩展点
     * @param goodsInfo 商品SKU对象
     * @return          修改后SKU的商品对象
     */
    protected BatchGoodsInfoDTO beforeGoodsInfoSave(BatchGoodsInfoDTO goodsInfo){
        return goodsInfo;
    }

    /***
     * 发送初始化ES消息
     * @param newSkuIds SKUID集合
     */
    protected void sendInitEsMsg(List<String> newSkuIds) {
        producerService.initGoodsBySkuIds(newSkuIds);
    }

}
