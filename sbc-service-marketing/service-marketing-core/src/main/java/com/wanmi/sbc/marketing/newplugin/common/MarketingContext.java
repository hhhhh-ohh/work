package com.wanmi.sbc.marketing.newplugin.common;

import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.bean.MarketingPluginBaseParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhanggaolei
 * @className MarketingContext
 * @description
 * @date 2021/5/19 14:15
 */
public class MarketingContext {

    private static final ThreadLocal<Object> RESPONSE = new InheritableThreadLocal();

    private static final ThreadLocal<MarketingPluginBaseParam> BASE_REQUEST =
            new InheritableThreadLocal();

    /** sku类型的营销 */
    public static final Set<MarketingPluginType> SKU_TYPE_MAP = new HashSet<>();

    /** 多种类型关联的营销 */
    public static final Set<MarketingPluginType> MULTI_TYPE_MAP = new HashSet<>();

    /** 虚拟商品限制的营销 */
    public static final Set<MarketingPluginType> VIRTUAL_TYPE_MAP = new HashSet<>();

    /** 卡券商品限制的营销 */
    public static final Set<MarketingPluginType> ELECTRONIC_TYPE_MAP = new HashSet<>();

    static {
        SKU_TYPE_MAP.add(MarketingPluginType.GROUPON);
        SKU_TYPE_MAP.add(MarketingPluginType.FLASH_SALE);
        // 限时购
        SKU_TYPE_MAP.add(MarketingPluginType.FLASH_PROMOTION);
//        SKU_TYPE_MAP.add(MarketingPluginType.SUITS);
        SKU_TYPE_MAP.add(MarketingPluginType.APPOINTMENT_SALE);
        SKU_TYPE_MAP.add(MarketingPluginType.BOOKING_SALE);
//        SKU_TYPE_MAP.add(MarketingPluginType.DISTRIBUTION);

        MULTI_TYPE_MAP.add(MarketingPluginType.REDUCTION);
        MULTI_TYPE_MAP.add(MarketingPluginType.DISCOUNT);
        MULTI_TYPE_MAP.add(MarketingPluginType.GIFT);
        MULTI_TYPE_MAP.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE);
        MULTI_TYPE_MAP.add(MarketingPluginType.BUYOUT_PRICE);
        MULTI_TYPE_MAP.add(MarketingPluginType.SUITS);
        MULTI_TYPE_MAP.add(MarketingPluginType.RETURN);
        MULTI_TYPE_MAP.add(MarketingPluginType.PREFERENTIAL);

        VIRTUAL_TYPE_MAP.add(MarketingPluginType.GIFT);
        VIRTUAL_TYPE_MAP.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE);
        VIRTUAL_TYPE_MAP.add(MarketingPluginType.BUYOUT_PRICE);
        VIRTUAL_TYPE_MAP.add(MarketingPluginType.SUITS);
        VIRTUAL_TYPE_MAP.add(MarketingPluginType.PREFERENTIAL);

        ELECTRONIC_TYPE_MAP.add(MarketingPluginType.GIFT);
        ELECTRONIC_TYPE_MAP.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE);
        ELECTRONIC_TYPE_MAP.add(MarketingPluginType.BUYOUT_PRICE);
        ELECTRONIC_TYPE_MAP.add(MarketingPluginType.SUITS);
        ELECTRONIC_TYPE_MAP.add(MarketingPluginType.PREFERENTIAL);
    }

    public static void setResponse(Object t) {
        RESPONSE.set(t);
    }

    public static <T> T getResponse() {
        if (RESPONSE.get() == null) {
            return null;
        }
        return (T) RESPONSE.get();
    }

    public static void removeResponse() {
        RESPONSE.remove();
    }

    public static void setBaseRequest(MarketingPluginBaseParam t) {
        BASE_REQUEST.set(t);
    }

    public static MarketingPluginBaseParam getBaseRequest() {
        return BASE_REQUEST.get();
    }

    public static void removeBaseRequest() {
        BASE_REQUEST.remove();
    }

    public static boolean isNotNullSkuMarketingMap() {
        if (BASE_REQUEST.get() != null && BASE_REQUEST.get().getSkuMarketingMap() != null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullSkuMarketingMap(String goodsInfoId) {
        if (isNotNullSkuMarketingMap()
                && MapUtils.isNotEmpty(BASE_REQUEST.get().getSkuMarketingMap().get(goodsInfoId))) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullMultiMarketingMap() {
        if (BASE_REQUEST.get() != null && BASE_REQUEST.get().getMultiTypeMarketingMap() != null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullMultiMarketingMap(String goodsInfoId) {
        if (isNotNullMultiMarketingMap()
                && CollectionUtils.isNotEmpty(
                        BASE_REQUEST.get().getMultiTypeMarketingMap().get(goodsInfoId))) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullMultiMarketingMapKey(String goodsInfoId) {
        if (isNotNullMultiMarketingMap()
                &&  BASE_REQUEST.get().getMultiTypeMarketingMap().containsKey(goodsInfoId)) {
            return true;
        }
        return false;
    }
}
