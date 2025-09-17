package com.wanmi.sbc.marketing.communitysku.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateGoodsRelaQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsCountByConditionRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateGoodsRelaCountRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityQueryRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityValidateRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.CommunitySkuRelQueryRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.UpdateSalesRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunitySkuRelVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.marketing.communitysku.model.root.CommunitySkuRel;
import com.wanmi.sbc.marketing.communitysku.repository.CommunitySkuRelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动商品表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
@Service
public class CommunitySkuRelService {
	@Autowired
	private CommunitySkuRelRepository communitySkuRelRepository;

	@Autowired private CommunityActivityService communityActivityService;

	@Autowired private EntityManager entityManager;

	@Autowired private GoodsQueryProvider goodsQueryProvider;

	@Autowired private StoreCateGoodsRelaQueryProvider storeCateGoodsRelaQueryProvider;

	/**
	 * 新增社区团购活动商品表
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityActivity activity, CommunityActivityAddRequest addRequest) {
		List<CommunitySkuRel> relList = addRequest.getSkuList().stream().map(a -> {
			CommunitySkuRel rel = new CommunitySkuRel();
			rel.setSkuId(a.getSkuId());
			rel.setSpuId(a.getSpuId());
			rel.setPrice(a.getPrice());
			rel.setAssistCommission(a.getAssistCommission());
			rel.setPickupCommission(a.getPickupCommission());
			rel.setActivityStock(a.getActivityStock());
			rel.setSales(0L);
			rel.setActivityId(activity.getActivityId());
			return rel;
		}).collect(Collectors.toList());
		communitySkuRelRepository.saveAll(relList);
	}

	/**
	 * 修改社区团购活动商品表
	 * @author dyt
	 */
	@Transactional
	public void modify(CommunityActivity activity, CommunityActivityModifyRequest modifyRequest) {
		communitySkuRelRepository.deleteByActivityId(activity.getActivityId());
		CommunityActivityAddRequest addRequest = CommunityActivityAddRequest.builder()
				.skuList(modifyRequest.getSkuList()).build();
		this.add(activity, addRequest);
	}

	/**
	 * 修改社区团购活动商品表
	 * @author dyt
	 */
	@Transactional
	public void modifyStock(CommunityActivityModifyRequest modifyRequest) {
		modifyRequest.getSkuList().forEach(s -> communitySkuRelRepository.updateActivityStockById(s.getActivityStock(), s.getId()));
	}

