package com.wanmi.sbc.goods.buycyclegoods.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsAddRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoAddRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoModifyRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoQueryRequest;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.BuyCycleVO;
import com.wanmi.sbc.goods.buycyclegoods.model.root.BuyCycleGoods;
import com.wanmi.sbc.goods.buycyclegoods.repository.BuyCycleGoodsRepository;
import com.wanmi.sbc.goods.buycyclegoodsinfo.model.root.BuyCycleGoodsInfo;
import com.wanmi.sbc.goods.buycyclegoodsinfo.repository.BuyCycleGoodsInfoRepository;
import com.wanmi.sbc.goods.buycyclegoodsinfo.service.BuyCycleGoodsInfoWhereCriteriaBuilder;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>周期购spu表业务逻辑</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Service("BuyCycleGoodsService")
public class BuyCycleGoodsService {

	@Autowired
	private BuyCycleGoodsRepository buyCycleGoodsRepository;
	@Autowired
	private BuyCycleGoodsInfoRepository buyCycleGoodsInfoRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private GoodsInfoRepository goodsInfoRepository;
	@Autowired private EntityManager entityManager;

	/**
	 * 新增周期购spu表
	 * @author zhanghao
	 */
	@Transactional
	public void add(BuyCycleGoodsAddRequest buyCycleGoodsAddRequest) {
		//保存周期购spu
		BuyCycleGoods buyCycleGoods = KsBeanUtil.convert(buyCycleGoodsAddRequest, BuyCycleGoods.class);
		Objects.requireNonNull(buyCycleGoods).setCreateTime(LocalDateTime.now());
		buyCycleGoodsRepository.save(buyCycleGoods);
		List<BuyCycleGoodsInfoAddRequest> buyCycleGoodsInfoAddRequestList = buyCycleGoodsAddRequest.getBuyCycleGoodsInfoAddRequestList();
		//保存周期购sku
		String goodsId = buyCycleGoodsAddRequest.getGoodsId();
		buyCycleGoodsInfoRepository.saveAll(buyCycleGoodsInfoAddRequestList.parallelStream()
				.map(buyCycleGoodsInfoAddRequest -> {
					BuyCycleGoodsInfo buyCycleGoodsInfo = KsBeanUtil.convert(buyCycleGoodsInfoAddRequest, BuyCycleGoodsInfo.class);
					Objects.requireNonNull(buyCycleGoodsInfo).setGoodsId(goodsId);
					buyCycleGoodsInfo.setDelFlag(DeleteFlag.NO);
					buyCycleGoodsInfo.setBuyCycleId(buyCycleGoods.getId());
					buyCycleGoodsInfo.setCycleState(buyCycleGoods.getCycleState());
					buyCycleGoodsInfo.setCreatePerson(buyCycleGoodsAddRequest.getCreatePerson());
					buyCycleGoodsInfo.setCreateTime(LocalDateTime.now());
					return buyCycleGoodsInfo;
				})
				.collect(Collectors.toList()));
		// 修改SPU是否参与周期购的标识
		goodsRepository.modifyIsBuyCycleById(Constants.ONE, goodsId);
		// 修改SKU是否参与周期购的标识
		goodsInfoRepository.modifyIsBuyCycleByIds(Constants.ONE,buyCycleGoodsInfoAddRequestList.parallelStream()
				.map(BuyCycleGoodsInfoAddRequest::getGoodsInfoId)
				.collect(Collectors.toList()));
	}

