package com.wanmi.sbc.message.provider.impl.storemessagedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.storemessagedetail.StoreMessageDetailQueryProvider;
import com.wanmi.sbc.message.api.request.storemessagedetail.*;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailByIdResponse;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailListResponse;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailPageResponse;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailTopListResponse;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.vo.StoreMessageDetailVO;
import com.wanmi.sbc.message.storemessagedetail.model.root.StoreMessageDetail;
import com.wanmi.sbc.message.storemessagedetail.service.StoreMessageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商家消息/公告查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@RestController
@Validated
public class StoreMessageDetailQueryController implements StoreMessageDetailQueryProvider {
	@Autowired
	private StoreMessageDetailService storeMessageDetailService;

	@Override
	public BaseResponse<StoreMessageDetailPageResponse> page(@RequestBody @Valid StoreMessageDetailPageRequest storeMessageDetailPageReq) {
		StoreMessageDetailQueryRequest queryReq = KsBeanUtil.convert(storeMessageDetailPageReq, StoreMessageDetailQueryRequest.class);
		Page<StoreMessageDetail> storeMessageDetailPage = storeMessageDetailService.page(queryReq);
		Page<StoreMessageDetailVO> newPage = storeMessageDetailPage.map(entity -> storeMessageDetailService.wrapperVo(entity));
		MicroServicePage<StoreMessageDetailVO> microPage = new MicroServicePage<>(newPage, storeMessageDetailPageReq.getPageable());
		StoreMessageDetailPageResponse finalRes = new StoreMessageDetailPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<StoreMessageDetailTopListResponse> topList(@Valid StoreMessageDetailTopListRequest topListRequest) {
		Long storeId = topListRequest.getStoreId();
		List<Long> joinIdList = topListRequest.getJoinIdList();
		// 最近50条
		StoreMessageDetailQueryRequest pageReq = new StoreMessageDetailQueryRequest();
		pageReq.setStoreId(storeId);
		pageReq.setJoinIdList(joinIdList);
		pageReq.setIsShowNotice(Boolean.TRUE);
		pageReq.setDelFlag(DeleteFlag.NO);
		pageReq.setPageSize(Constants.NUM_50);
		pageReq.putSort("isRead", "asc");
		pageReq.putSort("sendTime", "desc");
		List<StoreMessageDetail> messageDetailList = storeMessageDetailService.page(pageReq).getContent();
		// 转化为VO
		List<StoreMessageDetailVO> messageDetailVOList = KsBeanUtil.convert(messageDetailList, StoreMessageDetailVO.class);
		// 未读数量
		StoreMessageDetailQueryRequest countRequest = StoreMessageDetailQueryRequest.builder()
				.delFlag(DeleteFlag.NO)
				.isRead(ReadFlag.NO)
				.isShowNotice(Boolean.TRUE)
				.joinIdList(topListRequest.getJoinIdList())
				.storeId(topListRequest.getStoreId()).build();
		Long unReadCount = storeMessageDetailService.count(countRequest);
		// 封装出参
		StoreMessageDetailTopListResponse response = new StoreMessageDetailTopListResponse();
		response.setUnReadCount(unReadCount);
		response.setMessageDetailVOList(messageDetailVOList);
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse<StoreMessageDetailListResponse> list(@RequestBody @Valid StoreMessageDetailListRequest storeMessageDetailListReq) {
		StoreMessageDetailQueryRequest queryReq = KsBeanUtil.convert(storeMessageDetailListReq, StoreMessageDetailQueryRequest.class);
		List<StoreMessageDetail> storeMessageDetailList = storeMessageDetailService.list(queryReq);
		List<StoreMessageDetailVO> newList = storeMessageDetailList.stream().map(entity -> storeMessageDetailService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new StoreMessageDetailListResponse(newList));
	}

	@Override
	public BaseResponse<StoreMessageDetailByIdResponse> getById(@RequestBody @Valid StoreMessageDetailByIdRequest storeMessageDetailByIdRequest) {
		StoreMessageDetail storeMessageDetail =
		storeMessageDetailService.getOne(storeMessageDetailByIdRequest.getId(), storeMessageDetailByIdRequest.getStoreId());
		return BaseResponse.success(new StoreMessageDetailByIdResponse(storeMessageDetailService.wrapperVo(storeMessageDetail)));
	}
}

