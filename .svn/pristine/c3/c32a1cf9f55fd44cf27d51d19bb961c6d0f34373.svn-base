package com.wanmi.ares.marketing.bookingsale.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.wanmi.ares.marketing.bookingsale.dao.BookingSaleStatisticsMapper;
import com.wanmi.ares.marketing.commonservice.AnalysisCommonService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.goods.dao.GoodsInfoSpecDetailRelMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.response.DepositBookingReport;
import com.wanmi.ares.response.FullMoneyBookingReport;
import com.wanmi.ares.source.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.ares.source.service.StoreService;
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

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DepositBookingSaleService implements ExportBaseService {
    @Autowired
    private BookingSaleStatisticsMapper bookingSaleStatisticsMapper;

    @Autowired
    private GoodsInfoSpecDetailRelMapper goodsInfoSpecDetailRelMapper;

    @Resource
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

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

    /**
     * 导出列字段
     * @return
     */
    public Column[] getDepositBookingColumn(){
        return new Column[]{
                new Column("商品信息", new SpelColumnRender<DepositBookingReport>("goodsInfoName")),
                new Column("规格", new SpelColumnRender<DepositBookingReport>("specDetails")),
                new Column("sku编码", new SpelColumnRender<DepositBookingReport>("goodsInfoNo")),
                new Column("ROI", (cell, object) -> {
                    DepositBookingReport depositBookingReport = (DepositBookingReport) object;
                    if (depositBookingReport.getDiscountMoney() == null ||
                            depositBookingReport.getDiscountMoney().compareTo(BigDecimal.ZERO) == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate =
                                depositBookingReport.getPayMoney().divide(depositBookingReport.getDiscountMoney(), 2, RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("定金支付人数", new SpelColumnRender<DepositBookingReport>("payDepositCount")),
                new Column("尾款支付人数", new SpelColumnRender<DepositBookingReport>("payTailCount")),
                new Column("定金-尾款转化率", (cell, object) -> {
                    DepositBookingReport depositBookingReport = (DepositBookingReport) object;
                    if (depositBookingReport.getPayTailCount() == null || depositBookingReport.getPayTailCount() == 0
                            || depositBookingReport.getPayDepositCount() == null || depositBookingReport.getPayDepositCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate = new BigDecimal(depositBookingReport.getPayTailCount())
                                .divide(new BigDecimal(depositBookingReport.getPayDepositCount()), 4,
                                        RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(rate));
                    }
                }),
                new Column("营销支付件数", new SpelColumnRender<DepositBookingReport>("payGoodsCount")),
                new Column("营销支付订单数", new SpelColumnRender<DepositBookingReport>("payTradeCount")),
                new Column("营销支付金额", new SpelColumnRender<DepositBookingReport>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<DepositBookingReport>("discountMoney")),
                new Column("新成交客户", new SpelColumnRender<DepositBookingReport>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<DepositBookingReport>("oldCustomerCount")),
                new Column("连带率", (cell, object) -> {
                    DepositBookingReport depositBookingReport = (DepositBookingReport) object;
                    if (depositBookingReport.getPayTradeCount() == null || depositBookingReport.getPayTradeCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate = new BigDecimal(depositBookingReport.getPayGoodsCount())
                                .divide(new BigDecimal(depositBookingReport.getPayTradeCount()), 2,
                                        RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("客单价", (cell, object) -> {
                    DepositBookingReport depositBookingReport = (DepositBookingReport) object;
                    if (depositBookingReport.getPayMoney() == null || depositBookingReport.getPayMoney().compareTo(BigDecimal.ZERO) == 0
                            || depositBookingReport.getPayTailCount() == null || depositBookingReport.getPayTailCount() == 0){
                        cell.setCellValue("-");
                    } else {
                        BigDecimal rate =depositBookingReport.getPayMoney()
                                .divide(new BigDecimal(depositBookingReport.getPayTailCount()), 2,
                                        RoundingMode.DOWN);
                        cell.setCellValue(rate.toString());
                    }
                }),
                new Column("供货价", new SpelColumnRender<DepositBookingReport>("supplyPrice")),
                new Column("浏览量", new SpelColumnRender<DepositBookingReport>("pv")),
                new Column("访客数", new SpelColumnRender<DepositBookingReport>("uv")),
                new Column("访问-定金转化率", (cell, object) -> {
                    DepositBookingReport depositBookingReport = (DepositBookingReport) object;
                    if (Objects.nonNull(depositBookingReport.getUv())
                            && depositBookingReport.getUv() > 0) {
                        BigDecimal uvDepositRate = BigDecimal.valueOf(depositBookingReport.getPayDepositCount()).divide( new BigDecimal(depositBookingReport.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvDepositRate));
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("访问-支付转化率", (cell, object) -> {
                    DepositBookingReport depositBookingReport = (DepositBookingReport) object;
                    if (Objects.nonNull(depositBookingReport.getUv())
                            && depositBookingReport.getUv() > 0) {
                        BigDecimal uvPayRate = BigDecimal.valueOf(depositBookingReport.getPayTailCount()).divide( new BigDecimal(depositBookingReport.getUv()), 4, RoundingMode.DOWN);
                        cell.setCellValue(AnalysisCommonService.formatPercent(uvPayRate));
                    } else {
                        cell.setCellValue("-");
                    }
                })
        };
    }

    private void uploadFileOfDepositBookingSale(List<DepositBookingReport> records, List<String> locations,
                                             ExportQuery query, int index)
            throws Exception {
        //如果没有报表数据，则生成只有表头的excel文件
        ExcelHelper<DepositBookingReport> excelHelper = new ExcelHelper<>();
        excelHelper.addSheet(
                "全款预售活动效果统计",
                getDepositBookingColumn(),
                records
        );

        String fileName = "";
        String randomData = RandomString.get().randomAlpha(4);
        if (index == 0){
            fileName = String.format("marketing/bookingsale/%s/%s定金预售活动效果统计_%s_%s-%s.xls",
                    query.getCompanyId(),
                    storeService.getStoreName(query),
                    query.getDateFrom(),
                    query.getDateTo(),
                    randomData
            );
            if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
                fileName = String.format("marketing/bookingsale/%s/%s/%s定金预售活动效果统计_%s_%s-%s.xls",
                        query.getCompanyId(),
                        MD5Util.md5Hex(Joiner.on(",").join(query.getMarketingIds())),
                        storeService.getStoreName(query),
                        query.getDateFrom(),
                        query.getDateTo(),
                        randomData
                );
            }
        } else {
            fileName = String.format("marketing/bookingsale/%s/%s定金预售活动效果统计_%s_%s(%d)-%s.xls",
                    query.getCompanyId(),
                    storeService.getStoreName(query),
                    query.getDateFrom(),
                    query.getDateTo(),
                    index,
                    randomData
            );
            if (CollectionUtils.isNotEmpty(query.getMarketingIds())) {
                fileName = String.format("marketing/bookingsale/%s/%s/%s定金预售活动效果统计_%s_%s(%d)-%s.xls",
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
        PageInfo<DepositBookingReport> pageInfo = this.queryDepositBookingByList(request);
        List<DepositBookingReport> reports = pageInfo.getList();
        long total = pageInfo.getTotal();
        int i = reports.size();
        if (total > i){
            uploadFileOfDepositBookingSale(reports, locations, exportQuery, 1);
        } else {
            uploadFileOfDepositBookingSale(reports, locations, exportQuery, 0);
        }
        if (total != 0){
            while (i < total){
                request.setPageNum(request.getPageNum() + 1);
                pageInfo = this.queryDepositBookingByList(request);
                reports = pageInfo.getList();
                uploadFileOfDepositBookingSale(reports, locations, exportQuery, request.getPageNum() + 1);
                i += reports.size();
            }
        }
        return BaseResponse.success(locations);
    }
}
