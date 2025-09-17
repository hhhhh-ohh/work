package com.wanmi.sbc.pickupsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListByIdsResponse;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByIdsVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PickupEmployeeRelaVO;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author: xufeng
 * @time: 2021/9/9 11:02
 */
@Service
public class PickupSettingService {

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    public void getInfo(List<PickupSettingVO> pickupSettingList) {

        if (CollectionUtils.isNotEmpty(pickupSettingList)) {
            List<String> addrIds = new ArrayList<>();
            pickupSettingList.forEach(detail -> {
                addrIds.add(Objects.toString(detail.getProvinceId()));
                addrIds.add(Objects.toString(detail.getCityId()));
                addrIds.add(Objects.toString(detail.getAreaId()));
                addrIds.add(Objects.toString(detail.getStreetId()));
            });
            //根据Id获取地址信息
            List<PlatformAddressVO> voList = platformAddressQueryProvider.list(PlatformAddressListRequest.builder()
                    .addrIdList(addrIds).build()).getContext().getPlatformAddressVOList();

            Map<String, String> addrMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(voList)) {
                addrMap = voList.stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
            }

            BaseResponse<ListNoDeleteStoreByIdsResponse> response = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest
                    .builder().storeIds(pickupSettingList.stream().map(PickupSettingVO::getStoreId).collect(Collectors.toList())).build());

            for (PickupSettingVO pickupSetting : pickupSettingList) {

                List<StoreVO> storeVOList = response.getContext().getStoreVOList();
                if (CollectionUtils.isNotEmpty(storeVOList)) {
                    Optional<StoreVO> optional = storeVOList.stream().filter(storeVO -> pickupSetting.getStoreId().equals(storeVO.getStoreId())).findFirst();
                    optional.ifPresent(storeVO -> pickupSetting.setStoreName(storeVO.getStoreName()));
                }
                AuditStatus auditStatus = AuditStatus.fromValue(pickupSetting.getAuditStatus());
                pickupSetting.setAuditStatusName(auditStatus.name());
                String provinceName = addrMap.get(Objects.toString(pickupSetting.getProvinceId()));
                String cityName = addrMap.get(Objects.toString(pickupSetting.getCityId()));
                String areaName = addrMap.get(Objects.toString(pickupSetting.getAreaId()));
                String streetName = addrMap.get(Objects.toString(pickupSetting.getStreetId()));

                if(Objects.isNull(provinceName)){
                    provinceName = "";
                }
                if(Objects.isNull(cityName)){
                    cityName = "";
                }
                if(Objects.isNull(areaName)){
                    areaName = "";
                }

                if(Objects.nonNull(streetName)){
                    pickupSetting.setAddress(provinceName + "/" + cityName + "/" + areaName + "/" + streetName);
                }else {
                    pickupSetting.setAddress(provinceName + "/" + cityName + "/" + areaName);
                }
                pickupSetting.setIsDefaultAddressName(DefaultFlag.fromValue(pickupSetting.getIsDefaultAddress()).name());
                pickupSetting.setProvinceName(provinceName);
                pickupSetting.setCityName(cityName);
                pickupSetting.setAreaName(areaName);
                pickupSetting.setStreetName(streetName);
            }
        }
    }

    /**
     * 根据自提点Id获取自提点员工信息
     *
     * @param relaList
     * @return
     */
    public List<PickupEmployeeRelaVO> getEmployeeInfo(List<PickupEmployeeRelaVO> relaList) {

        if (CollectionUtils.isEmpty(relaList)) {
            return null;
        }

        List<String> employeeIds = relaList.stream().map(PickupEmployeeRelaVO::getEmployeeId).collect(Collectors.toList());
        EmployeeListByIdsResponse response = employeeQueryProvider
                .listByIds(new EmployeeListByIdsRequest(employeeIds)).getContext();

        return relaList.stream().peek(rela -> {
            if (response != null && CollectionUtils.isNotEmpty(response.getEmployeeList())) {
                Optional<EmployeeListByIdsVO> optional = response.getEmployeeList().stream()
                        .filter(res -> res.getEmployeeId().equals(rela.getEmployeeId())).findFirst();
                optional.ifPresent(employeeListByIdsVO -> rela.setEmployeeName(employeeListByIdsVO.getEmployeeName()));
            }
        }).collect(Collectors.toList());
    }

    public void phoneDesensitization(List<PickupSettingVO> pickupSettingPage){
        for (PickupSettingVO pickupSetting : pickupSettingPage) {
            if (pickupSetting.getPhone().length() == 11){
                pickupSetting.setPhone(pickupSetting.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }else {
                pickupSetting.setPhone(pickupSetting.getPhone().replaceAll("(\\d{0})\\d{4}(\\d{4})", "$1****$2"));
            }

        }
    }

}
