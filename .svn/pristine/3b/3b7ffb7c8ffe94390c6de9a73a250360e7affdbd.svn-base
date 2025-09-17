package com.wanmi.ares.marketing.preferential.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.reducediscountgift.dao.MarketingReduceDiscountGiftMapper;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.response.MarketingReduceDiscountGiftReport;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.MD5Util;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jodd.util.RandomString;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description 加价购服务
 * @author malianfeng
 * @date 2022/11/29 14:06
 */
@Service
@Slf4j
public class PreferentialService implements ExportBaseService {

    @Autowired AnalysisCommonService analysisCommonService;

    @Autowired private StoreService storeService;

    @Autowired private OsdService osdService;

    @Autowired GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Autowired
    private MarketingReduceDiscountGiftMapper marketingReduceDiscountGiftMapper;

    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws Exception {
        List<String> locations = new ArrayList<>();
        EffectPageRequest request = new EffectPageRequest();
        request.setPageSize(5000);
        request.setQueryDate(exportQuery.getDateTo());
        if (CollectionUtils.isNotEmpty(exportQuery.getMarketingIds())){
            request.setMarketingIds(exportQuery.getMarketingIds());
        }
        request.setStoreId(exportQuery.getStoreId());
        request.setSortName("payMoney");
        request.setSortOrder("DESC");
        request.setQueryDate(exportQuery.getDateFrom());
        request.setMarketingType(MarketingType.PREFERENTIAL);
        PageInfo<MarketingReduceDiscountGiftReport> pageInfo = this.pageActivityEffect(request);
        List<MarketingReduceDiscountGiftReport> reports = pageInfo.getList();
        long total = pageInfo.getTotal();
        int i = reports.size();
        uploadFileOfFullMoneyBookingSale(reports, locations, exportQuery);

        if (total != 0){
            while (i < total){
                request.setPageNum(request.getPageNum() + 1);
                pageInfo = this.pageActivityEffect(request);
                reports = pageInfo.getList();
                uploadFileOfFullMoneyBookingSale(reports, locations, exportQuery);
                i += reports.size();
            }
        }
        return BaseResponse.success(locations);
    }

