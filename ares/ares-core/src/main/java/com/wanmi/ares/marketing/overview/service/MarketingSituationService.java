package com.wanmi.ares.marketing.overview.service;

import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.marketing.overview.dao.MarketingSituationMapper;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.marketing.MarketingOverviewRequest;
import com.wanmi.ares.request.marketing.MarketingSituationInsertRequest;
import com.wanmi.ares.response.MarketingDataSituation;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;

import jodd.util.RandomString;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName MarketingSituationService
 * @Description 营销概览-活动数据概况统计service
 * @Author chenli
 * @Date 2021/2/17 9:36
 * @Version 1.0
 **/
@Service
public class MarketingSituationService implements ExportBaseService {

    @Autowired
    private MarketingSituationMapper marketingSituationMapper;

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void generator(LocalDate localDate) {
        dayStatistics(localDate);
        sevenStatistics(localDate);
        thirtyStatistics(localDate);
        // 每月1号执行一次
        if (LocalDate.now().getDayOfMonth() == 1) {
            monthStatistics(localDate);
        }
    }

    /**
     * 插入昨天数据
     *
     * @param localDate
     */
    public void dayStatistics(LocalDate localDate) {
        String beginTime = DateUtil.format(localDate, DateUtil.FMT_DATE_1);
        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);
        String startDate = beginTime;
        //删除老数据
        marketingSituationMapper.deleteDay(startDate);

        MarketingSituationInsertRequest request =
                new MarketingSituationInsertRequest();
        request.setStartDate(startDate);
        request.setBeginTime(beginTime);
        request.setEndTime(endTime);
        marketingSituationMapper.saveBossFlashDay(request);
        marketingSituationMapper.saveBossAppointmentDay(request);
        marketingSituationMapper.saveBossBookingDay(request);
        marketingSituationMapper.saveBossGrouponDay(request);
        marketingSituationMapper.saveBossCouponDay(request);
        marketingSituationMapper.saveBossFullSeriesDay(request);

        marketingSituationMapper.saveSupplierFlashDay(request);
        marketingSituationMapper.saveSupplierAppointmentDay(request);
        marketingSituationMapper.saveSupplierBookingDay(request);
        marketingSituationMapper.saveSupplierGrouponDay(request);
        marketingSituationMapper.saveSupplierCouponDay(request);
        marketingSituationMapper.saveSupplierFullSeriesDay(request);

