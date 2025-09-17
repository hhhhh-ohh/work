package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsSelectOptionsResponse;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsInfoDTO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.MiniProgramQrCodeRequest;
import com.wanmi.sbc.empower.api.request.wechatauth.ShareMiniProgramRequest;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.provider.xsitegoodscate.XsiteGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoListByCustomerIdAndGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoSmallProgramCodeRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateQueryByCateUuidRequest;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoListByCustomerIdAndGoodsIdResponse;
import com.wanmi.sbc.goods.api.response.goods.*;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSalePurchaseResponse;
import com.wanmi.sbc.goods.api.response.groupongoodsinfo.GrouponGoodsByGrouponActivityIdAndGoodsInfoIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSimpleDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByGoodsAndSkuResponse;
import com.wanmi.sbc.goods.api.response.xsitegoodscate.XsiteGoodsCateByCateUuidResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.request.GrouponGoodsViewByIdResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.detail.GoodsDetailInterface;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleAndBookingSaleRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityingByGoodsInfoIdsRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleAndBookingSaleResponse;
import com.wanmi.sbc.marketing.bean.enums.GrouponDetailOptStatus;
import com.wanmi.sbc.marketing.bean.enums.GrouponDetailOptType;
import com.wanmi.sbc.order.api.provider.groupon.GrouponProvider;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseProvider;
import com.wanmi.sbc.order.api.request.groupon.GrouponDetailQueryRequest;
import com.wanmi.sbc.order.api.request.groupon.GrouponDetailWithGoodsRequest;
import com.wanmi.sbc.order.api.response.groupon.GrouponDetailQueryResponse;
import com.wanmi.sbc.order.api.response.groupon.GrouponDetailWithGoodsResponse;
import com.wanmi.sbc.order.bean.vo.GrouponDetailVO;
import com.wanmi.sbc.order.bean.vo.GrouponDetailWithGoodsVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.BossGoodsOutOfStockShowResponse;
import com.wanmi.sbc.setting.bean.vo.EsGoodsBoostSettingVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.third.goods.ThirdPlatformGoodsService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;
import com.wanmi.sbc.util.LaKaLaUtils;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;


import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 商品Controller Created by daiyitian on 17/4/12. */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name = "GoodsBaseController", description = "S2B web公用-商品信息API")
public class GoodsBaseController {

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private MarketingPluginProvider marketingPluginProvider;

    @Autowired private MarketingQueryProvider marketingQueryProvider;

    @Autowired private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired private PurchaseProvider purchaseProvider;

    @Autowired private CommonUtil commonUtil;

    @Autowired private OsUtil osUtil;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired private WechatAuthProvider wechatAuthProvider;

    @Autowired private GoodsInfoProvider goodsInfoProvider;

    @Autowired private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Autowired private DistributionService distributionService;

    @Autowired private DistributionCacheService distributionCacheService;

    @Autowired private GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Autowired private GrouponProvider grouponProvider;

    @Autowired private SystemPointsConfigService systemPointsConfigService;

    @Autowired private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired private GoodsBaseService goodsBaseService;

    @Autowired private ThirdPlatformGoodsService thirdPlatformGoodsService;

    @Autowired private RedisUtil redisService;

    @Autowired private GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Resource private AuditQueryProvider auditQueryProvider;

