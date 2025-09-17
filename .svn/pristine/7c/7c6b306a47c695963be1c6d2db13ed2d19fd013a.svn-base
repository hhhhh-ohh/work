package com.wanmi.ares.scheduled.marketing;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.marketing.appointment.service.AppointmentSaleService;
import com.wanmi.ares.marketing.bargain.GoodsBargainService;
import com.wanmi.ares.marketing.bookingsale.service.BookingSaleService;
import com.wanmi.ares.marketing.flashsale.service.FlashSaleService;
import com.wanmi.ares.marketing.groupon.service.GrouponService;
import com.wanmi.ares.marketing.reducediscountgift.service.MarketingReduceDiscountGiftService;
import com.wanmi.ares.marketing.suits.service.SuitsService;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.time.LocalDate;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-27 11:39
 */
@Component
@RestController
public class MarketingAnalysisJobHandler {

    @Resource
    private FlashSaleService flashSaleService;

    @Resource
    private AppointmentSaleService appointmentSaleService;

    @Resource
    private GrouponService grouponService;

    @Resource
    private BookingSaleService bookingSaleService;

    @Autowired
    private MarketingReduceDiscountGiftService marketingReduceDiscountGiftService;

    @Autowired
    GoodsBargainService goodsBargainService;

    @Autowired
    private SuitsService suitsService;

