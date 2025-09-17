package com.wanmi.sbc.goods.info.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoLiveGoods;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoParams;
import com.wanmi.sbc.goods.info.model.entity.GoodsMarketingPrice;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * SKU数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, String>, JpaSpecificationExecutor<GoodsInfo> {

    /**
     * 根据spuIdList查询sku
     *
     * @param goodsIdList
     */
    List<GoodsInfo> findByGoodsIdIn(List<String> goodsIdList);

    Optional<GoodsInfo> findByGoodsInfoIdAndStoreIdAndDelFlag(String goodsInfoId, Long storeId, DeleteFlag deleteFlag);

    /**
     * 根据spuIdList查询sku(不包含已删除的)
     *
     * @param goodsIdList
     */
    @Query("from GoodsInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsId in ?1")
    List<GoodsInfo> findByGoodsIds(List<String> goodsIdList);

    @Query("from GoodsInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsId = ?1")
    List<GoodsInfo> findByGoodsId(String goodsId);

    /**
     * 根据第三方SKUId查询
     * @param thirdPlatformSkuIdList
     * @return
     */
    @Query("from GoodsInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.thirdPlatformSkuId in ?1 and w.goodsSource = ?2")
    List<GoodsInfo> findByThirdPlatformSkuIds(List<String> thirdPlatformSkuIdList, Integer goodsSource);

    /**
     * 根据spuIdList查询sku(不包含已删除的)
     *
     * @param goodsIdList
     */
    @Query("from GoodsInfo w where w.goodsId in ?1")
    List<GoodsInfo> findByGoodsIdsAndDelFlag(List<String> goodsIdList);



    /**
     * 根据供应商id查询sku信息
     *
     * @param providerGoodsInfoId
     */
    @Query("from GoodsInfo w where w.providerGoodsInfoId in ?1 and w.goodsId in ?2 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    GoodsInfo findByGoodsInfoByProviderId(String providerGoodsInfoId, String goodsId);


    /**
     * 根据skuIdList查询sku(包含已删除的)
     *
     * @param goodsInfoIdList
     */
    @Query("from GoodsInfo w where w.goodsInfoId in ?1")
    List<GoodsInfo> findByGoodsInfoIds(List<String> goodsInfoIdList);

    @Query("from GoodsInfo where goodsInfoId in ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND addedFlag = 1 AND auditStatus = com.wanmi.sbc.goods.bean.enums.CheckStatus.CHECKED AND (goodsSource = 1 OR (goodsSource <>1 AND vendibility = 1))")
    List<GoodsInfo> findValidByGoodsInfoIds(List<String> goodsInfoIdList);

    /***
     * 统计单个SPU下上下架SKU数量
     * @param goodsId SPUID
     * @return
     */
    @Query(value = "select count(1),sum(case when added_flag = '1' then 1 else 0 end)," +
            "sum(case when added_flag <> '1' then 1 else 0 end) " +
            "from goods_info where goods_id = :goodsId",nativeQuery = true)
    Object countAddedFlagCountByGoodsId(String goodsId);

    /**
     * 根据多个商品ID编号进行删除
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);

    /**
     * 根据商品ID编号进行删除
     *
     * @param goodsId 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId = ?1")
    void updateDeleteFlagByGoodsId(String goodsId);

    /**
     * 根据多个商品skuId进行删除
     *
     * @param goodsInfoIds 商品skuId列表
     */
    @Modifying
    @Query("update GoodsInfo w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsInfoId in ?1")
    void deleteByGoodsInfoIds(List<String> goodsInfoIds);

    /**
     * 根据多个商品skuId进行状态更新
     *
     * @param goodsInfoId 商品skuId列表
     */
    @Modifying
    @Query("update GoodsInfo w set w.delFlag = ?1 ,w.updateTime = now() where w.goodsInfoId in ?2")
    void updateByGoodsInfoIds(DeleteFlag delFlag,List<String> goodsInfoId);


    /**
     * 更新商品的图片和条形码
     *
     */
    @Modifying
    @Query("update GoodsInfo w set w.goodsInfoBarcode = ?1 ,w.goodsInfoImg = ?2 , w.updateTime = now() where w.goodsInfoId in ?3")
    void updateBarImgByIds(String goodsInfoBarcode,String goodsInfoImg, List<String> goodsInfoId);

    /**
     * 根据多个商品ID编号更新上下架状态
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1,w.addedTimingFlag = ?3, w.takedownTimeFlag = ?4, w.updateTime = now(), w.addedTime = now() where w.goodsId in ?2")
    void updateAddedFlagByGoodsIds(Integer addedFlag, List<String> goodsIds, Boolean addedTimingFlag, Boolean takedownTimeFlag);

    /**
     * 根据skuId更新上下架状态
     *
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1, w.updateTime = now() where w.goodsInfoId = ?2 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void updateAddedFlagByGoodsInfoId(Integer addedFlag, String goodsInfoId);

    /**
     * 根据spuId获取原始SKU数据
     *
     */
    @Query("from GoodsInfo w where w.goodsId = ?1 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.oldGoodsInfoId IS NULL")
    List<GoodsInfo> findOriginalGoodsInfos(String originalGoodsId);

    /**
     * 根据多个商品ID编号更新定时上下架状态
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1, w.addedTimingFlag = ?2, w.addedTimingTime = ?3, w.updateTime = " +
            "now(), w.addedTime = now() where w.goodsId in ?4")
    void updateAddedTimingTimeByGoodsIds(Integer addedFlag, Boolean addedTimingFlag, LocalDateTime addedTimingTime,
                                         List<String> goodsIds);

    /**
     * 根据多个商品ID编号更新定时上下架状态
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1, w.takedownTimeFlag = ?2, w.takedownTime = ?3, w.updateTime = " +
            "now(), w.addedTime = now() where w.goodsId in ?4")
    void updateTakedownTimeByGoodsIds(Integer addedFlag, Boolean takedownTimeFlag, LocalDateTime takedownTime, List<String> goodsIds);


    /**
     * 根据多个商品ID编号更新定时上下架状态
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.takedownTimeFlag = ?1, w.updateTime = " +
            "now(), w.addedTime = now() where w.goodsId in ?2")
    void updateTakedownTimeByGoodsIds(Boolean takedownTimeFlag, List<String> goodsIds);

    /**
     * 根据多个商品ID编号更新上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedTimingFlag = ?1, w.updateTime = now() where w.goodsId in ?2")
    void updateAddedTimingFlagByGoodsIds(Boolean addedTimingFlag, List<String> goodsIds);

    /**
     * 根据多个商品skuId更新上下架状态
     *
     * @param addedFlag    上下架状态 为上架
     * @param goodsInfoIds 商品skuId列表
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1, w.addedTimingFlag = ?3, w.updateTime = now(), w.addedTime = now() where w.goodsInfoId in ?2")
    void updateAddedFlagByGoodsInfoIds(Integer addedFlag, List<String> goodsInfoIds, Boolean addedTimingFlag);

    /**
     * 根据多个商品skuId更新上下架状态
     *
     * @param addedFlag    上下架状态 为下架
     * @param goodsInfoIds 商品skuId列表
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1, w.takedownTimeFlag = ?3, w.updateTime = now(), w.addedTime = now() where w.goodsInfoId in ?2")
    void updateAddedFlagAndTakedownTimeFlagByGoodsInfoIds(Integer addedFlag, List<String> goodsInfoIds, Boolean takedownTimeFlag);


    /**
     * 根据商品SKU编号加库存
     *
     * @param stock       库存数
     * @param goodsInfoId 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.stock = w.stock + ?1, w.updateTime = now() where w.goodsInfoId = ?2")
    int addStockById(Long stock, String goodsInfoId);

    /**
     * 根据商品SKU编号减库存
     *
     * @param stock       库存数
     * @param goodsInfoId 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.stock = w.stock - ?1, w.updateTime = now() where w.goodsInfoId = ?2 and w.stock  >= ?1")
    int subStockById(Long stock, String goodsInfoId);

    /**
     * 根据商品SKU编号减库存
     *
     * @param stock       库存数
     * @param goodsInfoId 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.stock = ?1, w.updateTime = now() where w.goodsInfoId = ?2")
    int updateStockById(Long stock, String goodsInfoId);

    /**
     * 根据多个Sku编号更新审核状态
     *
     * @param auditStatus 审核状态
     * @param goodsIds    多个商品
     */
    @Modifying
    @Query("update GoodsInfo w set w.auditStatus = ?1  where w.goodsId in ?2")
    void updateAuditDetail(CheckStatus auditStatus, List<String> goodsIds);


    @Modifying
    @Query("update GoodsInfo g set g.smallProgramCode = ?2 where g.goodsInfoId = ?1 ")
    void updateSkuSmallProgram(String goodsInfoId, String codeUrl);

    @Modifying
    @Query("update GoodsInfo g set g.smallProgramCode = null ")
    void clearSkuSmallProgramCode();

    /**
     * 根据品牌id 批量把sku品牌置为null
     *
     * @param brandId
     */
    @Modifying
    @Query("update GoodsInfo g set g.brandId = null where g.brandId = :brandId")
    void updateSKUBrandByBrandId(@Param("brandId") Long brandId);

    /**
     * 根据店铺id及品牌id列表 批量把sku品牌置为null
     *
     * @param storeId
     * @param brandIds
     */
    @Modifying
    @Query("update GoodsInfo g set g.brandId = null where g.storeId = :storeId and g.brandId in (:brandIds)")
    void updateBrandByStoreIdAndBrandIds(@Param("storeId") Long storeId, @Param("brandIds") List<Long> brandIds);

    /**
     * 根据多个分类ID编号更新sku关联分类
     *
     * @param newCateId 分类ID
     * @param cateIds   多个分类ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.cateId = ?1, w.updateTime = now() where w.cateId in ?2")
    void updateSKUCateByCateIds(Long newCateId, List<Long> cateIds);

    /**
     * 分销商品审核通过(单个)
     *
     * @param goodsInfoId
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.distributionGoodsAudit = com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit.CHECKED, w.updateTime = now() where w.goodsInfoId = ?1")
    int checkDistributionGoods(String goodsInfoId);

    /**
     * 批量审核分销商品
     *
     * @param goodsInfoIds
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.distributionGoodsAudit = com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit.CHECKED, w.updateTime = now() where w.goodsInfoId in ?1")
    int batchCheckDistributionGoods(List<String> goodsInfoIds);

    /**
     * 驳回或禁止分销商品
     *
     * @param goodsInfoId
     * @param distributionGoodsAudit
     * @param distributionGoodsAuditReason
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.distributionGoodsAudit = ?2, w.distributionGoodsAuditReason = ?3, w.updateTime =" +
            " now() where w.goodsInfoId = ?1")
    int refuseCheckDistributionGoods(String goodsInfoId, DistributionGoodsAudit distributionGoodsAudit,
                                     String distributionGoodsAuditReason);

    /**
     * 删除分销商品
     *
     * @param goodsInfoId
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.distributionGoodsAudit = com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit.COMMON_GOODS, w.updateTime = now() where w.goodsInfoId = ?1")
    int delDistributionGoods(String goodsInfoId);

    /**
     * 编辑分销商品，修改佣金比例和状态
     *
     * @param goodsInfoId
     * @param
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.commissionRate = ?2,w.distributionCommission = ?3, w.distributionGoodsAudit = ?4, w" +
            ".updateTime = now() where w.goodsInfoId = ?1")
    int modifyCommissionDistributionGoods(String goodsInfoId, BigDecimal commissionRate, BigDecimal
            distributionCommission, DistributionGoodsAudit distributionGoodsAudit);

    /**
     * 添加分销商品，修改佣金比例和状态
     *
     * @param goodsInfoId
     * @param
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.commissionRate = ?2,w.distributionCommission = ?3, w.distributionGoodsAudit = ?4, w" +
            ".updateTime = now(), w.distributionCreateTime = now() where w.goodsInfoId = ?1")
    int addCommissionDistributionGoods(String goodsInfoId, BigDecimal commissionRate, BigDecimal
            distributionCommission, DistributionGoodsAudit distributionGoodsAudit);

    /*
     * @Description: 商品ID<spu> 修改商品审核状态
     * @Author: Bob
     * @Date: 2019-03-11 16:28
     */
    @Modifying
    @Query("update GoodsInfo w set w.distributionGoodsAudit = :distributionGoodsAudit, w.updateTime = now() where w.goodsId = :goodsId")
    int modifyDistributeState(@Param("goodsId") String goodsId, @Param("distributionGoodsAudit") DistributionGoodsAudit distributionGoodsAudit);


    /**
     * 添加分销商品前，验证所添加的sku是否符合条件
     * 条件：商品是否有效状态（商品已审核通过且未删除和上架状态）以及是否是零售商品
     *
     * @param goodsInfoIds
     * @return
     */
    @Query(value = "select info.goods_info_id " +
            "from goods_info info " +
            "left join goods g on info.goods_id = g.goods_id " +
            "where info.goods_info_id in (:goodsInfoIds) " +
            "and (info.distribution_goods_audit != 0 " +
            "or info.del_flag = 1 " +
            "or g.sale_type = 0 " +
            "or info.audit_status != 1)", nativeQuery = true)
    List<Object> getInvalidGoodsInfoByGoodsInfoIds(@Param("goodsInfoIds") List<String> goodsInfoIds);


    /**
     * 添加企业购商品前，验证所添加的sku是否符合条件
     * 条件：商品是否有效状态（商品已审核通过且未删除和上架状态) 以及是否是零售商品
     *
     * @param goodsInfoIds
     * @return
     */
    @Query(value = "select info.goods_info_id " +
            "from goods_info info " +
            "left join goods g on info.goods_id = g.goods_id " +
            "where info.goods_info_id in (:goodsInfoIds) " +
            "and (info.enterprise_goods_audit > 0 " +
            "or info.added_flag = 0 " +
            "or info.del_flag = 1 " +
            "or g.sale_type = 0 " +
            "or info.audit_status != 1)", nativeQuery = true)
    List<Object> getInvalidEnterpriseByGoodsInfoIds(@Param("goodsInfoIds") List<String> goodsInfoIds);


    /**
     * 根据单品ids，查询商品名称、市场价
     *
     * @param goodsInfoIds 单品ids
     * @return
     */
    @Query(value = "select new com.wanmi.sbc.goods.info.model.entity.GoodsInfoParams(g.goodsInfoId, g.goodsInfoNo, g.goodsInfoName,g.marketPrice, g.goodsType) " +
            " from GoodsInfo g where g.goodsInfoId in (?1)")
    List<GoodsInfoParams> findGoodsInfoParamsByIds(List<String> goodsInfoIds);


    /**
     * 查询必须实时的商品字段
     *
     * @param goodsInfoIds
     * @return
     */
    @Query(value = "select info.goods_info_id,info.del_flag,info.added_flag,info.vendibility,info.audit_status,info.market_price,info.supply_price,info.buy_point,info.goods_id,info.brand_id,info.goods_info_name from goods_info info where info.goods_info_id in (:goodsInfoIds)", nativeQuery = true)
    List<Object> findGoodsInfoPartColsByIds(@Param("goodsInfoIds") List<String> goodsInfoIds);

    /**
     * 修改商品的企业价格,并更新企业商品审核的状态
     *
     * @param goodsInfoId
     * @param enterPrisePrice
     * @param enterPriseAuditState
     * @return
     */
    @Modifying
    @Query(value = "update GoodsInfo gi set gi.enterPrisePrice = :enterPrisePrice ,gi.enterPriseAuditState = :enterPriseAuditState, " +
            "gi.updateTime = now() where gi.goodsInfoId = :goodsInfoId and gi.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int updateGoodsInfoEnterPrisePrice(@Param("goodsInfoId") String goodsInfoId,
                                       @Param("enterPrisePrice") BigDecimal enterPrisePrice,
                                       @Param("enterPriseAuditState") EnterpriseAuditState enterPriseAuditState);

    /**
     * 批量审核企业购商品 - 审核通过
     *
     * @param goodsInfoIds
     * @param enterPriseAuditState
     * @return
     */
    @Modifying
    @Query(value = "update GoodsInfo gi set gi.enterPriseAuditState = :enterPriseAuditState,gi.updateTime = now() where gi.goodsInfoId in :goodsInfoIds " +
            "and gi.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int batchAuditEnterprise(@Param("goodsInfoIds") List<String> goodsInfoIds,
                             @Param("enterPriseAuditState") EnterpriseAuditState enterPriseAuditState);

    /**
     * 批量审核企业购商品 - 被驳回
     *
     * @param goodsInfoIds
     * @param enterPriseAuditState
     * @return
     */
    @Modifying
    @Query(value = "update GoodsInfo gi set gi.enterPriseAuditState = :enterPriseAuditState,gi.enterPriseGoodsAuditReason = :enterPriseGoodsAuditReason, " +
            "gi.updateTime = now() where gi.goodsInfoId in :goodsInfoIds and gi.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int batchRejectAuditEnterprise(@Param("goodsInfoIds") List<String> goodsInfoIds,
                                   @Param("enterPriseAuditState") EnterpriseAuditState enterPriseAuditState,
                                   @Param("enterPriseGoodsAuditReason") String enterPriseGoodsAuditReason);

    /**
     * 根据供应商商品详情找到商品详情
     *
     * @param goodsInfoId
     */
    @Query
    List<GoodsInfo> findByProviderGoodsInfoId(String goodsInfoId);

    /**
     * 根据供应商商品infoId找到
     *
     * @param delInfoIds
     * @return
     */
    List<GoodsInfo> findByProviderGoodsInfoIdInAndDelFlag(List<String> delInfoIds, DeleteFlag delFlag);

    @Modifying
    @Query(value = "UPDATE goods_info SET cate_id=:cateId WHERE goods_source=:source AND third_cate_id=:thirdCateId", nativeQuery = true)
    void updateThirdCateMap(@Param("source") int source, @Param("thirdCateId") long thirdCateId, @Param("cateId") long cateId);

    /**
     * 根据sku编号、是否删除查询商品信息
     *
     * @param goodsInfoId
     * @param deleteFlag
     * @return
     */
    GoodsInfo findByGoodsInfoIdAndDelFlag(String goodsInfoId, DeleteFlag deleteFlag);

    @Modifying
    @Query("update GoodsInfo set vendibility=?1 where goodsSource=1 and thirdPlatformType=?2")
    void vendibilityLinkedmallGoodsInfos(Integer vendibility, ThirdPlatformType thirdPlatformType);

    @Modifying
    @Query("update GoodsInfo set vendibility=?1 where providerGoodsInfoId in ?2")
    void updateGoodsInfoVendibility(Integer vendibility, List<String> goodsInfoIds);


    @Query("select goodsInfoId from GoodsInfo where goodsId in ?1")
    List<String> findGoodsInfoIdByGoodsId(List<String> goodsIds);

    /**
     * 更新供应商店铺状态
     *
     * @param providerStatus
     * @param storeIds
     */
    @Modifying
    @Query("update GoodsInfo  set providerStatus = ?1 where providerId in ?2")
    void updateProviderStatus(Integer providerStatus, List<Long> storeIds);


    /**
     * @param
     * @return
     * @discription 查询storeId
     * @author yangzhen
     * @date 2020/9/2 20:35
     */
    @Query(value = "select storeId from GoodsInfo where goodsInfoId=?1")
    Long queryStoreId(String skuId);

    /**
     * 根据单品ids，SPU、库存、规格值
     *
     * @param goodsInfoIds 单品ids
     * @return
     */
    @Query(value = "select new com.wanmi.sbc.goods.info.model.entity.GoodsInfoLiveGoods(g.goodsInfoId, g.goodsId, g.stock) " +
            " from GoodsInfo g where g.goodsInfoId in ?1 and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    List<GoodsInfoLiveGoods> findGoodsInfoLiveGoodsByIds(List<String> goodsInfoIds);

    @Query(value = "SELECT new com.wanmi.sbc.goods.info.model.entity.GoodsMarketingPrice(g.goodsInfoId,g.goodsInfoNo," +
            "g.goodsInfoName,g.goodsId,g.saleType,g.marketPrice,g.supplyPrice, g.providerGoodsInfoId, g.goodsType) FROM GoodsInfo g " +
            "WHERE g.goodsInfoNo in ?1 and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and g.storeId = ?2 and g.oldGoodsInfoId is null")
    List<GoodsMarketingPrice> marketingPriceByNos(List<String> goodsInfoNos, Long storeId);

    @Modifying
    @Query("update GoodsInfo g set g.supplyPrice = ?2 where g.providerGoodsInfoId = ?1")
    void updateSupplyPriceByProviderGoodsInfoId(String goodsInfoId, BigDecimal supplyPrice);

    @Query("select goodsInfoId from GoodsInfo where goodsId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsInfoId not in ?2")
    List<String> findOtherGoodsInfoByGoodsInfoIds(String goodsId, List<String> goodsInfoIds);

    /**
     * 根据多个商品skuId更新上下架状态
     *
     * @param addedFlag    上下架状态
     * @param storeIds 商品skuId列表
     */
    @Modifying
    @Query("update GoodsInfo w set w.addedFlag = ?1, w.updateTime = now(), w.addedTime = now() where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.addedFlag = 1 and w.storeId in ?2")
    void updateAddedFlagByStoreIds(Integer addedFlag, List<Long> storeIds);

    @Query(value = "select 1 from goods_info where goods_info_no=?1 and del_flag=0 limit 1", nativeQuery = true)
    Integer skuNoExist(String skuNo);


    /**
     * 根据skuNo查询商品信息
     * @author  wur
     * @date: 2021/6/8 20:06
     * @param goodsInfoNo SKU码
     * @return 商品详情
     **/
    @Query("from GoodsInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsInfoNo = ?1")
    GoodsInfo findByGoodsInfoNo(String goodsInfoNo);

    /**
     * 根据skuNo查询商品信息
     * @author  wur
     * @date: 2021/6/8 20:06
     * @param goodsInfoNo SKU码
     * @return 商品详情
     **/
    @Query("from GoodsInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsInfoNo in ?1")
    List<GoodsInfo> findByGoodsInfoNoList(List<String> goodsInfoNo);

    /**
     * 根据skuNo查询商品信息
     * @author  wur
     * @date: 2021/6/8 20:06
     * @param goodsInfoNo SKU码
     * @return 商品详情
     **/
    @Query("from GoodsInfo w where w.goodsInfoNo in ?1")
    List<GoodsInfo> findByGoodsInfoNoListDel(List<String> goodsInfoNo);

    /**
     * 根据商品SKU编号更新库存
     * @param stock  库存
     * @param goodsInfoId  商品ID
     * @return
     */
    @Modifying
    @Query("update GoodsInfo w set w.stock = ?1, w.updateTime = now() where w.goodsInfoId = ?2")
    int modifyStockById(Long stock, String goodsInfoId);

    /**
     * 根据商品类型，电子卡券id，删除标识查询Sku
     * @param goodsType
     * @param electronicCouponsId
     * @param deleteFlag
     * @return
     */
    @Query
    Optional<GoodsInfo> findByGoodsTypeAndElectronicCouponsIdAndDelFlagAndOldGoodsInfoId(Integer goodsType,Long electronicCouponsId,DeleteFlag deleteFlag,String oldGoodsInfoId);

    /**
     * 根据商品类型，删除标识查询SKU
     * @param goodsType
     * @param deleteFlag
     * @return
     */
    @Query("from GoodsInfo w where w.goodsType = ?1 and w.delFlag = ?2")
    Page<GoodsInfo> findAllByGoodsTypeAndDelFlag(Integer goodsType, DeleteFlag deleteFlag,Pageable pageable);

    @Query("select electronicCouponsId from GoodsInfo where goodsId in ?1 and goodsType = 2 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<Long> findElectronicIdsByGoodsIds(List<String> goodsId);

    /**
     * 根据销量查询skuId
     * @param goodsInfoIdList
     * @param minGoodsSalesNum
     * @param maxGoodsSalesNum
     * @return
     */
    @Query(value = "SELECT " +
            "goods_info_id " +
            "FROM goods_info JOIN goods " +
            "ON goods_info.goods_id = goods.goods_id " +
            "WHERE " +
            "goods_info.goods_info_id IN :goodsInfoIdList " +
            "AND if(:minGoodsSalesNum is NULL,1=1,goods.goods_sales_num >= :minGoodsSalesNum) " +
            "AND if(:maxGoodsSalesNum is NULL,1=1,goods.goods_sales_num <= :maxGoodsSalesNum) ",nativeQuery = true)
    List<String> findGoodsInfoIdList(@Param("goodsInfoIdList") List<String> goodsInfoIdList,
                                     @Param("minGoodsSalesNum") Long minGoodsSalesNum,
                                     @Param("maxGoodsSalesNum") Long maxGoodsSalesNum);


    /**
     * 根据商品ID编号变更周期购标识
     * @param goodsInfoIds 商品ID
     */
    @Modifying
    @Query("update GoodsInfo w set w.isBuyCycle = ?1, w.updateTime = now() where w.goodsInfoId in ?2")
    void modifyIsBuyCycleByIds(Integer isBuyCycle, List<String> goodsInfoIds);


    @Query("select goodsInfoId from GoodsInfo where goodsInfoId in ?1 and isBuyCycle = 0")
    List<String> filterCycleGoods(List<String> ids);

    @Query("select goodsId from GoodsInfo where stock < ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and storeId = ?2")
    List<String> getGoodsIdByStock (Long warningStock,Long storeId);

    /**
     * 根据卡券id查询商品
     * @param electronicCouponIds
     * @return
     */
    @Query("from GoodsInfo where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and electronicCouponsId in ?1")
    List<GoodsInfo> findByElectronicCouponIds(List<Long> electronicCouponIds);

}
