package com.wanmi.sbc.goods.api.provider.goodspropertydetailrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelListRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelPageRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailGoodsIdResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelByIdResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelListResponse;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelPageResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>商品与属性值关联查询服务Provider</p>
 *
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsPropertyDetailRelQueryProvider")
public interface GoodsPropertyDetailRelQueryProvider {

    /**
     * 分页查询商品与属性值关联API
     *
     * @param goodsPropertyDetailRelPageReq 分页请求参数和筛选对象 {@link GoodsPropertyDetailRelPageRequest}
     * @return 商品与属性值关联分页列表信息 {@link GoodsPropertyDetailRelPageResponse}
     * @author chenli
     */
    @PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/page")
    BaseResponse<GoodsPropertyDetailRelPageResponse> page(@RequestBody @Valid GoodsPropertyDetailRelPageRequest goodsPropertyDetailRelPageReq);

    /**
     * 查看详情
     *
     * @param request 分页请求参数和筛选对象 {@link GoodsPropertyDetailGoodsIdRequest}
     * @return 商品与属性值关联分页列表信息 {@link GoodsPropertyDetailRelPageResponse}
     * @author chenli
     */
    @PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/get-goods-property")
    BaseResponse<GoodsPropertyDetailGoodsIdResponse> getGoodsProperty(@RequestBody @Valid GoodsPropertyDetailGoodsIdRequest request);

    /**
     * 查看详情
     *
     * @param request 分页请求参数和筛选对象 {@link GoodsPropertyDetailGoodsIdRequest}
     * @return 商品与属性值关联分页列表信息 {@link GoodsPropertyDetailRelPageResponse}
     * @author chenli
     */
    @PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/get-cate-property-detail")
    BaseResponse<List<GoodsPropertyDetailRelDTO>> getCatePropsDetail(@RequestBody @Valid GoodsPropCateRelListRequest request);


    /**
     * 列表查询商品与属性值关联API
     *
     * @param goodsPropertyDetailRelListReq 列表请求参数和筛选对象 {@link GoodsPropertyDetailRelListRequest}
     * @return 商品与属性值关联的列表信息 {@link GoodsPropertyDetailRelListResponse}
     * @author chenli
     */
    @PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/list")
    BaseResponse<GoodsPropertyDetailRelListResponse> list(@RequestBody @Valid GoodsPropertyDetailRelListRequest goodsPropertyDetailRelListReq);

    /**
     * 单个查询商品与属性值关联API
     *
     * @param goodsPropertyDetailRelByIdRequest 单个查询商品与属性值关联请求参数 {@link GoodsPropertyDetailRelByIdRequest}
     * @return 商品与属性值关联详情 {@link GoodsPropertyDetailRelByIdResponse}
     * @author chenli
     */
    @PostMapping("/goods/${application.goods.version}/goodspropertydetailrel/get-by-id")
    BaseResponse<GoodsPropertyDetailRelByIdResponse> getById(@RequestBody @Valid GoodsPropertyDetailRelByIdRequest goodsPropertyDetailRelByIdRequest);

}

