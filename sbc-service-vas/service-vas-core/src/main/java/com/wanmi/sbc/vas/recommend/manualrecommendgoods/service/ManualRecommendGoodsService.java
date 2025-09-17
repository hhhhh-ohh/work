package com.wanmi.sbc.vas.recommend.manualrecommendgoods.service;

import com.github.pagehelper.PageHelper;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsAddListRequest;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsInfoListRequest;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsQueryRequest;
import com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods.ManualRecommendGoodsUpdateWeightRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsSpecTextVO;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsInfoVO;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsVO;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.mapper.ManualRecommendGoodsMapper;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.model.root.ManualRecommendGoods;
import com.wanmi.sbc.vas.recommend.manualrecommendgoods.repository.ManualRecommendGoodsRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>手动推荐商品管理业务逻辑</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Service("ManualRecommendGoodsService")
public class ManualRecommendGoodsService {
	@Autowired
	private ManualRecommendGoodsRepository manualRecommendGoodsRepository;

	@Autowired
	private ManualRecommendGoodsMapper manualRecommendGoodsMapper;

	/**
	 * 新增手动推荐商品管理
	 * @author lvzhenwei
	 */
	@Transactional
	public ManualRecommendGoods add(ManualRecommendGoods entity) {
		manualRecommendGoodsRepository.save(entity);
		return entity;
	}

	/**
	 * 批量新增手动推荐商品管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void addList(ManualRecommendGoodsAddListRequest manualRecommendGoodsAddListRequest) {
		List<ManualRecommendGoods> manualRecommendGoodsList = new ArrayList<>();
		manualRecommendGoodsAddListRequest.getManualRecommendGoodsList().forEach(manualRecommendGoodsAddRequest -> {
			ManualRecommendGoods manualRecommendGoods = KsBeanUtil.convert(manualRecommendGoodsAddRequest, ManualRecommendGoods.class);
			manualRecommendGoods.setCreateTime(LocalDateTime.now());
			manualRecommendGoodsList.add(manualRecommendGoods);
		});
		manualRecommendGoodsRepository.saveAll(manualRecommendGoodsList);
	}

	/**
	 * 修改手动推荐商品管理
	 * @author lvzhenwei
	 */
	@Transactional
	public ManualRecommendGoods modify(ManualRecommendGoods entity) {
		manualRecommendGoodsRepository.save(entity);
		return entity;
	}

	@Transactional
	public int updateWeight(ManualRecommendGoodsUpdateWeightRequest request){
		if(Objects.isNull(request.getWeight())){
			return manualRecommendGoodsRepository.updateWeightNull(request.getId());
		} else {
			return manualRecommendGoodsRepository.updateWeight(request.getWeight(),request.getId());
		}
	}

