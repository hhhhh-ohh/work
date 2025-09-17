package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBillQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillExportRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailByIdRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardInfoRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardInfoResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBillVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.GiftCardRecordBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @description 礼品卡使用记录报表
 * @author malianfeng
 * @date 2022/12/14 15:09
 */
@Service
@Slf4j
public class GiftCardBillExportService implements ExportBaseService {

    @Autowired private GiftCardRecordBaseService giftCardRecordBaseService;

    @Autowired private GiftCardBillQueryProvider giftCardBillQueryProvider;

    @Autowired private GiftCardQueryProvider giftCardQueryProvider;

    @Autowired private ExportUtilService exportUtilService;

    @Autowired private OsdService osdService;

    public static final int MAX_SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        GiftCardBillExportRequest exportRequest = JSON.parseObject(data.getParam(), GiftCardBillExportRequest.class);

        String exportName = "礼品卡使用记录报表";
        String fileName = String.format("礼品卡使用记录_%s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + exportUtilService.getRandomNum());

        log.info("{} export begin, param:{}", exportName, exportRequest);

        List<String> resourceKeyList = new ArrayList<>();
        String resourceKey = String.format("giftCardBill/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);

        // 构造分页请求
        GiftCardBillPageRequest pageRequest = KsBeanUtil.convert(exportRequest, GiftCardBillPageRequest.class);
        pageRequest.setPageSize(MAX_SIZE);

        // 获取总数
        long totalCount = giftCardBillQueryProvider.countForExport(exportRequest).getContext();
        // 总页数
        long fileSize = (long) Math.ceil(1.0 * totalCount / MAX_SIZE);
        // 写入表头
        ExcelHelper<GiftCardBillVO> excelHelper;
        Column[] columns = this.getColumns();
        if(Objects.nonNull(exportRequest.getGiftCardType())
                && exportRequest.getGiftCardType() == GiftCardType.PICKUP_CARD){
            columns = this.getPickupCardColumns();
        }
        // 没有数据则生成空表
        if (totalCount == 0) {
            excelHelper = new ExcelHelper<>();
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
            excelHelper = new ExcelHelper<>();
            pageRequest.setPageNum(i);
            List<GiftCardBillVO> dataList = giftCardRecordBaseService.getGiftCardBillList(data.getOperator(), pageRequest);
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
        return BaseResponse.success(String.join(",", resourceKeyList));
    }

    /** 获取表头 */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("订单号", new SpelColumnRender<GiftCardBillVO>("businessId")),
                new Column("交易类型", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    cell.setCellValue(this.getBusinessTypeName(detailVO.getBusinessType()));
                }),
                new Column("卡号", new SpelColumnRender<GiftCardBillVO>("giftCardNo")),
                new Column("礼品卡信息", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    cell.setCellValue(String.format("%s ¥%s.00",
                            Optional.ofNullable(detailVO.getGiftCardName()).orElse("-"),
                            Optional.ofNullable(detailVO.getGiftCardParValue()).orElse(0L)));
                }),
                new Column("金额", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    GiftCardBusinessType businessType = detailVO.getBusinessType();
                    BigDecimal beforeBalance = detailVO.getBeforeBalance();
                    BigDecimal afterBalance = detailVO.getAfterBalance();
                    if (Objects.isNull(beforeBalance) && Objects.isNull(afterBalance)){
                        cell.setCellValue("-");
                    } else {
                        String symbol = afterBalance.compareTo(beforeBalance) > 0
                                || GiftCardBusinessType.ORDER_REFUND == businessType ? "+" : "-";
                        cell.setCellValue(String.format("%s¥%s", symbol, detailVO.getTradeBalance()));
                    }
                }),
                new Column("余额", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    BigDecimal beforeBalance = detailVO.getBeforeBalance();
                    BigDecimal afterBalance = detailVO.getAfterBalance();
                    if (Objects.isNull(beforeBalance) && Objects.isNull(afterBalance)){
                        cell.setCellValue("-");
                    } else {
                        cell.setCellValue(String.format("¥%s", detailVO.getAfterBalance()));
                    }
                }),
                new Column("交易时间", new SpelColumnRender<GiftCardBillVO>("tradeTime")),
                new Column("客户信息", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    String tradePersonAccount = Optional.ofNullable(detailVO.getTradePersonAccount()).orElse("");
                    String tradePersonName = Optional.ofNullable(detailVO.getTradePersonName()).orElse("");
                    cell.setCellValue(String.format("%s %s", tradePersonAccount, tradePersonName));
                })
            };

        return columns;
    }

    /** 获取表头 */
    public Column[] getPickupCardColumns() {
        Column[] columns = {
                new Column("订单号", new SpelColumnRender<GiftCardBillVO>("businessId")),
                new Column("交易类型", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    cell.setCellValue(this.getBusinessTypeName(detailVO.getBusinessType()));
                }),
                new Column("卡号", new SpelColumnRender<GiftCardBillVO>("giftCardNo")),
                new Column("礼品卡信息", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    cell.setCellValue(String.format("%s",
                            Optional.ofNullable(detailVO.getGiftCardName()).orElse("-")));
                }),
                new Column("交易时间", new SpelColumnRender<GiftCardBillVO>("tradeTime")),
                new Column("客户信息", (cell, object) -> {
                    GiftCardBillVO detailVO = (GiftCardBillVO) object;
                    String tradePersonAccount = Optional.ofNullable(detailVO.getTradePersonAccount()).orElse("");
                    String tradePersonName = Optional.ofNullable(detailVO.getTradePersonName()).orElse("");
                    cell.setCellValue(String.format("%s %s", tradePersonAccount, tradePersonName));
                })
        };

        return columns;
    }

    /**
     * 获取状态名称
     */
    public String getBusinessTypeName(GiftCardBusinessType businessType) {
        if (GiftCardBusinessType.ORDER_DEDUCTION == businessType) {
            return "订单抵扣";
        } else if (GiftCardBusinessType.ORDER_REFUND == businessType) {
            return "订单退款";
        } else if (GiftCardBusinessType.ACTIVATE_CARD_FOR_SEND_CARD == businessType) {
            return "礼品卡激活（发卡礼品卡）";
        } else if (GiftCardBusinessType.ACTIVATE_CARD_FOR_EXCHANGE_CARD == businessType) {
            return "礼品卡激活（兑换礼品卡）";
        } else if (GiftCardBusinessType.CANCEL_CARD == businessType) {
            return "销卡";
        } else if (GiftCardBusinessType.ORDER_CANCEL == businessType) {
            return "订单取消";
        }
        return "";
    }
}
