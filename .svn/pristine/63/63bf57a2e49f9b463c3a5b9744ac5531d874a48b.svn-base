package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.MapType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressProvider;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.address.*;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.response.address.*;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;
import com.wanmi.sbc.empower.api.provider.map.MapQueryProvider;
import com.wanmi.sbc.empower.api.request.map.GetAddressByLocationRequest;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationResponse;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 客户地址
 * Created by CHENLI on 2017/4/20.
 */
@RestController
@Validated
@RequestMapping("/customer")
@Tag(name = "CustomerDeliveryAddressBaseController", description = "S2B web公用-客户地址信息API")
public class CustomerDeliveryAddressBaseController {

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired
    private CustomerDeliveryAddressProvider customerDeliveryAddressProvider;

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private MapQueryProvider mapQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    /**
     * 查询该客户的所有收货地址
     *
     * @return
     */
    @Operation(summary = "查询该客户的所有收货地址")
    @RequestMapping(value = "/addressList", method = RequestMethod.GET)
    public BaseResponse<List<CustomerDeliveryAddressVO>> findAddressList() {
        CustomerDeliveryAddressListRequest customerDeliveryAddressListRequest = new CustomerDeliveryAddressListRequest();
        customerDeliveryAddressListRequest.setCustomerId(commonUtil.getOperatorId());
        BaseResponse<CustomerDeliveryAddressListResponse> customerDeliveryAddressListResponseBaseResponse = customerDeliveryAddressQueryProvider.listByCustomerId(customerDeliveryAddressListRequest);
        CustomerDeliveryAddressListResponse customerDeliveryAddressListResponse = customerDeliveryAddressListResponseBaseResponse.getContext();
        List<CustomerDeliveryAddressVO> customerDeliveryAddressVOList = Collections.emptyList();
        if(Objects.nonNull(customerDeliveryAddressListResponse)){
           customerDeliveryAddressVOList = customerDeliveryAddressListResponse.getCustomerDeliveryAddressVOList()
                    .stream().sorted(Comparator.comparing(CustomerDeliveryAddressVO::getIsDefaltAddress).reversed()).collect(Collectors.toList());
        }

        return BaseResponse.success(customerDeliveryAddressVOList);
    }

