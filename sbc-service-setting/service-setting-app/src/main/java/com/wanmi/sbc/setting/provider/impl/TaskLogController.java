package com.wanmi.sbc.setting.provider.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.TaskLogProvider;
import com.wanmi.sbc.setting.api.request.TaskLogAddRequest;
import com.wanmi.sbc.setting.api.request.TaskLogCountByParamsRequest;
import com.wanmi.sbc.setting.api.response.TaskLogCountByParamsResponse;
import com.wanmi.sbc.setting.tasklog.model.root.TaskLog;
import com.wanmi.sbc.setting.tasklog.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskLogController implements TaskLogProvider {

    @Autowired
    private TaskLogService taskLogService;

    @Override
    public BaseResponse add(@RequestBody TaskLogAddRequest request) {
        TaskLog taskLog = KsBeanUtil.copyPropertiesThird(request, TaskLog.class);
        taskLogService.add(taskLog);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<TaskLogCountByParamsResponse> count(@RequestBody TaskLogCountByParamsRequest request) {
        TaskLogCountByParamsResponse response = new TaskLogCountByParamsResponse();
        response.setCount(taskLogService.count(request));
        return BaseResponse.success(response);
    }
}