	/**
	 * 单个删除社区团购活动商品表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(Long id) {
		communitySkuRelRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动商品表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 预约互斥验证
	 * @param request 入参
	 * @return 验证结果
	 */
	public void validate(CommunityActivityValidateRequest request) {
		CommunityActivityQueryRequest queryRequest = new CommunityActivityQueryRequest();
		queryRequest.setStoreId(request.getStoreId());
		queryRequest.setNotId(request.getNotId());
		//活动结束时间 >= 交叉开始时间
		queryRequest.setEndTimeBegin(request.getCrossBeginTime());
		//活动开始时间 <= 交叉结束时间
		queryRequest.setStartTimeEnd(request.getCrossEndTime());
		queryRequest.setPageSize(100);
		queryRequest.putSort("id", SortType.ASC.toValue());
		boolean res = false;
		for (int pageNo = 0; ; pageNo++) {
			queryRequest.setPageNum(pageNo);
			Page<CommunityActivity> sales = communityActivityService.pageCols(queryRequest, Collections.singletonList("activityId"));
			if (sales.getTotalElements() == 0) {
				break;
			}
			if(CollectionUtils.isNotEmpty(sales.getContent())){
				if(Boolean.TRUE.equals(request.getAllFlag())){
					res = true;
					break;
				}

				CommunitySkuRelQueryRequest goodsRequest = new CommunitySkuRelQueryRequest();
				goodsRequest.setActivityIdList(sales.stream().map(CommunityActivity::getActivityId).collect(Collectors.toList()));
				List<CommunitySkuRel> saleGoodsList = this.listCols(goodsRequest, Arrays.asList("spuId", "skuId"));
				//数据不存在
				if (CollectionUtils.isEmpty(saleGoodsList)) {
					continue;
				}
				List<String> skuIds = saleGoodsList.stream().map(CommunitySkuRel::getSkuId).filter(Objects::nonNull).toList();
				List<String> spuIds = saleGoodsList.stream().map(CommunitySkuRel::getSpuId).filter(Objects::nonNull).toList();

				//验证商品相关品牌是否存在
				if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
					if(this.checkGoodsAndBrand(spuIds, request.getBrandIds())){
						res = true;
						break;
					}
				} else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
					//验证商品相关店铺分类是否存在
					if(this.checkGoodsAndStoreCate(spuIds, request.getStoreCateIds())){
						res = true;
						break;
					}
				} else if (CollectionUtils.isNotEmpty(request.getSkuIds())
						&& request.getSkuIds().stream().anyMatch(skuIds::contains)) {
					//验证自定义货品范围
					res = true;
					break;
				}
			}
			// 最后一页，退出循环
			if (pageNo >= sales.getTotalPages() - 1) {
				break;
			}
		}
		if(res){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"社区团购"});
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
	 * 自定义字段的列表查询
	 * @param queryRequest 参数
	 * @param cols 列名
	 * @return 列表
	 */
	public List<CommunitySkuRel> listCols(CommunitySkuRelQueryRequest queryRequest, List<String> cols) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<CommunitySkuRel> rt = cq.from(CommunitySkuRel.class);
		cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
		Specification<CommunitySkuRel> spec = CommunitySkuRelWhereCriteriaBuilder.build(queryRequest);
		Predicate predicate = spec.toPredicate(rt, cq, cb);
		if (predicate != null) {
			cq.where(predicate);
		}
		Sort sort = queryRequest.getSort();
		if (sort.isSorted()) {
			cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
		}
		return this.converter(entityManager.createQuery(cq).getResultList(), cols);
	}

	/**
	 * 查询对象转换
	 * @param result
	 * @return
	 */
	private List<CommunitySkuRel> converter(List<Tuple> result, List<String> cols) {
		return result.stream().map(item -> {
			CommunitySkuRel sale = new CommunitySkuRel();
			sale.setSpuId(JpaUtil.toString(item,"spuId", cols));
			sale.setSkuId(JpaUtil.toString(item,"skuId", cols));
			return sale;
		}).collect(Collectors.toList());
	}

	/**
	 * 单个查询社区团购活动商品表
	 * @author dyt
	 */
	public CommunitySkuRel getOne(Long id){
		return communitySkuRelRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动商品表不存在"));
	}

	/**
	 * 分页查询社区团购活动商品表
	 * @author dyt
	 */
	public Page<CommunitySkuRel> page(CommunitySkuRelQueryRequest queryReq){
		return communitySkuRelRepository.findAll(
				CommunitySkuRelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动商品表
	 * @author dyt
	 */
	public List<CommunitySkuRel> list(CommunitySkuRelQueryRequest queryReq){
		return communitySkuRelRepository.findAll(CommunitySkuRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 活动填充商品
	 * @param voList 活动VO
	 */
	public void fillActivity(List<CommunityActivityVO> voList) {
		List<String> ids = voList.stream()
				.map(CommunityActivityVO::getActivityId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		CommunitySkuRelQueryRequest queryRequest = new CommunitySkuRelQueryRequest();
		queryRequest.setActivityIdList(ids);
		Map<String, List<CommunitySkuRelVO>> relMap = this.list(queryRequest).stream().map(this::wrapperVo).collect(Collectors.groupingBy(CommunitySkuRelVO::getActivityId));
		List<CommunitySkuRelVO> empty = Collections.emptyList();
		voList.forEach(v -> v.setSkuList(relMap.getOrDefault(v.getActivityId(), empty)));
	}

	@Transactional
	public void updateSales(UpdateSalesRequest updateSalesRequest){
		Boolean addFlag = updateSalesRequest.getAddFlag();
		updateSalesRequest.getUpdateSalesDTOS().forEach(g -> {
			communitySkuRelRepository.getBySkuIdAndActivityId(g.getGoodsInfoId(), g.getActivityId()).ifPresent(x -> {
				if (addFlag){
					if (Objects.nonNull(x.getActivityStock())){
						int count = communitySkuRelRepository.updateSalesCheck(g.getStock(), g.getActivityId(), g.getGoodsInfoId());
						if (count == 0){
							throw new SbcRuntimeException(MarketingErrorCodeEnum.K080203);
						}
					} else {
						communitySkuRelRepository.updateSales(g.getStock(), g.getActivityId(), g.getGoodsInfoId());
					}
				} else {
					communitySkuRelRepository.updateSales(~g.getStock() + 1, g.getActivityId(), g.getGoodsInfoId());
				}
			});
		});



	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunitySkuRelVO wrapperVo(CommunitySkuRel communitySkuRel) {
		if (communitySkuRel != null){
			CommunitySkuRelVO communitySkuRelVO = KsBeanUtil.convert(communitySkuRel, CommunitySkuRelVO.class);
			return communitySkuRelVO;
		}
		return null;
	}
}

