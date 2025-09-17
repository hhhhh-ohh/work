package com.wanmi.sbc.marketing.communitystatistics.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatistics;
import com.wanmi.sbc.marketing.communitystatistics.repository.CommunityStatisticsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动统计信息表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Service
public class CommunityStatisticsService {
	@Autowired
	private CommunityStatisticsRepository communityStatisticsRepository;

	/**
	 * 单个团长的统计数据汇总
	 * @param queryReq
	 * @return
	 */
	public CommunityStatistics findByLeaderIdGroup(CommunityStatisticsQueryRequest queryReq){
		CommunityStatistics vo = new CommunityStatistics();
		Object data = communityStatisticsRepository.findByLeaderIdGroup(queryReq.getLeaderId());
		if(Objects.nonNull(data)){
			vo.setCommissionReceived(((BigDecimal) ((Object[]) data)[0]));
			vo.setCommissionReceivedPickup(((BigDecimal) ((Object[]) data)[1]));
			vo.setCommissionReceivedAssist(((BigDecimal) ((Object[]) data)[2]));

			//未入账佣金 = 总未入账佣金 - 总已入账佣金 - 总退款佣金
			BigDecimal commissionPending = ((BigDecimal) ((Object[]) data)[3])
					.subtract(((BigDecimal) ((Object[]) data)[0]))
					.subtract(((BigDecimal) ((Object[]) data)[10]));
			vo.setCommissionPending(commissionPending);

			//未入账自提佣金 = 总未入账自提佣金 - 总已入账自提佣金 - 总退款自提佣金
			BigDecimal commissionPendingPickup = ((BigDecimal) ((Object[]) data)[4])
					.subtract(((BigDecimal) ((Object[]) data)[1]))
					.subtract(((BigDecimal) ((Object[]) data)[12]));
			vo.setCommissionPendingPickup(commissionPendingPickup);

			//未入账帮卖佣金 = 总未入账帮卖佣金 - 总已入账帮卖佣金 - 总退款帮卖佣金
			BigDecimal commissionPendingAssist = ((BigDecimal) ((Object[]) data)[5])
					.subtract(((BigDecimal) ((Object[]) data)[2]))
					.subtract(((BigDecimal) ((Object[]) data)[11]));
			vo.setCommissionPendingAssist(commissionPendingAssist);

			vo.setAssistOrderNum(((BigDecimal) ((Object[]) data)[6]).longValue());
			vo.setAssistOrderTotal(((BigDecimal) ((Object[]) data)[7]));
			vo.setPickupServiceOrderNum(((BigDecimal) ((Object[]) data)[8]).longValue());
			vo.setPickupServiceOrderTotal(((BigDecimal) ((Object[]) data)[9]));
		}

		return vo;
	}

	/**
	 * 多个团长的统计数据汇总
	 * @param queryReq
	 * @return
	 */
	public Map<String, CommunityStatisticsVO> findByLeaderIdsGroup(CommunityStatisticsQueryRequest queryReq){
		List<Object> data = communityStatisticsRepository.findByLeaderIdsGroup(queryReq.getIdList());

		// 转换MAP对象
		Map<String, CommunityStatisticsVO> communityStatisticsMap =
				data.stream()
						.map(
								obj -> {
									CommunityStatisticsVO vo =
											new CommunityStatisticsVO();
									vo.setCommissionReceived(((BigDecimal) ((Object[]) obj)[0]));
									vo.setCommissionReceivedPickup(((BigDecimal) ((Object[]) obj)[1]));
									vo.setCommissionReceivedAssist(((BigDecimal) ((Object[]) obj)[2]));

									//未入账佣金 = 总未入账佣金 - 总已入账佣金 - 总退款佣金
									BigDecimal commissionPending = ((BigDecimal) ((Object[]) obj)[3])
											.subtract(((BigDecimal) ((Object[]) obj)[0]))
											.subtract(((BigDecimal) ((Object[]) obj)[10]));
									vo.setCommissionPending(commissionPending);

									//未入账自提佣金 = 总未入账自提佣金 - 总已入账自提佣金 - 总退款自提佣金
									BigDecimal commissionPendingPickup = ((BigDecimal) ((Object[]) obj)[4])
											.subtract(((BigDecimal) ((Object[]) obj)[1]))
											.subtract(((BigDecimal) ((Object[]) obj)[12]));
									vo.setCommissionPendingPickup(commissionPendingPickup);

									//未入账帮卖佣金 = 总未入账帮卖佣金 - 总已入账帮卖佣金 - 总退款帮卖佣金
									BigDecimal commissionPendingAssist = ((BigDecimal) ((Object[]) obj)[5])
											.subtract(((BigDecimal) ((Object[]) obj)[2]))
											.subtract(((BigDecimal) ((Object[]) obj)[11]));
									vo.setCommissionPendingAssist(commissionPendingAssist);

									vo.setAssistOrderNum(((BigDecimal) ((Object[]) obj)[6]).longValue());
									vo.setAssistOrderTotal(((BigDecimal) ((Object[]) obj)[7]));
									vo.setPickupServiceOrderNum(((BigDecimal) ((Object[]) obj)[8]).longValue());
									vo.setPickupServiceOrderTotal(((BigDecimal) ((Object[]) obj)[9]));
									vo.setLeaderId(((String) ((Object[]) obj)[13]));
									return vo;
								})
						.collect(
								Collectors.toMap(
										CommunityStatisticsVO::getLeaderId, Function.identity()));

		return communityStatisticsMap;
	}

