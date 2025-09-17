package com.wanmi.sbc.setting.openapisetting.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.bean.RedisHsetBean;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.openapisetting.*;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingVO;
import com.wanmi.sbc.setting.openapisetting.model.root.OpenApiSetting;
import com.wanmi.sbc.setting.openapisetting.repository.OpenApiSettingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 开放平台api设置业务逻辑
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@Service("OpenApiSettingService")
public class OpenApiSettingService {
    @Autowired private OpenApiSettingRepository openApiSettingRepository;

    @Autowired private RedisUtil redisUtil;

    /**
     * 新增开放平台api设置
     *
     * @author lvzhenwei
     */
    @Transactional
    public OpenApiSetting add(OpenApiSetting entity) {
        entity.setCreateTime(LocalDateTime.now());
        openApiSettingRepository.save(entity);
        if (entity.getAuditState() == AuditStatus.CHECKED) {
            cacheAppKeySecret(
                    OpenApiSettingCacheRequest.builder()
                            .appKey(entity.getAppKey())
                            .appSecret(entity.getAppSecret())
                            .limitingNum(entity.getLimitingNum())
                            .storeId(entity.getStoreId())
                            .contractEndDate(
                                    DateUtil.format(
                                            entity.getContractEndDate(), DateUtil.FMT_TIME_1))
                            .platformType(entity.getPlatformType().toValue())
                            .platformDesc(entity.getPlatformDesc())
                            .build());
        }
        return entity;
    }

    /**
     * 修改开放平台api设置
     *
     * @author lvzhenwei
     */
    @Transactional
    public OpenApiSetting modify(OpenApiSetting entity) {
        entity.setUpdateTime(LocalDateTime.now());
        OpenApiSetting openApiSetting = openApiSettingRepository.findById(entity.getId()).get();
        if (StringUtils.isNotBlank(openApiSetting.getAppSecret())) {
            entity.setAppSecret(openApiSetting.getAppSecret());
        }
        entity.setDelFlag(openApiSetting.getDelFlag());
        openApiSettingRepository.save(entity);
        if (entity.getAuditState() == AuditStatus.CHECKED
                && entity.getDisableState() == EnableStatus.ENABLE) {
            delCacheAppKeySecret(entity.getId());
            cacheAppKeySecret(
                    OpenApiSettingCacheRequest.builder()
                            .appKey(entity.getAppKey())
                            .appSecret(entity.getAppSecret())
                            .limitingNum(entity.getLimitingNum())
                            .storeId(entity.getStoreId())
                            .contractEndDate(
                                    DateUtil.format(
                                            entity.getContractEndDate(), DateUtil.FMT_TIME_1))
                            .platformType(entity.getPlatformType().toValue())
                            .platformDesc(entity.getPlatformDesc())
                            .build());
        }
        return entity;
    }

    /**
     * 单个删除开放平台api设置
     *
     * @author lvzhenwei
     * @date 2021/4/16 3:52 下午
     * @param openApiSettingDelByIdRequest
     * @return void
     */
    @Transactional
    public void deleteById(OpenApiSettingDelByIdRequest openApiSettingDelByIdRequest) {
        openApiSettingRepository.deleteById(openApiSettingDelByIdRequest.getId());
        delCacheAppKeySecret(openApiSettingDelByIdRequest.getId());
    }

