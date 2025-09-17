package com.wanmi.sbc.empower.provider.impl.customerservice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingListRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingPageRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingQueryRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByIdResponse;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByStoreIdResponse;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingListResponse;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingPageResponse;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSetting;
import com.wanmi.sbc.empower.customerservice.service.CustomerServiceSettingService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 在线客服配置查询服务接口实现
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@RestController
@Validated
public class CustomerServiceSettingQueryController implements CustomerServiceSettingQueryProvider {
    @Autowired private CustomerServiceSettingService customerServiceSettingService;

    @Override
    public BaseResponse<CustomerServiceSettingPageResponse> page(
            @RequestBody @Valid CustomerServiceSettingPageRequest customerServiceSettingPageReq) {
        CustomerServiceSettingQueryRequest queryReq =
                KsBeanUtil.convert(
                        customerServiceSettingPageReq, CustomerServiceSettingQueryRequest.class);
        Page<CustomerServiceSetting> customerServiceSettingPage =
                customerServiceSettingService.page(queryReq);
        Page<CustomerServiceSettingVO> newPage =
                customerServiceSettingPage.map(
                        entity -> customerServiceSettingService.wrapperVo(entity));
        MicroServicePage<CustomerServiceSettingVO> microPage =
                new MicroServicePage<>(newPage, customerServiceSettingPageReq.getPageable());
        CustomerServiceSettingPageResponse finalRes =
                new CustomerServiceSettingPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<CustomerServiceSettingListResponse> list(
            @RequestBody @Valid CustomerServiceSettingListRequest customerServiceSettingListReq) {
        CustomerServiceSettingQueryRequest queryReq =
                KsBeanUtil.convert(
                        customerServiceSettingListReq, CustomerServiceSettingQueryRequest.class);
        List<CustomerServiceSetting> customerServiceSettingList =
                customerServiceSettingService.list(queryReq);
        List<CustomerServiceSettingVO> newList =
                customerServiceSettingList.stream()
                        .map(entity -> customerServiceSettingService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new CustomerServiceSettingListResponse(newList));
    }

    @Override
    public BaseResponse<CustomerServiceSettingByIdResponse> getById(
            @RequestBody @Valid
                    CustomerServiceSettingByIdRequest customerServiceSettingByIdRequest) {
        CustomerServiceSetting customerServiceSetting =
                customerServiceSettingService.getOne(customerServiceSettingByIdRequest.getId());
        return BaseResponse.success(
                new CustomerServiceSettingByIdResponse(
                        customerServiceSettingService.wrapperVo(customerServiceSetting)));
    }

    @Override
    public BaseResponse<CustomerServiceSettingByStoreIdResponse> getByStoreId(
            @Valid CustomerServiceSettingByStoreIdRequest customerServiceSettingByStoreIdRequest) {
        if(customerServiceSettingByStoreIdRequest.isBffWeb()){
            customerServiceSettingByStoreIdRequest.setStatus(DefaultFlag.YES);
        }else{
            customerServiceSettingByStoreIdRequest.setStatus(null);
        }
        List<CustomerServiceSetting> customerServiceSettingList =
                customerServiceSettingService.list(CustomerServiceSettingQueryRequest.builder()
                        .storeId(customerServiceSettingByStoreIdRequest.getStoreId())
                        .platformType(customerServiceSettingByStoreIdRequest.getPlatformType())
                        .delFlag(DeleteFlag.NO)
                        .status(customerServiceSettingByStoreIdRequest.getStatus())
                        .build());
        // 客服配置不存在时，初始化配置
        if (CollectionUtils.isEmpty(customerServiceSettingList)
                && Objects.nonNull(customerServiceSettingByStoreIdRequest.getPlatformType())) {
            customerServiceSettingList =
                    Collections.singletonList(customerServiceSettingService.initByStoreId(
                            customerServiceSettingByStoreIdRequest.getStoreId(),
                            customerServiceSettingByStoreIdRequest.getPlatformType()));
        }
        if (CollectionUtils.isEmpty(customerServiceSettingList)){
            return BaseResponse.success(null);
        }
        CustomerServiceSettingVO customerServiceSettingVO =
                customerServiceSettingService.wrapperVo(customerServiceSettingList.get(0));
        if(CustomerServicePlatformType.WECHAT.equals((customerServiceSettingByStoreIdRequest.getPlatformType()))){
            return BaseResponse.success(
                    new CustomerServiceSettingByStoreIdResponse(
                            null,
                            customerServiceSettingVO,
                            null,
                            customerServiceSettingVO.getCustomerServiceSettingItemVOList(),
                            null));
        }
        if(CustomerServicePlatformType.QIYU.equals((customerServiceSettingByStoreIdRequest.getPlatformType()))){
            return BaseResponse.success(
                    new CustomerServiceSettingByStoreIdResponse(
                            null,
                            null,
                            null,
                            null,
                            customerServiceSettingVO));
        }
        return BaseResponse.success(
                new CustomerServiceSettingByStoreIdResponse(
                        customerServiceSettingVO,
                        null,
                        customerServiceSettingVO.getCustomerServiceSettingItemVOList(),
                        null,
                        null));
    }
}
