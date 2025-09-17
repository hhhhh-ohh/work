package com.wanmi.sbc.trade;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountListRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.QueryByBusinessIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListCompanyStoreByCompanyIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountListResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.QueryByBusinessIdsResponse;
import com.wanmi.sbc.customer.api.response.store.ListStoreResponse;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.unionb2b.UnionB2BProvider;
import com.wanmi.sbc.empower.api.provider.pay.unioncloud.UnionCloudPayProvider;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.PayRefundBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.PayExtraRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.OpenedChannelItemRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayOpenedByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForNativeRequest;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.*;
import com.wanmi.sbc.empower.bean.vo.LakalaCasherPayItemVO;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.provider.plugin.PluginPayInfoProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesListRequest;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoAddRequest;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesListResponse;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradePayCallBackOnlineDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.SupplierVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.PlatformPayCompanyProvider;
import com.wanmi.sbc.setting.api.response.PlatformPayCompanyListResponse;
import com.wanmi.sbc.setting.api.response.wechat.WechatSetResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformPayCompanyVO;
import com.wanmi.sbc.third.wechat.WechatSetService;
import com.wanmi.sbc.trade.request.PayItemsRequest;
import com.wanmi.sbc.trade.request.PayMobileRequest;
import com.wanmi.sbc.trade.request.WeiXinPayRequest;
import com.wanmi.sbc.util.CommonUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sunkun on 2017/8/23.
 */
