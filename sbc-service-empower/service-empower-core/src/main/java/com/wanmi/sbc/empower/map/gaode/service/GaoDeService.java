package com.wanmi.sbc.empower.map.gaode.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.map.GetAddressByLocationRequest;
import com.wanmi.sbc.empower.api.request.map.GetDistinctRequest;
import com.wanmi.sbc.empower.api.request.map.GetPoiRequest;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationResponse;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationSimpleResponse;
import com.wanmi.sbc.empower.api.response.map.PoiListResponse;
import com.wanmi.sbc.empower.bean.vo.DistrictVO;
import com.wanmi.sbc.empower.bean.vo.PoiVO;
import com.wanmi.sbc.empower.map.UrlConstant.UrlConstant;
import com.wanmi.sbc.empower.map.base.MapBaseServiceInterface;
import com.wanmi.sbc.empower.util.HttpUtils;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressSaveProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressAddRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressBatchAddRequest;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @className PlaceService
 * @description 高德地图服务
 * @author 张文昌
 * @date 2021/7/15 10:50
 */
@Slf4j
@Service("GaoDeService")
public class GaoDeService implements MapBaseServiceInterface {

    private static final String SUCCESS = "1";

    @Value("${map.gaode.key}")
    private String key;

    @Autowired
    private PlatformAddressSaveProvider platformAddressSaveProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    /**
     * 根据关键字搜索POI，默认返回20条
     *
     * @param placeGetRequest
     * @return
     */
    @Override
    public PoiListResponse getPoi(GetPoiRequest placeGetRequest) {
        placeGetRequest.setKey(key);
        String resultStr = HttpUtils.getData(UrlConstant.GET_POI, placeGetRequest);
        log.info("高德地图接口-关键字搜索POI返回信息，{}", resultStr);
        if (StringUtils.isNotEmpty(resultStr)) {
            JSONObject tokenResJson = JSON.parseObject(resultStr);
            String status = tokenResJson.getString("status");
            if (SUCCESS.equals(status)) {
                String pois = tokenResJson.getString("pois");
                return PoiListResponse.builder()
                        .pois(fillCode(JSON.parseArray(pois, PoiVO.class), null))
                        .build();
            } else {
                log.error("错误信息，infocode={}", tokenResJson.getString("infocode"));
                log.error("错误信息，info={}", tokenResJson.getString("info"));
                throw new SbcRuntimeException(
                        CommonErrorCodeEnum.K000001, tokenResJson.getString("info"));
            }
        }
        return null;
    }

    /**
     * @description 经纬度反查地址
     * @author 张文昌
     * @date 2021/7/23 13:53
     * @param getAddressByLocationRequest
     * @return
     */
    @Override
    public GetAddressByLocationResponse getAddressByLocation(
            GetAddressByLocationRequest getAddressByLocationRequest) {
        //校验经纬度传参
        String location = getAddressByLocationRequest.getLocation();
        if(StringUtils.isBlank(location)){
            throw new SbcRuntimeException(
                    CommonErrorCodeEnum.K000001, "经纬度参数错误");
        }
        String[] split = location.split(",");
        if(split.length < Constants.TWO || StringUtils.isBlank(split[0])
                || StringUtils.isBlank(split[1])
                || "null".equals(split[0])
                || "null".equals(split[1])){
            throw new SbcRuntimeException(
                    CommonErrorCodeEnum.K000001, "经纬度参数错误");
        }
        getAddressByLocationRequest.setKey(key);
        String resultStr =
                HttpUtils.getData(UrlConstant.GET_BY_GEOCODE, getAddressByLocationRequest);
        log.info("高德地图接口-经纬度反查地址，{}", resultStr);
        if (StringUtils.isNotEmpty(resultStr)) {
            JSONObject tokenResJson = JSON.parseObject(resultStr);
            String status = tokenResJson.getString("status");
            if (SUCCESS.equals(status)) {
                String regeocodes = tokenResJson.getString("regeocode");
                JSONObject addressJson = JSON.parseObject(regeocodes);
                JSONObject addressComponent =
                        JSON.parseObject(addressJson.getString("addressComponent"));
                return GetAddressByLocationResponse.builder()
                        .formattedAddress(addressJson.getString("formatted_address"))
                        .pois(
                                fillCode(
                                        JSON.parseArray(addressJson.getString("pois"), PoiVO.class),
                                        addressComponent.getString("towncode")))
                        .province(addressComponent.getString("province"))
                        .pcode(subByLength(addressComponent.getString("towncode"), 2, "0000"))
                        .city(addressComponent.getString("city"))
                        .citycode(subByLength(addressComponent.getString("towncode"), 4, "00"))
                        .district(addressComponent.getString("district"))
                        .adcode(subByLength(addressComponent.getString("towncode"), 6, ""))
                        .township(addressComponent.getString("township"))
                        .towncode(subByLength(addressComponent.getString("towncode"), 9, ""))
                        .build();
            } else {
                log.error("错误信息，infocode={}", tokenResJson.getString("infocode"));
                log.error("错误信息，info={}", tokenResJson.getString("info"));
                throw new SbcRuntimeException(
                        CommonErrorCodeEnum.K000001, tokenResJson.getString("info"));
            }
        }
        return null;
    }

