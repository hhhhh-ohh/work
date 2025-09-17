package com.wanmi.sbc.marketing.coupon.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.handler.aop.MasterRouteOnly;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCheckoutResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeValidOrderCommitResponse;
import com.wanmi.sbc.marketing.api.response.coupon.GetCouponGroupResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponActivityConfigAndCouponInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeAutoSelectDTO;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeBatchModifyDTO;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.common.request.TradeItemInfo;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.request.CouponCodeListForUseRequest;
import com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest;
import com.wanmi.sbc.marketing.coupon.response.CouponCodeQueryResponse;
import com.wanmi.sbc.marketing.coupon.response.CouponLeftResponse;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/***
 *
 * @className CouponCodeServiceInterface
 * @author zhengyang
 * @date 2021/10/29 11:11
 **/
public interface CouponCodeServiceInterface {
    /**
     * 根据条件查询优惠券码列表
     */
    List<CouponCode> listCouponCodeByCondition(CouponCodeQueryRequest request);

    Page<CouponCode> pageCouponCodeByCondition(CouponCodeQueryRequest request);

    /**
     * 查询我的未使用优惠券(订单)
     */
    @MasterRouteOnly
    List<CouponCode> findNotUseStatusCouponCode(CouponCodeQueryRequest request);

    /**
     * 查询使用优惠券页需要的优惠券列表
     */
    @Transactional
    List<CouponCodeVO> listCouponCodeForUse(CouponCodeListForUseRequest request);

    /**
     * 根据传入的券列表，返回自动选券列表
     */
    List<CouponCodeAutoSelectDTO> autoSelectForCart(CouponCodeListForUseRequest request, CouponCodeAutoSelectForCartRequest selectRequest);

    /**
     * 过滤出优惠券包含的商品列表
     *
     * @param tradeItems 待过滤商品列表
     * @param couponCode 优惠券信息
     * @param scopeList  优惠券作用范围
     * @return
     */
    List<TradeItemInfo> listCouponSkuIds(List<TradeItemInfo> tradeItems, CouponCodeVO couponCode,
                                         List<CouponMarketingScope> scopeList);


    /**
     * 根据勾选的优惠券，返回不可用的平台券、以及优惠券实际优惠总额、每个店铺优惠总额
     */
    CouponCheckoutResponse checkoutCoupons(CouponCheckoutRequest request);

    /**
     * APP / H5 查询我的优惠券
     *
     * @param request
     * @return
     * @returncustomerFetchCoupon
     */
    CouponCodeQueryResponse listMyCouponList(CouponCodePageRequest request);

    /**
     * PC - 分页查询我的优惠券
     *
     * @param request
     * @return
     */
    Page<CouponCodeVO> pageMyCouponList(CouponCodePageRequest request);

    /**
     * 用户领取优惠券
     *
     * @param couponFetchRequest
     */
    @Transactional(rollbackFor = Exception.class)
    BaseResponse customerFetchCoupon(CouponFetchRequest couponFetchRequest);

    /**
     * 获取优惠券库存 -- 不精确
     *
     * @param activityId
     * @param couponId
     * @param totalCount
     * @param hasLeft
     * @return
     */
    long getCouponLeftCount(String activityId, String couponId, Long totalCount, DefaultFlag hasLeft);

    /**
     * 批量获取优惠券的领取状态
     *
     * @param couponCacheList
     * @return <优惠券活动Id，<优惠券Id, 领取状态>>
     */
    Map<String, Map<String, CouponLeftResponse>> mapLeftCount(List<CouponCache> couponCacheList);

    /**
     * 批量获取优惠券领用状态
     *
     * @param couponCacheList
     * @return <优惠券活动Id，<优惠券Id, 领取状态>>
     */
    Map<String, Map<String, String>> mapFetchStatus(List<CouponCache> couponCacheList, String customerId);

    /**
     * 批量修改优惠券
     */
    @Transactional
    void batchModify(List<CouponCodeBatchModifyDTO> batchModifyDTOList);

    /**
     * 发放优惠券码
     *
     * @return
     */
    @Transactional
    CouponCode sendCouponCode(CouponInfo couponInfo, CouponFetchRequest couponFetchRequest);

    /**
     * 退优惠券
     *
     * @param couponCodeId
     * @parem customerId
     */
    @Transactional
    void returnCoupon(String couponCodeId, String customerId);

    /**
     * 对同一个用户，批量发放优惠券
     *
     * @param couponInfoList
     * @param customerId
     * @param couponActivityId
     * @return
     */
    @Transactional
    List<CouponCode> sendBatchCouponCodeByCustomer(List<GetCouponGroupResponse> couponInfoList,
                                                   String customerId, String couponActivityId,String recordId);

    /**
     * 批量发券，根据会员id和活动id
     */
    List<String> sendBatchCouponCodeByCustomerList(CouponCodeBatchSendCouponRequest request);

    /**
     * @param customerId
     * @return
     */
    Integer countByCustomerId(String customerId);

    /**
     * 根据customerId和activityId查询优惠券数量
     *
     * @param customerId
     * @param activieyId
     * @return
     */
    Integer countByCustomerIdAndActivityId(String customerId, String activieyId);

    /**
     * 精准发券
     *
     * @param customerIds
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    void precisionVouchers(List<String> customerIds, List<CouponActivityConfigAndCouponInfoDTO> list);

    /**
     * 验证优惠券
     *
     * @param request
     * @return
     */
    CouponCodeValidOrderCommitResponse validOrderCommit(CouponCodeValidOrderCommitRequest request);

    /**
     * 满返支付精准发券
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean precisionCoupon(CouponCodeBatchSendFullReturnRequest request);

    /**
     * 批量修改优惠券
     */
    @Transactional
    void modifyByCustomerId(String customerId);

    /**
     * 修改优惠券发送订阅消息状态
     */
    @Transactional
    void updateCouponExpiredSendFlagById(String id);


    @Transactional
    void recycleCoupon(String couponCodeId, String customerId);
}
