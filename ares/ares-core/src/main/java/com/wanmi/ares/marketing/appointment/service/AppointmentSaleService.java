package com.wanmi.ares.marketing.appointment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.wanmi.ares.common.DataPeriod;
import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.ares.enums.MarketingStatus;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.marketing.appointment.dao.AppointmentStatisticsMapper;
import com.wanmi.ares.marketing.appointment.model.entity.CountAppointmentForDay;
import com.wanmi.ares.marketing.appointment.model.entity.CountAppointmentForWeek;
import com.wanmi.ares.marketing.appointment.model.entity.GoodsNameAndSpecDetail;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.AppointmentAnalysisForGoods;
import com.wanmi.ares.response.AppointmentAnalysisOverview;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.response.MarketingInfoResp;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
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

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class AppointmentSaleService implements ExportBaseService {
    @Autowired
    private AppointmentStatisticsMapper appointmentStatisticsMapper;

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
     * 分页查询预约活动
     *
     * @param request
     * @return
     */
    public List<MarketingInfoResp> pageForAppointmentSale(SelectMarketingRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize(), false);
        List<MarketingInfoResp> appointmentSaleList = appointmentStatisticsMapper.getAppointmentSaleList(request);
        appointmentSaleList.forEach(this::buildStatus);
        return appointmentSaleList;
    }

    public Long countTotal(SelectMarketingRequest request) {
        return appointmentStatisticsMapper.countByPageTotal(request);
    }

    public void buildStatus(MarketingInfoResp appointmentSale) {
        LocalDateTime startDatetime = LocalDateTime.ofInstant(appointmentSale.getStartDatetime().toInstant(), ZoneId.systemDefault());
        LocalDateTime endDatetime = LocalDateTime.ofInstant(appointmentSale.getEndDatetime().toInstant(), ZoneId.systemDefault());
        if (startDatetime.isAfter(LocalDateTime.now())) {
            appointmentSale.setStatus(MarketingStatus.NOT_START);
        } else if (startDatetime.isBefore(LocalDateTime.now())
                && endDatetime.isAfter(LocalDateTime.now())) {
            appointmentSale.setStatus(MarketingStatus.STARTED);
        } else if (appointmentSale.getPauseFlag() == DefaultFlag.YES) {
            appointmentSale.setStatus(MarketingStatus.PAUSED);
        } else if (endDatetime.isBefore(LocalDateTime.now())) {
            appointmentSale.setStatus(MarketingStatus.ENDED);
        }
    }

    /**
     * 定时任务：当天所有的预约订单，入库 appointment_overview
     */
    @Transactional
    public Long analysisTradeForDay(MarketingAnalysisJobRequest jobRequest) {
        //营销概况
        long result = appointmentStatisticsMapper.saveAppointmentTradeDetail(jobRequest);
        jobRequest.setMarketingType(MarketingType.APPOINTMENT_SALE.name());
        marketingTaskRecordService.insert(jobRequest);
        return result;
    }

    /**
     * 近90天的营销概况
     *
     * @param queryParams
     * @return
     */
    public AppointmentAnalysisOverview analysisOverview(MarketingQueryRequest queryParams) {
        queryParams.setPeriod(DataPeriod.period);
        AppointmentAnalysisOverview overview = appointmentStatisticsMapper.analysisOverview(queryParams);
        Long countAppointment = appointmentStatisticsMapper.countAppointment(queryParams);
        overview.setAppointmentCount(countAppointment);
        if (Objects.nonNull(overview.getUv()) && overview.getUv() > 0) {
            overview.setUvAppointmentRate(BigDecimal.valueOf(countAppointment).divide(BigDecimal.valueOf(overview.getUv()), 2, RoundingMode.DOWN));
        }
        if (countAppointment > 0){
            BigDecimal appointmentPayRate = new BigDecimal(overview.getPayCustomerCount())
                    .divide(new BigDecimal(overview.getAppointmentCount()), 4, RoundingMode.DOWN);
            overview.setAppointmentPayRate(appointmentPayRate);
        }
        return overview;
    }

    /**
     * 近90天的营销概况--趋势图
     *
     * @param queryParams
     * @return
     */
    public List<AppointmentAnalysisOverview> analysisOverviewForTrendChart(MarketingQueryRequest queryParams) {
        queryParams.setPeriod(DataPeriod.period);
        List<AppointmentAnalysisOverview> overviewList = null;
        if (StatisticsDataType.DAY == queryParams.getType()) {
            if (Objects.isNull(queryParams.getStoreId())) {
                overviewList = appointmentStatisticsMapper.analysisOverviewForDayByBoss(queryParams);
            } else {
                overviewList = appointmentStatisticsMapper.analysisOverviewForDay(queryParams);
            }

            // <日期，预约人数>
            Map<LocalDate, Long> appointmentCountMap = appointmentStatisticsMapper.countAppointmentForDay(queryParams)
                    .stream()
                    .collect(Collectors.toMap(CountAppointmentForDay::getDate, CountAppointmentForDay::getAppointmentCount));

            overviewList = (List<AppointmentAnalysisOverview>) analysisCommonService.fullDataForDay(overviewList, AppointmentAnalysisOverview.class, queryParams);

            overviewList.forEach(v -> {
                Long count = appointmentCountMap.get(v.getDay());
                v.setAppointmentCount(Objects.isNull(count) ? Long.valueOf(0) : count);
                if (Objects.nonNull(v.getUv()) && v.getUv() > 0) {
                    v.setUvAppointmentRate(BigDecimal.valueOf(v.getAppointmentCount()).divide(BigDecimal.valueOf(v.getUv()), 2, RoundingMode.DOWN));
                }
                if ( Objects.nonNull(v.getAppointmentCount()) && v.getAppointmentCount() > 0){
                    BigDecimal appointmentPayRate = new BigDecimal(v.getPayCustomerCount())
                            .divide(new BigDecimal(v.getAppointmentCount()), 4, RoundingMode.DOWN);
                    v.setAppointmentPayRate(appointmentPayRate);
                }
            });

        } else if (StatisticsDataType.WEEK == queryParams.getType()) {
            if (Objects.isNull(queryParams.getStoreId())) {
                overviewList = appointmentStatisticsMapper.analysisOverviewForWeekByBoss(queryParams);
            } else {
                overviewList = appointmentStatisticsMapper.analysisOverviewForWeek(queryParams);
            }
            List<CountAppointmentForWeek> countAppointmentForDays = appointmentStatisticsMapper.countAppointmentForWeek(queryParams);
            Map<String, CountAppointmentForWeek> countAppointmentForWeekMap = countAppointmentForDays.stream()
                    .collect(Collectors.toMap(CountAppointmentForWeek::getWeek, Function.identity()));

            List<MarketingAnalysisBase> baseOverviewList = KsBeanUtil.convertList(overviewList, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(baseOverviewList, AppointmentAnalysisOverview.class, queryParams);
            overviewList = KsBeanUtil.copyListProperties(fulledOverviewList, AppointmentAnalysisOverview.class);

            // 预约人数填充
            overviewList.forEach(v -> {
                CountAppointmentForWeek countAppointmentForWeek = countAppointmentForWeekMap.get(v.getWeek());
                v.setAppointmentCount(Objects.isNull(countAppointmentForWeek) ? Long.valueOf(0)  : countAppointmentForWeek.getAppointmentCount());
                if (Objects.nonNull(v.getUv()) && v.getUv() > 0) {
                    v.setUvAppointmentRate(BigDecimal.valueOf(v.getAppointmentCount()).divide(BigDecimal.valueOf(v.getUv()), 2, RoundingMode.DOWN));
                }
                if (Objects.nonNull(v.getAppointmentCount()) && v.getAppointmentCount() > 0){
                    BigDecimal appointmentPayRate = new BigDecimal(v.getPayCustomerCount())
                            .divide(new BigDecimal(v.getAppointmentCount()), 4, RoundingMode.DOWN);
                    v.setAppointmentPayRate(appointmentPayRate);
                }
            });
        }
        return overviewList;
    }

    public PageInfo<AppointmentAnalysisForGoods> analysisForGoods(EffectPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        if (StringUtils.isEmpty(request.getSortName())) {
            request.setSortName("pay_money");
            request.setSortOrder("desc");
        }
        List<AppointmentAnalysisForGoods> analysisForGoods = appointmentStatisticsMapper.analysisForGoods(request);
        PageInfo<AppointmentAnalysisForGoods> pageInfo = new PageInfo<>(analysisForGoods);
        List<String> goodsInfoIds = pageInfo.getList().stream().map(AppointmentAnalysisForGoods::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
        Map<String, String> specDetailMap = specDetailRelList.stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
        pageInfo.getList().forEach(v -> {
            if (Objects.isNull(v.getAppointmentCount())) {
                v.setAppointmentCount(0L);
            }
            v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId()));
        });
        return pageInfo;
    }

    /**
     * 导出预约商品营销分析报表
     *
     * @return
     */
    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws IOException {
        List<String> fileUrl = Lists.newArrayList();
        if (StringUtils.isBlank(exportQuery.getCompanyId())) {
            exportQuery.setCompanyId(Constants.BOSS_ID);
        }
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("marketing/appointment/%s/%s/%s预约活动统计报表_%s_%s-%s",
                exportQuery.getCompanyId(),
                DateUtil.format(LocalDate.now().minusDays(1L), DateUtil.FMT_MONTH_2),
                storeService.getStoreName(exportQuery),
                exportQuery.getDateFrom(),
                exportQuery.getDateTo(),
                randomData);
        if (!CollectionUtils.isEmpty(exportQuery.getMarketingIds())) {
            commonFileName = String.format("marketing/appointment/%s/%s/%s/%s预约活动统计报表_%s_%s-%s",
                    exportQuery.getCompanyId(),
                    DateUtil.format(LocalDate.now().minusDays(1L), DateUtil.FMT_MONTH_2),
                    MD5Util.md5Hex(Joiner.on(",").join(exportQuery.getMarketingIds())),
                    storeService.getStoreName(exportQuery),
                    exportQuery.getDateFrom(),
                    exportQuery.getDateTo(),
                    randomData
            );
        }
        commonFileName = osdService.getFileRootPath().concat(commonFileName);

        EffectPageRequest effectRequest = new EffectPageRequest();
        effectRequest.setQueryDate(exportQuery.getDateFrom());
        effectRequest.setStoreId(exportQuery.getStoreId());
        effectRequest.setMarketingIds(exportQuery.getMarketingIds());
        effectRequest.setGoodsInfoName(exportQuery.getGoodsInfoName());
        effectRequest.setSortName("pay_money");
        effectRequest.setSortOrder("desc");
        effectRequest.setPageSize(exportQuery.getSize());
        Long total = appointmentStatisticsMapper.countForAppointmentGoods(effectRequest);
        if (total == 0) {
            String fileName = String.format("%s.xls", commonFileName);
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper excelHelper = new ExcelHelper();
                analysisReportSheet(Lists.newArrayList(), excelHelper);
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
            List<AppointmentAnalysisForGoods> dataList = appointmentStatisticsMapper.analysisForReport(effectRequest);
            //空数据的属性赋0
            dataList.parallelStream().forEach(MarketingAnalysisBase::init);

            List<String> goodsInfoIds = dataList.stream().map(AppointmentAnalysisForGoods::getGoodsInfoId).collect(Collectors.toList());
            List<FutureTask<List<GoodsNameAndSpecDetail>>> futureTasks = new ArrayList<>();
            List<GoodsNameAndSpecDetail> nameAndSpecDetails = new ArrayList<>();
            List<Collection> splitList = splitList(goodsInfoIds, 5, exportQuery.getSize() / 5);
            //异步查询商品名称，规格
            for (int n = 0; n < splitList.size(); n++) {
                List skuIds = (List) splitList.get(n);
                FutureTask<List<GoodsNameAndSpecDetail>> futureTask = new FutureTask<>(
                        (Callable<List<GoodsNameAndSpecDetail>>) () -> goodsInfoSpecDetailRelMapper.findGoodsNameAndSpecDetail(skuIds)
                );
                futureTasks.add(futureTask);
                new Thread(futureTask).start();
            }
            futureTasks.forEach(v -> {
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

    /**
     * 导出分类
     * @return
     */
    public Column[] getReportColumn() {
        return new Column[]{
                new Column("商品信息 ", new SpelColumnRender<AppointmentAnalysisForGoods>("goodsInfoName")),
                new Column("规格", new SpelColumnRender<AppointmentAnalysisForGoods>("specDetails")),
                new Column("sku编码", new SpelColumnRender<AppointmentAnalysisForGoods>("goodsInfoNo")),
                new Column("ROI", (cell, object) -> {
                    AppointmentAnalysisForGoods appointmentAnalysisForGoods = (AppointmentAnalysisForGoods) object;
                    if (Objects.nonNull(appointmentAnalysisForGoods.getPayROI())) {
                        cell.setCellValue(appointmentAnalysisForGoods.getPayROI().doubleValue());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("预约人数", (cell, object) -> {
                    AppointmentAnalysisForGoods o = (AppointmentAnalysisForGoods) object;
                    cell.setCellValue(Objects.nonNull(o.getAppointmentCount()) ? o.getAppointmentCount() : 0L);
                }),
                new Column("营销支付件数", new SpelColumnRender<AppointmentAnalysisForGoods>("payGoodsCount")),
                new Column("营销支付人数", new SpelColumnRender<AppointmentAnalysisForGoods>("payCustomerCount")),
                new Column("营销支付订单数", new SpelColumnRender<AppointmentAnalysisForGoods>("payTradeCount")),
                new Column("营销支付金额", new SpelColumnRender<AppointmentAnalysisForGoods>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<AppointmentAnalysisForGoods>("discountMoney")),
                new Column("新成交客户", new SpelColumnRender<AppointmentAnalysisForGoods>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<AppointmentAnalysisForGoods>("oldCustomerCount")),
                new Column("连带率", (cell, object) -> {
                    AppointmentAnalysisForGoods appointmentAnalysisForGoods = (AppointmentAnalysisForGoods) object;
                    if (Objects.nonNull(appointmentAnalysisForGoods.getJointRate())) {
                        cell.setCellValue(appointmentAnalysisForGoods.getJointRate().doubleValue());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("客单价", new SpelColumnRender<AppointmentAnalysisForGoods>("customerPrice")),
                new Column("供货价", new SpelColumnRender<AppointmentAnalysisForGoods>("supplyPrice")),
                new Column("预约-支付转化率", (cell, object) -> {
                    AppointmentAnalysisForGoods appointmentAnalysisForGoods = (AppointmentAnalysisForGoods) object;
                    if (Objects.nonNull(appointmentAnalysisForGoods.getAppointmentCount())
                            && appointmentAnalysisForGoods.getAppointmentCount() > 0) {
                        BigDecimal appointmentPayRate = new BigDecimal(appointmentAnalysisForGoods.getPayCustomerCount())
                                .divide(new BigDecimal(appointmentAnalysisForGoods.getAppointmentCount()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(appointmentPayRate));
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("浏览量", new SpelColumnRender<AppointmentAnalysisForGoods>("pv")),
                new Column("访客数", new SpelColumnRender<AppointmentAnalysisForGoods>("uv")),
                new Column("访问-预约转化率", (cell, object) -> {
                    AppointmentAnalysisForGoods appointmentAnalysisForGoods = (AppointmentAnalysisForGoods) object;
                    if (Objects.nonNull(appointmentAnalysisForGoods.getUv()) && Objects.nonNull(appointmentAnalysisForGoods.getAppointmentCount())
                            && appointmentAnalysisForGoods.getUv() > 0){
                        BigDecimal uvAppointmentRate = new BigDecimal(appointmentAnalysisForGoods.getAppointmentCount())
                                .divide(new BigDecimal(appointmentAnalysisForGoods.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvAppointmentRate));
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("访问-支付转化率", (cell, object) -> {
                    AppointmentAnalysisForGoods appointmentAnalysisForGoods = (AppointmentAnalysisForGoods) object;
                    if (Objects.nonNull(appointmentAnalysisForGoods.getUv()) && Objects.nonNull(appointmentAnalysisForGoods.getPayCustomerCount())
                            && appointmentAnalysisForGoods.getUv() > 0){
                        BigDecimal uvPayRate = new BigDecimal(appointmentAnalysisForGoods.getPayCustomerCount())
                                .divide(new BigDecimal(appointmentAnalysisForGoods.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvPayRate));
                    }else{
                        cell.setCellValue("-");
                    }
                })
        };
    }

    /**
     * 预约活动效果报表构造
     */
    private void analysisReportSheet(List<AppointmentAnalysisForGoods> appointmentAnalysisForGoodsList, ExcelHelper excelHelper) {
        excelHelper.addSheet("活动效果报表", this.getReportColumn(), appointmentAnalysisForGoodsList);
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
        if (CollectionUtils.isEmpty(collection)) return Collections.emptyList();
        return Stream.iterate(0, f -> f + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> collection.parallelStream().skip(a * (long) splitSize).limit(splitSize).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
    }
}
