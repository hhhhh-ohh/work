package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailExportRequest;
import com.wanmi.sbc.account.bean.enums.FundsType;
import com.wanmi.sbc.account.bean.vo.CustomerFundsDetailVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.bean.enums.TabType;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.CustomerFundsDetailBaseService;
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
import java.util.Objects;

/**
 * @author xuyunpeng
 * @className CustomerFundsDetailExportService
 * @description 会员资金明细导出
 * @date 2021/6/7 1:38 下午
 **/
@Service
@Slf4j
public class CustomerFundsDetailExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CustomerFundsDetailQueryProvider customerFundsDetailQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private CustomerFundsDetailBaseService customerFundsDetailBaseService;

    public static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("customerFundsDetail export begin, param:{}", data);

        CustomerFundsDetailExportRequest queryReq = JSON.parseObject(data.getParam(), CustomerFundsDetailExportRequest.class);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName;
        // 根据tabType 判断分销员佣金明细 or 会员资金明细
        if (queryReq.getTabType().equals(TabType.COMMISSION.toValue())) {
            fileName = String.format("批量导出分销员佣金明细_%s.xls"
                    , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        } else {
            fileName = String.format("批量导出会员资金明细_%s.xls"
                    , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        }
        String resourceKey = String.format("customerFundsDetail/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns(queryReq);
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead(getSheetName(queryReq), columns);

        //总数量
        Long total = customerFundsDetailQueryProvider.countForExport(queryReq).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
//            List<CustomerFundsDetailVO> dataRecords = customerFundsDetailQueryProvider.export(queryReq).getContext().getMicroServicePage();
            excelHelper.addSXSSFSheetRow(sheet, columns, customerFundsDetailBaseService.query(data.getOperator(), queryReq), rowIndex + 1);
            rowIndex = rowIndex + customerFundsDetailBaseService.query(data.getOperator(), queryReq).size();
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
     * @date 2021/6/7 1:46 下午
     */
    public Column[] getColumns(CustomerFundsDetailExportRequest queryReq) {
        Column[] columns = {
                new Column("入账时间", new SpelColumnRender<CustomerFundsDetailVO>("createTime")),
                new Column("业务编号", new SpelColumnRender<CustomerFundsDetailVO>("businessId")),
                new Column("账务类型", (cell, object) -> {
                    CustomerFundsDetailVO customerFundsDetailVO = (CustomerFundsDetailVO) object;
                    if (Objects.nonNull(customerFundsDetailVO.getFundsType())) {
                        // 佣金提现 分销佣金 邀新奖励
                        cell.setCellValue(customerFundsDetailVO.getFundsType().getDesc());
                    }
                }),
                new Column("收支金额", (cell, object) -> {
                    CustomerFundsDetailVO customerFundsDetailVO = (CustomerFundsDetailVO) object;
                    if (Objects.nonNull(customerFundsDetailVO.getReceiptPaymentAmount())) {
                        if (Objects.nonNull(customerFundsDetailVO.getFundsType())) {
                            if (customerFundsDetailVO.getFundsType().equals(FundsType.COMMISSION_WITHDRAWAL) || customerFundsDetailVO.getFundsType().equals(FundsType.BALANCE_PAY)) {
                                // 支出
                                cell.setCellValue("-" + customerFundsDetailVO.getReceiptPaymentAmount());
                            } else {
                                // 收入
                                cell.setCellValue("+" + customerFundsDetailVO.getReceiptPaymentAmount());
                            }
                        }
                    } else {
                        cell.setCellValue("0");
                    }
                }),
        };
        Column[] customerColumn = {
                new Column("账户余额", (cell, object) -> {
                    CustomerFundsDetailVO customerFundsDetailVO = (CustomerFundsDetailVO) object;
                    if (Objects.nonNull(customerFundsDetailVO.getAccountBalance())) {
                        cell.setCellValue(customerFundsDetailVO.getAccountBalance().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
        };

        Column[] newColumns;
        if (queryReq.getTabType().equals(TabType.COMMISSION.toValue())) {
            newColumns = columns;
        } else {
            newColumns = ArrayUtils.addAll(columns, customerColumn);
        }
        return newColumns;
    }

    /**
     * @param queryReq
     * @return
     * @description 获取sheet名称
     * @author xuyunpeng
     * @date 2021/6/7 1:50 下午
     */
    public String getSheetName(CustomerFundsDetailExportRequest queryReq) {
        String sheetName = "明细导出";
        if (queryReq.getTabType().equals(TabType.COMMISSION.toValue())) {
            if (Objects.nonNull(queryReq.getCustomerFundsDetailIdList())) {
                sheetName = "分销员佣金明细导出";
            } else {
                sheetName = getFundsTypeString(queryReq, sheetName);
            }
        } else {
            if (Objects.nonNull(queryReq.getCustomerFundsDetailIdList())) {
                sheetName = "会员资金余额明细导出";
            } else {
                sheetName = getTabTypeString(queryReq, sheetName);
            }
        }
        return sheetName;
    }

    /**
     * 获取账户类型名称
     *
     * @param queryReq
     * @param sheetName
     * @return
     */
    private String getFundsTypeString(CustomerFundsDetailExportRequest queryReq, String sheetName) {
        if (Objects.nonNull(queryReq.getFundsType())) {
            //全部 分销佣金 邀新奖励
            sheetName = queryReq.getFundsType().getDesc();
        }
        return sheetName;
    }

    /**
     * 获取tab类型名称
     *
     * @param queryReq
     * @param sheetName
     * @return
     */
    private String getTabTypeString(CustomerFundsDetailExportRequest queryReq, String sheetName) {
        if (Objects.nonNull(queryReq.getTabType())) {
            //全部 收入 支出
            sheetName = TabType.fromValue(queryReq.getTabType()).getDesc();
        }
        return sheetName;
    }
}
