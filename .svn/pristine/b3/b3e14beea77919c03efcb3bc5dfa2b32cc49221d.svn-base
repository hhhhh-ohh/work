package com.wanmi.ares.marketing.bargain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.MarketingStatus;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.marketing.appointment.model.entity.GoodsNameAndSpecDetail;
import com.wanmi.ares.marketing.bargain.dao.GoodsBargainStatisticsMapper;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.*;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jodd.util.RandomString;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wur
 * @className GoodsBargainService
 * @description TODO
 * @date 2022/8/23 11:31
 */
@Service
@Slf4j
public class GoodsBargainService implements ExportBaseService {

    @Autowired GoodsBargainStatisticsMapper goodsBargainStatisticsMapper;

    @Autowired MarketingTaskRecordService marketingTaskRecordService;

    @Autowired AnalysisCommonService analysisCommonService;

    @Autowired private StoreService storeService;

    @Autowired private OsdService osdService;

    @Autowired GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    /** 定时任务：合计当天所有的砍价订单，聚合出对应的信息插入bargain_sale */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Integer insertBargainGoodsInfo(MarketingAnalysisJobRequest jobRequest) {
        int result = goodsBargainStatisticsMapper.insertBargainGoodsOverViewByTrade(jobRequest);
        jobRequest.setMarketingType(MarketingType.GOODS_BARGAIN.name());
        marketingTaskRecordService.insert(jobRequest);
        return result;
    }

