package com.wanmi.sbc.trade;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailAddRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsModifyRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountListResponse;
import com.wanmi.sbc.customer.api.response.store.ListStoreResponse;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerCheckPayPasswordRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.unioncloud.UnionCloudPayProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.ali.PayExtraRequest;
import com.wanmi.sbc.empower.api.request.pay.channelItem.OpenedChannelItemRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayIdRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayOpenedByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxChannelsPayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForAppRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForJSApiRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForMWebRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.bean.enums.*;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.TerminalType;
import com.wanmi.sbc.empower.bean.vo.LakalaCasherPayItemVO;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.paycallback.PayAndRefundCallBackProvider;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.plugin.PluginPayInfoProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempByIdRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesListRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoAddRequest;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesListResponse;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;
import com.wanmi.sbc.order.bean.dto.TradeDTO;
import com.wanmi.sbc.order.bean.dto.TradePayCallBackOnlineDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import com.wanmi.sbc.order.bean.vo.SupplierVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.PlatformPayCompanyProvider;
import com.wanmi.sbc.setting.api.response.PlatformPayCompanyListResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.PlatformPayCompanyVO;
import com.wanmi.sbc.third.login.api.WechatApi;
import com.wanmi.sbc.third.login.response.GetAccessTokeResponse;
import com.wanmi.sbc.third.login.response.LittleProgramAuthResponse;
import com.wanmi.sbc.third.wechat.WechatSetService;
import com.wanmi.sbc.trade.request.PayItemsRequest;
import com.wanmi.sbc.trade.request.PayMobileRequest;
import com.wanmi.sbc.trade.request.WeiXinPayRequest;
import com.wanmi.sbc.util.CommonUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支付
 * Created by sunkun on 2017/8/10.
 */
@RestController
@Validated
@RequestMapping("/pay")
@Tag(name =  "PayController", description =  "mobile 支付")
@Slf4j
public class PayController {

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private CustomerFundsProvider customerFundsProvider;

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    @Autowired
    private CustomerFundsDetailProvider customerFundsDetailProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private WechatSetService wechatSetService;

    @Autowired
    private MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    @Autowired
    private UnionCloudPayProvider unionCloudPayProvider;

    @Autowired
    private PayProvider payProvider;

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired
    private PluginPayInfoProvider pluginPayInfoProvider;

    @Autowired
    private PlatformPayCompanyProvider platformPayChannelProvider;

    @Autowired
    private PayAndRefundCallBackProvider payAndRefundCallBackProvider;

    @Autowired
    private PayingMemberRecordTempQueryProvider payingMemberRecordTempQueryProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    /**
     * 获取支付网关配置
     *
     * @return
     */
    @Operation(summary = "获取支付网关配置")
    @RequestMapping(value = "/getWxConfig", method = RequestMethod.GET)
    public BaseResponse<String> getWxConfig() {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<PayGatewayConfigVO> payGatewayConfigs = paySettingQueryProvider.listOpenedGatewayConfig(request).getContext()
                .getGatewayConfigVOList();
        Optional<PayGatewayConfigVO> optional = payGatewayConfigs.stream().filter(
                c -> c.getPayGateway().getName() == PayGatewayEnum.WECHAT
        ).findFirst();
        String appId2 = StringUtils.EMPTY;
        if (optional.isPresent()){
            appId2 = optional.get().getAppId2();
        }
        return BaseResponse.success(appId2);
    }

    /**
     * 获取可用支付项
     *
     * @return
     */
    @Operation(summary = "获取可用支付项")
    @RequestMapping(value = "/items/{type}", method = RequestMethod.GET)
    public BaseResponse<List<PayChannelItemVO>> items(@PathVariable String type) {
        GatewayOpenedByStoreIdRequest request = new GatewayOpenedByStoreIdRequest();
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<PayGatewayConfigVO> payGatewayConfigList = paySettingQueryProvider.listOpenedGatewayConfig(request).getContext()
                .getGatewayConfigVOList();

        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.valueOf(type));
        //如果小程序，暂时传H5支付类型
        if (com.wanmi.sbc.common.enums.TerminalType.MINI.name().equals(type)) {
            type = TerminalType.H5.name();
        }
        final String terminalType = type;

        List<PayChannelItemVO> itemList = new ArrayList<>();
        payGatewayConfigList.forEach(config -> {
            List<PayChannelItemVO> payChannelItemList = paySettingQueryProvider.listOpenedChannelItemByGatewayName(new
                    OpenedChannelItemRequest(
                    config.getPayGateway().getName(), TerminalType.valueOf(terminalType))).getContext().getPayChannelItemVOList();
            if (CollectionUtils.isNotEmpty(payChannelItemList)) {
                itemList.addAll(payChannelItemList);
            }
        });

