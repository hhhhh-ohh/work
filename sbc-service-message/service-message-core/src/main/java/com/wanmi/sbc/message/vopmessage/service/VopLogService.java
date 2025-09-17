package com.wanmi.sbc.message.vopmessage.service;

import com.wanmi.sbc.message.vopmessage.model.root.VopLog;
import com.wanmi.sbc.message.vopmessage.repository.VopLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Vop日志表业务逻辑</p>
 * @author xufeng
 * @date 2022-05-20 15:53:00
 */
@Service("VopLogService")
public class VopLogService {
	@Autowired
	private VopLogRepository vopLogRepository;


	/**
	 * 新增Vop日志
	 * @author xufeng
	 */
	@Transactional
	public VopLog add(VopLog entity) {
		vopLogRepository.save(entity);
		return entity;
	}

}