        //打包一口价
        this.wrapper(request, MarketingType.BUYOUT_PRICE);
        marketingSituationMapper.saveBossCommonDay(request);
        marketingSituationMapper.saveSupplierCommonDay(request);
        //第二件半价
        this.wrapper(request, MarketingType.HALF_PRICE_SECOND_PIECE);
        marketingSituationMapper.saveBossCommonDay(request);
        marketingSituationMapper.saveSupplierCommonDay(request);
        //组合购
        this.wrapper(request, MarketingType.SUITS);
        marketingSituationMapper.saveBossCommonDay(request);
        marketingSituationMapper.saveSupplierCommonDay(request);
        //加价购
        this.wrapper(request, MarketingType.PREFERENTIAL);
        marketingSituationMapper.saveBossCommonDay(request);
        marketingSituationMapper.saveSupplierCommonDay(request);
        //砍价
        this.wrapper(request, MarketingType.GOODS_BARGAIN);
        marketingSituationMapper.saveBossBargainDay(request);
        marketingSituationMapper.saveSupplierBargainDay(request);
    }

    /**
     * 插入最近7天数据
     *
     * @param localDate
     */
    public void sevenStatistics(LocalDate localDate) {
        //删除老数据
        marketingSituationMapper.deleteSeven();
        String beginTime = DateUtil.format(localDate.minusDays(6), DateUtil.FMT_DATE_1);
        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);
        MarketingSituationInsertRequest request =
                new MarketingSituationInsertRequest();
        request.setBeginTime(beginTime);
        request.setEndTime(endTime);
        marketingSituationMapper.saveBossFlashSeven(request);
        marketingSituationMapper.saveBossAppointmentSeven(request);
        marketingSituationMapper.saveBossBookingSeven(request);
        marketingSituationMapper.saveBossGrouponSeven(request);
        marketingSituationMapper.saveBossCouponSeven(request);
        marketingSituationMapper.saveBossFullSeriesSeven(request);

        marketingSituationMapper.saveSupplierFlashSeven(request);
        marketingSituationMapper.saveSupplierAppointmentSeven(request);
        marketingSituationMapper.saveSupplierBookingSeven(request);
        marketingSituationMapper.saveSupplierGrouponSeven(request);
        marketingSituationMapper.saveSupplierCouponSeven(request);
        marketingSituationMapper.saveSupplierFullSeriesSeven(request);
        //打包一口价，第二件半价，组合购，加价购
        request.setTable("marketing_situation_seven");
        this.commonInsert(request);
        //砍价
        this.wrapper(request, MarketingType.GOODS_BARGAIN);
        marketingSituationMapper.saveBossBargainCommon(request);
        marketingSituationMapper.saveSupplierBargainCommon(request);
    }

    /**
     * 插入最近30天数据
     *
     * @param localDate
     */
    public void thirtyStatistics(LocalDate localDate) {
        //删除老数据
        marketingSituationMapper.deleteThirty();

        String beginTime = DateUtil.format(localDate.minusDays(29), DateUtil.FMT_DATE_1);
        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);

        MarketingSituationInsertRequest request =
                new MarketingSituationInsertRequest();
        request.setBeginTime(beginTime);
        request.setEndTime(endTime);
        marketingSituationMapper.saveBossFlashThirty(request);
        marketingSituationMapper.saveBossAppointmentThirty(request);
        marketingSituationMapper.saveBossBookingThirty(request);
        marketingSituationMapper.saveBossGrouponThirty(request);
        marketingSituationMapper.saveBossCouponThirty(request);
        marketingSituationMapper.saveBossFullSeriesThirty(request);

        marketingSituationMapper.saveSupplierFlashThirty(request);
        marketingSituationMapper.saveSupplierAppointmentThirty(request);
        marketingSituationMapper.saveSupplierBookingThirty(request);
        marketingSituationMapper.saveSupplierGrouponThirty(request);
        marketingSituationMapper.saveSupplierCouponThirty(request);
        marketingSituationMapper.saveSupplierFullSeriesThirty(request);
        //打包一口价，第二件半价，组合购，加价购
        request.setTable("marketing_situation_thirty");
        this.commonInsert(request);
        //砍价
        this.wrapper(request, MarketingType.GOODS_BARGAIN);
        marketingSituationMapper.saveBossBargainCommon(request);
        marketingSituationMapper.saveSupplierBargainCommon(request);
    }

    /**
     * 插入自然月数据
     *
     * @param localDate
     */
    public void monthStatistics(LocalDate localDate) {
        String startMonth = DateUtil.format(LocalDate.now().minusMonths(1), DateUtil.FMT_MONTH_2);
        String beginTime = DateUtil.format(LocalDate.now().minusMonths(1), DateUtil.FMT_DATE_1);
        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);
        //删除老数据
        marketingSituationMapper.deleteMonth(startMonth);

        MarketingSituationInsertRequest request =
                new MarketingSituationInsertRequest();
        request.setBeginTime(beginTime);
        request.setEndTime(endTime);
        marketingSituationMapper.saveBossFlashMonth(request);
        marketingSituationMapper.saveBossAppointmentMonth(request);
        marketingSituationMapper.saveBossBookingMonth(request);
        marketingSituationMapper.saveBossGrouponMonth(request);
        marketingSituationMapper.saveBossCouponMonth(request);
        marketingSituationMapper.saveBossFullSeriesMonth(request);

        marketingSituationMapper.saveSupplierFlashMonth(request);
        marketingSituationMapper.saveSupplierAppointmentMonth(request);
        marketingSituationMapper.saveSupplierBookingMonth(request);
        marketingSituationMapper.saveSupplierGrouponMonth(request);
        marketingSituationMapper.saveSupplierCouponMonth(request);
        marketingSituationMapper.saveSupplierFullSeriesMonth(request);

        //打包一口价
        this.wrapper(request, MarketingType.BUYOUT_PRICE);
        marketingSituationMapper.saveBossCommonMonth(request);
        marketingSituationMapper.saveSupplierCommonMonth(request);
        //第二件半价
        this.wrapper(request, MarketingType.HALF_PRICE_SECOND_PIECE);
        marketingSituationMapper.saveBossCommonMonth(request);
        marketingSituationMapper.saveSupplierCommonMonth(request);
        //组合购
        this.wrapper(request, MarketingType.SUITS);
        marketingSituationMapper.saveBossCommonMonth(request);
        marketingSituationMapper.saveSupplierCommonMonth(request);
        //加价购
        this.wrapper(request, MarketingType.PREFERENTIAL);
        marketingSituationMapper.saveBossCommonMonth(request);
        marketingSituationMapper.saveSupplierCommonMonth(request);
        //砍价
        this.wrapper(request, MarketingType.GOODS_BARGAIN);
        marketingSituationMapper.saveBossBargainMonth(request);
        marketingSituationMapper.saveSupplierBargainMonth(request);
    }

    /**
     * 查询营销概览-活动数据概况
     *
     * @param request
     * @return
     */
    public List<MarketingDataSituation> findMarketingDataSituationList(MarketingOverviewRequest request) {
        if (StringUtils.isBlank(request.getSortName())) {
            request.setSortName("pay_money");
            request.setSortOrder("desc");
        }
        List<MarketingDataSituation> marketingDataSituationList = new ArrayList<>();
        switch (request.getStatisticsDataType()) {
            case YESTERDAY:
                marketingDataSituationList = marketingSituationMapper.findByDay(request);
                break;
            case SEVEN:
                marketingDataSituationList = marketingSituationMapper.findBySeven(request);
                break;
            case THIRTY:
                marketingDataSituationList = marketingSituationMapper.findByThirty(request);
                break;
            case MONTH:
                marketingDataSituationList = marketingSituationMapper.findByMonth(request);
                break;
            default:
                break;
        }
        return marketingDataSituationList;
    }


    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws Exception {
        // 默认商家id
        if (StringUtils.isBlank(exportQuery.getCompanyId())) {
            exportQuery.setCompanyId(Constants.BOSS_ID);
        }
        // 默认排序
        if (StringUtils.isBlank(exportQuery.getSortName())) {
            exportQuery.setSortName("pay_money");
            exportQuery.setSortOrder("desc");
        }

        // 组装查询列表的条件对象
        MarketingOverviewRequest request = new MarketingOverviewRequest();
        request.setStatisticsDataType(exportQuery.getStatisticsDataType());
        request.setStoreId(exportQuery.getStoreId());
        request.setSortName(exportQuery.getSortName());
        request.setSortOrder(exportQuery.getSortOrder());
        request.setMonth(exportQuery.getMonth());

        // 报表开始时间
        String startDate = "";
        // 报表结束时间
        String endDate = "";

        // 查询结果list
        List<MarketingDataSituation> marketingDataSituationList = new ArrayList<>();
        // 根据导出日期类型判断，设置开始、结束时间
        switch (request.getStatisticsDataType()) {
            case YESTERDAY:
                startDate = LocalDate.now().minusDays(1L).toString();
                marketingDataSituationList = marketingSituationMapper.findByDay(request);
                break;
            case SEVEN:
                startDate = LocalDate.now().minusDays(7L).toString();
                endDate = "_" + LocalDate.now().minusDays(1L);
                marketingDataSituationList = marketingSituationMapper.findBySeven(request);
                break;
            case THIRTY:
                startDate = LocalDate.now().minusDays(30L).toString();
                endDate = "_" + LocalDate.now().minusDays(1L);
                marketingDataSituationList = marketingSituationMapper.findByThirty(request);
                break;
            case MONTH:
                startDate = request.getMonth();
                marketingDataSituationList = marketingSituationMapper.findByMonth(request);
                break;
            default:
                break;
        }

        // 导出内容
        ExcelHelper<MarketingDataSituation> excelHelper = new ExcelHelper<>();
        // 文件路径 如：/marketing/overview/2021-02/0/md5参数加密/某店铺_营销概览_2020-10-30_2021-01-27.xls
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("marketing/overview/%s/%s/%s/%s营销概览报表_%s%s-%s.xls",
                DateUtil.format(LocalDate.now().minusDays(1L), DateUtil.FMT_MONTH_2),
                exportQuery.getCompanyId(),
                exportQuery.getMd5HexParams(),
                storeService.getStoreName(exportQuery),
                startDate,
                endDate,
                randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);

        if (CollectionUtils.isEmpty(marketingDataSituationList)) {
            // 如果该文件已存在，则直接返回
            if (osdService.existsFiles(commonFileName)) {
                return BaseResponse.success(commonFileName);
            }
        }else {
            // 判断是否门店导出、门店导出暂时只导出满折增和优惠券数据
            if (StoreSelectType.O2O == exportQuery.getStoreSelectType()) {
                marketingDataSituationList = marketingDataSituationList
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(marketing-> MarketingType.COUPON == marketing.getMarketingType()
                                || MarketingType.REDUCTION_DISCOUNT_GIFT ==  marketing.getMarketingType())
                        .collect(Collectors.toList());
            }
        }
        excelHelper.addSheet(
                "营销概览",
                getColumn(),
                marketingDataSituationList
        );

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            excelHelper.write(os);
            osdService.uploadExcel(os, commonFileName);
        }

        return BaseResponse.success(commonFileName);
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getColumn(){
        return new Column[]{
                new Column("活动类型", (cell, object) -> {
                    MarketingDataSituation marketingDataSituation = (MarketingDataSituation) object;
                    // 0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售
                    switch (marketingDataSituation.getMarketingType()) {
                        case COUPON:
                            cell.setCellValue("优惠券");
                            break;
                        case GROUPON:
                            cell.setCellValue("拼团");
                            break;
                        case FLASH_SALE:
                            cell.setCellValue("秒杀");
                            break;
                        case REDUCTION_DISCOUNT_GIFT:
                            cell.setCellValue("减折赠");
                            break;
                        case APPOINTMENT_SALE:
                            cell.setCellValue("预约");
                            break;
                        case BOOKING_SALE:
                            cell.setCellValue("预售");
                            break;
                        case BUYOUT_PRICE:
                            cell.setCellValue("打包一口价");
                            break;
                        case HALF_PRICE_SECOND_PIECE:
                            cell.setCellValue("第二件半价");
                            break;
                        case SUITS:
                            cell.setCellValue("组合购");
                            break;
                        case GOODS_BARGAIN:
                            cell.setCellValue("砍价");
                            break;
                        case PREFERENTIAL:
                            cell.setCellValue("加价购");
                            break;
                        default:
                            break;
                    }
                }),
                new Column("活动数量", new SpelColumnRender<MarketingDataSituation>("marketingActivityCount")),
                // 支付ROI：统计时间内，营销支付金额 / 营销优惠金额
                new Column("支付ROI", (cell, object) -> {
                    MarketingDataSituation marketingDataSituation = (MarketingDataSituation) object;
                    if (Objects.nonNull(marketingDataSituation.getDiscountMoney())
                            && marketingDataSituation.getDiscountMoney().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal pyaROI = marketingDataSituation.getPayMoney().divide(marketingDataSituation.getDiscountMoney(), 2, RoundingMode.DOWN);
                        cell.setCellValue(pyaROI.setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("营销支付金额", new SpelColumnRender<MarketingDataSituation>("payMoney")),
                new Column("营销优惠金额", new SpelColumnRender<MarketingDataSituation>("discountMoney")),
                new Column("营销支付人数", new SpelColumnRender<MarketingDataSituation>("payCustomerCount")),
                new Column("营销支付件数", new SpelColumnRender<MarketingDataSituation>("payGoodsCount")),
                new Column("营销支付订单数", new SpelColumnRender<MarketingDataSituation>("payTradeCount")),
                new Column("新成交客户", new SpelColumnRender<MarketingDataSituation>("newCustomerCount")),
                new Column("老成交客户", new SpelColumnRender<MarketingDataSituation>("oldCustomerCount")),
                //连带率：统计时间内，营销支付件数 / 营销支付订单数
                new Column("连带率", (cell, object) -> {
                    MarketingDataSituation marketingDataSituation = (MarketingDataSituation) object;
                    if (Objects.nonNull(marketingDataSituation.getPayTradeCount())
                            && marketingDataSituation.getPayTradeCount() > 0) {
                        double jointRate = marketingDataSituation.getPayGoodsCount().doubleValue() / marketingDataSituation.getPayTradeCount().doubleValue();
                        cell.setCellValue(BigDecimal.valueOf(jointRate).setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                //客单价：统计时间内，营销支付金额/营销支付人数
                new Column("客单价", (cell, object) -> {
                    MarketingDataSituation marketingDataSituation = (MarketingDataSituation) object;
                    if (Objects.nonNull(marketingDataSituation.getPayCustomerCount())
                            && marketingDataSituation.getPayCustomerCount() > 0) {
                        double jointRate = marketingDataSituation.getPayMoney().doubleValue() / marketingDataSituation.getPayCustomerCount().doubleValue();
                        cell.setCellValue(BigDecimal.valueOf(jointRate).setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                }),
                new Column("浏览量", new SpelColumnRender<MarketingDataSituation>("pv")),
                new Column("访客数", new SpelColumnRender<MarketingDataSituation>("uv")),
                new Column("访问-支付转化率", (cell, object) -> {
                    MarketingDataSituation marketingDataSituation = (MarketingDataSituation) object;
                    if (Objects.nonNull(marketingDataSituation.getUv())
                            && marketingDataSituation.getUv() > 0) {
                        double uvPayRate = marketingDataSituation.getPayCustomerCount().doubleValue() / marketingDataSituation.getUv().doubleValue();
                        cell.setCellValue(BigDecimal.valueOf(uvPayRate).setScale(2, RoundingMode.DOWN).toString());
                    } else {
                        cell.setCellValue("-");
                    }
                })
        };
    }

    /**
     * @description 7天、30天维度数据插入
     * @author  张文昌
     * @date 2022/10/24 下午5:12
     * @return
     */
    public void commonInsert(MarketingSituationInsertRequest request){
        //打包一口价
        this.wrapper(request, MarketingType.BUYOUT_PRICE);
        marketingSituationMapper.saveBossCommon(request);
        marketingSituationMapper.saveSupplierCommon(request);
        //第二件半价
        this.wrapper(request, MarketingType.HALF_PRICE_SECOND_PIECE);
        marketingSituationMapper.saveBossCommon(request);
        marketingSituationMapper.saveSupplierCommon(request);
        //组合购
        this.wrapper(request, MarketingType.SUITS);
        marketingSituationMapper.saveBossCommon(request);
        marketingSituationMapper.saveSupplierCommon(request);
        //加价购
        this.wrapper(request, MarketingType.PREFERENTIAL);
        marketingSituationMapper.saveBossCommon(request);
        marketingSituationMapper.saveSupplierCommon(request);
    }

    /**
     * @description 构建入库类型-查询类型
     * @author  张文昌
     * @date 2022/10/24 下午5:47
     * @return
     */
    public void wrapper(MarketingSituationInsertRequest request,
                     MarketingType marketingType){
        request.setTargetMarketingType(marketingType.toValue());
        switch (marketingType){
            case BUYOUT_PRICE:
                request.setMarketingType(3);
                break;
            case HALF_PRICE_SECOND_PIECE:
                request.setMarketingType(4);
                break;
            case SUITS:
                request.setMarketingType(6);
                break;
            case GOODS_BARGAIN:
                request.setMarketingType(11);
                break;
            case PREFERENTIAL:
                request.setMarketingType(8);
                break;
            default:
                break;
        }
    }
}
