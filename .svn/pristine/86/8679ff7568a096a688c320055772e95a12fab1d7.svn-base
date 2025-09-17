package com.wanmi.sbc.setting.api.provider;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.TaskLogAddRequest;
import com.wanmi.sbc.setting.api.request.TaskLogCountByParamsRequest;
import com.wanmi.sbc.setting.api.response.TaskLogCountByParamsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${application.setting.name}", contextId = "TaskLogProvider")
public interface TaskLogProvider {

    /**
     * @description 添加定时任务日志
     * @author malianfeng
     * @date 2021/9/9 14:06
     */
    @PostMapping("/setting/${application.setting.version}/task-log/add")
    BaseResponse add(@RequestBody TaskLogAddRequest request);

    /**
     * @description 统计定时任务日志
     * @author dyt
     * @date 2021/9/9 14:06
     */
    @PostMapping("/setting/${application.setting.version}/task-log/count")
    BaseResponse<TaskLogCountByParamsResponse> count(@RequestBody TaskLogCountByParamsRequest request);
}
