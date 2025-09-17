package com.wanmi.sbc.goods.info.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.constant.GoodsEditMsg;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.goods.ProviderGoodsNotSellRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsModifyInfoResponse;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSaveVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.ContractBrandRepository;
import com.wanmi.sbc.goods.brand.request.ContractBrandQueryRequest;
import com.wanmi.sbc.goods.brand.service.GoodsBrandService;
import com.wanmi.sbc.goods.buycyclegoods.repository.BuyCycleGoodsRepository;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.ContractCateRepository;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.ContractCateQueryRequest;
import com.wanmi.sbc.goods.common.GoodsCommonService;
import com.wanmi.sbc.goods.common.SystemPointsConfigService;
import com.wanmi.sbc.goods.distributor.goods.repository.DistributiorGoodsInfoRepository;
import com.wanmi.sbc.goods.flashsalegoods.model.root.FlashSaleGoods;
import com.wanmi.sbc.goods.flashsalegoods.service.FlashSaleGoodsService;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditWhereCriteriaBuilder;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsPropDetailRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsRequest;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import com.wanmi.sbc.goods.mainimages.GoodsMainImage;
import com.wanmi.sbc.goods.mainimages.GoodsMainImageRepository;
import com.wanmi.sbc.goods.message.StoreMessageBizService;
import com.wanmi.sbc.goods.pointsgoods.repository.PointsGoodsRepository;
import com.wanmi.sbc.goods.price.model.root.GoodsCustomerPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsLevelPrice;
import com.wanmi.sbc.goods.price.repository.GoodsCustomerPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsIntervalPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsLevelPriceRepository;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsEditDetailService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.service.StandardGoodsService;
import com.wanmi.sbc.goods.standard.service.StandardImportAsyncService;
import com.wanmi.sbc.goods.standard.service.StandardImportService;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.goods.storecate.repository.StoreCateRepository;
import com.wanmi.sbc.goods.storecate.request.StoreCateQueryRequest;
import com.wanmi.sbc.goods.storegoodstab.model.root.GoodsTabRela;
import com.wanmi.sbc.goods.storegoodstab.repository.GoodsTabRelaRepository;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuService;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponUpdateBindRequest;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/***
 * 商品基础信息维护抽象类
 * @className AbstractGoodsBaseService
 * @author zhengyang
 * @date 2021/7/6 15:01
 **/
@Slf4j
public abstract class AbstractGoodsBaseService implements GoodsBaseInterface {

    @Resource
    protected GoodsRepository goodsRepository;
    @Resource
    protected GoodsInfoRepository goodsInfoRepository;
    @Resource
    protected GoodsSpecRepository goodsSpecRepository;
    @Resource
    protected GoodsSpecDetailRepository goodsSpecDetailRepository;
    @Resource
    protected GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;
    @Resource
    protected GoodsImageRepository goodsImageRepository;
    @Resource
    protected GoodsMainImageRepository goodsMainImageRepository;
    @Resource
    protected GoodsTabRelaRepository goodsTabRelaRepository;
    @Resource
    protected StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;
    @Resource
    protected GoodsCommonService goodsCommonService;
    @Resource
    protected OsUtil osUtil;
    @Resource
    protected ContractBrandRepository contractBrandRepository;
    @Resource
    protected StoreCateRepository storeCateRepository;
    @Resource
    protected ContractCateRepository contractCateRepository;

    @Resource
    protected GoodsBrandService goodsBrandService;

    @Resource
    protected GoodsCateRepository goodsCateRepository;

    @Resource
    protected GoodsInfoStockService goodsInfoStockService;
    @Resource
    protected StandardImportService standardImportService;
    @Resource
    protected GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;
    @Resource
    protected PointsGoodsRepository pointsGoodsRepository;
    @Resource
    protected FlashSaleGoodsService flashSaleGoodsService;
    @Resource
    protected GoodsLevelPriceRepository goodsLevelPriceRepository;
    @Resource
    protected GoodsCustomerPriceRepository goodsCustomerPriceRepository;
    @Resource
    protected GoodsIntervalPriceRepository goodsIntervalPriceRepository;
    @Resource
    protected GoodsPropDetailRelRepository goodsPropDetailRelRepository;
    @Resource
    protected StandardGoodsRelRepository standardGoodsRelRepository;
    @Resource
    protected DistributiorGoodsInfoRepository distributiorGoodsInfoRepository;
    @Resource
    protected StandardImportAsyncService standardImportAsyncService;

    @Autowired
    private ProviderGoodsEditDetailService providerGoodsEditDetailService;

    @Autowired
    private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired
    private GoodsCommissionConfigService goodsCommissionConfigService;

    @Resource
    protected GoodsAuditRepository goodsAuditRepository;
    @Resource
    protected StandardGoodsService standardGoodsService;

    @Autowired
    private WechatSkuService wechatSkuService;


    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private GoodsLedgerService goodsLedgerService;

    @Autowired private MqSendProvider mqSendProvider;

    @Autowired
    private BuyCycleGoodsRepository buyCycleGoodsRepository;

    @Autowired
    private StoreMessageBizService storeMessageBizService;


    /**
     * 商品新增模板类
     *
     * @param saveRequest 保存对象
     * @return 保存后的商品ID
     * @throws SbcRuntimeException 业务异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsAddResponse add(GoodsSaveRequest saveRequest) throws SbcRuntimeException {
        try {
            log.info("商品新增开始");
            long startTime = System.currentTimeMillis();

            /****** 0.拆分临时变量 ******/
            // 商品对象
            GoodsSaveDTO goods = saveRequest.getGoods();
            goods.setGoodsName(StringUtils.trim(goods.getGoodsName()));
            // 商品SKU集合
            List<GoodsInfoSaveDTO> goodsInfos = saveRequest.getGoodsInfos();
            // 商品图片集合
            List<GoodsImage> goodsImages = KsBeanUtil.convert(saveRequest.getImages(), GoodsImage.class);
            List<GoodsMainImage> mainImage = KsBeanUtil.convert(saveRequest.getMainImage(), GoodsMainImage.class);


            /****** 1.商品校验 ******/
            // 1.1 校验商品是否重复
            this.checkGoodsInfoRepeat(goods, null, goodsInfos);

            // 1.2 校验商品分类、商品品牌、是否存在
            // 1.3 如果是S2B，校验签约分类、签约品牌、店铺分类是否正确
            this.checkBasic(goods,0);

            /****** 2.填充默认值 ******/
            // 2.1 填充商品默认值,新增时旧商品对象为空
            populateGoodsDefaultVal(saveRequest, goods, null, goodsInfos, goods.getAddedFlag());

            /****** 3.商品保存 ******/
            // 3.1 保存商品SPU并生成SPU ID
            goods = saveMainGoods(goods);

            final String goodsId = goods.getGoodsId();

            // 3.2 保存图片
            saveGoodsImage(goods, goodsImages, null);
            saveGoodsMainImage(goods, mainImage);
            // 3.3 保存店铺分类
            saveGoodsStoreCateRel(goods, false);
            // 3.4 保存商品属性
            saveGoodsPropertyDetailRel(saveRequest, goodsId, null, null);
            // 3.5 保存商品详情模板关联实体
            saveGoodsTabRel(saveRequest, goodsId);

            // 3.6 保存商品规格与规格详情
            List<GoodsSpecSaveDTO> specs = saveRequest.getGoodsSpecs();
            List<GoodsSpecDetailSaveDTO> specDetails = saveRequest.getGoodsSpecDetails();
            saveGoodsSpecAndDetails(goods, null, goodsInfos, specs, specDetails, null, null);

            // 3.7 保存商品SKU
            saveGoodsInfo(goods, goodsInfos, specs, specDetails, startTime, saveRequest.getSkuEditPrice(), saveRequest,null);

            /****** 4.商品导入到ES ******/
            // 4.1 供应商发布商品审核通过则直接加入到商品库中
            import2Es(goods, goodsId);

            log.info("商品新增结束->花费{}毫秒", (System.currentTimeMillis() - startTime));

