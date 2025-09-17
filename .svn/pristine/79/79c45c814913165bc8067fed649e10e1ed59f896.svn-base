package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsExportRequest;
import com.wanmi.sbc.account.bean.vo.CustomerFundsVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.CustomerFundsBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @className CustomerFundsExportService
 * @description 会员资金导出
 * @date 2021/6/4 6:19 下午
 **/
@Service
@Slf4j
public class CustomerFundsExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    public static final int SIZE = 5000;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private CustomerFundsBaseService customerFundsBaseService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("customerFunds export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出会员资金记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("customerFunds/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("会员资金导出", columns);

        CustomerFundsExportRequest queryReq = JSON.parseObject(data.getParam(), CustomerFundsExportRequest.class);
        Long total = customerFundsQueryProvider.count(queryReq).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            // 导出数据查询
            List<CustomerFundsVO> customerFundsVOList = customerFundsBaseService.query(data.getOperator(),queryReq);
            if(CollectionUtils.isNotEmpty(customerFundsVOList)){
                //判断客户是否已注销
                Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerFundsVOList.stream()
                        .map(CustomerFundsVO::getCustomerId).collect(Collectors.toList()));

                customerFundsVOList.forEach(dataRecord -> {
                    if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(dataRecord.getCustomerId()))){
                        dataRecord.setCustomerAccount(dataRecord.getCustomerAccount()+Constants.LOGGED_OUT);
                    }
                });
            }

            excelHelper.addSXSSFSheetRow(sheet, columns, customerFundsVOList, rowIndex + 1);
            rowIndex = rowIndex + customerFundsVOList.size();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * @description 获取表头
     * @author  xuyunpeng
     * @date 2021/6/4 6:22 下午
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("会员名称", new SpelColumnRender<CustomerFundsVO>("customerName")),
                new Column("会员账号", (cell, object) -> {
                    CustomerFundsVO customerFundsVO = (CustomerFundsVO) object;
                    // 账号脱敏
                    cell.setCellValue(SensitiveUtils.handlerMobilePhone(customerFundsVO.getCustomerAccount()));
                }),
                new Column("会员等级", (cell, object) -> {
                    CustomerFundsVO customerFundsVO = (CustomerFundsVO) object;
                    if (Objects.nonNull(customerFundsVO.getDistributor()) && customerFundsVO.getDistributor() ==1
                            && StringUtils.isNotBlank(customerFundsVO.getCustomerLevelName())) {
                        cell.setCellValue("分销员 & " + customerFundsVO.getCustomerLevelName());
                    }else {
                        if (Objects.nonNull(customerFundsVO.getDistributor()) && customerFundsVO.getDistributor() == 1) {
                            cell.setCellValue("分销员");
                        }
                        if (StringUtils.isNotBlank(customerFundsVO.getCustomerLevelName())) {
                            cell.setCellValue(customerFundsVO.getCustomerLevelName());
                        }
                    }
                }),
                new Column("账户余额", (cell, object) -> {
                    CustomerFundsVO customerFundsVO = (CustomerFundsVO) object;
                    if (Objects.nonNull(customerFundsVO.getAccountBalance())) {
                        cell.setCellValue(customerFundsVO.getAccountBalance().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("冻结余额", (cell, object) -> {
                    CustomerFundsVO customerFundsVO = (CustomerFundsVO) object;
                    if (Objects.nonNull(customerFundsVO.getBlockedBalance())) {
                        cell.setCellValue(customerFundsVO.getBlockedBalance().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("可提现余额", (cell, object) -> {
                    CustomerFundsVO customerFundsVO = (CustomerFundsVO) object;
                    if (Objects.nonNull(customerFundsVO.getWithdrawAmount())) {
                        cell.setCellValue(customerFundsVO.getWithdrawAmount().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
        };
        return columns;
    }
}
