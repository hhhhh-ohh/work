package com.wanmi.sbc.goods.standard.repository;

import com.wanmi.sbc.goods.standard.model.root.StandardPropDetailRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @auther ruilinxin
 * @create 2018/03/20 10:04
 */
@Repository
public interface StandardPropDetailRelRepository extends JpaRepository<StandardPropDetailRel, Long>, JpaSpecificationExecutor<StandardPropDetailRel> {

    @Query(value = " from StandardPropDetailRel a where a.goodsId= ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO ")
    List<StandardPropDetailRel> queryByGoodsId(String goodsId);

    /**
     * 根据多个商品ID编号进行删除
     *
     * @param goodsIds 商品ID
     */
    @Modifying
    @Query("update StandardPropDetailRel w set w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,w.updateTime = now() where w.goodsId in ?1")
    void deleteByGoodsIds(List<String> goodsIds);

}
