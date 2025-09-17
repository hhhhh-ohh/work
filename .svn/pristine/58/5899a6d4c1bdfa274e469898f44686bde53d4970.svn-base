package com.wanmi.sbc.goods.provider.impl.grouponsharerecord;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.grouponsharerecord.GrouponShareRecordQueryProvider;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordPageRequest;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordQueryRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordPageResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordListRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordListResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordByIdRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GrouponShareRecordVO;
import com.wanmi.sbc.goods.grouponsharerecord.service.GrouponShareRecordService;
import com.wanmi.sbc.goods.grouponsharerecord.model.root.GrouponShareRecord;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>拼团分享访问记录查询服务接口实现</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@RestController
@Validated
public class GrouponShareRecordQueryController implements GrouponShareRecordQueryProvider {
	@Autowired
	private GrouponShareRecordService grouponShareRecordService;

	@Override
	public BaseResponse<GrouponShareRecordPageResponse> page(@RequestBody @Valid GrouponShareRecordPageRequest grouponShareRecordPageReq) {
		GrouponShareRecordQueryRequest queryReq = KsBeanUtil.convert(grouponShareRecordPageReq, GrouponShareRecordQueryRequest.class);
		Page<GrouponShareRecord> grouponShareRecordPage = grouponShareRecordService.page(queryReq);
		Page<GrouponShareRecordVO> newPage = grouponShareRecordPage.map(entity -> grouponShareRecordService.wrapperVo(entity));
		MicroServicePage<GrouponShareRecordVO> microPage = new MicroServicePage<>(newPage, grouponShareRecordPageReq.getPageable());
		GrouponShareRecordPageResponse finalRes = new GrouponShareRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GrouponShareRecordListResponse> list(@RequestBody @Valid GrouponShareRecordListRequest grouponShareRecordListReq) {
		GrouponShareRecordQueryRequest queryReq = KsBeanUtil.convert(grouponShareRecordListReq, GrouponShareRecordQueryRequest.class);
		List<GrouponShareRecord> grouponShareRecordList = grouponShareRecordService.list(queryReq);
		List<GrouponShareRecordVO> newList = grouponShareRecordList.stream().map(entity -> grouponShareRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GrouponShareRecordListResponse(newList));
	}

	@Override
	public BaseResponse<GrouponShareRecordByIdResponse> getById(@RequestBody @Valid GrouponShareRecordByIdRequest grouponShareRecordByIdRequest) {
		GrouponShareRecord grouponShareRecord =
		grouponShareRecordService.getOne(grouponShareRecordByIdRequest.getId(), grouponShareRecordByIdRequest.getStoreId());
		return BaseResponse.success(new GrouponShareRecordByIdResponse(grouponShareRecordService.wrapperVo(grouponShareRecord)));
	}

}

