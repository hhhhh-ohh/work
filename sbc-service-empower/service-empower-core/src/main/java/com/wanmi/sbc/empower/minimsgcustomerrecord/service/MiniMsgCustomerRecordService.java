package com.wanmi.sbc.empower.minimsgcustomerrecord.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordQueryRequest;
import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;
import com.wanmi.sbc.empower.minimsgcustomerrecord.model.root.MiniMsgCustomerRecord;
import com.wanmi.sbc.empower.minimsgcustomerrecord.repository.MiniMsgCustomerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>客户订阅消息信息表业务逻辑</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Service("MiniMsgCustomerRecordService")
public class MiniMsgCustomerRecordService {
	@Autowired
	private MiniMsgCustomerRecordRepository miniMsgCustomerRecordRepository;

	/**
	 * 新增客户订阅消息信息表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgCustomerRecord add(MiniMsgCustomerRecord entity) {
		miniMsgCustomerRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改客户订阅消息信息表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgCustomerRecord modify(MiniMsgCustomerRecord entity) {
		miniMsgCustomerRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除客户订阅消息信息表
	 * @author xufeng
	 */
	@Transactional
	public void deleteById(Long id) {
		miniMsgCustomerRecordRepository.deleteById(id);
	}

	/**
	 * 批量删除客户订阅消息信息表
	 * @author xufeng
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		miniMsgCustomerRecordRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询客户订阅消息信息表
	 * @author xufeng
	 */
	public MiniMsgCustomerRecord getOne(Long id){
		return miniMsgCustomerRecordRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "客户订阅消息信息表不存在"));
	}

	/**
	 * 分页查询客户订阅消息信息表
	 * @author xufeng
	 */
	public Page<MiniMsgCustomerRecord> page(MiniMsgCusRecordQueryRequest queryReq){
		return miniMsgCustomerRecordRepository.findAll(
				MiniMsgCustomerRecordWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询客户订阅消息信息表
	 * @author xufeng
	 */
	public List<MiniMsgCustomerRecord> list(MiniMsgCusRecordQueryRequest queryReq){
		return miniMsgCustomerRecordRepository.findAll(MiniMsgCustomerRecordWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author xufeng
	 */
	public MiniMsgCustomerRecordVO wrapperVo(MiniMsgCustomerRecord miniMsgCustomerRecord) {
		if (miniMsgCustomerRecord != null){
			MiniMsgCustomerRecordVO miniMsgCustomerRecordVO = KsBeanUtil.convert(miniMsgCustomerRecord, MiniMsgCustomerRecordVO.class);
			return miniMsgCustomerRecordVO;
		}
		return null;
	}

	/**
	 * 查询可推送人数
	 * @author xufeng
	 */
	public Long countRecordsByTriggerNodeId(TriggerNodeType triggerNodeId){
		return miniMsgCustomerRecordRepository.countRecordsByTriggerNodeId(triggerNodeId);
	}

	/**
	 * 查询可实际推送人数
	 * @author xufeng
	 */
	public Long countRecordsByActivityId(Long activityId){
		return miniMsgCustomerRecordRepository.countRecordsByActivityId(activityId);
	}

	/**
	 * 查询实际推送成功人数
	 * @author xufeng
	 */
	public Long countRecordsByActivityIdAndErrCode(Long activityId){
		return miniMsgCustomerRecordRepository.countRecordsByActivityIdAndErrCode(activityId);
	}

	/**
	 * 批量修改
	 * @author xufeng
	 */
	@Transactional
	public void updateActivityIdByIdList(Long activityId, List<Long> idList) {
		miniMsgCustomerRecordRepository.updateActivityIdByIdList(activityId,  idList);
	}

	/**
	 * 修改
	 * @author xufeng
	 */
	@Transactional
	public void updateByActivityIdAndOpenId(String errCode, String errMsg, Long activityId, String openId) {
		miniMsgCustomerRecordRepository.updateByActivityIdAndOpenId(errCode, errMsg, activityId, openId);
	}

	/**
	 * 修改
	 * @author xufeng
	 */
	@Transactional
	public void updateById(String errCode, String errMsg, Long id) {
		miniMsgCustomerRecordRepository.updateById(errCode, errMsg, id);
	}

	/**
	 * 批量修改
	 * @author xufeng
	 */
	@Transactional
	public void updateByActivityId(Long activityId) {
		miniMsgCustomerRecordRepository.updateByActivityId(activityId);
	}
}