	/**
	 * 多个活动的统计数据汇总
	 * @param queryReq 统计数据参数
	 * @return 活动统计Map<活动id,统计信息>
	 */
	public Map<String, CommunityStatisticsVO> findByActivityIdsGroup(CommunityStatisticsQueryRequest queryReq) {
		List<Object> data = communityStatisticsRepository.findByActivityIdsGroup(queryReq.getActivityIds());
		// 转换MAP对象
		return data.stream()
				.filter(o -> o != null && ((Object[]) o).length > 0)
				.map(obj -> {
					Object[] results = StringUtil.cast(obj, Object[].class);
					CommunityStatisticsVO vo = new CommunityStatisticsVO();
					vo.setPayNum(StringUtil.cast(results, 0, BigDecimal.class) != null ? StringUtil.cast(results, 0,
							BigDecimal.class).longValue() : 0L);
					vo.setPayTotal(StringUtil.cast(results, 1, BigDecimal.class));
					vo.setAssistOrderNum(StringUtil.cast(results, 2, BigDecimal.class) != null ? StringUtil.cast(results
							, 2,	BigDecimal.class).longValue() : 0L);
					vo.setAssistOrderTotal(StringUtil.cast(results, 3, BigDecimal.class));
					vo.setCommissionReceived(StringUtil.cast(results, 4, BigDecimal.class));
					vo.setCommissionPending(StringUtil.cast(results, 5, BigDecimal.class));
					vo.setActivityId(StringUtil.cast(results, 6, String.class));
					//帮卖订单数/支付订单数×100%
					if (vo.getPayNum() > 0) {
						vo.setAssistOrderRatio(
								new BigDecimal(Long.toString(vo.getAssistOrderNum()))
										.multiply(new BigDecimal("100"))
										.divide(new BigDecimal(Long.toString(vo.getPayNum())), 2, RoundingMode.DOWN));
					} else if (vo.getAssistOrderNum() > 0) {
						vo.setAssistOrderRatio(new BigDecimal("100"));
					}
					return vo;
				})
				.collect(Collectors.toMap(CommunityStatisticsVO::getActivityId, Function.identity()));
	}

	/**
	 * 新增社区团购活动统计信息表
	 * @author dyt
	 */
	@Transactional
	public CommunityStatistics add(CommunityStatistics entity) {
		Optional<CommunityStatistics> optional =
				communityStatisticsRepository.findByActivityIdAndLeaderIdAndCreateDate(entity.getActivityId(),
						entity.getLeaderId(), LocalDate.now());
		if (optional.isPresent()){
			communityStatisticsRepository.update(entity.getPayTotal(),entity.getAssistOrderNum(),
					entity.getAssistOrderTotal(),
					entity.getCommissionPending(), entity.getCommissionPendingPickup(),
					entity.getCommissionPendingAssist(), entity.getActivityId(), entity.getLeaderId(),
					LocalDate.now(),
					entity.getPickupServiceOrderNum(), entity.getPickupServiceOrderTotal());
		} else {
			communityStatisticsRepository.save(entity);
		}
		return entity;
	}