    /**
     * 查询客户默认收货地址
     *
     * @return
     */
    @Operation(summary = "查询客户默认收货地址")
    @RequestMapping(value = "/addressinfo", method = RequestMethod.GET)
    public BaseResponse<CustomerDeliveryAddressResponse> findDefaultAddress() {
        CustomerDeliveryAddressRequest queryRequest = new CustomerDeliveryAddressRequest();
        String customerId = commonUtil.getOperatorId();
        if (StringUtils.isEmpty(customerId) || StringUtils.equals(customerId,"null")) {
            return new BaseResponse(CommonErrorCodeEnum.K000015);
        }
        queryRequest.setCustomerId(customerId);
        BaseResponse<CustomerDeliveryAddressResponse> customerDeliveryAddressResponseBaseResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest);
        CustomerDeliveryAddressResponse customerDeliveryAddressResponse = customerDeliveryAddressResponseBaseResponse.getContext();
        if (Objects.isNull(customerDeliveryAddressResponse)) {
            return BaseResponse.error("收货地址为空");
        } else {
            return BaseResponse.success(customerDeliveryAddressResponse);
        }
    }

    /**
     * 保存客户收货地址
     *
     * @param editRequest
     * @return
     */
    @Operation(summary = "保存客户收货地址")
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse saveAddress(@Valid @RequestBody CustomerDeliveryAddressAddRequest editRequest) {
        String customerId = commonUtil.getOperatorId();
        //一个客户最多可以添加20条地址
        CustomerDeliveryAddressByCustomerIdRequest customerDeliveryAddressByCustomerIdRequest = new CustomerDeliveryAddressByCustomerIdRequest();
        customerDeliveryAddressByCustomerIdRequest.setCustomerId(customerId);

        if (customerDeliveryAddressQueryProvider.countByCustomerId(customerDeliveryAddressByCustomerIdRequest).getContext().getResult() >= Constants.NUM_20) {
            return BaseResponse.error("最多可以添加20条收货地址");
        }
        
        if(StringUtils.isNotEmpty(editRequest.getConsigneeName())){
            if(editRequest.getConsigneeName().trim().length() < Constants.TWO ||
                    editRequest.getConsigneeName().trim().length() > 15){
                return BaseResponse.error("收货人长度必须为2-15个字符之间");
            }
        }else{
            return BaseResponse.error("收货人不能为空");
        }


        if(StringUtils.isNotEmpty(editRequest.getConsigneeNumber())){
            if(!Pattern.matches(CommonUtil.REGEX_MOBILE,editRequest.getConsigneeNumber())){
                return BaseResponse.error("请输入正确的手机号码");
            }
        }else{
            return BaseResponse.error("手机号码不能为空");
        }

        editRequest.setCustomerId(customerId);

        //查询高德地图设置
        Integer mapStatus = pickupSettingQueryProvider.getWhetherOpenMap().getContext().getMapStatus();
        if (Objects.nonNull(mapStatus) && mapStatus == 0) {
            //当高德地图设置关闭时，手动清除经纬度，置为null
            editRequest.setLatitude(null);
            editRequest.setLongitude(null);
        }

        //传参中没有街道编码，有经纬度，反查街道编码
        if (Objects.isNull(editRequest.getStreetId())
                && Objects.nonNull(editRequest.getLatitude())
                && Objects.nonNull(editRequest.getLongitude())) {
            editRequest.setStreetId(streetId(editRequest.getLatitude(),
                    editRequest.getLongitude(), editRequest.getAreaId()));
        }

        BaseResponse<CustomerDeliveryAddressAddResponse> customerDeliveryAddressAddResponseBaseResponse = customerDeliveryAddressProvider.add(editRequest);
        CustomerDeliveryAddressAddResponse customerDeliveryAddressAddResponse = customerDeliveryAddressAddResponseBaseResponse.getContext();
        if (Objects.isNull(customerDeliveryAddressAddResponse)) {
            return BaseResponse.FAILED();
        } else {
            // 增加成长值
            customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder()
                    .customerId(editRequest.getCustomerId())
                    .type(OperateType.GROWTH)
                    .serviceType(GrowthValueServiceType.ADDSHIPPINGADDRESS)
                    .build());
            // 增加积分
            customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                    .customerId(editRequest.getCustomerId())
                    .type(OperateType.GROWTH)
                    .serviceType(PointsServiceType.ADDSHIPPINGADDRESS)
                    .build());

            return BaseResponse.success(customerDeliveryAddressAddResponse);
        }
    }

    /**
     * 根据地址id查询客户收货地址详情
     *
     * @param addressId
     * @return
     */
    @Operation(summary = "根据地址id查询客户收货地址详情")
    @Parameter( name = "addressId", description = "地址id", required = true)
    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    public BaseResponse<CustomerDeliveryAddressByIdResponse> findById(@PathVariable String addressId) {
        CustomerDeliveryAddressByIdRequest customerDeliveryAddressByIdRequest = CustomerDeliveryAddressByIdRequest
                .builder().deliveryAddressId(addressId).build();
        BaseResponse<CustomerDeliveryAddressByIdResponse> customerDeliveryAddressByIdResponseBaseResponse = customerDeliveryAddressQueryProvider.getById(customerDeliveryAddressByIdRequest);
        CustomerDeliveryAddressByIdResponse  customerDeliveryAddressByIdResponse = customerDeliveryAddressByIdResponseBaseResponse.getContext();
        if (Objects.isNull(customerDeliveryAddressByIdResponse)) {
            return BaseResponse.error("该收货地址不存在");
        } else {
            if (!StringUtils.equals(commonUtil.getOperatorId(), customerDeliveryAddressByIdResponse.getCustomerId())){
                return BaseResponse.error("非法请求");
            }
            return BaseResponse.success(customerDeliveryAddressByIdResponse);
        }
    }

    /**
     * 修改客户收货地址
     *
     * @param editRequest
     * @return
     */
    @Operation(summary = "修改客户收货地址")
    @RequestMapping(value = "/addressInfo", method = RequestMethod.PUT)
    public BaseResponse updateAddress(@Valid @RequestBody CustomerDeliveryAddressModifyRequest editRequest) {

        BaseResponse<CustomerDeliveryAddressByIdResponse> customerDeliveryAddressByIdResponseBaseResponse
                = customerDeliveryAddressQueryProvider.getById(CustomerDeliveryAddressByIdRequest.builder().deliveryAddressId(editRequest.getDeliveryAddressId()).build());

        CustomerDeliveryAddressByIdResponse  customerDeliveryAddressByIdResponse = customerDeliveryAddressByIdResponseBaseResponse.getContext();

        if(Objects.isNull(customerDeliveryAddressByIdResponse)){
            return BaseResponse.error("该收货地址不存在");
        }else{
            if(!commonUtil.getOperatorId().equals(customerDeliveryAddressByIdResponse.getCustomerId()) || (StringUtils.isNotBlank(editRequest.getCustomerId()) && !commonUtil.getOperatorId().equals(editRequest.getCustomerId()))){
                return BaseResponse.error("非法越权操作");
            }
        }

        editRequest.setEmployeeId(commonUtil.getOperatorId());

        if(StringUtils.isNotEmpty(editRequest.getConsigneeName())){
            if(editRequest.getConsigneeName().trim().length() < Constants.TWO ||
                    editRequest.getConsigneeName().trim().length() > 15){
                return BaseResponse.error("收货人长度必须为2-15个字符之间");
            }
        }else{
            return BaseResponse.error("收货人不能为空");
        }


        if(StringUtils.isNotEmpty(editRequest.getConsigneeNumber())){
            if(!Pattern.matches(CommonUtil.REGEX_MOBILE,editRequest.getConsigneeNumber())){
                return BaseResponse.error("请输入正确的手机号码");
            }
        }else{
            return BaseResponse.error("手机号码不能为空");
        }

        //查询高德地图设置
        Integer mapStatus = pickupSettingQueryProvider.getWhetherOpenMap().getContext().getMapStatus();
        if (Objects.nonNull(mapStatus) && mapStatus == 0) {
            //当高德地图设置关闭时，手动清除经纬度，置为null
            editRequest.setLatitude(null);
            editRequest.setLongitude(null);
        }

        //传参中没有街道编码，有经纬度，反查街道编码
        if (Objects.isNull(editRequest.getStreetId())
                && Objects.nonNull(editRequest.getLatitude())
                && Objects.nonNull(editRequest.getLongitude())) {
            editRequest.setStreetId(streetId(editRequest.getLatitude(),
                    editRequest.getLongitude(), editRequest.getAreaId()));
        }

        BaseResponse<CustomerDeliveryAddressModifyResponse> customerDeliveryAddressModifyResponseBaseResponse = customerDeliveryAddressProvider.modify(editRequest);
        CustomerDeliveryAddressModifyResponse response = customerDeliveryAddressModifyResponseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.FAILED();
        } else {
            return BaseResponse.success(response);
        }
    }

    /**
     * 设置客户收货地址为默认
     *
     * @return
     */
    @Operation(summary = "设置客户收货地址为默认")
    @Parameter(name = "deliveryAddressId", description = "地址id", required = true)
    @RequestMapping(value = "/defaultAddress/{deliveryAddressId}", method = RequestMethod.POST)
    public BaseResponse setDefaultAddress(@PathVariable String deliveryAddressId) {
        CustomerDeliveryAddressModifyDefaultRequest queryRequest = new CustomerDeliveryAddressModifyDefaultRequest();
        queryRequest.setCustomerId(commonUtil.getOperatorId());
        queryRequest.setDeliveryAddressId(deliveryAddressId);
        customerDeliveryAddressProvider.modifyDefaultByIdAndCustomerId(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除客户收货地址
     *
     * @param addressId
     * @return
     */
    @Operation(summary = "删除客户收货地址")
    @Parameter( name = "addressId", description = "地址id", required = true)
    @RequestMapping(value = "/addressInfo/{addressId}", method = RequestMethod.DELETE)
    public BaseResponse deleteAddress(@PathVariable String addressId) {

        BaseResponse<CustomerDeliveryAddressByIdResponse> customerDeliveryAddressByIdResponseBaseResponse
                = customerDeliveryAddressQueryProvider.getById(CustomerDeliveryAddressByIdRequest.builder().deliveryAddressId(addressId).build());

        CustomerDeliveryAddressByIdResponse  customerDeliveryAddressByIdResponse = customerDeliveryAddressByIdResponseBaseResponse.getContext();

        if(Objects.isNull(customerDeliveryAddressByIdResponse)){
            return BaseResponse.error("该收货地址不存在");
        }else{
            if(!commonUtil.getOperatorId().equals(customerDeliveryAddressByIdResponse.getCustomerId())){
                return BaseResponse.error("非法越权操作");
            }
        }

        CustomerDeliveryAddressDeleteRequest customerDeliveryAddressDeleteRequest = new CustomerDeliveryAddressDeleteRequest();
        customerDeliveryAddressDeleteRequest.setAddressId(addressId);
        customerDeliveryAddressProvider.deleteById(customerDeliveryAddressDeleteRequest);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 根据经纬度反查街道编码
     * @param latitude
     * @param longitude
     * @return
     */
    public Long streetId(BigDecimal latitude, BigDecimal longitude, Long areaId){
        // 如果街道没传值，传了经纬度，则反查街道信息
        GetAddressByLocationResponse getAddressByLocationResponse =
                mapQueryProvider
                        .getAddressByLocation(
                                GetAddressByLocationRequest.builder()
                                        .location(longitude + "," + latitude)
                                        .mapType(MapType.GAO_DE)
                                        .build())
                        .getContext();
        if (Objects.nonNull(getAddressByLocationResponse) ) {
            // 优先根据街道名称取
            if (Objects.nonNull(areaId) && StringUtils.isNotBlank(getAddressByLocationResponse.getTownship())) {
                PlatformAddressListRequest listRequest = new PlatformAddressListRequest();
                listRequest.setAddrParentId(Objects.toString(areaId));
                listRequest.setDelFlag(DeleteFlag.NO);
                List<PlatformAddressVO> addressList = platformAddressQueryProvider.list(listRequest).getContext()
                        .getPlatformAddressVOList();
                if (CollectionUtils.isNotEmpty(addressList)) {
                    PlatformAddressVO address = addressList.stream()
                            .filter(s -> getAddressByLocationResponse.getTownship().replaceAll("街道", "").equals(s.getAddrName().replaceAll("街道", "")))
                            .findFirst().orElse(null);
                    if (Objects.nonNull(address)) {
                        return NumberUtils.toLong(address.getAddrId());
                    }
                }
            }
            //取反查街道id
            if (Objects.nonNull(getAddressByLocationResponse.getTowncode())) {
                return Long.valueOf(getAddressByLocationResponse.getTowncode());
            }
        }
        return null;
    }
}
