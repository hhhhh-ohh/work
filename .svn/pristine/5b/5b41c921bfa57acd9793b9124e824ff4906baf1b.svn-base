package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.empower.bean.enums.WxSceneGroup;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.mq.report.entity.DisabledExportRequest;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.entity.TradeExportRequest;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.TradeBaseService;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListExportRequest;
import com.wanmi.sbc.order.bean.dto.DisabledDTO;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuyunpeng
 * @className TradeExportService
 * @description 订单导出
 * @date 2021/6/1 10:43 上午
 **/
@Service
@Slf4j
public class TradeExportService implements ExportBaseService {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private ProviderTradeQueryProvider providerTradeQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private TradeBaseService tradeBaseService;


    private static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出订单_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("trade/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);
        TradeExportRequest tradeExportRequest = JSON.parseObject(data.getParam(), TradeExportRequest.class);
        // 标识Map，用于生成不同的columns
        Map<String, Object> typeMap = new HashMap<>();
        // 标识代销平台
        typeMap.put("sellPlatformType", tradeExportRequest.getSellPlatformType());
        boolean o2O = tradeExportRequest.isO2O();
        if(o2O) {
            tradeExportRequest.setStoreType(StoreType.O2O);
        }
        //boss/supplier是否只导出子订单
        Boolean isOnlyForProvider = Boolean.FALSE;
        Long total;
        TradeListExportRequest exportRequest;
        if(data.getPlatform() == Platform.PROVIDER) {
            exportRequest = getProvderTradeRequest(data,tradeExportRequest);
            total = providerTradeQueryProvider.countProviderTradeExport(exportRequest).getContext();
        } else {
            exportRequest = getTradeRequest(data,tradeExportRequest);
            total = tradeQueryProvider.countTradeExport(exportRequest).getContext();
        }
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        //写入表头
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns;
        if(data.getPlatform() == Platform.PROVIDER) {
            columns = this.getProviderColumns(data.getPlatform(), data.getBuyAnyThirdChannelOrNot(), typeMap);
        } else {
            DisabledExportRequest disabledExportRequest =JSON.parseObject(data.getDisabled(), DisabledExportRequest.class);
            DisabledDTO disabledDTO=KsBeanUtil.convert(disabledExportRequest,DisabledDTO.class);
            isOnlyForProvider = disabledDTO.getDisabled().equals("true");
            columns = isOnlyForProvider
                    ? this.getProviderColumns(data.getPlatform(), data.getBuyAnyThirdChannelOrNot(), typeMap)
                    : this.getColumns(data.getPlatform() == Platform.PLATFORM,o2O, typeMap);
        }
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("订单导出", columns);

        //如果没有数据，直接生成空Excel
        if (total.equals(NumberUtils.LONG_ZERO)) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }

        //分页查询、导出
        exportRequest.getTradeQueryDTO().setPageSize(SIZE);

