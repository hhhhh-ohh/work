package com.wanmi.sbc.empower.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.*;
import com.wanmi.sbc.empower.api.response.sellplatform.goods.PlatformAddGoodsResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.goods.PlatformGetGoodsResponse;

/**
*
 * @description    商品处理接口
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface PlatformGoodsService extends PlatformBaseService {

    /**
    *
     * @description   添加审核商品
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse<PlatformAddGoodsResponse> addGoods(PlatformAddGoodsRequest request);

    /**
     *
     * @description   删除审核商品
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse delAudit(PlatformGoodsBaseRequest request);

    /**
     *
     * @description   查询商品信息
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse<PlatformGetGoodsResponse> getSpu(PlatformGetGoodsRequest request);

    /**
     *
     * @description     修改商品
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse<PlatformAddGoodsResponse> updateGoods(PlatformUpdateGoodsRequest request);

    /**
     *
     * @description   删除商品
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse delGoods(PlatformDeleteGoodsRequest request);

    /**
     *
     * @description    商品上架
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse listing(PlatformGoodsBaseRequest request);

    /**
     *
     * @description   商品下架
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse delisting(PlatformGoodsBaseRequest request);

    /**
     *
     * @description   更新商品免审信息
     * @author  wur
     * @date: 2022/4/19 11:25
     * @param request
     * @return
     **/
    BaseResponse updateNoAuditGoods(PlatformUpdateNoAuditRequest request);
}
