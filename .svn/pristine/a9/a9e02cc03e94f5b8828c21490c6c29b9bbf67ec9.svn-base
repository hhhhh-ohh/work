package com.wanmi.sbc.vas.vop.address;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className VopAddressService
 * @description VOP地址服务
 * @date 2021/5/11 18:04
 */
@Slf4j
@Service
public class VopAddressService {

    private final static String BEIJING = "1";
    private final static String SHANGHAI = "2";
    private final static String TIANJIN = "3";
    private final static String CHONGQING = "4";

    @Autowired private ThirdAddressProvider thirdAddressProvider;

    @Autowired private ThirdAddressQueryProvider thirdAddressQueryProvider;

    @Autowired private ChannelAddressProvider channelAddressProvider;

    /**
     * 初始化VOP地址数据
     *
     * @author wur
     * @date: 2021/5/11 18:05
     */
    @Async
    public void init() {
        log.info("初始化VOP地址数据, 开始");
        // 同步一级地址
        List<ChannelAddressVO> provinceList = queryVopAddress("0", AddrLevel.PROVINCE);
        if (CollectionUtils.isEmpty(provinceList)) {
            log.error("初始化VOP地址数据, 省初始化异常");
            return;
        }
        this.merge(provinceList, AddrLevel.PROVINCE, StringUtils.EMPTY);
        provinceList.forEach(
                province -> {
                    // 同步二级地址
                    boolean isDireCity = false;
                    // 对1.北京2.上海3.天津4.重庆 需要做特殊处理
                    if (BEIJING.equals(province.getAddrId())
                            || SHANGHAI.equals(province.getAddrId())
                            || TIANJIN.equals(province.getAddrId())
                            || CHONGQING.equals(province.getAddrId())) {
                        isDireCity = true;
                        List<ChannelAddressVO> direCityList = new ArrayList<>();
                        direCityList.add(
                                ChannelAddressVO.builder()
                                        .addrId("S" + province.getAddrId())
                                        .addrName(province.getAddrName() + "市")
                                        .addrLevel(1)
                                        .parentId(province.getAddrId())
                                        .build());
                        this.merge(direCityList, AddrLevel.CITY, StringUtils.EMPTY);
                    }
                    List<ChannelAddressVO> cityList = queryVopAddress(province.getAddrId(), AddrLevel.CITY);
                    if (CollectionUtils.isNotEmpty(cityList)) {
                        this.merge(
                                cityList,
                                isDireCity ? AddrLevel.DISTRICT : AddrLevel.CITY,
                                isDireCity ? "S" + province.getAddrId() : StringUtils.EMPTY);
                        boolean finalIsDireCity = isDireCity;
                        cityList.forEach(
                                city -> {
                                    // 同步三级地址
                                    List<ChannelAddressVO> districtList =
                                            queryVopAddress(city.getAddrId(), AddrLevel.DISTRICT);
                                    if (CollectionUtils.isNotEmpty(districtList)) {
                                        this.merge(
                                                districtList,
                                                finalIsDireCity
                                                        ? AddrLevel.STREET
                                                        : AddrLevel.DISTRICT,
                                                StringUtils.EMPTY);
                                        districtList.forEach(
                                                district -> {
                                                    // 同步四级地址
                                                    List<ChannelAddressVO> streetList =
                                                            queryVopAddress(
                                                                    district.getAddrId(),
                                                                    AddrLevel.STREET);
                                                    this.merge(
                                                            streetList,
                                                            AddrLevel.STREET,
                                                            StringUtils.EMPTY);
                                                });
                                    }
                                });
                    }
                });
        log.info("初始化VOP地址数据，结束");
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
                                        ThirdAddressVO::getPlatformAddrId, ThirdAddressVO::getId, (t1, t2) -> t1));
        thirdAddress.setProvinceId(map.get(address.getProvinceId()));
        thirdAddress.setCityId(map.get(address.getCityId()));
        thirdAddress.setAreaId(map.get(address.getAreaId()));
        thirdAddress.setStreetId(map.get(address.getStreetId()));
        if (thirdAddress.hasNull()) {
            return thirdAddress;
        }
        String zero = "0";
        if(StringUtils.isBlank(thirdAddress.getStreetId())){
            thirdAddress.setStreetId(zero);
        }

        Map<String, String> districtToCityMap = new HashMap<>();
        districtToCityMap.put("4122", "4110");      // 五家渠市
        districtToCityMap.put("129143", "129142");  // 北屯市
        districtToCityMap.put("145530", "145492");  // 可克达拉市
        districtToCityMap.put("15947", "15946");    // 图木舒克市
        districtToCityMap.put("53688", "53668");    // 昆玉市
        districtToCityMap.put("2657", "2656");      // 石河子市
        districtToCityMap.put("146207", "146206");  // 胡杨河市
        districtToCityMap.put("53108", "53090");    // 铁门关市
        districtToCityMap.put("15948", "15945");    // 阿拉尔市
        if (districtToCityMap.containsKey(thirdAddress.getAreaId())) {
            thirdAddress.setCityId(districtToCityMap.get(thirdAddress.getAreaId()));
        }

        // S开头是直辖市
        if (thirdAddress.getCityId().contains(Constants.STR_S)) {
            thirdAddress.setCityId(thirdAddress.getAreaId());
            thirdAddress.setAreaId(thirdAddress.getStreetId());
            thirdAddress.setStreetId(zero);
        }
        // S开头是
        if (thirdAddress.getAreaId().contains(Constants.STR_S)) {
            thirdAddress.setAreaId(thirdAddress.getStreetId());
            thirdAddress.setStreetId(zero);
        }

        return thirdAddress;
    }


    /**
     * 调用渠道查询接口获取关联的地址子集
     *
     * @author wur
     * @date: 2021/5/17 10:20
     * @param parentId 渠道地址ID
     * @param level 地址级别
     * @return 子集列表
     */
    private List<ChannelAddressVO> queryVopAddress(String parentId, AddrLevel level) {
        BaseResponse<ChannelAddressResponse> baseResponse =
                channelAddressProvider.getAddress(
                        ChannelAddressRequest.builder()
                                .addrLevel(level.toValue())
                                .thirdPlatformType(ThirdPlatformType.VOP)
                                .parentId(parentId)
                                .build());
        if (Objects.nonNull(baseResponse)
                && CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
            return baseResponse.getContext().getChannelAddressList();
        }
        return null;
    }

    /**
     * 初始化第三方地址信息
     *
     * @author wur
     * @date: 2021/5/17 10:22
     * @param channelAddressList 渠道地址列表
     * @param level 地址级别
     * @param parentId 上级渠道地址ID
     */
    private void merge(
            List<ChannelAddressVO> channelAddressList, AddrLevel level, String parentId) {
        if (CollectionUtils.isEmpty(channelAddressList)) {
            return;
        }
        List<ThirdAddressDTO> addressList =
                channelAddressList.stream()
                        .map(
                                channelAddress -> {
                                    ThirdAddressDTO dto = new ThirdAddressDTO();
                                    dto.setId(channelAddress.getAddrId());
                                    dto.setThirdAddrId(channelAddress.getAddrId());
                                    dto.setThirdParentId(
                                            StringUtils.isNotEmpty(parentId)
                                                    ? parentId
                                                    : channelAddress.getParentId());
                                    dto.setAddrName(channelAddress.getAddrName());
                                    dto.setThirdFlag(ThirdPlatformType.VOP);
                                    dto.setLevel(level);
                                    return dto;
                                })
                        .collect(Collectors.toList());
        thirdAddressProvider.batchMerge(
                ThirdAddressBatchMergeRequest.builder().thirdAddressList(addressList).build());
    }
}
