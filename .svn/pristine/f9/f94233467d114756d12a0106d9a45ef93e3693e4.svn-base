package com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationModifyIsOpenRequest;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root.RecommendPositionConfiguration;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.repository.RecommendPositionConfigurationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>推荐坑位设置业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:14:13
 */
@Service("RecommendPositionConfigurationService")
public class RecommendPositionConfigurationService {
	@Autowired
	private RecommendPositionConfigurationRepository recommendPositionConfigurationRepository;

	/**
	 * 新增推荐坑位设置
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendPositionConfiguration add(RecommendPositionConfiguration entity) {
		recommendPositionConfigurationRepository.save(entity);
		return entity;
	}

	/**
	 * 修改推荐坑位设置
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendPositionConfiguration modify(RecommendPositionConfiguration entity) {
		recommendPositionConfigurationRepository.save(entity);
		return entity;
	}

	@Transactional
	public int modifyIsOpen(RecommendPositionConfigurationModifyIsOpenRequest request){
		return recommendPositionConfigurationRepository.updateIsOpen(request.getIsOpen(),request.getId());
	}

	/**
	 * 单个删除推荐坑位设置
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		recommendPositionConfigurationRepository.deleteById(id);
	}

	/**
	 * 批量删除推荐坑位设置
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
//		recommendPositionConfigurationRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询推荐坑位设置
	 * @author lvzhenwei
	 */
	public RecommendPositionConfiguration getOne(Long id){
		return recommendPositionConfigurationRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "推荐坑位设置不存在"));
	}

	/**
	 * 分页查询推荐坑位设置
	 * @author lvzhenwei
	 */
	public Page<RecommendPositionConfiguration> page(RecommendPositionConfigurationQueryRequest queryReq){
		return recommendPositionConfigurationRepository.findAll(
				RecommendPositionConfigurationWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询推荐坑位设置
	 * @author lvzhenwei
	 */
	public List<RecommendPositionConfiguration> list(RecommendPositionConfigurationQueryRequest queryReq){
		return recommendPositionConfigurationRepository.findAll(RecommendPositionConfigurationWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public RecommendPositionConfigurationVO wrapperVo(RecommendPositionConfiguration recommendPositionConfiguration) {
		if (recommendPositionConfiguration != null){
			RecommendPositionConfigurationVO recommendPositionConfigurationVO = KsBeanUtil.convert(recommendPositionConfiguration, RecommendPositionConfigurationVO.class);
			if (StringUtils.isNotBlank(recommendPositionConfiguration.getContent())){
				List<String> content = Arrays.asList(recommendPositionConfiguration.getContent().split(","));
				recommendPositionConfigurationVO.setContent(content);
			}
			return recommendPositionConfigurationVO;
		}
		return null;
	}
}