    /**
     * 查询活动 列表
     *
     * @param request
     * @return
     */
    public List<MarketingInfoResp> findMarketing(SelectMarketingRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize(), false);
        List<MarketingInfoResp> marketingInfoResps =
                goodsBargainStatisticsMapper.queryBargainGoodsList(request);
        marketingInfoResps.forEach(
                (info) -> {
                    LocalDateTime startDatetime =
                            LocalDateTime.ofInstant(
                                    info.getStartDatetime().toInstant(), ZoneId.systemDefault());
                    LocalDateTime endDatetime =
                            LocalDateTime.ofInstant(
                                    info.getEndDatetime().toInstant(), ZoneId.systemDefault());
                    if (LocalDateTime.now().isBefore(startDatetime)) {
                        info.setStatus(MarketingStatus.NOT_START);
                    } else if (LocalDateTime.now().isAfter(endDatetime)) {
                        info.setStatus(MarketingStatus.ENDED);
                    } else if (LocalDateTime.now().isAfter(startDatetime)
                            && LocalDateTime.now().isBefore(endDatetime)) {
                        info.setStatus(MarketingStatus.STARTED);
                    }
                });
        return marketingInfoResps;
    }

    /**
     * 查询活动总数
     *
     * @param request
     * @return
     */
    public Long findMarketingTotal(SelectMarketingRequest request) {
        return goodsBargainStatisticsMapper.countByPageTotal(request);
    }

    /**
     * 砍价活动营销概况
     *
     * @param query {@link MarketingQueryRequest}
     */
    public GoodsBargainReport queryBargainGoodsOverView(MarketingQueryRequest query) {
        return goodsBargainStatisticsMapper.queryBargainGoodsOverView(query);
    }

    /**
     * 活动效果
     * @param query
     * @return
     */
    public PageInfo<GoodsBargainReport> queryGoodsForBargainGoods(EffectPageRequest query) {
        if (StringUtils.isBlank(query.getSortName())){
            query.setSortName("payMoney");
            query.setSortOrder("desc");
        }
        PageHelper.startPage(query.getPageNum() + 1, query.getPageSize());
        List<GoodsBargainReport> reports = goodsBargainStatisticsMapper.queryGoodsForBargainGoods(query);
        PageInfo<GoodsBargainReport> goodsBargainPageInfo = new PageInfo<>(reports);
        List<String> goodsInfoIds = goodsBargainPageInfo.getList().stream().map(GoodsBargainReport::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)){
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            goodsBargainPageInfo.getList().forEach(v -> v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId())));
        }
        return goodsBargainPageInfo;
    }

    /**
     * 活动数据趋势图
     *
     * @param query {@link MarketingQueryRequest}
     */
    public List<GoodsBargainOverview> queryBargainTrend(MarketingQueryRequest query) {
        List<GoodsBargainOverview> bargainOverviews = new ArrayList<>();
        if (StatisticsDataType.DAY == query.getType()) {
            bargainOverviews = goodsBargainStatisticsMapper.queryBargainByDay(query);
            // 没有日期数据的自动补0
            bargainOverviews =
                    (List<GoodsBargainOverview>)
                            analysisCommonService.fullDataForDay(
                                    bargainOverviews, GoodsBargainOverview.class, query);
        } else if (StatisticsDataType.WEEK == query.getType()) {
            bargainOverviews = goodsBargainStatisticsMapper.queryBargainByWeek(query);
            // 没有日期数据的自动补0
            List<MarketingAnalysisBase> baseOverviewList =
                    KsBeanUtil.convertList(bargainOverviews, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(
                            baseOverviewList, GoodsBargainOverview.class, query);
            bargainOverviews =
                    KsBeanUtil.copyListProperties(fulledOverviewList, GoodsBargainOverview.class);
        }
        return bargainOverviews;
    }

    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws Exception {
        List<String> fileUrl = Lists.newArrayList();
        if (StringUtils.isBlank(exportQuery.getCompanyId())) {
            exportQuery.setCompanyId(Constants.BOSS_ID);
        }
        if (StringUtils.isBlank(exportQuery.getSortName())) {
            exportQuery.setSortName("pay_money");
            exportQuery.setSortOrder("desc");
        }

        // 文件路径 如：/marketing/flashsale/2021-01/0/md5参数加密/某店铺_秒杀活动统计报表_2020-10-30_2021-01-27.xls
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName =
                String.format(
                        "marketing/bargain/%s/%s/%s/%s砍价活动统计报表_%s_%s-%s",
                        DateUtil.format(LocalDate.now().minusDays(1L), DateUtil.FMT_MONTH_2),
                        exportQuery.getCompanyId(),
                        exportQuery.getMd5HexParams(),
                        storeService.getStoreName(exportQuery),
                        exportQuery.getDateFrom(),
                        LocalDate.now().minusDays(1L),
                        randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);

        EffectPageRequest effectRequest = new EffectPageRequest();
        effectRequest.setQueryDate(exportQuery.getDateFrom());
        effectRequest.setStoreId(exportQuery.getStoreId());
        effectRequest.setMarketingIds(exportQuery.getMarketingIds());
        effectRequest.setGoodsInfoName(exportQuery.getGoodsInfoName());
        effectRequest.setSortName(exportQuery.getSortName());
        effectRequest.setSortOrder(exportQuery.getSortOrder());
        effectRequest.setPageSize(exportQuery.getSize());
        Long total = goodsBargainStatisticsMapper.countExportBargain(effectRequest);
        if (total == 0) {
            String fileName = String.format("%s.xls", commonFileName);
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper excelHelper = new ExcelHelper();
                this.analysisReportSheet(Lists.newArrayList(), excelHelper);
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, fileName);
            }
            fileUrl.add(fileName);
            return BaseResponse.success(fileUrl);
        }
        long fileSize = calPage(total, exportQuery.getSize());
        for (int i = 0; i < fileSize; i++) {
            long num = i * (long) exportQuery.getSize();
            effectRequest.setBeginIndex(num);
            List<GoodsBargainReport> dataList =
                    goodsBargainStatisticsMapper.exportBargain(effectRequest);
            if (CollectionUtils.isEmpty(dataList)) {
                return BaseResponse.success(fileUrl);
            }

            // 空数据的属性赋0
            dataList.parallelStream().forEach(MarketingAnalysisBase::init);
            // 聚合出商品Id
            List<String> goodsInfoIds =
                    dataList.stream()
                            .map(GoodsBargainReport::getGoodsInfoId)
                            .collect(Collectors.toList());
            List<FutureTask<List<GoodsNameAndSpecDetail>>> futureTasks = new ArrayList<>();
            List<GoodsNameAndSpecDetail> nameAndSpecDetails = new ArrayList<>();
            List<Collection> splitList = splitList(goodsInfoIds, 5, exportQuery.getSize() / 5);
            // 异步查询商品名称，规格
            for (int n = 0; n < splitList.size(); n++) {
                List skuIds = (List) splitList.get(n);
                FutureTask<List<GoodsNameAndSpecDetail>> futureTask =
                        new FutureTask<>(
                                new Callable<List<GoodsNameAndSpecDetail>>() {
                                    @Override
                                    public List<GoodsNameAndSpecDetail> call() {
                                        return goodsInfoSpecDetailRelMapper
                                                .findGoodsNameAndSpecDetail(skuIds);
                                    }
                                });
                futureTasks.add(futureTask);
                new Thread(futureTask).start();
            }
            futureTasks.forEach(
                    v -> {
                        try {
                            nameAndSpecDetails.addAll(v.get());
                        } catch (ExecutionException e) {
                            log.error("nameAndSpecDetails任务执行出错:{}", e.getStackTrace());
                        } catch (InterruptedException e) {
                            log.error("nameAndSpecDetails任务执行-线程池异常", e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    });
            // 得到map<goodsInfoId, goodsNameAndSpecDetail>
            Map<String, GoodsNameAndSpecDetail> nameAndSpecDetailMap =
                    nameAndSpecDetails.stream()
                            .collect(
                                    Collectors.toMap(
                                            GoodsNameAndSpecDetail::getGoodsInfoId,
                                            Function.identity()));
            dataList.parallelStream()
                    .forEach(
                            v -> {
                                GoodsNameAndSpecDetail nameAndSpecDetail =
                                        nameAndSpecDetailMap.get(v.getGoodsInfoId());
                                v.setGoodsInfoName(nameAndSpecDetail.getGoodsInfoName());
                                v.setGoodsInfoNo(nameAndSpecDetail.getGoodsInfoNo());
                                v.setSpecDetails(nameAndSpecDetail.getSpecDetail());
                            });

            ExcelHelper excelHelper = new ExcelHelper();
            this.analysisReportSheet(dataList, excelHelper);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.write(byteArrayOutputStream);
            // 如果超过一页，文件名后缀增加(索引值)
            String suffix = StringUtils.EMPTY;
            if (fileSize > 1) {
                suffix = "(".concat(String.valueOf(i + 1)).concat(")");
            }
            String fileName = String.format("%s%s.xls", commonFileName, suffix);
            osdService.uploadExcel(byteArrayOutputStream, fileName);
            fileUrl.add(fileName);
        }
        return BaseResponse.success(fileUrl);
    }

    private void analysisReportSheet(List<GoodsBargainReport> goodsList, ExcelHelper excelHelper) {
        excelHelper.addSheet("砍价活动统计", getReportColumn(), goodsList);
    }

    private long calPage(long count, int size) {
        long page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }

    public Column[] getReportColumn() {
        return new Column[] {
            new Column("商品信息 ", new SpelColumnRender<GoodsBargainReport>("goodsInfoName")),
            new Column("sku编码", new SpelColumnRender<GoodsBargainReport>("goodsInfoNo")),
            new Column(
                    "规格",
                    (cell, object) -> {
                        GoodsBargainReport flashSaleGoods = (GoodsBargainReport) object;
                        if (StringUtils.isBlank(flashSaleGoods.getSpecDetails())) {
                            cell.setCellValue("-");
                        } else {
                            cell.setCellValue(flashSaleGoods.getSpecDetails());
                        }
                    }),
            new Column(
                    "支付ROI",
                    (cell, object) -> {
                        GoodsBargainReport flashSaleGoods = (GoodsBargainReport) object;
                        if (Objects.nonNull(flashSaleGoods.getDiscountMoney())
                                && flashSaleGoods.getDiscountMoney().compareTo(BigDecimal.ZERO)
                                        > 0) {
                            BigDecimal pyaROI =
                                    flashSaleGoods
                                            .getPayMoney()
                                            .divide(
                                                    flashSaleGoods.getDiscountMoney(),
                                                    2,
                                                    RoundingMode.DOWN);
                            cell.setCellValue(pyaROI.setScale(2, RoundingMode.DOWN).toString());
                        } else {
                            cell.setCellValue("-");
                        }
                    }),
            new Column("营销支付件数", new SpelColumnRender<GoodsBargainReport>("payGoodsCount")),
            new Column("营销支付人数", new SpelColumnRender<GoodsBargainReport>("payCustomerCount")),
            new Column("营销支付订单数", new SpelColumnRender<GoodsBargainReport>("payTradeCount")),
            new Column("营销支付金额", new SpelColumnRender<GoodsBargainReport>("payMoney")),
            new Column("营销优惠金额", new SpelColumnRender<GoodsBargainReport>("discountMoney")),
            new Column("新成交客户", new SpelColumnRender<GoodsBargainReport>("newCustomerCount")),
            new Column("老成交客户", new SpelColumnRender<GoodsBargainReport>("oldCustomerCount")),
            new Column(
                    "连带率",
                    (cell, object) -> {
                        GoodsBargainReport flashSaleGoods = (GoodsBargainReport) object;
                        if (Objects.nonNull(flashSaleGoods.getPayTradeCount())
                                && flashSaleGoods.getPayTradeCount() > 0) {
                            double jointRate =
                                    flashSaleGoods.getPayGoodsCount().doubleValue()
                                            / flashSaleGoods.getPayTradeCount().doubleValue();
                            cell.setCellValue(
                                    BigDecimal.valueOf(jointRate)
                                            .setScale(2, RoundingMode.DOWN)
                                            .toString());
                        } else {
                            cell.setCellValue("-");
                        }
                    }),
            new Column("客单价", new SpelColumnRender<GoodsBargainReport>("customerPrice")),
            new Column("供货价", new SpelColumnRender<GoodsBargainReport>("supplyPrice")),
            new Column("浏览量", new SpelColumnRender<GoodsBargainReport>("pv")),
            new Column("访客数", new SpelColumnRender<GoodsBargainReport>("uv")),
            new Column(
                    "访问-支付转化率",
                    (cell, object) -> {
                        GoodsBargainReport flashSaleGoods = (GoodsBargainReport) object;
                        if (Objects.nonNull(flashSaleGoods.getUv()) && flashSaleGoods.getUv() > 0) {
                            BigDecimal uvPayRate =
                                    BigDecimal.valueOf(flashSaleGoods.getPayCustomerCount())
                                            .divide(
                                                    new BigDecimal(flashSaleGoods.getUv()),
                                                    4,
                                                    RoundingMode.DOWN);
                            cell.setCellValue(AnalysisCommonService.formatPercent(uvPayRate));
                        } else {
                            cell.setCellValue("-");
                        }
                    })
        };
    }

    public static <T> List<Collection> splitList(
            Collection<T> collection, int maxSize, int splitSize) {
        if (org.springframework.util.CollectionUtils.isEmpty(collection))
            return Collections.emptyList();
        return Stream.iterate(0, f -> f + 1)
                .limit(maxSize)
                .parallel()
                .map(
                        a ->
                                collection.parallelStream()
                                        .skip(a * (long) splitSize)
                                        .limit(splitSize)
                                        .collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
    }
}