	/**
	 * 单个删除手动推荐商品管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		manualRecommendGoodsRepository.deleteById(id);
	}

	/**
	 * 批量删除手动推荐商品管理
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		manualRecommendGoodsRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询手动推荐商品管理
	 * @author lvzhenwei
	 */
	public ManualRecommendGoods getOne(Long id){
		return manualRecommendGoodsRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "手动推荐商品管理不存在"));
	}

	/**
	 * 分页查询手动推荐商品管理
	 * @author lvzhenwei
	 */
	public Page<ManualRecommendGoods> page(ManualRecommendGoodsQueryRequest queryReq){
		return manualRecommendGoodsRepository.findAll(
				ManualRecommendGoodsWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询手动推荐商品管理
	 * @author lvzhenwei
	 */
	public List<ManualRecommendGoods> list(ManualRecommendGoodsQueryRequest queryReq){
		return manualRecommendGoodsRepository.findAll(ManualRecommendGoodsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 查询手动推荐商品列表
	 * @param request
	 * @return
	 */
	public Page<ManualRecommendGoodsInfoVO> getManualRecommendGoodsInfoList(ManualRecommendGoodsInfoListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<ManualRecommendGoodsInfoVO> recommendGoodsManageInfoVOList = manualRecommendGoodsMapper.getManualRecommendGoodsInfoList(request);
		getGoodsBuyPointAndMarketPrice(recommendGoodsManageInfoVOList);
		Long pageTotal = manualRecommendGoodsMapper.getManualRecommendGoodsInfoNum(request);
		return new PageImpl<>(recommendGoodsManageInfoVOList,request.getPageable(),pageTotal);
	}

	/**
	 * 查询手动推荐选择商品列表
	 * @param request
	 * @return
	 */
	public Page<ManualRecommendGoodsInfoVO> getManualRecommendChooseGoodsList(ManualRecommendGoodsInfoListRequest request){
		PageHelper.startPage(request.getPageNum()+1,request.getPageSize(),false);
		List<ManualRecommendGoodsInfoVO> recommendGoodsManageInfoVOList = manualRecommendGoodsMapper.getManualRecommendChooseGoodsList(request);
		getGoodsBuyPointAndMarketPrice(recommendGoodsManageInfoVOList);
		Map<String, String> specTextMap = this.specTextMap(recommendGoodsManageInfoVOList);
		Long pageTotal = manualRecommendGoodsMapper.getManualRecommendChooseGoodsNum(request);
		List<ManualRecommendGoodsInfoVO> newList = recommendGoodsManageInfoVOList.stream().peek(recommendGoodsManageInfoVO -> {
			String goodsId = recommendGoodsManageInfoVO.getGoodsId();
			String specText = specTextMap.get(goodsId);
			recommendGoodsManageInfoVO.setSpecText(specText);
		}).collect(Collectors.toList());
		return new PageImpl<>(newList,request.getPageable(),pageTotal);
	}

	/**
	 * 根据goodsId获商品价格以及积分价
	 * @param recommendGoodsManageInfoVOList
	 * @return
	 */
	private List<ManualRecommendGoodsInfoVO> getGoodsBuyPointAndMarketPrice(List<ManualRecommendGoodsInfoVO> recommendGoodsManageInfoVOList){
		if(CollectionUtils.isEmpty(recommendGoodsManageInfoVOList)){
			return recommendGoodsManageInfoVOList;
		}
		List<String> goodsIdList = recommendGoodsManageInfoVOList.stream()
				.map(ManualRecommendGoodsInfoVO::getGoodsId)
				.collect(Collectors.toList());
		List<ManualRecommendGoodsInfoVO> goodsBuyPointAndMarketPriceList = manualRecommendGoodsMapper.getGoodsBuyPointAndMarketPrice(goodsIdList);
		Map<String,ManualRecommendGoodsInfoVO> buyPointAndMarketPriceMap = new HashMap<>();
		goodsBuyPointAndMarketPriceList.forEach(goodsBuyPointAndMarketPrice -> {
			buyPointAndMarketPriceMap.put(goodsBuyPointAndMarketPrice.getGoodsId(),goodsBuyPointAndMarketPrice);
		});
		recommendGoodsManageInfoVOList.forEach(manualRecommendGoodsInfoVO -> {
			ManualRecommendGoodsInfoVO goodsBuyPointAndMarketPrice = buyPointAndMarketPriceMap.get(manualRecommendGoodsInfoVO.getGoodsId());
			if(Objects.nonNull(goodsBuyPointAndMarketPrice)){
				manualRecommendGoodsInfoVO.setBuyPoint(goodsBuyPointAndMarketPrice.getBuyPoint());
				manualRecommendGoodsInfoVO.setMarketPrice(goodsBuyPointAndMarketPrice.getMarketPrice());
			}
		});
		return recommendGoodsManageInfoVOList;
	}

	/**
	 * 根据goodsId获取规格值
	 * @param recommendGoodsManageInfoVOList
	 * @return
	 */
	private Map<String,String> specTextMap(List<ManualRecommendGoodsInfoVO> recommendGoodsManageInfoVOList){
		if(CollectionUtils.isEmpty(recommendGoodsManageInfoVOList)){
			return Collections.emptyMap();
		}
		List<String> goodsIdList = recommendGoodsManageInfoVOList.stream()
				.map(ManualRecommendGoodsInfoVO::getGoodsId)
				.collect(Collectors.toList());
		List<GoodsSpecTextVO> goodsSpecTextList = manualRecommendGoodsMapper.getGoodsSpecTextList(goodsIdList);
		if(CollectionUtils.isEmpty(goodsSpecTextList)){
			return Collections.emptyMap();
		}
		return goodsSpecTextList.stream()
				.collect(Collectors.groupingBy(GoodsSpecTextVO::getGoodsId,
				Collectors.mapping(GoodsSpecTextVO::getSpecText,Collectors.joining(" "))));
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public ManualRecommendGoodsVO wrapperVo(ManualRecommendGoods manualRecommendGoods) {
		if (manualRecommendGoods != null){
			ManualRecommendGoodsVO manualRecommendGoodsVO = KsBeanUtil.convert(manualRecommendGoods, ManualRecommendGoodsVO.class);
			return manualRecommendGoodsVO;
		}
		return null;
	}
}