        //如果是关的，则关闭渠道
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            itemList.stream().filter(i -> PayWay.WECHAT.name().equalsIgnoreCase(i.getChannel())).forEach(i -> {
                i.setIsOpen(IsOpen.NO);
            });
        }
        return BaseResponse.success(itemList);
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
        //如果小程序，暂时传H5支付类型
        if (com.wanmi.sbc.common.enums.TerminalType.MINI.name().equals(type)) {
            type = TerminalType.H5.name();
        }
        final String terminalType = type;

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

        Optional<PayChannelItemVO> lakalaPayCasher = payItemList.stream().filter(p -> "lakalacashier_wx".equals(p.getCode())).findFirst();
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
            payItemList.stream().filter(p -> "lakalacashier_wx".equals(p.getCode())).forEach(i -> {
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
                payItemList.stream().filter(p -> "lakalacashier_wx".equals(p.getCode())).forEach(i -> {
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
                payItemList.stream().filter(p -> "lakalacashier_wx".equals(p.getCode())).forEach(i -> {
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
                payItemList.stream().filter(p -> "lakalacashier_wx".equals(p.getCode())).forEach(i -> {
                    if(Objects.isNull(i.getLakalaCasherPayItemVO())){
                        i.setLakalaCasherPayItemVO(new LakalaCasherPayItemVO());
                    }
                    i.getLakalaCasherPayItemVO().setQuickPayStatus(DefaultFlag.YES);
                });
            }

            //银行卡网银终端号不为空的商户数
            long unionTermNoCount = ledgerAccounts.getLedgerAccountVOList().stream().filter(ledgerAccountVO -> StringUtils.isNotBlank(ledgerAccountVO.getUnionTermNo())).count();
            if(unionTermNoCount == companyInfoIds.size()){
                payItemList.stream().filter(p -> "lakalacashier_wx".equals(p.getCode())).forEach(i -> {
                    i.getLakalaCasherPayItemVO().setEBankStatus(DefaultFlag.YES);
                    //暂不开通
//                    i.getLakalaCasherPayItemVO().setUnionCCStatus(DefaultFlag.YES);
                });
            }
        }
        return BaseResponse.success(payItemList);
    }

    /**
     * 非微信浏览器h5支付统一下单接口
     *
     * @param weiXinPayRequest
     * @return
     */
    @Deprecated
    @Operation(summary = "非微信浏览器h5支付统一下单接口，返回结果mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付")
    @RequestMapping(value = "/wxPayUnifiedorderForMweb", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse wxPayUnifiedorderForMweb(@RequestBody @Valid WeiXinPayRequest weiXinPayRequest) {
        //验证H5支付开关
        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.H5);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        WxPayForMWebRequest mWebRequest = new WxPayForMWebRequest();
        String id, body, totalPrice;
        boolean isPayMember = payServiceHelper.isPayMember(weiXinPayRequest.getTid());
        //如果是付费会员订单
        if (isPayMember) {
            id = weiXinPayRequest.getTid();
            //根据付费记录id 查询记录
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(id)
                    .build()).getContext().getPayingMemberRecordTempVO();
            body = payingMemberRecordTempVO.getLevelName().concat("付费会员").concat(payingMemberRecordTempVO.getPayNum()+"个月");
            totalPrice = payingMemberRecordTempVO.getPayAmount().multiply(new BigDecimal(Constants.NUM_100)).setScale(0, RoundingMode.DOWN).toString();
        } else {
            id= payServiceHelper.getPayBusinessId(weiXinPayRequest.getTid(), weiXinPayRequest.getParentTid(), weiXinPayRequest.getRepayOrderCode());
            List<TradeVO> trades = payServiceHelper.checkTrades(id);
            //订单总金额
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
            body = payServiceHelper.buildBody(trades);
        }

        mWebRequest.setBody(body + "订单");
        mWebRequest.setOut_trade_no(id);
        mWebRequest.setTotal_fee(totalPrice);
        mWebRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        mWebRequest.setTrade_type(WxPayTradeType.MWEB.toString());
        mWebRequest.setScene_info("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://m.s2btest2.kstore.shop\"," +
                "\"wap_name\": \"h5下单支付\"}}");
        mWebRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        mWebRequest.setSceneType(weiXinPayRequest.getSceneType());

        if (!isPayMember) {
            //保存支付请求信息到插件
            PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
            pluginPayInfoAddRequest.setOrderCode(id);
            pluginPayInfoAddRequest.setPayRequest(mWebRequest.toString());
            pluginPayInfoProvider.add(pluginPayInfoAddRequest);
        }


        return payProvider.pay(BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.ONE)
                .wxPayForMWebRequest(mWebRequest)
                .build());
    }


    /**
     * 微信浏览器内JSApi支付统一下单接口
     *
     * @param weiXinPayRequest
     * @return
     */
    @Deprecated
    @Operation(summary = "微信浏览器内JSApi支付统一下单接口，返回用于在微信内支付的所需参数")
    @RequestMapping(value = "/wxPayUnifiedorderForJSApi", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse wxPayUnifiedorderForJSApi(@RequestBody @Valid WeiXinPayRequest weiXinPayRequest) {
        //验证H5支付开关
        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.H5);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        return payProvider.pay(BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.TWO)
                .wxPayForJSApiRequest(wxPayCommon(weiXinPayRequest))
                .build());
    }

    /**
     * 小程序内JSApi支付统一下单接口
     *
     * @param weiXinPayRequest
     * @return
     */
    @Deprecated
    @Operation(summary = "小程序内JSApi支付统一下单接口，返回用于在小程序内支付的所需参数")
    @RequestMapping(value = "/wxPayUnifiedorderForLittleProgram", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse wxPayUnifiedorderForLittleProgram(@RequestBody @Valid WeiXinPayRequest weiXinPayRequest) {

        String appId;
        BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                .type(Constants.ZERO)
                .build());
        if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(),miniProgramSetByTypeResponseBaseResponse.getCode())) {
            MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
            //验证小程序支付开关
            if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            appId = miniProgramSetVO.getAppId();
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        WxPayForJSApiRequest req = wxPayCommon(weiXinPayRequest);
        req.setAppid(appId);
        return payProvider.pay(BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.THREE)
                .wxPayForJSApiRequest(req)
                .build());
    }

    /**
     * 微信内浏览器,小程序支付公用逻辑
     *
     * @param weiXinPayRequest
     * @return
     */
    private WxPayForJSApiRequest wxPayCommon(WeiXinPayRequest weiXinPayRequest) {
        WxPayForJSApiRequest jsApiRequest = new WxPayForJSApiRequest();
        boolean isPayMember = payServiceHelper.isPayMember(weiXinPayRequest.getTid());
        String id, body, totalPrice;
        if (isPayMember) {
            id = weiXinPayRequest.getTid();
            //根据付费记录id 查询记录
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(id)
                    .build()).getContext().getPayingMemberRecordTempVO();
            body = payingMemberRecordTempVO.getLevelName().concat("付费会员").concat(payingMemberRecordTempVO.getPayNum()+"个月");
            totalPrice = payingMemberRecordTempVO.getPayAmount().multiply(new BigDecimal(Constants.NUM_100)).setScale(0, RoundingMode.DOWN).toString();
        } else {
            id = payServiceHelper.getPayBusinessId(weiXinPayRequest.getTid(), weiXinPayRequest.getParentTid(), weiXinPayRequest.getRepayOrderCode());
            List<TradeVO> trades = payServiceHelper.checkTrades(id);
            //订单总金额
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
            body = payServiceHelper.buildBody(trades);
        }

        jsApiRequest.setBody(body.trim() + "订单");
        jsApiRequest.setOut_trade_no(id);
        jsApiRequest.setTotal_fee(totalPrice);
        jsApiRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        jsApiRequest.setTrade_type(WxPayTradeType.JSAPI.toString());
        jsApiRequest.setOpenid(weiXinPayRequest.getOpenid());
        jsApiRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);

        if (!isPayMember){
            //保存支付请求信息到插件
            PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
            pluginPayInfoAddRequest.setOrderCode(id);
            pluginPayInfoAddRequest.setPayRequest(jsApiRequest.toString());
            pluginPayInfoProvider.add(pluginPayInfoAddRequest);
        }
        return jsApiRequest;
    }

    /**
     * @description  视频号支付
     * @author  wur
     * @date: 2022/4/20 10:23
     * @param weiXinPayRequest
     * @return
     **/
    @Deprecated
    @Operation(summary = "微信视频号支付，微信视频号支付")
    @RequestMapping(value = "/wxChannelsPay", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse wxChannelsPay(@RequestBody @Valid WeiXinPayRequest weiXinPayRequest) {
        //验证是否开通视频号增值服务
        if (!commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        // 验证小程序支付
        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.MINI);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        WxChannelsPayRequest wxChannelsPayRequest = wxChannelsPayCommon(weiXinPayRequest);
        return payProvider.pay(BasePayRequest.builder()
                .payType(PayType.WXCHANNELSPAY)
                .wxChannelsPayRequest(wxChannelsPayRequest)
                .build());
    }

    /**
     * @description    视频号支付封装请求参数
     *                 如果是单订单，则需要验证其夫订单是否有多个子单  如果多个  需要将子单先下单到微信再支付
     * @author  wur
     * @date: 2022/4/20 10:23
     * @param weiXinPayRequest
     * @return
     **/
    private WxChannelsPayRequest wxChannelsPayCommon(WeiXinPayRequest weiXinPayRequest) {
        WxChannelsPayRequest wxChannelsPayRequest = new WxChannelsPayRequest();
        String id = payServiceHelper.getPayBusinessId(weiXinPayRequest.getTid(), weiXinPayRequest.getParentTid(), weiXinPayRequest.getRepayOrderCode());
        List<TradeVO> trades = payServiceHelper.checkTrades(id);

        if(CollectionUtils.isEmpty(trades)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        if (trades.size() > 1) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }

        Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
        //视频号不支持授信还款
        if (creditRepayFlag) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070092);
        }
        //验证订单是否是视频号订单
        TradeVO tradeVO = trades.get(0);
        //SceneGroup= -1 开通视频号流程时输入的强制执行订单
        if (Objects.isNull(tradeVO.getSceneGroup()) || tradeVO.getSceneGroup() != -1) {
            if (!SellPlatformType.WECHAT_VIDEO.equals(tradeVO.getSellPlatformType())
                    || StringUtils.isEmpty(tradeVO.getSellPlatformOrderId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
        }

        if (StringUtils.isBlank(weiXinPayRequest.getOpenid())
                || !weiXinPayRequest.getOpenid().equals(tradeVO.getBuyer().getThirdLoginOpenId())) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070099);
        }

        String totalPrice = payServiceHelper.calcTotalPriceByPenny(trades).toString();
        wxChannelsPayRequest.setOpenid(trades.get(0).getBuyer().getThirdLoginOpenId());
        wxChannelsPayRequest.setOrderId(id);
        wxChannelsPayRequest.setThirdOrderId(trades.get(0).getSellPlatformOrderId());
        wxChannelsPayRequest.setTotalPrice(totalPrice);
        wxChannelsPayRequest.setClientIp(HttpUtil.getIpAddr());

        //保存支付请求信息到插件
        PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
        pluginPayInfoAddRequest.setOrderCode(id);
        pluginPayInfoAddRequest.setPayRequest(wxChannelsPayRequest.toString());
        pluginPayInfoProvider.add(pluginPayInfoAddRequest);
        return wxChannelsPayRequest;
    }


    /**
     * 微信app支付统一下单接口
     *
     * @param weiXinPayRequest
     * @return
     */
    @Deprecated
    @Operation(summary = " 微信app支付统一下单接口，返回用于app内支付的所需参数")
    @RequestMapping(value = "/wxPayUnifiedorderForApp", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse wxPayUnifiedorderForApp(@RequestBody @Valid WeiXinPayRequest weiXinPayRequest) {
        //验证H5支付开关
        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.APP);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        WxPayForAppRequest appRequest = new WxPayForAppRequest();
        boolean isPayMember = payServiceHelper.isPayMember(weiXinPayRequest.getTid());
        String id, body, totalPrice;
        if (isPayMember) {
            id = weiXinPayRequest.getTid();
            //根据付费记录id 查询记录
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(id)
                    .build()).getContext().getPayingMemberRecordTempVO();
            body = payingMemberRecordTempVO.getLevelName().concat("付费会员").concat(payingMemberRecordTempVO.getPayNum()+"个月");
            totalPrice = payingMemberRecordTempVO.getPayAmount().multiply(new BigDecimal(Constants.NUM_100)).setScale(0, RoundingMode.DOWN).toString();
        } else {
            id = payServiceHelper.getPayBusinessId(weiXinPayRequest.getTid(), weiXinPayRequest.getParentTid(), weiXinPayRequest.getRepayOrderCode());
            List<TradeVO> trades = payServiceHelper.checkTrades(id);
            //订单总金额
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
            body = payServiceHelper.buildBody(trades);
        }

        appRequest.setBody(body + "订单");
        appRequest.setOut_trade_no(id);
        appRequest.setTotal_fee(totalPrice);
        appRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        appRequest.setTrade_type(WxPayTradeType.APP.toString());
        appRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        if (!isPayMember) {
            //保存支付请求信息到插件
            PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
            pluginPayInfoAddRequest.setOrderCode(id);
            pluginPayInfoAddRequest.setPayRequest(appRequest.toString());
            pluginPayInfoProvider.add(pluginPayInfoAddRequest);
        }
        return  payProvider.pay(BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.FOUR)
                .wxPayForAppRequest(appRequest)
                .build());
    }


    @Operation(summary = "支付校验，支付前校验是否已支付成功", method = "GET")
    @Parameter(name = "tid", description = "订单号", required = true)
    @RequestMapping(value = "/aliPay/check/{tid}/{flowState}", method = RequestMethod.GET)
    public BaseResponse checkPayState(@PathVariable String tid,@PathVariable String flowState) {
        boolean isPayMember = payServiceHelper.isPayMember(tid);
        //0:未支付
        String flag = "0";
        if (isPayMember) {
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(tid)
                    .build()).getContext().getPayingMemberRecordTempVO();
            Integer payState = payingMemberRecordTempVO.getPayState();
            //已支付
            if (payState.equals(NumberUtils.INTEGER_ONE)) {
                flag = "1";
            }
        } else {

            TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                    .tid(tid).build()).getContext().getTradeVO();
            if (Objects.nonNull(trade)) {
                if (Objects.nonNull(trade.getTradeState())) {
                    LocalDateTime orderTimeOut = trade.getOrderTimeOut();
                    //已支付
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

                    //已超过未支付取消订单时间或者已作废
                    if (FlowState.VOID.equals(trade.getTradeState().getFlowState()) || Objects.nonNull(orderTimeOut) && orderTimeOut.isBefore(LocalDateTime.now())) {
                        flag = "2";
                    }
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

    @Operation(summary = "微信支付订单支付状态校验，微信支付后校验是否已支付成功", method = "GET")
    @Parameter(name = "tid", description = "订单号", required = true)
    @RequestMapping(value = "/weiXinPay/checkOrderPayState/{tid}/{flowState}", method = RequestMethod.GET)
    public BaseResponse checkOrderPayState(@PathVariable String tid,@PathVariable String flowState) {
        boolean isPayMember = payServiceHelper.isPayMember(tid);
        String flag = "0";
        if (isPayMember) {
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(tid)
                    .build()).getContext().getPayingMemberRecordTempVO();
            Integer payState = payingMemberRecordTempVO.getPayState();
            //已支付
            if (payState.equals(NumberUtils.INTEGER_ONE)) {
                flag = "1";
            }
        } else {
            List<TradeVO> tradeVOList = new ArrayList<>();
            if (tid.startsWith(GeneratorService._PREFIX_TRADE_ID) || tid.startsWith(GeneratorService.NEW_PREFIX_TRADE_ID)) {
                tradeVOList.add(tradeQueryProvider.getById(TradeGetByIdRequest.builder()
                        .tid(tid).build()).getContext().getTradeVO());
            } else if (tid.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID) || tid.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
                tradeVOList.addAll(tradeQueryProvider.getListByParentId(TradeListByParentIdRequest.builder().parentTid(tid)
                        .build()).getContext().getTradeVOList());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (tradeVOList.size() > 0) {
                if(tradeVOList.get(0).getTradeState().getPayState() == PayState.PAID){
                    flag = "1";
                }

                if (tradeVOList.get(0).getTradeState().getPayState() == PayState.PAID_EARNEST) {
                    flag = "2";
                }

                //支付定金页面，已支付定金返回支付成功
                if(Objects.nonNull(flowState) && flowState.equals(FlowState.WAIT_PAY_EARNEST.toValue()) &&
                        PayState.PAID_EARNEST.equals(tradeVOList.get(0).getTradeState().getPayState())
                ){
                    flag = "1";
                }
            }


        }

        return BaseResponse.success(flag);
    }

    /*
     * @Description: 支付表单
     * @Param:
     * @Author: Bob->
     * @Date: 2019-02-01 11:12
     */
    @Deprecated
    @Operation(summary = "H5支付宝支付表单，该请求需新打开一个空白页，返回的支付宝脚本会自动提交重定向到支付宝收银台")
    @Parameter(name = "encrypted", description = "base64编码后的支付请求",
            required = true)
    @RequestMapping(value = "/aliPay/{encrypted}", method = RequestMethod.GET)
    @GlobalTransactional
    public void aliPay(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        PayMobileRequest payMobileRequest = JSON.parseObject(decrypted, PayMobileRequest.class);
        log.info("====================支付宝支付表单================payMobileRequest :{}", payMobileRequest);

        payMobileRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        String form = this.alipayUtil(payMobileRequest);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            // TODO: 2019-01-28 gb支付异常未处理
            log.error("execute alipay has IO exception:{} ", e);
        }
    }

    /*
     * @Description: app支付表单
     * @Param:
     * @Author: Bob->
     * @Date: 2019-02-01 11:12
     */
    @Deprecated
    @Operation(summary = "APP支付宝支付，APP支付宝签名后的参数,返回的数据直接调用appSDK")
    @RequestMapping(value = "app/aliPay/", method = RequestMethod.POST)
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse<String> appAliPay(@RequestBody @Valid PayMobileRequest payMobileRequest) {
        log.info("====================支付宝支付表单=APP支付宝支付================payMobileRequest :{}", payMobileRequest);
        payMobileRequest.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);

        return BaseResponse.success(this.alipayUtil(payMobileRequest));
    }

    /*
     * @Description: 支付之前的公共判断条件及数据组装
     * @Param:  payMobileRequest
     * @Author: Bob
     * @Date: 2019-02-22 11:27
     */
    @Deprecated
    private String alipayUtil(PayMobileRequest payMobileRequest) {
        boolean isPayMember = payServiceHelper.isPayMember(payMobileRequest.getTid());
        String id, title, body;
        BigDecimal totalPrice;
        List<TradeVO> trades = Lists.newArrayList();
        if (isPayMember) {
            id = payMobileRequest.getTid();
            //根据付费记录id 查询记录
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(id)
                    .build()).getContext().getPayingMemberRecordTempVO();
            title = payingMemberRecordTempVO.getLevelName().concat("付费会员");
            body = payingMemberRecordTempVO.getLevelName().concat("付费会员").concat(payingMemberRecordTempVO.getPayNum()+"个月");
            totalPrice = payingMemberRecordTempVO.getPayAmount();
        }else {
            id = payServiceHelper.getPayBusinessId(payMobileRequest.getTid(), payMobileRequest.getParentTid(), payMobileRequest.getRepayOrderCode());
            trades = payServiceHelper.checkTrades(id);
            // 支付总金额
            Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(id);
            if (creditRepayFlag) {
                totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(trades);
            } else {
                if (commonUtil.lakalaPayIsOpen()){
                    log.warn("非法支付渠道：订单ID：{}，用户ID：{}", id, trades.get(0).getBuyer().getAccount());
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }
                totalPrice = payServiceHelper.calcTotalPriceByYuan(trades);
            }
            TradeVO trade = trades.get(0);
            title = trade.getTradeItems().get(0).getSkuName();
            body = trade.getTradeItems().get(0).getSkuName() + " " + (trade.getTradeItems().get(0).getSpecDetails
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
            title = title.replaceAll("&", "");
            body = body.replaceAll("&", "");
        }
        log.info("=============body", body);
        log.info("=============title", title);
        PayExtraRequest payExtraRequest = new PayExtraRequest();
        payExtraRequest.setOutTradeNo(id);
        payExtraRequest.setStoreId(payMobileRequest.getStoreId());
        payExtraRequest.setChannelItemId(payMobileRequest.getChannelItemId());
        payExtraRequest.setTerminal(payMobileRequest.getTerminal());
        if (TerminalType.H5.equals(payMobileRequest.getTerminal())) {
            payExtraRequest.setSuccessUrl(payMobileRequest.getSuccessUrl());
        }
        payExtraRequest.setAmount(totalPrice);
        payExtraRequest.setOpenId(payMobileRequest.getOpenId());
        payExtraRequest.setSubject(title);
        payExtraRequest.setBody(body);
        payExtraRequest.setClientIp(HttpUtil.getIpAddr());
        String form = "";
        try {
            if (!isPayMember) {
                //保存支付请求信息到插件
                PluginPayInfoAddRequest pluginPayInfoAddRequest = new PluginPayInfoAddRequest();
                pluginPayInfoAddRequest.setOrderCode(trades.get(0).getId());
                pluginPayInfoAddRequest.setPayRequest(payExtraRequest.toString());
                pluginPayInfoProvider.add(pluginPayInfoAddRequest);
            }

            LinkedHashMap linkedHashMap= (LinkedHashMap) payProvider.pay(BasePayRequest.builder()
                    .payType(PayType.ALIPAY)
                    .payExtraRequest(payExtraRequest)
                    .build()).getContext();
            form = String.valueOf(linkedHashMap.get("form"));
        } catch (SbcRuntimeException e) {
            if (e.getErrorCode() != null && e.getErrorCode().equals(EmpowerErrorCodeEnum.K060003.getCode())) {
                //已支付，手动回调
                Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("1").name("SYSTEM").platform
                        (Platform.BOSS).build();
                if (!isPayMember) {
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
                } else {
                    tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                            .recordId(id)
                            .operator(operator)
                            .build());
                    if (Objects.isNull(id)) {
                        esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                                .customerId(trades.get(0).getBuyer().getId())
                                .build());
                    }
                }
            }
            throw e;
