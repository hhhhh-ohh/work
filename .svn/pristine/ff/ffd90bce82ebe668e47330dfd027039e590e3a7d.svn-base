package com.wanmi.sbc.pay;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.provider.credit.CustomerCreditRepayProvider;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayByRepayCodeRequest;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayModifyRequest;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayAndOrdersByRepayCodeResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.bean.vo.CustomerCreditRepayVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.empower.api.provider.pay.PayProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.unionb2b.UnionB2BProvider;
import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.request.pay.WxPayV3CertificatesRequest;
import com.wanmi.sbc.empower.api.request.pay.gateway.GatewayConfigByGatewayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaCasherNotifyRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaNotifyRequest;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionDirectRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayCompanyPaymentInfoRequest;
import com.wanmi.sbc.empower.api.response.pay.WxPayV3CertificatesResponse;
import com.wanmi.sbc.empower.api.response.pay.geteway.PayGatewayConfigResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayCompanyPaymentRsponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayRefundCallBackDataResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayRefundCallBackResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayResultResponse;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.ledger.VerifySignUtils;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.paycallbackresult.PayCallBackResultProvider;
import com.wanmi.sbc.order.api.provider.paycallbackresult.PayCallBackResultQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.plugin.PluginPayInfoProvider;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.refundcallbackresult.RefundCallBackResultProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultAddRequest;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultListRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByIdRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesModifyRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoModifyRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultAddRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByConditionRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByIdRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderOnlineRefundRequest;
import com.wanmi.sbc.order.api.request.settlement.UpdateSettlementDetailStatusRequest;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnCodeResponse;
import com.wanmi.sbc.order.api.response.trade.TradePageCriteriaResponse;
import com.wanmi.sbc.order.bean.dto.*;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.PayTimeSeriesVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.pay.service.PayAndRefundCallBackTaskService;
import com.wanmi.sbc.pay.service.PayServiceHelper;
import com.wanmi.sbc.pay.service.PayVerifySignUtils;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayUtil;
import com.wanmi.sbc.util.CallbackOperateLogMQUtil;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 交易回调
 * Created by sunkun on 2017/8/8.
 */
@Tag(name = "PayCallbackController", description = "交易回调")
@RestController
@Validated
@RequestMapping("/tradeCallback")
@Slf4j
public class PayCallbackController {

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private WxPayProvider wxPayProvider;

    @Autowired
    private CallbackOperateLogMQUtil callbackOperateLogMQUtil;

    @Autowired
    private PayCallBackResultProvider payCallBackResultProvider;

    @Autowired
    private PayAndRefundCallBackTaskService payCallBackTaskService;

    @Autowired
    private RefundCallBackResultProvider refundCallBackResultProvider;

    @Autowired
    private CustomerCreditRepayProvider customerCreditRepayProvider;

    @Autowired
    private CreditRepayQueryProvider customerCreditRepayQueryProvider;

    @Autowired
    private PayServiceHelper payServiceHelper;

    @Autowired
    private UnionB2BProvider unionB2BProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private PayTradeRecordProvider payTradeRecordProvider;

    @Autowired private PluginPayInfoProvider pluginPayInfoProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private PayCallBackResultQueryProvider payCallBackResultQueryProvider;

    @Autowired
    private SettlementDetailProvider settlementDetailProvider;

    @Autowired
    private PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    @Autowired
    private PayTimeSeriesProvider payTimeSeriesProvider;

    @Value("${callback.error.num.lakala}")
    private int eoorNum;

