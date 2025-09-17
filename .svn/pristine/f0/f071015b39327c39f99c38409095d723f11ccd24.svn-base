package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordExportRequest;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className DistributionRecordExportService
 * @description 分销记录导出
 * @date 2021/6/7 4:17 下午
 **/
@Service
@Slf4j
public class DistributionRecordExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private DistributionRecordQueryProvider distributionRecordQueryProvider;

    public static final int SIZE = 5000;

    @Autowired
    private ExportUtilService exportUtilService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("distributionRecord export begin, param:{}", data);

        DistributionRecordExportRequest queryReq = JSON.parseObject(data.getParam(), DistributionRecordExportRequest.class);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出分销记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("distributionRecord/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead(getSheetName(queryReq), columns);

        Long total = distributionRecordQueryProvider.countForExport(queryReq).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            // 导出数据查询
            List<DistributionRecordVO> dataRecords = distributionRecordQueryProvider.export(queryReq).getContext().getDistributionRecordVOList();
            //判断客户是否已注销
            Map<String, LogOutStatus> customerMap = exportUtilService.getLogOutStatus(dataRecords.stream()
                    .filter(v-> Objects.nonNull(v.getCustomerDetailVO()))
                    .map(v -> v.getCustomerDetailVO().getCustomerId()).collect(Collectors.toList()));
            Map<String, LogOutStatus> distributionMap = exportUtilService.getLogOutStatus(dataRecords.stream()
                    .map(v -> v.getDistributionCustomerVO().getCustomerId()).collect(Collectors.toList()));
            dataRecords.forEach(dataRecord -> {
                if (Objects.nonNull(dataRecord.getCustomerDetailVO())
                        && Objects.equals(LogOutStatus.LOGGED_OUT,customerMap.get(dataRecord.getCustomerDetailVO().getCustomerId()))){
                    dataRecord.getCustomerDetailVO().getCustomerVO().setCustomerAccount(dataRecord.getCustomerDetailVO().getCustomerVO().getCustomerAccount()+Constants.LOGGED_OUT);
                }
                if (Objects.equals(LogOutStatus.LOGGED_OUT,distributionMap.get(dataRecord.getDistributionCustomerVO().getCustomerId()))){
                    dataRecord.getDistributionCustomerVO().setCustomerAccount(dataRecord.getDistributionCustomerVO().getCustomerAccount()+Constants.LOGGED_OUT);
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
     * @return
     * @description 获取表头
     * @author xuyunpeng
     * @date 2021/6/7 3:14 下午
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("商品名称",
                        (cell, object) -> {
                            final StringBuilder sb = new StringBuilder();
                            DistributionRecordVO distributionRecord = (DistributionRecordVO) object;
                            if(Objects.nonNull(distributionRecord.getGoodsInfo())){
                                sb.append(distributionRecord.getGoodsInfo().getGoodsInfoName());
                            } else {
                                sb.append("");
                            }
                            List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOS = distributionRecord.getGoodsInfoSpecDetailRelVOS();
                            if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelVOS)) {
                                goodsInfoSpecDetailRelVOS.forEach(v -> sb.append(v.getDetailName()));

                            }
                            cell.setCellValue(sb.toString());
                        }),
                new Column("商品sku编号", new SpelColumnRender<DistributionRecordVO>("goodsInfo.goodsInfoNo")),
                new Column("订单编号", new SpelColumnRender<DistributionRecordVO>("tradeId")),
                new Column("店铺名称", new SpelColumnRender<DistributionRecordVO>("storeVO.storeName")),
                new Column("店铺编号", new SpelColumnRender<DistributionRecordVO>("companyInfoVO.companyCode")),
                new Column("客户名称", new SpelColumnRender<DistributionRecordVO>("customerDetailVO.customerName")),
                new Column("客户账号", (cell, object) -> {
                    DistributionRecordVO distributionRecord = (DistributionRecordVO) object;
                    // 账号脱敏
                    cell.setCellValue(distributionRecord.getCustomerDetailVO() != null ?
                            SensitiveUtils.handlerMobilePhone(distributionRecord.getCustomerDetailVO().getCustomerVO().getCustomerAccount()) : "");
                }),
                new Column("分销员名称", new SpelColumnRender<DistributionRecordVO>("distributionCustomerVO.customerName")),
                new Column("分销员账号", new SpelColumnRender<DistributionRecordVO>("distributionCustomerVO.customerAccount")),
                new Column("支付时间", new SpelColumnRender<DistributionRecordVO>("payTime")),
                new Column("订单完成时间", new SpelColumnRender<DistributionRecordVO>("finishTime")),
                new Column("佣金入账时间", new SpelColumnRender<DistributionRecordVO>("missionReceivedTime")),
                new Column("金额", (cell, object) -> {
                    DistributionRecordVO distributionRecord = (DistributionRecordVO) object;
                    if (Objects.nonNull(distributionRecord.getOrderGoodsPrice())) {
                        cell.setCellValue(distributionRecord.getOrderGoodsPrice().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("数量", new SpelColumnRender<DistributionRecordVO>("orderGoodsCount")),
                new Column("佣金", (cell, object) -> {
                    DistributionRecordVO distributionRecord = (DistributionRecordVO) object;
                    if (Objects.nonNull(distributionRecord.getCommissionGoods())) {
                        cell.setCellValue(distributionRecord.getCommissionGoods().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                // 单个货品佣金比例 默认0%
                new Column("比例", (cell, object) -> {
                    DistributionRecordVO distributionRecord = (DistributionRecordVO) object;
                    if (Objects.nonNull(distributionRecord.getCommissionRate())) {
                        cell.setCellValue(distributionRecord.getCommissionRate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString() + "%");
                    } else {
                        cell.setCellValue("0%");
                    }
                }),
        };

        return columns;
    }

    /**
     * @param queryReq
     * @return
     * @description 获取sheet名
     * @author xuyunepng
     * @date 2021/6/7 4:21 下午
     */
    public String getSheetName(DistributionRecordExportRequest queryReq) {
        String sheetName = "分销记录";
        if (Objects.nonNull(queryReq.getRecordIdList())) {
            sheetName = "分销记录导出";
        } else if (Objects.nonNull(queryReq.getCommissionState())) {
            // 佣金已入账 佣金未入账
            sheetName = queryReq.getCommissionState().getValue();
        } else if (queryReq.getDeleteFlag().equals(DeleteFlag.YES)) {
            sheetName = "入账失败";
        }
        return sheetName;
    }
}
