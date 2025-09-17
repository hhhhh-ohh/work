package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyListRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.OrderInvoiceResponse;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.OrderTicketBaseService;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceQueryProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.orderinvoice.OrderInvoiceFindAllRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className OrderTicketExportService
 * @description 订单开票导出
 * @date 2021/6/8 9:48 上午
 **/
@Service
@Slf4j
public class OrderTicketExportService implements ExportBaseService {

    @Autowired
    private OrderInvoiceQueryProvider orderInvoiceQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private OrderTicketBaseService orderTicketBaseService;

    public static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("orderTicket export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("订单开票_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("orderTicket/excel/%s", fileName);

        //表头
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("订单开票", columns);

        OrderInvoiceFindAllRequest request = JSON.parseObject(data.getParam(), OrderInvoiceFindAllRequest.class);
        //模糊匹配会员/商户名称
        if(this.likeCustomerAndSupplierName(request)){
            Long total = orderInvoiceQueryProvider.count(request).getContext();
            //总页数
            long fileSize = ReportUtil.calPage(total, SIZE);
            //分页处理
            int rowIndex = 0;
            request.setPageSize(SIZE);
            for (int i = 0; i < fileSize; i++) {
                request.setPageNum(i);
                // 导出数据查询
//                List<OrderInvoiceVO> orderInvoiceVOS = orderInvoiceQueryProvider.findAll(request).getContext().getValue().getContent();
                List<OrderInvoiceResponse> orderInvoiceResponse = orderTicketBaseService.queryExport(data.getOperator(), request);
                //获取用户注销状态
                List<String> customerIds = orderInvoiceResponse.stream().map(OrderInvoiceResponse::getCustomerId).collect(Collectors.toList());
                Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerIds);
                orderInvoiceResponse.forEach(v->{
                    if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(v.getCustomerId()))){
                        v.setCustomerName(v.getCustomerName()+ Constants.LOGGED_OUT);
                    }
                });

                excelHelper.addSXSSFSheetRow(sheet, columns, orderInvoiceResponse, rowIndex + 1);
                rowIndex = rowIndex + orderInvoiceResponse.size();
            }
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    /**
     * @return
     * @description 获取表头
     * @author xuyunpeng
     * @date 2021/6/8 9:50 上午
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("客户名称", new SpelColumnRender<OrderInvoiceResponse>("customerName")),
                new Column("开票时间", new SpelColumnRender<OrderInvoiceResponse>("invoiceTime")),
                new Column("订单号", new SpelColumnRender<OrderInvoiceResponse>("orderNo")),
                new Column("订单金额", new SpelColumnRender<OrderInvoiceResponse>("orderPrice")),
                new Column("付款状态", (cell, object) -> {
                    String status = "";
                    if (Objects.isNull(((OrderInvoiceResponse) object).getPayOrderStatus())) {
                        cell.setCellValue("未付款");
                        return;
                    }
                    switch (((OrderInvoiceResponse) object).getPayOrderStatus()) {
                        case PAYED:
                            status = "已付款";
                            break;
                        case NOTPAY:
                            status = "未付款";
                            break;
                        case TOCONFIRM:
                            status = "待确认";
                            break;
                        default:
                            break;
                    }
                    cell.setCellValue(status);
                }),
                new Column("订单状态", new SpelColumnRender<OrderInvoiceResponse>("flowState") {
                    @Override
                    public void render(Cell cell, OrderInvoiceResponse object) {
                        FlowState flowState = object.getFlowState();
                        String cellValue = "";
                        if (flowState != null) {
                            switch (flowState) {
                                case INIT:
                                    cellValue = "待审核";
                                    break;
                                case REMEDY:
                                    cellValue = "修改订单";
                                    break;
                                case AUDIT:
                                case DELIVERED_PART:
                                    cellValue = "待发货";
                                    break;
                                case DELIVERED:
                                    cellValue = "待收货";
                                    break;
                                case CONFIRMED:
                                    cellValue = "已收货";
                                    break;
                                case COMPLETED:
                                    cellValue = "已完成";
                                    break;
                                case VOID:
                                    cellValue = "已作废";
                                    break;
                                case REFUND:
                                    cellValue = "已退款";
                                    break;
                                case GROUPON:
                                    cellValue = "待成团";
                                    break;
                                default:
                            }
                        }
                        cell.setCellValue(cellValue);
                    }
                }),
                new Column("发票类型", (cell, object) -> {
                    InvoiceType invoiceType = ((OrderInvoiceResponse) object).getInvoiceType();
                    if (InvoiceType.SPECIAL.equals(invoiceType)) {
                        cell.setCellValue("增值税专用发票");
                    } else {
                        cell.setCellValue("普通发票");
                    }
                }),
                new Column("发票抬头", (cell, object) -> {
                    String invoiceTitle = ((OrderInvoiceResponse) object).getInvoiceTitle();
                    if (Objects.isNull(invoiceTitle) || StringUtils.isBlank(invoiceTitle)) {
                        cell.setCellValue("个人");
                    } else {
                        cell.setCellValue(invoiceTitle);
                    }
                }),
                new Column("开票状态", (cell, object) -> {
                    InvoiceState invoiceState = ((OrderInvoiceResponse) object).getInvoiceState();
                    if (InvoiceState.ALREADY.equals(invoiceState)) {
                        cell.setCellValue("已开票");
                    } else {
                        cell.setCellValue("待开票");
                    }
                }),
        };
        return columns;
    }

    /**
     * 替代关联查询-模糊商家名称、模糊会员名称，以并且关系的判断
     * @param queryRequest
     * @return true:有符合条件的数据,false:没有符合条件的数据
     */
    private boolean likeCustomerAndSupplierName(final OrderInvoiceFindAllRequest queryRequest) {
        boolean supplierLike = true;
        //商家名称
        if (!StringUtils.isEmpty(queryRequest.getSupplierName()) && !StringUtils.isEmpty(queryRequest
                .getSupplierName().trim())) {
            List<Long> companyIds = companyInfoQueryProvider.listCompanyInfo(
                    CompanyListRequest.builder().supplierName(queryRequest.getSupplierName()).build()
            ).getContext().getCompanyInfoVOList().stream()
                    .map(CompanyInfoVO::getCompanyInfoId)
                    .collect(Collectors.toList());
            queryRequest.setCompanyInfoIds(companyIds);
            if(CollectionUtils.isEmpty(queryRequest.getCompanyInfoIds())){
                supplierLike = false;
            }
        }
        boolean customerLike = true;
        //模糊会员名称
        if (StringUtils.isNotBlank(queryRequest.getCustomerName()) ||  StringUtils.isNotBlank(queryRequest.getEmployeeId())) {

            List<String> customerIds = customerDetailQueryProvider.listCustomerDetailByCondition(
                    CustomerDetailListByConditionRequest.builder().customerName(queryRequest.getCustomerName()).employeeId(queryRequest.getEmployeeId()).build()
            ).getContext().getCustomerDetailVOList().stream()
                    .map(CustomerDetailVO::getCustomerId).collect(Collectors.toList());

            queryRequest.setCustomerIds(customerIds);

            if (CollectionUtils.isEmpty(queryRequest.getCustomerIds())) {
                customerLike = false;
            }
        }
        return supplierLike && customerLike;
    }
}