    /**
     * 获取所有回调的参数
     *
     * @param request
     * @return 参数Map
     */
    private static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 银联企业支付异步回调
     *
     * @param request
     * @param response
     */
    @Operation(summary = "银联企业支付异步回调")
    @RequestMapping(value = "/unionB2BCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void unionB2BCallBack(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        log.info("=========================银联企业支付异步回调接收报文返回开始=======================");
        String encoding = request.getParameter("encoding");
        log.info("返回报文中encoding=[{}]", encoding);
        Map<String, String> respParam = getAllRequestParam(request);
        //验签
        if (!unionB2BProvider.unionCheckSign(respParam).getContext()) {
            log.info("验证签名结果[失败].");
        } else {
            log.info("验证签名结果[成功].");
            log.info("-------------银联支付回调,respParam：{}------------", JSONObject.toJSONString(respParam));
            String respCode = respParam.get("respCode");
            if (Constants.STR_00.equals(respCode)) {
                //判断respCode=00 后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
                String payNo = request.getParameter("orderId");
                PayTimeSeriesVO payTimeSeriesVO = payTimeSeriesQueryProvider.getById(PayTimeSeriesByIdRequest.builder().payNo(payNo).build()).getContext().getPayTimeSeriesVO();
                String businessId= null;
                if(payNo.startsWith(GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)){
                    businessId = payNo;
                }else{
//                    payTimeSeriesVO = payTimeSeriesQueryProvider.getById(PayTimeSeriesByIdRequest.builder().payNo(payNo).build()).getContext().getPayTimeSeriesVO();
                    businessId = payTimeSeriesVO.getBusinessId();
                }
                UnionPayRequest unionPayRequest = new UnionPayRequest();
                unionPayRequest.setApiKey(request.getParameter("merId"));
                unionPayRequest.setOutTradeNo(payNo);
                unionPayRequest.setTxnTime(request.getParameter("txnTime"));
                Map<String, String> resultMap = unionB2BProvider.getUnionPayResult(unionPayRequest).getContext();


                PayTradeRecordResponse payTradeRecord;
                //交易成功，更新商户订单状态
                if (resultMap != null && Constants.STR_00.equals(resultMap.get(Constants.RESPCODE))) {
//                    payTradeRecord = payTradeRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
//                            (resultMap.get("orderId"))).getContext();
                    Operator operator =
                            Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.UNIONB2B.name())
                                    .account(PayGatewayEnum.UNIONB2B.name()).platform(Platform.THIRD).build();

                    //单笔支付与组合支付区分，获取支付订单的信息
                    boolean isMergePay = isMergePayOrder(businessId);
                    boolean isCreditRepay = payServiceHelper.isCreditRepayFlag(businessId);
                 //   unionB2BProvider.unionCallBack(resultMap);
                    if (isMergePay) {
                        List<TradeVO> trades = new ArrayList<>();
                        trades.addAll(tradeQueryProvider.getListByParentId(TradeListByParentIdRequest.builder()
                                .parentTid(businessId).build()).getContext().getTradeVOList());
                        //订单合并支付场景状态采样
                        boolean paid =
                                trades.stream().anyMatch(i -> i.getTradeState().getPayState() == PayState.PAID);
                        boolean cancel =
                                trades.stream().anyMatch(i -> i.getTradeState().getFlowState() == FlowState.VOID);
                        //订单的支付渠道。17、18、19是我们自己对接的支付宝渠道， 表：pay_channel_item
                        if (cancel || paid ) {
                            //重复支付，直接退款
                            log.error("订单重复支付或过期作废,不直接退款payCallBackResult:{}.businessId:{}",respParam,businessId);
                            payTimeSeriesVO.setCallbackTime(LocalDateTime.now());
                            payTimeSeriesVO.setTradeNo(resultMap.get("queryId"));
                            payTimeSeriesVO.setStatus(TradeStatus.SUCCEED);
                            payTimeSeriesVO.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(resultMap.get("txnAmt"))).
                                    divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
                            payTimeSeriesProvider.modify(KsBeanUtil.convert(payTimeSeriesVO,PayTimeSeriesModifyRequest.class));
                        } else {
                            unionB2BProvider.unionCallBackByTimeSeries(resultMap);
                            payCallbackOnline(trades, operator, true);

                        }
                    } else if (isCreditRepay) {
                        unionB2BProvider.unionCallBackByTimeSeries(resultMap);
                        creditUnionPayCallbackHandle(businessId, resultMap,payNo);
                    } else {
                        List<TradeVO> trades = new ArrayList<>();
                        TradeVO trade = tradeQueryProvider.getById(TradeGetByIdRequest
                                .builder().tid(businessId).build()).getContext().getTradeVO();
                        trades.add(trade);
                        if (trade.getTradeState().getFlowState() == FlowState.VOID
                                || (trade.getTradeState().getPayState() == PayState.PAID)) {
                            // 同一批订单重复支付或过期作废，直接退款
                            //                            alipayRefundHandle(out_trade_no,
                            // total_amount);
                            log.error(
                                    "订单重复支付或过期作废,不直接退款payCallBackResult:{}.businessId:{}",
                                    respParam,
                                    businessId);
                            payTimeSeriesVO.setCallbackTime(LocalDateTime.now());
                            payTimeSeriesVO.setTradeNo(resultMap.get("queryId"));
                            payTimeSeriesVO.setStatus(TradeStatus.SUCCEED);
                            payTimeSeriesVO.setPracticalPrice(BigDecimal.valueOf(Double.valueOf(resultMap.get("txnAmt"))).
                                    divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN));
                            payTimeSeriesProvider.modify(KsBeanUtil.convert(payTimeSeriesVO,PayTimeSeriesModifyRequest.class));
                        } else {
                            unionB2BProvider.unionCallBackByTimeSeries(resultMap);
                            payCallbackOnline(trades, operator, false);
                        }
                    }
                }
                response.getWriter().print("ok");
            }

        }

        log.info("银联企业支付异步回调接收报文返回结束");

    }

    /**
     * 银联退款回调
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Operation(summary = "银联退款回调")
    @RequestMapping(value = "/unionRefundCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void unionRefundCallBack(HttpServletRequest request, HttpServletResponse response) throws ServletException
            , IOException {
        log.info("退款回调接收后台通知开始");
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(request);
        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!unionB2BProvider.unionCheckSign(reqParam).getContext()) {
            log.info("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            log.info("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
            String respCode = reqParam.get("respCode");
            if (Constants.STR_00.equals(respCode)) {
                PayTradeRecordResponse payTradeRecord =
                        payTradeRecordQueryProvider.getTradeRecordByOrderCode(new TradeRecordByOrderCodeRequest
                                (reqParam.get("orderId"))).getContext();
                ReturnOrderVO returnOrder;
                if (payTradeRecord.getBusinessId().startsWith(Constants.STR_RT)) {
                    returnOrder = returnOrderQueryProvider.listByCondition(ReturnOrderByConditionRequest.builder()
                            .businessTailId(payTradeRecord.getBusinessId()).build()).getContext().getReturnOrderList().get(0);
                } else {
                    returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder()
                            .rid(payTradeRecord.getBusinessId()).build()).getContext();
                }
                RefundOrderByReturnCodeResponse refundOrder =
                        refundOrderQueryProvider.getByReturnOrderCode(new RefundOrderByReturnOrderCodeRequest(payTradeRecord.getBusinessId())).getContext();
                if (payTradeRecord.getBusinessId().startsWith(Constants.STR_RT)) {
                    refundOrder.setRefundChannel(RefundChannel.TAIL);
                }
                Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name("UNIONB2B")
                        .account("UNIONB2B").platform(Platform.THIRD).build();
                unionB2BProvider.unionCallBack(reqParam);
                returnOrderProvider.onlineRefund(
                        ReturnOrderOnlineRefundRequest.builder().operator(operator)
                                .returnOrder(KsBeanUtil.convert(returnOrder, ReturnOrderDTO.class))
                                .refundOrder(KsBeanUtil.convert(refundOrder, RefundOrderDTO.class)).build());
                response.getWriter().print("ok");
            }
        }
        log.info("退款回调接收后台通知结束");
    }

    @Operation(summary = "获取未支付订单，拼装对应支付报文信息")
    @GetMapping(value = "/getNotPaidTrade")
    public void getNotPaidTrade() {
        int pageSize = 2000;
        String xmlStr = "<xml><appid><![CDATA[wxb67fac0bbb6b4031]]></appid><bank_type><![CDATA[OTHERS]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1489104242]]></mch_id><nonce_str><![CDATA[jr4itIa2Kin4j9p5VRV1UDAmZLsZGA8P]]></nonce_str><openid><![CDATA[o6wq55YZ9xrgyQwQeka6btYt5HOQ]]></openid><out_trade_no><![CDATA[%s]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[DA4F5784AEA7A17B608267EF8131A87A]]></sign><time_end><![CDATA[20200701144442]]></time_end><total_fee>%s</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000616202007015217733087]]></transaction_id></xml>\r\n";
        OutputStream out = null;
        try {
            String savePath = "/data";
            File file = new File(savePath);
            if (!file.exists()) {//保存文件夹不存在则建立
                file.mkdirs();
            }
            File xmlfile = new File("/data/add.txt");
            if (!xmlfile.exists()) {
                if (!xmlfile.createNewFile()) {
                    log.error("文件创建失败!");
                }
            }
            out = Files.newOutputStream(Paths.get("/data/add.txt"));
            TradeQueryDTO tradeQueryRequest = new TradeQueryDTO();
            tradeQueryRequest.setBeginTime(LocalDate.now().toString());
            tradeQueryRequest.setEndTime(LocalDate.now().toString());
            tradeQueryRequest.setTradeState(TradeStateDTO.builder().payState(PayState.NOT_PAID).build());
            tradeQueryRequest.setPageSize(pageSize);
            TradePageCriteriaResponse tradePageCriteriaResponse = tradeQueryProvider.pageCriteria(TradePageCriteriaRequest.builder()
                    .tradePageDTO(tradeQueryRequest)
                    .build()).getContext();
            long totalPages = tradePageCriteriaResponse.getTradePage().getTotal();
            log.info("未支付订单总条数：total=:{}", totalPages);
            int pageNum =  ((int)totalPages / pageSize) + 1;
            if (totalPages > 1) {
                OutputStream finalOut = out;
                tradePageCriteriaResponse.getTradePage().getContent().forEach(tradeVO -> {
                    String totalPrice = tradeVO.getTradePrice().getTotalPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).toString();
                    String xmlStrNew = String.format(xmlStr, tradeVO.getId(), totalPrice);
                    try {
                        finalOut.write(xmlStrNew.getBytes(StandardCharsets.UTF_8.name()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                for (int num = 1; num < pageNum; num++) {
                    tradeQueryRequest.setPageNum(num);
                    tradePageCriteriaResponse = tradeQueryProvider.pageCriteria(TradePageCriteriaRequest.builder()
                            .tradePageDTO(tradeQueryRequest)
                            .build()).getContext();
                    OutputStream finalOut1 = out;
                    tradePageCriteriaResponse.getTradePage().getContent().forEach(tradeVO -> {
                        String totalPrice = tradeVO.getTradePrice().getTotalPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).toString();
                        String xmlStrNew = String.format(xmlStr, tradeVO.getId(), totalPrice);
                        try {
                            finalOut1.write(xmlStrNew.getBytes(StandardCharsets.UTF_8.name()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 微信支付异步回调
     * 通过信号量控制回调并发数，代码中默认8，线上配置16
     *
     * @param request
     * @param response
     */
    @Operation(summary = "微信支付异步回调")
    @RequestMapping(value = "/paySucLockForBatch", method = {RequestMethod.POST, RequestMethod.GET})
    public void paySucLockForBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            paySucForBatch(request, response);
        } catch (Exception e) {
            response.getWriter().write(WXPayUtil.setXML("FAIL", "ERROR"));
        }
    }

    /**
     * 微信支付异步回调
     *
     * @param request
     * @param response
     */
    @GlobalTransactional
    public void paySucForBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("======================微信支付异步通知回调start======================");
        //支付回调结果
        String result = WXPayConstants.SUCCESS;
        //微信回调结果参数对象
        WxPayResultResponse wxPayResultResponse = new WxPayResultResponse();
        BufferedReader reader = null;
        try {
            //获取回调数据输入流
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder retXml = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                retXml.append(line);
            }
            //微信支付异步回调结果xml
            log.info("微信支付异步通知回调结果xml===={}", retXml);
            String retXmlStr;
            retXmlStr = request.getParameter("xml").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
