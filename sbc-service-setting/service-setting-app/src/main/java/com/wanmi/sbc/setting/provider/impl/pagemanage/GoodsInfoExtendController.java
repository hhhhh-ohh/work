package com.wanmi.sbc.setting.provider.impl.pagemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.pagemanage.GoodsInfoExtendProvider;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendDeleteByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendModifyRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendModifyRequest;
import com.wanmi.sbc.setting.pagemanage.service.GoodsInfoExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houshuai
 * @date 2021/5/26 17:20
 * @description <p> 商品推广渠道编辑 </p>
 */
@RestController
public class GoodsInfoExtendController implements GoodsInfoExtendProvider {

    @Autowired
    private GoodsInfoExtendService goodsInfoExtendService;

    @Override
    public BaseResponse modifyGoodsInfoExtend(GoodsInfoExtendModifyRequest request) {
        goodsInfoExtendService.modifyGoodsInfoExtend(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteGoodsInfoExtend(GoodsInfoExtendDeleteByIdRequest request) {
        goodsInfoExtendService.deleteGoodsInfoExtend(request);
        return BaseResponse.SUCCESSFUL();
    }
}
