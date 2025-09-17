package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.api.provider.storeevaluate.StoreEvaluateQueryProvider;
import com.wanmi.sbc.customer.api.provider.storeevaluatenum.StoreEvaluateNumQueryProvider;
import com.wanmi.sbc.customer.api.request.storeevaluate.StoreEvaluatePageRequest;
import com.wanmi.sbc.customer.api.request.storeevaluatenum.StoreEvaluateNumListRequest;
import com.wanmi.sbc.customer.api.response.storeevaluate.StoreEvaluatePageResponse;
import com.wanmi.sbc.customer.api.response.storeevaluatenum.StoreEvaluateNumResponse;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liutao
 * @date 2019/2/23 2:53 PM
 */
@Tag(name =  "商家店铺评价API" ,description =  "StoreEvaluateController")
@RestController
@Validated
@RequestMapping("/store/evaluate/num")
public class StoreEvaluateNumController {

    @Autowired
    private StoreEvaluateNumQueryProvider storeEvaluateNumQueryProvider;

    @Autowired
    private StoreEvaluateQueryProvider storeEvaluateQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 查询店铺统计评分等级人数统计列表
     * @param listRequest
     * @return
     */
    @Operation(summary = "查询店铺统计评分等级人数统计列表")
    @RequestMapping(value = "/storeEvaluateNumByStoreIdAndScoreCycle",method = RequestMethod.POST)
    public BaseResponse<StoreEvaluateNumResponse> listByStoreIdAndScoreCycle(@RequestBody
                                                                                         StoreEvaluateNumListRequest
                                                                               listRequest){
        listRequest.setStoreId(commonUtil.getStoreId());
        return storeEvaluateNumQueryProvider.listByStoreIdAndScoreCycle(listRequest);
    }

    /**
     * 分页查询当前店铺的评价历史记录
     * @param pageRequest
     * @return
     */
    @Operation(summary = "分页查询当前店铺的评价历史记录")
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public BaseResponse<StoreEvaluatePageResponse> page(@RequestBody StoreEvaluatePageRequest pageRequest){
        pageRequest.setStoreId(commonUtil.getStoreId());
        BaseResponse<StoreEvaluatePageResponse> response =  storeEvaluateQueryProvider.page(pageRequest);
        //获取会员注销状态
        List<String> customerIds = response.getContext().getStoreEvaluateVOPage().getContent()
                .stream()
                .map(StoreEvaluateVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        response.getContext().getStoreEvaluateVOPage().getContent()
                .forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return response;
    }
}
