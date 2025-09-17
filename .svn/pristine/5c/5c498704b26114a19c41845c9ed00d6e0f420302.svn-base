package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.SettlementToExcelRequest;
import com.wanmi.sbc.account.api.response.finance.record.SettlementToExcelResponse;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xuyunpeng
 * @className FinanceSettlementExportService
 * @description 财务结算导出
 * @date 2021/6/4 10:26 上午
 **/
@Service
@Slf4j
public class FinanceSettlementExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private SettlementQueryProvider settlementQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    public static final String NOT_SETTLED = "未结算";

    public static final String SETTLED = "已结算";

    public static final String SETTLE_LATER = "暂不处理";

    public static final String EXCEL_NAME = "财务结算";

    public static final String EXCEL_TYPE = "xls";

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("financeSettlement export begin, param:{}", data);

        SettlementToExcelRequest request = JSON.parseObject(data.getParam(), SettlementToExcelRequest.class);
        String fileName = getFileName(request.getStartTime(), request.getEndTime());
        String resourceKey = String.format("financeSettlement/excel/%s", fileName);

        // 财务结算报表导出数据
        SettlementToExcelResponse excelResponse = settlementQueryProvider.getSettlementExportData(request).getContext();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeSettleToExcel(excelResponse, outputStream, request.getStoreType(), request.getSettleStatus());
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * 按照财务结算状态写入Excel
     *
     * @param settlementViewExcel 财务结算报表导出数据
     * @param outputStream
     */
    private void writeSettleToExcel(SettlementToExcelResponse settlementViewExcel, OutputStream outputStream,
                                    StoreType storeType, SettleStatus settleStatus) {
        ExcelHelper helper = new ExcelHelper();

        if(storeType == StoreType.PROVIDER) {
            //写入未结算数据
            doProviderExportSettle(helper, settlementViewExcel.getNotSettledSettlements(), NOT_SETTLED,
                    settleStatus.equals(SettleStatus.NOT_SETTLED));
            //写入已结算数据
            doProviderExportSettle(helper, settlementViewExcel.getSettledSettlements(), SETTLED,
                    settleStatus.equals(SettleStatus.SETTLED));
        } else {
            //写入未结算数据
            doExportSettle(helper, settlementViewExcel.getNotSettledSettlements(), NOT_SETTLED,
                    settleStatus.equals(SettleStatus.NOT_SETTLED));
            //写入已结算数据
            doExportSettle(helper, settlementViewExcel.getSettledSettlements(), SETTLED,
                    settleStatus.equals(SettleStatus.SETTLED));
        }
        //导出文件
        helper.writeForSXSSF(outputStream);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getSettleColumn(String sheetName) {
        List<Column> columnList = new ArrayList<>();
        if (sheetName.equals(SETTLED)) {
            columnList.add(new Column("结算时间", (cell, object) -> {
                SettlementViewVO settlementView = (SettlementViewVO) object;
                String cellValue = DateUtil.format(settlementView.getSettleTime(), DateUtil.FMT_DATE_1);
                cell.setCellValue(StringUtils.defaultString(cellValue));
            }));
        }
        columnList.add(new Column("生成时间", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            String cellValue = DateUtil.format(settlementView.getCreateTime(), DateUtil.FMT_DATE_1);
            cell.setCellValue(StringUtils.defaultString(cellValue));
        }));
        columnList.add(new Column("结算单号", new SpelColumnRender<SettlementViewVO>("settlementCode")));
        columnList.add(new Column("结算时间段", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            String startTime = settlementView.getStartTime();
            String endTime = settlementView.getEndTime();
            String cellValue = startTime +
                    "～" +
                    endTime;
            cell.setCellValue(cellValue);
        }));
        columnList.add(new Column("店铺名称", new SpelColumnRender<SettlementViewVO>("storeName")));
        columnList.add(new Column("商品实付总额", new SpelColumnRender<SettlementViewVO>("splitPayPrice")));
        columnList.add(new Column("运费总额", new SpelColumnRender<SettlementViewVO>("deliveryPrice")));
        columnList.add(new Column("通用券优惠总额", new SpelColumnRender<SettlementViewVO>("commonCouponPrice")));
        columnList.add(new Column("积分抵扣总额", new SpelColumnRender<SettlementViewVO>("pointPrice")));
        columnList.add(new Column("积分数量", new SpelColumnRender<SettlementViewVO>("points")));
        columnList.add(new Column("平台佣金总额", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            BigDecimal platformPrice = settlementView.getPlatformPrice();
            cell.setCellValue(this.getPriceStr(platformPrice));
        }));
        columnList.add(new Column("分销佣金总额", new SpelColumnRender<SettlementViewVO>("commissionPrice")));
        columnList.add(new Column("社区团购佣金总额", new SpelColumnRender<SettlementViewVO>("communityCommissionPrice")));
        columnList.add(new Column("供货总额", new SpelColumnRender<SettlementViewVO>("providerPrice")));
        columnList.add(new Column("供货运费", new SpelColumnRender<SettlementViewVO>("thirdPlatFormFreight")));
        columnList.add(new Column("店铺应收总额", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            BigDecimal storePrice = settlementView.getStorePrice();
            cell.setCellValue(this.getPriceStr(storePrice));
        }));
        columnList.add(new Column("结算状态", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            SettleStatus settleStatus = settlementView.getSettleStatus();
            if (settleStatus == SettleStatus.NOT_SETTLED) {
                cell.setCellValue(NOT_SETTLED);
            } else if (settleStatus == SettleStatus.SETTLED) {
                cell.setCellValue(SETTLED);
            } else if (settleStatus == SettleStatus.SETTLE_LATER) {
                cell.setCellValue(SETTLE_LATER);
            }
        }));
        return columnList.toArray(new Column[columnList.size()]);
    }

    /**
     * 财务结算报表写入数据
     *
     * @param settlementViewList
     * @param helper
     * @param sheetName
     */
    @SuppressWarnings("unchecked")
    private void doExportSettle(ExcelHelper helper, List<SettlementViewVO>
            settlementViewList, String sheetName, boolean isActiveSheet) {
        helper.addSXSSFSheet(sheetName, this.getSettleColumn(sheetName), settlementViewList, isActiveSheet);
    }


    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(String sheetName) {
        List<Column> columnList = new ArrayList<>();
        if (sheetName.equals(SETTLED)) {
            columnList.add(new Column("结算时间", (cell, object) -> {
                SettlementViewVO settlementView = (SettlementViewVO) object;
                String cellValue = DateUtil.format(settlementView.getSettleTime(), DateUtil.FMT_DATE_1);
                cell.setCellValue(StringUtils.defaultString(cellValue));
            }));
        }
        columnList.add(new Column("结算单生成时间", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            String cellValue = DateUtil.format(settlementView.getCreateTime(), DateUtil.FMT_DATE_1);
            cell.setCellValue(StringUtils.defaultString(cellValue));
        }));
        columnList.add(new Column("结算单号", new SpelColumnRender<SettlementViewVO>("settlementCode")));
        columnList.add(new Column("结算时间段", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            String startTime = settlementView.getStartTime();
            String endTime = settlementView.getEndTime();
            String cellValue = startTime +
                    "～" +
                    endTime;
            cell.setCellValue(cellValue);
        }));
        columnList.add(new Column("供货运费", new SpelColumnRender<SettlementViewVO>("thirdPlatFormFreight")));
        columnList.add(new Column("供应商名称", new SpelColumnRender<SettlementViewVO>("storeName")));
        columnList.add(new Column("应收总额", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            BigDecimal storePrice = settlementView.getStorePrice();
            cell.setCellValue(this.getPriceStr(storePrice));
        }));
        columnList.add(new Column("结算状态", (cell, object) -> {
            SettlementViewVO settlementView = (SettlementViewVO) object;
            SettleStatus settleStatus = settlementView.getSettleStatus();
            if (settleStatus == SettleStatus.NOT_SETTLED) {
                cell.setCellValue(NOT_SETTLED);
            } else if (settleStatus == SettleStatus.SETTLED) {
                cell.setCellValue(SETTLED);
            } else if (settleStatus == SettleStatus.SETTLE_LATER) {
                cell.setCellValue(SETTLE_LATER);
            }
        }));
        return columnList.toArray(new Column[columnList.size()]);
    }

    /**
     * 财务结算报表写入数据
     *
     * @param settlementViewList
     * @param helper
     * @param sheetName
     */
    @SuppressWarnings("unchecked")
    private void doProviderExportSettle(ExcelHelper helper, List<SettlementViewVO>
            settlementViewList, String sheetName, boolean isActiveSheet) {
        helper.addSXSSFSheet(sheetName, getColumn(sheetName), settlementViewList, isActiveSheet);
    }

    /**
     * 设置导出文件名
     * @return
     */
    private String getFileName(String startTime, String endTime) {
        String fileName = EXCEL_NAME + "." + EXCEL_TYPE;
        if (StringUtils.isNotBlank(startTime)) {
            fileName = EXCEL_NAME + startTime + "～" + endTime + "." + EXCEL_TYPE;
        }
        return fileName;
    }

    /**
     * 处理价格格式
     * @return
     */
    private String getPriceStr(BigDecimal price) {
        if (Objects.nonNull(price)) {
            return price.setScale(2, RoundingMode.HALF_UP).toString();
        }
        return "0.00";
    }
}
