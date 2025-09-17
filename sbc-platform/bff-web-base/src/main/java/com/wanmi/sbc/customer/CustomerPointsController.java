package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailQueryRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsShareRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsDetailPageResponse;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsExpireResponse;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.util.CommonUtil;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * 会员积分
 */
@Tag(name =  "PC、H5会员积分API", description =  "CustomerPointsController")
@RestController
@Validated
@RequestMapping(value = "/customer/points")
public class CustomerPointsController {

    @Autowired
    private CustomerPointsDetailQueryProvider customerPointsDetailQueryProvider;

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisService;

    @Operation(summary = "分页查询会员积分明细")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<CustomerPointsDetailPageResponse> page(@RequestBody @Valid CustomerPointsDetailQueryRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return customerPointsDetailQueryProvider.page(request);
    }

    @Operation(summary = "根据会员Id查询会员快过期积分")
    @RequestMapping(value = "/expire", method = RequestMethod.GET)
    public BaseResponse<CustomerPointsExpireResponse> queryWillExpirePoints() {
        return customerPointsDetailQueryProvider.queryWillExpirePoints(new CustomerGetByIdRequest(commonUtil.getOperatorId()));
    }

    @Operation(summary = "会员分享获得积分")
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public BaseResponse share(@RequestBody CustomerPointsShareRequest request) {
        // 防止刷分行为，先确保这条分享没有获取过积分和成长值，增加后将该条记录删除
        if (Objects.isNull(request.getShareId())
                || Objects.isNull(redisService.getString(request.getShareId()))) {
            return BaseResponse.SUCCESSFUL();
        }
        if (StringUtils.isNotBlank(request.getToken())) {
            Operator operator = commonUtil.getUserInfo(request.getToken());
            // jwt token不正确 且 被分享人与分享人id一致时
            if (StringUtils.isBlank(operator.getUserId())
                    || operator.getUserId().equals(request.getCustomerId())) {
                return BaseResponse.SUCCESSFUL();
            }
        }

        // 增加成长值
        customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder()
                .customerId(request.getCustomerId())
                .type(OperateType.GROWTH)
                .serviceType(GrowthValueServiceType.SHARE)
                .build());
        // 增加积分
        customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                .customerId(request.getCustomerId())
                .type(OperateType.GROWTH)
                .serviceType(PointsServiceType.SHARE)
                .build());

        redisService.delete(request.getShareId());

        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "记录分享")
    @RequestMapping(value = "/shareRecord", method = RequestMethod.GET)
    public BaseResponse<String> shareRecord(){
        return BaseResponse.success(commonUtil.getShareId(StringUtils.EMPTY));
    }
}
