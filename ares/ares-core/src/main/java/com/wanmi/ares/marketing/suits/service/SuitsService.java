package com.wanmi.ares.marketing.suits.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.marketing.suits.dao.SuitsStatisticsMapper;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.response.SuitsReport;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/13 13:56
 * @Description：
 */
@Service
@Slf4j
public class SuitsService implements ExportBaseService {

    @Autowired
    SuitsStatisticsMapper suitsStatisticsMapper;

    @Autowired MarketingTaskRecordService marketingTaskRecordService;

    @Autowired AnalysisCommonService analysisCommonService;

    @Autowired GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Autowired private StoreService storeService;

    @Autowired private OsdService osdService;

    /**
     * 按订单与营销维度根据指定时间/默认昨天统计:营销支付金额 + 营销优惠金额 + 营销支付订单数 + 营销支付人数 + 营销支付件数 + 新老客户(平台/商家)
     * @param jobRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertMarketingStatisticsDay(MarketingAnalysisJobRequest jobRequest){
        int result = suitsStatisticsMapper.insertMarketingStatisticsDay(jobRequest);
        jobRequest.setMarketingType(MarketingType.SUITS.name());
        marketingTaskRecordService.insert(jobRequest);
        return result;
    }

    /**
     * 按订单与SKU编号维度每日统计:会员ID + 店铺ID + 营销ID + 购买数量 + 订单实际支付金额 + 优惠金额 + 支付时间
     * @param jobRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertTradeMarketingSkuDetailDay(MarketingAnalysisJobRequest jobRequest){
        return suitsStatisticsMapper.insertTradeMarketingSkuDetailDay(jobRequest);
    }

    /**
     * 活动效果
     * @param request
     * @return
     */
    public PageInfo<SuitsReport> pageActivityEffect(EffectPageRequest request){
        if (StringUtils.isBlank(request.getSortName())){
            request.setSortName("payMoney");
            request.setSortOrder("desc");
        }
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<SuitsReport> reports =
                suitsStatisticsMapper.pageActivityEffect(request);
        PageInfo<SuitsReport> suitsReportPageInfo = new PageInfo<>(reports);
        List<String> goodsInfoIds =
                suitsReportPageInfo.getList().stream().map(SuitsReport::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)){
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            suitsReportPageInfo.getList().forEach(v -> v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId())));
        }
        return suitsReportPageInfo;
    }

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
        request.setMarketingType(MarketingType.SUITS);
        PageInfo<SuitsReport> pageInfo = this.pageActivityEffect(request);
        List<SuitsReport> reports = pageInfo.getList();
        long total = pageInfo.getTotal();
        int i = reports.size();
        uploadFileOfSuits(reports, locations, exportQuery);

        if (total != 0){
            while (i < total){
                request.setPageNum(request.getPageNum() + 1);
                pageInfo = this.pageActivityEffect(request);
                reports = pageInfo.getList();
                uploadFileOfSuits(reports, locations, exportQuery);
                i += reports.size();
            }
        }
        return BaseResponse.success(locations);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(){
        return new Column[]{
                new Column("商品信息", new SpelColumnRender<SuitsReport>("goodsInfoName")),
                new Column("规格", new SpelColumnRender<SuitsReport>("specDetails")),
                new Column("sku编码", new SpelColumnRender<SuitsReport>("goodsInfoNo")),
                new Column("ROI", (cell, object) -> {
                    SuitsReport suitsReport =
                            (SuitsReport) object;
                    if (suitsReport.getDiscountMoney() == null ||
                            suitsReport.getDiscountMoney().compareTo(BigDecimal.ZERO) == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate =
                                suitsReport.getPayMoney().divide(suitsReport.getDiscountMoney(),
                                        2, RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("营销支付件数", new SpelColumnRender<SuitsReport>("payGoodsCount")),
                new Column("营销支付人数", new SpelColumnRender<SuitsReport>("payCustomerCount")),
                new Column("营销支付订单数", new SpelColumnRender<SuitsReport>("payTradeCount")),
                new Column("营销支付金额", new SpelColumnRender<SuitsReport>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<SuitsReport>("discountMoney")),
                new Column("新成交客户", new SpelColumnRender<SuitsReport>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<SuitsReport>("oldCustomerCount")),
                new Column("连带率", (cell, object) -> {
                    SuitsReport suitsReport = (SuitsReport) object;
                    if (suitsReport.getPayTradeCount() == null ||
                            suitsReport.getPayTradeCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate = new BigDecimal(suitsReport.getPayGoodsCount())
                                .divide(new BigDecimal(suitsReport.getPayTradeCount()), 2,
                                        RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("客单价", (cell, object) -> {
                    SuitsReport depositBookingReport = (SuitsReport) object;
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
                new Column("供货价", new SpelColumnRender<SuitsReport>("supplyPrice")),
                new Column("浏览量", new SpelColumnRender<SuitsReport>("pv")),
                new Column("访客数", new SpelColumnRender<SuitsReport>("uv")),
                new Column("访问-支付转化率", (cell, object) -> {
                    SuitsReport marketingReduceDiscountGiftReport = (SuitsReport) object;
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

    protected void uploadFileOfSuits(List<SuitsReport> records, List<String> locations, ExportQuery query)
            throws Exception {
        //如果没有报表数据，则生成只有表头的excel文件
        ExcelHelper<SuitsReport> excelHelper = new ExcelHelper<>();
        excelHelper.addSheet(
                "组合购活动效果统计",
                getColumn(),
                records
        );
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("marketing/suits/%s/%s组合购活动效果统计_%s_%s" +
                        "-%s.xls",
                query.getCompanyId(),
                storeService.getStoreName(query),
                query.getDateFrom(),
                query.getDateTo(),
                randomData
        );
        if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
            fileName = String.format("marketing/suits/%s/%s/%s组合购活动效果统计_%s_%s-%s.xls",
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
