package com.wanmi.ares.marketing.flashsale.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.marketing.appointment.model.entity.GoodsNameAndSpecDetail;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.flashsale.dao.FlashSaleStatisticsMapper;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.FlashSaleGoods;
import com.wanmi.ares.response.FlashSaleOverview;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.response.MarketingInfoResp;
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

import jakarta.annotation.Resource;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FlashSaleService implements ExportBaseService {
    @Autowired
    private FlashSaleStatisticsMapper flashSaleStatisticsMapper;

    @Autowired
    private AnalysisCommonService analysisCommonService;

    @Autowired
    private GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Resource
    private MarketingTaskRecordService marketingTaskRecordService;

    /**
     * 定时任务：合计当天所有的秒杀订单，聚合出对应的信息插入flash_sale
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Integer insertFlashSaleInfo(MarketingAnalysisJobRequest jobRequest) {
        int result = flashSaleStatisticsMapper.insertFlashSaleOverViewByTrade(jobRequest);
        jobRequest.setMarketingType(MarketingType.FLASH_SALE.name());
        marketingTaskRecordService.insert(jobRequest);

        return result;
    }

    /**
     * 秒杀活动营销概况
     *
     * @param query {@link MarketingQueryRequest}
     * @return {@link FlashSaleOverview}
     */
    public FlashSaleOverview queryFlashSaleOverView(MarketingQueryRequest query) {
        return flashSaleStatisticsMapper.queryFlashSaleOverView(query);
    }

    /**
     * 秒杀活动数据趋势图
     *
     * @param query {@link MarketingQueryRequest}
     * @return {@link FlashSaleOverview}
     */
    public List<FlashSaleOverview> queryFlashSaleTrend(MarketingQueryRequest query) {
        List<FlashSaleOverview> flashSaleOverviews = new ArrayList<>();
        if (StatisticsDataType.DAY == query.getType()) {
            flashSaleOverviews = flashSaleStatisticsMapper.queryFlashSaleByDay(query);
            // 没有日期数据的自动补0
            flashSaleOverviews = (List<FlashSaleOverview>) analysisCommonService.fullDataForDay(flashSaleOverviews, FlashSaleOverview.class, query);
        } else if (StatisticsDataType.WEEK == query.getType()) {
            flashSaleOverviews = flashSaleStatisticsMapper.queryFlashSaleByWeek(query);
            // 没有日期数据的自动补0
            List<MarketingAnalysisBase> baseOverviewList = KsBeanUtil.convertList(flashSaleOverviews, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(baseOverviewList, FlashSaleOverview.class, query);
            flashSaleOverviews = KsBeanUtil.copyListProperties(fulledOverviewList, FlashSaleOverview.class);
        }
        return flashSaleOverviews;
    }

    /**
     * 分页查询秒杀活动-选择活动组件
     *
     * @param request
     * @return
     */
    public List<MarketingInfoResp> pageForFlashSale(SelectMarketingRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize(), false);
        List<MarketingInfoResp> flashSaleList = flashSaleStatisticsMapper.queryFlashSaleList(request);
        return flashSaleList;
    }

    /**
     * 查询秒杀活动总数-选择活动组件
     *
     * @param request
     * @return
     */
    public Long countTotal(SelectMarketingRequest request) {
        return flashSaleStatisticsMapper.countFlashSale(request);
    }

    /**
     * 分页查询秒杀活动效果-活动效果
     *
     * @param request
     * @return
     */
    public PageInfo<FlashSaleGoods> pageGoodsForFlashSales(EffectPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        if (StringUtils.isBlank(request.getSortName())) {
            request.setSortName("pay_money");
            request.setSortOrder("desc");
        }
        List<FlashSaleGoods> flashSaleList = flashSaleStatisticsMapper.queryGoodsForFlashSales(request);
        if (CollectionUtils.isNotEmpty(flashSaleList)) {
            List<String> goodsInfoIds = flashSaleList.stream().map(FlashSaleGoods::getGoodsInfoId).collect(Collectors.toList());
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            flashSaleList.forEach(flashSaleGoods -> flashSaleGoods.setSpecDetails(specDetailMap.get(flashSaleGoods.getGoodsInfoId())));
        }
        PageInfo<FlashSaleGoods> pageInfo = new PageInfo<>(flashSaleList);
        return pageInfo;
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getReportColumn(){
        return new Column[]{
                new Column("商品信息 ", new SpelColumnRender<FlashSaleGoods>("goodsInfoName")),
                new Column("规格", (cell, object) -> {
                    FlashSaleGoods flashSaleGoods = (FlashSaleGoods) object;
                    if (StringUtils.isBlank(flashSaleGoods.getSpecDetails())) {
                        cell.setCellValue("-");
                    } else {
                        cell.setCellValue(flashSaleGoods.getSpecDetails());
                    }
                }),
                new Column("sku编码", new SpelColumnRender<FlashSaleGoods>("goodsInfoNo")),
                new Column("支付ROI", (cell, object) -> {
                    FlashSaleGoods flashSaleGoods = (FlashSaleGoods) object;
                    if (Objects.nonNull(flashSaleGoods.getDiscountMoney())
                            && flashSaleGoods.getDiscountMoney().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal pyaROI = flashSaleGoods.getPayMoney().divide(flashSaleGoods.getDiscountMoney(), 2, RoundingMode.DOWN);
                        cell.setCellValue(pyaROI.setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("营销支付件数", new SpelColumnRender<FlashSaleGoods>("payGoodsCount")),
                new Column("营销支付人数", new SpelColumnRender<FlashSaleGoods>("payCustomerCount")),
                new Column("营销支付订单数", new SpelColumnRender<FlashSaleGoods>("payTradeCount")),
                new Column("营销支付金额", new SpelColumnRender<FlashSaleGoods>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<FlashSaleGoods>("discountMoney")),
                new Column("新成交客户", new SpelColumnRender<FlashSaleGoods>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<FlashSaleGoods>("oldCustomerCount")),
                new Column("连带率", (cell, object) -> {
                    FlashSaleGoods flashSaleGoods = (FlashSaleGoods) object;
                    if (Objects.nonNull(flashSaleGoods.getPayTradeCount())
                            && flashSaleGoods.getPayTradeCount() > 0) {
                        double jointRate = flashSaleGoods.getPayGoodsCount().doubleValue() / flashSaleGoods.getPayTradeCount().doubleValue();
                        cell.setCellValue( BigDecimal.valueOf(jointRate).setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("客单价", new SpelColumnRender<FlashSaleGoods>("customerPrice")),
                new Column("供货价", new SpelColumnRender<FlashSaleGoods>("supplyPrice")),
                new Column("浏览量", new SpelColumnRender<FlashSaleGoods>("pv")),
                new Column("访客数", new SpelColumnRender<FlashSaleGoods>("uv")),
                new Column("访问-支付转化率", (cell, object) -> {
                    FlashSaleGoods flashSaleGoods = (FlashSaleGoods) object;
                    if (Objects.nonNull(flashSaleGoods.getUv())
                            && flashSaleGoods.getUv() > 0) {
                        BigDecimal uvPayRate = BigDecimal.valueOf(flashSaleGoods.getPayCustomerCount()).divide( new BigDecimal(flashSaleGoods.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvPayRate));
                    } else {
                        cell.setCellValue("-");
                    }
                })

        };
    }

    /**
     * 秒杀活动效果报表构造
     */
    private void analysisReportSheet(List<FlashSaleGoods> flashSaleGoodsList, ExcelHelper excelHelper) {
        excelHelper.addSheet("秒杀活动统计", getReportColumn(), flashSaleGoodsList);
    }

    /**
     * 计算页码
     *
     * @param count
     * @param size
     * @return
     */
    private long calPage(long count, int size) {
        long page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }

    /**
     * 分割list
     *
     * @param collection 目标集合
     * @param maxSize    分页数量
     * @param splitSize  分割值
     * @return
     */
    public static <T> List<Collection> splitList(Collection<T> collection, int maxSize, int splitSize) {
        if (org.springframework.util.CollectionUtils.isEmpty(collection)) return Collections.emptyList();
        return Stream.iterate(0, f -> f + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> collection.parallelStream().skip(a * (long)splitSize).limit(splitSize).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
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
        String commonFileName = String.format("marketing/flashsale/%s/%s/%s/%s秒杀活动统计报表_%s_%s-%s",
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
        Long total = flashSaleStatisticsMapper.countExportFlashSales(effectRequest);
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
            long num = i * (long)exportQuery.getSize();
            effectRequest.setBeginIndex(num);
            List<FlashSaleGoods> dataList = flashSaleStatisticsMapper.exportFlashSales(effectRequest);
            if (CollectionUtils.isEmpty(dataList)) {
                return BaseResponse.success(fileUrl);
            }

            //空数据的属性赋0
            dataList.parallelStream().forEach(MarketingAnalysisBase::init);
            // 聚合出商品Id
            List<String> goodsInfoIds = dataList.stream().map(FlashSaleGoods::getGoodsInfoId).collect(Collectors.toList());
            List<FutureTask<List<GoodsNameAndSpecDetail>>> futureTasks = new ArrayList<>();
            List<GoodsNameAndSpecDetail> nameAndSpecDetails = new ArrayList<>();
            List<Collection> splitList = this.splitList(goodsInfoIds, 5, exportQuery.getSize() / 5);
            // 异步查询商品名称，规格
            for (int n = 0; n < splitList.size(); n++) {
                List skuIds = (List) splitList.get(n);
                FutureTask<List<GoodsNameAndSpecDetail>> futureTask = new FutureTask<>(
                        new Callable<List<GoodsNameAndSpecDetail>>() {
                            @Override
                            public List<GoodsNameAndSpecDetail> call() {
                                return goodsInfoSpecDetailRelMapper.findGoodsNameAndSpecDetail(skuIds);
                            }
                        }
                );
                futureTasks.add(futureTask);
                new Thread(futureTask).start();
            }
            futureTasks.forEach(v -> {
                try {
                    nameAndSpecDetails.addAll(v.get());
                } catch (ExecutionException e) {
                    log.error("nameAndSpecDetails任务执行出错:{}",e.getStackTrace());
                } catch (InterruptedException e) {
                    log.error("nameAndSpecDetails任务执行-线程池异常", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            });
            // 得到map<goodsInfoId, goodsNameAndSpecDetail>
            Map<String, GoodsNameAndSpecDetail> nameAndSpecDetailMap
                    = nameAndSpecDetails.stream().collect(Collectors.toMap(GoodsNameAndSpecDetail::getGoodsInfoId, Function.identity()));
            dataList.parallelStream().forEach(v -> {
                GoodsNameAndSpecDetail nameAndSpecDetail = nameAndSpecDetailMap.get(v.getGoodsInfoId());
                v.setGoodsInfoName(nameAndSpecDetail.getGoodsInfoName());
                v.setGoodsInfoNo(nameAndSpecDetail.getGoodsInfoNo());
                v.setSpecDetails(nameAndSpecDetail.getSpecDetail());
            });

            ExcelHelper excelHelper = new ExcelHelper();
            this.analysisReportSheet(dataList, excelHelper);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.write(byteArrayOutputStream);
            //如果超过一页，文件名后缀增加(索引值)
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
}
