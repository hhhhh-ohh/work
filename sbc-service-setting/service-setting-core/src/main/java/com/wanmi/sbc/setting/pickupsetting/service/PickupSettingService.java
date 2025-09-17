package com.wanmi.sbc.setting.pickupsetting.service;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.ConfigRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingAuditRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingDefaultAddressRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingIdsRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingQueryRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingIdsResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.setting.config.Config;
import com.wanmi.sbc.setting.config.ConfigRepository;
import com.wanmi.sbc.setting.pickupsetting.model.root.PickupSetting;
import com.wanmi.sbc.setting.pickupsetting.repository.PickupSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>pickup_setting业务逻辑</p>
 *
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Service("PickupSettingService")
public class PickupSettingService {

    @Autowired
    private PickupSettingRepository pickupSettingRepository;

    @Autowired
    private ConfigRepository configRepository;

    /**
     * 新增pickup_setting
     *
     * @author 黄昭
     */
    @Transactional
    public PickupSetting add(PickupSetting entity) {
        pickupSettingRepository.save(entity);
        return entity;
    }

    /**
     * 修改pickup_setting
     *
     * @author 黄昭
     */
    @Transactional
    public PickupSetting modify(PickupSetting entity) {
        pickupSettingRepository.save(entity);
        return entity;
    }

    /**
     * 单个删除pickup_setting
     *
     * @author 黄昭
     */
    @Transactional
    public void deleteById(PickupSetting entity) {
        pickupSettingRepository.save(entity);
    }

    /**
     * 单个查询pickup_setting
     *
     * @author 黄昭
     */
    public PickupSetting getOne(Long id) {
        return pickupSettingRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "自提点不存在"));
    }

    /**
     * 分页查询pickup_setting
     *
     * @author 黄昭
     */
    public Page<PickupSetting> page(PickupSettingQueryRequest queryReq) {
        return pickupSettingRepository.findAll(
                PickupSettingWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询pickup_setting
     *
     * @author 黄昭
     */
    public List<PickupSetting> list(PickupSettingQueryRequest queryReq) {
        Sort sort = queryReq.getSort();
        if (Objects.nonNull(sort)) {
            return pickupSettingRepository.findAll(PickupSettingWhereCriteriaBuilder.build(queryReq), sort);
        }
        return pickupSettingRepository.findAll(PickupSettingWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author 黄昭
     */
    public PickupSettingVO wrapperVo(PickupSetting pickupSetting) {
        if (pickupSetting != null) {
            PickupSettingVO pickupSettingVO = KsBeanUtil.convert(pickupSetting, PickupSettingVO.class);
            return pickupSettingVO;
        }
        return null;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void pickupSettingAudit(PickupSettingAuditRequest request) {
        if (request.getAuditStatus() != null) {

            if (AuditStatus.CHECKED.equals(AuditStatus.fromValue(request.getAuditStatus()))){
                pickupSettingRepository.updateAuditStatusById(request.getId()
                        ,request.getAuditStatus(),null,request.getUpdatePerson());
            }
            if (AuditStatus.NOT_PASS.equals(AuditStatus.fromValue(request.getAuditStatus()))){
                pickupSettingRepository.updateAuditStatusById(request.getId()
                        ,request.getAuditStatus(),request.getAuditReason(),request.getUpdatePerson());
            }
        }
        if (request.getEnableStatus() != null) {
            int isDefaultAddress = 0;
            if (EnableStatus.ENABLE.toValue() == request.getEnableStatus()) {
                //查询当前商家有无自提点
                PickupSetting pickupSetting = pickupSettingRepository.findById(request.getId()).orElse(null);
                if (Objects.nonNull(pickupSetting)) {
                    List<PickupSetting> settings = pickupSettingRepository
                            .findAll(PickupSettingWhereCriteriaBuilder
                                    .build(PickupSettingQueryRequest.builder()
                                            .storeId(pickupSetting.getStoreId())
                                            .enableStatus(Integer.valueOf(EnableStatus.ENABLE.toValue()))
                                            .delFlag(DeleteFlag.NO).build()));
                    if (CollectionUtils.isEmpty(settings)) {
                        isDefaultAddress = 1;
                    }
                }
            }
            pickupSettingRepository.updateEnableStatusById(request.getId(),request.getEnableStatus(),request.getUpdatePerson(),isDefaultAddress);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void pickupSettingDefaultAddress(PickupSettingDefaultAddressRequest request) {
        // 将默认自提点改为非默认自提点
        pickupSettingRepository.updateDefaultAddressByState(request.getUpdatePerson(), request.getBaseStoreId());
        // 更新为默认自提点
        pickupSettingRepository.updateDefaultAddressById(request.getId(), request.getUpdatePerson());
    }

    @Transactional(rollbackFor = {Exception.class})
    public void pickupSettingConfig(ConfigRequest request) {
        configRepository.updateStatusByTypeAndConfigKey(request.getConfigType()
                , request.getConfigKey().toString(), request.getStatus(), null);
    }

    public PickupSettingConfigResponse pickupSettingConfigShow() {

        PickupSettingConfigResponse response = new PickupSettingConfigResponse();
        List<Config> configs = configRepository.findByConfigKeyAndDelFlag(ConfigKey.PICKUP_SETTING.toString(), DeleteFlag.NO);
        configs.forEach(config -> {
            if(ConfigType.SELF_MERCHANT.toValue().equals(config.getConfigType())){
                response.setSelfMerchantStatus(config.getStatus());
            }
            if(ConfigType.THIRD_MERCHANT.toValue().equals(config.getConfigType())){
                response.setThirdMerchantStatus(config.getStatus());
            }
            if(ConfigType.STORE.toValue().equals(config.getConfigType())){
                response.setStoreStatus(config.getStatus());
            }
        });
        return response;
    }


    public PickupSettingIdsResponse getNoEmployeePickupIdsByStoreId(PickupSettingIdsRequest request) {

        List<Long> pickupIds = pickupSettingRepository.findNoEmployeePickupIds(request.getStoreId());
        return new PickupSettingIdsResponse(pickupIds);
    }

    public Long total(PickupSettingQueryRequest queryReq) {
        return pickupSettingRepository.count(PickupSettingWhereCriteriaBuilder.build(queryReq));
    }

    public PickupSettingIdsResponse getPickupIdsByEmployeeId(PickupSettingIdsRequest request) {
        return new PickupSettingIdsResponse(pickupSettingRepository.findByEmployeeId(request.getEmployeeId()));
    }

    public List<PickupSetting> getPickupIdsByName(String name){
        return pickupSettingRepository.findByNameAndDelFlag(name, DeleteFlag.NO);
    }

    public PickupSettingConfigResponse getWhetherOpenMap() {
        PickupSettingConfigResponse response = new PickupSettingConfigResponse();
        List<Config> configs = configRepository.getWhetherOpenMap(ConfigKey.PICKUP_SETTING.toString(),ConfigType.WHETHER_OPEN_MAP.toValue());
        configs.forEach(config -> {
            if (ConfigType.WHETHER_OPEN_MAP.toValue().equals(config.getConfigType())) {
                response.setMapStatus(config.getStatus());
            }
        });
        return response;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modifyMapSetting(ConfigRequest request) {
        configRepository.updateStatusByTypeAndConfigKey(request.getConfigType()
                , request.getConfigKey().toString(), request.getStatus(), null);
    }
}

