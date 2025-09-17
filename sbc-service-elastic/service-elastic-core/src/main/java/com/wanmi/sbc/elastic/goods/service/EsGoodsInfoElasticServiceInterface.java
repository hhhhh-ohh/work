package com.wanmi.sbc.elastic.goods.service;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoAdjustPriceRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoEnterpriseAuditRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyDistributionCommissionRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyDistributionGoodsAuditRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyDistributionGoodsStatusRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.response.goods.*;
import com.wanmi.sbc.elastic.base.response.EsSearchResponse;
import com.wanmi.sbc.goods.bean.dto.BatchEnterPrisePriceDTO;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/***
 *
 * @className EsGoodsInfoElasticServiceInterface
 * @author zhengyang
 * @date 2021/9/16 16:24
 **/
public interface EsGoodsInfoElasticServiceInterface {
    /**
     * 分页查询ES商品(实现WEB的商品列表)
     *
     * @param queryRequest
     * @return
     */
    EsGoodsInfoResponse page(EsGoodsInfoQueryRequest queryRequest);

    EsGoodsInfoResponse optimizationPage(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 我的店铺（店铺精选页）
     *
     * @param queryRequest
     * @return
     */
    EsGoodsInfoResponse distributorGoodsListByCustomerId(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 分销员-我的店铺-选品功能
     *
     * @param queryRequest
     * @return
     */
    EsGoodsInfoResponse distributorGoodsList(EsGoodsInfoQueryRequest queryRequest, List<String> goodsIdList);

    /**
     * 根据不同条件查询ES商品信息
     *
     * @param queryRequest
     * @return
     */
    EsSearchResponse getEsBaseInfoByParams(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 提取商品品牌聚合数据
     *
     * @param response
     * @return
     */
    EsGoodsBaseResponse extractBrands(EsSearchResponse response);

    /**
     * 提取商品分类聚合数据
     *
     * @param response
     * @return
     */
    EsGoodsBaseResponse extractGoodsCate(EsSearchResponse response);

    /**
     * 提取商品属性聚合数据
     *
     * @param response
     * @return
     */
    EsGoodsBaseResponse extractGoodsProp(EsSearchResponse response, Long cateId);

    /**
     * 提取规格与规格值聚合数据
     *
     * @param response
     * @return
     */
    EsGoodsBaseResponse extractGoodsSpecsAndSpecDetails(EsSearchResponse response);

    /**
     * 包装排序字段到EsGoodsInfoQueryRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    EsGoodsInfoQueryRequest wrapperSortToEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 包装店铺分类信息到EsGoodsInfoQueryRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    EsGoodsInfoQueryRequest wrapperStoreCateToEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 包装分类商品信息到EsGoodsInfoQueryRequest查询对象中
     *
     * @param queryRequest
     * @return
     */
    EsGoodsInfoQueryRequest wrapperGoodsCateToEsGoodsInfoQueryRequest(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 分页查询ES商品(实现WEB的商品列表)
     *
     * @param queryRequest
     * @return
     */
    EsGoodsResponse pageByGoods(EsGoodsInfoQueryRequest queryRequest);

    //更新与供应商关联的商家商品es
    void initProviderEsGoodsInfo(Long storeId, List<String> providerGoodsIds);

    /**
     * 初始化SKU持化于ES
     */
    void initEsGoodsInfo(EsGoodsInfoRequest request);

    /**
     * 上下架
     *
     * @param addedFlag    上下架状态
     * @param goodsIds     商品id列表
     * @param goodsInfoIds 商品skuId列表
     */
    void updateAddedStatus(Integer addedFlag, List<String> goodsIds, List<String> goodsInfoIds, PluginType pluginType);

    /**
     * 根据商品批量删除
     *
     * @param goodsIds
     */
    void deleteByGoods(List<String> goodsIds);

    /**
     * 批量删除
     *
     * @param skuIds SKU编号
     */
    void delete(List<String> skuIds);

    /**
     * 使用标准分词对字符串分词
     *
     * @param text 待分词文本
     * @return 分此后的词条
     */
    String analyze(String text);

    /**
     * 删除品牌时，更新es数据
     */
    void delBrandIds(List<Long> brandIds, Long storeId);

    /**
     * 删除店铺分类时更新es数据
     *
     * @param storeCateIds
     * @param storeId
     */
    void delStoreCateIds(List<Long> storeCateIds, Long storeId);

    /**
     * 更新分销佣金、分销商品审核状态（添加分销商品时）
     *
     * @param esGoodsInfoDistributionRequest
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    Boolean modifyDistributionCommission(EsGoodsInfoModifyDistributionCommissionRequest esGoodsInfoDistributionRequest);

    /**
     * 更新分销商品审核状态（平台端审核时）
     *
     * @param request
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    Boolean modifyDistributionGoodsAudit(EsGoodsInfoModifyDistributionGoodsAuditRequest request);

    /**
     * 商家-社交分销开关设置，更新分销商品状态
     *
     * @param request
     * @return
     */
    Boolean modifyDistributionGoodsStatus(EsGoodsInfoModifyDistributionGoodsStatusRequest request);

    /**
     * 更新分销商品审核状态（修改商品销售模式：零售->批发）
     *
     * @param spuId
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    Boolean modifyDistributionGoodsAudit(String spuId);

    /**
     * 修改spu和sku的商品分类索引信息
     *
     * @param goodsCateVO
     */
    void updateCateName(GoodsCateVO goodsCateVO);

    /**
     * 修改spu和sku的商品品牌索引信息
     *
     * @param goodsBrandVO
     */
    void updateBrandName(GoodsBrandVO goodsBrandVO);

    /**
     * 新增不需要审核的企业购商品时 刷新es
     *
     * @param batchEnterPrisePriceDTOS
     */
    Boolean updateEnterpriseGoodsInfo(List<BatchEnterPrisePriceDTO> batchEnterPrisePriceDTOS, EnterpriseAuditState enterpriseAuditState);

    /**
     * 更新企业购商品（平台端审核时）
     *
     * @param request
     * @return true:批量更新成功，false:批量更新失败（部分更新成功）
     */
    Boolean modifyEnterpriseAuditStatus(EsGoodsInfoEnterpriseAuditRequest request);

    /**
     * 修改商品销量
     *
     * @param spuId    spuId
     * @param salesNum 销量
     * @return
     */
    Long updateSalesNumBySpuId(String spuId, Long salesNum);

    /**
     * 修改商品排序号
     *
     * @param spuId  spuId
     * @param sortNo 排序号
     * @return
     */
    Long updateSortNoBySpuId(String spuId, Long sortNo);

    /**
     * 修改商品价格
     *
     * @param request 调价参数
     * @return
     */
    void adjustPrice(EsGoodsInfoAdjustPriceRequest request);

    /**
     * 代销商品调价
     *
     * @param supplyPriceMap
     */
    void adjustConsignedGoods(Map<String, BigDecimal> supplyPriceMap);

    /**
     * 查询商品的筛选项
     * @param request
     * @return
     */
    EsGoodsSelectOptionsResponse selectOptions(EsGoodsInfoQueryRequest request);

    /**
     * 查询spu列表，精简字段版
     * @param queryRequest
     * @return
     */
    EsGoodsSimpleResponse spuPage(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 查询sku列表，精简字段版
     * @param queryRequest
     * @return
     */
    EsGoodsInfoSimpleResponse skuPage(EsGoodsInfoQueryRequest queryRequest);

    /**
     * 按照请求参数进行查询，不做任何参数处理
     * @param queryRequest
     * @return
     */
    EsGoodsInfoSimpleResponse skuPageByAllParams(EsGoodsInfoQueryRequest queryRequest);
}
