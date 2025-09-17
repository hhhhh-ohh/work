package com.wanmi.sbc.marketing.api.provider.drawprize;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeAddRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeDelByIdListRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeModifyRequest;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeAddResponse;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抽奖活动奖品表保存服务Provider</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@FeignClient(value = "${application.marketing.name}", contextId = "DrawPrizeSaveProvider")
public interface DrawPrizeSaveProvider {

	/**
	 * 新增抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizeAddRequest 抽奖活动奖品表新增参数结构 {@link DrawPrizeAddRequest}
	 * @return 新增的抽奖活动奖品表信息 {@link DrawPrizeAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/add")
	BaseResponse<DrawPrizeAddResponse> add(@RequestBody @Valid DrawPrizeAddRequest drawPrizeAddRequest);

	/**
	 * 修改抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizeModifyRequest 抽奖活动奖品表修改参数结构 {@link DrawPrizeModifyRequest}
	 * @return 修改的抽奖活动奖品表信息 {@link DrawPrizeModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/modify")
	BaseResponse<DrawPrizeModifyResponse> modify(@RequestBody @Valid DrawPrizeModifyRequest drawPrizeModifyRequest);

	/**
	 * 单个删除抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizeDelByIdRequest 单个删除参数结构 {@link DrawPrizeByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid DrawPrizeByIdRequest drawPrizeDelByIdRequest);

	/**
	 * 批量删除抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizeDelByIdListRequest 批量删除参数结构 {@link DrawPrizeDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid DrawPrizeDelByIdListRequest drawPrizeDelByIdListRequest);

	/**
	 * 扣除奖品库存
	 * @param drawPrizeDelByIdRequest
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/sub-prize-stock")
	BaseResponse subPrizeStock(@RequestBody @Valid DrawPrizeByIdRequest drawPrizeDelByIdRequest);

}

