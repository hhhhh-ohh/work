package com.wanmi.sbc.goods.tcc;

import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

@LocalTCC
public interface TccInterface {
    /**
     * Prepare boolean.
     * @param tccParam      the tcc param
     * @return the boolean
     */
    @TwoPhaseBusinessAction(name = "test-tcc", commitMethod = "commitTcc", rollbackMethod = "rollbackTcc")
    boolean prepare(
                    @BusinessActionContextParameter(paramName = "tccParam") GoodsInfoRequest tccParam);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean commitTcc(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    boolean rollbackTcc(BusinessActionContext actionContext);
}
