package com.wanmi.sbc.marketing.provider.impl.drawprize;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.drawprize.DrawPrizeSaveProvider;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeAddRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeDelByIdListRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeModifyRequest;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeAddResponse;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeModifyResponse;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.service.DrawPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>抽奖活动奖品表保存服务接口实现</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@RestController
@Validated
public class DrawPrizeSaveController implements DrawPrizeSaveProvider {
	@Autowired
	private DrawPrizeService drawPrizeService;

	@Override
	public BaseResponse<DrawPrizeAddResponse> add(@RequestBody @Valid DrawPrizeAddRequest drawPrizeAddRequest) {
		DrawPrize drawPrize = new DrawPrize();
		KsBeanUtil.copyPropertiesThird(drawPrizeAddRequest, drawPrize);
		return BaseResponse.success(new DrawPrizeAddResponse(
				drawPrizeService.wrapperVo(drawPrizeService.add(drawPrize))));
	}


	@Override
	public BaseResponse<DrawPrizeModifyResponse> modify(@RequestBody @Valid DrawPrizeModifyRequest drawPrizeModifyRequest) {
		DrawPrize drawPrize = new DrawPrize();
		KsBeanUtil.copyPropertiesThird(drawPrizeModifyRequest, drawPrize);
		return BaseResponse.success(new DrawPrizeModifyResponse(
				drawPrizeService.wrapperVo(drawPrizeService.modify(drawPrize))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid DrawPrizeByIdRequest drawPrizeDelByIdRequest) {
		drawPrizeService.deleteById(drawPrizeDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid DrawPrizeDelByIdListRequest drawPrizeDelByIdListRequest) {
		drawPrizeService.deleteByIdList(drawPrizeDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse subPrizeStock(@RequestBody @Valid DrawPrizeByIdRequest drawPrizeDelByIdRequest) {
		drawPrizeService.subPrizeStock(drawPrizeDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

}

