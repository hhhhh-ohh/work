package com.wanmi.sbc.setting.appexternalconfig.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigAddRequest;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigDelByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigModifyRequest;
import com.wanmi.sbc.setting.appexternallink.repository.AppExternalLinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.appexternalconfig.repository.AppExternalConfigRepository;
import com.wanmi.sbc.setting.appexternalconfig.model.root.AppExternalConfig;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigQueryRequest;
import com.wanmi.sbc.setting.bean.vo.AppExternalConfigVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>AppExternalConfig业务逻辑</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Service("AppExternalConfigService")
public class AppExternalConfigService {
	@Autowired
	private AppExternalConfigRepository appExternalConfigRepository;

	@Autowired
	private AppExternalLinkRepository appExternalLinkRepository;

	/**
	 * 新增AppExternalConfig
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public AppExternalConfig add(@Valid AppExternalConfigAddRequest request) {
		AppExternalConfig entity = KsBeanUtil.convert(request, AppExternalConfig.class);
		entity.setDelFlag(DeleteFlag.NO);
		entity.setCreateTime(LocalDateTime.now());
		entity.setUpdatePerson(entity.getCreatePerson());
		entity.setUpdateTime(LocalDateTime.now());
		appExternalConfigRepository.save(entity);
		return entity;
	}

	/**
	 * 修改AppExternalConfig
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public AppExternalConfig modify(@Valid AppExternalConfigModifyRequest request) {
		AppExternalConfig appExternalConfig = appExternalConfigRepository
				.findByIdAndDelFlag(request.getId(), DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
		appExternalConfig.setAppId(request.getAppId());
		appExternalConfig.setAppName(request.getAppName());
		appExternalConfig.setOriginalId(request.getOriginalId());
		appExternalConfig.setUpdatePerson(request.getUpdatePerson());
		appExternalConfig.setUpdateTime(LocalDateTime.now());
		appExternalConfigRepository.save(appExternalConfig);
		return appExternalConfig;
	}

	/**
	 * 单个删除AppExternalConfig
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteById(@Valid AppExternalConfigDelByIdRequest request) {
		AppExternalConfig appExternalConfig = appExternalConfigRepository
				.findByIdAndDelFlag(request.getId(), DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
		appExternalConfig.setDelFlag(DeleteFlag.YES);
		appExternalConfig.setUpdatePerson(request.getUpdatePerson());
		appExternalConfig.setUpdateTime(LocalDateTime.now());
		appExternalConfigRepository.save(appExternalConfig);
		//删除关联小程序页面链接
		appExternalLinkRepository.deleteByConfigId(request.getId());
	}

	/**
	 * 单个查询AppExternalConfig
	 * @author 黄昭
	 */
	public AppExternalConfig getOne(Long id){
		return appExternalConfigRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "AppExternalConfig不存在"));
	}

	/**
	 * 分页查询AppExternalConfig
	 * @author 黄昭
	 */
	public Page<AppExternalConfig> page(AppExternalConfigQueryRequest queryReq){
		return appExternalConfigRepository.findAll(
				AppExternalConfigWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询AppExternalConfig
	 * @author 黄昭
	 */
	public List<AppExternalConfig> list(AppExternalConfigQueryRequest queryReq){
		return appExternalConfigRepository.findAll(AppExternalConfigWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 黄昭
	 */
	public AppExternalConfigVO wrapperVo(AppExternalConfig appExternalConfig) {
		if (appExternalConfig != null){
			AppExternalConfigVO appExternalConfigVO = KsBeanUtil.convert(appExternalConfig, AppExternalConfigVO.class);
			return appExternalConfigVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 黄昭
	 */
	public Long count(AppExternalConfigQueryRequest queryReq) {
		return appExternalConfigRepository.count(AppExternalConfigWhereCriteriaBuilder.build(queryReq));
	}

	public int getPageTotal(AppExternalConfigQueryRequest queryReq) {
		return appExternalConfigRepository.findAll(AppExternalConfigWhereCriteriaBuilder.build(queryReq)).size();
	}
}

