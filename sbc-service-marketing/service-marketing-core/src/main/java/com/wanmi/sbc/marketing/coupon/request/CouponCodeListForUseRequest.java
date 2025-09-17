package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.common.request.TradeItemInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivity;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午5:58 2018/9/27
 * @Description: 使用优惠券列表请求对象
 */
@Data
public class CouponCodeListForUseRequest extends BaseRequest {

    /**
     * 客户id
     */
    private String customerId;

    /**
     * 用户终端token
     */
    private String terminalToken;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private List<CouponMarketingType> couponMarketingTypes;

    private CouponMarketingType couponMarketingType;

    private CouponType couponType;

    /**
     * 已选优惠券 codeIds 列表
     * 因为系统按照 店铺券 -> 平台券 -> 店铺券 的顺序判断使用门槛和算价，前者的时候可能会导致后者不满足门槛，
     * 这里通过传入已选券 codeIds，二次算价判断门槛，对不可用的后者，进行过滤
     */
    private List<String> selectedCouponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;

    /**
     * 确认订单中的商品列表
     */
    private List<TradeItemInfo> tradeItems = new ArrayList<>();

    private Long storeId;

    private BigDecimal price;

    private PluginType pluginType = PluginType.NORMAL;

    public static CouponCodeVO converter(CouponCode couponCode, CouponInfo couponInfo, CouponActivity couponActivity) {
        return CouponCodeVO.builder()
                .couponCodeId(couponCode.getCouponCodeId())
                .couponCode(couponCode.getCouponCode())
                .useStatus(couponCode.getUseStatus())
                .useDate(couponCode.getUseDate())
                .orderCode(couponCode.getOrderCode())
                .startTime(couponCode.getStartTime())
                .endTime(couponCode.getEndTime())
                .acquireTime(couponCode.getAcquireTime())
                .activityId(couponCode.getActivityId())
                .couponId(couponCode.getCouponId())
                .couponName(couponInfo.getCouponName())
                .fullBuyType(couponInfo.getFullBuyType())
                .fullBuyPrice(couponInfo.getFullBuyPrice())
                .denomination(couponInfo.getDenomination())
                .platformFlag(couponInfo.getPlatformFlag())
                .couponType(couponInfo.getCouponType())
                .createTime(couponInfo.getCreateTime())
                .scopeType(couponInfo.getScopeType())
                .couponDesc(couponInfo.getCouponDesc())
                .couponMarketingType(couponInfo.getCouponMarketingType())
                .couponDiscountMode(couponInfo.getCouponDiscountMode())
                .storeId(couponActivity.getStoreId())
                .activityTitle(couponActivity.getActivityTitle())
                .activityDesc(couponActivity.getActivityDesc())
                .maxDiscountLimit(couponInfo.getMaxDiscountLimit())
                .createTime(couponInfo.getCreateTime())
                .scopeType(couponInfo.getScopeType())
                .participateType(couponInfo.getParticipateType())
                .build();
    }

    public static CouponCodeVO converter(CouponCode couponCode, CouponInfo couponInfo) {
        return CouponCodeVO.builder()
                .couponCodeId(couponCode.getCouponCodeId())
                .couponCode(couponCode.getCouponCode())
                .useStatus(couponCode.getUseStatus())
                .useDate(couponCode.getUseDate())
                .orderCode(couponCode.getOrderCode())
                .startTime(couponCode.getStartTime())
                .endTime(couponCode.getEndTime())
                .acquireTime(couponCode.getAcquireTime())
                .activityId(couponCode.getActivityId())
                .couponId(couponCode.getCouponId())
                .couponName(couponInfo.getCouponName())
                .fullBuyType(couponInfo.getFullBuyType())
                .fullBuyPrice(couponInfo.getFullBuyPrice())
                .denomination(couponInfo.getDenomination())
                .platformFlag(couponInfo.getPlatformFlag())
                .couponType(couponInfo.getCouponType())
                .createTime(couponInfo.getCreateTime())
                .scopeType(couponInfo.getScopeType())
                .couponDesc(couponInfo.getCouponDesc())
                .couponMarketingType(couponInfo.getCouponMarketingType())
                .couponDiscountMode(couponInfo.getCouponDiscountMode())
                .storeId(couponInfo.getStoreId())
                .maxDiscountLimit(couponInfo.getMaxDiscountLimit())
                .createTime(couponInfo.getCreateTime())
                .scopeType(couponInfo.getScopeType())
                .participateType(couponInfo.getParticipateType())
                .build();
    }

