package com.wanmi.sbc.marketing.api.provider.bargaingoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsValidateRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>砍价商品查询服务Provider</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BargainGoodsQueryProvider")
public interface BargainGoodsQueryProvider {

	/**
	 * 分页查询砍价商品API
	 *
	 * @param bargainGoodsPageReq 分页请求参数和筛选对象 {@link BargainGoodsQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/page")
	BaseResponse<MicroServicePage<BargainGoodsVO>> page(@RequestBody @Valid BargainGoodsQueryRequest bargainGoodsPageReq);

	/**
	 * 分页查询砍价商品API
	 *
	 * @param bargainGoodsPageReq 分页请求参数和筛选对象 {@link BargainGoodsQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods-new/page")
	BaseResponse<MicroServicePage<BargainGoodsVO>> pageNew(@RequestBody @Valid BargainGoodsQueryRequest bargainGoodsPageReq);

	/**
	 * 列表查询砍价商品API
	 *
	 * @param bargainGoodsListReq 列表请求参数和筛选对象 {@link BargainGoodsQueryRequest}
	 * @author
	 */
	@PostMapping("/marketing/${application.marketing.version}/bargaingoods/list")
	BaseResponse<List<BargainGoodsVO>> listForCustomer(@RequestBody BargainGoodsQueryRequest bargainGoodsListReq);

    /**
     * 分页查询砍价商品API
     *
     * @param bargainGoodsPageReq 分页请求参数和筛选对象 {@link BargainGoodsQueryRequest}
     * @author
     */
    @PostMapping("/marketing/${application.marketing.version}/bargaingoods/pageForCustomer")
    BaseResponse<MicroServicePage<BargainGoodsVO>> pageForCustomer(@RequestBody @Valid BargainGoodsQueryRequest bargainGoodsPageReq);

    /**
     * 单个查询砍价商品API
     *
     * @param request 单个查询砍价商品请求参数 {@link BargainGoodsByIdRequest}
     * @author
     */
    @PostMapping("/marketing/${application.marketing.version}/bargaingoods/get-by-id")
    BaseResponse<BargainGoodsVO> getById(@RequestBody @Valid BargainGoodsQueryRequest request);

	/**
	 * 互斥验证
	 *
	 * @author dyt
	 * @param request 互斥请求参数 {@link BargainGoodsValidateRequest}
	 */
	@PostMapping("/marketing/${application.goods.version}/bargaingoods/validate")
	BaseResponse validate(@RequestBody @Valid BargainGoodsValidateRequest request);
}

