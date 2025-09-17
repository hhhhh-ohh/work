package com.wanmi.sbc.vas.recommend.recommendsystemconfig.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.recommendsystemconfig.RecommendSystemConfigQueryRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendSystemConfigVO;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.model.root.RecommendSystemConfig;
import com.wanmi.sbc.vas.recommend.recommendsystemconfig.repository.RecommendSystemConfigRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>智能推荐配置业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Service("RecommendSystemConfigService")
public class RecommendSystemConfigService {
	@Autowired
	private RecommendSystemConfigRepository recommendSystemConfigRepository;

	/**
	 * 新增智能推荐配置
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendSystemConfig add(RecommendSystemConfig entity) {
		recommendSystemConfigRepository.save(entity);
		return entity;
	}

	/**
	 * 修改智能推荐配置
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendSystemConfig modify(RecommendSystemConfig entity) {
		recommendSystemConfigRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除智能推荐配置
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(RecommendSystemConfig entity) {
		recommendSystemConfigRepository.save(entity);
	}

	/**
	 * 批量删除智能推荐配置
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteByIdList(List<RecommendSystemConfig> infos) {
		recommendSystemConfigRepository.saveAll(infos);
	}

	/**
	 * 单个查询智能推荐配置
	 * @author lvzhenwei
	 */
	public RecommendSystemConfig getOne(Long id){
		return recommendSystemConfigRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "智能推荐配置不存在"));
	}

	/**
	 * 单个查询智能推荐配置
	 * @author lvzhenwei
	 */
	public RecommendSystemConfig getRecommendSystemConfig(RecommendSystemConfigQueryRequest request){
		List<RecommendSystemConfig> recommendSystemConfigList = recommendSystemConfigRepository.findAll(RecommendSystemConfigWhereCriteriaBuilder.build(request));
		RecommendSystemConfig recommendSystemConfig = new RecommendSystemConfig();
		if(CollectionUtils.isNotEmpty(recommendSystemConfigList)){
			recommendSystemConfig = recommendSystemConfigList.get(0);
		}
		return recommendSystemConfig;
	}

	/**
	 * 分页查询智能推荐配置
	 * @author lvzhenwei
	 */
	public Page<RecommendSystemConfig> page(RecommendSystemConfigQueryRequest queryReq){
		return recommendSystemConfigRepository.findAll(
				RecommendSystemConfigWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询智能推荐配置
	 * @author lvzhenwei
	 */
	public List<RecommendSystemConfig> list(RecommendSystemConfigQueryRequest queryReq){
		return recommendSystemConfigRepository.findAll(RecommendSystemConfigWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public RecommendSystemConfigVO wrapperVo(RecommendSystemConfig recommendSystemConfig) {
		if (recommendSystemConfig != null){
			RecommendSystemConfigVO recommendSystemConfigVO = KsBeanUtil.convert(recommendSystemConfig, RecommendSystemConfigVO.class);
			return recommendSystemConfigVO;
		}
		return null;
	}
}

