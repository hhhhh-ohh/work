package com.wanmi.sbc.goods.provider.impl.goodsproperty;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsproperty.*;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailQueryRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelQueryRequest;
import com.wanmi.sbc.goods.api.response.goodsproperty.*;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.goodspropcaterel.service.GoodsPropCateRelService;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import com.wanmi.sbc.goods.goodsproperty.service.GoodsPropertyService;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import com.wanmi.sbc.goods.goodspropertydetail.service.GoodsPropertyDetailService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.service.GoodsPropertyDetailRelService;
import com.wanmi.sbc.setting.api.provider.country.PlatformCountryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.country.PlatformCountryQueryRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.response.country.PlatformCountryListResponse;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.setting.bean.vo.PlatformCountryVO;

import jakarta.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 商品属性查询服务接口实现
 *
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@RestController
@Validated
public class GoodsPropertyQueryController implements GoodsPropertyQueryProvider {

    /**
     * logger 日志
     */
    private static final Logger logger =
            LoggerFactory.getLogger(GoodsPropertyQueryController.class);

    @Autowired
    private GoodsPropertyService goodsPropertyService;

    @Autowired
    private GoodsPropertyDetailService goodsPropertyDetailService;

    @Autowired
    private GoodsPropCateRelService goodsPropCateRelService;

    @Autowired
    private GoodsPropertyDetailRelService goodsPropertyDetailRelService;

    @Autowired
    private PlatformAddressQueryProvider addressQueryProvider;

    @Autowired
    private PlatformCountryProvider platformCountryProvider;


