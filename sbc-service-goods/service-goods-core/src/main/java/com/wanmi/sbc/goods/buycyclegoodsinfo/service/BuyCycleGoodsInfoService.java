package com.wanmi.sbc.goods.buycyclegoodsinfo.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateGoodsRelaQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoQueryRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoValidateRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsCountByConditionRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateGoodsRelaCountRequest;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoVO;
import com.wanmi.sbc.goods.buycyclegoods.model.root.BuyCycleGoods;
import com.wanmi.sbc.goods.buycyclegoods.service.BuyCycleGoodsService;
import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import com.wanmi.sbc.goods.buycyclegoodsinfo.repository.BuyCycleGoodsInfoRepository;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>周期购sku表业务逻辑</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Service("BuyCycleGoodsInfoService")
public class BuyCycleGoodsInfoService {
	@Autowired
	private BuyCycleGoodsInfoRepository buyCycleGoodsInfoRepository;

	@Autowired
	private BuyCycleGoodsService buyCycleGoodsService;

	@Autowired private GoodsQueryProvider goodsQueryProvider;

	@Autowired private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

	/**
	 * 新增周期购sku表
	 * @author zhanghao
	 */
	@Transactional
	public BuyCycleGoodsInfo add(BuyCycleGoodsInfo entity) {
		buyCycleGoodsInfoRepository.save(entity);
		return entity;
	}

	/**
	 * 修改周期购sku表
	 * @author zhanghao
	 */
	@Transactional
	public BuyCycleGoodsInfo modify(BuyCycleGoodsInfo entity) {
		buyCycleGoodsInfoRepository.save(entity);
		return entity;
	}

	/**
	 * 拼团互斥验证
	 * @param request 入参
	 * @return 验证结果
	 */
	public void validate(BuyCycleGoodsInfoValidateRequest request) {
		BuyCycleGoodsQueryRequest queryRequest = new BuyCycleGoodsQueryRequest();
		queryRequest.setDelFlag(DeleteFlag.NO);
		queryRequest.setStoreId(request.getStoreId());
		queryRequest.setPageSize(100);
		queryRequest.putSort("id", SortType.ASC.toValue());
		boolean res = false;
		for (int pageNo = 0; ; pageNo++) {
			queryRequest.setPageNum(pageNo);
			Page<BuyCycleGoods> activityPage = buyCycleGoodsService.pageCols(queryRequest, Arrays.asList("id", "goodsId"));
			if (activityPage.getTotalElements() == 0) {
				break;
			}
			if (CollectionUtils.isNotEmpty(activityPage.getContent())) {
				//所有商品
				if(Boolean.TRUE.equals(request.getAllFlag())){
					res = true;
					break;
				}
				List<String> spuIds = activityPage.stream().map(BuyCycleGoods::getGoodsId).filter(StringUtils::isNotBlank).toList();
				//验证商品相关品牌是否存在
				if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
					if (this.checkGoodsAndBrand(spuIds, request.getBrandIds())) {
						res = true;
						break;
					}
				} else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
					//验证商品相关店铺分类是否存在
					if (this.checkGoodsAndStoreCate(spuIds, request.getStoreCateIds())) {
						res = true;
						break;
					}
				} else if (CollectionUtils.isNotEmpty(request.getSkuIds())) {
					//验证自定义货品范围
					BuyCycleGoodsInfoQueryRequest goodsRequest = new BuyCycleGoodsInfoQueryRequest();
					goodsRequest.setGoodsInfoIdList(request.getSkuIds());
					goodsRequest.setBuyCycleIdList(activityPage.stream().map(BuyCycleGoods::getId).toList());
					goodsRequest.setDelFlag(DeleteFlag.NO);
					Long count = this.count(goodsRequest);
					if (count > 0) {
						res = true;
						break;
					}
				}
				// 最后一页，退出循环
				if (pageNo >= activityPage.getTotalPages() - 1) {
					break;
				}
			}
		}
		if(res){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"周期购"});
		}
	}

	/**
	 * 验证商品、品牌的重合
	 * @param spuIds 商品skuId
	 * @param brandIds 品牌Id
	 * @return 重合结果
	 */
	public Boolean checkGoodsAndBrand(List<String> spuIds, List<Long> brandIds) {
		GoodsCountByConditionRequest count = new GoodsCountByConditionRequest();
		count.setGoodsIds(spuIds);
		count.setBrandIds(brandIds);
		count.setDelFlag(DeleteFlag.NO.toValue());
		return goodsQueryProvider.countByCondition(count).getContext().getCount() > 0;
	}

	/**
	 * 验证商品、店铺分类的重合
	 * @param spuIds 商品skuId
	 * @param cateIds 店铺分类Id
	 * @return 重合结果
	 */
	public Boolean checkGoodsAndStoreCate(List<String> spuIds, List<Long> cateIds) {
		StoreCateGoodsRelaCountRequest count = new StoreCateGoodsRelaCountRequest();
		count.setGoodsIds(spuIds);
		count.setStoreCateIds(cateIds);
		return storeCateGoodsRelaQueryProvider.countByParams(count).getContext().getCount() > 0;
	}

	/**
	 * 单个删除周期购sku表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(BuyCycleGoodsInfo entity) {
		buyCycleGoodsInfoRepository.save(entity);
	}

	/**
	 * 批量删除周期购sku表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		buyCycleGoodsInfoRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询周期购sku表
	 * @author zhanghao
	 */
	public BuyCycleGoodsInfo getOne(String id){
		return buyCycleGoodsInfoRepository.findByGoodsInfoIdAndCycleStateAndDelFlag(id, Constants.ZERO, DeleteFlag.NO)
		.orElse(null);
	}

	/**
	 * 单个查询周期购sku表
	 * @author zhanghao
	 */
	public BuyCycleGoodsInfo getOne(Long id){
		return buyCycleGoodsInfoRepository.findById(id)
				.orElse(null);
	}

	/**
	 * 分页查询周期购sku表
	 * @author zhanghao
	 */
	public Page<BuyCycleGoodsInfo> page(BuyCycleGoodsInfoQueryRequest queryReq){
		return buyCycleGoodsInfoRepository.findAll(
				BuyCycleGoodsInfoWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询周期购sku表
	 * @author zhanghao
	 */
	public List<BuyCycleGoodsInfo> list(BuyCycleGoodsInfoQueryRequest queryReq){
		return buyCycleGoodsInfoRepository.findAll(BuyCycleGoodsInfoWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public BuyCycleGoodsInfoVO wrapperVo(BuyCycleGoodsInfo buyCycleGoodsInfo) {
		if (buyCycleGoodsInfo != null){
			BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = KsBeanUtil.convert(buyCycleGoodsInfo, BuyCycleGoodsInfoVO.class);
			return buyCycleGoodsInfoVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(BuyCycleGoodsInfoQueryRequest queryReq) {
		return buyCycleGoodsInfoRepository.count(BuyCycleGoodsInfoWhereCriteriaBuilder.build(queryReq));
	}
}

