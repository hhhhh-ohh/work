package com.wanmi.ares.report.trade.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.flow.model.reponse.FlowStoreReportResponse;
import com.wanmi.ares.report.flow.model.request.FlowStoreReportRequest;
import com.wanmi.ares.report.flow.service.FlowReportService;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.reponse.TradeReponse;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.trade.model.root.TradeBase;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.ParseData;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 交易报表实现
 * Created by sunkun on 2017/11/9.
 */
@Service
public class StoreTradeExportService implements ExportBaseService {

    @Resource
    private OsdService osdService;

    @Resource
    private ReplayStoreMapper replayStoreMapper;

    @Resource
    private TradeReportMapper tradeReportMapper;

    @Resource
    private FlowReportService flowReportService;


    private void computeUv(TradeBase tradeBase,Long uv){
        tradeBase.setCreateTime(LocalDateTime.now());
        //填充下单转化率 统计时间内，下单人数/访客数UV
        if (uv == null || uv.equals(0L)){
            tradeBase.setUv(0L);
            tradeBase.setOrderConversion(new BigDecimal("100.00"));
            //填充全店转换率 统计时间内，付款人数/访客数UV
            tradeBase.setAllConversion(new BigDecimal("100.00"));
        }else {
            tradeBase.setUv(uv);
            tradeBase.setOrderConversion(new BigDecimal(tradeBase.getOrderUserNum())
                    .multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(uv), 2, RoundingMode.HALF_UP));
            //填充全店转换率 统计时间内，付款人数/访客数UV
            tradeBase.setAllConversion(new BigDecimal(tradeBase.getPayUserNum())
                    .multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(uv), 2, RoundingMode.HALF_UP));
        }
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        LocalDate beginDate = DateUtil.parse2Date(query.getDateFrom(), DateUtil.FMT_DATE_1);
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1);
        List<TradeBase> tradeBases =
                tradeReportMapper.collectTrade(
                        TradeCollect.builder()
                                .beginDate(beginDate)
                                .endDate(endDate)
                                .storeType(query.getTypeCd() == 50 ? 2 : null)
                                .build());
        //查询所有店铺流量
        List<FlowStoreReportResponse> flowStoreReportList = flowReportService.getFlowStoreReportList(FlowStoreReportRequest.builder()
                .beginDate(beginDate)
                .endDate(endDate)
                .build());
        Map<String, Long> comUvMap = flowStoreReportList.stream()
                .collect(Collectors.toMap(FlowStoreReportResponse::getCompanyId, FlowStoreReportResponse::getUv));
        List<TradeReponse> list = tradeBases.stream()
                .filter(i-> i.getCompanyId() != null)
                .sorted((i,j)->j.getOrderMoney().compareTo(i.getOrderMoney()))
                .map(i->{
                    this.computeUv(i,comUvMap.get(i.getCompanyId().toString()));
                    TradeReponse tradeReponse = ParseData.parseDataResponse(i);
                    String companyName = replayStoreMapper.findCompanyName(i.getCompanyId().toString());
                    tradeReponse.setTitle(companyName);
                    return tradeReponse;
                }).collect(Collectors.toList());
        ExcelHelper<TradeReponse> excelHelper = new ExcelHelper<>();
        String randomData = RandomString.get().randomAlpha(4);
        String fileNameExt = "店铺";
        if(query.getTypeCd() == 50){
            fileNameExt = "门店";
        }
        String fileName = String.format("trade/%s/" + fileNameExt + "交易统计报表_" +
                        "%s-%s-%s.xls",
                DateUtil.format(endDate,
                DateUtil.FMT_MONTH_2), query.getDateFrom(), query.getDateTo(), randomData);
        fileName = osdService.getFileRootPath().concat(fileName);
        if (list == null || list.size() == 0) {
//            String emptyFile = "trade/店铺交易.xls";
            if (osdService.existsFiles(fileName)) {
                return BaseResponse.success(fileName);
            }
        }
        excelHelper.addSheet(
                fileNameExt + "交易报表统计",
                getColumn(fileNameExt),
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
    public Column[] getColumn(String fileNameExt){
        return new Column[]{
                new Column(fileNameExt + "名称",
                        new SpelColumnRender<TradeReponse>("title")),
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
