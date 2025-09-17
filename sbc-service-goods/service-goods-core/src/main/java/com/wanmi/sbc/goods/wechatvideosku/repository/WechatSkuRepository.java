package com.wanmi.sbc.goods.wechatvideosku.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>微信视频号带货商品DAO</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Repository
public interface WechatSkuRepository extends JpaRepository<WechatSku, Long>,
        JpaSpecificationExecutor<WechatSku> {

    @Override
    @EntityGraph(value = "goodsInfo")
    Page<WechatSku> findAll(Specification<WechatSku> specification, Pageable pageable);


    @Modifying
    @Query("update WechatSku set editStatus = ?2,rejectReason = ?3,wechatShelveStatus = ?4,updateTime = now() where goodsId = ?1")
    int audit(String goodsId, EditStatus editStatus, String rejectReason, WechatShelveStatus wechatShelveStatus);

    @Modifying
    @Query("update WechatSku set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES,updatePerson = ?2,updateTime = now() where goodsId in ?1")
    int delByGoodsId(List<String> goodsId,String updatePerson);

    @Modifying
    @Query("update WechatSku set wechatShelveStatus = com.wanmi.sbc.goods.bean.enums.WechatShelveStatus.SHELVE,updateTime = now() where goodsId = ?1")
    int up(String goodsId);

    @Modifying
    @Query("update WechatSku set wechatShelveStatus = com.wanmi.sbc.goods.bean.enums.WechatShelveStatus.VIOLATION_UN_SHELVE,downReason = ?2,updateTime = now() where goodsId = ?1")
    int down(String goodsId,String downReason);

    @Query("select goodsId from WechatSku where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsId in ?1")
    List<String> selectGoodsId(List<String> goodsIds);

    @Query("select DISTINCT(w.goodsId) from WechatSku w INNER JOIN GoodsInfo g on w.goodsId = g.goodsId where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.goodsId in ?1 and g.storeId = ?2")
    List<String> selectByGoodsIdsAndStoreId(List<String> goodsIds, Long storeId);

    @Query("select goodsId from WechatSku where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and goodsInfoId in ?1")
    List<String> selectGoodsIdByGoodsInfoIds(List<String> goodsInfoIds);


    int countAllByDelFlagAndGoodsIdAndEditStatus(DeleteFlag deleteFlag,String goodsId,EditStatus editStatus);

    @Modifying
    @Query("update WechatSku set wechatShelveStatus = ?2,updateTime = now() where goodsId in ?1")
    int updateAddedByGoodsId(List<String> goodsId, WechatShelveStatus wechatShelveStatus);

    @Modifying
    @Query(value = "UPDATE wechat_sku w JOIN goods_info g ON w.goods_info_id = g.goods_info_id SET w.wechat_shelve_status = ?2,w.update_time = NOW() WHERE g.store_id = ?1 and w.edit_status = 4 and w.wechat_shelve_status <> ?2",nativeQuery = true)
    int updateShelve(Long storeId, int wechatShelveStatus);

    @Query("select w.goodsId from WechatSku w join GoodsInfo g on w.goodsInfoId = g.goodsInfoId where g.storeId = ?1 and w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.editStatus = 1 and w.wechatShelveStatus <> ?2")
    List<String> selectByStoreIdAndWechatShelveStatus(Long storeId,WechatShelveStatus wechatShelveStatus);

    @Query("select new com.wanmi.sbc.goods.bean.vo.GoodsInfoVO(g.goodsInfoId,g.goodsId,g.stock) from WechatSku w join GoodsInfo g on w.goodsInfoId = g.goodsInfoId where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.editStatus = 1 and w.wechatShelveStatus = 5")
    List<GoodsInfoVO> selectStock();

    @Modifying
    @Query("update WechatSku set img = ?2,updateTime = now() where goodsInfoId = ?1")
    int updateImg(String goodsInfoId,String img);

    @Modifying
    @Query("update WechatSku set editStatus = ?2,updateTime = now() where goodsId = ?1")
    int updateAuditStatus(String goodsInfoId, EditStatus editStatus);

    @Modifying
    @Query("update WechatSku set editStatus = ?2,img = ?3,updateTime = now() where goodsId = ?1")
    int reAdd(String goodsId, EditStatus editStatus,String img);

    @Modifying
    @Query("update WechatSku set wechatSkuId = ?2 ,productId = ?3,updateTime = now() where goodsInfoId = ?1")
    int updateProductId(String goodsInfoId,String wechatSkuId,Long productId);

}