    /**
     * 填充省市区编码，规则：接口返回的街道编码取前两位+0000为省编码，取前四位+00为市编码，取前六位为区编码，取前九位位为街道编码
     *
     * @param poiVOList source
     * @param towncode 街道编码
     * @return
     */
    public List<PoiVO> fillCode(List<PoiVO> poiVOList, String towncode) {
        if (CollectionUtils.isEmpty(poiVOList)) {
            return poiVOList;
        }
        String pcode = null, citycode = null, adcode = null;
        if (StringUtils.isNotBlank(towncode)) {
            pcode = subByLength(towncode, 2 , "0000");
            citycode = subByLength(towncode, 4 , "00");
            adcode = subByLength(towncode, 6, "");
            towncode = subByLength(towncode, 10, "");
            String finalPcode = pcode;
            String finalCitycode = citycode;
            String finalAdcode = adcode;
            String finalTowncode = towncode;
            poiVOList =
                    poiVOList.stream()
                            .map(
                                    poiVO -> {
                                        poiVO.setPcode(finalPcode);
                                        poiVO.setCitycode(finalCitycode);
                                        poiVO.setAdcode(finalAdcode);
                                        poiVO.setTowncode(finalTowncode);
                                        return poiVO;
                                    })
                            .collect(Collectors.toList());
        } else {
            poiVOList =
                    poiVOList.stream()
                            .map(
                                    poiVO -> {
                                        poiVO.setPcode(subByLength(poiVO.getAdcode(), 2 , "0000"));
                                        poiVO.setCitycode(subByLength(poiVO.getAdcode(),4, "00"));
                                        poiVO.setAdcode(subByLength(poiVO.getAdcode(), 6, ""));
                                        return poiVO;
                                    })
                            .collect(Collectors.toList());
        }
        return poiVOList;
    }

    /**
     * 根据搜索周边，默认返回20条
     *
     * @param placeGetRequest
     * @return
     */
    @Override
    public PoiListResponse getAroundPoi(GetPoiRequest placeGetRequest) {
        placeGetRequest.setKey(key);
        String resultStr = HttpUtils.getData(UrlConstant.GET_AROUND_POI, placeGetRequest);
        log.info("高德地图接口-搜索周边POI返回信息，{}", resultStr);
        if (StringUtils.isNotEmpty(resultStr)) {
            JSONObject tokenResJson = JSON.parseObject(resultStr);
            String status = tokenResJson.getString("status");
            if (SUCCESS.equals(status)) {
                String pois = tokenResJson.getString("pois");
                return PoiListResponse.builder()
                        .pois(fillCode(JSON.parseArray(pois, PoiVO.class), null))
                        .build();
            } else {
                log.error("错误信息，infocode={}", tokenResJson.getString("infocode"));
                log.error("错误信息，info={}", tokenResJson.getString("info"));
                throw new SbcRuntimeException(
                        CommonErrorCodeEnum.K000001, tokenResJson.getString("info"));
            }
        }
        return null;
    }

