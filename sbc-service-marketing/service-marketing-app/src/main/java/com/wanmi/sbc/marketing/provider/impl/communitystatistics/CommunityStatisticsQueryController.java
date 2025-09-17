package com.wanmi.sbc.marketing.provider.impl.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsListRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsQueryRequest;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsListResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatistics;
import com.wanmi.sbc.marketing.communitystatistics.service.CommunityStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动统计信息表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@RestController
@Validated
public class CommunityStatisticsQueryController implements CommunityStatisticsQueryProvider {
	@Autowired
	private CommunityStatisticsService communityStatisticsService;

	@Override
	public BaseResponse<CommunityStatisticsListResponse> list(@RequestBody @Valid CommunityStatisticsListRequest communityStatisticsListReq) {
		CommunityStatisticsQueryRequest queryReq = KsBeanUtil.convert(communityStatisticsListReq, CommunityStatisticsQueryRequest.class);
		List<CommunityStatistics> communityStatisticsList = communityStatisticsService.list(queryReq);
		List<CommunityStatisticsVO> newList = communityStatisticsList.stream().map(entity -> communityStatisticsService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(CommunityStatisticsListResponse.builder().communityStatisticsVOList(newList).build());
	}

	/**
	 * 单个团长统计数据
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return
	 */
	@Override
	public BaseResponse<CommunityStatisticsListResponse> findByLeaderIdGroup(CommunityStatisticsListRequest communityStatisticsListReq) {
		CommunityStatisticsQueryRequest queryReq = KsBeanUtil.convert(communityStatisticsListReq, CommunityStatisticsQueryRequest.class);
		CommunityStatistics communityStatistics = communityStatisticsService.findByLeaderIdGroup(queryReq);
		return BaseResponse.success(CommunityStatisticsListResponse.builder().communityStatisticsVOList(
				Arrays.asList(communityStatisticsService.wrapperVo(communityStatistics))).build());
	}

	/**
	 * 多个团长统计数据
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return
	 */
	@Override
	public BaseResponse<CommunityStatisticsListResponse> findByLeaderIdsGroup(CommunityStatisticsListRequest communityStatisticsListReq) {
		CommunityStatisticsQueryRequest queryReq = KsBeanUtil.convert(communityStatisticsListReq, CommunityStatisticsQueryRequest.class);
		Map<String,CommunityStatisticsVO> communityStatisticsVOMap = communityStatisticsService.findByLeaderIdsGroup(queryReq);
		return BaseResponse.success(CommunityStatisticsListResponse.builder().communityStatisticsVOMap(communityStatisticsVOMap).build());
	}

	/**
	 * 多个团长统计数据
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return
	 */
	@Override
	public BaseResponse<CommunityStatisticsListResponse> findByActivityIdsGroup(@RequestBody @Valid CommunityStatisticsListRequest communityStatisticsListReq) {
		CommunityStatisticsQueryRequest queryReq = KsBeanUtil.convert(communityStatisticsListReq, CommunityStatisticsQueryRequest.class);
		Map<String,CommunityStatisticsVO> communityStatisticsMap = communityStatisticsService.findByActivityIdsGroup(queryReq);
		return BaseResponse.success(CommunityStatisticsListResponse.builder().communityStatisticsVOMap(communityStatisticsMap).build());
	}
}

