package com.wanmi.sbc.goods.goodstemplate.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.request.goodstemplate.GoodsTemplateJoinRequest;
import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplateRel;
import com.wanmi.sbc.goods.goodstemplate.repository.GoodsTemplateRelRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * <p>GoodsTemplateRel业务逻辑</p>
 * @author 黄昭
 * @date 2022-10-09 10:59:41
 */
@Service("GoodsTemplateRelService")
public class GoodsTemplateRelService {
	@Autowired
	private GoodsTemplateRelRepository goodsTemplateRelRepository;

	/**
	 * 单个删除GoodsTemplateRel
	 * @author 黄昭
	 */
	@Transactional
	public void deleteById(GoodsTemplateRel entity) {
		goodsTemplateRelRepository.save(entity);
	}

	/**
	 * 批量删除GoodsTemplateRel
	 * @author 黄昭
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		goodsTemplateRelRepository.deleteByIdList(ids);
	}

	/**
	 * @description 查询总数量
	 * @author 黄昭
	 */
	public Long count(Long templateId) {
		return goodsTemplateRelRepository.countByTemplateId(templateId);
	}

	/**
	 * 关联商品详情
	 * @param id
	 */
	public List<String> joinGoodsDetails(Long id) {
		 return goodsTemplateRelRepository.joinGoodsDetails(id);
	}

	/**
	 * 关联商品
	 * @param request
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void joinGoods(GoodsTemplateJoinRequest request) {
		List<String> newGoodsIds = request.getJoinGoodsIdList();
		List<String> oldGoodsIds = goodsTemplateRelRepository.joinGoodsDetails(request.getTemplateId());
		if (CollectionUtils.isEmpty(newGoodsIds)){
			goodsTemplateRelRepository.deleteByGoodsIds(oldGoodsIds);
			return;
		}
		List<String> intersection = newGoodsIds.stream().filter(oldGoodsIds::contains).collect(toList());

		//删除未关联商品信息
		oldGoodsIds.removeAll(intersection);
		if (CollectionUtils.isNotEmpty(oldGoodsIds)) {
			goodsTemplateRelRepository.deleteByGoodsIds(oldGoodsIds);
		}

		//添加新关联商品
		newGoodsIds.removeAll(intersection);
		goodsTemplateRelRepository.deleteByGoodsIds(newGoodsIds);
		List<GoodsTemplateRel> rels = newGoodsIds.stream().map(v -> {
			GoodsTemplateRel rel = new GoodsTemplateRel();
			rel.setGoodsId(v);
			rel.setTemplateId(request.getTemplateId());
			rel.setDelFlag(DeleteFlag.NO);
			rel.setCreateTime(LocalDateTime.now());
			rel.setCreatePerson(request.getUserId());
			rel.setUpdatePerson(request.getUserId());
			rel.setUpdateTime(LocalDateTime.now());
			return rel;
		}).collect(Collectors.toList());
		goodsTemplateRelRepository.saveAll(rels);
	}

	/**
	 * 单个删除模版关联商品
	 * @param goodsId
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteByGoodsId(String goodsId) {
		goodsTemplateRelRepository.deleteByGoodsIds(Collections.singletonList(goodsId));
	}
}

