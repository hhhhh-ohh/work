package com.wanmi.sbc.empower.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.apply.PlatformFinishAccessRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.apply.PlatformApplySceneRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.apply.PlatformCheckResponse;
import org.springframework.stereotype.Service;

/**
*
 * @description    注册和申请
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
@Service
public interface PlatformRegistApplyService extends PlatformBaseService {

    /**
     * @description    接入申请
     * @author  wur
     * @date: 2022/4/29 11:31
     * @return
     **/
    BaseResponse apply();

    /**
     * @description   查询接入状态
     * @author  wur
     * @date: 2022/4/29 11:31
     * @return
     **/
    BaseResponse<PlatformCheckResponse> check();

    /**
     * @description    完成接入任务
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse finishAccess(PlatformFinishAccessRequest request);

    /**
     * @description  场景申请
     * @author  wur
     * @date: 2022/4/29 11:31
     * @param request
     * @return
     **/
    BaseResponse applyScene(PlatformApplySceneRequest request);
}
