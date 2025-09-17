package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashQueryProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.customerdrawcash.CustomerDrawCashExportRequest;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.account.bean.enums.AccountStatus;
import com.wanmi.sbc.account.bean.enums.CheckState;
import com.wanmi.sbc.account.bean.vo.CustomerDrawCashVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.DisableCustomerDetailGetByAccountRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.DrawCashBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
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
 * @className DrawCashExportService
 * @description 会员提现导出
 * @date 2021/6/7 3:11 下午
 **/
@Service
@Slf4j
public class DrawCashExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CustomerDrawCashQueryProvider customerDrawCashQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    public static final int SIZE = 5000;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private DrawCashBaseService drawCashBaseService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("drawCash export begin, param:{}", data);

        CustomerDrawCashExportRequest queryReq = JSON.parseObject(data.getParam(), CustomerDrawCashExportRequest.class);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出会员提现记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("drawCash/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns(queryReq);
        String sheetName = "会员提现导出";
        if (Objects.nonNull(queryReq.getCheckState())) {
            sheetName = queryReq.getCheckState().getDesc();
        }
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead(sheetName, columns);

        Long total = customerDrawCashQueryProvider.countForExport(queryReq).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            // 导出数据查询
//            MicroServicePage<CustomerDrawCashVO> cashVOS = customerDrawCashQueryProvider.export(queryReq).getContext().getCustomerDrawCashVOPage();
            // 设置账号信息
            List<CustomerDrawCashVO> dataRecords = drawCashBaseService.queryExport(data.getOperator(),queryReq).getContent();
            dataRecords.forEach(this::setAccountInfo);
            //判断客户是否已注销
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(dataRecords.stream()
                    .map(CustomerDrawCashVO::getCustomerId).collect(Collectors.toList()));
            dataRecords.forEach(dataRecord -> {
                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(dataRecord.getCustomerId()))){
                    dataRecord.setCustomerAccount(dataRecord.getCustomerAccount()+Constants.LOGGED_OUT);
                }
            });
            excelHelper.addSXSSFSheetRow(sheet, columns, dataRecords, rowIndex + 1);
            rowIndex = rowIndex + dataRecords.size();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    /**
     * @param queryReq
     * @return
     * @description 获取表头
     * @author xuyunpeng
     * @date 2021/6/7 3:14 下午
     */
    public Column[] getColumns(CustomerDrawCashExportRequest queryReq) {
        Column[] columns = {
                new Column("提现单号", new SpelColumnRender<CustomerDrawCashVO>("drawCashNo")),
                new Column("申请时间", new SpelColumnRender<CustomerDrawCashVO>("applyTime")),
        };
        Column[] finishColumn = {
                new Column("完成时间", new SpelColumnRender<CustomerDrawCashVO>("finishTime")),
        };
        Column[] baseColumn = {
                new Column("会员名称", new SpelColumnRender<CustomerDrawCashVO>("customerName")),
                new Column("会员账号", (cell, object) -> {
                    CustomerDrawCashVO customerDrawCashVO = (CustomerDrawCashVO) object;
                    // 账号脱敏
                    cell.setCellValue(SensitiveUtils.handlerMobilePhone(customerDrawCashVO.getCustomerAccount()));
                }),
                new Column("账号状态", (cell, object) -> {
                    CustomerDrawCashVO cashVO = (CustomerDrawCashVO) object;
                    if (Objects.nonNull(cashVO.getAccountStatus())) {
                        if (cashVO.getAccountStatus().equals(AccountStatus.DISABLE)) {
                            cell.setCellValue("禁用");
                        } else if (cashVO.getAccountStatus().equals(AccountStatus.ENABLE)) {
                            cell.setCellValue("启用");
                        }
                    }
                    }),
                new Column("提现渠道", (cell, object) -> {
                    CustomerDrawCashVO cashVO = (CustomerDrawCashVO) object;
                    if (Objects.nonNull(cashVO.getDrawCashChannel())) {
                        cell.setCellValue(cashVO.getDrawCashChannel().getDesc());
                    }
                }),
                new Column("提现账户名称", new SpelColumnRender<CustomerDrawCashVO>("drawCashAccountName")),
                new Column("账户余额", new SpelColumnRender<CustomerDrawCashVO>("accountBalance")),
                new Column("本次提现", new SpelColumnRender<CustomerDrawCashVO>("drawCashSum")),
                new Column("提现备注", new SpelColumnRender<CustomerDrawCashVO>("drawCashRemark")),
        };
        Column[] failColumn = {
                new Column("提现失败原因", new SpelColumnRender<CustomerDrawCashVO>("drawCashFailedReason")),
        };

        Column[] auditColumn = {
                new Column("驳回原因", new SpelColumnRender<CustomerDrawCashVO>("rejectReason")),
        };

        Column[] newColumns = {};
        if (Objects.nonNull(queryReq.getCheckState())) {
            if (queryReq.getCheckState().equals(CheckState.CHECK)) {
                newColumns = ArrayUtils.addAll(columns, baseColumn);
            } else if (queryReq.getCheckState().equals(CheckState.FINISH)) {
                newColumns = ArrayUtils.addAll(columns, finishColumn);
                newColumns = ArrayUtils.addAll(newColumns, baseColumn);
            } else if (queryReq.getCheckState().equals(CheckState.FAIL)) {
                newColumns = ArrayUtils.addAll(columns, baseColumn);
                newColumns = ArrayUtils.addAll(newColumns, failColumn);
            } else if (queryReq.getCheckState().equals(CheckState.NOT_AUDIT)) {
                newColumns = ArrayUtils.addAll(columns, baseColumn);
                newColumns = ArrayUtils.addAll(newColumns, auditColumn);
            } else if (queryReq.getCheckState().equals(CheckState.CANCEL)) {
                newColumns = ArrayUtils.addAll(columns, baseColumn);
            }
        }
        return newColumns;
    }

    /**
     * 设置账户信息
     *
     * @param customerDrawCashVO
     */
    private void setAccountInfo(CustomerDrawCashVO customerDrawCashVO) {
        AccountStatus status = AccountStatus.DISABLE;
        CustomerGetByIdResponse response = customerQueryProvider.getCustomerById(
                new CustomerGetByIdRequest(customerDrawCashVO.getCustomerId())).getContext();
        if (response != null && response.getCustomerDetail() != null && response.getCustomerDetail().getCustomerStatus() != null) {
            status = AccountStatus.valueOf(response.getCustomerDetail().getCustomerStatus().name());
        }
        customerDrawCashVO.setAccountStatus(status);

        DisableCustomerDetailGetByAccountRequest byAccountRequest = new DisableCustomerDetailGetByAccountRequest();
        byAccountRequest.setCustomerAccount(customerDrawCashVO.getCustomerAccount());
        customerDrawCashVO.setForbidReason(customerQueryProvider.getDisableCustomerDetailByAccount(byAccountRequest)
                .getContext().getForbidReason());

        // 会员账号中间4位*号
        customerDrawCashVO.setCustomerAccount(customerDrawCashVO.getCustomerAccount()
                .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));

        CustomerFundsByCustomerIdRequest customerIdReques = new CustomerFundsByCustomerIdRequest();
        customerIdReques.setCustomerId(customerDrawCashVO.getCustomerId());

        CustomerFundsByCustomerIdResponse customerFundsByCustomerIdResponse = customerFundsQueryProvider
                .getByCustomerId(customerIdReques).getContext();

        if (customerFundsByCustomerIdResponse != null) {
            customerDrawCashVO.setCustomerFundsId(customerFundsByCustomerIdResponse.getCustomerFundsId()
            );
        }
    }
}