    /**
     * 活动效果
     * @param request
     * @return
     */
    public PageInfo<MarketingReduceDiscountGiftReport> pageActivityEffect(EffectPageRequest request){
        if (StringUtils.isBlank(request.getSortName())){
            request.setSortName("payMoney");
            request.setSortOrder("desc");
        }
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<MarketingReduceDiscountGiftReport> reports = marketingReduceDiscountGiftMapper.pageActivityEffect(request);
        PageInfo<MarketingReduceDiscountGiftReport> reduceDiscountGiftReportPageInfo = new PageInfo<>(reports);
        List<String> goodsInfoIds = reduceDiscountGiftReportPageInfo.getList().stream().map(MarketingReduceDiscountGiftReport::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)){
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            reduceDiscountGiftReportPageInfo.getList().forEach(v -> v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId())));
        }
        return reduceDiscountGiftReportPageInfo;
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(){
        return new Column[]{
                new Column("商品信息", new SpelColumnRender<MarketingReduceDiscountGiftReport>("goodsInfoName")),
                new Column("规格", new SpelColumnRender<MarketingReduceDiscountGiftReport>("specDetails")),
                new Column("sku编码", new SpelColumnRender<MarketingReduceDiscountGiftReport>("goodsInfoNo")),
                new Column("ROI", (cell, object) -> {
                    MarketingReduceDiscountGiftReport fullMoneyBookingReport = (MarketingReduceDiscountGiftReport) object;
                    if (fullMoneyBookingReport.getDiscountMoney() == null ||
                            fullMoneyBookingReport.getDiscountMoney().compareTo(BigDecimal.ZERO) == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate =
                                fullMoneyBookingReport.getPayMoney().divide(fullMoneyBookingReport.getDiscountMoney(),
                                        2, RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("营销支付件数", new SpelColumnRender<MarketingReduceDiscountGiftReport>("payGoodsCount")),
                new Column("营销支付人数", new SpelColumnRender<MarketingReduceDiscountGiftReport>("payCustomerCount")),
                new Column("营销支付订单数", new SpelColumnRender<MarketingReduceDiscountGiftReport>("payTradeCount")),
                new Column("营销支付金额", new SpelColumnRender<MarketingReduceDiscountGiftReport>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<MarketingReduceDiscountGiftReport>("discountMoney")),
                new Column("新成交客户", new SpelColumnRender<MarketingReduceDiscountGiftReport>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<MarketingReduceDiscountGiftReport>("oldCustomerCount")),
                new Column("连带率", (cell, object) -> {
                    MarketingReduceDiscountGiftReport fullMoneyBookingReport = (MarketingReduceDiscountGiftReport) object;
                    if (fullMoneyBookingReport.getPayTradeCount() == null ||
                            fullMoneyBookingReport.getPayTradeCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate = new BigDecimal(fullMoneyBookingReport.getPayGoodsCount())
                                .divide(new BigDecimal(fullMoneyBookingReport.getPayTradeCount()), 2,
                                        RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("客单价", (cell, object) -> {
                    MarketingReduceDiscountGiftReport depositBookingReport = (MarketingReduceDiscountGiftReport) object;
                    if (depositBookingReport.getPayMoney() == null || depositBookingReport.getPayMoney().compareTo(BigDecimal.ZERO) == 0
                            || depositBookingReport.getPayCustomerCount() == null || depositBookingReport.getPayCustomerCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate = depositBookingReport.getPayMoney()
                                .divide(new BigDecimal(depositBookingReport.getPayCustomerCount()), 2,
                                        RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("供货价", new SpelColumnRender<MarketingReduceDiscountGiftReport>("supplyPrice")),
                new Column("浏览量", new SpelColumnRender<MarketingReduceDiscountGiftReport>("pv")),
                new Column("访客数", new SpelColumnRender<MarketingReduceDiscountGiftReport>("uv")),
                new Column("访问-支付转化率", (cell, object) -> {
                    MarketingReduceDiscountGiftReport marketingReduceDiscountGiftReport = (MarketingReduceDiscountGiftReport) object;
                    if (Objects.nonNull(marketingReduceDiscountGiftReport.getUv())
                            && marketingReduceDiscountGiftReport.getUv() > 0) {
                        BigDecimal uvPayRate = BigDecimal.valueOf(marketingReduceDiscountGiftReport.getPayCustomerCount()).divide( new BigDecimal(marketingReduceDiscountGiftReport.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvPayRate));
                    } else {
                        cell.setCellValue("-");
                    }
                })
        };
    }

    protected void uploadFileOfFullMoneyBookingSale(List<MarketingReduceDiscountGiftReport> records, List<String> locations, ExportQuery query)
            throws Exception {
        //如果没有报表数据，则生成只有表头的excel文件
        ExcelHelper<MarketingReduceDiscountGiftReport> excelHelper = new ExcelHelper<>();
        excelHelper.addSheet(
                "加价购活动效果统计",
                getColumn(),
                records
        );
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("marketing/buyoutprice/%s/%s" +
                        "加价购活动效果统计_%s_%s-%s.xls",
                query.getCompanyId(),
                storeService.getStoreName(query),
                query.getDateFrom(),
                query.getDateTo(),
                randomData
        );
        if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
            fileName = String.format("marketing/preferential/%s/%s/%s加价购活动效果统计_%s_%s-%s.xls",
                    query.getCompanyId(),
                    MD5Util.md5Hex(Joiner.on(",").join(query.getMarketingIds())),
                    storeService.getStoreName(query),
                    query.getDateFrom(),
                    query.getDateTo(),
                    randomData
            );
        }

        fileName = osdService.getFileRootPath().concat(fileName);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            excelHelper.write(os);
            osdService.uploadExcel(os, fileName);
            locations.add(fileName);
        }
    }
}
