package com.wanmi.sbc.message.provider.impl.storenoticescope;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.storenoticescope.StoreNoticeScopeProvider;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeAddRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeAddResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeModifyRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeModifyResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeDelByIdRequest;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeDelByIdListRequest;
import com.wanmi.sbc.message.storenoticescope.service.StoreNoticeScopeService;
import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>商家公告发送范围保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@RestController
@Validated
public class StoreNoticeScopeController implements StoreNoticeScopeProvider {
	@Autowired
	private StoreNoticeScopeService storeNoticeScopeService;

	@Override
	public BaseResponse<StoreNoticeScopeAddResponse> add(@RequestBody @Valid StoreNoticeScopeAddRequest storeNoticeScopeAddRequest) {
		StoreNoticeScope storeNoticeScope = KsBeanUtil.convert(storeNoticeScopeAddRequest, StoreNoticeScope.class);
		return BaseResponse.success(new StoreNoticeScopeAddResponse(
				storeNoticeScopeService.wrapperVo(storeNoticeScopeService.add(storeNoticeScope))));
	}

	@Override
	public BaseResponse<StoreNoticeScopeModifyResponse> modify(@RequestBody @Valid StoreNoticeScopeModifyRequest storeNoticeScopeModifyRequest) {
		StoreNoticeScope storeNoticeScope = KsBeanUtil.convert(storeNoticeScopeModifyRequest, StoreNoticeScope.class);
		return BaseResponse.success(new StoreNoticeScopeModifyResponse(
				storeNoticeScopeService.wrapperVo(storeNoticeScopeService.modify(storeNoticeScope))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid StoreNoticeScopeDelByIdRequest storeNoticeScopeDelByIdRequest) {
		storeNoticeScopeService.deleteById(storeNoticeScopeDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid StoreNoticeScopeDelByIdListRequest storeNoticeScopeDelByIdListRequest) {
		storeNoticeScopeService.deleteByIdList(storeNoticeScopeDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