    /**
     * 截取指定指定长度字符串
     * @param source 源数据
     * @param targetLength 截取长度
     * @param suffix 尾部拼接
     * @return
     */
    public String subByLength(String source, int targetLength, String suffix){
        if(StringUtils.isBlank(source) || source.length() < targetLength){
            return null;
        }
        return source.substring(0, targetLength) + suffix;
    }

    public static List<PlatformAddressAddRequest> getData(List<DistrictVO> districts, DistrictVO parent){
        List<PlatformAddressAddRequest> reData = Lists.newArrayList();
        for(DistrictVO district :districts) {
            if(Objects.nonNull(parent)){
                // 处理省级父级为0
                if (parent.getAdcode().equals("100000")){
                    district.setParentadcode("0");
                }else{
                    district.setParentadcode(parent.getAdcode());
                }
            }else {
                district.setParentadcode("0");
            }
            if(CollectionUtils.isNotEmpty(district.getDistricts()) && district.getDistricts().size() > 0){
                List<PlatformAddressAddRequest> tmp = getData(district.getDistricts(), district);
                reData.addAll(tmp);
            }
            // 国家不存入
            if (!district.getLevel().equals("country")){
                PlatformAddressAddRequest platformAddressAddRequest = new PlatformAddressAddRequest();
                platformAddressAddRequest.setAddrId(district.getAdcode());
                platformAddressAddRequest.setAddrName(district.getName());
                platformAddressAddRequest.setAddrParentId(district.getParentadcode());
                platformAddressAddRequest.setCenter(district.getCenter());
                switch (district.getLevel()) {
                    case "province":
                        platformAddressAddRequest.setAddrLevel(AddrLevel.PROVINCE);
                        break;
                    case "city":
                        platformAddressAddRequest.setAddrLevel(AddrLevel.CITY);
                        break;
                    case "district":
                        platformAddressAddRequest.setAddrLevel(AddrLevel.DISTRICT);
                        break;
                    case "street":
                        platformAddressAddRequest.setAddrLevel(AddrLevel.STREET);
                        break;
                    default:
                        break;
                }

                reData.add(platformAddressAddRequest);
            }
        }
        return reData;
    }

