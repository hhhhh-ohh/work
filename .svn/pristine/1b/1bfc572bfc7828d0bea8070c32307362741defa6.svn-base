package com.wanmi.sbc.goods.api.provider.info;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.common.GoodsInfoTradeRequest;
import com.wanmi.sbc.goods.api.request.common.InfoForPurchaseRequest;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterpriseGoodsInfoPageRequest;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.response.enterprise.EnterpriseGoodsInfoPageResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsIdByStockResponse;
import com.wanmi.sbc.goods.api.response.info.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>对商品sku查询接口</p>
 * Created by daiyitian on 2018-11-5-下午6:23.
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsInfoQueryProvider")
public interface GoodsInfoQueryProvider {

    /**
     * 分页查询商品sku视图列表
     *
     * @param request 商品sku视图分页条件查询结构 {@link GoodsInfoViewPageRequest}
     * @return 商品sku视图分页列表 {@link GoodsInfoViewPageResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/page-view")
    BaseResponse<GoodsInfoViewPageResponse> pageView(@RequestBody @Valid GoodsInfoViewPageRequest request);

    /**
     * 分页查询商品sku列表
     *
     * @param request 商品sku分页条件查询结构 {@link GoodsInfoPageRequest}
     * @return 商品sku分页列表 {@link GoodsInfoPageResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/page")
    BaseResponse<GoodsInfoPageResponse> page(@RequestBody @Valid GoodsInfoPageRequest request);

    /**
     * 根据商品skuId批量查询商品sku视图列表
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoViewByIdsRequest}
     * @return 商品sku视图列表 {@link GoodsInfoViewByIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-view-by-ids")
    BaseResponse<GoodsInfoViewByIdsResponse> listViewByIds(@RequestBody @Valid GoodsInfoViewByIdsRequest request);


    /**
     * 订单提交查询商品
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoViewByIdsRequest}
     * @return 商品sku视图列表 {@link GoodsInfoViewByIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/trade-confirm-goods")
    BaseResponse<TradeConfirmGoodsResponse> tradeConfirmGoodsInfo(@RequestBody @Valid TradeConfirmGoodsRequest request);

    /**
     * 根据商品skuId查询商品sku视图
     *
     * @param request 根据商品skuId查询结构 {@link GoodsInfoViewByIdRequest}
     * @return 商品sku视图 {@link GoodsInfoViewByIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/get-view-by-id")
    BaseResponse<GoodsInfoViewByIdResponse> getViewById(@RequestBody @Valid GoodsInfoViewByIdRequest request);

    /**
     * 根据商品skuId批量查询商品sku列表
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoListByIdsRequest}
     * @return 商品sku列表 {@link GoodsInfoListByIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-by-ids")
    BaseResponse<GoodsInfoListByIdsResponse> listByIds(@RequestBody @Valid GoodsInfoListByIdsRequest request);

    /**
     * 根据商品skuId查询商品sku
     *
     * @param request 根据商品skuId查询结构 {@link GoodsInfoByIdRequest}
     * @return 商品sku {@link GoodsInfoByIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/get-by-id")
    BaseResponse<GoodsInfoByIdResponse> getById(@RequestBody @Valid GoodsInfoByIdRequest request);

    /**
     * 根据动态条件查询商品sku列表
     *
     * @param request 根据动态条件查询结构 {@link GoodsInfoListByConditionRequest}
     * @return 商品sku列表 {@link GoodsInfoListByConditionResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-by-condition")
    BaseResponse<GoodsInfoListByConditionResponse> listByCondition(@RequestBody @Valid GoodsInfoListByConditionRequest
                                                                           request);

    /**
     * 根据动态条件查询商品sku列表
     *
     * @param request 根据动态条件查询结构 {@link GoodsInfoListByConditionRequest}
     * @return 商品sku列表 {@link GoodsInfoListByConditionResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-by-condition-add-goods")
    BaseResponse<GoodsInfoListByConditionResponse> listByConditionAddGoods(@RequestBody @Valid GoodsInfoListByConditionRequest
                                                                           request);
    /**
     * 根据动态条件统计商品sku个数
     *
     * @param request 根据动态条件统计结构 {@link GoodsInfoCountByConditionRequest}
     * @return 商品sku个数 {@link GoodsInfoCountByConditionResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/count-by-condition")
    BaseResponse<GoodsInfoCountByConditionResponse> countByCondition(@RequestBody @Valid
                                                                             GoodsInfoCountByConditionRequest
                                                                             request);

    /**
     * 分页查询分销商品sku视图列表
     *
     * @param request 分销商品sku视图分页条件查询结构 {@link DistributionGoodsPageRequest}
     * @return 分销商品sku视图分页列表 {@link DistributionGoodsInfoPageResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/page-distribution")
    BaseResponse<DistributionGoodsInfoPageResponse> distributionGoodsInfoPage(@RequestBody @Valid DistributionGoodsPageRequest request);

    /**
     * @Description: 商品ID<spu> 查询sku信息
     * @Author: Bob
     * @Date: 2019-03-11 20:43
     */
    @PostMapping("/goods/${application.goods.version}/info/get-by-goodsid")
    BaseResponse<GoodsInfoByGoodsIdresponse> getByGoodsId(@RequestBody @Valid DistributionGoodsChangeRequest request);

