package com.wanmi.sbc.goods.liveroomlivegoodsrel.repository;

import com.wanmi.sbc.goods.liveroomlivegoodsrel.model.root.LiveRoomLiveGoodsRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>直播房间和直播商品关联表DAO</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Repository
public interface LiveRoomLiveGoodsRelRepository extends JpaRepository<LiveRoomLiveGoodsRel, Long>,
        JpaSpecificationExecutor<LiveRoomLiveGoodsRel> {

    /**
     * 单个删除直播房间和直播商品关联表
     * @author zwb
     */
    @Modifying
    @Query("update LiveRoomLiveGoodsRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    Optional<LiveRoomLiveGoodsRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

   List<LiveRoomLiveGoodsRel> findByRoomIdAndDelFlag(Long roomId, DeleteFlag delFlag);

    List<LiveRoomLiveGoodsRel> findByGoodsIdAndDelFlag(Long goodsId, DeleteFlag delFlag);

    @Modifying
    @Query("update LiveRoomLiveGoodsRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where goodsId = ?1")
    void deleteByGoodsIdAndDelFlag(Long id, DeleteFlag delFlag);


    List<LiveRoomLiveGoodsRel> findByRoomIdInAndDelFlag(List<Long> roomIds, DeleteFlag deleteFlag);
}
