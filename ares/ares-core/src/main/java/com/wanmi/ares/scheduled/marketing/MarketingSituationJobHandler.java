package com.wanmi.ares.scheduled.marketing;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.marketing.overview.service.MarketingSituationService;
import com.wanmi.ares.utils.DateUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @ClassName MarketingSituationJobHandler
 * @Description 营销概览-活动数据概况统计任务
 * @Author chenli
 * @Date 2021/2/17 9:36
 * @Version 1.0
 **/
@Slf4j
@Component
public class MarketingSituationJobHandler  {
    @Autowired
    private MarketingSituationService marketingSituationService;

    /**
     * 凌晨4点执行，统计结束范围为前一天
     * XXLJOB-param 如果参数不为空，则统计传入日期的数据，否则，统计前一天的数据
     * @return
     * @throws Exception
     */
    @Transactional
    @XxlJob(value = "marketingSituationJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();

        LocalDate localDate = LocalDate.now().minusDays(1);
        if (StringUtils.isNotBlank(param)){
            JSONObject jsonObject = JSONObject.parseObject(param);
            XxlJobHelper.log("参数：" + param);
            String initDate = jsonObject.getString("initDate");

            localDate = DateUtil.parse2Date(initDate, DateUtil.FMT_DATE_1);
            // 如果传入的参数等于 或者 大于 今天
            if (Objects.equals(LocalDate.now(), localDate) || localDate.isAfter(LocalDate.now())){
                XxlJobHelper.log("营销概览-活动数据概况统计日期{}不能等于或大于当天", localDate);
                return;
            }
        }

        XxlJobHelper.log("{}-营销概览-活动数据概况统计开始", localDate);
        marketingSituationService.generator(localDate);
        XxlJobHelper.log("营销概览-活动数据概况统计完成");
    }
}
