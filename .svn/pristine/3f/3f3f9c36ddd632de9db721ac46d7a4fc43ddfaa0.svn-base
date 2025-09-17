package com.wanmi.sbc.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.PayOrderDetailRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaAllPayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaPayRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaAllPayResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaTradeQueryResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListByIdOrPidRequest;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import com.wanmi.sbc.pay.response.LakalaPayItemResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author zhanggaolei
 * @type LakalaWechatPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.LAKALA_WX)
public class LakalaWechatPayService extends ExternalPayService<LakalaAllPayRequest> {

    private static final ThreadLocal<List<TradeVO>> BASE_TRADES = new InheritableThreadLocal();

    @Autowired RedissonClient redissonClient;
    @Autowired LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired TradeProvider tradeProvider;

    @Autowired TradeQueryProvider tradeQueryProvider;

    @Autowired MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    /**
     * FIXME 此处逻辑有可能有问题
     *
     * @param request
     */
    @Override
    protected void check(PayChannelRequest request) {
        BaseResponse queryBaseResponse = null;
        // 查询这笔订单有没有拉卡拉支付过
        try {
            queryBaseResponse =
                    payProvider.getPayOrderDetail(
                            PayOrderDetailRequest.builder()
                                    .payType(PayType.LAKALA_PAY)
                                    .businessId(request.getId())
                                    .build());
        } catch (SbcRuntimeException ignored) {
            // 查询不到会抛出异常。不做处理
        }

        LakalaTradeQueryResponse lakalaTradeQueryResponse = null;
        if (Objects.nonNull(queryBaseResponse)) {
            lakalaTradeQueryResponse =
                    JSON.parseObject(
                            JSON.toJSONString(queryBaseResponse.getContext()),
                            LakalaTradeQueryResponse.class);
        }

        if (Objects.nonNull(lakalaTradeQueryResponse)
                && !"FAIL".equals(lakalaTradeQueryResponse.getTradeState())) {
            throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060003);
        }
    }

    @Override
    protected PayBaseBean payMemberProcess(PayChannelRequest request) {
        throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
    }

    @Override
    protected LakalaAllPayRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        LakalaAllPayRequest buildRequest = this.lakalaCommonBuildRequest(bean, request);
        Duration duration = null;
        if (bean.getOrderTimeOut() != null) {
            // 拉卡拉支付订单超时时间最大支持99分钟
            duration = Duration.between(LocalDateTime.now(), bean.getOrderTimeOut());
        }

        TerminalType terminalType = request.getTerminal();
        String appId = StringUtils.EMPTY;

        if (TerminalType.WX_H5.equals(terminalType)) {
            buildRequest.setTransType("51");
        } else if (TerminalType.APP.equals(terminalType)
                || TerminalType.MINI.equals(terminalType)
                || TerminalType.PC.equals(terminalType)) {
            buildRequest.setTransType("71");
        }else{
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
        }

        BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse =
                miniProgramSetQueryProvider.getByType(
                        MiniProgramSetByTypeRequest.builder().type(Constants.ZERO).build());
        if (StringUtils.equals(
                CommonErrorCodeEnum.K000000.getCode(), miniProgramSetByTypeResponseBaseResponse.getCode())) {
            MiniProgramSetVO miniProgramSetVO =
                    miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
            // 验证小程序支付开关
            if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            appId = miniProgramSetVO.getAppId();
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }

        buildRequest.setAccountType("WECHAT");
        LakalaPayRequest.WxPayAccBusi wxPayAccBusi = new LakalaPayRequest.WxPayAccBusi();
        if (duration != null) {
            wxPayAccBusi.setTimeout_express(
                    duration.toMinutes() > 99 ? "99" : duration.toMinutes() + "");
        }
        wxPayAccBusi.setSub_appid(appId);
        wxPayAccBusi.setUser_id(request.getOpenid());
        buildRequest.setAccBusiFields(wxPayAccBusi);

        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(LakalaAllPayRequest t,PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.LAKALA_PAY)
                .lakalaAllPayRequest(t)
                .channelItemId(t.getRemark())
                .tradeId(bean.getId())
                .build();
    }

    /**
     * 重写父类方法，为了获得Trade
     *
     * @param id
     * @return
     */
    @Override
    protected List<TradeVO> checkTrades(String id) {
        List<TradeVO> tradeVOS = super.checkTrades(id);
        BASE_TRADES.set(tradeVOS);
        return tradeVOS;
    }

    @Override
    protected BaseResponse pay(BasePayRequest basePayRequest,PayBaseBean payBaseBean) {
        String tid = null;
        String parentId = null;
        List<TradeVO> tradeVOS = BASE_TRADES.get();
        if (tradeVOS.size() > 1) {
            parentId = tradeVOS.get(0).getParentId();
        } else {
            tid = tradeVOS.get(0).getId();
        }
        // 更新支付版本号
        tradeProvider.updateVersion(
                TradeListByIdOrPidRequest.builder().tid(tid).parentTid(parentId).build());

        BaseResponse baseResponse = super.pay(basePayRequest,payBaseBean);
        LakalaAllPayResponse allPayResponse =
                JSONObject.parseObject(
                        JSONObject.toJSONString(baseResponse.getContext()),
                        LakalaAllPayResponse.class);
        String appId = "";
        if (basePayRequest.getLakalaAllPayRequest().getAccBusiFields()
                instanceof LakalaPayRequest.WxPayAccBusi) {
            LakalaPayRequest.WxPayAccBusi wxPayAccBusi =
                    (LakalaPayRequest.WxPayAccBusi)
                            basePayRequest.getLakalaAllPayRequest().getAccBusiFields();
            appId = wxPayAccBusi.getSub_appid();
        }
        BASE_TRADES.remove();
        return BaseResponse.success(
                LakalaPayItemResponse.builder()
                        .appId(appId)
                        .respData(allPayResponse.getAccRespFields())
                        .build());
    }

    protected LakalaAllPayRequest lakalaCommonBuildRequest(
            PayBaseBean bean, PayChannelRequest request) {
        List<TradeVO> tradeVOS = BASE_TRADES.get();
        TradeVO trade = tradeVOS.get(0);
        String id = bean.getId();
        TradeVO maxPriceTrade =
                tradeVOS.stream()
                        .max(Comparator.comparing(trade2 -> trade2.getTradePrice().getTotalPrice()))
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

        if (Objects.nonNull(trade.getIsBookingSaleGoods())
                && trade.getIsBookingSaleGoods()
                && StringUtils.isNotBlank(trade.getTailOrderNo())) {
            id = trade.getTailOrderNo();
        }
//        String tradeNo = id.concat(String.valueOf(trade.getPayVersion()));
        String payNo = bean.getPayNo();
        List<LakalaAllPayRequest.OutSplitInfo> outSplitInfos =
                this.getOutSplitInfos(
                        tradeVOS, ledgerAccountVO, trade.getPayVersion(), maxPriceTrade.getId());
        LakalaAllPayRequest buildRequest = new LakalaAllPayRequest();
        buildRequest.setMerchantNo(ledgerAccountVO.getMerCupNo());
        buildRequest.setTermNo(ledgerAccountVO.getTermNo());
        buildRequest.setOutTradeNo(payNo);
        buildRequest.setOutSplitInfo(outSplitInfos);
        buildRequest.setRemark(String.valueOf(request.getChannelItemId()));

        buildRequest.setTotalAmount(
                bean.getTotalPrice()
                        .multiply(new BigDecimal(Constants.NUM_100))
                        .setScale(0, RoundingMode.DOWN)
                        .toString());
        LakalaAllPayRequest.LocationInfo locationInfo = new LakalaAllPayRequest.LocationInfo();
        locationInfo.setRequestIp(HttpUtil.getIpAddr());
        buildRequest.setLocationInfo(locationInfo);
        buildRequest.setSubject(bean.getTitle());
        return buildRequest;
    }

    private List<LakalaAllPayRequest.OutSplitInfo> getOutSplitInfos(
            List<TradeVO> tradeVOS,
            LedgerAccountVO ledgerAccountVO,
            int tradeVersion,
            String maxPriceTradeId) {
        List<LakalaAllPayRequest.OutSplitInfo> outSplitInfos = new ArrayList<>();
        for (TradeVO tradeItem : tradeVOS) {
            LakalaAllPayRequest.OutSplitInfo outSplitInfo = new LakalaAllPayRequest.OutSplitInfo();
            String no = tradeItem.getId().concat(String.valueOf(tradeVersion));
            outSplitInfo.setOutSubTradeNo(no);
            if (maxPriceTradeId.equals(tradeItem.getId())) {
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
        }
        return outSplitInfos;
    }

    /**
     * 拉卡拉支付不需要校验
     *
     * @param trades
     */
    @Override
    protected void checkLakala(List<TradeVO> trades) {}
}
