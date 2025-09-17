package com.wanmi.sbc.setting.provider.impl.storemessagenode;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storemessagenode.StoreMessageNodeProvider;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeAddRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeDelByIdRequest;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeAddResponse;
import com.wanmi.sbc.setting.storemessagenode.model.root.StoreMessageNode;
import com.wanmi.sbc.setting.storemessagenode.service.StoreMessageNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>商家消息节点保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@RestController
@Validated
public class StoreMessageNodeController implements StoreMessageNodeProvider {
	@Autowired
	private StoreMessageNodeService storeMessageNodeService;

	@Override
	public BaseResponse<StoreMessageNodeAddResponse> add(@RequestBody @Valid StoreMessageNodeAddRequest storeMessageNodeAddRequest) {
		StoreMessageNode storeMessageNode = KsBeanUtil.convert(storeMessageNodeAddRequest, StoreMessageNode.class);
		return BaseResponse.success(new StoreMessageNodeAddResponse(
				storeMessageNodeService.wrapperVo(storeMessageNodeService.add(storeMessageNode))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid StoreMessageNodeDelByIdRequest storeMessageNodeDelByIdRequest) {
		StoreMessageNode storeMessageNode = KsBeanUtil.convert(storeMessageNodeDelByIdRequest, StoreMessageNode.class);
		storeMessageNode.setDelFlag(DeleteFlag.YES);
		storeMessageNodeService.deleteById(storeMessageNode);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid StoreMessageNodeDelByIdListRequest storeMessageNodeDelByIdListRequest) {
		storeMessageNodeService.deleteByIdList(storeMessageNodeDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

