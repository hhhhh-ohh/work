package com.wanmi.sbc.goods.api.provider.spec;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.spec.GoodsSpecListByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsSpecQueryRequest;
import com.wanmi.sbc.goods.api.response.spec.GoodsDetailSpecInfoResponse;
import com.wanmi.sbc.goods.api.response.spec.GoodsInfoSpecForExportResponse;
import com.wanmi.sbc.goods.api.response.spec.GoodsSpecListByGoodsIdsResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 商品规格查询服务
 *
 * @author daiyitian
 * @dateTime 2018/11/13 14:57
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsSpecQueryProvider")
public interface GoodsSpecQueryProvider {

    /**
     * 根据goodsIds批量查询商品规格列表
     *
     * @param request 包含goodsIds的查询请求结构 {@link GoodsSpecListByGoodsIdsRequest }
     * @return 商品规格列表 {@link GoodsSpecListByGoodsIdsResponse }
     */
    @PostMapping("/goods/${application.goods.version}/spec/list-by-goods-ids")
    BaseResponse<GoodsSpecListByGoodsIdsResponse> listByGoodsIds(@RequestBody @Valid GoodsSpecListByGoodsIdsRequest
                                                                         request);

    /**
     * 根据goodsInfoIds批量查询商品规格列表（导出）
     *
     * @param request 包含goodsIds的查询请求结构 {@link GoodsSpecListByGoodsIdsRequest }
     * @return 商品规格列表 {@link GoodsSpecListByGoodsIdsResponse }
     */
    @PostMapping("/goods/${application.goods.version}/spec/list-by-goods-info-ids-for-export")
    BaseResponse<GoodsInfoSpecForExportResponse> listByGoodsInfoIdsForExport(@RequestBody @Valid GoodsSpecListByGoodsIdsRequest
                                                                         request);
    /***
     * 根据商品ID和规格Name查询规格列表
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/spec/list-spec-by-name-and-goodsid")
    BaseResponse<List<GoodsSpecVO>> listSpecByNameAndGoodsId(@RequestBody GoodsSpecQueryRequest request);

    /***
     * 根据商品ID和规格Name查询规格详情列表
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/spec/list-spec-detail-by-name-and-goodsid")
    BaseResponse<List<GoodsSpecDetailVO>> listSpecDetailsByNameAndGoodsId(@RequestBody GoodsSpecQueryRequest request);

    /***
     * 根据商品ID查询规格详情列表
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/spec/list-spec-detail-by-goodsid")
    BaseResponse<GoodsDetailSpecInfoResponse> goodsDetailSpecInfo(@RequestBody GoodsSpecQueryRequest request);
}
