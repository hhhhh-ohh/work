package com.wanmi.sbc.marketing.api.provider.bargainjoin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>帮砍记录查询服务Provider</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BargainJoinQueryProvider")
public interface BargainJoinQueryProvider {

	/**
	 * 分页查询帮砍记录API
	 *
	 * @param bargainJoinPageReq 分页请求参数和筛选对象 {@link BargainJoinQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargainjoin/page")
	BaseResponse<MicroServicePage<BargainJoinVO>> page(@RequestBody @Valid BargainJoinQueryRequest bargainJoinPageReq);

	/**
	 * 列表查询帮砍记录API
	 *
	 * @param bargainJoinListReq 列表请求参数和筛选对象 {@link BargainJoinQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargainjoin/list")
	BaseResponse<List<BargainJoinVO>> list(@RequestBody @Valid BargainJoinQueryRequest bargainJoinListReq);

	/**
	 * 单个查询帮砍记录API
	 *
	 * @param bargainJoinByIdRequest 单个查询帮砍记录请求参数 {@link BargainJoinByIdRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargainjoin/get-by-id")
	BaseResponse<BargainJoinVO> getById(@RequestBody @Valid BargainJoinByIdRequest bargainJoinByIdRequest);

}

