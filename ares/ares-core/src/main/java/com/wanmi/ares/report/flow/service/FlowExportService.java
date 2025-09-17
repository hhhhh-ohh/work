package com.wanmi.ares.report.flow.service;

import com.wanmi.ares.enums.SortOrder;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.flow.model.reponse.FlowReponse;
import com.wanmi.ares.report.flow.model.request.FlowReportRequest;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 流量报表下载实现
 * Created by sunkun on 2017/11/8.
 */
@Service
public class FlowExportService implements ExportBaseService {

    @Resource
    private FlowReportService flowReportService;

    @Resource
    private OsdService osdService;

    @Resource
    private ReplayStoreMapper replayStoreMapper;

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        FlowReportRequest flowReportRequest = new FlowReportRequest();
        flowReportRequest.setBeginDate(DateUtil.parse2Date(query.getDateFrom(), DateUtil.FMT_DATE_1));
        flowReportRequest.setEndDate(DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1));
        flowReportRequest.setPageSize(365);
        flowReportRequest.setSortOrder(SortOrder.DESC);
        // 将CompanyInfoId置为特殊值
        if (Objects.isNull(query.getCompanyId())) {
            if (StoreSelectType.O2O.equals(query.getStoreSelectType())
                    || StoreSelectType.SUPPLIER.equals(query.getStoreSelectType())) {
                query.setCompanyId(query.getStoreSelectType()
                        .getMockCompanyInfoId());
            }
        }
        flowReportRequest.setCompanyId(query.getCompanyId());
        // 如果companyId小于0，代表是特殊companyId，将StoreSelectType设为-1不影响业务
        if( Objects.nonNull(query.getCompanyId()) && "0".compareTo(query.getCompanyId()) > 0 || Objects.isNull(query.getStoreSelectType())){
            flowReportRequest.setStoreSelectType(StoreSelectType.ALL.toValue());
        } else {
            flowReportRequest.setStoreSelectType(query.getStoreSelectType().toValue());
        }
        Page<FlowReponse> page = flowReportService.getPage(flowReportRequest);
        List<FlowReponse> flowReponseList = page.getContent();
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        ExcelHelper<FlowReponse> excelHelper = new ExcelHelper<>();
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("flow/%s/%s/流量统计报表_%s-%s-%s.xls", DateUtil.format(endDate,
                DateUtil.FMT_MONTH_2),query.getCompanyId(), query.getDateFrom(), query.getDateTo(), randomData);
        if(!"0".equals(query.getCompanyId())){
            String storeName = "";
            if(StoreSelectType.SUPPLIER.getMockCompanyInfoId().equals(query.getCompanyId())){
                storeName = "全部店铺";
            } else if(StoreSelectType.O2O.getMockCompanyInfoId().equals(query.getCompanyId())){
                storeName = "全部门店";
            } else {
                storeName = replayStoreMapper.findCompanyName(query.getCompanyId());
            }
            fileName = String.format("flow/%s/%s/%s_流量统计报表_%s-%s-%s.xls", DateUtil.format(endDate, DateUtil.FMT_MONTH_2)
                    ,query.getCompanyId(),storeName, query.getDateFrom(), query.getDateTo(), randomData);
        }
        fileName = osdService.getFileRootPath().concat(fileName);
        if (flowReponseList == null || flowReponseList.isEmpty()) {
            if (osdService.existsFiles(fileName)) {
                return BaseResponse.success(fileName);
            }
        }
        excelHelper.addSheet(
                "流量报表统计",
                getColumn(),
                flowReponseList
        );

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            excelHelper.write(os);
            osdService.uploadExcel(os, fileName);
        }
        return BaseResponse.success(fileName);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(){
        return new Column[]{
                new Column("日期", (cell, object) -> {
                    FlowReponse flowReponse = (FlowReponse) object;
                    cell.setCellValue(DateUtil.format(flowReponse.getDate(), DateUtil.FMT_DATE_1));
                }),
                new Column("访客数UV", new SpelColumnRender<FlowReponse>("totalUv")),
                new Column("浏览量PV", new SpelColumnRender<FlowReponse>("totalPv")),
                new Column("商品访客数", new SpelColumnRender<FlowReponse>("skuTotalUv")),
                new Column("商品浏览量", new SpelColumnRender<FlowReponse>("skuTotalPv")),
        };
    }
}
