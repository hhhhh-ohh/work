package com.wanmi.sbc.marketing.api.provider.bargain;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.request.bargain.BargainByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargain.BargainQueryRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>砍价查询服务Provider</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BargainQueryProvider")
public interface BargainQueryProvider {

	/**
	 * 分页查询砍价API
	 *
	 * @param bargainPageReq 分页请求参数和筛选对象 {@link BargainQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/page")
	BaseResponse<MicroServicePage<BargainVO>> page(@RequestBody @Valid BargainQueryRequest bargainPageReq);

	@PostMapping("/marketing/${application.marketing.version}/bargain/pageForPlatForm")
	BaseResponse<MicroServicePage<BargainVO>> pageForPlatForm(@RequestBody @Valid BargainQueryRequest bargainPageReq);

	/**
	 * 列表查询砍价API
	 *
	 * @param bargainListReq 列表请求参数和筛选对象 {@link BargainQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/list")
	BaseResponse<List<BargainVO>> list(@RequestBody @Valid BargainQueryRequest bargainListReq);

	/**
	 * 单个查询砍价API
	 *
	 * @param bargainByIdRequest 单个查询砍价请求参数 {@link BargainByIdRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargain/get-by-id")
	BaseResponse<BargainVO> getById(@RequestBody @Valid BargainByIdRequest bargainByIdRequest);

	@PostMapping("/marketing/${application.marketing.version}/bargain/Simple")
	BaseResponse<BargainVO> getByIdWithBargainGoods(@RequestBody @Valid BargainByIdRequest bargainByIdRequest);

	@PostMapping("/marketing/${application.marketing.version}/bargain/getByIdForPlatForm")
	BaseResponse<BargainVO> getByIdForPlatForm(@RequestBody @Valid BargainByIdRequest bargainByIdRequest);

	@PostMapping("/marketing/${application.marketing.version}/bargain/commited")
	BaseResponse<Boolean> canCommit(@RequestBody Long bargainId);

	@PostMapping("/marketing/${application.marketing.version}/bargain/getStock")
	BaseResponse<Long> getStock(@RequestBody Long bargainId);

}

