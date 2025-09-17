package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformAuditCateRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformUploadImgRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformAuditResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformCateResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.cate.SellPlatformUploadImgResponse;

import java.util.List;

/**
*
 * @description    类目相关处理
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface SellPlatformCateService extends SellPlatformBaseService {

    /**
     * @description   查询类目
     * @author  wur
     * @date: 2022/4/29 11:29
     * @return
     **/
    List<SellPlatformCateResponse> queryCate();

    /**
     * @description    上上传类目资质
     * @author  wur
     * @date: 2022/4/29 11:30
     * @param request
     * @return
     **/
    SellPlatformUploadImgResponse uploadImg(SellPlatformUploadImgRequest request);

    /**
     * @description    类目申请
     * @author  wur
     * @date: 2022/4/29 11:30
     * @param request
     * @return
     **/
    SellPlatformAuditResponse auditCate(SellPlatformAuditCateRequest request);
}