        try {
            if(data.getPlatform() == Platform.PROVIDER || isOnlyForProvider) {
                exportProviderTrade(excelHelper, sheet, columns, data, fileSize, exportRequest);
            } else {
                exportTrade(excelHelper, sheet, columns, fileSize, exportRequest, data);
            }
        } catch (Exception e) {
            excelHelper.deleteTempFile();
            throw e;
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * @description 获取订单数量（商家/平台）
     * @author  xuyunpeng
     * @date 2021/6/1 6:46 下午
     * @param data
     * @return
     */
    public TradeListExportRequest getTradeRequest(ExportData data,TradeExportRequest tradeExportRequest) {

        if (StringUtils.isNotEmpty(data.getAdminId())) {
            tradeExportRequest.setSupplierId(Long.valueOf(data.getAdminId()));
        }

        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeExportRequest.makeAllAuditFlow();
        TradeQueryDTO tradeQueryDTO = KsBeanUtil.convert(tradeExportRequest, TradeQueryDTO.class);
        TradeListExportRequest exportRequest = TradeListExportRequest.builder().tradeQueryDTO(tradeQueryDTO).build();
        return exportRequest;
    }

    /**
     * @description 获取供应商订单数量
     * @author  xuyunpeng
     * @date 2021/6/1 6:46 下午
     * @param data
     * @return
     */
    public TradeListExportRequest getProvderTradeRequest(ExportData data,TradeExportRequest tradeExportRequest) {
        if (StringUtils.isNotEmpty(data.getAdminId())) {
            tradeExportRequest.setSupplierId(Long.valueOf(data.getAdminId()));
        }
        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeExportRequest.makeAllAuditFlow();
        TradeQueryDTO tradeQueryDTO = KsBeanUtil.convert(tradeExportRequest, TradeQueryDTO.class);
        TradeListExportRequest request = TradeListExportRequest.builder().tradeQueryDTO(tradeQueryDTO).build();
        return request;
    }

    /**
     * @description 导出trade
     * @author  xuyupeng
     * @date 2021/6/1 2:45 下午
     * @return
     */
    public void exportTrade(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns, long fileSize, TradeListExportRequest exportRequest, ExportData data) throws Exception {
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            exportRequest.getTradeQueryDTO().setPageNum(i);
            List<TradeVO> tradeVOS = tradeBaseService.getTrade(data.getOperator(), exportRequest);
            //获取用户注销状态
            List<String> customerIds = tradeVOS.stream().map(v->v.getBuyer().getId()).collect(Collectors.toList());
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(customerIds);
            tradeVOS.forEach(v->{
                v.setDeliverWayName(Objects.nonNull(v.getDeliverWay())?
                        v.getDeliverWay().getDesc():"");
                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(v.getBuyer().getId()))){
                    v.getBuyer().setAccount(v.getBuyer().getAccount()+ Constants.LOGGED_OUT);
                }
            });
            // 表格需要合并的位置
            int[] index = {0,1,2,3,4,5,6,7,8,9,10,18};
            addSXSSFSheetRowForTrade(sheet, columns, tradeVOS, rowIndex + 1, index);
            rowIndex = rowIndex + tradeVOS.size();
        }
    }

    /**
     * 分页导出Excel
     * @author  xufeng
     * @date 2021/12/15 10:37 下午
     * @param sheet
     * @param columns
     * @param dataList
     * @param rowIndex
     * @return void
     **/
    public void addSXSSFSheetRowForTrade(SXSSFSheet sheet, Column[] columns, List<TradeVO> dataList,int rowIndex,
                                         int[] ints) {
        for (TradeVO data : dataList) {
            int cellIndex = 0;
            SXSSFRow row = sheet.createRow(rowIndex);
            for (Column column : columns) {
                SXSSFCell cell = row.createCell(cellIndex++);
                column.getRender().render(cell, data);
            }

            // 合并单元格
            int lastRow = data.getRowNum();
            if (lastRow > Constants.ONE){
                for (int i : ints) {
                    CellRangeAddress region = new CellRangeAddress(rowIndex,rowIndex+lastRow-1,i,i);
                    sheet.addMergedRegion(region);
                }
            }
            rowIndex++;
        }
    }

    /**
     * @description 导出provider类型订单
     * @author  xuyupeng
     * @date 2021/6/1 2:45 下午
     * @return
     */
    public void exportProviderTrade(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns, ExportData data, long fileSize, TradeListExportRequest exportRequest) {
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            exportRequest.getTradeQueryDTO().setPageNum(i);
            List<ProviderTradeExportVO> tradeVOS =
                    data.getPlatform() == Platform.PROVIDER ?
                            tradeBaseService.getTradeForProvider(data.getOperator(), exportRequest) :
                            tradeBaseService.getProviderTrade(data.getOperator(),data, exportRequest);
            tradeVOS.forEach(vo->vo.setDeliverWayName(vo.getDeliverWay().getDesc()));
            excelHelper.addSXSSFSheetRow(sheet, columns, tradeVOS, rowIndex + 1);
            // 表格需要合并的位置
            int[] index = {0,1,2,3,4,5,6,7,8,9,10,18};
            rowIndex = rowIndex + tradeVOS.size();
        }
    }

    /**
     * @description
     * @author  主订单报表表头
     * @date 2021/6/1 2:06 下午
     * @param existSupplier
     * @return
     */
    public Column[] getColumns(boolean existSupplier,boolean isO2O, Map<String, Object> typeMap) {
        Column[] columns = {
                new Column("订单编号", new SpelColumnRender<TradeVO>("id")),
                new Column("下单时间", new SpelColumnRender<TradeVO>("tradeState.createTime")),
//                new Column("完成时间", new SpelColumnRender<TradeVO>("tradeState.endTime != null && tradeState.flowState =='COMPLETED' ? tradeState.endTime : '-'")),
                new Column("完成时间", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    FlowState flowState=trade.getTradeState().getFlowState();
                    if (Objects.nonNull(trade.getTradeState().getEndTime()) ){
                        if(flowState.equals(FlowState.COMPLETED)){
                            cell.setCellValue(trade.getTradeState().getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }else{
                            cell.setCellValue("-");
                        }
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("客户名称", new SpelColumnRender<TradeVO>("buyer.name")),
                new Column("客户账号", new SpelColumnRender<TradeVO>("buyer.account")),
                new Column("客户级别", new SpelColumnRender<TradeVO>("buyer.levelName")),
                new Column("收货人", new SpelColumnRender<TradeVO>("consignee.name")),
                new Column("收货人手机", new SpelColumnRender<TradeVO>("consignee.phone")),
                new Column("收货人地址", new SpelColumnRender<TradeVO>("consignee.detailAddress")),
                new Column("订单商品金额", new SpelColumnRender<TradeVO>("tradePrice.goodsPrice")),
                new Column("订单改价金额", new SpelColumnRender<TradeVO>("tradePrice.special ? tradePrice.privilegePrice : '0.00'")),
                new Column("订单应付金额", new SpelColumnRender<TradeVO>("tradePrice.totalPrice")),
                new Column("交易流水号", new SpelColumnRender<TradeVO>("tradeNo")),
                new Column("支付方式", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String payTypeStr = "-";
                    String payTypeId = trade.getPayInfo().getPayTypeId();
                    if (String.valueOf(PayType.OFFLINE.toValue()).equals(payTypeId)) {
                        // 线下支付
                        payTypeStr = PayType.OFFLINE.getDesc();
                    } else if(String.valueOf(PayType.ONLINE.toValue()).equals(payTypeId)) {
                        // 线上支付
                        payTypeStr = PayType.ONLINE.getDesc();
                    }
                    cell.setCellValue(payTypeStr);
                }),
                new Column("支付渠道", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    StringBuilder sb = new StringBuilder();

                    if (trade.getBookingType() == BookingType.EARNEST_MONEY){
                        sb.append(trade.getPayWayValue());
                    }else {
                        //纯积分需要显示 不需要显示线上还是线下
                        //线下、积分+线下需要显示
                        //积分+线上 需要判断是否付款 未付款的不显示
                        PayState payState = trade.getTradeState().getPayState();

                        String payTypeId = trade.getPayInfo().getPayTypeId();
                        Long points = trade.getTradePrice().getPoints();
                        Boolean isZero = trade.getTradePrice().getTotalPrice().compareTo(BigDecimal.ZERO) < 1;

                        if (payState == PayState.PAID_EARNEST){
                            isZero = false;
                        }

                        if(!isZero){
                            if(String.valueOf(PayType.OFFLINE.toValue()).equals(payTypeId)){
                                //线下支付
                                sb.append("-");
                            }else if(String.valueOf(PayType.ONLINE.toValue()).equals(payTypeId)){
                                //线上支付
                                if(Objects.nonNull(trade.getPayWay())){
                                    sb.append(trade.getPayWay().getDesc());
                                }
                            }
                        }

                        if(payState.equals(PayState.NOT_PAID) && !isZero){
                            sb = new StringBuilder("-");
                        }

                        if (Objects.isNull(trade.getPayWay()) || isZero) {
                            sb = new StringBuilder("-");
                        }
                    }

                    cell.setCellValue(sb.toString());
                }),
                new Column("抵扣方式", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    trade
                            .getTradeItems()
                            .forEach(i -> {
                                        String desc = "";
                                        if (Objects.nonNull(i.getPointsPrice()) && i.getPointsPrice().compareTo(BigDecimal.ZERO) > 0) {
                                            desc = "积分";
                                        }
                                        if (CollectionUtils.isNotEmpty(i.getGiftCardItemList())) {
                                            BigDecimal giftCardPrice = i.getGiftCardItemList().stream().map(v->Objects.isNull(v.getPrice())?BigDecimal.ZERO : v.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                            if (Objects.nonNull(giftCardPrice) && giftCardPrice.compareTo(BigDecimal.ZERO) > 0) {
                                                if (StringUtils.isBlank(desc)) {
                                                    if (GiftCardType.PICKUP_CARD.equals(i.getGiftCardItemList().get(0).getGiftCardType())){
                                                        desc = "礼品卡-提货卡";
                                                    } else {
                                                        desc = "礼品卡-现金卡";
                                                    }
                                                } else {
                                                    if (GiftCardType.PICKUP_CARD.equals(i.getGiftCardItemList().get(0).getGiftCardType())){
                                                        desc = "礼品卡-提货卡";
                                                    } else {
                                                        desc = "积分/礼品卡-现金卡";
                                                    }
                                                }
                                            }
                                        }
                                        if (StringUtils.isBlank(desc)) {
                                            desc = "-";
                                        }
                                        sb.append(desc).append(',');
                                    }
                            );
                    sb.trimToSize();
                    cell.setCellValue(sb.substring(0, sb.length() - 1));
                }),
                new Column("礼品卡-现金卡抵扣", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
                        cell.setCellValue("-");
                        return;
                    }
                    trade
                            .getTradeItems()
                            .forEach(i -> {
                                        if (CollectionUtils.isNotEmpty(i.getGiftCardItemList())) {
                                            BigDecimal giftCardPrice = i.getGiftCardItemList().stream().map(v-> Objects.isNull(v.getPrice())?BigDecimal.ZERO:v.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                                            if (giftCardPrice == null || giftCardPrice.compareTo(BigDecimal.ZERO)== 0) {
                                                sb.append("-").append(',');

                                            } else {
                                                DecimalFormat df1 = new DecimalFormat("0.00");
                                                sb.append(df1.format(giftCardPrice)).append(',');
                                            }
                                        } else {
                                            sb.append("-").append(',');
                                        }
                                    }
                            );
                    sb.trimToSize();
                    cell.setCellValue(sb.substring(0, sb.length() - 1));
                }),
                new Column("礼品卡-提货卡抵扣", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String v = "-";
                    if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
                        v = trade.getTradePrice().getGiftCardPrice().toString();
                    }
                    cell.setCellValue(v);
                }),

                new Column("积分抵扣", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
                        cell.setCellValue("-");
                        return;
                    }
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    trade
                            .getTradeItems()
                            .forEach(i ->
                                    sb.append(i.getPointsPrice() == null || i.getPointsPrice().compareTo(BigDecimal.ZERO)== 0.00 ? "-" : i.getPointsPrice()).append(',')
                            );
                    sb.trimToSize();
                    cell.setCellValue(sb.substring(0, sb.length() - 1));
                }),

                new Column("积分", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
                        cell.setCellValue("-");
                        return;
                    }
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    trade
                            .getTradeItems()
                            .forEach(i ->
                                    sb.append(i.getPoints() == null || i.getPoints().longValue() == 0 ? "-" : i.getPoints()).append(',')
                            );
                    sb.trimToSize();
                    cell.setCellValue(sb.substring(0, sb.length() - 1));
                }),
                new Column("配送方式", new SpelColumnRender<ProviderTradeExportVO>("deliverWayName")),
                new Column("配送费用", new SpelColumnRender<TradeVO>("tradePrice.deliveryPrice != null ? tradePrice.deliveryPrice : '0.00'")),
                new Column("商品名称", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String skuName = trade
                            .getTradeItems()
                            .stream()
                            .map(TradeItemVO::getSkuName)
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining(","));
                    cell.setCellValue(skuName);
                }),
                new Column("商品类型", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    OrderTagVO orderTagVO = trade.getOrderTag();
                    if(Objects.nonNull(orderTagVO)){
                        if (orderTagVO.getVirtualFlag()){
                            cell.setCellValue("虚拟商品");
                        } else if (orderTagVO.getElectronicCouponFlag()){
                            cell.setCellValue("电子卡券");
                        } else {
                            cell.setCellValue("实物商品");
                        }
                    }
                }),
                new Column("供货价", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    trade
                            .getTradeItems()
                            .forEach(i ->
                                    sb.append(i.getSupplyPrice() == null ? "-" : i.getSupplyPrice()).append(',')
                            );
                    sb.trimToSize();
                    cell.setCellValue(sb.substring(0, sb.length() - 1));
                }),
                new Column("供货商", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String providerName=trade
                            .getTradeItems()
                            .stream()
                            .filter(item -> Objects.nonNull(item.getProviderId()))
                            .map(TradeItemVO::getProviderName)
                            .collect(Collectors.joining(","));
                    if (StringUtils.isBlank(providerName)){
                        providerName="-";
                    }
                    cell.setCellValue(providerName);
                }),
                new Column("商品规格", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String specDetails = trade
                            .getTradeItems()
                            .stream()
                            .map(TradeItemVO::getSpecDetails)
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining(","));
                    cell.setCellValue(specDetails);
                }),
                new Column("商品SKU", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    StringBuilder sb = new StringBuilder(trade.getTradeItems().size() * 32);
                    trade
                            .getTradeItems()
                            .forEach(i -> {
                                sb.append(i.getSkuNo()).append(',');
                            });
                    sb.trimToSize();
                    cell.setCellValue(sb.substring(0, sb.length() - 1));
                }),
                new Column("商品种类", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    cell.setCellValue(trade.skuItemMap().size());
                }),
                new Column("商品总数量", (cell, object) -> {
                    Optional<Long> size = ((TradeVO) object)
                            .getTradeItems()
                            .stream()
                            .map(TradeItemVO::getNum)
                            .reduce((sum, item) -> {
                                sum += item;
                                return sum;
                            });
                    cell.setCellValue((double) size.get());
                }),
                new Column("买家备注", new SpelColumnRender<TradeVO>("buyerRemark")),
                new Column("卖家备注", new SpelColumnRender<TradeVO>("sellerRemark")),
                new Column("订单状态", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
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
                    if (StringUtils.isBlank(cellValue) && Objects.equals(PayState.PAID_EARNEST, trade.getTradeState().getPayState())) {
                        cellValue = "待支付尾款";
                    }
                    cell.setCellValue(cellValue);
                }),
                new Column("付款状态", new SpelColumnRender<TradeVO>("tradeState.payState.getDescription()")),
                new Column("发货状态", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String deliverStatuses = trade
                            .getTradeItems()
                            .stream()
                            .map(TradeItemVO::getDeliverStatus)
                            .filter(Objects::nonNull)
                            .map(DeliverStatus::getDescription)
                            .collect(Collectors.joining(","));
                    cell.setCellValue(deliverStatuses);
                }),
                new Column("发票类型", new SpelColumnRender<TradeVO>("invoice.type == 0 ? '普通发票' : invoice.type == 1 ?'增值税发票':'不需要发票' ")),
                new Column("开票项目", new SpelColumnRender<TradeVO>("invoice.projectName")),
                new Column("发票抬头", new SpelColumnRender<TradeVO>("invoice.type == 0 ? invoice.generalInvoice.title:" +
                        "invoice.specialInvoice.companyName")),
                new Column("订单类型", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String cellValue;
                    Optional<TradeItemVO> optional = trade.getTradeItems().stream()
                            .filter(tradeItemVO -> tradeItemVO.getBuyPoint() != null && tradeItemVO.getBuyPoint() > 0)
                            .findAny();
                    if (Boolean.TRUE.equals(trade.getGrouponFlag())) {
                        cellValue = "拼团订单";
                    } else if (Boolean.TRUE.equals(trade.getIsFlashSaleGoods())) {
                        cellValue = "秒杀订单";
                    } else if (Boolean.TRUE.equals(trade.getIsBookingSaleGoods())) {
                        cellValue = "预售订单";
                    } else if (OrderType.NORMAL_ORDER.equals(trade.getOrderType()) && optional.isPresent()) {
                        cellValue = "积分购买订单";
                    } else if (Boolean.TRUE.equals(trade.getBargain())) {
                        cellValue = "砍价订单";
                    } else if (trade.getOrderTag() != null && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag())) {
                        cellValue = "周期购订单";
                    } else {
                        cellValue = "-";
                    }
                    cell.setCellValue(cellValue);
                }),
                new Column("计划发货时间", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    TradeStateVO tradeState = trade.getTradeState();
                    OrderTagVO orderTag = trade.getOrderTag();
                    String cellValue = "-";
                    if (orderTag != null && Boolean.TRUE.equals(orderTag.getBuyCycleFlag())
                            && !FlowState.VOID.equals(tradeState.getFlowState())
                            && (DeliverStatus.NOT_YET_SHIPPED == tradeState.getDeliverStatus() || DeliverStatus.PART_SHIPPED.equals(tradeState.getDeliverStatus()))
                            && trade.getTradeBuyCycle().getBuyCycleNextPlanDate() != null) {
                        cellValue = trade.getTradeBuyCycle().getBuyCycleNextPlanDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
                    }
                    cell.setCellValue(cellValue);
                }),
        };
        List<Column> columnList = Stream.of(columns).collect(Collectors.toList());
        if (existSupplier) {
            if(isO2O){
                columnList.add(
                        new Column("门店名称", new SpelColumnRender<ReturnOrderVO>("supplier.storeName"))
                );
            }else{
                columnList.add(
                        new Column("商家", new SpelColumnRender<ReturnOrderVO>("supplier.supplierName"))
                );
            }
        }
        // 获取代销类型
        SellPlatformType sellPlatformType = (SellPlatformType) typeMap.get("sellPlatformType");
        if (sellPlatformType == SellPlatformType.WECHAT_VIDEO) {
            // 处理视频号订单
            columnList.add(new Column("带货视频号", new SpelColumnRender<TradeVO>("videoUser.videoName")));
            columnList.add(new Column("下单场景", (cell, object) -> {
                TradeVO trade = (TradeVO) object;
                String sceneGroupStr = Optional.ofNullable(WxSceneGroup.fromValue(trade.getSceneGroup()))
                        .map(WxSceneGroup::toName).orElse("");
                cell.setCellValue(sceneGroupStr);
            }));
        }
        columns = columnList.toArray(new Column[0]);
        return columns;
    }

    /**
     * @description 子订单报表表头
     * @author  xuyunpeng
     * @date 2021/6/1 2:03 下午
     * @param platform
     * @param flag
     * @return
     */
    public Column[] getProviderColumns(Platform platform, boolean flag, Map<String, Object> typeMap) {
        List<Column> columnList = new ArrayList<>(20);
        Column[] columns;

        if(platform.equals(Platform.PLATFORM)){
            columnList.add(new Column("订单编号", new SpelColumnRender<ProviderTradeExportVO>("parentId")));
            columnList.add(new Column("子单号", new SpelColumnRender<ProviderTradeExportVO>("id")));

            if (flag) {
                columnList.add(new Column("渠道订单号", new SpelColumnRender<ProviderTradeExportVO>(
                        "thirdPlatformOrderId")));
                columnList.add(new Column("渠道子单号", new SpelColumnRender<ProviderTradeExportVO>("outOrderId")));
            }
            columnList.add(new Column("下单时间", new SpelColumnRender<ProviderTradeExportVO>("createTime")));
            columnList.add(new Column("完成时间", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                if (FlowState.COMPLETED.equals(exportTrade.getFlowState())) {
                    cell.setCellValue(DateUtil.format(exportTrade.getEndTime(), DateUtil.FMT_TIME_1));
                } else {
                    cell.setCellValue("-");
                }
            }));
            columnList.add(new Column("商家", new SpelColumnRender<ProviderTradeExportVO>("supplierInfo")));
            columnList.add(new Column("收货人", new SpelColumnRender<ProviderTradeExportVO>("consigneeName")));
            columnList.add(new Column("收货人手机", new SpelColumnRender<ProviderTradeExportVO>("consigneePhone")));
            columnList.add(new Column("收货人地址", new SpelColumnRender<ProviderTradeExportVO>("detailAddress")));
            columnList.add(new Column("抵扣方式", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                String cellValue = "";
                if (Objects.nonNull(exportTrade.getPointsPrice())) {
                    cellValue = "积分";
                }
                if (Objects.nonNull(exportTrade.getGiftCardPrice())) {
                    if (StringUtils.isNotEmpty(cellValue)) {
                        if (Objects.nonNull(exportTrade.getOrderTag()) && Objects.nonNull(exportTrade.getOrderTag().getPickupCardFlag()) && exportTrade.getOrderTag().getPickupCardFlag()){
                            cellValue = "礼品卡-提货卡";
                        } else {
                            cellValue = "积分/礼品卡-现金卡";
                        }
                    } else {
                        if (Objects.nonNull(exportTrade.getOrderTag()) && Objects.nonNull(exportTrade.getOrderTag().getPickupCardFlag()) && exportTrade.getOrderTag().getPickupCardFlag()){
                            cellValue = "礼品卡-提货卡";
                        } else {
                            cellValue = "礼品卡-现金卡";
                        }
                    }
                }
                cell.setCellValue(cellValue);
            }));
            columnList.add(new Column("礼品卡-现金卡抵扣", new SpelColumnRender<ProviderTradeExportVO>("giftCardPrice != " +
                    "null  ? giftCardPrice : '-' ")));
