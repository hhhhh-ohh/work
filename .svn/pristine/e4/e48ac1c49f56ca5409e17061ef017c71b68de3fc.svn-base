package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.finance.record.AccountRecordQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.AccountDetailsExportRequest;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.vo.AccountDetailsVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.FinanceBillDetailBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className FinanceBillDetailExportService
 * @description 对账明细导出
 * @date 2021/6/3 8:18 下午
 **/
@Service
@Slf4j
public class FinanceBillDetailExportService implements ExportBaseService {

    /**
     * 支持的支付渠道，支付宝、微信、企业银联、云闪付、余额、授信
     */
    private static final List<PayWay> SUPPORT_PAY_WAYS = Arrays.asList(
            PayWay.ALIPAY,
            PayWay.WECHAT,
            PayWay.UNIONPAY_B2B,
            PayWay.UNIONPAY,
            PayWay.BALANCE,
            PayWay.CREDIT
    );

    @Autowired
    private AccountRecordQueryProvider accountRecordQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private FinanceBillDetailBaseService financeBillDetailBaseService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("financeBillDetail export begin, param:{}", data);

        AccountDetailsExportRequest request = JSON.parseObject(data.getParam(), AccountDetailsExportRequest.class);

        List<AccountDetailsVO> accountDetailsVOS = financeBillDetailBaseService.queryExport(data.getOperator(),request);

