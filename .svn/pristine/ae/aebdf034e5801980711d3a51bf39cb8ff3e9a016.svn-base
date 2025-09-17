package com.wanmi.sbc.freight;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.freight.response.FreightPurchaseInfoResponse;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import com.wanmi.sbc.freight.service.FreightBaseService;
import com.wanmi.sbc.goods.api.request.freight.CollectPageInfoRequest;
import com.wanmi.sbc.goods.api.request.freight.GetFreightInGoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.freight.GetFreightInGoodsInfoResponse;
import com.wanmi.sbc.util.CommonUtil;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * @description   运费信息
 * @author  wur
 * @date: 2022/7/5 9:21
 **/
@RestController
@Validated
@RequestMapping("/freight")
@Tag(name = "FreightBaseController", description = "S2B web公用-运费信息")
public class FreightBaseController {

    @Autowired
    private FreightBaseService freightBaseService;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * @description   商品详情页运费获取
     * @author  wur
     * @date: 2022/7/6 9:34
     * @param request
     * @return
     **/
    @Operation(summary = "商品详情-运费信息")
    @RequestMapping(value = "/goodsInfo", method = RequestMethod.POST)
    public BaseResponse<GetFreightInGoodsInfoResponse> getFreightInGoodsInfo(@RequestBody @Valid GetFreightInGoodsInfoRequest request) {
        return freightBaseService.inGoodsInfoFreight(request);
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/7/6 19:43
     * @param request
     * @return
     **/
    @Operation(summary = "运费凑单页-凑单信息")
    @RequestMapping(value = "/collectPageInfo", method = RequestMethod.POST)
    public BaseResponse<CollectPageInfoResponse> collectPageInfo(@RequestBody @Valid CollectPageInfoRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())
                && CollectionUtils.isEmpty(request.getFreightGoodsInfoVOList())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomer(KsBeanUtil.convert(commonUtil.getCustomer(), CustomerDTO.class));
        return freightBaseService.collectPageInfo(request);
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/7/6 19:43
     * @param request
     * @return
     **/
    @Operation(summary = "运费凑单页-采购单命中")
    @RequestMapping(value = "/purchaseInfo", method = RequestMethod.POST)
    public BaseResponse<FreightPurchaseInfoResponse> purchaseInfo(@RequestBody @Valid CollectPageInfoRequest request) {
        request.setCustomer(KsBeanUtil.convert(commonUtil.getCustomer(), CustomerDTO.class));
        return freightBaseService.purchaseInfo(request);
    }
}
