package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewExportRequest;
import com.wanmi.sbc.customer.bean.enums.RewardFlag;
import com.wanmi.sbc.customer.bean.enums.RewardRecordedFlag;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
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
 * @author xuyunpeng
 * @className InviteRecordExportService
 * @description 邀新记录导出
 * @date 2021/6/16 2:06 下午
 **/
@Service
@Slf4j
public class InviteRecordExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;

    private static final int SIZE = 5000;

    @Autowired
    private ExportUtilService exportUtilService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("inviteRecord export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出邀新记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("inviteRecord/excel/%s", fileName);

        DistributionInviteNewExportRequest queryReq = JSON.parseObject(data.getParam(), DistributionInviteNewExportRequest.class);
        Long total = distributionInviteNewQueryProvider.countForExport(queryReq).getContext();

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns(queryReq);
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead(queryReq.getIsRewardRecorded().getDesc(), columns);

        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            // 导出数据查询
            List<DistributionInviteNewForPageVO> dataRecords = distributionInviteNewQueryProvider.exportDistributionInviteNewRecord(queryReq).getContext().getRecordList();
            //判断客户是否已注销
            Map<String, LogOutStatus> invitedMap = exportUtilService.getLogOutStatus(dataRecords.stream()
                    .map(DistributionInviteNewForPageVO::getInvitedNewCustomerId).collect(Collectors.toList()));
            Map<String, LogOutStatus> requestMap = exportUtilService.getLogOutStatus(dataRecords.stream()
                    .map(DistributionInviteNewForPageVO::getRequestCustomerId).collect(Collectors.toList()));
            dataRecords.forEach(dataRecord -> {
                if (Objects.equals(LogOutStatus.LOGGED_OUT,invitedMap.get(dataRecord.getInvitedNewCustomerId()))){
                    dataRecord.setInvitedNewCustomerAccount(dataRecord.getInvitedNewCustomerAccount()+Constants.LOGGED_OUT);
                }
                if (Objects.equals(LogOutStatus.LOGGED_OUT,requestMap.get(dataRecord.getRequestCustomerId()))){
                    dataRecord.setRequestCustomerAccount(dataRecord.getRequestCustomerAccount()+Constants.LOGGED_OUT);
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
     * @date 2021/6/16 2:06 下午
     */
    public Column[] getColumns(DistributionInviteNewExportRequest queryReq) {
        Column[] columns = {
                new Column("受邀人名称", new SpelColumnRender<DistributionInviteNewForPageVO>("invitedNewCustomerName")),
                new Column("受邀人账号", (cell, object) -> {
                    DistributionInviteNewForPageVO distributionInviteNewForPageVO = (DistributionInviteNewForPageVO) object;
                    // 账号脱敏
                    cell.setCellValue(SensitiveUtils.handlerMobilePhone(distributionInviteNewForPageVO.getInvitedNewCustomerAccount()));
                }),
                new Column("邀请人名称", new SpelColumnRender<DistributionInviteNewForPageVO>("requestCustomerName")),
                new Column("邀请人账号", (cell, object) -> {
                    DistributionInviteNewForPageVO distributionInviteNewForPageVO = (DistributionInviteNewForPageVO) object;
                    // 账号脱敏
                    cell.setCellValue(SensitiveUtils.handlerMobilePhone(distributionInviteNewForPageVO.getRequestCustomerAccount()));
                }),
                new Column("有效邀新", (cell, object) -> {
                    DistributionInviteNewForPageVO distributionInviteNew = (DistributionInviteNewForPageVO) object;
                    cell.setCellValue(distributionInviteNew.getAvailableDistribution().getValue());
                }),
                new Column("注册时间", new SpelColumnRender<DistributionInviteNewForPageVO>("registerTime")),
                new Column("下单时间", new SpelColumnRender<DistributionInviteNewForPageVO>("firstOrderTime")),
                new Column("订单编号", new SpelColumnRender<DistributionInviteNewForPageVO>("orderCode")),
                new Column("订单完成时间", new SpelColumnRender<DistributionInviteNewForPageVO>("orderFinishTime")),
                new Column("奖励入账时间", new SpelColumnRender<DistributionInviteNewForPageVO>("rewardRecordedTime")),
        };

        Column[] isRewardRecordedColumns = {
                //奖励区分 奖励金额 优惠券
                new Column("奖励", (cell, object) -> {
                    DistributionInviteNewForPageVO distributionInviteNew = (DistributionInviteNewForPageVO) object;
                    if(Objects.nonNull(distributionInviteNew.getRewardFlag())){
                        if (distributionInviteNew.getRewardFlag().equals(RewardFlag.CASH)) {
                            // 现金
                            cell.setCellValue(distributionInviteNew.getRewardCash().toString());
                        } else if(distributionInviteNew.getRewardFlag().equals(RewardFlag.COUPON)) {
                            //优惠券
                            cell.setCellValue(distributionInviteNew.getRewardCoupon());
                        }
                    }
                }),
        };

        Column[] isNotRewardRecordedColumns = {
                //未入账奖励区分 奖励金额 优惠券
                new Column("未入账奖励", (cell, object) -> {
                    DistributionInviteNewForPageVO distributionInviteNew = (DistributionInviteNewForPageVO) object;
                    if(Objects.nonNull(distributionInviteNew.getRewardFlag())){
                        if (distributionInviteNew.getRewardFlag().equals(RewardFlag.CASH)) {
                            // 现金
                            cell.setCellValue(distributionInviteNew.getSettingAmount().toString());
                        } else if(distributionInviteNew.getRewardFlag().equals(RewardFlag.COUPON)) {
                            //优惠券
                            cell.setCellValue(distributionInviteNew.getSettingCoupons());
                        }
                    }
                }),
                new Column("未入账原因", (cell, object) -> {
                    DistributionInviteNewForPageVO distributionInviteNew = (DistributionInviteNewForPageVO) object;
                    if(Objects.nonNull(distributionInviteNew.getFailReasonFlag())) {
                        // 0:非有效邀新 1：奖励达到上限 2：奖励未开启
                        cell.setCellValue(distributionInviteNew.getFailReasonFlag().getDesc());
                    }
                }),
        };
        Column[] newColumns={};
        if(queryReq.getIsRewardRecorded().equals(RewardRecordedFlag.YES)){
            newColumns =  ArrayUtils.addAll(columns,isRewardRecordedColumns);
        }else if(queryReq.getIsRewardRecorded().equals(RewardRecordedFlag.NO)){
            newColumns =  ArrayUtils.addAll(columns,isNotRewardRecordedColumns);
        }
        return newColumns;
    }
}
