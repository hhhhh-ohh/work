package com.wanmi.sbc.message.provider.impl.storenoticesend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendQueryProvider;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeQueryRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendByIdRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendListRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendPageRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendQueryRequest;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendByIdResponse;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendListResponse;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendPageResponse;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.vo.StoreNoticeSendVO;
import com.wanmi.sbc.message.storenoticescope.model.root.StoreNoticeScope;
import com.wanmi.sbc.message.storenoticescope.service.StoreNoticeScopeService;
import com.wanmi.sbc.message.storenoticesend.model.root.StoreNoticeSend;
import com.wanmi.sbc.message.storenoticesend.service.StoreNoticeSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商家公告查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@RestController
@Validated
public class StoreNoticeSendQueryController implements StoreNoticeSendQueryProvider {

	@Autowired private StoreNoticeSendService storeNoticeSendService;

	@Autowired private StoreNoticeScopeService storeNoticeScopeService;

	@Override
	public BaseResponse<StoreNoticeSendPageResponse> page(@RequestBody @Valid StoreNoticeSendPageRequest storeNoticeSendPageReq) {
		StoreNoticeSendQueryRequest queryReq = KsBeanUtil.convert(storeNoticeSendPageReq, StoreNoticeSendQueryRequest.class);
		Page<StoreNoticeSend> storeNoticeSendPage = storeNoticeSendService.page(queryReq);
		Page<StoreNoticeSendVO> newPage = storeNoticeSendPage.map(entity -> storeNoticeSendService.wrapperVo(entity));
		MicroServicePage<StoreNoticeSendVO> microPage = new MicroServicePage<>(newPage, storeNoticeSendPageReq.getPageable());
		StoreNoticeSendPageResponse finalRes = new StoreNoticeSendPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<StoreNoticeSendListResponse> list(@RequestBody @Valid StoreNoticeSendListRequest storeNoticeSendListReq) {
		StoreNoticeSendQueryRequest queryReq = KsBeanUtil.convert(storeNoticeSendListReq, StoreNoticeSendQueryRequest.class);
		List<StoreNoticeSend> storeNoticeSendList = storeNoticeSendService.list(queryReq);
		List<StoreNoticeSendVO> newList = storeNoticeSendList.stream().map(entity -> storeNoticeSendService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new StoreNoticeSendListResponse(newList));
	}

	@Override
	public BaseResponse<StoreNoticeSendByIdResponse> getById(@RequestBody @Valid StoreNoticeSendByIdRequest storeNoticeSendByIdRequest) {
		StoreNoticeSendVO storeNoticeSendVO = storeNoticeSendService.getOneWithScope(storeNoticeSendByIdRequest.getId());
		return BaseResponse.success(new StoreNoticeSendByIdResponse(storeNoticeSendVO));
	}
}

