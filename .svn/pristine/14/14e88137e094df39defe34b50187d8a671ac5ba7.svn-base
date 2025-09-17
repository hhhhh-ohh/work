package com.wanmi.sbc.marketing.communitystatistics.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerCountCustomerRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsCustomerVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatisticsCustomer;
import com.wanmi.sbc.marketing.communitystatistics.repository.CommunityStatisticsCustomerRepository;
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
 * <p>社区团购活动会员信息表业务逻辑</p>
 * @author dyt
 * @date 2023-07-24 14:49:55
 */
@Service
public class CommunityStatisticsCustomerService {
	@Autowired
	private CommunityStatisticsCustomerRepository communityStatisticsCustomerRepository;

	/**
	 * 新增社区团购活动会员信息表
	 * @author dyt
	 */
	@Transactional
	public CommunityStatisticsCustomer add(CommunityStatisticsCustomer entity) {
		entity.setCreateTime(LocalDateTime.now());
		communityStatisticsCustomerRepository.save(entity);
		return entity;
	}

	/**
	 * 修改社区团购活动会员信息表
	 * @author dyt
	 */
	@Transactional
	public CommunityStatisticsCustomer modify(CommunityStatisticsCustomer entity) {
		communityStatisticsCustomerRepository.save(entity);
		return entity;
	}

	/**
	 * 团长分组的统计去重会员人数
	 * @param queryReq 统计数据参数
	 * @return 活动统计Map<团长id,会员人数去重数>
	 */
	public Map<String, Long> countCustomerGroupLeader(CommunityStatisticsCustomerCountCustomerRequest queryReq) {
		List<Object> data = communityStatisticsCustomerRepository.countCustomerGroupLeaderIds(queryReq.getLeaderIds());
		// 转换MAP对象
		return data.stream()
				.filter(o -> o != null && ((Object[]) o).length > 0)
				.map(obj -> {
					Object[] results = StringUtil.cast(obj, Object[].class);
					CommunityStatisticsVO vo = new CommunityStatisticsVO();
					vo.setLeaderId(StringUtil.cast(results, 0, String.class));
					vo.setCustomerAddNum(StringUtil.cast(results, 1, Long.class));
					return vo;
				})
				.collect(Collectors.toMap(CommunityStatisticsVO::getLeaderId, CommunityStatisticsVO::getCustomerAddNum));
	}

	/**
	 * 单个删除社区团购活动会员信息表
	 * @author dyt
	 */
	@Transactional
	public void deleteById(String id) {
		communityStatisticsCustomerRepository.deleteById(id);
	}

	/**
	 * 批量删除社区团购活动会员信息表
	 * @author dyt
	 */
	@Transactional
	public void deleteByIdList(List<String> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询社区团购活动会员信息表
	 * @author dyt
	 */
	public CommunityStatisticsCustomer getOne(String id){
		return communityStatisticsCustomerRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购活动会员信息表不存在"));
	}

	/**
	 * 分页查询社区团购活动会员信息表
	 * @author dyt
	 */
	public Page<CommunityStatisticsCustomer> page(CommunityStatisticsCustomerQueryRequest queryReq){
		return communityStatisticsCustomerRepository.findAll(
				CommunityStatisticsCustomerWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 拓客人数
	 * @author dyt
	 */
	public Long count(CommunityStatisticsCustomerQueryRequest queryReq){
		return communityStatisticsCustomerRepository.count(
				CommunityStatisticsCustomerWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 列表查询社区团购活动会员信息表
	 * @author dyt
	 */
	public List<CommunityStatisticsCustomer> list(CommunityStatisticsCustomerQueryRequest queryReq){
		return communityStatisticsCustomerRepository.findAll(CommunityStatisticsCustomerWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityStatisticsCustomerVO wrapperVo(CommunityStatisticsCustomer communityStatisticsCustomer) {
		if (communityStatisticsCustomer != null){
			CommunityStatisticsCustomerVO communityStatisticsCustomerVO = KsBeanUtil.convert(communityStatisticsCustomer, CommunityStatisticsCustomerVO.class);
			return communityStatisticsCustomerVO;
		}
		return null;
	}
}

