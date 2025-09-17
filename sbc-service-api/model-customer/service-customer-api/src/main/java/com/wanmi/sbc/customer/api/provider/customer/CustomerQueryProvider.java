package com.wanmi.sbc.customer.api.provider.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.customer.*;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerByGrowthValueRequest;
import com.wanmi.sbc.customer.api.response.customer.*;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(value = "${application.customer.name}", contextId = "CustomerQueryProvider")
public interface CustomerQueryProvider {
    /**
     * 条件查询会员带分页
     *
     * @param request {@link CustomerDetailPageRequest}
     * @return {@link CustomerDetailPageResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/page")
    BaseResponse<CustomerDetailPageResponse> page(@RequestBody @Valid CustomerDetailPageRequest request);


    /**
     * 查询会员数量
     *
     * @param request {@link CustomerDetailPageRequest}
     * @return {@link CustomerDetailPageResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/count")
    BaseResponse<Long> count(@RequestBody @Valid CustomerDetailPageRequest request);

    /**
     * 企业购条件查询会员带分页
     *
     * @param request {@link EnterpriseCustomerPageRequest}
     * @return {@link CustomerDetailPageResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/page-for-enterprise")
    BaseResponse<CustomerDetailPageResponse> pageForEnterpriseCustomer(@RequestBody @Valid EnterpriseCustomerPageRequest request);

    /**
     * 条件查询会员带分页(S2b)
     *
     * @param request {@link CustomerDetailPageForSupplierRequest}
     * @return {@link CustomerDetailPageResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/page-for-s2b-supplier")
    BaseResponse<CustomerDetailPageForSupplierResponse> pageForS2bSupplier(@RequestBody @Valid
                                                                                   CustomerDetailPageForSupplierRequest request);

    /**
     * 多条件查询会员详细信息
     *
     * @param request {@link CustomerDetailListByConditionRequest}
     * @return {@link CustomerDetailListByConditionResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-detail-by-condition")
    BaseResponse<CustomerDetailListByConditionResponse> listCustomerDetailByCondition(@RequestBody @Valid
                                                                                               CustomerDetailListByConditionRequest request);

    /**
     * 多条件查询会员详细信息
     *
     * @param request {@link CustomerDetailListForOrderRequest}
     * @return {@link CustomerDetailListForOrderResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-detail-for-order")
    BaseResponse<CustomerDetailListForOrderResponse> listCustomerDetailForOrder(@RequestBody @Valid CustomerDetailListForOrderRequest request);

    /**
     * 多条件查询会员详细信息
     *
     * @param request {@link CustomerDetailListByPageRequest}
     * @return {@link CustomerDetailListByPageResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-detail-by-page")
    BaseResponse<CustomerDetailListByPageResponse> listCustomerDetailByPage(@RequestBody @Valid CustomerDetailListByPageRequest request);

    /**
     * 根据会员的名称和账号模糊查询会员详细信息
     *
     * @param request {@link CustomerDetailListByPageRequest}
     * @return {@link CustomerDetailListByPageResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-detail-for-distribution-record")
    BaseResponse<CustomerDetailListByPageResponse> listCustomerDetailForDistributionRecord(@RequestBody @Valid
                                                                                                   CustomerDetailListByPageFordrRequest request);


    /**
     * 根据业务员id获取客户列表
     *
     * @param request {@link CustomerIdListRequest}
     * @return {@link CustomerIdListResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-id-by-employeeId")
    BaseResponse<CustomerIdListResponse> listCustomerId(@RequestBody @Valid CustomerIdListRequest request);

    /**
     * 获取id客户列表
     *
     * @return {@link CustomerIdListResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-id")
    BaseResponse<CustomerIdListResponse> listCustomerId();

    /**
     * 模糊查询会员信息
     *
     * @param request {@link CustomerListByConditionRequest}
     * @return {@link CustomerListByConditionResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-by-condition")
    BaseResponse<CustomerListByConditionResponse> listCustomerByCondition(@RequestBody @Valid CustomerListByConditionRequest request);

    /**
     * 查询单条会员信息（任意状态）,没有拼装第三方的头像信息，用的时候要注意，如果要拼装第三方数据信息请用
     * @see CustomerQueryProvider#getCustomerById(com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest)
     *
     * @param request {@link CustomerGetByIdRequest}
     * @return {@link CustomerGetByIdResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-no-third-img-by-id")
    BaseResponse<CustomerGetByIdResponse> getCustomerNoThirdImgById(@RequestBody @Valid CustomerGetByIdRequest request);

    /**
     * 查询单条会员信息（任意状态）
     *
     * @param request {@link CustomerGetByIdRequest}
     * @return {@link CustomerGetByIdResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-by-id")
    BaseResponse<CustomerGetByIdResponse> getCustomerById(@RequestBody @Valid CustomerGetByIdRequest request);

    /**
     * 查询单条会员信息（任意状态）
     *
     * @param request {@link CustomerGetByIdRequest}
     * @return {@link CustomerGetByIdResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/customer-info-by-id")
    BaseResponse<CustomerGetByIdResponse> getCustomerInfoById(@RequestBody @Valid CustomerGetByIdRequest request);

    /**
     * 删除标记
     *
     * @param request {@link CustomerDelFlagGetRequest}
     * @return {@link CustomerDelFlagGetResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-del-flag")
    BaseResponse<CustomerDelFlagGetResponse> getCustomerDelFlag(@RequestBody @Valid CustomerDelFlagGetRequest request);

    /**
     * 查询是否有客户获取了成长值
     *
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/has-obtained-growth-value")
    BaseResponse<Boolean> hasObtainedGrowthValue();

    /**
     * 根据账号获取会员信息
     *
     * @param request {@link NoDeleteCustomerGetByAccountRequest}
     * @return {@link NoDeleteCustomerGetByAccountResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-by-condition")
    BaseResponse<NoDeleteCustomerGetByAccountResponse> getNoDeleteCustomerByAccount(@RequestBody @Valid NoDeleteCustomerGetByAccountRequest request);

    /**
     * 检查账户是否禁用
     *
     * @param request {@link DisableCustomerDetailGetByAccountRequest}
     * @return {@link DisableCustomerDetailGetByAccountResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-detail-by-condition")
    BaseResponse<DisableCustomerDetailGetByAccountResponse> getDisableCustomerDetailByAccount(@RequestBody @Valid DisableCustomerDetailGetByAccountRequest request);

    /**
     * 根据审核状态统计用户
     *
     * @param request {@link CustomerCountByStateRequest}
     * @return {@link CustomerCountByStateResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/count-customer-by-state")
    BaseResponse<CustomerCountByStateResponse> countCustomerByState(@RequestBody @Valid CustomerCountByStateRequest request);

    /**
     * 根据审核状态统计增票资质
     *
     * @param request {@link InvoiceCountByStateRequest}
     * @return {@link InvoiceCountByStateResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/count-invoice-by-state")
    BaseResponse<InvoiceCountByStateResponse> countInvoiceByState(@RequestBody @Valid InvoiceCountByStateRequest request);

    /**
     * 按照客户名称模糊匹配，当前商家未关联的平台客户
     *
     * @param request {@link CustomerNotRelatedListRequest}
     * @return {@link CustomerNotRelatedListResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-not-related")
    BaseResponse<CustomerNotRelatedListResponse> listCustomerNotRelated(@RequestBody @Valid CustomerNotRelatedListRequest request);

    /**
     * 获取客户详情信息，包括业务员名称，等级，等级扣率
     *
     * @param request {@link CustomerGetForSupplierRequest}
     * @return {@link CustomerGetForSupplierResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-for-supplier")
    BaseResponse<CustomerGetForSupplierResponse> getCustomerForSupplier(@RequestBody @Valid CustomerGetForSupplierRequest request);

    /**
     * 根据成长值获取客户列表
     *
     * @param request {@link CustomerIdListRequest}
     * @return {@link CustomerIdListResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-id-by-growth-value")
    BaseResponse<CustomerIdListResponse> listCustomerIdByGrowthValue(@RequestBody @Valid CustomerByGrowthValueRequest request);

    /**
     * 根据会员ID、是否删除查询会员基础信息
     * @param request  {@link CustomerIdListRequest}
     * @return  {@link CustomerBaseByCustomerIdAndDeleteFlagResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-base-customer")
    BaseResponse<CustomerBaseByCustomerIdAndDeleteFlagResponse> getBaseCustomer(@RequestBody @Valid CustomerBaseByCustomerIdAndDeleteFlagRequest request);

    /**
     * 查询未删除、已审核的会员ID集合
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-id-by-pageable")
    BaseResponse<CustomerListCustomerIdByPageableResponse> listCustomerIdByPageable(@RequestBody @Valid CustomerListCustomerIdByPageableRequest request);

    /**
     * 根据ids查询会员列表（不涉及关联查询）
     *
     * @return 第三方登录方式列表 {@link CustomerIdsListRequest}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-base-customer-by-ids")
     BaseResponse<CustomerBaseListByIdsResponse> getCustomerListByIds(@RequestBody @Valid CustomerIdsListRequest
                                                                                  request);

    /**
     *  根据会员ID查询会员的可用积分
     *
     * @return 可用积分 {@link CustomerPointsAvailableByCustomerIdResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-points-available")
    BaseResponse<CustomerPointsAvailableByCustomerIdResponse> getPointsAvailable(@RequestBody @Valid CustomerPointsAvailableByIdRequest request);

    @PostMapping("/customer/${application.customer.version}/get-customers-by-phones")
    BaseResponse<CustomerByPhonesResponse> getCustomersByPhones(@RequestBody List<String> phones);

    /**
     * 查询单条会员信息（精简版）
     *
     * @param request {@link CustomerGetByIdRequest}
     * @return {@link CustomerGetByIdResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/simplify-by-id")
    BaseResponse<CustomerSimplifyByIdResponse> simplifyById(@RequestBody @Valid CustomerSimplifyByIdRequest request);

    /**
     * 查询会员信息
     *
     * @param request {@link CustomerDetailListByCustomerIdsRequest}
     * @return {@link CustomerDetailListByCustomerIdsResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-by-customer-ids")
    BaseResponse<CustomerDetailListByCustomerIdsResponse> listByCustomerIds(@RequestBody @Valid CustomerDetailListByCustomerIdsRequest request);

    /**
     * 初始化会员ES
     *
     * @param request {@link CustomerDetailInitEsRequest}
     * @return {@link CustomerDetailInitEsResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-by-page")
    BaseResponse<CustomerDetailInitEsResponse> listByPage(@RequestBody @Valid CustomerDetailInitEsRequest request);

    /***
     * 查询客户基础信息
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/get-customer-by-customerid")
    BaseResponse<CustomerVO> getCustomerByCustomerId(@RequestBody @Valid CustomerDelFlagGetRequest request);

    /***
     * 查询客户基础信息
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-ids-by-employee-ids")
    BaseResponse<CustomerIdsByEmployeeIdsResponse> listCustomerIdsByEmployeeIds(@RequestBody @Valid CustomerIdsByEmployeeIdsRequest request);

    /***
     * 根据会员等级和升级时间查询会员ids
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-ids-by-upgrade-time")
    BaseResponse<CustomerListCustomerIdByPageableResponse> listCustomerIdsByUpgradeTime(@RequestBody @Valid CustomerIdsByUpgradeTimeRequest request);

    /**
     * 根据会员id判断会员是否是企业会员
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/is-iep-customer")
    BaseResponse<Boolean> isIepCustomer(@RequestBody @Valid CustomerGetByIdRequest request);

    /**
     * 根据会员id判断会员是否是分销员
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/is-distributor")
    BaseResponse<Boolean> isDistributor(@RequestBody @Valid CustomerGetByIdRequest request);

    /**
     * 模糊查询会员信息
     *
     * @param request {@link CustomerListByConditionRequest}
     * @return {@link CustomerListByConditionResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-by-condition-new")
    BaseResponse<CustomerListByConditionResponse> listCustomerByConditionNew(@RequestBody @Valid CustomerListByConditionRequest request);



    /**
     * 根据会员id判断会员是否是新人
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/is-new-customer")
    BaseResponse<Integer> isNewCustomer(@RequestBody @Valid CustomerGetByIdRequest request);

    /**
     * 获取id客户列表 按时间范围
     *
     * @return {@link CustomerIdListResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-customer-id-by-expiredtimme")
    BaseResponse<CustomerIdListResponse> CustomerIdsByMembershipExpiredTimeRequest(@RequestBody @Valid CustomerIdsByMembershipExpiredTimeRequest  request);
}