    /**
     * 单个查询开放平台api设置
     *
     * @author lvzhenwei
     */
    public OpenApiSetting getOne(Long id) {
        return openApiSettingRepository
                .findByIdAndDelFlag(id, DeleteFlag.NO)
                .orElseThrow(
                        () -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "开放平台api设置不存在"));
    }

    /**
     * 根据店铺ID查询
     *
     * @author wur
     * @date: 2021/4/25 11:20
     * @param storeId 店铺ID
     */
    public OpenApiSetting getByStoreId(Long storeId) {
        return openApiSettingRepository.findByStoreId(storeId);
    }

    /**
     * 分页查询开放平台api设置
     *
     * @author lvzhenwei
     */
    public Page<OpenApiSetting> page(OpenApiSettingQueryRequest queryReq) {
        return openApiSettingRepository.findAll(
                OpenApiSettingWhereCriteriaBuilder.build(queryReq), queryReq.getPageRequest());
    }

    /**
     * 列表查询开放平台api设置
     *
     * @author lvzhenwei
     */
    public List<OpenApiSetting> list(OpenApiSettingQueryRequest queryReq) {
        return openApiSettingRepository.findAll(OpenApiSettingWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 开放平台权限审核
     *
     * @author lvzhenwei
     * @date 2021/4/14 4:23 下午
     * @param request
     * @return void
     */
    @Transactional
    public void checkAuditState(OpenApiSettingCheckAuditStateRequest request) {
        openApiSettingRepository.checkAuditState(
                request.getAuditState(),
                request.getAppSecret(),
                request.getLimitingNum(),
                request.getContractEndDate(),
                request.getId(),
                request.getAppKey());
        OpenApiSetting openApiSetting = openApiSettingRepository.getOne(request.getId());
        cacheAppKeySecret(
                OpenApiSettingCacheRequest.builder()
                        .appKey(openApiSetting.getAppKey())
                        .appSecret(request.getAppSecret())
                        .limitingNum(request.getLimitingNum())
                        .storeId(openApiSetting.getStoreId())
                        .contractEndDate(
                                DateUtil.format(request.getContractEndDate(), DateUtil.FMT_TIME_1))
                        .build());
    }

    /**
     * 审核驳回
     *
     * @author lvzhenwei
     * @date 2021/4/15 7:47 下午
     * @param request
     * @return void
     */
    @Transactional
    public void checkAuditStateReason(OpenApiSettingCheckAuditStateRequest request) {
        openApiSettingRepository.checkAuditStateReason(
                request.getAuditState(), request.getAuditReason(), request.getId());
        delCacheAppKeySecret(request.getId());
    }

    /**
     * @description 开放平台权限禁用启用
     * @author lvzhenwei
     * @date 2021/4/14 4:23 下午
     * @param request
     * @return void
     */
    @Transactional
    public void changeDisableState(OpenApiSettingChangeDisableStateRequest request) {
        openApiSettingRepository.changeDisableState(
                request.getDisableState(), request.getDisableReason(), request.getId());
        if (request.getDisableState() == EnableStatus.ENABLE) {
            OpenApiSetting openApiSetting = openApiSettingRepository.getOne(request.getId());
            cacheAppKeySecret(
                    OpenApiSettingCacheRequest.builder()
                            .appKey(openApiSetting.getAppKey())
                            .appSecret(openApiSetting.getAppSecret())
                            .limitingNum(openApiSetting.getLimitingNum())
                            .storeId(openApiSetting.getStoreId())
                            .contractEndDate(
                                    DateUtil.format(
                                            openApiSetting.getContractEndDate(),
                                            DateUtil.FMT_TIME_1))
                            .build());
        } else {
            delCacheAppKeySecret(request.getId());
        }
    }

    /**
     * @description 开放平台重置app secret
     * @author lvzhenwei
     * @date 2021/4/14 4:23 下午
     * @param request
     * @return void
     */
    @Transactional
    public void resetAppSecret(OpenApiSettingResetSecretRequest request) {
        openApiSettingRepository.resetAppSecret(request.getAppSecret(), request.getId());
        OpenApiSetting openApiSetting = openApiSettingRepository.findById(request.getId()).get();
        if (Objects.isNull(openApiSetting.getContractEndDate())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "开放权限未审核通过，暂不支持此操作！");
        }
        delCacheAppKeySecret(openApiSetting.getId());
        cacheAppKeySecret(
                OpenApiSettingCacheRequest.builder()
                        .appKey(openApiSetting.getAppKey())
                        .appSecret(request.getAppSecret())
                        .limitingNum(openApiSetting.getLimitingNum())
                        .storeId(openApiSetting.getStoreId())
                        .contractEndDate(
                                DateUtil.format(
                                        openApiSetting.getContractEndDate(), DateUtil.FMT_TIME_1))
                        .build());
    }

    /**
     * 将实体包装成VO
     *
     * @author lvzhenwei
     */
    public OpenApiSettingVO wrapperVo(OpenApiSetting openApiSetting) {
        if (openApiSetting != null) {
            OpenApiSettingVO openApiSettingVO =
                    KsBeanUtil.convert(openApiSetting, OpenApiSettingVO.class);
            return openApiSettingVO;
        }
        return null;
    }

    /**
     * 将app key以及app secret保存到redis，当审核通过以及启用的情况下进行缓存
     *
     * @author lvzhenwei
     * @date 2021/4/16 2:52 下午
     * @param addReq
     * @return void
     */
    private void cacheAppKeySecret(OpenApiSettingCacheRequest addReq) {
        Map<String, Object> openApiMap = JSONObject.parseObject(JSON.toJSONString(addReq));
        String key = RedisKeyConstant.OPEN_API_SETTING_KEY + addReq.getAppKey();
        List<RedisHsetBean> fieldValues = new ArrayList<>();
        openApiMap.forEach(
                (k, v) -> {
                    RedisHsetBean redisHsetBean = new RedisHsetBean();
                    redisHsetBean.setField(k);
                    redisHsetBean.setValue(v.toString());
                    fieldValues.add(redisHsetBean);
                });
        redisUtil.hsetPipeline(key, fieldValues);
    }

    /**
     * 删除以及禁用时删除对应的缓存,更新操作以及重置secret时先删除，在重新缓存
     *
     * @author lvzhenwei
     * @date 2021/4/16 3:16 下午
     * @param id
     * @return void
     */
    private void delCacheAppKeySecret(Long id) {
        OpenApiSettingByIdRequest idReq = new OpenApiSettingByIdRequest();
        idReq.setId(id);
        OpenApiSetting openApiSetting = openApiSettingRepository.getOne(id);
        String key = RedisKeyConstant.OPEN_API_SETTING_KEY + openApiSetting.getAppKey();
        redisUtil.delete(key);
    }

    /**
     * 初始化缓存
     *
     * @author wur
     * @date: 2021/4/27 11:39
     */
    public void initOpenApiCache() {
        // 查询审核通过&启用&未删除的数据
        OpenApiSettingQueryRequest queryReq =
                OpenApiSettingQueryRequest.builder()
                        .auditState(AuditStatus.CHECKED)
                        .disableState(EnableStatus.ENABLE)
                        .delFlag(DeleteFlag.NO)
                        .build();
        List<OpenApiSetting> openApiList =
                openApiSettingRepository.findAll(
                        OpenApiSettingWhereCriteriaBuilder.build(queryReq));
        if (CollectionUtils.isEmpty(openApiList)) {
            return;
        }
        // 逐条更新对应的缓存
        openApiList.forEach(
                openApiSetting ->
                        cacheAppKeySecret(
                                OpenApiSettingCacheRequest.builder()
                                        .appKey(openApiSetting.getAppKey())
                                        .appSecret(openApiSetting.getAppSecret())
                                        .limitingNum(openApiSetting.getLimitingNum())
                                        .storeId(openApiSetting.getStoreId())
                                        .contractEndDate(
                                                DateUtil.format(
                                                        openApiSetting.getContractEndDate(),
                                                        DateUtil.FMT_TIME_1))
                                        .build()));
    }


    /**
     * 查询BOSS平台设置
     *
     */
    public OpenApiSetting getBossSetting() {
        return openApiSettingRepository.findBossSetting();
    }
}
