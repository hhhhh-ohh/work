package com.wanmi.sbc.setting.systemconfig.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.SeoSettingModifyRequest;
import com.wanmi.sbc.setting.api.request.systemconfig.PayingMemberModifyRequest;
import com.wanmi.sbc.setting.api.request.systemconfig.SystemConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import com.wanmi.sbc.setting.systemconfig.model.root.SystemConfig;
import com.wanmi.sbc.setting.systemconfig.repository.SystemConfigRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>系统配置表业务逻辑</p>
 *
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Service("SystemConfigService")
public class SystemConfigService {
    @Autowired
    private SystemConfigRepository systemConfigRepository;


    /**
     * 修改系统配置表
     *
     * @author yang
     */
    @Transactional
    public SystemConfig modify(SystemConfig entity) {
        systemConfigRepository.save(entity);
        return entity;
    }

    /**
     * 修改esgoods系统配置表
     *
     * @author yang
     */
    @Transactional
    public SystemConfig modifyEsGoodsBoost(String esGoodsBoost) {
        SystemConfigQueryRequest queryRequest = SystemConfigQueryRequest.builder()
                .configType(ConfigType.ES_QUERY_BOOST.toValue())
                .delFlag(DeleteFlag.NO)
                .build();
        List<SystemConfig> systemConfigList = list(queryRequest);
        if (!CollectionUtils.isEmpty(systemConfigList)) {
            SystemConfig systemConfig = systemConfigList.get(0);
            if (Objects.nonNull(systemConfig.getUpdateTime())
                    && systemConfig.getUpdateTime().toLocalDate().isEqual(LocalDate.now())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "每天只允许修改一次");
            }
            systemConfig.setContext(esGoodsBoost);
            systemConfig.setUpdateTime(LocalDateTime.now());
            systemConfigRepository.save(systemConfig);
            return systemConfig;
        } else {
            SystemConfig addSys = new SystemConfig();
            addSys.setConfigKey(ConfigKey.ES_QUERY_SETTING.toValue());
            addSys.setConfigType(ConfigType.ES_QUERY_BOOST.toValue());
            addSys.setConfigName("es查询权重配置");
            addSys.setUpdateTime(null);
            addSys.setRemark("配置es权重设置");
            addSys.setStatus(NumberUtils.INTEGER_ONE);
            addSys.setContext(esGoodsBoost);
            addSys.setCreateTime(LocalDateTime.now());
            addSys.setDelFlag(DeleteFlag.NO);
            systemConfigRepository.save(addSys);
            return  addSys;
        }
    }


    public String findContextByConfigType() {
        return systemConfigRepository.findContextByConfigType(ConfigType.ES_QUERY_BOOST.toValue());
    }

    public String findContextByConfigTypeAndConfigKey() {
        return systemConfigRepository.findContextByConfigTypeAndConfigKey(ConfigKey.PLATFORM_ADDRESS.toString(),ConfigType.POPULAR_CITY.toValue());
    }

    /**
     * 单个查询系统配置表
     *
     * @author yang
     */
    public SystemConfig getById(Long id) {
        return systemConfigRepository.findById(id).orElse(null);
    }


    /**
     * 列表查询系统配置表
     *
     * @author yang
     */
    public List<SystemConfig> list(SystemConfigQueryRequest queryReq) {
        return systemConfigRepository.findAll(
                SystemConfigWhereCriteriaBuilder.build(queryReq),
                queryReq.getSort());
    }

    /**
     * 查询可用的云配置
     * @return
     */
    public SystemConfig getAvailableYun() {
        SystemConfigQueryRequest queryRequest = SystemConfigQueryRequest.builder()
                .configKey(ConfigKey.RESOURCESERVER.toString())
                .status(EnableStatus.ENABLE.toValue())
                .delFlag(DeleteFlag.NO)
                .build();
        List<SystemConfig> systemConfigList = list(queryRequest);
        if (Objects.nonNull(systemConfigList) && systemConfigList.size() > 0) {
            return systemConfigList.get(0);
        } else {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070040);
        }
    }

    /**
     * 将实体包装成VO
     *
     * @author yang
     */
    public SystemConfigVO wrapperVo(SystemConfig systemConfig) {
        if (systemConfig != null) {
            SystemConfigVO systemConfigVO = new SystemConfigVO();
            KsBeanUtil.copyPropertiesThird(systemConfig, systemConfigVO);
            return systemConfigVO;
        }
        return null;
    }

    public SystemConfig findByConfigKeyAndConfigType(String configKey,
                                                String configType){
        return systemConfigRepository.findByConfigKeyAndConfigTypeAndDelFlag(configKey, configType, DeleteFlag.NO);
    }

    /**
     * 编辑付费会员配置
     * @param request
     */
    @Transactional
    public void modifyPayingMemberSetting(PayingMemberModifyRequest request){
        SystemConfig config = systemConfigRepository.findByConfigKeyAndConfigTypeAndDelFlag(ConfigType.PAYING_MEMBER.toValue(), ConfigType.PAYING_MEMBER.toValue(), DeleteFlag.NO);
        //首次开启设置为true
        boolean openFlag = Boolean.valueOf(JSONObject.parseObject(config.getContext()).get("openFlag").toString());
        if (!openFlag && NumberUtils.INTEGER_ONE.equals(request.getEnable())) {
            request.setOpenFlag(true);
        } else {
            request.setOpenFlag(openFlag);
        }
        config.setContext(JSONObject.toJSONString(request));
        systemConfigRepository.save(config);
    }

    /**
     * 修改SEO设置
     * @param request
     */
    public void modifySeoSetting(SeoSettingModifyRequest request) {
        // 查询配置
        SystemConfig config = systemConfigRepository.findByConfigKeyAndConfigTypeAndDelFlag(ConfigType.SEO_SETTING.toValue(), ConfigKey.SEO_SETTING.toValue(), DeleteFlag.NO);
        if (Objects.isNull(config)) {
            // 若不存在，则初始化
            config = new SystemConfig();
            config.setConfigName("SEO设置");
            config.setConfigKey(ConfigKey.SEO_SETTING.toValue());
            config.setConfigType(ConfigType.SEO_SETTING.toValue());
            config.setStatus(BoolFlag.YES.toValue());
            config.setDelFlag(DeleteFlag.NO);
            config.setCreateTime(LocalDateTime.now());
        }
        config.setContext(JSONObject.toJSONString(request));
        systemConfigRepository.save(config);
    }
}