//            throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
        }

        return form;
    }

    /**
     * 根据不同的支付方式获取微信支付对应的appId
     *
     * @return
     */
    @Operation(summary = "根据不同的支付方式获取微信支付对应的appId")
    @RequestMapping(value = "/getAppId/{payGateway}", method = RequestMethod.GET)
    public BaseResponse<Map<String, Object>> getAppId(@PathVariable PayGatewayEnum payGateway) {
        GatewayConfigByGatewayRequest request = new GatewayConfigByGatewayRequest();
        request.setGatewayEnum(payGateway);
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        BaseResponse<PayGatewayConfigResponse> baseResponse = paySettingQueryProvider.getGatewayConfigByGateway(request);
        Map<String, Object> appIdMap = new HashMap<>();
        if (baseResponse.getCode().equals(CommonErrorCodeEnum.K000000.getCode())) {
            appIdMap.put("appId", baseResponse.getContext().getAppId());
        }
        return BaseResponse.success(appIdMap);
    }

    /**
     * 获取不同渠道对应的openid
     *
     * @param code
     * @return
     */
    @Operation(summary = "获取微信支付对应的openId")
    @RequestMapping(value = "/getOpenId/{payGateway}/{code}", method = RequestMethod.GET)
    public BaseResponse<String> getOpenIdByChannel(@PathVariable PayGatewayEnum payGateway, @PathVariable String code) {
        String appId = "";
        String secret = "";
        GatewayConfigByGatewayRequest request = new GatewayConfigByGatewayRequest();
        request.setGatewayEnum(payGateway);
        request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        BaseResponse<PayGatewayConfigResponse> baseResponse = paySettingQueryProvider.getGatewayConfigByGateway(request);
        if (baseResponse.getCode().equals(CommonErrorCodeEnum.K000000.getCode())) {
            appId = baseResponse.getContext().getAppId();
            secret = baseResponse.getContext().getSecret();
        }
        GetAccessTokeResponse getAccessTokeResponse = wechatApi.getWeChatAccessToken(appId, secret, code);
        return BaseResponse.success(getAccessTokeResponse.getOpenid());
    }

    /**
     * 小程序通过code获取openId
     *
     * @param code
     * @return
     */
    @Operation(summary = "获取小程序微信支付对应的openId")
    @RequestMapping(value = "/getOpenId/littleProgram/{code}", method = RequestMethod.GET)
    public BaseResponse<String> getLittleProgramOpenId(@PathVariable String code) {
        String appId;
        String secret;
        BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                .type(Constants.ZERO)
                .build());
        if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(),miniProgramSetByTypeResponseBaseResponse.getCode())) {
            MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
            appId = miniProgramSetVO.getAppId();
            secret = miniProgramSetVO.getAppSecret();
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        log.info("获取小程序微信支付对应的openId==start");
        LittleProgramAuthResponse getAccessTokeResponse = wechatApi.getLittleProgramAccessToken(appId, secret, code);
        log.info("获取小程序微信支付对应的openId==end");
        return BaseResponse.success(getAccessTokeResponse.getOpenid());
    }

    @Deprecated
    @Operation(summary = "余额支付")
    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse balancePay(@RequestBody @Valid PayMobileRequest payMobileRequest) {
        RLock rLock = redissonClient.getFairLock(commonUtil.getOperatorId());
        rLock.lock();
        try {
            PayGatewayConfigResponse gatewayConfigResponse =
                    paySettingQueryProvider.getGatewayConfigByGatewayId(GatewayConfigByGatewayIdRequest.builder()
                            .gatewayId((long) 5)
                            // boss端才有余额支付
                            .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                            .build()).getContext();
            if (gatewayConfigResponse.getPayGateway().getIsOpen().equals(IsOpen.NO)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050099);
            }
            boolean isPayMember = payServiceHelper.isPayMember(payMobileRequest.getTid());
            if(isPayMember) {
                String id = payMobileRequest.getTid();
                //根据付费记录id 查询记录
                PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                        .recordId(id)
                        .build()).getContext().getPayingMemberRecordTempVO();
                BigDecimal totalPrice = payingMemberRecordTempVO.getPayAmount();
                //校验密码是否可用
                CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerById(new
                        CustomerGetByIdRequest(commonUtil.getOperatorId())).getContext();
                if (StringUtils.isBlank(customerGetByIdResponse.getCustomerPayPassword())) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020023);
                }
                if (customerGetByIdResponse.getPayErrorTime() != null && customerGetByIdResponse.getPayErrorTime() == Constants.THREE) {
                    Duration duration = Duration.between(customerGetByIdResponse.getPayLockTime(), LocalDateTime.now());
                    if (duration.toMinutes() < Constants.NUM_30) {
                        //支付密码输错三次，并且锁定时间还未超过30分钟，返回账户冻结错误信息
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010032, new Object[]{30 - duration.toMinutes()});
                    }
                }

                //校验密码是否正确
                CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest = new CustomerCheckPayPasswordRequest();
                customerCheckPayPasswordRequest.setPayPassword(payMobileRequest.getPayPassword());
                customerCheckPayPasswordRequest.setCustomerId(commonUtil.getOperatorId());
                customerSiteProvider.checkCustomerPayPwd(customerCheckPayPasswordRequest);

                // 处理用户余额, 校验余额是否可用
                CustomerFundsByCustomerIdResponse fundsByCustomerIdResponse =
                        customerFundsQueryProvider.getByCustomerId(new CustomerFundsByCustomerIdRequest(
                                commonUtil.getOperatorId())).getContext();
                if (fundsByCustomerIdResponse.getWithdrawAmount().compareTo(totalPrice) < 0) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020027);
                }
                CustomerFundsModifyRequest fundsModifyRequest = new CustomerFundsModifyRequest();
                fundsModifyRequest.setCustomerFundsId(fundsByCustomerIdResponse.getCustomerFundsId());
                fundsModifyRequest.setExpenseAmount(totalPrice);
                customerFundsProvider.balancePay(fundsModifyRequest);

                CustomerFundsDetailAddRequest customerFundsDetailAddRequest =
                        new CustomerFundsDetailAddRequest();
                customerFundsDetailAddRequest.setCustomerId(fundsByCustomerIdResponse.getCustomerId());
                customerFundsDetailAddRequest.setBusinessId(id);
                customerFundsDetailAddRequest.setFundsType(FundsType.BALANCE_PAY);
                customerFundsDetailAddRequest.setReceiptPaymentAmount(totalPrice);
                customerFundsDetailAddRequest.setFundsStatus(FundsStatus.YES);
                customerFundsDetailAddRequest.setAccountBalance(fundsByCustomerIdResponse.getAccountBalance().subtract(totalPrice));
                customerFundsDetailAddRequest.setSubType(FundsSubType.BALANCE_PAY);
                customerFundsDetailAddRequest.setCreateTime(LocalDateTime.now());
                customerFundsDetailProvider.batchAdd(Lists.newArrayList(customerFundsDetailAddRequest));

                PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                payTradeRecordRequest.setBusinessId(id);
                payTradeRecordRequest.setApplyPrice(totalPrice);
                payTradeRecordRequest.setPracticalPrice(payTradeRecordRequest.getApplyPrice());
                payTradeRecordRequest.setResult_code("SUCCESS");
                payTradeRecordRequest.setChannelItemId(payMobileRequest.getChannelItemId());
                payTradeRecordProvider.queryAndSave(payTradeRecordRequest);

                Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name("UNIONB2B")
                        .platform(Platform.THIRD).build();
                tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                        .recordId(id)
                        .operator(operator)
                        .build());
                if (Objects.isNull(id)) {
                    esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                            .customerId(commonUtil.getOperatorId())
                            .build());
                }
            } else {
                String tradeId = payServiceHelper.getPayBusinessId(
                        payMobileRequest.getTid(),
                        payMobileRequest.getParentTid(),
                        payMobileRequest.getRepayOrderCode());
                List<TradeVO> tradeVOS = payServiceHelper.checkTrades(tradeId);
                // 支付总金额
                BigDecimal totalPrice;
                Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(tradeId);
                if(creditRepayFlag){
                    totalPrice = payServiceHelper.calcCreditTotalPriceByYuan(tradeVOS);
                }else {
                    totalPrice = payServiceHelper.calcTotalPriceByYuan(tradeVOS);
                }
                //校验密码是否可用
                CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerById(new
                        CustomerGetByIdRequest(commonUtil.getOperatorId())).getContext();
                if (StringUtils.isBlank(customerGetByIdResponse.getCustomerPayPassword())) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020023);
                }
                if (customerGetByIdResponse.getPayErrorTime() != null && customerGetByIdResponse.getPayErrorTime() == Constants.THREE) {
                    Duration duration = Duration.between(customerGetByIdResponse.getPayLockTime(), LocalDateTime.now());
                    if (duration.toMinutes() < Constants.NUM_30) {
                        //支付密码输错三次，并且锁定时间还未超过30分钟，返回账户冻结错误信息
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010032, new Object[]{30 - duration.toMinutes()});
                    }
                }

                //校验密码是否正确
                CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest = new CustomerCheckPayPasswordRequest();
                customerCheckPayPasswordRequest.setPayPassword(payMobileRequest.getPayPassword());
                customerCheckPayPasswordRequest.setCustomerId(commonUtil.getOperatorId());
                customerSiteProvider.checkCustomerPayPwd(customerCheckPayPasswordRequest);

                // 处理用户余额, 校验余额是否可用
                CustomerFundsByCustomerIdResponse fundsByCustomerIdResponse =
                        customerFundsQueryProvider.getByCustomerId(new CustomerFundsByCustomerIdRequest(
                                commonUtil.getOperatorId())).getContext();
                if (fundsByCustomerIdResponse.getWithdrawAmount().compareTo(totalPrice) < 0) {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020027);
                }
                CustomerFundsModifyRequest fundsModifyRequest = new CustomerFundsModifyRequest();
                fundsModifyRequest.setCustomerFundsId(fundsByCustomerIdResponse.getCustomerFundsId());
                fundsModifyRequest.setExpenseAmount(totalPrice);
                customerFundsProvider.balancePay(fundsModifyRequest);

                BigDecimal tradeTotalPrice = BigDecimal.ZERO;
                List<CustomerFundsDetailAddRequest> customerFundsDetailAddRequestList = new ArrayList<>();
                for (TradeVO tradeVO : tradeVOS) {
                    //定金预售取定金/尾款，其他取订单总金额
                    BigDecimal price = BigDecimal.ZERO;
                    if(creditRepayFlag){
                        price = tradeVO.getCanRepayPrice();
                    }else{
                        price = Objects.nonNull(tradeVO.getIsBookingSaleGoods()) && tradeVO.getBookingType() == BookingType.EARNEST_MONEY ? totalPrice : tradeVO.getTradePrice().getTotalPrice();
                    }

                    tradeTotalPrice = tradeTotalPrice.add(price);
                    CustomerFundsDetailAddRequest customerFundsDetailAddRequest =
                            new CustomerFundsDetailAddRequest();
                    customerFundsDetailAddRequest.setCustomerId(fundsByCustomerIdResponse.getCustomerId());
                    customerFundsDetailAddRequest.setBusinessId(tradeId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID) ? tradeId : tradeVO.getId());
                    customerFundsDetailAddRequest.setFundsType(FundsType.BALANCE_PAY);
                    customerFundsDetailAddRequest.setReceiptPaymentAmount(price);
                    customerFundsDetailAddRequest.setFundsStatus(FundsStatus.YES);
                    customerFundsDetailAddRequest.setAccountBalance(fundsByCustomerIdResponse.getAccountBalance().subtract(tradeTotalPrice));
                    customerFundsDetailAddRequest.setSubType(FundsSubType.BALANCE_PAY);
                    customerFundsDetailAddRequest.setCreateTime(LocalDateTime.now());
                    customerFundsDetailAddRequestList.add(customerFundsDetailAddRequest);
                }

                customerFundsDetailProvider.batchAdd(customerFundsDetailAddRequestList);
                // 新增交易记录
                List<PayTradeRecordRequest> payTradeRecordRequests = tradeVOS.stream()
                        .map(tradeVO -> {
                            PayTradeRecordRequest payTradeRecordRequest = new PayTradeRecordRequest();
                            payTradeRecordRequest.setBusinessId(tradeId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID) ? tradeId : tradeVO.getId());
                            if(creditRepayFlag){
                                payTradeRecordRequest.setApplyPrice(tradeVO.getCanRepayPrice());
                            }else{
                                payTradeRecordRequest.setApplyPrice(payServiceHelper.calcTotalPriceByYuan(Collections.singletonList(tradeVO)));
                            }
                            payTradeRecordRequest.setPracticalPrice(payTradeRecordRequest.getApplyPrice());
                            payTradeRecordRequest.setResult_code("SUCCESS");
                            payTradeRecordRequest.setChannelItemId(payMobileRequest.getChannelItemId());
                            return payTradeRecordRequest;
                        }).collect(Collectors.toList());
                payTradeRecordProvider.batchSavePayTradeRecord(payTradeRecordRequests);

                if(creditRepayFlag){
                    List<String> ids = tradeVOS.stream().map(TradeVO::getId).collect(Collectors.toList());
                    payAndRefundCallBackProvider.creditCallBack(
                            CreditCallBackRequest.builder()
                                    .repayOrderCode(tradeId)
                                    .ids(ids)
                                    .userId(commonUtil.getOperatorId())
                                    .repayType(CreditRepayTypeEnum.BALANCE).build()
                    );
                }else{
                    // 支付成功，处理订单
                    List<TradePayCallBackOnlineDTO> tradePayCallBackOnlineDTOS = tradeVOS.stream()
                            .map(tradeVO -> {
                                TradePayCallBackOnlineDTO tradePayCallBackOnlineDTO = new TradePayCallBackOnlineDTO();
                                tradePayCallBackOnlineDTO.setTrade(KsBeanUtil.convert(tradeVO, TradeDTO.class));
                                PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest.builder()
                                        .payOrderId(tradeId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID) ? tradeVO.getTailPayOrderId() : tradeVO.getPayOrderId()).build()).getContext().getPayOrder();
                                tradePayCallBackOnlineDTO.setPayOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class));
                                return tradePayCallBackOnlineDTO;
                            }).collect(Collectors.toList());
                    // 若订单为代销订单，则更新为普通订单
                    for (TradePayCallBackOnlineDTO callBackOnlineDTO : tradePayCallBackOnlineDTOS) {
                        TradeDTO tradeDTO = callBackOnlineDTO.getTrade();
                        if (payServiceHelper.checkAndWrapperTradeSellInfo(tradeDTO)) {
                            tradeProvider.update(new TradeUpdateRequest(tradeDTO));
                        }
                    }
                    Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name("UNIONB2B")
                            .platform(Platform.THIRD).build();
                    tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                            .requestList(tradePayCallBackOnlineDTOS)
                            .operator(operator)
                            .build());
                    if (CollectionUtils.isEmpty(tradePayCallBackOnlineDTOS)) {
                        esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                                .customerId(tradeVOS.get(0).getBuyer().getId())
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }


    /***
     * H5 银联云闪付
     *
     * @date 14:33 2021/3/19
     * @author zhangyong
     * @param encrypted
     * @param response
     * @return
     */
    @Deprecated
    @Operation(summary = "银联云闪付支付H5消费支付接口，返回html")
    @Parameter(name = "encrypted", description = "base64编码后的支付请求", required = true)
    @RequestMapping(value = "/pay-union/{encrypted}", method = RequestMethod.GET)
    @GlobalTransactional
    public void payUnionCloud(@PathVariable String encrypted, HttpServletResponse response) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        PayMobileRequest payMobileRequest = JSON.parseObject(decrypted, PayMobileRequest.class);
        boolean isPayMember = payServiceHelper.isPayMember(payMobileRequest.getTid());
        log.info("====================银联支付逻辑开始================");
        String tradeId,title,body;
        BigDecimal totalPrice;
        LocalDateTime orderTimeOut;
        TradeVO trade = new TradeVO();
        if (isPayMember) {
            tradeId = payMobileRequest.getTid();
        } else {
            tradeId = payServiceHelper.getPayBusinessId(payMobileRequest.getTid(), payMobileRequest.getParentTid(), payMobileRequest.getRepayOrderCode());
        }
        String html = "";
        try {
            try {
                if (isPayMember) {
                    orderTimeOut = LocalDateTime.now().plusMinutes(10L);
                    //根据付费记录id 查询记录
                    PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                            .recordId(tradeId)
                            .build()).getContext().getPayingMemberRecordTempVO();
                    title = payingMemberRecordTempVO.getLevelName().concat("付费会员");
                    body = payingMemberRecordTempVO.getLevelName().concat("付费会员").concat(payingMemberRecordTempVO.getPayNum()+"个月");
                    totalPrice = payingMemberRecordTempVO.getPayAmount();
                } else {
                    //1.支付前检查订单状态
                    List<TradeVO> trades = payServiceHelper.checkTrades(tradeId);
                    trade = trades.get(0);
                    //2..计算价格
                    log.info("=====================计算价格开始==================");
                    Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(tradeId);
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
                    title = dataMap.get("title");
                    body = dataMap.get("body");
                }
                PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
                        GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONPAY, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
                //4.组装消费接口数据
                log.info("payUnionCloud组装银联云闪付支付数据开始");
                UnionPayRequest unionPayRequest = new UnionPayRequest();
                unionPayRequest.setOutTradeNo(tradeId);
                unionPayRequest.setSubject(title);
                unionPayRequest.setBody(body);
                unionPayRequest.setAmount(totalPrice);
                unionPayRequest.setApiKey(payGatewayConfigResponse.getApiKey());
                //前端回调地址 \(^o^)/~ h5  自己传值
                unionPayRequest.setFrontUrl(payMobileRequest.getSuccessUrl());
                //后台回调地址
                unionPayRequest.setNotifyUrl(payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback" +
                        "/unionPayCallBack");
                //来源
                unionPayRequest.setTerminal(TerminalType.H5);
                //银联支付pc渠道编号
                unionPayRequest.setChannelItemId(28L);
                unionPayRequest.setClientIp(HttpUtil.getIpAddr());
                //订单有效时间最后的有效时间
                unionPayRequest.setOrderTimeOut(orderTimeOut);
                log.info("payUnionCloud组装调用支付provider开始 requset:{}", unionPayRequest.toString());
                //5.调用银联支付接口
//                html = unionCloudPayProvider.payUnionCloud(unionPayRequest).getContext();

                html = (String) payProvider.pay(BasePayRequest.builder()
                        .payType(PayType.UNIONCLONDPAY)
                        .unionPayRequest(unionPayRequest)
                        .build()).getContext();
                if (!isPayMember) {
                    log.info("payUnionCloud组装调用支付provider结束,开始更新订单数据");

                    //6.初步更新订单的开始支付时间数据
                    trade.getTradeState().setStartPayTime(LocalDateTime.now());
                    tradeProvider.update(TradeUpdateRequest.builder().trade(KsBeanUtil.convert(trade,
                            TradeDTO.class)).build());
                    log.info("结束更新订单数据，订单号：{},开始支付时间：{StartPayTime}", trade.getTradeState().getStartPayTime());
                }
            } catch (SbcRuntimeException e) {
                log.error("银联支付异常，{}", e);
                throw e;
//                throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
            } catch (Exception e) {
                log.error("银联支付异常，{}", e);
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

    @Deprecated
    @PostMapping("/pay-union/get-tn")
    @Operation(summary = "银联云闪付支付app获取tn，获取tn")
    public BaseResponse<String> getTn(@RequestBody  PayMobileRequest payMobileRequest){
        boolean isPayMember = payServiceHelper.isPayMember(payMobileRequest.getTid());
        String tradeId,title,body;
        BigDecimal totalPrice;
        LocalDateTime orderTimeOut;
        TradeVO trade = new TradeVO();
        if (isPayMember) {
            tradeId = payMobileRequest.getTid();
            orderTimeOut = LocalDateTime.now().plusMinutes(10L);
            //根据付费记录id 查询记录
            PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                    .recordId(tradeId)
                    .build()).getContext().getPayingMemberRecordTempVO();
            title = payingMemberRecordTempVO.getLevelName().concat("付费会员");
            body = payingMemberRecordTempVO.getLevelName().concat("付费会员").concat(payingMemberRecordTempVO.getPayNum()+"个月");
            totalPrice = payingMemberRecordTempVO.getPayAmount();
        } else {
            tradeId = payServiceHelper.getPayBusinessId(payMobileRequest.getTid(), payMobileRequest.getParentTid(), payMobileRequest.getRepayOrderCode());
            //1.支付前检查订单状态
            List<TradeVO> trades = payServiceHelper.checkTrades(tradeId);
            trade = trades.get(0);
            //2..计算价格
            Boolean creditRepayFlag = payServiceHelper.isCreditRepayFlag(tradeId);
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
            //3.组装消费接口数据的body  title 获取相应的支付网关配置
            Map<String, String> dataMap = wrapperRequestAndCheckTrades(trades, trade);
            title = dataMap.get("title");
            body = dataMap.get("body");
        }


        PayGatewayConfigResponse payGatewayConfigResponse = paySettingQueryProvider.getGatewayConfigByGateway(new
                GatewayConfigByGatewayRequest(PayGatewayEnum.UNIONPAY, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
        //4.组装消费接口数据
        UnionPayRequest unionPayRequest = new UnionPayRequest();
        unionPayRequest.setOutTradeNo(tradeId);
        unionPayRequest.setSubject(title);
        unionPayRequest.setBody(body);
        unionPayRequest.setAmount(totalPrice);
        unionPayRequest.setApiKey(payGatewayConfigResponse.getApiKey());
        unionPayRequest.setFrontUrl(payMobileRequest.getSuccessUrl());
        //后台回调地址
        unionPayRequest.setNotifyUrl(payGatewayConfigResponse.getBossBackUrl() + "/tradeCallback" +
                "/unionPayCallBack");
        //来源
        unionPayRequest.setTerminal(TerminalType.APP);
        //银联支付pc渠道编号
        unionPayRequest.setChannelItemId(29L);
        unionPayRequest.setClientIp(HttpUtil.getIpAddr());
        //订单有效时间最后的有效时间
        unionPayRequest.setOrderTimeOut(orderTimeOut);
        if (!isPayMember) {
            //6.初步更新订单的开始支付时间数据
            trade.getTradeState().setStartPayTime(LocalDateTime.now());
            tradeProvider.update(TradeUpdateRequest.builder().trade(KsBeanUtil.convert(trade,
                    TradeDTO.class)).build());
        }
        return unionCloudPayProvider.getTn(BasePayRequest.builder().unionPayRequest(unionPayRequest).build());
    }
}
