package com.wanmi.sbc.marketing.util.common;

import com.wanmi.sbc.common.enums.SnowFlake;
import com.wanmi.sbc.common.util.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 订单ID生成
 * Created by Administrator on 2017/4/18.
 */
@Service
public class MarketingGeneratorService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_7, Locale.UK);

    /**
     * 供应商订单号前缀
     */
    public static final String PREFIX_GIFT_CARD_NO = "Z";

    /**
     * 服务于 动态 mid 的 SnowFlake
     * 服务启动，mid 生成时，生成方主动调用 dynamicMidSnowFlakeInstance 创建该实例
     */
    private static volatile MarketingSnowFlake DYNAMIC_MID_SNOW_FLAKE = null;

    /**
     * 为动态 mid，创建 SnowFlake 实例
     * @param serviceId 服务id
     * @param mid 机器id
     * @return
     */
    public MarketingSnowFlake dynamicMidSnowFlakeInstance(Integer serviceId, Integer mid) {
        if (DYNAMIC_MID_SNOW_FLAKE == null) {
            synchronized (MarketingGeneratorService.class) {
                if (DYNAMIC_MID_SNOW_FLAKE == null) {
                    DYNAMIC_MID_SNOW_FLAKE = new MarketingSnowFlake(serviceId, mid);
                }
            }
        }
        return DYNAMIC_MID_SNOW_FLAKE;
    }


    /**
     * 生成礼品卡批次编号
     * "yyyyMMddHHmmssSSS" + random(3)
     */
    public String generateGiftCardBatchNo() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER) + RandomStringUtils.randomNumeric(3);
    }

    /**
     * 生成礼品卡卡号
     * "以Z开头+18位数字，不可重复"
     */
    public String generateGiftCardNo(Integer serviceId, Integer mid) {
        return PREFIX_GIFT_CARD_NO + dynamicMidSnowFlakeInstance(serviceId, mid).nextId();
    }
}
