package com.wanmi.sbc.message.minimsgsetting.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ProgramNodeType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingQueryRequest;
import com.wanmi.sbc.message.bean.vo.MiniMsgSettingVO;
import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingSimpleVO;
import com.wanmi.sbc.message.minimsgsetting.model.root.MiniMsgSetting;
import com.wanmi.sbc.message.minimsgsetting.repository.MiniMsgSettingRepository;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>小程序订阅消息配置表业务逻辑</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Service("MiniMsgSettingService")
public class MiniMsgSettingService {
	@Autowired
	private MiniMsgSettingRepository miniMsgSettingRepository;


	/**
	 * 修改小程序订阅消息配置表
	 * @author xufeng
	 */
	@Transactional
	public MiniMsgSetting modify(MiniMsgSetting entity) {
		miniMsgSettingRepository.save(entity);
		return entity;
	}

	/**
	 * 单个查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public MiniMsgSetting getOne(Long id){
		return miniMsgSettingRepository.findById(id)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "小程序订阅消息配置表不存在"));
	}

	/**
	 * 单个查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public MiniMsgSetting findByNodeId(ProgramNodeType nodeId){
		return miniMsgSettingRepository.findByNodeId(nodeId);
	}

	/**
	 * 分页查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public Page<MiniMsgSetting> page(MiniMsgSettingQueryRequest queryReq){
		return miniMsgSettingRepository.findAll(
				MiniMsgSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询小程序订阅消息配置表
	 * @author xufeng
	 */
	public List<MiniMsgSetting> list(MiniMsgSettingQueryRequest queryReq){
		return miniMsgSettingRepository.findAll(MiniMsgSettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author xufeng
	 */
	public MiniMsgSettingVO wrapperVo(MiniMsgSetting miniMsgSetting) {
		if (miniMsgSetting != null){
			MiniMsgSettingVO miniMsgSettingVO = KsBeanUtil.convert(miniMsgSetting, MiniMsgSettingVO.class);
			if (StringUtils.isNotEmpty(miniMsgSetting.getMessage())){
				miniMsgSettingVO.setData(JSON.parseArray(miniMsgSetting.getMessage(), MiniMsgTemplateSettingSimpleVO.class));
			}
			return miniMsgSettingVO;
		}
		return null;
	}

}

