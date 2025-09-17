package com.wanmi.sbc.message.api.request.appmessage;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.message.bean.enums.SendStatus;
import lombok.Data;

/**
 * @author lvzhenwei
 * @className ModifyAppMessageStatusRequest
 * @description 修改app message 发送状态
 * @date 2021/8/19 2:44 下午
 **/
@Data
public class ModifyAppMessageStatusRequest extends BaseRequest {

    private Long messageId;

    private SendStatus status;
}
