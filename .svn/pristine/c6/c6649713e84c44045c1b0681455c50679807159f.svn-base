package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.entity.TradeExportRequest;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.TradeBaseService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListExportRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.CommunityTradeCommissionVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
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
 * @author 王超
 * @className TradeExportService
 * @description 社区团长佣金明细导出
 * @date 2023/8/1 10:43 上午
 **/
@Service
@Slf4j
public class CommunityTradeExportService implements ExportBaseService {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private TradeBaseService tradeBaseService;


    private static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出社区团订单_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("communityTrade/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);
        TradeExportRequest tradeExportRequest = JSON.parseObject(data.getParam(), TradeExportRequest.class);
        TradeListExportRequest exportRequest = getTradeRequest(data,tradeExportRequest);
        exportRequest.getTradeQueryDTO().setFillLeaderNameFlag(Boolean.TRUE);
        Long total = tradeQueryProvider.countTradeExport(exportRequest).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //写入表头
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = this.getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("团长佣金明细导出", columns);

        //如果没有数据，直接生成空Excel
        if (total.equals(NumberUtils.LONG_ZERO)) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }

        //分页查询、导出
        exportRequest.getTradeQueryDTO().setPageSize(SIZE);

        try {
            exportTrade(excelHelper, sheet, columns, fileSize, exportRequest, data);
        } catch (Exception e) {
            excelHelper.deleteTempFile();
            throw e;
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * @description 获取订单数量（商家/平台）
     * @author  xuyunpeng
     * @date 2021/6/1 6:46 下午
     * @param data
     * @return
     */
    public TradeListExportRequest getTradeRequest(ExportData data,TradeExportRequest tradeExportRequest) {

        if (StringUtils.isNotEmpty(data.getAdminId())) {
            tradeExportRequest.setSupplierId(Long.valueOf(data.getAdminId()));
        }

        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeExportRequest.makeAllAuditFlow();
        TradeQueryDTO tradeQueryDTO = KsBeanUtil.convert(tradeExportRequest, TradeQueryDTO.class);
        TradeListExportRequest exportRequest = TradeListExportRequest.builder().tradeQueryDTO(tradeQueryDTO).build();
        return exportRequest;
    }

    /**
     * @description 导出trade
     * @author  xuyupeng
     * @date 2021/6/1 2:45 下午
     * @return
     */
    public void exportTrade(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns, long fileSize, TradeListExportRequest exportRequest, ExportData data) throws Exception {
        //分页写获取数据并写入excel
        int rowIndex = 0;
        // 表格需要合并的位置
        int[] index = {0,1,2,3,5,6,7,8,9,10,11};
        for (int i = 0; i < fileSize; i++) {
            exportRequest.getTradeQueryDTO().setPageNum(i);
            List<TradeVO> tradeVos = tradeBaseService.getTrade(data.getOperator(), exportRequest);
            //获取用户注销状态
            List<String> customerIds = tradeVos.stream().map(v -> v.getBuyer().getId()).collect(Collectors.toList());
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerIds);
            tradeVos.forEach(v -> {
                if (Objects.equals(LogOutStatus.LOGGED_OUT, map.get(v.getBuyer().getId()))) {
                    v.getBuyer().setName(Objects.toString(v.getBuyer().getName(), "") + Constants.LOGGED_OUT);
                }
            });
            addSXSSFSheetRowForTrade(sheet, columns, tradeVos, rowIndex + 1, index);
            rowIndex = rowIndex + tradeVos.size();
        }
    }



    /**
     * 分页导出Excel
     * @author  xufeng
     * @date 2021/12/15 10:37 下午
     * @param sheet
     * @param columns
     * @param dataList
     * @param rowIndex
     * @return void
     **/
    public void addSXSSFSheetRowForTrade(SXSSFSheet sheet, Column[] columns, List<TradeVO> dataList,int rowIndex, int[] ints) {
        for (TradeVO data : dataList) {
            int cellIndex = 0;
            SXSSFRow row = sheet.createRow(rowIndex);
            for (Column column : columns) {
                SXSSFCell cell = row.createCell(cellIndex++);
                column.getRender().render(cell, data);
            }

            // 合并单元格
            int lastRow = data.getRowNum();
            if (lastRow > Constants.ONE){
                for (int i : ints) {
                    CellRangeAddress region = new CellRangeAddress(rowIndex,rowIndex+lastRow-1,i,i);
                    sheet.addMergedRegion(region);
                }
            }
            rowIndex++;
        }
    }

    /**
     * @description
     * @author  佣金明细报表表头
     * @date 2021/6/1 2:06 下午
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("订单编号", new SpelColumnRender<TradeVO>("id")),
                new Column("下单时间", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    if (Objects.nonNull(trade.getTradeState()) && Objects.nonNull(trade.getTradeState().getCreateTime())){
                        cell.setCellValue(trade.getTradeState().getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("会员", new SpelColumnRender<TradeVO>("buyer.name")),
                new Column("跟团号", new SpelColumnRender<TradeVO>("communityTradeCommission.activityTradeNo")),
                new Column("商品", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String skuName = trade
                            .getTradeItems()
                            .stream()
                            .map(s -> Objects.toString(s.getSkuName(), "")
                                    .concat(Objects.toString(s.getSpecDetails(), "")).concat("×").concat(String.valueOf(s.getNum() == null?1:s.getNum())))
                            .collect(Collectors.joining(","));
                    cell.setCellValue(skuName);
                }),
                new Column("金额", new SpelColumnRender<TradeVO>("tradePrice.totalPrice")),
                new Column("订单状态", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    FlowState flowState = trade.getTradeState().getFlowState();
                    String cellValue = "";
                    switch (flowState) {
                        case INIT:
                            cellValue = "待审核";
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
                        default:
                    }
                    if (StringUtils.isBlank(cellValue) && Objects.equals(PayState.PAID_EARNEST, trade.getTradeState().getPayState())) {
                        cellValue = "待支付尾款";
                    }
                    cell.setCellValue(cellValue);
                }),
                new Column("所属团长名称", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && StringUtils.isNotBlank(vo.getLeaderName())){
                        cell.setCellValue(vo.getLeaderName());
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("所属团长账号", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && StringUtils.isNotBlank(vo.getLeaderPhone())){
                        cell.setCellValue(vo.getLeaderPhone());
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("销售渠道", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && Objects.nonNull(vo.getSalesType()) ){
                        if(vo.getSalesType().equals(CommunitySalesType.SELF)){
                            cell.setCellValue("自主销售");
                        }else if(vo.getSalesType().equals(CommunitySalesType.LEADER)){
                            cell.setCellValue("团长帮卖");
                        }else{
                            cell.setCellValue("-");
                        }
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("入账状态", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && BoolFlag.YES.equals(vo.getBoolFlag())){
                        cell.setCellValue("已入账");
                    }else{
                        cell.setCellValue("未入账");
                    }
                }),
                new Column("自提/帮卖佣金", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && Objects.nonNull(vo.getTotalCommission())){
                        cell.setCellValue(vo.getTotalCommission().toString());
                    }else{
                        cell.setCellValue("0");
                    }
                })
        };
        return columns;
    }

}
