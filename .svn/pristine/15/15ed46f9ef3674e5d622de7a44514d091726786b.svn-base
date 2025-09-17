 package com.wanmi.sbc.marketing.newcomerpurchasecoupon.service;

import com.wanmi.sbc.marketing.bean.dto.NewcomerCouponStockDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

/**
 * @description Interface for manipulating coupon inventory
 * @author  xuyunpeng
 * @date 2022/8/26 2:42 PM
 **/
@LocalTCC
public interface NewcomerCouponStockTccInterface {
    /**
     * sub stock
     * @param dtoList
     * @return
     */
    @TwoPhaseBusinessAction(
            name = "subStock",
            commitMethod="subCommit",
            rollbackMethod = "subRollback")
    boolean subStock(@BusinessActionContextParameter(paramName = "dtoList")
                             List<NewcomerCouponStockDTO> dtoList);

    /**
     * sub commit
     * @param actionContext
     * @return
     */
    boolean subCommit(BusinessActionContext actionContext);

    /**
     * sub rollback
     * @param actionContext
     * @return
     */
    boolean subRollback(BusinessActionContext actionContext);

    /**
     * add stock
     * @param dtoList
     * @return
     */
    @TwoPhaseBusinessAction(
            name = "addStock",
            commitMethod="addCommit",
            rollbackMethod = "addRollback")
    boolean addStock(@BusinessActionContextParameter(paramName = "dtoList")
                             List<NewcomerCouponStockDTO> dtoList);

    /**
     * add commit
     * @param actionContext
     * @return
     */
    boolean addCommit(BusinessActionContext actionContext);

    /**
     * add rollback
     * @param actionContext
     * @return
     */
    boolean addRollback(BusinessActionContext actionContext);
}