	/**
	 * 修改社区团购活动统计信息表
	 * @author dyt
	 */
	@Transactional
	public CommunityStatistics modify(CommunityStatistics entity) {
		communityStatisticsRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除社区团购活动统计信息表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(String id) {
		communityStatisticsRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动统计信息表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动统计信息表
	 * @author dyt
	 */
	public CommunityStatistics getOne(String id, Long storeId){
		return communityStatisticsRepository.findByIdAndStoreId(id, storeId)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动统计信息表不存在"));
	}

	/**
	 * 分页查询社区团购活动统计信息表
	 * @author dyt
	 */
	public Page<CommunityStatistics> page(CommunityStatisticsQueryRequest queryReq){
		return communityStatisticsRepository.findAll(
				CommunityStatisticsWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动统计信息表
	 * @author dyt
	 */
	public List<CommunityStatistics> list(CommunityStatisticsQueryRequest queryReq){
		return communityStatisticsRepository.findAll(CommunityStatisticsWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityStatisticsVO wrapperVo(CommunityStatistics communityStatistics) {
		if (communityStatistics != null){
			return KsBeanUtil.convert(communityStatistics, CommunityStatisticsVO.class);
		}
		return null;
	}

	@Transactional
	public void returnTradeUpdate(CommunityStatistics entity){
		Optional<CommunityStatistics> optional =
				communityStatisticsRepository.findByActivityIdAndLeaderIdAndCreateDate(entity.getActivityId(),
						entity.getLeaderId(), LocalDate.now());
		if (optional.isPresent()){
			communityStatisticsRepository.returnUpdate(entity.getReturnTotal(),
					entity.getAssistReturnNum(), entity.getAssistReturnTotal(),
					entity.getReturnTradeCommission(), entity.getActivityId(),
					entity.getLeaderId(), LocalDate.now(),
					entity.getReturnTradeCommissionAssist(),
					entity.getReturnTradeCommissionPickup()
					);
		} else {
			entity.setPayNum(0L);
			entity.setPayTotal(BigDecimal.ZERO);
			entity.setAssistOrderNum(0L);
			entity.setAssistOrderTotal(BigDecimal.ZERO);
			entity.setCommissionPending(BigDecimal.ZERO);
			entity.setCommissionPendingAssist(BigDecimal.ZERO);
			entity.setCommissionPendingPickup(BigDecimal.ZERO);
			entity.setCommissionReceived(BigDecimal.ZERO);
			entity.setCommissionReceivedPickup(BigDecimal.ZERO);
			entity.setCommissionReceivedAssist(BigDecimal.ZERO);
			entity.setCreateDate(LocalDate.now());
			entity.setPickupServiceOrderNum(0L);
			entity.setPickupServiceOrderTotal(BigDecimal.ZERO);
			communityStatisticsRepository.save(entity);
		}
	}

	@Transactional
	public void commissionUpdate(CommunityStatistics entity){
		Optional<CommunityStatistics> optional =
				communityStatisticsRepository.findByActivityIdAndLeaderIdAndCreateDate(entity.getActivityId(),
						entity.getLeaderId(), LocalDate.now());
		if (optional.isPresent()){
			communityStatisticsRepository.commissionUpdate(entity.getCommissionReceived(),
					entity.getCommissionReceivedPickup(), entity.getCommissionReceivedAssist(),
					entity.getActivityId(), entity.getLeaderId(), LocalDate.now());
		} else {
			entity.setPayNum(0L);
			entity.setPayTotal(BigDecimal.ZERO);
			entity.setAssistOrderNum(0L);
			entity.setAssistOrderTotal(BigDecimal.ZERO);
			entity.setCommissionPending(BigDecimal.ZERO);
			entity.setCommissionPendingAssist(BigDecimal.ZERO);
			entity.setCommissionPendingPickup(BigDecimal.ZERO);
			entity.setCreateDate(LocalDate.now());
			entity.setReturnNum(0L);
			entity.setReturnTotal(BigDecimal.ZERO);
			entity.setAssistReturnNum(0L);
			entity.setAssistReturnTotal(BigDecimal.ZERO);
			entity.setReturnTradeCommission(BigDecimal.ZERO);
			entity.setPickupServiceOrderNum(0L);
			entity.setPickupServiceOrderTotal(BigDecimal.ZERO);
			entity.setReturnTradeCommissionAssist(BigDecimal.ZERO);
			entity.setReturnTradeCommissionPickup(BigDecimal.ZERO);
			communityStatisticsRepository.save(entity);
		}
	}
}

