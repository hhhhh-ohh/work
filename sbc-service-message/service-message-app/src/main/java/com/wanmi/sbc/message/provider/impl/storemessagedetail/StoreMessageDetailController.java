package com.wanmi.sbc.message.provider.impl.storemessagedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.storemessagedetail.StoreMessageDetailProvider;
import com.wanmi.sbc.message.api.request.storemessagedetail.*;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailAddResponse;
import com.wanmi.sbc.message.storemessagedetail.model.root.StoreMessageDetail;
import com.wanmi.sbc.message.storemessagedetail.service.StoreMessageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>商家消息/公告保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@RestController
@Validated
public class StoreMessageDetailController implements StoreMessageDetailProvider {

	@Autowired
	private StoreMessageDetailService storeMessageDetailService;

	@Override
	public BaseResponse<StoreMessageDetailAddResponse> add(@RequestBody @Valid StoreMessageDetailAddRequest storeMessageDetailAddRequest) {
		StoreMessageDetail storeMessageDetail = KsBeanUtil.convert(storeMessageDetailAddRequest, StoreMessageDetail.class);
		return BaseResponse.success(new StoreMessageDetailAddResponse(
				storeMessageDetailService.wrapperVo(storeMessageDetailService.add(storeMessageDetail))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid StoreMessageDetailDelByIdRequest delByIdRequest) {
		storeMessageDetailService.deleteById(delByIdRequest.getId(), delByIdRequest.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse readById(@Valid StoreMessageDetailReadByIdRequest readByIdRequest) {
		storeMessageDetailService.readById(readByIdRequest.getId(), readByIdRequest.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid StoreMessageDetailDelByIdListRequest delByIdListRequest) {
		storeMessageDetailService.deleteByIdList(delByIdListRequest.getIdList(), delByIdListRequest.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse batchRead(@RequestBody @Valid StoreMessageDetailListRequest storeMessageDetailListReq) {
		StoreMessageDetailQueryRequest queryReq = KsBeanUtil.convert(storeMessageDetailListReq, StoreMessageDetailQueryRequest.class);
		storeMessageDetailService.batchRead(queryReq);
		return BaseResponse.SUCCESSFUL();
	}

}