	/**
	 * 修改周期购spu表
	 * @author zhanghao
	 */
	@Transactional
	public void modify(BuyCycleGoodsModifyRequest buyCycleGoodsModifyRequest) {
		BuyCycleGoods entity = KsBeanUtil.convert(buyCycleGoodsModifyRequest, BuyCycleGoods.class);
		BuyCycleGoods buyCycleGoods = getOne(entity.getId());
		entity.setCycleState(buyCycleGoods.getCycleState());
		entity.setUpdateTime(LocalDateTime.now());
		entity.setStoreId(buyCycleGoods.getStoreId());
		entity.setDelFlag(buyCycleGoods.getDelFlag());
		entity.setGoodsId(buyCycleGoods.getGoodsId());
		entity.setCreateTime(buyCycleGoods.getCreateTime());
		entity.setGoodsName(buyCycleGoods.getGoodsName());
		buyCycleGoodsRepository.save(entity);
		List<BuyCycleGoodsInfoModifyRequest> buyCycleGoodsInfoModifyRequests = buyCycleGoodsModifyRequest.getBuyCycleGoodsInfoModifyRequests();
		List<BuyCycleGoodsInfo> buyCycleGoodsInfos = Lists.newArrayList();
		buyCycleGoodsInfoModifyRequests.forEach(buyCycleGoodsInfoModifyRequest -> {
			Long id = buyCycleGoodsInfoModifyRequest.getId();
			BuyCycleGoodsInfo buyCycleGoodsInfo = buyCycleGoodsInfoRepository.findById(id).orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
			buyCycleGoodsInfo.setCyclePrice(buyCycleGoodsInfoModifyRequest.getCyclePrice());
			buyCycleGoodsInfo.setMinCycleNum(buyCycleGoodsInfoModifyRequest.getMinCycleNum());
			buyCycleGoodsInfos.add(buyCycleGoodsInfo);
		});
		//保存周期购sku
		buyCycleGoodsInfoRepository.saveAll(buyCycleGoodsInfos);
	}

	/**
	 * 单个删除周期购spu表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(Long id) {
		BuyCycleGoods buyCycleGoods= getOne(id);
		String goodsId = buyCycleGoods.getGoodsId();
		List<BuyCycleGoodsInfo> buyCycleGoodsInfos = buyCycleGoodsInfoRepository.findAll(BuyCycleGoodsInfoWhereCriteriaBuilder.build(BuyCycleGoodsInfoQueryRequest.builder()
				.buyCycleId(id)
				.delFlag(DeleteFlag.NO)
				.build()));
		List<String> goodsInfoIds = buyCycleGoodsInfos.stream().map(BuyCycleGoodsInfo::getGoodsInfoId).distinct().collect(Collectors.toList());
		List<String> activeSkuIds = buyCycleGoodsInfoRepository.findActiveCountExclude(goodsInfoIds, id);
		if (CollectionUtils.isNotEmpty(activeSkuIds)) {
			goodsInfoIds = goodsInfoIds.stream().filter(goodsInfoId -> !activeSkuIds.contains(goodsInfoId)).collect(Collectors.toList());
		} else {
			// 修改SPU是否参与周期购的标识
			goodsRepository.modifyIsBuyCycleById(Constants.ZERO, goodsId);
		}
		// 修改SKU是否参与周期购的标识
		goodsInfoRepository.modifyIsBuyCycleByIds(Constants.ZERO,goodsInfoIds);
		buyCycleGoodsRepository.deleteById(id);
		buyCycleGoodsInfoRepository.deleteByBuyCycleId(id);
	}

	/**
	 * 修改周期购spu表状态
	 * @author zhanghao
	 */
	@Transactional
	public void modifyState(Long id,Integer cycleState) {
		BuyCycleGoods buyCycleGoods= getOne(id);
		String goodsId = buyCycleGoods.getGoodsId();
		BuyCycleGoods goods = findByGoodsIdAndCycleStateAndDelFlag(goodsId);
		if (Objects.nonNull(goods) && cycleState == Constants.ZERO )  {
			throw new SbcRuntimeException(OrderErrorCodeEnum.K050158);
		}
		buyCycleGoodsRepository.modifyState(id,cycleState);
		buyCycleGoodsInfoRepository.modifyStateByBuyCycleId(id,cycleState);
		Integer isBuyCycle = cycleState == Constants.ZERO ? Constants.ONE : Constants.ZERO;
		// 修改SPU是否参与周期购的标识
		goodsRepository.modifyIsBuyCycleById(isBuyCycle, goodsId);
		List<BuyCycleGoodsInfo> buyCycleGoodsInfos = buyCycleGoodsInfoRepository.findAll(BuyCycleGoodsInfoWhereCriteriaBuilder.build(BuyCycleGoodsInfoQueryRequest.builder()
				.buyCycleId(id)
				.delFlag(DeleteFlag.NO)
				.build()));
		// 修改SKU是否参与周期购的标识
		goodsInfoRepository.modifyIsBuyCycleByIds(isBuyCycle,buyCycleGoodsInfos.parallelStream()
				.map(BuyCycleGoodsInfo::getGoodsInfoId)
				.collect(Collectors.toList()));
	}

