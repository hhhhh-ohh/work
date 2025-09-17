package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderPageRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderQueryRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderPageResponse;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsListRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.CommunityLeaderCommissionBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className communityLeaderCommissionBaseService
 * @description 社区团长佣金导出
 * @date 2021/6/7 2:34 下午
 **/
@Service
@Slf4j
public class CommunityLeaderCommissionExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private CommunityLeaderCommissionBaseService communityLeaderCommissionBaseService;

    public static final int SIZE = 5000;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private CommunityStatisticsQueryProvider communityStatisticsQueryProvider;


    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("communityleader export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出社区团长佣金记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("communityLeaderCommission/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("社区团长佣金导出", columns);

        CommunityLeaderQueryRequest queryReq = JSON.parseObject(data.getParam(), CommunityLeaderQueryRequest.class);
        queryReq.setIsCheck(Constants.yes);
        Long total = communityLeaderQueryProvider.countForExport(queryReq).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            CommunityLeaderPageRequest pageRequest = new CommunityLeaderPageRequest();
            KsBeanUtil.copyPropertiesThird(queryReq, pageRequest);

            //查询团长列表信息
            CommunityLeaderPageResponse response = communityLeaderCommissionBaseService.queryExport(data.getOperator(),pageRequest);

            //汇总团长id
            List<String> ids = response.getCommunityLeaderPage().stream().map(CommunityLeaderVO::getLeaderId).collect(Collectors.toList());

            //根据团长ids查询团长统计数据
            Map<String, CommunityStatisticsVO> statisticsVOMap = communityStatisticsQueryProvider.findByLeaderIdsGroup(
                    CommunityStatisticsListRequest.builder().idList(ids).build()).getContext().getCommunityStatisticsVOMap();

            //判断客户是否已注销
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(response.getCommunityLeaderPage().stream()
                    .map(CommunityLeaderVO::getCustomerId).collect(Collectors.toList()));

            response.getCommunityLeaderPage().forEach(dataRecord -> {
                CommunityStatisticsVO vo = statisticsVOMap.get(dataRecord.getLeaderId());
                if(null != vo){
                    dataRecord.setCommissionReceived(vo.getCommissionReceived());
                    dataRecord.setCommissionReceivedPickup(vo.getCommissionReceivedPickup());
                    dataRecord.setCommissionReceivedAssist(vo.getCommissionReceivedAssist());
                    dataRecord.setCommissionPending(vo.getCommissionPending());
                    dataRecord.setCommissionPendingPickup(vo.getCommissionPendingPickup());
                    dataRecord.setCommissionPendingAssist(vo.getCommissionPendingAssist());
                }

                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(dataRecord.getCustomerId()))){
                    dataRecord.setLeaderAccount(dataRecord.getLeaderAccount()+Constants.LOGGED_OUT);
                }
            });
            excelHelper.addSXSSFSheetRow(sheet, columns, response.getCommunityLeaderPage().getContent(), rowIndex + 1);
            rowIndex = rowIndex + response.getCommunityLeaderPage().getContent().size();
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
     * @date 2021/6/7 2:37 下午
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("社区团长名称", new SpelColumnRender<CommunityLeaderVO>("leaderName")),
                new Column("社区团长账号", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    // 账号脱敏
                    cell.setCellValue(SensitiveUtils.handlerMobilePhone(communityLeaderVO.getLeaderAccount()));
                }),
                new Column("加入时间", new SpelColumnRender<CommunityLeaderVO>("checkTime")),
                new Column("账号状态", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    if (communityLeaderVO.getCheckStatus().equals(LeaderCheckStatus.FORBADE)) {
                        cell.setCellValue("禁用");
                    } else if (communityLeaderVO.getCheckStatus().equals(LeaderCheckStatus.CHECKED)) {
                        cell.setCellValue("启用");
                    }
                }),
                new Column("已入账佣金总额", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    cell.setCellValue(communityLeaderVO.getCommissionReceived().toString());
                }),
                new Column("已入账自提服务佣金", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    cell.setCellValue(communityLeaderVO.getCommissionReceivedPickup().toString());
                }),
                new Column("已入账帮卖佣金", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    cell.setCellValue(communityLeaderVO.getCommissionReceivedAssist().toString());
                }),
                new Column("待入账佣金总额", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    cell.setCellValue(communityLeaderVO.getCommissionPending().toString());
                }),
                new Column("待入账自提服务佣金", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    cell.setCellValue(communityLeaderVO.getCommissionPendingPickup().toString());
                }),
                new Column("待入账帮卖佣金", (cell, object) -> {
                    CommunityLeaderVO communityLeaderVO = (CommunityLeaderVO) object;
                    cell.setCellValue(communityLeaderVO.getCommissionPendingAssist().toString());
                }),
        };
        return columns;
    }
}
