package com.wanmi.sbc.customer.liveroom.repository;

import com.wanmi.sbc.customer.api.request.liveroom.LiveRoomQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LiveRoomStatus;
import com.wanmi.sbc.customer.liveroom.model.root.LiveRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>直播间DAO</p>
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Repository
public interface LiveRoomRepository extends JpaRepository<LiveRoom, Long>,
        JpaSpecificationExecutor<LiveRoom> {

    /**
     * 单个删除直播间
     * @author zwb
     */
    @Modifying
    @Query("update LiveRoom set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量修改
     * @author zwb
     */
    @Modifying
    @Query("update LiveRoom set liveStatus =?1 where roomId in ?2 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void updateStatusByRoomIdList(LiveRoomStatus liveStatus, List<Long> roomIdList);


    /**
     * 单个修改直播间推荐状态
     * @author zwb
     */
    @Modifying
    @Query("update LiveRoom set recommend =?1 where roomId = ?2")
    void updateRecommendByRoomId(Integer recommend, Long roomId);

    @Query(value = "select lr.* from live_room as lr " +
                    "left join store as st on lr.store_id=st.store_id " +
                    "left join live_company as lc on lr.store_id = lc.store_id  " +
                    "where " +
                    "IF (ifnull(:#{#request.name},'') != '', lr.name LIKE CONCAT('%',:#{#request.name},'%'), 1=1) and " +
                    "IF (ifnull(:#{#request.anchorName},'') != '', lr.anchor_name like CONCAT('%',:#{#request.anchorName},'%'), 1=1) and " +
                    "IF (ifnull(:#{#request.storeName},'') != '', st.store_name like CONCAT('%',:#{#request.storeName},'%'), 1=1) and " +
                    "IF (ifnull(:#{#request.storeId},'') != '', st.store_id = :#{#request.storeId}, 1=1) and " +
                    "IF (ifnull(:#{#request.startTime},'') != '', lr.start_time >= :#{#request.startTime}, 1=1) and " +
                    "IF (ifnull(:#{#request.endTime},'') != '', lr.end_time <= :#{#request.endTime}, 1=1) and " +
                    "IF (ifnull(:#{#request.liveStatus?.toValue()},'') != '', lr.live_status = :#{#request.liveStatus?.toValue()},1=1) and " +
                    "st.contract_end_date >= now() and " +
                    "st.store_state = 0 and " +
                    "lc.live_broadcast_status = 2 " +
                    "order by lr.create_time desc",
            countQuery ="select count(1) from live_room as lr " +
                    "left join store as st on lr.store_id=st.store_id " +
                    "left join live_company as lc on lr.store_id = lc.store_id  " +
                    "where " +
                    "IF (ifnull(:#{#request.name},'') != '', lr.name LIKE CONCAT('%',:#{#request.name},'%'), 1=1) and " +
                    "IF (ifnull(:#{#request.anchorName},'') != '', lr.anchor_name like CONCAT('%',:#{#request.anchorName},'%'), 1=1) and " +
                    "IF (ifnull(:#{#request.storeName},'') != '', st.store_name like CONCAT('%',:#{#request.storeName},'%'), 1=1) and " +
                    "IF (ifnull(:#{#request.storeId},'') != '', st.store_id = :#{#request.storeId}, 1=1) and " +
                    "IF (ifnull(:#{#request.startTime},'') != '', lr.start_time >= :#{#request.startTime}, 1=1) and " +
                    "IF (ifnull(:#{#request.endTime},'') != '', lr.end_time <= :#{#request.endTime}, 1=1) and " +
                    "IF (ifnull(:#{#request.liveStatus?.toValue()},'') != '', lr.live_status = :#{#request.liveStatus?.toValue()},1=1) and " +
                    "st.contract_end_date >= now() and " +
                    "st.store_state = 0 and " +
                    "lc.live_broadcast_status = 2",
            nativeQuery = true)
    Page<List<LiveRoom>> findByTerm(@Param("request") LiveRoomQueryRequest request, Pageable pageable);
}
