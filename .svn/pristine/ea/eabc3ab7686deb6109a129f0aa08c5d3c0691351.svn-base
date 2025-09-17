package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailListByConditionResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.request.refund.RefundOrderPageRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderWithoutPageRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderPageResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderWithoutPageResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderResponseVO;
import com.wanmi.sbc.order.bean.vo.RefundOrderResponse;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RefundOrderExportService implements ExportBaseService {

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider ;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider ;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private OsdService osdService;

    public BaseResponse exportReport(ExportData data) throws Exception{

        RefundOrderRequest refundOrderRequest = JSON.parseObject(data.getParam(), RefundOrderRequest.class);
        List<RefundOrderResponse> refundOrderList;

        if (CollectionUtils.isNotEmpty(refundOrderRequest.getRefundIds())) {
            // 选中导出，pageSize设为选中ids数
            refundOrderRequest.setPageSize(refundOrderRequest.getRefundIds().size());
        } else {
            // 参数筛选导出，pageSize设为上限100000
            refundOrderRequest.setPageSize(100000);
        }

        RefundOrderPageRequest refundOrderPageRequest = KsBeanUtil.convert(refundOrderRequest, RefundOrderPageRequest.class);
        RefundOrderPageResponse refundOrderPageResponse = refundOrderQueryProvider.page(refundOrderPageRequest).getContext();
        refundOrderList = refundOrderPageResponse.getData();

        List<String> withoutCustomerNameIds = refundOrderList.stream()
                .filter(item -> StringUtils.isBlank(item.getCustomerName()))
                .map(RefundOrderResponse::getCustomerDetailId)
                .distinct()
                .collect(Collectors.toList());

        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(withoutCustomerNameIds)) {
            CustomerDetailListByConditionRequest request = CustomerDetailListByConditionRequest.builder()
                    .customerDetailIds(withoutCustomerNameIds)
                    .build();
            CustomerDetailListByConditionResponse detailResponse =
                    customerDetailQueryProvider.listCustomerDetailByCondition(request).getContext();
            List<CustomerDetailVO> customerDetailVOList = detailResponse.getCustomerDetailVOList();
            Map<String, String> customerNameMap = customerDetailVOList.stream()
                    .collect(Collectors.toMap(CustomerDetailVO::getCustomerDetailId, CustomerDetailVO::getCustomerName));
            refundOrderList.stream()
                    .filter(item -> customerNameMap.containsKey(item.getCustomerDetailId()))
                    .forEach(item -> item.setCustomerName(customerNameMap.get(item.getCustomerDetailId())));
        }

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出退单退款记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("refundOrder/excel/%s", fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //写入表头
        export(refundOrderList, outputStream);

        //上传
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(){
        return new Column[]{
                new Column("退款流水号", new SpelColumnRender<RefundOrderResponse>("refundBillCode != null && refundBillCode != '' ? refundBillCode : '-' ")),
                new Column("交易流水号", new SpelColumnRender<RefundOrderResponse>("tradeNo != null && tradeNo != '' ? tradeNo : '-' ")),
                new Column("退单时间", new SpelColumnRender<RefundOrderResponse>("createTime != null && createTime != '' ? createTime : '-'")),
                new Column("退单编号", new SpelColumnRender<RefundOrderResponse>("returnOrderCode != null && returnOrderCode != '' ? returnOrderCode : '-'")),
                new Column("商家名称", new SpelColumnRender<RefundOrderResponse>("supplierName != null && supplierName != '' ? supplierName : '-'")),
                new Column("客户名称", new SpelColumnRender<RefundOrderResponse>("customerName != null && customerName != '' ? customerName : '-'")),
                new Column("退款时间", new SpelColumnRender<RefundOrderResponse>("refundBillTime != null && refundBillTime != '' ? refundBillTime : '-'")),
                new Column("支付方式", (cell, object) -> {
                    RefundOrderResponse refundOrderResponse = (RefundOrderResponse) object;
                    String payTypeStr = "-";
                    PayType payType = refundOrderResponse.getPayType();
                    if (PayType.OFFLINE == payType) {
                        // 线下支付
                        payTypeStr = PayType.OFFLINE.getDesc();
                    } else if(PayType.ONLINE == payType) {
                        // 线上支付
                        payTypeStr = "线上支付";
                    }
                    cell.setCellValue(payTypeStr);
                }),
                new Column("退款渠道", new SpelColumnRender<RefundOrderResponse>("payChannelValue != null && payChannelValue != '' ? payChannelValue : '-'")),
                new Column("抵扣方式", (cell, object) -> {
                    RefundOrderResponse refundOrderResponse = (RefundOrderResponse) object;
                    String valStr = "-";
                    boolean usePayOrderPointsFlag =
                            Objects.nonNull(refundOrderResponse.getReturnPoints()) && refundOrderResponse.getReturnPoints().compareTo(0L) > 0;
                    boolean useGiftCardPriceFlag =
                            Objects.nonNull(refundOrderResponse.getGiftCardPrice()) && refundOrderResponse.getGiftCardPrice().compareTo(BigDecimal.ZERO) > 0;
                    if (usePayOrderPointsFlag && useGiftCardPriceFlag) {
                        valStr = "积分/礼品卡";
                    } else if (usePayOrderPointsFlag) {
                        valStr = "积分";
                    } else if (useGiftCardPriceFlag) {
                        valStr = "礼品卡";
                    }
                    cell.setCellValue(valStr);
                }),
                new Column("应退金额", (cell, object) -> {
                    RefundOrderResponse refundOrderResponse = (RefundOrderResponse) object;
                    if (Objects.nonNull(refundOrderResponse.getReturnPrice())) {
                        cell.setCellValue("￥" + refundOrderResponse.getReturnPrice().toString());
                    }
                }),
                new Column("应退积分", new SpelColumnRender<RefundOrderResponse>("returnPoints")),
                new Column("退单改价", (cell, object) -> {
                    RefundOrderResponse refundOrderResponse = (RefundOrderResponse) object;
                    if (Objects.nonNull(refundOrderResponse.getActualReturnPrice())
                            && refundOrderResponse.getActualReturnPrice().compareTo(refundOrderResponse.getReturnPrice() )!= 0) {
                        cell.setCellValue("￥" + refundOrderResponse.getActualReturnPrice().toString());
                    }else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("退款账户", new SpelColumnRender<RefundOrderResponse>("returnAccountName != null && returnAccountName != '' ? returnAccountName : '-'" )),
                new Column("客户收款账户", new SpelColumnRender<RefundOrderResponse>("customerAccountName != null && customerAccountName != '' ? customerAccountName : '-'" )),
                new Column("退款状态", (cell, object) -> {
                    RefundOrderResponse refundOrderResponse = (RefundOrderResponse) object;
                    if (RefundStatus.TODO.equals(refundOrderResponse.getRefundStatus())) {
                        cell.setCellValue("待退款");
                    } else if (RefundStatus.APPLY.equals(refundOrderResponse.getRefundStatus())) {
                        cell.setCellValue("待退款");
                    } else if (RefundStatus.FINISH.equals(refundOrderResponse.getRefundStatus())) {
                        cell.setCellValue("已退款");
                    } else {
                        cell.setCellValue("-");
                    }
                }),
        };
    }

    private void export(List<RefundOrderResponse> refundOrderList, OutputStream outputStream) {
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.addSheet(
                "退款明细",
                getColumn(),
                refundOrderList
        );
        excelHelper.write(outputStream);
    }
}
