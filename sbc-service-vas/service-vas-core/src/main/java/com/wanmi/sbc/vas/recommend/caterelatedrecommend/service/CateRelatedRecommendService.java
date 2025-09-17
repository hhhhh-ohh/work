package com.wanmi.sbc.vas.recommend.caterelatedrecommend.service;

import com.github.pagehelper.PageHelper;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.*;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendDetailVO;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendVO;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.mapper.CateRelatedRecommendMapper;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.model.root.CateRelatedRecommend;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.repository.CateRelatedRecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>分类相关性推荐业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Service("CateRelatedRecommendService")
public class CateRelatedRecommendService {
	@Autowired
	private CateRelatedRecommendRepository cateRelatedRecommendRepository;

	@Autowired
	private CateRelatedRecommendMapper cateRelatedRecommendMapper;

	/**
	 * 新增分类相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public CateRelatedRecommend add(CateRelatedRecommend entity) {
		cateRelatedRecommendRepository.save(entity);
		return entity;
	}

	/**
	 * 新增分类相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public void addList(CateRelatedRecommendAddListRequest request) {
		List<CateRelatedRecommend> cateRelatedRecommendList = new ArrayList<>();
		request.getCateRelatedRecommendAddRequestList().forEach(cateRelatedRecommendAddRequest -> {
			CateRelatedRecommend cateRelatedRecommend = KsBeanUtil.convert(cateRelatedRecommendAddRequest, CateRelatedRecommend.class);
			cateRelatedRecommendList.add(cateRelatedRecommend);
		});
		cateRelatedRecommendRepository.saveAll(cateRelatedRecommendList);
	}

	/**
	 * 修改分类相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public CateRelatedRecommend modify(CateRelatedRecommend entity) {
		cateRelatedRecommendRepository.save(entity);
		return entity;
	}

	/**
	 * @Author lvzhenwei
	 * @Description 更新权重
	 * @Date 16:41 2020/11/26
	 * @Param [request]
	 * @return void
	 **/
	@Transactional
	public void updateWeight(CateRelatedRecommendUpdateWeightRequest request){
		if(Objects.nonNull(request.getWeight())){
			cateRelatedRecommendRepository.updateWeight(request.getWeight(),request.getId());
		} else {
			cateRelatedRecommendRepository.updateWeightNull(request.getId());
		}

	}

	/**
	 * 单个删除分类相关性推荐
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		cateRelatedRecommendRepository.deleteById(id);
	}

	/**
	 * 单个查询分类相关性推荐
	 * @author lvzhenwei
	 */
	public CateRelatedRecommend getOne(Long id){
		return cateRelatedRecommendRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "分类相关性推荐不存在"));
	}

	/**
	 * 分页查询分类相关性推荐
	 * @author lvzhenwei
	 */
	public Page<CateRelatedRecommend> page(CateRelatedRecommendQueryRequest queryReq){
		return cateRelatedRecommendRepository.findAll(
				CateRelatedRecommendWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询分类相关性推荐
	 * @author lvzhenwei
	 */
	public List<CateRelatedRecommend> list(CateRelatedRecommendQueryRequest queryReq){
		return cateRelatedRecommendRepository.findAll(CateRelatedRecommendWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * @Author lvzhenwei
	 * @Description 基于商品相关性推荐--按类目查看--合并
	 * @Date 14:49 2020/11/26
	 * @Param [relatedRecommendInfoListRequest]
	 * @return java.util.List<com.wanmi.sbc.crm.bean.vo.CateRelatedRecommendInfoVO>
	 **/
	public List<CateRelatedRecommendInfoVO> getCateRelateRecommendInfoList(CateRelatedRecommendInfoListRequest relatedRecommendInfoListRequest){
		return cateRelatedRecommendMapper.getCateRelateRecommendInfoList(relatedRecommendInfoListRequest);
	}

	/**
	 * @Author lvzhenwei
	 * @Description 基于商品相关性推荐--按类目查看--逐条
	 * @Date 14:49 2020/11/26
	 * @Param [relatedRecommendInfoListRequest]
	 * @return java.util.List<com.wanmi.sbc.crm.bean.vo.CateRelatedRecommendInfoVO>
	 **/
	public Page<CateRelatedRecommendDetailVO> getCateRelateRecommendDetailList(CateRelatedRecommendDetailListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<CateRelatedRecommendDetailVO> recommendGoodsManageInfoVOList = cateRelatedRecommendMapper.getCateRelateRecommendDetailList(request);
		Long pageTotal = cateRelatedRecommendMapper.getCateRelateRecommendDetailNum(request);
		return new PageImpl<>(recommendGoodsManageInfoVOList,request.getPageable(),pageTotal);
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public CateRelatedRecommendVO wrapperVo(CateRelatedRecommend cateRelatedRecommend) {
		if (cateRelatedRecommend != null){
			CateRelatedRecommendVO cateRelatedRecommendVO = KsBeanUtil.convert(cateRelatedRecommend, CateRelatedRecommendVO.class);
			return cateRelatedRecommendVO;
		}
		return null;
	}
}

