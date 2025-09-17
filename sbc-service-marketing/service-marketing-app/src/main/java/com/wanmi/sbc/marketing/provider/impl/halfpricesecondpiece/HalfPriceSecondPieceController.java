package com.wanmi.sbc.marketing.provider.impl.halfpricesecondpiece;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.halfpricesecondpiece.HalfPriceSecondPieceProvider;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceAddRequest;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullReductionAddRequest;
import com.wanmi.sbc.marketing.api.request.halfpricesecondpiece.HalfPriceSecondPieceAddRequest;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.halfpricesecondpiece.model.request.HalfPriceSecondPieceSaveRequest;
import com.wanmi.sbc.marketing.halfpricesecondpiece.service.HalfPriceSecondPieceService;
import com.wanmi.sbc.marketing.halfpricesecondpiece.service.HalfPriceSecondPieceServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: weiwnehao
 * @Description:
 * @Date: 2020-05-22
 */
@Validated
@RestController
public class HalfPriceSecondPieceController implements HalfPriceSecondPieceProvider {

    @Autowired
    private HalfPriceSecondPieceServiceInterface halfPriceSecondPieceServiceInterface;

    /**
     * 新增第二件半价
     * @param request  第二件半价请求结构 {@link MarketingFullReductionAddRequest}
     * @return
     */
    @Override
    public BaseResponse addHalfPriceSecondPiece(@RequestBody @Valid HalfPriceSecondPieceAddRequest request) {
        Marketing marketing=halfPriceSecondPieceServiceInterface.addMarketingHalfPriceSecondPiece(KsBeanUtil.convert(request,
                HalfPriceSecondPieceSaveRequest.class));
        return BaseResponse.success(marketing);
    }

    /**
     * @param request 修改一口价请求结构 {@link MarketingBuyoutPriceAddRequest}
     * @return
     */
    @Override
    public BaseResponse modifyHalfPriceSecondPiece(@RequestBody @Valid HalfPriceSecondPieceAddRequest request) {
        halfPriceSecondPieceServiceInterface.modifyHalfPriceSecondPiece(KsBeanUtil.convert(request,
                HalfPriceSecondPieceSaveRequest.class));
        return BaseResponse.SUCCESSFUL();
    }
}
