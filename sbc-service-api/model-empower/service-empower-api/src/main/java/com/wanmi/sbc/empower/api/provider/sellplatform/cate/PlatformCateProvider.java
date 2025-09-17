package com.wanmi.sbc.empower.api.provider.sellplatform.cate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.cate.PlatformAuditCateRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.cate.PlatformUploadImgRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.PlatformAuditResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformCateResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformUploadImgResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author wur
 * @className PlatformCateProvider
 * @description 微信类目处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformCateProvider")
public interface PlatformCateProvider {

    /**
     * 查询商品微信类目
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/query_cate/")
    BaseResponse<List<PlatformCateResponse>> queryCate(ThirdBaseRequest request);

    /**
     * 上传图片
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/upload_img/")
    BaseResponse<PlatformUploadImgResponse> uploadImg(@RequestBody @Valid PlatformUploadImgRequest request);

    /**
     * 上传类目资质
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/audit_cate/")
    BaseResponse<PlatformAuditResponse> auditCate(@RequestBody @Valid PlatformAuditCateRequest request);

}
