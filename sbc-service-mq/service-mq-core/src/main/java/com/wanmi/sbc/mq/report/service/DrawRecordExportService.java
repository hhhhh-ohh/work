package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.api.provider.drawrecord.DrawRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordPageRequest;
import com.wanmi.sbc.marketing.bean.vo.DrawRecordVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.DrawRecordFundsBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
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
 * @author huangzhao 抽奖记录导出
 */
@Service
@Slf4j
public class DrawRecordExportService implements ExportBaseService {

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private DrawRecordQueryProvider drawRecordQueryProvider;

    @Autowired
    private DrawRecordFundsBaseService drawRecordFundsBaseService;

    @Autowired
    private OsdService osdService;

    public static final int SIZE = 5000;


    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        DrawRecordPageRequest request = JSON.parseObject(data.getParam(), DrawRecordPageRequest.class);

        log.info("drawRecordPageRequest export begin, param:{}", JSON.toJSONString(request));

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出抽奖记录明细_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("drawRecord/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("抽奖记录明细", columns);

        request.putSort("id", "desc");

        //总数量
        Long total = drawRecordQueryProvider.total(request).getContext();

        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        request.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            request.setPageNum(i);
            List<DrawRecordVO> dataRecords = drawRecordFundsBaseService.query(data.getOperator(), request);
            getInfo(dataRecords);
            excelHelper.addSXSSFSheetRow(sheet, columns, dataRecords, rowIndex + 1);
            rowIndex = rowIndex + dataRecords.size();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    private void getInfo(List<DrawRecordVO> dataRecords) {
        //获取用户注销状态
        List<String> customerIds = dataRecords.stream().map(DrawRecordVO::getCustomerId).collect(Collectors.toList());
        Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerIds);
        dataRecords.forEach(v->{
            if(Objects.nonNull(v.getPrizeType())){
                v.setDeliverStatusName("-");
                switch (v.getPrizeType()){
                    case CUSTOMIZE:
                        v.setPrizeTypeName("自定义奖品");
                        break;
                    case COUPON:
                        v.setPrizeTypeName("优惠券奖品");
                        break;
                    case POINTS:
                        v.setPrizeTypeName("积分奖品");
                        break;
                    case GOODS:
                        v.setPrizeTypeName("实物奖品");
                        v.setDeliverStatusName(Objects.equals(Constants.ONE,v.getDeliverStatus())?"已发货":"未发货");
                        break;
                    default:
                }
            }
            v.setDrawStatusName(Objects.equals(Constants.ONE,v.getDrawStatus())?"中奖":"未中奖");
            v.setRedeemPrizeStatusName(Objects.equals(Constants.ONE,v.getRedeemPrizeStatus())?"已兑奖":"未兑奖");
            if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(v.getCustomerId()))){
                v.setCustomerAccount(v.getCustomerAccount()+ Constants.LOGGED_OUT);
            }
        });

    }


    private Column[] getColumns() {
        return new Column[]{
                new Column("会员名称", new SpelColumnRender<DrawRecordVO>("customerName")),
                new Column("会员登录账号", new SpelColumnRender<DrawRecordVO>("customerAccount")),
                new Column("活动名称", new SpelColumnRender<DrawRecordVO>("activityName")),
                new Column("抽奖时间", new SpelColumnRender<DrawRecordVO>("drawTime")),
                new Column("抽奖状态", new SpelColumnRender<DrawRecordVO>("drawStatusName")),
                new Column("奖品类型", new SpelColumnRender<DrawRecordVO>("prizeTypeName")),
                new Column("奖品名称", new SpelColumnRender<DrawRecordVO>("prizeName")),
                new Column("兑奖状态", new SpelColumnRender<DrawRecordVO>("redeemPrizeStatusName")),
                new Column("发货状态", new SpelColumnRender<DrawRecordVO>("deliverStatusName")),
                new Column("收货人", new SpelColumnRender<DrawRecordVO>("consigneeName")),
                new Column("收货人手机号码", new SpelColumnRender<DrawRecordVO>("consigneeNumber")),
                new Column("详细收货地址(包含省市区）", new SpelColumnRender<DrawRecordVO>("detailAddress")),
        };

    }
}
