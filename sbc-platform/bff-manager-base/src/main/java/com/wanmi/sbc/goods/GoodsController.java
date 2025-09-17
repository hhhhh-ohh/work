package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.*;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsProvider;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionPriceConfigProvider;
import com.wanmi.sbc.goods.api.provider.goodspropertydetailrel.GoodsPropertyDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.wechatvideo.wechatsku.WechatSkuQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsBySpuIdRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsModifyStateRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsExistsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditByIdRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditDelByIdListRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsChangeRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.api.response.freight.FreightTemplateGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.*;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditByIdResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditModifyResponse;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionPriceConfigQueryResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailGoodsIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByGoodsIdresponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByGoodsResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.bean.vo.wechatvideo.WechatSkuVO;
import com.wanmi.sbc.goods.request.GoodsSupplierExcelImportRequest;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.GoodsExcelService;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardInvalidRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponListRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponUpdateBindRequest;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCardModifyCountResponse;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.operatedatalog.OperateDataLogQueryProvider;
import com.wanmi.sbc.setting.api.provider.operatedatalog.OperateDataLogSaveProvider;
import com.wanmi.sbc.setting.api.provider.pagemanage.GoodsInfoExtendProvider;
import com.wanmi.sbc.setting.api.provider.pagemanage.GoodsInfoExtendQueryProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.operatedatalog.OperateDataLogDelByOperateIdRequest;
import com.wanmi.sbc.setting.api.request.operatedatalog.OperateDataLogListRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendDeleteByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendModifyRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.api.response.operatedatalog.OperateDataLogListResponse;
import com.wanmi.sbc.setting.api.response.pagemanage.GoodsInfoExtendByIdResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.OperateDataLogVO;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformGoodsProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.SellPlatformDeleteGoodsRequest;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 17/4/12.
 */
@Slf4j
@Tag(name = "GoodsController", description = "商品服务 Api")
@RestController
@Validated
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 默认全部支持的购买方式
     */
    private static final String GOODS_BUY_TYPES = "0,1";

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private GoodsExcelService goodsExcelService;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private FreightTemplateGoodsQueryProvider freightTemplateGoodsQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private OperateDataLogQueryProvider operateDataLogQueryProvider;

    @Autowired
    private OperateDataLogSaveProvider operateDataLogSaveProvider;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Autowired
    private GoodsInfoExtendQueryProvider goodsInfoExtendQueryProvider;

    @Autowired
    private GoodsInfoExtendProvider goodsInfoExtendProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsPropertyDetailRelQueryProvider goodsPropertyDetailRelQueryProvider;

    @Autowired
    private GoodsCommissionPriceConfigProvider goodsCommissionPriceConfigProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired
    private GoodsAuditProvider goodsAuditProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    @Autowired
    private WechatSkuQueryProvider wechatSkuQueryProvider;

    @Autowired
    private SellPlatformGoodsProvider sellPlatformGoodsProvider;

    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    @Autowired private MqSendProvider mqSendProvider;

    @Autowired
    private BuyCycleGoodsQueryProvider buyCycleGoodsQueryProvider;

    @Autowired
    private BuyCycleGoodsProvider buyCycleGoodsProvider;

    @Autowired
    private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;


    /**
     * 新增商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "新增商品")
    @RequestMapping(value = "/spu", method = RequestMethod.POST)
    public BaseResponse<String> add(@RequestBody @Valid GoodsAddRequest request) {
        log.info("商品新增开始supplier");
        Platform platform = commonUtil.getOperator().getPlatform();
        if (!Platform.SUPPLIER.equals(platform)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        long startTime = System.currentTimeMillis();
        if (request.getGoods() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Long fId = request.getGoods().getFreightTempId();
        Integer goodsType = request.getGoods().getGoodsType();
        Integer goodsSource = request.getGoods().getGoodsSource();
        //商品来源必须是商家
        if (!Integer.valueOf(Constants.ONE).equals(goodsSource)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(CollectionUtils.isEmpty(request.getGoodsSpecs())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(Objects.isNull(fId)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(CollectionUtils.isEmpty(request.getGoodsInfos())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        for (GoodsInfoDTO item : request.getGoodsInfos()) {
            BigDecimal marketPrice = item.getMarketPrice();
            BigDecimal linePrice = item.getLinePrice();
            if (Objects.nonNull(marketPrice) && Objects.nonNull(linePrice)) {
                if (linePrice.compareTo(marketPrice) < 0) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }
        //虚拟商品校验
        goodsBaseService.checkVirtualGoods(request.getGoods(),request.getGoodsInfos());
        // 添加默认值, 适应云掌柜新增商品没有设置购买方式, 导致前台不展示购买方式问题
        if (StringUtils.isBlank(request.getGoods().getGoodsBuyTypes())) {
            request.getGoods().setGoodsBuyTypes(GOODS_BUY_TYPES);
        }

        // 查询公司VO，查不到则直接报错
        CompanyInfoVO companyInfo = BaseResUtils.getContextFromRes(companyInfoQueryProvider
                .getCompanyInfoById(new CompanyInfoByIdRequest(commonUtil.getCompanyInfoId())), CommonErrorCodeEnum.K000009);

        // 判断运费模板是否存在
        freightTemplateGoodsQueryProvider.existsById(
                FreightTemplateGoodsExistsByIdRequest.builder().freightTempId(fId).build());

        request.getGoods().setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.getGoods().setCompanyType(companyInfo.getCompanyType());
        request.getGoods().setStoreId(commonUtil.getStoreId());
        request.getGoods().setSupplierName(companyInfo.getSupplierName());
        request.getGoods().setStoreType(companyInfo.getStoreType());
        // 将spu的商品类型带入sku
        request.setGoodsInfos(request.getGoodsInfos().parallelStream()
                .peek(goodsInfoDTO -> {
                    goodsInfoDTO.setGoodsType(goodsType);
                    // 如果不是实体商品，设置重量体积为0
                    if (!goodsType.equals(NumberUtils.INTEGER_ZERO)) {
                        goodsInfoDTO.setGoodsCubage(BigDecimal.ZERO);
                        goodsInfoDTO.setGoodsWeight(BigDecimal.ZERO);
                    }
                    //如果是卡券商品，库存取卡密实际库存
                    if (goodsType.equals(Constants.TWO)) {
                        Long count = countEffectiveCoupon(goodsInfoDTO.getElectronicCouponsId()).getContext().getCount();
                        goodsInfoDTO.setStock(count);
                    }
                })
                .collect(Collectors.toList()));
        BaseResponse<GoodsAddResponse> baseResponse = goodsProvider.add(request);
        GoodsAddResponse response = baseResponse.getContext();
        CheckStatus checkStatus = Optional.ofNullable(response)
                .map(GoodsAddResponse::getAuditStatus)
                .orElse(null);
        String goodsId = Optional.ofNullable(response)
                .map(GoodsAddResponse::getResult)
                .orElse(null);
        if (Objects.equals(CheckStatus.CHECKED, checkStatus) && StringUtils.isNotBlank(goodsId)) {
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsId).build());
        }
        operateLogMQUtil.convertAndSend("商品", "直接发布",
                "直接发布：SPU编码" + request.getGoods().getGoodsNo());
        log.info("商品新增结束supplier->花费{}毫秒", (System.currentTimeMillis() - startTime));

        // ============= 处理平台的消息发送：商家新增商品，平台待审核 START =============
        storeMessageBizService.handleForGoodsAudit(checkStatus, request, response);
        // ============= 处理平台的消息发送：商家新增商品，平台待审核 END =============
        return BaseResponse.success(goodsId);
    }

    /**
     * 同时新增商品基本和商品设价
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "同时新增商品基本和商品设价")
    @RequestMapping(value = "/spu/price", method = RequestMethod.POST)
    public BaseResponse<String> spuDetail(@RequestBody @Valid GoodsAddAllRequest request) {
        Long fId = request.getGoods().getFreightTempId();
        if (request.getGoods() == null || CollectionUtils.isEmpty(request.getGoodsInfos()) || Objects.isNull(fId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 添加默认值, 适应云掌柜新增商品没有设置购买方式, 导致前台不展示购买方式问题
        if (StringUtils.isBlank(request.getGoods().getGoodsBuyTypes())) {
            request.getGoods().setGoodsBuyTypes(GOODS_BUY_TYPES);
        }

        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
        ).getContext();
        if (companyInfo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //判断运费模板是否存在
        freightTemplateGoodsQueryProvider.existsById(
                FreightTemplateGoodsExistsByIdRequest.builder().freightTempId(fId).build());
        request.getGoods().setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.getGoods().setCompanyType(companyInfo.getCompanyType());
        request.getGoods().setStoreId(commonUtil.getStoreId());
        request.getGoods().setSupplierName(companyInfo.getSupplierName());

        BaseResponse<GoodsAddAllResponse> baseResponse = goodsProvider.addAll(request);
        GoodsAddAllResponse response = baseResponse.getContext();
        CheckStatus checkStatus = Optional.ofNullable(response).map(GoodsAddAllResponse::getAuditStatus).orElse(null);
        String goodsId = Optional.ofNullable(response).map(GoodsAddAllResponse::getResult).orElse(null);
        if (Objects.equals(CheckStatus.CHECKED, checkStatus) && StringUtils.isNotBlank(goodsId)) {
            //更新redis商品基本数据
            String goodsDetailInfo =
                    redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
            if (StringUtils.isNotBlank(goodsDetailInfo)) {
                redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
            }

            //初始化es
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsId).build());
        }

        //更新智能设价下商家独立加价比例，若是存在
        if (CollectionUtils.isNotEmpty(request.getCommissionPriceTargetVOS())) {
            request.setUserId(commonUtil.getOperatorId());
            request.setBaseStoreId(commonUtil.getStoreId());
            goodsCommissionPriceConfigProvider.update(GoodsCommissionPriceConfigUpdateRequest.builder().commissionPriceTargetVOList(request.getCommissionPriceTargetVOS()).build());
        }
        operateLogMQUtil.convertAndSend("商品", "直接发布",
                "直接发布：SPU编码" + request.getGoods().getGoodsNo());
        return BaseResponse.success(goodsId);
    }

    /**
     * 编辑商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑商品")
    @RequestMapping(value = "/spu", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid GoodsModifyRequest request) {
        if(CollectionUtils.isEmpty(request.getGoodsInfos())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsBaseService.edit(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 保存商品价格
     */
    @MultiSubmit
