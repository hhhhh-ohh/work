package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.goodstobeevaluate.GoodsTobeEvaluateQueryProvider;
import com.wanmi.sbc.goods.api.request.goodstobeevaluate.GoodsTobeEvaluatePageRequest;
import com.wanmi.sbc.goods.api.request.goodstobeevaluate.GoodsTobeEvaluateQueryRequest;
import com.wanmi.sbc.goods.api.response.goodstobeevaluate.GoodsTobeEvaluatePageResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * @Author lvzhenwei
 * @Description 商品待评价controller
 * @Date 14:56 2019/4/1
 * @Param
 * @return
 **/
@RestController
@Validated
@RequestMapping("/goodsTobeEvaluate")
@Tag(name = "GoodsTobeEvaluateController", description = "S2B web公用-商品待评价API")
public class GoodsTobeEvaluateController {

    @Autowired
    private GoodsTobeEvaluateQueryProvider goodsTobeEvaluateQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    /**
     * 分页查询订单商品待评价
     * @param goodsTobeEvaluatePageReq
     * @return
     */
    @Operation(summary = "分页查询订单商品待评价")
    @RequestMapping(value = "/pageGoodsTobeEvaluate", method = RequestMethod.POST)
    public BaseResponse<GoodsTobeEvaluatePageResponse> pageGoodsTobeEvaluate(@RequestBody GoodsTobeEvaluatePageRequest
                                                                                         goodsTobeEvaluatePageReq){
        goodsTobeEvaluatePageReq.setCustomerId(commonUtil.getOperatorId());

        return goodsTobeEvaluateQueryProvider.page(goodsTobeEvaluatePageReq);
    }

    /**
     * 获取待评价数量
     * @return
     */
    @Operation(summary = "获取待评价数量")
    @RequestMapping(value = "/getGoodsTobeEvaluateNum", method = RequestMethod.GET)
    public BaseResponse<Long> getGoodsTobeEvaluateNum(){
        GoodsTobeEvaluateQueryRequest queryReq = new GoodsTobeEvaluateQueryRequest();
        queryReq.setCustomerId(commonUtil.getOperatorId());
        return goodsTobeEvaluateQueryProvider.getGoodsTobeEvaluateNum(queryReq);
    }
}