    /**
     * 分页查询企业购商品sku视图列表
     *
     * @param request 分页查询企业购商品sku视图列表 {@link EnterpriseGoodsInfoPageRequest}
     * @return 分销商品sku视图分页列表 {@link EnterpriseGoodsInfoPageResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/page-enterprise")
    BaseResponse<EnterpriseGoodsInfoPageResponse> enterpriseGoodsInfoPage(@RequestBody @Valid EnterpriseGoodsInfoPageRequest request);

    /**
     * 根据skuid获取storeid
     *
     * @param {@link GoodsInfoStoreIdBySkuIdRequest}
     * @return {@link GoodsInfoStoreIdBySkuIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/get-storeId-By-GoodsId")
    BaseResponse<GoodsInfoStoreIdBySkuIdResponse> getStoreIdByGoodsId(@RequestBody @Valid GoodsInfoStoreIdBySkuIdRequest request);

    /**
     * 根据商品id查询商品的积分价
     *
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/list-integral-goods-by-ids")
    BaseResponse<GoodsInfoListByIdsResponse> listIntegralPriceGoodsByIds(@RequestBody @Valid GoodsInfoListByIdsRequest request);

    /**
     * 根据商品id批量查询商品sku部分字段列表
     *
     * @param request 根据商品id批量查询结构 {@link GoodsInfoPartColsByIdsRequest}
     * @return 商品sku部分字段列表 {@link GoodsInfoPartColsByIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-part-cols-by-ids")
    BaseResponse<GoodsInfoPartColsByIdsResponse> listPartColsByIds(@RequestBody @Valid GoodsInfoPartColsByIdsRequest
                                                                           request);

    /**
     * 根据商品id查询商品的积分价
     *
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/goods-info-by-id")
    BaseResponse<GoodsInfoListByIdResponse> getGoodsInfoById(@RequestBody GoodsInfoListByIdRequest request);

    /**
     * 根据商品skuId批量查询商品sku列表
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoListByIdsRequest}
     * @return 商品sku列表 {@link GoodsInfoListByIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/goods-info-by-ids")
    BaseResponse<GoodsInfoListByIdsResponse> getGoodsInfoByIds(@RequestBody @Valid GoodsInfoListByIdsRequest request);


    /**
     * 根据SKU NO批量查询商品SKU市场价信息
     *
     * @param request 包含SKU ID 集合的条件参数 {@link GoodsInfoMarketingPriceByNosRequest}
     * @return SKU市场价列表 {@link GoodsInfoMarketingPriceByNosResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-marketing-price-by-nos")
    BaseResponse<GoodsInfoMarketingPriceByNosResponse> listMarketingPriceByNos(@RequestBody @Valid GoodsInfoMarketingPriceByNosRequest request);

    /**
     * 根据SKU NO批量查询商品SKU集合
     *
     * @param request 包含SKU ID 集合的条件参数 {@link GoodsInfoListBySkuNosRequest}
     * @return SKU列表 {@link GoodsInfoBySkuNosResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/list-goods-info-by-sku-nos")
    BaseResponse<GoodsInfoBySkuNosResponse> listGoodsInfoBySkuNos(@RequestBody @Valid GoodsInfoListBySkuNosRequest request);

    /**
     * @description 校验skuNo在库中是否存在
     * @author  EDZ
     * @date 2021/6/18 10:05
     * @return com.wanmi.sbc.common.base.BaseResponse<java.lang.Boolean>
     **/
    @GetMapping("/goods/${application.goods.version}/info/skuNoExist")
    BaseResponse<Boolean> skuNoExist(@RequestParam("skuNo") String skuNo);

    /**
     * 据商品skuNo查询商品sku视图
     * @author  wur
     * @date: 2021/6/8 19:50
     * @param request 根据商品skuNo查询结构 {@link GoodsInfoViewBySkuNoRequest}
     * @return 商品sku视图 {@link GoodsInfoViewBySkuNoResponse }
     **/
    @PostMapping("/goods/${application.goods.version}/info/get-view-by-skuno")
    BaseResponse<GoodsInfoViewBySkuNoResponse> getViewBySkuNo(@RequestBody @Valid GoodsInfoViewBySkuNoRequest request);

