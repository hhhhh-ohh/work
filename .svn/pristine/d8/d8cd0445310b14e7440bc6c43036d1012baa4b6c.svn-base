package com.wanmi.sbc.mq.report;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ExportStatus;
import com.wanmi.ares.provider.ExportDataServiceProvider;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author edz
 * @className ExportCenter
 * @description 数据导出
 * @date 2021/5/28 4:54 下午
 **/
@Slf4j
@Service
//@EnableBinding
public class ExportCenter {

    @Autowired
    private ExportDataServiceProvider exportDataServiceProvider;

    @Autowired
    private ExportServiceFactory exportServiceFactory;

    @Async
    public void dealExport(String json){
        log.info("produceReport execute begin, param={}", json);
        //转换消费信息,准备生产导出文件
        ExportData entity = JSONObject.parseObject(json, ExportData.class);
        //获取导出服务对象
        ExportBaseService exportBaseService = exportServiceFactory.create(entity.getTypeCd());
        ExportDataRequest exportDataRequest = KsBeanUtil.convert(entity, ExportDataRequest.class);
        try {
            //导出数据
            BaseResponse baseResponse = exportBaseService.exportReport(entity);
            String fileUrl = String.valueOf(baseResponse.getContext());
            exportDataRequest.setFilePath(fileUrl);
            exportDataRequest.setExportStatus(ExportStatus.SUCCESS_EXPORT);//导出成功
        } catch (Exception e) {
            exportDataRequest.setExportStatus(ExportStatus.ERROR_EXPORT);
            log.error("produceReport execute error, param={}, exception:{}", entity, e);
        }
        exportDataRequest.setFinishTime(DateUtil.nowTime());
        exportDataServiceProvider.updateExportData(exportDataRequest);
    }


}
