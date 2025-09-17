package com.wanmi.sbc.marketing.api.provider.drawrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.drawrecord.*;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordAddResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抽奖记录表保存服务Provider</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@FeignClient(value = "${application.marketing.name}", contextId = "DrawRecordSaveProvider")
public interface DrawRecordSaveProvider {

	/**
	 * 新增抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordAddRequest 抽奖记录表新增参数结构 {@link DrawRecordAddRequest}
	 * @return 新增的抽奖记录表信息 {@link DrawRecordAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/add")
	BaseResponse<DrawRecordAddResponse> add(@RequestBody @Valid DrawRecordAddRequest drawRecordAddRequest);

	/**
	 * 修改抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordModifyRequest 抽奖记录表修改参数结构 {@link DrawRecordModifyRequest}
	 * @return 修改的抽奖记录表信息 {@link DrawRecordModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/modify")
	BaseResponse<DrawRecordModifyResponse> modify(@RequestBody @Valid DrawRecordModifyRequest drawRecordModifyRequest);

	/**
	 * 单个删除抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordDelByIdRequest 单个删除参数结构 {@link DrawRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid DrawRecordDelByIdRequest drawRecordDelByIdRequest);

	/**
	 * 批量删除抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordDelByIdListRequest 批量删除参数结构 {@link DrawRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid DrawRecordDelByIdListRequest drawRecordDelByIdListRequest);

	/**
	 * 批量导入中奖发货数据
	 * @author ghj
	 * @param drawRecordModifyListRequest
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/modify-import-peize-delivery")
	BaseResponse modifyImportPeizeDelivery(@RequestBody @Valid DrawRecordModifyListRequest drawRecordModifyListRequest);

	/**
	 * 领取奖品
	 * @author ghj
	 * @param drawRecordRedeemPrizeRequest
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/modify-redeem-prize")
	BaseResponse<DrawRecordModifyResponse> modifyRedeemPrizeStatus(@RequestBody @Valid DrawRecordRedeemPrizeRequest drawRecordRedeemPrizeRequest);

	/**
	 * 添加奖品中奖发货信息
	 * @param drawRecordAddLogisticrequest
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/add-logistic-by-id")
	BaseResponse<DrawRecordModifyResponse> addLogisticByDrawRecordId(@RequestBody @Valid DrawRecordAddLogisticRequest drawRecordAddLogisticrequest);
}

