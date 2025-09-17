package com.wanmi.sbc.goods.newcomerpurchasegoods.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsQueryRequest;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsMagicVO;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.newcomerpurchasegoods.model.root.NewcomerPurchaseGoods;
import com.wanmi.sbc.goods.newcomerpurchasegoods.repository.NewcomerPurchaseGoodsRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>新人购商品表业务逻辑</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Service("NewcomerPurchaseGoodsService")
public class NewcomerPurchaseGoodsService {
	@Autowired
	private NewcomerPurchaseGoodsRepository newcomerPurchaseGoodsRepository;

	/**
	 * 新增新人购商品表
	 * @author zhanghao
	 */
	@Transactional
	public NewcomerPurchaseGoods add(NewcomerPurchaseGoods entity) {
		newcomerPurchaseGoodsRepository.save(entity);
		return entity;
	}

	/**
	 * 修改新人购商品表
	 * @author zhanghao
	 */
	@Transactional
	public NewcomerPurchaseGoods modify(NewcomerPurchaseGoods entity) {
		newcomerPurchaseGoodsRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除新人购商品表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(NewcomerPurchaseGoods entity) {
		newcomerPurchaseGoodsRepository.save(entity);
	}

	/**
	 * 批量删除新人购商品表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Integer> ids) {
		newcomerPurchaseGoodsRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询新人购商品表
	 * @author zhanghao
	 */
	public NewcomerPurchaseGoods getOne(Integer id){
		return newcomerPurchaseGoodsRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "新人购商品表不存在"));
	}

	/**
	 * 分页查询新人购商品表
	 * @author zhanghao
	 */
	public Page<NewcomerPurchaseGoods> page(NewcomerPurchaseGoodsQueryRequest queryReq){
		return newcomerPurchaseGoodsRepository.findAll(
				NewcomerPurchaseGoodsWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询新人购商品表
	 * @author zhanghao
	 */
	public List<NewcomerPurchaseGoods> list(NewcomerPurchaseGoodsQueryRequest queryReq){
		return newcomerPurchaseGoodsRepository.findAll(NewcomerPurchaseGoodsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public NewcomerPurchaseGoodsVO wrapperVo(NewcomerPurchaseGoods newcomerPurchaseGoods) {
		if (newcomerPurchaseGoods != null){
			NewcomerPurchaseGoodsVO newcomerPurchaseGoodsVO = KsBeanUtil.convert(newcomerPurchaseGoods, NewcomerPurchaseGoodsVO.class);
			return newcomerPurchaseGoodsVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(NewcomerPurchaseGoodsQueryRequest queryReq) {
		return newcomerPurchaseGoodsRepository.count(NewcomerPurchaseGoodsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 批量保存新人购商品
	 * @param goodsInfoIds
	 */
	@Transactional
	public void saveAll(List<String> goodsInfoIds) {
		//首先删除所有关联的商品
		newcomerPurchaseGoodsRepository.deleteAll();
		//再保存
		if (CollectionUtils.isNotEmpty(goodsInfoIds)){
			newcomerPurchaseGoodsRepository.saveAll(goodsInfoIds.parallelStream().filter(StringUtils::isNotEmpty).map(goodsInfoId -> {
				NewcomerPurchaseGoods newcomerPurchaseGoods = new NewcomerPurchaseGoods();
				newcomerPurchaseGoods.setGoodsInfoId(goodsInfoId);
				newcomerPurchaseGoods.setDelFlag(DeleteFlag.NO);
				return newcomerPurchaseGoods;
			}).collect(Collectors.toList()));
		}
	}

	/**
	 * 查询未删除的单品ids
	 * @return
	 */
	public List<String> findGoodsInfoIds() {
		return newcomerPurchaseGoodsRepository.findGoodsInfoIds();
	}

	public NewcomerPurchaseGoodsMagicVO wrapperMagicVO(GoodsInfo goodsInfo, Map<Long, StoreVO> storeVOMap) {
		if (goodsInfo != null) {
			NewcomerPurchaseGoodsMagicVO magicVO = KsBeanUtil.convert(goodsInfo, NewcomerPurchaseGoodsMagicVO.class);
			StoreVO storeVO = storeVOMap.get(goodsInfo.getStoreId());
			magicVO.setStoreName(storeVO.getStoreName());
			return magicVO;
		}
		return null;
	}
}

