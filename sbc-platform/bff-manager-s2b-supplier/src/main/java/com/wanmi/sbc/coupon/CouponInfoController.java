package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoAddRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoPageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponInfoPageResponse;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponInfoDTO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoAddRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponInfoAddResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.ParticipateType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Tag(name = "CouponInfoController", description = "优惠券信息 API")
@RestController
@Validated
@RequestMapping("/coupon-info")
public class CouponInfoController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CouponInfoProvider couponInfoProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCouponInfoProvider esCouponInfoProvider;

    @Autowired
    private EsCouponInfoQueryProvider esCouponInfoQueryProvider;

    /**
     * 获取优惠券列表
     *
     * @param couponInfoQueryRequest
     * @return
     */
    @Operation(summary = "获取优惠券列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<EsCouponInfoPageResponse> page(@RequestBody EsCouponInfoPageRequest couponInfoQueryRequest) {
        couponInfoQueryRequest.setPlatformFlag(DefaultFlag.NO);
        couponInfoQueryRequest.setDelFlag(DeleteFlag.NO);
        couponInfoQueryRequest.setStoreId(commonUtil.getStoreId());
        couponInfoQueryRequest.putSort("createTime", SortType.DESC.toValue());
        return esCouponInfoQueryProvider.page(couponInfoQueryRequest);
    }

    /**
     * 新增优惠券
     *
     * @param request
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "新增优惠券")
    @MultiSubmit
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse add(@Valid @RequestBody CouponInfoAddRequest request) {
        request.setPlatformFlag(DefaultFlag.NO);
        request.setStoreId(commonUtil.getStoreId());
        request.setCreatePerson(commonUtil.getOperatorId());
        StoreType storeType = commonUtil.getStoreType();
        request.setCouponType(CouponType.STORE_VOUCHERS);
        if (StoreType.O2O == storeType) {
            request.setCouponType(CouponType.STOREFRONT_VOUCHER);
            request.setParticipateType(ParticipateType.PART);
        }

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "创建优惠券", "优惠券名称：" + request.getCouponName());
        BaseResponse<CouponInfoAddResponse> baseResponse = couponInfoProvider.add(request);
        CouponInfoVO couponInfoVO = baseResponse.getContext().getCouponInfoVO();
        EsCouponInfoDTO esCouponInfoDTO = KsBeanUtil.convert(couponInfoVO, EsCouponInfoDTO.class);
        esCouponInfoProvider.add(new EsCouponInfoAddRequest(esCouponInfoDTO));
        return BaseResponse.SUCCESSFUL();
    }
}
