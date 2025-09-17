package com.wanmi.sbc.marketing.provider.impl.bargain;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargain.BargainAddRequest;
import com.wanmi.sbc.marketing.api.request.bargain.OriginateRequest;
import com.wanmi.sbc.marketing.api.request.bargain.UpdateTradeRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.UpdateStockRequest;
import com.wanmi.sbc.marketing.bargain.service.BargainService;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>砍价保存服务接口实现</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@RestController
@Validated
public class BargainSaveController implements BargainSaveProvider {
	@Autowired
	private BargainService bargainService;

	/**
	 * 新增砍价API
	 *
	 * @param request 砍价新增参数结构 {@link BargainAddRequest}
	 * @return 新增的砍价信息 {@link BargainVO}
	 * @author
	 */
	@Override
	public BaseResponse<BargainVO> originate(@RequestBody @Valid OriginateRequest request) {
		BargainVO bargainRecordVO = bargainService.originate(request);
		return BaseResponse.success(bargainRecordVO);
	}

	@Override
	public BaseResponse commitTrade(@RequestBody @Valid UpdateTradeRequest request) {
		bargainService.commitTrade(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse cancelTrade(Long bargainId) {
		bargainService.cancelTrade(bargainId);
		return BaseResponse.SUCCESSFUL();
	}


	@Override
	public BaseResponse subStock(@RequestBody @Valid UpdateStockRequest request) {
		bargainService.subStock(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse addStock(@RequestBody @Valid UpdateStockRequest request) {
		bargainService.addStock(request);
		return BaseResponse.SUCCESSFUL();
	}

}

