package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodspropertydetailrel.GoodsPropertyDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateByIdResponse;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateListByConditionResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品分类Controller
 * Created by daiyitian on 17/4/12.
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name = "GoodsCateBaseController", description = "S2B web公用-商品分类信息API")
public class GoodsCateBaseController {

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsPropertyDetailRelQueryProvider goodsPropertyDetailRelQueryProvider;

    /**
     * 查询商品分类
     * @return 商品分类树形JSON
     */
    @Operation(summary = "从缓存中获取商品分类信息列表")
    @RequestMapping(value = {"/allGoodsCates"}, method = RequestMethod.GET)
    public BaseResponse<String> list() {
        return BaseResponse.success(goodsCateQueryProvider.getByCache().getContext().getResult());
    }

    /**
     * 查询分类的路径
     * @return 商品分类树形JSON
     */
    @Operation(summary = "根据条件查询商品分类列表信息")
    @Parameter(name = "id", description = "商品分类id", required = true)
    @RequestMapping(value = {"/parentGoodsCates/{id}"}, method = RequestMethod.GET)
    public BaseResponse<List<GoodsCateVO>> list(@PathVariable Long id) {

        GoodsCateByIdRequest goodsCateByIdRequest = new GoodsCateByIdRequest();
        goodsCateByIdRequest.setCateId(id);
        BaseResponse<GoodsCateByIdResponse> goodsCate = goodsCateQueryProvider.getById(goodsCateByIdRequest);
        GoodsCateByIdResponse goodsCateByIdResponse = goodsCate.getContext();

        if(Objects.isNull(goodsCateByIdResponse)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        GoodsCateListByConditionRequest goodsCateListByConditionRequest = new GoodsCateListByConditionRequest();
        goodsCateListByConditionRequest.setCateIds(Arrays.stream(ObjectUtils.toString(goodsCateByIdResponse.getCatePath())
                .split(Constants.CATE_PATH_SPLITTER))
                .map(NumberUtils::toLong)
                .collect(Collectors.toList()));
        goodsCateListByConditionRequest.putSort("isDefault", SortType.DESC.toValue());
        goodsCateListByConditionRequest.putSort("sort", SortType.ASC.toValue());
        goodsCateListByConditionRequest.putSort("createTime", SortType.ASC.toValue());
        BaseResponse<GoodsCateListByConditionResponse> goodsCateListByConditionResponseBaseResponse
                = goodsCateQueryProvider.listByCondition(goodsCateListByConditionRequest);
        List<GoodsCateVO> cates = goodsCateListByConditionResponseBaseResponse.getContext().getGoodsCateVOList();
        cates.add(KsBeanUtil.convert(goodsCateByIdResponse, GoodsCateVO.class));

        return BaseResponse.success(cates);
    }

    /**
     * 分页查询商品属性
     *
     * @param cateId
     * @return
     */
    @Operation(summary = "根据类目id查询商品属性、属性值")
    @GetMapping(value = "/cate/prop-detail/{cateId}")
    public BaseResponse<List<GoodsPropertyDetailRelDTO>> findGoodsPropertyPage(@PathVariable("cateId") Long cateId) {
        GoodsPropCateRelListRequest request = GoodsPropCateRelListRequest.builder()
                .cateId(cateId)
                .build();
        return goodsPropertyDetailRelQueryProvider.getCatePropsDetail(request);
    }
}
