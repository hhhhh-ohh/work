package com.wanmi.sbc.marketing.drawprize.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.DrawPrizeVO;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.repository.DrawPrizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>抽奖活动奖品表业务逻辑</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Service("DrawPrizeService")
public class DrawPrizeService {
	@Autowired
	private DrawPrizeRepository drawPrizeRepository;
	
	/** 
	 * 新增抽奖活动奖品表
	 * @author wwc
	 */
	@Transactional
	public DrawPrize add(DrawPrize entity) {
		drawPrizeRepository.save(entity);
		return entity;
	}
	
	/** 
	 * 修改抽奖活动奖品表
	 * @author wwc
	 */
	@Transactional
	public DrawPrize modify(DrawPrize entity) {
		drawPrizeRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除抽奖活动奖品表
	 * @author wwc
	 */
	@Transactional
	public void deleteById(Long id) {
		drawPrizeRepository.deleteById(id);
	}
	
	/** 
	 * 批量删除抽奖活动奖品表
	 * @author wwc
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		drawPrizeRepository.deleteByIdList(ids);
	}

	/**
	 * 扣除奖品库存
	 * @author wwc
	 */
	@Transactional
	public void subPrizeStock(Long id) {
		drawPrizeRepository.subPrizeStock(id);
	}
	
	/** 
	 * 单个查询抽奖活动奖品表
	 * @author wwc
	 */
	public DrawPrize getById(Long id){
		return drawPrizeRepository.findById(id).orElse(null);
	}

	/**
	 * 查询奖品
	 * @param activityId
	 * @param deleteFlag
	 * @return
	 */
	public List<DrawPrize> findAllByActivityIdAndDelFlag(Long activityId, DeleteFlag deleteFlag){
		return drawPrizeRepository.findAllByActivityIdAndDelFlag(activityId,deleteFlag);
	}
	
	/** 
	 * 分页查询抽奖活动奖品表
	 * @author wwc
	 */
	public Page<DrawPrize> page(DrawPrizeQueryRequest queryReq){
		return drawPrizeRepository.findAll(
				DrawPrizeWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}
	
	/** 
	 * 列表查询抽奖活动奖品表
	 * @author wwc
	 */
	public List<DrawPrize> list(DrawPrizeQueryRequest queryReq){
		return drawPrizeRepository.findAll(
				DrawPrizeWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author wwc
	 */
	public DrawPrizeVO wrapperVo(DrawPrize drawPrize) {
		if (drawPrize != null){
			DrawPrizeVO drawPrizeVO=new DrawPrizeVO();
			KsBeanUtil.copyPropertiesThird(drawPrize,drawPrizeVO);
			return drawPrizeVO;
		}
		return null;
	}
}
