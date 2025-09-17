package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.apply.SellPlatformApplySceneRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.apply.SellPlatformFinishAccessRequest;

/**
 * @description 代销平台申请处理
 * @author malianfeng
 * @date 2022/4/22 17:07
 */
public interface SellPlatformApplyService extends SellPlatformBaseService {

    /**
     * 开通自定义交易组件
     * @return
     */
    BaseResponse registerApply(SellPlatformBaseRequest request);

    /**
     * 完成接入任务
     * @return
     */
    BaseResponse registerFinishAccess(SellPlatformFinishAccessRequest request);

    /**
     * 场景接入申请
     * @return
     */
    BaseResponse registerApplyScene(SellPlatformApplySceneRequest request);

    /**
     *  获取接入状态
     * @return
     */
    BaseResponse registerCheck(SellPlatformBaseRequest request);
}
