package com.wanmi.sbc.elastic.provider.impl.pointsgoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.pointsgoods.EsPointsGoodsProvider;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsDeleteByIdRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsInitRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsModifyStatusRequest;
import com.wanmi.sbc.elastic.pointsgoods.serivce.EsPointsGoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES积分商品服务实现
 */
@RestController
@Validated
public class EsPointsGoodsController implements EsPointsGoodsProvider {

    @Autowired
    private EsPointsGoodsService esPointsGoodsService;

    @Override
    public BaseResponse init(@RequestBody EsPointsGoodsInitRequest request) {
        if(CollectionUtils.isNotEmpty(request.getIdList())){
            request.setPointsGoodsIds(request.getIdList());
            request.setPageNum(0);
            request.setPageSize(request.getIdList().size());
        } else if(CollectionUtils.isNotEmpty(request.getGoodsInfoIds())){
            request.setPageNum(0);
            request.setPageSize(request.getGoodsInfoIds().size());
        }
        esPointsGoodsService.init(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteById(@RequestBody @Valid EsPointsGoodsDeleteByIdRequest request) {
        esPointsGoodsService.delete(request.getPointsGoodsId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyStatus(@RequestBody @Valid EsPointsGoodsModifyStatusRequest request) {
        esPointsGoodsService.modifyStatus(request.getPointsGoodsId(), request.getStatus());
        return BaseResponse.SUCCESSFUL();
    }
}