    @Override
    public BaseResponse<GoodsPropertyPageResponse> findGoodsPropertyPage(
            @RequestBody @Valid GoodsPropertyPageRequest request) {
        Long cateId = request.getCateId();

        if (Objects.nonNull(cateId)) {
            List<Long> propIdList = goodsPropCateRelService.getPropIdList(cateId);
            request.setPropIdList(propIdList);
        }
        Page<GoodsProperty> goodsPropertyPage = goodsPropertyService.page(request);

        List<Long> propIdList =
                goodsPropertyPage.getContent().stream()
                        .map(GoodsProperty::getPropId)
                        .collect(Collectors.toList());
        // 获取属性值
        Map<Long, String> detailNameMap = goodsPropCateRelService.detailNameMap(propIdList);
        // 获取分类名称
        Map<Long, String> cateNameMap = goodsPropCateRelService.cateNameMap(propIdList);
        Page<GoodsPropertyVO> newPage =
                goodsPropertyPage.map(
                        target -> {
                            GoodsPropertyVO source = new GoodsPropertyVO();
                            BeanUtils.copyProperties(target, source);
                            String detailName = detailNameMap.get(target.getPropId());
                            source.setDetailName(detailName);
                            String cateName = cateNameMap.get(target.getPropId());
                            source.setCateName(cateName);
                            return source;
                        });
        MicroServicePage<GoodsPropertyVO> microPage =
                new MicroServicePage<>(newPage, request.getPageable());
        GoodsPropertyPageResponse finalRes = new GoodsPropertyPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<GoodsPropertyListResponse> list(
            @RequestBody @Valid GoodsPropertyListRequest goodsPropertyListReq) {
        GoodsPropertyPageRequest pageRequest =
                KsBeanUtil.convert(goodsPropertyListReq, GoodsPropertyPageRequest.class);
        List<GoodsProperty> goodsPropertyList = goodsPropertyService.list(pageRequest);
        List<GoodsPropertyVO> newList =
                goodsPropertyList.stream()
                        .map(entity -> goodsPropertyService.wrapperVo(entity))
                        .collect(Collectors.toList());
        return BaseResponse.success(new GoodsPropertyListResponse(newList));
    }

    @Override
    public BaseResponse<GoodsPropertyByIdResponse> getGoodsPropertyById(
            @RequestBody @Valid GoodsPropertyByIdRequest request) {
        GoodsProperty goodsProperty = goodsPropertyService.getOne(request.getPropId());
        GoodsPropertyByIdResponse response =
                KsBeanUtil.convert(goodsProperty, GoodsPropertyByIdResponse.class);
        List<String> cateIdList = goodsPropCateRelService.getCateIdList(request.getPropId());
        List<PropertyDetailVO> propDetailList = goodsPropertyDetailService.getPropDetailList(request.getPropId());
        response.setCateIdList(cateIdList);
        response.setDetailList(propDetailList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsPropertySortResponse> getGoodsPropSortById(
            @RequestBody @Valid GoodsPropertyByIdRequest request) {
        List<GoodsProperty> propSortList = goodsPropertyService.getPropSortList(request.getPropId());
        Map<Long, Long> propSortMap = propSortList.stream()
                .collect(Collectors.toMap(GoodsProperty::getPropId, GoodsProperty::getPropSort));
        GoodsPropertySortResponse response = new GoodsPropertySortResponse(propSortMap);
        return BaseResponse.success(response);
    }
    @Override
    public BaseResponse<BigDecimal> getGoodsPropertyListForGoodsByType(@RequestBody @Valid GoodsPropertyByGoodsIdRequest request){
        String priceByIdAndType = goodsPropertyDetailRelService.findPriceByIdAndType(request.getGoodsId(), request.getPropName());
        BigDecimal bigDecimal = new BigDecimal(priceByIdAndType);
        return BaseResponse.success(bigDecimal);
    }
    @Override
    public BaseResponse<GoodsPropertyListForGoodsResponse> getGoodsPropertyListForGoods(
            @RequestBody @Valid GoodsPropertyByGoodsIdRequest request) {
        logger.info("根据商品id查询商品属性，参数：{}", JSONObject.toJSONString(request));
        GoodsPropertyListForGoodsResponse response = new GoodsPropertyListForGoodsResponse();
        // 根据商品spuId查询出商品属性关联表数据
        List<GoodsPropertyDetailRel> propertyDetailRels =
                goodsPropertyDetailRelService.list(
                        GoodsPropertyDetailRelQueryRequest.builder()
                                .goodsId(request.getGoodsId())
                                .goodsType(GoodsPropertyType.GOODS)
                                .delFlag(DeleteFlag.NO)
                                .build());
        if (CollectionUtils.isEmpty(propertyDetailRels)) {
            return BaseResponse.success(response);
        }
        // 把属性值为空的数据去除掉
        List<GoodsPropertyDetailRel> newPropDetailRelList = propertyDetailRels.stream()
                .filter(rel ->
                        (rel.getPropType() == GoodsPropertyEnterType.CHOOSE && StringUtils.isNoneBlank(rel.getDetailId()))
                                || (rel.getPropType() == GoodsPropertyEnterType.TEXT && StringUtils.isNoneBlank(rel.getPropValueText()))
                                || (rel.getPropType() == GoodsPropertyEnterType.DATE && Objects.nonNull(rel.getPropValueDate()))
                                || (rel.getPropType() == GoodsPropertyEnterType.PROVINCE && StringUtils.isNoneBlank(rel.getPropValueProvince()))
                                || (rel.getPropType() == GoodsPropertyEnterType.COUNTRY && StringUtils.isNoneBlank(rel.getPropValueCountry())))
                .collect(Collectors.toList());

        // 聚合出商品属性id
        List<Long> propIds =
                newPropDetailRelList.stream()
                        .map(GoodsPropertyDetailRel::getPropId)
                        .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(propIds)) {
            return BaseResponse.success(response);
        }

        // 找出三级分类id
        Long cateId = newPropDetailRelList.get(0).getCateId();
        Map<Long, Integer> catePropSortMap = goodsPropCateRelService.catePropSortMap(cateId);

        // 根据属性id集合查询出属性列表
        List<GoodsProperty> goodsPropertyList =
                goodsPropertyService.list(
                        GoodsPropertyPageRequest.builder().propIdList(propIds).build());

        // 聚合出该商品输入方式为选项的属性且已选择的属性值id集合
        List<Long> propDetailList = new ArrayList<>();
        newPropDetailRelList.stream()
                .filter(rel -> rel.getPropType() == GoodsPropertyEnterType.CHOOSE)
                .forEach(
                        rel -> {
                            String[] detailIdStrList = rel.getDetailId().split(",");
                            for (String idStr : detailIdStrList) {
                                propDetailList.add(Long.valueOf(idStr));
                            }
                        });
        List<GoodsPropertyDetail> goodsPropertyDetails = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(propDetailList)) {
            // 根据已选属性值id集合查询出属性值列表
            goodsPropertyDetails =
                    goodsPropertyDetailService.list(
                            GoodsPropertyDetailQueryRequest.builder()
                                    .detailIdList(propDetailList)
                                    .delFlag(DeleteFlag.NO)
                                    .build());
        }

        // 聚合出该商品已选择的省市id集合
        List<String> provinceList = new ArrayList<>();
        newPropDetailRelList.stream()
                .filter(rel -> rel.getPropType() == GoodsPropertyEnterType.PROVINCE)
                .forEach(
                        rel -> {
                            String[] chooseStrList = rel.getPropValueProvince().split(",");
                            for (String idStr : chooseStrList) {
                                provinceList.add(idStr);
                            }
                        });
        List<PlatformAddressVO> provinceVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(provinceList)) {
            provinceVOList =
                    addressQueryProvider
                            .list(
                                    PlatformAddressListRequest.builder()
                                            .addrIdList(provinceList)
                                            .delFlag(DeleteFlag.NO)
                                            .build())
                            .getContext()
                            .getPlatformAddressVOList();
        }

        // 已选的国家地区
        List<Long> countryList = new ArrayList<>();
        newPropDetailRelList.stream()
                .filter(rel -> rel.getPropType() == GoodsPropertyEnterType.COUNTRY)
                .forEach(
                        rel -> {
                            String[] chooseStrList = rel.getPropValueCountry().split(",");
                            for (String idStr : chooseStrList) {
                                countryList.add(Long.valueOf(idStr));
                            }
                        });
        List<PlatformCountryVO> countryVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(countryList)) {
            countryVOList =
                    platformCountryProvider
                            .findCountryList(
                                    PlatformCountryQueryRequest.builder()
                                            .countryIdList(countryList)
                                            .build())
                            .getContext()
                            .getPlatformCountryVOList();
        }

