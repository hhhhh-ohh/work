package com.wanmi.sbc.customer.goodsfootmark.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkAddRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkDelByTimeRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkQueryRequest;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import com.wanmi.sbc.customer.goodsfootmark.model.root.GoodsFootmark;
import com.wanmi.sbc.customer.goodsfootmark.repository.GoodsFootmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>我的足迹业务逻辑</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Service("GoodsFootmarkService")
public class GoodsFootmarkService {
	@Autowired
	private GoodsFootmarkRepository goodsFootmarkRepository;
	
	/** 
	 * 修改我的足迹
	 * @author 
	 */
	@Transactional
	public GoodsFootmark modify(GoodsFootmark entity) {
		goodsFootmarkRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除我的足迹
	 * @author 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(Long id, String customerId) {
		goodsFootmarkRepository.deleteById(id, customerId);
	}
	
	/** 
	 * 批量删除我的足迹
	 * @author 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIdList(List<Long> ids, String customerId) {
		goodsFootmarkRepository.deleteByIdList(ids, customerId);
	}
	
	/** 
	 * 单个查询我的足迹
	 * @author 
	 */
	public GoodsFootmark getById(Long id){
		return goodsFootmarkRepository.findById(id).orElse(null);
	}
	
	/** 
	 * 分页查询我的足迹
	 * @author 
	 */
	public Page<GoodsFootmark> page(GoodsFootmarkQueryRequest queryReq){
		return goodsFootmarkRepository.findAll(
				GoodsFootmarkWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * @description 查询总数量
	 * @author 马连峰
	 */
	public Long count(GoodsFootmarkQueryRequest queryReq) {
		return goodsFootmarkRepository.count(GoodsFootmarkWhereCriteriaBuilder.build(queryReq));
	}
	
	/** 
	 * 列表查询我的足迹
	 * @author 
	 */
	public List<GoodsFootmark> list(GoodsFootmarkQueryRequest queryReq){
		return goodsFootmarkRepository.findAll(
				GoodsFootmarkWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author 
	 */
	public GoodsFootmarkVO wrapperVo(GoodsFootmark goodsFootmark) {
		if (goodsFootmark != null){
			GoodsFootmarkVO goodsFootmarkVO=new GoodsFootmarkVO();
			KsBeanUtil.copyPropertiesThird(goodsFootmark,goodsFootmarkVO);
			return goodsFootmarkVO;
		}
		return null;
	}

	/**
	 * 新增我的足迹
	 * @author
	 * @param request
	 */
	@Transactional
	public void add( GoodsFootmarkAddRequest request) {
		goodsFootmarkRepository.saveOrUpdate(request.getFootmarkId(),request.getCustomerId(),request.getGoodsInfoId());
	}

	/**
	 * 删除我的足迹
	 * @author
	 */
	@Transactional
	public void deleteByUpdateTime(GoodsFootmarkDelByTimeRequest goodsFootmarkDelByTimeRequest) {
		goodsFootmarkRepository.deleteByUpdateTime(goodsFootmarkDelByTimeRequest.getUpdateTimeEnd());
	}
}
