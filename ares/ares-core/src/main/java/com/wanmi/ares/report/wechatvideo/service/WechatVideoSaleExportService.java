package com.wanmi.ares.report.wechatvideo.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.report.wechatvideo.videotradeday.service.VideoTradeDayService;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.wechatvideo.VideoTradeDayView;
import com.wanmi.ares.view.wechatvideo.VideoView;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhaiqiankun
 * @className WechatVideoStatisticExportService
 * @description 视频号销售数据导出
 * @date 2022/4/11 23:21
 **/
@Service
@Slf4j
public class WechatVideoSaleExportService implements ExportBaseService {
    
    @Autowired private VideoTradeDayService videoTradeDayService;

    @Autowired private OsdService osdService;

    public static final int MAX_SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportQuery query) throws Exception {
        // 报表类型
        String exportName = "视频号天报表";
        log.info("{} export begin, param:{}", exportName, query);
        // 起止日期
        LocalDate begDate = DateUtil.parse2Date(query.getDateFrom(), DateUtil.FMT_DATE_1);
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1).plusDays(1);
        // 排序字段
        String sortOrder = query.getSortOrder();
        String sortName = query.getSortName();
        // 默认日期升序
        if (StringUtils.isAllBlank(sortOrder, sortName)) {
            sortOrder = "DESC";
            sortName = "date";
        }
        // 文件名称
        String fileName = String.format("导出" + exportName + "数据_%s.xlsx", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String resourceKey = String.format("wechatVideoStatistic/excel/%s", fileName);
        // 构造查询请求
        VideoQueryPageRequest pageRequest = new VideoQueryPageRequest();
        pageRequest.setPageSize(MAX_SIZE);
        pageRequest.setBeginDate(begDate);
        pageRequest.setEndDate(endDate);
        pageRequest.setSortOrder(sortOrder);
        pageRequest.setSortName(sortName);
        pageRequest.setCompanyInfoId(Long.valueOf(query.getCompanyId()));
        // 获取总数
        long totalCount = videoTradeDayService.count(pageRequest);
        // 总页数
        long fileSize = (long) Math.ceil(1.0 * totalCount / MAX_SIZE);
        // 写入表头
        ExcelHelper<VideoTradeDayView> excelHelper = new ExcelHelper<>();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead(exportName, columns);
        // 没有数据则生成空表
        if (totalCount == 0) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }
        // 分页查询、导出
        this.doExport(excelHelper, sheet, columns, fileSize, pageRequest);
        // 报表上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    public void doExport(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns, long fileSize, VideoQueryPageRequest pageRequest) {
        // 分页获取数据并写入
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            pageRequest.setPageNum(i);
            List<VideoTradeDayView> dataList = videoTradeDayService.getPage(pageRequest).getList();
            excelHelper.addSXSSFSheetRow(sheet, columns, dataList, rowIndex + 1);
            rowIndex = rowIndex + dataList.size();
        }
    }

    /**
     * 获取表头
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("日期", new SpelColumnRender<VideoView>("date")),
                new Column("视频号总销售金额", new SpelColumnRender<VideoView>("videoSaleAmount")),
                new Column("直播间销售金额", new SpelColumnRender<VideoView>("liveSaleAmount")),
                new Column("橱窗销售金额", new SpelColumnRender<VideoView>("shopwindowSaleAmount")),
                new Column("视频号退货金额", new SpelColumnRender<VideoView>("videoReturnAmount")),
                new Column("直播间退货金额", new SpelColumnRender<VideoView>("liveReturnAmount")),
                new Column("橱窗退货金额", new SpelColumnRender<VideoView>("shopwindowReturnAmount"))
        };
        return columns;
    }
}