//            String retXmlStr = "<xml><appid><![CDATA[wxb67fac0bbb6b4031]]></appid><bank_type><![CDATA[OTHERS]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1489104242]]></mch_id><nonce_str><![CDATA[jr4itIa2Kin4j9p5VRV1UDAmZLsZGA8P]]></nonce_str><openid><![CDATA[o6wq55YZ9xrgyQwQeka6btYt5HOQ]]></openid><out_trade_no><![CDATA[O202007011444302542]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[DA4F5784AEA7A17B608267EF8131A87A]]></sign><time_end><![CDATA[20200701144442]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000616202007015217733087]]></transaction_id></xml>";
            retXmlStr = retXmlStr.replaceAll("<coupon_id_[0-9]{0,11}[^>]*>(.*?)</coupon_id_[0-9]{0,11}>", "");
            retXmlStr = retXmlStr.replaceAll("<coupon_type_[0-9]{0,11}[^>]*>(.*?)</coupon_type_[0-9]{0,11}>", "");
            retXmlStr = retXmlStr.replaceAll("<coupon_fee_[0-9]{0,11}[^>]*>(.*?)</coupon_fee_[0-9]{0,11}>", "");

            //将回调数据写入数据库
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("xml", WxPayResultResponse.class);
            Class<?>[] classes = new Class[] { WxPayResultResponse.class};
            xStream.allowTypes(classes);
            wxPayResultResponse = (WxPayResultResponse) xStream.fromXML(retXmlStr);
            //如果线程池队列已满，则采取拒绝策略（AbortPolicy），抛出RejectedExecutionException异常，则将对应的回调改为处理失败，然后通过定时任务处理补偿
            try {
                addPayCallBackResult(PayCallBackResultAddRequest.builder()
                        .businessId(wxPayResultResponse.getOut_trade_no())
                        .resultXml(retXmlStr)
                        .resultContext(retXmlStr)
                        .resultStatus(PayCallBackResultStatus.HANDLING)
                        .errorNum(0)
                        .payType(PayCallBackType.WECAHT)
                        .build());
                payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.WECAHT)
                        .wxPayCallBackResultStr(retXmlStr)
                        .wxPayCallBackResultXmlStr(retXmlStr)
                        .build());
            } catch (RejectedExecutionException e) {
                addPayCallBackResult(PayCallBackResultAddRequest.builder()
                        .businessId(wxPayResultResponse.getOut_trade_no())
                        .resultContext(retXmlStr)
                        .resultXml(retXmlStr)
                        .resultStatus(PayCallBackResultStatus.TODO)
                        .errorNum(0)
                        .payType(PayCallBackType.WECAHT)
                        .build());
            }
        } catch (Exception e) {
            log.error("微信异步通知处理失败:", e);
            result = "fail";
            throw e;
        } finally {
            // 异步消息接收后处理结果返回微信
            wxCallbackResultHandle(response, result);
            log.info("微信异步通知完成：结果={}，微信交易号={}", result, wxPayResultResponse.getTransaction_id());
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 微信支付异步回调
     *
     * @param request
     * @param response
     */
    @Operation(summary = "微信支付异步回调")
    @PostMapping(value = "/WXPaySuccessCallBack")
    public void wxPayBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("======================微信支付异步通知回调start======================");
        //支付回调结果
        String result = WXPayConstants.SUCCESS;
        //微信回调结果参数对象
        WxPayResultResponse wxPayResultResponse = new WxPayResultResponse();
        BufferedReader reader = null;
        try {
            //获取回调数据输入流
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder retXml = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                retXml.append(line);
            }
            //微信支付异步回调结果xml
            log.info("微信支付异步通知回调结果xml===={}", retXml);
            String retXmlStr = retXml.toString();
            retXmlStr = retXmlStr.replaceAll("<coupon_id_[0-9]{0,11}[^>]*>(.*?)</coupon_id_[0-9]{0,11}>", "");
            retXmlStr = retXmlStr.replaceAll("<coupon_type_[0-9]{0,11}[^>]*>(.*?)</coupon_type_[0-9]{0,11}>", "");
            retXmlStr = retXmlStr.replaceAll("<coupon_fee_[0-9]{0,11}[^>]*>(.*?)</coupon_fee_[0-9]{0,11}>", "");

            //将回调数据写入数据库
            String out_trade_no = retXmlStr.split("out_trade_no")[1].split("CDATA")[1]
                    .replace("[", "").replace("]]></", "");
            //如果线程池队列已满，则采取拒绝策略（AbortPolicy），抛出RejectedExecutionException异常，则将对应的回调改为处理失败，然后通过定时任务处理补偿
            try {
                addPayCallBackResult(PayCallBackResultAddRequest.builder()
                        .businessId(out_trade_no)
                        .resultXml(retXml.toString())
                        .resultContext(retXmlStr)
                        .resultStatus(PayCallBackResultStatus.HANDLING)
                        .errorNum(0)
                        .payType(PayCallBackType.WECAHT)
                        .build());
                payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.WECAHT)
                        .wxPayCallBackResultStr(retXmlStr)
                        .wxPayCallBackResultXmlStr(retXml.toString())
                        .build());
                //保存支付请求信息到插件
                PluginPayInfoModifyRequest pluginPayInfoModifyRequest = new PluginPayInfoModifyRequest();
                pluginPayInfoModifyRequest.setOrderCode(out_trade_no);
                pluginPayInfoModifyRequest.setPayResponse(retXml.toString());
                pluginPayInfoProvider.modify(pluginPayInfoModifyRequest);
            } catch (RejectedExecutionException e) {
                addPayCallBackResult(PayCallBackResultAddRequest.builder()
                        .businessId(out_trade_no)
                        .resultContext(retXmlStr)
                        .resultXml(retXml.toString())
                        .resultStatus(PayCallBackResultStatus.TODO)
                        .errorNum(0)
                        .payType(PayCallBackType.WECAHT)
                        .build());
            }
        } catch (Exception e) {
            log.error("微信异步通知处理失败:", e);
            result = "fail";
            throw e;
        } finally {
            // 异步消息接收后处理结果返回微信
            wxCallbackResultHandle(response, result);
            log.info("微信异步通知完成：结果={}，微信交易号={}" , result , wxPayResultResponse.getTransaction_id());
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 保存支付回调结果
     * @Date 14:08 2020/7/20
     * @Param [resultAddRequest]
     **/
    private void addPayCallBackResult(PayCallBackResultAddRequest resultAddRequest) {
        try {
            payCallBackResultProvider.add(resultAddRequest);
        } catch (SbcRuntimeException e) {
            //business_id唯一索引报错捕获，不影响流程处理
            if (!e.getErrorCode().equals(OrderErrorCodeEnum.K050145.getCode())) {
                throw e;
            }
            e.printStackTrace();
        }
    }


    /**
     * @return void
     * @Author lvzhenwei
     * @Description 保存退款回调结果
     * @Date 14:08 2020/7/20
     * @Param [resultAddRequest]
     **/
    private void addRefundCallBackResult(RefundCallBackResultAddRequest request) {
        try {
            refundCallBackResultProvider.add(request);
        } catch (SbcRuntimeException e) {
            //business_id唯一索引报错捕获，不影响流程处理
            if (!e.getErrorCode().equals(OrderErrorCodeEnum.K050145.getCode())) {
                throw e;
            }
            e.printStackTrace();
        }
    }

    /**
     * 微信支付退款成功异步回调
     *
     * @param request
     * @param response
     */
    @Operation(summary = "微信支付退款成功异步回调")
    @PostMapping(value = "/WXPayRefundSuccessCallBack")
    public void wxPayRefundSuccessCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PayGatewayConfigResponse payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new
                GatewayConfigByGatewayRequest(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID)).getContext();
        String apiKey = payGatewayConfig.getApiKey();
        InputStream inStream;
        String refund_status = "";
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            log.info("refund:微信退款----start----");
            // 获取微信调用我们notify_url的返回信息
            String result = new String(outSteam.toByteArray(), StandardCharsets.UTF_8);
            log.info("refund:微信退款----result----={}" , result);
            // 关闭流
            outSteam.close();
            inStream.close();

            // xml转换为map
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            WxPayRefundCallBackResponse refundCallBackResponse = (WxPayRefundCallBackResponse) WXPayUtil.
                    mapToObject(map, WxPayRefundCallBackResponse.class);
            if (WXPayConstants.SUCCESS.equalsIgnoreCase(refundCallBackResponse.getReturn_code())) {
                log.info("refund:微信退款----返回成功");
                /** 以下字段在return_code为SUCCESS的时候有返回： **/
                // 加密信息：加密信息请用商户秘钥进行解密，详见解密方式
                String req_info = refundCallBackResponse.getReq_info();
                /**
                 * 解密方式
                 * 解密步骤如下：
                 * （1）对加密串A做base64解码，得到加密串B
                 * （2）对商户key做md5，得到32位小写key* ( key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 )
                 * （3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
                 */
                java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
                byte[] b = decoder.decode(req_info);
                SecretKeySpec key =
                        new SecretKeySpec(WXPayUtil.MD5(apiKey).toLowerCase().getBytes(StandardCharsets.UTF_8), "AES");
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//                KeyGenerator.getInstance("AES").init(128);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, key);
                String resultStr = new String(cipher.doFinal(b), StandardCharsets.UTF_8);

                Map<String, String> aesMap = WXPayUtil.xmlToMap(resultStr);
                WxPayRefundCallBackDataResponse dataResponse = (WxPayRefundCallBackDataResponse) WXPayUtil.
                        mapToObject(aesMap, WxPayRefundCallBackDataResponse.class);

                /** 以下为返回的加密字段： **/
                //  商户退款单号  是   String(64)  1.21775E+27 商户退款单号
                String out_refund_no = dataResponse.getOut_refund_no();
                //  退款状态   SUCCESS SUCCESS-退款成功、CHANGE-退款异常、REFUNDCLOSE—退款关闭
                refund_status = dataResponse.getRefund_status();

                if (refund_status.equals(WXPayConstants.SUCCESS)) {

                    RefundCallBackResultAddRequest refundCallBackResultAddRequest = new RefundCallBackResultAddRequest(RefundCallBackResultDTO.builder()
                            .businessId(out_refund_no)
                            .resultXml(result)
                            .resultContext(resultStr)
                            .resultStatus(PayCallBackResultStatus.TODO)
                            .errorNum(0)
                            .payType(PayCallBackType.WECAHT)
                            .build());
                    addRefundCallBackResult(refundCallBackResultAddRequest);

                    payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.WECAHT)
                            .wxRefundCallBackResultXmlStr(result).wxRefundCallBackResultStr(resultStr).out_refund_no(out_refund_no)
                            .build());

                }

            } else {
                log.error("refund:支付失败,错误信息：{}" , refundCallBackResponse.getReturn_msg());
            }
        } catch (Exception e) {
            log.error("refund:微信退款回调发布异常：", e);
        } finally {
            wxCallbackResultHandle(response, refund_status);
        }
    }

    @Operation(summary = "支付宝回调方法")
    @RequestMapping(value = "/aliPayCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void aliPayBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no")
                    .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            Map<String, String[]> requestParams = request.getParameterMap();
            Map<String, String> params = new HashMap<>(requestParams.size());
            //返回的参数放到params中
            for (Map.Entry<String, String[]> iter : requestParams.entrySet()) {
                String name = iter.getKey();
                String[] values = iter.getValue();
                params.put(name, StringUtils.join(values, ','));
            }
            String aliPayResultStr = JSONObject.toJSONString(params);
            //支付和退款公用一个回调，所以要判断回调的类型
            if (params.containsKey("refund_fee")) {
                //退款只有app支付的订单有回调，退款的逻辑在同步方法中已经处理了，这儿不再做处理
                log.info("APP退款回调,单号：{}", params.containsKey("out_trade_no"));
                try {
                    response.getWriter().print("success");
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //如果线程池队列已满，则采取拒绝策略（AbortPolicy），抛出RejectedExecutionException异常，则将对应的回调改为处理失败，然后通过定时任务处理补偿
                try {
                    addPayCallBackResult(PayCallBackResultAddRequest.builder()
                            .businessId(out_trade_no)
                            .resultContext(aliPayResultStr)
                            .resultStatus(PayCallBackResultStatus.HANDLING)
                            .errorNum(0)
                            .payType(PayCallBackType.ALI)
                            .build());
                    payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.ALI)
                            .aliPayCallBackResultStr(aliPayResultStr)
                            .build());
                    //保存支付请求信息到插件
                    PluginPayInfoModifyRequest pluginPayInfoModifyRequest = new PluginPayInfoModifyRequest();
                    pluginPayInfoModifyRequest.setOrderCode(out_trade_no);
                    pluginPayInfoModifyRequest.setPayResponse(aliPayResultStr);
                    pluginPayInfoProvider.modify(pluginPayInfoModifyRequest);
                    try {
                        response.getWriter().print("success");
                        response.getWriter().flush();
                        response.getWriter().close();
                        log.info("支付回调返回success");
                    } catch (IOException e) {
                        log.error("支付宝回调异常", e);
                        throw e;
                    }
                } catch (SbcRuntimeException e) {
                    //business_id唯一索引报错捕获，不影响流程处理
                    if (!e.getErrorCode().equals(OrderErrorCodeEnum.K050145.getCode())) {
                        throw e;
                    }
                    e.printStackTrace();
                } catch (RejectedExecutionException e) {
                    addPayCallBackResult(PayCallBackResultAddRequest.builder()
                            .businessId(out_trade_no)
                            .resultContext(aliPayResultStr)
                            .resultStatus(PayCallBackResultStatus.TODO)
                            .errorNum(0)
                            .payType(PayCallBackType.ALI)
                            .build());
                    try {
                        response.getWriter().print("success");
                        response.getWriter().flush();
                        response.getWriter().close();
                        log.info("支付回调返回success");
                    } catch (IOException iOE) {
                        log.error("支付宝回调异常", iOE);
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            request.getParameterMap().keySet().forEach(x -> log.info("callback param: {}", x));
            e.printStackTrace();
            try {
                response.getWriter().print("failure");
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException iOE) {
                log.error("支付宝回调异常", iOE);
                throw e;
            }
        }
    }

    /**
     * 微信支付--微信企业付款到零钱
     *
     * @return
     */
    @RequestMapping(value = "/wxPayCompanyPayment", method = RequestMethod.POST)
    public BaseResponse<WxPayCompanyPaymentRsponse> wxPayCompanyPayment(@RequestBody WxPayCompanyPaymentInfoRequest request) {
        request.setSpbill_create_ip(HttpUtil.getIpAddr());
        BaseResponse<WxPayCompanyPaymentRsponse> response = wxPayProvider.wxPayCompanyPayment(request);
        return response;
    }

    @Operation(summary = "拉卡拉支付回调方法")
    @RequestMapping(value = "/lakala/pay/callBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void lakalaCallBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("拉卡拉支付回调入口");
        JSONObject returnJson = new JSONObject();
        StringBuffer bf = new StringBuffer();
        try (InputStreamReader in = new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8)){
            int len;
            char[] chs = new char[1024];
            while ((len = in.read(chs)) != -1) {
                bf.append(new String(chs, 0, len));
            }
        } catch (Exception e) {
            log.error("请求头部取数据异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "无效的请求数据");
        }
        String body = bf.toString();
        String authorization = request.getHeader("Authorization");
        String[] strings = authorization.replace("LKLAPI-SHA256withRSA ", "").split(",");
        String[] timestampItem = strings[0].split("=");
        String timestamp = timestampItem[1].replaceAll("\"", "");
        String[] nonceItem = strings[1].split("=");
        String nonce = nonceItem[1].replaceAll("\"", "");
        String[] signatureItem = strings[2].split("=");
        String signature = signatureItem[1].replaceAll("\"", "");
        boolean verifySignFlag =
                PayVerifySignUtils.lakalaCallBackVerify(timestamp, nonce, signature, body);
        log.info("拉卡拉支付回调验签结果：{}", verifySignFlag);
        LakalaNotifyRequest lakalaNotifyRequest =
                JSON.parseObject(
                        body,LakalaNotifyRequest.class);
        String outTradeNo =
                lakalaNotifyRequest
                        .getOutTradeNo();
//                        .substring(0, lakalaNotifyRequest.getOutTradeNo().length() - 2);
        if (!payCallBackResultQueryProvider
                .list(PayCallBackResultListRequest.builder().businessId(outTradeNo).build())
                .getContext()
                .getPayCallBackResultVOList()
                .isEmpty()) {
            try {
                returnJson.put("code", "SUCCESS");
                returnJson.put("message", "执行成功");
                response.getWriter().print(returnJson);
                response.getWriter().flush();
                response.getWriter().close();
                log.info("lakala pay call back success");
                return;
            } catch (IOException e) {
                log.error("lakala pay call back io exception", e);
            }
        }
        if (verifySignFlag){ // 成功
            try {
                addPayCallBackResult(
                        PayCallBackResultAddRequest.builder()
                                .businessId(outTradeNo)
                                .resultContext(body)
                                .resultStatus(
                                        PayCallBackResultStatus.HANDLING)
                                .errorNum(0)
                                .payType(PayCallBackType.LAKALA)
                                .build());
                payCallBackTaskService.payCallBack(
                        TradePayOnlineCallBackRequest.builder()
                                .payCallBackType(PayCallBackType.LAKALA)
                                .lakalaPayCallBack(body)
                                .build());
                // 保存支付请求信息到插件
                PluginPayInfoModifyRequest pluginPayInfoModifyRequest =
                        new PluginPayInfoModifyRequest();
                pluginPayInfoModifyRequest.setOrderCode(outTradeNo);
                pluginPayInfoModifyRequest.setPayResponse(body);
                pluginPayInfoProvider.modify(pluginPayInfoModifyRequest);
                try {
                    returnJson.put("code", "SUCCESS");
                    returnJson.put("message", "");
                    response.getWriter().print(returnJson);
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("lakala pay call back success");
                } catch (IOException e) {
                    log.error("lakala pay call back io exception", e);
                }
            } catch (RejectedExecutionException e) {
                log.error("lakala pay call back rejected execution exception ", e);
                addPayCallBackResult(
                        PayCallBackResultAddRequest.builder()
                                .businessId(outTradeNo)
                                .resultContext(body)
                                .resultStatus(
                                        PayCallBackResultStatus.TODO)
                                .errorNum(0)
                                .payType(PayCallBackType.LAKALA)
                                .build());
                try {
                    returnJson.put("code", "SUCCESS");
                    returnJson.put("message", "");
                    response.getWriter().print(returnJson);
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("lakala pay call back success");
                } catch (IOException iOE) {
                    log.error("lakala pay call back io exception", iOE);
                }
            }
        } else { // 失败
            addPayCallBackResult(
                    PayCallBackResultAddRequest.builder()
                            .businessId(outTradeNo)
                            .resultContext(body)
                            .resultStatus(PayCallBackResultStatus.FAILED)
                            .errorNum(99)
                            .payType(PayCallBackType.LAKALA)
                            .build());
            try {
                returnJson.put("code", "SUCCESS");
                returnJson.put("message", "");
                response.getWriter().print(returnJson);
                response.getWriter().flush();
                response.getWriter().close();
                log.info("lakala pay call back success");
            } catch (IOException iOE) {
                log.error("lakala pay call back io exception", iOE);
            }
        }

    }

    private boolean isMergePayOrder(String businessId) {
        return (businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID));
    }

    private void wxCallbackResultHandle(HttpServletResponse response, String result) throws IOException {
        if (result.equals(WXPayConstants.SUCCESS)) {
            response.getWriter().write(WXPayUtil.setXML("SUCCESS", "OK"));
            log.info("微信支付异步通知回调成功的消息回复结束");
        } else {
            response.getWriter().write(WXPayUtil.setXML("FAIL", "ERROR"));
            log.info("微信支付异步通知回调失败的消息回复结束");
        }
    }

    /**
     * 线上订单支付回调
     * 订单 支付单 操作信息
     *
     * @return 操作结果
     */
    private void payCallbackOnline(List<TradeVO> trades, Operator operator, boolean isMergePay) {
        //封装回调参数
        List<TradePayCallBackOnlineDTO> reqOnlineDTOList = trades.stream().map(i -> {
            //每笔订单做是否合并支付标识
            i.getPayInfo().setMergePay(isMergePay);
            tradeProvider.update(new TradeUpdateRequest(KsBeanUtil.convert(i, TradeDTO.class)));
            if (Objects.nonNull(i.getIsBookingSaleGoods()) && i.getIsBookingSaleGoods() && i.getBookingType() == BookingType.EARNEST_MONEY &&
                    StringUtils.isNotEmpty(i.getTailOrderNo()) && StringUtils.isNotEmpty(i.getTailPayOrderId())) {
                //支付单信息
                PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest
                        .builder().payOrderId(i.getTailPayOrderId()).build()).getContext().getPayOrder();

                return TradePayCallBackOnlineDTO.builder()
                        .trade(KsBeanUtil.convert(i, TradeDTO.class))
                        .payOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class))
                        .build();
            } else {
                //支付单信息
                PayOrderVO payOrder = tradeQueryProvider.getPayOrderById(TradeGetPayOrderByIdRequest
                        .builder().payOrderId(i.getPayOrderId()).build()).getContext().getPayOrder();
                return TradePayCallBackOnlineDTO.builder()
                        .trade(KsBeanUtil.convert(i, TradeDTO.class))
                        .payOrderOld(KsBeanUtil.convert(payOrder, PayOrderDTO.class))
                        .build();
            }

        }).collect(Collectors.toList());
        //批量回调
        tradeProvider.payCallBackOnlineBatch(TradePayCallBackOnlineBatchRequest.builder()
                .requestList(reqOnlineDTOList)
                .operator(operator)
                .build());
        if (CollectionUtils.isEmpty(reqOnlineDTOList)) {
            esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                    .customerId(trades.get(0).getBuyer().getId())
                    .build());
        }
        // 订单支付回调同步供应商订单状态
        //this.providerTradePayCallBack(trades);

    }

    /**
     * 授信还款-处理银联回调
     */
    private void creditUnionPayCallbackHandle(String businessId, Map<String, String> resultMap,String payNo) {
        CustomerCreditRepayAndOrdersByRepayCodeResponse creditRepayAndOrders =
                customerCreditRepayQueryProvider.getCreditRepayAndOrdersByRepayCode(CustomerCreditRepayByRepayCodeRequest.builder()
                        .repayCode(businessId)
                        .build()).getContext();
        List<TradeVO> tradeVOList = tradeQueryProvider.listAll(TradeListAllRequest.builder()
                .tradeQueryDTO(TradeQueryDTO.builder()
                        .ids(creditRepayAndOrders.getCreditOrderVOList().stream()
                                .map(CustomerCreditOrderVO::getOrderId)
                                .distinct()
                                .toArray(String[]::new))
                        .build())
                .build()).getContext().getTradeVOList();
        payServiceHelper.wrapperCreditTrade(tradeVOList);
        //订单合并支付场景状态采样
        boolean paid =
                tradeVOList.stream().anyMatch(i -> i.getCreditPayInfo().getHasRepaid());
        boolean cancel =
                CreditRepayStatus.VOID.equals(creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean finish = CreditRepayStatus.FINISH.equals(
                creditRepayAndOrders.getCustomerCreditRepayVO().getRepayStatus());
        boolean returning = tradeVOList.stream().anyMatch(TradeVO::getReturningFlag);
        boolean correctAmount =
                tradeVOList.stream().map(TradeVO::getCanRepayPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(new BigDecimal(Long.parseLong(resultMap.get("txnAmt")) / 100)) == 0;
        if (cancel || paid || !correctAmount || returning||finish) {
            //直接退款
            UnionDirectRefundRequest request = new UnionDirectRefundRequest();
            //此处使用支付流水号
            request.setBusinessId(payNo);
            request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
            unionB2BProvider.unionDirectRefund(request);

            //重复支付退款成功处理逻辑
            callbackOperateLogMQUtil.convertAndSend("银联企业支付", "重复支付退款成功", "授信还款单号：" + businessId);
        } else {
            //订单 支付单 操作信息
            Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("-1").name(PayGatewayEnum.WECHAT.name())
                    .account(PayGatewayEnum.WECHAT.name()).platform(Platform.THIRD).build();
            creditRepayAndOrders.getCustomerCreditRepayVO().setRepayType(CreditRepayTypeEnum.UNIONPAY);
            creditRepayCallbackOnline(businessId, tradeVOList, operator, creditRepayAndOrders.getCustomerCreditRepayVO());
        }

    }

    /**
     * 授信还款支付成功-更新相关数据
     *
     * @param businessId
     * @param trades
     * @param operator
     */
    private void creditRepayCallbackOnline(String businessId, List<TradeVO> trades, Operator operator, CustomerCreditRepayVO customerCreditRepay) {
        //更新还款记录
        customerCreditRepayProvider.modifyByPaySuccess(CustomerCreditRepayModifyRequest.builder()
                .repayOrderCode(businessId)
                .repayType(customerCreditRepay.getRepayType())
                .updatePerson(operator.getAdminId())
                .repayTime(LocalDateTime.now())
                .build());

        //更新订单中还款状态
        trades.forEach(item -> {
            tradeProvider.updateCreditHasRepaid(TradeCreditHasRepaidRequest.builder()
                    .hasRepaid(Boolean.TRUE)
                    .tid(item.getId())
                    .build());
        });
    }


    /***
     * 银联在线网关支付异步回调
     * @date 10:30 2021/3/15
     * @author zhangyong
     * @param request
     * @param response
     * @return
     */
    @Operation(summary = "银联支付异步回调")
    @RequestMapping(value = "/unionPayCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void unionPayCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("=========================银联支付异步回调接收报文返回开始=======================");
        String encoding = request.getParameter("encoding");
        log.info("返回报文中encoding=[{}]", encoding);
        Map<String, String> respParam = getAllRequestParam(request);
        if (!unionB2BProvider.unionCheckSign(respParam).getContext()) {
            log.info("验证签名结果[失败].");
            //验签失败，需解决验签问题
        } else {
            //如果线程池队列已满，则采取拒绝策略（AbortPolicy），抛出RejectedExecutionException异常，则将对应的回调改为处理失败，然后通过定时任务处理补偿
            try {
                addPayCallBackResult(PayCallBackResultAddRequest.builder()
                        .businessId(request.getParameter("orderId"))
                        .resultContext(JSONObject.toJSONString(respParam))
                        .resultStatus(PayCallBackResultStatus.HANDLING)
                        .errorNum(0)
                        .payType(PayCallBackType.UNIONPAY)
                        .build());
                payCallBackTaskService.payCallBack(TradePayOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.UNIONPAY)
                        .unionPayCallBackResultStr(JSONObject.toJSONString(respParam))
                        .build());
                try {
                    //返回给银联服务器http 200  状态码
                    response.getWriter().print("ok");
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("银联支付回调返回success");
                } catch (IOException e) {
                    log.error("银联回调通知异常", e);
                    throw e;
                }
            } catch (RejectedExecutionException e) {
                addPayCallBackResult(PayCallBackResultAddRequest.builder()
                        .businessId(request.getParameter("orderId"))
                        .resultContext(JSONObject.toJSONString(respParam))
                        .resultStatus(PayCallBackResultStatus.TODO)
                        .errorNum(0)
                        .payType(PayCallBackType.UNIONPAY)
                        .build());
                try {
                    response.getWriter().print("ok");
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("支付回调返回success");
                } catch (IOException iOE) {
                    log.error("支付宝回调异常", iOE);
                    throw e;
                }
            }
        }
        log.info("===============银联支付异步回调接收报文返回结束end==================================");

    }


    /**
     * 银联在线网关支付退款回调
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Operation(summary = "银联在线网关支付退款回调")
    @RequestMapping(value = "/unionPayRefundCallBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void unionPayRefundCallBack(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        log.info("退款回调接收后台通知开始");
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(request);
        if (!unionB2BProvider.unionCheckSign(reqParam).getContext()) {
            log.info("验证签名结果[失败].");
            //验签失败，需解决验签问题
        } else {
            //如果线程池队列已满，则采取拒绝策略（AbortPolicy），抛出RejectedExecutionException异常，则将对应的回调改为处理失败，然后通过定时任务处理补偿
            try {
                log.info("验证签名结果[成功].");
                String unionRefundCallBackResultStr = JSONObject.toJSONString(reqParam);
                String orderId = request.getParameter("orderId");
                RefundCallBackResultAddRequest refundCallBackResultAddRequest =
                        new RefundCallBackResultAddRequest(RefundCallBackResultDTO.builder()
                                .businessId(orderId)
                                .resultXml(unionRefundCallBackResultStr)
                                .resultContext(unionRefundCallBackResultStr)
                                .resultStatus(PayCallBackResultStatus.TODO)
                                .errorNum(0)
                                .payType(PayCallBackType.UNIONPAY)
                                .build());
                addRefundCallBackResult(refundCallBackResultAddRequest);
                payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.UNIONPAY)
                        .unionRefundCallBackResultStr(unionRefundCallBackResultStr).out_refund_no(orderId)
                        .build());
            } catch (SbcRuntimeException e) {
                //business_id唯一索引报错捕获，不影响流程处理
                if (!e.getErrorCode().equals(OrderErrorCodeEnum.K050145.getCode())) {
                    throw e;
                }
                e.printStackTrace();
            } finally {
                response.getWriter().print("ok");
                response.getWriter().flush();
                response.getWriter().close();
                log.info("银联支付退款回调返回success");
            }
        }
        log.info("银联支付退款回调接收后台通知结束");
    }

    @Operation(summary = "拉卡拉分账回调")
    @RequestMapping(value = "/seporcancel", method = RequestMethod.POST)
    public void seporcancelCallBack(HttpServletRequest request, HttpServletResponse response){
        try{
            //获取回调的bodyStr
            String respStr = VerifySignUtils.getRespStr(request.getInputStream());
            log.info("分账回调入参：{}", respStr);
            //获取authorization 头部信息
            String authorization = request.getHeader("Authorization");
            //验签
            boolean verify = VerifySignUtils.verify(authorization, respStr);
            if (verify){
                LakalaShareProfitQueryResponse lakalaShareProfitQueryResponse =
                        JSON.parseObject(
                                respStr, LakalaShareProfitQueryResponse.class);
                if (Objects.nonNull(lakalaShareProfitQueryResponse)){
                    LakalaLedgerStatus lakalaLedgerStatus = null;
                    switch (lakalaShareProfitQueryResponse.getStatus()){
                        case "0":
                            lakalaLedgerStatus = LakalaLedgerStatus.NOT_SETTLED;
                            break;
                        case "1":
                            lakalaLedgerStatus = LakalaLedgerStatus.SUCCESS;
                            break;
                        case "2":
                            lakalaLedgerStatus = LakalaLedgerStatus.PROCESSING;
                            break;
                        case "3":
                            lakalaLedgerStatus = LakalaLedgerStatus.FAIL;
                            break;
                        default:
                            log.error("分账状态异常：{}", lakalaShareProfitQueryResponse.getStatus());
                            break;
                    }
                    settlementDetailProvider.updateLakalaSettlementDetailStatus(
                            UpdateSettlementDetailStatusRequest.builder()
                                    .payInstId(lakalaShareProfitQueryResponse.getSepTranSid())
                                    .lakalaLedgerStatus(lakalaLedgerStatus)
                                    .build());
                    JSONObject returnJson = new JSONObject();
                    returnJson.put("code", "SUCCESS");
                    returnJson.put("message", "执行成功");
                    response.getWriter().print(returnJson);
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("lakala pay call back success");
                } else {
                    log.error("lakalaShareProfitQueryResponse序列化异常。lakalaShareProfitQueryResponse：{}", lakalaShareProfitQueryResponse);
                }
            } else {
                log.error("拉卡拉分账回调验签失败");
            }
        } catch (IOException e){
            log.error("拉卡拉分账回调io异常", e);
        }

    }

    @Autowired private PayProvider payProvider;

    /**
     * @description 微信支付异步回调
     * @author wur
     * @date: 2022/11/30 10:54
     * @param request
     * @return
     */
    @Operation(summary = "微信支付异步回调")
    @PostMapping(value = "/wxPayV3SuccessCallBack")
    public void wxPayV3Back(
            HttpServletRequest request, HttpServletResponse response) {
        String wechatpaySerial = request.getHeader("Wechatpay-Serial");
        String wechatpaySignature = request.getHeader("Wechatpay-Signature");
        String wechatpayTimestamp = request.getHeader("Wechatpay-Timestamp");
        String wechatpayNonce = request.getHeader("Wechatpay-Nonce");
        log.info("======================微信支付-V3,异步通知回调start======================");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String body = this.getRequestBody(request);

            log.info(
                    "=========微信V3支付-回调请求，wechatpaySerial={}， wechatpaySignature={}， wechatpayTimestamp={}，wechatpayNonce={}，body={}",
                    wechatpaySerial,
                    wechatpaySignature,
                    wechatpayTimestamp,
                    wechatpayNonce,
                    body);
            // 验证签名
            JSONObject bodyJson = JSON.parseObject(body);
            if(!bodyJson.containsKey("resource")) {
                log.info("======================微信支付-V3,异常结束：请求参数不全");
                JSONObject returnJson = new JSONObject();
                returnJson.put("code", "FAIL");
                returnJson.put("message", "");
                out.write(JSON.toJSONString(returnJson));
                return;
            }

            PayGatewayConfigResponse payGatewayConfig =
                    paySettingQueryProvider
                            .getGatewayConfigByGateway(
                                    new GatewayConfigByGatewayRequest(
                                            PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID))
                            .getContext();
            WxPayV3CertificatesResponse wxPayV3CertificatesResponse = payProvider.getWxPayV3Certificates(WxPayV3CertificatesRequest.builder().cleanCacheFlag(Boolean.FALSE).serial_no(wechatpaySerial).build()).getContext();
            if (Objects.isNull(wxPayV3CertificatesResponse) || StringUtils.isBlank(wxPayV3CertificatesResponse.getPublicKey())) {
                log.info("======================微信支付-V3,异常结束：获取微信平台证书公钥失败");
                JSONObject returnJson = new JSONObject();
                returnJson.put("code", "FAIL");
                returnJson.put("message", "");
                out.write(JSON.toJSONString(returnJson));
                return;
            }
            boolean signVerify =
                    this.responseSignVerify(
                            wechatpaySignature,
                            wechatpayTimestamp,
                            wechatpayNonce,
                            body,
                            wxPayV3CertificatesResponse.getPublicKey(),
                            true,
                            wechatpaySerial);
            if (!signVerify) {
                log.info("======================微信支付-V3,异常结束：签名验证失败");
                JSONObject returnJson = new JSONObject();
                returnJson.put("code", "FAIL");
                returnJson.put("message", "");
                out.write(JSON.toJSONString(returnJson));
                return;
            }

            // 解密
            JSONObject resourceJson = JSON.parseObject(bodyJson.getString("resource"));
            String requestStr = this.wxPayV3GetSign(payGatewayConfig.getApiV3Key(), resourceJson.getString("associated_data"), resourceJson.getString("nonce"), resourceJson.getString("ciphertext"));
            log.info("======================微信支付-解密后数据{}", requestStr);
            // 将回调数据写入数据库
            JSONObject requestJson = JSON.parseObject(requestStr);
            String out_trade_no = requestJson.getString("out_trade_no");
            try {
                addPayCallBackResult(
                        PayCallBackResultAddRequest.builder()
                                .businessId(out_trade_no)
                                .resultXml(requestStr)
                                .resultContext(body)
                                .resultStatus(PayCallBackResultStatus.HANDLING)
                                .errorNum(0)
                                .payType(PayCallBackType.WECAHT_V3)
                                .build());
                payCallBackTaskService.payCallBack(
                        TradePayOnlineCallBackRequest.builder()
                                .payCallBackType(PayCallBackType.WECAHT_V3)
                                .wxPayV3CallBackBody(requestStr)
                                .build());
                // 保存支付请求信息到插件
                PluginPayInfoModifyRequest pluginPayInfoModifyRequest =
                        new PluginPayInfoModifyRequest();
                pluginPayInfoModifyRequest.setOrderCode(out_trade_no);
                pluginPayInfoModifyRequest.setPayResponse(requestStr);
                pluginPayInfoProvider.modify(pluginPayInfoModifyRequest);
            } catch (RejectedExecutionException e) {
                addPayCallBackResult(
                        PayCallBackResultAddRequest.builder()
                                .businessId(out_trade_no)
                                .resultXml(requestStr)
                                .resultContext(body)
                                .resultStatus(PayCallBackResultStatus.TODO)
                                .errorNum(0)
                                .payType(PayCallBackType.WECAHT_V3)
                                .build());
            }
            JSONObject returnJson = new JSONObject();
            returnJson.put("code", "SUCCESS");
            returnJson.put("message", "");
            out.write(JSON.toJSONString(returnJson));
            return;
        } catch (Exception e) {
            log.info("======================微信支付-V3,异常结束：微信异步通知处理失败:", e);
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("code", "FAIL");
        returnJson.put("message", "");
        out.write(JSON.toJSONString(returnJson));
        return;
    }

    /**
     * 微信支付退款成功异步回调
     *
     * @param request
     */
    @Operation(summary = "微信支付退款成功异步回调")
    @PostMapping(value = "/wxPayV3RefundSuccessCallBack")
    public void wxPayV3RefundSuccessCallBack(@RequestHeader("Wechatpay-Serial") String wechatpaySerial,
                                             @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
                                             @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
                                             @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
                                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String body = this.getRequestBody(request);
            log.info(
                    "=========微信V3退款-回调请求，wechatpaySerial={}， wechatpaySignature={}， wechatpayTimestamp={}，wechatpayNonce={}，body={}",
                    wechatpaySerial,
                    wechatpaySignature,
                    wechatpayTimestamp,
                    wechatpayNonce,
                    body);
            // 验证签名
            JSONObject bodyJson = JSON.parseObject(body);
            if(!bodyJson.containsKey("resource") || !"REFUND.SUCCESS".equals(bodyJson.getString("event_type"))) {
                log.info("======================微信退款-V3,异常结束：请求参数有误");
                JSONObject returnJson = new JSONObject();
                returnJson.put("code", "FAIL");
                returnJson.put("message", "");
                out.write(JSON.toJSONString(returnJson));
                return;
            }

            PayGatewayConfigResponse payGatewayConfig = paySettingQueryProvider.getGatewayConfigByGateway(new GatewayConfigByGatewayRequest(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID))
                            .getContext();
            WxPayV3CertificatesResponse wxPayV3CertificatesResponse = payProvider.getWxPayV3Certificates(WxPayV3CertificatesRequest.builder().cleanCacheFlag(Boolean.FALSE).serial_no(wechatpaySerial).build()).getContext();
            if (Objects.isNull(wxPayV3CertificatesResponse) || StringUtils.isBlank(wxPayV3CertificatesResponse.getPublicKey())) {
                log.info("======================微信支付-V3,异常结束：获取微信平台证书公钥失败");
                JSONObject returnJson = new JSONObject();
                returnJson.put("code", "FAIL");
                returnJson.put("message", "");
                out.write(JSON.toJSONString(returnJson));
                return;
            }
            boolean signVerify =
                    this.responseSignVerify(
                            wechatpaySignature,
                            wechatpayTimestamp,
                            wechatpayNonce,
                            body,
                            wxPayV3CertificatesResponse.getPublicKey(),
                            true,
                            wechatpaySerial);
            if (!signVerify) {
                log.info("======================微信退款-V3,异常结束：签名验证失败");
                JSONObject returnJson = new JSONObject();
                returnJson.put("code", "FAIL");
                returnJson.put("message", "");
                out.write(JSON.toJSONString(returnJson));
                return;
            }

            // 解密 获取消息体
            JSONObject resourceJson = JSON.parseObject(bodyJson.getString("resource"));
            String requestStr = this.wxPayV3GetSign(payGatewayConfig.getApiV3Key(), resourceJson.getString("associated_data"), resourceJson.getString("nonce"), resourceJson.getString("ciphertext"));
            log.info("======================微信退款-解密后数据{}", requestStr);
            // 将回调数据写入数据库
            JSONObject requestJson = JSON.parseObject(requestStr);
            String out_refund_no = requestJson.getString("out_refund_no");
            String refund_status = requestJson.getString("refund_status");
            if (WXPayConstants.SUCCESS.equalsIgnoreCase(refund_status)) {
                //保存 保存退款回调结果
                RefundCallBackResultAddRequest refundCallBackResultAddRequest = new RefundCallBackResultAddRequest(RefundCallBackResultDTO.builder()
                        .businessId(out_refund_no)
                        .resultXml(requestStr)
                        .resultContext(body)
                        .resultStatus(PayCallBackResultStatus.TODO)
                        .errorNum(0)
                        .payType(PayCallBackType.WECAHT_V3)
                        .build());
                addRefundCallBackResult(refundCallBackResultAddRequest);
                // 调用回调
                payCallBackTaskService.refundCallBack(TradeRefundOnlineCallBackRequest.builder().payCallBackType(PayCallBackType.WECAHT_V3)
                        .wxV3RefundCallBackResultStr(requestStr).out_refund_no(out_refund_no)
                        .build());
            }
            JSONObject returnJson = new JSONObject();
            returnJson.put("code", "SUCCESS");
            returnJson.put("message", "");
            out.write(JSON.toJSONString(returnJson));
            return;
        } catch (Exception e) {
            log.info("======================微信退款-V3,异常结束：异常：{}", e);
            JSONObject returnJson = new JSONObject();
            returnJson.put("code", "FAIL");
            returnJson.put("message", "");
            out.write(JSON.toJSONString(returnJson));
            return;
        }
    }

    /**
     * @description  微信支付V3 回调支持 ServletRequest可重复读取
     * @author  wur
     * @date: 2022/12/2 9:31
     * @param request
     * @return
     **/
    public String getRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 支付回调解密
     * @param apiV3Key     密钥
     * @param associatedData
     * @param nonce
     * @param ciphertext
     * @return
     * @throws GeneralSecurityException
     */
    private String wxPayV3GetSign(String apiV3Key, String associatedData, String nonce, String ciphertext) throws GeneralSecurityException {
        AesUtil wxAesUtil = new AesUtil(apiV3Key.getBytes());
        String jsonStr = wxAesUtil.decryptToString(associatedData.getBytes(),nonce.getBytes(),ciphertext);
        return jsonStr;
    }

    /**
     * 回调验签
     * https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_1.shtml
     * @param wechatpaySignature 回调head头部
     * @param wechatpayTimestamp 回调head头部
     * @param wechatpayNonce 回调head头部
     * @param body 请求数据
     * @return
     */
    public  boolean  responseSignVerify(String wechatpaySignature, String wechatpayTimestamp, String wechatpayNonce, String body, String publicKeyStr, boolean firstFlag,String wechatpaySerial) {
        try {
            String signatureStr = buildMessage(wechatpayTimestamp, wechatpayNonce, body);
            Signature signer = Signature.getInstance("SHA256withRSA");
            X509Certificate receivedCertificate = loadCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(publicKeyStr)));
            signer.initVerify(receivedCertificate);
            signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
            return signer.verify(Base64.getDecoder().decode(wechatpaySignature));
        } catch (Exception e ) {
            if (firstFlag && (e instanceof CertificateExpiredException || e instanceof CertificateNotYetValidException || e instanceof CertificateException)) {
                WxPayV3CertificatesResponse wxPayV3CertificatesResponse = payProvider.getWxPayV3Certificates(WxPayV3CertificatesRequest.builder().cleanCacheFlag(Boolean.TRUE).serial_no(wechatpaySerial).build()).getContext();
                if (Objects.nonNull(wxPayV3CertificatesResponse) && StringUtils.isNotBlank(wxPayV3CertificatesResponse.getPublicKey())) {
                    this.responseSignVerify(wechatpaySignature, wechatpayTimestamp, wechatpayNonce, body, wxPayV3CertificatesResponse.getPublicKey(), false,wechatpaySerial);
                }
            }
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 回调验签-加载微信平台证书
     * @param inputStream
     * @return
     */
    public static X509Certificate loadCertificate(InputStream inputStream) throws CertificateException {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw e;
        } catch (CertificateNotYetValidException e) {
            throw e;
        } catch (CertificateException e) {
            throw e;
        }
    }
    /**
     * 回调验签-构建签名数据
     * @param
     * @return
     */
    public static String buildMessage(String wechatpayTimestamp, String wechatpayNonce, String body) {
        return wechatpayTimestamp + "\n"
                + wechatpayNonce + "\n"
                + body + "\n";
    }


    @Operation(summary = "拉卡拉收银台支付回调方法")
    @RequestMapping(value = "/lakala/casher/pay/callBack", method = RequestMethod.POST)
    @GlobalTransactional
    public void lakalaCasherCallBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("拉卡拉收银台支付回调入口");
        JSONObject returnJson = new JSONObject();
        StringBuffer bf = new StringBuffer();
        try (InputStreamReader in = new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8)){
            int len;
            char[] chs = new char[1024];
            while ((len = in.read(chs)) != -1) {
                bf.append(new String(chs, 0, len));
            }
        } catch (Exception e) {
            log.error("请求头部取数据异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "无效的请求数据");
        }
        String body = bf.toString();
        log.info("拉卡拉收银台支付回调参数：{}", body);
        String authorization = request.getHeader("Authorization");
        String[] strings = authorization.replace("LKLAPI-SHA256withRSA ", "").split(",");
        String[] timestampItem = strings[0].split("=");
        String timestamp = timestampItem[1].replaceAll("\"", "");
        String[] nonceItem = strings[1].split("=");
        String nonce = nonceItem[1].replaceAll("\"", "");
        String[] signatureItem = strings[2].split("=");
        String signature = signatureItem[1].replaceAll("\"", "");
        boolean verifySignFlag = PayVerifySignUtils.lakalaCasherCallBackVerify(timestamp, nonce, signature, body);
        log.info("拉卡拉收银台支付回调验签结果：{}", verifySignFlag);
        LakalaCasherNotifyRequest lakalaNotifyRequest =
                JSON.parseObject(
                        body, LakalaCasherNotifyRequest.class);
        String businessId =
                lakalaNotifyRequest
                        .getOutOrderNo()
                        .substring(0, lakalaNotifyRequest.getOutOrderNo().length() - 2);
        if (!payCallBackResultQueryProvider
                .list(PayCallBackResultListRequest.builder().businessId(businessId).build())
                .getContext()
                .getPayCallBackResultVOList()
                .isEmpty()) {
            try {
                returnJson.put("code", "SUCCESS");
                returnJson.put("message", "执行成功");
                response.getWriter().print(returnJson);
                response.getWriter().flush();
                response.getWriter().close();
                log.info("lakala pay call back success");
                return;
            } catch (IOException e) {
                log.error("lakala pay call back io exception", e);
            }
        }
        if (verifySignFlag){ // 成功
            try {
                addPayCallBackResult(
                        PayCallBackResultAddRequest.builder()
                                .businessId(businessId)
                                .resultContext(body)
                                .resultStatus(
                                        PayCallBackResultStatus.HANDLING)
                                .errorNum(0)
                                .payType(PayCallBackType.LAKALA_CASHER)
                                .build());
                payCallBackTaskService.payCallBack(
                        TradePayOnlineCallBackRequest.builder()
                                .payCallBackType(PayCallBackType.LAKALA_CASHER)
                                .lakalaPayCallBack(body)
                                .build());
                // 保存支付请求信息到插件
                PluginPayInfoModifyRequest pluginPayInfoModifyRequest =
                        new PluginPayInfoModifyRequest();
                pluginPayInfoModifyRequest.setOrderCode(businessId);
                pluginPayInfoModifyRequest.setPayResponse(body);
                pluginPayInfoProvider.modify(pluginPayInfoModifyRequest);
                try {
                    returnJson.put("code", "SUCCESS");
                    returnJson.put("message", "执行成功");
                    response.getWriter().print(returnJson);
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("lakala pay call back success");
                } catch (IOException e) {
                    log.error("lakala pay call back io exception", e);
                }
            } catch (RejectedExecutionException e) {
                log.error("lakala pay call back rejected execution exception ", e);
                addPayCallBackResult(
                        PayCallBackResultAddRequest.builder()
                                .businessId(businessId)
                                .resultContext(body)
                                .resultStatus(
                                        PayCallBackResultStatus.TODO)
                                .errorNum(0)
                                .payType(PayCallBackType.LAKALA_CASHER)
                                .build());
                try {
                    returnJson.put("code", "SUCCESS");
                    returnJson.put("message", "执行成功");
                    response.getWriter().print(returnJson);
                    response.getWriter().flush();
                    response.getWriter().close();
                    log.info("lakala pay call back success");
                } catch (IOException iOE) {
                    log.error("lakala pay call back io exception", iOE);
                }
            }
        } else { // 失败
            addPayCallBackResult(
                    PayCallBackResultAddRequest.builder()
                            .businessId(businessId)
                            .resultContext(body)
                            .resultStatus(PayCallBackResultStatus.FAILED)
                            .errorNum(99)
                            .payType(PayCallBackType.LAKALA_CASHER)
                            .build());
            try {
                returnJson.put("code", "SUCCESS");
                returnJson.put("message", "执行成功");
                response.getWriter().print(returnJson);
                response.getWriter().flush();
                response.getWriter().close();
                log.info("lakala pay call back success");
            } catch (IOException iOE) {
                log.error("lakala pay call back io exception", iOE);
            }
        }

    }
}