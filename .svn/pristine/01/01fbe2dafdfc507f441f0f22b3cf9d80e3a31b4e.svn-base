package com.wanmi.sbc.setting.recommendcate.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateDelByIdRequest;
import com.wanmi.sbc.setting.bean.vo.RecommendCateSortVO;
import com.wanmi.sbc.setting.recommend.repository.RecommendRepository;
import com.wanmi.sbc.setting.recommendcate.model.entity.RecommendCateSort;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.recommendcate.repository.RecommendCateRepository;
import com.wanmi.sbc.setting.recommendcate.model.root.RecommendCate;
import com.wanmi.sbc.setting.api.request.recommendcate.RecommendCateQueryRequest;
import com.wanmi.sbc.setting.bean.vo.RecommendCateVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>笔记分类表业务逻辑</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Service("RecommendCateService")
public class RecommendCateService {
	@Autowired
	private RecommendCateRepository recommendCateRepository;

	@Autowired
	private RecommendRepository recommendRepository;

	/**
	 * 新增笔记分类表
	 * @author 王超
	 */
	@Transactional
	public RecommendCate add(RecommendCate entity) {

		int count = recommendCateRepository.selectCountByFlag();
		if(count > Constants.NUM_14){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		entity.setCateSort(Constants.ZERO);
		entity.setDelFlag(DeleteFlag.NO);
		entity.setCreateTime(LocalDateTime.now());
		recommendCateRepository.save(entity);
		return entity;
	}

	/**
	 * 修改笔记分类表
	 * @author 王超
	 */
	@Transactional
	public RecommendCate modify(RecommendCate entity) {

		RecommendCate recommendCate = getOne(entity.getCateId());
		if(Objects.isNull(recommendCate)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		recommendCate.setCateName(entity.getCateName());
		recommendCate.setUpdateTime(LocalDateTime.now());
		recommendCateRepository.save(recommendCate);
		return entity;
	}

	/**
	 * 单个删除笔记分类表
	 * @author 王超
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteById(@Valid RecommendCateDelByIdRequest request) {
		RecommendCate recommendCate = recommendCateRepository.findById(request.getCateId())
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003));
		recommendCate.setDelFlag(DeleteFlag.YES);
		recommendRepository.updateCateId(request.getCateId());
		recommendRepository.updateNewCateId(request.getCateId());
		recommendCateRepository.save(recommendCate);
	}

	/**
	 * 批量删除笔记分类表
	 * @author 王超
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		recommendCateRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询笔记分类表
	 * @author 王超
	 */
	public RecommendCate getOne(Long id){
		return recommendCateRepository.findByCateIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "笔记分类表不存在"));
	}

	/**
	 * 分页查询笔记分类表
	 * @author 王超
	 */
	public Page<RecommendCate> page(RecommendCateQueryRequest queryReq){
		return recommendCateRepository.findAll(
				RecommendCateWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询笔记分类表
	 * @author 王超
	 */
	public List<RecommendCate> list(RecommendCateQueryRequest queryReq){
		return recommendCateRepository.findAllBySort();
	}

	/**
	 * 将实体包装成VO
	 * @author 王超
	 */
	public RecommendCateVO wrapperVo(RecommendCate recommendCate) {
		if (recommendCate != null){
			RecommendCateVO recommendCateVO = KsBeanUtil.convert(recommendCate, RecommendCateVO.class);
			return recommendCateVO;
		}
		return null;
	}

	/**
	 * 将实体包装成VO
	 *
	 * @author 王超
	 */
	public RecommendCateSort wrapperSortVo(RecommendCateSortVO recommendCateSort) {
		if (recommendCateSort != null) {
			RecommendCateSort grouponCateSort = new RecommendCateSort();
			KsBeanUtil.copyProperties(recommendCateSort, grouponCateSort);
			return grouponCateSort;
		}
		return null;
	}

	/**
	 * 分类拖拽排序
	 *
	 * @param sortRequestList
	 */
	@Transactional
	public void dragSort(List<RecommendCateSort> sortRequestList) {
		if (CollectionUtils.isEmpty(sortRequestList)
				|| sortRequestList.size() > Constants.NUM_15) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		sortRequestList.forEach(cate ->
				recommendCateRepository.updateCateSort(cate.getCatId(), cate.getCateSort()));
	}

}

