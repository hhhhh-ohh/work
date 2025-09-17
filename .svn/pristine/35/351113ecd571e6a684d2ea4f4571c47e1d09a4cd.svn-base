package com.wanmi.sbc.marketing.communitystockorder.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStockOrderVO;
import com.wanmi.sbc.marketing.communitystockorder.model.root.CommunityStockOrder;
import com.wanmi.sbc.marketing.communitystockorder.repository.CommunityStockOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区团购备货单业务逻辑</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Service
public class CommunityStockOrderService {
	@Autowired
	private CommunityStockOrderRepository communityStockOrderRepository;

	/**
	 * 新增社区团购备货单
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityStockOrderAddRequest request) {
		List<CommunityStockOrder> orders = request.getCommunityStockOrderDTOList().stream()
				.map(s -> {
					CommunityStockOrder order = KsBeanUtil.convert(s, CommunityStockOrder.class);
					order.setCreateTime(LocalDateTime.now());
					return order;
				}).collect(Collectors.toList());
		communityStockOrderRepository.saveAll(orders);
	}

	/**
	 * 单个删除社区团购备货单
	 * @author dyt
	 */
	@Transactional
	public void deleteByActivityId(String activityId) {
		communityStockOrderRepository.deleteByActivityId(activityId);
	}

	/**
	 * 单个查询社区团购备货单
	 * @author dyt
	 */
	public CommunityStockOrder getOne(String id){
		return communityStockOrderRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购备货单不存在"));
	}

	/**
	 * 分页查询社区团购备货单
	 * @author dyt
	 */
	public Page<CommunityStockOrder> page(CommunityStockOrderQueryRequest queryReq){
		queryReq.putSort("createTime", SortType.ASC.toValue());
		return communityStockOrderRepository.findAll(
				CommunityStockOrderWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购备货单
	 * @author dyt
	 */
	public List<CommunityStockOrder> list(CommunityStockOrderQueryRequest queryReq){
		queryReq.putSort("createTime", SortType.ASC.toValue());
		return communityStockOrderRepository.findAll(CommunityStockOrderWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityStockOrderVO wrapperVo(CommunityStockOrder communityStockOrder) {
		if (communityStockOrder != null){
			CommunityStockOrderVO communityStockOrderVO = KsBeanUtil.convert(communityStockOrder, CommunityStockOrderVO.class);
			return communityStockOrderVO;
		}
		return null;
	}
}

