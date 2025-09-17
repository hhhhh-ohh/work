package com.wanmi.sbc.goods.goodstemplate.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplateRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>GoodsTemplateRelDAO</p>
 * @author 黄昭
 * @date 2022-10-09 10:59:41
 */
@Repository
public interface GoodsTemplateRelRepository extends JpaRepository<GoodsTemplateRel, Long>,
        JpaSpecificationExecutor<GoodsTemplateRel> {

    /**
     * 单个删除GoodsTemplateRel
     * @author 黄昭
     */
    @Modifying
    @Query("update GoodsTemplateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除GoodsTemplateRel
     * @author 黄昭
     */
    @Modifying
    @Query("update GoodsTemplateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个GoodsTemplateRel
     * @author 黄昭
     */
    Optional<GoodsTemplateRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);


    @Query("SELECT count(g.id) from GoodsTemplateRel g where g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and g.templateId = ?1")
    Long countByTemplateId(Long templateId);

    @Query("SELECT g.goodsId from GoodsTemplateRel g where g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and g.templateId = ?1 order by g.updateTime DESC")
    List<String> joinGoodsDetails(Long id);

    @Modifying
    @Query("update GoodsTemplateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);

    @Query("from GoodsTemplateRel where goodsId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    GoodsTemplateRel getByGoodsId(String goodsId);

    @Modifying
    @Query("update GoodsTemplateRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where templateId = ?1")
    void deleteByTemplateId(Long id);
}