    /**
     *
     * 据商品sku id查询库存
     * @author  zgl
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/info/get-stock-by-skuid")
    BaseResponse<Map<String,Long>> getStockByGoodsInfoIds(@RequestBody @Valid GoodsInfoListByIdsRequest request);


    /**
     * @description   查询商品关联的供应商商品
     * @author  wur
     * @date: 2021/9/24 16:50
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/info/get-provider-sku")
    BaseResponse<ProviderGoodsInfoResponse> getProviderSku(@RequestBody @Valid ProviderGoodsInfoRequest request);

    /**
     * 根据skuId获取审核商品sku信息
     * @param goodsInfoViewByIdRequest
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/get-audit-view-by-id")
    BaseResponse<GoodsInfoViewByIdResponse> getAuditViewById(@RequestBody @Valid GoodsInfoViewByIdRequest goodsInfoViewByIdRequest);

    /**
     * 根据id获取商品
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/get-cart-sku_by_ids")
    BaseResponse<List<GoodsInfoCartVO>> getCartGoodsInfoByIds(@RequestBody @Valid InfoForPurchaseRequest request);


    /**
     * 根据id获取商品
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/get-trade-sku_by_ids")
    public BaseResponse<List<GoodsInfoTradeVO>> getTradeGoodsInfoByIds(@RequestBody @Valid GoodsInfoTradeRequest request);

    /**
     * 根据SkuId查询  只查询商品GoodsInfo 不处理任何业务
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/origina-list-by-ids")
    BaseResponse<GoodsInfoListByIdsResponse> originalListByIds(@RequestBody @Valid GoodsInfoListByIdsRequest request);

    /**
     * 根据SkuId获取商品信息
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/get-Original-by-id")
    BaseResponse<GoodsInfoOriginalResponse> getOriginalById(@RequestBody @Valid GoodsInfoByIdRequest request);

    /**
     * 根据SKUId查询Sku信息和商品其他信息
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/sku-other-info-byId")
    BaseResponse<GoodsInfoAndOtherByIdResponse> getSkuAndOtherInfoById(@RequestBody @Valid GoodsInfoByIdRequest request);

    /**
     * 根据SKUId查询Sku信息和商品其他信息
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/info/sku-other-info-byIds")
    BaseResponse<GoodsInfoAndOtherByIdsResponse> getSkuAndOtherInfoByIds(@RequestBody @Valid GoodsInfoByIdsRequest request);

    /**
     * @description   根据SpuId 查询 SkuId
     * @author  wur
     * @date: 2022/8/25 13:57
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/info/skuId-by-spuId")
    BaseResponse<GoodsInfoIdBySpuIdsResponse> getSkuIdBySpuId(@RequestBody @Valid GoodsInfoIdsBySpuIdsRequest request);

    /**
    *
     * @description  查询商品基础信息
     * @author  wur
     * @date: 2022/8/29 16:08
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/info/get-original-by-ids")
    BaseResponse<GoodsInfosOriginalResponse> getOriginalByIds(@RequestBody @Valid GoodsInfoByIdsRequest request);

    /**
     *
     * @description  填充商品信息的营销商品状态
     * @author  qyz
     * @date: 2022/10/09 16:08
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/info/populate-marketing-goods-status")
    BaseResponse<GoodsInfoPopulateStatusResponse> populateMarketingGoodsStatus(@RequestBody GoodsInfoPopulateStatusRequest request);

    /**
     * @description   过滤周期购商品id
     * @author  xuyunpeng
     * @date: 2022/8/25 13:57
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/info/filter-cycle-goodsInfoId")
    BaseResponse<GoodsInfoFilterCycleResponse> filterCycleGoodsInfoId(@RequestBody @Valid GoodsInfoFilterCycleRequest request);

    /**
     * 根据商品stock批量查询商品goodsId
     *
     * @param request 根据商品stock批量查询商品goodsId
     * @return 商品sku列表 {@link GoodsInfoListByIdsResponse}
     */
    @PostMapping("/goods/${application.goods.version}/info/goods-info-by-stock-and-store")
    BaseResponse<GoodsIdByStockResponse> getGoodsIdByStock(@RequestBody @Valid GoodsIdByStockRequest request);

    /**
     * @description 根据卡券ids查询商品
     * @author  xyp
     * @date 2023/5/26 10:05
     * @param {@link GoodsInfoByElectronicCouponIdsRequest}
     * @return {@link GoodsInfoByElectronicCouponIdsResponse}
     **/
    @PostMapping("/goods/${application.goods.version}/info/find-by-electronic-coupon-ids")
    BaseResponse<GoodsInfoByElectronicCouponIdsResponse> findByElectronicCouponIds(@RequestBody @Valid GoodsInfoByElectronicCouponIdsRequest request);
}
