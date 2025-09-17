package com.wanmi.sbc.marketing.provider.impl.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.goods.GoodsEditSynProvider;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.goods.MarketingGoodsEdit;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author wur
 * @className GoodsEditSynController
 * @description TODO
 * @date 2022/8/29 10:40
 **/
@Validated
@RestController
public class GoodsEditSynController implements GoodsEditSynProvider {

    @Autowired
    private List<MarketingGoodsEdit> marketingGoodsEditList;

    @Override
    public BaseResponse goodsEdit(@Valid GoodsEditSynRequest addRequest) {
        if (CollectionUtils.isNotEmpty(marketingGoodsEditList)) {
            marketingGoodsEditList.forEach(marketingGoodsEdit -> marketingGoodsEdit.goodsEdit(addRequest));
        }
        return BaseResponse.SUCCESSFUL();
    }
}