//            columnList.add(new Column("礼品卡-提货卡抵扣", (cell, object) -> {
//                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                String v = "-";
//                if (exportTrade.getOrderTag().getPickupCardFlag()){
//                    v = exportTrade.getGiftCardPrice().toString();
//                }
//                cell.setCellValue(v);
//            }));
            columnList.add(new Column("积分抵扣", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                String v = "-";
                if (Objects.isNull(exportTrade.getOrderTag()) || Objects.isNull(exportTrade.getOrderTag().getPickupCardFlag()) || !exportTrade.getOrderTag().getPickupCardFlag()){
                    if(exportTrade.getPointsPrice() != null) {
                        v = exportTrade.getPointsPrice().toString();
                    }
                }
                cell.setCellValue(v);
            }));
            columnList.add(new Column("积分", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                String v = "-";
                if (Objects.isNull(exportTrade.getOrderTag()) || Objects.isNull(exportTrade.getOrderTag().getPickupCardFlag()) || !exportTrade.getOrderTag().getPickupCardFlag()){
                    if(Objects.nonNull(exportTrade.getPoints())){
                        v = exportTrade.getPoints().toString();
                    }
                }
                cell.setCellValue(v);
            }));
            columnList.add(new Column("配送方式", new SpelColumnRender<ProviderTradeExportVO>("deliverWayName")));
            columnList.add(new Column("订单商品金额", new SpelColumnRender<ProviderTradeExportVO>("orderGoodsPrice")));
            columnList.add(new Column("商品名称", new SpelColumnRender<ProviderTradeExportVO>("skuName")));
            columnList.add(new Column("供货价", new SpelColumnRender<ProviderTradeExportVO>("SupplyPrice")));
            columnList.add(new Column("供货商", new SpelColumnRender<ProviderTradeExportVO>("providerName")));
            // 供应商只有实物商品
            columnList.add(new Column("商品类型", (cell, object) -> cell.setCellValue("实物商品")));
            columnList.add(new Column("商品规格", new SpelColumnRender<ProviderTradeExportVO>("specDetails")));
            columnList.add(new Column("SKU编码", new SpelColumnRender<ProviderTradeExportVO>("skuNo")));

            columnList.add(new Column("购买数量", new SpelColumnRender<ProviderTradeExportVO>("num")));
            columnList.add(new Column("商品种类", new SpelColumnRender<ProviderTradeExportVO>("goodsSpecies")));
            columnList.add(new Column("商品总数量", new SpelColumnRender<ProviderTradeExportVO>("totalNum")));

            columnList.add(new Column("买家备注", new SpelColumnRender<ProviderTradeExportVO>("buyerRemark")));
            columnList.add(new Column("订单状态", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                FlowState flowState = exportTrade.getFlowState();
                if (Objects.nonNull(flowState)) {
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
                }
            }));
            columnList.add(new Column("发货状态", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                if (Objects.nonNull(exportTrade.getDeliverStatus())) {
                    cell.setCellValue(exportTrade.getDeliverStatus().getDescription());
                }
            }));
            columnList.add(new Column("付款状态", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                if (Objects.nonNull(exportTrade.getPayState())) {
                    cell.setCellValue(exportTrade.getPayState().getDescription());
                }
            }));
            columnList.add(new Column("订单类型", (cell, object) -> {
                ProviderTradeExportVO trade = (ProviderTradeExportVO) object;
                String cellValue;
                if (Boolean.TRUE.equals(trade.getGrouponFlag())) {
                    cellValue = "拼团订单";
                } else if (Boolean.TRUE.equals(trade.getIsFlashSaleGoods())) {
                    cellValue = "秒杀订单";
                } else if (Boolean.TRUE.equals(trade.getBargain())) {
                    cellValue = "砍价订单";
                } else if (trade.getOrderTag() != null && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag())) {
                    cellValue = "周期购订单";
                } else {
                    cellValue = "-";
                }
                cell.setCellValue(cellValue);
            }));
            columnList.add(new Column("计划发货时间", (cell, object) -> {
                ProviderTradeExportVO trade = (ProviderTradeExportVO) object;
                OrderTagVO orderTag = trade.getOrderTag();
                String cellValue = "-";
                if (orderTag != null && Boolean.TRUE.equals(orderTag.getBuyCycleFlag())
                        && !FlowState.VOID.equals(trade.getFlowState())
                        && (DeliverStatus.NOT_YET_SHIPPED.equals(trade.getDeliverStatus()) || DeliverStatus.PART_SHIPPED.equals(trade.getDeliverStatus()))
                        && trade.getTradeBuyCycle().getBuyCycleNextPlanDate() != null) {
                    cellValue = trade.getTradeBuyCycle().getBuyCycleNextPlanDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
                }
                cell.setCellValue(cellValue);
            }));
            // 获取代销类型
            SellPlatformType sellPlatformType = (SellPlatformType) typeMap.get("sellPlatformType");
            if (sellPlatformType == SellPlatformType.WECHAT_VIDEO) {
                // 处理视频号订单
                columnList.add(new Column("带货视频号", new SpelColumnRender<TradeVO>("videoUser.videoName")));
                columnList.add(new Column("下单场景", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String sceneGroupStr = Optional.ofNullable(WxSceneGroup.fromValue(trade.getSceneGroup()))
                            .map(WxSceneGroup::toName).orElse("");
                    cell.setCellValue(sceneGroupStr);
                }));
            }
            columns = columnList.toArray(new Column[0]);
        } else if(Platform.SUPPLIER == platform) {
            columnList.add(new Column("订单编号", new SpelColumnRender<ProviderTradeExportVO>("parentId")));
            columnList.add(new Column("子单号", new SpelColumnRender<ProviderTradeExportVO>("id")));

            if (flag) {
                columnList.add(new Column("渠道订单号", new SpelColumnRender<ProviderTradeExportVO>(
                        "thirdPlatformOrderId")));
                columnList.add(new Column("渠道子单号", new SpelColumnRender<ProviderTradeExportVO>("outOrderId")));
            }
            columnList.add(new Column("下单时间", new SpelColumnRender<ProviderTradeExportVO>("createTime")));
            columnList.add(new Column("完成时间", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                if (FlowState.COMPLETED.equals(exportTrade.getFlowState())) {
                    cell.setCellValue(DateUtil.format(exportTrade.getEndTime(), DateUtil.FMT_TIME_1));
                } else {
                    cell.setCellValue("-");
                }
            }));
            columnList.add(new Column("商家名称", new SpelColumnRender<ProviderTradeExportVO>("supplierName")));
            columnList.add(new Column("收货人", new SpelColumnRender<ProviderTradeExportVO>("consigneeName")));
            columnList.add(new Column("收货人手机", new SpelColumnRender<ProviderTradeExportVO>("consigneePhone")));
            columnList.add(new Column("收货人地址", new SpelColumnRender<ProviderTradeExportVO>("detailAddress")));
            columnList.add(new Column("配送方式", new SpelColumnRender<ProviderTradeExportVO>("deliverWayName")));

            columnList.add(new Column("抵扣方式", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                String cellValue = "";
                if (Objects.nonNull(exportTrade.getPointsPrice())) {
                    cellValue = "积分";
                }
                if (Objects.nonNull(exportTrade.getGiftCardPrice())) {
                    if (StringUtils.isNotEmpty(cellValue)) {
                        if (Objects.nonNull(exportTrade.getOrderTag()) && Objects.nonNull(exportTrade.getOrderTag().getPickupCardFlag()) && exportTrade.getOrderTag().getPickupCardFlag()){
                            cellValue = "礼品卡-提货卡";
                        } else {
                            cellValue = "积分/礼品卡-现金卡";
                        }
                    } else {
                        if (Objects.nonNull(exportTrade.getOrderTag()) && Objects.nonNull(exportTrade.getOrderTag().getPickupCardFlag()) && exportTrade.getOrderTag().getPickupCardFlag()){
                            cellValue = "礼品卡-提货卡";
                        } else {
                            cellValue = "礼品卡-现金卡";
                        }
                    }
                }
                cell.setCellValue(cellValue);
            }));
            columnList.add(new Column("礼品卡-现金卡抵扣", new SpelColumnRender<ProviderTradeExportVO>("giftCardPrice")));
