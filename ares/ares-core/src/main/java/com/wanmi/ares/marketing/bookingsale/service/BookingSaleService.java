package com.wanmi.ares.marketing.bookingsale.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.ares.enums.MarketingStatus;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.marketing.bookingsale.dao.BookingSaleStatisticsMapper;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.DepositBookingReport;
import com.wanmi.ares.response.FullMoneyBookingReport;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.response.MarketingInfoResp;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.utils.MD5Util;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jakarta.annotation.Resource;

import jodd.util.RandomString;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingSaleService implements ExportBaseService {
    @Autowired
    private BookingSaleStatisticsMapper bookingSaleStatisticsMapper;

    @Autowired
    private AnalysisCommonService analysisCommonService;

    @Autowired
    private GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Resource
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Resource
    private MarketingTaskRecordService marketingTaskRecordService;

    public List<MarketingInfoResp> findMarketing(SelectMarketingRequest request){
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize(), false);
        List<MarketingInfoResp> marketingInfoResps = bookingSaleStatisticsMapper.findMarketing(request);
        marketingInfoResps.forEach(info -> {
            if (info.getPauseFlag() == DefaultFlag.YES){
                info.setStatus(MarketingStatus.PAUSED);
            } else {
                LocalDateTime startDatetime = LocalDateTime.ofInstant(info.getStartDatetime().toInstant(), ZoneId.systemDefault());
                LocalDateTime endDatetime = LocalDateTime.ofInstant(info.getEndDatetime().toInstant(), ZoneId.systemDefault());
                if (LocalDateTime.now().isBefore(startDatetime)){
                    info.setStatus(MarketingStatus.NOT_START);
                } else if (LocalDateTime.now().isAfter(endDatetime)){
                    info.setStatus(MarketingStatus.ENDED);
                } else if (LocalDateTime.now().isAfter(startDatetime) && LocalDateTime.now().isBefore(endDatetime)){
                    info.setStatus(MarketingStatus.STARTED);
                }
            }
        });
        return marketingInfoResps;
    }

    public Long findMarketingTotal(SelectMarketingRequest request){
        return bookingSaleStatisticsMapper.countByPageTotal(request);
    }

    /**
     * 拉取订单信息
     */
    @Transactional
    public Integer insertFullMoneyBooking(MarketingAnalysisJobRequest jobRequest){
        Integer result =
                bookingSaleStatisticsMapper.insertFullMoneyBooking(jobRequest);
        jobRequest.setMarketingType(MarketingType.FULL_MONEY_BOOKING_SALE.name());
        marketingTaskRecordService.insert(jobRequest);
        return result;
    }

    @Transactional
    public Integer insertDepositBooking(MarketingAnalysisJobRequest jobRequest){
        Integer result =
                bookingSaleStatisticsMapper.insertDepositBooking(jobRequest);
        bookingSaleStatisticsMapper.insertDepositBookingOfToday(jobRequest);
        jobRequest.setMarketingType(MarketingType.DEPOSIT_BOOKING_SALE.name());
        marketingTaskRecordService.insert(jobRequest);
        return result;
    }



    /**
     * 全款营销概况
     * @param query {@link MarketingQueryRequest}
     * @return {@link FullMoneyBookingReport}
     */
    public FullMoneyBookingReport queryFullMoneyBookingOverView(MarketingQueryRequest query){
        return bookingSaleStatisticsMapper.fullBookingByOverview(query);
    }

    /**
     * 数据趋势
     * @param query {@link MarketingQueryRequest}
     * @return {@link FullMoneyBookingReport}
     */
    public List<FullMoneyBookingReport> queryFullMoneyBookingForTrend(MarketingQueryRequest query){
        List<FullMoneyBookingReport> fullMoneyBookingReports = new ArrayList<>();
        if (StatisticsDataType.DAY == query.getType()){
            fullMoneyBookingReports = bookingSaleStatisticsMapper.fullBookingByDay(query);
            fullMoneyBookingReports = (List<FullMoneyBookingReport>) analysisCommonService.fullDataForDay(fullMoneyBookingReports,
                    FullMoneyBookingReport.class, query);
        } else if (StatisticsDataType.WEEK == query.getType()){
            fullMoneyBookingReports = bookingSaleStatisticsMapper.fullBookingByWeek(query);
            List<MarketingAnalysisBase> baseOverviewList = KsBeanUtil.convertList(fullMoneyBookingReports, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(baseOverviewList, FullMoneyBookingReport.class, query);
            fullMoneyBookingReports = KsBeanUtil.convertList(fulledOverviewList, FullMoneyBookingReport.class);
        }
        return fullMoneyBookingReports;
    }

    /**
     * 活动效果-列表
     * @param request {@link EffectPageRequest}
     * @return {@link FullMoneyBookingReport}
     */
    public PageInfo<FullMoneyBookingReport> queryBookingByList(EffectPageRequest request){
        if (StringUtils.isBlank(request.getSortName())){
            request.setSortName("payMoney");
            request.setSortOrder("desc");
        }
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<FullMoneyBookingReport> reports = bookingSaleStatisticsMapper.fullBookingByList(request);
        PageInfo<FullMoneyBookingReport> pageInfo = new PageInfo<>(reports);
        List<String> goodsInfoIds = pageInfo.getList().stream().map(FullMoneyBookingReport::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)){
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            pageInfo.getList().forEach(v -> v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId())));
        }
        return pageInfo;
    }



    /**
     * 订金营销概况
     * @param query {@link MarketingQueryRequest}
     * @return {@link DepositBookingReport}
     */
    public DepositBookingReport queryDepositBookingOverView(MarketingQueryRequest query){
        return bookingSaleStatisticsMapper.depositBookingByOverview(query);
    }

    /**
     * 订金数据趋势
     * @param query {@link MarketingQueryRequest}
     * @return {@link DepositBookingReport}
     */
    public List<DepositBookingReport> queryDepositBookingForTrend(MarketingQueryRequest query){
        List<DepositBookingReport> depositBookingReports = new ArrayList<>();
        if (StatisticsDataType.DAY == query.getType()){
            depositBookingReports = bookingSaleStatisticsMapper.depositBookingByDay(query);
            depositBookingReports = (List<DepositBookingReport>) analysisCommonService.fullDataForDay(depositBookingReports,
                    DepositBookingReport.class, query);
        } else if (StatisticsDataType.WEEK == query.getType()){
            depositBookingReports = bookingSaleStatisticsMapper.depositBookingByWeek(query);
            Map<String, DepositBookingReport> depositBookingReportMap =
                    depositBookingReports.stream().collect(Collectors.toMap(DepositBookingReport::getWeek,
                            Function.identity()));
            List<MarketingAnalysisBase> baseOverviewList = KsBeanUtil.convertList(depositBookingReports, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(baseOverviewList, DepositBookingReport.class, query);
            depositBookingReports = KsBeanUtil.convertList(fulledOverviewList, DepositBookingReport.class);
            depositBookingReports.forEach(depositBookingReport -> {
                if (depositBookingReportMap.containsKey(depositBookingReport.getWeek())){
                    DepositBookingReport report = depositBookingReportMap.get(depositBookingReport.getWeek());
                    depositBookingReport.setPayDepositCount(report.getPayDepositCount());
                    depositBookingReport.setPayTailCount(report.getPayTailCount());
                    depositBookingReport.setConversionRates(report.getConversionRates());
                    depositBookingReport.setUvDepositRate(report.getUvDepositRate());
                }
            });
        }
        return depositBookingReports;
    }

    /**
     * 活动效果-列表
     * @param request {@link EffectPageRequest}
     * @return {@link FullMoneyBookingReport}
     */
    public PageInfo<DepositBookingReport> queryDepositBookingByList(EffectPageRequest request){
        if (StringUtils.isBlank(request.getSortName())){
            request.setSortName("payMoney");
            request.setSortOrder("desc");
        }
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<DepositBookingReport> reports = bookingSaleStatisticsMapper.depositBookingByList(request);
        PageInfo<DepositBookingReport> pageInfo = new PageInfo<>(reports);
        List<String> goodsInfoIds = pageInfo.getList().stream().map(DepositBookingReport::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)){
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            pageInfo.getList().forEach(v -> v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId())));
        }
        return pageInfo;
    }

    public Column[] getBookingSaleColumn(){
        return new Column[]{
                new Column("商品信息", new SpelColumnRender<FullMoneyBookingReport>("goodsInfoName")),
                new Column("规格", new SpelColumnRender<FullMoneyBookingReport>("specDetails")),
                new Column("sku编码", new SpelColumnRender<FullMoneyBookingReport>("goodsInfoNo")),
                new Column("ROI", (cell, object) -> {
                    FullMoneyBookingReport fullMoneyBookingReport = (FullMoneyBookingReport) object;
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
                new Column("营销支付件数", new SpelColumnRender<FullMoneyBookingReport>("payGoodsCount")),
                new Column("营销支付人数", new SpelColumnRender<FullMoneyBookingReport>("payCustomerCount")),
                new Column("营销支付订单数", new SpelColumnRender<FullMoneyBookingReport>("payTradeCount")),
                new Column("营销支付金额", new SpelColumnRender<FullMoneyBookingReport>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<FullMoneyBookingReport>("discountMoney")),
                new Column("新成交客户", new SpelColumnRender<FullMoneyBookingReport>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<FullMoneyBookingReport>("oldCustomerCount")),
                new Column("连带率", (cell, object) -> {
                    FullMoneyBookingReport fullMoneyBookingReport = (FullMoneyBookingReport) object;
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
                    FullMoneyBookingReport fullMoneyBookingReport = (FullMoneyBookingReport) object;
                    if (fullMoneyBookingReport.getPayMoney() == null || fullMoneyBookingReport.getPayCustomerCount() == null
                            || fullMoneyBookingReport.getPayCustomerCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate = fullMoneyBookingReport.getPayMoney()
                                .divide(new BigDecimal(fullMoneyBookingReport.getPayCustomerCount()), 2,
                                        RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("供货价", new SpelColumnRender<FullMoneyBookingReport>("supplyPrice")),
                new Column("浏览量", new SpelColumnRender<FullMoneyBookingReport>("pv")),
                new Column("访客数", new SpelColumnRender<FullMoneyBookingReport>("uv")),
                new Column("访问-支付转化率", (cell, object) -> {
                    FullMoneyBookingReport fullMoneyBookingReport = (FullMoneyBookingReport) object;
                    if (Objects.nonNull(fullMoneyBookingReport.getUv())
                            && fullMoneyBookingReport.getUv() > 0) {
                        BigDecimal uvPayRate = BigDecimal.valueOf(fullMoneyBookingReport.getPayCustomerCount()).divide( new BigDecimal(fullMoneyBookingReport.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvPayRate));
                    } else {
                        cell.setCellValue("-");
                    }
                })
        };
    }

    private void uploadFileOfFullMoneyBookingSale(List<FullMoneyBookingReport> records, List<String> locations,
                                         ExportQuery query, int index)
            throws Exception {
        //如果没有报表数据，则生成只有表头的excel文件
        ExcelHelper<FullMoneyBookingReport> excelHelper = new ExcelHelper<>();
        excelHelper.addSheet(
                "全款预售活动效果统计", this.getBookingSaleColumn(),
                records
        );
        String fileName = "";
        String randomData = RandomString.get().randomAlpha(4);
        if (index == 0){
            fileName = String.format("marketing/bookingsale/%s/%s全款预售活动效果统计_%s_%s-%s.xls",
                    query.getCompanyId(),
                    storeService.getStoreName(query),
                    query.getDateFrom(),
                    query.getDateTo(),
                    randomData
            );
            if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
                fileName = String.format("marketing/bookingsale/%s/%s/%s全款预售活动效果统计_%s_%s-%s.xls",
                        query.getCompanyId(),
                        MD5Util.md5Hex(Joiner.on(",").join(query.getMarketingIds())),
                        storeService.getStoreName(query),
                        query.getDateFrom(),
                        query.getDateTo(),
                        randomData
                );
            }
        } else {
            fileName = String.format("marketing/bookingsale/%s/%s全款预售活动效果统计_%s_%s(%d)-%s.xls",
                    query.getCompanyId(),
                    storeService.getStoreName(query),
                    query.getDateFrom(),
                    query.getDateTo(),
                    index,
                    randomData
            );
            if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
                fileName = String.format("marketing/bookingsale/%s/%s/%s全款预售活动效果统计_%s_%s(%d)-%s.xls",
                        query.getCompanyId(),
                        MD5Util.md5Hex(Joiner.on(",").join(query.getMarketingIds())),
                        storeService.getStoreName(query),
                        query.getDateFrom(),
                        query.getDateTo(),
                        index,
                        randomData
                );
            }
        }

        fileName = osdService.getFileRootPath().concat(fileName);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            excelHelper.write(os);
            osdService.uploadExcel(os, fileName);
            locations.add(fileName);
        }
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
        PageInfo<FullMoneyBookingReport> pageInfo = this.queryBookingByList(request);
        List<FullMoneyBookingReport> reports = pageInfo.getList();
        long total = pageInfo.getTotal();
        int i = reports.size();
        if (total > i){
            uploadFileOfFullMoneyBookingSale(reports, locations, exportQuery, 1);
        } else {
            uploadFileOfFullMoneyBookingSale(reports, locations, exportQuery, 0);
        }

        if (total != 0){
            while (i < total){
                request.setPageNum(request.getPageNum() + 1);
                pageInfo = this.queryBookingByList(request);
                reports = pageInfo.getList();
                uploadFileOfFullMoneyBookingSale(reports, locations, exportQuery, request.getPageNum() + 1);
                i += reports.size();
            }
        }
        return BaseResponse.success(locations);
    }
}
