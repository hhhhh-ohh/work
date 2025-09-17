package com.wanmi.sbc.goods.tcc;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhanggaolei
 * @className TccActionImpl
 * @description
 * @date 2022/6/28 15:37
 */
@Slf4j
@Service
public class TccActionImpl implements TccInterface {
    @Override
    @Transactional
    public boolean prepare(GoodsInfoRequest tccParam) {
        log.info(tccParam.getGoodsInfoId());
        return true;
    }

    @Override
    public boolean commitTcc(BusinessActionContext actionContext) {
        System.out.println("commit");
        return true;
    }

    @Override
    public boolean rollbackTcc(BusinessActionContext actionContext) {
        GoodsInfoRequest request =
                JSON.toJavaObject(
                        (JSONObject) actionContext.getActionContext("tccParam"),
                        GoodsInfoRequest.class);

        log.error("rollback:{}", request != null ? request.getGoodsInfoId() : null);
        return true;
    }
}
