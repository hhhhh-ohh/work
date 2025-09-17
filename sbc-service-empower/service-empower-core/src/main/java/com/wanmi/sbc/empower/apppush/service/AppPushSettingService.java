package com.wanmi.sbc.empower.apppush.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingQueryRequest;
import com.wanmi.sbc.empower.apppush.model.root.AppPushSetting;
import com.wanmi.sbc.empower.apppush.repository.AppPushSettingRepository;
import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import com.wanmi.sbc.empower.bean.vo.AppPushSettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>消息推送配置业务逻辑</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Service
public class AppPushSettingService {

	//默认主键
	public static final Integer APP_PUSH_SETTING_ID = -1;

	@Autowired
	private AppPushSettingRepository appPushSettingRepository;
	@Autowired
	private RedisUtil redisService;

	/**
	 * 新增消息推送配置
	 * @author 韩伟
	 */
	@Transactional
	public AppPushSetting add(AppPushSetting entity) {
		Optional<AppPushSetting> config = appPushSettingRepository.findById(APP_PUSH_SETTING_ID);
		entity.setId(APP_PUSH_SETTING_ID);
		entity.setPlatformType(AppPushPlatformType.UMENG);
		entity.setDelFlag(DeleteFlag.NO);
		if (config.isPresent()){
			entity = modify(entity);
		} else {
			entity = appPushSettingRepository.save(entity);
		}
		redisService.put(CacheKeyConstant.UMENG_CONFIG, wrapperVo(entity));
		return entity;
	}

	/**
	 * 修改消息推送配置
	 * @author 韩伟
	 */
	@Transactional
	public AppPushSetting modify(AppPushSetting entity) {
		entity = appPushSettingRepository.save(entity);
		redisService.put(CacheKeyConstant.UMENG_CONFIG, wrapperVo(entity));
		return entity;
	}

	/**
	 * 单个删除消息推送配置
	 * @author 韩伟
	 */
	@Transactional
	public void deleteById(AppPushSetting entity) {
		appPushSettingRepository.save(entity);
	}

	/**
	 * 批量删除消息推送配置
	 * @author 韩伟
	 */
	@Transactional
	public void deleteByIdList(List<AppPushSetting> infos) {
		appPushSettingRepository.saveAll(infos);
	}

	/**
	 * 单个查询消息推送配置
	 * @author 韩伟
	 */
	public AppPushSetting getOne(Integer id){
		return appPushSettingRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "消息推送配置不存在"));
	}

	/**
	 * 分页查询消息推送配置
	 * @author 韩伟
	 */
	public Page<AppPushSetting> page(AppPushSettingQueryRequest queryReq){
		return appPushSettingRepository.findAll(
				AppPushSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询消息推送配置
	 * @author 韩伟
	 */
	public List<AppPushSetting> list(AppPushSettingQueryRequest queryReq){
		return appPushSettingRepository.findAll(AppPushSettingWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 韩伟
	 */
	public AppPushSettingVO wrapperVo(AppPushSetting appPushSetting) {
		if (appPushSetting != null){
			AppPushSettingVO appPushSettingVO = KsBeanUtil.convert(appPushSetting, AppPushSettingVO.class);
			return appPushSettingVO;
		}
		return null;
	}

	/**
	 * 获取可用消息推送配置
	 * 使用redis缓存
	 * @return
	 */
	public AppPushSettingVO getAvailable(){
		AppPushSettingVO vo = (AppPushSettingVO) redisService.get(CacheKeyConstant.UMENG_CONFIG);
		if (Objects.isNull(vo)){
			vo = wrapperVo(getOne(APP_PUSH_SETTING_ID));
			redisService.put(CacheKeyConstant.UMENG_CONFIG, vo);
		}
		return vo;
	}
}

