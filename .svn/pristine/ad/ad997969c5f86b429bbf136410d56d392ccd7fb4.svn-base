package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.ares.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description 导出工厂类
 * @author  xuyunpeng
 * @date 2021/5/28 6:04 下午
 */
@Component
public class ExportServiceFactory {

    @Autowired
    private Map<String, ExportBaseService> exportBaseServiceMap;

    public ExportBaseService create(ReportType reportType) {
        return exportBaseServiceMap.get(reportType.getBeanName());
    }
}
