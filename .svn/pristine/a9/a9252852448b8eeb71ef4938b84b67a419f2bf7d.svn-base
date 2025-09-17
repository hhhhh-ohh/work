package com.wanmi.ares.marketing.reducediscountgift.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.ares.enums.MarketingStatus;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.marketing.reducediscountgift.dao.MarketingReduceDiscountGiftMapper;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.response.MarketingInfoResp;
import com.wanmi.ares.response.MarketingReduceDiscountGiftReport;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.DateUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 减折赠
 * @author edz
 */
@Service
public class MarketingReduceDiscountGiftService implements ExportBaseService {

    @Autowired
    private MarketingReduceDiscountGiftMapper marketingReduceDiscountGiftMapper;

    @Autowired
    protected AnalysisCommonService analysisCommonService;

    @Autowired
    protected GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Resource
    protected OsdService osdService;

    @Autowired
    protected StoreService storeService;

    @Resource
    private MarketingTaskRecordService marketingTaskRecordService;

    /**
     * 按订单与营销维度根据指定时间/默认昨天统计:营销支付金额 + 营销优惠金额 + 营销支付订单数 + 营销支付人数 + 营销支付件数 + 新老客户(平台/商家)
     * @param jobRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertMarketingStatisticsDay(MarketingAnalysisJobRequest jobRequest){
        int result = marketingReduceDiscountGiftMapper.insertMarketingStatisticsDay(jobRequest);
        jobRequest.setMarketingType(jobRequest.getMarketingType().toUpperCase());
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
        return marketingReduceDiscountGiftMapper.insertTradeMarketingSkuDetailDay(jobRequest);
    }

    /**
     * 查询营销活动
     * @param request
     * @return
     */
    public List<MarketingInfoResp> findMarketing(SelectMarketingRequest request){
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize(), false);
        List<MarketingInfoResp> marketingInfoRespList = marketingReduceDiscountGiftMapper.findMarketing(request);
        marketingInfoRespList.forEach(info -> {
            LocalDateTime startDatetime = DateUtil.convertDate(info.getStartDatetime());
            LocalDateTime endDatetime = DateUtil.convertDate(info.getEndDatetime());
            info.setStatus(convertMarketingStatus(startDatetime, endDatetime));
            if (info.getPauseFlag() == DefaultFlag.YES && info.getStatus() == MarketingStatus.STARTED) {
                info.setStatus(MarketingStatus.PAUSED);
            }
        });
        return marketingInfoRespList;
    }

    /**
     * 查询营销活动总数
     * @param request
     * @return
     */
    public Long findMarketingTotal(SelectMarketingRequest request){
        return marketingReduceDiscountGiftMapper.countByPageTotal(request);
    }

    /**
     * 营销概况
     * @param request
     * @return
     */
    public MarketingReduceDiscountGiftReport getMarketingOverviewByStoreIdAndMarketing(MarketingQueryRequest request){
        return marketingReduceDiscountGiftMapper.getMarketingOverviewByStoreIdAndMarketing(request);
    }

    /**
     * 数据趋势
     * @param request
     * @return
     */
    public List<MarketingReduceDiscountGiftReport> getMarketingTrendChartByStoreIdAndMarketing(MarketingQueryRequest request){
        List<MarketingReduceDiscountGiftReport> marketingReduceDiscountGiftReportList = new ArrayList<>();
        if (StatisticsDataType.DAY == request.getType()){
            marketingReduceDiscountGiftReportList = marketingReduceDiscountGiftMapper.getMarketingTrendChartDayByStoreIdAndMarketing(request);
            marketingReduceDiscountGiftReportList = (List<MarketingReduceDiscountGiftReport>) analysisCommonService.fullDataForDay(marketingReduceDiscountGiftReportList,
                    MarketingReduceDiscountGiftReport.class, request);
        } else if (StatisticsDataType.WEEK == request.getType()){
            marketingReduceDiscountGiftReportList = marketingReduceDiscountGiftMapper.getMarketingTrendChartWeekByStoreIdAndMarketing(request);
            List<MarketingAnalysisBase> baseOverviewList = KsBeanUtil.convertList(marketingReduceDiscountGiftReportList, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(baseOverviewList, MarketingReduceDiscountGiftReport.class, request);
            marketingReduceDiscountGiftReportList = KsBeanUtil.convertList(fulledOverviewList, MarketingReduceDiscountGiftReport.class);
        }
        return marketingReduceDiscountGiftReportList;
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
                "减折赠活动效果统计",
                getColumn(),
                records
        );
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("marketing/reducediscountgift/%s/%s减折赠活动效果统计_%s_%s-%s.xls",
                query.getCompanyId(),
                storeService.getStoreName(query),
                query.getDateFrom(),
                query.getDateTo(),
                randomData
        );
        if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
            fileName = String.format("marketing/reducediscountgift/%s/%s/%s减折赠活动效果统计_%s_%s-%s.xls",
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

    /**
     * 根据活动开始时间、结束时间转换活动状态
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    public MarketingStatus convertMarketingStatus(LocalDateTime startDatetime,LocalDateTime endDatetime){
        MarketingStatus marketingStatus = null;
        if (LocalDateTime.now().isBefore(startDatetime)) {
            marketingStatus = MarketingStatus.NOT_START;
        } else if (LocalDateTime.now().isAfter(endDatetime)) {
            marketingStatus = MarketingStatus.ENDED;
        } else if (LocalDateTime.now().isAfter(startDatetime) && LocalDateTime.now().isBefore(endDatetime)) {
            marketingStatus = MarketingStatus.STARTED;
        }
        return marketingStatus;
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
        request.setMarketingType(MarketingType.REDUCTION_DISCOUNT_GIFT);
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
}
