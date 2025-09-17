package com.wanmi.sbc.goods.info.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.goods.api.request.adjustprice.AdjustPriceExecuteRequest;
import com.wanmi.sbc.goods.api.request.goods.ProviderGoodsNotSellRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsCheckResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsModifyInfoResponse;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.distributor.goods.repository.DistributiorGoodsInfoRepository;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditService;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditWhereCriteriaBuilder;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsCheckRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsRequest;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import com.wanmi.sbc.goods.log.GoodsCheckLog;
import com.wanmi.sbc.goods.log.service.GoodsCheckLogService;
import com.wanmi.sbc.goods.price.model.root.GoodsCustomerPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsLevelPrice;
import com.wanmi.sbc.goods.price.repository.GoodsCustomerPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsIntervalPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsLevelPriceRepository;
import com.wanmi.sbc.goods.priceadjustmentrecord.model.root.PriceAdjustmentRecord;
import com.wanmi.sbc.goods.priceadjustmentrecord.repository.PriceAdjustmentRecordRepository;
import com.wanmi.sbc.goods.priceadjustmentrecorddetail.model.root.PriceAdjustmentRecordDetail;
import com.wanmi.sbc.goods.priceadjustmentrecorddetail.repository.PriceAdjustmentRecordDetailRepository;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsEditDetailService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.service.StandardImportService;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.goods.storegoodstab.model.root.GoodsTabRela;
import com.wanmi.sbc.goods.storegoodstab.repository.GoodsTabRelaRepository;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * S2b商品服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class S2bGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsCheckLogService goodsCheckLogService;

    @Autowired
    private DistributiorGoodsInfoRepository distributiorGoodsInfoRepository;

    @Autowired
    private StandardImportService standardImportService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderGoodsEditDetailService providerGoodsEditDetailService;

    @Autowired
    private GoodsAuditRepository goodsAuditRepository;

    @Autowired
    private GoodsAuditService goodsAuditService;

    @Autowired
    private GoodsImageRepository goodsImageRepository;

    @Autowired
    protected StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired
    private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired
    private GoodsTabRelaRepository goodsTabRelaRepository;

    @Resource
    protected GoodsSpecRepository goodsSpecRepository;
    @Resource
    protected GoodsSpecDetailRepository goodsSpecDetailRepository;
    @Resource
    protected GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Resource
    private GoodsLevelPriceRepository goodsLevelPriceRepository;

    @Resource
    private GoodsCustomerPriceRepository goodsCustomerPriceRepository;

    @Resource
    private GoodsIntervalPriceRepository goodsIntervalPriceRepository;

    @Resource
    private PriceAdjustmentRecordDetailRepository recordDetailRepository;

    @Resource
    private PriceAdjustmentRecordRepository recordRepository;

    @Resource
    private MqSendProvider mqSendProvider;

    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    /**
     * 商品审核
     *
     * @param checkRequest
     * @throws SbcRuntimeException
     */
    @Transactional(rollbackFor = {Exception.class})
    public GoodsCheckResponse check(GoodsCheckRequest checkRequest) throws SbcRuntimeException {
        GoodsCheckResponse response = new GoodsCheckResponse();
        //获取审核商品信息
        List<GoodsAudit> auditList = goodsAuditRepository.findAllById(checkRequest.getGoodsIds());
        List<GoodsAudit> goodsAuditList = KsBeanUtil.convert(auditList, GoodsAudit.class);

        if (Objects.equals(CheckStatus.CHECKED, checkRequest.getAuditStatus())) {
            //审核通过
            checkRequest.setGoodsIds(new ArrayList<>());
            List<GoodsAudit> initialAuditGoods = goodsAuditList
                    .stream()
                    .filter(goodsAudit -> Objects.equals(AuditType.INITIAL_AUDIT.toValue(), goodsAudit.getAuditType()))
                    .collect(Collectors.toList());
            List<GoodsAudit> secondAuditGoods = goodsAuditList
                    .stream()
                    .filter(goodsAudit -> Objects.equals(AuditType.SECOND_AUDIT.toValue(), goodsAudit.getAuditType()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(initialAuditGoods)) {
                //判断spuNo不可重复
                long count = goodsService.countByCondition(GoodsQueryRequest.builder().goodsNos(initialAuditGoods.parallelStream()
                                .map(GoodsAudit::getGoodsNo)
                                .collect(Collectors.toList()))
                                .delFlag(DeleteFlag.NO.toValue())
                        .build());
                if (count > 0) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030036);
                }
                //商品初次审核
                this.initalAuditGoods(checkRequest, initialAuditGoods);
            }

            if (CollectionUtils.isNotEmpty(secondAuditGoods)) {
                //判断spuNo不可重复
                long count = goodsService.countByCondition(GoodsQueryRequest
                        .builder()
                        .goodsNos(secondAuditGoods
                                .parallelStream()
                                .map(GoodsAudit::getGoodsNo)
                                .collect(Collectors.toList()))
                        .notGoodsIds(secondAuditGoods
                                .parallelStream()
                                .map(GoodsAudit::getOldGoodsId)
                                .collect(Collectors.toList()))
                        .delFlag(DeleteFlag.NO.toValue())
                        .build());
                if (count > 0) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030036);
                }
                //二次审核商品
                Boolean isProvideGoods = this.secondAduitGoods(checkRequest, secondAuditGoods);
                //删除商品审核记录,过滤禁售商品
                List<String> delGoodsAuditIds = secondAuditGoods
                        .stream()
                        .filter(v -> !Objects.equals(v.getGoodsId(), v.getOldGoodsId()))
                        .map(GoodsAudit::getGoodsId)
                        .collect(Collectors.toList());
                List<String> delGoodsAuditIds2 = secondAuditGoods
                        .stream()
                        .filter(v -> Objects.equals(v.getGoodsId(), v.getOldGoodsId()))
                        .map(GoodsAudit::getGoodsId)
                        .collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(delGoodsAuditIds)){
                    goodsAuditService.deleteByIdList(delGoodsAuditIds);
                }
                if (CollectionUtils.isNotEmpty(delGoodsAuditIds2)){
                    goodsAuditRepository.deleteByIdList(delGoodsAuditIds2);
                }
                if (!isProvideGoods) {
                    MqSendDTO mqSendDTO = new MqSendDTO();
                    mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
                    GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
                    sendRequest.setGoodsIds(
                            secondAuditGoods.stream()
                                    .map(GoodsAudit::getOldGoodsId)
                                    .collect(Collectors.toList()));
                    sendRequest.setFlag(GoodsEditFlag.INFO);
                    mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
                    mqSendProvider.send(mqSendDTO);
                }
            }
        } else if (Objects.equals(CheckStatus.FORBADE, checkRequest.getAuditStatus())) {
            //禁售
            forbadeAuditGoods(checkRequest);
        } else {
            //驳回商品
            turnDownAuditGoods(checkRequest, auditList);
        }

        //商品库禁售商品
        if (Boolean.TRUE.equals(checkRequest.getDealStandardGoodsFlag())) {
            response.setDeleteStandardIds(this.dealStandardGoods(checkRequest));
        }
        //同步代码 处理供应商商品的编辑供应商商品 审核通过的情况(1.boss删除了供应商商品库 2.boss没删除供应商商品库，供应商二次编辑 3.供应商第一次发布商品)
        response.setStandardIds(dealProviderGoodsEdit(checkRequest));

        //更新商家代销商品可售性
        Boolean checkFlag = null;
        if (CheckStatus.FORBADE == checkRequest.getAuditStatus()) {
            checkFlag = Boolean.FALSE;
        } else if (CheckStatus.CHECKED == checkRequest.getAuditStatus()) {
            checkFlag = Boolean.TRUE;
        }
        if (checkFlag != null) {
            ProviderGoodsNotSellRequest request = ProviderGoodsNotSellRequest.builder().goodsIds(checkRequest.getGoodsIds()).checkFlag(checkFlag).build();
            goodsService.dealGoodsVendibility(request);
        }

        //商品禁售删除分销员分销商品
        checkRequest.getGoodsIds().forEach(goodsID -> {
            distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
        });

        //新增审核记录
        checkRequest.getGoodsIds().forEach(goodsId -> {
            GoodsCheckLog checkLog = new GoodsCheckLog();
            checkLog.setId(UUIDUtil.getUUID());
            checkLog.setGoodsId(goodsId);
            checkLog.setChecker(checkRequest.getChecker());
            checkLog.setAuditReason(checkRequest.getAuditReason());
            goodsCheckLogService.addGoodsCheckLog(checkLog);
            checkLog.setAuditStatus(checkRequest.getAuditStatus());
        });

        //禁售 同步供应商商品操作日志
        if (CheckStatus.FORBADE.toValue() == checkRequest.getAuditStatus().toValue()) {
            providerGoodsEditDetailService.goodsForbade(checkRequest.getGoodsIds());
        }

        return response;
    }

    /**
     * 二次审核商品
     * @param checkRequest
     * @param secondAuditGoods
     */
    private Boolean secondAduitGoods(GoodsCheckRequest checkRequest, List<GoodsAudit> secondAuditGoods) {
        Boolean isProvideGoods = Boolean.FALSE;
        for (GoodsAudit goodsAudit : secondAuditGoods) {
            Goods oldGoods = goodsRepository.getOne(goodsAudit.getOldGoodsId());
            isProvideGoods = GoodsSource.PROVIDER.toValue() == oldGoods.getGoodsSource();
            List<GoodsInfo> infoList = null;
            if (StringUtils.isNotBlank(oldGoods.getProviderGoodsId())) {
                infoList = goodsInfoRepository.findByGoodsIdsAndDelFlag(Collections.singletonList(goodsAudit.getGoodsId()));
            } else {
                infoList = goodsInfoRepository.findByGoodsIds(Collections.singletonList(goodsAudit.getGoodsId()));
            }
            String goodsId = goodsAudit.getOldGoodsId();
            checkRequest.getGoodsIds().add(goodsId);

            List<PriceAdjustmentRecordDetail> recordDetails = getPriceAdjustmentRecordDetails(infoList,Constants.yes,null);

            if (CollectionUtils.isNotEmpty(recordDetails)){
                PriceAdjustmentRecordDetail recordDetail = recordDetails.get(0);
                PriceAdjustmentRecord record = recordRepository.findById(recordDetail.getPriceAdjustmentNo()).orElse(null);
                if (Objects.nonNull(record) && record.getEffectiveTime().isAfter(LocalDateTime.now())){
                    Duration duration = Duration.between(LocalDateTime.now(), record.getEffectiveTime());
                    MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
                    mqSendDelayDTO.setTopic(ProducerTopic.GOODS_PRICE_ADJUST);
                    AdjustPriceExecuteRequest adjustPriceExecuteRequest = AdjustPriceExecuteRequest.builder()
                            .adjustNo(record.getId())
                            .storeId(record.getStoreId())
                            .build();
                    long delayTime = duration.toMillis();
                    mqSendDelayDTO.setData(JSON.toJSONString(adjustPriceExecuteRequest));
                    mqSendDelayDTO.setDelayTime(delayTime);
                    mqSendProvider.sendDelay(mqSendDelayDTO);
                    // 批量更新扫描标识为已扫描
                    recordRepository.modifyScanType(Collections.singletonList(record.getId()));
                    break;
                }else {
                    recordDetails.forEach(v->v.setAdjustResult(PriceAdjustmentResult.DONE));
                    recordDetailRepository.saveAll(recordDetails);
                }
            }

            GoodsSaveRequest goodsSaveRequest = new GoodsSaveRequest();
            goodsSaveRequest.setIsChecked(Boolean.TRUE);
            //更新goods
            String[] exclude = {"addedTime", "addedFlag", "addedTime", "addedTimingFlag", "addedTimingTime", "addFalseReason","goodsBuyTypes"};
            GoodsSaveDTO goods = KsBeanUtil.convert(oldGoods, GoodsSaveDTO.class);
            KsBeanUtil.copyPropertiesThird(goodsAudit, goods,exclude);
            goodsAudit.setGoodsSource(goods.getGoodsSource());

            goods.setGoodsId(goodsId);
            List<StoreCateGoodsRela> storeCateGoodsRelas = storeCateGoodsRelaRepository.selectByGoodsId(Collections.singletonList(goodsAudit.getGoodsId()));
            goods.setStoreCateIds(storeCateGoodsRelas.stream().map(StoreCateGoodsRela::getStoreCateId).collect(Collectors.toList()));
            goodsSaveRequest.setGoods(goods);
            //更新图片
            List<GoodsImage> imageList = goodsImageRepository.findByGoodsId(goodsAudit.getGoodsId());
            List<GoodsImageDTO> goodsImageList = KsBeanUtil.convert(imageList, GoodsImageDTO.class);
            goodsImageList.forEach(v -> v.setGoodsId(goodsId));
            goodsSaveRequest.setImages(goodsImageList);
            //更新商品属性
            List<GoodsPropertyDetailRel> propertyDetailRelList = goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(goodsAudit.getGoodsId(), DeleteFlag.NO, GoodsPropertyType.GOODS);
            List<GoodsPropertyDetailRelSaveDTO> goodsPropertyDetailRelList = KsBeanUtil.convert(propertyDetailRelList, GoodsPropertyDetailRelSaveDTO.class);
            goodsPropertyDetailRelList.forEach(v -> v.setGoodsId(goodsId));
            goodsSaveRequest.setGoodsPropertyDetailRel(goodsPropertyDetailRelList);
            //更新商品详情模板关联实体
            List<GoodsTabRela> tabRelaList = goodsTabRelaRepository.queryListByGoodsId(goodsId);
            List<GoodsTabRelaDTO> goodsTabRelas = KsBeanUtil.convert(tabRelaList, GoodsTabRelaDTO.class);
            goodsTabRelas.forEach(v -> v.setGoodsId(goodsAudit.getGoodsId()));
            goodsSaveRequest.setGoodsTabRelas(goodsTabRelas);
            if (goodsAudit.getMoreSpecFlag() == Constants.ONE) {
                //更新规格数据
                List<GoodsSpec> specList = goodsSpecRepository.findByGoodsId(goodsAudit.getGoodsId());
                List<GoodsSpecSaveDTO> goodsSpecList = KsBeanUtil.convert(specList, GoodsSpecSaveDTO.class);
                goodsSpecList.forEach(v -> {
                    v.setGoodsId(goodsId);
                    v.setMockSpecId(v.getSpecId());
                    if (Objects.nonNull(v.getOldSpecId())){
                        v.setSpecId(v.getOldSpecId());
                        v.setMockSpecId(v.getSpecId());
                    }else {
                        v.setSpecId(null);
                    }
                });
                goodsSaveRequest.setGoodsSpecs(goodsSpecList);

                List<GoodsSpecDetail> specDetailList = goodsSpecDetailRepository.findByGoodsId(goodsAudit.getGoodsId());
                List<GoodsSpecDetailSaveDTO> goodsSpecDetailList = KsBeanUtil.convert(specDetailList, GoodsSpecDetailSaveDTO.class);
                goodsSpecDetailList.forEach(v -> {
                    v.setGoodsId(goodsId);
                    GoodsSpec spec = goodsSpecRepository.getOne(v.getSpecId());
                    v.setMockSpecId(Objects.isNull(spec.getOldSpecId())?spec.getSpecId():spec.getOldSpecId());
                    v.setMockSpecDetailId(v.getSpecDetailId());
                    if (Objects.nonNull(v.getOldSpecDetailId())){
                        v.setSpecDetailId(v.getOldSpecDetailId());
                        v.setSpecId(spec.getOldSpecId());
                        v.setMockSpecDetailId(v.getOldSpecDetailId());
                    }else {
                        v.setSpecDetailId(null);
                        v.setSpecId(null);
                    }
                });
                goodsSaveRequest.setGoodsSpecDetails(goodsSpecDetailList);
            }
            // goodsInfoId映射关系，老的旧的
            List<Map<String, String>> goodsInfoMaps = Lists.newArrayList();
            List<GoodsInfoSaveDTO> newGoodsInfoList = KsBeanUtil.convert(infoList, GoodsInfoSaveDTO.class);
            Goods finalGoods = goodsRepository.getOne(goodsId);
            List<GoodsInfo> oldGoodsInfoList = goodsInfoRepository.findByGoodsInfoIds(newGoodsInfoList.stream().map(GoodsInfoSaveDTO::getOldGoodsInfoId).filter(Objects::nonNull).collect(Collectors.toList()));
            for (GoodsInfoSaveDTO goodsInfo : newGoodsInfoList) {
                Map<String, String> goodsInfoMap = Maps.newHashMap();
                if (StringUtils.isNotBlank(goodsInfo.getOldGoodsInfoId()) && StringUtils.isNotBlank(goodsInfo.getGoodsInfoId())){
                    goodsInfoMap.put(goodsInfo.getOldGoodsInfoId(), goodsInfo.getGoodsInfoId());
                    goodsInfoMaps.add(goodsInfoMap);
                }
                List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRelList;
                //false:表示当前是auditGoods下的规格， true:是Goods下的规格
                Boolean isGoodsSpecFlag = false;
                if (StringUtils.isNotBlank(goodsInfo.getOldGoodsInfoId())) {
                    goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfo.getOldGoodsInfoId());
                    isGoodsSpecFlag = true;
                    if (CollectionUtils.isEmpty(goodsInfoSpecDetailRelList)){
                        goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfo.getGoodsInfoId());
                        isGoodsSpecFlag = false;
                    }
                    goodsInfo.setGoodsInfoId(goodsInfo.getOldGoodsInfoId());
                    //不同步sku上下架信息 和商品分销相关信息
                    Optional<GoodsInfo> opt = oldGoodsInfoList.stream().filter(v -> Objects.equals(v.getGoodsInfoId(), goodsInfo.getOldGoodsInfoId())).findFirst();
                    if (opt.isPresent()){
                        goodsInfo.setAddedFlag(opt.get().getAddedFlag());
                        goodsInfo.setAddedTime(opt.get().getAddedTime());
                        goodsInfo.setAddedTimingFlag(opt.get().getAddedTimingFlag());
                        goodsInfo.setAddedTimingTime(opt.get().getAddedTimingTime());
                        goodsInfo.setDistributionCommission(opt.get().getDistributionCommission());
                        goodsInfo.setDistributionSalesCount(opt.get().getDistributionSalesCount());
                        goodsInfo.setDistributionGoodsAudit(opt.get().getDistributionGoodsAudit());
                        goodsInfo.setDistributionGoodsAuditReason(opt.get().getDistributionGoodsAuditReason());
                        goodsInfo.setDistributionCreateTime(opt.get().getDistributionCreateTime());
                        if (Objects.isNull(goodsInfo.getElectronicCouponsId())) {
                            goodsInfo.setElectronicCouponsId(opt.get().getElectronicCouponsId());
                        }
                    }
                } else if (Objects.equals(CheckStatus.FORBADE,finalGoods.getAuditStatus())){
                    goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfo.getGoodsInfoId());
                }else {
                    goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelRepository.findByGoodsInfoId(goodsInfo.getGoodsInfoId());
                    goodsInfo.setGoodsInfoId(null);
                }
                goodsInfo.setOldGoodsInfoId(null);
                goodsInfo.setGoodsId(goodsId);
                goodsInfo.setAuditStatus(CheckStatus.CHECKED);
                List<GoodsSpec> specs = goodsSpecRepository.findAllById(goodsInfoSpecDetailRelList.stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                List<GoodsSpecDetail> specDetails = goodsSpecDetailRepository.findAllById(goodsInfoSpecDetailRelList.stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
                //Goods下的规格，直接以规格id为主，因为前面mockIds用的是auditGoods规格下的oldId
                if (isGoodsSpecFlag) {
                    goodsInfo.setMockSpecIds(specs.stream().map(GoodsSpec::getSpecId).collect(Collectors.toList()));
                    goodsInfo.setMockSpecDetailIds(specDetails.stream().map(GoodsSpecDetail::getSpecDetailId).collect(Collectors.toList()));
                } else {
                    goodsInfo.setMockSpecIds(specs.stream().map(v -> Objects.isNull(v.getOldSpecId()) ? v.getSpecId() : v.getOldSpecId()).collect(Collectors.toList()));
                    goodsInfo.setMockSpecDetailIds(specDetails.stream().map(v -> Objects.isNull(v.getOldSpecDetailId()) ? v.getSpecDetailId() : v.getOldSpecDetailId()).collect(Collectors.toList()));
                }
            }
            goodsSaveRequest.setGoodsInfos(newGoodsInfoList);

            List<GoodsLevelPrice> levelPriceList = goodsLevelPriceRepository.findByGoodsId(goodsAudit.getGoodsId());
            List<GoodsLevelPriceDTO> goodsLevelPriceList = KsBeanUtil.convert(levelPriceList, GoodsLevelPriceDTO.class);
            goodsLevelPriceList.forEach(v -> v.setGoodsId(goodsId));
            goodsSaveRequest.setGoodsLevelPrices(goodsLevelPriceList);

            List<GoodsCustomerPrice> customerPriceList = goodsCustomerPriceRepository.findByGoodsId(goodsAudit.getGoodsId());
            List<GoodsCustomerPriceDTO> goodsCustomerPriceList = KsBeanUtil.convert(customerPriceList, GoodsCustomerPriceDTO.class);
            goodsCustomerPriceList.forEach(v -> v.setGoodsId(goodsId));
            goodsSaveRequest.setGoodsCustomerPrices(goodsCustomerPriceList);

            List<GoodsIntervalPrice> intervalPriceList = goodsIntervalPriceRepository.findByGoodsId(goodsAudit.getGoodsId());
            List<GoodsIntervalPriceDTO> goodsIntervalPriceList = KsBeanUtil.convert(intervalPriceList, GoodsIntervalPriceDTO.class);
            goodsIntervalPriceList.forEach(v -> v.setGoodsId(goodsId));
            goodsSaveRequest.setGoodsIntervalPrices(goodsIntervalPriceList);
            Map<String, String> goodsIdMap = Maps.newHashMap();
            if (Objects.nonNull(goodsAudit.getOldGoodsId()) && Objects.nonNull(goodsAudit.getGoodsId())){
                goodsIdMap.put(goodsAudit.getOldGoodsId(), goodsAudit.getGoodsId());
            }
            //针对sku设价保存goodsId和goodsInfoId关系
            goodsSaveRequest.setGoodsIdMap(goodsIdMap);
            goodsSaveRequest.setGoodsInfoMaps(goodsInfoMaps);
            goodsSaveRequest.setIsIndependent(goods.getIsIndependent());
            GoodsModifyInfoResponse map = goodsService.editAll(goodsSaveRequest);

            if (Objects.nonNull(map)) {
                //更改商家代销商品的可售性
                Boolean isDealGoodsVendibility = map.getIsDealGoodsVendibility();
                if (isDealGoodsVendibility != null && isDealGoodsVendibility) {
                    goodsService.dealGoodsVendibility(ProviderGoodsNotSellRequest.builder().checkFlag(Boolean.TRUE).stockFlag(Boolean.TRUE)
                            .goodsIds(Lists.newArrayList(goodsId)).build());
                }
            }
        }
        return isProvideGoods;
    }

    /**
     * 初次审核商品
     * @param checkRequest
     * @param initialAuditGoods
     */
    private void initalAuditGoods(GoodsCheckRequest checkRequest, List<GoodsAudit> initialAuditGoods) {
        Goods saveGoods = null;
        for (GoodsAudit initialAuditGood : initialAuditGoods) {
            initialAuditGood.setAuditStatus(CheckStatus.CHECKED);
            Goods goods = KsBeanUtil.convert(initialAuditGood, Goods.class);
            saveGoods = goodsRepository.save(goods);
            checkRequest.getGoodsIds().add(goods.getGoodsId());
        }
        goodsInfoRepository.updateAuditDetail(checkRequest.getAuditStatus(), initialAuditGoods.stream().map(GoodsAudit::getGoodsId).collect(Collectors.toList()));
        goodsAuditRepository.deleteByIdList(initialAuditGoods.stream().map(GoodsAudit::getGoodsId).collect(Collectors.toList()));
        if(Objects.nonNull(saveGoods)) {
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
            GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
            sendRequest.setGoodsIds(StringUtils.isEmpty(saveGoods.getGoodsId())?Collections.EMPTY_LIST:Arrays.asList(saveGoods.getGoodsId()));
            sendRequest.setFlag(GoodsEditFlag.INFO);
            sendRequest.setIsProvider(
                    0 == saveGoods.getGoodsSource()
                            || 4 == saveGoods.getGoodsSource()
                            || 2 == saveGoods.getGoodsSource());
            mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
            mqSendProvider.send(mqSendDTO);
        }
    }

    private void forbadeAuditGoods(GoodsCheckRequest checkRequest) {
        List<Goods> goodsList = goodsRepository.findAllById(checkRequest.getGoodsIds());
        //如果该商品正在待审核,直接驳回
        goodsAuditRepository.updateByOldGoodsIdsAndAuditState(goodsList.stream().map(Goods::getGoodsId).collect(Collectors.toList()), CheckStatus.WAIT_CHECK);
        List<GoodsAudit> goodsAudits = goodsList.stream().map(goods -> {
            GoodsAudit goodsAudit = KsBeanUtil.convert(goods, GoodsAudit.class);
            goodsAudit.setOldGoodsId(goods.getGoodsId());
            goodsAudit.setGoodsId(goods.getGoodsId());
            goodsAudit.setAuditType(AuditType.SECOND_AUDIT.toValue());
            goodsAudit.setAuditStatus(CheckStatus.FORBADE);
            goodsAudit.setAuditReason(checkRequest.getAuditReason());
            return goodsAudit;
        }).collect(Collectors.toList());
        goodsAuditRepository.saveAll(goodsAudits);
        goodsInfoRepository.updateAuditDetail(checkRequest.getAuditStatus(), checkRequest.getGoodsIds());
        //删除禁售商品
        goodsRepository.updateAuditDetail(checkRequest.getAuditStatus(), checkRequest.getAuditReason(), checkRequest.getGoodsIds());

        List<GoodsInfo> goodsInfoList = goodsInfoRepository.findByGoodsIds(checkRequest.getGoodsIds());
        getPriceAdjustmentRecordDetails(goodsInfoList,Constants.TWO, checkRequest.getAuditReason());

        // 处理商品禁售MQ  只处理商家商品
        if (CollectionUtils.isNotEmpty(goodsInfoList)
                && 1 == goodsInfoList.get(0).getGoodsSource()) {
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
            GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
            sendRequest.setGoodsInfoIds(
                    goodsInfoList.stream()
                            .map(GoodsInfo::getGoodsInfoId)
                            .collect(Collectors.toList()));
            sendRequest.setFlag(GoodsEditFlag.FORBID);
            mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
            mqSendProvider.send(mqSendDTO);
        }
    }

    /**
     * 驳回商品
     * @param checkRequest
     * @param auditList
     */
    private void turnDownAuditGoods(GoodsCheckRequest checkRequest, List<GoodsAudit> auditList) {
        //审核驳回
        List<String> forbidGoodsIds = auditList
                .stream().filter(v -> Objects.equals(v.getGoodsId(), v.getOldGoodsId()))
                .map(GoodsAudit::getGoodsId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(forbidGoodsIds)){
            goodsAuditRepository.updateAuditDetail(CheckStatus.FORBADE, checkRequest.getAuditReason(), forbidGoodsIds);
            goodsInfoRepository.updateAuditDetail(CheckStatus.FORBADE, forbidGoodsIds);
        }
        checkRequest.getGoodsIds().removeAll(forbidGoodsIds);
        if (CollectionUtils.isNotEmpty(checkRequest.getGoodsIds())){
            goodsAuditRepository.updateAuditDetail(checkRequest.getAuditStatus(), checkRequest.getAuditReason(), checkRequest.getGoodsIds());
            goodsInfoRepository.updateAuditDetail(checkRequest.getAuditStatus(), checkRequest.getGoodsIds());
        }

        //批量改价商品记录
        List<GoodsInfo> goodsInfoList = goodsInfoRepository.findByGoodsIds(checkRequest.getGoodsIds());
        getPriceAdjustmentRecordDetails(goodsInfoList,Constants.TWO, checkRequest.getAuditReason());

        checkRequest.getGoodsIds().addAll(forbidGoodsIds);
    }

    private List<PriceAdjustmentRecordDetail> getPriceAdjustmentRecordDetails(List<GoodsInfo> infoList,Integer auditStatus,String reason) {
        //判断该商品是否为批量改价商品,且定时生效
        List<PriceAdjustmentRecordDetail> recordDetails = recordDetailRepository
                .findByGoodsInfoIds(infoList.stream()
                        .map(v->StringUtils.isNotBlank(v.getOldGoodsInfoId())?v.getOldGoodsInfoId():v.getGoodsInfoId())
                        .collect(Collectors.toList()));
        recordDetails.forEach(v -> {
            v.setAuditStatus(auditStatus);
            v.setAuditTime(LocalDateTime.now());
            v.setAuditReason(reason);
        });
        recordDetailRepository.saveAll(recordDetails);
        return recordDetails;
    }


    /**
     * 处理商品库商品
     *
     * @param checkRequest
     */
    private List<String> dealStandardGoods(GoodsCheckRequest checkRequest) {
        //如果商品库里有此商品    禁售状态 同步到商品库
        List<StandardGoodsRel> standardGoodsRels = standardGoodsRelRepository.findByGoodsIds(checkRequest.getGoodsIds());

        if (CollectionUtils.isNotEmpty(standardGoodsRels) && (CheckStatus.FORBADE == checkRequest.getAuditStatus())) {
            List<String> standardGoodsIds = standardGoodsRels.stream().map(StandardGoodsRel::getStandardId).collect(Collectors.toList());
            standardGoodsRepository.deleteByGoodsIds(standardGoodsIds);
            standardSkuRepository.deleteByGoodsIds(standardGoodsIds);
            standardGoodsRelRepository.deleteByGoodsIds(checkRequest.getGoodsIds());
            return standardGoodsIds;
        }
        return Collections.emptyList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> dealProviderGoodsEdit(GoodsCheckRequest checkRequest) {
        List<String> standardIds = new ArrayList<>();
        for (String goodsId : checkRequest.getGoodsIds()) {
            StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByGoodsId(goodsId);
            //供应商商品 审核通过的情况(1.boss删除了供应商商品库 2.boss没删除供应商商品库，供应商二次编辑 3.供应商第一次发布商品)
            Goods g = goodsRepository.findById(goodsId).orElse(null);
            Goods goods = KsBeanUtil.convert(g, Goods.class);
            if (Objects.isNull(g)) {
                GoodsAudit goodsAudit = goodsAuditRepository.findById(goodsId).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
                goods = KsBeanUtil.convert(goodsAudit, Goods.class);
            }
            if (goods.getGoodsSource() == 0 && checkRequest.getAuditStatus() == CheckStatus.CHECKED) {
                if (standardGoodsRel != null) {
                    if (standardGoodsRel.getDelFlag().equals(DeleteFlag.YES)) {
                        //1.boss删除了供应商商品库  暂时不管，等他导入商品库的时候，再同步供应商商品库
                    } else if (standardGoodsRel.getDelFlag().equals(DeleteFlag.NO)) {
                        //2.boss没删除供应商商品库，供应商二次编辑-------需要同步供应商商品库，然后设置商家商品为待同步
                        standardImportService.synProviderGoods(goods.getGoodsId(), standardGoodsRel.getStandardId());
                        standardIds.add(standardGoodsRel.getStandardId());
                    }
                    //如果审核的时候，供应商商品是上架状态，那么关联的商家商品也要变为商家上架状态 如果是下架，商家商品也是下架20200506
//                    List<Goods> supplierGoods = goodsRepository.findAllByProviderGoodsId(goods.getGoodsId());
//                    supplierGoods.forEach(g->{g.setAddedFlag(goods.getAddedFlag());});
//                    goodsRepository.saveAll(supplierGoods);
                } else {
                    //3.供应商第一次发布商品，走正常流程
                    // 同步到商品库
                    GoodsRequest synRequest = new GoodsRequest();
                    synRequest.setGoodsIds(Collections.singletonList(goodsId));
                    standardIds.addAll(standardImportService.importStandard(synRequest));
                }
            }
        }
        return standardIds;
    }

    /**
     * 待审核统计
     *
     * @param request
     * @return
     */
    public Long countByTodo(GoodsQueryRequest request) {
        request.setAuditStatus(CheckStatus.WAIT_CHECK);
        // 商家ID为空则可视为Boss端查询需要屏蔽 供应商商品
        if (Objects.isNull(request.getStoreId())) {
            request.setGoodsSource(GoodsSource.SELLER.toValue());
        }
        request.setDelFlag(DeleteFlag.NO.toValue());

        GoodsAuditQueryRequest goodsAuditQueryRequest = new GoodsAuditQueryRequest();
        KsBeanUtil.copyProperties(request, goodsAuditQueryRequest);
        goodsAuditQueryRequest.setAuditStatus(request.getAuditStatus().toValue());
        return goodsAuditRepository.count(GoodsAuditWhereCriteriaBuilder.build(goodsAuditQueryRequest));
    }
}
