package com.wanmi.ares.scheduled.goods;

import com.wanmi.ares.task.GoodsTask;
import com.wanmi.ares.utils.CommUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-9-24
 * \* Time: 11:25
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
public class GoodsReportGenerate {
    @Resource
    private GoodsTask goodsTask;

    @XxlJob(value = "goodsReportGenerate")
    public void execute() throws Exception {
        String types = XxlJobHelper.getJobParam();
        if (CommUtils.isNumeric(types)) {
            String strs[] = types.split(",");
            for (String type : strs) {
                if (StringUtils.isNotBlank(type)) {
                    goodsTask.generate(Integer.parseInt(type));
                }
            }
        } else {
            XxlJobHelper.handleResult(ReturnT.FAIL_CODE, "请求参数不合法");
        }
    }
}
