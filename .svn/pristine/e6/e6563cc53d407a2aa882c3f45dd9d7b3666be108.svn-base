package com.wanmi.sbc.marketing.coupon.utils;

import com.wanmi.sbc.marketing.bean.enums.CouponType;
import jakarta.persistence.AttributeConverter;

/**
 * @description CouponType 的属性转换器
 * 由于原 CouponType.FREIGHT_VOUCHER（运费券）与 CouponMarketingType.FREIGHT_COUPON（运费券）存在定义冲突，故弃用 CouponType.FREIGHT_VOUCHER
 * 移除该枚举值后，导致下面枚举值的 ordinal 发生改变（与自定义的 value 属性值不一致），可能会导致 value为 3，保存为 2 的情况
 * 因此，添加此 AttributeConverter 用于兼容，数据库存取时都以 自定义的 value 属性值 为准
 * @author malianfeng
 * @date 2022/10/17 16:00
 */
public class CouponTypeAttributeConverter implements AttributeConverter<CouponType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponType couponType) {
        return couponType.toValue();
    }

    @Override
    public CouponType convertToEntityAttribute(Integer integer) {
        return CouponType.fromValue(integer);
    }
}