@Tag(name = "PayController", description = "支付Api")
@RestController
@Validated
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private WechatSetService wechatSetService;

    @Autowired
    private UnionCloudPayProvider unionCloudPayProvider;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private UnionB2BProvider unionB2BProvider;

    @Autowired private PluginPayInfoProvider pluginPayInfoProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private PlatformPayCompanyProvider platformPayChannelProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    /**
     * 获取可用支付项
     *
     * @return
     */
    @Operation(summary = "获取可用支付项")
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public BaseResponse<List<PayChannelItemVO>> items() {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<PayGatewayConfigVO> payGatewayConfigList = paySettingQueryProvider.listOpenedGatewayConfig(request).getContext()
                .getGatewayConfigVOList();

        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.H5);

        List<PayChannelItemVO> itemList = new ArrayList<>();
        payGatewayConfigList.forEach(config -> {
            List<PayChannelItemVO> payChannelItemList = paySettingQueryProvider.listOpenedChannelItemByGatewayName(new
                    OpenedChannelItemRequest(config.getPayGateway().getName(), TerminalType.PC)).getContext()
                    .getPayChannelItemVOList();
            if (CollectionUtils.isNotEmpty(payChannelItemList)) {
                itemList.addAll(payChannelItemList);
            }
        });

        //如果是关的，则关闭渠道
        if(DefaultFlag.NO.equals(wxOpenFlag)) {
            itemList.stream().filter(i -> PayWay.WECHAT.name().equalsIgnoreCase(i.getChannel())).forEach(i -> {
                i.setIsOpen(IsOpen.NO);
            });
        }
        return BaseResponse.success(itemList);
    }

    @Operation(summary = "获取可用支付项")
    @Parameter(name = "directPaymentFlag",
            description = "0:非直连支付 1：直连支付",
            required = true)
    @RequestMapping(value = "/items/{directPaymentFlag}", method = RequestMethod.GET)
    public BaseResponse<List<PayChannelItemVO>> items(@PathVariable Integer directPaymentFlag) {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<PayGatewayConfigVO> payGatewayConfigList =
                paySettingQueryProvider
                        .listOpenedGatewayConfig(request)
                        .getContext()
                        .getGatewayConfigVOList();

        DefaultFlag wxOpenFlag =
                wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.H5);

        boolean lakalaFlag =
                payGatewayConfigList.stream()
                        .anyMatch(
                                config ->
                                        PayGatewayEnum.LAKALA.equals(
                                                config.getPayGateway().getName()));
        // directPaymentFlag 0: 非直连 1：直连
        lakalaFlag = lakalaFlag && directPaymentFlag == 0;

        List<PayChannelItemVO> itemList = new ArrayList<>();
        boolean finalLakalaFlag = lakalaFlag;
        payGatewayConfigList.forEach(
                config -> {
                    List<PayChannelItemVO> payChannelItemList =
                            paySettingQueryProvider
                                    .listOpenedChannelItemByGatewayName(
                                            new OpenedChannelItemRequest(
                                                    config.getPayGateway().getName(),
                                                    TerminalType.PC))
                                    .getContext()
                                    .getPayChannelItemVOList();
                    if (CollectionUtils.isNotEmpty(payChannelItemList)) {
                        if (finalLakalaFlag) {
                            if (payChannelItemList.stream()
                                    .noneMatch(
                                            item ->
                                                    Arrays.asList(
                                                                    PayGatewayEnum.ALIPAY,
                                                                    PayGatewayEnum.WECHAT,
                                                                    PayGatewayEnum.UNIONB2B,
                                                                    PayGatewayEnum.UNIONPAY)
                                                            .contains(
                                                                    item.getGateway().getName()))) {
                                itemList.addAll(payChannelItemList);
                            }
                        } else {
                            if (payChannelItemList.stream()
                                    .noneMatch(
                                            item ->
                                                    PayGatewayEnum.LAKALA.equals(
                                                            item.getGateway().getName()))) {
                                itemList.addAll(payChannelItemList);
                            }
                        }
                    }
                });

        // 如果是关的，则关闭渠道
        if (DefaultFlag.NO.equals(wxOpenFlag) && !lakalaFlag) {
            itemList.stream()
                    .filter(i -> PayWay.WECHAT.name().equalsIgnoreCase(i.getChannel()))
                    .forEach(
                            i -> {
                                i.setIsOpen(IsOpen.NO);
                            });
        }
        return BaseResponse.success(itemList);
    }

    @Operation(summary = "支付前校验是否已支付成功")
    @Parameter(name = "tid", description = "订单号", required = true)
    @RequestMapping(value = "/aliPay/check/{tid}/{flowState}", method = RequestMethod.GET)
    public BaseResponse checkPayState(@PathVariable String tid,@PathVariable String flowState) {
        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                .tid(tid).build()).getContext().getTradeVO();

        String flag = "0";
        if (Objects.nonNull(trade)) {
            if (Objects.nonNull(trade.getTradeState())) {
                if (PayState.PAID.equals(trade.getTradeState().getPayState())) {
                    flag = "1";
                }

                //支付定金页面，已支付定金返回支付成功
                if(Objects.nonNull(flowState) &&
                        flowState.equals(FlowState.WAIT_PAY_EARNEST.toValue()) &&
                        PayState.PAID_EARNEST.equals(trade.getTradeState().getPayState())
                ){
                    flag = "1";
                }
            }
        }
        if(flag.equals("0")){
            //查询拉卡拉收银台大额支付拉起记录
            PayTimeSeriesListResponse payTimeSeriesListResponse = payTimeSeriesQueryProvider.list(PayTimeSeriesListRequest.builder()
                    .businessId(tid)
                    .payChannelType("LAKALACASHER_LKLAT")
                    .build()).getContext();
            //如果有拉起记录提醒可能重复支付，不强制
            if(Objects.nonNull(payTimeSeriesListResponse) &&
                    CollectionUtils.isNotEmpty(payTimeSeriesListResponse.getPayTimeSeriesVOList())){
                flag = "2";
            }
        }
        return BaseResponse.success(flag);
    }


    /**
     * 获取可用支付项
     *
     * @return
     */
    @Operation(summary = "获取可用支付项")
    @RequestMapping(value = "/items/{type}", method = RequestMethod.POST)
    public BaseResponse<List<PayChannelItemVO>> items(@PathVariable String type,
                                                      @RequestBody PayItemsRequest payItemsRequest) {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<PayGatewayConfigVO> payGatewayConfigList = paySettingQueryProvider.listOpenedGatewayConfig(request).getContext()
                .getGatewayConfigVOList();

        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.valueOf(type));
        final String terminalType = TerminalType.PC.name();

        boolean lakalaFlag =
                payGatewayConfigList.stream().anyMatch(config -> PayGatewayEnum.LAKALA.equals(config.getPayGateway().getName()));
        if (Objects.nonNull(payItemsRequest)){
            lakalaFlag = lakalaFlag && !payItemsRequest.getDirectPaymentFlag();
        }

        // 定金是否拉卡拉支付
        boolean onlyLakala = false;
        // 定金预售订单
        boolean earnestMoneyTrade = false;
        if (
                lakalaFlag &&
                        Objects.nonNull(payItemsRequest) &&
                        StringUtils.isNotBlank(payItemsRequest.getTid())) {
            TradeVO tradeVO =
                    tradeQueryProvider.getOrderById(TradeGetByIdRequest.builder().tid(payItemsRequest.getTid()).build()).getContext().getTradeVO();

            if (BookingType.EARNEST_MONEY.equals(tradeVO.getBookingType()) &&
                    !FlowState.WAIT_PAY_EARNEST.equals(tradeVO.getTradeState().getFlowState())
            ){
                if(Objects.isNull(payItemsRequest.getIsBookingSaleGoods())){
                    earnestMoneyTrade = true;
                    if (PayWay.LAKALA.equals(tradeVO.getPayWay())){
                        onlyLakala = true;
                    }
                }
            }
        }

        List<PayChannelItemVO> itemList = new ArrayList<>();
        boolean finalLakalaFlag = lakalaFlag;
        boolean finalOnlyLakala = onlyLakala;
        boolean finalEarnestMoneyTrade = earnestMoneyTrade;
        payGatewayConfigList.forEach(config -> {
            List<PayChannelItemVO> payChannelItemList = paySettingQueryProvider.listOpenedChannelItemByGatewayName(new
                    OpenedChannelItemRequest(
                    config.getPayGateway().getName(), TerminalType.valueOf(terminalType))).getContext().getPayChannelItemVOList();
            if (CollectionUtils.isNotEmpty(payChannelItemList)) {
                if (finalLakalaFlag){
                    // 定金预售的订单。定金如是拉卡拉支付，尾款仅能是拉卡拉支付。
                    // 定金是余额、授信等方式支付，尾款则不可用拉卡拉支付
                    if (finalEarnestMoneyTrade){
                        if (finalOnlyLakala){
                            List<PayChannelItemVO> lakalaItemList =
                                    payChannelItemList.stream().filter(g -> PayGatewayEnum.LAKALA.equals(g.getGateway().getName()))
                                            .collect(Collectors.toList());
                            itemList.addAll(lakalaItemList);
                        } else {
                            List<PayChannelItemVO> lakalaItemList =
                                    payChannelItemList.stream().filter(g -> Arrays.asList(PayGatewayEnum.BALANCE,
                                            PayGatewayEnum.CREDIT).contains(g.getGateway().getName()))
                                            .collect(Collectors.toList());
                            itemList.addAll(lakalaItemList);
                        }
                    } else if (payChannelItemList.stream().noneMatch(item -> Arrays.asList(PayGatewayEnum.ALIPAY,
                            PayGatewayEnum.WECHAT, PayGatewayEnum.UNIONB2B, PayGatewayEnum.UNIONPAY).contains(item.getGateway().getName()))){
                        itemList.addAll(payChannelItemList);
                    }
                } else {
                    if (payChannelItemList.stream().noneMatch(item -> PayGatewayEnum.LAKALA.equals(item.getGateway().getName()))){
                        itemList.addAll(payChannelItemList);
                    }
                }
            }
        });

        List<PayChannelItemVO> payItemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(itemList)) {
            //视频号只返回微信支付
            if (Objects.nonNull(payItemsRequest)
                    && Boolean.TRUE.equals(payItemsRequest.getIsChannelsFlag())
                    && commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
                payItemList.addAll(itemList.stream()
                        .filter(p -> PayWay.WECHAT.name().equalsIgnoreCase(p.getChannel())).collect(Collectors.toList()));
            } else if (Objects.nonNull(payItemsRequest) && Boolean.TRUE.equals(payItemsRequest.getCrossBorderFlag())) {
                // 如果是跨境订单需要 仅展示SBC与跨境中重复的支付方式且对应订单所属电子口岸绑定的支付企业的支付方式集合
                BaseResponse<PlatformPayCompanyListResponse> companyListPay = platformPayChannelProvider.payCompanyList();
                if (Objects.nonNull(companyListPay)
                        && CollectionUtils.isNotEmpty(companyListPay.getContext().getPlatformPayCompanyList())) {
                    List<Long> payCompanyVOList = companyListPay.getContext().getPlatformPayCompanyList()
                            .stream().map(PlatformPayCompanyVO::getGatewayId).collect(Collectors.toList());
                    payItemList.addAll(itemList.stream()
                            .filter(p -> payCompanyVOList.contains(p.getGateway().getId())).collect(Collectors.toList()));
                }
            } else {
                payItemList.addAll(itemList);
            }
        }

        //如果是关的，则关闭渠道
        if (DefaultFlag.NO.equals(wxOpenFlag) && !lakalaFlag) {
            payItemList.stream().filter(i -> PayWay.WECHAT.name().equalsIgnoreCase(i.getChannel())).forEach(i -> {
                i.setIsOpen(IsOpen.NO);
            });
        }
        Optional<PayChannelItemVO> lakalaPayCasher = payItemList.stream().filter(p -> "lakalacashier_pc".equals(p.getCode())).findFirst();
        List<Long> companyInfoIds = new ArrayList<>();
        if(lakalaPayCasher.isPresent() && StringUtils.isNotBlank(payItemsRequest.getParentTid())) {
            List<TradeVO> tradeVOs =
                    tradeQueryProvider.getListByParentId(TradeListByParentIdRequest.builder().parentTid(payItemsRequest.getParentTid()).build()).getContext().getTradeVOList();
            if(CollectionUtils.isNotEmpty(tradeVOs)){
                companyInfoIds.addAll(tradeVOs.stream().map(TradeVO::getSupplier).map(SupplierVO::getSupplierId).collect(Collectors.toList()));
            }
        } else if(lakalaPayCasher.isPresent() && StringUtils.isNotBlank(payItemsRequest.getTid())){
            TradeVO tradeVO =
                    tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(payItemsRequest.getTid()).build()).getContext().getTradeVO();
            if(Objects.nonNull(tradeVO)){
                companyInfoIds.add(tradeVO.getSupplier().getSupplierId());
            }
        }
        if(CollectionUtils.isNotEmpty(companyInfoIds)){
            payItemList.stream().filter(p -> "lakalacashier_pc".equals(p.getCode())).forEach(i -> {
                if(Objects.isNull(i.getLakalaCasherPayItemVO())){
                    i.setLakalaCasherPayItemVO(new LakalaCasherPayItemVO());
                }
            });
            //去重
            companyInfoIds = companyInfoIds.stream().distinct().collect(Collectors.toList());
            //查询清分账户，过滤掉没有终端号的支付方式
            LedgerAccountListResponse ledgerAccounts = ledgerAccountQueryProvider.list(LedgerAccountListRequest.builder().businessIds(companyInfoIds.stream().map(String::valueOf).collect(Collectors.toList())).build()).getContext();
            if(Objects.isNull(ledgerAccounts) || CollectionUtils.isEmpty(ledgerAccounts.getLedgerAccountVOList())){
                return BaseResponse.success(payItemList);
            }
            //扫码终端号不为空的商户数
            long termNoCount = ledgerAccounts.getLedgerAccountVOList().stream().filter(ledgerAccountVO -> StringUtils.isNotBlank(ledgerAccountVO.getTermNo())).count();
            if(termNoCount == companyInfoIds.size()){
                payItemList.stream().filter(p -> "lakalacashier_pc".equals(p.getCode())).forEach(i -> {
                    if(Objects.isNull(i.getLakalaCasherPayItemVO())){
                        i.setLakalaCasherPayItemVO(new LakalaCasherPayItemVO());
                    }
                    i.getLakalaCasherPayItemVO().setAlipayStatus(DefaultFlag.YES);
                    i.getLakalaCasherPayItemVO().setWechatStatus(DefaultFlag.YES);
                    i.getLakalaCasherPayItemVO().setUnionStatus(DefaultFlag.YES);
                });
            }

            //银行卡终端号不为空的商户数
            long bankTermNoCount = ledgerAccounts.getLedgerAccountVOList().stream().filter(ledgerAccountVO -> StringUtils.isNotBlank(ledgerAccountVO.getBankTermNo())).count();
            if(bankTermNoCount == companyInfoIds.size()){
                payItemList.stream().filter(p -> "lakalacashier_pc".equals(p.getCode())).forEach(i -> {
                    if(Objects.isNull(i.getLakalaCasherPayItemVO())){
                        i.setLakalaCasherPayItemVO(new LakalaCasherPayItemVO());
                    }
                    i.getLakalaCasherPayItemVO().setLklAtStatus(DefaultFlag.YES);
                    //暂不开通
//                    i.getLakalaCasherPayItemVO().setCardStatus(DefaultFlag.YES);
                });
            }

            //快捷端号不为空的商户数
            long quickPayTermNoCount = ledgerAccounts.getLedgerAccountVOList().stream().filter(ledgerAccountVO -> StringUtils.isNotBlank(ledgerAccountVO.getQuickPayTermNo())).count();
            if(quickPayTermNoCount == companyInfoIds.size()){
                payItemList.stream().filter(p -> "lakalacashier_pc".equals(p.getCode())).forEach(i -> {
                    if(Objects.isNull(i.getLakalaCasherPayItemVO())){
                        i.setLakalaCasherPayItemVO(new LakalaCasherPayItemVO());
                    }
                    i.getLakalaCasherPayItemVO().setQuickPayStatus(DefaultFlag.YES);
                });
            }

            //银行卡网银终端号不为空的商户数
            long unionTermNoCount = ledgerAccounts.getLedgerAccountVOList().stream().filter(ledgerAccountVO -> StringUtils.isNotBlank(ledgerAccountVO.getUnionTermNo())).count();
            if(unionTermNoCount == companyInfoIds.size()){
                payItemList.stream().filter(p -> "lakalacashier_pc".equals(p.getCode())).forEach(i -> {
                    i.getLakalaCasherPayItemVO().setEBankStatus(DefaultFlag.YES);
                    //暂不开通
//                    i.getLakalaCasherPayItemVO().setUnionCCStatus(DefaultFlag.YES);
                });
            }
        }

        return BaseResponse.success(payItemList);
    }

    /*
     * @Description: 支付表单
     * @Param:
     * @Author: Bob->
     * @Date: 2019-02-01 11:12
     */
    @Deprecated
    @Operation(summary = "支付宝支付表单，该请求需新打开一个空白页，返回的支付宝脚本会自动提交重定向到支付宝收银台")
    @Parameter(name = "encrypted", description = "base64编码后的支付请求",
            required = true)
    @RequestMapping(value = "/aliPay/{encrypted}", method = RequestMethod.GET)
    @GlobalTransactional
    public void aliPay(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        PayMobileRequest payMobileRequest = JSON.parseObject(decrypted, PayMobileRequest.class);
        log.info("====================支付宝支付表单================payMobileRequest :{}",payMobileRequest);

        payMobileRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        String id = payServiceHelper.getPayBusinessId(payMobileRequest.getTid(), payMobileRequest.getParentTid(), payMobileRequest.getRepayOrderCode());
        List<TradeVO> trades = payServiceHelper.checkTrades(id);
        //订单金额
        BigDecimal totalPrice;
        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        if(creditRepayFlag){
            totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
        }else {
            if (commonUtil.lakalaPayIsOpen()){
                log.warn("非法支付渠道：订单ID：{}，用户ID：{}", id, trades.get(0).getBuyer().getAccount());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
        }

        //组装请求数据
        PayExtraRequest payExtraRequest = new PayExtraRequest();
        payExtraRequest.setStoreId(payMobileRequest.getStoreId());
        payExtraRequest.setSuccessUrl(payMobileRequest.getSuccessUrl());
        payExtraRequest.setOutTradeNo(id);
        payExtraRequest.setChannelItemId(payMobileRequest.getChannelItemId());
        payExtraRequest.setTerminal(TerminalType.PC);
        payExtraRequest.setAmount(totalPrice);
        payExtraRequest.setOpenId(payMobileRequest.getOpenId());
        payExtraRequest.setCreditRepayFlag(payServiceHelper.isCreditRepayFlag(id));
        //组装商品信息
        TradeVO trade = trades.get(0);
        String title = trade.getTradeItems().get(0).getSkuName();
        String body = trade.getTradeItems().get(0).getSkuName() + " " + (trade.getTradeItems().get(0).getSpecDetails
                () == null ? "" : trade.getTradeItems().get(0).getSpecDetails());
        if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
            if (title.length() > Constants.NUM_23) {
                title = title.substring(0, 22) + "...  等多件商品";
            } else {
                title = title + " 等多件商品";
            }
            body = body + " 等多件商品";
        } else {
            if (title.length() > Constants.NUM_29) {
                title = title.substring(0, 28) + "...";
            }
        }
        payExtraRequest.setSubject(title);
        payExtraRequest.setBody(body);
        payExtraRequest.setClientIp(HttpUtil.getIpAddr());
        payExtraRequest.setTerminal(TerminalType.PC);
        try {
            //保存支付请求信息到插件
            PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
            pluginPayInfoAddRequest.setOrderCode(trade.getId());
            pluginPayInfoAddRequest.setPayRequest(payExtraRequest.toString());
            pluginPayInfoProvider.add(pluginPayInfoAddRequest);
            //form是一段js脚本，必须返回到一个没有任何代码的空白页，脚本会自动提交重定向到支付宝的收银台
            LinkedHashMap linkedHashMap = (LinkedHashMap)payProvider.pay(BasePayRequest.builder()
                    .payType(PayType.ALIPAY)
                    .payExtraRequest(payExtraRequest)
                    .build()).getContext();
            String form = String.valueOf(linkedHashMap.get("form"));
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (SbcRuntimeException e) {
            if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060003.getCode())) {
                //已支付，手动回调
                Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("1").name("SYSTEM").platform
                        (Platform.BOSS).build();
                if (!payExtraRequest.getCreditRepayFlag()) {
                    //订单回调
                    List<TradePayCallBackOnlineDTO> list = new ArrayList<>();
                    trades.forEach(i -> {
                        //获取订单信息
                        PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest.builder()
                                .payOrderId(i.getPayOrderId()).build()).getContext().getPayOrder();
                        TradePayCallBackOnlineDTO dto = TradePayCallBackOnlineDTO.builder()
                                .payOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class))
                                .trade(KsBeanUtil.convert(i, TradeDTO.class))
                                .build();
                        list.add(dto);

                    });
                    tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                                                            .requestList(list)
                                                            .operator(operator)
                                                            .build());
                    if (CollectionUtils.isEmpty(list)) {
                        esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                                .customerId(trades.get(0).getBuyer().getId())
                                .build());
                    }
                }
            }
            throw e;