            GoodsAddResponse response = new GoodsAddResponse();
            response.setResult(goodsId);
            response.setAuditStatus(goods.getAuditStatus());
            return response;
        } catch (SbcRuntimeException e) {
            log.error("商品新增异常", e);
            throw e;
        } catch (Exception e) {
            log.error("商品新增异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 商品更新
     *
     * @param saveRequest 商品更新请求对象
     * @return 更新参数
     * @throws SbcRuntimeException 更新业务异常
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public GoodsModifyInfoResponse edit(GoodsSaveRequest saveRequest) throws SbcRuntimeException {
        log.info("商品更新开始");
        long startTime = System.currentTimeMillis();

        GoodsCommissionConfig goodsCommissionConfig = null;
        if (Objects.nonNull(saveRequest.getBaseStoreId())){
            goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(saveRequest.getBaseStoreId());
        }

        /****** 0.拆分临时变量，查询旧商品 ******/
        GoodsSaveDTO newGoods = saveRequest.getGoods();
        newGoods.setGoodsName(StringUtils.trim(newGoods.getGoodsName()));
        // 商品SKU集合
        List<GoodsInfoSaveDTO> goodsInfos = saveRequest.getGoodsInfos();
        // 商品图片集合
        List<GoodsImage> goodsImages = KsBeanUtil.convert(saveRequest.getImages(), GoodsImage.class);
        // 商品主图
        List<GoodsMainImage> mainImage = KsBeanUtil.convert(saveRequest.getMainImage(), GoodsMainImage.class);

        GoodsSaveDTO finalGoods = KsBeanUtil.copyPropertiesThird(goodsRepository.findById(newGoods.getGoodsId()).orElse(null), GoodsSaveDTO.class);
        if (finalGoods == null || finalGoods.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        Integer oldMoreSpecFlag = finalGoods.getMoreSpecFlag();

        //校验分账绑定关系
        goodsLedgerService.checkLedgerBindState(newGoods.getAddedFlag(),
                newGoods.getAddedTimingFlag(),
                finalGoods.getStoreId(),
                Collections.singletonList(finalGoods.getProviderId()));

        if(PluginType.CROSS_BORDER.equals(finalGoods.getPluginType())) {
            newGoods.setPluginType(finalGoods.getPluginType());
        }
        //修改上下架状态,下单方式,加价比例实时生效
        finalGoods.setGoodsBuyTypes(newGoods.getGoodsBuyTypes());
        Integer oldAddedFlag = finalGoods.getAddedFlag();
        finalGoods.setAddedFlag(newGoods.getAddedFlag());
        finalGoods.setIsIndependent(saveRequest.getIsIndependent());
        if (newGoods.getAddedTimingFlag() != null && newGoods.getAddedTimingFlag()) {
            finalGoods.setAddedTimingTime(newGoods.getAddedTimingTime());
            finalGoods.setAddedTimingFlag(newGoods.getAddedTimingFlag());
        }
        //定时下架
        if (newGoods.getTakedownTimeFlag() != null && newGoods.getTakedownTimeFlag()){
            finalGoods.setTakedownTimeFlag(newGoods.getTakedownTimeFlag());
            finalGoods.setTakedownTime(newGoods.getTakedownTime());
        }
        //如果不是按订货量设置叠加客户等级折扣为关闭
        if (newGoods.getPriceType() != null && GoodsPriceType.STOCK.toValue() != newGoods.getPriceType()){
            newGoods.setLevelDiscountFlag(DefaultFlag.NO.toValue());
        }

        //如果商品为代销商品且有待审核数据,待审核数据同步修改加价比例后的价格
        if(Objects.nonNull(finalGoods.getProviderGoodsId())
                && Objects.nonNull(goodsCommissionConfig)
                && Objects.equals(CommissionSynPriceType.AI_SYN,goodsCommissionConfig.getSynPriceType())){
            finalGoods.setMarketPrice(newGoods.getMarketPrice());
            List<GoodsInfo> goodsInfoList = goodsInfoRepository
                    .findByGoodsIds(Collections.singletonList(finalGoods.getGoodsId()));
            for (GoodsInfo info : goodsInfoList) {
                Optional<GoodsInfoSaveDTO> opt = goodsInfos.stream()
                        .filter(oldInfo -> Objects.equals(oldInfo.getGoodsInfoId(), info.getGoodsInfoId()))
                        .findFirst();
                opt.ifPresent(goodsInfo -> info.setMarketPrice(goodsInfo.getMarketPrice()));
            }
            goodsInfoRepository.saveAll(goodsInfoList);
        }
        finalGoods.setGoodsId(goodsRepository.save(KsBeanUtil.copyPropertiesThird(finalGoods, Goods.class)).getGoodsId());

        GoodsSaveDTO oldGoods = KsBeanUtil.convert(finalGoods, GoodsSaveDTO.class);

        BigDecimal oldSpuMarketPrice = Objects.isNull(oldGoods.getMarketPrice()) ? BigDecimal.ZERO : oldGoods.getMarketPrice();
        //为供应商spu添加价格独立设置属性
        if (Objects.nonNull(newGoods.getProviderGoodsId())) {
            newGoods.setIsIndependent(saveRequest.getIsIndependent());
        }
        if (Objects.nonNull(oldGoods.getProviderGoodsId())) {
            oldGoods.setIsIndependent(saveRequest.getIsIndependent());
        }

        /****** 1.商品修改校验 ******/
        // 如果S2B模式下，商品已审核无法编辑分类
        // TODO 京东商品处理迁移到子类 如果是京东商品，同步时支持修改商品CateId
        if (GoodsSource.VOP.toValue() != oldGoods.getGoodsSource()) {
            if (osUtil.isS2b()
                    && CheckStatus.CHECKED.toValue() == oldGoods.getAuditStatus().toValue()
                    && (!oldGoods.getCateId().equals(newGoods.getCateId()))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030039);
            }
        }

        // 1.1 校验商品是否重复
        this.checkGoodsInfoRepeat(oldGoods, newGoods, goodsInfos);

        // 1.2 校验商品分类、商品品牌、是否存在
        // 1.3 如果是S2B，校验签约分类、签约品牌、店铺分类是否正确
        newGoods.setStoreId(oldGoods.getStoreId());
        this.checkBasic(newGoods,1);

        /****** 2.填充默认值 ******/
        // 2.1 填充商品默认值,修改时旧商品对象不为空
        boolean[] changeStatus = populateGoodsDefaultVal(saveRequest, newGoods, oldGoods, goodsInfos, oldAddedFlag);

        //比较是否只修改了上下架与下单方式
        if (!saveRequest.getIsChecked()) {
            boolean isAudit;
            if (Objects.equals(BoolFlag.YES,saveRequest.getIsBatchEditPrice())){
                isAudit = false;
            }else {
                isAudit = checkGoodsIsAudit(finalGoods, newGoods, saveRequest,goodsCommissionConfig,true);
            }

            if (isAudit) {
                oldGoods.setAuditStatus(CheckStatus.CHECKED);

                //如果商品为代销商品且有待审核数据,待审核数据同步修改加价比例后的价格
                if(Objects.nonNull(oldGoods.getProviderGoodsId())
                        && Objects.nonNull(goodsCommissionConfig)
                        && Objects.equals(CommissionSynPriceType.AI_SYN,goodsCommissionConfig.getSynPriceType())){
                    List<GoodsAudit> goodsAuditList = goodsAuditRepository
                            .findByOldGoodsIdsAndAuditState(Collections.singletonList(oldGoods.getGoodsId()),CheckStatus.WAIT_CHECK);
                    if (CollectionUtils.isNotEmpty(goodsAuditList)){
                        GoodsAudit goodsAudit = goodsAuditList.get(0);
                        goodsAudit.setMarketPrice(oldGoods.getMarketPrice());
                        goodsAudit.setIsIndependent(oldGoods.getIsIndependent());
                        goodsAuditRepository.save(goodsAudit);
                        List<GoodsInfo> goodsInfoList = goodsInfoRepository
                                .findByGoodsIds(Collections.singletonList(goodsAudit.getGoodsId()));
                        for (GoodsInfo info : goodsInfoList) {
                            Optional<GoodsInfoSaveDTO> opt = goodsInfos.stream()
                                    .filter(oldInfo -> Objects.equals(oldInfo.getGoodsInfoId(), info.getOldGoodsInfoId()))
                                    .findFirst();
                            opt.ifPresent(goodsInfo -> info.setMarketPrice(goodsInfo.getMarketPrice()));
                            opt.ifPresent(goodsInfo -> info.setQuickOrderNo(goodsInfo.getQuickOrderNo()));
                        }
                        goodsInfoRepository.saveAll(goodsInfoList);
                    }
                }
            } else {
                //如果进二次审核，sku同步spu上下架状态、定时上下架状态
                List<GoodsInfo> goodsInfoList = goodsInfoRepository
                        .findByGoodsIds(Collections.singletonList(oldGoods.getGoodsId()));
                for (GoodsInfo info : goodsInfoList) {
                    //如果商品不是部分上架状态则 GoodsInfo 和 Goods 保持一直
                    if (AddedFlag.PART.toValue() != newGoods.getAddedFlag()) {
                        info.setAddedFlag(oldGoods.getAddedFlag());
                    }
                    if (oldGoods.getAddedTimingFlag() != null && oldGoods.getAddedTimingFlag()) {
                        info.setAddedTimingTime(oldGoods.getAddedTimingTime());
                        info.setAddedTimingFlag(oldGoods.getAddedTimingFlag());
                    }
                    //定时下架
                    if (oldGoods.getTakedownTimeFlag() != null && oldGoods.getTakedownTimeFlag()){
                        info.setTakedownTimeFlag(oldGoods.getTakedownTimeFlag());
                        info.setTakedownTime(oldGoods.getTakedownTime());
                    }
                    Optional<GoodsInfoSaveDTO> opt = goodsInfos.stream()
                            .filter(oldInfo -> Objects.equals(oldInfo.getGoodsInfoId(), info.getGoodsInfoId()))
                            .findFirst();
                    opt.ifPresent(goodsInfo -> info.setQuickOrderNo(goodsInfo.getQuickOrderNo()));
                }
                goodsInfoRepository.saveAll(goodsInfoList);
            }
        }

        // sku维度设价
        if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
            oldGoods.setAuditStatus(CheckStatus.WAIT_CHECK);
        }

        //如果商品为代销商品且有待审核数据,待审核数据供应商订货号保留
        if(StringUtils.isNotEmpty(finalGoods.getProviderGoodsId())){
            List<GoodsInfo> goodsInfoList = goodsInfoRepository
                    .findByGoodsIds(Collections.singletonList(finalGoods.getGoodsId()));
            for (GoodsInfoSaveDTO info : goodsInfos) {
                Optional<GoodsInfo> opt = goodsInfoList.stream()
                        .filter(oldInfo -> Objects.equals(oldInfo.getGoodsInfoId(), info.getGoodsInfoId()))
                        .findFirst();
                opt.ifPresent(goodsInfo -> info.setProviderQuickOrderNo(goodsInfo.getProviderQuickOrderNo()));
            }
        }

        // TODO 临时解决方案，O2O商品不需要二次审核，在此判断是否需要二次审核，有时间需要对该逻辑进行拆分
        /****** 3.商品保存 ******/
        // 3.1 保存商品SPU并生成SPU ID
        if (getNeedSecondCheck() && Objects.equals(CheckStatus.WAIT_CHECK, oldGoods.getAuditStatus())) {
            // 判断商品是否正在待审核
            checkAudit(saveRequest);

            // 拷贝原始goodsId，获取原始SKU数据
            String originalGoodsId = String.valueOf(oldGoods.getGoodsId());
            List<GoodsInfo> originalGoodsInfos = goodsInfoRepository.findOriginalGoodsInfos(originalGoodsId);
            Map<String, Integer> originalAddedFlagMap = originalGoodsInfos.stream()
                    .collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, GoodsInfo::getAddedFlag));

            GoodsAudit goodsAudit = KsBeanUtil.convert(oldGoods, GoodsAudit.class);
            goodsAudit.setOldGoodsId(oldGoods.getGoodsId());
            goodsAudit.setAuditType(AuditType.SECOND_AUDIT.toValue());
            goodsAudit.setGoodsId(UUIDUtil.getUUID());
            goodsAudit.setUpdateTime(LocalDateTime.now());
            String orderGoodsId = oldGoods.getGoodsId();
            oldGoods.setGoodsId(goodsAuditRepository.save(goodsAudit).getGoodsId());

            //替换GoodsId
            checkGoodsId(saveRequest, oldGoods);

            // 3.2 保存图片
            saveGoodsImage(oldGoods, goodsImages, null);
            saveGoodsMainImage(oldGoods, mainImage);
            // 3.3 保存店铺分类
            saveGoodsStoreCateRel(oldGoods, false);
            // 3.4 保存商品属性
            saveGoodsPropertyDetailRel(saveRequest, oldGoods.getGoodsId(), null, null);
            // 3.5 保存商品详情模板关联实体
            saveGoodsTabRel(saveRequest, oldGoods.getGoodsId());

            // 3.6 保存商品规格与规格详情
            List<GoodsSpecSaveDTO> specs = saveRequest.getGoodsSpecs();
            List<GoodsSpecDetailSaveDTO> specDetails = saveRequest.getGoodsSpecDetails();
            saveGoodsSpecAndDetails(oldGoods, null, goodsInfos, specs, specDetails, null, null);

            // 3.7 保存商品SKU
            if (AddedFlag.PART.toValue() == saveRequest.getGoods().getAddedFlag()) {
                // SPU为部分上架时，应该为存在OldGoodsInfo的数据副本填充AddedFlag，否则saveGoodsInfo中会直接置为上架
                // 这里只处理部分上架时的情况，配合saveGoodsInfo中的方法，共同完成对AddedFlag的赋值
                goodsInfos.stream()
                        // 筛选AddedFlag为空且存在OldGoodsInfo的数据
                        .filter(item -> Objects.isNull(item.getAddedFlag()) && Objects.nonNull(item.getOldGoodsInfoId()))
                        // 为其填充AddedFlag
                        .forEach(item -> item.setAddedFlag(originalAddedFlagMap.getOrDefault(item.getOldGoodsInfoId(), null)));

                Goods updateGoods = KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class);
                updateGoods.setGoodsId(orderGoodsId);
                long addedCount = goodsInfos.stream().filter(g -> DeleteFlag.NO.equals(g.getDelFlag()) && AddedFlag.YES.toValue() == g.getAddedFlag()).count();
                long addedDownCount = goodsInfos.stream().filter(g -> DeleteFlag.NO.equals(g.getDelFlag()) && AddedFlag.NO.toValue() == g.getAddedFlag()).count();
                long count = goodsInfos.stream().filter(g -> DeleteFlag.NO.equals(g.getDelFlag())).count();
                if (addedCount>0 && addedCount == count) {
                    updateGoods.setAddedFlag(AddedFlag.YES.toValue());
                    goodsRepository.save(updateGoods);
                }
                if (addedDownCount>0 && addedDownCount == count) {
                    updateGoods.setAddedFlag(AddedFlag.NO.toValue());
                    goodsRepository.save(updateGoods);
                }
            }
            saveGoodsInfo(oldGoods,
                    goodsInfos,
                    specs,
                    specDetails,
                    startTime,
                    saveRequest.getSkuEditPrice(),
                    saveRequest,
                    goodsCommissionConfig);
            // 发送异步MQ
            update(KsBeanUtil.convertList(goodsInfos, GoodsInfo.class), null, KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));

            // 3.8 更新ES
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(Collections.singletonList(originalGoodsId)).build());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(originalGoodsId).build());

            // 3.8 更新ES
            esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(Collections.singletonList(originalGoodsId)).build());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(originalGoodsId).build());

            log.info("商品更新结束->花费{}毫秒", (System.currentTimeMillis() - startTime));

            return null;
        } else {
            //商家关闭所有供应商商品代销，删除当前spu,开放导入功能
            if (StringUtils.isNotBlank(oldGoods.getProviderGoodsId())) {
                List<GoodsInfoSaveDTO> consignAll = goodsInfos.stream().filter(goodsInfo -> goodsInfo.getDelFlag().toValue() == 0).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(consignAll)) {
                    oldGoods.setDelFlag(DeleteFlag.YES);
                    standardGoodsRelRepository.deleteByGoodsIds(Collections.singletonList(oldGoods.getGoodsId()));
                }
            }

            //*******用于处理供应商商品变更业务处理
            Map<GoodsEditType, ArrayList<String>> editMap = new HashMap<>();
            List<String> updateSupplyPrice = new ArrayList<>();
            providerGoodsEditDetailService.goodsEdit(finalGoods, oldGoods, editMap);

            goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));

            // 3.2 删除或更新旧图片,保存新图片
            saveGoodsImage(newGoods, deleteOrUpdateOldImages(newGoods, goodsImages, editMap), editMap);
            saveGoodsMainImage(oldGoods, mainImage);
            // 3.3 保存店铺分类
            saveGoodsStoreCateRel(newGoods, true);
            // 3.4 保存商品属性
            saveGoodsPropertyDetailRel(saveRequest, newGoods.getGoodsId(), oldGoods, editMap);
            // 3.5 保存商品详情模板关联实体
            saveGoodsTabRel(saveRequest, newGoods.getGoodsId());

            // 3.6 保存商品规格与规格详情
            List<GoodsSpecSaveDTO> specs = saveRequest.getGoodsSpecs();
            List<GoodsSpecDetailSaveDTO> specDetails = saveRequest.getGoodsSpecDetails();
            saveGoodsSpecAndDetails(newGoods, oldGoods, goodsInfos, specs, specDetails, editMap, oldMoreSpecFlag);


            // 只存储新增的SKU数据，用于当修改价格及订货量设置为否时，只为新SKU增加相关的价格数据
            // 需要被添加的sku信息
            List<GoodsInfoSaveDTO> newGoodsInfo = new ArrayList<>();

            // 更新原有的SKU列表
            // 需要被更新的sku信息
            List<GoodsInfoSaveDTO> oldGoodsInfos = new ArrayList<>();
            // 需要被删除的sku信息
            List<String> delInfoIds = new ArrayList<>();
            //旧SKU绑定的卡券ids
            List<Long> oldElectronicIds = new ArrayList<>();

            // 主要用于供应商商品信息同步使用
            Map<String, GoodsInfoSaveDTO> delInfoNoMap = new HashMap<>();
            Map<String, GoodsInfoSaveDTO> newInfoNoMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
                infoQueryRequest.setGoodsId(newGoods.getGoodsId());
                infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
                List<GoodsInfo> goodsInfoList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
                List<GoodsInfoSaveDTO> oldInfos = KsBeanUtil.convertList(goodsInfoList, GoodsInfoSaveDTO.class);

                if (CollectionUtils.isNotEmpty(oldInfos)) {
                    for (GoodsInfoSaveDTO oldInfo : oldInfos) {
                        if (GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == oldInfo.getGoodsType()) {
                            oldElectronicIds.add(oldInfo.getElectronicCouponsId());
                        }
                        if (Objects.isNull(oldInfo.getStock())) {
                            oldInfo.setStock(0L);
                        }
                        //若是页面的sku在数据库中存在，则取页面sku操作
                        Optional<GoodsInfoSaveDTO> infoOpt = goodsInfos.stream().filter(goodsInfo -> oldInfo.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).findFirst();
                        //如果SKU存在，更新
                        if (infoOpt.isPresent()) {
                            //处理供应商数据变更
                            providerGoodsEditDetailService.goodsInfoEdit(infoOpt.get(), oldInfo, editMap, updateSupplyPrice);
                            infoOpt.get().setAddedFlag(oldInfo.getAddedFlag());
                            infoOpt.get().setAddedTimingTime(oldInfo.getAddedTimingTime());
                            infoOpt.get().setAddedTimingFlag(oldInfo.getAddedTimingFlag());
                            infoOpt.get().setVendibility(oldInfo.getVendibility());
                            infoOpt.get().setTakedownTimeFlag(oldInfo.getTakedownTimeFlag());
                            infoOpt.get().setTakedownTime(oldInfo.getTakedownTime());
                            infoOpt.get().setProviderQuickOrderNo(oldInfo.getProviderQuickOrderNo());

                            //如果上下架不是部分上下架，以SPU为准
                            if (!newGoods.getAddedFlag().equals(AddedFlag.PART.toValue())) {
                                infoOpt.get().setAddedFlag(newGoods.getAddedFlag());
                                infoOpt.get().setAddedTimingFlag(newGoods.getAddedTimingFlag());
                                infoOpt.get().setAddedTimingTime(newGoods.getAddedTimingTime());
                                //同步定时下架参数值
                                infoOpt.get().setTakedownTimeFlag(newGoods.getTakedownTimeFlag());
                                infoOpt.get().setTakedownTime(newGoods.getTakedownTime());
                            }

                            //更新上下架时间
                            if (changeStatus[1]) {
                                infoOpt.get().setAddedTime(newGoods.getAddedTime());
                            }

                            if (Objects.isNull(infoOpt.get().getStock())) {
                                infoOpt.get().setStock(0L);
                            }

                            //如果发生设价类型变化，原SKU的独立设价设为FALSE
                            if (changeStatus[2]) {
                                oldInfo.setAloneFlag(Boolean.FALSE);
                            }

                            // 查询是否存在sku维度的映射关系
                            if (CollectionUtils.isEmpty(saveRequest.getGoodsInfoMaps())){
                                //非独立设价SKU的叠加等级价、自定义客户都要以SPU为准
                                if (Objects.isNull(oldInfo.getAloneFlag())
                                        || Boolean.FALSE.equals(oldInfo.getAloneFlag())) {
                                    infoOpt.get().setCustomFlag(newGoods.getCustomFlag());
                                    infoOpt.get().setLevelDiscountFlag(newGoods.getLevelDiscountFlag());
                                }
                                //市场价刷新非独立设价的SKU
                                if (Objects.nonNull(newGoods.getMarketPrice()) && Boolean.FALSE.equals(oldInfo
                                        .getAloneFlag()) && !Objects.equals(EnableStatus.ENABLE,newGoods.getIsIndependent())) {
                                    infoOpt.get().setMarketPrice(newGoods.getMarketPrice());
                                }
                            }

                            //供应商价格备用变量
                            BigDecimal marketPriceForProvider = infoOpt.get().getMarketPrice();

                            //不允许独立设价 sku的叠加客户等级折扣  与spu同步
                            if (newGoods.getPriceType() == 1 && newGoods.getAllowPriceSet() == 0) {
                                infoOpt.get().setLevelDiscountFlag(newGoods.getLevelDiscountFlag());
                            }
                            infoOpt.get().setCostPrice(newGoods.getCostPrice());
                            infoOpt.get().setAuditStatus(oldGoods.getAuditStatus());
                            infoOpt.get().setBrandId(oldGoods.getBrandId());
                            infoOpt.get().setCateId(oldGoods.getCateId());
                            infoOpt.get().setCateTopId(oldGoods.getCateTopId());
                            infoOpt.get().setSaleType(oldGoods.getSaleType());
                            infoOpt.get().setPluginType(oldInfo.getPluginType());
                            if (StringUtils.isNotBlank(newGoods.getGoodsName())){
                                infoOpt.get().setGoodsInfoName(newGoods.getGoodsName());
                            }

                            //批发类型不支付购买积分
                            if (Integer.valueOf(SaleType.WHOLESALE.toValue()).equals(newGoods.getSaleType())) {
                                infoOpt.get().setBuyPoint(0L);
                            }

                            //更新微信视频号商品
                            wechatSkuService.updateGoods(oldInfo, infoOpt.get(), oldGoods, newGoods, finalGoods);

                            beforeSaveSku(infoOpt.get(), Boolean.TRUE);

                            KsBeanUtil.copyProperties(infoOpt.get(), oldInfo);

                            oldInfo.setSupplyPrice(infoOpt.get().getSupplyPrice());
                            oldInfo.setLinePrice(infoOpt.get().getLinePrice());
                            oldInfo.setQuickOrderNo(infoOpt.get().getQuickOrderNo());
                            //若是供应商的商品
                            if (StringUtils.isNotBlank(oldInfo.getProviderGoodsInfoId())) {
                                GoodsCommissionConfig goodsInfoCommissionConfig = goodsCommissionConfigService.queryBytStoreId(oldInfo.getStoreId());
                                //智能设价  如果打开独立设置加价比例开关则使用前端传值  如果没有打开则重新计算
                                if (CommissionSynPriceType.AI_SYN.toValue() == goodsInfoCommissionConfig.getSynPriceType().toValue()) {
                                    //关闭供应商价格独立编辑开关
                                    if (Objects.nonNull(saveRequest.getIsIndependent()) && saveRequest.getIsIndependent().toValue() == 0) {
                                        //重新获取加价比例
                                        GoodsCommissionPriceConfig goodsCommissionPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(oldInfo.getStoreId(), oldInfo);
                                        BigDecimal addRate = BigDecimal.ZERO;
                                        //重新计算加价值
                                        if (Objects.nonNull(goodsCommissionPriceConfig)) {
                                            addRate = goodsCommissionPriceConfig.getAddRate();
                                        } else {
                                            addRate = Objects.isNull(goodsInfoCommissionConfig.getAddRate()) ? BigDecimal.ZERO : goodsInfoCommissionConfig.getAddRate();
                                        }
                                        BigDecimal addPrice = goodsCommissionPriceService.getAddPrice(addRate, oldInfo.getSupplyPrice());
                                        oldInfo.setMarketPrice(oldInfo.getSupplyPrice().add(addPrice));
                                    } else {
                                        //直接取页面sku价格
                                        oldInfo.setMarketPrice(Objects.nonNull(marketPriceForProvider) ? marketPriceForProvider : BigDecimal.ZERO);
                                    }
                                } else {
                                    //手动设价如果是按用户设价并且SPU的市场价格有变更 则以spu的市场价为准  否则以前端转入的SKU市场价为准
                                    BigDecimal newSpuMarketPrice = Objects.isNull(oldGoods.getMarketPrice()) ? BigDecimal.ZERO : oldGoods.getMarketPrice();
                                    if (Objects.nonNull(newGoods.getPriceType()) && newGoods.getPriceType() == 0
                                            && oldSpuMarketPrice.compareTo(newSpuMarketPrice) != 0) {
                                        oldInfo.setMarketPrice(newSpuMarketPrice);
                                    } else {
                                        oldInfo.setMarketPrice(Objects.nonNull(marketPriceForProvider) ? marketPriceForProvider : BigDecimal.ZERO);
                                    }
                                }
                            }
                            //修改预估佣金
                            if (Objects.nonNull(oldInfo.getMarketPrice()) && Objects.nonNull(oldInfo.getCommissionRate())) {
                                oldInfo.setDistributionCommission(oldInfo.getMarketPrice().multiply(oldInfo
                                        .getCommissionRate()));
                            }
                            // 修改前后都存在的数据--加入需要被更新的sku中
                            oldGoodsInfos.add(oldInfo);
                        } else {
                            delInfoNoMap.put(oldInfo.getGoodsInfoNo(), oldInfo);
                            oldInfo.setDelFlag(DeleteFlag.YES);
                            // 修改后不存在的数据--加入需要被删除的sku中
                            delInfoIds.add(oldInfo.getGoodsInfoId());
                        }
                        //VOP需要特殊处理 避免所有SKU名字会
                        if (Objects.nonNull(oldInfo.getThirdPlatformType())
                                && ThirdPlatformType.VOP.toValue() == oldInfo.getThirdPlatformType().toValue()) {
                            oldInfo.setGoodsInfoName(StringUtils.isBlank(oldInfo.getGoodsInfoName()) ? newGoods.getGoodsName() : oldInfo.getGoodsInfoName());
                        } else {
                            oldInfo.setGoodsInfoName(newGoods.getGoodsName());
                        }
                        oldInfo.setUpdateTime(LocalDateTime.now());
                        // 查询是否存在sku维度的映射关系
                        List<Map<String, String>> goodsInfoMaps = saveRequest.getGoodsInfoMaps();
                        if (CollectionUtils.isNotEmpty(goodsInfoMaps)){
                            saveRequest.getGoodsInfos().forEach(goodsInfo -> {
                                if (StringUtils.isNotBlank(goodsInfo.getGoodsInfoId())  &&
                                        goodsInfo.getGoodsInfoId().equals(oldInfo.getGoodsInfoId())) {
                                    oldInfo.setAloneFlag(goodsInfo.getAloneFlag());
                                    oldInfo.setMarketPrice(goodsInfo.getMarketPrice());
                                    oldInfo.setCustomFlag(goodsInfo.getCustomFlag());
                                    oldInfo.setLevelDiscountFlag(goodsInfo.getLevelDiscountFlag());
                                    oldInfo.setQuickOrderNo(goodsInfo.getQuickOrderNo());
                                }
                            });
                        }
                        goodsInfoRepository.save(KsBeanUtil.copyPropertiesThird(oldInfo, GoodsInfo.class));

                        //覆盖redis中的库存，如需增量修改，则需要结合前端修改
                        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                            @Override
                            public void afterCommit() {
                                goodsInfoStockService.initCacheStock(oldInfo.getStock(), oldInfo.getGoodsInfoId());
                                handleWaringStock(KsBeanUtil.convert(oldInfo, GoodsInfo.class));
                            }
                        });

                    }
                }
                log.info("商品更新多规格结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
                // 只保存新SKU
                for (GoodsInfoSaveDTO sku : goodsInfos) {
                    sku.setCateTopId(newGoods.getCateTopId());
                    sku.setCateId(newGoods.getCateId());
                    sku.setBrandId(newGoods.getBrandId());
                    sku.setGoodsId(newGoods.getGoodsId());
                    sku.setGoodsInfoName(StringUtils.isBlank(sku.getGoodsInfoName()) ? newGoods.getGoodsName() : sku.getGoodsInfoName());
                    sku.setCreateTime(LocalDateTime.now());
                    sku.setUpdateTime(LocalDateTime.now());
                    sku.setDelFlag(DeleteFlag.NO);
                    sku.setCompanyInfoId(oldGoods.getCompanyInfoId());
                    sku.setPriceType(oldGoods.getPriceType());
                    sku.setStoreId(oldGoods.getStoreId());
                    sku.setAuditStatus(oldGoods.getAuditStatus());
                    sku.setCompanyType(oldGoods.getCompanyType());
                    //只处理新增的SKU
                    if (sku.getGoodsInfoId() != null) {
                        goodsInfoSpecDetailRelRepository.deleteByGoodsInfoIds(Arrays.asList(sku.getGoodsInfoId()), newGoods.getGoodsId());
                        editGoodsInfoDetailsRel(newGoods, specs, specDetails, sku, sku.getGoodsInfoId());
                        continue;
                    }
                    if (sku.getStock() == null) {
                        sku.setStock(0L);
                    }
                    sku.setCustomFlag(oldGoods.getCustomFlag());
                    sku.setLevelDiscountFlag(oldGoods.getLevelDiscountFlag());

                    //新商品会采用SPU市场价
                    if (newGoods.getMarketPrice() != null) {
                        sku.setMarketPrice(newGoods.getMarketPrice());
                    }
                    sku.setCostPrice(oldGoods.getCostPrice());

                    // 如果SPU选择部分上架，新增SKU的上下架状态为上架
                    sku.setAddedFlag(oldGoods.getAddedFlag().equals(AddedFlag.PART.toValue()) ? Integer.valueOf(AddedFlag.YES.toValue()) : newGoods.getAddedFlag());
                    sku.setAddedTime(oldGoods.getAddedTime());
                    sku.setAddedTimingFlag(oldGoods.getAddedTimingFlag());
                    sku.setAddedTimingTime(oldGoods.getAddedTimingTime());
                    sku.setTakedownTime(oldGoods.getTakedownTime());
                    sku.setTakedownTimeFlag(oldGoods.getTakedownTimeFlag());
                    sku.setAloneFlag(Boolean.FALSE);
                    sku.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                    sku.setSaleType(newGoods.getSaleType());
                    sku.setPluginType(oldGoods.getPluginType());
                    // 如果goodsType为空，重新赋值
                    if (Objects.isNull(sku.getGoodsType())) {
                        sku.setGoodsType(oldGoods.getGoodsType());
                    }
                    if(PluginType.CROSS_BORDER.toValue() == oldGoods.getPluginType().toValue()) {
                        sku.setPluginType(PluginType.NORMAL);
                    }

                    //批发类型不支付购买积分
                    if (Integer.valueOf(SaleType.WHOLESALE.toValue()).equals(newGoods.getSaleType())) {
                        sku.setBuyPoint(0L);
                    }
                    sku.setGoodsSource(oldGoods.getGoodsSource());
                    Nutils.isNullAction(sku.getIsBuyCycle(), Constants.no, sku::setIsBuyCycle);
                    if(Objects.nonNull(sku.getProviderId())){
                        sku.setProviderQuickOrderNo(sku.getQuickOrderNo());
                        sku.setQuickOrderNo(null);
                    }
                    sku = beforeSaveSku(sku, Boolean.TRUE);
                    String goodsInfoId = goodsInfoRepository.save(KsBeanUtil.copyPropertiesThird(sku, GoodsInfo.class)).getGoodsInfoId();
                    sku.setGoodsInfoId(goodsInfoId);

                    Long stock = sku.getStock();
                    //初始化redis缓存
                    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            goodsInfoStockService.initCacheStock(stock, goodsInfoId);
                        }
                    });

                    log.info("商品更新多规格开始->花费{}毫秒", (System.currentTimeMillis() - startTime));
                    //如果是多规格,新增SKU与规格明细值的关联表
                    editGoodsInfoDetailsRel(newGoods, specs, specDetails, sku, goodsInfoId);
                    // 修改后才存在(新出现)的数据--加入需要被添加的sku中
                    newGoodsInfo.add(sku);
                    newInfoNoMap.put(sku.getGoodsInfoNo(), sku);
                }
            }

            //SKU变化后刷新秒杀活动删除
            FlashSaleGoodsQueryRequest flashSaleGoodsQueryRequest = FlashSaleGoodsQueryRequest.builder().goodsId(saveRequest.getGoods().getGoodsId()).delFlag(DeleteFlag.NO).findAll(Boolean.TRUE).build();
            List<FlashSaleGoods> flashSaleGoodsList = flashSaleGoodsService.list(flashSaleGoodsQueryRequest);
            if (!flashSaleGoodsList.isEmpty()) {
                boolean res = saveRequest.getGoodsInfos().stream().noneMatch((now) ->
                        flashSaleGoodsList.stream().anyMatch(r -> r.getGoodsInfoId().equals(now.getGoodsInfoId())));
                if (res) {
//                秒杀活动，规格改变全删
                    List<Long> ids = flashSaleGoodsList.stream().map(FlashSaleGoods::getId).collect(Collectors.toList());
                    flashSaleGoodsService.deleteByIdList(ids);
                }
            }
            //为新增加的SKU补充设价数据

            if (CollectionUtils.isNotEmpty(newGoodsInfo)) {
                if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(oldGoods.getPriceType()) && CollectionUtils.isEmpty(saveRequest.getGoodsIntervalPrices())) {
                    saveRequest.setGoodsIntervalPrices(KsBeanUtil.convert(goodsIntervalPriceRepository.findByGoodsId(newGoods.getGoodsId()),GoodsIntervalPriceDTO.class));
                } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(oldGoods.getPriceType()) && CollectionUtils.isEmpty(saveRequest.getGoodsLevelPrices())) {
                    saveRequest.setGoodsLevelPrices(KsBeanUtil.convert(goodsLevelPriceRepository.findByGoodsId(newGoods.getGoodsId()),GoodsLevelPriceDTO.class));
                    //按客户单独定价
                    if (Constants.yes.equals(oldGoods.getCustomFlag()) && CollectionUtils.isEmpty(saveRequest.getGoodsCustomerPrices())) {
                        saveRequest.setGoodsCustomerPrices(KsBeanUtil.convert(goodsCustomerPriceRepository.findByGoodsId(newGoods.getGoodsId()),GoodsCustomerPriceDTO.class));
                    }
                }
                this.saveGoodsPrice(newGoodsInfo, oldGoods, saveRequest);
            }

            //如果spu是部分上架,当相关Sku都是上架时，自动设为上架
            if (AddedFlag.PART.toValue() == oldGoods.getAddedFlag()) {
                long addedCount = goodsInfos.stream().filter(g -> DeleteFlag.NO.equals(g.getDelFlag()) && AddedFlag.YES.toValue() == g.getAddedFlag()).count();
                long addedDownCount = goodsInfos.stream().filter(g -> DeleteFlag.NO.equals(g.getDelFlag()) && AddedFlag.NO.toValue() == g.getAddedFlag()).count();
                long count = goodsInfos.stream().filter(g -> DeleteFlag.NO.equals(g.getDelFlag())).count();
                if (addedCount > 0 && addedCount == count) {
                    oldGoods.setAddedFlag(AddedFlag.YES.toValue());
                    goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
                }
                if (addedDownCount > 0 && addedDownCount == count) {
                    oldGoods.setAddedFlag(AddedFlag.NO.toValue());
                    goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
                }
            }

            // 所有可用的SKU数据
            List<GoodsInfoSaveDTO> allSku = new ArrayList<>();
            allSku.addAll(oldGoodsInfos);
            allSku.addAll(newGoodsInfo);
            Long newStock = allSku.stream().filter(s -> Objects.nonNull(s.getStock())).mapToLong(GoodsInfoSaveDTO::getStock).sum();
            //库存发生变化，更改商家代销商品的库存
            if (!newStock.equals(oldGoods.getStock())) {
                changeStatus[0] = Boolean.TRUE;
            }
            oldGoods.setStock(newStock);
            oldGoods.setSkuMinMarketPrice(allSku.stream().map(GoodsInfoSaveDTO::getMarketPrice)
                    .filter(Objects::nonNull)
                    .reduce(BinaryOperator.minBy(BigDecimal::compareTo))
                    .orElseGet(oldGoods::getMarketPrice));

            //删除SKU相关的规格关联表  积分商城  和已经代销商品不可售
            if (!delInfoIds.isEmpty()) {
                goodsInfoSpecDetailRelRepository.deleteByGoodsInfoIds(delInfoIds, newGoods.getGoodsId());
                //批量删除积分商城中该sku，避免前台出现脏数据
                pointsGoodsRepository.deleteByGoodInfoIdList(delInfoIds);
                //删除视频号商品
                wechatSkuService.deleteGoods(delInfoIds, saveRequest.getUserId());
                //被删除的sku需被更新已经代销商品不可售  需要过滤同一个SKUNO新增的SKU
                if (!newInfoNoMap.isEmpty()) {
                    List<String> newSkuNo = new ArrayList<>(newInfoNoMap.keySet());
                    List<String> delSkuNo = new ArrayList<>(delInfoNoMap.keySet());
                    delSkuNo.retainAll(newSkuNo);
                    if (CollectionUtils.isNotEmpty(delSkuNo)) {
                        delSkuNo.forEach(skuNo -> {
                            delInfoIds.remove(delInfoNoMap.get(skuNo).getGoodsInfoId());
                        });
                    }
                }
                if (!delInfoIds.isEmpty()) {
                    ProviderGoodsNotSellRequest providerGoodsNotSellRequest = ProviderGoodsNotSellRequest.builder()
                            .checkFlag(Boolean.FALSE).goodsInfoIds(delInfoIds).build();
                    this.dealGoodsVendibility(providerGoodsNotSellRequest);
                }
                clearDelGoodsInfo(delInfoIds);
            }

            oldGoods = beforeSaveSpu(oldGoods, Boolean.TRUE);
            goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
            //更新周期购的商品名称
            buyCycleGoodsRepository.modifyGoodsNameByGoodsId(oldGoods.getGoodsId(),oldGoods.getGoodsName());

            log.info("商品更新结束->花费{}毫秒", (System.currentTimeMillis() - startTime));

            //*****  异步处理数据变更
            providerGoodsEditDetailService.synGoodsUpdate(oldGoods, oldGoodsInfos, newInfoNoMap, delInfoNoMap, editMap, updateSupplyPrice);

            if (GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == oldGoods.getGoodsType()) {
                List<Long> newElectronicIds = goodsInfos.stream().map(GoodsInfoSaveDTO::getElectronicCouponsId).collect(Collectors.toList());
                electronicCouponProvider.updateBindingFlag(ElectronicCouponUpdateBindRequest.builder()
                        .unBindingIds(oldElectronicIds).bindingIds(newElectronicIds).build());
            }

            //发送异步消息
            delete(delInfoIds, KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
            update(KsBeanUtil.convert(oldGoodsInfos, GoodsInfo.class), GoodsEditFlag.INFO, KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));

            return GoodsModifyInfoResponse.builder()
                    .newGoodsInfo(KsBeanUtil.convert(newGoodsInfo, GoodsInfoVO.class))
                    .delInfoIds(delInfoIds)
                    .oldGoodsInfos(KsBeanUtil.convert(oldGoodsInfos, GoodsInfoVO.class))
                    .oldGoods(KsBeanUtil.copyPropertiesThird(oldGoods, GoodsVO.class))
                    .isDealGoodsVendibility(changeStatus[0])
                    .build();
        }
    }

    /**
     * 商品更新
     * @param goodsInfoList
     * @param flag
     * @param goods
     */
    public void update(List<GoodsInfo> goodsInfoList, GoodsEditFlag flag, Goods goods) {
        //异步通知处理
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return;
        }
        Boolean isProvider = GoodsSource.PROVIDER.toValue() == goods.getGoodsSource()
                || GoodsSource.VOP.toValue() == goods.getGoodsSource()
                || GoodsSource.LINKED_MALL.toValue() == goods.getGoodsSource();

        if (Objects.isNull(flag)) {
            if (AddedFlag.PART.toValue() == goods.getAddedFlag()) {
                return;
            }
            flag = AddedFlag.NO.toValue() == goods.getAddedFlag() ? GoodsEditFlag.DOWN : GoodsEditFlag.UP;
        }

        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        sendRequest.setGoodsInfoIds(goodsInfoList.stream().map(GoodsInfo :: getGoodsInfoId).collect(Collectors.toList()));
        sendRequest.setFlag(flag);
        sendRequest.setIsProvider(isProvider);
        sendRequest.setStoreId(goods.getStoreId());
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
        return;
    }

    /**
     * 商品删除
     * @param goodsInfoIds
     * @param goods
     */
    public void delete(List<String> goodsInfoIds, Goods goods) {
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            return;
        }
        //异步通知处理
        Boolean isProvider = GoodsSource.PROVIDER.toValue() == goods.getGoodsSource()
                || GoodsSource.VOP.toValue() == goods.getGoodsSource()
                || GoodsSource.LINKED_MALL.toValue() == goods.getGoodsSource();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        sendRequest.setGoodsInfoIds(goodsInfoIds);
        sendRequest.setFlag(GoodsEditFlag.DELETE);
        sendRequest.setIsProvider(isProvider);
        sendRequest.setStoreId(goods.getStoreId());
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
        return;
    }
    /**
     * 返回是否需要二次审核
     */
    protected Boolean getNeedSecondCheck() {
        return Boolean.TRUE;
    }

    /***
     * 清理需要删除的商品SKU
     * @param delInfoIds
     */
    protected void clearDelGoodsInfo(List<String> delInfoIds) {

    }

    /**
     * 修改sku规格关联表
     *
     * @param newGoods
     * @param specs
     * @param specDetails
     * @param sku
     * @param goodsInfoId2
     */
    private void editGoodsInfoDetailsRel(GoodsSaveDTO newGoods, List<GoodsSpecSaveDTO> specs, List<GoodsSpecDetailSaveDTO> specDetails, GoodsInfoSaveDTO sku, String goodsInfoId2) {
        if (Constants.yes.equals(newGoods.getMoreSpecFlag())) {
            if (CollectionUtils.isNotEmpty(specs)) {
                for (GoodsSpecSaveDTO spec : specs) {
                    if (sku.getMockSpecIds().contains(spec.getMockSpecId())) {
                        for (GoodsSpecDetailSaveDTO detail : specDetails) {
                            if (spec.getMockSpecId().equals(detail.getMockSpecId()) && sku.getMockSpecDetailIds().contains(detail.getMockSpecDetailId())) {
                                GoodsInfoSpecDetailRel detailRel = new GoodsInfoSpecDetailRel();
                                detailRel.setGoodsId(newGoods.getGoodsId());
                                detailRel.setGoodsInfoId(goodsInfoId2);
                                detailRel.setSpecId(spec.getSpecId());
                                detailRel.setSpecDetailId(detail.getSpecDetailId());
                                detailRel.setDetailName(detail.getDetailName());
                                detailRel.setCreateTime(LocalDateTime.now());
                                detailRel.setUpdateTime(LocalDateTime.now());
                                detailRel.setDelFlag(DeleteFlag.NO);
                                goodsInfoSpecDetailRelRepository.save(detailRel);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 校验当前商品是否在待审核
     * // TODO 将该方法移到SBC子类中
     * @param saveRequest
     */
    protected void checkAudit(GoodsSaveRequest saveRequest) {
        if (saveRequest.getIsChecked()) {
            return;
        }
        List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIdsAndAuditState(Collections.singletonList(saveRequest.getGoods().getGoodsId()), CheckStatus.WAIT_CHECK);
        if (CollectionUtils.isNotEmpty(goodsAuditList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030148);
        }
    }

    /**
     * 对比商品是否经过修改
     *
     * @param oldGoods
     * @param newGoods
     * @param saveRequest
     * @return
     */
    @Override
    public boolean checkGoodsIsAudit(GoodsSaveDTO oldGoods, GoodsSaveDTO newGoods, GoodsSaveRequest saveRequest,
                                     GoodsCommissionConfig goodsCommissionConfig,boolean flag) {
        GoodsSaveDTO checkGoods1 = getCheckGoods(oldGoods,goodsCommissionConfig);
        GoodsSaveDTO checkGoods2 = getCheckGoods(newGoods,goodsCommissionConfig);
        String[] strings = new String[6];
        strings[0] = "goodsId";
        strings[1] = "singleSpecFlag";
        strings[3] = "stock";
        //代销供应商商品无需验证
        if(StringUtils.isNotBlank(checkGoods1.getProviderGoodsId())) {
            strings[2] = "delFlag";
            strings[3] = "stock";
            strings[4] = "moreSpecFlag";
        }
        //京东VOP
        if(Objects.equals(ThirdPlatformType.VOP, checkGoods2.getThirdPlatformType())) {
            strings[5] = "goodsDetail";
        }
        log.info("老商品参数" + JSON.toJSONString(checkGoods1));
        log.info("新商品参数" + JSON.toJSONString(checkGoods2));
        if (!CompareUtil.compareObject(checkGoods1, checkGoods2,strings)) {
            return false;
        }

        List<StoreCateGoodsRela> storeCateGoodsRelaList = storeCateGoodsRelaRepository.selectByGoodsId(Collections.singletonList(oldGoods.getGoodsId()));
        List<Long> oldStoreCateIds = storeCateGoodsRelaList.stream().map(StoreCateGoodsRela::getStoreCateId).collect(Collectors.toList());
        if (oldStoreCateIds.size() != newGoods.getStoreCateIds().size()) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(newGoods.getStoreCateIds().stream()
                .filter(v -> !oldStoreCateIds.contains(v))
                .collect(Collectors.toList()))) {
            return false;
        }

        //比较图片
        List<GoodsImageDTO> newGoodsImages = CollectionUtils.isEmpty(saveRequest.getImages()) ? new ArrayList<>() : saveRequest.getImages();
        List<GoodsImage> oldGoodsImages = goodsImageRepository.findByGoodsId(oldGoods.getGoodsId());
        if (oldGoodsImages.size() != newGoodsImages.size()) {
            return false;
        }
        List<String> artworkUrlList = oldGoodsImages.stream().map(GoodsImage::getArtworkUrl).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(newGoodsImages.stream()
                .filter(v -> !artworkUrlList.contains(v.getArtworkUrl()))
                .collect(Collectors.toList()))) {
            return false;
        }

        //比较商品属性
        List<GoodsPropertyDetailRel> oldPropertyDetailRelList =
                goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(oldGoods.getGoodsId(), DeleteFlag.NO, GoodsPropertyType.GOODS);
        List<GoodsPropertyDetailRelSaveDTO> newPropertyDetailRelList = CollectionUtils
                .isEmpty(saveRequest.getGoodsPropertyDetailRel()) ? new ArrayList<>() : saveRequest.getGoodsPropertyDetailRel();
        if (oldPropertyDetailRelList.size() != newPropertyDetailRelList.size()) {
            return false;
        }
        List<Long> propIds = oldPropertyDetailRelList.stream().map(GoodsPropertyDetailRel::getPropId).collect(Collectors.toList());
        List<GoodsPropertyDetailRelSaveDTO> detailRelList = newPropertyDetailRelList
                .stream()
                .filter(v -> !propIds.contains(v.getPropId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(detailRelList)) {
            return false;
        }


        if (checkGoods2.getMoreSpecFlag() == Constants.ONE) {
            //比较商品规格列表
            List<GoodsSpecSaveDTO> goodsSpecList = saveRequest.getGoodsSpecs()
                    .stream()
                    .filter(v -> Objects.isNull(v.getSpecId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsSpecList)) {
                //specId为空为新增spec
                return false;
            }
            List<String> oldSpecNameList = goodsSpecRepository.findByGoodsId(oldGoods.getGoodsId())
                    .stream()
                    .map(GoodsSpec::getSpecName)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(saveRequest.getGoodsSpecs()
                    .stream()
                    .filter(v -> !oldSpecNameList.contains(v.getSpecName()))
                    .collect(Collectors.toList()))) {
                return false;
            }

            //比较商品规格值列表
            List<GoodsSpecDetailSaveDTO> specDetailList = saveRequest.getGoodsSpecDetails()
                    .stream()
                    .filter(v -> Objects.isNull(v.getSpecDetailId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(specDetailList)) {
                return false;
            }
            List<String> specDetailNameList = goodsSpecDetailRepository.findByGoodsId(oldGoods.getGoodsId())
                    .stream()
                    .map(GoodsSpecDetail::getDetailName)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(saveRequest.getGoodsSpecDetails()
                    .stream()
                    .filter(v -> !specDetailNameList.contains(v.getDetailName()))
                    .collect(Collectors.toList()))) {
                return false;
            }
        }

        //比较商品SKU列表
        if (flag){
            List<GoodsInfo> oldInfoList = goodsInfoRepository.findByGoodsIds(Collections.singletonList(oldGoods.getGoodsId()));
            List<GoodsInfoSaveDTO> newInfoList = saveRequest.getGoodsInfos();

            List<GoodsInfoSaveDTO> goodsInfos = newInfoList.stream().filter(v -> Objects.isNull(v.getGoodsInfoId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                return false;
            }
            //获取积分设置
            boolean isGoodsPoint = systemPointsConfigService.isGoodsPoint();
            List<String> infoId = oldInfoList.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
            //代销商品

            for (String s : infoId) {
                GoodsInfoSaveDTO newGoodsInfo = newInfoList.stream().filter(v -> Objects.equals(v.getGoodsInfoId(), s)).findFirst().orElse(null);
                GoodsInfo oldGoodsInfo = oldInfoList.stream().filter(v -> Objects.equals(v.getGoodsInfoId(), s)).findFirst().orElse(null);
                if (Objects.isNull(newGoodsInfo) || Objects.isNull(oldGoodsInfo)) {
                    return false;
                }
                if (!Objects.equals(newGoodsInfo.getGoodsInfoNo(), oldGoodsInfo.getGoodsInfoNo())) {
                    return false;
                }

                if (!(newGoodsInfo.getGoodsCubage().compareTo(oldGoodsInfo.getGoodsCubage()) == 0)) {
                    return false;
                }
                if (!(newGoodsInfo.getGoodsWeight().compareTo(oldGoodsInfo.getGoodsWeight()) == 0)) {
                    return false;
                }
                if (!Objects.equals(newGoodsInfo.getGoodsInfoBarcode(), oldGoodsInfo.getGoodsInfoBarcode())) {
                    return false;
                }
                if (!Objects.equals(newGoodsInfo.getGoodsInfoImg(), oldGoodsInfo.getGoodsInfoImg())) {
                    return false;
                }
                //验证积分设置
                if (isGoodsPoint && !Objects.equals(
                        Objects.nonNull(newGoodsInfo.getBuyPoint())?newGoodsInfo.getBuyPoint():0,
                        Objects.nonNull(oldGoodsInfo.getBuyPoint())?oldGoodsInfo.getBuyPoint():0)
                ) {
                    return false;
                }
                //智能设价  如果打开独立设置加价比例开关则使用前端传值  如果没有打开则重新计算
                if (Objects.nonNull(newGoodsInfo.getMarketPrice())) {
                    if (!(newGoodsInfo.getMarketPrice().compareTo(oldGoodsInfo.getMarketPrice()) == 0)) {
                        if (Objects.isNull(newGoods.getProviderGoodsId())){
                            return false;
                        }
                        if (Objects.isNull(goodsCommissionConfig)){
                            return false;
                        }
                        if (!Objects.equals(CommissionSynPriceType.AI_SYN,goodsCommissionConfig.getSynPriceType())){
                            return false;
                        }
                    }
                }
                //自营商品和平台商品不比较供货价是否改变
                if (oldGoodsInfo.getGoodsSource() != GoodsSource.SELLER.toValue() && oldGoodsInfo.getGoodsSource() != GoodsSource.PLATFORM.toValue() ){
                    if (Objects.nonNull(newGoodsInfo.getSupplyPrice())) {
                        if (!(newGoodsInfo.getSupplyPrice().compareTo(oldGoodsInfo.getSupplyPrice()) == 0)) {
                            return false;
                        }
                    }
                }
            }
        }

        //商品等级价格列表
        if (Objects.nonNull(saveRequest.getGoodsLevelPrices())) {
            List<GoodsLevelPrice> levelPriceList = goodsLevelPriceRepository.findByGoodsId(oldGoods.getGoodsId());
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
                for (GoodsLevelPrice levelPrice : levelPriceList) {
                    GoodsLevelPriceDTO goodsLevelPrice = goodsLevelPriceList1
                            .stream()
                            .filter(v -> Objects.equals(v.getLevelId(), levelPrice.getLevelId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(goodsLevelPrice)){
                        return false;
                    }
                    if (Objects.isNull(goodsLevelPrice.getPrice()) || goodsLevelPrice.getPrice().compareTo(levelPrice.getPrice()) != 0){
                        return false;
                    }
                }
            }
        }
        //比较商品客户价格列表
        if (Objects.nonNull(saveRequest.getGoodsCustomerPrices())) {
            List<GoodsCustomerPrice> customerPriceList = goodsCustomerPriceRepository.findByGoodsId(oldGoods.getGoodsId());
            if (saveRequest.getGoodsCustomerPrices().size() != customerPriceList.size()) {
                return false;
            }
            List<GoodsCustomerPriceDTO> customerPrices = saveRequest.getGoodsCustomerPrices();
            List<GoodsCustomerPriceDTO> goodsCustomerPriceList = customerPrices
                    .stream()
                    .filter(v -> Objects.isNull(v.getCustomerPriceId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsCustomerPriceList)) {
                return false;
            }
            List<GoodsCustomerPriceDTO> goodsCustomerPriceList1 = customerPrices
                    .stream()
                    .filter(v -> Objects.nonNull(v.getCustomerPriceId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsCustomerPriceList1)) {
                for (GoodsCustomerPrice customerPrice : customerPriceList) {
                    GoodsCustomerPriceDTO goodsCustomerPrice = goodsCustomerPriceList1
                            .stream()
                            .filter(v -> Objects.equals(v.getCustomerId(), customerPrice.getCustomerId()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(goodsCustomerPrice)){
                        return false;
                    }
                    if (goodsCustomerPrice.getPrice().compareTo(customerPrice.getPrice()) != 0){
                        return false;
                    }
                }
            }
        }
        //比较商品订货区间价格列表
        if (Objects.nonNull(saveRequest.getGoodsIntervalPrices())) {
            List<GoodsIntervalPrice> intervalPriceList = goodsIntervalPriceRepository.findByGoodsId(oldGoods.getGoodsId());
            List<GoodsIntervalPriceDTO> intervalPrices = saveRequest.getGoodsIntervalPrices();
            List<GoodsIntervalPriceDTO> savePriceList = intervalPrices.stream().filter(p ->
                    Objects.nonNull(p.getPrice())).collect(Collectors.toList());
            if (savePriceList.size() != intervalPriceList.size()) {
                return false;
            }
            List<GoodsIntervalPriceDTO> savePriceList1 = intervalPrices.stream().filter(p ->
                    Objects.nonNull(p.getPrice())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(savePriceList1)) {
                for (GoodsIntervalPrice intervalPrice : intervalPriceList) {
                    GoodsIntervalPriceDTO goodsIntervalPrice = savePriceList1
                            .stream()
                            .filter(v -> Objects.equals(v.getCount(), intervalPrice.getCount()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(goodsIntervalPrice)){
                        return false;
                    }
                    if (intervalPrice.getPrice().compareTo(goodsIntervalPrice.getPrice()) != 0){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private GoodsSaveDTO getCheckGoods(GoodsSaveDTO goods,GoodsCommissionConfig goodsCommissionConfig) {
        GoodsSaveDTO checkGoods = KsBeanUtil.convert(goods, GoodsSaveDTO.class);
        checkGoods.setAddedFlag(null);
        checkGoods.setAddedTimingFlag(null);
        checkGoods.setAddFalseReason(null);
        checkGoods.setAddedTime(null);
        checkGoods.setAddedTimingTime(null);
        checkGoods.setTakedownTime(null);
        checkGoods.setTakedownTimeFlag(null);
        checkGoods.setGoodsBuyTypes(null);
        checkGoods.setIsIndependent(null);
        checkGoods.setAuditStatus(null);
        checkGoods.setUpdateTime(null);
        checkGoods.setFreightTempName(null);
        checkGoods.setStoreCateIds(null);
        checkGoods.setAllSalesNum(null);
        checkGoods.setCateTopId(null);
        checkGoods.setSkuMinMarketPrice(null);
        checkGoods.setGoodsType(null);
        checkGoods.setPluginType(null);
        checkGoods.setFreightTempId(null);

        //如果不是按客户设价，则不校验SPU统一市场价字段
        if(Objects.nonNull(checkGoods.getPriceType()) && checkGoods.getPriceType() != 0){
            checkGoods.setMarketPrice(null);
        }

        if (Objects.nonNull(checkGoods.getProviderGoodsId())
        && Objects.nonNull(goodsCommissionConfig)
        && Objects.equals(CommissionSynPriceType.AI_SYN,goodsCommissionConfig.getSynPriceType())){
            checkGoods.setMarketPrice(null);
        }

        if (StringUtils.isBlank(checkGoods.getLabelIdStr())) {
            checkGoods.setLabelIdStr(null);
        }
        if (Objects.nonNull(checkGoods.getSupplyPrice())) {
            checkGoods.setSupplyPrice(checkGoods.getSupplyPrice().setScale(2, RoundingMode.HALF_UP));
        }
        checkGoods.setVendibility(null);
        if (StringUtils.isNotBlank(checkGoods.getGoodsDetail())) {
            checkGoods.setGoodsDetail(checkGoods.getGoodsDetail().replaceAll(" ", "").replaceAll("&gt;", ">").replaceAll("&lt;", "<"));
        }
        checkGoods.setFeedbackRate(null);
        return checkGoods;
    }

    private void checkGoodsId(GoodsSaveRequest saveRequest, GoodsSaveDTO goods) {

        saveRequest.setGoods(goods);
        if (CollectionUtils.isNotEmpty(saveRequest.getImages())) {
            saveRequest.getImages().forEach(v -> v.setGoodsId(goods.getGoodsId()));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsPropDetailRels())) {
            saveRequest.getGoodsPropDetailRels().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsPropDetailRels().forEach(v -> v.setRelId(null));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsSpecs())) {
            saveRequest.getGoodsSpecs().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsSpecs().forEach(v -> v.setOldSpecId(v.getSpecId()));
            saveRequest.getGoodsSpecs().forEach(v -> v.setSpecId(null));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsSpecDetails())) {
            saveRequest.getGoodsSpecDetails().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsSpecDetails().forEach(v -> v.setOldSpecDetailId(v.getSpecDetailId()));
            saveRequest.getGoodsSpecDetails().forEach(v -> v.setSpecDetailId(null));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsInfos())) {
            saveRequest.getGoodsInfos().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsInfos().forEach(v -> v.setOldGoodsInfoId(v.getGoodsInfoId()));
            saveRequest.getGoodsInfos().forEach(v -> v.setGoodsInfoId(null));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsLevelPrices())) {
            saveRequest.getGoodsLevelPrices().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsLevelPrices().forEach(v -> v.setLevelPriceId(null));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsIntervalPrices())) {
            saveRequest.getGoodsIntervalPrices().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsIntervalPrices().forEach(v -> v.setIntervalPriceId(null));
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsTabRelas())) {
            saveRequest.getGoodsTabRelas().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsTabRelas().forEach(v -> v.setId(null));

        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsPropertyDetailRel())) {
            saveRequest.getGoodsPropertyDetailRel().forEach(v -> v.setGoodsId(goods.getGoodsId()));
            saveRequest.getGoodsPropertyDetailRel().forEach(v -> v.setDetailRelId(null));
        }
    }

    /**
     * 供应商关联商品是否可售
     * 1、 禁售、删除、SPU的上下架都需要同时更改SPU和SKU
     * 2、编辑SKU时，SPU的上下架场景：
     * 下架 -> 上架    修改SPU和所有SKU
     * 下架 -> 部分上架（上架部分商品）  修改当前SKU和SPU
     * 上架 -> 下架    修改所有SKU和SPU
     * 上架 -> 部分上架（下架部分商品）  只修改SKU
     * 部分上架 -> 上架  修改当前SKU
     * 部分上架 -> 下架  修改当前SKU和SPU
     * 部分上架 -> 部分上架 修改当前SKU
     */
    @Transactional
    public void dealGoodsVendibility(ProviderGoodsNotSellRequest request) {

        List<String> goodsInfoIds = new ArrayList<>();
        // 处理spu
        if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
            List<Goods> goods = goodsRepository.findAllByGoodsIdIn(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(goods)) {
                // 上架、禁售后重新编辑的时候要综合情况看
                if (Boolean.TRUE.equals(request.getCheckFlag())) {
                    goods.forEach(g -> {
                        Integer goodsVendibility = Constants.no;
                        // 未下架、未删除、已审核视为商品可售（商品维度）
                        if ((AddedFlag.NO.toValue() != g.getAddedFlag())
                                && (DeleteFlag.NO == g.getDelFlag())
                                && (CheckStatus.CHECKED == g.getAuditStatus())) {
                            goodsVendibility = Constants.yes;
                        }
                        goodsRepository.updateGoodsVendibility(goodsVendibility, Lists.newArrayList(g.getGoodsId()));
                    });
                } else {
                    goodsRepository.updateGoodsVendibility(Constants.no, request.getGoodsIds());
                }

                //同步库存
                if (Boolean.TRUE.equals(request.getStockFlag())) {
                    goods.forEach(g -> goodsRepository.updateStockByProviderGoodsIds(g.getStock(), Lists.newArrayList(g.getGoodsId())));
                }
            }

            List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsIdIn(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(goodsInfos) && CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
                goodsInfoIds = goodsInfos.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
            }
        }

        //传入goodsInfoIds时，说明指定修改sku
        if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
            goodsInfoIds = request.getGoodsInfoIds();
        }

        // 处理sku
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfos)) {
            if (Boolean.TRUE.equals(request.getCheckFlag())) {
                goodsInfos.forEach(g -> {
                    Integer goodsVendibility = Constants.no;
                    // 上架、未删除、已审核视为商品可售（商品维度）
                    if ((AddedFlag.YES.toValue() == g.getAddedFlag())
                            && (DeleteFlag.NO == g.getDelFlag())
                            && (CheckStatus.CHECKED == g.getAuditStatus())) {
                        goodsVendibility = Constants.yes;
                    }
                    goodsInfoRepository.updateGoodsInfoVendibility(goodsVendibility, Lists.newArrayList(g.getGoodsInfoId()));
                });
            } else {
                goodsInfoRepository.updateGoodsInfoVendibility(Constants.no, goodsInfoIds);
            }
        }

    }

    /***
     * 校验商品数据是否重复
     * 新增、修改时调用
     * @param goods           SPU对象
     * @param newGoods        新商品对象，修改时不为空
     * @param goodsInfos      SKU列表
     */
    protected void checkGoodsInfoRepeat(GoodsSaveDTO goods, GoodsSaveDTO newGoods, List<GoodsInfoSaveDTO> goodsInfos) {
        //spu编码不存在，则抛出异常
        if (StringUtils.isEmpty(goods.getGoodsNo())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030049);
        }
        //找出是否存在没有skuNO的数据
        Optional<GoodsInfoSaveDTO> optional = goodsInfos.parallelStream().filter(goodsInfo -> StringUtils.isEmpty(goodsInfo.getGoodsInfoNo())).findFirst();
        //如果有，则抛出异常
        if (optional.isPresent()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030050);
        }
        // 验证SPU编码重复,需判断goods表和goods_audit表
        GoodsQueryRequest queryRequest = new GoodsQueryRequest();
        GoodsAuditQueryRequest auditQueryRequest = new GoodsAuditQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        auditQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        if (Objects.nonNull(newGoods)) {
            queryRequest.setGoodsNo(newGoods.getGoodsNo());
            queryRequest.setNotGoodsId(newGoods.getGoodsId());
            auditQueryRequest.setGoodsNo(newGoods.getGoodsNo());
            auditQueryRequest.setNotOldGoodsId(newGoods.getGoodsId());
        } else {
            queryRequest.setGoodsNo(goods.getGoodsNo());
            auditQueryRequest.setGoodsNo(goods.getGoodsNo());
        }
        // 断言为0
        Assert.assertIsZero(goodsRepository.count(queryRequest.getWhereCriteria()), GoodsErrorCodeEnum.K030036);
        Assert.assertIsZero(goodsAuditRepository.count(GoodsAuditWhereCriteriaBuilder.build(auditQueryRequest)), GoodsErrorCodeEnum.K030036);

        // 验证SKU数据有重复
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        if (Objects.nonNull(newGoods)) {
            infoQueryRequest.setNotGoodsId(newGoods.getGoodsId());
            infoQueryRequest.setGoodsInfoNos(goodsInfos.stream()
                    .map(GoodsInfoSaveDTO::getGoodsInfoNo)
                    .distinct().collect(Collectors
                            .toList()));

        } else {
            infoQueryRequest.setGoodsInfoNos(goodsInfos.stream()
                    .map(GoodsInfoSaveDTO::getGoodsInfoNo)
                    .distinct()
                    .collect(Collectors.toList()));
        }
        // 如果SKU数据有重复
        if (goodsInfos.size() != infoQueryRequest.getGoodsInfoNos().size()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030037);
        }

        // 验证SKU编码重复
        List<GoodsInfo> goodsInfosList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        if (CollectionUtils.isNotEmpty(goodsInfosList)) {

            List<String> goodsIds = goodsInfosList.stream().map(GoodsInfo::getGoodsId).distinct().collect(Collectors.toList());
            List<GoodsAudit> goodsAuditList = goodsAuditRepository.findAllById(goodsIds);
            List<String> wrongGoodsInfoNos = new ArrayList<>();
            if (goodsAuditList.size() != goodsIds.size()){
                goodsIds.removeAll(goodsAuditList.stream().map(GoodsAudit::getGoodsId).collect(Collectors.toList()));
                for (String goodsId : goodsIds) {
                    wrongGoodsInfoNos.addAll(goodsInfosList.stream()
                            .filter(v -> Objects.equals(v.getGoodsId(), goodsId))
                            .map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList()));
                }
            }
            if(Objects.nonNull(newGoods)){
                goodsAuditList = goodsAuditList.stream()
                        .filter(v -> !Objects.equals(v.getOldGoodsId(), newGoods.getGoodsId())).collect(Collectors.toList());
            }
            List<String> goodsAuditIds = goodsAuditList.stream()
                    .map(GoodsAudit::getGoodsId).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(goodsAuditIds)) {
                for (String goodsAuditId : goodsAuditIds) {
                    wrongGoodsInfoNos.addAll(goodsInfosList.stream()
                            .filter(v -> Objects.equals(v.getGoodsId(), goodsAuditId))
                            .map(GoodsInfo::getGoodsInfoNo).collect(Collectors.toList()));
                }
            }
            if (CollectionUtils.isNotEmpty(wrongGoodsInfoNos)){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030038,
                        new Object[]{StringUtils
                                .join(wrongGoodsInfoNos, ",")});
            }

        }
        //校验订货号重复
        if(Objects.isNull(newGoods)) {
            this.checkQuickOrderNo(goods,  goodsInfos);
        } else {
            this.checkQuickOrderNo(newGoods,  goodsInfos);
        }
    }

    /**
     * 校验订货号
     * @param newGoods
     * @param goodsInfos
     */
    public void checkQuickOrderNo(GoodsSaveDTO newGoods, List<GoodsInfoSaveDTO> goodsInfos){
        // 实物商品需要验证订货单号
        if(Objects.nonNull(newGoods.getGoodsType()) && newGoods.getGoodsType() == GoodsType.REAL_GOODS.toValue()){
            List<String> quickOrderNos = goodsInfos.stream()
                    .filter(g -> StringUtils.isNotBlank(g.getQuickOrderNo()))
                    .map(GoodsInfoSaveDTO::getQuickOrderNo)
                    .distinct()
                    .collect(Collectors.toList());
            if(CollectionUtils.isEmpty(quickOrderNos)){
                return;
            }
            // 验证SKU订货号是否有重复
            GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            infoQueryRequest.setQuickOrderNos(quickOrderNos);
            if (Objects.nonNull(newGoods)) {
                infoQueryRequest.setNotGoodsId(newGoods.getGoodsId());
            }
            // 如果SKU数据订货号有重复
            List<GoodsInfoSaveDTO> newGoodsInfos = goodsInfos.stream().filter(g -> StringUtils.isNotBlank(g.getQuickOrderNo())).collect(Collectors.toList());
            if (newGoodsInfos.size() != infoQueryRequest.getQuickOrderNos().size()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030192);
            }

            // 验证SKU订货号重复
            List<GoodsInfo> goodsInfosList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(goodsInfosList)) {

                List<String> goodsIds = goodsInfosList.stream().map(GoodsInfo::getGoodsId).distinct().collect(Collectors.toList());
                List<GoodsAudit> goodsAuditList = goodsAuditRepository.findAllById(goodsIds);
                List<String> wrongQuickOrderNos = new ArrayList<>();
                if (goodsAuditList.size() != goodsIds.size()){
                    goodsIds.removeAll(goodsAuditList.stream().map(GoodsAudit::getGoodsId).collect(Collectors.toList()));
                    for (String goodsId : goodsIds) {
                        wrongQuickOrderNos.addAll(goodsInfosList.stream()
                                .filter(v -> Objects.equals(v.getGoodsId(), goodsId))
                                .map(GoodsInfo::getQuickOrderNo).collect(Collectors.toList()));
                    }
                }
                if(Objects.nonNull(newGoods)){
                    goodsAuditList = goodsAuditList.stream()
                            .filter(v -> !Objects.equals(v.getOldGoodsId(), newGoods.getGoodsId())).collect(Collectors.toList());
                }
                List<String> goodsAuditIds = goodsAuditList.stream()
                        .map(GoodsAudit::getGoodsId).collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(goodsAuditIds)) {
                    for (String goodsAuditId : goodsAuditIds) {
                        wrongQuickOrderNos.addAll(goodsInfosList.stream()
                                .filter(v -> Objects.equals(v.getGoodsId(), goodsAuditId))
                                .map(GoodsInfo::getQuickOrderNo).collect(Collectors.toList()));
                    }
                }
                if (CollectionUtils.isNotEmpty(wrongQuickOrderNos)){
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030193,
                            new Object[]{StringUtils
                                    .join(wrongQuickOrderNos, ",")});
                }

            }
        }
    }

    /**
     * 1.校验商品分类、商品品牌、是否存在
     * 2.如果是S2B，校验签约分类、签约品牌、店铺分类是否正确
     *
     * @param goods 商品信息
     */
    @Override
    public void checkBasic(GoodsSaveDTO goods,int type) {
        // TODO VOP迁移
        GoodsCate cate = this.goodsCateRepository.findById(goods.getCateId()).orElse(null);
        if (Objects.isNull(cate) || Objects.equals(DeleteFlag.YES, cate.getDelFlag())) {
            // VOP商品-1代表没有映射
            if (!Objects.equals(ThirdPlatformType.VOP, goods.getThirdPlatformType())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030057);
            } else {
                goods.setCateId(-1L);
            }
        }
        if (!Objects.isNull(cate) && !Objects.isNull(cate.getCatePath())) {
            if (cate.getCatePath().split("\\|").length >=2) {
                goods.setCateTopId(Long.valueOf(cate.getCatePath().split("\\|")[1]));
            } else {
                goods.setCateId(-1L);
            }
        }
        if(type==0 && !Objects.equals(ThirdPlatformType.VOP, goods.getThirdPlatformType()) && cate.getContainsVirtual().equals(BoolFlag.NO) && goods.getGoodsType()!=0){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"该分类不可以包含虚拟商品！");
        }

        if (Objects.nonNull(goods.getBrandId())) {
            GoodsBrand brand = this.goodsBrandService.findById(goods.getBrandId());
            if (Objects.isNull(brand) || Objects.equals(DeleteFlag.YES, brand.getDelFlag())) {
                throw new SbcRuntimeException(this.goodsBrandService.getDeleteIndex(goods.getBrandId()),
                        GoodsErrorCodeEnum.K030058);
            }
        }
        // 判断是否京东VOP商品，如果是直接跳过校验
        if (Integer.valueOf(GoodsSource.VOP.toValue()).equals(goods.getGoodsSource())) {
            return;
        }

        if (osUtil.isS2b()) {
            // 验证是否签约分类
            ContractCateQueryRequest cateQueryRequest = new ContractCateQueryRequest();
            cateQueryRequest.setStoreId(goods.getStoreId());
            cateQueryRequest.setCateId(goods.getCateId());
            if (contractCateRepository.count(cateQueryRequest.getWhereCriteria()) < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030005);
            }

            // 验证是否签约品牌
            if (goods.getBrandId() != null) {
                ContractBrandQueryRequest brandQueryRequest = new ContractBrandQueryRequest();
                brandQueryRequest.setStoreId(goods.getStoreId());
                brandQueryRequest.setGoodsBrandIds(Collections.singletonList(goods.getBrandId()));
                if (contractBrandRepository.count(brandQueryRequest.getWhereCriteria()) < 1) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030058);
                }
            }

            // 验证店铺分类存在性
            StoreCateQueryRequest request = new StoreCateQueryRequest();
            request.setStoreId(goods.getStoreId());
            request.setStoreCateIds(goods.getStoreCateIds());
            request.setDelFlag(DeleteFlag.NO);
            if (goods.getStoreCateIds().size() != storeCateRepository.count(request.getWhereCriteria())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030020);
            }
        }
    }

    /***
     * 填充商品默认数据
     * @param saveRequest   保存请求对象
     * @param goods         商品SPU对象
     * @param goodsInfos    商品SKU集合
     *
     * @return 0-商家代销商品的可售性，修改时使用
     *          1-上下架状态是否发生变化
     *          2-设价类型是否发生变化
     */
    protected boolean[] populateGoodsDefaultVal(
            GoodsSaveRequest saveRequest, GoodsSaveDTO goods, GoodsSaveDTO oldGoods, List<GoodsInfoSaveDTO> goodsInfos, Integer oldAddedFlag) {

        boolean[] results = new boolean[]{false, false, false};
        // 当前时间
        LocalDateTime currDate = LocalDateTime.now();
        // 判断入参
        if (Objects.nonNull(oldGoods)) {
            if (saveRequest.getIsChecked()) {
                goods.setAuditStatus(CheckStatus.CHECKED);
                oldGoods.setAuditStatus(CheckStatus.CHECKED);
            } else {
                goodsCommonService.setCheckState(oldGoods);
            }
        } else {
            goodsCommonService.setCheckState(goods);
        }


        // 新增时默认值
        if (Objects.isNull(oldGoods) || StringUtils.isEmpty(goods.getGoodsId())) {
            goods.setDelFlag(DeleteFlag.NO);
            goods.setCreateTime(currDate);
            goods.setAddedTime(currDate);
        }
        goods.setUpdateTime(currDate);

        // 商家代销商品的可售性，修改时使用
        Boolean isDealGoodsVendibility = Boolean.FALSE;

        // 默认按市场定价
        Nutils.isNullAction(goods.getPriceType(), GoodsPriceType.MARKET.toValue(), goods::setPriceType);
        // 默认状态-上架
        Nutils.isNullAction(goods.getAddedFlag(), AddedFlag.YES.toValue(), goods::setAddedFlag);
        // 默认取第一张图片作为商品图片
        if (CollectionUtils.isNotEmpty(saveRequest.getImages())) {
            Nutils.getFirst(saveRequest.getImages(), image -> goods.setGoodsImg(image.getArtworkUrl()));
        } else {
            goods.setGoodsImg(null);
        }
        // 默认不是多规格商品
        Nutils.isNullAction(goods.getMoreSpecFlag(), Constants.no, goods::setMoreSpecFlag);
        // 默认单规格商品
        goods.setSingleSpecFlag(Boolean.TRUE);
        // 默认不按客户单独定价
        Nutils.isNullAction(goods.getCustomFlag(), Constants.no, goods::setCustomFlag);
        // 默认不叠加客户等级折扣
        Nutils.isNullAction(goods.getLevelDiscountFlag(), Constants.no, goods::setLevelDiscountFlag);
        // 新增时赋值
        if (Objects.isNull(oldGoods)) {
            // 默认商品收藏量为0
            Nutils.isNullAction(goods.getGoodsCollectNum(), 0L, goods::setGoodsCollectNum);
            // 默认商品销量为0
            Nutils.isNullAction(goods.getGoodsSalesNum(), 0L, goods::setGoodsSalesNum);
            // 默认商品评论数为0
            Nutils.isNullAction(goods.getGoodsEvaluateNum(), 0L, goods::setGoodsEvaluateNum);
            // 默认商品好评数量为0
            Nutils.isNullAction(
                    goods.getGoodsFavorableCommentNum(), 0L, goods::setGoodsFavorableCommentNum);
            // 默认注水销量为0
            goods.setShamSalesNum(0L);
            // 默认排序号为0
            goods.setSortNo(0L);
            goods.setIsBuyCycle(Constants.no);
        } else {
            //填充上下架
            populateGoodsAddedFlag(goods);

            // 上下架状态是否发生变化
            boolean isChgAddedTime = false;
            // 上下架更改商家代销商品的可售性
            if (!Objects.equals(oldAddedFlag, goods.getAddedFlag())) {
                isChgAddedTime = true;
                isDealGoodsVendibility = Boolean.TRUE;
            }

            results[1] = isChgAddedTime;

            // 根据上下架状态是否发生变化设置上下架时间是当前时间还是等于之前时间
            if (isChgAddedTime) {
                goods.setAddedTime(currDate);
            } else {
                goods.setAddedTime(oldGoods.getAddedTime());
            }

            // 设价类型是否发生变化 -> 影响sku的独立设价状态为false
            boolean isChgPriceType = !goods.getPriceType().equals(oldGoods.getPriceType());
            results[2] = isChgPriceType;

            // 如果设价方式变化为非按客户设价，则将spu市场价清空
            if (isChgPriceType
                    && !GoodsPriceType.CUSTOMER.toIntegerValue().equals(goods.getPriceType())) {
                goods.setMarketPrice(null);
                goods.setCustomFlag(Constants.no);
            }
            if (SaleType.RETAIL.toValue() == goods.getSaleType()
                    && GoodsPriceType.STOCK.toValue() == goods.getPriceType()) {
                goods.setPriceType(GoodsPriceType.MARKET.toValue());
            }

            if (GoodsPriceType.MARKET.toValue() == goods.getPriceType()) {
                goods.setMarketPrice(null);
            }
            KsBeanUtil.copyPropertiesThird(goods, oldGoods, new String[]{"auditStatus", "pluginType","createTime"});
            // 如果当自动审核时，更改商家代销商品的可售性
            if (CheckStatus.CHECKED == oldGoods.getAuditStatus()
                    && (CheckStatus.CHECKED != goods.getAuditStatus())) {
                isDealGoodsVendibility = Boolean.TRUE;
            }
        }
        goods.setStock(goodsInfos.stream().filter(s -> Objects.nonNull(s.getStock()) && !Objects.equals(s.getDelFlag(),DeleteFlag.YES)).mapToLong(GoodsInfoSaveDTO::getStock).sum());
        // 兼容云掌柜, 如果没有定时上下架功能, 则默认设置为false
        if (Objects.isNull(goods.getAddedTimingFlag())) {
            goods.setAddedTimingFlag(Boolean.FALSE);
        }

        //填充上下架
        populateGoodsAddedFlag(goods);

        goods.setPluginType(saveRequest.getPluginType());
        results[0] = isDealGoodsVendibility;
        return results;
    }

    /**
     * @description 填充上下架
     * @Author qiyuanzhao
     * @Date 2022/6/25 11:20
     * @param
     * @return
     **/
    protected void populateGoodsAddedFlag(GoodsSaveDTO goods){
        // 如果只勾选了定时上架时间-先下架-后上架
        if (Boolean.TRUE.equals(goods.getAddedTimingFlag())
                && Objects.nonNull(goods.getAddedTimingTime())
                && !Boolean.TRUE.equals(goods.getTakedownTimeFlag())) {
            if (goods.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0) {
                goods.setAddedFlag(AddedFlag.NO.toValue());
            } else {
                goods.setAddedFlag(AddedFlag.YES.toValue());
            }
        }
        //如果只勾选了定时下架时间-先上架-后下架
        if (Boolean.TRUE.equals(goods.getTakedownTimeFlag())
                && !Boolean.TRUE.equals(goods.getAddedTimingFlag())){
            if (goods.getTakedownTime().compareTo(LocalDateTime.now()) > 0) {
                goods.setAddedFlag(AddedFlag.YES.toValue());
            }else {
                goods.setAddedFlag(AddedFlag.NO.toValue());
            }
        }
        //即选择了定时上架也选择了定时下架
        if (Boolean.TRUE.equals(goods.getTakedownTimeFlag()) && Boolean.TRUE.equals(goods.getAddedTimingFlag())){
            //商品此时上下架状态取决于谁的时间靠前
            if (goods.getAddedTimingTime().isBefore(goods.getTakedownTime())){
                goods.setAddedFlag(AddedFlag.NO.toValue());
            }else {
                goods.setAddedFlag(AddedFlag.YES.toValue());
            }
        }
    }

    /***
     * 删除或者更新商品关联的旧图片
     * @param newGoods      新商品对象
     * @param goodsImages   新商品图片对象
     * @return 需要新增的商品图片集合
     */
    protected List<GoodsImage> deleteOrUpdateOldImages(GoodsSaveDTO newGoods, List<GoodsImage> goodsImages, Map<GoodsEditType, ArrayList<String>> editMap) {
        List<GoodsImage> oldImages = goodsImageRepository.findByGoodsId(newGoods.getGoodsId());
        if (CollectionUtils.isEmpty(oldImages)) {
            return goodsImages;
        }

        //图片更新处理
        if (goodsImages.size() != oldImages.size()) {
            providerGoodsEditDetailService.addUpdateGoods(newGoods, editMap);
        }

        List<String> goodsImgUrl = goodsImages.stream().map(GoodsImage::getArtworkUrl).collect(Collectors.toList());
        List<String> oldImgUrl = oldImages.stream().map(GoodsImage::getArtworkUrl).collect(Collectors.toList());

        if (goodsImgUrl.hashCode() != oldImgUrl.hashCode()) {
            log.info("deleteOrUpdateOldImages true");
            providerGoodsEditDetailService.addUpdateGoods(newGoods, editMap);
            goodsImageRepository.deleteInBatch(oldImages);
            return goodsImages;
        }

        return Collections.emptyList();
    }

    /***
     * 保存商品主数据
     * @param goods     商品对象
     */
    protected abstract GoodsSaveDTO saveMainGoods(GoodsSaveDTO goods);

    /***
     * 保存商品图片
     * @param goods         商品对象
     * @param goodsImages   商品图片
     */
    protected void saveGoodsImage(GoodsSaveDTO goods, List<GoodsImage> goodsImages, Map<GoodsEditType, ArrayList<String>> editMap) {
        if (CollectionUtils.isNotEmpty(goodsImages)) {
            providerGoodsEditDetailService.addUpdateGoods(goods, editMap);
            goodsImages.forEach(goodsImage -> {
                goodsImage.setCreateTime(goods.getCreateTime());
                goodsImage.setUpdateTime(goods.getUpdateTime());
                goodsImage.setGoodsId(goods.getGoodsId());
                goodsImage.setDelFlag(DeleteFlag.NO);
                goodsImage.setImageId(null);
                goodsImage.setImageId(goodsImageRepository.save(goodsImage).getImageId());
            });
        }
    }

    protected void saveGoodsMainImage(GoodsSaveDTO goods, List<GoodsMainImage> mainImage) {

        /**
         * 更新商品主图信息
         * 1. 先查询已存在的主图信息
         * 2. 如果存在则先批量删除旧的主图数据
         * 3. 然后将新的主图URL保存到数据库中
         */
        if (CollectionUtils.isNotEmpty(mainImage)) {

            List<GoodsMainImage> goodsMainImageList = goodsMainImageRepository.findByGoodsId(goods.getGoodsId());

            if (CollectionUtils.isNotEmpty(goodsMainImageList)) {
                goodsMainImageRepository.deleteInBatch(goodsMainImageList);
            }

            mainImage.stream().map(GoodsMainImage::getArtworkUrl).forEach(item -> {
                GoodsMainImage goodsMainImage = new GoodsMainImage();
                goodsMainImage.setGoodsId(goods.getGoodsId());
                goodsMainImage.setArtworkUrl(item);
                goodsMainImage.setCreateTime(goods.getCreateTime());
                goodsMainImage.setUpdateTime(goods.getUpdateTime());
                goodsMainImage.setDelFlag(DeleteFlag.NO);
                goodsMainImage.setImageId(null);
                goodsMainImageRepository.save(goodsMainImage);
            });

        }
    }

    /***
     * 保存商品-门店分类关系
     * @param goods         商品对象
     * @param isUpdate      是否更新
     */
    protected void saveGoodsStoreCateRel(GoodsSaveDTO goods, boolean isUpdate) {
        if (osUtil.isS2b() && CollectionUtils.isNotEmpty(goods.getStoreCateIds())) {
            if (isUpdate) {
                storeCateGoodsRelaRepository.deleteByGoodsId(goods.getGoodsId());
            }
            goods.getStoreCateIds().stream().distinct().forEach(cateId -> {
                StoreCateGoodsRela storeCateGoodsRel = new StoreCateGoodsRela();
                storeCateGoodsRel.setGoodsId(goods.getGoodsId());
                storeCateGoodsRel.setStoreCateId(cateId);
                storeCateGoodsRelaRepository.save(storeCateGoodsRel);
            });
        }
    }

    /***
     * 保存商品属性
     * @param saveRequest   保存请求对象
     * @param goodsId       商品ID
     * @param oldGoods       商品
     * @param editMap      是否更新
     */
    protected void saveGoodsPropertyDetailRel(GoodsSaveRequest saveRequest, String goodsId, GoodsSaveDTO oldGoods, Map<GoodsEditType, ArrayList<String>> editMap) {
        // 如果是修改，删除旧数据
        List<GoodsPropertyDetailRel> propertyDetailRelList = KsBeanUtil.convert(saveRequest.getGoodsPropertyDetailRel(), GoodsPropertyDetailRel.class);
        if (Objects.nonNull(oldGoods)) {
            //处理属性比对
            if (Objects.nonNull(editMap)) {
                providerGoodsEditDetailService.goodsPropEdit(oldGoods, editMap, propertyDetailRelList);
            }
            // 先删除该商品下的属性
            goodsPropertyDetailRelRepository.deleteByGoodsIdAndGoodsType(goodsId, GoodsPropertyType.GOODS);
        }
        if (CollectionUtils.isNotEmpty(propertyDetailRelList)) {
            propertyDetailRelList.forEach(detailRel ->
                    Nutils.isNullAction(detailRel.getGoodsId(), goodsId, detailRel::setGoodsId));
            // 保存该商品的属性
            goodsPropertyDetailRelRepository.saveAll(propertyDetailRelList);
        }
    }

    /***
     * 保存商品详情模板关联实体
     * @param saveRequest   保存请求对象
     * @param goodsId       商品ID
     */
    protected void saveGoodsTabRel(GoodsSaveRequest saveRequest, String goodsId) {
        List<GoodsTabRela> goodsTabRelas = KsBeanUtil.convert(saveRequest.getGoodsTabRelas(), GoodsTabRela.class);
        if (CollectionUtils.isNotEmpty(goodsTabRelas)) {
            goodsTabRelas.forEach(info -> {
                Nutils.isNullAction(info.getGoodsId(), goodsId, info::setGoodsId);
                goodsTabRelaRepository.save(info);
            });
        }
    }

    /***
     * 更新、删除旧商品规格与规格详情数据
     * @param goods                     商品对象
     * @param oldGoods                  旧商品对象
     * @param specs                     商品规格
     * @param specDetails               商品规格详情
     * @param isSpecEnable              是否存在商品规格
     * @param isSpecDetailEnable        是否存在规格详情
     */
    protected void deleteOrUpdateGoodsSpecAndDetails(GoodsSaveDTO goods, GoodsSaveDTO oldGoods,
                                                     List<GoodsSpecSaveDTO> specs, List<GoodsSpecDetailSaveDTO> specDetails,
                                                     Map<Long, Integer> isSpecEnable, Map<Long, Integer> isSpecDetailEnable,
                                                     Map<Long, Boolean> specCount, Map<GoodsEditType, ArrayList<String>> editMap) {
        // 老商品数据是否多规格
        Integer oldMoreSpecFlag = Nutils.defaultVal(oldGoods.getMoreSpecFlag(), Constants.yes);
        // 新商品不是多规格，处理老数据，然后返回
        if (!Constants.yes.equals(goods.getMoreSpecFlag())) {
            // 如果老数据为多规格,修改为单规格
            if (Constants.yes.equals(oldMoreSpecFlag)) {
                // 删除规格
                goodsSpecRepository.deleteByGoodsId(goods.getGoodsId());
                // 删除规格值
                goodsSpecDetailRepository.deleteByGoodsId(goods.getGoodsId());
                // 删除商品规格值
                goodsInfoSpecDetailRelRepository.deleteByGoodsId(goods.getGoodsId());
            }
            providerGoodsEditDetailService.updateGoodsSpec(oldGoods, editMap);
            oldGoods.setSingleSpecFlag(Boolean.TRUE);
            goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
            return;
        }
        //VOP 商品需要删除 所有规格相关内容
        if(4 == goods.getGoodsSource()) {
            // 删除规格
            goodsSpecRepository.deleteByGoodsId(goods.getGoodsId());
            // 删除规格值
            goodsSpecDetailRepository.deleteByGoodsId(goods.getGoodsId());
            // 删除商品规格值
            goodsInfoSpecDetailRelRepository.deleteByGoodsId(goods.getGoodsId());
        }


        // 老商品不是多规格，不需要处理老数据
        if (Constants.no.equals(oldMoreSpecFlag)) {
            return;
        }

        // 更新规格
        List<GoodsSpec> goodsSpecs = goodsSpecRepository.findByGoodsId(oldGoods.getGoodsId());
        List<Long> specId = new ArrayList<>();
        List<Long> specDetailId = new ArrayList<>();
        // 不为空时，处理旧规格数据
        WmCollectionUtils.notEmpty2Loop(goodsSpecs, oldSpec -> {
            if (CollectionUtils.isNotEmpty(specs)) {
                Optional<GoodsSpecSaveDTO> specOpt = specs.stream().filter(spec -> oldSpec.getSpecId().equals(spec.getSpecId())).findFirst();
                // 如果规格存在且SKU有这个规格，更新
                if (specOpt.isPresent() && Constants.yes.equals(isSpecEnable.get(specOpt.get().getMockSpecId()))) {
                    //处理SKU信息变更
                    providerGoodsEditDetailService.addUpdateGoodsSpec(oldGoods, editMap, specOpt.get(), oldSpec);
                    KsBeanUtil.copyProperties(specOpt.get(), oldSpec);
                } else {
                    oldSpec.setDelFlag(DeleteFlag.YES);
                    specId.add(oldSpec.getSpecId());
                }
            } else {
                oldSpec.setDelFlag(DeleteFlag.YES);
                specId.add(oldSpec.getSpecId());
            }
            oldSpec.setUpdateTime(LocalDateTime.now());
            goodsSpecRepository.save(oldSpec);
        });

        //更新规格值
        List<GoodsSpecDetail> goodsSpecDetails = goodsSpecDetailRepository.findByGoodsId(oldGoods.getGoodsId());
        if (CollectionUtils.isNotEmpty(goodsSpecDetails)) {

            for (GoodsSpecDetail oldSpecDetail : goodsSpecDetails) {
                if (CollectionUtils.isNotEmpty(specDetails)) {
                    Optional<GoodsSpecDetailSaveDTO> specDetailOpt = specDetails.stream()
                            .filter(specDetail -> oldSpecDetail.getSpecDetailId().equals(specDetail.getSpecDetailId()))
                            .findFirst();
                    //如果规格值存在且SKU有这个规格值，更新
                    if (specDetailOpt.isPresent()
                            && Constants.yes.equals(isSpecDetailEnable.get(specDetailOpt.get().getMockSpecDetailId()))) {
                        //处理SKU信息变更
                        providerGoodsEditDetailService.addUpdateGoodsSpecDetail(oldGoods, editMap, specDetailOpt.get(), oldSpecDetail);
                        KsBeanUtil.copyProperties(specDetailOpt.get(), oldSpecDetail);

                        //更新SKU规格值表的名称备注
                        goodsInfoSpecDetailRelRepository.updateNameBySpecDetail(specDetailOpt.get().getDetailName(), oldSpecDetail.getSpecDetailId(), oldGoods.getGoodsId());
                    } else {
                        oldSpecDetail.setDelFlag(DeleteFlag.YES);
                        specDetailId.add(oldSpecDetail.getSpecDetailId());
                    }
                } else {
                    oldSpecDetail.setDelFlag(DeleteFlag.YES);
                    specDetailId.add(oldSpecDetail.getSpecDetailId());
                }
                oldSpecDetail.setUpdateTime(LocalDateTime.now());
                goodsSpecDetailRepository.save(oldSpecDetail);

                if (DeleteFlag.NO.equals(oldSpecDetail.getDelFlag())) {
                    specCount.put(oldSpecDetail.getSpecDetailId(), Boolean.TRUE);
                }
            }
        }
        //如果时供应商商品则根据规格项和规格值删除 商品规格关联关系
        if(0 == goods.getGoodsSource()) {
            // 根据规格项删除
            if (CollectionUtils.isNotEmpty(specId)) {
                goodsInfoSpecDetailRelRepository.deleteByGoodsIdAndSpecId(oldGoods.getGoodsId(), specId);
            }
            // 根据规格值删除
            if (CollectionUtils.isNotEmpty(specDetailId)) {
                goodsInfoSpecDetailRelRepository.deleteByGoodsIdAndSpecDetailId(oldGoods.getGoodsId(), specDetailId);
            }
        }
    }

    /***
     * 保存商品规格与规格详情数据
     * @param goods         商品对象
     * @param oldGoods      旧商品对象
     * @param goodsInfos    商品SKU集合
     * @param specs         商品规格
     * @param specDetails   商品规格详情
     */
    protected void saveGoodsSpecAndDetails(GoodsSaveDTO goods, GoodsSaveDTO oldGoods, List<GoodsInfoSaveDTO> goodsInfos,
                                           List<GoodsSpecSaveDTO> specs, List<GoodsSpecDetailSaveDTO> specDetails,
                                           Map<GoodsEditType, ArrayList<String>> editMap, Integer oldMoreSpecFlag) {
        // 如果不是多规格，不处理直接返回
        log.info("----------------goods:{}, oldGoods:{}", JSONObject.toJSONString(goods), JSONObject.toJSONString(oldGoods));
        if (!Constants.yes.equals(goods.getMoreSpecFlag())) {
            if (Objects.nonNull(oldGoods) && Objects.nonNull(oldMoreSpecFlag) && Constants.yes.equals(oldMoreSpecFlag)){
                //如果之前是多规格,删除之前的规格值
                goodsSpecRepository.deleteByGoodsId(oldGoods.getGoodsId());
                goodsSpecDetailRepository.deleteByGoodsId(oldGoods.getGoodsId());
                //属性信息
                if (editMap.containsKey(GoodsEditType.INFO_EDIT)) {
                    ArrayList<String> editMsg = editMap.get(GoodsEditType.INFO_EDIT);
                    if (!editMap.get(GoodsEditType.INFO_EDIT).contains(GoodsEditMsg.UPDATE_GOODS)) {
                        editMsg.add(GoodsEditMsg.GOODS_INFO_DEL);
                        editMap.put(GoodsEditType.INFO_EDIT, editMsg);
                    }
                } else {
                    ArrayList<String> editMsg = new ArrayList<>(Arrays.asList(GoodsEditMsg.GOODS_INFO_DEL));
                    editMap.put(GoodsEditType.INFO_EDIT, editMsg);
                }
            }
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
        // 删除/更新规格/规格详情
        if (Objects.nonNull(oldGoods)) {
            // TODO 处理旧数据
            deleteOrUpdateGoodsSpecAndDetails(goods, oldGoods, specs,
                    specDetails, isSpecEnable, isSpecDetailEnable, specCount, editMap);
        }

        // 新增规格
        // 过滤SKU有这个规格
        specs.stream().filter(goodsSpec -> Constants.yes.equals(isSpecEnable.get(goodsSpec.getMockSpecId())))
                .forEach(goodsSpec -> {
                    goodsSpec.setCreateTime(goods.getCreateTime());
                    goodsSpec.setUpdateTime(goods.getUpdateTime());
                    goodsSpec.setGoodsId(goods.getGoodsId());
                    goodsSpec.setDelFlag(DeleteFlag.NO);
                    goodsSpec.setSpecId(goodsSpecRepository.save(KsBeanUtil.copyPropertiesThird(goodsSpec, GoodsSpec.class)).getSpecId());
                });

        // 新增规格值
        // 过滤SKU有这个规格详情
        specDetails.stream().filter(goodsSpecDetail -> Constants.yes.equals(isSpecDetailEnable.get(goodsSpecDetail.getMockSpecDetailId())))
                .forEach(goodsSpecDetail -> {
                    Optional<GoodsSpecSaveDTO> specOpt = specs.stream().filter(goodsSpec -> goodsSpec.getMockSpecId().equals(goodsSpecDetail.getMockSpecId())).findFirst();
                    if (specOpt.isPresent()) {
                        goodsSpecDetail.setCreateTime(goods.getCreateTime());
                        goodsSpecDetail.setUpdateTime(goods.getUpdateTime());
                        goodsSpecDetail.setGoodsId(goods.getGoodsId());
                        goodsSpecDetail.setDelFlag(DeleteFlag.NO);
                        goodsSpecDetail.setSpecId(specOpt.get().getSpecId());
                        goodsSpecDetail.setSpecDetailId(
                                goodsSpecDetailRepository.save(KsBeanUtil.copyPropertiesThird(goodsSpecDetail, GoodsSpecDetail.class))
                                        .getSpecDetailId());
                        specCount.put(goodsSpecDetail.getSpecDetailId(), Boolean.TRUE);
                    }
                    //更新SKU规格值表的名称备注
                    if (Objects.nonNull(oldGoods)) {
                        goodsInfoSpecDetailRelRepository.updateNameBySpecDetail(goodsSpecDetail.getDetailName(), goodsSpecDetail.getMockSpecDetailId(), oldGoods.getGoodsId());
                    }
                });

        // 判断是否单规格标识
        if (specDetails.stream().filter(s -> StringUtils.isNotBlank(s.getGoodsId())).count() > 1) {
            if (Objects.equals(CheckStatus.CHECKED,goods.getAuditStatus())){
                goods.setSingleSpecFlag(Boolean.FALSE);
                goodsRepository.save(KsBeanUtil.copyPropertiesThird(goods, Goods.class));
            }else {
                goodsAuditRepository.updateSingleSpecFlagByGoodsAuditId(goods.getGoodsId(), Boolean.FALSE);
            }
        }

        // 判断是否更新
        if (Objects.nonNull(oldGoods)) {
            if (specCount.size() <= 1) {
                oldGoods.setSingleSpecFlag(Boolean.TRUE);
                goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
            } else {
                oldGoods.setSingleSpecFlag(Boolean.FALSE);
                goodsRepository.save(KsBeanUtil.copyPropertiesThird(oldGoods, Goods.class));
            }
        }
    }

    /****
     * 保存商品详情，商品-规格映射信息
     * @param goods         商品对象
     * @param goodsInfos    商品SKU对象
     * @param specs         商品规格值对象
     * @param specDetails   商品规格值详情对象
     * @param saveRequest
     * @param goodsCommissionConfig
     */
    protected void saveGoodsInfo(GoodsSaveDTO goods, List<GoodsInfoSaveDTO> goodsInfos,
                                 List<GoodsSpecSaveDTO> specs, List<GoodsSpecDetailSaveDTO> specDetails,
                                 long startTime, BoolFlag skuEditPrice, GoodsSaveRequest saveRequest,
                                 GoodsCommissionConfig goodsCommissionConfig) {
        // 最小sku市场价
        BigDecimal minPrice = goods.getMarketPrice();
        List<GoodsInfoSpecDetailRelDTO> detailRelList = new ArrayList<>();

        for (GoodsInfoSaveDTO sku : goodsInfos) {
            // sku维度设价
            if (BoolFlag.YES.equals(skuEditPrice)){
                // 属性拷贝
                KsBeanUtil.copyPropertiesIgnoreNullVal(goods, sku, new String[]{"aloneFlag", "customFlag", "levelDiscountFlag",
                        "marketPrice", "stock", "addedFlag", "supplyPrice", "isBuyCycle"});
            } else {
                // 属性拷贝
                KsBeanUtil.copyPropertiesIgnoreNullVal(goods, sku, new String[]{"marketPrice", "stock", "addedFlag", "supplyPrice", "isBuyCycle"});
                //备用sku价格
                BigDecimal marketPriceForProvider = sku.getMarketPrice();

                if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())
                        && goods.getMarketPrice() != null) {
                    // XXX 后面商家boss新增商品页优化后，可以删除这部分逻辑
                    sku.setMarketPrice(goods.getMarketPrice());
                    if (Objects.nonNull(goods.getProviderGoodsId()) && Objects.nonNull(goodsCommissionConfig)){
                        //智能设价  如果打开独立设置加价比例开关则使用前端传值  如果没有打开则重新计算
                        if (CommissionSynPriceType.AI_SYN.toValue() == goodsCommissionConfig.getSynPriceType().toValue()) {
                            //关闭供应商价格独立编辑开关
                            if (Objects.nonNull(saveRequest.getIsIndependent()) && saveRequest.getIsIndependent().toValue() == 0) {
                                //重新获取加价比例
                                GoodsCommissionPriceConfig goodsCommissionPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(sku.getStoreId(), sku);
                                BigDecimal addRate = BigDecimal.ZERO;
                                //重新计算加价值
                                if (Objects.nonNull(goodsCommissionPriceConfig)) {
                                    addRate = goodsCommissionPriceConfig.getAddRate();
                                } else {
                                    addRate = Objects.isNull(goodsCommissionConfig.getAddRate()) ? BigDecimal.ZERO : goodsCommissionConfig.getAddRate();
                                }
                                BigDecimal addPrice = goodsCommissionPriceService.getAddPrice(addRate, sku.getSupplyPrice());
                                sku.setMarketPrice(sku.getSupplyPrice().add(addPrice));
                            } else {
                                //直接取页面sku价格
                                sku.setMarketPrice(Objects.nonNull(marketPriceForProvider) ? marketPriceForProvider : BigDecimal.ZERO);
                            }
                        }
                    }
                }
                sku.setAloneFlag(Boolean.FALSE);
            }

            //VOP商品需要特殊处理
            if(Objects.nonNull(goods.getThirdPlatformType())
                    && ThirdPlatformType.VOP.toValue() == goods.getThirdPlatformType().toValue()) {
                sku.setGoodsInfoName(StringUtils.isBlank(sku.getGoodsInfoName()) ? goods.getGoodsName() : sku.getGoodsInfoName());
            } else {
                sku.setGoodsInfoName(goods.getGoodsName());
            }

            // SKU库存为空默认给0
            Nutils.isNullAction(sku.getStock(), 0L, sku::setStock);

            Nutils.isNullAction(sku.getIsBuyCycle(), Constants.no, sku::setIsBuyCycle);

            // 填充上下架状态
            if (Objects.isNull(sku.getAddedFlag())) {
                if (!goods.getAddedFlag().equals(AddedFlag.PART.toValue())) {
                    // 如果上下架不是部分上下架，以SPU为准
                    sku.setAddedFlag(goods.getAddedFlag());
                } else {
                    // 如果SPU选择部分上架，新增SKU的上下架状态为上架
                    sku.setAddedFlag(AddedFlag.YES.toValue());
                }
            }

            // 为二审更新原始SKU信息
            if (Objects.equals(CheckStatus.WAIT_CHECK, goods.getAuditStatus())) {
                goodsInfoRepository.updateAddedFlagByGoodsInfoId(sku.getAddedFlag(), sku.getOldGoodsInfoId());
            }

            sku.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);

            // 批发类型不支付购买积分
            if (Integer.valueOf(SaleType.WHOLESALE.toValue()).equals(goods.getSaleType())) {
                sku.setBuyPoint(0L);
            }
            if (Objects.nonNull(sku.getOldGoodsInfoId())) {
                GoodsInfoSaveVO info = KsBeanUtil.copyPropertiesThird(goodsInfoRepository.getOne(sku.getOldGoodsInfoId()), GoodsInfoSaveVO.class);
                if (Objects.nonNull(info.getProviderGoodsInfoId())) {
                    sku.setProviderGoodsInfoId(info.getProviderGoodsInfoId());
                }
                if (Objects.nonNull(info.getDistributionGoodsAudit())){
                    sku.setDistributionGoodsAudit(info.getDistributionGoodsAudit());
                }
                if(Objects.nonNull(info.getAloneFlag()) && Objects.equals(BoolFlag.NO,saveRequest.getSkuEditPrice())
                && Objects.equals(info.getPriceType(),sku.getPriceType())){
                    sku.setAloneFlag(info.getAloneFlag());
                    if(info.getAloneFlag()){
                        sku.setMarketPrice(info.getMarketPrice());
                    }
                }
                sku.setIsBuyCycle(info.getIsBuyCycle());
            }

            String goodsInfoId = goodsInfoRepository.save(KsBeanUtil.copyPropertiesThird(sku, GoodsInfo.class)).getGoodsInfoId();
            sku.setGoodsInfoId(goodsInfoId);

            // 初始化Redis中的SKU库存
            initRedisSkuStock(sku.getStock(), goodsInfoId);

            // 处理库存预警
            if (CheckStatus.CHECKED.equals(sku.getAuditStatus()) && DeleteFlag.NO.equals(sku.getDelFlag())) {
                handleWaringStockRegister(KsBeanUtil.convert(sku, GoodsInfo.class));
            }


            log.info("商品新增多规格开始->花费{}毫秒", (System.currentTimeMillis() - startTime));
            // 如果是多规格,新增SKU与规格明细值的关联表
            if (Constants.yes.equals(goods.getMoreSpecFlag())) {
                WmCollectionUtils.notEmpty2Loop(specs, spec -> {
                    if (!sku.getMockSpecIds().contains(spec.getMockSpecId())) {
                        return;
                    }
                    specDetails.stream()
                            .filter(detail -> spec.getMockSpecId().equals(detail.getMockSpecId())
                                    && sku.getMockSpecDetailIds().contains(detail.getMockSpecDetailId())).forEach(detail -> {
                                GoodsInfoSpecDetailRelDTO detailRel = new GoodsInfoSpecDetailRelDTO();
                                detailRel.setGoodsId(goods.getGoodsId());
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
            log.info("商品新增多规格结束->花费{}毫秒", (System.currentTimeMillis() - startTime));

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
        goods.setSkuMinMarketPrice(minPrice);

        if (GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == goods.getGoodsType()) {
            List<Long> newElectronicIds = goodsInfos.stream().map(GoodsInfoSaveDTO::getElectronicCouponsId).collect(Collectors.toList());
            electronicCouponProvider.updateBindingFlag(ElectronicCouponUpdateBindRequest.builder().bindingIds(newElectronicIds).build());
        }
    }

    /***
     * 初始化Redis中的SKU库存
     * @param stock         库存
     * @param goodsInfoId   商品SkuId
     */
    protected void initRedisSkuStock(Long stock, String goodsInfoId) {
        // 覆盖redis中缓存，如果需要增量修改，则需要结合前端修改
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                goodsInfoStockService.initCacheStock(stock, goodsInfoId);
            }
        });
    }

    protected void handleWaringStockRegister(GoodsInfo sku) {
        // 覆盖redis中缓存，如果需要增量修改，则需要结合前端修改
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                handleWaringStock(sku);
            }
        });
    }

    protected void handleWaringStock(GoodsInfo sku) {
        storeMessageBizService.handleWarningStockByGoodsInfos(Collections.singletonList(sku));
    }

    /***
     * 商品导入到ES
     * @param goods     商品对象
     * @param goodsId   商品ID
     */
    protected void import2Es(GoodsSaveDTO goods, String goodsId) {
        // 商品未经过审核不导入ES
        if (goods.getAuditStatus() != CheckStatus.CHECKED) {
            return;
        }
        if (goods.getAuditStatus() == CheckStatus.CHECKED) {
            if (GoodsSource.VOP.toValue() == goods.getGoodsSource()) {
                standardImportService.importStandard(GoodsRequest.builder().goodsIds(Collections.singletonList(goodsId)).build());
            } else if (GoodsSource.PROVIDER.toValue() == goods.getGoodsSource()) {
                // 供应商刷新商品库异步处理
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        standardImportAsyncService.asyncImportStandard(goodsId);
                    }
                });
            }
        }
    }

    /**
     * 储存商品相关设价信息
     *
     * @param goodsInfos  sku集
     * @param goods       同一个spu信息
     * @param saveRequest 请求封装参数
     */
    protected void saveGoodsPrice(List<GoodsInfoSaveDTO> goodsInfos, GoodsSaveDTO goods, GoodsSaveRequest saveRequest) {
        List<String> skuIds;
        //提取非独立设价的Sku编号,进行清理设价数据
        if (goods.getPriceType() == 1 && goods.getAllowPriceSet() == 0) {
            skuIds = goodsInfos.stream()
                    .map(GoodsInfoSaveDTO::getGoodsInfoId)
                    .collect(Collectors.toList());
        } else {
            skuIds = goodsInfos.stream()
                    .filter(sku -> Objects.isNull(sku.getAloneFlag()) || !sku.getAloneFlag())
                    .map(GoodsInfoSaveDTO::getGoodsInfoId)
                    .collect(Collectors.toList());
        }

        if (!skuIds.isEmpty() && !BoolFlag.YES.equals(saveRequest.getSkuEditPrice())) {
            goodsIntervalPriceRepository.deleteByGoodsInfoIds(skuIds);
            goodsLevelPriceRepository.deleteByGoodsInfoIds(skuIds);
            goodsCustomerPriceRepository.deleteByGoodsInfoIds(skuIds);
        }

        for (GoodsInfoSaveDTO sku : goodsInfos) {
            // 如果SKU是保持独立，则不更新
            if (!(goods.getPriceType() == 1 && goods.getAllowPriceSet() == 0) && Objects.nonNull(sku.getAloneFlag())
                    && sku.getAloneFlag()) {
                continue;
            }

            //按订货量设价，保存订货区间
            if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
                if (CollectionUtils.isNotEmpty(saveRequest.getGoodsIntervalPrices())) {
                    List<GoodsIntervalPrice> newGoodsInterValPrice = new ArrayList<>();
                    saveRequest.getGoodsIntervalPrices().forEach(intervalPrice -> {
                        GoodsIntervalPrice newIntervalPrice = new GoodsIntervalPrice();
                        newIntervalPrice.setGoodsId(sku.getGoodsId());
                        newIntervalPrice.setGoodsInfoId(sku.getGoodsInfoId());
                        newIntervalPrice.setType(PriceType.SKU);
                        newIntervalPrice.setCount(intervalPrice.getCount());
                        newIntervalPrice.setPrice(intervalPrice.getPrice());
                        newGoodsInterValPrice.add(newIntervalPrice);
                    });
                    goodsIntervalPriceRepository.saveAll(newGoodsInterValPrice);
                }
            } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())) {
                //按客户等级
                if (CollectionUtils.isNotEmpty(saveRequest.getGoodsLevelPrices())) {
                    List<GoodsLevelPrice> newLevelPrices = new ArrayList<>();
                    saveRequest.getGoodsLevelPrices().forEach(goodsLevelPrice -> {
                        GoodsLevelPrice newLevelPrice = new GoodsLevelPrice();
                        newLevelPrice.setLevelId(goodsLevelPrice.getLevelId());
                        newLevelPrice.setGoodsId(sku.getGoodsId());
                        newLevelPrice.setGoodsInfoId(sku.getGoodsInfoId());
                        newLevelPrice.setPrice(goodsLevelPrice.getPrice());
                        newLevelPrice.setCount(goodsLevelPrice.getCount());
                        newLevelPrice.setMaxCount(goodsLevelPrice.getMaxCount());
                        newLevelPrice.setType(PriceType.SKU);
                        newLevelPrices.add(newLevelPrice);
                    });
                    goodsLevelPriceRepository.saveAll(newLevelPrices);
                }

                //按客户单独定价
                if (Constants.yes.equals(goods.getCustomFlag()) && CollectionUtils.isNotEmpty(saveRequest.getGoodsCustomerPrices())) {
                    List<GoodsCustomerPrice> newCustomerPrices = new ArrayList<>();
                    saveRequest.getGoodsCustomerPrices().forEach(price -> {
                        GoodsCustomerPrice newCustomerPrice = new GoodsCustomerPrice();
                        newCustomerPrice.setGoodsInfoId(sku.getGoodsInfoId());
                        newCustomerPrice.setGoodsId(sku.getGoodsId());
                        newCustomerPrice.setCustomerId(price.getCustomerId());
                        newCustomerPrice.setMaxCount(price.getMaxCount());
                        newCustomerPrice.setCount(price.getCount());
                        newCustomerPrice.setType(PriceType.SKU);
                        newCustomerPrice.setPrice(price.getPrice());
                        newCustomerPrices.add(newCustomerPrice);
                    });
                    goodsCustomerPriceRepository.saveAll(newCustomerPrices);
                }
            }
        }
    }

    /***
     * 保存前最后一次改变商品SPU对象
     * @param goods     商品对象
     * @param isEdit    是否编辑模式
     * @return          改变后的商品对象
     */
    protected GoodsSaveDTO beforeSaveSpu(GoodsSaveDTO goods, Boolean isEdit){
        return goods;
    }

    /***
     * 保存前最后一次改变商品SKU对象
     * @param sku       商品SKU对象
     * @param isEdit    是否编辑模式
     * @return          改变后的商品对象
     */
    protected GoodsInfoSaveDTO beforeSaveSku(GoodsInfoSaveDTO sku, Boolean isEdit){
        return sku;
    }
}
