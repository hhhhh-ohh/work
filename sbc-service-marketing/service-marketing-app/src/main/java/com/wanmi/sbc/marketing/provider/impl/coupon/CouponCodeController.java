package com.wanmi.sbc.marketing.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeBatchSendResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponNotFetchCountResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponActivityConfigAndCouponInfoDTO;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingCustomerScope;
import com.wanmi.sbc.marketing.coupon.model.vo.CouponView;
import com.wanmi.sbc.marketing.coupon.request.CouponCacheCenterRequest;
import com.wanmi.sbc.marketing.coupon.response.CouponCenterPageResponse;
import com.wanmi.sbc.marketing.coupon.service.CouponCacheService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeCopyService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.coupon.service.CouponMarketingCustomerScopeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>对优惠券码操作接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@Validated
@RestController
@Slf4j
public class CouponCodeController implements CouponCodeProvider {

    @Autowired
    private CouponCodeService couponCodeService;

    @Autowired
    private CouponCodeCopyService couponCodeCopyService;

    @Autowired
    private CouponMarketingCustomerScopeService couponMarketingCustomerScopeService;

    @Autowired
    private CouponCacheService couponCacheService;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 领取优惠券
     *
     * @param request 优惠券领取请求结构 {@link CouponFetchRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse fetch(@RequestBody @Valid CouponFetchRequest request){
        return couponCodeService.customerFetchCoupon(request);
    }

    /**
     * 自动领取可领优惠券
     * @param request
     * @return
     */
    @Override
    public BaseResponse autoFetchCoupons(CouponAutoFetchRequest request) {
        // 获取客户ID
        String customerId = request.getCustomerId();
        // 加锁避免重复领取，复用领取1张券的redisKey，couponId用-1填充
        String lockKey = couponCodeService.getCouponBankKeyForCustomer(customerId, "-1");
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            CouponCacheListForGoodsListRequest couponListRequest = new CouponCacheListForGoodsListRequest();
            couponListRequest.setGoodsInfoIds(request.getGoodsInfoIds());
            couponListRequest.setCustomerId(request.getCustomerId());
            List<CouponView> couponViews = couponCacheService.listCouponForGoodsList(
                    request.getGoodsInfoIds(), customerId, null, PluginType.NORMAL).getCouponViews();
            // 筛选 有剩余 && 未领取 的优惠券
            List<CouponView> waitFetchCoupons = couponViews.stream()
                    .filter(coupon -> coupon.isLeftFlag() && !coupon.isHasFetched())
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(waitFetchCoupons)) {
                // 遍历券列表，循环领券
                waitFetchCoupons.forEach(item -> {
                    try {
                        couponCodeService.autoFetchCoupon(item, customerId);
                    } catch (Exception e) {
                        log.error("客户[{}]自动领取券[{}]失败", customerId, item.getCouponId(), e);
                    }
                });
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 批量更新券码使用状态
     *
     * @param request 批量修改请求结构 {@link CouponCodeBatchModifyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse batchModify(@RequestBody @Valid CouponCodeBatchModifyRequest request){
        couponCodeService.batchModify(request.getModifyDTOList());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据id撤销优惠券使用
     *
     * @param request 包含id的撤销使用请求结构 {@link CouponCodeReturnByIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse returnById(@RequestBody @Valid CouponCodeReturnByIdRequest request){
        couponCodeService.returnCoupon(request.getCouponCodeId(), request.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse precisionVouchers(@RequestBody @Valid CouponCodeBatchSendCouponRequest request){
        List<String> customerIds = request.getCustomerIds();
        List<CouponActivityConfigAndCouponInfoDTO> list = request.getList();
        if (CollectionUtils.isEmpty(customerIds)){
            List<CouponMarketingCustomerScope> customerScopes = couponMarketingCustomerScopeService.findByActivityId(list.get(0).getActivityId());
            customerIds = customerScopes.stream().map(CouponMarketingCustomerScope::getCustomerId).collect(Collectors.toList());
        }
        couponCodeService.precisionVouchers(customerIds,list);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 数据迁移：旧coupon_code按照新的分表规则进行拆分保存至新表中
     * @return
     */
    @Override
    public BaseResponse dataMigrationFromCouponCode() {
        Integer pageNum = NumberUtils.INTEGER_ZERO;
        Integer pageSize = 5000;
        int result = NumberUtils.INTEGER_ZERO;
        while (true) {
            int num = couponCodeCopyService.dataMigrationFromCouponCode(pageNum,pageSize);
            if (num == 0 ){
                break;
            }
            result += num;
        }
        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse sendBatchCouponCodeByCustomerList(@RequestBody CouponCodeBatchSendCouponRequest request) {

        return BaseResponse.success(this.couponCodeService.sendBatchCouponCodeByCustomerList(request));
    }

    @Override
    public BaseResponse<CouponCodeBatchSendResponse> sendBatchCouponCodeByCustomers(@RequestBody @Valid CouponCodeBatchSendRequest request){
        List<CouponCodeVO> codeList = this.couponCodeService.sendBatchCouponCodeByCustomer(request);
        return BaseResponse.success(new CouponCodeBatchSendResponse(codeList));
    }

    @Override
    public BaseResponse precisionCoupon(@RequestBody @Valid CouponCodeBatchSendFullReturnRequest request){
        return BaseResponse.success(couponCodeService.precisionCoupon(request));
    }

    /**
     * 注销后更新优惠券过期时间
     *
     * @param request 包含id的撤销使用请求结构 {@link CouponCodeReturnByIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse modifyByCustomerId(@RequestBody CouponCodeReturnByIdRequest request){
        couponCodeService.modifyByCustomerId(request.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateCouponExpiredSendFlagById(@RequestBody CouponCodeReturnByIdRequest request){
        couponCodeService.updateCouponExpiredSendFlagById(request.getCouponCodeId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse recycleCoupon(@RequestBody @Valid CouponCodeRecycleByIdRequest request) {
        couponCodeService.recycleCoupon(request.getCouponCodeId(), request.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }

    /***
     * 统计用户可领取但未领取的优惠券数量
     * @param request
     * @return
     */
    @Override
    public BaseResponse notFetchCount(CouponCacheCenterPageRequest request) {

        long count = 0;
        Integer page = Constants.ZERO;
        while (true){
            CouponCacheCenterRequest convert = KsBeanUtil.convert(request,
                    CouponCacheCenterRequest.class);
            convert.setPageNum(page++);
            convert.setPageSize(Constants.NUM_50);
            CouponCenterPageResponse response = couponCacheService.getCouponStarted(convert);

            List<CouponView> couponViews = response.getCouponViews().getContent();

            if (CollectionUtils.isNotEmpty(couponViews)){
                count += couponViews.stream().filter(couponView -> !couponView.isHasFetched() && couponView.isLeftFlag()).count();
            }else {
                break;
            }
            if (count >= Constants.NUM_100){
                break;
            }
        }

        return BaseResponse.success(
                CouponNotFetchCountResponse.builder().couponCount(count).build());
    }

    @Override
    public BaseResponse sendBatchCouponCodeAsyncByCustomers(@RequestBody @Valid CouponCodeBatchSendRequest request){
//         couponCodeService.couponSendBatchAsync(request);
        List<String> customerIds = request.getCustomerIds();
        List<CouponActivityConfigAndCouponInfoDTO> list = request.getList();
        try {
            couponCodeService.sendCouponToCustomer(customerIds, list);
        } catch (Exception e) {
            log.error("精准发券子批次处理失败", e);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
