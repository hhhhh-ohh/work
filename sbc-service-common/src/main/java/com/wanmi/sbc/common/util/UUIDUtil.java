package com.wanmi.sbc.common.util;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * </p>
 *
 * @version 1.0
 */
public class UUIDUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDUtil.class);
    private static UIDFactory uuid = null;

    static {
        try {
            uuid = UIDFactory.getInstance("UUID");
        } catch (Exception unsex) {
            LOGGER.info("Init UIDFactory Failed", unsex);
        }
    }

    /**
     * Constructor for the UUIDGener object
     */
    private UUIDUtil() {
    }

    /**
     * 获取uuid字符
     *
     * @author lihe 2013-7-4 下午5:31:09
     * @return
     * @see
     * @since
     */
    public static String getUUID() {
        return uuid.getNextUID();
    }

    /**
     * 获取16位UUID
     * @return UUID
     */
    public static String getUUID_16() {
        // 2位随机数 + 4位日期 + 10位hash
        String randomValue = String.valueOf((int) ((Math.random() * 9 + 1) * 10));
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        String dateStr = DateUtil.format(new Date(), "MMmm");
        return randomValue + dateStr + String.format("%010d", hashCodeV);
    }

    public static void main(String[] args) {
        System.out.println(getUUID_16());
    }
}