	/**
	 * 单个查询周期购spu表
	 * @author zhanghao
	 */
	public BuyCycleGoods getOne(Long id){
		return buyCycleGoodsRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003));
	}

	/**
	 * 单个查询周期购spu表
	 * @author zhanghao
	 */
	public BuyCycleGoods findByGoodsIdAndCycleStateAndDelFlag(String goodsId){
		return buyCycleGoodsRepository.findByGoodsIdAndCycleStateAndDelFlag(goodsId, Constants.ZERO,DeleteFlag.NO)
				.orElse(null);
	}

	/**
	 * 分页查询周期购spu表
	 * @author zhanghao
	 */
	public Page<BuyCycleGoods> page(BuyCycleGoodsQueryRequest queryReq){
		return buyCycleGoodsRepository.findAll(
				BuyCycleGoodsWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询周期购spu表
	 * @author zhanghao
	 */
	public List<BuyCycleGoods> list(BuyCycleGoodsQueryRequest queryReq){
		return buyCycleGoodsRepository.findAll(BuyCycleGoodsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public BuyCycleGoodsVO wrapperVo(BuyCycleGoods buyCycleGoods) {
		if (buyCycleGoods != null){
			return KsBeanUtil.convert(buyCycleGoods, BuyCycleGoodsVO.class);
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(BuyCycleGoodsQueryRequest queryReq) {
		return buyCycleGoodsRepository.count(BuyCycleGoodsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 根据skuId查询周期购信息
	 * @param skuId
	 * @return
	 */
	public BuyCycleVO getBySkuId(String skuId){
		BuyCycleGoodsInfo buyCycleGoodsInfo = buyCycleGoodsInfoRepository.findByGoodsInfoIdAndCycleStateAndDelFlag(skuId, Constants.ZERO,DeleteFlag.NO).orElse(null);
		if (buyCycleGoodsInfo == null) {
			return null;
		}

		BuyCycleGoods buyCycleGoods = getOne(buyCycleGoodsInfo.getBuyCycleId());
		if (buyCycleGoods == null) {
			return null;
		}

		BuyCycleVO buyCycleVO = KsBeanUtil.convert(buyCycleGoods, BuyCycleVO.class);
		buyCycleVO.setGoodsInfoId(buyCycleGoodsInfo.getGoodsInfoId());
		buyCycleVO.setMinCycleNum(buyCycleGoodsInfo.getMinCycleNum());
		buyCycleVO.setCyclePrice(buyCycleGoodsInfo.getCyclePrice());
		return buyCycleVO;
	}
	/**
	 * 自定义字段的列表查询
	 * @param request 参数
	 * @param cols 列名
	 * @return 列表
	 */
	public Page<BuyCycleGoods> pageCols(BuyCycleGoodsQueryRequest request, List<String> cols) {
		CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
		Specification<BuyCycleGoods> spec = BuyCycleGoodsWhereCriteriaBuilder.build(request);
		CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
		Root<BuyCycleGoods> countRt = countCq.from(BuyCycleGoods.class);
		countCq.select(countCb.count(countRt));
		Predicate countPredicate = spec.toPredicate(countRt, countCq, countCb);
		if (countPredicate != null) {
			countCq.where(countPredicate);
		}
		long sum = entityManager.createQuery(countCq).getResultList().stream().filter(Objects::nonNull)
				.mapToLong(s -> s).sum();
		if (sum == 0) {
			return PageableExecutionUtils.getPage(Collections.emptyList(), request.getPageable(), () -> sum);
		}
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<BuyCycleGoods> rt = cq.from(BuyCycleGoods.class);
		cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
		Predicate predicate = spec.toPredicate(rt, cq, cb);
		if (predicate != null) {
			cq.where(predicate);
		}
		cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
		TypedQuery<Tuple> query = entityManager.createQuery(cq);
		query.setFirstResult((int) request.getPageRequest().getOffset());
		query.setMaxResults(request.getPageRequest().getPageSize());
		return PageableExecutionUtils.getPage(this.converter(query.getResultList(), cols), request.getPageable(), () -> sum);
	}

	/**
	 * 查询对象转换
	 * @param result
	 * @return
	 */
	private List<BuyCycleGoods> converter(List<Tuple> result, List<String> cols) {
		return result.stream().map(item -> {
			BuyCycleGoods activity = new BuyCycleGoods();
			activity.setId(JpaUtil.toLong(item,"id", cols));
			activity.setGoodsId(JpaUtil.toString(item,"goodsId", cols));
			return activity;
		}).collect(Collectors.toList());
	}
}

