package com.wanmi.ares.source.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ExportStatus;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.export.dao.ExportDataMapper;
import com.wanmi.ares.export.model.entity.ExportDataEntity;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.report.service.ExportServiceFactory;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.sbc.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 导出任务要求的消息队列消费者
 * Author: bail
 * Time: 2017/11/2.10:54
 */
@Slf4j
@Component
public class ExportDataRequestConsumer {

    @Autowired
    private ExportDataMapper exportDataMapper;

    @Autowired
    private ExportServiceFactory exportServiceFactory;
    /**
     * 导出任务要求的消费者1
     * 目前只设置一个消费者,即单线程同步生成导出文件
     * @param json
     */
    @Transactional
    public void produceExportData(String json) {
          //1.转换消费信息,准备生产导出文件
        ExportDataEntity entity = JSONObject.parseObject(json, ExportDataEntity.class);

        //2.根据商家id,开始日期,截止日期,报表类别查询导出成功的任务数据
        entity.setExportStatus(ExportStatus.SUCCESS_EXPORT.getValue());
        entity.setParamsMD5(entity.getMd5HexParams());
        List<ExportDataEntity> expResultList = exportDataMapper.queryExportDataRequestList(entity);

        //3.若存在导出成功的文件,则直接利用之前文件
        if(expResultList!=null && !expResultList.isEmpty()){
            entity.setFilePath(expResultList.get(0).getFilePath());
            entity.setExportStatus(ExportStatus.SUCCESS_EXPORT.getValue());//导出成功
            entity.setFinishTime(DateUtil.nowTime());
        }else{
            log.info("导出报表MQ，下载参数：{}", json);
            //4.若不存在导出成功的文件,则调用生成excel服务(根据类别,生产不同的excel文件)
            produceDetail(entity);
        }

        //3.根据生产结果,更新导出状态
        exportDataMapper.updateExportDataRequest(entity);
    }

    /**
     * 根据导出报表类别,生产不同的导出文件
     * @param entity
     */
    private void produceDetail(ExportDataEntity entity){
        ExportQuery exportQuery = new ExportQuery().convertFromRequest(entity);
        List<String> fileList;
        String filePath = null;
        try {
            ReportType reportType = entity.getReportType();
            ExportBaseService exportBaseService = exportServiceFactory.create(reportType);
            BaseResponse baseResponse = exportBaseService.exportReport(exportQuery);
            Object object = baseResponse.getContext();
            if (object instanceof String){
                filePath = String.valueOf(object);
            } else {
                fileList = (List<String>) object;
                filePath = fileList.stream().collect(Collectors.joining(","));
            }
            entity.setFilePath(filePath);
            entity.setExportStatus(ExportStatus.SUCCESS_EXPORT.getValue());//导出成功
        }catch (Exception e){
            entity.setExportStatus(ExportStatus.ERROR_EXPORT.getValue());//导出失败
            log.error("produceReport execute error, param={}, exception:{}", entity, e);
        }
        entity.setFinishTime(DateUtil.nowTime());
    }

}
