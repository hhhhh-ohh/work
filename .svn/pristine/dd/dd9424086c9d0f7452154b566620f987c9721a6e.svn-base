package com.wanmi.sbc.marketing.communityactivity.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityLogisticsType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityactivity.repository.CommunityActivityRepository;
import com.wanmi.sbc.marketing.communityrel.service.CommunityAreaRelService;
import com.wanmi.sbc.marketing.communityrel.service.CommunityCommissionAreaRelService;
import com.wanmi.sbc.marketing.communityrel.service.CommunityCommissionLeaderRelService;
import com.wanmi.sbc.marketing.communityrel.service.CommunityLeaderRelService;
import com.wanmi.sbc.marketing.communitysku.service.CommunitySkuRelService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
 * <p>社区团购活动表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Service
public class CommunityActivityService {
	@Autowired
	private CommunityActivityRepository communityActivityRepository;

	@Autowired
	private CommunityAreaRelService communityAreaRelService;

	@Autowired
	private CommunityLeaderRelService communityLeaderRelService;

	@Autowired
	private CommunityCommissionAreaRelService communityCommissionAreaRelService;

	@Autowired
	private CommunityCommissionLeaderRelService communityCommissionLeaderRelService;

	@Autowired
	private CommunitySkuRelService communitySkuRelService;

	@Autowired private EntityManager entityManager;

	/**
	 * 任务延时时间5分钟
	 */
	private static final long JOB_TIME_END = 5L;

