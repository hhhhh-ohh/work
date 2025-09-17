package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoInitRequest;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCateProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCateQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCateGetByCouponCateIdResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponIdsResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponCateSortDTO;
import com.wanmi.sbc.marketing.bean.vo.CouponCateVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author: songhanlin
 * @Date: Created In 10:39 AM 2018/9/14
 * @Description: 优惠券分类Controller
 */
@Tag(name =  "优惠券分类API", description =  "CouponCateController")
@RestController
@Validated
@RequestMapping("/coupon-cate")
public class CouponCateController {

    @Resource
    private CouponCateQueryProvider couponCateQueryProvider;

    @Resource
    private CouponCateProvider couponCateProvider;

    @Autowired
    private EsCouponInfoProvider esCouponInfoProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 查询优惠券分类列表提供给优惠券使用, 最多查询3个
     *
     * @param couponCateIds 优惠券分类Id
     * @return
     */
    @Operation(summary = "查询优惠券分类列表提供给优惠券使用, 最多查询3个")
    @RequestMapping(value = "/list/limit-three", method = RequestMethod.POST)
    public BaseResponse<List<CouponCateVO>> listCouponCateLimitThreeByCouponCateIds(
            @Parameter(description = "优惠券分类Id列表") @Valid @RequestBody List<String> couponCateIds) {
        return BaseResponse.success(couponCateQueryProvider.listLimitThreeByCateIds(new CouponCateListLimitThreeByCateIdsRequest(couponCateIds)).getContext().getCouponCateVOList());
    }

    /**
     * 优惠券分类详情
     *
     * @param couponCateId 优惠券分类Id
     * @return
     */
    @Operation(summary = "优惠券分类详情")
    @Parameter(name = "couponCateId", description = "优惠券分类Id", required = true)
    @RequestMapping(value = "/{couponCateId}", method = RequestMethod.GET)
    public BaseResponse<CouponCateGetByCouponCateIdResponse> getCouponCateByCouponCateId(@PathVariable String couponCateId) {
        return BaseResponse.success(couponCateQueryProvider.getByCouponCateId(new CouponCateGetByCouponCateIdRequest(couponCateId)).getContext());
    }

    /**
     * 删除优惠券分类
     *
     * @param couponCateId
     * @return
     */
    @Operation(summary = "删除优惠券分类")
    @Parameter(name = "couponCateId", description = "优惠券分类Id", required = true)
    @RequestMapping(value = "/{couponCateId}", method = RequestMethod.DELETE)
    public BaseResponse deleteCouponCateByCouponCateId(@PathVariable String couponCateId) {

        if (StringUtils.isBlank(couponCateId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询优惠券信息
        CouponCateGetByCouponCateIdResponse queryResponse =
                couponCateQueryProvider.getByCouponCateId(new CouponCateGetByCouponCateIdRequest(couponCateId)).getContext();

        CouponCateDeleteRequest request = new CouponCateDeleteRequest();
        request.setCouponCateId(couponCateId);
        request.setDelTime(LocalDateTime.now());
        request.setDelPerson(commonUtil.getOperatorId());
        couponCateProvider.delete(request);

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "删除优惠券分类",
                "分类名称：" + (Objects.nonNull(queryResponse) ? queryResponse.getCouponCateName() : ""));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 新增优惠券分类
     *
     * @param couponCateName 优惠券分类名称
     * @return
     */
    @Operation(summary = "新增优惠券分类")
    @Parameter(name = "couponCateName", description = "优惠券分类名称", required = true)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BaseResponse addCouponCate(@NotBlank
                                      @Length(max = 10, min = 1) @RequestParam("couponCateName") String couponCateName) {
        CouponCateAddRequest request = new CouponCateAddRequest();
        request.setCreatePerson(commonUtil.getOperatorId());
        request.setCreateTime(LocalDateTime.now());
        request.setCouponCateName(couponCateName);
        couponCateProvider.add(request);

        operateLogMQUtil.convertAndSend("营销", "新增优惠券分类", "新增优惠券分类：" + couponCateName);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改优惠券分类
     *
     * @param couponCateId   优惠券分类Id
     * @param couponCateName 优惠券分类名称
     * @return
     */
    @Operation(summary = "修改优惠券分类")
    @Parameters({
        @Parameter(name = "couponCateId", description = "优惠券分类Id", required = true),
        @Parameter(name = "couponCateName", description = "优惠券分类名称", required = true)
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public BaseResponse modifyCouponCate(@NotBlank @RequestParam("couponCateId") String couponCateId, @NotBlank
    @Length(max = 10, min = 1) @RequestParam("couponCateName") String couponCateName) {
        //查询修改前优惠券信息
        CouponCateGetByCouponCateIdResponse queryResponse =
                couponCateQueryProvider.getByCouponCateId(new CouponCateGetByCouponCateIdRequest(couponCateId)).getContext();

        CouponCateModifyRequest request = new CouponCateModifyRequest();
        request.setCouponCateId(couponCateId);
        request.setCouponCateName(couponCateName);
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setUpdateTime(LocalDateTime.now());
        couponCateProvider.modify(request);

        operateLogMQUtil.convertAndSend("营销", "修改优惠券分类",
                "修改优惠券分类：" + (Objects.nonNull(queryResponse) ? queryResponse.getCouponCateName() : "") + " 改成 " + couponCateName);

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 排序
     *
     * @param list 排序集合
     * @return
     */
    @Operation(summary = "修改优惠券分类排序")
    @RequestMapping(value = "/sort", method = RequestMethod.PUT)
    public BaseResponse sortCouponCate(@RequestBody List<CouponCateSortDTO> list) {
        couponCateProvider.sort(new CouponCateSortRequest(list));
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 更改是否平台可用
     *
     * @param couponCateId 优惠券分类Id
     * @return
     */
    @Operation(summary = "更改是否平台可用")
    @Parameter(name = "couponCateId", description = "优惠券分类Id", required = true)
    @RequestMapping(value = "/platform", method = RequestMethod.PUT)
    public BaseResponse isOnlyPlatform(@NotBlank @RequestParam("couponCateId") String couponCateId) {
        CouponCateIsOnlyPlatformRequest request = new CouponCateIsOnlyPlatformRequest();
        request.setUpdateTime(LocalDateTime.now());
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setCouponCateId(couponCateId);
        BaseResponse<CouponIdsResponse> response = couponCateProvider.isOnlyPlatform(request);
        if (CollectionUtils.isNotEmpty(response.getContext().getCouponIds())){
            esCouponInfoProvider.init(EsCouponInfoInitRequest.builder().idList(response.getContext().getCouponIds()).build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询优惠券分类列表
     *
     * @return
     */
    @Operation(summary = "查询优惠券分类列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResponse<List<CouponCateVO>> listCouponCate() {
        return BaseResponse.success(couponCateQueryProvider.list().getContext().getList());
    }
}
