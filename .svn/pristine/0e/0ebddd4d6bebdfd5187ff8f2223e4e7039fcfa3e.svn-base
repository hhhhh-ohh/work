package com.wanmi.sbc.distribution;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerSaveProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomeffBatchModifyRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerAddForBossRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerListRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerByIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.EsDistributionCustomerAddResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerShowPhoneVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerAddRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerBatchModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerListResponse;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerPageResponse;
import com.wanmi.sbc.elastic.bean.vo.customer.EsDistributionCustomerVO;
import com.wanmi.sbc.util.CommonUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 分销员API
 */
@Tag(name = "DistributionCustomerController", description = "分销员API")
@RestController
@Validated
@RequestMapping("/distribution-customer")
public class DistributionCustomerController {

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private DistributionCustomerSaveProvider distributionCustomerSaveProvider;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired
    private EsDistributionCustomerQueryProvider esDistributionCustomerQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 分页查询分销员
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "分页查询分销员")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> page(@RequestBody EsDistributionCustomerPageRequest queryRequest) {
        if (Objects.nonNull(queryRequest.getPageSize()) && queryRequest.getPageSize() < Constants.ONE) {
            queryRequest.setPageSize(Constants.ONE);
        }
        queryRequest.setDistributorFlag(DefaultFlag.YES);
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        BaseResponse<EsDistributionCustomerPageResponse> page = esDistributionCustomerQueryProvider.page(queryRequest);
        List<String> customerIds = page.getContext().getDistributionCustomerVOPage().getContent()
                .stream()
                .map(EsDistributionCustomerVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContext().getDistributionCustomerVOPage().getContent().forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return ResponseEntity.ok(page);
    }

    /**
     * 新增分销员
     *
     * @param distributionCustomerAddForBossRequest DistributionCustomerAddForBossRequest
     * @return employee
     */
    @Operation(summary = "新增分销员")
    @MultiSubmit
    @RequestMapping(value = "", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> addForBoss(@Valid @RequestBody DistributionCustomerAddForBossRequest distributionCustomerAddForBossRequest) {
        //验证分销员等级是否空
        if (StringUtils.isBlank(distributionCustomerAddForBossRequest.getDistributorLevelId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        distributionCustomerAddForBossRequest.setCustomerType(CustomerType.PLATFORM);
        distributionCustomerAddForBossRequest.setEmployeeId(commonUtil.getOperatorId());
        BaseResponse<EsDistributionCustomerAddResponse> response = distributionCustomerSaveProvider.addForBoss(distributionCustomerAddForBossRequest);
        DistributionCustomerShowPhoneVO distributionCustomerVO = response.getContext().getEsDistributionCustomerVO();
        EsDistributionCustomerAddRequest request = EsDistributionCustomerAddRequest.builder().list(Lists.newArrayList(distributionCustomerVO)).build();
        //同步入到es
        esDistributionCustomerProvider.add(request);
        esCustomerDetailProvider.init(EsCustomerDetailInitRequest
                .builder()
                .idList(request.getList().stream().map(DistributionCustomerShowPhoneVO::getCustomerId).collect(Collectors.toList()))
                .build());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 根据分销员ID查询分销员信息
     *
     * @param distributionId 分销员ID
     * @return
     */
    @Operation(summary = "根据分销员ID查询分销员信息")
    @Parameter(name = "distributionId", description = "分销员ID", required = true)
    @RequestMapping(value = "/{distributionId}", method = RequestMethod.GET)
    public BaseResponse<DistributionCustomerVO> getInfoById(@PathVariable String distributionId) {
        if (StringUtils.isEmpty(distributionId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DistributionCustomerByIdResponse distributionCustomer = distributionCustomerQueryProvider.getById(new DistributionCustomerByIdRequest(distributionId)).getContext();
        return BaseResponse.success(distributionCustomer.getDistributionCustomerVO());
    }

    /**
     * 根据会员ID查询分销员信息
     *
     * @param customerId 会员ID
     * @return
     */
    @Operation(summary = "根据会员ID查询分销员信息")
    @Parameter(name = "customerId", description = "会员id", required = true)
    @RequestMapping(value = "/customer-id/{customerId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_get_info_by_customer_id_sign_word")
    public BaseResponse<DistributionCustomerVO> getInfoByCustomerId(@PathVariable String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        DistributionCustomerByCustomerIdResponse distributionCustomer = distributionCustomerQueryProvider.getByCustomerId(new DistributionCustomerByCustomerIdRequest(customerId)).getContext();
        distributionCustomer.getDistributionCustomerVO().setLogOutStatus(customerCacheService.getCustomerLogOutStatus(customerId));
        return BaseResponse.success(distributionCustomer.getDistributionCustomerVO());
    }

    /**
     * 批量启用/禁用分销员
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "批量启用/禁用分销员")
    @RequestMapping(value = "/forbidden-flag", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse updateForbiddenFlag(@RequestBody DistributionCustomeffBatchModifyRequest queryRequest) {
        if (null == queryRequest.getForbiddenFlag() || CollectionUtils.isEmpty(queryRequest.getDistributionIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 查询用户，获取用户是否注销，注销抛出异常
        DistributionCustomerListRequest customerListRequest = new DistributionCustomerListRequest();
        customerListRequest.setDistributionIdList(queryRequest.getDistributionIds());
        List<DistributionCustomerVO> distributionCustomerVOS = distributionCustomerQueryProvider.list(customerListRequest).getContext().getDistributionCustomerVOList();
        List<String> customerIds =
                distributionCustomerVOS.stream().map(DistributionCustomerVO::getCustomerId).distinct().collect(Collectors.toList());
        Map<String, LogOutStatus> logOutStatusMap = customerCacheService.getLogOutStatus(customerIds);
        boolean logOutFlag = customerIds.stream().anyMatch(customerId -> {
            LogOutStatus logOutStatus = logOutStatusMap.get(customerId);
            return Objects.nonNull(logOutStatus)
                    && Objects.equals(logOutStatus, LogOutStatus.LOGGED_OUT);
        });
        if(logOutFlag){
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010037);
        }

        BaseResponse baseResponse = distributionCustomerSaveProvider.modifyForbiddenFlagById(queryRequest);

        //同步es
        EsDistributionCustomerBatchModifyRequest request = new EsDistributionCustomerBatchModifyRequest(queryRequest.getDistributionIds());
        //同步查询es分销员信息
        BaseResponse<EsDistributionCustomerListResponse> response = esDistributionCustomerQueryProvider.listByIds(request);

        List<DistributionCustomerShowPhoneVO> newList = response.getContext().getList();
        if (CollectionUtils.isNotEmpty(newList)) {
            List<DistributionCustomerShowPhoneVO> list = response.getContext().getList().stream()
                    .peek(entity -> {
                        entity.setForbiddenFlag(queryRequest.getForbiddenFlag());
                        entity.setForbiddenReason(queryRequest.getForbiddenReason());
                    }).collect(Collectors.toList());

            EsDistributionCustomerAddRequest req = EsDistributionCustomerAddRequest.builder().list(list).build();
            //数据覆盖
            esDistributionCustomerProvider.add(req);
        }
        return baseResponse;
    }
}