    @Resource(name = "goodsListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest,EsGoodsInfoSimpleResponse> goodsListInterface;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private XsiteGoodsCateProvider xsiteGoodsCateProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Resource(name = "goodsInfoDetailService")
    private GoodsDetailInterface<GoodsInfoSimpleDetailByGoodsInfoResponse> goodsDetailInterface;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LaKaLaUtils laKaLaUtils;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //商品不存在
    private static final String GOODSNOTEXIST = "商品不存在";


    /**
     * 商品分页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "商品分页")
    @RequestMapping(value = "/spuList", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> spuList(
            @RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 分类uuid不为空，组装spuIds
        if (StringUtils.isNotBlank(queryRequest.getCateUuid())) {
            XsiteGoodsCateByCateUuidResponse xsiteGoodsCateByCateUuidResponse =
                    xsiteGoodsCateProvider.findByCateUuid(XsiteGoodsCateQueryByCateUuidRequest.builder().cateUuid(queryRequest.getCateUuid()).build()).getContext();
            if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                String goodsIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsIds();
                if (StringUtils.isNotBlank(goodsIds)){
                    List<String> goodsIdList = JSON.parseArray(goodsIds, String.class);
                    queryRequest.setGoodsIds(goodsIdList);
                }
            }else {
                return BaseResponse.success(new GoodsInfoListResponse());
            }
        }
        goodsBaseService.getEsGoodsBoost(queryRequest);
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        GoodsInfoListResponse goodsInfoListResponse = goodsBaseService.skuListConvert(goodsListInterface.getList(queryRequest), queryRequest.getSortFlag());
        setGoodsMainImage(goodsInfoListResponse);
        return BaseResponse.success(goodsInfoListResponse);
    }

    /**
     * 未登录时,查询商品分页(ES级)
     *
     * @param queryRequest 搜索条件
     * @return 返回分页结果
     */
    @Operation(summary = "未登录时,查询商品分页")
    @RequestMapping(value = "/unLogin/spuList", method = RequestMethod.POST)
    public BaseResponse<GoodsInfoListResponse> unLoginSkuList(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 分类uuid不为空，组装spuIds
        if (StringUtils.isNotBlank(queryRequest.getCateUuid())) {
            XsiteGoodsCateByCateUuidResponse xsiteGoodsCateByCateUuidResponse =
                    xsiteGoodsCateProvider.findByCateUuid(XsiteGoodsCateQueryByCateUuidRequest.builder().cateUuid(queryRequest.getCateUuid()).build()).getContext();
            if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                String goodsIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsIds();
                if (StringUtils.isNotBlank(goodsIds)){
                    List<String> goodsIdList = JSON.parseArray(goodsIds, String.class);
                    queryRequest.setGoodsIds(goodsIdList);
                }
            }
        }
        goodsBaseService.getEsGoodsBoost(queryRequest);
        EsGoodsInfoSimpleResponse response = goodsListInterface.getList(queryRequest);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(queryRequest.getEsGoodsInfoDTOList()) &&
                org.apache.commons.collections.CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())) {
            Map<String, List<EsGoodsInfoDTO>> buyCountMap =
                    queryRequest.getEsGoodsInfoDTOList().stream()
                            .collect(Collectors.groupingBy(EsGoodsInfoDTO::getGoodsInfoId));

            response.getEsGoodsInfoPage().getContent().stream()
                    .filter(esGoodsInfo -> Objects.nonNull(esGoodsInfo.getGoodsInfo())
                            && buyCountMap.containsKey(esGoodsInfo.getGoodsInfo().getGoodsInfoId()))
                    .forEach(esGoodsInfo -> {
                        GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
                        goodsInfo.setBuyCount(
                                buyCountMap.get(esGoodsInfo.getGoodsInfo().getGoodsInfoId()).get(0).getGoodsNum());
                    });
        }
        GoodsInfoListResponse goodsInfoListResponse = goodsBaseService.skuListConvert(goodsListInterface.getList(queryRequest), queryRequest.getSortFlag());
        setGoodsMainImage(goodsInfoListResponse);
        return BaseResponse.success(goodsInfoListResponse);
    }



    /**
     * Spu商品详情，放开删除的sku商品和下架的sku商品，前端判断多规格自动切换
     *
     * @return 返回分页结果
     */
    @Operation(summary = "购物车弹框查询Spu商品详情")
    @Parameter(
            name = "skuId",
            description = "skuId",
            required = true)
    @RequestMapping(value = "/spu/shopcart/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsViewByIdResponse> detailForShopCart(@PathVariable String skuId) {
        return BaseResponse.success(
                goodsBaseService.detail(skuId, commonUtil.getOperatorId(), Boolean.FALSE));
    }

    /**
     * 积分Spu商品详情
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询积分Spu商品详情")
    @Parameter(
            name = "pointsGoodsId",
            description = "pointsGoodsId",
            required = true)
    @RequestMapping(value = "/points/spu/{pointsGoodsId}", method = RequestMethod.GET)
    public BaseResponse<GoodsViewByPointsGoodsIdResponse> pointsGoodsDetail(
            @PathVariable String pointsGoodsId) {
        GoodsViewByPointsGoodsIdRequest request = new GoodsViewByPointsGoodsIdRequest();
        request.setPointsGoodsId(pointsGoodsId);
        request.setShowLabelFlag(Boolean.TRUE);
        request.setShowSiteLabelFlag(Boolean.TRUE);
        return BaseResponse.success(
                goodsQueryProvider.getViewByPointsGoodsId(request).getContext());
    }

    /**
     * 查询商品图文信息和属性
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品图文信息和属性")
    @Parameter(
            name = "skuId",
            description = "skuId",
            required = true)
    @RequestMapping(value = "/goodsDetailProper/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsDetailProperResponse> goodsDetailProper(@PathVariable String skuId) {
        GoodsDetailProperBySkuIdRequest request = new GoodsDetailProperBySkuIdRequest();
        request.setSkuId(skuId);
        request.setGoodsDetailType(GoodsDetailType.PC);
        TerminalSource terminalSource = commonUtil.getTerminal();
        if (TerminalSource.MINIPROGRAM.toValue() == terminalSource.toValue()) {
            request.setGoodsDetailType(GoodsDetailType.WECHAT);
        } else if(TerminalSource.H5.toValue() == terminalSource.toValue()
                || TerminalSource.APP.toValue() == terminalSource.toValue()) {
            request.setGoodsDetailType(GoodsDetailType.MOBILE);
        }
        return BaseResponse.success(goodsQueryProvider.getGoodsDetail(request).getContext());
    }

    /**
     * 查询商品图文信息和属性(VOP) 因VOP京东商品详情与SKU对应，和现有与SPU对应商品详情设计不符 因此提供单独接口，->empower->vop api 提供SKU 详情查询
     *
     * @return 返回商品图文详情
     */
    @Operation(summary = "查询商品图文信息和属性")
    @Parameters({
        @Parameter(
                name = "skuId",
                description = "skuId",
                required = true),
        @Parameter(
                name = "detailType",
                description = "detailType",
                required = true)
    })
    @RequestMapping(value = "/goodsDetailProper/{skuId}/{detailType}", method = RequestMethod.GET)
    public BaseResponse<GoodsDetailProperResponse> goodsDetailProper(
            @PathVariable String skuId, @PathVariable GoodsDetailType detailType) {
        // 是否购买京东渠道
        if (commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP)) {
            GoodsDetailProperBySkuIdRequest request = new GoodsDetailProperBySkuIdRequest();
            //获取第三方skuId
            GoodsInfoVO goodsInfoVO =
                    goodsInfoQueryProvider.getGoodsInfoById(GoodsInfoListByIdRequest.builder().goodsInfoId(skuId).build())
                            .getContext().getGoodsInfoVO();
            if (Objects.nonNull(skuId)) {
                String thirdSkuId = goodsInfoVO.getThirdPlatformSkuId();
                request.setSkuId(thirdSkuId);
            }
            request.setGoodsDetailType(detailType);
            return BaseResponse.success(
                    goodsQueryProvider.getChannelGoodsDetail(request).getContext());
        }
        return BaseResponse.success(null);
    }


    /**
     * 查询商品页面展示简易信息
     *
     * @return 返回分页结果
     */
    @Operation(summary = "查询商品页面展示简易信息")
    @Parameter(
            name = "skuId",
            required = true)
    @RequestMapping(value = "/goodsDetailSimple/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsDetailSimpleResponse> goodsDetailSimple(@PathVariable String skuId) {
        GoodsInfoVO goodsInfo =
                goodsInfoQueryProvider
                        .getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build())
                        .getContext();

        if (Objects.isNull(goodsInfo)
                || (!Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsDetailSimpleRequest request = new GoodsDetailSimpleRequest();
        request.setGoodsId(goodsInfo.getGoodsId());
        GoodsDetailSimpleResponse response =
                goodsQueryProvider.getGoodsDetailSimple(request).getContext();
        return BaseResponse.success(response);
    }

    /**
     * 小C-店铺精选-进入商品详情
     *
     * @return 返回分页结果
     */
    @Operation(summary = "小C-店铺精选-进入商品详情")
    @RequestMapping(
            value = "/shop/goods-detail/{distributorId}/{goodsId}/{goodsInfoId}",
            method = RequestMethod.GET)
    public BaseResponse<GoodsViewByIdAndSkuIdsResponse> shopGoodsDetail(
            @PathVariable String distributorId,
            @PathVariable String goodsId,
            @PathVariable String goodsInfoId) {
        DistributorGoodsInfoListByCustomerIdAndGoodsIdRequest request =
                new DistributorGoodsInfoListByCustomerIdAndGoodsIdRequest(
                        distributorId, goodsInfoId, goodsId);
        BaseResponse<DistributorGoodsInfoListByCustomerIdAndGoodsIdResponse> baseResponse =
                distributorGoodsInfoQueryProvider.listByCustomerIdAndGoodsId(request);
        List<DistributorGoodsInfoVO> list =
                baseResponse.getContext().getDistributorGoodsInfoVOList();
        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.info(CommonErrorCodeEnum.K000000.getCode(), GOODSNOTEXIST);
        }
        List<String> skuIds =
                list.stream()
                        .map(DistributorGoodsInfoVO::getGoodsInfoId)
                        .collect(Collectors.toList());
        return BaseResponse.success(
                goodsDetailBaseInfo(request.getGoodsInfoId(), skuIds, distributorId));
    }

    @Operation(summary = "未登录时,购物车弹框查询查询Spu商品详情")
    @Parameter(
            name = "skuId",
            description = "skuId",
            required = true)
    @RequestMapping(value = "/unLogin/spu/shopcart/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GoodsViewByIdResponse> unLoginDetailForShopCart(
            @PathVariable String skuId) {
        return BaseResponse.success(goodsBaseService.detail(skuId, null, Boolean.FALSE));
    }


    /**
     * SPU商品详情
     *
     * @param response
     * @param goodsInfoVOList
     * @param customer
     * @return
     */
    private GoodsViewByIdResponse detailGoodsInfoVOList(
            GoodsViewByIdResponse response,
            List<GoodsInfoVO> goodsInfoVOList,
            CustomerVO customer) {
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            // 根据开关重新设置分销商品标识
            distributionService.checkDistributionSwitch(goodsInfoVOList);
            String customerId = null;
            if (Objects.nonNull(customer) && StringUtils.isNotBlank(customer.getCustomerId())) {
                customerId = customer.getCustomerId();
            }
            // 预约，预售商品与其他营销活动互斥（在后面调用营销插件时，判断是否走插件中的逻辑）
            List<String> goodInfoIdList =
                    goodsInfoVOList.stream()
                            .map(GoodsInfoVO::getGoodsInfoId)
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodInfoIdList)) {
                AppointmentSaleAndBookingSaleResponse allRes =
                        appointmentSaleQueryProvider
                                .mergeAppointmentSaleAndBookingSale(
                                        AppointmentSaleAndBookingSaleRequest.builder()
                                                .goodsInfoIdList(goodInfoIdList)
                                                .build())
                                .getContext();
                if (Objects.nonNull(allRes)
                        && (CollectionUtils.isNotEmpty(allRes.getAppointmentSaleVOList())
                                || CollectionUtils.isNotEmpty(allRes.getBookingSaleVOList()))) {
                    goodsInfoVOList.forEach(
                            goodsInfoVO -> {
                                allRes.getAppointmentSaleVOList()
                                        .forEach(
                                                appointmentSaleVO -> {
                                                    if (appointmentSaleVO
                                                            .getAppointmentSaleGood()
                                                            .getGoodsInfoId()
                                                            .equals(goodsInfoVO.getGoodsInfoId())) {
                                                        goodsInfoVO.setAppointmentSaleVO(
                                                                appointmentSaleVO);
                                                    }
                                                });
                                allRes.getBookingSaleVOList()
                                        .forEach(
                                                bookingSaleVO -> {
                                                    if (Objects.nonNull(
                                                                    bookingSaleVO
                                                                            .getBookingSaleGoods())
                                                            && Objects.equals(
                                                                    bookingSaleVO
                                                                            .getBookingSaleGoods()
                                                                            .getGoodsInfoId(),
                                                                    goodsInfoVO.getGoodsInfoId())) {
                                                        goodsInfoVO.setBookingSaleVO(bookingSaleVO);
                                                    }
                                                });
                            });
                }
            }

            // 计算区间价
            GoodsIntervalPriceByGoodsAndSkuResponse priceResponse =
                    goodsIntervalPriceService.getGoodsAndSku(
                            goodsInfoVOList,
                            Collections.singletonList(response.getGoods()),
                            customerId);
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoVOList = priceResponse.getGoodsInfoVOList();

            // 计算营销价格
            MarketingPluginGoodsListFilterRequest filterRequest =
                    new MarketingPluginGoodsListFilterRequest();
            filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class));
            if (Objects.nonNull(customer)) {
                filterRequest.setCustomerId(customerId);
            }

            // 注意这边是取反，非全量营销时isFlashSaleMarketing=true
            if (Objects.nonNull(response.getFullMarketing())
                    && Objects.equals(response.getFullMarketing(), Boolean.FALSE)) {
                // 排除秒杀
                filterRequest.setIsFlashSaleMarketing(Boolean.TRUE);
            }
            response.setGoodsInfos(
                    marketingPluginProvider
                            .goodsListFilter(filterRequest)
                            .getContext()
                            .getGoodsInfoVOList());
            // 限售加入限售信息
            if (Objects.nonNull(customer)) {
                response.setGoodsInfos(this.setRestrictedNum(response.getGoodsInfos(), customer));
            }
            response.setGoodsInfos(this.fillActivityInfo(response.getGoodsInfos()));
        }
        return response;
    }

    /**
     * SPU商品详情-基础信息（不包括区间价、营销信息）
     *
     * @param skuId 商品skuId
     * @return SPU商品详情
     */
    private GoodsViewByIdResponse goodsDetailBaseInfo(String skuId) {
        GoodsInfoVO goodsInfo =
                goodsInfoQueryProvider
                        .getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build())
                        .getContext();

        if (Objects.isNull(goodsInfo)
                || (!Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        GoodsViewByIdRequest request = new GoodsViewByIdRequest();
        request.setGoodsId(goodsInfo.getGoodsId());
        request.setShowLabelFlag(Boolean.TRUE);
        request.setShowSiteLabelFlag(Boolean.TRUE);
        GoodsViewByIdResponse response = goodsQueryProvider.getViewById(request).getContext();
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        DefaultFlag storeOpenFlag = distributionCacheService.queryStoreOpenFlag(
                String.valueOf(goodsInfo.getStoreId()));
        if (DefaultFlag.NO.equals(openFlag)
                || DefaultFlag.NO.equals(storeOpenFlag)
                || !DistributionGoodsAudit.CHECKED.equals(goodsInfo.getDistributionGoodsAudit())) {
            response.setDistributionGoods(Boolean.FALSE);
        } else {
            response.setDistributionGoods(Boolean.TRUE);
        }

        List<GoodsInfoVO> goodsInfos = response.getGoodsInfos()
                .stream()
                .filter(v -> !Objects.equals(DistributionGoodsAudit.CHECKED, v.getDistributionGoodsAudit()))
                .collect(Collectors.toList());
        response.setGoodsInfos(goodsInfos);

        return response;
    }

    /**
     * 店铺精选页进入-商品详情页
     *
     * @param skuId
     * @param skuIds
     * @return
     */
    private GoodsViewByIdAndSkuIdsResponse goodsDetailBaseInfo(
            String skuId, List<String> skuIds, String distributorId) {
        GoodsInfoVO goodsInfo =
                goodsInfoQueryProvider
                        .getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build())
                        .getContext();


        if (Objects.isNull(goodsInfo)
                || Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())
                || (!Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()))
                || DefaultFlag.NO.equals(
                        distributionCacheService.queryStoreOpenFlag(
                                String.valueOf(goodsInfo.getStoreId())))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        if (AddedFlag.NO.toValue() == goodsInfo.getAddedFlag()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030041);
        }

        if (!DistributionGoodsAudit.CHECKED.equals(goodsInfo.getDistributionGoodsAudit())) {
            skuIds =
                    skuIds.stream()
                            .filter(goodsInfoId -> !goodsInfoId.equals(skuId))
                            .collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(skuIds)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        GoodsViewByIdAndSkuIdsRequest request = new GoodsViewByIdAndSkuIdsRequest();
        request.setGoodsId(goodsInfo.getGoodsId());
        request.setSkuIds(skuIds);
        GoodsViewByIdAndSkuIdsResponse goodsViewByIdAndSkuIdsResponse =
                goodsQueryProvider.getViewByIdAndSkuIds(request).getContext();
        if (CollectionUtils.isEmpty(goodsViewByIdAndSkuIdsResponse.getGoodsInfos())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        if (StringUtils.isNotBlank(distributorId)) {
            // 获取sku起售数量Map
            Map<String, RestrictedVO> skuStartSaleNumMap = goodsBaseService.getSkuStartSaleNumMap(skuIds);
            BaseResponse<DistributorLevelByCustomerIdResponse> baseResponse =
                    distributionService.getByCustomerId(distributorId);
            DistributorLevelVO distributorLevelVO =
                    Objects.isNull(baseResponse)
                            ? null
                            : baseResponse.getContext().getDistributorLevelVO();
            goodsViewByIdAndSkuIdsResponse
                    .getGoodsInfos()
                    .forEach(
                            goodsInfoVO -> {
                                if (Objects.nonNull(distributorLevelVO)
                                        && Objects.nonNull(distributorLevelVO.getCommissionRate())
                                        && DistributionGoodsAudit.CHECKED
                                                == goodsInfoVO.getDistributionGoodsAudit()) {
                                    goodsInfoVO.setDistributionCommission(
                                            distributionService.calDistributionCommission(
                                                    goodsInfoVO.getDistributionCommission(),
                                                    distributorLevelVO.getCommissionRate()));
                                }
                                // 填充起售数量
                                goodsInfoVO.setStartSaleNum(skuStartSaleNumMap.get(goodsInfoVO.getGoodsInfoId()).getRestrictedNum());
                                //还需要拉卡拉开启状态
                                PayGatewayVO payGatewayVO = laKaLaUtils.getPayGatewayVO();
                                boolean isOpen = IsOpen.YES.equals(Objects.nonNull(payGatewayVO) ? payGatewayVO.getIsOpen() : IsOpen.NO);
                                if (isOpen) {
                                    Long storeId = goodsInfoVO.getStoreId();
                                    StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder()
                                            .storeId(storeId)
                                            .build()).getContext().getStoreVO();
                                    String operatorId = commonUtil.getUserInfo().getUserId();
                                    List<LedgerReceiverRelVO> ledgerReceiverRelVOList = ledgerReceiverRelQueryProvider.list(LedgerReceiverRelListRequest.builder()
                                            .supplierId(storeVO.getCompanyInfoId())
                                            .receiverId(operatorId)
                                            .build()).getContext().getLedgerReceiverRelVOList();
                                    if (CollectionUtils.isEmpty(ledgerReceiverRelVOList)) {
                                        goodsInfoVO.setBindState(LedgerBindState.UNBOUND.toValue());
                                    } else {
                                        Integer bindState = ledgerReceiverRelVOList.get(0).getBindState();
                                        goodsInfoVO.setBindState(bindState);
                                    }
                                }
                            });
        }
        return goodsViewByIdAndSkuIdsResponse;
    }

    /**
     * 获取某个商品的小程序码
     *
     * @return
     */
    @Operation(summary = "获取某个商品的小程序码")
    @Parameter(
            name = "skuId",
            description = "skuId",
            required = true)
    @RequestMapping(value = "/getSkuQrCode", method = RequestMethod.POST)
    public BaseResponse<String> getSkuQrCode(@RequestBody ShareMiniProgramRequest request) {
        GoodsInfoVO goodsInfo =
                goodsInfoQueryProvider
                        .getById(
                                GoodsInfoByIdRequest.builder()
                                        .goodsInfoId(request.getSkuId())
                                        .build())
                        .getContext();
        if (Objects.isNull(goodsInfo)
                || Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())
                || (!Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        if (AddedFlag.NO.toValue() == goodsInfo.getAddedFlag()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030040);
        }

        // 新增判断条件，未登录情况下取数据库小程序码的oss地址，如果当前用户已登录，重新生成
        if (Objects.isNull(request.getShareUserId())) {
            if (StringUtils.isNotBlank(goodsInfo.getSmallProgramCode())) {
                return BaseResponse.success(goodsInfo.getSmallProgramCode());
            }
            // 没有，重新生成
            MiniProgramQrCodeRequest miniProgramQrCodeRequest = new MiniProgramQrCodeRequest();
            miniProgramQrCodeRequest.setPage("pages/sharepage/sharepage");
            // 判断如果是O2O商品，生成Redis缓存作为参数
            if(PluginType.O2O == goodsInfo.getPluginType()){
                String url = String.format("/pages/package-B/goods/goods-details/index?skuId=%s&businessModel=o2o",
                        request.getSkuId());
                // 把链接加密，生成redisKey，作为参数传递，用来生成小程序码
                String redisKey = RedisKeyConstant.QR_CODE_LINK.concat((MD5Util.md5Hex(url, "utf-8")).toUpperCase().substring(16));
                if (StringUtils.isNotBlank(redisKey)) {
                    redisService.setString(redisKey, url, 15000000L);
                }
                // 添加分享标识，方便小程序解析
                miniProgramQrCodeRequest.setScene("O2O" + redisKey);
            }else {
                miniProgramQrCodeRequest.setScene(request.getSkuId());
            }
            String codeUrl =
                    wechatAuthProvider.getWxaCodeUnlimit(miniProgramQrCodeRequest).getContext();
            // 更新字段
            if (StringUtils.isNotBlank(codeUrl)) {

                GoodsInfoSmallProgramCodeRequest goodsInfoSmallProgramCodeRequest =
                        new GoodsInfoSmallProgramCodeRequest();
                goodsInfoSmallProgramCodeRequest.setGoodsInfoId(request.getSkuId());
                goodsInfoSmallProgramCodeRequest.setCodeUrl(codeUrl);
                BaseResponse response =
                        goodsInfoProvider.updateSkuSmallProgram(goodsInfoSmallProgramCodeRequest);
                if (response.getCode().equals(BaseResponse.SUCCESSFUL().getCode())) {
                    return BaseResponse.success(codeUrl);
                }
            }
            return BaseResponse.success(codeUrl);
        } else {
            request.setShareId(commonUtil.getShareId(request.getShareUserId()));
            return wechatAuthProvider.getMiniProgramQrCodeWithShareUserId(request);
        }
    }

    /** 拼团-进入商品详情 */
    @Operation(summary = "拼团-进入商品详情")
    @RequestMapping(value = "/groupon/goods-detail/spu/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GrouponGoodsViewByIdResponse> grouponGoodsDetailLogin(
            @PathVariable String skuId) {
        // 拼团业务信息
        GrouponDetailQueryRequest grouponDetailQueryRequest =
                GrouponDetailQueryRequest.builder()
                        .optType(GrouponDetailOptType.GROUPON_GOODS_DETAIL)
                        .leader(Boolean.TRUE)
                        .goodsInfoId(skuId)
                        .build();

        return grouponGoodsDetail(grouponDetailQueryRequest);
    }

    /** 拼团-进入商品详情-未登录 */
    @Operation(summary = "拼团-进入商品详情")
    @RequestMapping(value = "/unLogin/groupon/goods-detail/spu/{skuId}", method = RequestMethod.GET)
    public BaseResponse<GrouponGoodsViewByIdResponse> grouponGoodsDetailUnLogin(
            @PathVariable String skuId) {
        // 拼团业务信息
        GrouponDetailQueryRequest grouponDetailQueryRequest =
                GrouponDetailQueryRequest.builder()
                        .optType(GrouponDetailOptType.GROUPON_GOODS_DETAIL)
                        .leader(Boolean.TRUE)
                        .goodsInfoId(skuId)
                        .build();
        return grouponGoodsDetail(grouponDetailQueryRequest);
    }

    /** 拼团-进入拼团详情页-未登录 */
    @Operation(summary = "拼团-进入拼团详情")
    @RequestMapping(
            value = "/unLogin/groupon/groupon-detail/{grouponNo}",
            method = RequestMethod.GET)
    public BaseResponse<GrouponGoodsViewByIdResponse> grouponDetailByGrouponNoUnLogin(
            @PathVariable String grouponNo) {
        // 拼团业务信息
        GrouponDetailQueryRequest grouponDetailWithGoodsRequest =
                GrouponDetailQueryRequest.builder()
                        .optType(GrouponDetailOptType.GROUPON_JOIN)
                        .grouponNo(grouponNo)
                        .build();
        return grouponGoodsDetailByGrouponNo(grouponDetailWithGoodsRequest);
    }

    /** 拼团-进入拼团详情页-登录 */
    @Operation(summary = "拼团-进入拼团详情")
    @RequestMapping(value = "/groupon/groupon-detail/{grouponNo}", method = RequestMethod.GET)
    public BaseResponse<GrouponGoodsViewByIdResponse> grouponDetailByGrouponNo(
            @PathVariable String grouponNo) {
        // 拼团业务信息
        GrouponDetailQueryRequest grouponDetailWithGoodsRequest =
                GrouponDetailQueryRequest.builder()
                        .optType(GrouponDetailOptType.GROUPON_JOIN)
                        .grouponNo(grouponNo)
                        .build();
        return grouponGoodsDetailByGrouponNo(grouponDetailWithGoodsRequest);
    }

    /** 商品类表筛选项 */
    @Operation(summary = "商品列表筛选项")
    @RequestMapping(value = "/selectOptions", method = RequestMethod.POST)
    public BaseResponse<EsGoodsSelectOptionsResponse> selectOptions(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 分类uuid不为空，组装spuIds
        if (StringUtils.isNotBlank(queryRequest.getCateUuid())) {
            XsiteGoodsCateByCateUuidResponse xsiteGoodsCateByCateUuidResponse =
                    xsiteGoodsCateProvider.findByCateUuid(XsiteGoodsCateQueryByCateUuidRequest.builder().cateUuid(queryRequest.getCateUuid()).build()).getContext();
            if (Objects.nonNull(queryRequest.getIsGoods()) && queryRequest.getIsGoods()){
                if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                    String goodsIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsIds();
                    if (StringUtils.isNotBlank(goodsIds)){
                        List<String> goodsIdList = JSON.parseArray(goodsIds, String.class);
                        queryRequest.setGoodsIds(goodsIdList);
                    }
                }
            } else {
                if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                    String goodsInfoIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsInfoIds();
                    if (StringUtils.isNotBlank(goodsInfoIds)) {
                        List<String> goodsInfoIdList = JSON.parseArray(goodsInfoIds, String.class);
                        queryRequest.setGoodsInfoIds(goodsInfoIdList);
                    }
                }
            }
        }
        goodsBaseService.getEsGoodsBoost(queryRequest);
        queryRequest = goodsListInterface.setRequest(queryRequest);
        return esGoodsInfoElasticQueryProvider.selectOptions(queryRequest);
    }

    /** 商品类表筛选项 */
    @Operation(summary = "未登录商品列表筛选项")
    @RequestMapping(value = "/unLogin/selectOptions", method = RequestMethod.POST)
    public BaseResponse<EsGoodsSelectOptionsResponse> unLoginSelectOptions(@RequestBody EsGoodsInfoQueryRequest queryRequest) {
        // 分类uuid不为空，组装spuIds
        if (StringUtils.isNotBlank(queryRequest.getCateUuid())) {
            XsiteGoodsCateByCateUuidResponse xsiteGoodsCateByCateUuidResponse =
                    xsiteGoodsCateProvider.findByCateUuid(XsiteGoodsCateQueryByCateUuidRequest.builder().cateUuid(queryRequest.getCateUuid()).build()).getContext();
            if (Objects.nonNull(queryRequest.getIsGoods()) && queryRequest.getIsGoods()){
                if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                    String goodsIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsIds();
                    if (StringUtils.isNotBlank(goodsIds)){
                        List<String> goodsIdList = JSON.parseArray(goodsIds, String.class);
                        queryRequest.setGoodsIds(goodsIdList);
                    }
                }
            } else {
                if (Objects.nonNull(xsiteGoodsCateByCateUuidResponse) && Objects.nonNull(xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO())){
                    String goodsInfoIds = xsiteGoodsCateByCateUuidResponse.getXsiteGoodsCateVO().getGoodsInfoIds();
                    if (StringUtils.isNotBlank(goodsInfoIds)) {
                        List<String> goodsInfoIdList = JSON.parseArray(goodsInfoIds, String.class);
                        queryRequest.setGoodsInfoIds(goodsInfoIdList);
                    }
                }
            }
        }
        goodsBaseService.getEsGoodsBoost(queryRequest);
        queryRequest = goodsListInterface.setRequest(queryRequest);
        return esGoodsInfoElasticQueryProvider.selectOptions(queryRequest);
    }

    /**
     * 1.根据商品获取团信息 2.查询商品信息 3.根据拼团活动设置商品信息
     *
     * @param grouponDetailQueryRequest
     * @return
     */
    private BaseResponse<GrouponGoodsViewByIdResponse> grouponGoodsDetail(
            GrouponDetailQueryRequest grouponDetailQueryRequest) {
        // 用户信息
        CustomerVO customer =
                Objects.nonNull(commonUtil.getOperatorId()) ? commonUtil.getCustomer() : null;
        // spu
        GoodsViewByIdResponse response =
                goodsDetailBaseInfo(grouponDetailQueryRequest.getGoodsInfoId());

        List<GoodsInfoVO> goodsInfoVOList =
                response.getGoodsInfos().stream()
                        .filter(g -> AddedFlag.YES.toValue() == g.getAddedFlag())
                        .collect(Collectors.toList());
        // 查询skus信息
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            response = detailGoodsInfoVOList(response, response.getGoodsInfos(), customer);
        }

        // 拼团信息
        grouponDetailQueryRequest.setGoodsId(response.getGoods().getGoodsId());
        grouponDetailQueryRequest.setCustomerId(
                Objects.isNull(customer) ? null : customer.getCustomerId());
        GrouponDetailQueryResponse grouponDetailQueryResponse =
                grouponProvider.getGrouponDetail(grouponDetailQueryRequest).getContext();
        // sku-spu-skus
        List<GrouponGoodsInfoVO> grouponGoodsInfoVOList =
                grouponDetailQueryResponse.getGrouponDetail().getGrouponGoodsInfos();
        List<String> skuIds =
                grouponGoodsInfoVOList.stream()
                        .map(GrouponGoodsInfoVO::getGoodsInfoId)
                        .collect(Collectors.toList());

        // 过滤拼团商品
        goodsInfoVOList =
                goodsInfoVOList.stream()
                        .filter(g -> skuIds.contains(g.getGoodsInfoId()))
                        .collect(Collectors.toList());

        IteratorUtils.zip(
                goodsInfoVOList,
                response.getGoodsInfos(),
                (a, b) -> a.getGoodsInfoId().equals(b.getGoodsInfoId()),
                (c, d) -> {
                    c.setCouponLabels(d.getCouponLabels());
                });

        response.setGoodsInfos(goodsInfoVOList);
        // 商品是否存在
        if (CollectionUtils.isEmpty(goodsInfoVOList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        //企业购商品前端禁用（库存给0）
        for (GoodsInfoVO goodsInfo : response.getGoodsInfos()) {
            boolean flag = false;
            GoodsInfoSimpleDetailByGoodsInfoResponse goodsInfoResponse =
                    goodsDetailInterface.getDetail(goodsInfo.getGoodsInfoId(), null);
            flag = flag ||
                    goodsInfoResponse.getGoodsInfo().getMarketingPluginLabels().stream()
                    .filter(marketingPluginLabelVO -> marketingPluginLabelVO.getMarketingType() == 111
                            || marketingPluginLabelVO.getMarketingType() == 5
                            || marketingPluginLabelVO.getMarketingType() == 6
                            || marketingPluginLabelVO.getMarketingType() == 102
                            || marketingPluginLabelVO.getMarketingType() == 103
                            || marketingPluginLabelVO.getMarketingType() == 109
                    ).count() > 0;
            flag =
                    flag || Objects.nonNull(goodsInfo.getBuyPoint()) && goodsInfo.getBuyPoint().intValue() > 0;
            if(flag){
                goodsInfo.setGroupStatus(Boolean.FALSE);
                goodsInfo.setStock(0L);
            } else {
                goodsInfo.setGroupStatus(Boolean.TRUE);
            }
        }
        // 以上为商品信息
        return BaseResponse.success(
                wrapeGrouponGoodsViewByIdResponse(response, grouponDetailQueryResponse));
    }

    /**
     * 1.根据团编号获取团信息 2.查询商品信息 3.根据拼团活动设置商品信息
     *
     * @param grouponDetailQueryRequest
     * @return
     */
    private BaseResponse<GrouponGoodsViewByIdResponse> grouponGoodsDetailByGrouponNo(
            GrouponDetailQueryRequest grouponDetailQueryRequest) {
        // 用户信息
        CustomerVO customer =
                Objects.nonNull(commonUtil.getOperatorId()) ? commonUtil.getCustomer() : null;
        // 拼团信息
        grouponDetailQueryRequest.setCustomerId(
                Objects.isNull(customer) ? null : customer.getCustomerId());
        GrouponDetailQueryResponse grouponDetailQueryResponse =
                grouponProvider.getGrouponDetail(grouponDetailQueryRequest).getContext();
        // 根据活动商品筛选sku
        List<GrouponGoodsInfoVO> grouponGoodsInfoVOList =
                grouponDetailQueryResponse.getGrouponDetail().getGrouponGoodsInfos();
        if (CollectionUtils.isEmpty(grouponGoodsInfoVOList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030153);
        }
        List<String> skuIds =
                grouponGoodsInfoVOList.stream()
                        .map(GrouponGoodsInfoVO::getGoodsInfoId)
                        .collect(Collectors.toList());
        // 团详情页面根据groupon反查sku
        GoodsViewByIdResponse response = goodsDetailBaseInfo(skuIds.get(0));
        List<GoodsInfoVO> goodsInfoVOList =
                response.getGoodsInfos().stream()
                        .filter(g -> AddedFlag.YES.toValue() == g.getAddedFlag())
                        .collect(Collectors.toList());
        // 查询skus信息
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            response = detailGoodsInfoVOList(response, goodsInfoVOList, customer);
        }
        // 判断活动是否结束
        if (!GrouponDetailOptStatus.ACTIVITY_END.equals(
                grouponDetailQueryResponse.getGrouponDetail().getGrouponDetailOptStatus())) {
            // 过滤分销商品
            //            goodsInfoVOList = response.getGoodsInfos().stream().filter(g ->
            // !DistributionGoodsAudit.CHECKED.equals(g
            //                    .getDistributionGoodsAudit())).collect(Collectors.toList());
            // 过滤拼团商品
            //            goodsInfoVOList = goodsInfoVOList.stream()
            //                    .filter(g -> skuIds.contains(g.getGoodsInfoId())).filter(g ->
            // !DistributionGoodsAudit.CHECKED.equals
            //                            (g.getDistributionGoodsAudit()))
            //                    .collect(Collectors.toList());
            response.setGoodsInfos(goodsInfoVOList);
        }
        // 商品是否存在
        if (CollectionUtils.isEmpty(goodsInfoVOList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        // 以上为商品信息
        return BaseResponse.success(
                wrapeGrouponGoodsViewByIdResponse(response, grouponDetailQueryResponse));
    }

    /**
     * 商品信息处理拼团信息 起订限定量
     *
     * @param response
     * @param grouponDetailQueryResponse
     * @return
     */
    private GrouponGoodsViewByIdResponse wrapeGrouponGoodsViewByIdResponse(
            GoodsViewByIdResponse response, GrouponDetailQueryResponse grouponDetailQueryResponse) {
        GrouponDetailVO grouponDetail = grouponDetailQueryResponse.getGrouponDetail();
        // 用户信息
        String customerId = commonUtil.getOperatorId();

        // 商品处理拼团信息
        GrouponDetailWithGoodsRequest grouponDetailWithGoodsRequest =
                new GrouponDetailWithGoodsRequest();
        grouponDetailWithGoodsRequest.setGrouponActivity(grouponDetail.getGrouponActivity());
        grouponDetailWithGoodsRequest.setGoodsInfoList(response.getGoodsInfos());
        grouponDetailWithGoodsRequest.setGrouponGoodsInfoVOList(
                grouponDetail.getGrouponGoodsInfos());
        grouponDetailWithGoodsRequest.setCustomerId(customerId);
        GrouponDetailWithGoodsResponse grouponDetailWithGoodsResponse =
                grouponProvider
                        .getGrouponDetailWithGoodsInfos(grouponDetailWithGoodsRequest)
                        .getContext();
        // 返回拼团信息
        GrouponGoodsViewByIdResponse grouponGoodsViewByIdResponse =
                KsBeanUtil.convert(response, GrouponGoodsViewByIdResponse.class);
        // 精简返回页面的数据
        GrouponDetailWithGoodsVO grouponDetailWithGoodsVO =
                KsBeanUtil.convert(grouponDetail, GrouponDetailWithGoodsVO.class);

        // 参团商品sku优先显示为团长开团的sku
        if (Objects.nonNull(grouponDetail.getTradeInGroupon())) {
            grouponDetailWithGoodsVO.setGoodInfoId(
                    grouponDetail.getTradeInGroupon().getTradeGroupon().getGoodInfoId());
            grouponDetailWithGoodsVO.setGroupCustomerId(
                    grouponDetail.getTradeInGroupon().getBuyer().getId());
        }
        grouponGoodsViewByIdResponse.setGrouponDetails(grouponDetailWithGoodsVO);
        grouponGoodsViewByIdResponse.setGoodsInfos(
                grouponDetailWithGoodsResponse.getGoodsInfoVOList());
        grouponGoodsViewByIdResponse.setGrouponInstanceList(grouponDetail.getGrouponInstanceList());

        return grouponGoodsViewByIdResponse;
    }

    /**
     * 填充预售信息
     *
     * @param goodsInfoVOS
     * @return
     */
    private List<GoodsInfoVO> fillActivityInfo(List<GoodsInfoVO> goodsInfoVOS) {
        List<String> goodsInfoIds =
                goodsInfoVOS.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        List<AppointmentSaleVO> appointmentSaleVOList =
                appointmentSaleQueryProvider
                        .inProgressAppointmentSaleInfoByGoodsInfoIdList(
                                (AppointmentSaleInProgressRequest.builder()
                                        .goodsInfoIdList(goodsInfoIds)
                                        .build()))
                        .getContext()
                        .getAppointmentSaleVOList();
        if (CollectionUtils.isNotEmpty(appointmentSaleVOList)) {
            Map<String, List<AppointmentSaleVO>> appointmentMap =
                    appointmentSaleVOList.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            a -> a.getAppointmentSaleGood().getGoodsInfoId()));
            goodsInfoVOS.forEach(
                    g -> {
                        if (appointmentMap.containsKey(g.getGoodsInfoId())) {
                            BigDecimal appointmentPrice =
                                    appointmentMap
                                            .get(g.getGoodsInfoId())
                                            .get(0)
                                            .getAppointmentSaleGood()
                                            .getPrice();
                            g.setAppointmentPrice(appointmentPrice);
                            g.setAppointmentSaleVO(appointmentMap.get(g.getGoodsInfoId()).get(0));
                        }
                    });
        }
        List<BookingSaleVO> bookingSaleVOList =
                bookingSaleQueryProvider
                        .inProgressBookingSaleInfoByGoodsInfoIdList(
                                BookingSaleInProgressRequest.builder()
                                        .goodsInfoIdList(goodsInfoIds)
                                        .build())
                        .getContext()
                        .getBookingSaleVOList();
        if (CollectionUtils.isNotEmpty(bookingSaleVOList)) {
            Map<String, List<BookingSaleVO>> bookingMap =
                    bookingSaleVOList.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            a -> a.getBookingSaleGoods().getGoodsInfoId()));
            goodsInfoVOS.forEach(
                    g -> {
                        if (bookingMap.containsKey(g.getGoodsInfoId())) {
                            BigDecimal bookingPrice =
                                    bookingMap
                                            .get(g.getGoodsInfoId())
                                            .get(0)
                                            .getBookingSaleGoods()
                                            .getBookingPrice();
                            g.setBookingPrice(bookingPrice);
                            g.setBookingSaleVO(bookingMap.get(g.getGoodsInfoId()).get(0));
                        }
                    });
        }
        return goodsInfoVOS;
    }

    /**
     * 设置限售数据
     *
     * @param goodsInfoVOS
     * @param customerVO
     * @return
     */
    private List<GoodsInfoVO> setRestrictedNum(
            List<GoodsInfoVO> goodsInfoVOS, CustomerVO customerVO) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(goodsInfoVOS)) {
            List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS = new ArrayList<>();
            goodsInfoVOS.stream()
                    .forEach(
                            g -> {
                                GoodsRestrictedValidateVO rvv = new GoodsRestrictedValidateVO();
                                rvv.setNum(g.getBuyCount());
                                rvv.setSkuId(g.getGoodsInfoId());
                                goodsRestrictedValidateVOS.add(rvv);
                            });
            GoodsRestrictedSalePurchaseResponse response =
                    goodsRestrictedSaleQueryProvider
                            .validatePurchaseRestricted(
                                    GoodsRestrictedBatchValidateRequest.builder()
                                            .goodsRestrictedValidateVOS(goodsRestrictedValidateVOS)
                                            .customerVO(customerVO)
                                            .build())
                            .getContext();
            if (Objects.nonNull(response)
                    && org.apache.commons.collections4.CollectionUtils.isNotEmpty(
                            response.getGoodsRestrictedPurchaseVOS())) {
                List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS =
                        response.getGoodsRestrictedPurchaseVOS();
                Map<String, GoodsRestrictedPurchaseVO> purchaseMap =
                        goodsRestrictedPurchaseVOS.stream()
                                .collect(
                                        (Collectors.toMap(
                                                GoodsRestrictedPurchaseVO::getGoodsInfoId,
                                                Function.identity())));
                goodsInfoVOS.stream()
                        .forEach(
                                g -> {
                                    GoodsRestrictedPurchaseVO goodsRestrictedPurchaseVO =
                                            purchaseMap.get(g.getGoodsInfoId());
                                    if (Objects.nonNull(goodsRestrictedPurchaseVO)) {
                                        if (DefaultFlag.YES.equals(
                                                goodsRestrictedPurchaseVO.getDefaultFlag())) {
                                            g.setMaxCount(
                                                    goodsRestrictedPurchaseVO.getRestrictedNum());
                                            g.setCount(goodsRestrictedPurchaseVO.getStartSaleNum());
                                        } else {
                                            g.setGoodsStatus(GoodsStatus.INVALID);
                                        }
                                    }
                                });
            }
            return goodsInfoVOS;
        }
        return goodsInfoVOS;
    }

    /**
     * 查询这个sku是否为正在进行中拼团
     *
     * @return
     */
    @Operation(summary = "查询这个sku是否为正在进行中拼团")
    @Parameter(
            name = "goodsInfoId",
            description = "goodsInfoId",
            required = true)
    @RequestMapping(value = "/groupOn/sku/{goodsInfoId}", method = RequestMethod.GET)
    public BaseResponse<GrouponGoodsByGrouponActivityIdAndGoodsInfoIdResponse> checkGroupOnFlag(
            @PathVariable String goodsInfoId) {

        GrouponGoodsInfoVO grouponGoodsInfo =
                grouponActivityQueryProvider
                        .listActivityingByGoodsInfoIds(
                                new GrouponActivityingByGoodsInfoIdsRequest(
                                        Collections.singletonList(goodsInfoId)))
                        .getContext()
                        .getGrouponGoodsInfoMap()
                        .get(goodsInfoId);

        return BaseResponse.success(
                new GrouponGoodsByGrouponActivityIdAndGoodsInfoIdResponse(grouponGoodsInfo));
    }

    @Operation(summary = "查询仅看有货开关状态")
    @GetMapping("/out-of-stock/flag")
    public BaseResponse<BossGoodsOutOfStockShowResponse> findGoodsOutOfStockFlag() {

        return auditQueryProvider.isGoodsOutOfStockShow();
    }


    private void getEsGoodsBoost(EsGoodsInfoQueryRequest request){
        //查询并组装
        String context = systemConfigQueryProvider.findContextByConfigType().getContext().getContext();
        if (StringUtils.isNotBlank(context)){
            try{
                EsGoodsBoostSettingVO esGoodsBoostSettingVO = JSONObject.parseObject(context, EsGoodsBoostSettingVO.class);
                if (Objects.nonNull(esGoodsBoostSettingVO)){
                    request.setGoodsSubtitleBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsSubtitleBoost())
                            ?esGoodsBoostSettingVO.getGoodsSubtitleBoost():null);
                    request.setBrandNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getBrandNameBoost())
                            ?esGoodsBoostSettingVO.getBrandNameBoost():null);
                    request.setCateNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getCateNameBoost())
                            ?esGoodsBoostSettingVO.getCateNameBoost():null);
                    request.setGoodsInfoNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsInfoNameBoost())
                            ?esGoodsBoostSettingVO.getGoodsInfoNameBoost():null);
                    request.setSpecTextBoost(Objects.nonNull(esGoodsBoostSettingVO.getSpecTextBoost())
                            ?esGoodsBoostSettingVO.getSpecTextBoost():null);
                    request.setGoodsLabelNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsLabelNameBoost())
                            ?esGoodsBoostSettingVO.getGoodsLabelNameBoost():null);
                    request.setGoodsPropDetailNestNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsPropDetailNestNameBoost())
                            ?esGoodsBoostSettingVO.getGoodsPropDetailNestNameBoost():null);
                }
            } catch (Exception e) {
                logger.error("es查询权重配比失败，{}", e);
            }
        }
    }

    /**
     * 设置商品主图
     * @param response
     */
    public void setGoodsMainImage(GoodsInfoListResponse response) {
        if(CollectionUtils.isEmpty(response.getGoodsInfoPage().getContent())){
            return;
        }

        List<GoodsInfoListVO> content = response.getGoodsInfoPage().getContent();

        List<String> goodsIdList = content.stream().map(GoodsInfoListVO::getGoodsId).collect(Collectors.toList());

        // 获取商品主图
        GoodsMainImageResponse mainImageResponse = goodsQueryProvider.findGoodsMainImageByGoodsId(goodsIdList).getContext();
        if (CollectionUtils.isNotEmpty(mainImageResponse.getGoodsMainImageVOList())) {
            // 构建 goodsId 到 mainImage 的映射
            Map<String, String> goodsIdToMainImage = mainImageResponse.getGoodsMainImageVOList ().stream()
                    .filter(goodsMainImageVO -> goodsMainImageVO.getGoodsId() != null)
                    .collect(Collectors.toMap(GoodsMainImageVO::getGoodsId, GoodsMainImageVO::getArtworkUrl));

            // 设置主图
            content.forEach(esGoodsInfoVO -> {
                esGoodsInfoVO.setGoodsInfoMainImg(goodsIdToMainImage.get(esGoodsInfoVO.getGoodsId()));
            });
        }

    }

}
