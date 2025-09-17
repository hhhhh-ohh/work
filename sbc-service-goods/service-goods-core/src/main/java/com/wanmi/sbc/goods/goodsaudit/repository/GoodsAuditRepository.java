package com.wanmi.sbc.goods.goodsaudit.repository;

import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>商品审核DAO</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Repository
public interface GoodsAuditRepository extends JpaRepository<GoodsAudit, String>,
        JpaSpecificationExecutor<GoodsAudit> {

    /**
     * 批量删除商品审核
     * @author 黄昭
     */
    @Modifying
    @Query("update GoodsAudit set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsId in ?1")
    void deleteByIdList(List<String> goodsIdList);

    /**
     * 根据多个商品ID编号更新审核状态
     * @param auditStatus 审核状态
     * @param auditReason 审核原因
     * @param goodsIds 多个商品
     */
    @Modifying
    @Query("update GoodsAudit w set w.auditStatus = ?1,w.deleteReason=null,w.addFalseReason=null, w.auditReason = ?2, w.submitTime = now()  where w.goodsId in ?3")
    void updateAuditDetail(CheckStatus auditStatus, String auditReason, List<String> goodsIds);

    /**
     * 商品禁用直接驳回待审核商品
     * @param oldGoodsIds
     * @param auditStatus
     */
    @Modifying
    @Query("update GoodsAudit w set w.auditStatus = com.wanmi.sbc.goods.bean.enums.CheckStatus.NOT_PASS,w.deleteReason=null,w.addFalseReason=null, w.auditReason = '商品被禁用', w.submitTime = now()  where w.oldGoodsId in ?1 and w.auditStatus = ?2")
    void updateByOldGoodsIdsAndAuditState(List<String> oldGoodsIds, CheckStatus auditStatus);

    /**
     * 根据老商品Id和审核状态查询审核商品
     * @param oldGoodsIds
     * @param auditStatus
     * @return
     */
    @Query("from GoodsAudit w where w.oldGoodsId in ?1 and w.auditStatus = ?2 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<GoodsAudit> findByOldGoodsIdsAndAuditState(List<String> oldGoodsIds, CheckStatus auditStatus);

    /**
     * 根据老商品Id和查询审核商品
     * @param oldGoodsIds
     * @return
     */
    @Query("from GoodsAudit w where w.oldGoodsId in ?1 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<GoodsAudit> findByOldGoodsIds(List<String> oldGoodsIds);

    /**
     * 修改商品运费模板为默认运费模板
     * @param oldFreightTempId
     * @param freightTempId
     */
    @Modifying
    @Query("update GoodsAudit g set g.freightTempId = :freightTempId where g.freightTempId = :oldFreightTempId")
    void updateFreightTempId(@Param("oldFreightTempId") Long oldFreightTempId, @Param("freightTempId") Long freightTempId);

    /**
     * 根据老商品Id和查询审核商品
     * @param goodsIdList
     * @return
     */
    @Query("from GoodsAudit w where w.goodsId in ?1 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<GoodsAudit> findByGoodsIdList(List<String> goodsIdList);

    /**
     * 根据商品Id修改商品是否单规格
     * @param goodsId
     */
    @Modifying
    @Query("update GoodsAudit g set g.singleSpecFlag = :singleSpecFlag where g.goodsId = :goodsId")
    void updateSingleSpecFlagByGoodsAuditId(@Param("goodsId") String goodsId, @Param("singleSpecFlag") Boolean singleSpecFlag);

    /**
     * 根据oldGoodsId修改商品审核的上下架状态
     * @param addedFlag
     * @param oldGoodsId
     */
    @Modifying
    @Query("update GoodsAudit g set g.addedFlag = :addedFlag where g.oldGoodsId = :oldGoodsId and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void updateAddedFlagByOldGoodsId(@Param("addedFlag") Integer addedFlag, @Param("oldGoodsId") String oldGoodsId);
}
