package com.wanmi.sbc.empower.logisticslog.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.empower.bean.dto.KuaiDiHundredNoticeResultItemDTO;
import com.wanmi.sbc.empower.bean.dto.KuaidiHundredNoticeDTO;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.logisticslogdetail.model.root.LogisticsLogDetail;
import com.wanmi.sbc.empower.logisticslogdetail.service.LogisticsLogDetailService;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsSettingService;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.empower.logisticslog.repository.LogisticsLogRepository;
import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogQueryRequest;
import com.wanmi.sbc.empower.bean.vo.LogisticsLogVO;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 物流记录业务逻辑
 *
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Service("LogisticsLogService")
public class LogisticsLogService {

    @Autowired private LogisticsLogRepository logisticsLogRepository;

    /** kuaidi100 请求地址 */
    private static final String KUAIDI_URL = "https://poll.kuaidi100.com/poll";

    @Autowired private LogisticsSettingService logisticsSettingService;

    @Autowired private LogisticsLogDetailService logisticsLogDetailService;

    /**
     * 新增物流记录
     *
     * @author 宋汉林
     */
    @Transactional
    public void add(LogisticsLog entity) {
        // 获取快递100配置信息
        LogisticsSetting logisticsSettingVO =
                logisticsSettingService.getOneByLogisticsType(LogisticsType.KUAI_DI_100);
        if (DefaultFlag.YES == logisticsSettingVO.getSubscribeStatus()) {
            String callBackUrl = logisticsSettingVO.getCallbackUrl();
            if (StringUtils.isBlank(callBackUrl)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050051);
            }
            if (!(callBackUrl.endsWith("/") || callBackUrl.endsWith("\\"))) {
                callBackUrl = callBackUrl + "/";
            }
            entity.setHasDetailsFlag(BoolFlag.NO);
            entity.setEndFlag(BoolFlag.NO);
            entity.setDelFlag(DeleteFlag.NO);
            String id = this.addLogisticsLog(entity).getId();
            callBackUrl += id;
            Map<String, Object> resMap =
                    fetchKuaidi(entity, callBackUrl, logisticsSettingVO.getDeliveryKey());
            entity.setSuccessFlag(
                    Boolean.parseBoolean(Objects.toString(resMap.get("result"), "false"))
                            ? BoolFlag.YES
                            : BoolFlag.NO);
            entity.setMessage(Objects.toString(resMap.get("message")));
            this.update(entity);
        }
    }

    /**
     * 修改物流记录
     *
     * @author 宋汉林
     */
    @Transactional
    public LogisticsLog modify(LogisticsLog entity) {
        LogisticsLog log =
                logisticsLogRepository
                        .findByIdAndDelFlag(entity.getId(), DeleteFlag.NO)
                        .orElseThrow(
                                () ->
                                        new SbcRuntimeException(
                                                CommonErrorCodeEnum.K999999, "物流记录不存在"));

        log.setComOld(entity.getComOld());
        log.setCustomerId(entity.getCustomerId());
        log.setDeliverId(entity.getDeliverId());
        log.setGoodsImg(entity.getGoodsImg());
        log.setGoodsName(entity.getGoodsName());
        log.setLogisticNo(entity.getLogisticNo());
        log.setOrderNo(entity.getOrderNo());
        log.setPhone(entity.getPhone());
        log.setTo(entity.getTo());
        log.setAutoCheck(entity.getAutoCheck());
        log.setCheckTime(LocalDateTime.now());
        log.setComNew(entity.getComNew());
        log.setUpdatePerson(entity.getUpdatePerson());
        log.setSuccessFlag(entity.getSuccessFlag());
        log.setStatus(entity.getStatus());
        log.setState(entity.getState());
        log.setFrom(entity.getFrom());
        log.setEndFlag(entity.getEndFlag());
        log.setIsCheck(entity.getIsCheck());
        log.setMessage(entity.getMessage());

        logisticsLogRepository.save(log);
        return log;
    }

    /**
     * 单个删除物流记录
     *
     * @author 宋汉林
     */
    @Transactional
    public void deleteById(LogisticsLog entity) {
        logisticsLogRepository.save(entity);
    }

    /**
     * 批量删除物流记录
     *
     * @author 宋汉林
     */
    @Transactional
    public void deleteByIdList(List<LogisticsLog> infos) {
        logisticsLogRepository.saveAll(infos);
    }

    /**
     * 单个查询物流记录
     *
     * @author 宋汉林
     */
    public LogisticsLog getOne(String id, Long storeId) {
        return logisticsLogRepository
                .findByIdAndStoreIdAndDelFlag(id, storeId, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "物流记录不存在"));
    }

    /**
     * 分页查询物流记录
     *
     * @author 宋汉林
     */
    public Page<LogisticsLog> page(LogisticsLogQueryRequest queryReq) {
        return logisticsLogRepository.findAll(
                LogisticsLogWhereCriteriaBuilder.build(queryReq), queryReq.getPageRequest());
    }

    /**
     * 列表查询物流记录
     *
     * @author 宋汉林
     */
    public List<LogisticsLog> list(LogisticsLogQueryRequest queryReq) {
        return logisticsLogRepository.findAll(LogisticsLogWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author 宋汉林
     */
    public LogisticsLogVO wrapperVo(LogisticsLog logisticsLog) {
        if (logisticsLog != null) {
            LogisticsLogVO logisticsLogVO = KsBeanUtil.convert(logisticsLog, LogisticsLogVO.class);
            return logisticsLogVO;
        }
        return null;
    }

    /** @param noticeDTO */
    @Transactional
    public void modifyForKuaiDi100(KuaidiHundredNoticeDTO noticeDTO) {
        LogisticsLog log =
                logisticsLogRepository
                        .findByIdAndDelFlag(noticeDTO.getId(), DeleteFlag.NO)
                        .orElse(null);
        if (Objects.nonNull(log)) {
            log.setStatus(noticeDTO.getStatus());
            log.setMessage(noticeDTO.getMessage());
            if (String.valueOf(Constants.yes).equals(noticeDTO.getAutoCheck())) {
                log.setAutoCheck(noticeDTO.getAutoCheck());
            }
            if (StringUtils.isNotEmpty(noticeDTO.getComNew())) {
                log.setComNew(noticeDTO.getComNew());
            }
            if (Objects.nonNull(noticeDTO.getLastResult())) {
                log.setState(noticeDTO.getLastResult().getState());
                if (Objects.nonNull(noticeDTO.getLastResult().getIscheck())) {
                    log.setIsCheck(noticeDTO.getLastResult().getIscheck());
                }
                log.setLogisticNo(noticeDTO.getLastResult().getNu());
                if (CollectionUtils.isNotEmpty(noticeDTO.getLastResult().getData())) {
                    log.setHasDetailsFlag(BoolFlag.YES);
                    log.setLogisticsLogDetails(new ArrayList<>());
                    for (KuaiDiHundredNoticeResultItemDTO dto :
                            noticeDTO.getLastResult().getData()) {
                        LogisticsLogDetail detail = new LogisticsLogDetail();
                        KsBeanUtil.copyPropertiesThird(dto, detail);
                        detail.setTime(dto.getFtime());
                        detail.setLogisticsLogId(log.getId());
                        detail.setDelFlag(DeleteFlag.NO);
                        log.getLogisticsLogDetails().add(detail);
                    }

                    if (Constants.yes.equals(NumberUtils.toInt(log.getIsCheck(), 0))) {
                        log.setCheckTime(
                                DateUtil.parse(
                                        log.getLogisticsLogDetails().get(0).getTime(),
                                        DateUtil.FMT_TIME_1));
                    }
                    if (CollectionUtils.isNotEmpty(log.getLogisticsLogDetails())) {
                        logisticsLogDetailService.addAll(log.getLogisticsLogDetails());
                    }
                }
            }
            logisticsLogRepository.save(log);
        }
    }

    /**
     * 调快递100接口
     *
     * @param logisticsLog
     * @param callBackUrl
     * @param deliveryKey
     * @return
     */
    private Map<String, Object> fetchKuaidi(
            LogisticsLog logisticsLog, String callBackUrl, String deliveryKey) {
        String yes = Constants.yes.toString();
        JSONObject parameters = new JSONObject();
        parameters.put("callbackurl", callBackUrl);
        parameters.put("resultv2", yes);
        if (StringUtils.isBlank(logisticsLog.getComOld())) {
            parameters.put("autoCom", yes);
        }
        parameters.put("phone", logisticsLog.getPhone());

        JSONObject param = new JSONObject();
        param.put("company", StringUtils.trimToEmpty(logisticsLog.getComOld()).toLowerCase());
        param.put("number", StringUtils.trimToEmpty(logisticsLog.getLogisticNo()));
        param.put("from", logisticsLog.getFrom());
        param.put("to", logisticsLog.getTo());
        param.put("key", deliveryKey);
        param.put("parameters", parameters);

        String paramStr = String.format("schema=json&param=%s", param.toJSONString());

        Map<String, Object> resMap = new HashMap<>();
        try {
            String res = WebUtil.post(KUAIDI_URL, paramStr);
            if (StringUtils.isBlank(res)) {
                resMap.put("result", false);
                resMap.put("returnCode", "500");
                resMap.put("message", "快递100服务没有返回信息");
                return resMap;
            }
            return JSON.parseObject(res);
        } catch (IOException e) {
            resMap.put("result", false);
            resMap.put("returnCode", "500");
            resMap.put("message", e.getMessage());
        }
        return resMap;
    }

    /**
     * 新增文档 专门用于数据新增服务,不允许数据修改的时候调用
     *
     * @param log
     */
    public LogisticsLog addLogisticsLog(LogisticsLog log) {
        return logisticsLogRepository.save(log);
    }

    /**
     * 修改文档 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param log
     */
    public LogisticsLog update(LogisticsLog log) {
        return logisticsLogRepository.save(log);
    }

    /**
     * 结束物流信息
     *
     * @param orderNo 订单号
     */
    @Transactional
    public void modifyEndFlagByOrderNo(String orderNo) {
        List<LogisticsLog> logs =
                this.list(LogisticsLogQueryRequest.builder().orderNo(orderNo).build());
        if (CollectionUtils.isNotEmpty(logs)) {
            logs.forEach(
                    l -> {
                        l.setEndFlag(BoolFlag.YES);
                        this.update(l);
                    });
        }
    }

    public LogisticsLog getByOrderNo(String orderNo) {
        return logisticsLogRepository.findByOrderNo(orderNo);
    }
}
