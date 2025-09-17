package com.wanmi.ares.scheduled;

import com.wanmi.ares.report.wechatvideo.service.WechatVideoReportService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 视频号销售额退单额统计
 */
@Component
public class WechatVideoScheduledGenerateData {

    @Autowired
    private WechatVideoReportService wechatVideoReportService;

    @XxlJob(value = "wechatVideoScheduledGenerateData")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(param)) {
            wechatVideoReportService.generateData(param);
        }
    }
}
