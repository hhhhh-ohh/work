package com.wanmi.sbc.setting.systemresource.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceQueryRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import com.wanmi.sbc.setting.systemresource.model.root.SystemResource;
import com.wanmi.sbc.setting.systemresource.repository.SystemResourceRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>平台素材资源业务逻辑</p>
 *
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Service("SystemResourceService")
public class SystemResourceService {
    @Autowired
    private SystemResourceRepository systemResourceRepository;

    /**
     * 新增平台素材资源
     *
     * @author lq
     */
    @Transactional
    public SystemResource add(SystemResource entity) {
        // storeId 没传，默认查系统资源
        if (null == entity.getStoreId()){
            entity.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        }
        entity.setDelFlag(DeleteFlag.NO);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        systemResourceRepository.save(entity);
        return entity;
    }

    /**
     * 修改平台素材资源
     *
     * @author lq
     */
    @Transactional
    public SystemResource modify(SystemResource newResource) {
        SystemResource oldResource = systemResourceRepository.findById(newResource.getResourceId()).orElse(null);
        if (oldResource == null || oldResource.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070062);
        }
        if (null == oldResource.getStoreId()){
            oldResource.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        }
        //更新素材
        newResource.setUpdateTime(LocalDateTime.now());
        KsBeanUtil.copyProperties(newResource, oldResource);
        systemResourceRepository.save(oldResource);
        return oldResource;
    }

    /**
     * 单个删除平台素材资源
     *
     * @author lq
     */
    @Transactional
    public void deleteById(Long id) {
        systemResourceRepository.deleteById(id);
    }

    /**
     * 批量删除平台素材资源
     *
     * @author lq
     */
    @Transactional
    public void deleteByIdList(List<Long> ids) {
        systemResourceRepository.deleteByIdList(ids);
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<SystemResourceVO> findByIdList(List<Long> ids) {
        List<SystemResource> systemResourceList = systemResourceRepository.findAllById(ids);
        return KsBeanUtil.convert(systemResourceList, SystemResourceVO.class);
    }


    /**
     * 单个查询平台素材资源
     *
     * @author lq
     */
    public SystemResource getById(Long id) {
        return systemResourceRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询平台素材资源
     *
     * @author lq
     */
    public Page<SystemResource> page(SystemResourceQueryRequest queryReq) {
        if (null == queryReq.getStoreId()){
            queryReq.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        }
        return systemResourceRepository.findAll(
                SystemResourceWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询平台素材资源
     *
     * @author lq
     */
    public List<SystemResource> list(SystemResourceQueryRequest queryReq) {
        // storeId 没传，默认查系统资源
        if (null == queryReq.getStoreId()){
            queryReq.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        }
        return systemResourceRepository.findAll(
                SystemResourceWhereCriteriaBuilder.build(queryReq),
                queryReq.getSort());
    }

    /**
     * 将实体包装成VO
     *
     * @author lq
     */
    public SystemResourceVO wrapperVo(SystemResource systemResource) {
        if (systemResource != null) {
            SystemResourceVO systemResourceVO = new SystemResourceVO();
            KsBeanUtil.copyPropertiesThird(systemResource, systemResourceVO);
            return systemResourceVO;
        }
        return null;
    }


    /**
     * 批量更新素材的分类
     *
     * @param resourceIds
     */
    @Transactional
    public void updateCateByIds(Long cateId, List<Long> resourceIds, Long storeId) {
        // storeId 没传，默认查系统资源
        if (null == storeId){
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }
        systemResourceRepository.updateCateByIds(cateId, resourceIds, storeId);
    }

    /**
     * 逻辑删除素材
     *
     * @param resourceIds
     */
    @Transactional
    public List<SystemResourceVO> delete(List<Long> resourceIds) {
        SystemResourceQueryRequest queryRequest = SystemResourceQueryRequest.builder()
                .resourceIdList(resourceIds).build();
        List<SystemResource> resources = systemResourceRepository.findAll(SystemResourceWhereCriteriaBuilder.build(queryRequest));
        if (CollectionUtils.isNotEmpty(resources)) {
            //逻辑删除素材库
            systemResourceRepository.deleteByIdList(resourceIds);
        }
        List<SystemResource> systemResourceList = resources.stream().peek(entity->entity.setDelFlag(DeleteFlag.YES)).collect(Collectors.toList());
        return KsBeanUtil.convert(systemResourceList, SystemResourceVO.class);
    }

    /**
     * 分页查询平台素材资源
     *
     * @author lq
     */
    public Page<SystemResource> initEsPage(SystemResourceQueryRequest queryReq) {
        return systemResourceRepository.findAll(
                SystemResourceWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 单个查询平台素材资源
     *
     * @author lq
     */
    public Integer getMaxSort() {
        return systemResourceRepository.getMaxSort();
    }

}
