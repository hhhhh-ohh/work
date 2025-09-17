package com.wanmi.ares.marketing.groupon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.MarketingStatus;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.marketing.groupon.dao.GrouponStatisticsMapper;
import com.wanmi.ares.marketing.marketingtaskrecord.service.MarketingTaskRecordService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.GrouponOverview;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.response.MarketingInfoResp;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.IteratorUtils;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jakarta.annotation.Resource;

import jodd.util.RandomString;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class GrouponService implements ExportBaseService {

    @Autowired
    private GrouponStatisticsMapper grouponStatisticsMapper;

    @Autowired
    private AnalysisCommonService analysisCommonService;

    @Autowired
    private OsdService osdService;

    @Autowired
    private GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Autowired
    private StoreService storeService;

    @Resource
    private MarketingTaskRecordService marketingTaskRecordService;

    /**
     * 拉取订单信息
     */
    @Transactional
    public long infoForDay(MarketingAnalysisJobRequest jobRequest) {
        long result = grouponStatisticsMapper.insertByTrade(jobRequest);
        jobRequest.setMarketingType(MarketingType.GROUPON.name());
        marketingTaskRecordService.insert(jobRequest);
        return result;
    }

    /**
     * 营销概况
     *
     * @param query {@link MarketingQueryRequest}
     * @return {@link GrouponOverview}
     */
    public GrouponOverview queryGrouponOverView(MarketingQueryRequest query) {
        GrouponOverview grouponOverview = grouponStatisticsMapper.grouponByOverview(query);
        GrouponOverview shareCountByOverview = grouponStatisticsMapper.grouponShareCountByOverview(query);
        grouponOverview.setShareCount(shareCountByOverview.getShareCount());
        grouponOverview.setShareVisitorsCount(shareCountByOverview.getShareVisitorsCount());
        return grouponOverview;
    }

    /**
     * 数据趋势
     *
     * @param query {@link MarketingQueryRequest}
     * @return {@link GrouponOverview}
     */
    public List<GrouponOverview> queryGrouponTrend(MarketingQueryRequest query) {
        List<GrouponOverview> grouponOverviews = new ArrayList<>();
        if (StatisticsDataType.DAY == query.getType()) {
            grouponOverviews = grouponStatisticsMapper.grouponByDay(query);
            //分享人数
            List<GrouponOverview> grouponShareCountByDay = grouponStatisticsMapper.grouponShareCountByDay(query);
            IteratorUtils.zip(grouponOverviews, grouponShareCountByDay,
                    (grouponOverview, shareCount) ->
                            grouponOverview.getDay().equals(shareCount.getDay())
                    ,
                    (grouponOverview, shareCount) -> {
                        grouponOverview.setShareCount(shareCount.getShareCount());
                        grouponOverview.setShareVisitorsCount(shareCount.getShareVisitorsCount());
                    });
            grouponOverviews = (List<GrouponOverview>) analysisCommonService.fullDataForDay(grouponOverviews,
                    GrouponOverview.class, query);
        } else if (StatisticsDataType.WEEK == query.getType()) {
            grouponOverviews = grouponStatisticsMapper.grouponByWeek(query);
            //分享人数
            List<GrouponOverview> grouponShareCountByDay = grouponStatisticsMapper.grouponShareCountByWeek(query);
            IteratorUtils.zip(grouponOverviews, grouponShareCountByDay,
                    (grouponOverview, shareCount) ->
                            grouponOverview.getWeek().equals(shareCount.getWeek())
                    ,
                    (grouponOverview, shareCount) -> {
                        grouponOverview.setShareCount(shareCount.getShareCount());
                        grouponOverview.setShareVisitorsCount(shareCount.getShareVisitorsCount());
                    });
            List<GrouponOverview> grouponOverviewsTemp = grouponOverviews;
            List<MarketingAnalysisBase> baseOverviewList = KsBeanUtil.convertList(grouponOverviews, MarketingAnalysisBase.class);
            List<MarketingAnalysisBase> fulledOverviewList =
                    analysisCommonService.fullOverviewForWeek(baseOverviewList, GrouponOverview.class, query);
            grouponOverviews = KsBeanUtil.convertList(fulledOverviewList, GrouponOverview.class);
            IteratorUtils.zip(grouponOverviews, grouponOverviewsTemp,
                    (grouponOverview1, grouponOverview2) ->
                            grouponOverview1.getWeek().equals(grouponOverview2.getWeek())
                    ,
                    (grouponOverview1, grouponOverview2) -> {
                        grouponOverview1.setShareCount(grouponOverview2.getShareCount());
                        grouponOverview1.setShareVisitorsCount(grouponOverview2.getShareVisitorsCount());
                        grouponOverview1.setShareGrouponCount(grouponOverview2.getShareGrouponCount());
                        grouponOverview1.setGrouponTradeCount(grouponOverview2.getGrouponTradeCount());
                        grouponOverview1.setGrouponPersonCount(grouponOverview2.getGrouponPersonCount());
                        grouponOverview1.setAlreadyGrouponTradeCount(grouponOverview2.getAlreadyGrouponTradeCount());
                        grouponOverview1.setAlreadyGrouponPersonCount(grouponOverview2.getAlreadyGrouponPersonCount());
                        grouponOverview1.setGrouponRoi(grouponOverview2.getGrouponRoi());
                        grouponOverview1.setAlreadyGrouponRoi(grouponOverview2.getAlreadyGrouponRoi());
                        grouponOverview1.setUvGrouponRoi(grouponOverview2.getUvGrouponRoi());
                    });
        }
        return grouponOverviews;
    }

    /**
     * 营销活动列表（分页）
     *
     * @param request
     * @return
     */
    public List<MarketingInfoResp> findMarketing(SelectMarketingRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize(), false);
        List<MarketingInfoResp> marketingInfoResps = grouponStatisticsMapper.queryGrouponList(request);
        marketingInfoResps.forEach((info) -> {
            LocalDateTime startDatetime = LocalDateTime.ofInstant(info.getStartDatetime().toInstant(), ZoneId.systemDefault());
            LocalDateTime endDatetime = LocalDateTime.ofInstant(info.getEndDatetime().toInstant(), ZoneId.systemDefault());
            if (LocalDateTime.now().isBefore(startDatetime)) {
                info.setStatus(MarketingStatus.NOT_START);
            } else if (LocalDateTime.now().isAfter(endDatetime)) {
                info.setStatus(MarketingStatus.ENDED);
            } else if (LocalDateTime.now().isAfter(startDatetime) && LocalDateTime.now().isBefore(endDatetime)) {
                info.setStatus(MarketingStatus.STARTED);
            }

        });
        return marketingInfoResps;
    }

    /**
     * 营销活动数量
     *
     * @param request
     * @return
     */
    public Long findMarketingTotal(SelectMarketingRequest request) {
        return grouponStatisticsMapper.countByPageTotal(request);
    }

    /**
     * 营销活动效果列表（分页）
     *
     * @param request
     * @return
     */
    public PageInfo<GrouponOverview> effectPage(EffectPageRequest request) {
        if (StringUtils.isBlank(request.getSortName())){
            request.setSortName("payMoney");
            request.setSortOrder("desc");
        }
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<GrouponOverview> list = grouponStatisticsMapper.grouponByEffect(request);
        PageInfo<GrouponOverview> pageInfo = new PageInfo<>(list);
        List<String> goodsInfoIds = pageInfo.getList().stream().map(GrouponOverview::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            List<GoodsInfoSpecDetailRel> specDetailRelList = goodsInfoSpecDetailRelMapper.queryDetailNameGroupByGoodsId(goodsInfoIds, 0);
            Map<String, String> specDetailMap = specDetailRelList.stream()
                    .collect(Collectors.toMap(GoodsInfoSpecDetailRel::getGoodsInfoId, GoodsInfoSpecDetailRel::getDetailName));
            pageInfo.getList().forEach(v -> {
                v.setSpecDetails(specDetailMap.get(v.getGoodsInfoId()));
            });
        }
        return pageInfo;
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getGrouponColumn() {
        return new Column[]{
                new Column("商品信息", new SpelColumnRender<GrouponOverview>("goodsInfoName")),
                new Column("规格信息", (cell, object) -> {
                    GrouponOverview grouponOverview = (GrouponOverview) object;
                    if (StringUtils.isBlank(grouponOverview.getSpecDetails())){
                        cell.setCellValue("-");
                    } else {
                        cell.setCellValue(grouponOverview.getSpecDetails());
                    }
                }),
                new Column("sku编码", new SpelColumnRender<GrouponOverview>("goodsInfoNo")),
                new Column("拼团人数", new SpelColumnRender<GrouponOverview>("grouponPersonCount")),
                new Column("拼团订单数", new SpelColumnRender<GrouponOverview>("grouponTradeCount")),
                new Column("成团人数", new SpelColumnRender<GrouponOverview>("alreadyGrouponPersonCount")),
                new Column("成团订单数", new SpelColumnRender<GrouponOverview>("alreadyGrouponTradeCount")),
                new Column("拼团-成团转化率", (cell, object) -> {
                    GrouponOverview grouponOverview = (GrouponOverview) object;
                    if (Objects.nonNull(grouponOverview.getGrouponPersonCount())
                            && grouponOverview.getGrouponPersonCount() > 0) {
                        cell.setCellValue(AnalysisCommonService.formatPercent(grouponOverview.getGrouponRoi()));
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("支付ROI", (cell, object) -> {
                    GrouponOverview grouponOverview = (GrouponOverview) object;
                    if (Objects.nonNull(grouponOverview.getDiscountMoney())
                            && grouponOverview.getDiscountMoney().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal pyaROI = grouponOverview.getPayMoney().divide(grouponOverview.getDiscountMoney(), 2, RoundingMode.DOWN);
                        cell.setCellValue(pyaROI.setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("拼团-成团转化率", new SpelColumnRender<GrouponOverview>("grouponRoi")),
                new Column("支付ROI", (cell, object) -> {
                    GrouponOverview grouponOverview = (GrouponOverview) object;
                    if (Objects.nonNull(grouponOverview.getDiscountMoney())
                            && grouponOverview.getDiscountMoney().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal pyaROI = grouponOverview.getPayMoney().divide(grouponOverview.getDiscountMoney(), 2, RoundingMode.DOWN);
                        cell.setCellValue(pyaROI.setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("营销支付金额", new SpelColumnRender<GrouponOverview>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<GrouponOverview>("discountMoney")),
                new Column("营销支付人数", new SpelColumnRender<GrouponOverview>("payCustomerCount")),
                new Column("新成交客户", new SpelColumnRender<GrouponOverview>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<GrouponOverview>("oldCustomerCount")),
                new Column("发起分享人数", new SpelColumnRender<GrouponOverview>("shareCount")),
                new Column("分享访客数", new SpelColumnRender<GrouponOverview>("shareVisitorsCount")),
                new Column("分享参团数", new SpelColumnRender<GrouponOverview>("shareGrouponCount")),
                new Column("客单价", new SpelColumnRender<GrouponOverview>("customerPrice")),
                new Column("供货价", new SpelColumnRender<GrouponOverview>("supplyPrice")),
                new Column("浏览量", new SpelColumnRender<GrouponOverview>("pv")),
                new Column("访客数", new SpelColumnRender<GrouponOverview>("uv")),
                new Column("访问-拼团转化率", (cell, object) -> {
                    GrouponOverview grouponOverview = (GrouponOverview) object;
                    if (Objects.nonNull(grouponOverview.getUv())
                            && grouponOverview.getUv() > 0) {
                        BigDecimal uvGrouponRoi = BigDecimal.valueOf(grouponOverview.getGrouponPersonCount()).divide(BigDecimal.valueOf(grouponOverview.getUv()), 2, RoundingMode.DOWN);
                        cell.setCellValue(uvGrouponRoi.setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("成团率", (cell, object) -> {
                    GrouponOverview grouponOverview = (GrouponOverview) object;
                    if (Objects.nonNull(grouponOverview.getUv())
                            && grouponOverview.getUv() > 0) {
                        BigDecimal alreadyGrouponRoi = BigDecimal.valueOf(grouponOverview.getAlreadyGrouponPersonCount()).divide(BigDecimal.valueOf(grouponOverview.getUv()), 2, RoundingMode.DOWN);
                        cell.setCellValue(alreadyGrouponRoi.setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),

        };
    }

    /**
     * 设置Excel信息
     *
     * @param grouponOverviews
     */
    private void setExcel(List<GrouponOverview> grouponOverviews, ExcelHelper excelHelper) {
        excelHelper.addSheet("拼团活动效果报表", getGrouponColumn(), grouponOverviews);
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

    @Override
    public BaseResponse exportReport(ExportQuery query) throws Exception {
        if (StringUtils.isBlank(query.getCompanyId())) {
            query.setCompanyId(Constants.BOSS_ID);
        }
        List<String> fileList = new ArrayList<>();
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("marketing/groupon/%s/%s/%s/%s拼团活动效果报表_%s_%s-%s",
                DateUtil.format(LocalDate.now().minusDays(1L), DateUtil.FMT_MONTH_2),
                query.getCompanyId(),
                query.getMd5HexParams(),
                storeService.getStoreName(query),
                query.getDateFrom(),
                LocalDate.now().minusDays(1L),
                randomData
        );
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        EffectPageRequest request = new EffectPageRequest();
        request.setQueryDate(query.getDateFrom());
        request.setGrouponMarketingIds(query.getGrouponMarketingIds());
        request.setStoreId(query.getStoreId());
        request.setSortName(query.getSortName());
        request.setSortOrder(query.getSortOrder());
        request.setPageSize(query.getSize());
        if (StringUtils.isBlank(query.getSortName())) {
            request.setSortName("payMoney");
            request.setSortOrder("desc");
        }
        long total = grouponStatisticsMapper.countByEffectTotal(request);
        if (total == 0) {
            String fileName = String.format("%s.xls", commonFileName);
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper excelHelper = new ExcelHelper();
                setExcel(Lists.newArrayList(), excelHelper);
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, fileName);
            }
            fileList.add(fileName);
            return BaseResponse.success(fileList);
        }
        long fileSize = calPage(total, request.getPageSize());
        PageInfo<GrouponOverview> pageInfo;
        for (int i = 0; i < fileSize; i++) {
            pageInfo = this.effectPage(request);
            ExcelHelper excelHelper = new ExcelHelper();
            pageInfo.getList().forEach(MarketingAnalysisBase::init);
            this.setExcel(pageInfo.getList(), excelHelper);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.write(byteArrayOutputStream);
            //如果超过一页，文件名后缀增加(索引值)
            String suffix = StringUtils.EMPTY;
            if (fileSize > 1) {
                suffix = "(".concat(String.valueOf(i + 1)).concat(")");
            }
            String fileName = String.format("%s%s.xls", commonFileName, suffix);
            osdService.uploadExcel(byteArrayOutputStream, fileName);
            fileList.add(fileName);
            request.setPageNum(request.getPageNum() + 1);
        }
        return BaseResponse.success(fileList);
    }
}