        // 类型转换
        List<GoodsPropertyDetailRelVO> detailRelVOList =
                newPropDetailRelList.stream()
                        .map(entity -> goodsPropertyDetailRelService.wrapperVo(entity))
                        .collect(Collectors.toList());

        List<GoodsPropertyVO> goodsPropertyVOList = new ArrayList<>();
        if(Objects.nonNull(catePropSortMap)&&catePropSortMap.size()>0){
            goodsPropertyVOList = goodsPropertyList.stream()
                    .map(entity -> {
                        GoodsPropertyVO propertyVO = goodsPropertyService.wrapperVo(entity);
                        // 商品属性按照三级分类下的商品属性排序
                        Integer catePropSort = catePropSortMap.get(entity.getPropId());
                        propertyVO.setSort(catePropSort);
                        return propertyVO;
                    }).sorted(Comparator.comparing(GoodsPropertyVO::getSort))
                    .collect(Collectors.toList());
        }

        List<GoodsPropertyDetailVO> detailVOList =
                goodsPropertyDetails.stream()
                        .map(entity -> goodsPropertyDetailService.wrapperVo(entity))
                        .collect(Collectors.toList());

        response.setGoodsPropertyDetailRelVOList(detailRelVOList);
        response.setGoodsPropertyVOList(goodsPropertyVOList);
        response.setGoodsPropertyDetailVOList(detailVOList);
        response.setProvinceVOList(provinceVOList);
        response.setCountryVOList(countryVOList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsPropertyByIdsListResponse> getGoodsPropRelNests(@RequestBody @Valid GoodsPropertyByIdRequest request) {
        GoodsPropertyByIdsListResponse response = new GoodsPropertyByIdsListResponse();
        Long propId = request.getPropId();
        List<Long> propIdList = Collections.singletonList(propId);
        //先去过滤propId
        List<GoodsPropertyDetailRel> relPropIdList = goodsPropertyDetailRelService.findByPropIdList(propIdList);
        if(CollectionUtils.isEmpty(relPropIdList)){
            return BaseResponse.success(response);
        }
        GoodsProperty goodsProperty = goodsPropertyService.getOne(propId);
        Map<Long, GoodsPropertyDetail> goodsPropertyDetailMap = goodsPropertyDetailService.getGoodsPropertyDetailMap(propIdList);
        Map<Long, String> countryMap = this.getCountryMap();
        GoodsPropRelVO goodsPropRelVO = new GoodsPropRelVO();
        goodsPropRelVO.setPropId(propId);
        goodsPropRelVO.setPropName(goodsProperty.getPropName());
        goodsPropRelVO.setPropSort(goodsProperty.getPropSort());
        goodsPropRelVO.setIndexFlag(goodsProperty.getIndexFlag());
        goodsPropRelVO.setPropCharacter(goodsProperty.getPropCharacter());
        goodsPropRelVO.setPropType(goodsProperty.getPropType());
        List<PropDetailVO> propDetailVOList =relPropIdList.stream()
                .map(target-> this.chooseDetailRel(target, goodsPropertyDetailMap, countryMap))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        goodsPropRelVO.setGoodsPropDetailNest(propDetailVOList);
        response.setGoodsPropRelVO(goodsPropRelVO);
        return BaseResponse.success(response);
    }

    private List<PropDetailVO> chooseDetailRel(GoodsPropertyDetailRel rel,
                                               Map<Long, GoodsPropertyDetail> goodsPropertyDetailMap,
                                               Map<Long, String> countryMap) {
        switch (rel.getPropType()) {
            case CHOOSE:
                String detailIds = rel.getDetailId();
                String[] detailIdArray = StringUtils.split(detailIds, ",");
                return Stream.of(detailIdArray).map(NumberUtils::toLong).map(id -> {
                    GoodsPropertyDetail propertyDetail = goodsPropertyDetailMap.get(id);
                    PropDetailVO propDetailVO = new PropDetailVO();
                    BeanUtils.copyProperties(propertyDetail, propDetailVO);
                    propDetailVO.setDetailNameValue(propertyDetail.getDetailName());
                    return propDetailVO;
                }).collect(Collectors.toList());
            case TEXT:
                String propValueText = rel.getPropValueText();
                PropDetailVO textDetailVO = new PropDetailVO();
                textDetailVO.setDetailId(NumberUtils.LONG_MINUS_ONE);
                textDetailVO.setDetailName(propValueText);
                textDetailVO.setDetailNameValue(propValueText);
                textDetailVO.setPropId(rel.getPropId());
                return Collections.singletonList(textDetailVO);
            case DATE:
                final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDateTime propValueDate = rel.getPropValueDate();
                String detailName = propValueDate.format(dateFormatter);
                String detailId = propValueDate.format(formatter);
                PropDetailVO dateDetailVO = new PropDetailVO();
                dateDetailVO.setDetailId(NumberUtils.toLong(detailId));
                dateDetailVO.setDetailName(detailName);
                dateDetailVO.setDetailNameValue(detailName);
                return Collections.singletonList(dateDetailVO);
            case PROVINCE:
                String propValueCountries = rel.getPropValueCountry();
                String[] countryArray = StringUtils.split(propValueCountries, ",");
                return Stream.of(countryArray).map(NumberUtils::toLong).map(id -> {
                    String provinceName = countryMap.get(id);
                    PropDetailVO propDetailVO = new PropDetailVO();
                    propDetailVO.setDetailName(provinceName);
                    propDetailVO.setDetailNameValue(provinceName);
                    propDetailVO.setDetailId(id);
                    propDetailVO.setPropId(rel.getPropId());
                    return propDetailVO;
                }).collect(Collectors.toList());
            default:
                String propValueProvinces = rel.getPropValueProvince();
                String[] provinceArray = StringUtils.split(propValueProvinces, ",");
                Map<String, String> addressMap = this.getAddress(Lists.newArrayList(provinceArray));
                return Stream.of(provinceArray).map(id -> {
                    String provinceName = addressMap.get(id);
                    PropDetailVO propDetailVO = new PropDetailVO();
                    propDetailVO.setDetailName(provinceName);
                    propDetailVO.setDetailNameValue(provinceName);
                    propDetailVO.setDetailId(NumberUtils.toLong(id));
                    propDetailVO.setPropId(rel.getPropId());
                    return propDetailVO;
                }).collect(Collectors.toList());

        }
    }


    /**
     * 获取国家地区
     *
     * @return
     */
    private Map<Long, String> getCountryMap() {
        BaseResponse<PlatformCountryListResponse> countryList = platformCountryProvider.findAll();
        List<PlatformCountryVO> platformCountryVOList = countryList.getContext().getPlatformCountryVOList();
        return platformCountryVOList.stream()
                .collect(Collectors.toMap(PlatformCountryVO::getId, PlatformCountryVO::getName));
    }

    /**
     * 获取地址
     *
     * @param addressIdList
     * @return
     */
    private Map<String, String> getAddress(List<String> addressIdList) {
        List<PlatformAddressVO> platformAddressVOList = addressQueryProvider
                .list(
                        PlatformAddressListRequest.builder()
                                .addrIdList(addressIdList)
                                .delFlag(DeleteFlag.NO)
                                .build())
                .getContext()
                .getPlatformAddressVOList();

        return platformAddressVOList.stream()
                .collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
    }
}