    public static List<CouponCodeVO> converter(List<Map<String, Object>> sqlResult) {
        return sqlResult.stream().map(item ->
                CouponCodeVO.builder()
                        .couponCodeId(toStr(item, "couponCodeId"))
                        .couponCode(toStr(item, "couponCode"))
                        .useStatus(toInteger(item, "useStatus") != null ? (toInteger(item, "useStatus") == 0 ? DefaultFlag.NO : DefaultFlag.YES) : null)
                        .useDate(toDate(item, "useDate"))
                        .orderCode(toStr(item, "orderCode"))
                        .startTime(toDate(item, "startTime"))
                        .endTime(toDate(item, "endTime"))
                        .acquireTime(toDate(item, "acquireTime"))
                        .activityId(toStr(item, "activityId"))
                        .couponId(toStr(item, "couponId"))
                        .couponName(toStr(item, "couponName"))
                        .fullBuyType(toInteger(item, "fullBuyType") != null ? FullBuyType.fromValue(toInteger(item, "fullBuyType")) : null)
                        .fullBuyPrice(toBigDecimal(item, "fullBuyPrice"))
                        .denomination(toBigDecimal(item, "denomination"))
                        .platformFlag(toInteger(item, "platformFlag") != null ? (toInteger(item, "platformFlag") == 0 ? DefaultFlag.NO : DefaultFlag.YES) : null)
                        .couponType(toInteger(item, "couponType") != null ? CouponType.fromValue(toInteger(item, "couponType")) : null)
                        .couponMarketingType(toInteger(item, "couponMarketingType") != null ? CouponMarketingType.fromValue(toInteger(item, "couponMarketingType")) : null)
                        .couponDiscountMode(toInteger(item, "couponDiscountMode") != null ? CouponDiscountMode.fromValue(toInteger(item, "couponDiscountMode")) : null)
                        .maxDiscountLimit(toBigDecimal(item, "maxDiscountLimit"))
                        .createTime(toDate(item, "createTime"))
                        .scopeType(toInteger(item, "scopeType") != null ? ScopeType.fromValue(toInteger(item, "scopeType")) : null)
                        .couponDesc(toStr(item, "couponDesc"))
                        .storeId(toLong(item, "storeId"))
                        .storeName(toStr(item, "storeName"))
                        .participateType(toInteger(item, "participateType") != null ? ParticipateType.fromValue(toInteger(item, "participateType")) : null)
                        .build()
        ).collect(Collectors.toList());
    }

    private static String toStr(Map<String, Object> map, String key) {
        return map.get(key) != null ? map.get(key).toString() : null;
    }

    private static Long toLong(Map<String, Object> map, String key) {
        return map.get(key) != null ? Long.valueOf(map.get(key).toString()) : null;
    }

    private static Integer toInteger(Map<String, Object> map, String key) {
        return map.get(key) != null ? Integer.valueOf(map.get(key).toString()) : null;
    }

    private static LocalDateTime toDate(Map<String, Object> map, String key) {
        return map.get(key) != null ? DateUtil.parse(map.get(key).toString(), "yyyy-MM-dd HH:mm:ss.S") : null;
    }

    private static BigDecimal toBigDecimal(Map<String, Object> map, String key) {
        return map.get(key) != null ? new BigDecimal(map.get(key).toString()) : null;
    }
}
