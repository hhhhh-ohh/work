package com.wanmi.sbc.goods.api.provider.standard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuByIdRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardPartColsListByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardSkuByStandardIdRequest;
import com.wanmi.sbc.goods.api.response.standard.StandardGoodsByConditionResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardSkuByIdResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardPartColsListByGoodsIdsResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardSkuByStandardIdResponse;
import com.wanmi.sbc.goods.bean.vo.StandardSpecDetailVO;
import com.wanmi.sbc.goods.bean.vo.StandardSpecVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>对商品库查询接口</p>
 * Created by daiyitian on 2018-11-5-下午6:23.
 */
@FeignClient(value = "${application.goods.name}", contextId = "StandardSkuQueryProvider")
public interface StandardSkuQueryProvider {

    /**
     * 根据id获取商品库信息
     *
     * @param request 包含id的商品库信息查询结构 {@link StandardSkuByIdRequest}
     * @return 商品库信息 {@link StandardSkuByIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/standard/sku/get-by-id")
    BaseResponse<StandardSkuByIdResponse> getById(@RequestBody @Valid StandardSkuByIdRequest request);

    /**
     * 根据商品库id获取商品库sku信息
     *
     * @param request 包含id的商品库信息查询结构 {@link StandardSkuByStandardIdRequest}
     * @return 商品库Sku信息 {@link StandardSkuByStandardIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/standard/sku/list-by-standard-id")
    BaseResponse<StandardSkuByStandardIdResponse> listByStandardId(@RequestBody @Valid StandardSkuByStandardIdRequest request);

    /**
     * 根据goodsIds批量查询商品Sku库的局部字段信息
     *
     * @param request 包含查询结构 {@link StandardPartColsListByGoodsIdsRequest}
     * @return 局部字段信息 {@link StandardPartColsListByGoodsIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/standard/sku/list-part-cols-by-goods-ids")
    BaseResponse<StandardPartColsListByGoodsIdsResponse> listPartColsByGoodsIds(@RequestBody @Valid StandardPartColsListByGoodsIdsRequest request);

    /***
     * 根据goodsId批量查询规格信息
     * @param request goodsId
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/standard/sku/list-spec-goods-id")
    BaseResponse<List<StandardSpecVO>> listSpecByGoodsId(@RequestBody @Valid StandardSkuByIdRequest request);

    /***
     * 根据goodsId批量查询规格信息详情
     * @param request goodsId
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/standard/sku/list-spec-detail-standard-id")
    BaseResponse<List<StandardSpecDetailVO>> listSpecDetailByGoodsId(@RequestBody @Valid StandardSkuByIdRequest request);

    /**
     * 根据动态条件查询商品sku列表
     *
     * @param request 根据动态条件查询结构 {@link StandardGoodsByConditionRequest}
     * @return 商品sku列表 {@link StandardGoodsByConditionResponse}
     */
    @PostMapping("/goods/${application.goods.version}/vop/info/list-by-condition")
    BaseResponse<StandardGoodsByConditionResponse> listByCondition(@RequestBody @Valid StandardGoodsByConditionRequest
                                                                           request);
}
