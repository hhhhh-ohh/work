package com.wanmi.sbc.elastic.api.provider.pointsgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsPageRequest;
import com.wanmi.sbc.elastic.api.response.pointsgoods.EsPointsGoodsPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES积分商品服务查询
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsPointsGoodsQueryProvider")
public interface EsPointsGoodsQueryProvider {

    /**
     * 根据条件分页查询积分商品分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsPointsGoodsPageRequest}
     * @return 分页列表 {@link EsPointsGoodsPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/points-goods/page")
    BaseResponse<EsPointsGoodsPageResponse> page(@RequestBody EsPointsGoodsPageRequest request);
}
