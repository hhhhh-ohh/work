package com.wanmi.ares.report.wechatvideo.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanyBase;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.wechatvideo.VideoView;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhaiqiankun
 * @className WechatVideoStatisticExportService
 * @description 视频号销售数据导出
 * @date 2022/4/11 23:21
 **/
@Service
@Slf4j
public class WechatVideoCompanyExportService implements ExportBaseService {

    @Autowired private TradeReportMapper tradeReportMapper;

    @Autowired private OsdService osdService;

    public static final int MAX_SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportQuery query) throws Exception {
        // 报表类型
        String exportName = "视频号店铺报表";
        log.info("{} export begin, param:{}", exportName, query);
        // 起止日期
        LocalDate begDate = DateUtil.parse2Date(query.getDateFrom(), DateUtil.FMT_DATE_1);
        LocalDate endDate = DateUtil.parse2Date(query.getDateTo(), DateUtil.FMT_DATE_1).plusDays(1);
        // 文件名称
        List<String> resourceKeyList = new ArrayList<>();
        String fileName = String.format("导出" + exportName + "数据_%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String resourceKey = String.format("wechatVideoStatistic/excel/%s", fileName);

        // 构造查询请求
        TradeCollect tradeCollect = new TradeCollect();
        tradeCollect.setBeginDate(begDate);
        tradeCollect.setEndDate(endDate);
        tradeCollect.setPageSize(MAX_SIZE);
        tradeCollect.setCompanyId("0".equals(query.getCompanyId()) ? null : query.getCompanyId());

        // 获取总数
        long totalCount = tradeReportMapper.countWechatTradeStatistics(tradeCollect);
        // 总页数
        long fileSize = (long) Math.ceil(1.0 * totalCount / MAX_SIZE);
        // 写入表头
        ExcelHelper<WechatVideoCompanyBase> excelHelper = new ExcelHelper<>();
        Column[] columns = getColumns();
        // 没有数据则生成空表
        if (totalCount == 0) {
            String newFileName = String.format("%s.xls", resourceKey);
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.addSheet(exportName, columns, Collections.emptyList());
            excelHelper.write(emptyStream);
            osdService.uploadExcel(emptyStream, newFileName);
            resourceKeyList.add(newFileName);
            return BaseResponse.success(resourceKeyList);
        }
        // 分页查询、导出
        for (int i = 0; i < fileSize; i++) {
            tradeCollect.setBeginIndex(i * tradeCollect.getPageSize());
            List<WechatVideoCompanyBase> dataList = tradeReportMapper.wechatTradeStatistics(tradeCollect);
            excelHelper = new ExcelHelper<>();
            excelHelper.addSheet(exportName, columns, dataList);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.write(byteArrayOutputStream);
            //如果超过一页，文件名后缀增加(索引值)
            String suffix = StringUtils.EMPTY;
            if (fileSize > 1) {
                suffix = "(".concat(String.valueOf(i + 1)).concat(")");
            }
            String newFileName = String.format("%s%s.xls", resourceKey, suffix);
            // 报表上传
            osdService.uploadExcel(byteArrayOutputStream, newFileName);
            resourceKeyList.add(newFileName);
        }
        return BaseResponse.success(resourceKeyList);
    }

    /**
     * 获取表头
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("店铺", new SpelColumnRender<VideoView>("storeName")),
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
