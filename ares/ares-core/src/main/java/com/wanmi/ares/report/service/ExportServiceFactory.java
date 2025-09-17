package com.wanmi.ares.report.service;

import com.wanmi.ares.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExportServiceFactory {

    //自动注入到map
    @Autowired
    private Map<String , ExportBaseService> exportBaseServiceMap;

    public ExportBaseService create(ReportType reportType){
        return exportBaseServiceMap.get(reportType.getBeanName());
    }
}
