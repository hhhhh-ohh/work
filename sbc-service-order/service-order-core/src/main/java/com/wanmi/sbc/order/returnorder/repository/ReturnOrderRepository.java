package com.wanmi.sbc.order.returnorder.repository;

import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 退货repository
 * Created by jinwei on 20/4/2017.
 */
@Repository
public interface ReturnOrderRepository extends MongoRepository<ReturnOrder, String> {

    /**
     * 根据订单号查询退单列表
     * @param tid
     * @return
     */
    List<ReturnOrder> findByTid(String tid);

    /**
     *
     * @param ptid
     * @return
     */
    List<ReturnOrder> findByPtid(String ptid);

    /**
     * 根据尾款单号查询退单
     * @param businessTailId
     * @return
     */
    ReturnOrder findByBusinessTailId(String businessTailId);

    /**
     * 根据尾款单号批量查询退单
     * @param businessTailIds
     * @return
     */
    List<ReturnOrder> findALlByBusinessTailIdIn(List<String> businessTailIds);

    /**
     * 根据退单ID集合查询退单
     * @param idList
     * @return
     */
    List<ReturnOrder> findByIdIn(List<String> idList);

    /**
     * 根据订单查询退单
     * @param tidList
     * @return
     */
    List<ReturnOrder> findByTidIn(List<String> tidList);

    /**
     * 根据ptid查询
     * @param tidList
     * @return
     */
    List<ReturnOrder> findByPtidIn(List<String> tidList);

}
