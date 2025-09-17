package com.wanmi.sbc.order.optimization.trade1.snapshot;

import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyInterface;
import org.springframework.stereotype.Service;

/**
 * @author edz
 * @className TradeBuyDispatch
 * @description TODO
 * @date 2022/2/23 15:10
 */
@Service
public class TradeBuyDispatch {

    public void commit(TradeBuyRequest request) {
        TradeBuyInterface tradeBuyInterface =
                SpringContextHolder.getBean(request.getBuyType().getBuyType());
        ParamsDataVO paramsDataVO = new ParamsDataVO(request);
        // 1. 查询数据
        tradeBuyInterface.queryData(paramsDataVO);
        // 2. 数据校验
        tradeBuyInterface.check(paramsDataVO);
        // 3. 数据组装
        tradeBuyInterface.assembleTrade(paramsDataVO);
        // 4. 营销
        tradeBuyInterface.getMarketing(paramsDataVO);
        // 5.保存
        paramsDataVO.setTerminalToken(request.getTerminalToken());
        tradeBuyInterface.saveTrade(paramsDataVO);
    }
}