//    @GlobalTransactional
    @Operation(summary = "保存商品价格")
    @RequestMapping(value = "/spu/price", method = RequestMethod.PUT)
    public BaseResponse editSpuPrice(@RequestBody @Valid GoodsModifyAllRequest request) {
        if(Objects.isNull(request.getGoods()) || StringUtils.isBlank(request.getGoods().getGoodsId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(CollectionUtils.isEmpty(request.getGoodsSpecs())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(CollectionUtils.isEmpty(request.getGoodsInfos())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        for (GoodsInfoVO item : request.getGoodsInfos()) {
            BigDecimal marketPrice = item.getMarketPrice();
            BigDecimal linePrice = item.getLinePrice();
            if (Objects.nonNull(marketPrice) && Objects.nonNull(linePrice)) {
                if (linePrice.compareTo(marketPrice) < 0) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }
        if(Objects.isNull(request.getGoods().getCompanyType())){
            request.getGoods().setCompanyType(commonUtil.getCompanyType());
            for (GoodsInfoVO goodsInfo : request.getGoodsInfos()) {
                goodsInfo.setCompanyType(commonUtil.getCompanyType());
            }
        }
        //校验是否当前登陆商家商品
        GoodsByIdResponse response = goodsQueryProvider.getById(GoodsByIdRequest.builder().goodsId(request.getGoods().getGoodsId()).build())
                .getContext();
        Integer isBuyCycle = Constants.no;
        if (Objects.nonNull(response)){
            commonUtil.checkStoreId(response.getStoreId());
            isBuyCycle = response.getIsBuyCycle();
        }
        if (Objects.equals(isBuyCycle,Constants.yes)
                && Objects.equals(SaleType.WHOLESALE,SaleType.fromValue(request.getGoods().getSaleType()))) {
            BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsQueryProvider.getBySpuId(BuyCycleGoodsBySpuIdRequest.builder()
                    .spuId(request.getGoods().getGoodsId())
                    .build()).getContext().getBuyCycleGoodsVO();
            if(buyCycleGoodsVO != null) {
                buyCycleGoodsProvider.modifyState(BuyCycleGoodsModifyStateRequest.builder()
                        .id(buyCycleGoodsVO.getId())
                        .cycleState(Constants.yes)
                        .build());
            }
        }
        goodsBaseService.dealPrice(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取商品详情信息
     *
     * @param goodsId 商品编号
     * @return 商品详情
     */
    @Operation(summary = "获取商品详情信息")
    @Parameter(name = "goodsId", description = "商品Id", required = true)
    @RequestMapping(value = "/spu/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsViewByIdResponse> info(@PathVariable String goodsId) {
        BaseResponse<GoodsViewByIdResponse> response = goodsBaseService.info(goodsId);
        List<GoodsInfoVO> goodsInfos = response.getContext().getGoodsInfos();
        if (!Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
            commonUtil.checkStoreId(response.getContext().getGoods().getStoreId());
        }
        Long storeId = commonUtil.getStoreId();
        if(Objects.nonNull(storeId)){
            //填充当前商家的库存预警值
            StoreMessageNodeSettingVO storeMessageNodeSettingVO =storeMessageNodeSettingQueryProvider.getWarningStock(StoreMessageNodeSettingByStoreIdRequest.builder().storeId(storeId).build()).getContext().getStoreMessageNodeSettingVO();
            if (Objects.nonNull(storeMessageNodeSettingVO) && storeMessageNodeSettingVO.getStatus().equals(BoolFlag.YES)) {
                Long warningStock = Nutils.defaultVal(storeMessageNodeSettingVO.getWarningStock(), 1L);
                goodsInfos.stream()
                        .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getAuditStatus(), CheckStatus.CHECKED))
                        .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getAddedFlag(), NumberUtils.INTEGER_ONE))
                        .forEach(item -> {
                            item.setWarningStock(warningStock);
                            if (item.getStock() < warningStock) {
                                item.setShowStockOutFlag(true);
                            }
                        });
            }
        }
        return response;
    }

    /**
     * 获取spu下的运费模板id
     *
     * @param goodsId 商品编号
     * @return 运费模板id
     */
    @Operation(summary = "获取运费模板id")
    @Parameter(name = "goodsId", description = "商品Id", required = true)
    @RequestMapping(value = "/spu/freightId/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsViewByIdResponse> getFreightId(@PathVariable String goodsId) {
        GoodsViewByIdRequest request = new GoodsViewByIdRequest();
        request.setGoodsId(goodsId);
        GoodsViewByIdResponse response = goodsQueryProvider.getViewById(request).getContext();
        if (!Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
            GoodsSellResponse sellResponse = goodsQueryProvider.findSellGoods(GoodsSellRequest.builder().storeId(commonUtil.getStoreId()).providerGoodsId(goodsId).build()).getContext();
            if (!Objects.equals(commonUtil.getStoreId(),response.getGoods().getStoreId()) && CollectionUtils.isEmpty(sellResponse.getGoodsList())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * @param request 当前页面的所有数据
     *                获取供应商新增某个spu下的sku
     */
    @Operation(summary = "获取新增的sku列表")
    @RequestMapping(value = "/spu/skuList", method = RequestMethod.GET)
    public BaseResponse<GoodsViewByIdResponse> getSkuList(@RequestBody GoodsModifyRequest request) {
        GoodsVO goods = request.getGoods();
        //页面已有的数据--商家的
        List<GoodsInfoVO> goodsInfos = request.getGoodsInfos();
        GoodsViewByIdRequest requestId = new GoodsViewByIdRequest();
        //根据当前商品的provideId获取供应商goodsIndo
        requestId.setGoodsId(goods.getProviderGoodsId());
        GoodsViewByIdResponse response = goodsQueryProvider.getViewById(requestId).getContext();
        List<GoodsInfoVO> goodsInfosProvider = response.getGoodsInfos();
        //商家和供应商的goodInfo数据差集
        List<GoodsInfoVO> newAddSkus = goodsInfosProvider.stream()
                .filter(provider -> !goodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId)
                        .collect(Collectors.toList()).contains(provider.getGoodsInfoId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(newAddSkus)) {
            newAddSkus.forEach(goodsInfoVO -> goodsInfoVO.setIsNewAdd(NewAdd.YES));
        }
        //返回查出的差集，会出现空值
//        response.setGoodsInfos(newAddSkus);
        return BaseResponse.success(response);
    }


    /**
     * 清空日志
     */
    @Operation(summary = "清空日志")
    @Parameter(name = "goodsId", description = "商品Id", required = true)
    @RequestMapping(value = "/log/delete/{goodsId}", method = RequestMethod.GET)
    public BaseResponse logDeleteByGoodsId(@PathVariable String goodsId) {
        operateDataLogSaveProvider.deleteByOperateId(OperateDataLogDelByOperateIdRequest.builder().operateId(goodsId).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "删除商品")
    @RequestMapping(value = "/spu", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody GoodsDeleteByIdsRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        if (1 == request.getGoodsIds().size()) {
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
            GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "删除商品",
                    "删除商品：SPU编码" + response.getGoodsNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量删除",
                    "批量删除");
        }

        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<Long> electronicCouponIds = goodsQueryProvider.findElectronicCouponIds(GoodsElectronicIdRequest.builder()
                        .goodsIds(request.getGoodsIds()).build())
                .getContext().getElectronicCouponIds();

        GoodsDeleteResponse deleteResponse = BaseResUtils.getContextFromRes(goodsProvider.deleteByIds(request));

        goodsAuditProvider.deleteByIdList(GoodsAuditDelByIdListRequest.builder().goodsIdList(request.getGoodsIds()).goodsIdType(Constants.ONE).build());
        //关联供应商商品下架
        GoodsListByIdsResponse goodsListByIdsResponse =
                goodsQueryProvider.listByProviderGoodsId(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build()).getContext();
        List<String> providerOfGoodInfoIds = null;
        if (goodsListByIdsResponse != null && CollectionUtils.isNotEmpty(goodsListByIdsResponse.getGoodsVOList())) {
            List<String> providerOfGoodsIds =
                    goodsListByIdsResponse.getGoodsVOList().stream().map(GoodsVO::getGoodsId).collect(Collectors.toList());
            GoodsInfoListByConditionResponse goodsInfoListByConditionResponse =
                    goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().goodsIds(providerOfGoodsIds).build()).getContext();
            if (goodsInfoListByConditionResponse != null && CollectionUtils.isNotEmpty(goodsInfoListByConditionResponse.getGoodsInfos())) {
                providerOfGoodInfoIds =
                        goodsInfoListByConditionResponse.getGoodsInfos().stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                //更新上下架状态
                esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                        addedFlag(AddedFlag.NO.toValue()).goodsIds(providerOfGoodsIds).
                        goodsInfoIds(providerOfGoodInfoIds).build()
                );
            }
        }
        // 更新ES
        esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(request.getGoodsIds()).build());
        // 如果商品库关联不为空，重新初始化商品库
        if(WmCollectionUtils.isNotEmpty(deleteResponse.getStandardIds())) {
            esStandardProvider.reInitEsStandardRelStoreIds(EsStandardInitRequest.builder()
                    .goodsIds(deleteResponse.getStandardIds()).build());
        }

        request.getGoodsIds().forEach(goodsId -> {
            //更新redis商品基本数据
            String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            if (StringUtils.isNotBlank(goodsDetailInfo)) {
                redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            }
        });
        if (commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
            //删除微信商品
            List<WechatSkuVO> wechatSkuVOS = wechatSkuQueryProvider.list(WechatSkuQueryRequest.builder().delFlag(DeleteFlag.NO).goodsIds(request.getGoodsIds()).build()).getContext();
            if (CollectionUtils.isNotEmpty(wechatSkuVOS)) {
                sellPlatformGoodsProvider.delGoods(new SellPlatformDeleteGoodsRequest(wechatSkuVOS.stream().map(v->v.getGoodsId()).collect(Collectors.toList())));
            }
        }
        //更新卡券绑定关系
        if (CollectionUtils.isNotEmpty(electronicCouponIds)) {
            electronicCouponProvider.updateBindingFlag(ElectronicCouponUpdateBindRequest.builder()
                    .unBindingIds(electronicCouponIds).build());
        }
        //异步通知处理
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        if(CollectionUtils.isNotEmpty(providerOfGoodInfoIds)) {
            sendRequest.setGoodsInfoIds(providerOfGoodInfoIds);
        } else {
            sendRequest.setGoodsIds(request.getGoodsIds());
        }
        sendRequest.setFlag(GoodsEditFlag.DELETE);
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 供应商删除商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "删除商品")
    @RequestMapping(value = "/spu/provider", method = RequestMethod.DELETE)
    public BaseResponse deleteProvider(@RequestBody GoodsDeleteByIdsRequest request) {
        //SUPPLIER商家不可直接调用
        if (Platform.SUPPLIER.equals(commonUtil.getOperator().getPlatform())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        List<GoodsVO> goodsVos = goodsQueryProvider
                .listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build())
                .getContext()
                .getGoodsVOList();

        if (CollectionUtils.isEmpty(goodsVos)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        List<Long> storeIds = goodsVos
                .stream()
                .map(GoodsVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());
        if (storeIds.size() != 1 || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        if (1 == request.getGoodsIds().size()) {
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
            GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "删除商品",
                    "删除商品：SPU编码" + response.getGoodsNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量删除",
                    "批量删除");
        }

        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<String> standardIds = goodsProvider.deleteProviderGoodsByIds(request).getContext().getStandardIds();

        goodsAuditProvider.deleteByIdList(GoodsAuditDelByIdListRequest.builder().goodsIdList(request.getGoodsIds()).goodsIdType(Constants.ONE).build());

        //关联供应商商品下架
        GoodsListByIdsResponse goodsListByIdsResponse =
                goodsQueryProvider.listByProviderGoodsId(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build()).getContext();
        if (goodsListByIdsResponse != null && CollectionUtils.isNotEmpty(goodsListByIdsResponse.getGoodsVOList())) {
            List<String> providerOfGoodsIds =
                    goodsListByIdsResponse.getGoodsVOList().stream().map(GoodsVO::getGoodsId).collect(Collectors.toList());
            GoodsInfoListByConditionResponse goodsInfoListByConditionResponse =
                    goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder().goodsIds(providerOfGoodsIds).build()).getContext();
            if (goodsInfoListByConditionResponse != null && CollectionUtils.isNotEmpty(goodsInfoListByConditionResponse.getGoodsInfos())) {
                List<String> providerOfGoodInfoIds =
                        goodsInfoListByConditionResponse.getGoodsInfos().stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                //更新上下架状态
                esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                        addedFlag(AddedFlag.NO.toValue()).goodsIds(providerOfGoodsIds).
                        goodsInfoIds(providerOfGoodInfoIds).build()
                );
            }
        }
        request.getGoodsIds().forEach(goodsId -> {
            //更新redis商品基本数据
            String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            if (StringUtils.isNotBlank(goodsDetailInfo)) {
                redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            }
        });
        //更新ES
        esGoodsInfoElasticProvider.deleteByGoods(EsGoodsDeleteByIdsRequest.builder().deleteIds(request.getGoodsIds()).build());
        //更新关联的商家商品es
        esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                storeId(null).providerGoodsIds(request.getGoodsIds()).build()
        );
        if (CollectionUtils.isNotEmpty(standardIds)) {
            esStandardProvider.deleteByIds(EsStandardDeleteByIdsRequest.builder().goodsIds(standardIds).build());
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量上架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量上架商品")
    @PutMapping(value = "/spu/sale")
    public BaseResponse onSale(@RequestBody GoodsModifyAddedStatusRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //如果勾选了定时上架时间
        if (Boolean.TRUE.equals(request.getAddedTimingFlag())
                && request.getAddedTimingTime() != null) {
            //定时上架可选时间不早于现在，不能晚于1年后
            if (request.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0 &&
                    request.getAddedTimingTime().compareTo(DateUtil.parseDayCanEmpty(DateUtil.afterOneYear())) < 0) {
                request.setAddedFlag(AddedFlag.NO.toValue());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else {
            request.setAddedFlag(AddedFlag.YES.toValue());
            request.setAddedTimingFlag(Boolean.FALSE);
        }

        goodsProvider.modifyAddedStatus(request);

        if (Platform.PROVIDER.equals(commonUtil.getOperator().getPlatform())) {
            esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(request.getGoodsIds()).build());
        }

        //更新ES
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsIds(request.getGoodsIds()).build());
//        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
//                addedFlag(request.getAddedFlag()).goodsIds(request.getGoodsIds()).goodsInfoIds(null).build());

        //更新积分商品ES
        managerBaseProducerService.sendMQForModifyPointsGoodsAddedFlag(request.getGoodsIds(), request.getAddedFlag());

        if (Boolean.TRUE.equals(request.getAddedTimingFlag())) {
            operateLogMQUtil.convertAndSend("商品", "批量定时上架", "批量定时上架");
        } else {
            if (1 == request.getGoodsIds().size()) {
                GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
                goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
                GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
                operateLogMQUtil.convertAndSend("商品", "上架",
                        "上架：SPU编码" + response.getGoodsNo());
            } else {
                operateLogMQUtil.convertAndSend("商品", "批量上架", "批量上架");
            }
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量下架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量下架商品")
    @RequestMapping(value = "/spu/sale", method = RequestMethod.DELETE)
    public BaseResponse offSale(@RequestBody GoodsModifyAddedStatusRequest request) {
        List<String> goodsIds = request.getGoodsIds();
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<GoodsVO> goodsVos = goodsQueryProvider
                .listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build())
                .getContext()
                .getGoodsVOList();

        if (CollectionUtils.isEmpty(goodsVos)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        List<Long> storeIds = goodsVos
                .stream()
                .map(GoodsVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());
        if (storeIds.size() != 1 || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        request.setAddedFlag(AddedFlag.NO.toValue());
        //如果下架商品是供应商商品，商家商品同步下架
        GoodsListByIdsRequest goodsListByIdsRequest = new GoodsListByIdsRequest();
        goodsListByIdsRequest.setGoodsIds(request.getGoodsIds());
        List<GoodsVO> goodsVOList = goodsQueryProvider.listByCondition(
                GoodsByConditionRequest.builder().providerGoodsIds(goodsIds).build()).getContext().getGoodsVOList();
        if (CollectionUtils.isNotEmpty(goodsVOList)) {
            goodsVOList.forEach(s -> {
                goodsIds.add(s.getGoodsId());
            });
        }
        request.setGoodsIds(goodsIds);

        goodsProvider.modifyAddedStatus(request);

        if (Platform.PROVIDER.equals(commonUtil.getOperator().getPlatform())) {
            esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(request.getGoodsIds()).build());
        }

        request.getGoodsIds().forEach(goodsId -> {
            //更新redis商品基本数据
            String goodsDetailInfo = redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            if (StringUtils.isNotBlank(goodsDetailInfo)) {
                redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId);
            }
        });

        //更新积分商品ES
        managerBaseProducerService.sendMQForModifyPointsGoodsAddedFlag(request.getGoodsIds(), AddedFlag.NO.toValue());

        if (1 == request.getGoodsIds().size()) {
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
            GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "下架",
                    "下架：SPU编码" + response.getGoodsNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量下架", "批量下架");
        }
        //更新ES
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(AddedFlag.NO.toValue()).goodsIds(request.getGoodsIds()).goodsInfoIds(null).build());
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 批量上/下架商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "批量上/下架商品")
    @RequestMapping(value = "/spu/sale", method = RequestMethod.POST)
    public BaseResponse onOrOffSale(@RequestBody GoodsModifyAddedStatusRequest request){
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<GoodsVO> goodsVos = goodsQueryProvider
                .listByIds(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIds()).build())
                .getContext()
                .getGoodsVOList();

        if (CollectionUtils.isEmpty(goodsVos)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        List<Long> storeIds = goodsVos
                .stream()
                .map(GoodsVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());
        if (storeIds.size() != 1 || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        //定时上架和定时下架
        if (Boolean.TRUE.equals(request.getAddedTimingFlag()) || Boolean.TRUE.equals(request.getTakedownTimeFlag())){
            //上下架时间校验
            goodsBaseService.checkAddAndTakedownTime(request.getTakedownTime(),request.getTakedownTimeFlag(),
                    request.getAddedTimingTime(),request.getAddedTimingFlag());

            String opContext = "";
            //如果只选择了定时上架，商品先下架-再定时上架
            if (!Boolean.TRUE.equals(request.getTakedownTimeFlag())){
                request.setAddedFlag(AddedFlag.NO.toValue());
                opContext = "上架";
            }
            //如果只选择了定时下架，商品先上架-再定时下架
            if (!Boolean.TRUE.equals(request.getAddedTimingFlag())){
                request.setAddedFlag(AddedFlag.YES.toValue());
                opContext = "下架";
            }
            //即选择了定时上架也选择了定时下架
            if (Boolean.TRUE.equals(request.getTakedownTimeFlag()) && Boolean.TRUE.equals(request.getAddedTimingFlag())){
                //商品此时上下架状态取决于谁的时间靠前
                if (request.getAddedTimingTime().isBefore(request.getTakedownTime())){
                    request.setAddedFlag(AddedFlag.NO.toValue());
                }else {
                    request.setAddedFlag(AddedFlag.YES.toValue());
                }
                opContext = "上/下架";
            }

            goodsProvider.modifyAddedStatus(request);
            //更新ES
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsIds(request.getGoodsIds()).build());

            operateLogMQUtil.convertAndSend("商品", "批量定时上/下架", opContext);

            return BaseResponse.SUCCESSFUL();
        }else {
            //上架
            if ((request.getAddedFlag() != null && request.getAddedFlag() == AddedFlag.YES.toValue())){
                return this.onSale(request);
            }

            //下架
            if (request.getAddedFlag() != null && request.getAddedFlag() == AddedFlag.NO.toValue()){
                return this.offSale(request);
            }
        }

        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 批量编辑运费模板
     */
    @MultiSubmit
    @Operation(summary = "批量编辑运费模板")
    @RequestMapping(value = "/spu/freight", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse setFeight(@RequestBody GoodsModifyFreightTempRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (Objects.isNull(request.getFreightTempId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 批量设置运费模板包含虚拟商品，则提示：虚拟商品不可设置运费模板
        List<GoodsVO> goodsVOList = goodsQueryProvider.listByIds(GoodsListByIdsRequest.builder()
                .goodsIds(request.getGoodsIds()).build()).getContext().getGoodsVOList();
        if (goodsVOList.stream().anyMatch(goodsVO -> goodsVO.getGoodsType() > NumberUtils.INTEGER_ZERO)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030048);
        }

        Long fId = request.getFreightTempId();

        //判断是否开启二次审核开关
        //如果开启走二次审核逻辑  如果关闭就直接修改freightid

        //判断是供应商还是商家
        StoreType storeType = commonUtil.getStoreType();
        List<String> sellGoodsInfo = new ArrayList<>();

        //默认查询是商家开关 兼容o2o门店和跨境商家 如果有单独设置开关 这块需要改动
        ConfigType configType = ConfigType.SUPPLIER_GOODS_SECONDARY_AUDIT;
        if(Objects.nonNull(storeType) && storeType.equals(StoreType.PROVIDER)){
            configType = ConfigType.PROVIDER_GOODS_SECONDARY_AUDIT;
            // 查询关联的商品Id TODO
            GoodsListByIdsResponse sellGoodsLest =
                    goodsQueryProvider
                            .listByProviderGoodsId(
                                    GoodsListByIdsRequest.builder()
                                            .goodsIds(request.getGoodsIds())
                                            .build())
                            .getContext();
            if (Objects.nonNull(sellGoodsLest)
                    && CollectionUtils.isNotEmpty(sellGoodsLest.getGoodsVOList())) {
                sellGoodsInfo =
                        sellGoodsLest.getGoodsVOList().stream()
                                .map(GoodsVO::getGoodsId)
                                .collect(Collectors.toList());
            }
        }

        //判断运费模板是否存在
        freightTemplateGoodsQueryProvider.existsById(
                FreightTemplateGoodsExistsByIdRequest.builder().freightTempId(fId).build());
        if(CollectionUtils.isNotEmpty(sellGoodsInfo)) {
            request.getGoodsIds().addAll(sellGoodsInfo);
        }
        goodsProvider.modifyFreightTemp(request);

        //更新商品ES
        esGoodsInfoElasticProvider.modifyFreightTemplateId(
                EsFreightTemplateRequest.builder().freightTemplateId(request.getFreightTempId()).goodsIdList(request.getGoodsIds()).build());

        FreightTemplateGoodsByIdResponse templateGoods = freightTemplateGoodsQueryProvider.getById(
                FreightTemplateGoodsByIdRequest.builder().freightTempId(fId).build()).getContext();
        if (1 == request.getGoodsIds().size()) {
            GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
            goodsByIdRequest.setGoodsId(request.getGoodsIds().get(0));
            GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "更换运费模板",
                    "更换运费模板：" + response.getGoodsName()
                            + " 模板名称改为" + templateGoods.getFreightTempName());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量更换运费模板",
                    "批量更换运费模板：模板名称改为" + templateGoods.getFreightTempName());
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改注水销量
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "修改注水销量")
    @RequestMapping(value = "/spu/sham", method = RequestMethod.PUT)
    public BaseResponse modifyShamSalesNum(@Valid @RequestBody GoodsModifyShamSalesNumRequest request) {
        if (request.getShamSalesNum() < 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
        goodsByIdRequest.setGoodsId(request.getGoodsId());
        GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
        if (response == null || StringUtils.isEmpty(response.getGoodsId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsProvider.modifyShamSalesNum(request);

        Long newSalesNum =
                (Objects.isNull(response.getGoodsSalesNum()) ? 0 : response.getGoodsSalesNum()) + request.getShamSalesNum();
        //更新ES
        esGoodsInfoElasticProvider.updateSalesNumBySpuId(EsGoodsModifySalesNumBySpuIdRequest.builder().
                spuId(request.getGoodsId()).salesNum(newSalesNum).build()
        );
        operateLogMQUtil.convertAndSend("商品", "修改注水销量",
                "修改注水销量：SPU编码" + response.getGoodsNo());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改排序号
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "修改排序号")
    @RequestMapping(value = "/spu/sort", method = RequestMethod.PUT)
    public BaseResponse modifySortNo(@Valid @RequestBody GoodsModifySortNoRequest request) {
        if (request.getSortNo() < 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsByIdRequest goodsByIdRequest = new GoodsByIdRequest();
        goodsByIdRequest.setGoodsId(request.getGoodsId());
        GoodsByIdResponse response = goodsQueryProvider.getById(goodsByIdRequest).getContext();
        if (response == null || StringUtils.isEmpty(response.getGoodsId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        goodsProvider.modifySortNo(request);
        esGoodsInfoElasticProvider.updateSortNoBySpuId(EsGoodsModifySortNoBySpuIdRequest.builder().
                spuId(request.getGoodsId()).sortNo(request.getSortNo()).build()
        );
        operateLogMQUtil.convertAndSend("商品", "修改排序号",
                "修改排序号：SPU编码" + response.getGoodsNo());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 上传文件(原来实体商品接口)
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(companyInfoId).build()
        ).getContext();
        if (companyInfo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsSupplierExcelImportRequest importRequest = new GoodsSupplierExcelImportRequest();
        importRequest.setUserId(commonUtil.getOperatorId());
        importRequest.setCompanyInfoId(companyInfoId);
        importRequest.setStoreId(commonUtil.getStoreId());
        importRequest.setCompanyType(companyInfo.getCompanyType());
        importRequest.setSupplierName(companyInfo.getSupplierName());
        importRequest.setType(StoreType.PROVIDER);
        //加入导入商品的类型
        importRequest.setGoodsType(NumberUtils.INTEGER_ZERO);
        return BaseResponse.success(goodsExcelService.upload(uploadFile, importRequest));
    }

    /**
     * 上传文件（包含虚拟商品导入）
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/excel/upload/{goodsType}", method = RequestMethod.POST)
    public BaseResponse<String> uploadForGoodsType(@PathVariable Integer goodsType,@RequestParam("uploadFile") MultipartFile uploadFile) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        CompanyInfoVO companyInfo = companyInfoQueryProvider.getCompanyInfoById(
                CompanyInfoByIdRequest.builder().companyInfoId(companyInfoId).build()
        ).getContext();
        if (companyInfo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GoodsSupplierExcelImportRequest importRequest = new GoodsSupplierExcelImportRequest();
        importRequest.setUserId(commonUtil.getOperatorId());
        importRequest.setCompanyInfoId(companyInfoId);
        importRequest.setStoreId(commonUtil.getStoreId());
        importRequest.setCompanyType(companyInfo.getCompanyType());
        importRequest.setSupplierName(companyInfo.getSupplierName());
        importRequest.setType(StoreType.SUPPLIER);
        //加入导入商品的类型
        importRequest.setGoodsType(goodsType);
        return BaseResponse.success(goodsExcelService.upload(uploadFile, importRequest));
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @Parameters({
            @Parameter(
                    name = "ext", description = "后缀", required = true),
            @Parameter(
                    name = "decrypted", description = "解密", required = true)
    })

    @RequestMapping(value = "/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        goodsExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }

    /**
     * 商品推广
     *
     * @param request
     * @return
     */
    @Operation(summary = "商品推广")
    @PostMapping(value = "/extend")
    public BaseResponse<GoodsInfoExtendByIdResponse> findGoodsInfoExtend(@RequestBody GoodsInfoExtendByIdRequest request) {
        GoodsInfoVO goodsInfo = this.getGoodsInfo(request.getGoodsId());
        request.setGoodsInfoId(goodsInfo.getGoodsInfoId());
        request.setStoreId(goodsInfo.getStoreId());
        request.setPluginType(goodsInfo.getPluginType());
        GoodsInfoExtendDeleteByIdRequest deleteByIdRequest = GoodsInfoExtendDeleteByIdRequest.builder()
                .goodsId(request.getGoodsId())
                .goodsInfoId(goodsInfo.getGoodsInfoId())
                .build();
        // 若市场价有变动，则删除原有商品推广信息
        goodsInfoExtendProvider.deleteGoodsInfoExtend(deleteByIdRequest);
        GoodsInfoExtendByIdResponse response = goodsInfoExtendQueryProvider.findById(request).getContext();
        if (!Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
            if (!Objects.equals(commonUtil.getStoreId(), response.getGoodsInfoExtend().getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * 商品推广
     *
     * @param request
     * @return
     */
    @Operation(summary = "添加推广渠道")
    @PostMapping(value = "/modify-extend")
    public BaseResponse modifyGoodsInfoExtend(@Valid @RequestBody GoodsInfoExtendModifyRequest request) {
        // 判断为空，抛出异常
        //自动话需要判空，但是影响推广展示，为空的数据需要继续走下面业务
        /*List<String> sources = request.getSources();
        if (CollectionUtils.isEmpty(sources)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        } else {
            // 判断各个元素
            for (String source : sources) {
                if (StringUtils.isEmpty(source)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }*/
        return goodsInfoExtendProvider.modifyGoodsInfoExtend(request);
    }

    /**
     * 获取skuid
     *
     * @param goodsId
     * @return
     */
    @Operation(summary = "获取skuId")
    @GetMapping(value = "/findGoodsInfoId/{goodsId}")
    public BaseResponse<String> findGoodsInfoId(@PathVariable("goodsId") String goodsId) {
        GoodsInfoVO goodsInfo = this.getGoodsInfo(goodsId);
        return BaseResponse.success(goodsInfo.getGoodsInfoId());
    }


    /**
     * 获取商品信息
     *
     * @param goodsId
     * @return
     */
    private GoodsInfoVO getGoodsInfo(String goodsId) {
        DistributionGoodsChangeRequest changeRequest = DistributionGoodsChangeRequest.builder()
                .goodsId(goodsId)
                .build();
        GoodsInfoByGoodsIdresponse goodsInfoByGoodsIdresponse =
                goodsInfoQueryProvider.getByGoodsId(changeRequest).getContext();
        List<GoodsInfoVO> goodsInfoVOList = goodsInfoByGoodsIdresponse.getGoodsInfoVOList();
        if (CollectionUtils.isEmpty(goodsInfoVOList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        //判断店铺是否关闭
        StoreVO storeInfo = this.getStoreInfo(goodsInfoVOList);
        if (Objects.equals(storeInfo.getStoreState(), StoreState.CLOSED)) {
            return goodsInfoVOList.get(NumberUtils.INTEGER_ZERO);
        }
        LocalDateTime contractEndDate = storeInfo.getContractEndDate();
        if (Objects.isNull(contractEndDate) || contractEndDate.isBefore(LocalDateTime.now())) {
            return goodsInfoVOList.get(NumberUtils.INTEGER_ZERO);
        }
        //判断是否有效
        List<GoodsInfoVO> validGoodsInfoList = goodsInfoVOList.stream()
                .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getAuditStatus(), CheckStatus.CHECKED))
                .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getAddedFlag(), NumberUtils.INTEGER_ONE))
                .filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getDelFlag(), DeleteFlag.NO))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(validGoodsInfoList)) {
            return goodsInfoVOList.get(NumberUtils.INTEGER_ZERO);
        }
        return Collections.min(validGoodsInfoList, Comparator.comparing(GoodsInfoVO::getMarketPrice));
    }


    /**
     * 获取店铺信息
     *
     * @param goodsInfoVOList
     * @return
     */
    private StoreVO getStoreInfo(List<GoodsInfoVO> goodsInfoVOList) {
        StoreByIdRequest request = StoreByIdRequest.builder()
                .storeId(goodsInfoVOList.get(0).getStoreId())
                .build();
        return storeQueryProvider.getById(request).getContext().getStoreVO();
    }

    @Operation(summary = "查看属性详情")
    @GetMapping(value = "/prop/detail/{goodId}/{goodsType}")
    public BaseResponse<GoodsPropertyDetailGoodsIdResponse> getGoodsProperty(@PathVariable("goodId") String goodId, @PathVariable("goodsType") Long goodsType) {
        GoodsPropertyDetailGoodsIdRequest request = GoodsPropertyDetailGoodsIdRequest.builder()
                .goodsId(goodId)
                .goodsType(goodsType)
                .storeId(commonUtil.getStoreId())
                .build();
        return goodsPropertyDetailRelQueryProvider.getGoodsProperty(request);
    }

    /**
     * 导出商品
     *
     * @return
     */
    @Operation(summary = "导出商品")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        Operator operator = commonUtil.getOperator();
        log.debug("/goods/export/params, employeeId={}", operator.getUserId());

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_GOODS);
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 京东vop批量导出商品
     *
     * @return
     */
    @Operation(summary = "京东vop批量导出商品")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/vop/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse vopGoodsBatchExport(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType. BUSINESS_VOP_GOODS);
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * LinkedMall商品批量导出
     */
    @Operation(summary = "LinkedMall批量导出商品")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/lm/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse lmGoodsBatchExport(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_LM_GOODS);
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 供货商商品批量导出
     */
    @Operation(summary = "LinkedMall批量导出商品")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/provider/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse providerGoodsBatchExport(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_PROVIDER_GOODS);
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取审核商品详情信息
     *
     * @param goodsId 商品编号
     * @return 商品详情
     */
    @Operation(summary = "获取审核商品详情信息")
    @Parameter(name = "goodsId", description = "商品Id", required = true)
    @RequestMapping(value = "/audit/spu/{goodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsAuditViewByIdResponse> auditInfo(@PathVariable String goodsId) {
        //控制显示是否是编辑供应商商品
        Boolean isEditProviderGoods = false;
        GoodsAuditByIdResponse auditByIdResponse = goodsAuditQueryProvider.getById(GoodsAuditByIdRequest.builder().goodsId(goodsId).build()).getContext();
        if (Objects.isNull(auditByIdResponse.getGoodsAuditVO())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
            if (!Objects.equals(commonUtil.getStoreId(),auditByIdResponse.getGoodsAuditVO().getStoreId())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }
        GoodsAuditVO vo = auditByIdResponse.getGoodsAuditVO();
        if (commonUtil.getOperator().getPlatform().equals(Platform.SUPPLIER)
                && Objects.nonNull(vo.getProviderGoodsId())) {
            isEditProviderGoods = true;
        }
        GoodsAuditViewByIdRequest request = new GoodsAuditViewByIdRequest();
        request.setGoodsId(goodsId);
        request.setShowLabelFlag(Boolean.TRUE);
        request.setIsEditProviderGoods(isEditProviderGoods);
        GoodsAuditViewByIdResponse response = goodsAuditQueryProvider.getViewById(request).getContext();

        //商家端代销功能展示
        if (commonUtil.getOperator().getPlatform().equals(Platform.SUPPLIER)
                && Objects.nonNull(response.getGoodsAudit().getProviderGoodsId())) {
            //商家当前的list
            List<GoodsInfoVO> goodsInfos = response.getGoodsInfos();
            GoodsViewByIdRequest providertId = new GoodsViewByIdRequest();
            //根据当前商品的provideId获取供应商goodsIndo
            providertId.setGoodsId(response.getGoodsAudit().getProviderGoodsId());
            providertId.setIsEditProviderGoods(isEditProviderGoods);
            GoodsViewByIdResponse providerResponse = goodsQueryProvider.getViewById(providertId).getContext();
            List<GoodsInfoVO> goodsInfosProvider = providerResponse.getGoodsInfos();

            //1.需要删除的list 这条信息在两边都是delflag=1
            List<GoodsInfoVO> collectDel = goodsInfos.stream().filter(goodsInfoVO -> goodsInfosProvider.stream()
                            .filter(goodsInfosProviderVO -> goodsInfosProviderVO.getDelFlag().toValue() == 1)
                            .map(GoodsInfoVO::getGoodsInfoId)
                            .collect(Collectors.toList()).contains(goodsInfoVO.getProviderGoodsInfoId()))
                    .filter(goodsInfoVO -> goodsInfoVO.getDelFlag().toValue() == 1)
                    .collect(Collectors.toList());

            //2. 商家删除供应商已经删除且商家不代销的
            goodsInfos.removeAll(collectDel);

            //2.去掉剩余的仅供应商有且删除的
            List<GoodsInfoVO> goodInfoNoDel = goodsInfosProvider.stream().filter(goodsInfoVO -> goodsInfoVO.getDelFlag().toValue() == 0)
                    .collect(Collectors.toList());

            //商家goodsInfos 和供应商 goodInfoNoDel数据差集  即展示0 0， 0，1，0 null的数据
            List<GoodsInfoVO> newAddSkus = goodInfoNoDel.stream()
                    .filter(provider -> !goodsInfos.stream().map(GoodsInfoVO::getProviderGoodsInfoId)
                            .collect(Collectors.toList()).contains(provider.getGoodsInfoId()))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(newAddSkus)) {
                newAddSkus.forEach(goodsInfoVO -> {
                    goodsInfoVO.setIsNewAdd(NewAdd.YES);
                    //将供应商的新sku状态改为不代销，可在页面显示
                    goodsInfoVO.setDelFlag(DeleteFlag.YES);
                });
            }
            //商家和供应商商品list合并
            goodsInfos.addAll(newAddSkus);

            //获取sku的加价比例
            GoodsCommissionPriceConfigQueryRequest goodsCommissionPriceConfigQueryRequest = new GoodsCommissionPriceConfigQueryRequest();
            goodsCommissionPriceConfigQueryRequest.setTargetIdList(goodsInfos.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList()));
            goodsCommissionPriceConfigQueryRequest.setTargetType(CommissionPriceTargetType.SKU);
            goodsCommissionPriceConfigQueryRequest.setUserId(commonUtil.getOperatorId());
            goodsCommissionPriceConfigQueryRequest.setBaseStoreId(commonUtil.getStoreId());
            GoodsCommissionPriceConfigQueryResponse context = goodsCommissionPriceConfigProvider.query(goodsCommissionPriceConfigQueryRequest).getContext();
            if (CollectionUtils.isNotEmpty(context.getCommissionPriceConfigVOList())) {
                Map<String, GoodsCommissionPriceConfigVO> priceConfigMap = context.getCommissionPriceConfigVOList().stream().collect(Collectors.toMap(GoodsCommissionPriceConfigVO::getTargetId, g -> g));
                goodsInfos.forEach(goodsInfoVO -> {
                    if (priceConfigMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                        goodsInfoVO.setAddRate(priceConfigMap.get(goodsInfoVO.getGoodsInfoId()).getAddRate());
                    }
                });
            }
            response.setGoodsInfos(goodsInfos);
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
            }
        }
        OperateDataLogListResponse operateDataLogListResponse =
                operateDataLogQueryProvider.list(OperateDataLogListRequest.builder().operateId(goodsId).delFlag(DeleteFlag.NO).build()).getContext();
        List<OperateDataLogVO> operateDataLogList = new ArrayList<OperateDataLogVO>();
        if (operateDataLogListResponse != null && CollectionUtils.isNotEmpty(operateDataLogListResponse.getOperateDataLogVOList())) {
            operateDataLogList = operateDataLogListResponse.getOperateDataLogVOList();
        }
        response.setOperateDataLogVOList(operateDataLogList);

        // 解析电子卡券商品关联的卡券名称
        List<Long> electronicCouponsIds = response.getGoodsInfos().stream().map(GoodsInfoVO::getElectronicCouponsId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(electronicCouponsIds)){
            // 根据卡券ID集合查询卡券列表
            List<ElectronicCouponVO> electronicCouponVOList = electronicCouponQueryProvider.list(ElectronicCouponListRequest.builder().idList(electronicCouponsIds).build()).getContext().getElectronicCouponVOList();
            if (CollectionUtils.isNotEmpty(electronicCouponVOList)){
                Map<Long, String> electricCouponMap = electronicCouponVOList.stream()
                        .collect(Collectors.toMap(ElectronicCouponVO::getId, ElectronicCouponVO::getCouponName));
                response.getGoodsInfos().forEach(v -> v.setElectronicCouponsName(electricCouponMap.get(v.getElectronicCouponsId())));
            }
        }
        return BaseResponse.success(response);
    }

    /**
     * 编辑审核商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "编辑审核商品")
    @RequestMapping(value = "/audit/spu", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody @Valid GoodsAuditModifyRequest request) {
        log.info("商品编辑开始supplier");
        long startTime = System.currentTimeMillis();
        if (request.getGoodsAudit() == null || CollectionUtils.isEmpty(request.getGoodsInfos()) || request
                .getGoodsAudit().getGoodsId() == null || Objects.isNull(request.getGoodsAudit().getFreightTempId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        Long fId = request.getGoodsAudit().getFreightTempId();
        request.setBaseStoreId(commonUtil.getStoreId());

        // 添加默认值, 适应云掌柜编辑商品没有设置购买方式, 导致前台不展示购买方式问题
        if (StringUtils.isBlank(request.getGoodsAudit().getGoodsBuyTypes())) {
            request.getGoodsAudit().setGoodsBuyTypes(GOODS_BUY_TYPES);
        }

        GoodsAuditViewByIdRequest goodsAuditViewByIdRequest = new GoodsAuditViewByIdRequest();
        goodsAuditViewByIdRequest.setGoodsId(request.getGoodsAudit().getGoodsId());
        GoodsAuditViewByIdResponse oldData = goodsAuditQueryProvider.getViewById(goodsAuditViewByIdRequest).getContext();

        //判断运费模板是否存在
        freightTemplateGoodsQueryProvider.existsById(
                FreightTemplateGoodsExistsByIdRequest.builder().freightTempId(fId).build());

        //获取商品店铺分类
        if (osUtil.isS2b()) {
            StoreCateListByGoodsRequest storeCateListByGoodsRequest =
                    new StoreCateListByGoodsRequest(Collections.singletonList(request.getGoodsAudit().getGoodsId()));
            BaseResponse<StoreCateListByGoodsResponse> baseResponse =
                    storeCateQueryProvider.listByGoods(storeCateListByGoodsRequest);
            StoreCateListByGoodsResponse storeCateListByGoodsResponse = baseResponse.getContext();
            if (Objects.nonNull(storeCateListByGoodsResponse)) {
                List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOList =
                        storeCateListByGoodsResponse.getStoreCateGoodsRelaVOList();
                oldData.getGoodsAudit().setStoreCateIds(storeCateGoodsRelaVOList.stream()
                        .filter(rela -> rela.getStoreCateId() != null)
                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                        .collect(Collectors.toList()));
            }
        }

        GoodsAuditModifyResponse response = goodsAuditProvider.modify(request).getContext();
        if(Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getCheckRequest().getGoodsIds())){
            goodsBaseService.check(response.getCheckRequest());
        }

        //ares埋点-商品-后台修改商品sku,迁移至goods微服务下
        operateLogMQUtil.convertAndSend("商品", "编辑商品",
                "编辑商品：SPU编码" + request.getGoodsAudit().getGoodsNo());
        log.info("商品编辑结束supplier->花费{}毫秒", (System.currentTimeMillis() - startTime));

        // ============= 处理平台的消息发送：待审核列表，商家/供应商编辑商品触发待审核 START =============
        storeMessageBizService.handleForEditAuditGoods(request, response);
        // ============= 处理平台的消息发送：待审核列表，商家/供应商编辑商品触发待审核 END =============

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除商品
     */
    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "删除审核商品")
    @RequestMapping(value = "/audit/spu", method = RequestMethod.DELETE)
    public BaseResponse auditDelete(@RequestBody GoodsDeleteByIdsRequest request) {
        List<GoodsAuditVO> goodsAuditVOList = goodsAuditQueryProvider
                .getByIds(GoodsAuditQueryRequest.builder().goodsIdList(request.getGoodsIds()).build())
                .getContext()
                .getGoodsAuditVOList();
        if(CollectionUtils.isEmpty(goodsAuditVOList)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        boolean anyMatch = goodsAuditVOList.stream()
                .map(GoodsAuditVO::getAuditStatus)
                .anyMatch(goodsAudit -> Objects.equals(goodsAudit, CheckStatus.WAIT_CHECK));
        //待审核数据不允许删除
        if(anyMatch){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030152);
        }
        request.setStoreId(commonUtil.getStoreId());
        if (1 == request.getGoodsIds().size()) {
            GoodsAuditByIdRequest goodsAuditByIdRequest = new GoodsAuditByIdRequest();
            goodsAuditByIdRequest.setGoodsId(request.getGoodsIds().get(0));
            GoodsAuditByIdResponse response = goodsAuditQueryProvider.getById(goodsAuditByIdRequest).getContext();
            operateLogMQUtil.convertAndSend("商品", "删除审核商品",
                    "删除商品：SPU编码" + response.getGoodsAuditVO().getGoodsNo());
        } else {
            operateLogMQUtil.convertAndSend("商品", "批量删除审核商品",
                    "批量删除");
        }
        List<String> goodsIds = goodsAuditVOList
                .stream()
                .filter(v->Objects.equals(CheckStatus.FORBADE,v.getAuditStatus()))
                .map(GoodsAuditVO::getOldGoodsId)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsIds)){
            GoodsDeleteByIdsRequest goodsDeleteByIdsRequest = new GoodsDeleteByIdsRequest();
            goodsDeleteByIdsRequest.setGoodsIds(goodsIds);
            delete(goodsDeleteByIdsRequest);
        }
        goodsAuditProvider.deleteByIdList(GoodsAuditDelByIdListRequest.builder().goodsIdList(request.getGoodsIds()).goodsIdType(Constants.ZERO).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据卡券id统计有效卡密API(卡密库存)
     * @return
     */
    @GetMapping("/countEffectiveCoupon/{couponId}")
    @Operation(summary = "根据卡券id统计有效卡密API(卡密库存)")
    public BaseResponse<ElectronicCardModifyCountResponse> countEffectiveCoupon(@PathVariable Long couponId) {
        LocalDateTime time = LocalDate.now().atTime(LocalDateTime.now().getHour(), 0, 0);
        ElectronicCardInvalidRequest request = ElectronicCardInvalidRequest.builder()
                .couponId(couponId)
                .time(time)
                .build();
        return electronicCardQueryProvider.countEffectiveCoupon(request);
    }
}
