package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.*;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformAddGoodsResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.goods.SellPlatformGetGoodsResponse;

import java.util.List;

/**
*
 * @description    代销平台商品处理
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface SellPlatformGoodsService extends SellPlatformBaseService {

    /**
     * @description     商品下架
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    void delistingGoods(SellPlatformGoodsBaseRequest request);

    /**
     * @description    商品shangjia
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    void listingGoods(SellPlatformGoodsBaseRequest request);

    /**
     * @description    修改商品
     * @author  wur
     * @date: 2022/4/29 11:31
     * @return
     **/
    BaseResponse updateGoods(SellPlatformUpdateGoodsRequest requestList);

    void syncStock(List<SellPlatformSyncStockRequest> requestList);

    /**
     * @description   删除商品
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    void delGoods(SellPlatformDeleteGoodsRequest request);

    /**
     * @description    撤回审核
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    SellPlatformGetGoodsResponse delAuditGoods(SellPlatformGoodsBaseRequest request);

    /**
     * @description    添加商品
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    SellPlatformAddGoodsResponse addGoods(SellPlatformAddGoodsRequest request);

    /**
     * @description   修改免审字段
     * @author  wur
     * @date: 2022/4/29 11:31
     * @return
     **/
    void updateNoAuditGoods(List<SellPlatformUpdateNoAuditRequest> list);
}
