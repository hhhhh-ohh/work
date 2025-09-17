package com.wanmi.sbc.marketing.communitydeliveryorder.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryItemVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryOrderVO;
import com.wanmi.sbc.marketing.communitydeliveryorder.model.root.CommunityDeliveryOrder;
import com.wanmi.sbc.marketing.communitydeliveryorder.repository.CommunityDeliveryOrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>社区团购发货单业务逻辑</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Service
public class CommunityDeliveryOrderService {
	@Autowired
	private CommunityDeliveryOrderRepository communityDeliveryOrderRepository;

	/**
	 * 新增社区团购发货单
	 * @author dyt
	 */
	@Transactional
	public void add(CommunityDeliveryOrderAddRequest communityDeliveryOrderAddRequest) {
		List<CommunityDeliveryOrder> orders = communityDeliveryOrderAddRequest.getCommunityDeliveryOrderDTOList().stream()
				.map(s -> {
					CommunityDeliveryOrder order = KsBeanUtil.convert(s, CommunityDeliveryOrder.class);
					order.setCreateTime(LocalDateTime.now());
					order.setGoodsContext(JSON.toJSONString(s.getGoodsList()));
					return order;
				}).collect(Collectors.toList());
		communityDeliveryOrderRepository.saveAll(orders);
	}

	/**
	 * 修改社区团购发货单
	 * @author dyt
	 */
	@Transactional
	public CommunityDeliveryOrder modify(CommunityDeliveryOrder entity) {
		communityDeliveryOrderRepository.save(entity);
		return entity;
	}

	/**
	 * 根据活动删除社区团购发货单
	 * @author dyt
	 */
	@Transactional
	public void deleteByActivityId(String activityId) {
		communityDeliveryOrderRepository.deleteByActivityId(activityId);
	}

	/**
	 * 批量发货社区团购发货单
	 * @author dyt
	 */
	@Transactional
	public void updateDeliveryStatusByActivityId(List<String> ids) {
		communityDeliveryOrderRepository.updateDeliveryStatusByActivityId(Constants.yes,ids);
	}

	/**
	 * 单个查询社区团购发货单
	 * @author dyt
	 */
	public CommunityDeliveryOrder getOne(String id){
		return communityDeliveryOrderRepository.findById(id)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "社区团购发货单不存在"));
	}

	/**
	 * 分页查询社区团购发货单
	 * @author dyt
	 */
	public Page<CommunityDeliveryOrder> page(CommunityDeliveryOrderQueryRequest queryReq){
		return communityDeliveryOrderRepository.findAll(
				CommunityDeliveryOrderWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询社区团购发货单
	 * @author dyt
	 */
	public List<CommunityDeliveryOrder> list(CommunityDeliveryOrderQueryRequest queryReq){
		return communityDeliveryOrderRepository.findAll(CommunityDeliveryOrderWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author dyt
	 */
	public CommunityDeliveryOrderVO wrapperVo(CommunityDeliveryOrder communityDeliveryOrder) {
		if (communityDeliveryOrder != null) {
			CommunityDeliveryOrderVO communityDeliveryOrderVO = KsBeanUtil.convert(communityDeliveryOrder, CommunityDeliveryOrderVO.class);
			if (StringUtils.isNotBlank(communityDeliveryOrder.getGoodsContext())) {
				communityDeliveryOrderVO.setGoodsList(JSON.parseArray(communityDeliveryOrder.getGoodsContext(), CommunityDeliveryItemVO.class));
			}
			return communityDeliveryOrderVO;
		}
		return null;
	}
}

