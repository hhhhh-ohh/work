package com.wanmi.sbc.message.provider.impl.storenoticescope;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.storenoticescope.StoreNoticeScopeQueryProvider;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeByIdRequest;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeListRequest;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopePageRequest;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeQueryRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeByIdResponse;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeListResponse;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopePageResponse;
import com.wanmi.sbc.message.bean.vo.StoreNoticeScopeVO;
import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import com.wanmi.sbc.message.storenoticescope.service.StoreNoticeScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商家公告发送范围查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@RestController
@Validated
public class StoreNoticeScopeQueryController implements StoreNoticeScopeQueryProvider {
	@Autowired
	private StoreNoticeScopeService storeNoticeScopeService;

	@Override
	public BaseResponse<StoreNoticeScopePageResponse> page(@RequestBody @Valid StoreNoticeScopePageRequest storeNoticeScopePageReq) {
		StoreNoticeScopeQueryRequest queryReq = KsBeanUtil.convert(storeNoticeScopePageReq, StoreNoticeScopeQueryRequest.class);
		Page<StoreNoticeScope> storeNoticeScopePage = storeNoticeScopeService.page(queryReq);
		Page<StoreNoticeScopeVO> newPage = storeNoticeScopePage.map(entity -> storeNoticeScopeService.wrapperVo(entity));
		MicroServicePage<StoreNoticeScopeVO> microPage = new MicroServicePage<>(newPage, storeNoticeScopePageReq.getPageable());
		StoreNoticeScopePageResponse finalRes = new StoreNoticeScopePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<StoreNoticeScopeListResponse> list(@RequestBody @Valid StoreNoticeScopeListRequest storeNoticeScopeListReq) {
		StoreNoticeScopeQueryRequest queryReq = KsBeanUtil.convert(storeNoticeScopeListReq, StoreNoticeScopeQueryRequest.class);
		List<StoreNoticeScope> storeNoticeScopeList = storeNoticeScopeService.list(queryReq);
		List<StoreNoticeScopeVO> newList = storeNoticeScopeList.stream().map(entity -> storeNoticeScopeService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new StoreNoticeScopeListResponse(newList));
	}

	@Override
	public BaseResponse<StoreNoticeScopeByIdResponse> getById(@RequestBody @Valid StoreNoticeScopeByIdRequest storeNoticeScopeByIdRequest) {
		StoreNoticeScope storeNoticeScope =
		storeNoticeScopeService.getOne(storeNoticeScopeByIdRequest.getId());
		return BaseResponse.success(new StoreNoticeScopeByIdResponse(storeNoticeScopeService.wrapperVo(storeNoticeScope)));
	}
}

