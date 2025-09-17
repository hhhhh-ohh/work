package com.wanmi.sbc.init;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsStoreEvaluateSumQueryProvider;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceProvider;
import com.wanmi.sbc.elastic.api.provider.distributioninvitenew.EsDistributionInviteNewProvider;
import com.wanmi.sbc.elastic.api.provider.distributionmatter.EsDistributionGoodsMatterProvider;
import com.wanmi.sbc.elastic.api.provider.distributionrecord.EsDistributionRecordProvider;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.goodsevaluate.EsGoodsEvaluateProvider;
import com.wanmi.sbc.elastic.api.provider.groupon.EsGrouponActivityProvider;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoProvider;
import com.wanmi.sbc.elastic.api.provider.operationlog.EsOperationLogQueryProvider;
import com.wanmi.sbc.elastic.api.provider.orderinvoice.EsOrderInvoiceProvider;
import com.wanmi.sbc.elastic.api.provider.pointsgoods.EsPointsGoodsProvider;
import com.wanmi.sbc.elastic.api.provider.searchterms.EsSearchAssociationalWordProvider;
import com.wanmi.sbc.elastic.api.provider.sensitivewords.EsSensitiveWordsProvider;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponInitRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityInitRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumInitRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewInitRequest;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatterInitRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordInitRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeInitRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandInitRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateInitRequest;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityInitRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogInitRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceInitRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsInitRequest;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordInitRequest;
import com.wanmi.sbc.elastic.api.request.sensitivewords.EsSensitiveWordsInitRequest;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementInitRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.ESStoreInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceInitRequest;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * Author: bail
 * Time: 2017/10/23.8:42
 */
@RestController
@Validated
@RequestMapping("/init")
@Slf4j
@Tag(name =  "初始化ES服务", description =  "InitESDataController")
public class InitESDataController {

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private EsCouponActivityProvider esCouponActivityProvider;

    @Autowired
    private EsCouponInfoProvider esCouponInfoProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired
    private EsStoreEvaluateSumQueryProvider esStoreEvaluateSumQueryProvider;

    @Autowired
    private EsGrouponActivityProvider esGrouponActivityProvider;

    @Autowired
    private EsOperationLogQueryProvider esOperationLogQueryProvider;

    @Autowired
    private EsSearchAssociationalWordProvider esSearchAssociationalWordProvider;

    @Autowired
    private EsSensitiveWordsProvider esSensitiveWordsProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Autowired
    private EsEmployeeProvider esEmployeeProvider;

    @Autowired
    private EsPointsGoodsProvider esPointsGoodsProvider;

    @Autowired
    private EsCustomerInvoiceProvider esCustomerInvoiceProvider;

    @Autowired
    private EsDistributionRecordProvider esDistributionRecordProvider;

    @Autowired
    private EsGoodsEvaluateProvider esGoodsEvaluateProvider;

    @Autowired
    private EsOrderInvoiceProvider esOrderInvoiceProvider;

    @Autowired
    private EsDistributionInviteNewProvider esDistributionInviteNewProvider;

    @Autowired
    private EsDistributionGoodsMatterProvider esDistributionGoodsMatterProvider;

    @Autowired
    private EsGoodsBrandProvider esGoodsBrandProvider;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private EsSettlementProvider esSettlementProvider;

    @Autowired
    private EsCouponScopeProvider esCouponScopeProvider;

    @Autowired
    private EsLedgerBindInfoProvider esLedgerBindInfoProvider;

