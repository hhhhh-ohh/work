package com.wanmi.sbc.goods.providergoodsedit.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import com.wanmi.sbc.goods.providergoodsedit.model.root.ProviderGoodsEditDetail;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 商品代销配置
 * @author  wur
 * @date: 2021/9/10 10:01
 **/
@Repository
public interface ProviderGoodsEditDetailRepository extends JpaRepository<ProviderGoodsEditDetail, Long>,
        JpaSpecificationExecutor<ProviderGoodsEditDetail> {

    /**
     * @description  根据商品信息查询
     * @author  wur
     * @date: 2021/9/14 14:38
     * @param goodsId   供应商商品Id
     * @param delFlag   删除标识
     * @return
     **/
    List<ProviderGoodsEditDetail> findAllByGoodsIdInAndDelFlag(List<String> goodsId, DeleteFlag delFlag);


    /**
     * @description 根据商品ID和操作类型查询
     * @author  wur
     * @date: 2021/9/16 15:34
     * @param goodsId
     * @param enditType
     * @param delFlag
     * @return
     **/
    List<ProviderGoodsEditDetail> findAllByGoodsIdInAndEnditTypeAndDelFlag(List<String> goodsId, GoodsEditType enditType, DeleteFlag delFlag);

    /**
     * @description
     * @author  wur
     * @date: 2021/9/18 16:32
     * @param goodsId
     * @param enditType
     * @param delFlag
     * @return
     **/
    ProviderGoodsEditDetail findByGoodsIdAndEnditTypeAndDelFlag(String goodsId, GoodsEditType enditType, DeleteFlag delFlag);

}
