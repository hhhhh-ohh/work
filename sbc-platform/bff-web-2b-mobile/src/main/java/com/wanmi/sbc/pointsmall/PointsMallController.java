package com.wanmi.sbc.pointsmall;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerCheckPayPasswordRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.elastic.api.provider.pointsgoods.EsPointsGoodsQueryProvider;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsPageRequest;
import com.wanmi.sbc.elastic.api.response.pointsgoods.EsPointsGoodsPageResponse;
import com.wanmi.sbc.goods.api.provider.pointsgoodscate.PointsGoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateListRequest;
import com.wanmi.sbc.goods.api.response.pointsgoodscate.PointsGoodsCateListResponse;
import com.wanmi.sbc.marketing.api.provider.pointscoupon.PointsCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.pointscoupon.PointsCouponSaveProvider;
import com.wanmi.sbc.marketing.api.request.pointscoupon.PointsCouponByIdRequest;
import com.wanmi.sbc.marketing.api.request.pointscoupon.PointsCouponFetchRequest;
import com.wanmi.sbc.marketing.api.request.pointscoupon.PointsCouponPageRequest;
import com.wanmi.sbc.marketing.api.response.pointscoupon.PointsCouponByIdResponse;
import com.wanmi.sbc.marketing.api.response.pointscoupon.PointsCouponPageResponse;
import com.wanmi.sbc.marketing.api.response.pointscoupon.PointsCouponSendCodeResponse;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.request.trade.PointsCouponTradeCommitRequest;
import com.wanmi.sbc.order.bean.vo.PointsTradeCommitResultVO;
import com.wanmi.sbc.pointsmall.request.FetchPointsCouponRequest;
import com.wanmi.sbc.pointsmall.response.CustomerInfoResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "积分商城API", description =  "PointsMallController")
@RestController
@Validated
@RequestMapping(value = "/pointsMall")
public class PointsMallController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private PointsGoodsCateQueryProvider pointsGoodsCateQueryProvider;

    @Autowired
    private PointsCouponQueryProvider pointsCouponQueryProvider;

    @Autowired
    private PointsCouponSaveProvider pointsCouponSaveProvider;

    @Autowired
    private EsPointsGoodsQueryProvider esPointsGoodsQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    /**
     * 积分兑换优惠券
     */
    @GlobalTransactional
    @Transactional
    @Operation(summary = "积分兑换优惠券")
    @PostMapping(value = "/fetchPointsCoupon/{pointsCouponId}")
    public BaseResponse<PointsTradeCommitResultVO> fetchPointsCoupon(@PathVariable Long pointsCouponId,@RequestBody @Valid FetchPointsCouponRequest request) {
        //验证支付密码是否正确
        CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest = new CustomerCheckPayPasswordRequest();
        customerCheckPayPasswordRequest.setPayPassword(request.getPayPassword());
        customerCheckPayPasswordRequest.setCustomerId(commonUtil.getCustomer().getCustomerId());
        customerSiteProvider.checkCustomerPayPwd(customerCheckPayPasswordRequest);

        // 生成优惠券券码 扣除积分优惠券库存
        PointsCouponSendCodeResponse response = pointsCouponSaveProvider.exchangePointsCoupon(PointsCouponFetchRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .pointsCouponId(pointsCouponId)
                .build())
                .getContext();

        PointsCouponTradeCommitRequest commitRequest = new PointsCouponTradeCommitRequest();
        KsBeanUtil.copyPropertiesThird(response, commitRequest);
        CustomerSimplifyOrderCommitVO customer = new CustomerSimplifyOrderCommitVO();
        customer.setCustomerId(response.getCustomer().getCustomerId());
        commitRequest.setCustomer(customer);
        commitRequest.setOperator(commonUtil.getOperator());

        // 生成积分订单 扣除用户积分
        PointsTradeCommitResultVO successResult =
                tradeProvider.pointsCouponCommit(commitRequest).getContext().getPointsTradeCommitResult();
        return BaseResponse.success(successResult);
    }

    @Operation(summary = "查询热门兑换")
    @PostMapping(value = "/hotExchange")
    public BaseResponse<EsPointsGoodsPageResponse> hotExchange(@RequestBody @Valid EsPointsGoodsPageRequest pointsGoodsPageReq) {

        //可兑换时间内启动状态下的商品
        wrapperConditionToPointsGoodsPageRequest(pointsGoodsPageReq);
        pointsGoodsPageReq.putSort("sales", SortType.DESC.toValue());
        pointsGoodsPageReq.putSort("createTime", SortType.DESC.toValue());
        pointsGoodsPageReq.setRecommendFlag(BoolFlag.YES);

        return esPointsGoodsQueryProvider.page(pointsGoodsPageReq);
    }

    @Operation(summary = "查询会员信息")
    @RequestMapping(value = "/customerInfo", method = RequestMethod.GET)
    public BaseResponse<CustomerInfoResponse> findCustomerInfo() {
        String customerId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();

        return BaseResponse.success(CustomerInfoResponse.builder()
                .customerId(customerId)
                .customerName(customer.getCustomerDetail().getCustomerName())
                .pointsAvailable(customer.getPointsAvailable())
                .build()
        );
    }

    /**
     * 查询积分商品分类
     *
     * @return
     */
    @Operation(summary = "查询积分商品分类")
    @RequestMapping(value = "/cateList", method = RequestMethod.GET)
    public BaseResponse<PointsGoodsCateListResponse> getCateList() {
        PointsGoodsCateListRequest listReq = new PointsGoodsCateListRequest();
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("sort", "asc");
        return pointsGoodsCateQueryProvider.list(listReq);
    }

    /**
     * 查询我能兑换的积分商品
     *
     * @return
     */
    @Operation(summary = "查询我能兑换的积分商品")
    @PostMapping("/pageCanExchange")
    public BaseResponse<EsPointsGoodsPageResponse> pageCanExchange(@RequestBody @Valid EsPointsGoodsPageRequest pointsGoodsPageReq) {

        //可兑换时间内启动状态下的商品
        wrapperConditionToPointsGoodsPageRequest(pointsGoodsPageReq);
        //根据是否推荐和销量排序
        wrapperSortToPointsGoodsPageRequest(pointsGoodsPageReq);

        //查询会员可用积分
        String customerId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        //设置积分兑换最大值
        pointsGoodsPageReq.setMaxPoints(customer.getPointsAvailable());

        return esPointsGoodsQueryProvider.page(pointsGoodsPageReq);
    }

    /**
     * 仅展示可兑换时间内且启用状态下的商品 按不同排序规则进行排序
     *
     * @param pointsGoodsPageReq
     * @return
     */
    @Operation(summary = "查询积分商品")
    @PostMapping("/page")
    public BaseResponse<EsPointsGoodsPageResponse> page(@RequestBody @Valid EsPointsGoodsPageRequest pointsGoodsPageReq) {
        wrapperConditionToPointsGoodsPageRequest(pointsGoodsPageReq);
        wrapperSortToPointsGoodsPageRequest(pointsGoodsPageReq);
        return esPointsGoodsQueryProvider.page(pointsGoodsPageReq);
    }

    /**
     * 查询我能兑换的积分优惠券
     */
    @Operation(summary = "查询我能兑换的积分优惠券")
    @PostMapping("/pageCanExchangeCoupon")
    public BaseResponse<PointsCouponPageResponse> pageCanExchangeCoupon(@RequestBody @Valid PointsCouponPageRequest pointsCouponPageReq) {
        // 未删除、兑换时间内、启动状态下的优惠券
        pointsCouponPageReq.setDelFlag(DeleteFlag.NO);
        pointsCouponPageReq.setStatus(EnableStatus.ENABLE);
        pointsCouponPageReq.setBeginTimeEnd(LocalDateTime.now());
        pointsCouponPageReq.setEndTimeBegin(LocalDateTime.now());

        // 根据积分及市场价排序
        wrapperSortToPointsCouponPageRequest(pointsCouponPageReq);

        // 查询会员可用积分
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (commonUtil.getOperatorId())).getContext();
        // 设置积分可兑换最大值
        pointsCouponPageReq.setPoints(customer.getPointsAvailable());

        return pointsCouponQueryProvider.page(pointsCouponPageReq);
    }

    /**
     * 仅展示可兑换时间内且启用状态下的优惠券 按不同排序规则进行排序
     *
     * @param pointsCouponPageReq
     * @return
     */
    @Operation(summary = "查询积分优惠券")
    @PostMapping("/pageCoupon")
    public BaseResponse<PointsCouponPageResponse> page(@RequestBody @Valid PointsCouponPageRequest pointsCouponPageReq) {
        // 未删除、兑换时间内、启动状态下的优惠券
        pointsCouponPageReq.setDelFlag(DeleteFlag.NO);
        pointsCouponPageReq.setStatus(EnableStatus.ENABLE);
        pointsCouponPageReq.setBeginTimeEnd(LocalDateTime.now());
        pointsCouponPageReq.setEndTimeBegin(LocalDateTime.now());

        // 根据积分及市场价排序
        wrapperSortToPointsCouponPageRequest(pointsCouponPageReq);

        return pointsCouponQueryProvider.page(pointsCouponPageReq);
    }

    @Operation(summary = "根据id查询积分兑换券表")
    @GetMapping("/coupon/{pointsCouponId}")
    public BaseResponse<PointsCouponByIdResponse> getById(@PathVariable Long pointsCouponId) {
        if (pointsCouponId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PointsCouponByIdRequest idReq = new PointsCouponByIdRequest();
        idReq.setPointsCouponId(pointsCouponId);
        return pointsCouponQueryProvider.getById(idReq);
    }

    /**
     * 包装筛选条件到PointsGoodsPageRequest查询对象中
     *
     * @param queryRequest
     */
    private void wrapperConditionToPointsGoodsPageRequest(EsPointsGoodsPageRequest queryRequest) {
        queryRequest.setStatus(EnableStatus.ENABLE);
        queryRequest.setBeginTimeEnd(LocalDateTime.now());
        queryRequest.setEndTimeBegin(LocalDateTime.now());
        queryRequest.setMinStock(1L);
        queryRequest.setSaleFlag(Boolean.TRUE);
    }

    /**
     * 包装排序字段到PointsGoodsPageRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    private void wrapperSortToPointsGoodsPageRequest(EsPointsGoodsPageRequest queryRequest) {
        if (queryRequest.getSortFlag() != null) {
            switch (queryRequest.getSortFlag()) {
                case 0:
                    //积分价格数升序
                    queryRequest.putSort("points", SortType.ASC.toValue());
                    break;
                case 1:
                    //积分价格数倒序
                    queryRequest.putSort("points", SortType.DESC.toValue());
                    break;
                case 2:
                    //市场价升序
                    queryRequest.putSort("marketPrice", SortType.ASC.toValue());
                    break;
                case 3:
                    //市场价倒序
                    queryRequest.putSort("marketPrice", SortType.DESC.toValue());
                    break;
                default:
                    break;
            }
        }

        //根据是否推荐和销量排序
        queryRequest.putSort("recommendFlag", SortType.DESC.toValue());
        queryRequest.putSort("sales", SortType.DESC.toValue());
        queryRequest.putSort("createTime", SortType.DESC.toValue());
    }

    /**
     * 包装排序字段到PointsGoodsPageRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    private void wrapperSortToPointsCouponPageRequest(PointsCouponPageRequest queryRequest) {
        queryRequest.putSort("sellOutFlag", SortType.ASC.toValue());
        if (queryRequest.getSortFlag() != null) {
            switch (queryRequest.getSortFlag()) {
                case 0:
                    //积分价格数升序
                    queryRequest.putSort("points", SortType.ASC.toValue());
                    break;
                case 1:
                    //积分价格数倒序
                    queryRequest.putSort("points", SortType.DESC.toValue());
                    break;
                case 2:
                    //市场价升序
                    queryRequest.putSort("couponInfo.denomination", SortType.ASC.toValue());
                    break;
                case 3:
                    //市场价倒序
                    queryRequest.putSort("couponInfo.denomination", SortType.DESC.toValue());
                    break;
                default:
                    break;
            }
        } else {
            queryRequest.putSort("couponInfo.denomination", SortType.DESC.toValue());
        }

    }
}
