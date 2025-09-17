package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.finance.record.AccountRecordProvider;
import com.wanmi.sbc.account.api.request.finance.record.AccountRecordToExcelRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * @author edz
 * @className FinanceBillExportService
 * @description 财务对账导出
 * @date 2021/6/3 4:14 下午
 **/
@Service
@Slf4j
public class FinanceBillExportService implements ExportBaseService {

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private AccountRecordProvider accountRecordProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    public static final String EXCEL_NAME = "财务对账";

    public static final String EXCEL_TYPE = "xlsx";

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("financeBill export begin, param:{}", data);
        AccountRecordToExcelRequest request = JSON.parseObject(data.getParam(), AccountRecordToExcelRequest.class);
        String file = accountRecordProvider.writeAccountRecordToExcel(request).getContext().getFile();

        String fileName = getFileName() + "." + EXCEL_TYPE;
        String resourceKey = String.format("financeBill/excel/%s", fileName);

        yunServiceProvider.uploadFile(YunUploadResourceRequest.builder().resourceType(ResourceType.EXCEL)
                .content(Base64.getMimeDecoder().decode(file))
                .resourceName(fileName)
                .resourceKey(resourceKey)
                .build());
        return BaseResponse.success(resourceKey);
    }

    /**
     * 财务对账获取excel名字
     * @return
     */
    private String getFileName() {
        String fileName = EXCEL_NAME + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+exportUtilService.getRandomNum();
        return fileName;
    }

}
