package com.wanmi.sbc.vas.provider.impl.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformCateProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformAuditCateRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformUploadImgRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformAuditResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformCateResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformUploadImgResponse;
import com.wanmi.sbc.vas.sellplatform.SellPlatformCateService;
import com.wanmi.sbc.vas.sellplatform.SellPlatformContext;
import com.wanmi.sbc.vas.sellplatform.SellPlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class SellPlatformCateController implements SellPlatformCateProvider {

    @Autowired private SellPlatformContext sellPlatformContext;

    @Override
    public BaseResponse<List<SellPlatformCateResponse>> queryCate(@RequestBody @Valid SellPlatformBaseRequest request) {
        SellPlatformCateService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_CATE_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(applyService.queryCate());
    }

    @Override
    public BaseResponse<SellPlatformUploadImgResponse> uploadImg(@RequestBody @Valid SellPlatformUploadImgRequest request) {
        SellPlatformCateService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_CATE_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(applyService.uploadImg(request));
    }

    @Override
    public BaseResponse<SellPlatformAuditResponse> auditCate(@RequestBody @Valid SellPlatformAuditCateRequest request) {
        SellPlatformCateService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_CATE_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(applyService.auditCate(request));
    }
}
