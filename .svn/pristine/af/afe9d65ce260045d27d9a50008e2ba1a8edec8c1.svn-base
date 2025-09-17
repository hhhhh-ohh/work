package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoStockTccInterface
 * @description
 * @date 2022/7/4 15:27
 */
@LocalTCC
public interface GoodsInfoStockTccInterface {
    /**
     * Prepare boolean.
     *
     * @param dtoList
     * @return the boolean
     */
    @TwoPhaseBusinessAction(
            name = "subStock",
            commitMethod = "subCommit",
            rollbackMethod = "subRollback")
    boolean subStock(
            @BusinessActionContextParameter(paramName = "dtoList")
                    List<GoodsInfoMinusStockDTO> dtoList);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean subCommit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean subRollback(BusinessActionContext actionContext);

    /**
     * Prepare boolean.
     *
     * @param dtoList
     * @return the boolean
     */
    @TwoPhaseBusinessAction(
            name = "addStock",
            commitMethod = "addCommit",
            rollbackMethod = "addRollback")
    boolean addStock(
            @BusinessActionContextParameter(paramName = "dtoList")
                    List<GoodsInfoPlusStockDTO> dtoList);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean addCommit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean addRollback(BusinessActionContext actionContext);


    /**
     * Prepare boolean.
     *
     * @param dtoList
     * @return the list
     */
    @TwoPhaseBusinessAction(
            name = "subGifStock",
            commitMethod = "subGifCommit",
            rollbackMethod = "subGifRollback")
    List<String> subGifStock(
            @BusinessActionContextParameter(paramName = "dtoList")
                    List<GoodsInfoMinusStockDTO> dtoList);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean subGifCommit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean subGifRollback(BusinessActionContext actionContext);
}