    /**
     * XXLJOB-param 例：{"marketingType":"appointment_sale","initDate":"2021-01-20"}
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @XxlJob(value = "marketingAnalysisJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();

        MarketingAnalysisJobRequest jobRequest = new MarketingAnalysisJobRequest();
        MarketingAnalysisJobRequest marketingRequest = new MarketingAnalysisJobRequest();
        if (StringUtils.isNotBlank(param)) {
            JSONObject jsonObject = JSONObject.parseObject(param);
            XxlJobHelper.log("参数：：：" + param);
            String marketingType = jsonObject.getString("marketingType");
            String initDate = jsonObject.getString("initDate");
            if (StringUtils.isBlank(marketingType) && StringUtils.isBlank(initDate)) {
                XxlJobHelper.log("==============参数有误==============");
                return;
            }
            jobRequest.setMarketingType(marketingType);
            jobRequest.setInitDate(initDate);

            switch (marketingType) {
                case "flash":
                    XxlJobHelper.log("==============秒杀==============");
                    Integer count = flashSaleService.insertFlashSaleInfo(jobRequest);
                    XxlJobHelper.log("{} flash_sale order total count is ：{}", initDate, count);
                    break;
                case "appointment_sale":
                    XxlJobHelper.log("==============预约==============");
                    Long countForAppointment = appointmentSaleService.analysisTradeForDay(jobRequest);
                    XxlJobHelper.log("====={}=====当天预约订单入库数量为：{}", initDate, countForAppointment);
                    break;
                case "groupon":
                    XxlJobHelper.log("==============拼团==============");
                    grouponService.infoForDay(jobRequest);
                    break;
                case "booking_sale":
                    XxlJobHelper.log("==============预售==============");
                    bookingSaleService.insertFullMoneyBooking(jobRequest);
                    bookingSaleService.insertDepositBooking(jobRequest);
                    break;
                case "reduction_discount_gift":
                    XxlJobHelper.log("==============减折赠==============");
                    marketingReduceDiscountGiftService.insertMarketingStatisticsDay(jobRequest);
                    marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(jobRequest);
                    break;
                case "bargain":
                    XxlJobHelper.log("=============砍价==============");
                    goodsBargainService.insertBargainGoodsInfo(jobRequest);
                    break;
                case "suits":
                    XxlJobHelper.log("=============组合购==============");
                    suitsService.insertMarketingStatisticsDay(jobRequest);
                    suitsService.insertTradeMarketingSkuDetailDay(jobRequest);
                    break;
                case "half_price_second_piece":
                    XxlJobHelper.log("=============第二件半价==============");
                    marketingReduceDiscountGiftService.insertMarketingStatisticsDay(jobRequest);
                    marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(jobRequest);
                    break;
                case "buyout_price":
                    XxlJobHelper.log("=============打包一口价==============");
                    marketingReduceDiscountGiftService.insertMarketingStatisticsDay(jobRequest);
                    marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(jobRequest);
                    break;
                case "preferential":
                    XxlJobHelper.log("=============加价购==============");
                    marketingReduceDiscountGiftService.insertMarketingStatisticsDay(jobRequest);
                    marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(jobRequest);
                    break;
                default:
                    XxlJobHelper.log("==============参数有误==============");
            }
            return;
        }

        XxlJobHelper.log("==============全部营销活动分析入库==============");

        //秒杀订单入库
        Integer count = flashSaleService.insertFlashSaleInfo(jobRequest);
        XxlJobHelper.log("====={}=====当天秒杀订单入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), count);

        //预约订单入库
        Long countForAppointment = appointmentSaleService.analysisTradeForDay(jobRequest);
        XxlJobHelper.log("====={}=====当天预约订单入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), countForAppointment);

        //砍价订单入库
        Integer bargainCount = goodsBargainService.insertBargainGoodsInfo(jobRequest);
        XxlJobHelper.log("====={}=====当天砍价订单入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), bargainCount);

        //拼团订单入库
        Long grouponCount = grouponService.infoForDay(jobRequest);
        XxlJobHelper.log("====={}=====当天拼团订单入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), grouponCount);

        //全款预售订单入库
        Integer fullMoneyBookingCount =
                bookingSaleService.insertFullMoneyBooking(jobRequest);
        XxlJobHelper.log("====={}=====当天全款预售订单入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), fullMoneyBookingCount);

        //定金预售订单
        Integer depositBookingCount =
                bookingSaleService.insertDepositBooking(jobRequest);
        XxlJobHelper.log("====={}=====当天定金预售订单入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), depositBookingCount);

        //满折赠订单按订单与营销维度入库
        marketingRequest.setMarketingType(MarketingType.REDUCTION_DISCOUNT_GIFT.name().toLowerCase());
        Integer marketingCount =
                marketingReduceDiscountGiftService.insertMarketingStatisticsDay(marketingRequest);
        XxlJobHelper.log("====={}=====当天按订单与营销维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), marketingCount);

        //满折赠订单按订单与sku维度入库
        Integer marketingSkuCount =
                marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(marketingRequest);
        XxlJobHelper.log("====={}=====满折赠-当天按订单与sku维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), marketingSkuCount);

        //打包一口价订单按订单与营销维度入库
        marketingRequest.setMarketingType(MarketingType.BUYOUT_PRICE.name().toLowerCase());
        Integer buyoutCount =
                marketingReduceDiscountGiftService.insertMarketingStatisticsDay(marketingRequest);
        XxlJobHelper.log("====={}=====打包一口价-当天按订单与营销维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), buyoutCount);

        //打包一口价订单按订单与sku维度入库
        Integer buyoutSkuCount =
                marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(marketingRequest);
        XxlJobHelper.log("====={}=====打包一口价-当天按订单与sku维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), buyoutSkuCount);

        //第二件半价订单按订单与营销维度入库
        marketingRequest.setMarketingType(MarketingType.HALF_PRICE_SECOND_PIECE.name().toLowerCase());
        Integer halfCount =
                marketingReduceDiscountGiftService.insertMarketingStatisticsDay(marketingRequest);
        XxlJobHelper.log("====={}=====第二件半价-当天按订单与营销维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), halfCount);

        //第二件半价订单按订单与sku维度入库
        Integer halfSkuCount =
                marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(marketingRequest);
        XxlJobHelper.log("====={}=====第二件半价-当天按订单与sku维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), halfSkuCount);

        //组合商品按订单与营销维度入库
        Integer suitsTradeCount =
                suitsService.insertMarketingStatisticsDay(jobRequest);
        XxlJobHelper.log("====={}=====当天组合商品按订单与营销维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), suitsTradeCount);

        //组合商品按订单与sku维度入库
        Integer suitsSkuCount =
                suitsService.insertTradeMarketingSkuDetailDay(jobRequest);
        XxlJobHelper.log("====={}=====当天组合商品按订单与sku维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), suitsSkuCount);

        //加价购订单按订单与营销维度入库
        marketingRequest.setMarketingType(MarketingType.PREFERENTIAL.name().toLowerCase());
        Integer preferentialCount =
                marketingReduceDiscountGiftService.insertMarketingStatisticsDay(marketingRequest);
        XxlJobHelper.log("====={}=====加价购-当天按订单与营销维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), preferentialCount);

        //加价购订单按订单与sku维度入库
        Integer preferentialSkuCount =
                marketingReduceDiscountGiftService.insertTradeMarketingSkuDetailDay(marketingRequest);
        XxlJobHelper.log("====={}=====加价购-当天按订单与sku维度入库数量为：{}",
                LocalDate.now().minusDays(NumberUtils.LONG_ONE), preferentialSkuCount);
    }
}
