package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description 选券入参
 * @author malianfeng
 * @date 2022/10/9 11:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponAutoSelectRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品快照列表
     */
    private List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos;

    /**
     * 券快照列表
     */
    private List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes;

    /**
     * 店铺运费列表
     */
    private List<StoreFreightDTO> storeFreights;
}

