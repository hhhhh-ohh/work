package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.mq.report.ExportCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className BatchBusinessExportService
 * @description 批量导出
 * @date 2021/8/12 5:40 下午
 */
@Slf4j
@Component
@Service
public class BatchBusinessExportService {

    @Autowired private ExportCenter exportCenter;

    @Bean
    public Consumer<Message<String>> mqBatchBusinessExportService() {
        return message->{
            String json = message.getPayload();
            exportCenter.dealExport(json);
        };
    }

}
