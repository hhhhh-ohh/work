package com.wanmi.sbc.empower.sms.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingQueryRequest;
import com.wanmi.sbc.empower.bean.vo.SmsSettingVO;
import com.wanmi.sbc.empower.sms.model.root.SmsSetting;
import com.wanmi.sbc.empower.sms.repository.SmsSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>短信配置业务逻辑</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@Service("SmsSettingService")
public class SmsSettingService {

	@Autowired
	private SmsSettingRepository smsSettingRepository;
	
	/** 
	 * 新增短信配置
	 * @author lvzhenwei
	 */
	@Transactional
	public SmsSetting add(SmsSetting entity) {
		smsSettingRepository.save(entity);
		return entity;
	}
	
	/** 
	 * 修改短信配置
	 * @author lvzhenwei
	 */
	@Transactional
	public SmsSetting modify(SmsSetting entity) {
		SmsSetting smsSettingInfo = smsSettingRepository.findById(entity.getId()).orElse(null);
		if(Objects.isNull(smsSettingInfo)){
			return smsSettingInfo;
		}
		smsSettingInfo.setAccessKeyId(entity.getAccessKeyId());
		smsSettingInfo.setAccessKeySecret(entity.getAccessKeySecret());
		smsSettingInfo.setStatus(entity.getStatus());
		smsSettingRepository.save(smsSettingInfo);
		return entity;
	}

	/**
	 * 单个删除短信配置
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteById(Long id) {
		smsSettingRepository.deleteByBeanId(id);
	}
	
	/** 
	 * 批量删除短信配置
	 * @author lvzhenwei
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		smsSettingRepository.deleteByIdList(ids);
	}
	
	/** 
	 * 单个查询短信配置
	 * @author lvzhenwei
	 */
	public SmsSetting getById(Long id){
		return smsSettingRepository.findById(id).orElse(null);
	}

	/**
	 * 单个查询短信配置
	 * @author lvzhenwei
	 */
	public SmsSetting getSmsSettingInfoByParam(SmsSettingQueryRequest smsSettingQueryRequest){
		return smsSettingRepository.findOne(SmsSettingWhereCriteriaBuilder.build(smsSettingQueryRequest)).orElse(null);
	}
	
	/** 
	 * 分页查询短信配置
	 * @author lvzhenwei
	 */
	public Page<SmsSetting> page(SmsSettingQueryRequest queryReq){
		return smsSettingRepository.findAll(
				SmsSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}
	
	/** 
	 * 列表查询短信配置
	 * @author lvzhenwei
	 */
	public List<SmsSetting> list(SmsSettingQueryRequest queryReq){
		return smsSettingRepository.findAll(
				SmsSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 * @author lvzhenwei
	 */
	public SmsSettingVO wrapperVo(SmsSetting smsSetting) {
		if (smsSetting != null){
			SmsSettingVO smsSettingVO=new SmsSettingVO();
			KsBeanUtil.copyPropertiesThird(smsSetting,smsSettingVO);
			return smsSettingVO;
		}
		return null;
	}
}
