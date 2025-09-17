package com.wanmi.sbc.empower.provider.impl.customerservice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.customerservice.CustomerServiceSettingProvider;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingModifyRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingQueryRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingModifyResponse;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingItemVO;
import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSetting;
import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSettingItem;
import com.wanmi.sbc.empower.customerservice.service.CustomerServiceSettingItemService;
import com.wanmi.sbc.empower.customerservice.service.CustomerServiceSettingService;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 在线客服配置保存服务接口实现
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@RestController
@Validated
public class CustomerServiceSettingController implements CustomerServiceSettingProvider {
    @Autowired
    private CustomerServiceSettingService customerServiceSettingService;
    @Autowired
    private CustomerServiceSettingItemService customerServiceSettingItemService;

    @Autowired
    private RedisUtil redisService;

    @Override
    public BaseResponse<CustomerServiceSettingModifyResponse> modify(
            @RequestBody @Valid
                    CustomerServiceSettingModifyRequest customerServiceSettingModifyRequest) {
        // 在线客服设置
        CustomerServiceSettingVO customerServiceSettingVO =
                customerServiceSettingModifyRequest.getQqOnlineServerRop();

        if (Objects.isNull(customerServiceSettingVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (customerServiceSettingVO.getServerStatus() != null) {
            customerServiceSettingVO.setStatus(customerServiceSettingVO.getServerStatus());
        }

        // 客服座席列表
        List<CustomerServiceSettingItemVO> onlineServerItemVoList =
                customerServiceSettingModifyRequest.getQqOnlineServerItemRopList();
        if(CustomerServicePlatformType.QQ.equals(customerServiceSettingVO.getPlatformType())){
            if (DefaultFlag.YES.equals(customerServiceSettingVO.getServerStatus())){
                //启用
                if (CollectionUtils.isEmpty(onlineServerItemVoList) || onlineServerItemVoList.size()> Constants.TEN){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }
        // 在线客服不能同时开启多个
        if (DefaultFlag.YES.equals(customerServiceSettingVO.getServerStatus())) {
            List<CustomerServiceSetting> openedList =
                    customerServiceSettingService.list(
                            CustomerServiceSettingQueryRequest.builder()
                                    .storeId(customerServiceSettingVO.getStoreId())
                                    .delFlag(DeleteFlag.NO)
                                    .status(DefaultFlag.YES)
                                    .build());
            if (openedList.stream()
                    .anyMatch(
                            customerServiceSetting ->
                                    !Objects.equals(
                                            customerServiceSettingVO.getId(),
                                            customerServiceSetting.getId()))) {
                throw new SbcRuntimeException(
                        EmpowerErrorCodeEnum.K060028);
            }
        }

        CustomerServiceSetting customerServiceSetting =
                customerServiceSettingService.getOne(
                        customerServiceSettingVO.getId() == null
                                ? customerServiceSettingVO.getOnlineServiceId()
                                : customerServiceSettingVO.getId());
        KsBeanUtil.copyPropertiesThird(customerServiceSettingVO, customerServiceSetting);

        // 删除客服座席
        customerServiceSettingItemService.deleteByCustomerServiceId(customerServiceSetting.getId());
        if (CollectionUtils.isNotEmpty(onlineServerItemVoList)) {
            List<String> serverAccount =
                    onlineServerItemVoList.stream()
                            .map(vo -> vo.getCustomerServiceAccount())
                            .collect(Collectors.toList());
            // 查询重复的QQ号
            List<CustomerServiceSettingItem> duplicateItem =
                    customerServiceSettingItemService.checkDuplicateAccount(serverAccount);
            duplicateItem =
                    duplicateItem.stream()
                            .filter(
                                    item ->
                                            item.getStoreId()
                                                    .equals(customerServiceSetting.getStoreId()))
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(duplicateItem)) {
                List<String> dupAccounts =
                        duplicateItem.stream()
                                .map(item -> item.getCustomerServiceAccount())
                                .collect(Collectors.toList());
                String dupAccount = StringUtils.join(dupAccounts, ",");
                throw new SbcRuntimeException(
                        SettingErrorCodeEnum.K070069,
                        new Object[] {dupAccount});
            }

            List<CustomerServiceSettingItem> customerServiceSettingItemList =
                    onlineServerItemVoList.stream()
                            .map(
                                    customerServiceSettingItemVO -> {
                                        CustomerServiceSettingItem customerServiceSettingItem =
                                                KsBeanUtil.copyPropertiesThird(
                                                        customerServiceSettingItemVO,
                                                        CustomerServiceSettingItem.class);
                                        customerServiceSettingItem.setDelFlag(DeleteFlag.NO);
                                        customerServiceSettingItem.setCustomerServiceId(
                                                Long.valueOf(
                                                        customerServiceSettingItemVO
                                                                .getOnlineServiceId()));
                                        return customerServiceSettingItem;
                                    })
                            .collect(Collectors.toList());
            // 保存客服座席
            customerServiceSettingItemService.save(customerServiceSettingItemList);
        }

        return BaseResponse.success(
                new CustomerServiceSettingModifyResponse(
                        customerServiceSettingService.wrapperVo(
                                customerServiceSettingService.modify(customerServiceSetting))));
    }

    @Override
    public BaseResponse<CustomerServiceSettingModifyResponse> qiYuModify(@Valid CustomerServiceSettingModifyRequest customerServiceSettingModifyRequest) {
        // 在线客服设置
        CustomerServiceSettingVO customerServiceSettingVO =
                customerServiceSettingModifyRequest.getQiYuOnlineServerRop();

        if (Objects.isNull(customerServiceSettingVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 在线客服不能同时开启多个
        if (DefaultFlag.YES.equals(customerServiceSettingVO.getStatus())) {
            List<CustomerServiceSetting> openedList =
                    customerServiceSettingService.list(
                            CustomerServiceSettingQueryRequest.builder()
                                    .storeId(customerServiceSettingVO.getStoreId())
                                    .delFlag(DeleteFlag.NO)
                                    .status(DefaultFlag.YES)
                                    .build());
            if (openedList.stream()
                    .anyMatch(
                            customerServiceSetting ->
                                    !Objects.equals(
                                            customerServiceSettingVO.getId(),
                                            customerServiceSetting.getId()))) {
                throw new SbcRuntimeException(
                        EmpowerErrorCodeEnum.K060028);
            }
        }
        if (0L != customerServiceSettingModifyRequest.getStoreId()){
            List<CustomerServiceSetting> customerServiceSettingList =
                    customerServiceSettingService.list(CustomerServiceSettingQueryRequest.builder()
                            .storeId(0L)
                            .platformType(CustomerServicePlatformType.QIYU)
                            .delFlag(DeleteFlag.NO)
                            .status(DefaultFlag.YES)
                            .build());
            // boss客服配置不存在时，抛异常
            if (customerServiceSettingVO.getStatus() == DefaultFlag.YES && CollectionUtils.isEmpty(customerServiceSettingList)){
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060035);
            }
        }

        CustomerServiceSetting customerServiceSetting =
                customerServiceSettingService.getOne(
                        customerServiceSettingVO.getId() == null
                                ? customerServiceSettingVO.getOnlineServiceId()
                                : customerServiceSettingVO.getId());
        KsBeanUtil.copyPropertiesThird(customerServiceSettingVO, customerServiceSetting);
        //删除缓存
        redisService.delete(RedisKeyConstant.CUSTOMER_SERVICE_QIYU_INFO + customerServiceSettingModifyRequest.getStoreId());
        return BaseResponse.success(
                new CustomerServiceSettingModifyResponse(
                        customerServiceSettingService.wrapperVo(
                                customerServiceSettingService.modify(customerServiceSetting))));
    }


    @Override
    @GlobalTransactional
    public BaseResponse<CustomerServiceSettingModifyResponse> weChatModify(
            @RequestBody @Valid
                    CustomerServiceSettingModifyRequest customerServiceSettingModifyRequest) {
        // 在线客服设置
        CustomerServiceSettingVO customerServiceSettingVO =
                customerServiceSettingModifyRequest.getWeChatOnlineServerRop();

        if (0L != customerServiceSettingModifyRequest.getStoreId()){
            List<CustomerServiceSetting> customerServiceSettingList =
                    customerServiceSettingService.list(CustomerServiceSettingQueryRequest.builder()
                            .storeId(0L)
                            .platformType(CustomerServicePlatformType.WECHAT)
                            .delFlag(DeleteFlag.NO)
                            .status(DefaultFlag.YES)
                            .build());
            // boss客服配置不存在时，抛异常
            if (CollectionUtils.isEmpty(customerServiceSettingList) && customerServiceSettingModifyRequest.getWeChatOnlineServerRop().getStatus() ==  DefaultFlag.YES){
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060035);
            }
            if(CollectionUtils.isEmpty(customerServiceSettingList)){
                //存取企业id
                customerServiceSettingVO.setEnterpriseId(customerServiceSettingModifyRequest.getWeChatOnlineServerRop().getEnterpriseId());
            } else {
                //存取企业id
                customerServiceSettingVO.setEnterpriseId(customerServiceSettingList.get(0).getEnterpriseId());
            }
        }

        if (Objects.isNull(customerServiceSettingVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 在线客服是否启用 0 不启用， 1 启用
        boolean status = DefaultFlag.YES.equals(customerServiceSettingVO.getStatus());
        // 在线客服组是否启用 0 不启用， 1 启用
        boolean groupStatus = DefaultFlag.YES.equals(customerServiceSettingVO.getGroupStatus());

        // 在线客服不能同时开启多个
        if (status) {
            List<CustomerServiceSetting> openedList =
                    customerServiceSettingService.list(
                            CustomerServiceSettingQueryRequest.builder()
                                    .storeId(customerServiceSettingVO.getStoreId())
                                    .delFlag(DeleteFlag.NO)
                                    .status(DefaultFlag.YES)
                                    .build());
            if (openedList.stream()
                    .anyMatch(
                            customerServiceSetting ->
                                    !Objects.equals(
                                            customerServiceSettingVO.getId(),
                                            customerServiceSetting.getId()))) {
                throw new SbcRuntimeException(
                        EmpowerErrorCodeEnum.K060028);
            }
        }

        // 客服座席列表
        List<CustomerServiceSettingItemVO> onlineServerItemVoList =
                customerServiceSettingModifyRequest.getWeChatOnlineServerRop().getCustomerServiceSettingItemVOList();
        if (status&&groupStatus){
            //启用客服分组后，至少维护两组客服
            if (CollectionUtils.isEmpty(onlineServerItemVoList) || onlineServerItemVoList.size()<Constants.TWO){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        CustomerServiceSetting customerServiceSetting =
                customerServiceSettingService.getOne(
                        customerServiceSettingVO.getId() == null
                                ? customerServiceSettingVO.getOnlineServiceId()
                                : customerServiceSettingVO.getId());
        KsBeanUtil.copyPropertiesThird(customerServiceSettingVO, customerServiceSetting);

        if (CollectionUtils.isNotEmpty(onlineServerItemVoList)) {
            // 删除客服座席
            customerServiceSettingItemService.deleteByCustomerServiceId(customerServiceSetting.getId());
            List<CustomerServiceSettingItem> customerServiceSettingItemList =
                    onlineServerItemVoList.stream()
                            .map(
                                    customerServiceSettingItemVO -> {
                                        CustomerServiceSettingItem customerServiceSettingItem =
                                                KsBeanUtil.copyPropertiesThird(
                                                        customerServiceSettingItemVO,
                                                        CustomerServiceSettingItem.class);
                                        customerServiceSettingItem.setDelFlag(DeleteFlag.NO);
                                        customerServiceSettingItem.setCustomerServiceId(
                                                Long.valueOf(customerServiceSettingVO.getOnlineServiceId()));
                                        return customerServiceSettingItem;
                                    })
                            .collect(Collectors.toList());
            // 保存客服座席
            customerServiceSettingItemService.save(customerServiceSettingItemList);
        }
        CustomerServiceSettingModifyResponse response = new CustomerServiceSettingModifyResponse(
                customerServiceSettingService.wrapperVo(
                        customerServiceSettingService.modify(customerServiceSetting)));
        //删除缓存
        redisService.delete(RedisKeyConstant.CUSTOMER_SERVICE_WECHAT_INFO + customerServiceSettingModifyRequest.getStoreId());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse deleteById(
            @RequestBody @Valid
                    CustomerServiceSettingDelByIdRequest customerServiceSettingDelByIdRequest) {
        CustomerServiceSetting customerServiceSetting =
                KsBeanUtil.convert(
                        customerServiceSettingDelByIdRequest, CustomerServiceSetting.class);
        customerServiceSetting.setDelFlag(DeleteFlag.YES);
        customerServiceSettingService.deleteById(customerServiceSetting);
        return BaseResponse.SUCCESSFUL();
    }
}
