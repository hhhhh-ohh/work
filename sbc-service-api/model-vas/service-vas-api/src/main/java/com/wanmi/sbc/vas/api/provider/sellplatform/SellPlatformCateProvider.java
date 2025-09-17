package com.wanmi.sbc.vas.api.provider.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformAuditCateRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformUploadImgRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformAuditResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformCateResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformUploadImgResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author wur
 * @className SellPlatformCateProvider
 * @description 微信类目处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.vas.name}", contextId = "SellPlatformCateProvider")
public interface SellPlatformCateProvider {

    /**
     * 查询商品微信类目
     * @return
     */
    @PostMapping("/channel/${application.vas.version}/sell-platform/query_cate/")
    BaseResponse<List<SellPlatformCateResponse>> queryCate(@RequestBody @Valid SellPlatformBaseRequest request);

    /**
     * 上传图片
     * @return
     */
    @PostMapping("/channel/${application.vas.version}/sell-platform/upload_img/")
    BaseResponse<SellPlatformUploadImgResponse> uploadImg(@RequestBody @Valid SellPlatformUploadImgRequest request);

    /**
     * 上传类目资质
     * @return
     */
    @PostMapping("/channel/${application.vas.version}/sell-platform/audit_cate/")
    BaseResponse<SellPlatformAuditResponse> auditCate(@RequestBody @Valid SellPlatformAuditCateRequest request);

}