        if(CollectionUtils.isNotEmpty(accountDetailsVOS)){
            //判断客户是否已注销
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(accountDetailsVOS.stream()
                    .map(AccountDetailsVO::getCustomerId).collect(Collectors.toList()));

            accountDetailsVOS.forEach(detailsVo -> {
                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(detailsVo.getCustomerId()))){
                    detailsVo.setCustomerName(detailsVo.getCustomerName()+Constants.LOGGED_OUT);
                }
            });
        }


        String begin = DateUtil.format(DateUtil.parse(request.getBeginTime(), DateUtil.FMT_TIME_1), DateUtil.FMT_TIME_5);
        String end = DateUtil.format(DateUtil.parse(request.getEndTime(), DateUtil.FMT_TIME_1), DateUtil.FMT_TIME_5);

        StoreVO store = storeQueryProvider.getById(new StoreByIdRequest(request.getStoreId())).getContext().getStoreVO();
        boolean flag = request.getAccountRecordType().toValue() == 0;
        String fileName = String.format("%s%s-%s%s对账明细.xls"
                , store.getStoreName(), begin, end, flag ? "收入" : "退款");
        String resourceKey = String.format("financeBillDetail/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        if (flag) {
            doExportIncomeDetails(excelHelper, accountDetailsVOS);
        } else {
            doExportRefundDetails(excelHelper, accountDetailsVOS);
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.write(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn() {
        return new Column[]{
                new Column("下单时间", new SpelColumnRender<AccountDetailsVO>("orderTime")),
                new Column("订单编号", new SpelColumnRender<AccountDetailsVO>("orderCode")),
                new Column("交易流水号", new SpelColumnRender<AccountDetailsVO>("tradeNo")),
                new Column("客户昵称", new SpelColumnRender<AccountDetailsVO>("customerName")),
                new Column("支付时间", new SpelColumnRender<AccountDetailsVO>("tradeTime")),
                new Column("支付渠道", (cell, object) -> {
                    AccountDetailsVO d = (AccountDetailsVO) object;
                    String val = this.handlePayWayVal(d);
                    cell.setCellValue(val);
                }),
                new Column("抵扣方式", (cell, object) -> {
                    AccountDetailsVO d = (AccountDetailsVO) object;
                    String val = this.handleDeductionTypeVal(d);
                    cell.setCellValue(val);
                }),
                new Column("支付金额", new SpelColumnRender<AccountDetailsVO>("amount")),
                new Column("积分抵现金额", new SpelColumnRender<AccountDetailsVO>("pointsPrice")),
                new Column("积分数量", new SpelColumnRender<AccountDetailsVO>("points")),
                new Column("礼品卡抵现金额", new SpelColumnRender<AccountDetailsVO>("giftCardPrice"))
        };
    }

    private String handlePayWayVal(AccountDetailsVO d) {
        String val = "-";
        BigDecimal amount;
        try {
            amount = new BigDecimal(d.getAmount());
        } catch (Exception e) {
            amount = BigDecimal.ZERO;
        }
        if (amount.compareTo(BigDecimal.ZERO) > 0 && Objects.nonNull(d.getPayWay()) && SUPPORT_PAY_WAYS.contains(d.getPayWay())) {
            val = d.getPayWay().getDesc();
        }
        return val;
    }

    private String handleDeductionTypeVal(AccountDetailsVO d) {
        String val = "-";
        BigDecimal giftCardPrice;
        try {
            giftCardPrice = new BigDecimal(d.getGiftCardPrice());
        } catch (Exception e) {
            giftCardPrice = BigDecimal.ZERO;
        }
        boolean usePointsFlag = Objects.nonNull(d.getPoints()) && d.getPoints() > 0;
        boolean useGiftCardPriceFlag = giftCardPrice.compareTo(BigDecimal.ZERO) > 0;
        if (usePointsFlag && useGiftCardPriceFlag) {
            if (GiftCardType.PICKUP_CARD.equals(d.getGiftCardType())){
                val = "礼品卡-提货卡";
            } else {
                val = "积分/礼品卡-现金卡";
            }
        } else if (usePointsFlag) {
            val = "积分";
        } else if (useGiftCardPriceFlag) {
            if (GiftCardType.PICKUP_CARD.equals(d.getGiftCardType())){
                val = "礼品卡-提货卡";
            } else {
                val = "礼品卡-现金卡";
            }
        }
        return val;
    }

    /**
     * 导出收入明细
     *
     * @param details 收入明细列表
     */
    @SuppressWarnings("unchecked")
    private void doExportIncomeDetails(ExcelHelper helper, List<AccountDetailsVO> details) {

        helper.addSheet("对账单收入明细", getColumn(), details);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getRefundDetailsColumn() {
        return new Column[]{
                new Column("退单时间", new SpelColumnRender("orderTime")),
                new Column("退单编号", new SpelColumnRender<AccountDetailsVO>("returnOrderCode")),
                new Column("订单编号", new SpelColumnRender<AccountDetailsVO>("orderCode")),
                new Column("交易流水号", new SpelColumnRender<AccountDetailsVO>("tradeNo")),
                new Column("客户昵称", new SpelColumnRender<AccountDetailsVO>("customerName")),
                new Column("退款时间", new SpelColumnRender<AccountDetailsVO>("tradeTime")),
                new Column("退款渠道", (cell, object) -> {
                    AccountDetailsVO d = (AccountDetailsVO) object;
                    String val = this.handlePayWayVal(d);
                    cell.setCellValue(val);
                }),
                new Column("抵扣方式", (cell, object) -> {
                    AccountDetailsVO d = (AccountDetailsVO) object;
                    String val = this.handleDeductionTypeVal(d);
                    cell.setCellValue(val);
                }),
                new Column("退款金额", new SpelColumnRender<AccountDetailsVO>("amount")),
                new Column("积分抵现金额", new SpelColumnRender<AccountDetailsVO>("pointsPrice")),
                new Column("积分数量", new SpelColumnRender<AccountDetailsVO>("points")),
                new Column("礼品卡抵现金额", new SpelColumnRender<AccountDetailsVO>("giftCardPrice"))
        };
    }

    /**
     * 导出退款明细
     *
     * @param details 退款明细列表
     */
    @SuppressWarnings("unchecked")
    private void doExportRefundDetails(ExcelHelper helper, List<AccountDetailsVO> details) {
        helper.addSheet("对账单退款明细", getRefundDetailsColumn(), details);
    }
}
