package com.wanmi.sbc.order.provider.impl.follow;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.provider.follow.FollowQueryProvider;
import com.wanmi.sbc.order.api.request.follow.*;
import com.wanmi.sbc.order.api.response.follow.*;
import com.wanmi.sbc.order.bean.vo.GoodsCustomerFollowVO;
import com.wanmi.sbc.order.follow.model.root.GoodsCustomerFollow;
import com.wanmi.sbc.order.follow.reponse.GoodsCustomerFollowResponse;
import com.wanmi.sbc.order.follow.request.GoodsCustomerFollowQueryRequest;
import com.wanmi.sbc.order.follow.request.GoodsCustomerFollowRequest;
import com.wanmi.sbc.order.follow.service.GoodsCustomerFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
@RestController
public class FollowQueryController implements FollowQueryProvider {

    @Autowired
    private GoodsCustomerFollowService service;

    @Override
    public BaseResponse<FollowListResponse> list(@RequestBody @Valid FollowListRequest request) {
        GoodsCustomerFollowQueryRequest serviceRequest = KsBeanUtil.convert(request, GoodsCustomerFollowQueryRequest.class);
        GoodsCustomerFollowResponse serviceResponse = service.list(serviceRequest);
        FollowListResponse response = KsBeanUtil.convert(serviceResponse, FollowListResponse.class);
        if(Objects.nonNull(serviceResponse.getGoodsInfos().getContent()) ){
            response.setGoodsInfos(KsBeanUtil.convertPage(serviceResponse.getGoodsInfos(), GoodsInfoVO.class));

        }
        return BaseResponse.success(response);
    }




    @Override
    public BaseResponse<FollowHaveInvalidGoodsResponse> haveInvalidGoods(@RequestBody @Valid HaveInvalidGoodsRequest request) {

        GoodsCustomerFollowRequest serviceRequest = KsBeanUtil.convert(request, GoodsCustomerFollowRequest.class);

        Boolean serviceResponse = service.haveInvalidGoods(serviceRequest);

        return BaseResponse.success(FollowHaveInvalidGoodsResponse.builder().boolValue(serviceResponse).build());
    }


    @Override
    public BaseResponse<IsFollowResponse> isFollow(@RequestBody @Valid IsFollowRequest request) {

        GoodsCustomerFollowRequest serviceRequest = KsBeanUtil.convert(request, GoodsCustomerFollowRequest.class);

        List<String> serviceResponse = service.isFollow(serviceRequest);

        return BaseResponse.success(IsFollowResponse.builder().value(serviceResponse).build());
    }

    @Override
    public BaseResponse<FollowCountResponse> count(@RequestBody @Valid FollowCountRequest request) {

        GoodsCustomerFollowQueryRequest serviceRequest = KsBeanUtil.convert(request, GoodsCustomerFollowQueryRequest.class);

        Long serviceResponse = service.count(serviceRequest);

        return BaseResponse.success(FollowCountResponse.builder().value(serviceResponse).build());
    }

    public BaseResponse<GoodsCustomerFollowPageResponse> page(@RequestBody @Valid FollowListRequest request){
        GoodsCustomerFollowQueryRequest queryRequest = KsBeanUtil.convert(request, GoodsCustomerFollowQueryRequest.class);
        Page<GoodsCustomerFollow> page = service.page(queryRequest);
        List<GoodsCustomerFollowVO> list =
                KsBeanUtil.convert(page.getContent(), GoodsCustomerFollowVO.class);
        MicroServicePage<GoodsCustomerFollowVO> goodsCustomerFollowVOS =
                new MicroServicePage<GoodsCustomerFollowVO>(
                        list, page.getPageable(), page.getTotalElements());
        return BaseResponse.success(
                GoodsCustomerFollowPageResponse.builder()
                        .goodsCustomerFollowVOS(goodsCustomerFollowVOS)
                        .build());
    }
}
