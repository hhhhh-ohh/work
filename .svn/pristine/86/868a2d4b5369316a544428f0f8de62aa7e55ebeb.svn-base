package com.wanmi.ares.report.trade.service;

import com.wanmi.ares.enums.SortOrder;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.report.trade.model.reponse.TradeReponse;
import com.wanmi.ares.report.trade.model.request.TradeReportRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 交易报表实现
 * Created by sunkun on 2017/11/9.
 */
@Service
public class TradeExportService implements ExportBaseService {

    @Resource
    private TradeReportService tradeReportService;

    @Resource
    private OsdService osdService;

    @Resource
    private ReplayStoreMapper replayStoreMapper;

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        TradeReportRequest tradeReportRequest = new TradeReportRequest();
        LocalDate beginDate = DateUtil.parse2Date(query.getDateFrom(), DateUtil.FMT_DATE_1);
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        tradeReportRequest.setBeginDate(beginDate);
        tradeReportRequest.setEndDate(endDate);
        tradeReportRequest.setSortOrder(SortOrder.DESC);
        // 将CompanyInfoId置为特殊值
        if (Objects.isNull(query.getCompanyId())) {
            // 将CompanyInfoId置为特殊值
            if (Objects.isNull(query.getCompanyId())) {
                if (StoreSelectType.O2O.equals(query.getStoreSelectType())
                        || StoreSelectType.SUPPLIER.equals(query.getStoreSelectType())) {
                    query.setCompanyId(query.getStoreSelectType()
                            .getMockCompanyInfoId());
                }
            }
        }
        tradeReportRequest.setCompanyId(query.getCompanyId());

        // 如果companyId小于0，代表是特殊companyId，将StoreSelectType设为-1不影响业务
        if( Objects.nonNull(query.getCompanyId()) && "0".compareTo(query.getCompanyId()) > 0 || Objects.isNull(query.getStoreSelectType())){
            tradeReportRequest.setStoreSelectType(StoreSelectType.ALL.toValue());
        } else {
            tradeReportRequest.setStoreSelectType(query.getStoreSelectType().toValue());
        }
        List<TradeReponse> list = tradeReportService.getList(tradeReportRequest);
        ExcelHelper<TradeReponse> excelHelper = new ExcelHelper<>();
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("trade/%s/%s/交易统计报表_%s-%s-%s.xls", DateUtil.format(endDate,
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
            fileName = String.format("trade/%s/%s/%s_交易统计报表_%s-%s-%s.xls", DateUtil.format(endDate,
                    DateUtil.FMT_MONTH_2),query.getCompanyId(),storeName, query.getDateFrom(), query.getDateTo(), randomData);
        }
        fileName = osdService.getFileRootPath().concat(fileName);
        if (list == null || list.size() == 0) {
            if (osdService.existsFiles(fileName)) {
                return BaseResponse.success(fileName);
            }
        }
        excelHelper.addSheet(
                "交易报表统计",
                getColumn(),
                list
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
                new Column("日期", (cell,object)->{
                    TradeReponse tradeReponse = (TradeReponse) object;
                    cell.setCellValue(DateUtil.format(tradeReponse.getTime(),DateUtil.FMT_DATE_1));
                }),
                new Column("下单笔数", new SpelColumnRender<TradeReponse>("orderCount")),
                new Column("下单人数", new SpelColumnRender<TradeReponse>("orderNum")),
                new Column("下单金额", new SpelColumnRender<TradeReponse>("orderAmt")),
                new Column("付款订单数", new SpelColumnRender<TradeReponse>("PayOrderCount")),
                new Column("付款人数", new SpelColumnRender<TradeReponse>("PayOrderNum")),
                new Column("付款金额", new SpelColumnRender<TradeReponse>("payOrderAmt")),
                new Column("下单转化率", (cell, object) -> {
                    TradeReponse tradeReponse = (TradeReponse) object;
                    cell.setCellValue(tradeReponse.getOrderConversionRate().toString() + "%");
                }),
                new Column("付款转化率", (cell, object) -> {
                    TradeReponse tradeReponse = (TradeReponse) object;
                    cell.setCellValue(tradeReponse.getPayOrderConversionRate().toString() + "%");
                }),
                new Column("全店转化率", (cell, object) -> {
                    TradeReponse tradeReponse = (TradeReponse) object;
                    cell.setCellValue(tradeReponse.getWholeStoreConversionRate().toString() + "%");
                }),
                new Column("客单价", new SpelColumnRender<TradeReponse>("customerUnitPrice")),
                new Column("笔单价", new SpelColumnRender<TradeReponse>("everyUnitPrice")),
                new Column("退单笔数", new SpelColumnRender<TradeReponse>("returnOrderCount")),
                new Column("退单人数", new SpelColumnRender<TradeReponse>("returnOrderNum")),
                new Column("退单金额", new SpelColumnRender<TradeReponse>("returnOrderAmt")),
        };
    }
}