    @Operation(summary = "初始化订单")
    @RequestMapping(value = "/initOrderEmployee", method = RequestMethod.GET)
    public BaseResponse initOrderEmployee() {
        tradeProvider.fillEmployeeId();
        returnOrderProvider.fillEmployeeId();
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 商品信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 商品信息 同步到-> ES中")
    @RequestMapping(value = "/goodsES", method = RequestMethod.POST)
    public BaseResponse initAllGoodsES(@RequestBody EsGoodsInfoRequest queryRequest) {
        queryRequest.setClearEsIndexFlag(DefaultFlag.YES);
        return esGoodsInfoElasticProvider.initEsGoodsInfo(queryRequest);
    }

    /**
     * 将mysql 商品信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 商品库信息 同步到-> ES中")
    @RequestMapping(value = "/standardES", method = RequestMethod.POST)
    public BaseResponse initAllGoodsES(@RequestBody EsStandardInitRequest queryRequest) {
        queryRequest.setClearEsIndexFlag(DefaultFlag.YES);
        return esStandardProvider.init(queryRequest);
    }

    /**
     * 将mysql 优惠券信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 优惠券信息 同步到-> ES中")
    @RequestMapping(value = "/couponInfoES", method = RequestMethod.POST)
    public BaseResponse initAllCouponInfoES(@RequestBody EsCouponInfoInitRequest queryRequest) {
        esCouponInfoProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 优惠券活动信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 优惠券活动信息 同步到-> ES中")
    @RequestMapping(value = "/couponActivityES", method = RequestMethod.POST)
    public BaseResponse initAllCouponActivityES(@RequestBody EsCouponActivityInitRequest queryRequest) {
        esCouponActivityProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 会员信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 会员信息 同步到-> ES中")
    @RequestMapping(value = "/customerES", method = RequestMethod.POST)
    public BaseResponse initAllCustomerES(@RequestBody EsCustomerDetailInitRequest queryRequest) {
        esCustomerDetailProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 分销员信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 分销员信息 同步到-> ES中")
    @RequestMapping(value = "/initDistributionCustomerES", method = RequestMethod.POST)
    public BaseResponse initEsDistributionCustomer(@RequestBody EsDistributionCustomerInitRequest queryRequest) {
        return esDistributionCustomerProvider.init(queryRequest);
    }

    /**
     * 将mysql 拼团信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 拼团信息 同步到-> ES中")
    @RequestMapping(value = "/initGrouponActivityES", method = RequestMethod.POST)
    public BaseResponse initEsGrouponActivity(@RequestBody EsGrouponActivityInitRequest queryRequest) {
        esGrouponActivityProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 商家评价信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 商家评价信息 同步到-> ES中")
    @RequestMapping(value = "/initStoreEvaluateSumES", method = RequestMethod.POST)
    public BaseResponse initStoreEvaluateSum(@RequestBody EsStoreEvaluateSumInitRequest queryRequest) {
        esStoreEvaluateSumQueryProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 操作日志信息 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 操作日志信息 同步到-> ES中")
    @RequestMapping(value = "/initOperationLogQueryES", method = RequestMethod.POST)
    public BaseResponse initOperationLog(@RequestBody EsOperationLogInitRequest queryRequest) {
        esOperationLogQueryProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  搜索词 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 搜索词 同步到-> ES中")
    @RequestMapping(value = "/initSearchAssociationalWordES", method = RequestMethod.POST)
    public BaseResponse initSearchAssociationalWord(@RequestBody EsSearchAssociationalWordInitRequest queryRequest) {
        esSearchAssociationalWordProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  素材 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 素材 同步到-> ES中")
    @RequestMapping(value = "/initSystemResourceES", method = RequestMethod.POST)
    public BaseResponse initSystemResource(@RequestBody EsSystemResourceInitRequest queryRequest) {
        esSystemResourceProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  敏感词 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 敏感词 同步到-> ES中")
    @RequestMapping(value = "/initSensitiveWordsES", method = RequestMethod.POST)
    public BaseResponse initSensitiveWords(@RequestBody EsSensitiveWordsInitRequest queryRequest) {
        esSensitiveWordsProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  员工 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 员工 同步到-> ES中")
    @RequestMapping(value = "/initEsEmployee", method = RequestMethod.POST)
    public BaseResponse initEsEmployee(@RequestBody EsEmployeeInitRequest employeePageRequest) {
        esEmployeeProvider.init(employeePageRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  分销记录 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 分销记录 同步到-> ES中")
    @RequestMapping(value = "/initDistributionRecordES", method = RequestMethod.POST)
    public BaseResponse initDistributionRecord(@RequestBody EsDistributionRecordInitRequest queryRequest) {
        esDistributionRecordProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  商品评价 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 商品评价 同步到-> ES中")
    @RequestMapping(value = "/initGoodsEvaluateES", method = RequestMethod.POST)
    public BaseResponse initGoodsEvaluate(@RequestBody EsGoodsEvaluateInitRequest queryRequest) {
        esGoodsEvaluateProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql  素材 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 积分商品 同步到-> ES中")
    @RequestMapping(value = "/initPointsGoodsES", method = RequestMethod.POST)
    public BaseResponse initPointsGoodsES(@RequestBody EsPointsGoodsInitRequest request) {
        return esPointsGoodsProvider.init(request);
    }

    /**
     * 将mysql 增票资质 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 增票资质 同步到-> ES中")
    @RequestMapping(value = "/initCustomerInvoiceES", method = RequestMethod.POST)
    public BaseResponse initCustomerInvoiceES(@RequestBody EsCustomerInvoicePageRequest esCustomerInvoicePageRequest) {
        esCustomerInvoiceProvider.init(esCustomerInvoicePageRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 邀新记录 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 邀新记录 同步到-> ES中")
    @RequestMapping(value = "/initDistributionInviteNewRecordES", method = RequestMethod.POST)
    public BaseResponse initDistributionInviteNewRecord(@RequestBody EsDistributionInviteNewInitRequest request) {
        esDistributionInviteNewProvider.initDistributionInviteNewRecord(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 分销素材 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 分销素材 同步到-> ES中")
    @RequestMapping(value = "/initDistributionGoodsMatterES", method = RequestMethod.POST)
    public BaseResponse initDistributionGoodsMatter(@RequestBody EsDistributionGoodsMatterInitRequest request) {
        esDistributionGoodsMatterProvider.init(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 将mysql 商品品牌 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 商品品牌 同步到-> ES中")
    @RequestMapping(value = "/initGoodsBrandMatterES", method = RequestMethod.POST)
    public BaseResponse initGoodsBrandMatter(@RequestBody EsGoodsBrandInitRequest request) {
        return esGoodsBrandProvider.init(request);
    }

    /**
     * 将mysql  订单开票 同步到-> ES中
     */
    @MultiSubmit
    @Operation(summary = "将mysql 订单开票 同步到-> ES中")
    @RequestMapping(value = "/initOrderInvoiceES", method = RequestMethod.POST)
    public BaseResponse initOrderInvoice(@RequestBody EsOrderInvoiceInitRequest request) {
        return esOrderInvoiceProvider.init(request);
    }

    /**
     * 将mysql 商家店铺信息 同步到-> ES中
     *
     * @param pageRequest
     * @return BaseResponse
     */
    @Operation(summary = "初始化es店铺信息")
    @RequestMapping(value = "initStoreInfoES", method = RequestMethod.POST)
    public BaseResponse initStoreInfoES(@RequestBody @Valid ESStoreInfoInitRequest
                                                pageRequest) {
        return esStoreInformationProvider.initStoreInformationList(pageRequest);
    }

    /**
     * 将mysql 结算单 同步到-> ES中
     *
     * @param pageRequest
     * @return BaseResponse
     */
    @Operation(summary = "初始化es结算单")
    @RequestMapping(value = "initSettlementES", method = RequestMethod.POST)
    public BaseResponse initSettlementES(@RequestBody @Valid EsSettlementInitRequest
                                                 pageRequest) {
        return esSettlementProvider.initSettlement(pageRequest);
    }

    @Operation(summary = "初始化es拉卡拉结算单")
    @RequestMapping(value = "initLakalaSettlementES", method = RequestMethod.POST)
    public BaseResponse initLakalaSettlementES(@RequestBody @Valid EsSettlementInitRequest
                                                 pageRequest) {
        return esSettlementProvider.initLakalaSettlement(pageRequest);
    }

    /**
     * 将mysql 活动优惠券 同步到-> ES中
     *
     * @param pageRequest
     * @return BaseResponse
     */
    @Operation(summary = "初始化es活动优惠券")
    @RequestMapping(value = "initActivityCouponES", method = RequestMethod.POST)
    public BaseResponse initActivityCouponES(@RequestBody @Valid EsActivityCouponInitRequest
                                                 pageRequest) {
        return esCouponScopeProvider.init(pageRequest);
    }

    /**
     * 将mysql 分账绑定关系 同步到-> ES中
     * @param request
     * @return
     */
    @Operation(summary = "初始化es分账绑定数据")
    @RequestMapping(value = "/initLedgerBindInfoES", method = RequestMethod.POST)
    public BaseResponse intiLedgerBindInfoES(EsLedgerBindInfoInitRequest request) {
        return esLedgerBindInfoProvider.init(request);
    }

}