	/**
	 * 新增社区团购活动表
	 * @author dyt
	 */
	@Transactional
	public CommunityActivity add(CommunityActivityAddRequest addRequest) {
		CommunityActivity activity = KsBeanUtil.convert(addRequest, CommunityActivity.class);
		if (activity == null) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
		}
		activity.setLogisticsType(CommunityLogisticsType.toValue(addRequest.getLogisticsTypes()));
		activity.setSalesType(CommunitySalesType.toValue(addRequest.getSalesTypes()));
		activity.setCreateTime(LocalDateTime.now());
		activity.setGenerateFlag(Constants.no);
		activity.setRealEndTime(activity.getEndTime().plusMinutes(JOB_TIME_END));
		activity = communityActivityRepository.save(activity);
		communityAreaRelService.add(activity, addRequest);
		communityLeaderRelService.add(activity, addRequest);
		communityCommissionAreaRelService.add(activity, addRequest);
		communityCommissionLeaderRelService.add(activity, addRequest);
		communitySkuRelService.add(activity, addRequest);
		return activity;
	}

	/**
	 * 修改社区团购活动表
	 * @author dyt
	 */
	@Transactional
	public CommunityActivity modify(CommunityActivityModifyRequest modifyRequest) {
		CommunityActivity activity = this.getOne(modifyRequest.getActivityId(), modifyRequest.getStoreId());
		if (Constants.yes.equals(activity.getGenerateFlag())) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "该活动已生成备货单和发货单，无法修改");
		}
		CommunityTabStatus status = this.getStatus(activity);
		if (CommunityTabStatus.STARTED.equals(status)) {
			activity.setEndTime(modifyRequest.getEndTime());
			activity.setDescription(modifyRequest.getDescription());
			activity.setImages(modifyRequest.getImages());
			activity.setVideoUrl(modifyRequest.getVideoUrl());
			activity.setDetails(modifyRequest.getDetails());
			activity.setUpdateTime(LocalDateTime.now());
			activity.setRealEndTime(activity.getEndTime().plusMinutes(JOB_TIME_END));
			activity.setGenerateFlag(Constants.no);
			communityActivityRepository.save(activity);
			communitySkuRelService.modifyStock(modifyRequest);
		} else if (CommunityTabStatus.NOT_START.equals(status)) {
			KsBeanUtil.copyPropertiesThird(modifyRequest, activity);
			activity.setLogisticsType(CommunityLogisticsType.toValue(modifyRequest.getLogisticsTypes()));
			activity.setSalesType(CommunitySalesType.toValue(modifyRequest.getSalesTypes()));
			activity.setUpdateTime(LocalDateTime.now());
			activity.setRealEndTime(activity.getEndTime().plusMinutes(JOB_TIME_END));
			communityActivityRepository.save(activity);
			communityAreaRelService.modify(activity, modifyRequest);
			communityLeaderRelService.modify(activity, modifyRequest);
			communityCommissionAreaRelService.modify(activity, modifyRequest);
			communityCommissionLeaderRelService.modify(activity, modifyRequest);
			communitySkuRelService.modify(activity, modifyRequest);
		}
		return activity;
	}

	/**
	 * 自定义字段的列表查询
	 * @param request 参数
	 * @param cols 列名
	 * @return 列表
	 */
	public Page<CommunityActivity> pageCols(CommunityActivityQueryRequest request, List<String> cols) {
		CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
		Specification<CommunityActivity> spec = CommunityActivityWhereCriteriaBuilder.build(request);
		CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
		Root<CommunityActivity> countRt = countCq.from(CommunityActivity.class);
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
		Root<CommunityActivity> rt = cq.from(CommunityActivity.class);
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
	 * 获取活动状态
	 * @param activity
	 * @return
	 */
	private CommunityTabStatus getStatus(CommunityActivity activity) {
		LocalDateTime now = LocalDateTime.now();
		if ((activity.getStartTime().isBefore(now) || activity.getStartTime().isEqual(now))
				&& (activity.getEndTime().isAfter(now) || activity.getEndTime().isEqual(now))) {
			return CommunityTabStatus.STARTED;
		} else if (activity.getStartTime().isAfter(now)) {
			return CommunityTabStatus.NOT_START;
		} else if (activity.getEndTime().isBefore(now)) {
			return CommunityTabStatus.ENDED;
		}
		return null;
	}

	/**
	 * 单个删除社区团购活动表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(String id) {
		communityActivityRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 批量删除社区团购活动表
	 * @author dyt
	 */
	@Transactional
	public void modifyGenerateFlagByIds(List<String> ids, Integer generateFlag, LocalDateTime generateTime) {
		communityActivityRepository.updateGenerateFlagByIds(ids, generateFlag, generateTime);
	}

	/**
	 * 单个查询社区团购活动表
	 * @author dyt
	 */
	public CommunityActivity getOne(String id, Long storeId){
		if(storeId == null){
			return communityActivityRepository.findById(id)
					.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动表不存在"));
		}
		return communityActivityRepository.findByActivityIdAndStoreId(id, storeId)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动表不存在"));
	}

	/**
	 * 分页查询社区团购活动表
	 * @author dyt
	 */
	public Page<CommunityActivity> page(CommunityActivityQueryRequest queryReq){
		return communityActivityRepository.findAll(
				CommunityActivityWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动表
	 * @author dyt
	 */
	public List<CommunityActivity> list(CommunityActivityQueryRequest queryReq){
		return communityActivityRepository.findAll(CommunityActivityWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 填充社区团购VO
	 * @param request
	 * @param voList
	 */
	public void fillCommunityActivityVO(CommunityActivityQueryRequest request, List<CommunityActivityVO> voList) {
		//填充销售范围
		if (Boolean.TRUE.equals(request.getSaleRelFlag())) {
			communityAreaRelService.fillActivity(voList);
			communityLeaderRelService.fillActivity(voList);
		}
		//填充佣金设置
		if (Boolean.TRUE.equals(request.getCommissionRelFlag())) {
			communityCommissionAreaRelService.fillActivity(voList);
			communityCommissionLeaderRelService.fillActivity(voList, request.getLeaderId());
		}
		//填充商品关联设置
		if (Boolean.TRUE.equals(request.getSkuRelFlag())) {
			communitySkuRelService.fillActivity(voList);
		}
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityActivityVO wrapperVo(CommunityActivity communityActivity) {
		if (communityActivity != null) {
			CommunityActivityVO communityActivityVO = KsBeanUtil.convert(communityActivity, CommunityActivityVO.class);
			if (communityActivityVO == null) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
			}
			communityActivityVO.setSalesTypes(CommunitySalesType.fromValue(communityActivity.getSalesType()));
			communityActivityVO.setLogisticsTypes(CommunityLogisticsType.fromValue(communityActivity.getLogisticsType()));
			communityActivityVO.setActivityStatus(this.getStatus(communityActivity));
			return communityActivityVO;
		}
		return null;
	}

	/**
	 * 查询对象转换
	 * @param result
	 * @return
	 */
	private List<CommunityActivity> converter(List<Tuple> result, List<String> cols) {
		return result.stream().map(item -> {
			CommunityActivity activity = new CommunityActivity();
			activity.setActivityId(JpaUtil.toString(item,"activityId", cols));
			return activity;
		}).collect(Collectors.toList());
	}
}

