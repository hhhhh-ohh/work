package com.wanmi.sbc.vas.recommend.recommendcatemanage.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.*;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageVO;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.mapper.RecommendCateManageMapper;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.model.root.RecommendCateManage;
import com.wanmi.sbc.vas.recommend.recommendcatemanage.repository.RecommendCateManageRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>分类推荐管理业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@Service("RecommendCateManageService")
public class RecommendCateManageService {
	@Autowired
	private RecommendCateManageRepository recommendCateManageRepository;

	@Autowired
	private RecommendCateManageMapper recommendCateManageMapper;

	/**
	 * 新增分类推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendCateManage add(RecommendCateManage entity) {
		recommendCateManageRepository.save(entity);
		return entity;
	}

	/**
	 * 新增分类推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void addList(RecommendCateManageAddListRequest recommendCateManageAddListRequest) {
		List<RecommendCateManage> recommendCateManageList = new ArrayList<>();
		recommendCateManageAddListRequest.getRecommendCateManageAddRequestList().forEach(recommendCateManageAddRequest -> {
			RecommendCateManage recommendCateManage = KsBeanUtil.convert(recommendCateManageAddRequest, RecommendCateManage.class);
			recommendCateManage.setCreateTime(LocalDateTime.now());
			recommendCateManageList.add(recommendCateManage);
		});
		recommendCateManageRepository.saveAll(recommendCateManageList);
	}

	/**
	 * 修改分类推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendCateManage modify(RecommendCateManage entity) {
		recommendCateManageRepository.save(entity);
		return entity;
	}

	/**
	 * 修改分类推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public int updateCateWeight(RecommendCateManageUpdateWeightRequest request) {
		if(Objects.nonNull(request.getWeight())){
			return recommendCateManageRepository.updateCateWeight(request.getWeight(),request.getId());
		} else {
			return recommendCateManageRepository.updateCateWeightNull(request.getId());
		}
	}

	@Transactional
	public int updateCateNoPushType(RecommendCateManageUpdateNoPushTypeRequest request){
		if(CollectionUtils.isNotEmpty(request.getIds())){
			return recommendCateManageRepository.updateCateNoPushTypeForIdList(request.getNoPushType(),request.getIds());
		} else {
			return recommendCateManageRepository.updateCateNoPushType(request.getNoPushType(),request.getId());
		}
	}

	/**
	 * 单个删除分类推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		recommendCateManageRepository.deleteById(id);
	}

	/**
	 * 批量删除分类推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
//		recommendCateManageRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询分类推荐管理
	 * @author lvzhenwei
	 */
	public RecommendCateManage getOne(Long id){
		return recommendCateManageRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "分类推荐管理不存在"));
	}

	/**
	 * 分页查询分类推荐管理
	 * @author lvzhenwei
	 */
	public Page<RecommendCateManage> page(RecommendCateManageQueryRequest queryReq){
		return recommendCateManageRepository.findAll(
				RecommendCateManageWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询分类推荐管理
	 * @author lvzhenwei
	 */
	public List<RecommendCateManage> list(RecommendCateManageQueryRequest queryReq){
		return recommendCateManageRepository.findAll(RecommendCateManageWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * @Author lvzhenwei
	 * @Description 分页查询分类管理列表数据
	 * @Date 14:40 2020/11/19
	 * @Param [request]
	 * @return java.util.List<com.wanmi.sbc.crm.bean.vo.RecommendCateManageInfoVO>
	 **/
	public List<RecommendCateManageInfoVO> getRecommendCateInfoList(RecommendCateManageInfoListRequest request){
		return recommendCateManageMapper.getRecommendCateInfoList(request);
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public RecommendCateManageVO wrapperVo(RecommendCateManage recommendCateManage) {
		if (recommendCateManage != null){
			RecommendCateManageVO recommendCateManageVO = KsBeanUtil.convert(recommendCateManage, RecommendCateManageVO.class);
			return recommendCateManageVO;
		}
		return null;
	}
}

