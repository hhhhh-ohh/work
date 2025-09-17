package com.wanmi.sbc.elastic.provider.impl.pointsgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.pointsgoods.EsPointsGoodsQueryProvider;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsPageRequest;
import com.wanmi.sbc.elastic.api.response.pointsgoods.EsPointsGoodsPageResponse;
import com.wanmi.sbc.elastic.pointsgoods.serivce.EsPointsGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES积分商品查询服务实现
 */
@RestController
@Validated
public class EsPointsGoodsQueryController implements EsPointsGoodsQueryProvider {

    @Autowired
    private EsPointsGoodsService esPointsGoodsService;

    @Override
    public BaseResponse<EsPointsGoodsPageResponse> page(@RequestBody EsPointsGoodsPageRequest request) {
        return BaseResponse.success(esPointsGoodsService.page(request));
    }
}
