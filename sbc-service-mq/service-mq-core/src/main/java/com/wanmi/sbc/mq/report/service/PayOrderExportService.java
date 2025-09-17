package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrdersRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrdersWithNoPageRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrdersResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrdersWithNoPageResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class PayOrderExportService implements ExportBaseService {

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider ;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private OsdService osdService;



    public BaseResponse exportReport(ExportData data) throws Exception {

        FindPayOrdersRequest payOrderRequest = JSON.parseObject(data.getParam(), FindPayOrdersRequest.class);

        FindPayOrdersResponse payOrderPageResponse;

        List<PayOrderResponseVO> payOrderResponses = new ArrayList<>();
        if (CollectionUtils.isEmpty(payOrderRequest.getPayOrderIds())) {
            payOrderRequest.setPageSize(1000);
            List<PayOrderResponseVO> pageList;
            do {
                log.info("findPayOrders page {}", payOrderRequest.getPageSize());
                // 获取分页数据
                FindPayOrdersResponse response = payOrderQueryProvider.findPayOrders(payOrderRequest).getContext();
                pageList = Optional.ofNullable(response).map(FindPayOrdersResponse::getPayOrderResponses).orElse(Collections.emptyList());
                payOrderResponses.addAll(pageList);
                // 更新分页请求，页数+1
                payOrderRequest.setPageNum(payOrderRequest.getPageNum() + 1);
            } while (pageList.size() == payOrderRequest.getPageSize());
        } else {

            FindPayOrdersWithNoPageRequest request = KsBeanUtil.convert(payOrderRequest,
                    FindPayOrdersWithNoPageRequest.class);

            FindPayOrdersWithNoPageResponse findPayOrdersWithNoPageResponse =
                    payOrderQueryProvider.findPayOrdersWithNoPage(request).getContext();

            payOrderPageResponse = KsBeanUtil.convert(findPayOrdersWithNoPageResponse, FindPayOrdersResponse.class);
            payOrderResponses = payOrderPageResponse.getPayOrderResponses();
        }
            LocalDateTime dateTime = LocalDateTime.now();
            String fileName = String.format("批量导出订单收款记录_%s.xls"
                    , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
            String resourceKey = String.format("payOrder/excel/%s", fileName);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //写入表头
            export(payOrderResponses, outputStream);


            //上传
            osdService.uploadExcel(outputStream, resourceKey);
            return BaseResponse.success(resourceKey);
    }

    private Column[] getColumn() {
        return new Column[]{
                new Column("收款流水号", new SpelColumnRender<PayOrderResponseVO>("receivableNo != null && receivableNo != '' ? receivableNo : '-'")),
                new Column("下单时间", new SpelColumnRender<PayOrderResponseVO>("createTime != null && createTime != '' ? createTime : '-'")),
                new Column("订单编号", new SpelColumnRender<PayOrderResponseVO>("orderCode != null && orderCode != '' ? orderCode : '-'")),
                new Column("支付时间", new SpelColumnRender<PayOrderResponseVO>("receiveTime != null && receiveTime != '' ? receiveTime : '-'")),
                new Column("商家名称", new SpelColumnRender<PayOrderResponseVO>("supplierName != null && supplierName != '' ? supplierName : '-'")),
                new Column("客户名称", new SpelColumnRender<PayOrderResponseVO>("customerName != null && customerName != '' ? customerName : '-'")),
                new Column("支付方式", (cell, object) -> {
                    PayOrderResponseVO payOrderResponse = (PayOrderResponseVO) object;
                    String payTypeStr = "-";
                    PayType payType = payOrderResponse.getPayType();
                    if (PayType.OFFLINE == payType) {
                        // 线下支付
                        payTypeStr = PayType.OFFLINE.getDesc();
                    } else if(PayType.ONLINE == payType) {
                        // 线上支付
                        payTypeStr = "线上支付";
                    }
                    cell.setCellValue(payTypeStr);
                }),
                new Column("支付渠道", new SpelColumnRender<PayOrderResponseVO>("payChannelValue != null && payChannelValue != '' ? payChannelValue : '-'")),
                new Column("抵扣方式", (cell, object) -> {
                    PayOrderResponseVO payOrderResponse = (PayOrderResponseVO) object;
                    String valStr = "-";
                    boolean usePayOrderPointsFlag =
                            Objects.nonNull(payOrderResponse.getPayOrderPoints()) && payOrderResponse.getPayOrderPoints().compareTo(0L) > 0;
                    boolean useGiftCardPriceFlag =
                            Objects.nonNull(payOrderResponse.getGiftCardPrice()) && payOrderResponse.getGiftCardPrice().compareTo(BigDecimal.ZERO) > 0;
                    if (usePayOrderPointsFlag && useGiftCardPriceFlag) {
                        if (GiftCardType.PICKUP_CARD.equals(payOrderResponse.getGiftCardType())){
                            valStr = "礼品卡-提货卡";
                        } else {
                            valStr = "积分/礼品卡-现金卡";
                        }
                    } else if (usePayOrderPointsFlag) {
                        valStr = "积分";
                    } else if (useGiftCardPriceFlag) {
                        if (GiftCardType.PICKUP_CARD.equals(payOrderResponse.getGiftCardType())){
                            valStr = "礼品卡-提货卡";
                        } else {
                            valStr = "礼品卡-现金卡";
                        }
                    }
                    cell.setCellValue(valStr);
                }),
                new Column("应收金额", (cell, object) -> {
                    StringBuilder str = new StringBuilder();
                    PayOrderResponseVO payOrderResponse = (PayOrderResponseVO) object;

                    if (Objects.nonNull(payOrderResponse.getPayOrderPoints())) {
                        Long points = payOrderResponse.getPayOrderPoints();
                        if (GiftCardType.PICKUP_CARD.equals(payOrderResponse.getGiftCardType())){
                            points= 0L;
                        }
                        str.append(points);
                        str.append("积分");
                    }
                    if (Objects.nonNull(payOrderResponse.getPayOrderPrice())) {
                        str.append(" ￥");
                        str.append(payOrderResponse.getPayOrderPrice());
                    }
                    cell.setCellValue(str.toString());
                }),
                new Column("收款账户", new SpelColumnRender<PayOrderResponseVO>("receivableAccount != null && receivableAccount != '' ? receivableAccount : '-'")),
                new Column("付款状态", (cell, object) -> {
                    PayOrderResponseVO payOrderResponse = (PayOrderResponseVO) object;
                    if (PayOrderStatus.NOTPAY.equals(payOrderResponse.getPayOrderStatus())) {
                        cell.setCellValue("未付款");
                    } else if (PayOrderStatus.PAYED.equals(payOrderResponse.getPayOrderStatus())) {
                        cell.setCellValue("已付款");
                    } else {
                        cell.setCellValue("待确认");
                    }
                }),
        };
    }

    public void export(List<PayOrderResponseVO> payOrderResponses, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.addSheet(
                "订单收款",
                getColumn(),
                payOrderResponses
        );
        excelHelper.write(outputStream);
    }
}
