package com.wanmi.sbc.distribute;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.empower.api.provider.wechatauth.WechatAuthProvider;
import com.wanmi.sbc.empower.api.request.wechatauth.DistributionMiniProgramRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Objects;

@Tag(name = "DistributionMiniProgramController", description = "生成各类小程序码")
@RestController
@RequestMapping("/distribution/miniProgram-code")
@Validated
public class DistributionMiniProgramController {

    @Autowired
    private WechatAuthProvider wechatAuthProvider;
    @Autowired
    private CustomerQueryProvider customerQueryProvider;
    @Autowired
    private RedisUtil redisService;
    @Autowired
    private CommonUtil commonUtil;
    @Resource
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Operation(summary = "分销生成各种小程序码")
    @RequestMapping(value = "/distributionMiniProgramQrCode", method = RequestMethod.POST)
    public BaseResponse<String> distributionMiniProgramQrCode(@RequestBody @Valid DistributionMiniProgramRequest request) {
        // tag标识不为空，说明是邀新或是分享店铺，不需要PluginType参数
        if (StringUtils.isBlank(request.getTag())) {
            GoodsInfoVO goodsInfo = BaseResUtils.getContextFromRes(goodsInfoQueryProvider
                    .getById(GoodsInfoByIdRequest.builder().goodsInfoId(request.getSkuId()).build()));
            if (Objects.isNull(goodsInfo)
                    || Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())
                    || (!Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            request.setPluginType(goodsInfo.getPluginType());
        }
        String customerId = Objects.nonNull(request.getInviteeId()) ? request.getInviteeId() : request.getShareUserId();
        CustomerGetByIdRequest customerGetByIdRequest = new CustomerGetByIdRequest();
        customerGetByIdRequest.setCustomerId(customerId);
        CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerInfoById(customerGetByIdRequest).getContext();
        if (customerGetByIdResponse == null || StringUtils.isBlank(customerGetByIdResponse.getCustomerId())) {
            return BaseResponse.error("分销员或分享人不存在");
        }
        request.setShareId(commonUtil.getShareId(customerId));
        return wechatAuthProvider.distributionMiniProgram(request);
    }

    /**
     * 接续出分销里面生成的二维码携带的真正参数
     * @return
     */
    @Operation(summary = "接续出分销里面生成的二维码携带的真正参数")
    @Parameter(name = "redisKey", description = "redisKey", required = true)
    @RequestMapping(value = "/decodeParam/{redisKey}", method = RequestMethod.GET)
    public BaseResponse getSkuQrCode(@PathVariable String redisKey) {
        String result = redisService.getString(redisKey);
        if (!Objects.isNull(result)) {
            return BaseResponse.success(result);
        }
        return BaseResponse.FAILED();
    }
}