//            columnList.add(new Column("礼品卡-提货卡抵扣", (cell, object) -> {
//                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                String v = "-";
//                if (exportTrade.getOrderTag().getPickupCardFlag()){
//                    v = exportTrade.getGiftCardPrice().toString();
//                }
//                cell.setCellValue(v);
//            }));
            columnList.add(new Column("积分抵扣", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                String v = "-";
                if (Objects.isNull(exportTrade.getOrderTag()) || Objects.isNull(exportTrade.getOrderTag().getPickupCardFlag()) || !exportTrade.getOrderTag().getPickupCardFlag()){
                    v = exportTrade.getPointsPrice().toString();
                }
                cell.setCellValue(v);
            }));
            columnList.add(new Column("积分", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                String v = "-";
                if (Objects.isNull(exportTrade.getOrderTag()) || Objects.isNull(exportTrade.getOrderTag().getPickupCardFlag()) || !exportTrade.getOrderTag().getPickupCardFlag()){
                    v = exportTrade.getPoints().toString();
                }
                cell.setCellValue(v);
            }));

            columnList.add(new Column("订单商品金额", new SpelColumnRender<ProviderTradeExportVO>("orderGoodsPrice")));
            columnList.add(new Column("商品名称", new SpelColumnRender<ProviderTradeExportVO>("skuName")));
            columnList.add(new Column("供货价", new SpelColumnRender<ProviderTradeExportVO>("supplyPrice")));
            columnList.add(new Column("供货商", new SpelColumnRender<ProviderTradeExportVO>("providerName")));
            //子单只有实物商品
            columnList.add(new Column("商品类型", (cell, object) -> cell.setCellValue("实物商品")));
            columnList.add(new Column("商品规格", new SpelColumnRender<ProviderTradeExportVO>("specDetails")));
            columnList.add(new Column("SKU编码", new SpelColumnRender<ProviderTradeExportVO>("skuNo")));

            columnList.add(new Column("购买数量", new SpelColumnRender<ProviderTradeExportVO>("num")));
            columnList.add(new Column("卖家备注", new SpelColumnRender<ProviderTradeExportVO>("buyerRemark")));
            columnList.add(new Column("订单状态", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                FlowState flowState = exportTrade.getFlowState();
                if (Objects.nonNull(flowState)) {
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
                }
            }));
            columnList.add(new Column("发货状态", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                if (Objects.nonNull(exportTrade.getDeliverStatus())) {
                    cell.setCellValue(exportTrade.getDeliverStatus().getDescription());
                }
            }));
            columnList.add(new Column("付款状态", (cell, object) -> {
                ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                if (Objects.nonNull(exportTrade.getPayState())) {
                    cell.setCellValue(exportTrade.getPayState().getDescription());
                }
            }));
            columnList.add(new Column("订单类型", (cell, object) -> {
                ProviderTradeExportVO trade = (ProviderTradeExportVO) object;
                String cellValue;
                if (Boolean.TRUE.equals(trade.getGrouponFlag())) {
                    cellValue = "拼团订单";
                } else if (Boolean.TRUE.equals(trade.getIsFlashSaleGoods())) {
                    cellValue = "秒杀订单";
                } else if (Boolean.TRUE.equals(trade.getBargain())) {
                    cellValue = "砍价订单";
                } else if (trade.getOrderTag() != null && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag())) {
                    cellValue = "周期购订单";
                } else {
                    cellValue = "-";
                }
                cell.setCellValue(cellValue);
            }));
            columnList.add(new Column("计划发货时间", (cell, object) -> {
                ProviderTradeExportVO trade = (ProviderTradeExportVO) object;
                OrderTagVO orderTag = trade.getOrderTag();
                String cellValue = "-";
                if (orderTag != null && Boolean.TRUE.equals(orderTag.getBuyCycleFlag())
                        && !FlowState.VOID.equals(trade.getFlowState())
                        && (DeliverStatus.NOT_YET_SHIPPED.equals(trade.getDeliverStatus()) || DeliverStatus.PART_SHIPPED.equals(trade.getDeliverStatus()))
                        && trade.getTradeBuyCycle().getBuyCycleNextPlanDate() != null) {
                    cellValue = trade.getTradeBuyCycle().getBuyCycleNextPlanDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
                }
                cell.setCellValue(cellValue);
            }));
            // 获取代销类型
            SellPlatformType sellPlatformType = (SellPlatformType) typeMap.get("sellPlatformType");
            if (sellPlatformType == SellPlatformType.WECHAT_VIDEO) {
                // 处理视频号订单
                columnList.add(new Column("带货视频号", new SpelColumnRender<TradeVO>("videoUser.videoName")));
                columnList.add(new Column("下单场景", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    String sceneGroupStr = Optional.ofNullable(WxSceneGroup.fromValue(trade.getSceneGroup()))
                            .map(WxSceneGroup::toName).orElse("");
                    cell.setCellValue(sceneGroupStr);
                }));
            }
            columns = columnList.toArray(new Column[0]);
        } else {
            Column[] col = {
                    new Column("订单编号", new SpelColumnRender<ProviderTradeExportVO>("parentId")),
                    new Column("子单号", new SpelColumnRender<ProviderTradeExportVO>("id")),
                    new Column("下单时间", new SpelColumnRender<ProviderTradeExportVO>("createTime")),
                    new Column("完成时间", (cell, object) -> {
                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                        if (FlowState.COMPLETED.equals(exportTrade.getFlowState())) {
                            cell.setCellValue(DateUtil.format(exportTrade.getEndTime(), DateUtil.FMT_TIME_1));
                        } else {
                            cell.setCellValue("-");
                        }
                    }),
                    new Column("商家", new SpelColumnRender<ProviderTradeExportVO>("supplierInfo")),
                    new Column("收货人", new SpelColumnRender<ProviderTradeExportVO>("consigneeName")),
                    new Column("收货人手机", new SpelColumnRender<ProviderTradeExportVO>("consigneePhone")),
                    new Column("收货人地址", new SpelColumnRender<ProviderTradeExportVO>("detailAddress")),
                    new Column("配送方式", new SpelColumnRender<ProviderTradeExportVO>("deliverWayName")),

//                    new Column("抵扣方式", (cell, object) -> {
//                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                        String cellValue = "";
//                        if (Objects.nonNull(exportTrade.getPointsPrice())) {
//                            cellValue = "积分";
//                        }
//                        if (Objects.nonNull(exportTrade.getGiftCardPrice())) {
//                            if (StringUtils.isNotEmpty(cellValue)) {
//                                if (Objects.nonNull(exportTrade.getOrderTag()) && Objects.nonNull(exportTrade.getOrderTag().getPickupCardFlag()) && exportTrade.getOrderTag().getPickupCardFlag()){
//                                    cellValue = "礼品卡-提货卡";
//                                } else {
//                                    cellValue = "积分/礼品卡-现金卡";
//                                }
//                            } else {
//                                if (Objects.nonNull(exportTrade.getOrderTag()) && Objects.nonNull(exportTrade.getOrderTag().getPickupCardFlag()) && exportTrade.getOrderTag().getPickupCardFlag()){
//                                    cellValue = "礼品卡-提货卡";
//                                } else {
//                                    cellValue = "礼品卡-现金卡";
//                                }
//                            }
//                        }
//                        cell.setCellValue(cellValue);
//                    }),
//                    new Column("礼品卡-现金卡抵扣", new SpelColumnRender<ProviderTradeExportVO>("giftCardPrice != null  ? giftCardPrice : '-' ")),
//                    new Column("礼品卡-提货卡抵扣", (cell, object) -> {
//                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                        String v = "-";
//                        if (exportTrade.getOrderTag().getPickupCardFlag()){
//                            v = exportTrade.getGiftCardPrice().toString();
//                        }
//                        cell.setCellValue(v);
//                    }),
//                    new Column("积分抵扣", (cell, object) -> {
//                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                        String v = "-";
//                        if (Objects.isNull(exportTrade.getOrderTag()) || Objects.isNull(exportTrade.getOrderTag().getPickupCardFlag()) || !exportTrade.getOrderTag().getPickupCardFlag()){
//                            if(exportTrade.getPointsPrice() != null) {
//                                v = exportTrade.getPointsPrice().toString();
//                            }
//                        }
//                        cell.setCellValue(v);
//                    }),
//                    new Column("积分", (cell, object) -> {
//                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                        String v = "-";
//                        if (Objects.isNull(exportTrade.getOrderTag()) || Objects.isNull(exportTrade.getOrderTag().getPickupCardFlag()) || !exportTrade.getOrderTag().getPickupCardFlag()){
//                            if(exportTrade.getPoints() != null) {
//                                v = exportTrade.getPoints().toString();
//                            }
//                        }
//                        cell.setCellValue(v);
//                    }),

//                    ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
//                    if (Objects.nonNull(exportTrade.getDeliverWay())) {
//                        cell.setCellValue(exportTrade.getDeliverWay().getDesc());
//                    }
//                    new Column("订单商品金额", new SpelColumnRender<ProviderTradeExportVO>("orderGoodsPrice")),
                    new Column("商品名称", new SpelColumnRender<ProviderTradeExportVO>("skuName")),
                    new Column("供货商", new SpelColumnRender<ProviderTradeExportVO>("providerName")),
                    new Column("供货价", new SpelColumnRender<ProviderTradeExportVO>("supplyPrice")),
                    //子单只有实物商品
                    new Column("商品类型", (cell, object) -> cell.setCellValue("实物商品")),
                    new Column("商品规格", new SpelColumnRender<ProviderTradeExportVO>("specDetails")),
                    new Column("SKU编码", new SpelColumnRender<ProviderTradeExportVO>("skuNo")),
                    new Column("商品种类", new SpelColumnRender<ProviderTradeExportVO>("goodsSpecies")),
                    new Column("商品总数量", new SpelColumnRender<ProviderTradeExportVO>("totalNum")),
                    new Column("购买数量", new SpelColumnRender<ProviderTradeExportVO>("num")),
                    new Column("卖家备注", new SpelColumnRender<ProviderTradeExportVO>("buyerRemark")),
                    new Column("订单状态", (cell, object) -> {
                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                        FlowState flowState = exportTrade.getFlowState();
                        if (Objects.nonNull(flowState)) {
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
                        }
                    }),
                    new Column("发货状态", (cell, object) -> {
                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                        if (Objects.nonNull(exportTrade.getDeliverStatus())) {
                            cell.setCellValue(exportTrade.getDeliverStatus().getDescription());
                        }
                    }),
                    new Column("付款状态", (cell, object) -> {
                        ProviderTradeExportVO exportTrade = (ProviderTradeExportVO) object;
                        if (Objects.nonNull(exportTrade.getPayState())) {
                            cell.setCellValue(exportTrade.getPayState().getDescription());
                        }
                    }),
                    new Column("订单类型", (cell, object) -> {
                        ProviderTradeExportVO trade = (ProviderTradeExportVO) object;
                        String cellValue;
                        if (trade.getOrderTag() != null && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag())) {
                            cellValue = "周期购订单";
                        } else {
                            cellValue = "-";
                        }
                        cell.setCellValue(cellValue);
                    }),
                    new Column("计划发货时间", (cell, object) -> {
                        ProviderTradeExportVO trade = (ProviderTradeExportVO) object;
                        OrderTagVO orderTag = trade.getOrderTag();
                        String cellValue = "-";
                        if (orderTag != null && Boolean.TRUE.equals(orderTag.getBuyCycleFlag())
                                && !FlowState.VOID.equals(trade.getFlowState())
                                && (DeliverStatus.NOT_YET_SHIPPED.equals(trade.getDeliverStatus()) || DeliverStatus.PART_SHIPPED.equals(trade.getDeliverStatus()))
                                && trade.getTradeBuyCycle().getBuyCycleNextPlanDate() != null) {
                            cellValue = trade.getTradeBuyCycle().getBuyCycleNextPlanDate().format(DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
                        }
                        cell.setCellValue(cellValue);
                    }),
            };
            columns = col;
        }
        return columns;
    }

}
