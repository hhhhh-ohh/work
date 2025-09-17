package com.wanmi.sbc.goods.goodstemplate.repository;

import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>GoodsTemplateDAO</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Repository
public interface GoodsTemplateRepository extends JpaRepository<GoodsTemplate, Long>,
        JpaSpecificationExecutor<GoodsTemplate> {

    /**
     * 单个删除GoodsTemplate
     * @author 黄昭
     */
    @Modifying
    @Query("update GoodsTemplate set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除GoodsTemplate
     * @author 黄昭
     */
    @Modifying
    @Query("update GoodsTemplate set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个GoodsTemplate
     * @author 黄昭
     */
    Optional<GoodsTemplate> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
