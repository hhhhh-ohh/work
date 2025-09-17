package com.wanmi.sbc.marketing.communityassist.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordLeaderCountRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityAssistRecordVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.marketing.communityassist.model.root.CommunityAssistRecord;
import com.wanmi.sbc.marketing.communityassist.repository.CommunityAssistRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动帮卖转发记录业务逻辑</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Service
public class CommunityAssistRecordService {
	@Autowired
	private CommunityAssistRecordRepository communityAssistRecordRepository;

	/**
	 * 新增社区团购活动帮卖转发记录
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityAssistRecord entity) {
		CommunityAssistRecordQueryRequest queryRequest = CommunityAssistRecordQueryRequest.builder()
				.leaderId(entity.getLeaderId()).activityId(entity.getActivityId()).build();
		if (communityAssistRecordRepository.count(CommunityAssistRecordWhereCriteriaBuilder.build(queryRequest)) > 0) {
			return;
		}
		entity.setCreateTime(LocalDateTime.now());
		communityAssistRecordRepository.save(entity);
	}

	/**
	 * 修改社区团购活动帮卖转发记录
	 * @author dyt
	 */
	@Transactional
	public CommunityAssistRecord modify(CommunityAssistRecord entity) {
		communityAssistRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除社区团购活动帮卖转发记录
	 * @author dyt
	 */
	@Transactional
	public void deleteById(String id) {
		communityAssistRecordRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动帮卖转发记录
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动帮卖转发记录
	 * @author dyt
	 */
	public CommunityAssistRecord getOne(String id){
		return communityAssistRecordRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动帮卖转发记录不存在"));
	}

	/**
	 * 分页查询社区团购活动帮卖转发记录
	 * @author dyt
	 */
	public Page<CommunityAssistRecord> page(CommunityAssistRecordQueryRequest queryReq){
		return communityAssistRecordRepository.findAll(
				CommunityAssistRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购活动帮卖转发记录
	 * @author dyt
	 */
	public List<CommunityAssistRecord> list(CommunityAssistRecordQueryRequest queryReq){
		return communityAssistRecordRepository.findAll(CommunityAssistRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 多个活动的帮卖团长去重数汇总
	 * @param queryReq 统计数据参数
	 * @return 活动统计Map<活动id,帮卖团长去重数>
	 */
	public Map<String, Long> countLeaderGroupActivity(CommunityAssistRecordLeaderCountRequest queryReq) {
		List<Object> data = communityAssistRecordRepository.totalGroupActivityIds(queryReq.getActivityIds());
		// 转换MAP对象
		return data.stream()
				.filter(o -> o != null && ((Object[]) o).length > 0)
				.map(
						obj -> {
							Object[] results = StringUtil.cast(obj, Object[].class);
							CommunityStatisticsVO vo = new CommunityStatisticsVO();
							vo.setActivityId(StringUtil.cast(results, 0, String.class));
							vo.setAssistNum(StringUtil.cast(results, 1, Long.class));
							return vo;
						})
				.collect(Collectors.toMap(CommunityStatisticsVO::getActivityId, CommunityStatisticsVO::getAssistNum));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityAssistRecordVO wrapperVo(CommunityAssistRecord communityAssistRecord) {
		if (communityAssistRecord != null){
			CommunityAssistRecordVO communityAssistRecordVO = KsBeanUtil.convert(communityAssistRecord, CommunityAssistRecordVO.class);
			return communityAssistRecordVO;
		}
		return null;
	}
}

