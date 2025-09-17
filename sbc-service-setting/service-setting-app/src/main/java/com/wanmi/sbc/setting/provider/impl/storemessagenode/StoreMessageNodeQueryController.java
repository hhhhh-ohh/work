package com.wanmi.sbc.setting.provider.impl.storemessagenode;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storemessagenode.StoreMessageNodeQueryProvider;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeListRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeQueryRequest;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeByIdResponse;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeListResponse;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeVO;
import com.wanmi.sbc.setting.storemessagenode.model.root.StoreMessageNode;
import com.wanmi.sbc.setting.storemessagenode.service.StoreMessageNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商家消息节点查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@RestController
@Validated
public class StoreMessageNodeQueryController implements StoreMessageNodeQueryProvider {
	@Autowired
	private StoreMessageNodeService storeMessageNodeService;

	@Override
	public BaseResponse<StoreMessageNodeListResponse> list(@RequestBody @Valid StoreMessageNodeListRequest storeMessageNodeListReq) {
		StoreMessageNodeQueryRequest queryReq = KsBeanUtil.convert(storeMessageNodeListReq, StoreMessageNodeQueryRequest.class);
		List<StoreMessageNode> storeMessageNodeList = storeMessageNodeService.list(queryReq);
		List<StoreMessageNodeVO> newList = storeMessageNodeList.stream().map(entity -> storeMessageNodeService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new StoreMessageNodeListResponse(newList));
	}

	@Override
	public BaseResponse<StoreMessageNodeByIdResponse> getById(@RequestBody @Valid StoreMessageNodeByIdRequest storeMessageNodeByIdRequest) {
		StoreMessageNode storeMessageNode =
		storeMessageNodeService.getOne(storeMessageNodeByIdRequest.getId());
		return BaseResponse.success(new StoreMessageNodeByIdResponse(storeMessageNodeService.wrapperVo(storeMessageNode)));
	}
}

