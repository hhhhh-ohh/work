package com.wanmi.sbc.goods.provider.impl.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodspropertydetailrel.GoodsPropertyDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.*;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailGoodsIdResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelByIdResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelListResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelPageResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import com.wanmi.sbc.goods.goodspropcaterel.service.GoodsPropCateRelService;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import com.wanmi.sbc.goods.goodsproperty.service.GoodsPropertyService;
import com.wanmi.sbc.goods.goodspropertydetail.service.GoodsPropertyDetailService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.service.GoodsPropertyDetailRelService;

import jakarta.validation.Valid;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>商品与属性值关联查询服务接口实现</p>
 *
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@RestController
@Validated
public class GoodsPropertyDetailRelQueryController implements GoodsPropertyDetailRelQueryProvider {
    @Autowired
    private GoodsPropertyDetailRelService goodsPropertyDetailRelService;

    @Autowired
    private GoodsPropertyService goodsPropertyService;

    @Autowired
    private GoodsPropertyDetailService goodsPropertyDetailService;

    @Autowired
    private GoodsPropCateRelService goodsPropCateRelService;


    @Override
    public BaseResponse<GoodsPropertyDetailRelPageResponse> page(@RequestBody @Valid GoodsPropertyDetailRelPageRequest goodsPropertyDetailRelPageReq) {
        GoodsPropertyDetailRelQueryRequest queryReq = KsBeanUtil.convert(goodsPropertyDetailRelPageReq, GoodsPropertyDetailRelQueryRequest.class);
        Page<GoodsPropertyDetailRel> goodsPropertyDetailRelPage = goodsPropertyDetailRelService.page(queryReq);
        Page<GoodsPropertyDetailRelVO> newPage = goodsPropertyDetailRelPage.map(entity -> goodsPropertyDetailRelService.wrapperVo(entity));
        MicroServicePage<GoodsPropertyDetailRelVO> microPage = new MicroServicePage<>(newPage, goodsPropertyDetailRelPageReq.getPageable());
        GoodsPropertyDetailRelPageResponse finalRes = new GoodsPropertyDetailRelPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<GoodsPropertyDetailGoodsIdResponse> getGoodsProperty(@RequestBody @Valid GoodsPropertyDetailGoodsIdRequest request) {
        String goodsId = request.getGoodsId();
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList =
                goodsPropertyDetailRelService.findByGoodsId(goodsId,request.getGoodsType(),request.getStoreId());


        List<Long> detailIdList = this.getDetailIdList(goodsPropertyDetailRelList);
        List<Long> propIdList = this.getPropIdList(goodsPropertyDetailRelList);
        Map<Long, GoodsProperty> goodsPropertyMap = goodsPropertyService.findGoodsPropertyMap(propIdList);
        Map<Long, List<GoodsPropDetailVO>> detailMap = goodsPropCateRelService.detailFilterMap(detailIdList);
        List<GoodsPropertyDetailRelDTO> newList = goodsPropertyDetailRelList.stream().map(target ->
                this.setProperties(target, goodsPropertyMap, detailMap)
        ).collect(Collectors.toList());
        GoodsPropertyDetailGoodsIdResponse response = new GoodsPropertyDetailGoodsIdResponse(newList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<List<GoodsPropertyDetailRelDTO>> getCatePropsDetail(@RequestBody @Valid GoodsPropCateRelListRequest request) {
        Long cateId = request.getCateId();
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList =
                goodsPropertyDetailRelService.findByCateId(cateId);

        List<Long> detailIdList = this.getDetailIdList(goodsPropertyDetailRelList);
        List<Long> propIdList = this.getPropIdList(goodsPropertyDetailRelList);
        Map<Long, GoodsProperty> goodsPropertyMap = goodsPropertyService.findGoodsPropertyMap(propIdList);
        Map<Long, List<GoodsPropDetailVO>> detailMap = goodsPropCateRelService.detailFilterMap(detailIdList);
        List<GoodsPropertyDetailRelDTO> newList = goodsPropertyDetailRelList.stream().map(target ->
                this.setProperties(target, goodsPropertyMap, detailMap)
        ).collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GoodsPropertyDetailRelDTO::getPropId))), ArrayList::new));
        return BaseResponse.success(newList);
    }

    @Override
    public BaseResponse<GoodsPropertyDetailRelListResponse> list(@RequestBody @Valid GoodsPropertyDetailRelListRequest goodsPropertyDetailRelListReq) {
        GoodsPropertyDetailRelQueryRequest queryReq = KsBeanUtil.convert(goodsPropertyDetailRelListReq, GoodsPropertyDetailRelQueryRequest.class);
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList = goodsPropertyDetailRelService.list(queryReq);
        List<GoodsPropertyDetailRelVO> newList = goodsPropertyDetailRelList.stream().map(entity -> goodsPropertyDetailRelService.wrapperVo(entity)).collect(Collectors.toList());
        return BaseResponse.success(new GoodsPropertyDetailRelListResponse(newList));
    }

    @Override
    public BaseResponse<GoodsPropertyDetailRelByIdResponse> getById(@RequestBody @Valid GoodsPropertyDetailRelByIdRequest goodsPropertyDetailRelByIdRequest) {
        GoodsPropertyDetailRel goodsPropertyDetailRel =
                goodsPropertyDetailRelService.getOne(goodsPropertyDetailRelByIdRequest.getDetailRelId());
        return BaseResponse.success(new GoodsPropertyDetailRelByIdResponse(goodsPropertyDetailRelService.wrapperVo(goodsPropertyDetailRel)));
    }

    /**
     * 获取选中属性值
     *
     * @param goodsPropertyDetailRelList
     * @return
     */
    private List<Long> getDetailIdList(List<GoodsPropertyDetailRel> goodsPropertyDetailRelList) {

        return goodsPropertyDetailRelList.stream()
                .map(GoodsPropertyDetailRel::getDetailId)
                .map(this::split)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * 获取属性id
     *
     * @param goodsPropertyDetailRelList
     * @return
     */
    private List<Long> getPropIdList(List<GoodsPropertyDetailRel> goodsPropertyDetailRelList) {
        return goodsPropertyDetailRelList.stream()
                .distinct()
                .map(GoodsPropertyDetailRel::getPropId)
                .collect(Collectors.toList());
    }

    /**
     * 书记库字段截取
     *
     * @param id
     * @return
     */
    private List<Long> split(String id) {
        if (StringUtils.isBlank(id)) {
            return Collections.emptyList();
        }
        String[] split = StringUtils.split(id, ",");
        return Stream.of(split)
                .filter(StringUtils::isNotBlank)
                .map(NumberUtils::toLong)
                .collect(Collectors.toList());
    }

    /**
     * 书记库字段截取
     *
     * @param id
     * @return
     */
    private List<String> splitCity(String id) {
        if (StringUtils.isBlank(id)) {
            return Collections.emptyList();
        }
        String[] split = StringUtils.split(id, ",");
        return Stream.of(split)
                .collect(Collectors.toList());
    }

    /**
     * 设值操作
     *
     * @return
     */
    private GoodsPropertyDetailRelDTO setProperties(GoodsPropertyDetailRel target,
                                                    Map<Long, GoodsProperty> goodsPropertyMap,
                                                    Map<Long, List<GoodsPropDetailVO>> detailMap) {
        GoodsPropertyDetailRelDTO source = new GoodsPropertyDetailRelDTO();
        BeanUtils.copyProperties(target, source);
        String propValueProvince = target.getPropValueProvince();
        String propValueCountry = target.getPropValueCountry();
        List<String> provinceList = this.splitCity(propValueProvince);
        List<Long> countryList = this.split(propValueCountry);
        source.setPropValueProvince(provinceList);
        source.setPropValueCountry(countryList);
        GoodsProperty goodsProperty = goodsPropertyMap.get(target.getPropId());
        source.setPropName(goodsProperty.getPropName());
        if (MapUtils.isNotEmpty(detailMap)) {
            List<GoodsPropDetailVO> detailVOList = detailMap.get(target.getPropId());
            source.setGoodsPropDetails(detailVOList);
        }
        source.setPropRequired(goodsProperty.getPropRequired());
        return source;
    }

}

