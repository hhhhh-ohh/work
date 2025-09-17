package com.wanmi.sbc.goods.provider.impl.goodspropcaterel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodspropcaterel.GoodsPropCateRelQueryProvider;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelPageRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelQueryRequest;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelByIdResponse;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelListResponse;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelPageResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropCateRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import com.wanmi.sbc.goods.goodspropcaterel.model.root.GoodsPropCateRel;
import com.wanmi.sbc.goods.goodspropcaterel.service.GoodsPropCateRelService;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import com.wanmi.sbc.goods.goodsproperty.service.GoodsPropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>商品类目与属性关联查询服务接口实现</p>
 *
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@RestController
@Validated
public class GoodsPropCateRelQueryController implements GoodsPropCateRelQueryProvider {

    @Autowired
    private GoodsPropCateRelService goodsPropCateRelService;

    @Autowired
    private GoodsPropertyService goodsPropertyService;

    @Override
    public BaseResponse<GoodsPropCateRelPageResponse> page(@RequestBody @Valid GoodsPropCateRelPageRequest goodsPropCateRelPageReq) {
        GoodsPropCateRelQueryRequest queryReq = KsBeanUtil.convert(goodsPropCateRelPageReq, GoodsPropCateRelQueryRequest.class);
        Page<GoodsPropCateRel> goodsPropCateRelPage = goodsPropCateRelService.page(queryReq);
        Page<GoodsPropCateRelVO> newPage = goodsPropCateRelPage.map(entity -> goodsPropCateRelService.wrapperVo(entity));
        MicroServicePage<GoodsPropCateRelVO> microPage = new MicroServicePage<>(newPage, goodsPropCateRelPageReq.getPageable());
        GoodsPropCateRelPageResponse finalRes = new GoodsPropCateRelPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<GoodsPropCateRelListResponse> findCateProperty(@RequestBody @Valid GoodsPropCateRelListRequest request) {
        Long cateId = request.getCateId();
        Map<Long, Integer> catePropSortMap = goodsPropCateRelService.catePropSortMap(cateId);
        List<GoodsProperty> catePropertyList = goodsPropertyService.findCatePropertyList(catePropSortMap.keySet());
        Map<Long, String> detailNameMap = goodsPropCateRelService.detailNameMap(catePropSortMap.keySet());
        List<GoodsPropertyVO> newList = catePropertyList.stream().map(target -> {
            GoodsPropertyVO source = new GoodsPropertyVO();
            BeanUtils.copyProperties(target, source);
            String detailName = detailNameMap.get(target.getPropId());
            source.setDetailName(detailName);
            Integer catePropSort = catePropSortMap.get(target.getPropId());
            source.setSort(catePropSort);
            source.setCateId(cateId);
            return source;
        }).sorted(Comparator.comparing(GoodsPropertyVO::getSort))
                .collect(Collectors.toList());
        GoodsPropCateRelListResponse response = new GoodsPropCateRelListResponse(newList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<List<GoodsPropertyDetailRelDTO>> findCateDetailProp(@RequestBody @Valid GoodsPropCateRelListRequest request) {
        Long cateId = request.getCateId();
        Map<Long, Integer> catePropSortMap = goodsPropCateRelService.catePropSortMap(cateId);
        List<GoodsProperty> catePropertyList = goodsPropertyService.findCatePropertyList(catePropSortMap.keySet());
        Map<Long, List<GoodsPropDetailVO>> detailMap = goodsPropCateRelService.detailMap(catePropSortMap.keySet());
        List<GoodsPropertyDetailRelDTO> result = catePropertyList.stream().map(target -> {
            GoodsPropertyDetailRelDTO source = new GoodsPropertyDetailRelDTO();
            BeanUtils.copyProperties(target, source);
            List<GoodsPropDetailVO> detailVOList = detailMap.get(target.getPropId());
            source.setGoodsPropDetails(detailVOList);
            Integer catePropSort = catePropSortMap.get(target.getPropId());
            source.setSort(catePropSort);
            return source;
        }).sorted(Comparator.comparing(GoodsPropertyDetailRelDTO::getSort))
                .collect(Collectors.toList());
        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse<GoodsPropCateRelByIdResponse> getById(@RequestBody @Valid GoodsPropCateRelByIdRequest goodsPropCateRelByIdRequest) {
        GoodsPropCateRel goodsPropCateRel =
                goodsPropCateRelService.getOne(goodsPropCateRelByIdRequest.getRelId());
        return BaseResponse.success(new GoodsPropCateRelByIdResponse(goodsPropCateRelService.wrapperVo(goodsPropCateRel)));
    }

    /**
     * 根据分类id和属性id查询关联关系
     * @param goodsPropCateRelQueryRequest
     * @return
     */
    @Override
    public BaseResponse<GoodsPropCateRelByIdResponse> getByCateIdAndPropId(
            @RequestBody @Valid GoodsPropCateRelQueryRequest goodsPropCateRelQueryRequest) {
        GoodsPropCateRel goodsPropCateRel = goodsPropCateRelService.getByCateIdAndPropId(
                goodsPropCateRelQueryRequest.getCateId(),
                goodsPropCateRelQueryRequest.getPropId());
        return BaseResponse.success(
                new GoodsPropCateRelByIdResponse(goodsPropCateRelService.wrapperVo(goodsPropCateRel)));
    }
}

