package com.wanmi.sbc.marketing.provider.impl.drawprize;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.drawprize.DrawPrizeQueryProvider;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeListRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizePageRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeQueryRequest;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeListResponse;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizePageResponse;
import com.wanmi.sbc.marketing.bean.vo.DrawPrizeVO;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import com.wanmi.sbc.marketing.drawprize.service.DrawPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>抽奖活动奖品表查询服务接口实现</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@RestController
@Validated
public class DrawPrizeQueryController implements DrawPrizeQueryProvider {
	@Autowired
	private DrawPrizeService drawPrizeService;

	@Override
	public BaseResponse<DrawPrizePageResponse> page(@RequestBody @Valid DrawPrizePageRequest drawPrizePageReq) {
		DrawPrizeQueryRequest queryReq = new DrawPrizeQueryRequest();
		KsBeanUtil.copyPropertiesThird(drawPrizePageReq, queryReq);
		Page<DrawPrize> drawPrizePage = drawPrizeService.page(queryReq);
		Page<DrawPrizeVO> newPage = drawPrizePage.map(entity -> drawPrizeService.wrapperVo(entity));
		MicroServicePage<DrawPrizeVO> microPage = new MicroServicePage<>(newPage, drawPrizePageReq.getPageable());
		DrawPrizePageResponse finalRes = new DrawPrizePageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<DrawPrizeListResponse> list(@RequestBody @Valid DrawPrizeListRequest drawPrizeListReq) {
		DrawPrizeQueryRequest queryReq = new DrawPrizeQueryRequest();
		KsBeanUtil.copyPropertiesThird(drawPrizeListReq, queryReq);
		List<DrawPrize> drawPrizeList = drawPrizeService.list(queryReq);
		List<DrawPrizeVO> newList = drawPrizeList.stream().map(entity -> drawPrizeService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new DrawPrizeListResponse(newList));
	}

	@Override
	public BaseResponse<DrawPrizeByIdResponse> getById(@RequestBody @Valid DrawPrizeByIdRequest drawPrizeByIdRequest) {
		DrawPrize drawPrize = drawPrizeService.getById(drawPrizeByIdRequest.getId());
		return BaseResponse.success(new DrawPrizeByIdResponse(drawPrizeService.wrapperVo(drawPrize)));
	}

}

