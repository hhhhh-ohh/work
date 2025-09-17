package com.wanmi.sbc.empower.pay.utils;

import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/***
 * 支付网关工具类
 */
public final class PayGateWayUtils {
    private PayGateWayUtils() {
    }

    /***
     * 余额支付-渠道IDList，不可更改
     */
    public static final Set<String> PAY_GATEWAY_BALANCE = new HashSet<>(Arrays.asList("21", "22", "23"));


    /***
     * 授信支付-渠道IDList，不可更改
     */
    public static final Set<String> PAY_GATEWAY_CREDIT = new HashSet<>(Arrays.asList("24", "25", "26"));

    /***
     * 判断指定值是否来源指定枚举的渠道
     * @param payGateway    网关类型
     * @param obj           网关ItemID
     * @return
     */
    public static boolean isGateway(PayGatewayEnum payGateway, Object obj) {
        if (Objects.isNull(payGateway) || Objects.isNull(obj)) {
            return false;
        }
        switch (payGateway) {
            case BALANCE:
                return PAY_GATEWAY_BALANCE.contains(obj.toString());
            case CREDIT:
                return PAY_GATEWAY_CREDIT.contains(obj.toString());
            default:
                return false;
        }
    }

    /***
     * 判断指定类型是否余额支付
     * @param obj
     * @return
     */
    public static boolean isBalance(Object obj) {
        return isGateway(PayGatewayEnum.BALANCE,obj);
    }

    /***
     * 判断指定类型是否授信支付
     * @param obj
     * @return
     */
    public static boolean isCredit(Object obj) {
        return isGateway(PayGatewayEnum.CREDIT,obj);
    }
}
