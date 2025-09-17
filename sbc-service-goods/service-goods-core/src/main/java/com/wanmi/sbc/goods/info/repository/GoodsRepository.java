package com.wanmi.sbc.goods.info.repository;


import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.info.model.root.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods, String>, JpaSpecificationExecutor<Goods>{

    /**
     * 根据多个商品ID编号进行删除
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);



    /**
     * 根据多个商品ID编号进行删除供应商商品
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES,w.deleteReason=?2 ,w.updateTime = now() where w.goodsId in ?1")
    void deleteProviderByGoodsIds(List<String> goodsIds,String deleteReason);

    /**
     * 根据多个商品ID编号更新上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedFlag = ?1, w.updateTime = now(),w.addedTimingFlag = ?4, w.takedownTimeFlag = ?5, w.addFalseReason=?3, w.addedTime = now() where w.goodsId in ?2")
    void updateAddedFlagByGoodsIds(Integer addedFlag, List<String> goodsIds,String unAddFlagReason, Boolean addedTimingFlag, Boolean takedownTimeFlag);

    /**
     * 根据多个商品ID编号更新上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedFlag = ?1, w.deleteReason=?4 , w.updateTime = now(),w.addFalseReason=?3, w.addedTime = now() where w.goodsId in ?2")
    void updateAddedFlagByGoodsIdsAddDeleteReason(Integer addedFlag, List<String> goodsIds,String unAddFlagReason,String reason);


    /**
     * 根据多个商品ID编号更新上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedFlag = ?1,w.addedTimingFlag = ?3, w.takedownTimeFlag = ?4, w.updateTime = now(), w.addedTime = now() where w.goodsId in ?2")
    void updateAddedFlagByGoodsIds(Integer addedFlag, List<String> goodsIds, Boolean addedTimingFlag, Boolean takedownTimeFlag);

    /**
     * 根据多个商品ID编号更新定时上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedFlag = ?1, w.addedTimingFlag = ?2, w.addedTimingTime = ?3, w.updateTime = now()" +
            ", w.addedTime = now() where w.goodsId in ?4")
    void updateAddedTimingTimeByGoodsIds(Integer addedFlag, Boolean addedTimingFlag, LocalDateTime addedTimingTime,
                                         List<String> goodsIds);

    /**
     * 根据多个商品ID编号更新定时上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedFlag = ?1, w.takedownTimeFlag = ?2, w.takedownTime = ?3, w.updateTime = now()" +
            ", w.addedTime = now() where w.goodsId in ?4")
    void updateTakedownTimeByGoodsIds(Integer addedFlag, Boolean takedownTimeFlag, LocalDateTime takedownTime,
                                      List<String> goodsIds);

    /**
     * 根据多个商品ID编号更新定时上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.takedownTimeFlag = ?1, w.updateTime = now()" +
            ", w.addedTime = now() where w.goodsId in ?2")
    void updateTakedownTimeByGoodsIds(Boolean takedownTimeFlag, List<String> goodsIds);

    /**
     * 根据多个商品ID编号更新上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedTimingFlag = ?1, w.updateTime = now() where w.goodsId in ?2")
    void updateAddedTimingFlagByGoodsIds(Boolean addedTimingFlag, List<String> goodsIds);


    /**
     * 根据多个分类ID编号更新分类
     * @param newCateId 分类ID
     * @param cateIds 多个分类ID
     */
    @Modifying
    @Query("update Goods w set w.cateId = ?1, w.updateTime = now() where w.cateId in ?2")
    void updateCateByCateIds(Long newCateId, List<Long> cateIds);

    /**
     * 根据多个商品ID编号更新审核状态
     * @param auditStatus 审核状态
     * @param auditReason 审核原因
     * @param goodsIds 多个商品
     */
    @Modifying
    @Query("update Goods w set w.auditStatus = ?1,w.deleteReason=null,w.addFalseReason=null, w.auditReason = ?2, w.submitTime = now()  where w.goodsId in ?3")
    void updateAuditDetail(CheckStatus auditStatus, String auditReason, List<String> goodsIds);

    /**
     * 根据商家id 批量更新商家名称
     * @param supplierName
     * @param storeId
     */
    @Modifying
    @Query(value = "UPDATE goods SET supplier_name=:supplierName WHERE store_id=:storeId", nativeQuery = true)
    void updateSupplierName(@Param("supplierName") String supplierName, @Param("storeId") Long storeId);

    /**
     * 根据店铺id 批量更新供应商名称
     * @param providerName
     * @param providerId
     */
    @Modifying
    @Query(value = "UPDATE goods SET provider_name=:providerName WHERE provider_id=:providerId", nativeQuery = true)
    void updateProviderName(@Param("providerName") String providerName, @Param("providerId") Long providerId);

    /**
     * 根据品牌id 批量把spu品牌置为null
     * @param brandId
     */
    @Modifying
    @Query("update Goods g set g.brandId = null where g.brandId = :brandId")
    void updateBrandByBrandId(@Param("brandId") Long brandId);


    /**
     * 根据店铺id及品牌id列表 批量把spu品牌置为null
     * @param storeId
     * @param brandIds
     */
    @Modifying
    @Query("update Goods g set g.brandId = null where g.storeId = :storeId and g.brandId in (:brandIds)")
    void updateBrandByStoreIdAndBrandIds(@Param("storeId") Long storeId, @Param("brandIds") List<Long> brandIds);

    /**
     * 根据类别id查询SPU
     * @param cateId
     * @return
     */
    @Query
    List<Goods> findAllByCateId(Long cateId);

    /**
     * 根据多个商品ID编号编辑运费模板
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.freightTempId = ?1, w.updateTime = now() where w.goodsId in ?2")
    void updateFreightTempIdByGoodsIds(Long freightTempId, List<String> goodsIds);

    /**
     * 修改商品运费模板为默认运费模板
     * @param oldFreightTempId
     * @param freightTempId
     */
    @Modifying
    @Query("update Goods g set g.freightTempId = :freightTempId where g.freightTempId = :oldFreightTempId")
    void updateFreightTempId(@Param("oldFreightTempId") Long oldFreightTempId, @Param("freightTempId") Long freightTempId);

    /**
     * @Author lvzhenwei
     * @Description 更新商品评论数
     * @Date 14:31 2019/4/11
     * @Param [goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.goodsEvaluateNum = g.goodsEvaluateNum+1, g.updateTime = now() where g.goodsId = ?1")
    void updateGoodsEvaluateNum(@Param("goodsId") String goodsId);

    /**
     * @Author lvzhenwei
     * @Description 更新商品收藏量
     * @Date 14:43 2019/4/11
     * @Param [goodsCollectNum, GoodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.goodsCollectNum = g.goodsCollectNum + ?1, g.updateTime = now()  where g.goodsId = ?2")
    void updateGoodsCollectNum(@Param("goodsCollectNum") Long goodsCollectNum,@Param("goodsId") String GoodsId);

    /**
     * @Author lvzhenwei
     * @Description 更新商品销量
     * @Date 14:43 2019/4/11
     * @Param [goodsSalesNum, goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.goodsSalesNum = g.goodsSalesNum + ?1, g.updateTime = now()  where g.goodsId = ?2")
    void updateGoodsSalesNum(@Param("goodsSalesNum") Long goodsSalesNum,@Param("goodsId") String goodsId);

    /**
     * @Author lvzhenwei
     * @Description 更新商品好评数量
     * @Date 14:50 2019/4/11
     * @Param [goodsPositiveFeedback, goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.goodsFavorableCommentNum = g.goodsFavorableCommentNum + ?1, g.updateTime = now()  where g.goodsId = ?2")
    void updateGoodsFavorableCommentNum(@Param("goodsPositiveFeedback") Long goodsFavorableCommentNum,@Param("goodsId") String goodsId);

    /**
     * @Description 更新商品注水销量
     * @Date 14:43 2019/4/11
     * @Param [shamSalesNum, goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.shamSalesNum = :shamSalesNum, g.updateTime = now()  where g.goodsId = :goodsId")
    void updateShamGoodsSalesNum(@Param("shamSalesNum") Long shamSalesNum,@Param("goodsId") String goodsId);

    /**
     * @Description 更新商品排序号
     * @Date 14:43 2019/4/11
     * @Param [shamSalesNum, goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.sortNo = :sortNo, g.updateTime = now()  where g.goodsId = :goodsId")
    void updateSortNo(@Param("sortNo") Long sortNo,@Param("goodsId") String goodsId);

    /**
     * 根据供应商商品id查询关联商品
     * @param providerGoodsId
     * @return
     */
    @Query
    List<Goods> findAllByProviderGoodsId(String providerGoodsId);

    /**
     * 根据商品id查询商品信息
     * @param goodsIds
     * @return
     */
    @Query
    List<Goods> findAllByGoodsIdIn(List<String> goodsIds);

    /**
     * 根据商品SPU编号更新库存
     * @param stock 库存数
     */
    @Modifying
    @Query("update Goods w set w.stock = w.stock + ?1 where w.goodsId = ?2 ")
    void addStockById(Long stock, String goodsId);

    /**
     * 更新代销商品的可售性
     * @param stock
     * @param providerGoodsIds
     */
    @Modifying
    @Query("update Goods set stock=?1 where providerGoodsId in ?2")
    void updateStockByProviderGoodsIds(Long stock, List<String> providerGoodsIds);

    @Modifying
    @Query(value = "UPDATE goods SET cate_id=:cateId WHERE goods_source=:source AND third_cate_id=:thirdCateId",nativeQuery = true)
    void updateThirdCateMap(@Param("source") int source, @Param("thirdCateId") long thirdCateId, @Param("cateId") long cateId);

    Goods findByDelFlagAndGoodsSourceAndThirdPlatformSpuId(DeleteFlag deleteFlag, Integer goodsSource, String thirdPlatformSpuId);

    @Modifying
    @Query("update Goods set vendibility=?1 where goodsSource=1 and thirdPlatformType=?2")
    void vendibilityLinkedmallGoods(Integer vendibility,ThirdPlatformType thirdPlatformType);

    /**
     * 更新代销商品的可售性
     * @param vendibility
     * @param goodsIds
     */
    @Modifying
    @Query("update Goods set vendibility=?1 where providerGoodsId in ?2")
    void updateGoodsVendibility(Integer vendibility, List<String> goodsIds);

    /**
     * 更新供应商店铺状态
     * @param providerStatus
     * @param storeIds
     */
    @Modifying
    @Query("update Goods  set providerStatus = ?1 where providerId in ?2")
    void updateProviderStatus(Integer providerStatus, List<Long> storeIds);


    /**
    * @discription 根据goodsid 查询图文信息
    * @author yangzhen
    * @date 2020/9/3 11:21
    * @param goodsId
    * @return
    */
    @Query("select goodsDetail  from Goods  where goodsId = ?1")
    String getGoodsDetail( String goodsId);

    /**
     * @Author lvzhenwei
     * @Description 增加商品评论数
     * @Date 14:31 2019/4/11
     * @Param [goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.goodsEvaluateNum = g.goodsEvaluateNum + 1, g.updateTime = now() where g.goodsId = ?1 ")
    void increaseGoodsEvaluateNum(@Param("goodsId") String goodsId);

    /**
     * @Author lvzhenwei
     * @Description 减少商品评论数
     * @Date 14:31 2019/4/11
     * @Param [goodsId]
     * @return void
     **/
    @Modifying
    @Query("update Goods g set g.goodsEvaluateNum = g.goodsEvaluateNum - 1, g.updateTime = now() where g.goodsId = ?1 and g.goodsEvaluateNum > 0 ")
    void decreaseGoodsEvaluateNum(@Param("goodsId") String goodsId);

    /**
     * 查询商品
     * @param goodsId
     * @param storeId
     * @param deleteFlag
     * @return
     */
    List<Goods> findByGoodsIdInAndStoreIdAndDelFlag(List<String> goodsIds, Long storeId, DeleteFlag deleteFlag);

    /**
     * 根据多个商品ID编号更新上下架状态
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update Goods w set w.addedFlag = ?1, w.updateTime = now(), w.addedTime = now() where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.addedFlag = 1 and w.storeId in ?2")
    void updateAddedFlagByStoreIds(Integer addedFlag, List<Long> storeIds);

    @Query("select distinct g.goodsId from  Goods g where g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and g.thirdCateId in ?1 and g.goodsSource=?2")
    List<String> findAllByThirdCateIdInAndGoodsSource(List<Long> thirdCateIds, int goodsSource);


    /**
     * 修改商品独立设置加价比例开关
     * @author  wur
     * @date: 2021/6/9 9:01
     * @param isIndependent  是否打开
     * @param goodsId  货品ID
     **/
    @Modifying
    @Query("update Goods w set w.isIndependent = ?1, w.updateTime = now() where w.goodsId = ?2")
    void updateIsIndependent(EnableStatus isIndependent, String goodsId);

    /**
     * 修改商品多规格
     * @param goodsId
     * @param zero
     */
    @Modifying
    @Query("update Goods w set w.moreSpecFlag = ?2, w.updateTime = now() where w.goodsId = ?1")
    void updateMoreSpecFlagByGoodsId(String goodsId, int zero);

    /**
     * 修改商品库存
     * @param goodsId 商品spuId
     * @param stock 库存
     */
    @Modifying
    @Query("update Goods w set w.stock = ?2, w.updateTime = now() where w.goodsId = ?1")
    void updateStockByGoodsId(String goodsId, long stock);

    /**
     * 验证商家是否代销指定商品
     * @param storeId
     * @param providerGoodsId
     * @return
     */
    @Query("from Goods g where g.storeId = ?1 and g.providerGoodsId = ?2 and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<Goods> findStoreIdAndDelFlag(Long storeId, String providerGoodsId);

    /**
     * 查询代销商品的Id
     * @param providerGoodsIds
     * @return
     */
    @Query("select goodsId from Goods where providerGoodsId in ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findOtherGoodsInfoByGoodsInfoIds(List<String> providerGoodsIds);

    /**
     * 根据店铺id查询providerId
     * @param storeId
     * @return
     */
    @Query("select distinct providerId from Goods where storeId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and addedFlag <> 0 and providerId is not null")
    List<Long> queryProviderIds(Long storeId);


    /**
     * 根据商品ID编号变更周期购标识
     * @param goodsId 商品ID
     */
    @Modifying
    @Query("update Goods w set w.isBuyCycle = ?1, w.updateTime = now() where w.goodsId = ?2")
    void modifyIsBuyCycleById(Integer isBuyCycle,String goodsId);

    @Query("select goodsName from Goods where goodsId in ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findGoodsNameByIds (List<String> joinGoodsIdList) ;
}
