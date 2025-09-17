package com.wanmi.sbc.goods.pointsgoods.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.pointsgoods.model.root.PointsGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>积分商品表DAO</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Repository
public interface PointsGoodsRepository extends JpaRepository<PointsGoods, String>,
        JpaSpecificationExecutor<PointsGoods> {

    /**
     * 单个删除积分商品表
     *
     * @author yang
     */
    @Modifying
    @Query("update PointsGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where pointsGoodsId = ?1")
    int modifyDelFlagById(String pointsGoodsId);

    /**
     * 批量删除积分商品表
     *
     * @author yang
     */
    @Modifying
    @Query("update PointsGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where pointsGoodsId in ?1")
    int deleteByIdList(List<String> pointsGoodsIdList);

    /**
     * 批量删除积分商品表
     *
     * @author yang
     */
    @Modifying
    @Query("update PointsGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsInfoId in ?1 and endTime >= now() ")
    int deleteByGoodInfoIdList(List<String> goodsInfoIdList);

    @Modifying
    @Query("update PointsGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsId in ?1 and endTime >= now() ")
    int deleteByGoodsIdList(List<String> goodsIdList);

    /**
     * 根据积分商品Id减商品活动数量
     *
     * @param stock         活动数量
     * @param pointsGoodsId 积分商品ID
     */
    @Modifying
    @Query("update PointsGoods set stock = stock - ?1, updateTime = now() where pointsGoodsId = ?2 and stock >= ?1")
    int subStockById(Long stock, String pointsGoodsId);

    /**
     * 根据积分商品Id库存清零并停用
     *
     * @param pointsGoodsId 积分商品ID
     */
    @Modifying
    @Query("update PointsGoods set stock = 0, status = com.wanmi.sbc.common.enums.EnableStatus.DISABLE, updateTime = now() where pointsGoodsId = ?1")
    int resetStockById(String pointsGoodsId);

    /**
     * 查询过期积分商品
     *
     * @return
     */
    @Query("select c from PointsGoods c where c.endTime< now() and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.stock != 0")
    List<PointsGoods> queryOverdueList();

    /**
     * 根据店铺id查询积分商品
     *
     * @param storeId
     * @return
     */
    @Query("from PointsGoods c where c.goods.storeId = ?1 and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.status = 1")
    List<PointsGoods> getByStoreId(Long storeId);

    /**
     * 根据店铺id更新积分商品状态
     *
     * @param storeId
     * @param status
     * @return
     */
    @Modifying
    @Query(value = "update points_goods c set c.status = ?2 where c.goods_id in (?1) and c.del_flag = 0",nativeQuery = true)
    void modifyStatusByStoreId(List<String> goodsIds, Integer status);

    /**
     * 根据积分商品Id加销量
     *
     * @param salesNum      商品销量
     * @param pointsGoodsId 积分商品ID
     */
    @Modifying
    @Query("update PointsGoods set sales = sales + ?1, updateTime = now() where pointsGoodsId = ?2")
    int updatePointsGoodsSalesNum(Long salesNum, String pointsGoodsId);


    /**
     * @description 修改结束时间
     * @author  xuyunpeng
     * @date 2021/6/23 2:12 下午
     * @param pointsGoodsId
     * @return
     */
    @Modifying
    @Query("update PointsGoods set endTime = now() where pointsGoodsId = ?1")
    void modifyEndTime(String pointsGoodsId);

    /**
    *
     * 根据goodsInfoId查询积分商品 未开始/进行中
     * @author  wur
     * @date: 2021/6/8 20:37
     * @param goodsInfoId 商品ID
     * @return  积分商品信息
     **/
    @Query("from PointsGoods where goodsInfoId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and endTime >= now()")
    List<PointsGoods> getByGoodsInfoId(String goodsInfoId);

    @Modifying
    @Query("update PointsGoods set cateId = null where cateId = ?1 and delFlag = ?2")
    void updateCateId(Integer cateId, DeleteFlag no);
}
