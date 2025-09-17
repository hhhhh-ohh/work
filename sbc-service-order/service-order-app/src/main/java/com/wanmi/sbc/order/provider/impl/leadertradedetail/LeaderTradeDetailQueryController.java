package com.wanmi.sbc.order.provider.impl.leadertradedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.leadertradedetail.LeaderTradeDetailQueryProvider;
import com.wanmi.sbc.order.api.request.leadertradedetail.*;
import com.wanmi.sbc.order.api.response.leadertradedetail.*;
import com.wanmi.sbc.order.bean.vo.CommunityTradeCountVO;
import com.wanmi.sbc.order.bean.vo.LeaderTradeDetailVO;
import com.wanmi.sbc.order.leadertradedetail.model.root.LeaderTradeDetail;
import com.wanmi.sbc.order.leadertradedetail.service.LeaderTradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>团长订单查询服务接口实现</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@RestController
@Validated
public class LeaderTradeDetailQueryController implements LeaderTradeDetailQueryProvider {
	@Autowired
	private LeaderTradeDetailService leaderTradeDetailService;

	@Override
	public BaseResponse<LeaderTradeDetailPageResponse> page(@RequestBody @Valid LeaderTradeDetailPageRequest leaderTradeDetailPageReq) {
		LeaderTradeDetailQueryRequest queryReq = KsBeanUtil.convert(leaderTradeDetailPageReq, LeaderTradeDetailQueryRequest.class);
		Page<LeaderTradeDetail> leaderTradeDetailPage = leaderTradeDetailService.page(queryReq);
		Page<LeaderTradeDetailVO> newPage = leaderTradeDetailPage.map(entity -> leaderTradeDetailService.wrapperVo(entity));
		MicroServicePage<LeaderTradeDetailVO> microPage = new MicroServicePage<>(newPage, leaderTradeDetailPageReq.getPageable());
		LeaderTradeDetailPageResponse finalRes = new LeaderTradeDetailPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LeaderTradeDetailListResponse> list(@RequestBody @Valid LeaderTradeDetailListRequest leaderTradeDetailListReq) {
		LeaderTradeDetailQueryRequest queryReq = KsBeanUtil.convert(leaderTradeDetailListReq, LeaderTradeDetailQueryRequest.class);
		List<LeaderTradeDetail> leaderTradeDetailList = leaderTradeDetailService.list(queryReq);
		List<LeaderTradeDetailVO> newList = leaderTradeDetailList.stream().map(entity -> leaderTradeDetailService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LeaderTradeDetailListResponse(newList));
	}

	@Override
	public BaseResponse<LeaderTradeDetailByIdResponse> getById(@RequestBody @Valid LeaderTradeDetailByIdRequest leaderTradeDetailByIdRequest) {
		LeaderTradeDetail leaderTradeDetail =
				leaderTradeDetailService.getOne(leaderTradeDetailByIdRequest.getId());
		return BaseResponse.success(new LeaderTradeDetailByIdResponse(leaderTradeDetailService.wrapperVo(leaderTradeDetail)));
	}

	/**
	 * 查询团长的跟团人数（支付成功+帮卖+去重）
	 * @param request
	 * @return
	 */
	@Override
	public BaseResponse<Long> findFollowNum(LeaderTradeDetailListRequest request) {
		return BaseResponse.success(leaderTradeDetailService.findFollowNum(request.getLeaderId()));
	}

	@Override
	public BaseResponse<LeaderTradeDetailTopGroupByActivityResponse> topGroupActivity(@RequestBody @Valid LeaderTradeDetailTopGroupByActivityRequest request) {
		List<LeaderTradeDetailVO> list = leaderTradeDetailService.topGroupActivity(request);
		return BaseResponse.success(LeaderTradeDetailTopGroupByActivityResponse.builder().leaderTradeDetailList(list).build());
	}

	@Override
	public BaseResponse<LeaderTradeDetailTradeCountGroupByActivityResponse> countTradeGroupActivity(@RequestBody @Valid LeaderTradeDetailTradeCountGroupByActivityRequest request) {
		List<CommunityTradeCountVO> list = leaderTradeDetailService.totalTradeByActivityIds(request);
		return BaseResponse.success(LeaderTradeDetailTradeCountGroupByActivityResponse.builder().tradeCountList(list).build());
	}

	@Override
	public BaseResponse<LeaderTradeDetailTradeCountLeaderResponse> countLeaderGroupActivity(@RequestBody @Valid LeaderTradeDetailTradeCountLeaderRequest request) {
		Map<String, Long> result = leaderTradeDetailService.countLeaderGroupActivity(request);
		return BaseResponse.success(LeaderTradeDetailTradeCountLeaderResponse.builder().result(result).build());
	}

	@Override
	public BaseResponse<LeaderTradeDetailTradeCountCustomerResponse> countCustomerGroupActivity(@RequestBody @Valid LeaderTradeDetailTradeCountCustomerRequest request) {
		Map<String, Long> result = leaderTradeDetailService.countCustomerGroupActivity(request);
		return BaseResponse.success(LeaderTradeDetailTradeCountCustomerResponse.builder().result(result).build());
	}
}