    @Override
    public void initGaoDeDistrict() {
        GetDistinctRequest getDistinctRequest = new GetDistinctRequest();
        getDistinctRequest.setKey(key);
        getDistinctRequest.setSubdistrict(Constants.FOUR);
        String resultStr = HttpUtils.getData(UrlConstant.GET_DISTRICT, getDistinctRequest);
        log.info("高德地图接口-行政区域查询返回信息，{}", resultStr);
        if (StringUtils.isNotEmpty(resultStr)) {
            JSONObject tokenResJson = JSON.parseObject(resultStr);
            String status = tokenResJson.getString("status");
            if (SUCCESS.equals(status)) {
                // 获取省市区数据
                List<PlatformAddressAddRequest> platformAddressAddRequestList = getData(JSON.parseArray(tokenResJson.getString("districts"),
                        DistrictVO.class), null);
                // 省市区存入数据库
                PlatformAddressBatchAddRequest platformAddressBatchAddRequest = new PlatformAddressBatchAddRequest();
                platformAddressBatchAddRequest.setPlatformAddressAddRequests(platformAddressAddRequestList);

                platformAddressSaveProvider.batchAdd(platformAddressBatchAddRequest);
                // 获取地区编号
                List<PlatformAddressAddRequest> streetList =
                        platformAddressAddRequestList.stream().filter(platformAddressAddRequest -> platformAddressAddRequest.getAddrLevel()
                                == AddrLevel.STREET).collect(Collectors.toList());

                List<List<PlatformAddressAddRequest>> partition = Lists.partition(streetList, Constants.NUM_20);
                System.out.println(partition.size());
// 测试数据
// AtomicInteger j = new AtomicInteger();
                partition.forEach(platformAddressAddRequests -> {
                    // 根据经纬度分批查询编码
                    StringBuffer stringBuffer = new StringBuffer();
                    platformAddressAddRequests.forEach(platformAddressAddRequest -> {
                        stringBuffer.append(platformAddressAddRequest.getCenter());
                        stringBuffer.append("%7C");
                    });
                    String location = stringBuffer.substring(0, stringBuffer.length()-3);
                    // 跑两次测试
                    //if (j.getAndIncrement() < 2){
                        List<GetAddressByLocationSimpleResponse> getAddressByLocationSimpleResponseList =
                                batchGetAddressByLocation(GetAddressByLocationRequest.builder().location(location).batch(Boolean.TRUE).build());
                        List<String> centerList = Lists.newArrayList();
                        for (int i=0;i<platformAddressAddRequests.size();i++){
                            if (CollectionUtils.isNotEmpty(getAddressByLocationSimpleResponseList)){
                                GetAddressByLocationSimpleResponse getAddressByLocationSimpleResponse =
                                        getAddressByLocationSimpleResponseList.get(i);
                                PlatformAddressAddRequest platformAddressAddRequest = platformAddressAddRequests.get(i);
                                if (StringUtils.isNotBlank(getAddressByLocationSimpleResponse.getTowncode())){
                                    // 验证根据坐标查出的街道是否正确
                                    if(StringUtils.equals(platformAddressAddRequest.getAddrName(),
                                            getAddressByLocationSimpleResponse.getTownship())){
                                        platformAddressAddRequest.setAddrId(getAddressByLocationSimpleResponse.getTowncode());
                                    }else {
                                        // 记录根据坐标查到编码不准确的数据
                                        log.info("根据坐标查到编码不准确的数据,{}", JSON.toJSONString(platformAddressAddRequest));
                                        centerList.add(platformAddressAddRequest.getCenter());
                                    }
                                }else {
                                    // 记录根据坐标没有查到编码的数据
                                    log.info("根据坐标没有查到编码的数据,{}", JSON.toJSONString(platformAddressAddRequest));
                                    centerList.add(platformAddressAddRequest.getCenter());
                                }

                            }
                        }
                        // 需要从原表补偿的
                        List<PlatformAddressAddRequest> needQuery = Lists.newArrayList();
                        // 数据正确的
                        List<PlatformAddressAddRequest> needAdd = Lists.newArrayList();
                        if (CollectionUtils.isNotEmpty(centerList)) {
                            needQuery =
                                    platformAddressAddRequests.stream().filter(platformAddressAddRequest -> centerList.contains(platformAddressAddRequest.getCenter())).collect(Collectors.toList());
                            needAdd =
                                    platformAddressAddRequests.stream().filter(platformAddressAddRequest -> !centerList.contains(platformAddressAddRequest.getCenter())).collect(Collectors.toList());
                        }
                        // 数据正确的
                        log.info("========================数据正确的=============================");
                        log.info("数据正确的,size={}", needAdd.size());
                        System.out.println(needAdd.size());
                        // 需要从原表补偿的
                        log.info("========================需要从原表补偿的=============================");
                        log.info("需要从原表补偿的,size={}", needQuery.size());

                        PlatformAddressBatchAddRequest addressBatchQueryRequest = new PlatformAddressBatchAddRequest();
                        addressBatchQueryRequest.setPlatformAddressAddRequests(needQuery);
                        List<PlatformAddressVO> platformAddressVOList =
                                platformAddressQueryProvider.batchQuery(addressBatchQueryRequest).getContext().getPlatformAddressVOList();
                        if (CollectionUtils.isNotEmpty(platformAddressVOList)){
                            needAdd.addAll(KsBeanUtil.convertList(platformAddressVOList, PlatformAddressAddRequest.class));
                        }
                        // 街道存入数据库
                        PlatformAddressBatchAddRequest addressBatchAddRequest = new PlatformAddressBatchAddRequest();
                        addressBatchAddRequest.setPlatformAddressAddRequests(needAdd.stream().filter(Objects::nonNull).collect(Collectors.toList()));
                        platformAddressSaveProvider.batchAdd(addressBatchAddRequest);
                    //}
                });
            } else {
                log.error("错误信息，infocode={}", tokenResJson.getString("infocode"));
                log.error("错误信息，info={}", tokenResJson.getString("info"));
                throw new SbcRuntimeException(
                        CommonErrorCodeEnum.K000001, tokenResJson.getString("info"));
            }
        }
    }

