package com.wanmi.sbc.elastic.api.provider.pointsgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsDeleteByIdRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsInitRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsModifyStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES积分商品服务
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsPointsGoodsProvider")
public interface EsPointsGoodsProvider {

    /**
     * 初始化积分商品
     *
     * @param request 初始化请求结构 {@link EsPointsGoodsInitRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/points-goods/init")
    BaseResponse init(@RequestBody EsPointsGoodsInitRequest request);

    /**
     * 修改状态-积分商品
     *
     * @param request 修改状态请求结构 {@link EsPointsGoodsInitRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/points-goods/modify-status")
    BaseResponse modifyStatus(@RequestBody @Valid EsPointsGoodsModifyStatusRequest request);

    /**
     * 修改状态-积分商品
     *
     * @param request 修改状态请求结构 {@link EsPointsGoodsInitRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/points-goods/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid EsPointsGoodsDeleteByIdRequest request);
}
