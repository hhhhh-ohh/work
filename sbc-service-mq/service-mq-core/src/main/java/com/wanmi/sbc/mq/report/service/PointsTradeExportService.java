package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.PointsOrderType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.mq.report.entity.*;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.PointsTradeBaseService;
import com.wanmi.sbc.mq.report.service.base.TradeBaseService;
import com.wanmi.sbc.order.api.provider.pointstrade.PointsTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeProvider;
import com.wanmi.sbc.order.api.request.pointstrade.PointsTradeListExportRequest;
import com.wanmi.sbc.order.api.request.trade.FindProviderTradeRequest;
import com.wanmi.sbc.order.bean.dto.DisabledDTO;
import com.wanmi.sbc.order.bean.dto.PointsTradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuyunpeng
 * @className PointsTradeExportService
 * @description 积分订单导出
 * @date 2021/6/2 9:29 上午
 **/
@Service
@Slf4j
public class PointsTradeExportService implements ExportBaseService {

    @Autowired
    private PointsTradeQueryProvider pointsTradeQueryProvider;

    @Autowired
    private ProviderTradeProvider providerTradeProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private PointsTradeBaseService pointsTradeBaseService;

    private static final int SIZE = 10000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出积分订单_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("pointsTrade/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);

        DisabledExportRequest disabledExportRequest = JSON.parseObject(data.getDisabled(), DisabledExportRequest.class);
        PointsTradeExportRequest pointsTradeExportRequest = JSON.parseObject(data.getParam(), PointsTradeExportRequest.class);
        DisabledDTO disabledDTO = KsBeanUtil.convert(disabledExportRequest, DisabledDTO.class);
        if (StringUtils.isNotEmpty(data.getAdminId())) {
            pointsTradeExportRequest.setSupplierId(Long.valueOf(data.getAdminId()));
        }

        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        pointsTradeExportRequest.makeAllAuditFlow();
        PointsTradeQueryDTO pointsTradeQueryDTO = KsBeanUtil.convert(pointsTradeExportRequest, PointsTradeQueryDTO.class);
        PointsTradeListExportRequest exportRequest = PointsTradeListExportRequest.builder().
                pointsTradeQueryDTO(pointsTradeQueryDTO).build();
        Long total = pointsTradeQueryProvider.countPointsTradeExport(exportRequest).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = this.getColumns(Platform.PLATFORM.equals(data.getPlatform()));
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("积分订单导出", columns);

        //如果没有数据，直接生成空Excel
        if (total.equals(NumberUtils.LONG_ZERO)) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }

        exportRequest.getPointsTradeQueryDTO().setPageSize(SIZE);
        this.export(excelHelper, sheet, columns, exportRequest, fileSize, disabledDTO,data);

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    /**
     * @return
     * @description 导出
     * @author xuyunpeng
     * @date 2021/6/2 9:53 上午
     */
    public void export(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns,
                       PointsTradeListExportRequest exportRequest, long fileSize, DisabledDTO disabledDTO,ExportData data) {
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            exportRequest.getPointsTradeQueryDTO().setPageNum(i);
            List<PointsTradeVO> tradeVOS = pointsTradeBaseService.getPointsTrade(data.getOperator(),exportRequest, disabledDTO);
            //获取用户注销状态
            List<String> customerIds = tradeVOS.stream().map(v->v.getBuyer().getId()).collect(Collectors.toList());
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerIds);
            tradeVOS.forEach(v->{
                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(v.getBuyer().getId()))){
                    v.getBuyer().setAccount(v.getBuyer().getAccount()+ Constants.LOGGED_OUT);
                }
            });
            excelHelper.addSXSSFSheetRow(sheet, columns, tradeVOS, rowIndex + 1);
            rowIndex = rowIndex + tradeVOS.size();
        }
    }


    /**
     * @param existSupplier
     * @return
     * @description 获取表头
     * @author xuyunpeng
     * @date 2021/6/2 9:41 上午
     */
    public Column[] getColumns(boolean existSupplier) {
        Column[] columns = {
                new Column("订单编号", new SpelColumnRender<PointsTradeVO>("id")),
                new Column("下单时间", new SpelColumnRender<PointsTradeVO>("tradeState.createTime")),
                new Column("完成时间", new SpelColumnRender<PointsTradeVO>("tradeState.endTime != null ? tradeState.endTime : '-'")),
                new Column("客户名称", new SpelColumnRender<PointsTradeVO>("buyer.name")),
                new Column("客户账号", new SpelColumnRender<PointsTradeVO>("buyer.account")),
                new Column("客户级别", new SpelColumnRender<PointsTradeVO>("buyer.levelName")),
                new Column("收货人", (cell, object) -> {
                    PointsTradeVO trade = (PointsTradeVO) object;
                    if (trade.getPointsOrderType() == PointsOrderType.POINTS_COUPON) {
                        cell.setCellValue(trade.getBuyer().getName());
                    } else {
                        cell.setCellValue(trade.getConsignee().getName());
                    }
                }),
                new Column("收货人手机", (cell, object) -> {
                    PointsTradeVO trade = (PointsTradeVO) object;
                    if (trade.getPointsOrderType() == PointsOrderType.POINTS_COUPON) {
                        cell.setCellValue(trade.getBuyer().getPhone());
                    } else {
                        cell.setCellValue(trade.getConsignee().getPhone());
                    }
                }),
                new Column("收货人地址", new SpelColumnRender<PointsTradeVO>("consignee.detailAddress")),
                new Column("支付方式", (cell, object) -> {
                    PointsTradeVO trade = (PointsTradeVO) object;
                    String payTypeStr = "-";
                    String payTypeId = trade.getPayInfo().getPayTypeId();
                    if (String.valueOf(PayType.OFFLINE.toValue()).equals(payTypeId)) {
                        // 线下支付
                        payTypeStr = PayType.OFFLINE.getDesc();
                    } else if(String.valueOf(PayType.ONLINE.toValue()).equals(payTypeId)) {
                        // 线上支付
                        payTypeStr = "线上支付";
                    }
                    cell.setCellValue(payTypeStr);
                }),
                new Column("配送方式", new SpelColumnRender<PointsTradeVO>("deliverWay.getDesc()")),
                new Column("订单积分", new SpelColumnRender<PointsTradeVO>("tradePrice.points")),
                new Column("商品SKU", (cell, object) -> {
                    PointsTradeVO trade = (PointsTradeVO) object;
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    trade
                            .getTradeItems()
                            .forEach(i -> {
                                sb.append(i.getSkuNo()).append(',');
                            });
                    sb.trimToSize();
                    cell.setCellValue(sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "");
                }),
                new Column("商品种类", (cell, object) -> {
                    PointsTradeVO trade = (PointsTradeVO) object;
                    cell.setCellValue(trade.getPointsOrderType() != PointsOrderType.POINTS_COUPON ? trade.skuItemMap().size() : 1);
                }),
                new Column("商品总数量", (cell, object) -> {
                    Optional<Long> size = ((PointsTradeVO) object)
                            .getTradeItems()
                            .stream()
                            .map(TradeItemVO::getNum)
                            .reduce((sum, item) -> {
                                sum += item;
                                return sum;
                            });
                    cell.setCellValue(size.orElse(1L));
                }),
                new Column("买家备注", new SpelColumnRender<PointsTradeVO>("buyerRemark")),
                new Column("卖家备注", new SpelColumnRender<PointsTradeVO>("sellerRemark")),
                new Column("订单状态", (cell, object) -> {
                    PointsTradeVO trade = (PointsTradeVO) object;
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

                    cell.setCellValue(cellValue);
                }),
                new Column("付款状态", new SpelColumnRender<PointsTradeVO>("tradeState.payState.getDescription()")),
                new Column("发货状态", new SpelColumnRender<PointsTradeVO>("tradeState.deliverStatus.getDescription()")),
        };
        if (existSupplier) {
            List<Column> columnList = Stream.of(columns).collect(Collectors.toList());
            columnList.add(
                    new Column("商家", new SpelColumnRender<ReturnOrderVO>("supplier.supplierName"))
            );
            columns = columnList.toArray(new Column[0]);
        }
        return columns;
    }
}
