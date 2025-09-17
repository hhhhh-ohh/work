package com.wanmi.sbc.setting.systempointsconfig.service;

import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.SystemPointsConfigModifyRequest;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.enums.SettingRedisKey;
import com.wanmi.sbc.setting.systempointsconfig.model.root.SystemPointsConfig;
import com.wanmi.sbc.setting.systempointsconfig.repository.SystemPointsConfigRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>积分设置业务逻辑</p>
 * @author yxz
 * @date 2019-03-28 16:24:21
 */
@Service("SystemPointsConfigService")
public class SystemPointsConfigService {

	@Autowired
	private SystemPointsConfigRepository systemPointsConfigRepository;

	@Autowired
	private RedisUtil redisService;

	/**
	 * 查询积分设置
	 *
	 * @return
	 */
	@Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'SYSTEM_POINTS_CONFIG:'")
	public SystemPointsConfig querySystemPointsConfig() {
		List<SystemPointsConfig> configs = systemPointsConfigRepository.findByDelFlag(DeleteFlag.NO);
		SystemPointsConfig systemPointsConfig = new SystemPointsConfig();
		if (CollectionUtils.isEmpty(configs)) {
			// 如果数据库里无数据，初始化
			systemPointsConfig.setPointsUsageFlag(PointsUsageFlag.ORDER);
			systemPointsConfig.setStatus(EnableStatus.DISABLE);
			systemPointsConfig.setPointsWorth(Constants.POINTS_WORTH);
			systemPointsConfig.setDelFlag(DeleteFlag.NO);
			systemPointsConfig.setCreateTime(LocalDateTime.now());
			systemPointsConfig.setSwitchUsageTime(LocalDateTime.now());
			systemPointsConfig = systemPointsConfigRepository.saveAndFlush(systemPointsConfig);
		} else {
			systemPointsConfig = configs.get(0);
		}

		return systemPointsConfig;
	}

	/**
	 * 修改积分设置
	 *
	 * @param request
	 */
	@CacheEvict(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'SYSTEM_POINTS_CONFIG:'")
	@Transactional(rollbackFor = {Exception.class})
	public void modifySystemPointsConfig(SystemPointsConfigModifyRequest request) {
		// 根据配置id查询积分设置详情
		SystemPointsConfig systemPointsConfig = systemPointsConfigRepository.findByPointsConfigIdAndDelFlag(
				request.getPointsConfigId(), DeleteFlag.NO);
		// 积分设置不存在
		if (Objects.isNull(systemPointsConfig)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		//切换抵扣方式的验证
		if(!(systemPointsConfig.getPointsUsageFlag() == request.getPointsUsageFlag())){
			//时间切换
			if(systemPointsConfig.getSwitchUsageTime() == null
					|| (!systemPointsConfig.getSwitchUsageTime().toLocalDate().isEqual(LocalDate.now()))){
				systemPointsConfig.setSwitchUsageTime(LocalDateTime.now());
			}else{
				throw new SbcRuntimeException(SettingErrorCodeEnum.K070072);
			}
		}
		redisService.delete(SettingRedisKey.SYSTEM_POINTS_CONFIG.toValue());
		request.setUpdateTime(LocalDateTime.now());
		if (Objects.isNull(request.getOverPointsAvailable())){
			request.setOverPointsAvailable(1L);
		}
		if (Objects.isNull(request.getMaxDeductionRate())){
			request.setMaxDeductionRate(BigDecimal.ZERO);
		}
		KsBeanUtil.copyProperties(request, systemPointsConfig);
		systemPointsConfigRepository.save(systemPointsConfig);
	}

}
