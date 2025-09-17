package com.wanmi.sbc.marketing.provider.impl.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsAddRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsModifyRequest;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatistics;
import com.wanmi.sbc.marketing.communitystatistics.service.CommunityStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动统计信息表保存服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@RestController
@Validated
@Slf4j
public class CommunityStatisticsController implements CommunityStatisticsProvider {
	@Autowired
	private CommunityStatisticsService communityStatisticsService;

	@Autowired
	RedissonClient redissonClient;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityStatisticsAddRequest communityStatisticsAddRequest) {
		CommunityStatistics communityStatistics = KsBeanUtil.convert(communityStatisticsAddRequest, CommunityStatistics.class);
		RLock rLock = redissonClient.getLock(RedisKeyConstant.COMMUNITY_STATISTICS_PAY_CALLBACK_UPDATE
						.concat(communityStatistics.getActivityId()).concat(communityStatistics.getLeaderId()));
		rLock.lock();
		try {
			communityStatisticsService.add(communityStatistics);
		} catch (Exception e){
			log.error("CommunityStatisticsController.add:入参:{}，异常",communityStatisticsAddRequest, e);
		} finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid CommunityStatisticsModifyRequest communityStatisticsModifyRequest) {
		CommunityStatistics communityStatistics = KsBeanUtil.convert(communityStatisticsModifyRequest, CommunityStatistics.class);
		communityStatisticsService.modify(communityStatistics);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * @param communityStatisticsModifyRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 * @description 商家退单退款时数据更新
	 * @author edz
	 * @date: 2023/8/7 10:37
	 */
	@Override
	public BaseResponse returnTradeUpdate(@RequestBody @Valid CommunityStatisticsModifyRequest communityStatisticsModifyRequest) {
		CommunityStatistics communityStatistics = KsBeanUtil.convert(communityStatisticsModifyRequest,
				CommunityStatistics.class);
		RLock rLock = redissonClient.getLock(RedisKeyConstant.COMMUNITY_STATISTICS_RETURN_UPDATE
				.concat(communityStatistics.getActivityId()).concat(communityStatistics.getLeaderId()));
		rLock.lock();
		try {
			communityStatisticsService.returnTradeUpdate(communityStatistics);
		} catch (Exception e){
			log.error("CommunityStatisticsController.returnTradeUpdate:入参:{}, 异常:", communityStatisticsModifyRequest,
					e);
		} finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * @param communityStatisticsModifyRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 * @description 入账佣金更新
	 * @author edz
	 * @date: 2023/8/7 11:17
	 */
	@Override
	public BaseResponse commissionUpdate(@RequestBody @Valid CommunityStatisticsModifyRequest communityStatisticsModifyRequest) {
		CommunityStatistics communityStatistics = KsBeanUtil.convert(communityStatisticsModifyRequest,
				CommunityStatistics.class);
		RLock rLock = redissonClient.getLock(RedisKeyConstant.COMMUNITY_STATISTICS_COMMISSION_UPDATE
				.concat(communityStatistics.getActivityId()).concat(communityStatistics.getLeaderId()));
		rLock.lock();
		try {
			communityStatisticsService.commissionUpdate(communityStatistics);
		} catch (Exception e){
			log.error("CommunityStatisticsController.commissionUpdate:入参:{}, 异常:", communityStatisticsModifyRequest,
					e);
		} finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}
}

