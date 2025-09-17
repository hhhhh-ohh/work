package com.wanmi.sbc.setting.appexternallink.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkAddRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkDelByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkModifyRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.appexternallink.repository.AppExternalLinkRepository;
import com.wanmi.sbc.setting.appexternallink.model.root.AppExternalLink;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkQueryRequest;
import com.wanmi.sbc.setting.bean.vo.AppExternalLinkVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>AppExternalLink业务逻辑</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Service("AppExternalLinkService")
public class AppExternalLinkService {
	@Autowired
	private AppExternalLinkRepository appExternalLinkRepository;

	/**
	 * 新增AppExternalLink
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public AppExternalLink add(@Valid AppExternalLinkAddRequest request) {
		//查询小程序已关联页面个数
		AppExternalLinkQueryRequest queryRequest = new AppExternalLinkQueryRequest();
		queryRequest.setConfigId(request.getConfigId());
		queryRequest.setDelFlag(DeleteFlag.NO);
		long count = appExternalLinkRepository.count(AppExternalLinkWhereCriteriaBuilder.build(queryRequest));
		//关联页面上限为200
		if (count >= Constants.NUM_200){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070039);
		}
		AppExternalLink entity = KsBeanUtil.convert(request, AppExternalLink.class);
		entity.setCreateTime(LocalDateTime.now());
		entity.setUpdateTime(LocalDateTime.now());
		entity.setUpdatePerson(entity.getCreatePerson());
		appExternalLinkRepository.save(entity);
		return entity;
	}

	/**
	 * 修改AppExternalLink
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public AppExternalLink modify(@Valid AppExternalLinkModifyRequest request) {
		AppExternalLink appExternalLink = appExternalLinkRepository
				.findByIdAndDelFlag(request.getId(), DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
		appExternalLink.setPageName(request.getPageName());
		appExternalLink.setPageLink(request.getPageLink());
		appExternalLink.setUpdateTime(LocalDateTime.now());
		appExternalLink.setUpdatePerson(request.getUpdatePerson());
		appExternalLinkRepository.save(appExternalLink);
		return appExternalLink;
	}

	/**
	 * 单个删除AppExternalLink
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteById(@Valid AppExternalLinkDelByIdRequest request) {
		AppExternalLink appExternalLink = appExternalLinkRepository
				.findByIdAndDelFlag(request.getId(), DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
		appExternalLink.setDelFlag(DeleteFlag.YES);
		appExternalLinkRepository.save(appExternalLink);
	}

	/**
	 * 单个查询AppExternalLink
	 * @author 黄昭
	 */
	public AppExternalLink getOne(Long id){
		return appExternalLinkRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "AppExternalLink不存在"));
	}

	/**
	 * 分页查询AppExternalLink
	 * @author 黄昭
	 */
	public Page<AppExternalLink> page(AppExternalLinkQueryRequest queryReq){
		return appExternalLinkRepository.findAll(
				AppExternalLinkWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询AppExternalLink
	 * @author 黄昭
	 */
	public List<AppExternalLink> list(AppExternalLinkQueryRequest queryReq){
		return appExternalLinkRepository.findAll(AppExternalLinkWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 黄昭
	 */
	public AppExternalLinkVO wrapperVo(AppExternalLink appExternalLink) {
		if (appExternalLink != null){
			AppExternalLinkVO appExternalLinkVO = KsBeanUtil.convert(appExternalLink, AppExternalLinkVO.class);
			return appExternalLinkVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 黄昭
	 */
	public Long count(AppExternalLinkQueryRequest queryReq) {
		return appExternalLinkRepository.count(AppExternalLinkWhereCriteriaBuilder.build(queryReq));
	}
}

