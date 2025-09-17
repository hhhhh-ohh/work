package com.wanmi.sbc.empower.logisticssetting.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingQueryRequest;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.bean.vo.LogisticsSettingVO;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import com.wanmi.sbc.empower.logisticssetting.repository.LogisticsSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>物流配置业务逻辑</p>
 *
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Service("LogisticsSettingService")
public class LogisticsSettingService {

    @Autowired
    private LogisticsSettingRepository logisticsSettingRepository;

    /**
     * 新增物流配置
     *
     * @author 宋汉林
     */
    @Transactional
    public LogisticsSetting add(LogisticsSetting entity) {
        //查询存不存在更新
        logisticsSettingRepository.findByLogisticsTypeAndDelFlag(entity.getLogisticsType(), DeleteFlag.NO)
                        .ifPresent(s -> {
							throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
						});
        logisticsSettingRepository.save(entity);
        return entity;
    }

    /**
     * 修改物流配置
     *
     * @author 宋汉林
     */
    @Transactional(rollbackFor = Exception.class)
    public LogisticsSetting modify(LogisticsSetting entity) {
        //id必须存在，否则无法更新
        if (entity.getId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //查询存不存在更新
        LogisticsSetting oldConfig = logisticsSettingRepository.findById(entity.getId()).orElse(null);
        if (Objects.isNull(oldConfig)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        oldConfig.setCallbackUrl(entity.getCallbackUrl());
        oldConfig.setCustomerKey(entity.getCustomerKey());
        oldConfig.setDeliveryKey(entity.getDeliveryKey());
        oldConfig.setRealTimeStatus(entity.getRealTimeStatus());
        oldConfig.setSubscribeStatus(entity.getSubscribeStatus());
        if(oldConfig.getLogisticsType() == LogisticsType.DADA) {
            oldConfig.setEnableStatus(entity.getEnableStatus());
            oldConfig.setShopNo(entity.getShopNo());
        }
        logisticsSettingRepository.save(oldConfig);
        return oldConfig;
    }

    /**
     * 单个删除物流配置
     *
     * @author 宋汉林
     */
    @Transactional
    public void deleteById(LogisticsSetting entity) {
        logisticsSettingRepository.save(entity);
    }

    /**
     * 批量删除物流配置
     *
     * @author 宋汉林
     */
    @Transactional
    public void deleteByIdList(List<LogisticsSetting> infos) {
        logisticsSettingRepository.saveAll(infos);
    }

    /**
     * 单个查询物流配置
     *
     * @author 宋汉林
     */
    public LogisticsSetting getOne(Long id) {
        return logisticsSettingRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "物流配置不存在"));
    }

    /**
     * 单个查询物流配置
     *
     * @author 宋汉林
     */
    public LogisticsSetting getOneByLogisticsType(LogisticsType logisticsType) {
        return logisticsSettingRepository.findByLogisticsTypeAndDelFlag(logisticsType, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "物流配置不存在"));
    }

    /**
     * 分页查询物流配置
     *
     * @author 宋汉林
     */
    public Page<LogisticsSetting> page(LogisticsSettingQueryRequest queryReq) {
        return logisticsSettingRepository.findAll(
                LogisticsSettingWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询物流配置
     *
     * @author 宋汉林
     */
    public List<LogisticsSetting> list(LogisticsSettingQueryRequest queryReq) {
        return logisticsSettingRepository.findAll(LogisticsSettingWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author 宋汉林
     */
    public LogisticsSettingVO wrapperVo(LogisticsSetting logisticsSetting) {
        if (logisticsSetting != null) {
            return KsBeanUtil.convert(logisticsSetting, LogisticsSettingVO.class);
        }
        return null;
    }
}