    /**
     * @description 经纬度反查地址批量
     * @author xufeng
     * @date 2022/10/11 15:53
     * @param getAddressByLocationRequest
     * @return
     */
    public List<GetAddressByLocationSimpleResponse> batchGetAddressByLocation(GetAddressByLocationRequest getAddressByLocationRequest) {
        List<GetAddressByLocationSimpleResponse> returnStr = Lists.newArrayList();
        //校验经纬度传参
        // getAddressByLocationRequest.setKey("3e2a8b0767bcc3ea300a95e5db114be8");
        getAddressByLocationRequest.setKey(key);
        String resultStr =
                HttpUtils.getData(UrlConstant.GET_BY_GEOCODE, getAddressByLocationRequest);
        log.info("高德地图接口-经纬度反查地址，{}", resultStr);
        if (StringUtils.isNotEmpty(resultStr)) {
            JSONObject tokenResJson = JSON.parseObject(resultStr);
            String status = tokenResJson.getString("status");
            if (SUCCESS.equals(status)) {
                String regeocodes = tokenResJson.getString("regeocodes");
                JSONArray jsonArray = JSON.parseArray(regeocodes);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject addressJson = jsonArray.getJSONObject(i);
                    JSONObject addressComponent =
                            JSON.parseObject(addressJson.getString("addressComponent"));
                    GetAddressByLocationSimpleResponse getAddressByLocationSimpleResponse = new GetAddressByLocationSimpleResponse();
                    getAddressByLocationSimpleResponse.setAdcode(addressComponent.getString("adcode"));
                    getAddressByLocationSimpleResponse.setTownship(addressComponent.getString("township"));
                    String towncode = subByLength(addressComponent.getString("towncode"), 9, "");
                    getAddressByLocationSimpleResponse.setTowncode(towncode);
                    returnStr.add(getAddressByLocationSimpleResponse);
                }
                return returnStr;
            } else {
                log.error("错误信息，infocode={}", tokenResJson.getString("infocode"));
                log.error("错误信息，info={}", tokenResJson.getString("info"));
                throw new SbcRuntimeException(
                        CommonErrorCodeEnum.K000001, tokenResJson.getString("info"));
            }
        }
        return null;
    }

    public static void main(String[] args) {
        GaoDeService gaoDeService = new GaoDeService();
        GetDistinctRequest getDistinctRequest = new GetDistinctRequest();
        getDistinctRequest.setKey("3e2a8b0767bcc3ea300a95e5db114be8");
        getDistinctRequest.setSubdistrict(4);
        String resultStr = HttpUtils.getData(UrlConstant.GET_DISTRICT, getDistinctRequest);
        log.info("高德地图接口-行政区域查询返回信息，{}", resultStr);
        if (StringUtils.isNotEmpty(resultStr)) {
            JSONObject tokenResJson = JSON.parseObject(resultStr);
            String status = tokenResJson.getString("status");
            if (SUCCESS.equals(status)) {
                // 获取省市区数据
                List<PlatformAddressAddRequest> list = getData(JSON.parseArray(tokenResJson.getString("districts"),
                        DistrictVO.class), null);
                // 省市区存入数据库

                // 获取地区编号
                List<PlatformAddressAddRequest> streetList =
                        list.stream().filter(platformAddressAddRequest -> platformAddressAddRequest.getAddrLevel()
                                == AddrLevel.STREET).collect(Collectors.toList());

                List<List<PlatformAddressAddRequest>> partition = Lists.partition(streetList, Constants.NUM_20);
                System.out.println(partition.size());
// 测试数据
                AtomicInteger j = new AtomicInteger();
                partition.forEach(platformAddressAddRequests -> {
                    // 根据经纬度分批查询编码
                    StringBuffer stringBuffer = new StringBuffer();
                    platformAddressAddRequests.forEach(platformAddressAddRequest -> {
                        stringBuffer.append(platformAddressAddRequest.getCenter());
                        stringBuffer.append("%7C");
                    });
                    String location = stringBuffer.substring(0, stringBuffer.length()-3);
                    if (j.getAndIncrement() ==0){
                        List<GetAddressByLocationSimpleResponse> getAddressByLocationSimpleResponseList =
                                gaoDeService.batchGetAddressByLocation(GetAddressByLocationRequest.builder().location(location).batch(Boolean.TRUE).build());
                        List<String> centerList = Lists.newArrayList();
                        for (int i=0;i<platformAddressAddRequests.size();i++){
                            if (CollectionUtils.isNotEmpty(getAddressByLocationSimpleResponseList)){
                                GetAddressByLocationSimpleResponse getAddressByLocationSimpleResponse =
                                        getAddressByLocationSimpleResponseList.get(i);
                                PlatformAddressAddRequest platformAddressAddRequest = platformAddressAddRequests.get(i);
                                if (StringUtils.isNotBlank(getAddressByLocationSimpleResponse.getTowncode())){
                                    // 验证根据坐标查出的街道是否正确
                                    if(StringUtils.equals(platformAddressAddRequest.getAddrName(),
                                            getAddressByLocationSimpleResponse.getTownship())){
                                        platformAddressAddRequest.setAddrId(getAddressByLocationSimpleResponse.getTowncode());
                                    }else {
                                        // 记录根据坐标查到编码不准确的数据
                                        log.info("根据坐标查到编码不准确的数据,{}", JSON.toJSONString(platformAddressAddRequest));
                                        centerList.add(platformAddressAddRequest.getCenter());
                                    }
                                }else {
                                    // 记录根据坐标没有查到编码的数据
                                    log.info("根据坐标没有查到编码的数据,{}", JSON.toJSONString(platformAddressAddRequest));
                                    centerList.add(platformAddressAddRequest.getCenter());
                                }

                            }
                        }
                        List<PlatformAddressAddRequest> platformAddressAddRequestList = Lists.newArrayList();
                        if (CollectionUtils.isNotEmpty(centerList)) {
                            platformAddressAddRequestList =
                                    platformAddressAddRequests.stream().filter(platformAddressAddRequest -> centerList.contains(platformAddressAddRequest.getCenter())).collect(Collectors.toList());
                            platformAddressAddRequests =
                                    platformAddressAddRequests.stream().filter(platformAddressAddRequest -> !centerList.contains(platformAddressAddRequest.getCenter())).collect(Collectors.toList());
                        }
                        // 数据正确的
                        System.out.println("========================数据正确的=============================");
                        System.out.println(platformAddressAddRequests.size());
                        System.out.println(JSON.toJSONString(platformAddressAddRequests));
                        // 需要从原表补偿的
                        System.out.println("========================需要从原表补偿的=============================");
                        System.out.println(platformAddressAddRequestList.size());
                        System.out.println(JSON.toJSONString(platformAddressAddRequestList));
                    }
                });
            } else {
                log.error("错误信息，infocode={}", tokenResJson.getString("infocode"));
                log.error("错误信息，info={}", tokenResJson.getString("info"));
                throw new SbcRuntimeException(
                        CommonErrorCodeEnum.K000001, tokenResJson.getString("info"));
            }
        }
    }
}
