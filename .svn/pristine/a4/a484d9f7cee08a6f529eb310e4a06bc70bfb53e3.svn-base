package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardExportRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardPageRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponByIdRequest;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicCardsExportService
 * @description 电子卡密导出
 * @date 2022/2/7 11:12 上午
 **/
@Slf4j
@Service
public class ElectronicCardsExportService implements ExportBaseService {

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    public static final int EXPORT_MAX_SIZE = 1000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("electronicCard export begin, param:{}", data);
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出电子卡券_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("electronicCards/excel/%s", fileName);

        ElectronicCardExportRequest electronicCardExportRequest = JSON.parseObject(data.getParam(), ElectronicCardExportRequest.class);
        String couponName = electronicCouponQueryProvider.getById(ElectronicCouponByIdRequest.builder().id(electronicCardExportRequest.getCouponId()).build())
                .getContext().getElectronicCouponVO().getCouponName();

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        String sheetName = "电子卡密导出";
        SXSSFSheet sxssfSheet = excelHelper.createSxssfSheet(sheetName);
        //设置第一行：卡券名称
        SXSSFRow headRow = sxssfSheet.createRow(0);
        SXSSFCell cellCoupon = headRow.createCell(0);
        cellCoupon.setCellValue("卡券名称");
        SXSSFCell cellCouponName = headRow.createCell(1);
        cellCouponName.setCellValue(couponName);
        //表头
        excelHelper.addSXSSFSheetHead(sxssfSheet,1,columns);

        Long total = electronicCardQueryProvider.countForExport(electronicCardExportRequest).getContext();
        ElectronicCardPageRequest pageRequest = KsBeanUtil.convert(electronicCardExportRequest, ElectronicCardPageRequest.class);
        //总页数
        long fileSize = ReportUtil.calPage(total, EXPORT_MAX_SIZE);
        //分页处理
        int rowIndex = 1;
        pageRequest.setPageSize(EXPORT_MAX_SIZE);
        pageRequest.setDelFlag(DeleteFlag.NO);
        for (int i = 0; i < fileSize; i++) {
            pageRequest.setPageNum(i);
            List<ElectronicCardVO> electronicCards = electronicCardQueryProvider.page(pageRequest)
                    .getContext().getElectronicCardVOPage().getContent();
            excelHelper.addSXSSFSheetRow(sxssfSheet, columns, electronicCards, rowIndex + 1);
            rowIndex = rowIndex + electronicCards.size();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * 获取表头
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("卡号", new SpelColumnRender<ElectronicCardVO>("cardNumber")),
                new Column("卡密", new SpelColumnRender<ElectronicCardVO>("cardPassword")),
                new Column("优惠码", new SpelColumnRender<ElectronicCardVO>("cardPromoCode")),
                new Column("状态", (cell, object) -> {
                    ElectronicCardVO electronicCardVO = (ElectronicCardVO) object;
                    String state = "";
                    switch (electronicCardVO.getCardState()) {
                        case 0:
                            state = "未发放";
                            break;
                        case 1:
                            state = "已发放";
                            break;
                        case 2:
                            state = "已失效";
                            break;
                        default:
                    }
                    cell.setCellValue(state);
                }),
                new Column("有效销售结束时间", (cell, object) -> {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    ElectronicCardVO electronicCardVO = (ElectronicCardVO) object;
                    cell.setCellValue(dtf.format(electronicCardVO.getSaleEndTime()));
                }),
        };
        return columns;
    }
}
