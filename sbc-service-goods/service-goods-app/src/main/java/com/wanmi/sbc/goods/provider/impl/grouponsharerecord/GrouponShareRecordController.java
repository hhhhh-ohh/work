package com.wanmi.sbc.goods.provider.impl.grouponsharerecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.grouponsharerecord.GrouponShareRecordProvider;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordAddRequest;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordDelByIdListRequest;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordDelByIdRequest;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordModifyRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordAddResponse;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordModifyResponse;
import com.wanmi.sbc.goods.grouponsharerecord.model.root.GrouponShareRecord;
import com.wanmi.sbc.goods.grouponsharerecord.service.GrouponShareRecordService;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoDetailResponse;
import com.wanmi.sbc.goods.info.service.GoodsInfoSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>拼团分享访问记录保存服务接口实现</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@RestController
@Validated
public class GrouponShareRecordController implements GrouponShareRecordProvider {
	@Autowired
	private GrouponShareRecordService grouponShareRecordService;

	@Autowired
	private GoodsInfoSiteService goodsInfoSiteService;

	@Override
	public BaseResponse<GrouponShareRecordAddResponse> add(@RequestBody @Valid GrouponShareRecordAddRequest grouponShareRecordAddRequest) {
		GrouponShareRecord grouponShareRecord = KsBeanUtil.convert(grouponShareRecordAddRequest, GrouponShareRecord.class);
		grouponShareRecord.setCreateTime(LocalDateTime.now());
		if (Objects.isNull(grouponShareRecordAddRequest.getGoodsId())
				|| Objects.isNull(grouponShareRecordAddRequest.getStoreId())
				|| Objects.isNull(grouponShareRecordAddRequest.getCompanyInfoId())) {
			GoodsInfoRequest goodsInfoRequest = new GoodsInfoRequest();
			goodsInfoRequest.setGoodsInfoId(grouponShareRecordAddRequest.getGoodsInfoId());
			GoodsInfoDetailResponse goodsInfoDetailResponse = goodsInfoSiteService.detail(goodsInfoRequest);
			grouponShareRecord.setCompanyInfoId(goodsInfoDetailResponse.getGoodsInfo().getCompanyInfoId());
			grouponShareRecord.setStoreId(goodsInfoDetailResponse.getGoodsInfo().getStoreId());
			grouponShareRecord.setGoodsId(goodsInfoDetailResponse.getGoodsInfo().getGoodsId());
		}
		return BaseResponse.success(new GrouponShareRecordAddResponse(
				grouponShareRecordService.wrapperVo(grouponShareRecordService.add(grouponShareRecord))));
	}

	@Override
	public BaseResponse<GrouponShareRecordModifyResponse> modify(@RequestBody @Valid GrouponShareRecordModifyRequest grouponShareRecordModifyRequest) {
		GrouponShareRecord grouponShareRecord = KsBeanUtil.convert(grouponShareRecordModifyRequest, GrouponShareRecord.class);
		return BaseResponse.success(new GrouponShareRecordModifyResponse(
				grouponShareRecordService.wrapperVo(grouponShareRecordService.modify(grouponShareRecord))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GrouponShareRecordDelByIdRequest grouponShareRecordDelByIdRequest) {
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GrouponShareRecordDelByIdListRequest grouponShareRecordDelByIdListRequest) {
		return BaseResponse.SUCCESSFUL();
	}

}

