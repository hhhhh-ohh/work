package com.wanmi.sbc.customer.service;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.util.SmsSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会员缓存服务
 *
 * @author Daiyitian
 * @date 2017/4/19
 */
@Slf4j
@Service
public class CustomerCacheService {

	@Autowired
	private RedisUtil redisService;

	@Autowired
	private SmsSendUtil smsSendUtil;

	/**
	 * 是否可以发送验证码
	 *
	 * @param mobile 要发送短信的手机号码
	 * @return true:可以发送，false:不可以
	 */
	public boolean validateSendMobileCode(String mobile) {
		String timeStr = redisService.getString(CacheKeyConstant.YZM_MOBILE_LAST_TIME.concat(mobile));
		if (StringUtils.isBlank(timeStr)) {
			return true;
		}
		//如果当前时间 > 上一次发送时间+1分钟
		return LocalDateTime.now().isAfter(DateUtil.parse(timeStr, DateUtil.FMT_TIME_1).plusMinutes(1));
	}

	/**
	 * 发送手机验证码
	 *
	 * @param redisKey    存入redis的验证码key
	 * @param mobile      要发送短信的手机号码
	 * @param smsTemplate 短信内容模版
	 * @return
	 */
	public Integer sendMobileCode(String redisKey, String mobile, SmsTemplate smsTemplate) {
		//记录发送时间
		redisService.setString(CacheKeyConstant.YZM_MOBILE_LAST_TIME.concat(mobile), DateUtil.nowTime());
		redisService.expireByMinutes(CacheKeyConstant.YZM_MOBILE_LAST_TIME.concat(mobile), Constants.NUM_1L);

		String verifyCode = RandomStringUtils.randomNumeric(6);
		smsSendUtil.send(smsTemplate, new String[]{mobile}, verifyCode);

		redisService.setString(redisKey.concat(mobile), verifyCode);
		redisService.expireByMinutes(redisKey.concat(mobile), Constants.SMS_TIME);
		return Constants.yes;
	}

	public String sendMobileCode(String redisKey, String mobile) {
		String verifyCode = RandomStringUtils.randomNumeric(6);
		redisService.setString(redisKey.concat(mobile), verifyCode);
		redisService.expireByMinutes(redisKey.concat(mobile), Constants.SMS_TIME);
		return verifyCode;
	}

	/**
	 * 批量获取当前登录人是否注销
	 * @param customerIds
	 * @return
	 */
	public Map<String, LogOutStatus> getLogOutStatus(List<String> customerIds){
		Map<String,LogOutStatus> map = new HashMap<>();
		if (CollectionUtils.isEmpty(customerIds)){
			return map;
		}
		List<String> keys = customerIds.stream().map(id -> CacheKeyConstant.LOG_OUT_STATUS + id).collect(Collectors.toList());
		List<String> results = redisService.getMString(keys).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(results)){
			results.forEach(result -> {
				String[] keyText = result.split(":");
				if (keyText.length < 1) {
					return;
				}
				String key = keyText[0];
				LogOutStatus value = LogOutStatus.fromValue(Integer.parseInt(keyText[1]));
				map.put(key, value);
			});
		}
		return map;
	}
}
