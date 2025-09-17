package com.wanmi.ares.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataPeriod implements InitializingBean {

    @Value("${data.period:90}")
    private int _period;

    public static int period;

    @Override
    public void afterPropertiesSet() throws Exception {
        period = _period;
    }

    /**
     * 根据配置获取起始日期
     * @return
     */
    public static String getStartDate(){
        return LocalDate.now().minusDays(period).toString();
    }

    /**
     * 根据配置获取String类型的时间周期，例如：2021-01-01~2021-02-01
     * @return
     */
    public static String getDateRange(){
        return getStartDate()+"~"+LocalDate.now().minusDays(1);
    }
}
