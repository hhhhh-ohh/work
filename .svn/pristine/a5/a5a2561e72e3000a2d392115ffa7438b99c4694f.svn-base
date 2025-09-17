package com.wanmi.sbc.setting.helpcenterarticle.service;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.HelpCenterArticleChangeSolveTypeRequest;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordQueryRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.helpcenterarticlecate.model.root.HelpCenterArticleCate;
import com.wanmi.sbc.setting.helpcenterarticlecate.repository.HelpCenterArticleCateRepository;
import com.wanmi.sbc.setting.helpcenterarticlerecord.model.root.HelpCenterArticleRecord;
import com.wanmi.sbc.setting.helpcenterarticlerecord.repository.HelpCenterArticleRecordRepository;
import com.wanmi.sbc.setting.helpcenterarticlerecord.service.HelpCenterArticleRecordService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.helpcenterarticle.repository.HelpCenterArticleRepository;
import com.wanmi.sbc.setting.helpcenterarticle.model.root.HelpCenterArticle;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.HelpCenterArticleQueryRequest;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>帮助中心文章信息业务逻辑</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Service("HelpCenterArticleService")
public class HelpCenterArticleService {

	@Autowired
	private HelpCenterArticleRepository helpCenterArticleRepository;

	@Autowired
	private HelpCenterArticleRecordService helpCenterArticleRecordService;

	@Autowired
	private HelpCenterArticleCateRepository helpCenterArticleCateRepository;

	private static final Integer DAY_HOURS = 24;

	/**
	 * 新增帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public HelpCenterArticle add(HelpCenterArticle entity) {
		HelpCenterArticleCate helpCenterArticleCate = helpCenterArticleCateRepository.getById(entity.getArticleCateId());
		if(Objects.isNull(helpCenterArticleCate)){
			new SbcRuntimeException(SettingErrorCodeEnum.K070108);
		}
		helpCenterArticleRepository.save(entity);
		return entity;
	}

	/**
	 * 修改帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public HelpCenterArticle modify(HelpCenterArticle entity) {
		HelpCenterArticleCate helpCenterArticleCate = helpCenterArticleCateRepository.getById(entity.getArticleCateId());
		if(Objects.isNull(helpCenterArticleCate)){
			new SbcRuntimeException(SettingErrorCodeEnum.K070108);
		}
		helpCenterArticleRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteById(HelpCenterArticle entity) {
		helpCenterArticleRepository.save(entity);
	}

	/**
	 * 批量删除帮助中心文章信息
	 * @author 吕振伟
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		helpCenterArticleRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询帮助中心文章信息
	 * @author 吕振伟
	 */
	public HelpCenterArticle getOne(Long id){
		return helpCenterArticleRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(SettingErrorCodeEnum.K070107));
	}

	/**
	 * 分页查询帮助中心文章信息
	 * @author 吕振伟
	 */
	public Page<HelpCenterArticle> page(HelpCenterArticleQueryRequest queryReq){
		return helpCenterArticleRepository.findAll(
				HelpCenterArticleWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询帮助中心文章信息
	 * @author 吕振伟
	 */
	public List<HelpCenterArticle> list(HelpCenterArticleQueryRequest queryReq){
		return helpCenterArticleRepository.findAll(HelpCenterArticleWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 吕振伟
	 */
	public HelpCenterArticleVO wrapperVo(HelpCenterArticle helpCenterArticle) {
		if (helpCenterArticle != null){
			HelpCenterArticleVO helpCenterArticleVO = KsBeanUtil.convert(helpCenterArticle, HelpCenterArticleVO.class);
			return helpCenterArticleVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author 吕振伟
	 */
	public Long count(HelpCenterArticleQueryRequest queryReq) {
		return helpCenterArticleRepository.count(HelpCenterArticleWhereCriteriaBuilder.build(queryReq));
	}

	@Transactional
	public void addViewNum(Long id){
		helpCenterArticleRepository.addViewNum(id);
	}

	/**
	 * 增加文章解决次数
	 * @author 吕振伟
	 */
	@Transactional
	public void clickSolve(HelpCenterArticleChangeSolveTypeRequest request){
		this.changeSolveType(request);
	}

	/**
	 * 增加文章未解决次数
	 * @author 吕振伟
	 */
	@Transactional
	public void clickUnresolved(HelpCenterArticleChangeSolveTypeRequest request){
		this.changeSolveType(request);
	}

	private void changeSolveType(HelpCenterArticleChangeSolveTypeRequest request){
		List<HelpCenterArticleRecord> helpCenterArticleRecordList = helpCenterArticleRecordService.list(
				HelpCenterArticleRecordQueryRequest.builder().articleId(request.getId()).customerId(request.getCustomerId()).build());
		HelpCenterArticleRecord helpCenterArticleRecord = new HelpCenterArticleRecord();
		if(CollectionUtils.isEmpty(helpCenterArticleRecordList)){
			helpCenterArticleRecord.setArticleId(request.getId());
			helpCenterArticleRecord.setCustomerId(request.getCustomerId());
			helpCenterArticleRecord.setSolveType(request.getSolveType());
			helpCenterArticleRecord.setSolveTime(LocalDateTime.now());
			helpCenterArticleRecord.setDelFlag(DeleteFlag.NO);
			helpCenterArticleRecord.setCreatePerson(request.getCustomerId());
			helpCenterArticleRecord.setCreateTime(LocalDateTime.now());
			helpCenterArticleRecord.setUpdatePerson(request.getCustomerId());
			helpCenterArticleRecord.setUpdateTime(LocalDateTime.now());
		} else {
			helpCenterArticleRecord = helpCenterArticleRecordList.get(0);
			Duration duration = Duration.between(helpCenterArticleRecord.getSolveTime(),LocalDateTime.now());
			long hours = duration.toHours();
			if(hours < DAY_HOURS){
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070114);
			}
			helpCenterArticleRecord.setSolveType(request.getSolveType());
			helpCenterArticleRecord.setSolveTime(LocalDateTime.now());
		}
		helpCenterArticleRecordService.add(helpCenterArticleRecord);
		if(request.getSolveType() == DefaultFlag.NO){
			helpCenterArticleRepository.clickUnresolved(request.getId());
		}
		if(request.getSolveType() == DefaultFlag.YES){
			helpCenterArticleRepository.clickSolve(request.getId());
		}
	}
}

