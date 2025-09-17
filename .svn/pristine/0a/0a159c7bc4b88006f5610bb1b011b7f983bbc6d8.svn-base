package com.wanmi.sbc.empower.provider.impl.sellplatform.cate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sellplatform.cate.PlatformCateProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.cate.PlatformAuditCateRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.cate.PlatformUploadImgRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.PlatformAuditResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformCateResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformUploadImgResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformCateService;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*
 * @description
 * @author  wur
 * @date: 2022/4/11 10:41
 **/
@RestController
public class PlatformCateController implements PlatformCateProvider {

    @Autowired private PlatformContext thirdPlatformContext;

    @Override
    public BaseResponse<List<PlatformCateResponse>> queryCate(ThirdBaseRequest request) {
        PlatformCateService cateService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.CATE_SERVICE);
        return cateService.queryCate();
    }

    @Override
    public BaseResponse<PlatformUploadImgResponse> uploadImg(PlatformUploadImgRequest request) {
        PlatformCateService cateService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.CATE_SERVICE);
        return cateService.uploadImg(request);
    }

    @Override
    public BaseResponse<PlatformAuditResponse> auditCate(PlatformAuditCateRequest request) {
        PlatformCateService cateService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.CATE_SERVICE);
        return cateService.auditCate(request);
    }
}
