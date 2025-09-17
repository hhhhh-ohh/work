package com.wanmi.sbc.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.QueryByBusinessIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.api.response.ledgeraccount.QueryByBusinessIdsResponse;
import com.wanmi.sbc.customer.api.response.store.ListStoreResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaAllPayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaCasherAllPayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaPayRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaAllPayResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaCasherAllPayResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaCasherTradeQueryResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaTradeQueryResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.LklPayType;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesAddRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesListRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByIdOrPidRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByParentIdRequest;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesListResponse;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.SupplierVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.request.LakalaPayItemRequest;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.response.LakalaCasherPayItemResponse;
import com.wanmi.sbc.pay.response.LakalaPayItemResponse;
import com.wanmi.sbc.pay.service.PayInterface;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.trade.PayServiceHelper;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className PayCommonController
 * @description TODO
 * @date 2022/7/13 16:28
 */
@Tag(name = "PayCommonController")
@Validated
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayCommonController {

    @Autowired
    private PayServiceHelper payServiceHelper;
    @Autowired
    private MiniProgramSetQueryProvider miniProgramSetQueryProvider;
    @Autowired
    private TradeProvider tradeProvider;
    @Autowired
    private PayProvider payProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    PayingMemberRecordTempQueryProvider payingMemberRecordTempQueryProvider;

    @Autowired
    PayTimeSeriesProvider payTimeSeriesProvider;

    @Autowired
    PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Deprecated
    @PostMapping("/lakala/pay")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse lakalaPay(@RequestBody @Valid LakalaPayItemRequest lakalaPayItemRequest) {

        String id, title;
        BigDecimal totalPrice;
        id =
                payServiceHelper.getPayBusinessId(
                        lakalaPayItemRequest.getTid(), lakalaPayItemRequest.getParentTid(), null);
        BaseResponse queryBaseResponse = null;

        // 查询这笔订单有没有拉卡拉支付过
        try {
            queryBaseResponse =
                    payProvider.getPayOrderDetail(PayOrderDetailRequest.builder().payType(PayType.LAKALA_PAY).businessId(id).build());
        } catch (SbcRuntimeException ignored) {
            // 查询不到会抛出异常。不做处理
        }

        LakalaTradeQueryResponse lakalaTradeQueryResponse = null;
        if (Objects.nonNull(queryBaseResponse)) {
            lakalaTradeQueryResponse =
                    JSON.parseObject(JSON.toJSONString(queryBaseResponse.getContext()),
                            LakalaTradeQueryResponse.class);
        }

        if (Objects.isNull(lakalaTradeQueryResponse)
                || "FAIL".equals(lakalaTradeQueryResponse.getTradeState())) {

            List<TradeVO> trades = payServiceHelper.checkTrades(id);
            // 支付总金额
            Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
            if (creditRepayFlag) {
                totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
            } else {
                totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
            }
            TradeVO trade = trades.get(0);
            title = trade.getTradeItems().get(0).getSkuName();
            if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
                if (title.length() > Constants.NUM_23) {
                    title = title.substring(0, 22) + "...  等多件商品";
                } else {
                    title = title + " 等多件商品";
                }
            } else {
                if (title.length() > Constants.NUM_29) {
                    title = title.substring(0, 28) + "...";
                }
            }
            title = title.replace("&", "");
            TradeVO maxPriceTrade =
                    trades.stream()
                            .max(
                                    Comparator.comparing(
                                            trade2 -> trade2.getTradePrice().getTotalPrice()))
                            .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
            LedgerAccountByIdResponse ledgerAccountByIdResponse =
                    ledgerAccountQueryProvider
                            .findByBusiness(
                                    LedgerAccountFindRequest.builder()
                                            .businessId(
                                                    maxPriceTrade
                                                            .getSupplier()
                                                            .getSupplierId()
                                                            .toString())
                                            .setFileFlag(Boolean.FALSE)
                                            .build())
                            .getContext();
            LedgerAccountVO ledgerAccountVO = ledgerAccountByIdResponse.getLedgerAccountVO();

            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && StringUtils.isNotBlank(trade.getTailOrderNo())) {
                id = trade.getTailOrderNo();
            }
            String tradeNo = id.concat(String.valueOf(trade.getPayVersion()));
            List<LakalaAllPayRequest.OutSplitInfo> outSplitInfos = new ArrayList<>();
            List<String> payOrderIds = new ArrayList<>();
            for (TradeVO tradeItem : trades) {
                LakalaAllPayRequest.OutSplitInfo outSplitInfo =
                        new LakalaAllPayRequest.OutSplitInfo();
                String no = tradeItem.getId().concat(String.valueOf(trade.getPayVersion()));
                outSplitInfo.setOutSubTradeNo(no);
                if (maxPriceTrade.getId().equals(tradeItem.getId())) {
                    outSplitInfo.setMerchantNo(ledgerAccountVO.getMerCupNo());
                    outSplitInfo.setTermNo(ledgerAccountVO.getTermNo());
                } else {
                    LedgerAccountByIdResponse accountByIdResponse =
                            ledgerAccountQueryProvider
                                    .findByBusiness(
                                            LedgerAccountFindRequest.builder()
                                                    .businessId(
                                                            tradeItem
                                                                    .getSupplier()
                                                                    .getSupplierId()
                                                                    .toString())
                                                    .setFileFlag(Boolean.FALSE)
                                                    .build())
                                    .getContext();
                    LedgerAccountVO accountVO = accountByIdResponse.getLedgerAccountVO();
                    outSplitInfo.setMerchantNo(accountVO.getMerCupNo());
                    outSplitInfo.setTermNo(accountVO.getTermNo());
                }

                outSplitInfo.setAmount(
                        tradeItem
                                .getTradePrice()
                                .getTotalPrice()
                                .multiply(new BigDecimal(100))
                                .toString());
                outSplitInfos.add(outSplitInfo);
                if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && StringUtils.isNotBlank(trade.getTailOrderNo())) {
                    payOrderIds.add(tradeItem.getTailPayOrderId());
                } else {
                    payOrderIds.add(tradeItem.getPayOrderId());
                }
            }
            LakalaAllPayRequest lakalaAllPayRequest = new LakalaAllPayRequest();
            lakalaAllPayRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
            lakalaAllPayRequest.setTermNo(ledgerAccountVO.getTermNo());
            lakalaAllPayRequest.setOutTradeNo(tradeNo);
            lakalaAllPayRequest.setOutSplitInfo(outSplitInfos);
            lakalaAllPayRequest.setRemark(lakalaPayItemRequest.getChannelItemId());

            lakalaAllPayRequest.setTotalAmount(
                    totalPrice
                            .multiply(new BigDecimal("100"))
                            .setScale(0, RoundingMode.DOWN)
                            .toString());
            LakalaAllPayRequest.LocationInfo locationInfo = new LakalaAllPayRequest.LocationInfo();
            locationInfo.setRequestIp(maxPriceTrade.getRequestIp());
            lakalaAllPayRequest.setLocationInfo(locationInfo);
            lakalaAllPayRequest.setSubject(title);
            Duration duration = null;
            if (trade.getOrderTimeOut() != null) {
                // 拉卡拉支付订单超时时间最大支持99分钟
                duration = Duration.between(LocalDateTime.now(), trade.getOrderTimeOut());
            }

            TerminalType terminalType = lakalaPayItemRequest.getTerminal();
            String appId = StringUtils.EMPTY;
            switch (lakalaPayItemRequest.getLakalaChannelItem()) {
                case ALIPAY:
                    lakalaAllPayRequest.setTransType("41");
                    lakalaAllPayRequest.setAccountType("ALIPAY");
                    LakalaAllPayRequest.AliPayAccBusi aliPayAccBusi =
                            new LakalaAllPayRequest.AliPayAccBusi();
                    if (duration != null) {
                        aliPayAccBusi.setTimeout_express(duration.toMinutes() > 99 ? "99" : duration.toMinutes() + "");
                    }
                    aliPayAccBusi.setQuit_url(lakalaPayItemRequest.getSuccessUrl());
                    lakalaAllPayRequest.setAccBusiFields(aliPayAccBusi);
                    break;
                case WECHAT:
                    if (TerminalType.WX_H5.equals(terminalType)) {
                        lakalaAllPayRequest.setTransType("51");
                    } else if (TerminalType.APP.equals(terminalType)
                            || TerminalType.MINI.equals(terminalType)
                            || TerminalType.PC.equals(terminalType)) {
                        lakalaAllPayRequest.setTransType("71");
                    }

                    BaseResponse<MiniProgramSetByTypeResponse>
                            miniProgramSetByTypeResponseBaseResponse =
                                    miniProgramSetQueryProvider.getByType(
                                            MiniProgramSetByTypeRequest.builder()
                                                    .type(Constants.ZERO)
                                                    .build());
                    if (StringUtils.equals(
                            CommonErrorCodeEnum.K000000.getCode(),
                            miniProgramSetByTypeResponseBaseResponse.getCode())) {
                        MiniProgramSetVO miniProgramSetVO =
                                miniProgramSetByTypeResponseBaseResponse
                                        .getContext()
                                        .getMiniProgramSetVO();
                        // 验证小程序支付开关
                        if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                        }
                        appId = miniProgramSetVO.getAppId();
                    } else {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                    }

                    lakalaAllPayRequest.setAccountType("WECHAT");
                    LakalaPayRequest.WxPayAccBusi wxPayAccBusi =
                            new LakalaPayRequest.WxPayAccBusi();
                    if (duration != null) {
                        wxPayAccBusi.setTimeout_express(duration.toMinutes() > 99 ? "99" : duration.toMinutes() + "");
                    }
                    wxPayAccBusi.setSub_appid(appId);
                    wxPayAccBusi.setUser_id(lakalaPayItemRequest.getOpenId());
                    lakalaAllPayRequest.setAccBusiFields(wxPayAccBusi);
                    break;
                case UNIONPAY:
                    lakalaAllPayRequest.setTransType("41");
                    lakalaAllPayRequest.setAccountType("UQRCODEPAY");
                    LakalaAllPayRequest.uqrCodePayAccBusi uqrCodePayAccBusi =
                            new LakalaAllPayRequest.uqrCodePayAccBusi();
                    uqrCodePayAccBusi.setUser_id(lakalaPayItemRequest.getCode());
                    if (duration != null) {
                        uqrCodePayAccBusi.setTimeout_express(duration.toMinutes() + "");
                    }
                    uqrCodePayAccBusi.setFront_url(lakalaPayItemRequest.getSuccessUrl());
                    break;
                default:
                    break;
            }

            // 更新支付版本号
            tradeProvider.updateVersion(
                    TradeListByIdOrPidRequest.builder()
                            .tid(lakalaPayItemRequest.getTid())
                            .parentTid(lakalaPayItemRequest.getParentTid())
                            .isAddFlag(Boolean.TRUE)
                            .build());

            BaseResponse baseResponse =
                    payProvider.pay(
                            BasePayRequest.builder()
                                    .payType(PayType.LAKALA_PAY)
                                    .lakalaAllPayRequest(lakalaAllPayRequest)
                                    .channelItemId(lakalaPayItemRequest.getChannelItemId())
                                    .build());
            LakalaAllPayResponse allPayResponse =
                    JSONObject.parseObject(
                            JSONObject.toJSONString(baseResponse.getContext()),
                            LakalaAllPayResponse.class);
            return BaseResponse.success(
                    LakalaPayItemResponse.builder()
                            .appId(appId)
                            .respData(allPayResponse.getAccRespFields())
                            .build());
        } else {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        }
    }

    /**
     * 聚合支付
     *
     * @param request
     * @return
     */
    @PostMapping("/integration/pay")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse integrationPay(@RequestBody @Valid PayChannelRequest request) {
        // 获取支付方式对应的bean
        PayInterface payInterface =
                SpringContextHolder.getBean(request.getPayChannelType().getPayService());
        return payInterface.getResult(request);
    }


    @PostMapping("/lakalacasher/pay")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse lakalaCasherPay(@RequestBody @Valid LakalaPayItemRequest lakalaPayItemRequest) {

        String id, title;
        BigDecimal totalPrice;
        id = payServiceHelper.getPayBusinessId(
                lakalaPayItemRequest.getTid(), lakalaPayItemRequest.getParentTid(), null);
        BaseResponse queryBaseResponse = null;

        // 查询这笔订单有没有拉卡拉支付过
        try {
            queryBaseResponse =
                    payProvider.getPayOrderDetail(PayOrderDetailRequest.builder().payType(PayType.LAKALA_CASHER_PAY).businessId(id).build());
        } catch (SbcRuntimeException ignored) {
            // 查询不到会抛出异常。不做处理
            log.error("lakala casher pay get fail: {}", ignored.getMessage(), ignored);
        }

        LakalaCasherTradeQueryResponse lakalaCasherTradeQueryResponse = null;
        if (Objects.nonNull(queryBaseResponse)) {
            lakalaCasherTradeQueryResponse =
                    JSON.parseObject(JSON.toJSONString(queryBaseResponse.getContext()),
                            LakalaCasherTradeQueryResponse.class);
        }

        List<String> notPayStatus = ImmutableList.of(Constants.STR_0, Constants.STR_1, Constants.STR_3);
        if (Objects.isNull(lakalaCasherTradeQueryResponse)
                || notPayStatus.contains(lakalaCasherTradeQueryResponse.getOrderStatus())) {

            String counterUrl = RedisUtil.getInstance().getString(RedisKeyConstant.LAKALA_CASHER_PAY_URL + id);
            if (StringUtils.isBlank(counterUrl) ||
                    (Objects.nonNull(lakalaCasherTradeQueryResponse) && Constants.STR_3.equals(lakalaCasherTradeQueryResponse.getOrderStatus()))) {

                if (Objects.nonNull(lakalaCasherTradeQueryResponse) && Constants.STR_3.equals(lakalaCasherTradeQueryResponse.getOrderStatus())) {
                    log.info("lakala casher pay get detail:{}", JSON.toJSONString(lakalaCasherTradeQueryResponse));
                    // 此场景可能存在商城订单可以支付，拉卡拉无法支付情况，需要更新支付版本号，以便重新发起支付
                    tradeProvider.updateVersion(
                            TradeListByIdOrPidRequest.builder()
                                    .tid(lakalaPayItemRequest.getTid())
                                    .parentTid(lakalaPayItemRequest.getParentTid())
                                    .isAddFlag(Boolean.TRUE)
                                    .build());
                }

                List<TradeVO> trades = payServiceHelper.checkTrades(id);
                // 支付总金额
                Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
                if (creditRepayFlag) {
                    totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
                } else {
                    totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
                }
                if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                TradeVO trade = trades.get(0);
                title = trade.getTradeItems().get(0).getSkuName();
                if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
                    if (title.length() > Constants.NUM_23) {
                        title = title.substring(0, 22) + "...  等多件商品";
                    } else {
                        title = title + " 等多件商品";
                    }
                } else {
                    if (title.length() > Constants.NUM_29) {
                        title = title.substring(0, 28) + "...";
                    }
                }
                title = title.replace("&", "");
                TradeVO maxPriceTrade =
                        trades.stream()
                                .max(
                                        Comparator.comparing(
                                                trade2 -> trade2.getTradePrice().getTotalPrice()))
                                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
                LedgerAccountByIdResponse ledgerAccountByIdResponse =
                        ledgerAccountQueryProvider
                                .findByBusiness(
                                        LedgerAccountFindRequest.builder()
                                                .businessId(
                                                        maxPriceTrade
                                                                .getSupplier()
                                                                .getSupplierId()
                                                                .toString())
                                                .setFileFlag(Boolean.FALSE)
                                                .build())
                                .getContext();
                LedgerAccountVO ledgerAccountVO = ledgerAccountByIdResponse.getLedgerAccountVO();

                if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && StringUtils.isNotBlank(trade.getTailOrderNo())) {
                    id = trade.getTailOrderNo();
                }
                String tradeNo = id.concat(String.valueOf(trade.getPayVersion()));
                List<LakalaCasherAllPayRequest.OutSplitInfoBean> outSplitInfos = new ArrayList<>();
                List<String> payOrderIds = new ArrayList<>();
                if (trades.size() > 1) {
                    for (TradeVO tradeItem : trades) {
                        LakalaCasherAllPayRequest.OutSplitInfoBean outSplitInfo =
                                new LakalaCasherAllPayRequest.OutSplitInfoBean();
                        String subNo = tradeItem.getId().concat(String.valueOf(trade.getPayVersion()));
                        outSplitInfo.setOutSubOrderNo(subNo);
                        if (maxPriceTrade.getId().equals(tradeItem.getId())) {
                            outSplitInfo.setMerchantNo(ledgerAccountVO.getMerCupNo());
                            outSplitInfo.setTermNo(ledgerAccountVO.getTermNo());
                        } else {
                            LedgerAccountByIdResponse accountByIdResponse =
                                    ledgerAccountQueryProvider
                                            .findByBusiness(
                                                    LedgerAccountFindRequest.builder()
                                                            .businessId(
                                                                    tradeItem
                                                                            .getSupplier()
                                                                            .getSupplierId()
                                                                            .toString())
                                                            .setFileFlag(Boolean.FALSE)
                                                            .build())
                                            .getContext();
                            LedgerAccountVO accountVO = accountByIdResponse.getLedgerAccountVO();
                            outSplitInfo.setMerchantNo(accountVO.getMerCupNo());
                            outSplitInfo.setTermNo(accountVO.getTermNo());
                        }

                        outSplitInfo.setAmount(
                                tradeItem
                                        .getTradePrice()
                                        .getTotalPrice()
                                        .multiply(new BigDecimal(100))
                                        .setScale(0, RoundingMode.DOWN)
                                        .toString());
                        outSplitInfos.add(outSplitInfo);
                        if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && StringUtils.isNotBlank(trade.getTailOrderNo())) {
                            payOrderIds.add(tradeItem.getTailPayOrderId());
                        } else {
                            payOrderIds.add(tradeItem.getPayOrderId());
                        }
                    }
                }
                LakalaCasherAllPayRequest lakalaAllPayRequest = new LakalaCasherAllPayRequest();
                lakalaAllPayRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
                lakalaAllPayRequest.setTermNo(ledgerAccountVO.getTermNo());
                lakalaAllPayRequest.setOutOrderNo(tradeNo);
                lakalaAllPayRequest.setOutSplitInfo(outSplitInfos);
                lakalaAllPayRequest.setOrderInfo(title);
                lakalaAllPayRequest.setCounterRemark(lakalaPayItemRequest.getChannelItemId());
                lakalaAllPayRequest.setCallbackUrl(lakalaPayItemRequest.getSuccessUrl());
                if (trades.size() > 1) {
                    lakalaAllPayRequest.setSplitMark("1");
                }
                lakalaAllPayRequest.setTotalAmount(
                        totalPrice
                                .multiply(new BigDecimal("100"))
                                .setScale(0, RoundingMode.DOWN)
                                .toString());

                Duration duration = null;
                String orderEfficientTime = DateUtil.format(LocalDateTime.now().plusDays(Constants.SEVEN), DateUtil.FMT_TIME_3);
                if (trade.getOrderTimeOut() != null) {
                    // 拉卡拉支付订单超时时间 最大支持下单时间+7天
                    duration = Duration.between(LocalDateTime.now(), trade.getOrderTimeOut());
                }
                if (Objects.nonNull(duration) && duration.toDays() < Constants.SEVEN) {
                    orderEfficientTime = DateUtil.format(trade.getOrderTimeOut(), DateUtil.FMT_TIME_3);
                }
                lakalaAllPayRequest.setOrderEfficientTime(orderEfficientTime);

                BaseResponse baseResponse =
                        payProvider.pay(
                                BasePayRequest.builder()
                                        .payType(PayType.LAKALA_CASHER_PAY)
                                        .lakalaCasherAllPayRequest(lakalaAllPayRequest)
                                        .channelItemId(lakalaPayItemRequest.getChannelItemId())
                                        .build());
                LakalaCasherAllPayResponse allPayResponse =
                        JSONObject.parseObject(
                                JSONObject.toJSONString(baseResponse.getContext()),
                                LakalaCasherAllPayResponse.class);

                counterUrl = allPayResponse.getCounterUrl();
                RedisUtil.getInstance().setString(RedisKeyConstant.LAKALA_CASHER_PAY_URL + id, counterUrl);
            }
            return BaseResponse.success(
                    LakalaCasherPayItemResponse.builder()
                            .counterUrl(counterUrl)
                            .build());
        } else {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        }
    }


    @PostMapping("/lakalacasher/pay/new")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse lakalaCasherPayNew(@RequestBody @Valid LakalaPayItemRequest lakalaPayItemRequest) {
        String id, title;
        BigDecimal totalPrice;
        id = payServiceHelper.getPayBusinessId(
                lakalaPayItemRequest.getTid(), lakalaPayItemRequest.getParentTid(), null);

        BaseResponse queryBaseResponse = null;

        boolean isPayMember = payServiceHelper.isPayMember(id);
        String payNo = id;
        if (!isPayMember) {
            payNo = payServiceHelper.regenerateId(id);
        }

        // 查询这笔订单有没有拉卡拉支付过
        try {
            queryBaseResponse =
                    payProvider.getPayOrderDetail(PayOrderDetailRequest.builder().payType(PayType.LAKALA_CASHER_PAY).businessId(payNo).orderCode(id).build());
        } catch (SbcRuntimeException ignored) {
            // 查询不到会抛出异常。不做处理
            log.error("lakala casher pay get fail: {}", ignored.getMessage(), ignored);
        }

        LakalaCasherTradeQueryResponse lakalaCasherTradeQueryResponse = null;
        if (Objects.nonNull(queryBaseResponse)) {
            lakalaCasherTradeQueryResponse =
                    JSON.parseObject(JSON.toJSONString(queryBaseResponse.getContext()),
                            LakalaCasherTradeQueryResponse.class);
        }

        List<String> notPayStatus = ImmutableList.of(Constants.STR_0, Constants.STR_1, Constants.STR_3);
        if (Objects.isNull(lakalaCasherTradeQueryResponse)
                || notPayStatus.contains(lakalaCasherTradeQueryResponse.getOrderStatus())) {
            TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
            request.setConfigType(ConfigType.ORDER_SETTING_TIMEOUT_CANCEL);
            TradeConfigGetByTypeResponse typeResponse = auditQueryProvider.getTradeConfigByType(request).getContext();
            log.error("11111111");
            String counterUrl = "";
            log.error("2222222");
            List<TradeVO> trades = payServiceHelper.checkTrades(id);
            // 支付总金额
            Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
            if (creditRepayFlag) {
                totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
            } else {
                totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
            }
            if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            TradeVO trade = trades.get(0);
            title = trade.getTradeItems().get(0).getSkuName();
            if (trades.size() > 1 || trade.getTradeItems().size() > 1) {
                if (title.length() > Constants.NUM_23) {
                    title = title.substring(0, 22) + "...  等多件商品";
                } else {
                    title = title + " 等多件商品";
                }
            } else {
                if (title.length() > Constants.NUM_29) {
                    title = title.substring(0, 28) + "...";
                }
            }
            title = title.replace("&", "");
            TradeVO maxPriceTrade =
                    trades.stream()
                            .max(
                                    Comparator.comparing(
                                            trade2 -> trade2.getTradePrice().getTotalPrice()))
                            .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
            List<String> supplierIds = trades.stream().map(TradeVO::getSupplier).map(SupplierVO::getSupplierId).map(String::valueOf).collect(Collectors.toList());

            //查询商家清分账户
            QueryByBusinessIdsResponse queryByBusinessIdsResponse = ledgerAccountQueryProvider.findByBusinessIds(QueryByBusinessIdsRequest.builder().businessIds(supplierIds).build()).getContext();
            Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap = queryByBusinessIdsResponse.getBusinessIdToLedgerAccountVOMap();

            LedgerAccountVO ledgerAccountVO = businessIdToLedgerAccountVOMap.get(maxPriceTrade.getSupplier().getSupplierId().toString());

            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && StringUtils.isNotBlank(trade.getTailOrderNo())) {
                id = trade.getTailOrderNo();
            }
            String tradeNo = id.concat(String.valueOf(trade.getPayVersion()));
            List<LakalaCasherAllPayRequest.OutSplitInfoBean> outSplitInfos = new ArrayList<>();
            List<String> payOrderIds = new ArrayList<>();
            if (trades.size() > 1) {
                for (TradeVO tradeItem : trades) {
                    LakalaCasherAllPayRequest.OutSplitInfoBean outSplitInfo =
                            new LakalaCasherAllPayRequest.OutSplitInfoBean();
                    String subNo = tradeItem.getId().concat(String.valueOf(trade.getPayVersion()));
                    outSplitInfo.setOutSubOrderNo(subNo);
                    if (maxPriceTrade.getId().equals(tradeItem.getId())) {
                        outSplitInfo.setMerchantNo(ledgerAccountVO.getMerCupNo());
                        outSplitInfo.setTermNo(getTermNoByPayType(lakalaPayItemRequest.getLklPayType(), ledgerAccountVO));
                    } else {
                        LedgerAccountVO accountVO = businessIdToLedgerAccountVOMap.get(tradeItem.getSupplier().getSupplierId().toString());
                        outSplitInfo.setMerchantNo(accountVO.getMerCupNo());
                        outSplitInfo.setTermNo(getTermNoByPayType(lakalaPayItemRequest.getLklPayType(), accountVO));
                    }

                    outSplitInfo.setAmount(
                            tradeItem
                                    .getTradePrice()
                                    .getTotalPrice()
                                    .multiply(new BigDecimal(100))
                                    .setScale(0, RoundingMode.DOWN)
                                    .toString());
                    outSplitInfos.add(outSplitInfo);
                    if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && StringUtils.isNotBlank(trade.getTailOrderNo())) {
                        payOrderIds.add(tradeItem.getTailPayOrderId());
                    } else {
                        payOrderIds.add(tradeItem.getPayOrderId());
                    }
                }
            }
            LakalaCasherAllPayRequest lakalaAllPayRequest = new LakalaCasherAllPayRequest();
            lakalaAllPayRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
            lakalaAllPayRequest.setTermNo(getTermNoByPayType(lakalaPayItemRequest.getLklPayType(), ledgerAccountVO));
            lakalaAllPayRequest.setOutOrderNo(payNo);
            lakalaAllPayRequest.setOutSplitInfo(outSplitInfos);
            lakalaAllPayRequest.setOrderInfo(title);
            lakalaAllPayRequest.setCounterRemark(lakalaPayItemRequest.getChannelItemId());
            lakalaAllPayRequest.setCallbackUrl(lakalaPayItemRequest.getSuccessUrl());
            if (trades.size() > 1) {
                lakalaAllPayRequest.setSplitMark("1");
            }
            lakalaAllPayRequest.setTotalAmount(
                    totalPrice
                            .multiply(new BigDecimal("100"))
                            .setScale(0, RoundingMode.DOWN)
                            .toString());

            String orderEfficientTime = DateUtil.format(LocalDateTime.now().plusDays(Constants.SEVEN), DateUtil.FMT_TIME_3);
            if (Objects.nonNull(typeResponse) && StringUtils.isNotBlank(typeResponse.getContext())) {
                Integer minute =
                        Integer.valueOf(
                                JSON.parseObject(typeResponse.getContext())
                                        .get("minute")
                                        .toString());
                // 拉卡拉支付订单超时时间 最大支持下单时间+7天
                orderEfficientTime = DateUtil.format(trade.getTradeState().getCreateTime().plusMinutes(minute), DateUtil.FMT_TIME_3);
            }
            lakalaAllPayRequest.setOrderEfficientTime(orderEfficientTime);
            // 将此记录放置在调用第三方支付接口之前，防止回调了还没有生成记录问题
            if (!payServiceHelper.isPayMember(id)) {
                // 增加支付流水，每次点击去支付都会重新生成一条记录
                PayTimeSeriesAddRequest payTimeSeriesAddRequest = new PayTimeSeriesAddRequest();
                payTimeSeriesAddRequest.setPayChannelType("LAKALACASHER_" + lakalaPayItemRequest.getLklPayType().getLklPayType());
                payTimeSeriesAddRequest.setPayNo(payNo);
                payTimeSeriesAddRequest.setApplyPrice(totalPrice);
                payTimeSeriesAddRequest.setBusinessId(id);
                payTimeSeriesAddRequest.setClientIp(HttpUtil.getIpAddr());
                if (StringUtils.isNotBlank(lakalaPayItemRequest.getChannelItemId())) {
                    payTimeSeriesAddRequest.setChannelItemId(Long.valueOf(lakalaPayItemRequest.getChannelItemId()));
                }
                payTimeSeriesProvider.add(payTimeSeriesAddRequest);
            }
            log.error("3333333333");
            BaseResponse baseResponse =
                    payProvider.pay(
                            BasePayRequest.builder()
                                    .payType(PayType.LAKALA_CASHER_PAY)
                                    .lakalaCasherAllPayRequest(lakalaAllPayRequest)
                                    .channelItemId(lakalaPayItemRequest.getChannelItemId())
                                    .lklPayType(lakalaPayItemRequest.getLklPayType())
                                    .orderCode(id)
                                    .build());
            LakalaCasherAllPayResponse allPayResponse =
                    JSONObject.parseObject(
                            JSONObject.toJSONString(baseResponse.getContext()),
                            LakalaCasherAllPayResponse.class);
            log.error("4444444");
            counterUrl = allPayResponse.getCounterUrl();
            return BaseResponse.success(
                    LakalaCasherPayItemResponse.builder()
                            .counterUrl(counterUrl)
                            .build());
        } else {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        }
    }

    /**
     * 根据支付方式取不同的终端号
     *
     * @param lklPayType
     * @param ledgerAccountVO
     * @return
     */
    public String getTermNoByPayType(LklPayType lklPayType, LedgerAccountVO ledgerAccountVO) {
        switch (lklPayType) {
            case ALIPAY:
                return ledgerAccountVO.getTermNo();
            case WECHAT:
                return ledgerAccountVO.getTermNo();
            case UNION:
                return ledgerAccountVO.getTermNo();
            case CARD:
                return ledgerAccountVO.getBankTermNo();
            case LKLAT:
                return ledgerAccountVO.getBankTermNo();
            case QUICK_PAY:
                return ledgerAccountVO.getQuickPayTermNo();
            case EBANK:
                return ledgerAccountVO.getUnionTermNo();
            case UNION_CC:
                return ledgerAccountVO.getUnionTermNo();
            default:
                return null;
        }
    }

    @Operation(summary = "支付前校验是否已支付成功")
    @Parameter(name = "tid", description = "订单号", required = true)
    @RequestMapping(value = "/lakalacasher/check/{tid}/{parentId}/{flowState}", method = RequestMethod.GET)
    public BaseResponse checkPayState(@PathVariable String tid, @PathVariable String parentId, @PathVariable String flowState) {
        String flag = "0";
        List<String> bussinessIds = new ArrayList<>();
        TradeVO trade = null;
        if (StringUtils.isNotBlank(tid) && !"undefined".equals(tid) && !"null".equals(tid)) {
            trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                    .tid(tid).build()).getContext().getTradeVO();
            bussinessIds.add(tid);
        } else if (StringUtils.isNotBlank(parentId) && !"undefined".equals(parentId) && !"null".equals(parentId)) {
            List<TradeVO> trades = tradeQueryProvider.getListByParentId(TradeListByParentIdRequest.builder().parentTid(parentId).build()).getContext().getTradeVOList();
            if (CollectionUtils.isNotEmpty(trades)) {
                trade = trades.get(0);
            }
            bussinessIds.add(parentId);
        }
        if (Objects.nonNull(trade)) {
            if (Objects.nonNull(trade.getTradeState())) {
                if (PayState.PAID.equals(trade.getTradeState().getPayState())) {
                    flag = "1";
                }

                //支付定金页面，已支付定金返回支付成功
                if (Objects.nonNull(flowState) &&
                        flowState.equals(FlowState.WAIT_PAY_EARNEST.toValue()) &&
                        PayState.PAID_EARNEST.equals(trade.getTradeState().getPayState())
                ) {
                    flag = "1";
                }
            }
        }
        if (flag.equals("0") && CollectionUtils.isNotEmpty(bussinessIds)) {
            //查询拉卡拉收银台大额支付拉起记录
            PayTimeSeriesListResponse payTimeSeriesListResponse = payTimeSeriesQueryProvider.list(PayTimeSeriesListRequest.builder()
                    .businessIdList(bussinessIds)
                    .payChannelType("LAKALACASHER_LKLAT")
                    .build()).getContext();
            //如果有拉起记录提醒可能重复支付，不强制
            if (Objects.nonNull(payTimeSeriesListResponse) &&
                    CollectionUtils.isNotEmpty(payTimeSeriesListResponse.getPayTimeSeriesVOList())) {
                flag = "2";
            }
        }
        return BaseResponse.success(flag);
    }

}
