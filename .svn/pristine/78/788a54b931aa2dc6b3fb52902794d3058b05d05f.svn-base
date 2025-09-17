package com.wanmi.sbc.order.pickupcoderecord.service;

import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.order.pickupcoderecord.repository.PickupCodeRecordRepository;
import com.wanmi.sbc.order.pickupcoderecord.model.root.PickupCodeRecord;
import com.wanmi.sbc.order.api.request.pickupcoderecord.PickupCodeRecordQueryRequest;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>提货码记录业务逻辑</p>
 * @author 吕振伟
 * @date 2023-04-19 14:03:52
 */
@Service("PickupCodeRecordService")
public class PickupCodeRecordService {
	@Autowired
	private PickupCodeRecordRepository pickupCodeRecordRepository;

	/**
	 * 新增提货码记录
	 * @author 吕振伟
	 */
	@Transactional
	public PickupCodeRecord add(PickupCodeRecord entity) {
		pickupCodeRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 列表查询提货码记录
	 * @author 吕振伟
	 */
	public List<PickupCodeRecord> list(PickupCodeRecordQueryRequest queryReq){
		return pickupCodeRecordRepository.findAll(PickupCodeRecordWhereCriteriaBuilder.build(queryReq));
	}

	@Transactional
	public void deleteExpirePickupCodeRecord(){
		//每个月末清理一批数据(比如3月1日删除去年3月份所有的编码)
		LocalDate delDate = LocalDate.now().minusMonths(11);
		pickupCodeRecordRepository.deleteExpirePickupCodeRecord(delDate.atStartOfDay());
	}

}

