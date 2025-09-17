package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailPageRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailForPageVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
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
 * @className CustomerPointsExportService
 * @description 客户积分导出
 * @date 2021/6/3 9:46 上午
 **/
@Slf4j
@Service
public class CustomerPointsExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    private final static int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("customerPoints export begin, param:{}", data);
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("积分列表_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("customerPoints/excel/%s", fileName);

        CustomerDetailPageRequest customerDetailQueryRequest = JSON.parseObject(data.getParam(), CustomerDetailPageRequest.class);
        customerDetailQueryRequest.setDelFlag(DefaultFlag.NO.toValue());
        Long total = customerQueryProvider.count(customerDetailQueryRequest).getContext();

        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("积分列表", columns);

        if (!total.equals(NumberUtils.LONG_ZERO)) {
            customerDetailQueryRequest.setPageSize(SIZE);
            customerDetailQueryRequest.putSort("customer.pointsAvailable", SortType.DESC.toValue());

            int rowIndex = 0;
            for (int i = 0; i < fileSize; i++) {
                customerDetailQueryRequest.setPageNum(i);
                List<CustomerDetailForPageVO> customerVOS = customerQueryProvider.page(customerDetailQueryRequest)
                        .getContext()
                        .getDetailResponseList();
                customerVOS.forEach(vo -> {
                                    if (Objects.equals(LogOutStatus.LOGGED_OUT,vo.getLogOutStatus())){
                                        vo.setCustomerAccount(vo.getCustomerAccount()+ Constants.LOGGED_OUT);
                                    }
                                });
                excelHelper.addSXSSFSheetRow(sheet, columns, customerVOS, rowIndex + 1);
                rowIndex = rowIndex + customerVOS.size();
            }
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
     * @date 2021/6/3 10:35 上午
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("会员名称", new SpelColumnRender<CustomerDetailForPageVO>("customerName")),
                new Column("账号", new SpelColumnRender<CustomerDetailForPageVO>("customerAccount")),
                new Column("账号状态", (cell, object) -> {
                    CustomerDetailForPageVO d = (CustomerDetailForPageVO) object;
                    if(Objects.nonNull(d)) {
                        if (d.getCustomerStatus().equals(CustomerStatus.ENABLE)) {
                            cell.setCellValue("启用");
                        } else {
                            cell.setCellValue("禁用");
                        }
                        if(Objects.nonNull(d.getLogOutStatus())
                                && d.getLogOutStatus().equals(LogOutStatus.LOGGED_OUT)){
                            cell.setCellValue("已注销");
                        }
                    }
                }),
                new Column("积分余额", new SpelColumnRender<CustomerDetailForPageVO>("pointsAvailable")),
        };
        return columns;
    }
}
