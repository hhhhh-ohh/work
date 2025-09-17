package com.wanmi.sbc.empower.sellplatform;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.cate.PlatformAuditCateRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.cate.PlatformUploadImgRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.PlatformAuditResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformCateResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformUploadImgResponse;

import java.util.List;

/**
*
 * @description    ThirdPlatformCateService 类目处理接口
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface PlatformCateService extends PlatformBaseService {

    /**
     * @description   查询类目
     * @author  wur
     * @date: 2022/4/19 11:20
     * @return
     **/
    BaseResponse<List<PlatformCateResponse>> queryCate();

    /**
     * @description   上传资质文件
     * @author  wur
     * @date: 2022/4/19 11:20
     * @param request
     * @return
     **/
    BaseResponse<PlatformUploadImgResponse> uploadImg(PlatformUploadImgRequest request);

    /**
     * @description   提交类目资质审核
     * @author  wur
     * @date: 2022/4/19 11:21
     * @param request
     * @return
     **/
    BaseResponse<PlatformAuditResponse> auditCate(PlatformAuditCateRequest request);

}
