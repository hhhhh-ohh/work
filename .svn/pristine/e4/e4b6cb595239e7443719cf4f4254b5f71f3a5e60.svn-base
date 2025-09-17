package com.wanmi.sbc.vas.linkedmall.address;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelAddressProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelAddressRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelAddressResponse;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelAddressVO;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressProvider;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressBatchMergeRequest;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressListRequest;
import com.wanmi.sbc.setting.api.response.thirdaddress.ThirdAddressListResponse;
import com.wanmi.sbc.setting.bean.dto.ThirdAddress;
import com.wanmi.sbc.setting.bean.dto.ThirdAddressDTO;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressVO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LinkedMallAddressService {

    @Autowired
    private ChannelAddressProvider channelAddressProvider;

    @Autowired
    private ThirdAddressProvider thirdAddressProvider;

    @Autowired private ThirdAddressQueryProvider thirdAddressQueryProvider;

    private List<ChannelAddressVO> queryVopAddress(String parentId, AddrLevel level) {
        BaseResponse<ChannelAddressResponse> baseResponse =
                channelAddressProvider.getAddress(
                        ChannelAddressRequest.builder()
                                .addrLevel(level.toValue())
                                .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                                .parentId(parentId)
                                .build());
        if (Objects.nonNull(baseResponse)
                && CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
            return baseResponse.getContext().getChannelAddressList();
        }
        return Collections.emptyList();
    }

    /**
     * 初始化likedMall地址数据
     */
    public void init() throws RuntimeException {
        log.info("linkedMall省市区初始化开始");
        //省
        List<ChannelAddressVO> provList = queryVopAddress("0", AddrLevel.PROVINCE);
        try {
            this.merge(provList, AddrLevel.PROVINCE);
        } catch (FeignException e) {
            log.error("省初始化异常", e);
        }

        int pLen = provList.size();
        int pErr = 0;
        for (int i = 0; i < pLen; i++) {
            //市
            List<ChannelAddressVO> cites = null;
            try {
                cites = queryVopAddress(provList.get(i).getAddrId(), AddrLevel.CITY);
                this.merge(cites, AddrLevel.CITY);
                pErr = 0;
            } catch (FeignException e) {
                log.error("城市初始化异常", e);
                if (pErr < 3) {
                    i--;
                }
                pErr++;
            }
            if (CollectionUtils.isEmpty(cites)) {
                continue;
            }
            int cLen = cites.size();
            int cErr = 0;
            for (int j = 0; j < cLen; j++) {
                //区县
                List<ChannelAddressVO> districts = null;
                try {
                    districts = queryVopAddress(cites.get(j).getAddrId(), AddrLevel.DISTRICT);
                    this.merge(districts, AddrLevel.DISTRICT);
                    cErr = 0;
                } catch (Exception e) {
                    log.error("区县初始化异常", e);
                    if (cErr < 3) {
                        j--;
                    }
                    cErr++;
                }
                if (CollectionUtils.isEmpty(districts)) {
                    continue;
                }
                int dLen = districts.size();
                int dErr = 0;
                for (int k = 0; k < dLen; k++) {
                    //街道
                    try {
                        String code = districts.get(k).getAddrId();
                        List<ChannelAddressVO> streets = new ArrayList<>();
                        if (String.valueOf(districts.get(k).getAddrName()).contains("其它区")) {
                            ChannelAddressVO otherItem = new ChannelAddressVO();
                            otherItem.setParentId(code);
                            otherItem.setAddrId(String.valueOf(code).concat("000"));
                            otherItem.setAddrLevel(AddrLevel.STREET.toValue());
                            otherItem.setAddrName("-");
                            streets.add(otherItem);
                        } else {
                            streets = queryVopAddress(code, AddrLevel.STREET);
                        }
                        this.merge(streets, AddrLevel.STREET);
                        dErr = 0;
                    } catch (Exception e) {
                        log.error("街道初始化异常", e);
                        if (dErr < 3) {
                            k--;
                        }
                        dErr++;
                    }
                }
            }
        }
        log.info("linkedMall省市区初始化结束");
    }

    /**
     * @description 本地地区转换为第三方地区
     * @author daiyitian
     * @date 2021/5/13 18:55
     * @param address 本地地区
     * @return com.wanmi.sbc.setting.bean.dto.ThirdAddress
     */
    public ThirdAddress convertPlatformToThird(PlatformAddress address) {
        ThirdAddress thirdAddress = new ThirdAddress();
        if (address == null || address.hasNull()) {
            return thirdAddress;
        }
        List<String> platformAddressList = new ArrayList<>();
        platformAddressList.add(address.getProvinceId());
        platformAddressList.add(address.getCityId());
        platformAddressList.add(address.getAreaId());
        if (StringUtils.isNotBlank(address.getStreetId())) {
            platformAddressList.add(address.getStreetId());
        }
        ThirdAddressListResponse response =
                thirdAddressQueryProvider
                        .list(
                                ThirdAddressListRequest.builder()
                                        .platformAddrIdList(platformAddressList)
                                        .thirdFlag(ThirdPlatformType.VOP)
                                        .build())
                        .getContext();
        // 没有映射完整
        if (CollectionUtils.isEmpty(response.getThirdAddressList())) {
            return thirdAddress;
        }
        Map<String, String> map =
                response.getThirdAddressList().stream()
                        .collect(
                                Collectors.toMap(
                                        ThirdAddressVO::getPlatformAddrId, ThirdAddressVO::getId));
        thirdAddress.setProvinceId(map.get(address.getProvinceId()));
        thirdAddress.setCityId(map.get(address.getCityId()));
        thirdAddress.setAreaId(map.get(address.getAreaId()));
        thirdAddress.setStreetId(map.get(address.getStreetId()));
        return thirdAddress;
    }

    private void merge(List<ChannelAddressVO> items, AddrLevel level) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        List<ThirdAddressDTO> dtos = items.stream().map(i -> {
            ThirdAddressDTO dto = new ThirdAddressDTO();
            dto.setId(String.valueOf(i.getAddrId()));
            dto.setThirdAddrId(String.valueOf(i.getAddrId()));
            dto.setThirdParentId(String.valueOf(i.getParentId()));
            dto.setAddrName(i.getAddrName());
            dto.setThirdFlag(ThirdPlatformType.LINKED_MALL);
            dto.setLevel(level);
            return dto;
        }).collect(Collectors.toList());
        thirdAddressProvider.batchMerge(ThirdAddressBatchMergeRequest.builder().thirdAddressList(dtos).build());
    }
}
