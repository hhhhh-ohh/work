package com.wanmi.sbc.vas.recommend.recommendgoodsmanage.service;

import com.github.pagehelper.PageHelper;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.recommendgoodsmanage.*;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendGoodsManageVO;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.mapper.RecommendGoodsManageMapper;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.model.root.RecommendGoodsManage;
import com.wanmi.sbc.vas.recommend.recommendgoodsmanage.repository.RecommendGoodsManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>商品推荐管理业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@Service("RecommendGoodsManageService")
public class RecommendGoodsManageService {
	@Autowired
	private RecommendGoodsManageRepository recommendGoodsManageRepository;

	@Autowired
	private RecommendGoodsManageMapper recommendGoodsManageMapper;

	/**
	 * 新增商品推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendGoodsManage add(RecommendGoodsManage entity) {
		entity.setCreateTime(LocalDateTime.now());
		recommendGoodsManageRepository.save(entity);
		return entity;
	}
	/**
	 * 新增商品推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void addList(RecommendGoodsManageAddListRequest request) {
		List<RecommendGoodsManage> recommendGoodsManageList = new ArrayList<>();
		request.getRecommendGoodsManageList().forEach(addRequest->{
			RecommendGoodsManage recommendGoodsManage = KsBeanUtil.convert(addRequest, RecommendGoodsManage.class);
			recommendGoodsManage.setCreateTime(LocalDateTime.now());
			recommendGoodsManageList.add(recommendGoodsManage);
		});
		recommendGoodsManageRepository.saveAll(recommendGoodsManageList);
	}


	/**
	 * 修改商品推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public RecommendGoodsManage modify(RecommendGoodsManage entity) {
		recommendGoodsManageRepository.save(entity);
		return entity;
	}

	@Transactional
	public int updateWeight(RecommendGoodsManageUpdateWeightRequest request){
		if(Objects.nonNull(request.getWeight())){
			return recommendGoodsManageRepository.updateWeight(new BigDecimal(request.getWeight()),request.getId());
		} else {
			return recommendGoodsManageRepository.updateWeightNull(request.getId());
		}
	}

	/**
	 * @Author lvzhenwei
	 * @Description 更新商品管理禁推状态
	 * @Date 14:33 2020/11/18
	 * @Param [request]
	 * @return int
	 **/
	@Transactional
	public int updateNoPush(RecommendGoodsManageUpdateNoPushRequest request){
		return recommendGoodsManageRepository.updateNoPush(request.getNoPushType(),request.getId());
	}

	/**
	 * @Author lvzhenwei
	 * @Description 批量更新商品管理禁推状态
	 * @Date 14:38 2020/11/18
	 * @Param [request]
	 * @return int
	 **/
	@Transactional
	public int updateNoPushForIds(RecommendGoodsManageUpdateNoPushRequest request){
		return recommendGoodsManageRepository.updateNoPushForIds(request.getNoPushType(),request.getIds());
	}

	/**
	 * 单个删除商品推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		recommendGoodsManageRepository.deleteById(id);
	}

	/**
	 * 批量删除商品推荐管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
//		recommendGoodsManageRepository.delete(ids);
	}

	/**
	 * 单个查询商品推荐管理
	 * @author lvzhenwei
	 */
	public RecommendGoodsManage getOne(Long id){
		return recommendGoodsManageRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品推荐管理不存在"));
	}

	/**
	 * 分页查询商品推荐管理
	 * @author lvzhenwei
	 */
	public Page<RecommendGoodsManage> page(RecommendGoodsManageQueryRequest queryReq){
		return recommendGoodsManageRepository.findAll(
				RecommendGoodsManageWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询商品推荐管理
	 * @author lvzhenwei
	 */
	public List<RecommendGoodsManage> list(RecommendGoodsManageQueryRequest queryReq){
		return recommendGoodsManageRepository.findAll(RecommendGoodsManageWhereCriteriaBuilder.build(queryReq));
	}

	public Page<RecommendGoodsManageInfoVO> getRecommendGoodsInfoList(RecommendGoodsManageListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<RecommendGoodsManageInfoVO> recommendGoodsManageInfoVOList = recommendGoodsManageMapper.getRecommendGoodsInfoList(request);
		Long pageTotal = recommendGoodsManageMapper.getRecommendGoodsInfoListNum(request);
		return new PageImpl<>(recommendGoodsManageInfoVOList,request.getPageable(),pageTotal);
	}

	/**
	 * 计算页码
	 *
	 * @param count
	 * @param size
	 * @return
	 */
	private int calPage(int count, int size) {
		int page = count / size;
		if (count % size == 0) {
			return page;
		} else {
			return page + 1;
		}
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public RecommendGoodsManageVO wrapperVo(RecommendGoodsManage recommendGoodsManage) {
		if (recommendGoodsManage != null){
			RecommendGoodsManageVO recommendGoodsManageVO = KsBeanUtil.convert(recommendGoodsManage, RecommendGoodsManageVO.class);
			return recommendGoodsManageVO;
		}
		return null;
	}
}

