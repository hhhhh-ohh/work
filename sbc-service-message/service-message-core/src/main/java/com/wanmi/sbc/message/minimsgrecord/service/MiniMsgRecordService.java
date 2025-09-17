package com.wanmi.sbc.message.minimsgrecord.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.bean.vo.MiniMsgAuthorizationRecordVO;
import com.wanmi.sbc.message.minimsgrecord.model.root.MiniMsgRecord;
import com.wanmi.sbc.message.minimsgrecord.repository.MiniMsgRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>小程序订阅消息配置表业务逻辑</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Service("MiniMsgRecordService")
public class MiniMsgRecordService {
	@Autowired
	private MiniMsgRecordRepository miniMsgRecordRepository;

	/**
	 * 新增小程序订阅消息配置表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgRecord add(MiniMsgRecord entity) {
		miniMsgRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 修改小程序订阅消息配置表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgRecord modify(MiniMsgRecord entity) {
		miniMsgRecordRepository.save(entity);
		return entity;
	}

	/**
	 * 单个查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public MiniMsgRecord getOne(Long id){
		return miniMsgRecordRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "小程序订阅消息配置表不存在"));
	}


	/**
	 * 单个查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public MiniMsgRecord findByCustomerId(String customerId){
		return miniMsgRecordRepository.findByCustomerId(customerId);
	}
	/**
	 * 将实体包装成VO
	 * @author xufeng
	 */
	public MiniMsgAuthorizationRecordVO wrapperVo(MiniMsgRecord miniMsgRecord) {
		if (miniMsgRecord != null){
			MiniMsgAuthorizationRecordVO miniMsgAuthorizationRecordVO = KsBeanUtil.convert(miniMsgRecord, MiniMsgAuthorizationRecordVO.class);
			return miniMsgAuthorizationRecordVO;
		}
		return null;
	}

}

