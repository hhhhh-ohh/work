package com.wanmi.sbc.elastic.provider.impl.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.customer.EsStoreEvaluateSumQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsStoreEvaluateSumPageResponse;
import com.wanmi.sbc.elastic.customer.service.EsStoreEvaluateSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>店铺评价查询服务Provider</p>
 * @author houshuai
 * @date 2020-12-03 10:59:09
 */
@RestController
public class EsStoreEvaluateSumQueryController implements EsStoreEvaluateSumQueryProvider {

	@Autowired
	private EsStoreEvaluateSumService storeEvaluateSumService;


	/**
	 * 分页查询店铺评价
	 * @param storeEvaluateSumPageRequest 分页请求参数和筛选对象 {@link }
	 * @return
	 */
	@Override
	public BaseResponse<EsStoreEvaluateSumPageResponse> page(@RequestBody @Valid EsStoreEvaluateSumPageRequest storeEvaluateSumPageRequest) {
		return storeEvaluateSumService.page(storeEvaluateSumPageRequest);
	}

	/**
	 * 初始化店铺评价数据
	 * @param request
	 * @return
	 */
	@Override
	public BaseResponse init(EsStoreEvaluateSumInitRequest request) {
		storeEvaluateSumService.init(request);
		return BaseResponse.SUCCESSFUL();
	}
}