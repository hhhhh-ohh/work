package com.wanmi.sbc.order.provider.impl.leadertradedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.leadertradedetail.LeaderTradeDetailProvider;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailAddRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailDelByIdListRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailDelByIdRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailModifyRequest;
import com.wanmi.sbc.order.api.response.leadertradedetail.LeaderTradeDetailAddResponse;
import com.wanmi.sbc.order.api.response.leadertradedetail.LeaderTradeDetailModifyResponse;
import com.wanmi.sbc.order.leadertradedetail.model.root.LeaderTradeDetail;
import com.wanmi.sbc.order.leadertradedetail.service.LeaderTradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>团长订单保存服务接口实现</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@RestController
@Validated
public class LeaderTradeDetailController implements LeaderTradeDetailProvider {
	@Autowired
	private LeaderTradeDetailService leaderTradeDetailService;

	@Override
	public BaseResponse<LeaderTradeDetailAddResponse> add(@RequestBody @Valid LeaderTradeDetailAddRequest leaderTradeDetailAddRequest) {
		LeaderTradeDetail leaderTradeDetail = KsBeanUtil.convert(leaderTradeDetailAddRequest, LeaderTradeDetail.class);
		return BaseResponse.success(new LeaderTradeDetailAddResponse(
				leaderTradeDetailService.wrapperVo(leaderTradeDetailService.add(leaderTradeDetail))));
	}

	@Override
	public BaseResponse<LeaderTradeDetailModifyResponse> modify(@RequestBody @Valid LeaderTradeDetailModifyRequest leaderTradeDetailModifyRequest) {
		LeaderTradeDetail leaderTradeDetail = KsBeanUtil.convert(leaderTradeDetailModifyRequest, LeaderTradeDetail.class);
		return BaseResponse.success(new LeaderTradeDetailModifyResponse(
				leaderTradeDetailService.wrapperVo(leaderTradeDetailService.modify(leaderTradeDetail))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LeaderTradeDetailDelByIdRequest leaderTradeDetailDelByIdRequest) {
		LeaderTradeDetail leaderTradeDetail = KsBeanUtil.convert(leaderTradeDetailDelByIdRequest, LeaderTradeDetail.class);
		leaderTradeDetail.setDelFlag(DeleteFlag.YES);
		leaderTradeDetailService.deleteById(leaderTradeDetail);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LeaderTradeDetailDelByIdListRequest leaderTradeDetailDelByIdListRequest) {
		List<LeaderTradeDetail> leaderTradeDetailList = leaderTradeDetailDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				LeaderTradeDetail leaderTradeDetail = KsBeanUtil.convert(Id, LeaderTradeDetail.class);
				leaderTradeDetail.setDelFlag(DeleteFlag.YES);
				return leaderTradeDetail;
			}).collect(Collectors.toList());
		leaderTradeDetailService.deleteByIdList(leaderTradeDetailList);
		return BaseResponse.SUCCESSFUL();
	}

}