//            throw new SbcRuntimeException(e.getErrorCode().to, e.getParams());
        } catch (IOException e) {
            // TODO: 2019-01-28 gb支付异常未处理
            log.error("execute alipay has IO exception:", e);
        }
    }

    /**
     * 银联企业支付
     *
     * @param tradeId  单笔支付场景为订单编号，多笔合并支付场景为父订单编号
     * @param response 银联交互的方式比较特殊，是返回html给前台，并没有加上重复提交注解 - @MultiSubmit
     *                 因为注解throw返回的是code和message，显示在前台不友好，所以单独判断重复提交，重复则返回提示字符串到前台
     */
//    @Operation(summary = "银联企业支付")
//    @Parameter(paramType = "path", dataType = "String", name = "tradeId", value = "订单编号", required = true)
//    @RequestMapping(value = "/unionB2BPay/{tradeId}", method = RequestMethod.GET)
//    @GlobalTransactional
//    public void unionB2BPay(@PathVariable("tradeId") String tradeId, HttpServletResponse response) {
//        log.info("====================银联支付逻辑开始================");
//        String unionPayKey = "unionB2BPay:".concat(tradeId);
//        String html = "";
//        try {
//            try {
//                if (redisService.setNx(unionPayKey)) {
//                    redisService.expireBySeconds(unionPayKey, 10L);
//                    List<TradeVO> trades = payServiceHelper.checkTrades(tradeId);
//                    TradeVO trade = trades.get(0);
//
//                    String title = trade.getTradeItems().get(0).getSkuName();
//                    String body =
//                            trade.getTradeItems().get(0).getSkuName() + " " + (trade.getTradeItems().get(0).getSpecDetails
//                                    () == null ? "" : trade.getTradeItems().get(0).getSpecDetails());
//                    if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
//                        if (title.length() > 23) {
////                title = String.format("s%s% %s", title.substring(0, 22), "...", "  等多件商品");
//                            title = title.substring(0, 22) + "...  等多件商品";
//                        } else {
//                            title = title + " 等多件商品";
//                        }
//                        body = body + " 等多件商品";
//                    } else {
//                        if (title.length() > 29) {
//                            title = title.substring(0, 28) + "...";
//                        }
//                    }
//                    // 支付总金额
//                    BigDecimal totalPrice;
//                    Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(tradeId);
//                    if(creditRepayFlag){
//                        totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
//                    }else {
//                        totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
//                    }
//                    UnionPayRequest unionPayRequest = new UnionPayRequest();
//                    unionPayRequest.setAmount(totalPrice);
//                    unionPayRequest.setBusinessId(tradeId);
//
//                    PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
//                            GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONB2B, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
//                    unionPayRequest.setApiKey(payGatewayConfigResponse.getApiKey());
//                    unionPayRequest.setFrontUrl(payGatewayConfigResponse.getPcBackUrl() + "/pay/pay-success");
//                    unionPayRequest.setNotifyUrl(payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback" +
//                            "/unionB2BCallBack");
//                    unionPayRequest.setTerminal(TerminalType.PC);
//                    unionPayRequest.setSubject(title);
//                    unionPayRequest.setBody(body);
//                    unionPayRequest.setChannelItemId(11L);
//                    unionPayRequest.setClientIp(HttpUtil.getIpAddr());
//                    html = paySettingProvider.unionB2BPay(unionPayRequest).getContext();
//                    if(!creditRepayFlag) {
//                        trade.getTradeState().setStartPayTime(LocalDateTime.now());
//                        tradeProvider.update(TradeUpdateRequest.builder().trade(KsBeanUtil.convert(trade,
//                                TradeUpdateDTO.class)).build());
//                    }
//                } else {
//                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999997);
//                }
//            } catch (SbcRuntimeException e) {
//                html = new BaseResponse<>(e.getErrorCode()).getMessage();
//                response.addHeader("Access-Control-Allow-Origin", "*");
//                response.setContentType("application/json;charset=utf-8");
//            } catch (Exception e) {
//                log.error("银联支付异常 ", e);
//            } finally {
//                if (StringUtils.isBlank(html)) {
//                    html = "发生未知错误，请重试";
//                }
//                response.getWriter().write(html);
//            }
//        } catch (IOException e) {
//            log.error("将生成的html写到浏览器失败", e);
//        }
//    }

    /**
     * 新银联企业支付
     *
     * @param tradeId  单笔支付场景为订单编号，多笔合并支付场景为父订单编号
     * @param response 银联交互的方式比较特殊，是返回html给前台，并没有加上重复提交注解 - @MultiSubmit
     *                 因为注解throw返回的是code和message，显示在前台不友好，所以单独判断重复提交，重复则返回提示字符串到前台
     */
    @Deprecated
    @Operation(summary = "新银联企业支付")
    @Parameter(name = "tradeId", description = "订单编号", required = true)
    @RequestMapping(value = "/unionB2BPay/{tradeId}", method = RequestMethod.GET)
    @GlobalTransactional
    public void newUnionB2BPay(@PathVariable("tradeId") String tradeId, HttpServletResponse response) {
        log.info("====================新银联支付逻辑开始================");
        String unionPayKey = "unionB2BPay".concat(tradeId);
        String html = "";
        try {
            try {
                if (redisService.setNx(unionPayKey)) {
                    redisService.expireBySeconds(unionPayKey, 10L);
                    List<TradeVO> trades = payServiceHelper.checkTrades(tradeId);
                    TradeVO trade = trades.get(0);
                    String title = trade.getTradeItems().get(0).getSkuName();
                    String body =
                            trade.getTradeItems().get(0).getSkuName() + " " + (trade.getTradeItems().get(0).getSpecDetails
                                    () == null ? "" : trade.getTradeItems().get(0).getSpecDetails());
                    if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
                        if (title.length() > Constants.NUM_23) {
                            title = title.substring(0, 22) + "...  等多件商品";
                        } else {
                            title = title + " 等多件商品";
                        }
                        body = body + " 等多件商品";
                    } else {
                        if (title.length() > Constants.NUM_29) {
                            title = title.substring(0, 28) + "...";
                        }
                    }
                    // 支付总金额
                    BigDecimal totalPrice;

                    Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(tradeId);
                    if(creditRepayFlag){
                        totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
                    }else {
                        totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
                    }

                    UnionPayRequest unionPayRequest = new UnionPayRequest();
                    unionPayRequest.setAmount(totalPrice);
                    unionPayRequest.setOutTradeNo(tradeId);

                    PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
                            GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONB2B, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
                    unionPayRequest.setApiKey(payGatewayConfigResponse.getApiKey());
                    if(creditRepayFlag){
                        unionPayRequest.setFrontUrl(payGatewayConfigResponse.getPcWebUrl() + "/credit-repayment-success/"+tradeId);
                    }else{
                        if (commonUtil.lakalaPayIsOpen()){
                            log.warn("非法支付渠道：订单ID：{}，用户ID：{}", tradeId, trades.get(0).getBuyer().getAccount());
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                        }
                        unionPayRequest.setFrontUrl(payGatewayConfigResponse.getPcWebUrl() + "/pay-success/"+tradeId);
                    }

                    unionPayRequest.setNotifyUrl(payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback/UnionB2BCallBack");
                    unionPayRequest.setTerminal(TerminalType.PC);
                    unionPayRequest.setSubject(title);
                    unionPayRequest.setBody(body);
                    unionPayRequest.setChannelItemId(11L);
                    unionPayRequest.setClientIp(HttpUtil.getIpAddr());
                    html = (String) payProvider.pay(BasePayRequest.builder()
                            .payType(PayType.UNIONB2BPAY)
                            .unionPayRequest(unionPayRequest)
                            .build()).getContext();
//                    html = payProvider.newUnionB2BPay(unionPayRequest).getContext();
                    if(!creditRepayFlag){
                        trade.getTradeState().setStartPayTime(LocalDateTime.now());
                        tradeProvider.update(TradeUpdateRequest.builder().trade(KsBeanUtil.convert(trade,
                                TradeDTO.class)).build());
                    }


                } else {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999997);
                }
            } catch (SbcRuntimeException e) {
                html = BaseResponse.info(e.getErrorCode(),e.getMessage()).getMessage();
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json;charset=utf-8");
            } catch (Exception e) {
                log.error("银联支付异常，{}", e);
            } finally {
                if (StringUtils.isBlank(html)) {
                    html = "发生未知错误，请重试";
                }
                response.getWriter().write(html);
            }
        } catch (IOException e) {
            log.error("将生成的html写到浏览器失败", e);
        }
    }
    /**
     * 银联企业支付同步回调
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Operation(summary = "银联企业支付同步回调")
    @RequestMapping(value = "/pay-success", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView unionPaySuccess(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        log.info("银联企业支付同步回调前台接收报文返回开始");
        String encoding = request.getParameter("encoding");
        log.info("返回报文中encoding=[{}]", encoding);
        Map<String, String> respParam = getAllRequestParam(request);
        PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
                GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONB2B,Constants.BOSS_DEFAULT_STORE_ID)).getContext();
        if (null != respParam && !respParam.isEmpty() && Constants.STR_00.equals(respParam.get("respCode"))) {

            if (!unionB2BProvider.unionCheckSign(respParam).getContext()) {

                log.info("验证签名结果[失败].");

            } else {
                log.info("验证签名结果[成功].");
//                    UnionPayRequest unionPayRequest = new UnionPayRequest();
//                    unionPayRequest.setApiKey(request.getParameter("merId"));
//                    unionPayRequest.setBusinessId(request.getParameter("orderId"));
//                    unionPayRequest.setTxnTime(request.getParameter("txnTime"));
//                    Map<String,String> resultMap = paySettingQueryProvider.getUnionPayResult(unionPayRequest).getContext();
//                    if("00".equals(resultMap.get("respCode")) && "00".equals(resultMap.get("origRespCode"))){
//                        paySettingProvider.unionCallBack(respParam);
//                        Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name("UNIONB2B")
//                                .account("UNIONB2B").platform
//                                (Platform.THIRD)
//                                .build();
//                        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(respParam.get
//                        ("orderId"))
//                                .build()).getContext().getTradeVO();
//                        PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest
//                                .builder().payOrderId(trade.getPayOrderId()).build()).getContext().getPayOrder();
                //修改订单状态
//                        tradeProvider.payCallBackOnline(TradePayCallBackOnlineRequest.builder()
//                                .trade(KsBeanUtil.convert(trade, TradeDTO.class))
//                                .payOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class))
//                                .operator(operator)
//                                .build());
//                    }

            }

        }
        log.info("银联企业支付同步回调结束");
        return new ModelAndView(new RedirectView(payGatewayConfigResponse.getPcWebUrl() + "/pay-success/" + respParam.get
                ("orderId")));
    }

    /**
     * 微信扫码支付统一下单接口获取二维码接口
     *
     * @param weiXinPayRequest
     * @return
     */
    @Deprecated
    @Operation(summary = "微信扫码支付统一下单接口获取二维码接口，返回pc端页面生成二维码所需参数")
    @RequestMapping(value = "/unifiedorderForNative", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse unifiedorderForNative(@RequestBody @Valid WeiXinPayRequest weiXinPayRequest) {
        //PC沿用H5公众号验证支付开关
        WechatSetResponse setResponse = wechatSetService.get(com.wanmi.sbc.common.enums.TerminalType.H5);
        if (DefaultFlag.NO.equals(setResponse.getMobileServerStatus())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        WxPayForNativeRequest nativeRequest = new WxPayForNativeRequest();
        String id = payServiceHelper.getPayBusinessId(weiXinPayRequest.getTid(), weiXinPayRequest.getParentTid(), weiXinPayRequest.getRepayOrderCode());
        List<TradeVO> trades = payServiceHelper.checkTrades(id);
        //订单金额
        String totalPrice;
        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        if(creditRepayFlag){
            totalPrice = payServiceHelper.calcCreditTotalPriceByPenny(trades).toString();
        }else {
            if (commonUtil.lakalaPayIsOpen()){
                log.warn("非法支付渠道：订单ID：{}，用户ID：{}", id, trades.get(0).getBuyer().getAccount());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            totalPrice = payServiceHelper.calcTotalPriceByPenny(trades).toString();
        }
        nativeRequest.setOut_trade_no(id);
        nativeRequest.setTotal_fee(totalPrice);
        nativeRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        String body = payServiceHelper.buildBody(trades);
        TradeVO trade = trades.get(0);
        String productId = trade.getTradeItems().get(0).getSkuId();
        if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
            body = body + " 等多件商品";
        }
        nativeRequest.setBody(body + "订单");
        nativeRequest.setProduct_id(productId);
        nativeRequest.setTrade_type(WxPayTradeType.NATIVE.toString());
        nativeRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        nativeRequest.setAppid(setResponse.getPcAppId());
        //保存支付请求信息到插件
        PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
        pluginPayInfoAddRequest.setOrderCode(trade.getId());
        pluginPayInfoAddRequest.setPayRequest(nativeRequest.toString());
        pluginPayInfoProvider.add(pluginPayInfoAddRequest);
        return payProvider.pay(BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.ZERO)
                .wxPayForNativeRequest(nativeRequest)
                .build());
    }

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
            }
        }
        return res;
    }





    /**
     * 银联云闪付支付
     *
     * @param encrypted
     * @param response
     */
    @Deprecated
    @Operation(summary = "银联云闪付支付消费支付接口，返回html")
    @Parameter(name = "encrypted", description = "base64编码后的支付请求", required = true)
    @RequestMapping(value = "/pay-union/{encrypted}", method = RequestMethod.GET)
    @GlobalTransactional
    public void payUnionCloud(@PathVariable("encrypted") String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        PayMobileRequest payMobileRequest = JSON.parseObject(decrypted, PayMobileRequest.class);
        log.info("====================银联支付逻辑开始================");
        String tradeId = payServiceHelper.getPayBusinessId(payMobileRequest.getTid(), payMobileRequest.getParentTid(),payMobileRequest.getRepayOrderCode());
        String html = "";
        UnionPayRequest  unionPayRequest = new UnionPayRequest();
        List<TradeVO> trades = payServiceHelper.checkTrades(tradeId);
        try {
            try {
                //1.支付前检查订单状态

                TradeVO trade = trades.get(0);
                //2..计算价格
                log.info("=====================计算价格开始==================");
                //订单金额
                BigDecimal totalPrice;
                Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(tradeId);
                LocalDateTime orderTimeOut;
                if(creditRepayFlag){
                    totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
                    orderTimeOut = LocalDateTime.now().plusMinutes(10L);
                }else {
                    if (commonUtil.lakalaPayIsOpen()){
                        log.warn("非法支付渠道：订单ID：{}，用户ID：{}", tradeId, trades.get(0).getBuyer().getAccount());
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                    }
                    totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
                    orderTimeOut = trade.getOrderTimeOut();
                }

                log.info("===================计算价格结束 totalPrice：{}==============", totalPrice);
                //3.组装消费接口数据的body  title 获取相应的支付网关配置
                Map<String, String> dataMap = wrapperRequestAndCheckTrades(trades, trade);
                PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
                        GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONPAY, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
                //4.组装消费接口数据
                log.info("payUnionCloud组装银联云闪付支付数据开始");
                unionPayRequest.setOutTradeNo(tradeId);
                unionPayRequest.setSubject(dataMap.get("title"));
                unionPayRequest.setBody(dataMap.get("body"));
                unionPayRequest.setAmount(totalPrice);
                unionPayRequest.setApiKey(payGatewayConfigResponse.getApiKey());
                unionPayRequest.setFrontUrl(payMobileRequest.getSuccessUrl());
                unionPayRequest.setNotifyUrl(payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback" +
                        "/unionPayCallBack");
                unionPayRequest.setTerminal(TerminalType.PC);
                //银联支付pc渠道编号
                unionPayRequest.setChannelItemId(payMobileRequest.getChannelItemId());
                unionPayRequest.setClientIp(HttpUtil.getIpAddr());
                //订单有效时间最后的有效时间
                unionPayRequest.setOrderTimeOut(orderTimeOut);
                unionPayRequest.setCreditRepayFlag(payServiceHelper.isCreditRepayFlag(tradeId));
                log.info("payUnionCloud组装调用支付provider开始 requset:{}", unionPayRequest);
                //5.调用银联支付接口
//                html = unionCloudPayProvider.payUnionCloud(unionPayRequest).getContext();
                html = (String) payProvider.pay(BasePayRequest.builder()
                        .payType(PayType.UNIONCLONDPAY)
                        .unionPayRequest(unionPayRequest)
                        .build()).getContext();
                log.info("payUnionCloud组装调用支付provider结束,开始更新订单数据");

                //6.初步更新订单的开始支付时间数据
                trade.getTradeState().setStartPayTime(LocalDateTime.now());
                tradeProvider.update(TradeUpdateRequest.builder().trade(KsBeanUtil.convert(trade,
                        TradeDTO.class)).build());
                log.info("结束更新订单数据，订单号：{}, 开始支付时间：{}", trade.getId(), trade.getTradeState().getStartPayTime());
            } catch (SbcRuntimeException e) {
                log.error("银联支付异常", e);
                if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060003.getCode())) {
                    //已支付，手动回调
                    Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("1").name("SYSTEM").platform
                            (Platform.BOSS).build();
                    if (!unionPayRequest.getCreditRepayFlag()) {
                        //订单回调
                        List<TradePayCallBackOnlineDTO> list = new ArrayList<>();
                        trades.forEach(i -> {
                            //获取订单信息
                            PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest.builder()
                                    .payOrderId(i.getPayOrderId()).build()).getContext().getPayOrder();
                            TradePayCallBackOnlineDTO dto = TradePayCallBackOnlineDTO.builder()
                                    .payOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class))
                                    .trade(KsBeanUtil.convert(i, TradeDTO.class))
                                    .build();
                            list.add(dto);

                        });
                        tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                                .requestList(list)
                                .operator(operator)
                                .build());
                    }
                }
                html = BaseResponse.info(e.getErrorCode(),e.getMessage()).getMessage();
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json;charset=utf-8");
            } catch (Exception e) {
                log.error("银联支付异常", e);
            } finally {
                if (StringUtils.isBlank(html)) {
                    html = "发生未知错误，请重试";
                }
                response.getWriter().write(html);
            }
        } catch (IOException e) {
            log.error("银联支付将生成的html写到浏览器失败", e);

        }
    }


    @Operation(summary = "银联云闪付支付查询接口")
    @Parameter(name = "tradeId", description = "订单编号", required = true)
    @RequestMapping(value = "/get-union-cloud/{tradeId}", method = RequestMethod.GET)
    public void getUnionCloudResult(@PathVariable("tradeId") String tradeId) {
        PayOrderDetailRequest unionPayRequest = new PayOrderDetailRequest();
        unionPayRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        unionPayRequest.setBusinessId(tradeId);
        BaseResponse<Map<String, String>> unionCloudPayResult =
                payProvider.getPayOrderDetail(unionPayRequest);
        log.info("{}==========================数据", unionCloudPayResult.getContext());
    }


    /***
     * 银联云闪付支付更新证书接口
     *
     * @date 17:18 2021/3/18
     * @author zhangyong
     * @param
     * @return   {@link BaseResponse}
     */
    @Operation(summary = "银联云闪付支付更新证书接口")
    @RequestMapping(value = "/get-update-encryptCer", method = RequestMethod.POST)
    public BaseResponse getUnionCloudResult() {
        log.info("银联云闪付支付更新证书接口开始==================");
        PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
                GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONPAY, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
        UnionPayRequest unionPayRequest = new UnionPayRequest();
        unionPayRequest.setApiKey(payGatewayConfigResponse.getApiKey());
        BaseResponse<Map<String, String>> unionCloudPayResult =
                unionCloudPayProvider.getUpdateEncryptCer(unionPayRequest);
        log.info("{}==========================数据", unionCloudPayResult.getContext());
        return unionCloudPayResult;
    }


    /***
     * 银联云闪付退款接口
     *
     * @date 17:18 2021/3/18
     * @author zhangyong
     * @param tradeId
     * @return   {@link BaseResponse}
     */
    @Operation(summary = "银联云闪付退款接口")
    @RequestMapping(value = "/refund/{tradeId}", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse refund(@PathVariable("tradeId") String tradeId) {
        UnionCloudPayRefundRequest unionPayRequest = new UnionCloudPayRefundRequest();
        unionPayRequest.setBusinessId(tradeId);
        PayRefundBaseRequest payRefundBaseRequest = new PayRefundBaseRequest();
        payRefundBaseRequest.setUnionCloudPayRefundRequest(unionPayRequest);
        payRefundBaseRequest.setPayType(PayType.UNIONCLONDPAY);
        BaseResponse unionCloudPayResult = payProvider.payRefund(payRefundBaseRequest);
        log.info("{}==========================数据", unionCloudPayResult.getContext());
        return unionCloudPayResult;
    }


    /***
     * 校验订单状态 及获取body 组装初步数据
     *
     * @date 9:08 2021/3/12
     * @author zhangyong
     * @param trades
     * @return   {@link Map<String, String>}
     */
    private Map<String, String> wrapperRequestAndCheckTrades(List<TradeVO> trades, TradeVO trade) {
        Map<String, String> map = new HashMap<>();
        String title = trade.getTradeItems().get(0).getSkuName();
        String body =
                trade.getTradeItems().get(0).getSkuName() + " " + (trade.getTradeItems().get(0).getSpecDetails
                        () == null ? "" : trade.getTradeItems().get(0).getSpecDetails());
        if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
            if (title.length() > Constants.NUM_23) {
                title = title.substring(0, 22) + "...  等多件商品";
            } else {
                title = title + " 等多件商品";
            }
            body = body + " 等多件商品";
        } else {
            if (title.length() > Constants.NUM_29) {
                title = title.substring(0, 28) + "...";
            }
        }
        map.put("title", title);
        map.put("body", body);
        return map;
    }





}