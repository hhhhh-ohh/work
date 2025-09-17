package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.*;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordPageVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class PayingMemberRecordExportService implements ExportBaseService {

    @Autowired
    private PayingMemberRecordQueryProvider payingMemberRecordQueryProvider;

    @Resource
    private OsdService osdService;

    private static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        PayingMemberRecordExportRequest queryReq = JSON.parseObject(data.getParam(), PayingMemberRecordExportRequest.class);
        queryReq.setDelFlag(DeleteFlag.NO);
        queryReq.putSort("recordId", "desc");

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("付费会员记录表列表_%s.xls", dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        String resourceKey = String.format("payingMemberRecord/excel/%s", fileName);

        Long total = payingMemberRecordQueryProvider.countForExport(queryReq).getContext();

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("付费会员记录表导出列表", columns);

        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            // 导出数据查询
            List<PayingMemberRecordPageVO> dataRecords = payingMemberRecordQueryProvider.exportPayingMemberRecordRecord(queryReq).getContext().getPayingMemberRecordList();
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
     * 导出列表数据具体实现
     */
    private Column[] getColumns() {
        Column[] columns = {
            new Column("会员等级", new SpelColumnRender<PayingMemberRecordVO>("levelName")),
            new Column("会员名称", new SpelColumnRender<PayingMemberRecordVO>("levelNickName")),
            new Column("付费金额", new SpelColumnRender<PayingMemberRecordVO>("payAmount")),
            new Column("客户名称", new SpelColumnRender<PayingMemberRecordVO>("customerName")),
            new Column("支付时间", new SpelColumnRender<PayingMemberRecordVO>("payTime")),
            new Column("会员到期时间", new SpelColumnRender<PayingMemberRecordVO>("expirationDate")),
            new Column("等级状态", (cell, object) -> {
                PayingMemberRecordVO payingMemberRecordVO = (PayingMemberRecordVO) object;
                Integer levelState = payingMemberRecordVO.getLevelState();
                String desc = "";
                switch (levelState) {
                    case 0:
                        desc = "生效中";
                        break;
                    case 1:
                        desc = "未生效";
                        break;
                    case 2:
                        desc = "已过期";
                        break;
                    case 3:
                        desc = "已退款";
                        break;
                    default:
                        break;
                }
                cell.setCellValue(desc);
            }),
            new Column("已优惠金额", new SpelColumnRender<PayingMemberRecordVO>("alreadyDiscountAmount")),
            new Column("已领积分", new SpelColumnRender<PayingMemberRecordVO>("alreadyReceivePoint")),
            new Column("退款金额", new SpelColumnRender<PayingMemberRecordVO>("returnAmount")),
            new Column("退款回收券", (cell, object) -> {
                    PayingMemberRecordVO payingMemberRecordVO = (PayingMemberRecordVO) object;
                    Integer returnCoupon = ObjectUtils.defaultIfNull(payingMemberRecordVO.getReturnCoupon(),Constants.MINUS_ONE);
                    String desc = "";
                    switch (returnCoupon) {
                        case 0:
                            desc = "是";
                            break;
                        case 1:
                            desc = "否";
                            break;
                        default:
                            break;
                    }
                    cell.setCellValue(desc);
            }),
            new Column("退款回收积分", (cell, object) -> {
                    PayingMemberRecordVO payingMemberRecordVO = (PayingMemberRecordVO) object;
                    Integer returnPoint =  ObjectUtils.defaultIfNull(payingMemberRecordVO.getReturnPoint(),Constants.MINUS_ONE);
                    String desc = "";
                    switch (returnPoint) {
                        case 0:
                            desc = "是";
                            break;
                        case 1:
                            desc = "否";
                            break;
                        default:
                            break;
                    }
                    cell.setCellValue(desc);
            })
        };
        return columns;
    }

}
