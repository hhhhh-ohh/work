package com.wanmi.ares.export.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ExportStatus;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.exception.AresRuntimeException;
import com.wanmi.ares.export.dao.ExportDataMapper;
import com.wanmi.ares.export.model.entity.ExportDataEntity;
import com.wanmi.ares.interfaces.export.ExportDataService;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.export.ExportDataResponse;
import com.wanmi.ares.view.export.ExportDataView;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 导出任务请求的Service
 * Author: bail
 * Time: 2017/11/01.17:00
 */
@Service
@Slf4j
public class ExportDataServiceImpl implements ExportDataService.Iface{

    private static final Pattern PATTERN = Pattern.compile(",");

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportDataMapper exportDataMapper;

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 发送导出报表任务的请求
     * @param expRequest 请求信息对象
     * @return 导出任务对象(包含导出状态)
     */
 //   @Transactional
    @Override
    public ExportDataView sendExportDataRequest(ExportDataRequest expRequest) {
        if(expRequest.getTypeCd()==null){
            //验证参数非空
            throw new AresRuntimeException(CommonErrorCodeEnum.K000009.getCode());
        }
        // 如果是营销概览下载报表，根据类型设置开始时间和结束时间
        if (Objects.equals(expRequest.getTypeCd(), ReportType.MARKETING_OVERVIEW)){
            // 根据导出日期类型判断，设置开始、结束时间
            switch (expRequest.getStatisticsDataType()) {
                case YESTERDAY:
                    expRequest.setBeginDate(LocalDate.now().minusDays(1L).toString());
                    expRequest.setEndDate(LocalDate.now().minusDays(1L).toString());
                    break;
                case SEVEN:
                    expRequest.setBeginDate(LocalDate.now().minusDays(7L).toString());
                    expRequest.setEndDate(LocalDate.now().minusDays(1L).toString());
                    break;
                case THIRTY:
                    expRequest.setBeginDate(LocalDate.now().minusDays(30L).toString());
                    expRequest.setEndDate(LocalDate.now().minusDays(1L).toString());
                    break;
                case MONTH:
                    if(expRequest.getMonth()==null){
                        //验证参数非空
                        throw new AresRuntimeException(CommonErrorCodeEnum.K000009.getCode());
                    }
                    expRequest.setBeginDate(DateUtil.pareeByMonthFirst(expRequest.getMonth()).toString());
                    expRequest.setEndDate(DateUtil.parseByMonth(expRequest.getMonth()).toString());
                    break;
                default:
                    break;
            }
        }
        if(expRequest.getBeginDate()==null || expRequest.getEndDate()==null){
            //验证参数非空
            throw new AresRuntimeException(CommonErrorCodeEnum.K000009.getCode());
        }
        try {
            //验证日期参数格式
            DateUtil.parse2Date(expRequest.getBeginDate(), DateUtil.FMT_DATE_1);
            DateUtil.parse2Date(expRequest.getEndDate(), DateUtil.FMT_DATE_1);
        }catch (Exception e){
            throw new AresRuntimeException(CommonErrorCodeEnum.K000009, e);
        }

        ExportDataEntity entity = new ExportDataEntity().convertEntityFromRequest(expRequest);//转换实体

        /**新增导出任务,并加入导出任务消息队列*/
        entity.setExportStatus(ExportStatus.WAIT_EXPORT.getValue());
        entity.setCreateTime(DateUtil.nowTime());
        entity.setParamsMD5(entity.getMd5HexParams());
        exportDataMapper.insertExportDataRequest(entity);
        entity.setReportType(expRequest.getTypeCd());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.AREAS_EXPORT);
        mqSendDTO.setData(JSONObject.toJSONString(entity));
        mqSendProvider.send(mqSendDTO);
        return entity.convertViewFromEntity(osdService.getPrefix());
    }

    @Override
    public ExportDataView sendExportBusinessDataRequest(ExportDataRequest request) {
        if(request.getTypeCd()==null){
            //验证参数非空
            throw new AresRuntimeException(CommonErrorCodeEnum.K000009.getCode());
        }
        ExportDataEntity entity = new ExportDataEntity().convertEntityFromRequest(request);//转换实体
        entity.setExportStatus(ExportStatus.WAIT_EXPORT.getValue());
        entity.setCreateTime(DateUtil.nowTime());
        exportDataMapper.insertExportDataRequest(entity);
        entity.setTypeCd(request.getTypeCd().toValue());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.Q_BATCH_BUSINESS_EXPORT);
        mqSendDTO.setData(JSONObject.toJSONString(entity));
        mqSendProvider.send(mqSendDTO);
        return entity.convertViewFromEntity(osdService.getPrefix());
    }

    /**
     * 分页查询导出任务
     * @param expRequest
     * @return
     */
    @Override
    public ExportDataResponse queryExportDataRequestPage(ExportDataRequest expRequest)  {
        ExportDataEntity entity = new ExportDataEntity().convertEntityFromRequest(expRequest);//转换实体

        ExportDataResponse response = new ExportDataResponse();
        Integer count = exportDataMapper.countExportDataRequest(entity);
        response.setTotal(count);
        if(count <= 0){
            response.setViewList(Collections.emptyList());
            return response;
        }
        entity.setStartNum((entity.getPageNum()-1)*entity.getPageSize());//分页的起始下标
        List<ExportDataEntity> entityList = exportDataMapper.queryExportDataRequest(entity);
        String prefix = osdService.getPrefix();
        response.setViewList(entityList.stream().map(en -> en.convertViewFromEntity(prefix)).collect(Collectors.toList()));
        return response;
    }

    /**
     * 用户删除自己的下载报表任务
     * @param expRequest
     * @return 删除成功条数
     */
    @Transactional
    @Override
    public int deleteExportDataRequest(ExportDataRequest expRequest)  {
        return exportDataMapper.deleteExportDataRequest(new ExportDataEntity().convertEntityFromRequest(expRequest));
    }

    /**
     * 用户删除自己的下载报表任务,并且删除文件
     * @param expRequest
     * @return 删除成功条数
     */
    @Transactional
    @Override
    public int deleteExportDataRequestAndFiles(ExportDataRequest expRequest)  {
        ExportDataEntity entity = exportDataMapper.queryOneExportDataRequest(new ExportDataEntity().convertEntityFromRequest(expRequest));
        if(StringUtils.isNotEmpty(entity.getFilePath())){
            osdService.deleteFiles(PATTERN.splitAsStream(entity.getFilePath()).collect(Collectors.toList()));
        }
        return exportDataMapper.deleteExportDataRequest(entity);
    }

    /**
     * 根据日期,清理导出数据任务以及对应的OSS文件
     * @param clearBaseDate
     * @return
     */
    @SuppressWarnings("unused")
    @Transactional
    public int clear(LocalDate clearBaseDate){
        ExportDataEntity req = new ExportDataEntity();
        req.setBeginDate(DateUtil.format(clearBaseDate,DateUtil.FMT_DATE_1));
        req.setExportStatus(ExportStatus.SUCCESS_EXPORT.getValue());
        /**1.查询某个时间以前的下载任务List*/
        List<ExportDataEntity> entityList = exportDataMapper.queryExportDataRequestListForClear(req);

        /**2.清理OSS文件(将任务对应的所有文件拼接到一个List中作为OSS删除文件的参数)*/
        List<String> files = entityList.stream().filter(expEntity -> StringUtils.isNotEmpty(expEntity.getFilePath())).flatMap(expEntity -> PATTERN.splitAsStream(expEntity.getFilePath())).collect(Collectors.toList());
        if(files!=null && !files.isEmpty()){
            osdService.deleteFiles(files);
        }

        /**3.清理导出任务表数据*/
        return exportDataMapper.clearExportDataRequest(req);
    }

    /**
     * 更新任务
     * @param exportDataRequest
     */
    @Transactional
    public int update(ExportDataRequest exportDataRequest) {
        ExportDataEntity entity = new ExportDataEntity().convertEntityFromRequest(exportDataRequest);//转换实体
        return exportDataMapper.updateExportDataRequest(entity);
    }
}
