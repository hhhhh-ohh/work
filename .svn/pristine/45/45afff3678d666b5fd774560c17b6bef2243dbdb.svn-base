package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>订单商品快照，为保证商品的即时性TradeItem只暂存skuId与商品数量，
 * 用户下单后在订单生成前的校验使用，做持久化处理
 * </p>
 * Created by of628-wenzhi on 2017-07-11-下午5:25.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeItemSnapshotVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 按商家，店铺分组的订单商品快照
     */
    private List<TradeItemGroupVO> itemGroups;

    /**
     * 快照类型--秒杀活动抢购商品订单快照："FLASH_SALE"
     */
    private String snapshotType;

    /**
     * 是否购物车购买
     */
    private Boolean purchaseBuy;

    /**
     * 砍价订单标识
     */
    private Boolean bargain;
}
