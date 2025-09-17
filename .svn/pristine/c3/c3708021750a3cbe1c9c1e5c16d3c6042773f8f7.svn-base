package com.wanmi.sbc.marketing.api.provider.bargain;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.bargain.OriginateRequest;
import com.wanmi.sbc.marketing.api.request.bargain.UpdateTradeRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.UpdateStockRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>砍价保存服务Provider</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BargainSaveProvider")
public interface BargainSaveProvider {

	/**
	 * 发起砍价
	 *
	 * @param request
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/originate")
	BaseResponse<BargainVO> originate(@RequestBody @Valid OriginateRequest request);

	/**
	 * 同步订单号
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/updateTrade")
	BaseResponse commitTrade(@RequestBody @Valid UpdateTradeRequest request);

	/**
	 * 取消支付订单
	 * @param bargainId
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/cancelTrade")
	BaseResponse cancelTrade(@RequestBody Long bargainId);

	/**
	 * 扣减库存
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/updateStock")
	BaseResponse subStock(@RequestBody @Valid UpdateStockRequest request);

	/**
	 * 库存回填
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/addStock")
	BaseResponse addStock(@RequestBody @Valid UpdateStockRequest request);

}

