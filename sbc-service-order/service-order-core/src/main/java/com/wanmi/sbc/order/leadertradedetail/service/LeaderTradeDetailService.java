package com.wanmi.sbc.order.leadertradedetail.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.order.api.request.leadertradedetail.*;
import com.wanmi.sbc.order.bean.vo.CommunityTradeCountVO;
import com.wanmi.sbc.order.bean.vo.LeaderTradeDetailVO;
import com.wanmi.sbc.order.leadertradedetail.model.root.LeaderTradeDetail;
import com.wanmi.sbc.order.leadertradedetail.repository.LeaderTradeDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>团长订单业务逻辑</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Service("LeaderTradeDetailService")
public class LeaderTradeDetailService {
	@Autowired
	private LeaderTradeDetailRepository leaderTradeDetailRepository;

	/**
	 * 新增团长订单
	 * @author Bob
	 */
	@Transactional
	public LeaderTradeDetail add(LeaderTradeDetail entity) {
		leaderTradeDetailRepository.save(entity);
		return entity;
	}

	@Transactional
	public List<LeaderTradeDetail> addBatch(List<LeaderTradeDetail> entity) {
		leaderTradeDetailRepository.saveAll(entity);
		return entity;
	}

	/**
	 * 修改团长订单
	 * @author Bob
	 */
	@Transactional
	public LeaderTradeDetail modify(LeaderTradeDetail entity) {
		leaderTradeDetailRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除团长订单
	 * @author Bob
	 */
	@Transactional
	public void deleteById(LeaderTradeDetail entity) {
		leaderTradeDetailRepository.save(entity);
	}

	/**
	 * 批量删除团长订单
	 * @author Bob
	 */
	@Transactional
	public void deleteByIdList(List<LeaderTradeDetail> infos) {
		leaderTradeDetailRepository.saveAll(infos);
	}

	/**
	 * 单个查询团长订单
	 * @author Bob
	 */
	public LeaderTradeDetail getOne(Long id){
		return leaderTradeDetailRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003, "团长订单不存在"));
	}

	/**
	 * 分页查询团长订单
	 * @author Bob
	 */
	public Page<LeaderTradeDetail> page(LeaderTradeDetailQueryRequest queryReq){
		return leaderTradeDetailRepository.findAll(
				LeaderTradeDetailWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询团长订单
	 * @author Bob
	 */
	public List<LeaderTradeDetail> list(LeaderTradeDetailQueryRequest queryReq){
		return leaderTradeDetailRepository.findAll(LeaderTradeDetailWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * @description 团当前最大跟团号
	 * @author  edz
	 * @date: 2023/8/3 15:47
	 * @param activityId
	 * @return int
	 */
	@Transactional
	public Long getActivityTradeNo(String activityId){
		return leaderTradeDetailRepository.findActivityNo(activityId);
	}

	/**
	 * 将实体包装成VO
	 * @author Bob
	 */
	public LeaderTradeDetailVO wrapperVo(LeaderTradeDetail leaderTradeDetail) {
		if (leaderTradeDetail != null){
			LeaderTradeDetailVO leaderTradeDetailVO = KsBeanUtil.convert(leaderTradeDetail, LeaderTradeDetailVO.class);
			return leaderTradeDetailVO;
		}
		return null;
	}


	/**
	 * 查询团长的跟团人数（支付成功+帮卖+去重）
	 * @param leaderId
	 * @return
	 */
	public Long findFollowNum(String leaderId){
		return leaderTradeDetailRepository.findFollowNum(leaderId);
	}

	/**
	 * 跟团记录按活动分组做分页查询
	 * @param request 筛选参数
	 * @return 活动跟团记录
	 */
	public List<LeaderTradeDetailVO> topGroupActivity(LeaderTradeDetailTopGroupByActivityRequest request) {
		if(request.getLeaderIds() == null) {
			request.setLeaderIds(Collections.emptyList());
		}
		List<LeaderTradeDetail> detailList = leaderTradeDetailRepository.queryTopByActivityIds(request.getActivityIds(), request.getLeaderIds(), 10, request.getBoolFlag());
		return detailList.stream().map(this::wrapperVo).collect(Collectors.toList());
	}

	/**
	 * 统计跟团次数查询
	 * @param request 筛选参数
	 * @return 活动跟团记录
	 */
	public List<CommunityTradeCountVO> totalTradeByActivityIds(LeaderTradeDetailTradeCountGroupByActivityRequest request) {
		if(request.getLeaderIds() == null) {
			request.setLeaderIds(Collections.emptyList());
		}
		List<Object> objects = leaderTradeDetailRepository.totalTradeByActivityIds(request.getActivityIds(), request.getLeaderIds(), DeleteFlag.NO.toValue(), request.getBoolFlag() == null? null: request.getBoolFlag().toValue());
		// 转换MAP对象
		return objects.stream()
				.filter(o -> o != null && ((Object[]) o).length > 0)
				.map(
						obj -> {
							Object[] results = StringUtil.cast(obj, Object[].class);
							CommunityTradeCountVO vo = new CommunityTradeCountVO();
							vo.setActivityId(StringUtil.cast(results, 0, String.class));
							vo.setPayOrderNum(StringUtil.cast(results, 1, Long.class));
							return vo;
						})
				.collect(Collectors.toList());
	}

	/**
	 * 多个活动的成团团长去重数汇总
	 * @param queryReq 统计数据参数
	 * @return 活动统计Map<活动id,成团团长去重数>
	 */
	public Map<String, Long> countLeaderGroupActivity(LeaderTradeDetailTradeCountLeaderRequest queryReq) {
		List<Object> data = leaderTradeDetailRepository.countLeaderGroupActivityIds(queryReq.getActivityIds(), DeleteFlag.NO, queryReq.getBoolFlag());
		// 转换MAP对象
		return data.stream()
				.filter(o -> o != null && ((Object[]) o).length > 0)
				.map(
						obj -> {
							Object[] results = StringUtil.cast(obj, Object[].class);
							CommunityStatisticsVO vo = new CommunityStatisticsVO();
							vo.setActivityId(StringUtil.cast(results, 0, String.class));
							vo.setAssistPayNum(StringUtil.cast(results, 1, Long.class));
							return vo;
						})
				.collect(Collectors.toMap(CommunityStatisticsVO::getActivityId, CommunityStatisticsVO::getAssistPayNum));
	}

	/**
	 * 多个活动的参团人数（去重）
	 * @param queryReq 统计数据参数
	 * @return 活动统计Map<活动id,参团人数（去重）>
	 */
	public Map<String, Long> countCustomerGroupActivity(LeaderTradeDetailTradeCountCustomerRequest queryReq) {
		List<Object> data = leaderTradeDetailRepository.countCustomerGroupActivityIds(queryReq.getActivityIds(), DeleteFlag.NO, queryReq.getBoolFlag());
		// 转换MAP对象
		return data.stream()
				.filter(o -> o != null && ((Object[]) o).length > 0)
				.map(
						obj -> {
							Object[] results = StringUtil.cast(obj, Object[].class);
							CommunityStatisticsVO vo = new CommunityStatisticsVO();
							vo.setActivityId(StringUtil.cast(results, 0, String.class));
							vo.setCustomerNum(StringUtil.cast(results, 1, Long.class));
							return vo;
						})
				.collect(Collectors.toMap(CommunityStatisticsVO::getActivityId, CommunityStatisticsVO::getCustomerNum));
	}

}

