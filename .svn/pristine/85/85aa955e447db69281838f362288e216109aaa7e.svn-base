package com.wanmi.sbc.message.minimsgtempsetting.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingQueryRequest;
import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import com.wanmi.sbc.message.minimsgtempsetting.repository.MiniMsgTempSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>小程序订阅消息模版配置表业务逻辑</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Service("MiniMsgTempSettingService")
public class MiniMsgTempSettingService {
	@Autowired
	private MiniMsgTempSettingRepository miniMsgTempSettingRepository;

    /**
	 * 修改小程序订阅消息模版配置表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgTempSetting modify(MiniMsgTempSetting entity) {
		miniMsgTempSettingRepository.save(entity);
		return entity;
	}

    /**
	 * 单个查询小程序订阅消息模版配置表
	 * @author xufeng
	 */
	public MiniMsgTempSetting getOne(Long id){
		return miniMsgTempSettingRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "小程序订阅消息模版配置表不存在"));
	}

	/**
	 * 单个查询小程序订阅消息模版配置表
	 * @author xufeng
	 */
	public MiniMsgTempSetting findByTriggerNodeId(TriggerNodeType nodeId){
		return miniMsgTempSettingRepository.findByTriggerNodeId(nodeId);
	}

	/**
	 * 分页查询小程序订阅消息模版配置表
	 * @author xufeng
	 */
	public Page<MiniMsgTempSetting> page(MiniMsgTempSettingQueryRequest queryReq){
		return miniMsgTempSettingRepository.findAll(
				MiniMsgTempSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询小程序订阅消息模版配置表
	 * @author xufeng
	 */
	public List<MiniMsgTempSetting> list(MiniMsgTempSettingQueryRequest queryReq){
		return miniMsgTempSettingRepository.findAll(MiniMsgTempSettingWhereCriteriaBuilder.build(queryReq));
	}


	/**
	 * 将实体包装成VO
	 * @author xufeng
	 */
	public MiniMsgTemplateSettingVO wrapperVo(MiniMsgTempSetting miniMsgTempSetting) {
		if (miniMsgTempSetting != null){
			MiniMsgTemplateSettingVO miniMsgTemplateSettingVO = KsBeanUtil.convert(miniMsgTempSetting, MiniMsgTemplateSettingVO.class);
			return miniMsgTemplateSettingVO;
		}
		return null;
	}

    /**
	 * 更新模板Id，同时清空修改的温馨提示
	 * @author xufeng
	 */
	@Transactional
	public void modifyByTid(String templateId, String tid) {
		miniMsgTempSettingRepository.modifyByTid(templateId, tid);
	}
}

