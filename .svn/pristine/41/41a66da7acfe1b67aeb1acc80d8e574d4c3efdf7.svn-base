package com.wanmi.sbc.goods.provider.impl.tcc;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.provider.tcc.TccProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.tcc.TccActionImpl;
import com.wanmi.sbc.goods.tcc.TccInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanggaolei
 * @className TccController
 * @description
 * @date 2022/6/28 16:27
 */
@RestController
public class TccController implements TccProvider {

    @Autowired TccActionImpl tccAction;

    @Override
    public BaseResponse insertTcc(GoodsInfoRequest goodsInfoRequest) {
        tccAction.prepare(goodsInfoRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
